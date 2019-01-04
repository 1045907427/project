<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按商品采购情况统计报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-buyGoods"></table>
    	<div id="report-toolbar-buyGoods" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/buy/buyGoodsReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-buyGoodsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-buyGoods" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>商品名称:</td>
	    				<td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 180px"/></td>
	    				<td>商品分类:</td>
	    				<td><input type="text" id="report-query-goodssort" name="goodssort"/></td>
	    			</tr>
	    			<tr>
	    				<td>供应商:</td>
	    				<td><input type="text" id="report-query-supplier" name="supplierid" style="width: 225px"/></td>
	    				<td>品牌名称:</td>
	    				<td><input type="text" id="report-query-brandid" name="brandid" style="width: 180px"/></td>
						<td>仓库名称:</td>
						<td><input type="text" id="report-query-storageid" name="storageid"/></td>
	    			</tr>
					<tr>
						<td colspan="4"></td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="report-queay-buyGoods" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-buyGoods" class="button-qr">重置</a>
						</td>
					</tr>
	    		</table>
	    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-buyGoods").serializeJSON();
    		$(function(){
                $("#report-buttons-buyGoodsPage").click(function(){
                    var queryJSON = $("#report-query-form-buyGoods").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-buyGoods").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/buy/exportBuyGoodsReportData.do";
                    exportByAnalyse(queryParam,"分商品采购情况统计报表","report-datagrid-buyGoods",url);
                });

    			var tableColumnListJson = $("#report-datagrid-buyGoods").createGridColumnLoad({
					frozenCol : [[
							{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'goodsid',title:'商品编码',width:70,sortable:true},
						  {field:'goodsname',title:'商品名称',width:130},
						  {field:'barcode',title:'条形码',width:90},
						  {field:'brandname',title:'品牌名称',width:80},
						  {field:'unitid',title:'主单位',width:60,hidden:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.unitname;
				        	}
						  },
						  {field:'auxunitid',title:'辅单位',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.auxunitname;
				        	}
						  },
						  {field:'enternum',title:'进货数量',width:80,align:'right',hidden:true,sortable:false,
						  	formatter:function(value,rowData,rowIndex){
								if(undefined != value){
									if("合计" != rowData.goodsname && "选中合计" != rowData.goodsname){
										return formatterBigNumNoLen(value)+rowData.unitname;
									}else{
										return formatterBigNumNoLen(value);
									}
								}
				        	}
						  },
						  {field:'entertotalbox',title:'进货箱数',width:80,align:'right',sortable:false,
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'entertaxamount',title:'进货总金额',resizable:true,sortable:false,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'enternotaxamount',title:'进货无税金额',resizable:true,sortable:false,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'entertax',title:'进货税额',resizable:true,align:'right',sortable:false,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outnum',title:'退货数量',width:80,sortable:false,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'outtotalbox',title:'退货箱数',width:80,sortable:false,align:'right',
							  formatter:function(value,rowData,rowIndex){
								  return formatterBigNumNoLen(value);
							  }
						  },
						  {field:'outtaxamount',title:'退货总金额',resizable:true,sortable:false,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outnotaxamount',title:'退货无税金额',resizable:true,sortable:false,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'outtax',title:'退货税额',resizable:true,sortable:false,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalamount',title:'合计金额',resizable:true,align:'right',sortable:false,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-buyGoods").datagrid({
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
					toolbar:'#report-toolbar-buyGoods',
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
				$("#report-query-goodsid").goodsWidget({});
				$("#report-query-supplier").supplierWidget({});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:180,
					onlyLeafCheck:false,
					singleSelect:true
				});
				$("#report-query-goodssort").widget({
					referwid:'RL_T_BASE_GOODS_WARESCLASS',
		    		width:130,
					onlyLeafCheck:false,
					singleSelect:false
				});
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
					width:130,
					onlyLeafCheck:false,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-buyGoods","report-reload-buyGoods");
				
				//查询
				$("#report-queay-buyGoods").click(function(){
		      		var queryJSON = $("#report-query-form-buyGoods").serializeJSON();
		      		$("#report-datagrid-buyGoods").datagrid({
		      			url: 'report/buy/showBuyGoodsReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-buyGoods").click(function(){
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-supplier").supplierWidget('clear');
					$("#report-query-goodssort").widget('clear');
					$("#report-query-storageid").widget('clear');
					$("#report-query-form-buyGoods")[0].reset();
					var queryJSON = $("#report-query-form-buyGoods").serializeJSON();
		       		$("#report-datagrid-buyGoods").datagrid('loadData',{total:0,rows:[]});
				});

				function countTotalAmount(){
	    			var rows =  $("#report-datagrid-buyGoods").datagrid('getChecked');
	    			
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
						enternum = Number(enternum)+Number(rows[i].enternum || 0);
						entertaxamount = Number(entertaxamount)+Number(rows[i].entertaxamount || 0);
						enternotaxamount = Number(enternotaxamount)+Number(rows[i].enternotaxamount || 0);
						entertax = Number(entertax)+Number(rows[i].entertax || 0);
						outnum = Number(outnum)+Number(rows[i].outnum || 0);
						outtaxamount = Number(outtaxamount)+Number(rows[i].outtaxamount || 0);
						outnotaxamount = Number(outnotaxamount)+Number(rows[i].outnotaxamount || 0);
						outtax = Number(outtax)+Number(rows[i].outtax || 0);
						totalamount = Number(totalamount)+Number(rows[i].totalamount || 0);
						auxenternum = Number(auxenternum)+Number(rows[i].auxenternum || 0);
						auxenterremainder = Number(auxenterremainder)+Number(rows[i].auxenterremainder || 0);
						auxoutnum = Number(auxoutnum)+Number(rows[i].auxoutnum || 0);
						auxoutremainder = Number(auxoutremainder)+Number(rows[i].auxoutremainder || 0);
						entertotalbox = Number(entertotalbox)+Number(rows[i].entertotalbox || 0);
						outtotalbox = Number(outtotalbox)+Number(rows[i].outtotalbox || 0);

					}
	        		var foot=[{goodsname:'选中合计',unitname:'',enternum:enternum,entertaxamount:entertaxamount,enternotaxamount:enternotaxamount,entertax:entertax,
	            				outnum:outnum,outtaxamount:outtaxamount,outnotaxamount:outnotaxamount,outtax:outtax,
	            				totalamount:totalamount,entertotalbox:entertotalbox,outtotalbox:outtotalbox
	            			}];
	        		if(null!=SR_footerobject){
	            		foot.push(SR_footerobject);
	        		}
	        		$("#report-datagrid-buyGoods").datagrid("reloadFooter",foot);
	    		}
    		});
    	</script>
  </body>
</html>
