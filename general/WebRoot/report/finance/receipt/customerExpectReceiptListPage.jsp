<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户预期应收款报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    <table id="report-datagrid-expectReceipt"></table>
    <div id="report-toolbar-expectReceipt" style="padding:0px;height:auto">
        <div class="buttonBG">
            <security:authorize url="/report/finance/expectReceiptExport.do">
                <a href="javaScript:void(0);" id="report-export-expectReceipt" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
        </div>
    	<form action="" id="report-form-expectReceipt" method="post">
    		<table class="querytable">
    			<tr>
    				<td>日期:</td>
    				<td><input type="text" id="report-input-businessdate1" name="businessdate1" value="${today }" style="width:150px;" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-widget-customerid" name="customerid"/></td>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-widget-pcustomerid" name="pcustomerid"/></td>
    			</tr>
    			<tr>
    				<td>收款人:</td>
	    			<td><input type="text" id="report-widget-payeeid" name="payeeid"/></td>
	    			<td>客户业务员:</td>
				    <td><input type="text" id="report-widget-salesuser" name="salesuser"/></td>
				    <td>销售区域:</td>
				    <td><input type="text" id="report-widget-salesarea" name="salesarea"/></td>
    			</tr>
    			<tr>
                    <td>品牌:</td>
                    <td><input type="text" id="report-widget-brandid" name="brandid"/></td>
                    <td>供应商:</td>
                    <td><input type="text" id="report-widget-supplierid" name="supplierid"/></td>
                    <td>销售部门:</td>
				    <td><input type="text" id="report-widget-salesdept" name="salesdept"/></td>
    			</tr>
    			<tr>
					<td>品牌业务员:</td>
					<td><input type="text" id="report-widget-branduser" name="branduser"/></td>
					<td>账期/现款:</td>
    				<td>
    					<select id="report-select-type" name="type" style="width:210px;">
    						<option></option>
    						<option value="1">账期</option>
    						<option value="2">现款</option>
    					</select>
    				</td>

    			</tr>
				<tr>
					<td>小计列：</td>
					<td colspan="3">
						<div style="margin-top:2px">
		    					<div style="line-height: 25px;margin-top: 2px;">
			    					<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid" checked="checked"/>客户</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="pcustomerid"/>总店</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort"/>客户分类</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesarea"/>销售区域</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
									<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="payeeid"/>收款人</label>
	    						<br/>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodssort"/>商品分类</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branddept"/>品牌部门</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branduser"/>品牌业务员</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                                    <input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
		    					</div>
	    					</div>
					</td>
					<td colspan="2">
    					<a href="javaScript:void(0);" id="report-query-expectReceipt" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-expectReceipt" class="button-qr">重置</a>
    				</td>
				</tr>
    		</table>
    	</form>
    </div>
    <div id="report-dialog-CER-salesflow"></div>
    <script type="text/javascript">
    	var FR_footerobject  = null;
    	var tableColumnListJson = $("#report-datagrid-expectReceipt").createGridColumnLoad({
    		frozenCol : [[
    			{field:'idok',checkbox:true,isShow:true}
    		]],
    		commonCol : [[
    			{field:'customerid',title:'客户编号',width:60,sortable:true},
				{field:'customername',title:'客户名称',width:210},
				{field:'pcustomerid',title:'总店编号',width:60,sortable:true},
				{field:'pcustomername',title:'总店名称',width:150,sortable:true},
				{field:'salesuser',title:'客户业务员',sortable:true,width:70,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesusername;
					}
				},
				{field:'payeeid',title:'收款人',width:60,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return rowData.payeename;
		        	}
				},
				{field:'customersort',title:'客户分类',sortable:true,width:60,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.customersortname;
					}
				},
				{field:'salesarea',title:'销售区域',sortable:true,width:60,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesareaname;
					}
				},
				{field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesdeptname;
					}
				},
				{field:'branddept',title:'品牌部门',sortable:true,width:80,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.branddeptname;
					}
				},
				{field:'branduser',title:'品牌业务员',sortable:true,width:70,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.brandusername;
					}
				},
				{field:'goodsid',title:'商品编码',sortable:true,width:60,hidden:true,
					formatter:function(value,rowData,rowIndex){
						if("nodata" != value){
							return value;
						}else{
							return "";
						}
					}
				},
				{field:'goodsname',title:'商品名称',width:250,hidden:true},
				{field:'goodssortname',title:'商品分类',width:60,hidden:true},
				{field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.brandname;
					}
				},
				{field:'supplierid',title:'供应商名称',sortable:true,width:200,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.suppliername;
					}
				},
				{field:'longtermamount',title:'账期',align:'right',resizable:true,width:60,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'cashamount',title:'现款',align:'right',resizable:true,width:60,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'expectreceipt',title:'预期收款',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
	        			return formatterMoney(value);
		        	}
				},
				{
					field: 'allunwithdrawnamount', title: '应收款总额', align: 'right', resizable: true, sortable: true,
					formatter: function (value, rowData, rowIndex) {
						return formatterMoney(value);
					}
				}
    		]]
    	});
    	
    	$(function(){
    		//客户
    		$("#report-widget-customerid").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER',
	    		width:210,
				singleSelect:false
			});
			//总店
			$("#report-widget-pcustomerid").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
	    		width:150,
				singleSelect:false
			});
			//收款人
			$("#report-widget-payeeid").widget({
				referwid:'RL_T_BASE_CUSTOMER_PAYEE',
	    		width:150,
	    		listnum:150,
	    		onlyLeafCheck:false,
				singleSelect:false
			});
			//客户业务员
			$("#report-widget-salesuser").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
	    		width:210,
				singleSelect:false
			});
			//品牌业务员
			$("#report-widget-branduser").widget({
				referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
				width:150,
				singleSelect:false
			});
			//销售区域
			$("#report-widget-salesarea").widget({
				referwid:'RT_T_BASE_SALES_AREA',
	    		width:150,
	    		onlyLeafCheck:false,
				singleSelect:false
			});
			//销售区域
			$("#report-widget-salesdept").widget({
				referwid:'RT_T_SYS_DEPT',
	    		width:150,
	    		onlyLeafCheck:false,
				singleSelect:false
			});

            //品牌
            $("#report-widget-brandid").widget({
                referwid:'RL_T_BASE_GOODS_BRAND',
                width:150,
                singleSelect:false
            });
            //供应商
            $("#report-widget-supplierid").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:210,
                singleSelect:false
            });
    		$("#report-datagrid-expectReceipt").datagrid({
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
				toolbar:'#report-toolbar-expectReceipt',
                onDblClickRow:function(rowIndex, rowData){
				    var cols = $("#report-query-groupcols").val();
				    if(cols=="customerid"){
				        var customerid = rowData.customerid;
						var url = 'report/finance/showCustomerExpectReceiptDetailPage.do?customerid='+customerid;
						$('<div id="report-dialog-CER-salesflow-content"></div>').appendTo('#report-dialog-CER-salesflow');
						$('#report-dialog-CER-salesflow-content').dialog({
							title: '客户：['+rowData.customername+'] 销售流水明细',
							fit:true,
							width: 680,
							height: 400,
							collapsible:false,
							minimizable:false,
							maximizable:true,
							resizable:true,
							closed: true,
							cache: false,
							href: url,
							modal: true,
							onClose:function(){
								$('#report-dialog-CER-salesflow-content').dialog("destroy");
							}
						});
						$('#report-dialog-CER-salesflow-content').dialog("open");
					}
                },
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						FR_footerobject = footerrows[0];
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
			//查询
			$("#report-query-expectReceipt").click(function(){
				if(!$("#report-form-expectReceipt").form('validate')){
					$.messager.alert("提醒","请输入日期再做查询操作！");
					return false;
				}
				var type = $("#report-select-type").val();
				if("1" == type){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "longtermamount");
					$("#report-datagrid-expectReceipt").datagrid('hideColumn', "cashamount");
				}else if("2" == type){
					$("#report-datagrid-expectReceipt").datagrid('hideColumn', "longtermamount");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "cashamount");
				}else{
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "cashamount");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "longtermamount");
				}
				setColumn();
	      		var queryJSON = $("#report-form-expectReceipt").serializeJSON();
	      		$("#report-datagrid-expectReceipt").datagrid({
	      			url: 'report/finance/showCustomerExpectReceiptListData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#report-reload-expectReceipt").click(function(){
				$("#report-widget-customerid").widget("clear");
				$("#report-widget-pcustomerid").widget('clear');
				$("#report-widget-payeeid").widget('clear');
				$("#report-widget-salesuser").widget('clear');
				$("#report-widget-salesarea").widget('clear');
				$("#report-widget-salesdept").widget('clear');

                $("#report-widget-brandid").widget('clear');
                $("#report-widget-supplierid").widget('clear');
				$("#report-widget-branduser").widget('clear');
				$("#report-form-expectReceipt")[0].reset();
				$("#report-datagrid-expectReceipt").datagrid('showColumn', "cashamount");
				$("#report-datagrid-expectReceipt").datagrid('showColumn', "longtermamount");
	       		$("#report-datagrid-expectReceipt").datagrid('loadData',{total:0,rows:[],footer:[]});
	       		setColumn();
			});
			
			$("#report-export-expectReceipt").click(function(){
				//封装查询条件
				var objecr  = $("#report-datagrid-expectReceipt").datagrid("options");
				var queryParam = objecr.queryParams;
				if(null != objecr.sortName && null != objecr.sortOrder ){
					queryParam["sort"] = objecr.sortName;
					queryParam["order"] = objecr.sortOrder;
				}
				var queryParam = JSON.stringify(queryParam);
				var url = 'report/finance/exportCustomerExpectReceiptData.do';
				exportByAnalyse(queryParam,"客户预期应收款统计报表","report-datagrid-expectReceipt",url);
			});
    	});
    	
    	//选中合计
    	function countTotalAmount(){
    		var rows = $("#report-datagrid-expectReceipt").datagrid('getChecked');
    		var longtermamount = 0;
    		var cashamount = 0;
    		var expectreceipt = 0;
    		var unpassamount = 0;
    		var totalpassamount = 0;
    		var returnamount = 0;
    		for(var i=0;i<rows.length;i++){
    			longtermamount = Number(longtermamount)+Number(rows[i].longtermamount == undefined ? 0 : rows[i].longtermamount);
    			cashamount = Number(cashamount)+Number(rows[i].cashamount == undefined ? 0 : rows[i].cashamount);
    			expectreceipt = Number(expectreceipt)+Number(rows[i].expectreceipt == undefined ? 0 : rows[i].expectreceipt);
    			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
    			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
    			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
    		}
    		var foot = [{customername:'选中合计',longtermamount:longtermamount,cashamount:cashamount,expectreceipt:expectreceipt,unpassamount:unpassamount,totalpassamount:totalpassamount,returnamount:returnamount}]
    		if(null!=FR_footerobject){
           		foot.push(FR_footerobject);
       		}
       		$("#report-datagrid-expectReceipt").datagrid("reloadFooter",foot);
    	}
    	function setColumn(){
			var cols = $("#report-query-groupcols").val();
			if(cols!=""){
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "customerid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "customername");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "pcustomerid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "pcustomername");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesuser");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "customersort");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesarea");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesdept");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodsid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodsname");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodssortname");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "brandid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "branduser");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "payeeid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "branddept");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "supplierid");
			}
			else{
				$("#report-datagrid-expectReceipt").datagrid('showColumn', "customerid");
				$("#report-datagrid-expectReceipt").datagrid('showColumn', "customername");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "pcustomerid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "pcustomername");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesuser");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesarea");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "salesdept");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodsid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodsname");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "goodssortname");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "brandid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "branduser");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "payeeid");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "branddept");
				$("#report-datagrid-expectReceipt").datagrid('hideColumn', "supplierid");
			}
			var colarr = cols.split(",");
			for(var i=0;i<colarr.length;i++){
				var col = colarr[i];
				if(col=='customerid'){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "customerid");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "customername");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "pcustomerid");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "pcustomername");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "salesuser");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "customersort");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "payeeid");
				}else if(col=="pcustomerid"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "pcustomerid");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "pcustomername");
				}else if(col=="salesuser"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "salesuser");
				}else if(col=="salesarea"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "salesarea");
				}else if(col=="salesdept"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "salesdept");
				}else if(col=="goodsid"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "goodsid");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "goodsname");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "goodssortname");
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "brandid");
				}else if(col=="goodssort"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "goodssortname");
				}else if(col=="brandid"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "brandid");
				}else if(col=="branduser"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "branduser");
				}else if(col=="branddept"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "branddept");
				}else if(col=="customersort"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "customersort");
				}else if(col=="supplierid"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "supplierid");
				}else if(col=="payeeid"){
					$("#report-datagrid-expectReceipt").datagrid('showColumn', "payeeid");
				}
			}
		}
  	</script>
  </body>
</html>
