<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>物流考核报表</title>
		<%@include file="/include.jsp"%>
		<script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
	</head>

	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<!-- 
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-logisticsPage" style="height:26px;"></div>
    	</div>
    	 -->
			<div data-options="region:'center'">
				<table id="report-datagrid-logisticsPage"
					data-options="border:false"></table>
			</div>
		</div>
		<div id="report-datagrid-toolbar-logisticsPage" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/logistics/logisticsExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-export" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
			<form action="" id="report-form-query-logisticsPage" method="post">
				<table class="querytable">

					<tr>
						<td>
							验收日期:
						</td>
						<td>
							<input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }"
								style="width: 100px;" class="Wdate"
								onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
							到
							<input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }"
								class="Wdate" style="width: 100px;"
								onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
						</td>
						<td>
							人员：
						</td>
						<td>
							<input type="text" id="report-userid-logisticsSourceQueryPage"
								name="driverid" />
							<input type="hidden" name="type" value="0">
						</td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="report-queay-logistics"
                               class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-logistics"
                               class="button-qr">重置</a>
							
						</td>
					</tr>
				</table>
				</td>
				</tr>
				</table>
			</form>
		</div>
		<div style="display:none">
	    	<div id="report-div-logistics-detail"></div>
	    </div>
		<script type="text/javascript">
     	var initQueryJSON = $("#report-form-query-logisticsPage").serializeJSON();
     	
    	$(function(){
    		$("#report-userid-logisticsSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    	
			var deliveryJson = $("#report-datagrid-logisticsPage").createGridColumnLoad({
				frozenCol : [[
							  //{field:'idok',checkbox:true,isShow:true}
							  //{field:'id',title:'编号',width:125,sortable:false}
							  //{field:'deliveryid',title:'配送单编号',width:125,sortable:false,isShow:true}
			    			]],
				commonCol : [[
					{field:'driverid',title:'人员名称',width:80,sortable:false,

						formatter:function(value,rowData,rowIndex){
							if(value=='合计')
								return value;
							return rowData.drivername;
						}
					},
					{field:'isdriver',title:'职位',resizable:true,sortable:false,
						formatter:function(value,rowData,rowIndex){
							if(value=='0')
								return '跟车';
							else if(value=='1')
								return '司机';
							return '';
						}
					},
					{field:'customernums',title:'家数',resizable:true,sortable:false},
					{field:'salesamount',title:'销售额',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'collectionamount',title:'收款金额',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'boxnum',title:'箱数',resizable:true,sortable:false,
						formatter:function(value,rowData,rowIndex){
							return formatterBigNumNoLen(value);
						}
					},
					{field:'trucksubsidy',title:'装车补助',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'carallowance',title:'出车津贴',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'carsubsidy',title:'出车补助',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'customersubsidy',title:'家数补助',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'salessubsidy',title:'销售补助',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'collectionsubsidy',title:'收款补助',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'othersubsidy',title:'其他',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'subamount',title:'合计金额',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'safebonus',title:'安全奖金',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'receiptbonus',title:'回单奖金',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'nightbonus',title:'晚上出车',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'totalamount',title:'总计金额',resizable:true,sortable:false,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}},
					{field:'addusername',title:'制单人',width:60,sortable:false,hidden:true},
					{field:'addtime',title:'制单时间',width:70,sortable:false,hidden:true},
					{field:'auditusername',title:'审核人',width:60,hidden:true,sortable:false},
					{field:'audittime',title:'审核时间',width:70,hidden:true,sortable:false},
					{field:'stopusername',title:'中止人',width:60,hidden:true,sortable:false},
					{field:'stoptime',title:'中止时间',width:70,hidden:true,sortable:false},
					{field:'status',title:'状态',width:60,sortable:false,hidden:true,
						formatter:function(value,rowData,rowIndex){
							return getSysCodeName("status",value);
						}
					},
					{field:'printtimes',title:'打印次数',width:60,hidden:true},
					{field:'remark',title:'备注',width:100,sortable:false,hidden:true}
				]]
			});
			$("#report-datagrid-logisticsPage").datagrid({ 
		 		authority:deliveryJson,
		 		frozenColumns: deliveryJson.frozen,
				columns:deliveryJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		//sortName:'id',
		 		//sortOrder:'desc',
		 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				//url: '',
				queryParams:initQueryJSON,
				toolbar:'#report-datagrid-toolbar-logisticsPage',
				onDblClickRow:function(rowIndex, rowData){
					var businessdate1 = $("#report-query-businessdate1").val();
   					var businessdate2 = $("#report-query-businessdate2").val();
   					var driverid = rowData.driverid;
   					var url = 'report/logistics/showLogisticsReportDetailPage.do?driverid='+driverid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&drivername='+rowData.drivername;
					$("#report-div-logistics-detail").dialog({
					    title: rowData.drivername+'的物流奖金明细列表', 
			    		fit:true,  
					    closed: true,  
					    cache: false,  
					    href: url,
						maximizable:true,
						resizable:true,  
					    modal: true
					});
					$("#report-div-logistics-detail").dialog("open");
				},
				onCheckAll:function(){
				},
				onUncheckAll:function(){
				},
				onCheck:function(){
				},
				onUncheck:function(){
				},
				onLoadSuccess: function(){
				}
			}).datagrid("columnMoving");
			//通用查询组建调用
			$("#report-query-advanced-logistics").advancedQuery({
				//查询针对的表
		 		name:'t_storage_logistics_delivery',
		 		//查询针对的表格id
		 		datagrid:'report-datagrid-logisticsPage',
		 		plain:true
			});
			
			//回车事件
			controlQueryAndResetByKey("report-queay-logistics","report-reload-logistics");
			
			//查询
			$("#report-queay-logistics").click(function(){
				
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#report-form-query-logisticsPage").serializeJSON();
	       		
		       	$("#report-datagrid-logisticsPage").datagrid({
		       		url: 'report/logistics/getLogisticsReportList.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
	       		
	       		//$("#report-datagrid-logisticsPage").datagrid("load",queryJSON);
			});
			//重置
			$("#report-reload-logistics").click(function(){
				$("#report-userid-logisticsSourceQueryPage").widget("clear");
				$("#report-form-query-logisticsPage")[0].reset();
				//var queryJSON = $("#report-form-query-logisticsPage").serializeJSON();
	       		$("#report-datagrid-logisticsPage").datagrid("loadData",{total:0,rows:[]});
			});
			
			$("#report-buttons-export").Excel('export',{
					queryForm: "#report-form-query-logisticsPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'物流奖金情况表',
			 		url:'report/logistics/exportLogisticsData.do'
			});
    	});
    	
    </script>
	</body>
</html>
