<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票新增</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <table id="account-datagrid-sourceBill"></table>
  	<div id="account-toolbar-query-sourceBill" style="padding: 0px">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="account-add-sourceBill"class="easyui-linkbutton" plain="true" iconCls="button-add" >申请采购发票</a>
        </div>
  		<form action="" method="post" id="account-form-query-sourceBill">
  			<input type="hidden" name="shownotisinvoice" value="1" />
  			<table class="querytable">
  				<tr>
  					<td>业务日期:</td>
  					<td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
  						到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
  					<td>采购部门:</td>
  					<td><input type="text" id="account-buydept-sourceBill" name="buydeptid" /></td>
					<td>是否代配送:</td>
  					<td>
	  					<select name="isdelivery" style="width: 130px;">
	  							<option></option>
	  							<option value="2">是</option>
	  							<option value="1">否</option>
	  					</select>
  					</td>
  				</tr>
  				<tr>
  					<td>供应商名称:</td>
  					<td><input type="text" id="account-supplierid-sourceBill" name="supplierid" style="width: 224px;"/>
                    <input type="hidden" id="supplierAmount" />
                    </td>
  					<td>单据类型:</td>
  					<td>
  						<select id="account-ordertype-sourceBill" name="ordertype" style="width: 130px;">
  							<option></option>
  							<option value="1">采购进货单</option>
  							<option value="2">采购退货通知单</option>
							<option value="3">应付款期初</option>
  						</select>
  					</td>
  				</tr>
                <tr>
                    <td>采购员:</td>
                    <td><input type="text" id="account-buyuser-sourceBill" name="buyuserid" /></td>
                    <td>单据编号:</td>
                    <td><input type="text" id="account-id-sourceBill" name="id" style="width: 130px;"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="account-query-sourceBill" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="account-reload-sourceBill" class="button-qr">重置</a>
                    </td>
                </tr>
  			</table>
  		</form>
  	</div>
  	<div id="account-panel-selectBillDetailPage"></div>
  	<div id="account-panel-purchaseinvoiceDetailPage"></div>
  	<input id="account-nochecked-detail-selectBillDetailPage" type="hidden"/>
    <input id="account-checkedbill-invoiceamount-selectBillDetailPage" type="hidden"/>
  	<input id="account-checkedbill-uncheckamount-selectBillDetailPage" type="hidden"/>
  	<script type="text/javascript">
  		var PSB_footerobject  = null;
  		$(function(){
  			var purchaseInvoiceSourceBillCols = $("#account-datagrid-sourceBill").createGridColumnLoad({
  				frozenCol : [[{field:'ck', checkbox:true}]],
  				commonCol : [[
  					{field:'id', title:'编号', width:130,sortable:true,
						formatter:function(value,row,index){
							if(null!=value){
								if(value!="选中合计" && value!="合计"){
									return '<a href="javascript:showDetailListPage(\''+value+'\','+index+')">'+value+'</a>';
								}else{
									return value;
								}
							}
				        }
					},
  					{field:'ordertype', title:'单据类型', width:100,sortable:true,
						formatter:function(value,row,index){
							if(value=='1'){
								return "采购进货单";
							}else if(value=='2'){
								return "采购退货通知单";
							}else if(value=='3'){
								return "应付款期初";
							}
				        }
					},
					{field:'businessdate', title:'业务日期', width:70,sortable:true},
					{field:'supplierid',title:'供应商编码',width:70,sortable:true},
			  		{field:'suppliername',title:'供应商名称',width:210,isShow:true},
					{field:'totalamount',title:'总金额',resizable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'uninvoiceamount',title:'未开票金额',resizable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'invoiceamount',title:'选中开票金额',resizable:true,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					},
					{field:'paydate',title:'应付日期',width:100,align:'left',hidden:true},
					{field:'buydeptid',title:'采购部门',width:90,align:'left',
						formatter:function(value,rowData,rowIndex){
				       		return rowData.buydeptname;
				        }
					},
					{field:'buyuserid',title:'采购员',width:90,align:'left',
						formatter:function(value,rowData,rowIndex){
				       		return rowData.buyusername;
				        }
					},
					{field:'handlerid',title:'对方经手人',width:80,align:'left',hidden:true,
						formatter:function(value,rowData,rowIndex){
				       		return rowData.handlername;
				        }
					},
					{field:'addusername',title:'制单人',width:60,align:'left'},
                    {field:'supplieramount',title:'账户余额',hidden:true,width:60,align:'left'},
					{field:'remark',title:'备注',width:100,align:'left'}
  				]]
  			});
  			
  			$("#account-datagrid-sourceBill").datagrid({
  				authority:purchaseInvoiceSourceBillCols,
		 		frozenColumns: purchaseInvoiceSourceBillCols.frozen,
				columns:purchaseInvoiceSourceBillCols.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
				sortName:'supplierid',
				sortOrder:'asc',
				pageSize:1000,
				pageList:[20,100,200,500,1000],
				showFooter:true,
				toolbar:'#account-toolbar-query-sourceBill',
				onBeforeLoad:function(){
		 		},
				onClickRow:function(rowIndex, rowData){
					var rows = $(this).datagrid("getChecked");
					var checkflag = true;
					for(var i=0;i<rows.length;i++){
						if(rows[i].supplierid!=rowData.supplierid){
							checkflag = false;
							break;
						}
					}
					if(!checkflag){
						$(this).datagrid("uncheckRow",rowIndex);
						return false;
					}
				},
				onCheckAll:function(rows){
					var checkflag = true;
		    		var data = rows[0];
		    		for(var i=0;i<rows.length;i++){
						if(rows[i].supplierid!=data.supplierid){
							checkflag = false;
							break;
						}
					}
					if(!checkflag){
						$.messager.alert("提醒","请选择相同供应商下的数据！");
						$(this).datagrid("uncheckAll");
						return false;
					}
					for(var i=0;i<rows.length;i++){
						if(rows[i].invoiceamount==null || rows[i].invoiceamount==0){
							rows[i].invoiceamount = rows[i].uninvoiceamount;
						}
		    			$(this).datagrid('updateRow',{index:i, row:rows[i]});
					}
					countTotalAmount();
				},
				onUncheckAll:function(rows){
					for(var i=0;i<rows.length;i++){
						rows[i].invoiceamount = null;
					}
					$(this).datagrid("loadData",rows);
					countTotalAmount();
				},
				onCheck:function(rowIndex, rowData){
					var rowArr = $(this).datagrid("getChecked");
					var checkFlag = true;
					for(var i=0;i<rowArr.length;i++){
						if(rowArr[i].supplierid!=rowData.supplierid){
							checkFlag = false;
							break;
						}
					}
					if(!checkFlag){
						$.messager.alert("提醒","请选择相同供应商下的数据！");
						$(this).datagrid("uncheckRow",rowIndex );
						return false;
					}else{
						var uncheckAmountStr = $("#account-checkedbill-uncheckamount-selectBillDetailPage").val();
						var uncheckAmountArr = null;
						var uncheckAmount = 0;
						if(null != uncheckAmountStr && "" != uncheckAmountStr){
							uncheckAmountArr = $.parseJSON(uncheckAmountStr);
						}else{
							uncheckAmountArr = [];
						}
						for(var i=0;i<uncheckAmountArr.length;i++){
			    			if(uncheckAmountArr[i].id==rowData.id){
			    				uncheckAmount = uncheckAmountArr[i].uncheckamount;
			    				break;
			    			}
			    		}
			    		rowData.invoiceamount = Number(rowData.uninvoiceamount)-Number(uncheckAmount);
			    		$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
			    		countTotalAmount();
					}
				},
				onUncheck:function(rowIndex, rowData){
					var rowArr = $(this).datagrid("getChecked");
					var checkFlag = true;
					for(var i=0;i<rowArr.length;i++){
						if(rowArr[i].supplierid!=rowData.supplierid){
							checkFlag = false;
							break;
						}
					}
		    		if(checkFlag){
		    			rowData.invoiceamount = null;
		    			$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
		    			countTotalAmount();
		    		}
				},
				onLoadSuccess:function(){
		    		var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						if(footerrows[0]!=null &&footerrows[0].id=="合计"){
							PSB_footerobject = footerrows[0];
							countTotalAmount();
						}
					}
		    	}
  			}).datagrid("columnMoving");
  			//供应商
  			$("#account-supplierid-sourceBill").supplierWidget({
  				singleSelect:true,
  				isdatasql:false
  			});
  			//采购部门
  			$("#account-buydept-sourceBill").widget({
  				referwid:'RL_T_BASE_DEPARTMENT_BUYER',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
  			});
  			//采购员
  			$("#account-buyuser-sourceBill").widget({
  				referwid:'RL_T_BASE_PERSONNEL_BUYER',
    			singleSelect:true,
    			width:225,
    			onlyLeafCheck:true
  			});
  			//查询
  			$("#account-query-sourceBill").click(function(){
  				$("#account-datagrid-sourceBill").datagrid('loadData',{total:0,rows:[],footer:[]});
  				$("#account-datagrid-sourceBill").datagrid('clearChecked');
  				$("#account-datagrid-sourceBill").datagrid('clearSelections');
  				var queryJSON = $("#account-form-query-sourceBill").serializeJSON();
		       	$("#account-datagrid-sourceBill").datagrid({
		       		url: 'account/payable/getPurchaseInvoiceSourceOfBillList.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
  			});
  			//重置
  			$("#account-reload-sourceBill").click(function(){
  				$("#account-datagrid-sourceBill").datagrid('loadData',{total:0,rows:[],footer:[]});
  				$("#account-datagrid-sourceBill").datagrid('clearChecked');
  				$("#account-datagrid-sourceBill").datagrid('clearSelections');
  				$("#account-supplierid-sourceBill").supplierWidget('clear');
  				$("#account-buydept-sourceBill").widget('clear');
  				$("#account-buyuser-sourceBill").widget('clear');
  				$("#account-nochecked-detail-selectBillDetailPage").val("");
  				$("#account-checkedbill-invoiceamount-selectBillDetailPage").val("");
  				$("#account-checkedbill-uncheckamount-selectBillDetailPage").val("");
  				$("#account-form-query-sourceBill")[0].reset();
  			});
  			//生成采购发票
  			$("#account-add-sourceBill").click(function(){
  				showPurchaseInvoiceDetail();
  			});
  		});
  		
  		function countTotalAmount(){
  			var rows = $("#account-datagrid-sourceBill").datagrid('getChecked');

  			var totalamount = 0;
  			var uninvoiceamount = 0;
  			var invoiceamount = 0;
  			for(var i=0;i<rows.length;i++){
       			totalamount = Number(totalamount)+Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
       			uninvoiceamount = Number(uninvoiceamount)+Number(rows[i].uninvoiceamount == undefined ? 0 : rows[i].uninvoiceamount);
       			if(null!=invoiceamount){
					invoiceamount = Number(invoiceamount) + Number(rows[i].invoiceamount);
				}
       		}
            if(rows[0] != undefined){
                var foot=[{id:'选中合计',supplierid:"供应商余额",suppliername:rows[0].supplieramount,totalamount:totalamount,uninvoiceamount:uninvoiceamount,invoiceamount:invoiceamount}];
            }else{
                var foot=[{id:'选中合计',totalamount:totalamount,uninvoiceamount:uninvoiceamount,invoiceamount:invoiceamount}];
            }

       		if(null!=PSB_footerobject){
           		foot.push(PSB_footerobject);
       		}
       		$("#account-datagrid-sourceBill").datagrid("reloadFooter",foot);
  		}
  		
  		//显示已选中来源单据明细列表页面
  		function showPurchaseInvoiceDetail(){
  			var rowArr = $("#account-datagrid-sourceBill").datagrid("getChecked");
  			var ids = null;
    		if(rowArr.length==0){
    			$.messager.alert("提醒","请选择数据");
    			return false;
    		}
			for(var i=0;i<rowArr.length;i++){
				if(ids==null){
					ids = rowArr[i].id;
				}else{
					ids +=","+ rowArr[i].id;
				}
			}
			var suppliername = rowArr[0].suppliername;
			var supplierid = rowArr[0].supplierid;
			var uncheckedjson = $("#account-nochecked-detail-selectBillDetailPage").val();
			loading("明细页面加载中..");
			$.ajax({
				url:'account/payable/showArrivalAndReturnBillDetailList.do',
				dataType:'html',
				type:'post',
				data:{id:ids,uncheckedjson:uncheckedjson,supplierid:supplierid,suppliername:suppliername},
				success:function(json){
					loaded();
					$("#account-panel-purchaseinvoiceDetailPage").dialog({
						title:"供应商:"+supplierid+suppliername+"，商品明细列表",
					    fit:true,
						closed:false,
						modal:true,
						cache:false,
						maximizable:true,
						resizable:true,
						content:json,
					    buttons:[
					    		//{
								//	text:'追加到申请单',
								//	handler:function(){
								//		var supplierid = $("#select-supplierid-arrivalAndreturn").val();
								//		showSupplieridPurchaseInvoiceList(supplierid);
								//	}
								//},
					    		{
									text:'生成采购发票',
									handler:function(){
										addPurchaseInvoiceByRefer();
									}
								}
						]
					});
				},
				error:function(){
					loaded();
				}
			});
  		}
  		
  		//显示选中单据明细列表页面
  		function showDetailListPage(id,rowIndex){
  			var checkrows = $("#account-datagrid-sourceBill").datagrid("getChecked");
  			var rows = $("#account-datagrid-sourceBill").datagrid("getRows");
  			var rowData = null;
  			for(var i=0;i<rows.length;i++){
				if(rows[i].id==id){
					rowData = rows[i];
					break;
				}
			}
			var checkflag = true;
			for(var i=0;i<checkrows.length;i++){
				if(checkrows[i].supplierid!=rowData.supplierid){
					checkflag = false;
					break;
				}
			}
			if(!checkflag){
				$("#account-datagrid-sourceBill").datagrid("uncheckRow",rowIndex);
				return false;
			}
			if(null!=rowData){
				$("#account-panel-selectBillDetailPage").dialog({
					href:'account/payable/showPurchaseInvoiceSourceBillDetailListPage.do?billid='+id+'&ordertype='+rowData.ordertype,
					title:"供应商:"+rowData.supplierid+rowData.suppliername+",单据明细列表",
					fit:true,
					closed:false,
					modal:true,
					cache:false,
					maximizable:true,
					resizable:true,
					buttons:[{
						text:'确认',
						handler:function(){
							addNocheckedDetail();
						}
					}]
				});
				$("#account-datagrid-sourceBill").datagrid("checkRow",rowIndex);
			}
  		}
  	</script>
  </body>
</html>
