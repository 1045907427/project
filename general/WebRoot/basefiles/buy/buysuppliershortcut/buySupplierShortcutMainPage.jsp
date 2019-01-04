<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="buy-backid-buySupplierAddPage" value="<c:out value="${id }"></c:out>" />
	<div class="easyui-layout" title="供应商简化版" data-options="fit:true" id="sales-layout-buySupplier">
		<div data-options="region:'north',border:false">
	 		<div class="buttonBG" id="sales-buttons-buySupplierShortcut" style="height:26px;"></div>
	   	</div>
	   	<div data-options="region:'center',border:false" >
	   		<div class="easyui-panel" data-options="fit:true" id="sales-panel-buySupplierShortcut">
	   	</div>
	</div>
    <script type="text/javascript">
    	//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
    	
    	function refreshPanel(url){
    		$("#sales-panel-buySupplierShortcut").panel('refresh', url);
    	}
    	function buySupplier_RefreshDataGrid(){
    		try{			
				tabsWindowURL('/basefiles/showBuySupplierShortcutPage.do').$("#buy-buySupplierShortcutListPage-table").datagrid('reload');
			}catch(e){
			}
    	}
    	$(function(){
            var buySupplierShortcut_type = "${type}";
            var buySupplierShortcut_url = "basefiles/showBuySupplierShortcutAddPage.do";
            if(buySupplierShortcut_type == "add"){
                buySupplierShortcut_url = "basefiles/showBuySupplierShortcutAddPage.do"
            }else if(buySupplierShortcut_type == "edit"){
                buySupplierShortcut_url = "basefiles/showBuySupplierShortcutEditPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
            }else if(buySupplierShortcut_type == "view"){
                buySupplierShortcut_url = "basefiles/showBuySupplierShortcutViewPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
            }else if(buySupplierShortcut_type == "copy"){
                buySupplierShortcut_url = "basefiles/showBuySupplierShortcutCopyPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
            }

    		$("#sales-panel-buySupplierShortcut").panel({
				href:buySupplierShortcut_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		$("#sales-buttons-buySupplierShortcut").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/buySupplierAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								refreshPanel('basefiles/showBuySupplierShortcutAddPage.do');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierEditBtn.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#buy-id-buySupplierShortcut").val();
								if(id == ""){
									return false;
								}
								var flag = isDoLockData(id,"t_base_buy_supplier");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								refreshPanel('basefiles/showBuySupplierShortcutEditPage.do?id='+ encodeURIComponent(id));
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSaveBtn.do">
						{
							type: "button-save",
							handler: function(){
								$("#buy-save-buySupplierShortcut").click();
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySuppplierGiveUpBtn.do">
						{
							type:'button-giveup',
							handler:function(){
								var type = $("#sales-buttons-buySupplierShortcut").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.closeTab(currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#buy-backid-buySupplierAddPage").val();
		    						if(id == ""){
		    							return false;
		    						}
		    						$("#sales-panel-buySupplierShortcut").panel('refresh', 'basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(id));
	    						}
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierDeleteBtn.do">
						{
	    					type:'button-delete',
	    					handler: function(){
	    						var id = $("#buy-id-buySupplierShortcut").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","是否删除该供应商信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteBuySupplier.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.delFlag==true){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","删除成功");
								  		        	buySupplier_RefreshDataGrid();
													var data = $("#purchase-buttons-buyOrderPage").buttonWidget("removeData", '');
													if(data && data.id && data.id!=""){
														refreshPanel('basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(data.id));
													}else{
														refreshPanel('basefiles/showBuySupplierShortcutAddPage.do');
													}		
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","删除失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
	    				},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierCopyBtn.do">
						{
							type: "button-copy",
							handler: function(){
								var id = $("#buy-id-buySupplierShortcut").val();
								if(id == ""){
									return false;
								}
								refreshPanel("basefiles/showBuySupplierShortcutCopyPage.do?id="+ encodeURIComponent(id));
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierOpenBtn.do">
						{
	    					type:'button-open',
	    					handler: function(){
	    						var id = $("#buy-id-buySupplierShortcut").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定启用该供应商信息?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openBuySupplier.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	refreshPanel('basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(json.backid));
			  		      							buySupplier_RefreshDataGrid();
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","启用失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierCloseBtn.do">
						{
	    					type:'button-close',
	    					handler: function(){
	    						var id = $("#buy-id-buySupplierShortcut").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定禁用该供应商信息?",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeBuySupplier.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","禁用成功");
			  		      							refreshPanel('basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(json.backid));
			  		      							buySupplier_RefreshDataGrid();
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","禁用失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
	    				},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierBackBtn.do">
						{
	    					type:'button-back',
	    					handler: function(data){
							   	if(data!=null && data.id!=null && data.id!=""){
		    						$("#buy-backid-buySupplierAddPage").val(data.id);
		    						$("#sales-panel-buySupplierShortcut").panel('refresh', 'basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(data.id));
							   	}
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierNextBtn.do">
						{
	    					type:'button-next',
	    					handler: function(data){
							   	if(data!=null && data.id!=null && data.id!=""){
		    						$("#buy-backid-buySupplierAddPage").val(data.id);
		    						$("#sales-panel-buySupplierShortcut").panel('refresh', 'basefiles/showBuySupplierShortcutViewPage.do?id='+ encodeURIComponent(data.id));
							   	}
	    					}
	    				},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'view',
				tname: 't_base_buy_supplier',
				taburl: '/basefiles/showBuySupplierShortcutPage.do',
				datagrid: 'buy-buySupplierShortcutListPage-table',
				id: $("#buy-backid-buySupplierAddPage").val()
			});
    	});
    </script>
  </body>
</html>
