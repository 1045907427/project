<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫统计明细报表</title>
  </head>
  
  <body>
  	<div id="journalsheet-tool-detail-matcostsInput">
  		<form action="" id="report-form-saleoutBalanceReportPage" method="post">
  			<input type="hidden" name="supplierid" value="${param.supplierid }"/>
  			<input type="hidden" name="businessdate1" value="${param.businessdate1 }"/>
  			<input type="hidden" name="businessdate2" value="${param.businessdate2 }"/>
  			<input type="hidden" name="writeoffstatus" value="${param.writeoffstatus }"/>
			<input type="hidden" name="deptid" value="${param.deptid}" />
  		</form>
  		<security:authorize url="/journalsheet/matcostsInput/exportMatcostsReportData.do">
  		<a href="javaScript:void(0);" id="journalsheet-table-detail-matcostsInput-export" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
  		</security:authorize>
  	</div>
    <table id="journalsheet-table-detail-matcostsInput"></table>
        
    <script type="text/javascript">
    	$(function(){
    		
			$("#journalsheet-table-detail-matcostsInput-export").Excel('export',{
				queryForm: "#report-form-saleoutBalanceReportPage",
				type:'exportUserdefined',
				name:'${suppliername}-部门【${deptname}】_代垫明细报表',
				url:'journalsheet/matcostsInput/exportMatcostsReportDetailData.do'
			});
    	});
    	function loadMatcostsInputReportDetail(){
    		var columnJson = $("#journalsheet-table-detail-matcostsInput").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'oaid',title:'OA编号',width:80,sortable:true,
			                        formatter: function(value,row,index){

			                            if(value != undefined 
			                            		&& value != null 
			                            		&& value != '' 
			                            		&& value != '合计' 
			                            		&& value != '选中金额') {

			                                return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
			                            }

			                            return value;
			                        }
			                  },
							  {field:'customerid',title:'客户编号',width:80,sortable:true},
							  {field:'customername',title:'客户名称',width:150,sortable:true},
							  {field:'subjectid',title:'科目名称',width:60,sortable:true,
								formatter:function(val,rowData,rowIndex){
									return rowData.subjectname;
								}
							  },
							  {field:'beginamount',title:'期初代垫金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							{field:'hcflag',title:'是否红冲',width:60,sortable:true,
								formatter:function(val,rowData,rowIndex){
									if(rowData.hcflag=='0'){
										return "否";
									}else if(rowData.hcflag=='1' || rowData.hcflag=='2'){
										return "是";
									}
								}
							},
							  <%--特别的
							  {field:'htcompdiscount',title:'电脑折让',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'htpayamount',title:'支付金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'branchaccount',title:'转入分公司',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  --%>
							  <c:forEach items="${reimbursetypeList }" var="list">
							  {field:'amount${list.code}',title:'${list.codename}',align:'right',resizable:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
				        			return formatterMoney(value);
					        	}
							  },
    						  </c:forEach>
							  {field:'reimburseamount',title:'收回金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'endamount',title:'期末代垫金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'remark',title:'备注',width:120}
				             ]]
			});
    		
			$("#journalsheet-table-detail-matcostsInput").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		method:'post',
		 		fit:true,
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		data: JSON.parse('${dataList}'),
		 		toolbar:'#journalsheet-tool-detail-matcostsInput',
		 		onLoadSuccess:function(data){
		 			var factoryamount = 0;
		 			var htcompdiscount = 0;
		 			var htpayamount = 0;
		 			var branchaccount = 0;
		 			<c:forEach items="${reimbursetypeList }" var="list">
		 			var amount${list.code} = 0;
		 			</c:forEach>
		 			var beginamount = 0;
		 			var endamount = 0;
		 			for(var i=0;i<data.rows.length;i++){
						var rowData = data.rows[i];
						if(i==0){
							beginamount = rowData.beginamount;
						}
						if(i==data.rows.length-1){
							endamount = rowData.endamount;
						}
						factoryamount = Number(factoryamount)+Number(rowData.factoryamount);
						htcompdiscount = Number(htcompdiscount)+Number(rowData.htcompdiscount);
						htpayamount = Number(htpayamount)+Number(rowData.htpayamount);
						branchaccount = Number(branchaccount)+Number(rowData.branchaccount);
						<c:forEach items="${reimbursetypeList }" var="list">
						if(rowData.amount${list.code}!=null){
		 					amount${list.code} = Number(amount${list.code})+Number(rowData.amount${list.code});
		 				}
		 				</c:forEach>
					}
					var object = [{businessdate:'合计',beginamount:beginamount,endamount:endamount,factoryamount:factoryamount,htcompdiscount:htcompdiscount,htpayamount:htpayamount,branchaccount:branchaccount
					<c:forEach items="${reimbursetypeList }" var="list">,amount${list.code}:amount${list.code}</c:forEach>}];
		    		$("#journalsheet-table-detail-matcostsInput").datagrid("reloadFooter",object );
					$("#journalsheet-table-detail-matcostsInput").datagrid("resize");
		 		},
	 			rowStyler:function(index,row){
		 			if(row.hcflag && row.hcflag=='1'){
		 				return "color:#f00";
		 			}else if(row.hcflag 
		 					&& row.hcflag=='2'){
		 				return "color:#00f";
		 			}
	 			}
			}).datagrid("columnMoving");
    	}
    </script>
  </body>
</html>
