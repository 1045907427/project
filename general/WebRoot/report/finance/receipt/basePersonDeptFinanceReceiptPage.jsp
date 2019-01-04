<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>人员部门资金应收情况表</title>
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
    	<table id="report-datagrid-basePersonDeptSalesReceipt"></table>
    	<div id="report-toolbar-basePersonDeptSalesReceipt" class="buttonBG">
            <a href="javaScript:void(0);" id="report-advancedQueay-basePersonDeptSalesReceiptPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="查询">查询</a>
            <security:authorize url="/report/finance/basePersonDeptSalesReceiptExport.do">
                <a href="javaScript:void(0);" id="report-buttons-basePersonDeptSalesReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
        </div>
        <div style="display: none">
            <div id="report-dialog-basePersonDeptSalesReceiptPage">
                <form action="" id="report-query-form-basePersonDeptSalesReceipt" method="post">
                    <table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
                        <tr>
                            <td width="70px">业务日期:</td>
                            <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                        </tr>
                        <tr>
                            <td>客户名称:</td>
                            <td><input type="text" id="report-query-customerid" name="customerid"/></td>
                        </tr>
                        <tr>
                            <td>总店名称:</td>
                            <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                        </tr>
                        <tr>
                            <td>品牌:</td>
                            <td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
                        </tr>
                        <tr>
                            <td>品牌部门:</td>
                            <td><input type="text" name="branddept" id="report-branddept-advancedQuery"/></td>
                        </tr>
                        <tr>
                            <td>品牌业务员:</td>
                            <td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
                        </tr>
                        <tr>
                            <td>商品:</td>
                            <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery" style="width: 210px;"/></td>
                        </tr>
                        <tr>
                            <td>销售区域:</td>
                            <td><input type="text" name="salesarea" id="report-salesarea-advancedQuery" style="width: 210px;"/></td>
                        </tr>
                        <tr>
                            <td>供应商:</td>
                            <td><input type="text" name="supplierid" id="report-supplierid-advancedQuery" style="width: 210px;"/></td>
                        </tr>
                        <tr>
	    					<td>销售部门:</td>
	    					<td><input type="text" name="salesdept" id="report-salesdept-advancedQuery"/></td>
	    				</tr>
                        <tr>
	    					<td>客户业务员:</td>
		  					<td><input type="text" name="salesuser" id="report-salesuser-advancedQuery"/></td>
	    				</tr>
                        <tr>
                            <td>人员部门:</td>
                            <td><input type="text" name="branduserdept" id="report-branduserdept-advancedQuery"/></td>
                        </tr>
                        <tr>
                            <td>小计列：</td>
                            <td>
                                <div style="float: left">
                                    <input type="checkbox" class="groupcols checkbox1" value="customerid" id="customerid"/>
                                    <label class="divtext" for="customerid">客户</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="pcustomerid" id="pcustomerid"/>
                                    <label class="divtext" for="pcustomerid">总店</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesuser" id="salesuser"/>
                                    <label class="divtext" for="salesuser">客户业务员</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesarea" id="salesarea"/>
                                    <label class="divtext" for="salesarea">销售区域</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesdept" id="salesdept"/>
                                    <label class="divtext" for="salesdept">销售部门</label>
                                </div>
                                <div style="float: left">
                                    <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                                    <label class="divtext" for="goodsid">商品</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="branddept" id="branddept"/>
                                    <label class="divtext" for="branddept">品牌部门</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="branduser" id="branduser"/>
                                    <label class="divtext" for="branduser">品牌业务员</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                                    <label class="divtext" for="brandid">品牌</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                                    <label class="divtext" for="supplierid">供应商</label>
                                </div>
                                <div style="float: left">
                                    <input type="checkbox" class="groupcols checkbox1" value="branduserdept" id="branduserdept"/>
                                    <label class="divtext" for="branduserdept">人员部门</label>
                                    <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                                    <input id="report-type-basePersonDeptSalesReceipt" type="hidden" name="type" value="branduserdept"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
    	</div>
    	<script type="text/javascript">
            var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-basePersonDeptSalesReceipt").serializeJSON();
    		$(function(){
                //全局导出
                $("#report-buttons-basePersonDeptSalesReceiptPage").click(function(){
                    var queryJSON = $("#report-query-form-basePersonDeptSalesReceipt").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-basePersonDeptSalesReceipt").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceReceiptData.do";
                    exportByAnalyse(queryParam,"人员部门资金应收情况表","report-datagrid-basePersonDeptSalesReceipt",url);
                });

    			$(".groupcols").click(function(){
    				var cols = "";
                    $("#report-query-groupcols").val(cols);
    				$(".groupcols").each(function(){
    					if($(this).attr("checked")){
    						if(cols==""){
    							cols = $(this).val();
    						}else{
    							cols += ","+$(this).val();
    						}
    						$("#report-query-groupcols").val(cols);
    					}
					});
    			});
    			var tableColumnListJson = $("#report-datagrid-basePersonDeptSalesReceipt").createGridColumnLoad({
					frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
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
                        {field:'branduserdept',title:'人员部门',sortable:true,width:90,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.branduserdeptname;
                            }
                        },
                        {field:'goodsid',title:'商品编码',sortable:true,width:60},
                        {field:'goodsname',title:'商品名称',sortable:true,width:130},
                        {field:'barcode',title:'条形码',sortable:true,width:90},
                        {field:'brandid',title:'品牌名称',sortable:true,width:80,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.brandname;
                            }
                        },
                        {field:'supplierid',title:'供应商',sortable:true,width:120,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.suppliername;
                            }
                        },
                        {field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                    ]]
				});
    			$("#report-datagrid-basePersonDeptSalesReceipt").datagrid({ 
    				authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
                    checkOnSelect:true,
                    selectOnCheck:true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
					toolbar:'#report-toolbar-basePersonDeptSalesReceipt',
                    url:'report/finance/showBaseFinanceReceiptData.do',
                    queryParams:initQueryJSON,
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
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:225,
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
		   			width:225,
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
		   			width:225,
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranddept-advancedQuery").val("");
		   			}
		   		});
		   		//品牌业务员
		   		$("#report-branduser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		   			singleSelect:false,
		   			width:225,
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranduser-advancedQuery").val("");
		   			}
		   		});
                //供应商
                $("#report-supplierid-advancedQuery").widget({
                    referwid:'RL_T_BASE_BUY_SUPPLIER',
                    singleSelect:false,
                    width:225,
                    onlyLeafCheck:true
                });
                //客户业务员
                $("#report-salesuser-advancedQuery").widget({
		  			referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		   			singleSelect:false,
		   			width:225,
		   			onlyLeafCheck:false
		  		});
                //销售部门
                $("#report-salesdept-advancedQuery").widget({
	    			name:'t_sales_order',
					col:'salesdept',
	    			width:225,
					singleSelect:false,
					onlyLeafCheck:true
	    		});
                //人员部门
                $("#report-branduserdept-advancedQuery").widget({
                    referwid:'RL_T_BRANDUSER_BELONG_DEPT',
                    width:225,
                    singleSelect:true,
                    onlyLeafCheck:false
                });
                
				//回车事件
				controlQueryAndResetByKey("report-queay-basePersonDeptSalesReceipt","report-reload-basePersonDeptSalesReceipt");

                //高级查询
                $("#report-advancedQueay-basePersonDeptSalesReceiptPage").click(function(){
                    $("#report-dialog-basePersonDeptSalesReceiptPage").dialog({
                        maximizable:true,
                        resizable:true,
                        title: '资金应收款情况表查询',
                        top:30,
                        width: 440,
                        height: 450,
                        closed: false,
                        cache: false,
                        modal: true,
                        buttons:[
                            {
                                text:'确定',
                                handler:function(){
                                    setColumn();
                                    //把form表单的name序列化成JSON对象
                                    var queryJSON = $("#report-query-form-basePersonDeptSalesReceipt").serializeJSON();
                                    $("#report-datagrid-basePersonDeptSalesReceipt").datagrid("load",queryJSON);
                                    $("#report-dialog-basePersonDeptSalesReceiptPage").dialog('close');
                                }
                            },
                            {
                                text:'重置',
                                handler:function(){
                                    $("#report-query-form-basePersonDeptSalesReceipt").form("reset");
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
                                    $("#report-supplierid-advancedQuery").widget('clear');
                                    $("#report-salesdept-advancedQuery").widget("clear");
                					$("#report-salesuser-advancedQuery").widget("clear");
                                    $("#report-branduserdept-advancedQuery").widget("clear");
                                    $("#report-query-form-basePersonDeptSalesReceipt").form("reset");
                                    $('#report-datagrid-basePersonDeptSalesReceipt').datagrid('loadData',[]);
                                    $('#report-datagrid-basePersonDeptSalesReceipt').datagrid('reloadFooter',[]);
                                    setColumn();
                                }
                            }
                        ],
                        onClose:function(){
                        }
                    });
                });
    		});
    		var $datagrid = $("#report-datagrid-basePersonDeptSalesReceipt");
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
                    $datagrid.datagrid('hideColumn', "supplierid");
                    $datagrid.datagrid('hideColumn', "branduserdept");
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
				}
				
					var colarr = cols.split(",");
					for(var i=0;i<colarr.length;i++){
						var col = colarr[i];
						if(col=='customerid'){
							$datagrid.datagrid('showColumn', "customerid");
							$datagrid.datagrid('showColumn', "customername");
							$datagrid.datagrid('showColumn', "pcustomerid");
							$datagrid.datagrid('showColumn', "salesuser");
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
						}else if(col=="brandid"){
							$datagrid.datagrid('showColumn', "brandid");
						}else if(col=="branduser"){
							$datagrid.datagrid('showColumn', "branduser");
						}else if(col=="branddept"){
							$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="supplierid"){
                            $datagrid.datagrid('showColumn', "supplierid");
                        }else if(col=="branduserdept"){
                            $datagrid.datagrid('showColumn', "branduserdept");
                        }
					}
    		}

            function base_retColsname(){
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
                }else if(colarr[0]=="branduserdept"){
                    colname = "branduserdeptname";
                }
                return colname;
            }

            function countTotalAmount(){
                var col = base_retColsname();
                var rows =  $("#report-datagrid-basePersonDeptSalesReceipt").datagrid('getChecked');
                var allunwithdrawnamount = 0;
                var unauditamount = 0;
                var auditamount = 0;
                var rejectamount = 0;
                var allpushbalanceamount = 0;
                for(var i=0;i<rows.length;i++){
                    allunwithdrawnamount = Number(allunwithdrawnamount)+Number(rows[i].allunwithdrawnamount == undefined ? 0 : rows[i].allunwithdrawnamount);
                    unauditamount = Number(unauditamount)+Number(rows[i].unauditamount == undefined ? 0 : rows[i].unauditamount);
                    auditamount = Number(auditamount)+Number(rows[i].auditamount == undefined ? 0 : rows[i].auditamount);
                    rejectamount = Number(rejectamount)+Number(rows[i].rejectamount == undefined ? 0 : rows[i].rejectamount);
                    allpushbalanceamount = Number(allpushbalanceamount)+Number(rows[i].allpushbalanceamount == undefined ? 0 : rows[i].allpushbalanceamount);
                }
                var obj={allunwithdrawnamount:allunwithdrawnamount,unauditamount:unauditamount,auditamount:auditamount,rejectamount:rejectamount,allpushbalanceamount:allpushbalanceamount};
                if(col != ""){
                    obj[col] = '选中合计';
                }else{
                    obj['goodsname'] = '选中合计';
                }
                var foot=[];
                foot.push(obj);
                if(null!=SR_footerobject){
                    foot.push(SR_footerobject);
                }
                $("#report-datagrid-basePersonDeptSalesReceipt").datagrid("reloadFooter",foot);
            }
    	</script>
  </body>
</html>
