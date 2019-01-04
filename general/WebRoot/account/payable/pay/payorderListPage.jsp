<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-payorderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="account-datagrid-payorderPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-payorderPage">
    	<form action="" id="account-form-query-payorderPage" method="post">
    		<table class="querytable">
    			<tr>
    				<td>编&nbsp;&nbsp;号：</td>
    				<td><input type="text" name="id" style="width: 225px;"/></td>
    				<td>供 应 商：</td>
    				<td><input id="account-query-supplierid" type="text" name="supplierid" style="width: 180px;"/></td>
                    <td>OA 编 号：</td>
                    <td>
                        <input type="text" name="oaid" style="width:130px;"/>
                    </td>
    			</tr>
    			<tr>
    				<td>业务日期：</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td>银行名称：</td>
    				<td><input id="account-query-bank" type="text" name="bank" style="width: 180px;"/></td>
    				<td>状&nbsp;&nbsp;态：</td>
    				<td><select name="isClose" style="width:130px;"><option></option><option value="0" selected="selected">未关闭</option><option value="1">已关闭</option></select></td>
    			</tr>
    			<tr>
    				<td>审核时间：</td>
    				<td><input type="text" name="auditdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="auditdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                    <td>采购部门：</td>
                    <td><input id="account-query-buydept" type="text" name="buydeptid" /></td>
    				<td colspan="2" style="padding-left: 3px">
    					<a href="javaScript:void(0);" id="account-queay-payorder" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-payorder" class="button-qr">重置</a>
						<span id="account-query-advanced-payorder"></span>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-panel-payorder-addpage"></div>
    <!-- <div id="account-panel-payorder-viewpage"></div> -->
    <div id="Payorder-account-dialog"></div>
     <script type="text/javascript">
     	var payorder_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
     	var initQueryJSON = $("#account-form-query-payorderPage").serializeJSON();
     	var footerobject = null;
    	$(function(){
    		//按钮
			$("#account-buttons-payorderPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/payable/payorderAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								$('#account-panel-payorder-addpage').dialog({  
								    title: '付款单新增',  
								    width: 680,  
								    height: 300,  
								    collapsible:false,
								    minimizable:false,
								    maximizable:true,
								    resizable:true,
								    closed: true,  
								    cache: false,  
								    href: 'account/payable/payorderAddPage.do',  
								    modal: true,
								    onLoad:function(){
								    	$("#account-payorder-supplierid").focus();
								    }
								});
								$('#account-panel-payorder-addpage').dialog("open");
								//top.addOrUpdateTab('account/payable/showPayorderAddPage.do', "付款单新增");
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderEditPage.do">
						{
							type: 'button-edit',
							handler: function(){
								var con = $("#account-datagrid-payorderPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								if(con.status=='2'){
									$('#account-panel-payorder-addpage').dialog({  
									    title: '付款单修改',  
									    width: 680,  
									    height: 300,  
									    collapsible:false,
									    minimizable:false,
									    maximizable:true,
									    resizable:true,
									    closed: true,  
									    cache: false,  
									    href: 'account/payable/payorderEditPage.do?id='+con.id,  
									    modal: true,
									    onLoad:function(){
									    	$("#account-payorder-supplierid").focus();
									    }
									});
									$('#account-panel-payorder-addpage').dialog("open");
								}else{
									$.messager.alert("提醒","该付款单不能修改");
								}
								//top.addOrUpdateTab('account/payable/showPayorderEditPage.do?id='+ con.id, "付款单修改");
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/deletePayorder.do">
						{
							type: 'button-delete',
							handler: function(){
								$.messager.confirm("提醒","是否删除当前付款单？",function(r){
									if(r){
										var rows = $("#account-datagrid-payorderPage").datagrid("getChecked");
										var ids = "";
										for(var i=0;i<rows.length;i++){
											if(ids==""){
												ids = rows[i].id;
											}else{
												ids += ","+ rows[i].id;
											}
										}
										loaded("删除中..");
										$.ajax({   
								            url :'account/payable/deleteMutPayorder.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒", ""+json.succssids+"</br>"+json.errorids);
								            		$("#account-datagrid-payorderPage").datagrid("reload");
								            	}else{
								            		$.messager.alert("提醒", "删除失败");
								            	}
								            }
								        });
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderViewPage.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#account-datagrid-payorderPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								$('#account-panel-payorder-addpage').dialog({  
								    title: '付款单查看',  
								    width: 680,  
								    height: 300,  
								    collapsible:false,
								    minimizable:false,
								    maximizable:true,
								    resizable:true,
								    closed: true,  
								    cache: false,  
								    href: 'account/payable/payorderViewPage.do?id='+con.id,  
								    modal: true
								});
								$('#account-panel-payorder-addpage').dialog("open");
								//top.addOrUpdateTab('account/payable/showPayorderViewPage.do?id='+ con.id, "付款单查看");
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/auditPayorder.do">
						{
							type: 'button-audit',
							handler: function(){
								var rows = $("#account-datagrid-payorderPage").datagrid("getChecked");
								if(rows.length == 0){
									$.messager.alert("提醒","请勾选记录！");
									return false;
								}
								var ids = "";
								for(var i=0;i<rows.length;i++){
									if(ids == ""){
										ids = rows[i].id;
									}else{
										ids += "," + rows[i].id;
									}
								}
								$.messager.confirm("提醒","是否审核付款单？",function(r){
									if(r){
										loading("审核中..");
										$.ajax({   
								            url :'account/payable/auditMutPayorder.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
													$.messager.alert("提醒","付款单审核信息:<br/>"+json.succssids+";<br/>"+json.errorids);
													$("#account-datagrid-payorderPage").datagrid("reload");
								            	}else{
								            		$.messager.alert("提醒","审核失败");
								            	}
								            },
								            error:function(){
								            	$.messager.alert("错误","审核失败");
								            }
								        });
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/oppauditPayorder.do">
						{
				 			type:'button-oppaudit',
				 			handler:function(){
				 				var rows = $("#account-datagrid-payorderPage").datagrid("getChecked");
								if(rows.length != 1){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
                                var flag = isDoneOppauditBillCaseAccounting(rows[0].businessdate);
                                if(!flag){
                                    $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                    return false;
                                }
								var ret = payorder_AjaxConn({supplierid:rows[0].supplierid,amount:rows[0].amount},'account/payable/backOppauditFlag.do');
								var retJson = $.parseJSON(ret);
								if(!retJson.flag){
									$.messager.alert("提醒","供应商余额不够,不允许反审!");
									return false;
								}
				 				$.messager.confirm("提醒","是否反审付款单？",function(r){
									if(r){
										loading("反审中..");
										$.ajax({
								            url :'account/payable/oppauditPayorder.do?id='+rows[0].id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
													$.messager.alert("提醒","反审成功");
													$("#account-datagrid-payorderPage").datagrid("reload");
								            	}else{
								            		$.messager.alert("提醒","反审失败");
								            	}
								            },
								            error:function(){
								            	$.messager.alert("错误","反审失败");
								            }
								        });
									}
								});
				 			}
			 			},
					</security:authorize>
					<security:authorize url="/account/payable/payorderImportPage.do">
						{
							type: 'button-import',
							attr: {
							
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderExportPage.do">
						{
							type: 'button-export',
							attr: {
                                datagrid:"#account-datagrid-payorderPage",
                                queryForm: "#account-form-query-payorderPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                                type:'exportUserdefined',
                                url:'account/payable/exportPayorder.do',
                                name:'付款单'
							}
						},
					</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'t_account_purchase_payorder',
                            //查询针对的表格id
                            datagrid:'account-datagrid-payorderPage',
                            plain:true
                        }
                    },
					{}
				],
                buttons:[
                    <security:authorize url="/erpconnect/addPayorderAccountVouch.do">
                    {
                        id:'Payorder-account',
                        name:'生成凭证',
                        iconCls:'button-audit',
                        handler: function() {
                            var rows = $("#account-datagrid-payorderPage").datagrid('getChecked');
                            if (rows == null || rows.length == 0) {
                                $.messager.alert("提醒", "请选择至少一条记录");
                                return false;
                            }
							var ids = "";
							var flag = false;
							for(var i=0;i<rows.length;i++){
								if(rows[i].status != 4){
									flag = true;
									break;
								}
								if(i==0){
									ids = rows[i].id;
								}else{
									ids += "," + rows[i].id;
								}
							}
							if(flag){
								$.messager.alert("提醒", "请选择关闭状态数据");
								return false;
							}
							$('<div id="Payorder-account-dialog-content"></div>').appendTo("#Payorder-account-dialog");
                            $("#Payorder-account-dialog-content").dialog({
                                title:'货款支付凭证',
                                width:400,
                                height:260,
                                closed:false,
                                modal:true,
                                cache:false,
                                href:'erpconnect/showPayorderAccountVouchPage.do',
                                onLoad:function(){
                                    $("#payorder-ids").val(ids);
                                },
								onClose: function () {
									$("#Payorder-account-dialog-content").dialog("destroy");
								},
                            });
                        }
                    }
                    </security:authorize>
                ],
				model: 'bill',
				type: 'list',
				tname: 't_account_collection_order'
			});
			var tableColumnListJson = $("#account-datagrid-payorderPage").createGridColumnLoad({
				name :'t_account_purchase_payorder',
				frozenCol : [[]],
				commonCol : [[
					  {field:'ck',checkbox:true},
					  {field:'id',title:'编号',width:130,sortable:true},
					  {field:'businessdate',title:'业务日期',width:80,sortable:true},
					  {field:'oaid',title:'OA编号',width:80,sortable:true},
					  {field:'supplierid',title:'供应商编码',width:70,sortable:true},
				  	  {field:'suppliername',title:'供应商名称',width:150,isShow:true},
					  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.handlername;
			        	}
					  },
					  {field:'buydeptid',title:'采购部门',width:100,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.buydeptname;
			        	}
					  },
					  {field:'buyuserid',title:'采购员',width:100,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.buyusername;
			        	}
					  },
					  {field:'amount',title:'付款金额',resizable:true,align:'right',sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  //{field:'writeoffamount',title:'已核销金额',align:'right',resizable:true,sortable:true,
					  //	formatter:function(value,rowData,rowIndex){
			          //	return formatterMoney(value);
			          //}
					  // },
					  //{field:'remainderamount',title:'剩余金额',resizable:true,align:'right',sortable:true,
					  //	formatter:function(value,rowData,rowIndex){
			          //	return formatterMoney(value);
			          //}
					  //},
					  {field:'prepay',title:'是否预付',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		if(value=='1'){
			        			return "是";
			        		}else if(value=='0'){
			        			return "否";
			        		}
			        	}
					  },
					  {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
					  {field:'addusername',title:'制单人',width:80,sortable:true},
					  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
					  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
					  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
					  {field:'stopusername',title:'中止人',width:80,sortable:true,hidden:true},
					  {field:'stoptime',title:'中止时间',width:80,sortable:true,hidden:true},
					  {field:'status',title:'状态',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return getSysCodeName("status",value);
			        	}
					  },
<!--					  {field:'iswriteoff',title:'是否核销',width:60,sortable:true,-->
<!--					  	formatter:function(value,rowData,rowIndex){-->
<!--			        		return getSysCodeName("iswriteoff",value);-->
<!--			        	}-->
<!--					  },-->
					  {field:'remark',title:'备注',width:80,sortable:true}
	             ]]
			});
			$("#account-datagrid-payorderPage").datagrid({ 
		 		authority:tableColumnListJson,
		 		frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		showFooter: true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
                pageSize:100,
				url: 'account/payable/showPayorderList.do',
				queryParams:initQueryJSON,
				toolbar:'#account-datagrid-toolbar-payorderPage',
				onDblClickRow:function(rowIndex, rowData){
					if(rowData.status=='2'){
						$('#account-panel-payorder-addpage').dialog({  
						    title: '付款单修改',  
						    width: 680,  
						    height: 300,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/payable/payorderEditPage.do?id='+rowData.id,  
						    modal: true,
						    onLoad:function(){
						    	$("#account-payorder-supplierid").focus();
						    }
						});
						$('#account-panel-payorder-addpage').dialog("open");
					}else{
						$('#account-panel-payorder-addpage').dialog({
						    title: '付款单查看',  
						    width: 680,  
						    height: 300,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/payable/payorderViewPage.do?id='+rowData.id,  
						    modal: true
						});
						$('#account-panel-payorder-addpage').dialog("open");
					}
					//top.addOrUpdateTab('account/payable/showPayorderViewPage.do?id='+ rowData.id, "付款单查看");
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
				},
				onLoadSuccess: function(){
					var footerrows = $("#account-datagrid-payorderPage").datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
						countTotalAmount();
					}
				}
			}).datagrid("columnMoving");
			
			$("#account-query-supplierid").widget({
				name:'t_account_purchase_payorder',
				col:'supplierid',
    			singleSelect:true,
    			width:180,
    			onlyLeafCheck:false,
    			view:true
			});
			$("#account-query-buydept").widget({
				name:'t_account_purchase_payorder',
    			col:'buydeptid',
    			singleSelect:true,
    			width:180,
    			view:true,
    			onlyLeafCheck:true
			});
			
			$("#account-query-bank").widget({
				name:'t_account_purchase_payorder',
				col:'bank',
    			singleSelect:false,
    			width:180,
    			view:true
			});
			
			//通用查询组建调用
