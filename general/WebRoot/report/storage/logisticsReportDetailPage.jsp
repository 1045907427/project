<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>物流奖金明细报表</title>
	</head>

	<body>
		<table id="report-table-detail-logistics" data-options="border:false"></table>
		<div id="report-tool-detail-logistics" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/logistics/logisticsExport.do">
                    <a href="javaScript:void(0);"
                       id="report-table-detail-logistics-export" class="easyui-linkbutton"
                       iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
			<form action="" id="report-form-logisticsReportPage" method="post">
				<input type="hidden" name="driverid" value="${driverid }" />
				<input type="hidden" name="businessdate1" value="${businessdate1 }" />
				<input type="hidden" name="businessdate2" value="${businessdate2 }" />
			</form>
		</div>

		<!-- 
    <div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center'">
			</div>
		</div>
		 -->

		<script type="text/javascript">
    	$(function(){
    		var initQueryJSON = $("#report-form-logisticsReportPage").serializeJSON();
    		var deliveryJson = $("#report-table-detail-logistics").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
					{field:'deliveryid',title:'配送单编号',resizable:true,sortable:false},
					{field:'driverid',title:'人员名称',resizable:true,sortable:false,
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
					{field:'businessdate',title:'验收日期',resizable:true,sortable:true},
					{field:'lineid',title:'线路名称',resizable:true,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return rowData.linename;
						}
					},
					{field:'carid',title:'车辆名称',resizable:true,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return rowData.carname;
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
					{field:'addtime',title:'制单时间',width:65,sortable:false,hidden:true},
					{field:'auditusername',title:'审核人',width:60,hidden:true,sortable:false},
					{field:'audittime',title:'审核时间',width:65,hidden:true,sortable:false},
					{field:'stopusername',title:'中止人',width:60,hidden:true,sortable:false},
					{field:'stoptime',title:'中止时间',width:65,hidden:true,sortable:false},
					{field:'status',title:'状态',width:60,sortable:false,hidden:true,
						formatter:function(value,rowData,rowIndex){
							return getSysCodeName("status",value);
						}
					},
					{field:'printtimes',title:'打印次数',width:60,hidden:true},
					{field:'remark',title:'备注',width:100,sortable:false,hidden:true}
				]]
			});
			$("#report-table-detail-logistics").datagrid({ 
		 		authority:deliveryJson,
		 		frozenColumns: deliveryJson.frozen,
				columns:deliveryJson.common,
		 		fit:true,
		 		//title:"",
		 		method:'post',
		 		rownumbers:true,
		 		//pagination:true,
		 		idField:'id',
		 		//sortName:'id',
		 		//sortOrder:'desc',
		 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		//data: JSON.parse('${dataList}'),
				url: 'report/logistics/getLogisticsReportDetailList.do',
				queryParams:initQueryJSON,
				toolbar:'#report-tool-detail-logistics',
				onLoadSuccess:function(data){
				},
				onDblClickRow:function(rowIndex, rowData){
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
			
			$("#report-table-detail-logistics-export").Excel('export',{
				queryForm: "#report-form-logisticsReportPage",
				type:'exportUserdefined',
				name:'${drivername}_物流奖金明细报表',
				url:'report/logistics/exportLogisticsDetailData.do'
			});
    	});
    </script>
	</body>
</html>
