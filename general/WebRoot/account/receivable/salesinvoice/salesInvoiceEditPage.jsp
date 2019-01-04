<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>销售发票</title>
  </head>
  
  <body>
  	<input type="hidden" id="salesInvoice-hidden-applytype" value="${salesInvoice.applytype }"/>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="account-form-salesInvoiceAdd" action="" method="post">
	    	<div data-options="region:'north',border:false,split:false" style="height: 150px;">
	    		<table style="border-collapse:collapse;" border="0"  cellpadding="2px" cellspacing="2px">
	    			<tr>
	    				<td class="len100 left">编号：</td>
	    				<td class="len165"><input class="len150" name="salesInvoice.id" value="${salesInvoice.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="account-salesInvoice-businessdate" class="len150" value="${salesInvoice.businessdate }" name="salesInvoice.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="account-salesInvoice-status"  disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == salesInvoice.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">客户名称(总店):</td>
	    				<td colspan="3">
	    					<input type="text" id="account-salesInvoice-customerid" name="salesInvoice.customerid" style="width: 300px;" value="${salesInvoice.customerid }" text="<c:out value="${salesInvoice.customername}"></c:out>" readonly="readonly"/>
	    					<span style="margin-left:5px;line-height:25px;">编码：${salesInvoice.customerid }</span>
	    				</td>
                        <td>账户余额:</td>
                        <td>
                            <input type="text" class="easyui-numberbox len150" data-options="min:0,precision:2" value="${salesInvoice.customeramount}" readonly="readonly"/>
                        </td>
    					<input type="hidden" name="salesInvoice.sourcetype" value="${salesInvoice.sourcetype }"/>
    					<input type="hidden" id="account-salesInvoice-sourceid" name="salesInvoice.sourceid" value="${salesInvoice.sourceid }"/>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">开票客户名称:</td>
	    				<td>
	    					<select id="account-salesInvoice-invoicecustomerid"  name="salesInvoice.invoicecustomerid" class="len150">
	    						<c:forEach items="${customerArr }" var="list">
								<option value="${list.id }" <c:if test="${list.id == salesInvoice.invoicecustomerid}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td colspan="2" class="len80 left">
	    					<input type="text" id="account-salesInvoice-invoicecustomername" name="salesInvoice.invoicecustomername" style="width: 234px;" value="${salesInvoice.invoicecustomername }" autocomplete="off"/>
	    				</td>
                        <td class="len80 left">开票状态:</td>
                        <td>
                            <select name="salesInvoice.isinvoicebill" class="len150" disabled="disabled">
								<option value="0" <c:if test="${salesInvoice.isinvoicebill=='0'}">selected="selected"</c:if>>未开票</option>
								<option value="1" <c:if test="${salesInvoice.isinvoicebill=='1'}">selected="selected"</c:if>>已开票</option>
                            </select>
                        </td>
	    				<%--<td class="len80 left">申请状态</td>--%>
	    				<%--<td>--%>
	    					<%--<select id="salesInvoice-select-applytype" name="salesInvoice.applytype" disabled="disabled" class="len150">--%>
	    						<%--<option value="1" <c:if test="${salesInvoice.applytype=='1'}">selected="selected"</c:if>>开票</option>--%>
	    						<%--<option value="2" <c:if test="${salesInvoice.applytype=='2'}">selected="selected"</c:if>>核销</option>--%>
