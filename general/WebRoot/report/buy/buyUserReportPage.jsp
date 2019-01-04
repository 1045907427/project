<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分采购员采购情况统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-buyuser"></table>
    	<div id="report-toolbar-buyuser" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/buy/buyUserReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-buyUserPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-buyuser" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>采购员:</td>
	    				<td><input type="text" id="report-query-buyuserid" name="buyuserid"/></td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-queay-buyuser" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-buyuser" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-buyuserDetail"></div>
    	<script type="text/javascript">
    		var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-buyuser").serializeJSON();
    		$(function(){
                $("#report-buttons-buyUserPage").click(function(){
                    var queryJSON = $("#report-query-form-buyuser").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-buyuser").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/buy/exportBuyUserReportData.do";
                    exportByAnalyse(queryParam,"分采购员采购情况统计报表","report-datagrid-buyuser",url);
                });

    			var tableColumnListJson = $("#report-datagrid-buyuser").createGridColumnLoad({
					name:'temp_purchase_report_buysuer',
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'buyuserid',title:'采购员',width:100,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.buyusername;
				        	}
						  },
						  //{field:'goodsname',title:'商品名称',width:250,sortable:true},
						  //{field:'barcode',title:'条形码',sortable:true,width:90},
						  {field:'unitid',title:'主单位',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.unitname;
				        	}
						  },
						  {field:'auxunitid',title:'辅单位',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.auxunitname;
				        	}
						  },
						  {field:'enternum',title:'进货数量',width:80,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		if("合计" != rowData.buyusername && "选中合计" != rowData.buyusername){
						  			return formatterBigNumNoLen(value)+rowData.unitname;
						  		}else{
						  			return formatterBigNumNoLen(value);
						  		}
				        	}
						  },
						  {field:'entertotalbox',title:'进货箱数',width:80,align:'right',
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'entertaxamount',title:'进货金额',resizable:true,sortable:true,align:'right',
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
						  {field:'outtotalbox',title:'退货箱数',width:80,sortable:true,align:'right',
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'outtaxamount',title:'退货金额',resizable:true,sortable:true,align:'right',
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
						  {field:'totalamount',title:'合计金额',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-buyuser").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-buyuser',
					onDblClickRow:function(rowIndex, rowData){
						var buyuserid = rowData.buyuserid;
						var buyusername = rowData.buyusername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/buy/showBuySupplierReportDetailDataPage.do?buyuserid='+buyuserid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&buyusername='+buyusername;
						$("#report-dialog-buyuserDetail").dialog({
						    title: '按采购员：['+rowData.buyusername+'],分商品统计表',  
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
						    href: url
						});
						$("#report-dialog-buyuserDetail").dialog("open");
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
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
				$("#report-query-buyuserid").widget({
					name:'t_storage_purchase_enter',
		    		width:130,
					col:'buyuserid',
					onlyLeafCheck:false,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-buyuser","report-reload-buyuser");
				
				//查询
				$("#report-queay-buyuser").click(function(){
		      		var queryJSON = $("#report-query-form-buyuser").serializeJSON();
		      		$("#report-datagrid-buyuser").datagrid({
		      			pageNumber:1,
		      			url: 'report/buy/getBuyUserReportData.do',
		      			queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-buyuser").click(function(){
					$("#report-query-buyuserid").widget("clear");
					$("#report-query-form-buyuser")[0].reset();
					var queryJSON = $("#report-query-form-buyuser").serializeJSON();
		       		$("#report-datagrid-buyuser").datagrid('loadData',{total:0,rows:[]});
				});

				function countTotalAmount(){
	    			var rows =  $("#report-datagrid-buyuser").datagrid('getChecked');
	    			
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
	        		var foot=[{buyusername:'选中合计',unitname:'',enternum:enternum,entertaxamount:entertaxamount,enternotaxamount:enternotaxamount,entertax:entertax,
	            				outnum:outnum,outtaxamount:outtaxamount,outnotaxamount:outnotaxamount,outtax:outtax,
	            				totalamount:totalamount,entertotalbox:entertotalbox,outtotalbox:outtotalbox
	            			}];
	        		if(null!=SR_footerobject){
	            		foot.push(SR_footerobject);
	        		}
	        		$("#report-datagrid-buyuser").datagrid("reloadFooter",foot);
	    		}
    		});
    	</script>
  </body>
</html>
