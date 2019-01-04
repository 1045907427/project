<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货入库单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-saleRejectEnterAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="saleRejectEnter.id" value="${saleRejectEnter.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="storage-saleRejectEnter-businessdate" type="text" class="len130" value="${saleRejectEnter.businessdate }" name="saleRejectEnter.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td>
	    					<select id="storage-saleRejectEnter-status" disabled="disabled" style="width: 130px">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == saleRejectEnter.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" name="saleRejectEnter.status" value="${saleRejectEnter.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3" style="text-align: left;">
	    					<input type="text" id="storage-saleRejectEnter-customerid" name="saleRejectEnter.customerid" value="${saleRejectEnter.customerid}" text="<c:out value="${saleRejectEnter.customername}"></c:out>" style="width: 300px;" <c:if test="${saleRejectEnter.sourcetype=='2'}">readonly="readonly"</c:if>/>
	    					<span id="storage-supplier-showid-saleRejectEnter" style="margin-left:5px;line-height:25px;">编号：${saleRejectEnter.customerid}</span>
	    					<input type="hidden" id="storage-saleRejectEnter-hidden-customerid" value="${saleRejectEnter.customerid }"/>
	    				</td>
	    				<td class="len80 left">司机：</td>
	    				<td>
	    					<input id="sales-driverid-saleRejectEnterAddPage" class="len136" name="saleRejectEnter.driverid" value="${saleRejectEnter.driverid }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
                            <input type="text" name="saleRejectEnter.storageid" id="sales-storage-saleRejectEnterAddPage" value="${saleRejectEnter.storageid}" disabled="disabled"/>
	    					<%--<select name="saleRejectEnter.storageid" class="len136" disabled="disabled">--%>
	    						<%--<c:forEach items="${storageList }" var="list">--%>
								<%--<option value="${list.id }" <c:if test="${list.id == saleRejectEnter.storageid}">selected="selected"</c:if>>${list.name }</option>--%>
	    						<%--</c:forEach>--%>
	    					<%--</select>--%>
	    				</td>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<select id="sales-salesDept-saleRejectEnterAddPage" name="saleRejectEnter.salesdept" class="len136"  <c:if test="${saleRejectEnter.sourcetype!='0'}">disabled="disabled"</c:if>>
	    						<option value=""></option>
	    						<c:forEach items="${salesDept }" var="list">
								<option value="${list.id }" <c:if test="${list.id == saleRejectEnter.salesdept}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input id="sales-salesMan-saleRejectEnterAddPage" class="len130" name="saleRejectEnter.salesuser" value="<c:out value="${saleRejectEnter.saleusername }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len150" disabled="disabled">
	    						<option value="0" <c:if test="${saleRejectEnter.sourcetype=='0'}">selected="selected"</c:if>>无</option>
	    						<option value="1" <c:if test="${saleRejectEnter.sourcetype=='1'}">selected="selected"</c:if>>售后退货</option>
	    						<option value="2" <c:if test="${saleRejectEnter.sourcetype=='2'}">selected="selected"</c:if>>直退退货</option>
	    					</select>
	    					<input type="hidden" id="storage-saleRejectEnter-sourcetype" name="saleRejectEnter.sourcetype" value="${saleRejectEnter.sourcetype}"/>
	    					<input type="hidden" id="storage-saleRejectEnter-sourceid" name="saleRejectEnter.sourceid" value="${saleRejectEnter.sourceid}"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3" style="text-align: left;"> <input type="text" style="width: 395px;" name="saleRejectEnter.remark" value="<c:out value="${saleRejectEnter.remark }"></c:out>"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-saleRejectEnterDetail" />
	    		<table id="storage-datagrid-saleRejectEnterAddPage"></table>
	    		<input type="hidden" name="saveaudit" id="storage-saveaudit-saleRejectEnterDetail" value="save"/>
	    	</div>
	    </form>
    	<input type="hidden" id="storage-printtimes-saleRejectEnterAddPage" value="${saleRejectEnter.printtimes }"/>
    	<input type="hidden" id="storage-printlimit-saleRejectEnterAddPage" value="${printlimit }"/>
    	<input type="hidden" id="storage-status-saleRejectEnterAddPage" value="${ saleRejectEnter.status}"/>
	    	<input type="hidden" id="storage-ischeck-saleRejectEnterAddPage" value="${ saleRejectEnter.ischeck}"/>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-saleRejectEnterAddPage" style="display: none;">
    	<%--<div id="sales-addRow-saleRejectEnterAddPage" data-options="iconCls:'button-add'">添加</div>--%>
    	<div id="sales-editRow-saleRejectEnterAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-saleRejectEnterAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-saleRejectEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-saleRejectEnterAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-saleRejectEnterAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    				<c:if test="${saleRejectEnter.sourcetype=='0'}">
    					beginAddDetail();
    				</c:if>
    				}else{
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#storage-saleRejectEnter-customerid").customerWidget({ //客户参照窗口
    			required:true,
    			onSelect:function(data){
    				$("#sales-salesDept-saleRejectEnterAddPage").val(data.salesdeptid);
    				$("input[name='saleRejectEnter.remark']").focus();
    				$("#storage-supplier-showid-saleRejectEnter").html("编码:"+data.id);
    				//客户变更后 更新明细价格数据
    				changeGoodsPrice();
    			},
    			onClear:function(){
    				$("#sales-salesDept-saleRejectEnterAddPage").val("");
    			}
    		});
            //入库仓库
            $("#sales-storage-saleRejectEnterAddPage").widget({
                width:150,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false
            });
    		$("#sales-driverid-saleRejectEnterAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:130,
				singleSelect:true
    		});
    		$("#sales-salesDept-saleRejectEnterAddPage").change(function(){
    			var deptid = $(this).val();
    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: deptid}, function(json){
    				if(json.length>0){
    					$("#sales-salesMan-saleRejectEnterAddPage").html("");
    					for(var i=0;i<json.length;i++){
    						$("#sales-salesMan-saleRejectEnterAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
    					}
    				}	
    			});
    		});
    		$("#sales-addRow-saleRejectEnterAddPage").click(function(){
    			var flag = $("#sales-contextMenu-saleRejectEnterAddPage").menu('getItem','#sales-addRow-saleRejectEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		$("#sales-editRow-saleRejectEnterAddPage").click(function(){
    			var flag = $("#sales-contextMenu-saleRejectEnterAddPage").menu('getItem','#sales-editRow-saleRejectEnterAddPage').disabled;
				if(flag){
					return false;
				}
				var row = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getSelected');
				<c:if test="${saleRejectEnter.sourcetype=='0'}">
	    			beginEditDetail();
   				</c:if>
	    		<c:if test="${saleRejectEnter.sourcetype!='0'}">
   					if(row.goodsid == undefined){
		    		}else{
		    			beginEditDetail();
		    		}
   				</c:if>
    		});
    		$("#sales-removeRow-saleRejectEnterAddPage").click(function(){
    			var flag = $("#sales-contextMenu-saleRejectEnterAddPage").menu('getItem','#sales-removeRow-saleRejectEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-saleRejectEnterAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-saleRejectEnterDetail").val(JSON.stringify(json));
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
			    			$.messager.alert("提醒","保存审核成功");
			    			$("#storage-panel-saleRejectEnterPage").panel("refresh");
			    			//刷新列表
			    	  		tabsWindowURL("/storage/showSaleRejectEnterListPage.do").$("#storage-datagrid-saleRejectEnterPage").datagrid('reload');
			    	  		//关闭当前标签页
			    	  		top.closeNowTab();
			    			//$("#storage-saleRejectEnter-status").val(4);
			    			//$("#storage-buttons-saleRejectEnterPage").buttonWidget("setDataID",{id:'${saleRejectEnter.id}',state:'4',type:'view'});
			    		}else{
			    			var saveaudit = $("#storage-saveaudit-saleRejectEnterDetail").val();
			    			if(saveaudit=="savecheck"){
			    				$.messager.alert("提醒","验收成功");
			    				$("#storage-panel-saleRejectEnterPage").panel("refresh");
			    			}else{
			    				$.messager.alert("提醒","保存成功");
			    			}
			    		}
			    		
			    	}else{
			    		$.messager.alert("提醒","操作失败</br>"+json.msg);
			    	}
			    }  
			}); 
    	});
    	//控制按钮状态
    	$("#storage-buttons-saleRejectEnterPage").buttonWidget("setDataID",{id:'${saleRejectEnter.id}',state:'${saleRejectEnter.status}',type:'edit'});
    	$("#storage-hidden-billid").val("${saleRejectEnter.id}");
    	var $wareList = $("#storage-datagrid-saleRejectEnterAddPage"); //商品datagrid的div对象
    	<c:if test="${saleRejectEnter.sourcetype=='2'}">
    		 $("#sales-contextMenu-saleRejectEnterAddPage").menu('disableItem','#sales-addRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('disableItem','#sales-removeRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('disableItem','#sales-editRow-saleRejectEnterAddPage');
    	</c:if>
    	<c:if test="${saleRejectEnter.sourcetype=='0'}">
    		 $("#sales-contextMenu-saleRejectEnterAddPage").menu('enableItem','#sales-addRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('enableItem','#sales-removeRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('enableItem','#sales-editRow-saleRejectEnterAddPage');
    	</c:if>
    	<c:if test="${saleRejectEnter.status=='3'}">
    		 $("#sales-contextMenu-saleRejectEnterAddPage").menu('disableItem','#sales-addRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('disableItem','#sales-removeRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").menu('enableItem','#sales-editRow-saleRejectEnterAddPage');
			 $("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton", 'button-check');
    	</c:if>
    	<c:if test="${saleRejectEnter.status!='3'}">
			 $("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton", 'button-check');
    	</c:if>
		<c:if test="${saleRejectEnter.status!='4' || saleRejectEnter.ischeck!='1' }">
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton","button-print");
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton","button-preview");
		</c:if>
	    <c:if test="${saleRejectEnter.status=='4' && saleRejectEnter.ischeck=='1' }">
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton","button-print");
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton","button-preview");
		</c:if>
		<c:if test="${saleRejectEnter.sourcetype!='1'}">
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton","button-check");
		</c:if>
		//客户变更后 更新明细价格以及相关信息
    	function changeGoodsPrice(){
    		var oldcustomerid = $("#storage-saleRejectEnter-hidden-customerid").val();
    		var customerid = $("#storage-saleRejectEnter-customerid").customerWidget("getValue");
    		$("#storage-saleRejectEnter-hidden-customerid").val(customerid);
    		if(oldcustomerid!=null && oldcustomerid!="" && oldcustomerid!=customerid){
    			loading("客户变更，明细价格调整中");
    			var rows = $wareList.datagrid('getRows');
    			var count = 0;
	    		for(var i=0; i<rows.length; i++){
	    			var goodsid = rows[i].goodsid;
	    			var num = rows[i].unitnum;
	    			var date = $("input[name='saleorder.businessdate']").val();
	    			if(goodsid!=null && goodsid!=""){
	    				var row = rows[i];
	    				$.ajax({
							url:'sales/countSalesGoodsByCustomer.do',
							dataType:'json',
							type:'post',
							data:{customerid:customerid,goodsid:goodsid,num:num,date:date},
							async:false,
							success:function(json){
								var rowIndex = $wareList.datagrid("getRowIndex",row);
								row.taxprice = json.taxprice;
								row.notaxprice = json.notaxprice;
								row.taxamount = json.taxamount;
								row.notaxamount = json.notaxamount;
								row.tax = json.tax;
								$wareList.datagrid('updateRow',{index:rowIndex, row:row});
							}
						});
	    			}
	    			count ++;
	    		}
	    		if(count>0){
	    			$("#sales-customer-hidden-orderAddPage").val(customerid);
	    			$.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");
	    		}
	    		loaded();
	    		countTotal();
    		}
    	}
    </script>
  </body>
</html>
