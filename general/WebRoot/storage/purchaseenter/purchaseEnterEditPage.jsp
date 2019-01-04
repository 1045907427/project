<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>采购入库单</title>
	<%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<form id="storage-form-purchaseEnterAdd" action="" method="post">
		<div data-options="region:'north',border:false" style="height: 140px;">
			<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
				<tr>
					<td class="len80 left">编号：</td>
					<td class="len165"><input class="len150 easyui-validatebox" name="purchaseEnter.id" readonly='readonly' value="${purchaseEnter.id}"/></td>
					<td class="len80 left">业务日期：</td>
					<td class="len165"><input type="text" id="storage-purchaseEnter-businessdate" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${purchaseEnter.businessdate }" name="purchaseEnter.businessdate" /></td>
					<td class="len80 left">状态：</td>
					<td class="len165">
						<select id="storage-purchaseEnter-status" disabled="disabled" class="len136">
							<c:forEach items="${statusList }" var="list">
								<c:choose>
									<c:when test="${list.code == purchaseEnter.status}">
										<option value="${list.code }" selected="selected">${list.codename }</option>
									</c:when>
									<c:otherwise>
										<option value="${list.code }">${list.codename }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<input type="hidden" name="purchaseEnter.status" value="${purchaseEnter.status }"/>
					</td>
				</tr>
				<tr>
					<td class="len80 left">供应商:</td>
					<td colspan="3" style="text-align: left">
						<input type="text" id="storage-purchaseEnter-supplierid" name="purchaseEnter.supplierid" style="width: 320px;" value="${purchaseEnter.supplierid }" text="<c:out value="${purchaseEnter.suppliername }"></c:out>" readonly="readonly"/>
						<span id="purchase-supplier-showid-purchaseEnter" style="margin-left:5px;line-height:25px;">编号：${purchaseEnter.supplierid }</span>
					</td>
					<td class="len80 left">入库仓库：</td>
					<td>
						<input id="storage-purchaseEnter-storageid" type="text" name="purchaseEnter.storageid" value="${purchaseEnter.storageid}"/>
					</td>
				</tr>
				<tr>
					<td class="len80 left">采购部门：</td>
					<td>
						<select id="storage-purchaseEnter-buydeptid" class="len150" disabled="disabled">
							<option></option>
							<c:forEach items="${deptList }" var="list">
								<option value="${list.id }" <c:if test="${list.id==purchaseEnter.buydeptid}">selected="selected"</c:if>>${list.name }</option>
							</c:forEach>
						</select>
						<input type="hidden" name="purchaseEnter.buydeptid" value="${purchaseEnter.buydeptid }"/>
					</td>
					<td class="len80 left">采购人员：</td>
					<td>
						<input type="text" id="storage-purchaseEnter-buyuserid" name="purchaseEnter.buyuserid" style="width:130px;" value="${purchaseEnter.buyuserid }" readonly="readonly" />
					</td>
					<td class="len80 left">来源类型：</td>
					<td>
						<input id="storage-purchaseEnter-sourcetype" type="text"  value="${purchaseEnter.sourcetype }"/>
						<input type="hidden" id="storage-purchaseEnter-sourceid" name="purchaseEnter.sourceid" value="${purchaseEnter.sourceid }"/>
						<input type="hidden" name="purchaseEnter.sourcetype" value="${purchaseEnter.sourcetype }"/>
					</td>
				</tr>
				<tr><td>手工单号:</td>
					<td><input id="storage-purchaseEnter-field04" type="text" name="purchaseEnter.field04"  style="width:150px" value="${purchaseEnter.field04 }"/></td>
					<td class="len80 left">备注：</td>
					<td colspan="3" style="text-align: left">
						<input type="text" name="purchaseEnter.remark" style="width: 400px;" value="<c:out value="${purchaseEnter.remark }"></c:out>"/>
					</td>
				</tr>
				<tr>
			</table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="storage-datagrid-purchaseEnterAddPage"></table>
		</div>
		<input type="hidden" id="storage-purchaseEnter-purchaseEnterDetail" name="detailJson" />
		<input type="hidden" id="storage-purchaseEnter-saveaudit" name="saveaudit" value="save"/>
	</form>
	<input type="hidden" id="storage-purchaseEnter-printtimes" value="${purchaseEnter.printtimes}" />
	<input type="hidden" id="storage-purchaseEnter-printlimit" value="${printlimit }"/>
