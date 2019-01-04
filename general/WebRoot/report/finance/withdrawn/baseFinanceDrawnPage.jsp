<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金回笼基础情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
      <style>
          .checkbox1{
              float:left;
              height: 22px;
              line-height: 22px;
          }
          .divtext{
              height:22px;
              line-height:22px;
              float:left;
              display: block;
          }
      </style>
  </head>

  <body>
    	<table id="report-datagrid-baseFinanceDrawn"></table>
    	<div id="report-toolbar-baseFinanceDrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/baseFinanceDrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-baseFinanceDrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
				<security:authorize url="/report/finance/baseFinanceDrawnPrint.do">
					<a href="javaScript:void(0);" id="report-buttons-baseFinanceDrawnPrint" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
				</security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-baseFinanceDrawn" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
    			</tr>
    			<tr>
    				<td>品&nbsp;&nbsp;牌:</td>
		  			<td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
		  			<td>品牌部门:</td>
		  			<td><input type="text" name="branddept" id="report-branddept-advancedQuery"/></td>
		  			<td>品牌业务员:</td>
		  			<td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
    			</tr>
    			<tr>
    				<td>商&nbsp;&nbsp;品:</td>
	  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery" style="width: 210px;"/>
    				<td>销售区域:</td>
	  				<td><input type="text" name="salesarea" id="report-salesarea-advancedQuery" style="width: 210px;"/>
	  				<td>销售部门:</td>
	  				<td><input type="text" name="salesdept" id="report-salesdept-advancedQuery" />
    			</tr>
    			<tr>
    				<td>客户业务员:</td>
					<td><input type="text" name="salesuser" id="report-salesuser-advancedQuery"/></td>
    				<td>供应商:</td>
					<td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
					<td>商品分类:</td>
					<td><input type="text" name="goodssort" id="report-goodssort-advancedQuery" style="width: 210px;"/>
    			</tr>
				<tr>
					<td colspan="4">
					</td>
					<td colspan="2">
						<a href="javaScript:void(0);" id="report-queay-baseFinanceDrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-baseFinanceDrawn" class="button-qr">重置</a>
					</td>
				</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="6">
    					<input type="checkbox" class="groupcols checkbox1" value="customerid" id="report-customerid-advancedQuery"/>
                        <label for="report-customerid-advancedQuery" class="divtext">客户</label>
    					<input type="checkbox" class="groupcols checkbox1" value="pcustomerid" id="report-pcustomerid-advancedQuery"/>
                        <label for="report-pcustomerid-advancedQuery" class="divtext">总店</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesuser" id="report-salesuser-checkbox-advancedQuery"/>
                        <label for="report-salesuser-checkbox-advancedQuery" class="divtext">客户业务员</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesarea" id="report-salesarea1-advancedQuery"/>
                        <label for="report-salesarea1-advancedQuery" class="divtext">销售区域</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesdept" id="report-salesdept1-advancedQuery"/>
                        <label for="report-salesdept1-advancedQuery" class="divtext">销售部门</label>
    					<input type="checkbox" class="groupcols checkbox1" value="goodsid" id="report-goodsid1-advancedQuery"/>
                        <label for="report-goodsid1-advancedQuery" class="divtext"> 商品</label>
    					<input type="checkbox" class="groupcols checkbox1" value="branddept"  id="report-branddept1-advancedQuery"/>
                        <label for="report-branddept1-advancedQuery" class="divtext">品牌部门</label>
    					<input type="checkbox" class="groupcols checkbox1" value="branduser"  id="report-branduser1-advancedQuery" />
                        <label for="report-branduser1-advancedQuery" class="divtext">品牌业务员</label>
    					<input type="checkbox" class="groupcols checkbox1" value="brandid" id="report-brandid1-advancedQuery"/>
                        <label for="report-brandid1-advancedQuery" class="divtext">品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" value="supplieruser" id="report-supplieruser-advancedQuery"/>
                        <label for="report-supplieruser-advancedQuery" class="divtext">厂家业务员</label>
						<input type="checkbox" class="groupcols checkbox1" value="supplierid" id="report-supplierid-advancedQuery"/>
						<label for="report-supplierid-advancedQuery" class="divtext">供应商</label>
						<input type="checkbox" class="groupcols checkbox1" value="goodssort" id="report-goodssort1-advancedQuery"/>
						<label for="report-goodssort1-advancedQuery" class="divtext">商品分类</label>
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<div id="report-dialog-selectmoreparam"></div>
    	<div id="report-div-selectmoreparam">
    		<form action="" method="post" id="report-form-selectmoreparam">
    			<table cellpadding="0" cellspacing="0" border="0">
    				<tr>
    					<td>客户:</td>
    					<td><input type="text" name="id" id="report-customer-selectmoreparamlist"/></td>
    					<td>
    						<a href="javaScript:void(0);" id="report-queay-selectmoreparam" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-selectmoreparam" class="button-qr">重置</a>
    					</td>
    				</tr>
    			</table>
		  	</form>
    	</div>
    	<script type="text/javascript">
    		var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseFinanceDrawn").serializeJSON();
    		$(function(){
                $("#report-buttons-baseFinanceDrawnPrint").click(function () {
                    var msg = "";
                    printByAnalyse("资金回笼基础情况表", "report-datagrid-baseFinanceDrawn", "report/finance/printBaseFinanceDrawnData.do", msg);
                })

                $("#report-buttons-baseFinanceDrawnPage").click(function(){
                    var queryJSON = $("#report-query-form-baseFinanceDrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-baseFinanceDrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"资金回笼情况表","report-datagrid-baseFinanceDrawn",url);
                });
		  		
		   		//查询
				$("#report-queay-selectmoreparam").click(function(){
		      		var selectmoreQueryJSON = $("#report-form-selectmoreparam").serializeJSON();
		      		$("#report-datagrid-selectmoreparam").datagrid('load',selectmoreQueryJSON);
				});
				//重置
				$("#report-reload-selectmoreparam").click(function(){
			  		$("#report-customer-selectmoreparamlist").widget('clear');
				    $("#report-form-selectmoreparam").form('reset');
					$("#report-datagrid-selectmoreparam").datagrid("reload");
				});
				
    			$(".groupcols").click(function(){
    				var cols = "";
    				$(".groupcols").each(function(){
    					if($(this).attr("checked")){
    						if(cols==""){
    							cols = $(this).val();
    						}else{
    							cols += ","+$(this).val();
    						}
    						$("#report-query-groupcols").val(cols);
    					}else{
                            $("#report-query-groupcols").val(cols);
                        }
					});
    			});
    			var tableColumnListJson = $("#report-datagrid-baseFinanceDrawn").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
                        {field:'customerid',title:'客户编号',sortable:true,width:60},
                        {field:'customername',title:'客户名称',width:130},
                        {field:'pcustomerid',title:'总店名称',sortable:true,width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.pcustomername;
                            }
                        },
                        {field:'salesuser',title:'客户业务员',sortable:true,width:80,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesusername;
                            }
                        },
                        {field:'salesarea',title:'销售区域',sortable:true,width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesareaname;
                            }
                        },
                        {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesdeptname;
                            }
                        },
                        {field:'branduser',title:'品牌业务员',sortable:true,width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.brandusername;
                            }
                        },
                        {field:'branddept',title:'品牌部门',sortable:true,width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.branddeptname;
                            }
                        },
                        {field:'supplieruser',title:'厂家业务员',sortable:true,width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.supplierusername;
                            }
                        },
						{field:'supplierid',title:'供应商编码',sortable:true,width:80,hidden:true},
						{field:'suppliername',title:'供应商名称',sortable:true,width:150,hidden:true},
                        {field:'goodsid',title:'商品编码',sortable:true,width:60},
                        {field:'goodsname',title:'商品名称',sortable:true,width:130},
                        {field:'goodssort',title:'商品分类',sortable:true,width:80,hidden:true},
                        {field:'goodssortname',title:'商品分类名称',sortable:true,width:150,hidden:true},
                        {field:'barcode',title:'条形码',sortable:true,width:90},
                        {field:'brandid',title:'品牌名称',sortable:true,width:80,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.brandname;
                            }
                        },
                        {field:'unitnum',title:'数量',width:60,
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
                        {field:'auxnumdetail',title:'辅数量',width:80,hidden:true},
                        {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                        ,
                        {field:'costwriteoffamount',title:'回笼成本',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                        ,
                        {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffrate == 'writeoffrate'}">
                        ,
                        {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                if(null != value && "" != value){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        }
                        </c:if>
                    ]]
				});

				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
					width:150,
					singleSelect:false
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
					width:120,
					singleSelect:false
				});
				$("#report-goodsid-advancedQuery").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
					singleSelect:false,
					width:225,
					onlyLeafCheck:true
				});
				$("#report-salesarea-advancedQuery").widget({
					referwid:'RT_T_BASE_SALES_AREA',
					singleSelect:false,
					width:'150',
					onlyLeafCheck:false
				});
				//品牌
				$("#report-brandid-advancedQuery").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
					singleSelect:false,
					width:225,
					onlyLeafCheck:true,
					onSelect:function(data){
						$("#report-hdbrandid-advancedQuery").val("");
					}
				});
				//品牌部门
				$("#report-branddept-advancedQuery").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
					singleSelect:false,
					width:'150',
					onlyLeafCheck:true,
					onSelect:function(data){
						$("#report-hdbranddept-advancedQuery").val("");
					}
				});
				//品牌业务员
				$("#report-branduser-advancedQuery").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
					singleSelect:false,
					width:'120',
					onlyLeafCheck:true,
					onSelect:function(data){
						$("#report-hdbranduser-advancedQuery").val("");
					}
				});
				//销售部门
				$("#report-salesdept-advancedQuery").widget({
					name:'t_sales_order',
					col:'salesdept',
					width:120,
					singleSelect:false,
					onlyLeafCheck:true
				});
				//客户业务员
				$("#report-salesuser-advancedQuery").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
					singleSelect:false,
					width:225,
					onlyLeafCheck:false
				});
				//供应商
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
					width:150,
					singleSelect:false
				});
                $("#report-goodssort-advancedQuery").widget({
                    referwid:'RL_T_BASE_GOODS_WARESCLASS',
                    singleSelect:false,
                    width:120,
                    onlyLeafCheck:false
                });
    			$("#report-datagrid-baseFinanceDrawn").datagrid({ 
    				authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-baseFinanceDrawn',
                    url:'report/finance/showBaseFinanceDrawnData.do',
                    queryParams:initQueryJSON,
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							baseFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
						}
			 		},
					onCheckAll:function(){
						baseFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onUncheckAll:function(){
						baseFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onCheck:function(){
						baseFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onUncheck:function(){
						baseFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					}
				}).datagrid("columnMoving");

                //查询
                $("#report-queay-baseFinanceDrawn").click(function(){
                    setColumn();
                    //把form表单的name序列化成JSON对象
                    var queryJSON = $("#report-query-form-baseFinanceDrawn").serializeJSON();
                    $("#report-datagrid-baseFinanceDrawn").datagrid("load",queryJSON);
                });
				//重置
				$("#report-reload-baseFinanceDrawn").click(function(){
					$(".groupcols").each(function(){
						if($(this).attr("checked")){
							$(this)[0].checked = false;
						}
					});
					$("#report-query-groupcols").val("");
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-brandid-advancedQuery").widget("clear");
					$("#report-branddept-advancedQuery").widget("clear");
					$("#report-branduser-advancedQuery").widget("clear");
					$("#report-goodsid-advancedQuery").widget("clear");
					$("#report-salesarea-advancedQuery").widget("clear");
					$("#report-salesdept-advancedQuery").widget("clear");
					$("#report-salesuser-advancedQuery").widget("clear");
                    $("#report-goodssort-advancedQuery").widget("clear");
					$("#report-query-supplierid").widget("clear");
					$("#report-query-form-baseFinanceDrawn").form("reset");
					$('#report-datagrid-baseFinanceDrawn').datagrid('loadData',{total:0,rows:[]});
					$('#report-datagrid-baseFinanceDrawn').datagrid('reloadFooter',[]);
				});

    		});
    		var $datagrid = $("#report-datagrid-baseFinanceDrawn");
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$datagrid.datagrid('hideColumn', "customerid");
					$datagrid.datagrid('hideColumn', "customername");
					$datagrid.datagrid('hideColumn', "pcustomerid");
					$datagrid.datagrid('hideColumn', "salesuser");
					$datagrid.datagrid('hideColumn', "salesarea");
					$datagrid.datagrid('hideColumn', "salesdept");
					$datagrid.datagrid('hideColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "goodsname");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('hideColumn', "brandid");
                    $datagrid.datagrid('hideColumn', "unitnum");
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
                    $datagrid.datagrid('hideColumn', "supplieruser");
					$datagrid.datagrid('hideColumn', "supplierid");
					$datagrid.datagrid('hideColumn', "suppliername");
                    $datagrid.datagrid('hideColumn', "goodssort");
                    $datagrid.datagrid('hideColumn', "goodssortname");
				}
				else{
					$datagrid.datagrid('showColumn', "customerid");
					$datagrid.datagrid('showColumn', "customername");
					$datagrid.datagrid('showColumn', "pcustomerid");
					$datagrid.datagrid('showColumn', "salesuser");
					$datagrid.datagrid('showColumn', "goodsid");
					$datagrid.datagrid('showColumn', "goodsname");
					$datagrid.datagrid('showColumn', "barcode");
					$datagrid.datagrid('showColumn', "brandid");
                    $datagrid.datagrid('showColumn', "unitnum");
                    $datagrid.datagrid('showColumn', "goodssort");
                    $datagrid.datagrid('showColumn', "goodssortname");
				}
				
					var colarr = cols.split(",");
					for(var i=0;i<colarr.length;i++){
						var col = colarr[i];
						if(col=='customerid'){
							$datagrid.datagrid('showColumn', "customerid");
							$datagrid.datagrid('showColumn', "customername");
							$datagrid.datagrid('showColumn', "pcustomerid");
							$datagrid.datagrid('showColumn', "salesuser");
							//$datagrid.datagrid('showColumn', "salesarea");
							//$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="pcustomerid"){
							$datagrid.datagrid('showColumn', "pcustomerid");
						}else if(col=="salesuser"){
							$datagrid.datagrid('showColumn', "salesuser");
							//$datagrid.datagrid('showColumn', "salesarea");
							//$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="salesarea"){
							$datagrid.datagrid('showColumn', "salesarea");
						}else if(col=="salesdept"){
							$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="goodsid"){
							$datagrid.datagrid('showColumn', "goodsid");
							$datagrid.datagrid('showColumn', "goodsname");
							$datagrid.datagrid('showColumn', "barcode");
							$datagrid.datagrid('showColumn', "brandid");
                            $datagrid.datagrid('showColumn', "unitnum");
                            $datagrid.datagrid('showColumn', "goodssort");
                            $datagrid.datagrid('showColumn', "goodssortname");
						}else if(col=="brandid"){
							$datagrid.datagrid('showColumn', "brandid");
							//$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="goodssort"){
                            $datagrid.datagrid('showColumn', "goodssort");
                            $datagrid.datagrid('showColumn', "goodssortname");
                        }else if(col=="branduser"){
							$datagrid.datagrid('showColumn', "branduser");
							//$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="branddept"){
							$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="supplieruser"){
                            $datagrid.datagrid('showColumn', "supplieruser");
                        }else if(col=="supplierid"){
							$datagrid.datagrid('showColumn', "supplierid");
							$datagrid.datagrid('showColumn', "suppliername");
						}
					}
    		}
    		
    		function baseWithdrawn_retColsname(){
	  			var colname = "";
	  			var cols = $("#report-query-groupcols").val();
	  			if(cols == ""){
	  				cols = "goodsid";
	  			}
	  			var colarr = cols.split(",");
	  			if(colarr[0]=="pcustomerid"){
					colname = "pcustomername";
				}else if(colarr[0]=='customerid'){
					colname = "customername";
				}else if(colarr[0]=="salesuser"){
					colname = "salesusername";
				}else if(colarr[0]=="salesarea"){
					colname = "salesareaname";
				}else if(colarr[0]=="deptid"){
					colname = "deptname";
				}else if(colarr[0]=="goodsid"){
					colname = "goodsname";
				}else if(colarr[0]=="brandid"){
					colname = "brandname";
				}else if(colarr[0]=="branduser"){
					colname = "brandusername";
				}else if(colarr[0]=="branddept"){
					colname = "branddeptname";
				}else if(colarr[0]=="supplieruser"){
                    colname = "supplierusername";
                }else if(colarr[0]=="supplierid"){
					colname = "suppliername";
				}else if(colarr[0]=="goodssort"){
                    colname = "goodssortname";
                }
				return colname;
	  		}
    	</script>
  </body>
</html>
