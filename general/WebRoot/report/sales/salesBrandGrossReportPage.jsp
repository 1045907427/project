<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌销售毛利分析报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-salesBrandGross"></table>
    	<div id="report-toolbar-salesBrandGross" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/exportSalesBrandGrossReportData.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesBrandGrossPageBtn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    <a href="javaScript:void(0);" id="report-buttons-salesBrandGrossPage" style="display: none">导出控件</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesBrandGross" method="post">
	    		<table  class="querytable">

	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>商品品牌:</td>
	    				<td><input type="text" id="report-query-brand" name="brandid"/></td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-queay-salesBrandGross" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-salesBrandGross" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesBrandGross").serializeJSON();
    		$(function(){
    			var tableColumnListBrandJson = $("#report-datagrid-salesBrandGross").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'brandid',title:'品牌编码',width:60},
						  {field:'brandname',title:'商品品牌',width:80},
						  {field:'saleamount',title:'销售金额',width:70,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'grossamount',title:'毛利冲差',width:70,resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'balanceamount',title:'费用冲差',width:70,resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'writeoffamount',title:'回笼金额',width:70,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'grossamountwriteoff',title:'回笼毛利冲差',width:85,resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'balanceamountwriteoff',title:'回笼费用冲差',width:85,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-salesBrandGross").datagrid({ 
					authority:tableColumnListBrandJson,
			 		frozenColumns: tableColumnListBrandJson.frozen,
					columns:tableColumnListBrandJson.common,
			 		method:'post',
			 		idField:'brandid',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-salesBrandGross',
		  	 		onLoadSuccess:function(){
	    				var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
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
				
				
				$("#report-query-brand").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:130,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-salesBrandGross","report-reload-salesBrandGross");
				
				//查询
				$("#report-queay-salesBrandGross").click(function(){
		      		var queryJSON = $("#report-query-form-salesBrandGross").serializeJSON();
		      		$("#report-datagrid-salesBrandGross").datagrid({
		      			url: 'report/sales/showSalesBrandGrossReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesBrandGross").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-brand").widget("clear");
					$("#report-query-form-salesBrandGross")[0].reset();
					var queryJSON = $("#report-query-form-salesBrandGross").serializeJSON();
					$("#report-datagrid-salesBrandGross").datagrid('loadData',{total:0,rows:[]});
				});
				<security:authorize url="/report/sales/exportSalesBrandGrossReportData.do">
				$("#report-buttons-salesBrandGrossPageBtn").click(function(){
					var rows =  $("#report-datagrid-salesBrandGross").datagrid('getChecked');

					//查询参数直接添加在url中         
		    		var brandidarrs=new Array();
		    		if(null !=rows && rows.length>0){
			    		for(var i=0;i<rows.length;i++){
				    		if(rows[i].brandid && rows[i].brandid!=""){
				    			brandidarrs.push(rows[i].brandid);
				    		}
			    		}
		    		}
		    		var startdate=$("#report-query-businessdate1").val();
		    		var enddate = $("#report-query-businessdate2").val();
		    		var exportTitle=startdate+"至"+enddate+" 品牌销售毛利分析报表";
		    		$("#report-buttons-salesBrandGrossPage").Excel('export',{
						queryForm: "#report-query-form-salesBrandGross", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
				 		type:'exportUserdefined',
				 		name:exportTitle,
				 		fieldParam:{brandidarr:brandidarrs.join(",")},
				 		url:'report/sales/exportSalesBrandGrossReportData.do'
					});
		    		$("#report-buttons-salesBrandGrossPage").trigger('click');
				});			
				</security:authorize>
    		});
    		function countTotalAmount(){
        		var rows =  $("#report-datagrid-salesBrandGross").datagrid('getChecked');
        		if(null==rows || rows.length==0){
               		var foot=[];
        			if(null!=SR_footerobject){
    	        		foot.push(SR_footerobject);
    	    		}
        			$("#report-datagrid-salesBrandGross").datagrid("reloadFooter",foot);
               		return false;
           		}
        		var saleamount = 0;
        		var grossamount = 0;
        		var balanceamount=0;
        		var writeoffamount=0;
        		var grossamountwriteoff=0;
        		var balanceamountwriteoff=0;
        		for(var i=0;i<rows.length;i++){
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			grossamount = Number(grossamount)+Number(rows[i].grossamount == undefined ? 0 : rows[i].grossamount);
        			balanceamount = Number(balanceamount)+Number(rows[i].balanceamount == undefined ? 0 : rows[i].balanceamount);
        			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
        			grossamountwriteoff = Number(grossamountwriteoff)+Number(rows[i].grossamountwriteoff == undefined ? 0 : rows[i].grossamountwriteoff);
        			balanceamountwriteoff = Number(balanceamountwriteoff)+Number(rows[i].balanceamountwriteoff == undefined ? 0 : rows[i].balanceamountwriteoff);
        		}
        		var foot=[{brandname:'选中金额',saleamount:saleamount,grossamount:grossamount,balanceamount:balanceamount,writeoffamount:writeoffamount,grossamountwriteoff:grossamountwriteoff,balanceamountwriteoff:balanceamountwriteoff}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-salesBrandGross").datagrid("reloadFooter",foot);
        	}
    	</script>
  </body>
</html>
