<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>对应客户</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true" id="goods-layout-customertogoods">
	    <div data-options="region:'center',split:true,border:true">
	    	<div id="personnel-toolbar-customertogoods" style="width: 483px; padding: 2px;">
	    		<form action="" id="personnel-form-customertogoodsQuery" method="post" style="padding-left: 5px;">
	    			<table cellpadding="2" cellspacing="0" border="0">
	    				<tr>
	    					<td>区域:</td>
	    					<td><!-- <input type="hidden" name="id" style="width: 80px"/> -->
				    			<!-- <input type="hidden" name="name" style="width: 120px"/> -->
				    			<input type="text" name="salesarea" id="personnel-customer-sortarea" />
				    			<input type="hidden" name="personid" value="${personid }" />
				    		</td>
				    		<td>分类:</td>
				    		<td><input type="text" name="customersort" id="personnel-customer-customersort" /></td>
	    				</tr>
	    				<tr>
	    					<td>客户:</td>
	    					<td><input type="text" name="id" id="personnel-customer-id" style="width: 170px;"/></td>
	    					<td colspan="2" align="right">
	    						<a href="javaScript:void(0);" id="personnel-query-customertogoods" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="personnel-reload-customertogoods" class="button-qr">重置</a>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
	    	</div>
	    	<table id="personnel-table-customertogoods"></table>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right">
			<a href="javaScript:void(0);" id="personnel-save-customertogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
			<a href="javaScript:void(0);" id="personnel-savegoon-customertogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定并继续新增">确定并继续新增</a>
		</div> 
     </div>
    <script type="text/javascript">
    	$("#personnel-customer-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:false,
   			width:170,
   			onlyLeafCheck:false,
   			view: true
   		});
    	
    	$("#personnel-customer-customersort").widget({ //分类
   			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
			//col:'customersort',
   			singleSelect:false,
   			width:173,
   			onlyLeafCheck:false,
   			view: true
   		});
   		
   		$("#personnel-customer-id").customerWidget({ //客户
   			singleSelect:true,
   			onlyLeafCheck:false
   		});
    	$(function(){
    		var employetype = $("#personnel-employetype").val();
    		
    		//$("#personnel-addBrandid").val("${brandids}");
    		var customerid3 = "${customerids}";
    		if("" != $("#personnel-personcustomer").val()){
    			customerid3 = $("#personnel-personcustomer").val() + customerid3;
    		}
			$("#personnel-table-customertogoods").datagrid({
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		checkOnSelect:true,
	  	 		selectOnCheck:true,
	  	 		pageSize:500,
	  	 		pagination:true,
				queryParams:{customerid:customerid3},
				toolbar:'#personnel-toolbar-customertogoods',
				columns:[[  
			        {field:'ck',title:'',width:100,checkbox:true},
			        {field:'id',title:'编码',width:60},  
			        {field:'name',title:'名称',width:210},
			        {field:'salesareaname',title:'所属区域',width:80,isShow:true},
			        {field:'customersortname',title:'所属分类',width:80,isShow:true}
			    ]],
			    onCheck:function(rowIndex,rowData){
                    loading("检测中...");
			    	var ret = personnel_AjaxConn({brandidStr:$("#personnel-addBrandid").val(),customerid:rowData.id,employetype:employetype},'basefiles/checkBrandAndCustomerRepeat.do');
			   		var retJson = $.parseJSON(ret);
                    loaded();
			   		if(!retJson.flag){
			   			$.messager.alert('提醒','已存在该对应品牌和客户的人员!');
			   			$("#personnel-table-customertogoods").datagrid('uncheckRow',rowIndex).datagrid('unselectRow',rowIndex);
			   		}
			    },
			    onCheckAll:function(rows){
                    loading("检测中...");

                    setTimeout(function(){
                        var num = 0;
                        for(var i=0;i<rows.length;i++){
                            var ret = personnel_AjaxConn({brandidStr:$("#personnel-addBrandid").val(),customerid:rows[i].id,employetype:employetype},'basefiles/checkBrandAndCustomerRepeat.do');
                            var retJson = $.parseJSON(ret);
                            if(!retJson.flag){
                                num++;
                                $("#personnel-table-customertogoods").datagrid('uncheckRow',i).datagrid('unselectRow',i);
                            }
                        }
                        loaded();
                        if(num > 0){
                            $.messager.alert('提醒','已存在该对应品牌和客户的人员!');
                        }
                    }, 50);
                }
			});

    		//查询
			$("#personnel-query-customertogoods").click(function(){
	      		var queryJSON = $("#personnel-form-customertogoodsQuery").serializeJSON();
	   			queryJSON['customerid'] = $("#personnel-personcustomer").val();
				$("#personnel-table-customertogoods").datagrid({
					url:'basefiles/getCustomerListForCombobox.do',
					pageNumber:1,
					queryParams:queryJSON
				});
			});
			
			//重置按钮
			$("#personnel-reload-customertogoods").click(function(){
				$("#personnel-form-customertogoodsQuery")[0].reset();
				$("#personnel-customer-sortarea").widget('clear');
				$("#personnel-customer-customersort").widget('clear');
				$("#personnel-customer-id").customerWidget('clear');
				$("#personnel-table-customertogoods").datagrid('loadData',{total:0,rows:[]});
			});
    		
    		$("#personnel-savegoon-customertogoods").click(function(){
                addPersonCustomer(true);
    		});
    		
    		$("#personnel-save-customertogoods").click(function(){
    			addPersonCustomer(false);
    		});
    	});
    	function addPersonCustomer(go){
    	    var customerid = "",customerid2 = "${customerids}";
    			var rows = $("#personnel-table-customertogoods").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请勾选对应客户名称！");
    				return false;
    			}
    			for(var i=0;i<rows.length;i++){
    				customerid += rows[i].id + ",";
    			}
    			$("#personnel-beginCustomeredit").val("1");
    			if("" == $("#personnel-personcustomer").val()){
    				$("#personnel-personcustomer").val(customerid2 + customerid);
    			}else{
    				$("#personnel-personcustomer").val($("#personnel-personcustomer").val() + customerid);
    			}
    			$("#personnel-beforeDelCustomerAdd").val(customerid);
    			var employetype = $("#personnel-employetype").val();
    			$.messager.confirm("警告","是否添加客户?",function(r){
					if(r){
						loading("添加中..");
						$.ajax({
							url:'basefiles/addPersonCustomer.do',
							data:{customerids:customerid,personid:'${personid }',employetype:employetype},
							dataType:'json',
							type:'post',
							success:function(json){
								loaded();
								if(json.flag){
									$.messager.alert("提醒","添加成功");
									$dgPersonnelCustomer.datagrid("reload");
                                    $("#personnel-table-customertogoods").datagrid("reload");
                                    if(go){
                                        var queryJSON = $("#personnel-form-customertogoodsQuery").serializeJSON();
                                        queryJSON['customerids'] = $("#personnel-personcustomer").val();
                                        queryJSON['customerid'] = $("#personnel-personcustomer").val();
                                        $("#personnel-table-customertogoods").datagrid("load",queryJSON);
                                    }else{
                                        $("#personnel-dialog-customer").dialog('close',true);
                                    }
								}else{
									$.messager.alert("提醒","添加失败");
								}
							}
						});
					}
				});
        }
    </script>
  </body>
</html>
