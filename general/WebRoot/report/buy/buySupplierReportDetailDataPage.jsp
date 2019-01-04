<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商商品采购情况统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-buysupplierDetail"></table>
    	<div id="report-toolbar-buysupplierDetail">
	    	<form action="" id="report-query-form-buysupplierDetail" method="post">
	    		<input type="hidden" name="supplierid" value="${supplierid }"/>
	    		<input type="hidden" name="suppliername" value="${suppliername }"/>
	    		<input type="hidden" name="businessdate1" value="${businessdate1 }"/>
	    		<input type="hidden" name="businessdate2" value="${businessdate2 }"/>
	    		<input type="hidden" name="buydeptid" value="${buydeptid }"/>
	    		<input type="hidden" name="buydeptname" value="${buydeptname }"/>
	    		<input type="hidden" name="brandid" value="${brandid }"/>
	    		<input type="hidden" name="brandname" value="${brandname }"/>
	    		<input type="hidden" name="buyuserid" value="${buyuserid }"/>
	    		<input type="hidden" name="buyusername" value="${buyusername }"/>
	    	</form>
	    	<security:authorize url="/report/buy/buysupplierReportExport.do">
				<a href="javaScript:void(0);" id="report-export-buysupplierDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
    	</div>
    	<script type="text/javascript">
			var SRD_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-buysupplierDetail").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-buysupplierDetail").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  //{field:'supplierid',title:'供应商编码',width:70,align:'left'},
						  //{field:'suppliername',title:'供应商名称',width:180,align:'left',sortable:true,isShow:true},
						  {field:'goodsname',title:'商品名称',width:230,sortable:true},
						  {field:'barcode',title:'条形码',sortable:true,width:90},
						  {field:'unitid',title:'主单位',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.unitname;
				        	}
						  },
						  {field:'enternum',title:'进货数量',width:80,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'entertotalbox',title:'进货箱数',width:80,align:'right',
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'auxunitid',title:'辅单位',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.auxunitname;
				        	}
						  },
						  {field:'entertaxamount',title:'进货总金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'enternotaxamount',title:'进货无税金额',resizable:true,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'entertax',title:'进货税额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outnum',title:'退货数量',width:80,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'outtotalbox',title:'退货箱数',width:80,align:'right',
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'outtaxamount',title:'退货总金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outnotaxamount',title:'退货无税金额',resizable:true,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outtax',title:'退货税额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalamount',title:'合计金额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
				
				$("#report-datagrid-buysupplierDetail").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		queryParams:initQueryJSON,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		url:'report/buy/showBuySupplierReportDetailData.do',
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		toolbar:'#report-toolbar-buysupplierDetail',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SRD_footerobject = footerrows[0];
							countTotalAmount();
						}
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
				
				$("#report-export-buysupplierDetail").Excel('export',{
					queryForm: "#report-query-form-buysupplierDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		<c:choose>
			 			<c:when test="${suppliername != null}">name:'按供应商：[${suppliername}]统计',</c:when>
			 			<c:when test="${buydeptname != null}">name:'按部门：[${buydeptname}]统计',</c:when>
			 			<c:when test="${brandname != null}">name:'按品牌：[${brandname}]统计',</c:when>
			 			<c:otherwise>name:'按采购员：[${buyusername}]统计',</c:otherwise>
			 		</c:choose>
			 		url:'report/buy/exportBuysupplierDetailReportData.do'
				});
    		});

			function countTotalAmount(){
    			var rows =  $("#report-datagrid-buysupplierDetail").datagrid('getChecked');
    			var enternum = 0;
        		var entertaxamount = 0;
        		var enternotaxamount=0;
        		var entertax = 0;
        		var outnum = 0;
        		var outtaxamount=0;
        		var outnotaxamount = 0;
        		var outtax = 0;
        		var totalamount = 0;
        		var auxenternum = 0;
        		var auxenterremainder = 0;
        		var auxoutnum = 0;
        		var auxoutremainder = 0;
				var entertotalbox = 0;
				var outtotalbox = 0;
        		
        		for(var i=0;i<rows.length;i++){
        			enternum = Number(enternum)+Number(rows[i].enternum == undefined ? 0 : rows[i].enternum);
        			entertaxamount = Number(entertaxamount)+Number(rows[i].entertaxamount == undefined ? 0 : rows[i].entertaxamount);
        			enternotaxamount = Number(enternotaxamount)+Number(rows[i].enternotaxamount == undefined ? 0 : rows[i].enternotaxamount);
        			entertax = Number(entertax)+Number(rows[i].entertax == undefined ? 0 : rows[i].entertax);
        			outnum = Number(outnum)+Number(rows[i].outnum == undefined ? 0 : rows[i].outnum);
        			outtaxamount = Number(outtaxamount)+Number(rows[i].outtaxamount == undefined ? 0 : rows[i].outtaxamount);
        			outnotaxamount = Number(outnotaxamount)+Number(rows[i].outnotaxamount == undefined ? 0 : rows[i].outnotaxamount);
        			outtax = Number(outtax)+Number(rows[i].outtax == undefined ? 0 : rows[i].outtax);
        			totalamount = Number(totalamount)+Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
        			auxenternum = Number(auxenternum)+Number(rows[i].auxenternum == undefined ? 0 : rows[i].auxenternum);
        			auxenterremainder = Number(auxenterremainder)+Number(rows[i].auxenterremainder == undefined ? 0 : rows[i].auxenterremainder);
        			auxoutnum = Number(auxoutnum)+Number(rows[i].auxoutnum == undefined ? 0 : rows[i].auxoutnum);
        			auxoutremainder = Number(auxoutremainder)+Number(rows[i].auxoutremainder == undefined ? 0 : rows[i].auxoutremainder);
					entertotalbox = Number(entertotalbox)+Number(rows[i].entertotalbox || 0);
					outtotalbox = Number(outtotalbox)+Number(rows[i].outtotalbox || 0);
				}
        		var foot=[{goodsname:'选中合计',enternum:enternum,entertaxamount:entertaxamount,enternotaxamount:enternotaxamount,entertax:entertax,
            				outnum:outnum,outtaxamount:outtaxamount,outnotaxamount:outnotaxamount,outtax:outtax,
            				totalamount:totalamount,entertotalbox:entertotalbox,outtotalbox:outtotalbox
            			}];
        		if(null!=SRD_footerobject){
            		foot.push(SRD_footerobject);
        		}
        		$("#report-datagrid-buysupplierDetail").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
