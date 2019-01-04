<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购退货出库单单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-purchaseRejectOutAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="purchaseRejectOut.id" value="${purchaseRejectOut.id}" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" value="${purchaseRejectOut.businessdate }" name="purchaseRejectOut.businessdate"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-purchaseRejectOut-status-select" disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == purchaseRejectOut.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" id="storage-purchaseRejectOut-status" name="purchaseRejectOut.status" value="${purchaseRejectOut.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商：</td>
	    				<td colspan="3" style="text-align: left;"><input type="text" id="storage-purchaseRejectOut-supplierid" name="purchaseRejectOut.supplierid" value="${purchaseRejectOut.supplierid }" text="<c:out value="${purchaseRejectOut.suppliername }"></c:out>" style="width: 320px;" readonly="readonly"/>
	    					<span id="purchase-supplier-showid-purchaseRejectOut" style="margin-left:5px;line-height:25px;">编号：${purchaseRejectOut.supplierid}</span>
	    				</td>
                        <td class="len80 left">来源类型：</td>
                        <td>
                            <input id="storage-purchaseRejectOut-sourcetype" type="text" value="${purchaseRejectOut.sourcetype }"/>
                            <input type="hidden" name="purchaseRejectOut.sourcetype" value="${purchaseRejectOut.sourcetype }"/>
                            <input type="hidden" id="storage-purchaseRejectOut-sourceid" name="purchaseRejectOut.sourceid" value="${purchaseRejectOut.sourceid }"/>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">退货仓库：</td>
	    				<td>
	    					<select id="storage-purchaseRejectOut-storageid" class="len150" disabled="disabled">
	    						<option></option>
	    						<c:forEach items="${storageList }" var="list">
								<option value="${list.id }" <c:if test="${list.id==purchaseRejectOut.storageid}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input id="storage-purchaseRejectOut-storageid-hidden" type="hidden" name="purchaseRejectOut.storageid" value="${purchaseRejectOut.storageid }"/>
	    				</td>
	    				<td class="len80 left">采购部门：</td>
	    				<td>
	    					<select id="storage-purchaseRejectOut-buydeptid" class="len136" disabled="disabled">
	    						<option></option>
	    						<c:forEach items="${deptList }" var="list">
								<option value="${list.id }" <c:if test="${list.id==purchaseRejectOut.buydeptid}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="purchaseRejectOut.buydeptid" value="${purchaseRejectOut.buydeptid }"/>
	    				</td>
	    				<td class="len80 left">采购员：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseRejectOut-buyuserid" name="purchaseRejectOut.buyuserid" style="width:135px;" value="${purchaseRejectOut.buyuserid }" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left;"><input type="text" name="purchaseRejectOut.remark" style="width: 665px;" value="<c:out value="${purchaseRejectOut.remark }"></c:out>"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-purchaseRejectOutDetail"/>
	    		<input type="hidden" name="saveaudit" id="storage-purchaseEnter-saveaudit" value="save" />
	    		<table id="storage-datagrid-purchaseRejectOutAddPage"></table>
	    	</div>
	    </form>
    	<input type="hidden" id="storage-printtimes-purchaseRejectOutAddPage" value="${purchaseRejectOut.printtimes }"/>
    	<input type="hidden" id="storage-printlimit-purchaseRejectOutAddPage" value="${printlimit }"/>
    	<input type="hidden" id="storage-ischeck-purchaseRejectOutAddPage" value="${purchaseRejectOut.ischeck }"/>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-purchaseRejectOutEditPage" style="display: none;">
    	<!-- <div id="storage-addRow-purchaseRejectOutEditPage" data-options="iconCls:'icon-add'">添加</div> -->
    	<div id="storage-editRow-purchaseRejectOutEditPage" data-options="iconCls:'button-edit'">编辑</div>
    	<%--<div id="storage-removeRow-purchaseRejectOutEditPage" data-options="iconCls:'button-delete'">删除</div>--%>
    </div>
    <div id="storage-dialog-purchaseRejectOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-purchaseRejectOutAddPage").datagrid({ //销售商品明细信息编辑
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
    				$("#storage-datagrid-purchaseRejectOutAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-purchaseRejectOutEditPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    				<c:if test="${purchaseRejectOut.sourcetype=='0'}">
    					beginAddDetail();
    				</c:if>
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-purchaseRejectOutAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-purchaseRejectOutAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#storage-purchaseRejectOut-storageid").change(function(){
    			$("#storage-purchaseRejectOut-storageid-hidden").val($(this).val());
    		});
            $("#storage-purchaseRejectOut-sourcetype").widget({
                name:'t_storage_purchasereject_out',
                col:'sourcetype',
                singleSelect:true,
                width:136,
                disabled:true
            });
    		$("#storage-purchaseRejectOut-buyuserid").widget({
				name:'t_base_buy_supplier',
				col:'buyuserid',
				width:135,
	    		async:false,
				singleSelect:true,
				onlyLeafCheck:false
			}); 	
    		$("#storage-purchaseRejectOut-supplierid").supplierWidget({
    			required:true,
	    		readonly:true,
	    		onSelect:function(data){
    				$("#storage-purchaseRejectOut-buydeptid").val(data.buydeptid);
    				/*
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.buydeptid}, function(json){
	    				if(json.length>0){
	    					$("#storage-purchaseRejectOut-buyuserid").html("");
	    					$("#storage-purchaseRejectOut-buyuserid").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#storage-purchaseRejectOut-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#storage-purchaseRejectOut-buyuserid").val(data.buyuserid);
	    				}	
	    			});
	    			*/
	    			$("#storage-purchaseRejectOut-buyuserid").widget('setValue',data.buyuserid);
	    			$("#storage-purchaseEnterBill-remark").focus();
	    		},
	    		onClear:function(){
		    		$("#storage-purchaseRejectOut-buyuserid").widget('clear');
		  			$("#storage-purchaseRejectOut-buydeptid").val("");
	    		}
    		});
    		/*
    		$("#storage-purchaseRejectOut-buydeptid").change(function(){
    			var deptid = $(this).val();
    			$.ajax({   
		            url :'basefiles/getPersonnelListByDeptid.do',
		            type:'post',
		            data:{deptid:deptid},
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	if(json.length>0){
	    					$("#storage-purchaseRejectOut-buyuserid").html("");
	    					for(var i=0;i<json.length;i++){
	    						$("#storage-purchaseRejectOut-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
	    				}
		            }
		        });
    		});
    		*/
    		$("#storage-addRow-purchaseRejectOutEditPage").click(function(){
    			beginAddDetail();
    		});
    		$("#storage-editRow-purchaseRejectOutEditPage").click(function(){
    			beginEditDetail();
    		});
    		$("#storage-removeRow-purchaseRejectOutEditPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-purchaseRejectOutAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-purchaseRejectOutDetail").val(JSON.stringify(json));
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
			    			$("#storage-panel-purchaseRejectOutPage").panel('refresh', 'storage/purchaseRejectOutViewPage.do?id=${purchaseRejectOut.id}');
		            		//$("#storage-purchaseEnter-status").val("4");
		            		//$("#storage-buttons-purchaseRejectOutPage").buttonWidget("setDataID",{id:'${purchaseRejectOut.id}',state:'4',type:'view'});
		            		//$("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-preview");
							//$("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-print");
							//刷新列表
			    	  		//tabsWindowURL("/storage/showPurchaseRejectOutListPage.do").$("#storage-datagrid-purchaseRejectOutPage").datagrid('reload');
			    	  		//关闭当前标签页
			    	  		//top.closeNowTab();
			    		}else{
			    			$.messager.alert("提醒","保存成功.");
			    		}
			    		tabsWindowURL("/storage/showPurchaseRejectOutListPage.do").$("#storage-datagrid-purchaseRejectOutPage").datagrid('reload');
			    		
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    	});
    	//控制按钮状态
    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("setDataID",{id:'${purchaseRejectOut.id}',state:'${purchaseRejectOut.status}',type:'edit'});
    	$("#storage-hidden-billid").val("${purchaseRejectOut.id}");
    	<c:if test="${purchaseRejectOut.sourcetype=='0'}">
	    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseRejectOut.sourcetype!='0'}">
	    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseRejectOut.sourcetype=='1'}">
    		 $("#storage-contextMenu-purchaseRejectOutEditPage").menu('disableItem','#storage-addRow-purchaseRejectOutEditPage');
			 $("#storage-contextMenu-purchaseRejectOutEditPage").menu('disableItem','#storage-removeRow-purchaseRejectOutEditPage');
    	</c:if>
    	<c:choose>
	       	<c:when test="${purchaseRejectOut.status =='3' or purchaseRejectOut.status =='4' }">
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-preview");
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-print");
	       	</c:when>
	       	<c:otherwise>           		
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableButton","button-preview");
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableButton","button-print");
	       	</c:otherwise>
	     </c:choose> 
    </script>
  </body>
</html>
