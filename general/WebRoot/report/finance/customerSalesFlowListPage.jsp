<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户销售情况流水表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
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
      <div class="nodisplay" id="report-exportBlock-customerSalesFlow">
      </div>
    	<table id="report-datagrid-customerSalesFlow"></table>
    	<div id="report-toolbar-customerSalesFlow" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/customerSalesFlowExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-customerSalesFlowPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
                <security:authorize url="/report/finance/customerSalesFlowDetailExport.do">
                    <a href="javaScript:void(0);" id="report-export-customerSalesFlowDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="销售清单导出">销售清单导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-customerSalesFlow" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td class="tdinput"><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>商品名称:</td>
    				<td class="tdinput"><input type="text" id="report-query-goodsid" name="goodsid"/></td>
    				<td>客户业务员:</td>
    				<td class="tdinput"><input type="text" id="report-query-salesuserid" name="salesuser"/></td>
                    <td>商品分类:</td>
                    <td class="tdinput"><input type="text" id="report-query-goodssort" name="goodssort"/></td>
    			</tr>
    			<tr>
    				<td>客户名称:</td>
    				<td class="tdinput"><input type="text" id="report-query-customerid" name="customerid" style="width: 225px;"/></td>
    				<td>总店名称:</td>
    				<td class="tdinput"><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
    				<td>销售区域:</td>
    				<td class="tdinput"><input type="text" id="report-query-salesarea" name="salesarea"/></td>
                    <td>客户分类:</td>
                    <td class="tdinput"><input type="text" id="report-query-customersort" name="customersort"/></td>
    			</tr>
    			<tr>
    				<td>品牌名称:</td>
    				<td class="tdinput"><input type="text" id="report-query-brandid" name="brandid" style="width: 225px;"/></td>
    				<td>单据类型:</td>
    				<td class="tdinput">
    					<select id="report-query-billtype" name="billtype" style="width: 150px;" class="easyui-combobox" data-options="multiple:true,onLoadSuccess:function(){$(this).combobox('clear');}">
    						<option></option>
    						<option value="1">发货单</option>
    						<option value="2">直退退货单</option>
    						<option value="3">售后退货单</option>
    						<option value="4">冲差单</option>
    					</select>
    				</td>
                    <td>订单编号:</td>
                    <td class="tdinput"><input type="text" name="orderid" style="width:125px;"/></td>
                    <td>开单类型:</td>
                    <td class="tdinput">
                        <select name="type" style="width: 155px;">
                            <option></option>
                            <option value="1">赠品开单</option>
                            <option value="2">折扣开单</option>
                        </select>
                    </td>
    			</tr>
                <tr>
                    <td colspan="2">
                        <input class="checkbox1" type="checkbox" name="invoice1" value="1" checked="checked" id="report-query-invoice1"/>
                        <label for="report-query-invoice1" class="divtext">未开票</label>
                        <input class="checkbox1"  type="checkbox" name="writeoff1" value="1" checked="checked" id="report-query-writeoff1"/>
                        <label for="report-query-writeoff1" class="divtext">未核销</label>
                        <input class="checkbox1"  type="checkbox" name="invoice2" value="1" checked="checked" id="report-query-invoice2" />
                        <label for="report-query-invoice2" class="divtext">已开票</label>
                        <input class="checkbox1" type="checkbox" name="writeoff2" value="1" checked="checked" id="report-query-writeoff2"/>
                        <label for="report-query-writeoff2" class="divtext">已核销</label>
                    </td>
                    <td>单据编号:</td>
                    <td class="tdinput"><input type="text" name="id" style="width:150px;"/></td>
                    <td>发货仓库：</td>
                    <td class="tdinput"><input id="report-query-storageid" name="storageid" style="width:125px;" /></td>
                    <td colspan="2" style="padding-left:8px">
                        <a href="javaScript:void(0);" id="report-queay-customerSalesFlow" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-customerSalesFlow" class="button-qr">重置</a>
                    </td>
                </tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-dialog-customerpush-div"></div>
    	<script type="text/javascript">
		var SR_footerobject  = null;
        var allColumns;
    		var initQueryJSON = $("#report-query-form-customerSalesFlow").serializeJSON();
    		$(function(){
                $("#report-query-customerid").widget({
                    referwid:'RL_T_BASE_SALES_CUSTOMER',
                    width:225,
                    singleSelect:true
                });

                $("#report-query-storageid").widget({
                    referwid:'RL_T_BASE_STORAGE_INFO',
                    width:125,
                    singleSelect:true
                });
                $("#report-query-pcustomerid").widget({
                    referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
                    width:150,
                    singleSelect:true
                });
                $("#report-query-salesarea").widget({
                    referwid:'RT_T_BASE_SALES_AREA',
                    width:125,
                    singleSelect:false,
                    onlyLeafCheck:false
                });
                $("#report-query-salesuserid").widget({
                    referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
                    width:125,
                    singleSelect:false,
                    onlyLeafCheck:false
                });
                $("#report-query-goodsid").widget({
                    referwid:'RL_T_BASE_GOODS_INFO',
                    width:150,
                    singleSelect:false
                });
                $("#report-query-brandid").widget({
                    referwid:'RL_T_BASE_GOODS_BRAND',
                    width:225,
                    singleSelect:false
                });
                $("#report-query-goodssort").widget({
                    referwid:'RL_T_BASE_GOODS_WARESCLASS',
                    singleSelect:false,
                    width:155,
                    onlyLeafCheck:false
                });
                $("#report-query-customersort").widget({
                    referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
                    singleSelect:false,
                    width:155,
                    onlyLeafCheck:false
                });
    			var tableColumnListJson = $("#report-datagrid-customerSalesFlow").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'businessdate',title:'业务日期',width:80},
						{field:'customerid',title:'客户编码',width:60},
						{field:'customername',title:'客户名称',width:100},
						{field:'salesusername',title:'客户业务员',width:70},
						{field:'customersortname',title:'客户分类',width:70,hidden:true},
						{field:'salesareaname',title:'销售区域',width:100,hidden:true},
						{field:'billtypename',title:'单据类型',width:60
						},
						{field:'deliverystoragename',title:'发货仓库',width:80},
						{field:'id',title:'单据编号',sortable:true,width:130,
							formatter:function(value,rowData,rowIndex){
								if(value!=null){
									if(rowData.customerid!=null && rowData.customerid!=""){
										if(rowData.billtype=='1'){
											return "<a href='javascript:showSaleoutView(\""+value+"\");' >"+value+"</a>";
										}else if(rowData.billtype=='2' || rowData.billtype=='3'){
											return "<a href='javascript:showRejectView(\""+value+"\");' >"+value+"</a>";
										}else if(rowData.billtype=='4'){
											return "<a href='javascript:showCustomerPushView(\""+value+"\");' >"+value+"</a>";
										}

									}else{
										return value;
									}
								}
							}
						},
						{field:'orderid',title:'订单编号',width:130,
							formatter:function(value,rowData,rowIndex){
								if(value!=null){
									if(rowData.billtype=='1' || rowData.billtype=='4'){
										return "<a href='javascript:showOrderView(\""+value+"\");' >"+value+"</a>";
									}else if(rowData.billtype=='2' || rowData.billtype=='3'){
										return "<a href='javascript:showRejectBillView(\""+value+"\");' >"+value+"</a>";
									}
								}
							}
						},
						{field:'goodsid',title:'商品编码',width:60},
						{field:'goodsname',title:'商品名称',width:120},
						{field:'spell',title:'助记符',width:60},
						{field:'barcode',title:'条形码',width:90},
						{field:'brandname',title:'品牌',width:60},
						{field:'boxnum',title:'箱装量',width:60,
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'unitname',title:'单位',width:40},
						{field:'unitnum',title:'数量',width:60,align:'right', precision: 0,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=0){
									return formatterBigNumNoLen(value);
								}
							}
						},
						{field:'price',title:'单价',width:60,align:'right', precision: 2,
							formatter:function(value,rowData,rowIndex){
								if(rowData.billtype !="9" && rowData.billtype !="4" && rowData.isdiscount!="1" && rowData.isdiscount!="2"){
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
						{field:'taxamount',title:'金额',align:'right',resizable:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'auxnumdetail',title:'箱数',align:'right',resizable:true},
						{field:'boxprice',title:'箱价',align:'right',resizable:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						<security:authorize url="/report/finance/costandmarginratebtn.do">
						{field:'costprice',title:'成本价',width:60,align:'right',hidden:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								if(rowData.billtype != "9"){
									return formatterMoney(value);
								}
							}
						},
						{field:'costamount',title:'成本金额',width:80,align:'right',hidden:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'marginamount',title:'毛利额',width:80,align:'right',hidden:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},

						{field:'marginamountrate',title:'毛利率',width:80,align:'right',hidden:true, precision: 2,
							formatter:function(value,rowData,rowIndex){
								if(undefined != value){
									return formatterMoney(value)+"%";
								}
							}
						},
						</security:authorize>
						{field:'isinvoicebillname',title:'开票状态',align:'right',width:60},
						{field:'writeoffname',title:'核销状态',align:'right',width:60,isShow:true},
						{field:'invoicebilldate',title:'开票日期',width:80},
						{field:'writeoffdate',title:'核销日期',width:80},
						{field:'duefromdate',title:'应收日期',width:80},
						{field:'remark',title:'备注',width:80}

					]]
				});


    			$("#report-datagrid-customerSalesFlow").datagrid({ 
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
					toolbar:'#report-toolbar-customerSalesFlow',
					rowStyler:function(index,row){
						if(row.isultra == "1"){
							return 'background-color:#DFF1D1';
						}
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

                allColumns = $("#report-datagrid-customerSalesFlow").datagrid("options").columns;

				//查询
				$("#report-queay-customerSalesFlow").click(function(){
					//判断复选框是否有选中
					var flag = false;
					$(".checkbox1").each(function(){
						if($(this)[0].checked){
							flag = true;
						}
					});
					if(!flag){
						$.messager.alert('提醒','请勾选要查询的单据状态！');
						return false;
					}
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-customerSalesFlow").serializeJSON();
		      		$("#report-datagrid-customerSalesFlow").datagrid({
		      			url:'report/finance/showCustomerSalesFlowList.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-customerSalesFlow").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-goodsid").widget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-salesarea").widget("clear");
                    $("#report-query-customersort").widget("clear");
                    $("#report-query-goodssort").widget("clear");
					$("#report-query-salesuserid").widget("clear");
                    $("#report-query-billtype").val("");
					$("#report-query-form-customerSalesFlow").form("reset");
					$("#report-query-billtype").val();
		       		$("#report-datagrid-customerSalesFlow").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-customerSalesFlowPage").Excel('export',{
					queryForm: "#report-query-form-customerSalesFlow", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户销售情况流水表',
			 		url:'report/finance/exportCustomerSalesFlowData.do'
				});
				
				$("#report-export-customerSalesFlowDetail").Excel('export',{
					queryForm: "#report-query-form-customerSalesFlow", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'销售清单导出',
			 		url:'report/finance/exportCustomerSalesFlowDetailData.do'
				});

                // $("#report-buttons-customerSalesFlowPage").off('click').click(function(){
                //
                //     //封装查询条件
                //     var objecr  = $("#report-datagrid-customerSalesFlow").datagrid("options");
                //     var queryParam = objecr.queryParams;
                //     if(null != objecr.sortName && null != objecr.sortOrder ){
                //         queryParam["sort"] = objecr.sortName;
                //         queryParam["order"] = objecr.sortOrder;
                //     }
                //
                //     var columns = $("#report-datagrid-customerSalesFlow").datagrid("options").columns;
                //     for(var i in columns) {
                //         var column = columns[i];
                //         for(var j in allColumns) {
                //             var c = allColumns[0][j];
                //             if(c.field == column.field) {
                //                 if(column.hidden == true) {
                //                     c.hidden = true;
                //                 } else {
                //                     c.hidden = false;
                //                 }
                //             }
                //         }
                //     }
                //
                //     queryParam.columnsStr = JSON.stringify(allColumns[0]);
                //
                //     $('#report-paramstr-customerSalesFlowListPage').val(JSON.stringify(queryParam));
                //
                //     loading('正在导出客户销售情况流水数据...');
                //     $.ajax({
                //         type: 'post',
                //         url: 'report/finance/generateCustomerFlowListFile.do',
                //         data: queryParam,
                //         dataType: 'json',
                //         success: function (json) {
                //
                //             loaded();
                //             if(json.flag == false) {
                //                 $.messager.alert('错误', json.msg || '生成销售流水文件出错！');
                //                 return false;
                //             }
                //
                //             var cf = document.createElement("iframe");
                //             cf.name = "exportform";
                //             cf.src = 'report/finance/downloadCustomerFlowListFile.do';
                //             document.getElementById("report-exportBlock-customerSalesFlow").innerHTML = "";
                //             document.getElementById("report-exportBlock-customerSalesFlow").appendChild(cf);
                //
                //         }
                //     });
                // });
    		});

        	function countTotalAmount(){
    			var rows =  $("#report-datagrid-customerSalesFlow").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-customerSalesFlow").datagrid("reloadFooter",foot);
            		return false;
        		}else{
        			for(var i=0;i<rows.length;i++){
        				var obj = rows[i];
        				if("1" == obj.isbitsum){
        					var index = $("#report-datagrid-customerSalesFlow").datagrid("getRowIndex",obj);
        					$("#report-datagrid-customerSalesFlow").datagrid("unselectRow",index);
        					$("#report-datagrid-customerSalesFlow").datagrid("uncheckRow",index);
        				}
        			}
        		}
        		rows =  $("#report-datagrid-customerSalesFlow").datagrid('getChecked');
    			var unitnum = 0;
        		var taxamount = 0;
        		var costamount = 0;
        		var auxnum = 0;
        		var auxremainder = 0;
        		var totalbox = 0;
				var marginamount = 0;
        		for(var i=0;i<rows.length;i++){
        			unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
        			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
        			costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
        			auxnum = Number(auxnum)+Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
        			auxremainder = Number(auxremainder)+Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
        			totalbox = Number(totalbox)+Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
					marginamount = Number(marginamount)+Number(rows[i].marginamount == undefined ? 0 : rows[i].marginamount);
        		}
        		totalbox = formatterMoney(totalbox) + "箱";
        		var foot=[{id:'选中合计',bankname:'',unitnum:unitnum,auxnum:auxnum,auxremainder:auxremainder,auxnumdetail:totalbox,taxamount:taxamount,costamount:costamount,marginamount:marginamount}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-customerSalesFlow").datagrid("reloadFooter",foot);
    		}
        	function showSaleoutView(id){
        		top.addOrUpdateTab('storage/showSaleOutViewPage.do?id='+ id, "发货单查看");
        	}
        	function showOrderView(id){
        		top.addTab('sales/orderPage.do?type=view&id='+id, '销售订单查看');
        	}
        	function showRejectView(id){
        		top.addOrUpdateTab('storage/showSaleRejectEnterEditPage.do?id='+ id, "销售退货入库单查看");
        	}
        	function showCustomerPushView(id){
        		$('#report-dialog-customerpush-div').dialog({  
				    title: '客户应收款冲差',  
				    width: 400,  
				    height: 350,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    href: 'account/receivable/showCustomerPushBanlanceViewPage.do?id='+id,  
				    modal: true,
				    onLoad:function(){
				    	$("#account-customerPushBanlance-addButton-hold").hide();
				    	$("#account-customerPushBanlance-addButton").hide();
				    }
				});
        		$('#report-dialog-customerpush-div').dialog("open");
        	}
        	function showRejectBillView(id){
        		top.addOrUpdateTab('sales/rejectBill.do?type=edit&id='+id, '退货通知单查看');
        	}
    	</script>
  </body>
</html>
