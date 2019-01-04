<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>缺货商品统计表</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
	<table id="report-datagrid-goodsOutReportPage"></table>
	<div id="report-tool-goodsOutReportPage" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/exception/goodsOutReportExport.do">
                <a href="javaScript:void(0);" id="report-export-goodsOutReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
            <security:authorize url="/sales/addOrderByGoodsout.do">
                <a href="javaScript:void(0);" id="report-addOrder-goodsOutReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="补单">补单</a>
            </security:authorize>
        </div>
		<form action="" id="report-form-goodsOutReportPage" methos="post">
			<table class="querytable">
				<tr>
					<td>订单日期：</td>
					<td><input type="text" name="begindate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date}" /> 到 <input type="text" name="enddate" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
					<td>客户名称：</td>
					<td><input name="customerid" id="report-customer-goodsOutReportPage" style="width:225px;"/></td>
					<td>状态：</td>
					<td>
						<select name="issupply" style="width: 165px;">
							<option value="0">未补单</option>
							<option value="1">已补单</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>商品名称：</td>
					<td><input name="goodsid" id="report-goods-goodsOutReportPage" style="width: 225px;"/></td>
					<td>品牌名称：</td>
					<td><input name="brandid" id="report-brand-goodsOutReportPage" /></td>
                    <td>总店：</td>
                    <td><input name="pid" id="report-pid-goodsOutReportPage" /></td>
				</tr>
                <tr>
                    <td>供 应 商：</td>
                    <td><input name="supplierid" id="report-supplierid-goodsOutReportPage" /></td>
                    <td>客户业务员：</td>
                    <td><input name="salesuser" id="report-salesuserid-goodsOutReportPage" /></td>
					<td>销售区域:</td>
					<td><input type="text" name="salesarea" id="report-salesarea-goodsOutReportPage"/></td>
                </tr>
				<tr>
					<td>销售部门:</td>
					<td><input type="text" name="salesdept" id="report-salesdept-goodsOutReportPage"/></td>
					<td colspan="2"></td>
					<td colspan="2">
						<a href="javaScript:void(0);" id="report-queay-goodsOutReportPage" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-goodsOutReportPage" class="button-qr">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
        var SR_footerobject = {orderid:"合计"};
		var tableColumnListJson = $("#report-datagrid-goodsOutReportPage").createGridColumnLoad({
			frozenCol : [[]],
			commonCol : [[
				{field:'ck',checkbox:true},
			    {field:'orderid',title:'订单编号',width:110},
                {field:'sourceid',title:'客户单号',width:110,sortable:true},
				{field:'businessdate',title:'订单日期',width:70,sortable:true},
				{field:'customerid',title:'客户编码',width:60,sortable:true},
				{field:'customername',title:'客户名称',width:140},
				{field:'goodsid',title:'商品编码',width:70,sortable:true},
				{field:'goodsname',title:'商品名称',width:140},
				{field:'brandid',title:'品牌名称',width:80,sortable:true,
					formatter:function(value,rowData,rowIndex){
						if(rowData.goodsid!=null){
							return rowData.brandname;
						}
				    	
				    }
				},
				{field:'barcode',title:'条形码',width:90},
				{field:'fixnum',title:'订购数量',width:60,sortable:true,
					formatter:function(value,rowData,rowIndex){
				    	return formatterBigNumNoLen(value);
				    }
				},
				{field:'fixauxnumdetail',title:'订购辅数量',width:80},
                {field:'fixamount',title:'订购金额',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
				{field:'sendnum',title:'已发数量',width:60,sortable:true,
					formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
				    }
				},
				{field:'sendauxnumdetail',title:'已发辅数量',width:80},
                {field:'sendamount',title:'已发金额',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
				{field:'outnum',title:'缺货数量',width:60,sortable:true,
					formatter:function(value,rowData,rowIndex){
				    	return formatterBigNumNoLen(value);
				    }
				},
				{field:'outauxnumdetail',title:'缺货辅数量',width:80},
                {field:'outamount',title:'缺货金额',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
				{field:'issupply',title:'状态',width:70,
					formatter:function(value,rowData,rowIndex){
				    	if(value=="0"){
				    		return "未补单";
				    	}else if(value=="1"){
				    		return "已补单";
				    	}
				    }
				}
	        ]]
		});
		$(function(){
			$("#report-datagrid-goodsOutReportPage").datagrid({ 
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
				idField:'id',  
				view:groupview,
                groupField:'businessdate',
                groupFormatter:function(value,rows){
                    return '<input type="checkbox" id="groupDate'+value+'" class="groupDate"  onclick="groupDate(\''+value+'\')" style="width:15px;height:18px;margin-left: 7px;" value="'+value+'"/>'+value + ' - ' + rows.length + ' 条';
                },
				toolbar:'#report-tool-goodsOutReportPage',
                onLoadSuccess:function(){
                    var footerrows = $(this).datagrid('getFooterRows');
                    if(null!=footerrows && footerrows.length>0){
                        SR_footerobject = footerrows[0];
                        countTotal();
                    }
                },
                onCheckAll:function(){
                    countTotal();
                },
                onUncheckAll:function(){
                    countTotal();
                },
                onCheck:function(){
                    countTotal();
                },
                onUncheck:function(){
                    countTotal();
                }
			}).datagrid("columnMoving");
			
			$("#report-customer-goodsOutReportPage").customerWidget({});
			$("#report-goods-goodsOutReportPage").goodsWidget({});
			$("#report-brand-goodsOutReportPage").widget({
			    referwid:"RL_T_BASE_GOODS_BRAND",
				width:225
			});
            $("#report-pid-goodsOutReportPage").widget({//总店
                referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
                singleSelect:true,
                width:165
            });
            $("#report-salesuserid-goodsOutReportPage").widget({//客户业务员
                referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
                singleSelect:true,
                width:225
            });
            $("#report-supplierid-goodsOutReportPage").widget({//供应商
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                singleSelect:true,
                width:225
            });
            $("#report-salesdept-goodsOutReportPage").widget({
                name:'t_sales_order',
                col:'salesdept',
                width:225,
                singleSelect:true,
                onlyLeafCheck:true
            });
            $("#report-salesarea-goodsOutReportPage").widget({
                referwid:'RT_T_BASE_SALES_AREA',
                singleSelect:true,
                width:165,
                onlyLeafCheck:true
            });

            $("#report-queay-goodsOutReportPage").click(function(){
				var queryJSON = $("#report-form-goodsOutReportPage").serializeJSON();
		      	$("#report-datagrid-goodsOutReportPage").datagrid({
		      		url:'report/exception/getGoodsOutReport.do',
		      		pageNumber:1,
   					queryParams:queryJSON
		      	}).datagrid("columnMoving");
			});
			$("#report-reload-goodsOutReportPage").click(function(){
                $("#report-pid-goodsOutReportPage").widget("clear");
                $("#report-salesuserid-goodsOutReportPage").widget("clear");
                $("#report-supplierid-goodsOutReportPage").widget("clear");
				$("#report-customer-goodsOutReportPage").customerWidget("clear");
				$("#report-goods-goodsOutReportPage").goodsWidget("clear");
				$("#report-brand-goodsOutReportPage").widget("clear");
				$("#report-form-goodsOutReportPage").form("reset");
				$("#report-datagrid-goodsOutReportPage").datagrid("uncheckAll");
				$("#report-datagrid-goodsOutReportPage").datagrid('loadData', []);
			});
			$("#report-export-goodsOutReportPage").Excel('export',{
				queryForm: "#report-form-goodsOutReportPage",
				type:'exportUserdefined',
				name:'缺货商品统计表',
				url:'report/exception/exportGoodsOutReport.do'
			});
			$("#report-addOrder-goodsOutReportPage").click(function(){
				var rows = $("#report-datagrid-goodsOutReportPage").datagrid("getChecked");
				if(rows.length==0){
					$.messager.alert("提醒","请选择明细");
					return false;
				}
				var ids = "";
				for(var i=0;i<rows.length;i++){
					if(rows[i].issupply=="0"){
						if(ids==""){
							ids = rows[i].id;
						}else{
							ids += ","+rows[i].id;
						}
					}
				}
				if(ids !=""){
					$.messager.confirm("提醒","是否补单？",function(r){
						if(r){
							loading("提交中..");
							$.ajax({
								url:'sales/addOrderByGoodsout.do',
								dataType:'json',
								type:'post',
								data:'ids='+ ids,
								success:function(json){
									loaded();
									if(json.flag == true){
										$.messager.alert("提醒","补单成功，生成订单："+json.billid);
										$("#report-datagrid-goodsOutReportPage").datagrid("reload");
									}
									else{
										$.messager.alert("提醒","补单失败");
									}
								},
								error:function(){
									loaded();
									$.messager.alert("错误","补单失败");
								}
							});
						}
					});
				}else{
					$.messager.alert("提醒","请选择未补单商品");
				}
			});
		});	
		function groupDate(date){
			if($("#groupDate"+date).attr("checked")){
				var rows = $("#report-datagrid-goodsOutReportPage").datagrid("getRows");
				for(var i=0;i<rows.length;i++){
					var object = rows[i];
					if(object.businessdate==date){
						$("#report-datagrid-goodsOutReportPage").datagrid("checkRow",i);
					}
				}
			}else{
				var rows = $("#report-datagrid-goodsOutReportPage").datagrid("getRows");
				for(var i=0;i<rows.length;i++){
					var object = rows[i];
					if(object.businessdate==date){
						$("#report-datagrid-goodsOutReportPage").datagrid("uncheckRow",i);
					}
				}
			}
		}
        function countTotal(){ //计算合计
            var checkrows =  $("#report-datagrid-goodsOutReportPage").datagrid('getChecked');
            if(checkrows.length>0){
                var fixnum = 0;
                var fixamount = 0;
                var fixnumint = 0;
                var fixauxnum = 0;
                var sendnum = 0;
                var sendamount = 0;
                var sendnumint = 0;
                var sendauxnum = 0;
                var outnum = 0;
                var outamount = 0;
                var outnumint = 0;
                var outauxnum = 0;
                for(var i=0; i<checkrows.length; i++){
                    fixnum += Number(checkrows[i].fixnum == undefined ? 0 : checkrows[i].fixnum);
                    fixamount += Number(checkrows[i].fixamount == undefined ? 0 : checkrows[i].fixamount);
                    fixnumint += Number(checkrows[i].fixnumint == undefined ? 0 : checkrows[i].fixnumint);
                    fixauxnum += Number(checkrows[i].fixauxnum == undefined ? 0 : checkrows[i].fixauxnum);

                    sendnum += Number(checkrows[i].sendnum == undefined ? 0 : checkrows[i].sendnum);
                    sendamount += Number(checkrows[i].sendamount == undefined ? 0 : checkrows[i].sendamount);
                    sendnumint += Number(checkrows[i].sendnumint == undefined ? 0 : checkrows[i].sendnumint);
                    sendauxnum += Number(checkrows[i].sendauxnum == undefined ? 0 : checkrows[i].sendauxnum);

                    outnum += Number(checkrows[i].outnum == undefined ? 0 : checkrows[i].outnum);
                    outamount += Number(checkrows[i].outamount == undefined ? 0 : checkrows[i].outamount);
                    outnumint += Number(checkrows[i].outnumint == undefined ? 0 : checkrows[i].outnumint);
                    outauxnum += Number(checkrows[i].outauxnum == undefined ? 0 : checkrows[i].outauxnum);
                }

                var fixauxnumdetail = formatterBigNumNoLen1(fixauxnum) +"箱" + formatterBigNumNoLen1(fixnumint);
                var sendauxnumdetail = formatterBigNumNoLen1(sendauxnum) +"箱" + formatterBigNumNoLen1(sendnumint);
                var outauxnumdetail = formatterBigNumNoLen1(outauxnum) +"箱" + formatterBigNumNoLen1(outnumint);
                var foot = [{orderid:'选中合计',fixnum:fixnum,fixamount:fixamount,fixauxnumdetail:fixauxnumdetail,
                    sendnum:sendnum,sendamount:sendamount,sendauxnumdetail:sendauxnumdetail,
                    outnum:outnum,outamount:outamount,outauxnumdetail:outauxnumdetail}];
                foot.push(SR_footerobject);
                $("#report-datagrid-goodsOutReportPage").datagrid('reloadFooter',foot);
            }else{
                var foot = [{orderid:'选中合计'}];
                foot.push(SR_footerobject);
                $("#report-datagrid-goodsOutReportPage").datagrid('reloadFooter',foot);
            }
        }
	</script>
  </body>
</html>
