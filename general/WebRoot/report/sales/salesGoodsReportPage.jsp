<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按商品销售情况统计报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-salesGoods"></table>
    	<div id="report-toolbar-salesGoods" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesGoodsReportExport.do">
                    <a href="javaScript:void(0);" id="report-autoExport-salesGoodsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" >全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesGoods" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>商品分类:</td>
    				<td><input type="text" id="report-query-goodssort" name="goodssort"/></td>
                    <td>商品类型:</td>
                    <td><select name="goodstype" id="report-query-goodstype" style="width: 132px;" data-options="multiple:true,onLoadSuccess:function(){$(this).combobox('clear');}">
                        <option></option>
                        <c:forEach items="${goodstypeList }" var="list">
                            <option value="${list.code }">${list.codename }</option>
                        </c:forEach>
                    </select>
                    </td>
    			</tr>
    			<tr>
    				<td>商品名称:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 225px"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="report-query-brandid" name="brandid"/></td>
    				<td colspan="2">
                        <a href="javaScript:void(0);" id="report-query-salesGoods" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-salesGoods" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-dialog-salesGoodsDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesGoods").serializeJSON();
    		$(function(){

                $("#report-autoExport-salesGoodsPage").click(function(){
                    var queryJSON = $("#report-query-form-salesGoods").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-salesGoods").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportBaseSalesReportData.do?groupcols=goodsid";
                    exportByAnalyse(queryParam,"分商品销售情况统计报表","report-datagrid-salesGoods",url);
                });

    			var tableColumnListJson = $("#report-datagrid-salesGoods").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'goodsid',title:'商品编码',width:60,sortable:true,
							  formatter:function(value,rowData,rowIndex){
								  if("nodata" != value){
									  return value;
								  }else{
									  return "";
								  }
							  }
						  },
						  {field:'goodsname',title:'商品名称',width:250},
						  {field:'barcode',title:'条形码',width:90,hidden:true},
                          {field:'spell',title:'助记符',sortable:true,width:90},
						  {field:'brandid',title:'商品品牌',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.brandname;
				        	}
						  },
						  {field:'goodssort',title:'商品分类',width:100,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.goodssortname;
				        	}
						  },
						  {field:'goodstype',title:'商品类型',width:60,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.goodstypename;
				        	}
						  },
						  {field:'unitname',title:'主单位',width:45,hidden:true},
						  {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'orderamount',title:'订单金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'ordernotaxamount',title:'订单未税金额',width:80,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'initsendnum',title:'发货单数量',width:70,align:'right',hidden:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'initsendamount',title:'发货单金额',width:70,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'initsendnotaxamount',title:'发货单未税金额',width:90,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'sendnum',title:'发货出库数量',width:80,align:'right',hidden:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'sendamount',title:'发货出库金额',width:80,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'sendnotaxamount',title:'发货出库未税金额',width:100,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  <c:if test="${map.sendcostamount == 'sendcostamount'}">
							  {field:'sendcostamount',title:'发货出库成本',width:80,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
						  </c:if>
						  {field:'pushbalanceamount',title:'冲差金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'directreturnnum',title:'直退数量',width:60,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'checkreturnamount',title:'退货金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'checkreturnrate',title:'退货率',width:60,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=0){
				        			return formatterMoney(value)+"%";
				        		}else{
				        			return "";
				        		}
				        	}
						  },
						  {field:'returnnum',title:'退货总数量',width:70,sortable:true,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'returnamount',title:'退货合计',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'saleamount',title:'销售金额',width:60,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salenotaxamount',title:'销售无税金额',width:80,align:'right',hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'saletax',title:'销售税额',width:60,align:'right',isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
						  <c:if test="${map.costamount == 'costamount'}">
						  	,
						  	{field:'costamount',title:'成本金额',align:'right',width:60,isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							 }
						  </c:if>
						  <c:if test="${map.salemarginamount == 'salemarginamount'}">
							  ,
							  {field:'salemarginamount',title:'销售毛利额',width:70,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.realrate == 'realrate'}">
							  ,
							  {field:'realrate',title:'实际毛利率',width:70,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(value!=null && value!=0){
					        			return formatterMoney(value)+"%";
					        		}else{
					        			return "";
					        		}
					        	}
							  }
						  </c:if>
			         ]]
				});

                var getCol = {};
                var commonCol = "{\"common\":[[";
                //填入不是复选框的冻结列
                var frozenCol = tableColumnListJson.frozen[0];
                if(frozenCol.length > 0){
                    for(var i = 0 ; i < frozenCol.length ; ++ i){
                        if(frozenCol[i].field != "idok"){
                            getCol["field"] = frozenCol[i].field;
                            getCol["title"] = frozenCol[i].title;
                        }else{
                            continue ;
                        }
                        commonCol = commonCol +  JSON.stringify(getCol) + "," ;
                    }
                }

                var col = tableColumnListJson.common[0];
                for(var i = 0 ; i < col.length ; ++ i){
                    if(col[i].hidden == undefined || col[i].hidden == false ){
                        getCol["field"] = col[i].field;
                        getCol["title"] = col[i].title;
                    }else{
                        continue ;
                    }
                    if(i != col.length - 1){
                        commonCol = commonCol +  JSON.stringify(getCol) + "," ;
                    }else{
                        commonCol = commonCol + "," + JSON.stringify(getCol) +"]],\"exportname\":\"分商品销售情况统计报表（自动）\"}";
                    }
                }
                $("#createGridColumnLoad-commonCol").val(commonCol);

    			$("#report-datagrid-salesGoods").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		pageSize:100,		  
					toolbar:'#report-toolbar-salesGoods',
					onDblClickRow:function(rowIndex, rowData){
						var goodsid = rowData.goodsid;
						var goodsname = rowData.goodsname;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/sales/showSalesGoodsCustomerDetailPage.do?goodsid='+goodsid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&goodsname='+goodsname;
						$("#report-dialog-salesGoodsDetail").dialog({
						    title: '按商品：['+rowData.goodsid+'：'+rowData.goodsname+'],分客户统计表',  
				    		width:800,
				    		height:400,
				    		closed:false,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
						    href: url
						});
					},
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							baseSalesReportCountTotalAmount('goodsname',this);
						}
			 		},
					onCheckAll:function(){
						baseSalesReportCountTotalAmount('goodsname',this);
					},
					onUncheckAll:function(){
						baseSalesReportCountTotalAmount('goodsname',this);
					},
					onCheck:function(){
						baseSalesReportCountTotalAmount('goodsname',this);
					},
					onUncheck:function(){
						baseSalesReportCountTotalAmount('goodsname',this);
					}
				}).datagrid("columnMoving");
				$("#report-query-goodsid").goodsWidget({
				});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:160,
					singleSelect:true
				});
				
				$("#report-query-goodssort").widget({
					referwid:'RL_T_BASE_GOODS_WARESCLASS',
		    		width:160,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-query-salesGoods","report-reload-salesGoods");
				
				//查询
				$("#report-query-salesGoods").click(function(){
		      		var queryJSON = $("#report-query-form-salesGoods").serializeJSON();
		      		$("#report-datagrid-salesGoods").datagrid({
		      			url: 'report/sales/showBaseSalesReportData.do?groupcols=goodsid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesGoods").click(function(){
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-goodssort").widget("clear");
					$("#report-query-goodstype").combobox('clear');
					$("#report-query-form-salesGoods")[0].reset();
					var queryJSON = $("#report-query-form-salesGoods").serializeJSON();
		       		$("#report-datagrid-salesGoods").datagrid('loadData',{total:0,rows:[]});
				});

    		});

    	</script>
  </body>
</html>
