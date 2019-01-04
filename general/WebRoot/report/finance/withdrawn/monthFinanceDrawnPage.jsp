<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金回笼按月统计情况表</title>
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
    	<table id="report-datagrid-monthFinanceDrawn"></table>
    	<div id="report-toolbar-monthFinanceDrawn" style="padding: 0px">
			<div class="buttonBG">
				<security:authorize url="/report/finance/exportMonthFinanceDrawnData.do">
					<a href="javaScript:void(0);" id="report-buttons-monthFinanceDrawnPage" class="easyui-linkbutton"
					   iconCls="button-export" plain="true" title="导出">导出</a>
				</security:authorize>
				<security:authorize url="/report/finance/printMonthFinanceDrawnData.do">
					<a href="javaScript:void(0);" id="report-print-monthFinanceDrawnPage" class="easyui-linkbutton"
					   iconCls="button-print" plain="true" title="打印">打印</a>
				</security:authorize>
			</div>
			<form action="" id="report-query-form-monthFinanceDrawn" method="post">
    		<table class="querytable">
    			<tr>
					<td>年份:</td>
	    			<td><input id="journalsheet-budgetListPage-month" type="text" required='required' name="year" style="width:225px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy'})" value="${current}"/></td>    				<td>客户名称:</td>
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
	  				<td>客户业务员:</td>
	  				<td><input type="text" name="salesuser" id="report-salesuser-advancedQuery" style="width: 210px;"/>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-monthFinanceDrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-monthFinanceDrawn" class="button-qr">重置</a>
    				</td>
    			</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="6">
    					<input type="checkbox" class="groupcols checkbox1" value="customerid" id="report-customerid-advancedQuery"/>
                        <label for="report-customerid-advancedQuery" class="divtext">客户</label>
    					<input type="checkbox" class="groupcols checkbox1" value="pcustomerid" id="report-pcustomerid-advancedQuery"/>
                        <label for="report-pcustomerid-advancedQuery" class="divtext">总店</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesuser" id="report-salesuser-advancedQuery"/>
                        <label for="report-salesuser-advancedQuery" class="divtext">客户业务员</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesarea" id="report-salesarea1-advancedQuery"/>
                        <label for="report-salesarea1-advancedQuery" class="divtext">销售区域</label>
    					<input type="checkbox" class="groupcols checkbox1" value="salesdept" id="report-salesdept-advancedQuery"/>
                        <label for="report-salesdept-advancedQuery" class="divtext">销售部门</label>
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
    		var initQueryJSON = $("#report-query-form-monthFinanceDrawn").serializeJSON();

    		$(function(){
                $("#report-print-monthFinanceDrawnPage").click(function () {
                    var msg = "";
                    printByAnalyse("资金回笼按月统计情况表", "report-datagrid-monthFinanceDrawn", "report/finance/printMonthsFinanceDrawnData.do", msg);
                })

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

                var tableColumnJson = $("#report-datagrid-monthFinanceDrawn").createGridColumnLoad({
                    frozenCol : [[
                        {field:'idok',checkbox:true,isShow:true}
                    ]],
                    commonCol : [
                        [
                            {field:'customerid',title:'客户编号',rowspan:2,sortable:true,width:60},
                            {field:'customername',title:'客户名称',rowspan:2,width:130},
                            {field:'pcustomerid',title:'总店名称',rowspan:2,sortable:true,width:60,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.pcustomername;
                                }
                            },
                            {field:'salesuser',title:'客户业务员',rowspan:2,sortable:true,width:80,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.salesusername;
                                }
                            },
                            {field:'salesarea',title:'销售区域',rowspan:2,sortable:true,width:80,hidden:true,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.salesareaname;
                                }
                            },
                            {field:'salesdept',title:'销售部门',rowspan:2,sortable:true,width:80,hidden:true,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.salesdeptname;
                                }
                            },
                            {field:'branduser',title:'品牌业务员',rowspan:2,sortable:true,width:80,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.brandusername;
                                }
                            },
                            {field:'branddept',title:'品牌部门',rowspan:2,sortable:true,width:80,hidden:true,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.branddeptname;
                                }
                            },
                            {field:'supplieruser',title:'厂家业务员',rowspan:2,sortable:true,width:80,hidden:true,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.supplierusername;
                                }
                            },
                            {field:'goodsid',title:'商品编码',rowspan:2,sortable:true,width:60},
                            {field:'goodsname',title:'商品名称',rowspan:2,sortable:true,width:130},
                            {field:'barcode',title:'条形码',rowspan:2,sortable:true,width:90},
                            {field:'brandid',title:'品牌名称',rowspan:2,sortable:true,width:80,
                                formatter:function(value,rowData,rowIndex){
                                    return rowData.brandname;
                                }
                            },
                            {title:'1月份',colspan:2},{title:'2月份',colspan:2},{title:'3月份',colspan:2},{title:'4月份',colspan:2},{title:'5月份',colspan:2},{title:'6月份',colspan:2},{title:'7月份',colspan:2},{title:'8月份',colspan:2},{title:'9月份',colspan:2},{title:'10月份',colspan:2},{title:'11月份',colspan:2},{title:'12月份',colspan:2}

                        ],
                        [
                            {field:'withdrawnamount01',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate01',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount02',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate02',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount03',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate03',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount04',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate04',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount05',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate05',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount06',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate06',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount07',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate07',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount08',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate08',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount09',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate09',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount10',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate10',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount11',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate11',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            },
                            {field:'withdrawnamount12',title:'回笼金额',align:'right',width:100,isShow:true,sortable:true,
                                formatter:function(value,rowData,rowIndex){
                                    return formatterMoney(value);
                                }
                            },
                            {field:'writeoffrate12',title:'回笼毛利率',align:'right',width:80,isShow:true,
                                formatter:function(value,rowData,rowIndex){
                                    if(null != value && "" != value){
                                        return formatterMoney(value)+"%";
                                    }
                                }
                            }
                        ]
                    ]
                });
    			
    			$("#report-datagrid-monthFinanceDrawn").datagrid({
                    authority:tableColumnJson,
                    frozenColumns: tableColumnJson.frozen,
                    columns:tableColumnJson.common,
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
					toolbar:'#report-toolbar-monthFinanceDrawn',
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							MonthFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
						}
			 		},
					onCheckAll:function(){
						MonthFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onUncheckAll:function(){
						MonthFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onCheck:function(){
						MonthFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onUncheck:function(){
						MonthFinanceWithDrawnCountTotalAmount(baseWithdrawn_retColsname(),this);
					},
					onHeaderContextMenu : customHeaderContextMenu
				}).datagrid("columnMoving");
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
		   		
		   		//客户业务员
		   		$("#report-salesuser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		   			singleSelect:false,
		   			width:120,
		   			onlyLeafCheck:false,
		   			onSelect:function(data){
		   				$("#report-hdbranduser-advancedQuery").val("");
		   			}
		   		});
		   		
		   		
				//回车事件
				controlQueryAndResetByKey("report-queay-monthFinanceDrawn","report-reload-monthFinanceDrawn");
				
				//导出
				$("#report-buttons-monthFinanceDrawnPage").Excel('export',{
					queryForm: "#report-query-form-monthFinanceDrawn", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'资金回笼按月汇总情况表',
			 		url:'report/finance/exportMonthFinanceDrawnData.do'
				});
				
				//查询
				$("#report-queay-monthFinanceDrawn").click(function(){
					setColumn();
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-monthFinanceDrawn").serializeJSON();
		      		$("#report-datagrid-monthFinanceDrawn").datagrid({
		      			url:'report/finance/showMonthsFinanceDrawnData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-monthFinanceDrawn").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-brandid-advancedQuery").widget("clear");
					$("#report-branddept-advancedQuery").widget("clear");
					$("#report-branduser-advancedQuery").widget("clear");
					$("#report-goodsid-advancedQuery").widget("clear");
					$("#report-salesarea-advancedQuery").widget("clear");
					$("#report-query-form-monthFinanceDrawn").form("reset");
					$('#report-datagrid-monthFinanceDrawn').datagrid('loadData',{total:0,rows:[]});
					$('#report-datagrid-monthFinanceDrawn').datagrid('reloadFooter',[]);
				});
				//导出
    		});
    		var $datagrid = $("#report-datagrid-monthFinanceDrawn");
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
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
                    $datagrid.datagrid('hideColumn', "supplieruser");
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
//                     $datagrid.datagrid('showColumn', "unitnum");
					$datagrid.datagrid('showColumn', "branduser");
				}
				
					var colarr = cols.split(",");
					for(var i=0;i<colarr.length;i++){
						var col = colarr[i];
						if(col=='customerid'){
							$datagrid.datagrid('showColumn', "customerid");
							$datagrid.datagrid('showColumn', "customername");
							$datagrid.datagrid('showColumn', "pcustomerid");
//							$datagrid.datagrid('showColumn', "salesuser");
						}else if(col=="pcustomerid"){
							$datagrid.datagrid('showColumn', "pcustomerid");
						}else if(col=="salesuser"){
							$datagrid.datagrid('showColumn', "salesuser");
						}else if(col=="salesarea"){
							$datagrid.datagrid('showColumn', "salesarea");
						}else if(col=="salesdept"){
							$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="goodsid"){
							$datagrid.datagrid('showColumn', "goodsid");
							$datagrid.datagrid('showColumn', "goodsname");
							$datagrid.datagrid('showColumn', "barcode");
							$datagrid.datagrid('showColumn', "brandid");
//                             $datagrid.datagrid('showColumn', "unitnum");
						}else if(col=="brandid"){
							$datagrid.datagrid('showColumn', "brandid");
						}else if(col=="branduser"){
							$datagrid.datagrid('showColumn', "branduser");
						}else if(col=="branddept"){
							$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="supplieruser"){
                            $datagrid.datagrid('showColumn', "supplieruser");
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
                }
				return colname;
	  		}
	  		
	  	
	  	
	  	function MonthFinanceWithDrawnCountTotalAmount(col,datagrid){
	  			var rows =  $("#"+datagrid.id+"").datagrid("getChecked");
				var withdrawnamount01 = 0; 
	  			var withdrawnamount02 = 0; 
	  			var withdrawnamount03 = 0; 
	  			var withdrawnamount04 = 0; 
	  			var withdrawnamount05 = 0; 
	  			var withdrawnamount06 = 0; 
	  			var withdrawnamount07 = 0; 
	  			var withdrawnamount08 = 0;
	  			var withdrawnamount09 = 0; 
	  			var withdrawnamount10 = 0;
	  			var withdrawnamount11 = 0; 
	  			var withdrawnamount12 = 0; 
	  			for(var i=0;i<rows.length;i++){
	  				withdrawnamount01 = Number(withdrawnamount01)+Number(rows[i].withdrawnamount01 == undefined ? 0 : rows[i].withdrawnamount01);
	  				withdrawnamount02 = Number(withdrawnamount02)+Number(rows[i].withdrawnamount02 == undefined ? 0 : rows[i].withdrawnamount02);
	  				withdrawnamount03 = Number(withdrawnamount03)+Number(rows[i].withdrawnamount03 == undefined ? 0 : rows[i].withdrawnamount03);
	  				withdrawnamount04 = Number(withdrawnamount04)+Number(rows[i].withdrawnamount04 == undefined ? 0 : rows[i].withdrawnamount04);
	  				withdrawnamount05 = Number(withdrawnamount05)+Number(rows[i].withdrawnamount05 == undefined ? 0 : rows[i].withdrawnamount05);
	  				withdrawnamount06 = Number(withdrawnamount06)+Number(rows[i].withdrawnamount06 == undefined ? 0 : rows[i].withdrawnamount06);
	  				withdrawnamount07 = Number(withdrawnamount07)+Number(rows[i].withdrawnamount07 == undefined ? 0 : rows[i].withdrawnamount07);
	  				withdrawnamount08 = Number(withdrawnamount08)+Number(rows[i].withdrawnamount08 == undefined ? 0 : rows[i].withdrawnamount08);
	  				withdrawnamount09 = Number(withdrawnamount09)+Number(rows[i].withdrawnamount09 == undefined ? 0 : rows[i].withdrawnamount09);
	  				withdrawnamount10 = Number(withdrawnamount10)+Number(rows[i].withdrawnamount10 == undefined ? 0 : rows[i].withdrawnamount10);
	  				withdrawnamount11 = Number(withdrawnamount11)+Number(rows[i].withdrawnamount11 == undefined ? 0 : rows[i].withdrawnamount11);
	  				withdrawnamount12 = Number(withdrawnamount12)+Number(rows[i].withdrawnamount12 == undefined ? 0 : rows[i].withdrawnamount12);
	  			}
	  			var obj = {
	  				withdrawnamount01:withdrawnamount01,
	  				withdrawnamount02:withdrawnamount02,
	  				withdrawnamount03:withdrawnamount03,
	  				withdrawnamount04:withdrawnamount04,
	  				withdrawnamount05:withdrawnamount05,
	  				withdrawnamount06:withdrawnamount06,
	  				withdrawnamount07:withdrawnamount07,
	  				withdrawnamount08:withdrawnamount08,
	  				withdrawnamount09:withdrawnamount09,
	  				withdrawnamount10:withdrawnamount10,
	  				withdrawnamount11:withdrawnamount11,
	  				withdrawnamount12:withdrawnamount12
	  			}	  			
	  			var foot=[];
				foot.push(obj);
				if(null!=SR_footerobject){
   					foot.push(SR_footerobject);
				}
	  			$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
	  		}
	  	
	  	
	  	
	  	
	  	
	  		
	  		

	  		
	  		
	  		function customHeaderContextMenu(e, field) {
	    		var monthArr =	[{title:'1月份',colspan:2},{title:'2月份',colspan:2},{title:'3月份',colspan:2},{title:'4月份',colspan:2},{title:'5月份',colspan:2},{title:'6月份',colspan:2},{title:'7月份',colspan:2},{title:'8月份',colspan:2},{title:'9月份',colspan:2},{title:'10月份',colspan:2},{title:'11月份',colspan:2},{title:'12月份',colspan:2}]
	  			
	  			e.preventDefault();
	  			var grid = $(this);/* grid本身 */
	  			
	  		  	var gridoptions = $(this).datagrid('options');
	  		    
	  			var frozenColumnArray = this.frozenColumn;
	  			var columnArray = this.columnArray;

	  			
	  			var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	  			if (!headerContextMenu|true) {
	  				var fields = grid.datagrid('getColumnFields');
	  				var frozenfields = grid.datagrid('getColumnFields',true);
	  				var tmenu;
	  				if(Number(fields.length)+Number(frozenfields.length)>10){
	  					if($.browser.msie){
	  						tmenu = $('<div style="overflow-y: scroll;width:150px;height:300px;"></div>').appendTo('body');
	  					}else{
	  						tmenu = $('<div class="menuContext"></div>').appendTo('body');
	  					}
	  				}else{
	  					tmenu = $('<div style="width:120px;"></div>').appendTo('body');
	  				}
	  				
	  				var fcolumn = []; 
	  				if(frozenfields.length>0){
	  					var frozenHtml = '<div><span>冻结列</span><div style="width:100px;">';
	  					
	  					for ( var i = 0; i < frozenfields.length; i++) {
	  						var frozenfildOption = grid.datagrid('getColumnOption', frozenfields[i]);
	  						if(fields[i]){
	  							if(fields[i].indexOf("writeoffrate")>=0||fields[i].indexOf("withdrawnamount")>=0){
	 		  						continue;
		  						}
	  						}
	  						fcolumn.push(frozenfildOption);
	  						var title = frozenfildOption.title;
	  						if(!frozenfildOption.title){
	  							title = "复选框";
	  						}
	  						if (!frozenfildOption.hidden) {
	  							frozenHtml += '<div iconCls="icon-ok" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
	  						} else {
	  							frozenHtml += '<div iconCls="icon-empty" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
	  						}
	  					}
	  					frozenHtml +='</div></div>';
	  					$(frozenHtml).appendTo(tmenu);
	  					$('<div class="menu-sep"/>').html("").appendTo(tmenu);
	  				}
	  				var frozenArray = [];
	  				frozenArray.push(fcolumn)
	  				frozenColumnArray = this.frozenColumnArray = frozenArray;
	  				var column = [];
	  				for ( var i = 0; i < fields.length; i++) {
	  					if(fields[i]){
		  					if(fields[i].indexOf("writeoffrate")>=0||fields[i].indexOf("withdrawnamount")>=0){
	 		  					continue;
		  					}
  						}
	  					var fildOption = grid.datagrid('getColumnOption', fields[i]);
	  					var colHtml = "";
	  					var title = fildOption.title;
	  					if(!fildOption.title){
	  						title = "复选框";
	  					}
	  					if (!fildOption.hidden) {
	  						colHtml+='<div iconCls="icon-ok" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
	  						'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
	  					} else {
	  						colHtml+='<div iconCls="icon-empty" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
	  						'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
	  					}
	  					colHtml +='</div></div>';
	  					$(colHtml).appendTo(tmenu);
	  					column.push(fildOption);
	  				}
	  				var cArray=[];
	  				cArray.push(column)
	  				for(var i = 0;i<monthArr.length;i++){
	  					cArray[0].push(monthArr[i])
	  				}
	  				cArray.push(commonCol[1])
	  				columnArray = this.columnArray = cArray;
	  		        var gridname = grid.context.id ;
	  				headerContextMenu = this.headerContextMenu = tmenu.menu({
	  					onClick : function(item) {
	  						console.log(item.iconCls)
	  						var field = $(item.target).attr('field');
	  						if (item.iconCls == 'icon-ok') {
	  							if($(item.target).hasClass("frozen-Column")){
	  								for(var i=0;i<frozenColumnArray[0].length;i++){
	  									console.log(frozenColumnArray[0][i])
	  									if(frozenColumnArray[0][i].field==field){
	  										columnArray[0].push(frozenColumnArray[0][i]);
	  										frozenColumnArray[0].splice(i,1);
	  									}
	  								}
	  								
	  								grid.datagrid({
	  									frozenColumns:frozenColumnArray,
	  									columns:columnArray
	  								})
	  							}else{
	  								grid.datagrid('hideColumn', field);
	  								$(this).menu('setIcon', {
	  									target : item.target,
	  									iconCls : 'icon-empty'
	  								});
	  							}
	  						}else if(item.iconCls == 'icon-remove'){
	  							for(var i=0;i<columnArray[0].length;i++){
	  								if(columnArray[0][i].field==field){
	  									frozenColumnArray[0].push(columnArray[0][i]);
	  									columnArray[0].splice(i,1);
	  								}
	  							}
	  							for(var i = 0 ;i<frozenColumnArray[0].length;i++){
	  								if(frozenColumnArray[0][i].rowspan){
	  									delete frozenColumnArray[0][i].rowspan
	  								}
	  							}
	  							
	  							grid.datagrid({
	  								frozenColumns:frozenColumnArray,
	  								columns:columnArray
	  							})
	  						}else {
	  							grid.datagrid('showColumn', field);
	  							$(this).menu('setIcon', {
	  								target : item.target,
	  								iconCls : 'icon-ok'
	  							});
	  						}
	  					}
	  				});
	  			}
	  			headerContextMenu.menu('show', {
	  				left : e.pageX,
	  				top : e.pageY
	  			});
	  		};
	  		
    	</script>
  </body>
</html>