<%--<!--	    						<option value="3" <c:if test="${salesInvoice.applytype=='3'}">selected="selected"</c:if>>开票后核销</option>-->--%>
	    					<%--</select>--%>
	    				<%--</td>--%>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">发票类型:</td>
	    				<td>
	    					<select name="salesInvoice.invoicetype" class="len150">
	    						<option value=""></option>
	    						<c:forEach items="${invoicetype }" var="list">
								<option value="${list.code }" <c:if test="${list.code == salesInvoice.invoicetype}">selected="selected"</c:if>>${list.codename }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">发票号:</td>
	    				<td>
	    					<input type="text" id="salesInvoice-invoiceno" name="salesInvoice.invoiceno" class="len150" value="${salesInvoice.invoiceno }" autocomplete="off"/>
	    				</td>
	    				<td class="len80 left">发票代码:</td>
	    				<td>
	    					<input type="text" id="salesInvoice-invoicecode" name="salesInvoice.invoicecode" class="len150" value="${salesInvoice.invoicecode }" autocomplete="off"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">销售部门:</td>
	    				<td>
	    					<select id="storage-otherEnter-deptid" class="len150" disabled="disabled">
	    						<option value=""></option>
	    						<c:forEach items="${deptList }" var="list">
								<option value="${list.id }" <c:if test="${list.id == salesInvoice.salesdept}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" id="account-salesInvoice-salesdept" name="salesInvoice.salesdept" class="len130" value="${salesInvoice.salesdept }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">客户业务员:</td>
	    				<td>
	    					<input type="text" id="account-salesInvoice-salesuser" class="len150" value="<c:out value="${salesInvoice.salesusername }"></c:out>" readonly="readonly"/>
	    					<input type="hidden"  name="salesInvoice.salesuser" value="${salesInvoice.salesuser }" readonly="readonly"/>
	    				</td>
                        <td class="len100 left">备注：</td>
                        <td style="text-align: left">
                            <input type="text" name="salesInvoice.remark" style="width: 150px;" value="<c:out value="${salesInvoice.remark }"></c:out>" autocomplete="off"/>
                        </td>
	    			</tr>
	    			<!--  
	    			<tr>
	    				<td class="len80 left">结算方式:</td>
	    				<td>
	    					<select name="salesInvoice.settletype" class="len136">
	    						<c:forEach items="${settletype }" var="list">
								<option value="${list.id }" <c:if test="${list.id == salesInvoice.settletype}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">是否折扣:</td>
	    				<td>
	    					<select id="account-salesInvoice-isdiscount" class="len136" name="salesInvoice.isdiscount">
	    						<option value="0" <c:if test="${salesInvoice.isdiscount=='0'}">selected="selected"</c:if>>否</option>
	    						<option value="1" <c:if test="${salesInvoice.isdiscount=='1'}">selected="selected"</c:if>>是</option>
	    					</select>
	    				</td>
	    			</tr>-->
	    			<%--<tr>--%>
	    				<%--<td class="len100 left">备注：</td>--%>
	    				<%--<td colspan="5" style="text-align: left">--%>
	    					<%--<input type="text" name="salesInvoice.remark" style="width: 656px;" value="<c:out value="${salesInvoice.remark }"></c:out>" autocomplete="off"/>--%>
	    				<%--</td>--%>
	    			<%--</tr>--%>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false,split:true">
	    		<table id="account-datagrid-salesInvoiceAddPage"></table>
	    	</div>
	    	<input type="hidden" id="account-salesInvoice-salesInvoiceDetail" name="detailJson" value="<c:out value="${detailList }"/>" />
	    	<input type="hidden" id="account-salesInvoice-salesInvoiceDetail-del" name="delgoodsids"/>
	    	<input type="hidden" id="account-saveaudit-salesInvoiceDetail" name="saveaudit" value="save"/>
	  		<input type="hidden" id="account-salesInvoice-printlimit" value="${printlimit}" />
	  		<input type="hidden" id="account-salesInvoice-printtimes" value="${salesInvoice.printtimes}" />	
	    </form>
    </div>
     
    <div id="account-contextMenu-salesInvoicePage">
    	<!-- 
    	<div id="account-addRow-salesInvoiceDiscountPage" data-options="iconCls:'icon-add'">打折</div> -->
    	<div id="account-deleteRow-salesInvoiceDiscountPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="account-dialog-salesInvoiceAddPage"></div>
    <div id="account-dialog-discountPage"></div>
    <script type="text/javascript">
		var SID_footerobject = null;
    	$(function(){
    		$('#account-contextMenu-salesInvoicePage').menu({  
			    onClick:function(item){  
			    	var flag = item.disabled;
					if(flag==true){
						return false;
					}
					if(item.text=="打折"){
						var isdiscount = $("#account-salesInvoice-isdiscount").val();
						if(isdiscount=="1"){
							discountSet();
						}
					}else if(item.text=="删除"){
						removeDetail();
					}
			    }  
			});
    		$("#account-form-salesInvoiceAdd").form({  
			    onSubmit: function(){  
			    	//var json = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
					//$("#account-salesInvoice-salesInvoiceDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		if(json.auditflag){
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#account-salesInvoice-status").val("3");
			    			$("#account-buttons-salesInvoicePage").buttonWidget("setDataID",{id:'${salesInvoice.id}',state:'3',type:'view'});
			    			$("#account-buttons-salesInvoicePage").buttonWidget("enableButton", 'button-cancel');
							$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem", 'button-push');
    						$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem", 'button-rebate');
			    			$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","mbutton-print");
			    			$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","mbutton-preview");
			    			if(json.hasblance==1){
		        				$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","print-blance-button");
		        				$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","printview-blance-button");
		        			}
			    		}else{
			    			$.messager.alert("提醒","保存成功");
			    		}
			    		<c:if test="${salesInvoice.status=='5' }">
	        				$("#account-panel-salesInvoicePage").panel({
								href:'account/receivable/salesInvoiceEditPage.do?id=${salesInvoice.id }',
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
				    	</c:if>
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
			$("#account-datagrid-salesInvoiceAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			<%--data:JSON.parse('${detailList}'),--%>
				url:'account/receivable/getSalesInvoiceDetailList.do?id=${salesInvoice.id }',
				pagination:true,
				pageSize:500,
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#account-datagrid-salesInvoiceAddPage").datagrid('selectRow', rowIndex);
                    $("#account-contextMenu-salesInvoicePage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#account-datagrid-salesInvoiceAddPage").datagrid('appendRow',{});
	            		}
   					}
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SID_footerobject = footerrows[0];
						countTotal();
					}
    			}
    		}).datagrid('columnMoving'); 
    		$("#account-salesInvoice-customerid").customerWidget({
    			name:'t_account_sales_invoice',
	    		width:130,
				col:'customerid',
				singleSelect:true,
				required:true
    		});
    		$("#account-salesInvoice-invoicecustomerid").change(function(){
    			$("#account-salesInvoice-invoicecustomername").val($("#account-salesInvoice-invoicecustomerid").find("option:selected").text());
    		});
    		$("#account-salesInvoice-isdiscount").change(function(){
    			var isdiscount = $(this).val();
    			if(isdiscount=='1'){
    				$("#account-contextMenu-salesInvoicePage").menu('enableItem','#account-addRow-salesInvoiceDiscountPage');
    				discountSet();
    			}else{
    				var rows =  $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
		    		for(var i=0;i<rows.length;i++){
		    			if(rows[i].goodsid!=null){
		    				rows[i].discountamount = 0;
		    			}
		    		}
		    		$("#account-datagrid-salesInvoiceAddPage").datagrid('loadData',rows);
		    		countTotal();
		    		$("#account-contextMenu-salesInvoicePage").menu('disableItem','#account-addRow-salesInvoiceDiscountPage');
    			}
    		});
    	});
    	function discountSet(){
    		$('#account-dialog-discountPage').dialog({  
			    title: '折扣金额计算',  
			    width: 420,  
			    height: 150,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,
			    cache: false,
			    href: 'account/receivable/showSalesInvoiceDiscountPage.do',  
			    modal: true,
			    buttons:[
			    	{  
	                    text:'确定',  
	                    iconCls:'button-save',
	                    plain:true,
	                    handler:function(){  
	                    	var discount = $("#account-salesInvoice-discount").numberbox("getValue");
	                    	var discountrate = Number(discount)/100;
	                    	var rows =  $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
				    		for(var i=0;i<rows.length;i++){
				    			if(rows[i].goodsid!=null){
				    				rows[i].discountamount = Number(rows[i].taxamount)*discountrate;
				    			}
				    		}
				    		$("#account-datagrid-salesInvoiceAddPage").datagrid('loadData',rows);
				    		countTotal();
				    		$('#account-dialog-discountPage').dialog("close");
	                    }  
	                }
			    ]
			});
			$('#account-dialog-discountPage').dialog("open");
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $("#account-datagrid-salesInvoiceAddPage").datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
		    		var delgoodsid = $("#account-salesInvoice-salesInvoiceDetail-del").val();
		    		if(delgoodsid !=null && delgoodsid!=""){
		    			delgoodsid += ","+row.goodsid;
		    		}else{
		    			delgoodsid = row.goodsid;
		    		}
		    		$("#account-salesInvoice-salesInvoiceDetail-del").val(delgoodsid);
			   		var rowIndex = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRowIndex', row);
			   		$("#account-datagrid-salesInvoiceAddPage").datagrid('deleteRow', rowIndex);
			   		countTotal(); 
			   		var rows = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
			   		var index = -1;
			   		for(var i=0; i<rows.length; i++){
			   			if(rows[i].goodsid != undefined){
			   				index = i;
			   				break;
			  			}
			   		}
		    	}
	    	});	
    	}
    	
    	//控制按钮状态
    	$("#account-buttons-salesInvoicePage").buttonWidget("setDataID",{id:'${salesInvoice.id}',state:'${salesInvoice.status}',type:'edit'});
    	$("#account-hidden-billid").val("${salesInvoice.id}");
		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem","mbutton-print");
		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem","mbutton-preview");
		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem","print-blance-button");
		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem","printview-blance-button");
    	<c:if test="${salesInvoice.status!='2' }">
    		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem", 'button-push');
    		$("#account-buttons-salesInvoicePage").buttonWidget("disableMenuItem", 'button-rebate');
    	</c:if>
    	<c:if test="${salesInvoice.status=='2' }">
    		$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem", 'button-push');
    		$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem", 'button-rebate');
    		$("#account-buttons-salesInvoicePage").buttonWidget("disableButton", 'button-cancel');
    	</c:if>
    	<c:if test="${salesInvoice.status=='3' or salesInvoice.status=='4'  }">
    		$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem", 'button-cancel');
			$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","print-bill-button");
			$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","mbutton-preview");
			<c:if test="${hasblance=='1'}">
				$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","print-blance-button");
				$("#account-buttons-salesInvoicePage").buttonWidget("enableMenuItem","printview-blance-button");
			</c:if>
    	</c:if>
    	<%--<c:if test="${salesInvoice.applytype=='1'}">--%>
    		<%--$("#account-buttons-salesInvoicePage").buttonWidget("disableButton", 'button-cancel');--%>
    	<%--</c:if>--%>
    	<c:if test="${salesInvoice.status=='5' }">
        $("#account-buttons-salesInvoicePage").buttonWidget("disableButton", 'button-cancel');
    		$("#account-buttons-salesInvoicePage").buttonWidget("enableButton", 'button-save');
    		$("#account-buttons-salesInvoicePage").buttonWidget("enableButton", 'button-delete');
    	</c:if>
    </script>
  </body>
</html>
