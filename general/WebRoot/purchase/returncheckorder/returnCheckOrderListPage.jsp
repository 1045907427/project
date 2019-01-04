<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购退货验收</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="purchase-buttons-returnCheckOrderListPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="purchase-table-returnCheckOrderListPage"></table>
    		<div id="purchase-table-query-returnCheckOrderListPage" style="padding:2px;height:auto">
				<div>
					<form action="" id="purchase-form-returnCheckOrderListPage" method="post">
						<table class="querytable">
			    			<tr>
			    				<td>业务日期:</td>
			    				<td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td>编号:</td>
			    				<td><input type="text" name="id" style="width: 136px;"/></td>
			    				<td>退货仓库:</td>
			    				<td>
			    				<input type="text" name="storageid" id="purchase-returnCheckOrderListPage-storageid"/>
			    				</td>
			    			</tr>
			    			<tr>
			    				<td>供应商:</td>
			    				<td><input id="purchase-returnCheckOrderListPage-supplier" type="text" name="supplierid" style="width: 225px;"/></td>
			    				
			    				<td>验收状态:</td>
			    				<td>
			    					<select name="ischeck" style="width:136px;">
			    						<option></option>
			    						<option value="0" selected="selected">未验收</option>
			    						<option value="1">已验收</option>
			    					</select>
			    				</td>		    				
			    				<td colspan="2" style="padding-left: 10px">
			    					<a href="javaScript:void(0);" id="purchase-btn-queryReturnOrderListPage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="purchase-btn-reloadReturnOrderListPage" class="button-qr">重置</a>
			    				</td>
			    			</tr>
			    		</table>
					</form>
				</div>
			</div>
    	</div>
    </div>
    
    <script type="text/javascript">
		var SR_footerobject  = null;
    	$(document).ready(function(){
    		var initQueryJSON = $("#purchase-form-returnCheckOrderListPage").serializeJSON();
    		var returnCheckOrderListJson = $("#purchase-table-returnCheckOrderListPage").createGridColumnLoad({
				name:'purchase_returnorder',
				frozenCol : [[
								{field:'idok',checkbox:true,isShow:true}
    			]],
    			commonCol :[[
 								{field:'id',title:'单据编号',width:120,sortable:true},
                                {field:'source',title:'来源类型',width:70,sortable:true,
                                    formatter: function(value){
                                        if(value=="2"){
                                            return "代配送";
                                        }else if(value==-999){
											return "";
										}else{
                                            return "正常单据";
                                        }
                                    }
                                },
                                {field:'billno',title:'来源编号',width:120,sortable:true},
    							{field:'businessdate',title:'业务日期',width:70,sortable:true},
    							{field:'supplierid',title:'供应商编码',width:70},
    							{field:'suppliername',title:'供应商名称',width:100,isShow:true},
    							{field:'handlerid',title:'对方联系人',width:100,
    								formatter: function(value,row,index){
										return row.handlername;
									}
								},
    							{field:'buydeptid',title:'采购部门',width:80,
									formatter: function(value,row,index){
										return row.buydeptname;
									}
								},
								{field:'field01',title:'金额',width:60,//align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							    },
							    {field:'field02',title:'未税金额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							    },
							    {field:'field03',title:'税额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							    },
    							{field:'status',title:'状态',width:60,
    								formatter: function(value,row,index){
										return getSysCodeName('status',value);
									}
								},
								{field:'isinvoice',title:'发票状态',width:70,
    								formatter: function(value,row,index){
										if(value==null || value=="" || value=="0"|| value=="4"){
											return "未开票";
										}else if(value=="1"){
											return "已开票";
										}else if(value=="2"){
											return "核销";
										}else if(value=="3"){
											return "开票中";
										}else if(value=="-999"){
        									return "";
    									}
									}
								}, 
								{field:'ischeck',title:'验收状态',width:60,
    								formatter: function(value,row,index){
										if(value=="1"){
											return "已验收";
										}else if(value=="0"){
											return "未验收";
										}
									}
								},
    							{field:'checkdate',title:'验收日期',width:70,sortable:true},   
    							{field:'checkusername',title:'验收人',width:60,hidden:true},    							    							
    							{field:'remark',title:'备注',width:180},
    							{field:'addusername',title:'制单人',width:60},
    							{field:'adddeptname',title:'制单人部门',width:100,hidden:true},
    							{field:'modifyusername',title:'修改人',width:120,hidden:true},
    							{field:'auditusername',title:'审核人',width:60},
    							{field:'audittime',title:'审核时间',width:100,hidden:true,sortable:true},
    							{field:'addtime',title:'制单时间',width:120,sortable:true,hidden:true}
    			]]
			});
			$("#purchase-table-returnCheckOrderListPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#purchase-table-query-returnCheckOrderListPage',
		 		url:"purchase/returnorder/showReturnCheckOrderPageList.do",
				queryParams:initQueryJSON,
				authority : returnCheckOrderListJson,
		 		frozenColumns: returnCheckOrderListJson.frozen,
				columns:returnCheckOrderListJson.common,
	  	 		onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
						countTotalAmount();
					}
	 			},
				onDblClickRow:function(index, data){
					<security:authorize url="/purchase/returnorder/returnOrderCheckViewBtn.do">
					top.addOrUpdateTab('purchase/returnorder/returnCheckOrderPage.do?type=edit&id='+ data.id, "采购退货验收查看");
					</security:authorize>
		    	},
				onCheckAll:function(){
					countTotalAmount();
				},
				onUncheckAll:function(){
					countTotalAmount();
				},
				onCheck:function(){
					countTotalAmount();
				},
				onUncheck:function(){
					countTotalAmount();
				}
			}).datagrid("columnMoving");

			//按钮
			$("#purchase-buttons-returnCheckOrderListPage").buttonWidget({
				initButton:[
                    {
                        type: 'button-commonquery',
                        attr:{
                            name:'purchase_returnorder',
                            plain:true,
                            datagrid:'purchase-table-returnCheckOrderListPage'
                        }
                    },
					{}
				],
				buttons:[
					<security:authorize url="/purchase/returnorder/returnCheckOrderMutiCheckBtn.do">
					{
						id: 'button-returnorder-check',
						name:'批量验收',
						iconCls:'button-audit',
						handler: function(){
							var rows = $("#purchase-table-returnCheckOrderListPage").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请选中需要验收的记录。");
								return false;
							}
							$.messager.confirm("提醒","确定验收这些退货通知单？",function(r){
								if(r){
									var failids = new Array();
									var idarrs=new Array();
									for(var i=0; i<rows.length; i++){
										if(rows[i].ischeck!='1'){
											idarrs.push(rows[i].id);
										}else{
											failids.push(rows[i].id);
										}
									}
									if(failids.length>0){
										$.messager.alert("提醒","请选中需要验收的记录。以下为已验收编号："+failids.join(","));
										return false;										
									}
									loading("验收操作中..");
									$.ajax({
						   				url:'purchase/returnorder/updateReturnOrderMutiCheck.do',
						   				dataType:'json',
						   				type:'post',
						   				data:{idarrs:idarrs.join(",")},
						   				success:function(json){
						   					loaded();
											if(json.flag == true){
												if(json.msg){
													$.messager.alert("提醒","验收操作成功，"+json.msg);
												}else{
													$.messager.alert("提醒","验收操作成功，"+json.msg);													
												}
												$("#purchase-table-returnCheckOrderListPage").datagrid('reload');
											}
											else{
												if(json.msg){
													$.messager.alert("提醒","验收操作失败，"+json.msg);
												}else{
													$.messager.alert("提醒","验收操作失败，"+json.msg);													
												}
												$.messager.alert("提醒","验收操作出错");
											}
						   				},
						   				error:function(){
						   					loaded();
						   					$.messager.alert("错误","验收操作出错");
						   				}
						  			});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/returnorder/returnOrderCheckViewBtn.do">
					{
						id: 'button-returnorder-view',
						name:'查看',
						iconCls:'button-view',
						handler: function(){
							var datarow = $("#purchase-table-returnCheckOrderListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要查看的采购退货验收单");
								return false;
							}
							top.addOrUpdateTab('purchase/returnorder/returnCheckOrderPage.do?type=edit&id='+datarow.id,'采购退货通知单查看');												
						}
					},
					</security:authorize>
					{}
				],
				model:'bill',
				type:'list',
				datagrid:'purchase-table-returnCheckOrderListPage',
				tname:'t_purchase_returnorder'
			});

			//仓库控件
			$("#purchase-returnCheckOrderListPage-storageid").widget({
     			width:136,
				referwid:'RL_T_BASE_STORAGE_INFO',
				singleSelect:true,
				onlyLeafCheck:false
     		});
			
			//回车事件
			controlQueryAndResetByKey("purchase-btn-queryReturnOrderListPage","purchase-btn-reloadReturnOrderListPage");

			$("#purchase-returnCheckOrderListPage-supplier").supplierWidget({ 
				name:'t_purchase_returnorder',
				col:'supplierid',
				width:225,
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
				}
			});	
			
			$("#purchase-btn-queryReturnOrderListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON = $("#purchase-form-returnCheckOrderListPage").serializeJSON();					
 				$('#purchase-table-returnCheckOrderListPage').datagrid('load',queryJSON);				
			});
			$("#purchase-btn-reloadReturnOrderListPage").click(function(){
                $("#purchase-returnCheckOrderListPage-storageid").widget('clear');
				$("#purchase-returnCheckOrderListPage-supplier").supplierWidget('clear');
				$("#purchase-form-returnCheckOrderListPage")[0].reset();
       			var queryJSON = $("#purchase-form-returnCheckOrderListPage").serializeJSON();					
 				$('#purchase-table-returnCheckOrderListPage').datagrid('load',queryJSON);
			});
			
    	});
    	function countTotalAmount(){
    		var rows =  $("#purchase-table-returnCheckOrderListPage").datagrid('getChecked');
    		/*if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
    			$("#purchase-table-returnCheckOrderListPage").datagrid("reloadFooter",foot);
           		return false;
       		}*/
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax=0;
    		for(var i=0;i<rows.length;i++){
    			taxamount = Number(taxamount)+Number(rows[i].field01 == undefined ? 0 : rows[i].field01);
    			notaxamount = Number(notaxamount)+Number(rows[i].field02 == undefined ? 0 : rows[i].field02);
    			tax = Number(tax)+Number(rows[i].field03 == undefined ? 0 : rows[i].field03);
    		}
    		var obj= {id:'选中金额',field01:taxamount,field02:notaxamount,field03:tax,source:" ",isinvoice:" ",source:-999};
    		var foot=[];
    		foot.push(obj);
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#purchase-table-returnCheckOrderListPage").datagrid("reloadFooter",foot);
    	}
    </script>
  </body>
</html>