//			$("#account-query-advanced-payorder").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_purchase_payorder',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-payorderPage',
//		 		plain:true
//			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-payorder","account-reload-payorder");
			
			//查询
			$("#account-queay-payorder").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-payorderPage").serializeJSON();
	       		$("#account-datagrid-payorderPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-payorder").click(function(){
				$("#account-query-supplierid").widget("clear");
				$("#account-query-buydept").widget("clear");
                $("#account-query-bank").widget("clear");
				$("#account-form-query-payorderPage")[0].reset();
				var json = [];
				json['isClose'] = "0";
	       		$("#account-datagrid-payorderPage").datagrid("load",json);
			});
			
			function countTotalAmount(){
	    		var rows =  $("#account-datagrid-payorderPage").datagrid('getChecked');
	    		var amount = 0;
	    		var writeoffamount = 0;
	    		var remainderamount = 0;
	    		for(var i=0;i<rows.length;i++){
	    			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
	    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
	    			remainderamount = Number(remainderamount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
	    		}
	    		var footerrows = [{id:'选中金额',amount:amount,writeoffamount:writeoffamount,remainderamount:remainderamount}];
	    		if(null!=footerobject){
    				footerrows.push(footerobject);
    			}
    			$("#account-datagrid-payorderPage").datagrid("reloadFooter",footerrows);
	    	}
    	});
    </script>
  </body>
</html>
