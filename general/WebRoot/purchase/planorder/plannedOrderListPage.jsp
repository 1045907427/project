<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购计划单</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="purchase-buttons-plannedOrderListPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="purchase-table-plannedOrderListPage"></table>
    		<div id="purchase-table-query-plannedOrderListPage" style="padding:2px;height:auto">
				<div>
					<form action="" id="purchase-form-plannedOrderListPage" method="post">
						<table class="querytable">
			    			<tr>
			    				<td>业务日期:</td>
			    				<td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td>编号:</td>
			    				<td><input type="text" name="id" style="width: 150px;"/></td>
                                <td>状态:</td>
                                <td>
                                    <select name="isClose" style="width:163px;"><option></option><option value="0" selected="selected">保存</option><option value="1">审核通过</option><option value="2">关闭</option></select>
                                </td>
			    			</tr>
			    			<tr>
                                <td>采购部门:</td>
                                <td>
                                    <input type="text" id="purchase-plannedOrderListPage-buydept" name="buydeptid" />
                                </td>
                                <td>供应商:</td>
                                <td><input id="purchase-plannedOrderListPage-supplier" type="text" name="supplierid" style="width: 150px;"/></td>
                                <td colspan="2" class="tdbutton">
                                    <a href="javaScript:void(0);" id="purchase-btn-queryPlannedOrderListPage" class="button-qr">查询</a>
                                    <a href="javaScript:void(0);" id="purchase-btn-reloadPlannedOrderListPage" class="button-qr">重置</a>
                                    <%--<span id="purchase-table-query-plannedOrderListPage-advanced"></span>--%>
                                </td>
			    			</tr>

			    		</table>
					</form>
				</div>
			</div>
    	</div>
    </div>
    
    <script type="text/javascript">
        var SD_footerobject  = null;
    	$(document).ready(function(){
    		var initQueryJSON = $("#purchase-form-plannedOrderListPage").serializeJSON();
    		var plannedOrderListJson = $("#purchase-table-plannedOrderListPage").createGridColumnLoad({
				name:'purchase_plannedorder',
				frozenCol : [[
    			]],
    			commonCol :[[
 								{field:'id',title:'编号',width:150,sortable:true},
    							{field:'businessdate',title:'业务日期',width:100,sortable:true},
    							{field:'supplierid',title:'供应商编码',width:70},
    							{field:'suppliername',title:'供应商名称',width:100,isShow:true},
    							{field:'handlerid',title:'对方联系人',width:80,
    								formatter: function(value,row,index){
										return row.handlername;
									}
								},
    							{field:'buydeptid',title:'采购部门',width:100,
									formatter: function(value,row,index){
										return row.buydeptname;
									}
								},
								{field:'field01',title:'金额',width:80,align:'right',
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
    							{field:'addusername',title:'制单人',width:70},
    							{field:'adddeptname',title:'制单人部门',width:80,hidden:true},
    							{field:'modifyusername',title:'修改人',width:80,hidden:true},
    							{field:'auditusername',title:'审核人',width:70},
    							{field:'audittime',title:'审核时间',width:100,hidden:true,sortable:true},
    							{field:'status',title:'状态',width:50,
    								formatter: function(value,row,index){
										return getSysCodeName('status',value);
									}
								},    							    							
    							{field:'remark',title:'备注',width:180},
    							{field:'addtime',title:'制单时间',width:120,sortable:true},
    							{field:'printtimes',title:'打印次数',width:80}
    			]]
			});
			$("#purchase-table-plannedOrderListPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
                checkOnSelect:true,
                showFooter:true,
				toolbar:'#purchase-table-query-plannedOrderListPage',
		 		url:"purchase/planorder/showPlannedOrderPageList.do",
				queryParams:initQueryJSON,
		 		sortName:'addtime',
		 		sortOrder:'desc',
                pageSize:100,
				authority : plannedOrderListJson,
		 		frozenColumns: plannedOrderListJson.frozen,
				columns:plannedOrderListJson.common,
				onDblClickRow:function(index, data){
					top.addOrUpdateTab('purchase/planorder/plannedOrderPage.do?type=edit&id='+ data.id, "采购计划单查看");
		    	},
                onCheckAll:function(){
                    countTotal();
                },
                onUncheckAll:function(){
                    countTotal();
                },
                onCheck:function(){
                    countTotal();
                },
                onUncheck:function(){
                    countTotal();
                },
                onLoadSuccess:function(){
                    var footerrows = $(this).datagrid('getFooterRows');
                    if(null!=footerrows && footerrows.length>0){
                        SD_footerobject = footerrows[0];
                    }
                }
			}).datagrid("columnMoving");

			//按钮
			$("#purchase-buttons-plannedOrderListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/purchase/planorder/plannedOrderAddBtn.do">
					{
						type:'button-add',
						handler: function(){
							top.addOrUpdateTab('purchase/planorder/plannedOrderPage.do','采购计划单新增');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/planorder/plannedOrderEditBtn.do">
					{
						type:'button-edit',
						handler: function(){
							var datarow = $("#purchase-table-plannedOrderListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要修改的采购计划单");
								return false;
							}
							top.addOrUpdateTab('purchase/planorder/plannedOrderPage.do?type=edit&id='+datarow.id,'采购计划单修改');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/planorder/plannedOrderViewBtn.do">
					{
						type:'button-view',
						handler: function(){
							var datarow = $("#purchase-table-plannedOrderListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要查看的采购计划单");
								return false;
							}
							top.addOrUpdateTab('purchase/planorder/plannedOrderPage.do?type=edit&id='+datarow.id,'采购计划单查看');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/planorder/plannedOrderImport.do">
					{
						type: 'button-import',
						attr: {
							type:'importUserdefined',
					 		importparam:'必填项：供应商编码、商品编码、箱数、个数<br/>商品编码、助记符、条形码三选一必填<br/>选填项：单价、金额、业务日期',
					 		url:'purchase/planorder/importPlannedOrder.do',
							onClose: function(){
						         $("#purchase-table-plannedOrderListPage").datagrid('reload');	//更新列表	                                                                                        
							}
						}
					},
					</security:authorize>
					{}
				],
				model:'bill',
				type:'list',
				datagrid:'purchase-table-plannedOrderListPage',
				tname:'t_purchase_plannedorder'
			});
			
            //采购部门
            $("#purchase-plannedOrderListPage-buydept").widget({
                referwid:'RL_T_BASE_DEPARTMENT_BUYER',
                singleSelect:true,
                width:225,
                onlyLeafCheck:false
            });
			$("#purchase-plannedOrderListPage-supplier").supplierWidget({
				name:'t_purchase_buyorder',
				col:'supplierid',
				width:150,
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
				}
			});	

// 			$("#purchase-table-query-plannedOrderListPage-advanced").advancedQuery({
//		 		name:'purchase_plannedorder',
//		 		plain:true,
//		 		datagrid:'purchase-table-plannedOrderListPage'
//			});
			
			//回车事件
			controlQueryAndResetByKey("purchase-btn-queryPlannedOrderListPage","purchase-btn-reloadPlannedOrderListPage");
			
			$("#purchase-btn-queryPlannedOrderListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON = $("#purchase-form-plannedOrderListPage").serializeJSON();					
 				$('#purchase-table-plannedOrderListPage').datagrid('load',queryJSON);				
			});
			$("#purchase-btn-reloadPlannedOrderListPage").click(function(){
                $("#purchase-plannedOrderListPage-buydept").widget('clear');
				$("#purchase-plannedOrderListPage-supplier").supplierWidget('clear');
				$("#purchase-form-plannedOrderListPage")[0].reset();
       			var queryJSON = $("#purchase-form-plannedOrderListPage").serializeJSON();
 				$('#purchase-table-plannedOrderListPage').datagrid('load',queryJSON);
			});
    	});

        function countTotal() { //计算合计
            var rows =  $("#purchase-table-plannedOrderListPage").datagrid('getChecked');
            if(null==rows || rows.length==0){
                var foot=[];
                if(null!=SD_footerobject){
                    foot.push(SD_footerobject);
                }
                $("#purchase-table-plannedOrderListPage").datagrid("reloadFooter",foot);
                return false;
            }

            var checkrows = $("#purchase-table-plannedOrderListPage").datagrid('getChecked');
            var field01 = 0;
            for (var i = 0; i < checkrows.length; i++) {
                field01 += Number(checkrows[i].field01 == undefined ? 0 : checkrows[i].field01);
            }
            var foot = [{
                id: '选中合计',
                field01: field01,
            }];
            if(null!=SD_footerobject){
                foot.push(SD_footerobject);
            }
            $("#purchase-table-plannedOrderListPage").datagrid('reloadFooter', foot);
        }
    </script>
  </body>
</html>