</div>
<div class="easyui-menu" id="storage-contextMenu-purchaseEnterEditPage" style="display: none;">
	<c:if test="${purchaseEnter.sourcetype=='0'}">
		<div id="storage-addRow-purchaseEnterEditPage" data-options="iconCls:'button-add'">添加</div>
	</c:if>
	<c:if test="${purchaseEnter.sourcetype=='1' && 1!=1}">
		<div id="storage-editLocation-purchaseEnterEditPage" data-options="iconCls:'button-add'">指定库位</div>
	</c:if>
	<security:authorize url="/storage/purchaseEnterAddGift.do">
		<div id="storage-addGift-purchaseEnterEditPage" data-options="iconCls:'button-add'">添加赠品</div>
	</security:authorize>
	<div id="storage-editRow-purchaseEnterEditPage" data-options="iconCls:'button-edit'">编辑</div>
	<div id="storage-removeRow-purchaseEnterEditPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="storage-dialog-purchaseEnterAddPage"></div>
<div id="storage-dialog-batchno-purchaseEnterAddPage"></div>
<div id="storage-editlocation-dialog-purchaseEnterAddPage"></div>
<script type="text/javascript">
    $(function(){
        $("#storage-datagrid-purchaseEnterAddPage").datagrid({ //采购入库单明细信息编辑
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: true,
            data:JSON.parse('${detailList}'),
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $("#storage-datagrid-purchaseEnterAddPage").datagrid('selectRow', rowIndex);
                $("#storage-contextMenu-purchaseEnterEditPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onDblClickRow: function(rowIndex, rowData){
                if(rowData.goodsid == undefined){
                    <c:if test="${purchaseEnter.sourcetype=='0'}">
                    beginAddDetail("0");
                    </c:if>
                    <c:if test="${purchaseEnter.sourcetype=='1'}">
                    beginAddDetail("1");
                    </c:if>
                }
                else{
                    beginEditDetail(rowData);
                }
            },
            onLoadSuccess:function(data){
                if(data.rows.length<10){
                    var j = 10-data.rows.length;
                    for(var i=0;i<j;i++){
                        $("#storage-datagrid-purchaseEnterAddPage").datagrid('appendRow',{});
                    }
                }else{
                    $("#storage-datagrid-purchaseEnterAddPage").datagrid('appendRow',{});
                }
                countTotal();
            }
        }).datagrid('columnMoving');
        $("#storage-purchaseEnter-storageid").widget({
            name:'t_storage_purchase_enter',
            col:'storageid',
            singleSelect:true,
            width:136,
            required:true
        });
        $("#storage-purchaseEnter-sourcetype").widget({
            name:'t_storage_purchase_enter',
            col:'sourcetype',
            singleSelect:true,
            width:136,
            disabled:true
        });
        $("#storage-purchaseEnter-buyuserid").widget({
            name:'t_base_buy_supplier',
            col:'buyuserid',
            width:130,
            async:false,
            singleSelect:true,
            onlyLeafCheck:false
        });
        $("#storage-purchaseEnter-supplierid").supplierWidget({
            readonly:true,
            required:true,
            onSelect:function(data){
                $("#storage-purchaseEnter-buydeptid").val(data.buydeptid);
                $("#storage-purchaseEnter-buyuserid").widget('setValue',data.buyuserid);
            },
            onClear:function(){
                $("#storage-purchaseEnter-buyuserid").widget('clear');
                $("#storage-purchaseEnter-buydeptid").val("");
            }
        });
		/*
		 $("#storage-purchaseEnter-buydeptid").change(function(){
		 var deptid = $(this).val();
		 $.ajax({
		 url :'basefiles/getPersonnelListByDeptid.do',
		 type:'post',
		 data:{deptid:deptid},
		 dataType:'json',
		 async:false,
		 success:function(json){
		 if(json.length>0){
		 $("#storage-purchaseEnter-buyuserid").html("");
		 for(var i=0;i<json.length;i++){
		 $("#storage-purchaseEnter-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		 }
		 }
		 }
		 });
		 });
		 */
        //采购入库单明细添加
        $("#storage-addRow-purchaseEnterEditPage").click(function(){
            var flag = $("#storage-contextMenu-purchaseEnterEditPage").menu('getItem','#storage-addRow-purchaseEnterAddPage').disabled;
            if(flag){
                return false;
            }
            beginAddDetail("0");
        });
        //采购入库单明细添加
        $("#storage-addGift-purchaseEnterEditPage").click(function(){
            var flag = $("#storage-contextMenu-purchaseEnterEditPage").menu('getItem','#storage-addGift-purchaseEnterAddPage').disabled;
            if(flag){
                return false;
            }
            beginAddDetail("1");
        });
        $("#storage-editLocation-purchaseEnterEditPage").click(function(){
            var flag = $("#storage-contextMenu-purchaseEnterEditPage").menu('getItem','#storage-editLocation-purchaseEnterAddPage').disabled;
            if(flag){
                return false;
            }
            editGoodsLocation();
        });
        //采购入库单明细修改
        $("#storage-editRow-purchaseEnterEditPage").click(function(){
            var flag = $("#storage-contextMenu-purchaseEnterEditPage").menu('getItem','#storage-editRow-purchaseEnterAddPage').disabled;
            if(flag){
                return false;
            }
            var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
            if(row != null && row.goodsid != undefined){
                beginEditDetail(row);
            }
        });
        //采购入库单明细删除
        $("#storage-removeRow-purchaseEnterEditPage").click(function(){
            var flag = $("#storage-contextMenu-purchaseEnterEditPage").menu('getItem','#storage-removeRow-purchaseEnterAddPage').disabled;
            if(flag){
                return false;
            }
            removeDetail();
        });
        $("#storage-form-purchaseEnterAdd").form({
            onSubmit: function(){
                var json = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
                $("#storage-purchaseEnter-purchaseEnterDetail").val(JSON.stringify(json));
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
                        var msg = "";
                        if(json.purchaseEnterId!=null){
                            msg = "采购订单未完成，自动生成新的采购入库单："+json.purchaseEnterId;
                            $.messager.alert("提醒","审核成功,生成采购进货单:"+json.downid+"."+msg);
                        }else{
                            $.messager.alert("提醒","审核成功,生成采购进货单:"+json.downid);
                        }
                        $("#storage-purchaseEnter-status").val("3");
                        $("#storage-buttons-purchaseEnterPage").buttonWidget("setDataID",{id:'${purchaseEnter.id}',state:'3',type:'view'});
                        //刷新列表
                        tabsWindowURL("/storage/showPurchaseEnterListPage.do").$("#storage-datagrid-purchaseEnterPage").datagrid('reload');
                        //关闭当前标签页
                        top.closeNowTab();
                    }else{
                        if(json.msg!=null){
                            $.messager.alert("提醒","保存成功."+json.msg);
                        }else{
                            $.messager.alert("提醒","保存成功.");
                        }
                    }

                }else{
                    $.messager.alert("提醒","保存失败</br>"+json.msg);
                }
            }
        });
    });
    //控制按钮状态
    $("#storage-buttons-purchaseEnterPage").buttonWidget("setDataID",{id:'${purchaseEnter.id}',state:'${purchaseEnter.status}',type:'edit'});
    $("#storage-hidden-billid").val("${purchaseEnter.id}");
    <c:if test="${purchaseEnter.sourcetype=='0'}">
    $("#storage-buttons-purchaseEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
    </c:if>
    <c:if test="${purchaseEnter.sourcetype!='0'}">
    $("#storage-buttons-purchaseEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
    </c:if>
</script>
</body>
</html>
