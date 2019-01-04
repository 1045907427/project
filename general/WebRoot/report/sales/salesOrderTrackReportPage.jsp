<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>订单追踪明细表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    	<table id="report-datagrid-salesOrderTrack"></table>
    	<div id="report-toolbar-salesOrderTrack" style="padding: 0px">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="report-buttons-salesOrderTrackPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </div>
    		<form action="" id="report-query-form-salesOrderTrack" method="post">
    		<table  class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>订单编号:</td>
    				<td><input name="id" style="width: 150px;"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="report-query-brandid" name="brandid"/></td>
    			</tr>
    			<tr>
    				<td>商品名称:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 225px;"/></td>
    				<td>客户名称:</td>
	   				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 150px;"/></td>
                    <td>验收差异:</td>
                    <td><select name="checkstatus" style="width:130px;">
						<option selected="selected"></option>
						<option value="1" >否</option>
						<option value="2">是</option>
						</select>
		    	    </td>
    			</tr>
    			<tr>
    				<td>客户业务员:</td>
    				<td><input type="text" id="report-query-salesuser" name="salesuser" style="width: 225px;"/></td>
                    <td>总店名称:</td>
                    <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
					<td>销售内勤:</td>
					<td><input type="text" id="report-query-indooruserid" name="indooruserid"/></td>
    			</tr>
				<tr>
                    <td>客户单号:</td>
                    <td><input name="sourceid" style="width: 225px;"/></td>
					<td>制单人:</td>
					<td><input type="text" id="report-query-adduserid" name="adduserid" /></td>
					<td>商品分类:</td>
					<td><input type="text" id="report-query-goodssort" name="goodssort"/></td>
					<td colspan="2" >
						<a href="javaScript:void(0);" id="report-queay-salesOrderTrack" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesOrderTrack" class="button-qr">重置</a>
					</td>
				</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesOrderTrack").serializeJSON();
    		$(function(){
				var tableColumnListBrandJson = $("#report-datagrid-salesOrderTrack").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'businessdate',title:'业务日期',width:80,sortable:true},
						{field:'orderid',title:'单据编号',width:130,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(null!=value){
									return "<a href='javascript:showOrderView(\""+value+"\");' >"+value+"</a>";
								}

							}
						},
                        {field:'sourcetype',title:'来源类型',width:80,sortable:true,
                            formatter:function(value,rowData,index){
                                if(value=="0"){
                                    return "普通订单";
                                }else if(value=="1"){
                                    return "手机订单";
                                }else if(value=="2"){
                                    return "零售车销";
                                }
                            }
                        },
                        {field:'sourceid',title:'客户单号',width:130,sortable:true},
						{field:'customerid',title:'客户编号',width:60,sortable:true},
						{field:'customername',title:'客户名称',width:100},
						{field:'salesuser',title:'客户业务员',width:70,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.salesusername;
							}
						},
						{field:'indooruserid',title:'销售内勤',width:70,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.indoorusername;
							}
						},
						{field:'adduserid',title:'制单人',width:70,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.addusername;
							}
						},
						{field:'goodsid',title:'商品编码',width:60,sortable:true},
						{field:'goodsname',title:'商品名称',width:200,
							formatter:function(value,rowData,rowIndex){
								if(rowData.deliverytype=='1'){
									return "<font color='blue'>&nbsp;赠 </font>"+value;
								}else{
									return value;
								}
							}
						},
                        {field:'goodssort',title:'商品分类',width:90,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.goodssortname;
                            }
                        },
                        {field:'spell',title:'助记符',hidden:true,width:90},
						{field:'barcode',title:'条形码',sortable:true,width:90},
						{field:'brandname',title:'品牌名称',width:60},
						{field:'unitname',title:'单位',width:40},
						{field:'taxprice',title:'订单单价',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(null!=value){
									if(parseFloat(value)>parseFloat(rowData.initprice)){
										return "<font color='blue' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
									}
									else if(parseFloat(value)<parseFloat(rowData.initprice)){
										return "<font color='red' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
									}
									else{
										return formatterMoney(value);
									}
								}
							}
						},
						{field:'ordernum',title:'订单数量',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'ordertotalbox',title:'订单箱数',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'orderamount',title:'订单金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'ordernotaxamount',title:'订单未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'dispatchprice',title:'发货单价',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.initsendnum>0){
									return formatterMoney(value);
								}else{
									return "";
								}
							}
						},
						{field:'initsendnum',title:'发货数量',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.initsendnum>0){
									return formatterBigNumNoLen(value);
								}else{
									return "";
								}
							}
						},
						{field:'initsendtotalbox',title:'发货箱数',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.initsendnum>0){
									return formatterBigNumNoLen(value);
								}else{
									return "";
								}
							}
						},
						{field:'initsendamount',title:'发货金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.initsendnum>0){
									return formatterMoney(value);
								}else{
									return "";
								}
							}
						},
						{field:'initsendnotaxamount',title:'发货未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'sendprice',title:'发货出库单价',width:85,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.sendnum>0){
									return formatterMoney(value);
								}else{
									return "";
								}
							}
						},
						{field:'sendnum',title:'发货出库数量',width:80,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'sendamount',title:'发货出库金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'sendnotaxamount',title:'发货出库未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'checkprice',title:'验收单价',resizable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(null!=value){
									if(parseFloat(value)>parseFloat(rowData.initprice)){
										return "<font color='blue' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
									}
									else if(parseFloat(value)<parseFloat(rowData.initprice)){
										return "<font color='red' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
									}
									else{
										return formatterMoney(value);
									}
								}
							}
						},
						{field:'checknum',title:'验收数量',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'checktotalbox',title:'验收箱数',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'checkamount',title:'验收金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'unitprice',title:'单位差价',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'unitpriceamount',title:'差价总额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'returnnum',title:'直退数量',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'returntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'returnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'salesnum',title:'销售数量',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'salestotalbox',title:'销售箱数',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'salesamount',title:'销售金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
						<c:if test="${map.salescostamount == 'salescostamount'}">
						,
						{field:'costprice',title:'成本价',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(rowData.salescostamount!=null){
									return formatterMoney(value);
								}else{
									return "";
								}
							}
						},
						{field:'salescostamount',title:'成本金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
						</c:if>
						<c:if test="${map.salemarginamount == 'salemarginamount'}">
						,
						{field:'salemarginamount',title:'毛利额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
						</c:if>
						<c:if test="${map.rate == 'rate'}">
						,
						{field:'rate',title:'毛利率',width:60,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								if(null!=value){
									return formatterMoney(value)+"%";
								}
							}
						}
						</c:if>
					]]
				});
    			$("#report-datagrid-salesOrderTrack").datagrid({ 
    				authority:tableColumnListBrandJson,
			 		frozenColumns: tableColumnListBrandJson.frozen,
					columns:tableColumnListBrandJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		sortName:'businessdate',
		  	 		sortOrder:'asc',
		  	 		pageSize:100,
					toolbar:'#report-toolbar-salesOrderTrack',
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
				$("#report-query-customerid").customerWidget({});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:150,
					singleSelect:true
				});
                // $("#report-query-goodsid").goodsWidget({});
                $("#report-query-goodsid").widget({
                    referwid:'RL_T_BASE_GOODS_INFO',
                    width:225,
                    singleSelect:false
                });
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:130,
					singleSelect:false
				});
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-indooruserid").widget({
					referwid:'RL_T_BASE_PERSONNEL_INDOORSTAFF',
					width:130,
					singleSelect:false
				});
				//制单人
				$("#report-query-adduserid").widget({ //制单人参照窗口
					referwid:'RT_T_SYS_USER',
					singleSelect:true,
					width:150,
					onlyLeafCheck:true
				});
                $("#report-query-goodssort").widget({
                    referwid:'RL_T_BASE_GOODS_WARESCLASS',
                    singleSelect:false,
                    width:'130',
                    onlyLeafCheck:false
                });
				//查询
				$("#report-queay-salesOrderTrack").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesOrderTrack").serializeJSON();
		      		$("#report-datagrid-salesOrderTrack").datagrid({
		      			url:'report/sales/showSalesOrderTrackReportData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-salesOrderTrack").click(function(){
					$("#report-query-customerid").customerWidget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-indooruserid").widget("clear");
					$("#report-query-adduserid").widget('clear');
                    $("#report-query-goodssort").widget('clear');
					$("#report-query-form-salesOrderTrack")[0].reset();
		       		$("#report-datagrid-salesOrderTrack").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-salesOrderTrackPage").Excel('export',{
					queryForm: "#report-query-form-salesOrderTrack", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'订单追踪明细表',
			 		url:'report/sales/exportSalesOrderTrackReportData.do'
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-salesOrderTrack").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-salesOrderTrack").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var ordernum = 0;
    			var ordertotalbox = 0;
        		var orderamount = 0;
        		var ordernotaxamount=0;
        		var initsendnum = 0;
        		var initsendtotalbox = 0;
        		var initsendamount = 0;
        		var initsendnotaxamount=0;
        		var sendnum = 0;
        		var sendtotalbox = 0;
        		var sendamount = 0;
        		var sendnotaxamount = 0;
        		var sendcostamount = 0;
        		var checknum=0;
        		var checktotalbox = 0;
        		var checkamount=0;
        		var unitpriceamount=0
        		var returnnum =0;
        		var returntotalbox = 0;
        		var returnamount = 0;
        		var salesnum = 0;
        		var salestotalbox = 0;
        		var salesamount = 0;
        		var salescostamount = 0;
        		var salemarginamount = 0;
        		for(var i=0;i<rows.length;i++){
        			ordernum = Number(ordernum)+Number(rows[i].ordernum == undefined ? 0 : rows[i].ordernum);
        			ordertotalbox = Number(ordertotalbox)+Number(rows[i].ordertotalbox == undefined ? 0 : rows[i].ordertotalbox);
        			orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
        			ordernotaxamount = Number(ordernotaxamount)+Number(rows[i].ordernotaxamount == undefined ? 0 : rows[i].ordernotaxamount);
        			initsendnum = Number(initsendnum)+Number(rows[i].initsendnum == undefined ? 0 : rows[i].initsendnum);
        			initsendtotalbox = Number(initsendtotalbox)+Number(rows[i].initsendtotalbox == undefined ? 0 : rows[i].initsendtotalbox);
        			initsendamount = Number(initsendamount)+Number(rows[i].initsendamount == undefined ? 0 : rows[i].initsendamount);
        			initsendnotaxamount = Number(initsendnotaxamount)+Number(rows[i].initsendnotaxamount == undefined ? 0 : rows[i].initsendnotaxamount);
        			sendnum = Number(sendnum)+Number(rows[i].sendnum == undefined ? 0 : rows[i].sendnum);
        			sendtotalbox = Number(sendtotalbox)+Number(rows[i].sendtotalbox == undefined ? 0 : rows[i].sendtotalbox);
        			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
        			sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
        			sendcostamount = Number(sendcostamount)+Number(rows[i].sendcostamount == undefined ? 0 : rows[i].sendcostamount);
        			checknum = Number(checknum)+Number(rows[i].checknum == undefined ? 0 : rows[i].checknum);
        			checktotalbox = Number(checktotalbox)+Number(rows[i].checktotalbox == undefined ? 0 : rows[i].checktotalbox);
        			checkamount = Number(checkamount)+Number(rows[i].checkamount == undefined ? 0 : rows[i].checkamount);
        			unitpriceamount = Number(unitpriceamount)+Number(rows[i].unitpriceamount == undefined ? 0 : rows[i].unitpriceamount);
        			returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
        			returntotalbox = Number(returntotalbox)+Number(rows[i].returntotalbox == undefined ? 0 : rows[i].returntotalbox);
        			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        			salesnum = Number(salesnum)+Number(rows[i].salesnum == undefined ? 0 : rows[i].salesnum);
        			salestotalbox = Number(salestotalbox)+Number(rows[i].salestotalbox == undefined ? 0 : rows[i].salestotalbox);
        			salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
        			salescostamount = Number(salescostamount)+Number(rows[i].salescostamount == undefined ? 0 : rows[i].salescostamount);
        			salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
        		}
        		var foot=[{businessdate:'选中金额',ordernum:ordernum,ordertotalbox:ordertotalbox,orderamount:orderamount,ordernotaxamount:ordernotaxamount,
        					initsendnum:initsendnum,initsendtotalbox:initsendtotalbox,initsendamount:initsendamount,initsendnotaxamount:initsendnotaxamount,
        					sendnum:sendnum,sendtotalbox:sendtotalbox,sendamount:sendamount,sendnotaxamount:sendnotaxamount,sendcostamount:sendcostamount,
            				checknum:checknum,checktotalbox:checktotalbox,checkamount:checkamount,unitpriceamount:unitpriceamount,
            				returnnum:returnnum,returntotalbox:returntotalbox,returnamount:returnamount,
            				salesnum:salesnum,salestotalbox:salestotalbox,salesamount:salesamount,salescostamount:salescostamount,salemarginamount:salemarginamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-salesOrderTrack").datagrid("reloadFooter",foot);
    		}
    		function showOrderView(id){
        		top.addTab('sales/orderPage.do?type=view&id='+id, '销售订单查看');
        	}
    	</script>
  </body>
</html>
