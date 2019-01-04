<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>大单发货单页面</title>
    <%@include file="/include.jsp" %>
      <%@include file="/printInclude.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-bigSaleOutPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="storage-datagrid-bigSaleOutListPage"></table>
            <div id="storage-datagrid-toolbar-bigSaleOutListPage" style="padding:2px;height:auto">
                <form action="" id="storage-form-query-bigSaleOutListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                            <td>发货单编号:</td>
                            <td><input name="saleoutid" type="text" style="width: 150px;" /> </td>
                            <td>状态:</td>
                            <td>
                                <select name="status" style="width:135px">
									<option></option>
									<option value="2" selected="selected">保存</option>
									<option value="3">审核通过</option>
									<option value="4">已关闭</option>
									<option value="5">中止</option>
								</select>
                            </td>
                        </tr>
                        <tr>
                            <td>商品:</td>
                            <td><input id="storage-goodsid-bigSaleOutListPage" name="goodsid" type="text" style="width: 225px;"/></td>
                            <td>大单编号:</td>
                            <td><input name="id" type="text" style="width: 150px;" /> </td>
                            <td>出库仓库:</td>
                            <td><input name="storageid" id="storage-storageid-bigSaleOutPage" type="text"/></td>
                        </tr>
                        <tr>
                            <td>客户:</td>
                            <td><input id="storage-customerid-bigSaleOutListPage" name="customerid" type="text" style="width: 225px;"/></td>
                            <td>订单编号:</td>
                            <td><input name="orderid" type="text" style="width: 150px;" /> </td>
                            <td rowspan="3" colspan="2" class="tdbutton">
                                <a href="javaScript:void(0);" id="storage-query-bigSaleOutListPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="storage-reload-bigSaleOutListPage" class="button-qr">重置</a>
                                <span id="storage-query-advanced-bigSaleOut" hidden="true"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
    	</div>
    </div>
    <div style="display: none;">
        <div id="storage-bigSaleOutList-zjfj-dialog-print">
            <form action="" method="post" id="storage-bigSaleOutList-zjfj-dialog-print-form">
                <table>
                    <tr id="storage-bigSaleOutList-zjfj-dialog-printtemplet-tr">
                        <td>整件模板：</td>
                        <td>
                            <select id="storage-bigSaleOutList-zjfj-dialog-printtemplet-id" name="templetid">
                            </select>
                        </td>
                    </tr>
                    <tr id="storage-bigSaleOutList-zjfj-dialog-printtemplet-tr-fj">
                        <td>分拣模板：</td>
                        <td>
                            <select id="storage-bigSaleOutList-zjfj-dialog-printtemplet-fjid" name="fjtempletid">
                            </select>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <script type="text/javascript">
    	var initQueryJSON = $("#storage-form-query-bigSaleOutListPage").serializeJSON();
    	$(function(){
    		//按钮
			$("#storage-buttons-bigSaleOutPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/bigSaleOut/bigSaleOutAdd.do">
						{
						type: 'button-add',
						handler: function(){
							top.addOrUpdateTab('storage/showBigSaleOutAddPage.do', "大单发货单新增");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutView.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#storage-datagrid-bigSaleOutListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
                                var type='view';
                                if(con.status=='2')
                                    type='edit';
                                top.addOrUpdateTab('storage/showBigSaleOutPage.do?id='+ con.id+'&type='+type, "大单发货单查看");
							}
						},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutDelete.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
					 			var rows = $("#storage-datagrid-bigSaleOutListPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请至少勾选一条记录");
									return false;
								}
				 				$.messager.confirm("提醒","是否确定删除选中的大单发货单?",function(r){
				 					if(r){
				 						var	ids="";
										for(var i=0;i<rows.length;i++){
					                		if(ids == ""){
					                			ids = rows[i].id ;
					                		}else{
					                			ids += "," + rows[i].id ;
					                		}
								        }
										loading("删除中..");
										$.ajax({
								            url :'storage/deleteBigSaleOuts.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.msg){
								            	    var title = tabsWindowTitle('/storage/showBigSaleOutListPage.do');
								            	    if (top.$('#tt').tabs('exists',title)){
									    				tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
									    			}
								            		$.messager.alert("提醒",json.msg);
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","删除出错");
								            }
								        });
				 					}
				 				});
				 			}
				 		},
				 	</security:authorize>				
					<security:authorize url="/storage/bigSaleOut/bigSaleOutAudit.do">
						{
							type: 'button-audit',
							handler: function(){
								var rows = $("#storage-datagrid-bigSaleOutListPage").datagrid('getChecked');
								if(rows.length != 1){
									$.messager.alert("提醒","只允许勾选一条记录");
									return false;
								}
								$.messager.confirm("提醒","是否审核选中的大单发货单？",function(r){
									if(r){
										var	ids="";
										for(var i=0;i<rows.length;i++){
					                		if(ids == ""){
					                			ids = rows[i].id ;
					                		}else{
					                			ids += "," + rows[i].id ;
					                		}
								        }
										loading("审核中..");
										$.ajax({
								            url :'storage/auditBigSaleOuts.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
								            	$.messager.alert("提醒",json.msg);
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","审核出错;");
								            }
								        });
										
									}
								});
							}
						},
					</security:authorize>
					{}
				],
				buttons:[
					{},
					<security:authorize url="/storage/bigSaleOut/bigSaleOutPrintView.do">
					{
						id:'printMenuButton',
						type:'menu',
						name:'打印预览',
						iconCls:'button-preview',
						button:[
							<security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintView.do">
							{
								id:'printview-totalgoods',
								name:'整件分拣预览',
								iconCls:'button-preview',
								handler:function(){
								}
							},
							</security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrint.do">
                            {
                                id:'print-totalgoods',
                                name:'整件分拣打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintViewForZJ.do">
                            {
                                id:'printview-totalgoods-zj',
                                name:'整件预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintForZJ.do">
                            {
                                id:'print-totalgoods-zj',
                                name:'整件打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintViewForFJ.do">
                            {
                                id:'printview-totalgoods-fj',
                                name:'分拣预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintForFJ.do">
                            {
                                id:'print-totalgoods-fj',
                                name:'分拣打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
							<security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerPrintView.do">
							{
								id:'printview-goodscustomer',
								name:'按商品分客户数预览',
								iconCls:'button-preview',
								handler:function(){
								}
							},
							</security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerPrint.do">
                            {
                                id:'print-goodscustomer',
                                name:'按商品分客户数打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
							<security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerDivPrintView.do">
							{
								id:'printview-goodscustomerdiv',
								name:'按商品分客户区块预览',
								iconCls:'button-preview',
								handler:function(){
								}
							},
							</security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerDivPrint.do">
                            {
                                id:'print-goodscustomerdiv',
                                name:'按商品分客户区块',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutBrandPrintView.do">
                            {
                                id:'printview-brand',
                                name:'按品牌分商品预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutBrandPrint.do">
                            {
                                id:'print-brand',
                                name:'按品牌分商品打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
						]
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_storage_bigsaleout'
			});
			
			var deliveryJson = $("#storage-datagrid-bigSaleOutListPage").createGridColumnLoad({
				name :'t_storage_bigsaleout',
				frozenCol : [[
							  {field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'storageid',title:'出库仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.storagename;
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,hidden:true,sortable:true},
							  {field:'audittime',title:'审核时间',width:80,hidden:true,sortable:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'printtimes',title:'打印次数',width:60},
							  {field:'remark',title:'备注',width:100,sortable:true}
				          ]]
			});
			
			$("#storage-datagrid-bigSaleOutListPage").datagrid({ 
		 		authority:deliveryJson,
		 		frozenColumns: deliveryJson.frozen,
				columns:deliveryJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'t.id',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: false,
				url: 'storage/getBigSaleOutList.do',
				queryParams:initQueryJSON,
				toolbar:'#storage-datagrid-toolbar-bigSaleOutListPage',
				onDblClickRow:function(rowIndex, rowData){
					var type='view';
					if(rowData.status=='2')
						type='edit';
					top.addOrUpdateTab('storage/showBigSaleOutPage.do?id='+ rowData.id+'&type='+type, "大单发货单查看");
				}
			}).datagrid("columnMoving");

            //出库仓库
            $("#storage-storageid-bigSaleOutPage").widget({
                width:135,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true
            });
			//客户
			$("#storage-customerid-bigSaleOutListPage").customerWidget({});
			//商品
			$("#storage-goodsid-bigSaleOutListPage").goodsWidget({
				singleSelect:true,
				isHiddenUsenum:true
			});
			
			//通用查询组建调用
			$("#storage-query-advanced-delivery").advancedQuery({
		 		name:'t_storage_bigsaleout',
		 		datagrid:'storage-datagrid-bigSaleOutListPage',
		 		plain:true
			});
			
			//查询
			$("#storage-query-bigSaleOutListPage").click(function(){
	       		var queryJSON = $("#storage-form-query-bigSaleOutListPage").serializeJSON();
	       		$("#storage-datagrid-bigSaleOutListPage").datagrid("load",queryJSON);
			});
			//重置
			$("#storage-reload-bigSaleOutListPage").click(function(){
                $("#storage-storageid-bigSaleOutPage").widget('clear');
				$("#storage-customerid-bigSaleOutListPage").customerWidget('clear');
				$("#storage-goodsid-bigSaleOutListPage").goodsWidget('clear');
				$("#storage-form-query-bigSaleOutListPage")[0].reset();
				var queryJSON = $("#storage-form-query-bigSaleOutListPage").serializeJSON();
	       		$("#storage-datagrid-bigSaleOutListPage").datagrid("load",queryJSON);
			});
    	});
    </script>
    <%--打印开始 --%>
    <script type="text/javascript">
        $(function () {
            if (AgReportPrint.isShowPrintTempletManualSelect("storage_bigsaleout")) {
                //整件单模板
                var options={
                    renderTo:'storage-bigSaleOutList-zjfj-dialog-printtemplet-id',
                    code:'storage_bigsaleout',
                    codereqparam:{
                        mark:'整件单模板'
                    }
                };
                AgReportPrint.createPrintTempletSelectOptionByMap(options);

                //分拣单模板
                var options={
                    renderTo:'storage-bigSaleOutList-zjfj-dialog-printtemplet-fjid',
                    code:'storage_bigsaleout',
                    codereqparam:{
                        mark:'分拣单模板'
                    }
                };
                AgReportPrint.createPrintTempletSelectOptionByMap(options);
            }
            //打印整件&分拣
            AgReportPrint.init({
                id: "storage-bigSaleOutList-zjfj-dialog-print",
                code: "storage_bigsaleout",
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=zjfj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=zjfj",
                btnPreview: "printview-totalgoods",
                btnPrint: "print-totalgoods",
                getData: getData
            });
            //打印整件
            AgReportPrint.init({
                id: "storage-bigSaleOutList-zj-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'整件单模板'
                },
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=zj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=zj",
                btnPreview: "printview-totalgoods-zj",
                btnPrint: "print-totalgoods-zj",
                getData: getData
            });
            //打印分拣
            AgReportPrint.init({
                id: "storage-bigSaleOutList-fj-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'分拣单模板'
                },
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=fj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=fj",
                btnPreview: "printview-totalgoods-fj",
                btnPrint: "print-totalgoods-fj",
                getData: getData
            });
            //打印按商品分客户
            AgReportPrint.init({
                id: "storage-bigSaleOutList-customer-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按商品分客户模板'
                },
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=customer",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=customer",
                btnPreview: "printview-goodscustomer",
                btnPrint: "print-goodscustomer",
                getData: getData
            });
            //打印按商品分客户区块
            AgReportPrint.init({
                id: "storage-bigSaleOutList-customerblock-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按商品分客户区块模板'
                },
                exPrintParam:{
                    jobtype:'2'
                },
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=customer",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=customer",
                btnPreview: "printview-goodscustomerdiv",
                btnPrint: "print-goodscustomerdiv",
                getData: getData
            });
            //打印按品牌分商品
            AgReportPrint.init({
                id: "storage-bigSaleOutList-brand-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按品牌分商品模板'
                },
                tableId: "storage-datagrid-bigSaleOutListPage",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=brand",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=brand",
                btnPreview: "printview-brand",
                btnPrint: "print-brand",
                getData: getData
            });
            function getData(tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择一条记录");
                    return false;
                }
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '3' && data[i].status != '4') {
                        $.messager.alert("提醒", data[i].id + "此单据不可打印预览");
                        return false;
                    }
                }
                return data;
            }
        });
    </script>
    <%--打印结束 --%>
  </body>
</html>
