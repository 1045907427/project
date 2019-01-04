<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="purchase-backid-returnOrderAddPage" value="${id }"/>
<div id="purchase-returnOrderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-returnOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="purchase-panel-returnOrderPage">
        </div>
    </div>
</div>
<div style="display:none">
    <div id="purchase-returnOrderAddPage-dialog-DetailOper"></div>
    <%--通用 --%>
    <div id="listPage-ReturnOrder-dialog-print">
        <form action="" id="listPage-ReturnOrder-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-ReturnOrder-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var returnOrder_type = '${type}';
    returnOrder_type = $.trim(returnOrder_type.toLowerCase());
    if (returnOrder_type == "") {
        returnOrder_type = 'add';
    }
    var returnOrder_url = "purchase/returnorder/returnOrderAddPage.do";
    if (returnOrder_type == "view" || returnOrder_type == "show" || returnOrder_type == "handle") {
        returnOrder_url = "purchase/returnorder/returnOrderViewPage.do?id=${id}";
    }
    if (returnOrder_type == "edit") {
        returnOrder_url = "purchase/returnorder/returnOrderEditPage.do?id=${id}";
    }
    if (returnOrder_type == "copy") {
        returnOrder_url = "purchase/returnorder/returnOrderCopyPage.do?id=${id}";
    }
    var pageListUrl = "/purchase/returnorder/returnOrderListPage.do";
    function returnOrder_tempSave_form_submit() {
        $("#purchase-form-returnOrderAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    returnOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#purchase-backid-returnOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-returnOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-returnOrderAddPage-status").val("1");
                    }
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function returnOrder_realSave_form_submit() {
        $("#purchase-form-returnOrderAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    returnOrder_RefreshDataGrid();
                    var saveaudit = $("#purchase-returnOrderAddPage-saveaudit").val();
                    if (saveaudit == "saveaudit") {
                        if (json.auditflag) {
                            if (json.billid && json.billid != "") {
                                $.messager.alert("提醒", "保存审核成功,并自动生成采购退货出库单，单据编号：" + json.billid);
                            } else {
                                $.messager.alert("提醒", "保存审核成功");
                            }
                            returnOrder_RefreshDataGrid();
                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + json.backid);
                        } else {
                            if (json.msg) {
                                $.messager.alert("提醒", "保存成功,审核失败。" + json.msg);
                            } else {
                                $.messager.alert("提醒", "保存成功,审核失败。");
                            }
                        }
                    } else {
                        $.messager.alert("提醒", "保存成功。");
                    }
                    $("#purchase-backid-returnOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-returnOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-returnOrderAddPage-status").val("2");
                    }
                }
                else {
                    $.messager.alert("提醒", "保存失败");
                }
            }
        });
    }
    function returnOrder_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#purchase-table-returnOrderListPage").datagrid('reload');
        } catch (e) {
        }
    }

    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }
    function orderDetailAddDialog() {
        var flag = $("#purchase-form-reurnOrderAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善采购退货通知单基本信息');
            return false;
        }
        var supplierid = $("#purchase-returnOrderAddPage-supplier").supplierWidget('getValue');
        if (supplierid == null || supplierid == '') {
            $.messager.alert("提醒", "请先选择供应商档案再进行添加商品信息");
            $("#purchase-returnOrderAddPage-supplier").focus();
            return false;
        }
        var storageid = $("#purchase-returnOrderAddPage-storage").widget('getValue');
        if (storageid == null || storageid == '') {
            $.messager.alert("提醒", "请先选择退货仓库再进行添加商品信息");
            $("#purchase-returnOrderAddPage-storage").focus();
            return false;
        }
        $('<div id="purchase-returnOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-returnOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息新增(按ESC退出)',
            width: 680,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/returnorder/returnOrderDetailAddPage.do?supplierid=" + supplierid + "&storageid=" + storageid,
            onLoad: function () {
                $("#purchase-returnOrderDetail-goodsid").focus();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {
        var flag = $("#purchase-form-returnOrderAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        if (initdata == null || initdata.goodsid == null || initdata.goodsid == "") {
            return false;
        }
        $('<div id="purchase-returnOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-returnOrderAddPage-dialog-DetailOper-content");
        var url = "purchase/returnorder/returnOrderDetailEditPage.do?goodsid=" + initdata.goodsid;
        $DetailOper.dialog({
            title: '商品信息修改(按ESC退出)',
            width: 680,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: url,
            onLoad: function () {
                if ($("#purchase-form-returnOrderDetailEditPage").size() > 0) {
                    if (initdata.goodsInfo) {
                        $("#purchase-form-returnOrderDetailEditPage").form('load', initdata.goodsInfo);
                    }
                    $("#purchase-returnOderDeail-highestbuyprice").val(initdata.goodsInfo.highestbuyprice);
                    $("#purchase-returnOrderDetail-span-auxunitname").html(initdata.auxunitname);
                    $("#purchase-returnOrderDetail-span-unitname").html(initdata.unitname);
                    $("#purchase-form-returnOrderDetailEditPage").form('load', initdata);

                    var isbatch = $("#purchase-returnOrderDetail-isbatch").val();
                    if (isbatch == '1') {
                        $("#purchase-returnOrderDetail-batchno").attr("readonly", false);
                        $("#purchase-returnOrderDetail-batchno").removeClass("no_input");
                        var storageid = $("#purchase-returnOrderAddPage-storage").widget("getValue");
                        var param = null;
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: initdata.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: data.id}];
                        }
                        $("#purchase-returnOrderDetail-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 165,
                            singleSelect: true,
                            required: true,
                            onSelect: function (obj) {
                                $("#purchase-returnOrderDetail-detail-summarybatchid").val(obj.id);
                                $("#purchase-returnOrderDetail-detail-storageid").val(obj.storageid);
                                $("#purchase-returnOrderDetail-detail-storagename").val(obj.storagename);
                                $("#purchase-returnOrderDetail-storagelocationname").val(obj.storagelocationname);
                                $("#purchase-returnOrderDetail-storagelocationid").val(obj.storagelocationid);
                                $("#purchase-returnOrderDetail-produceddate").val(obj.produceddate);
                                $("#purchase-returnOrderDetail-deadline").val(obj.deadline);

                                $("#purchase-returnOrderDetail-existingnum").val(obj.usablenum);
                                $("#purchase-returnOrderDetail-unitnum").attr("usablenum", obj.usablenum);
                                $("#purchase-returnOrderDetail-unitnum").val(obj.usablenum);
                                $("#purchase-returnOrderDetail-loadInfo").html("现存量：<font color='green'>" + obj.existingnum + obj.unitname + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");

                                $("#purchase-returnOrderDetail-auxnum").focus();
                                $("#purchase-returnOrderDetail-auxnum").select();
                            },
                            onClear: function () {
                                $("#purchase-returnOrderDetail-loadInfo").html(numdetailHtml);
                                $("#purchase-returnOrderDetail-existingnum").val(existingnum);
                                $("#purchase-returnOrderDetail-detail-summarybatchid").val("");
                                $("#purchase-returnOrderDetail-detail-storageid").val("");
                                $("#purchase-returnOrderDetail-detail-storagename").val("");
                                $("#purchase-returnOrderDetail-storagelocationname").val("");
                                $("#purchase-returnOrderDetail-storagelocationid").val("");
                                $("#purchase-returnOrderDetail-produceddate").val("");
                                $("#purchase-returnOrderDetail-deadline").val("");
                            }
                        });
                    } else {
                        $("#purchase-returnOrderDetail-batchno").attr("readonly", true);
                        $("#purchase-returnOrderDetail-batchno").addClass("no_input");
                    }
                }
                $("#purchase-returnOrderDetail-auxnum").focus();
                $("#purchase-returnOrderDetail-auxnum").select();

                formaterNumSubZeroAndDot();

                $("#purchase-form-returnOrderDetailEditPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }

    function footerReCalc() {
        disableChoiceWidget();
        var $potable = $("#purchase-returnOrderAddPage-returnOrdertable");
        var data = $potable.datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var auxnum = 0;
        var auxunitname = "";
        var auxremainder = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].unitnum) {
                unitnum = Number(unitnum) + Number(data[i].unitnum);
            }
            if (data[i].taxamount) {
                taxamount = Number(taxamount) + Number(data[i].taxamount);
            }
            if (data[i].notaxamount) {
                notaxamount = Number(notaxamount) + Number(data[i].notaxamount);
            }
            if (data[i].tax) {
                tax = Number(tax) + Number(data[i].tax);
            }
            if (data[i].auxnum) {
                auxnum = Number(auxnum) + Number(data[i].auxnum);
            }
            if (data[i].auxremainder) {
                auxremainder = Number(auxremainder) + Number(data[i].auxremainder);
            }
            if ((auxunitname == "" || auxunitname == null) && data[i].auxunitname) {
                auxunitname = data[i].auxunitname;
            }
        }
        unitnum = String(unitnum);
        taxamount = String(taxamount);
        notaxamount = String(notaxamount);
        tax = String(tax);
        auxnum = String(parseInt(auxnum)) + auxunitname + (auxremainder > 0 ? auxremainder : "");
        $potable.datagrid('reloadFooter', [
            {
                goodsid: '合计',
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                auxnumdetail: auxnum,
                tax: tax
            }
        ]);
    }

    function checkGoodsDetailEmpty() {
        var flag = true;
        var $ordertable = $("#purchase-returnOrderAddPage-returnOrdertable");
        var data = $ordertable.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }
    //禁用表单title
    function disableChoiceWidget() {
        var rows = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#purchase-returnOrderAddPage-supplier").supplierWidget("readonly", true);
            if (count == 1) {
                $("#purchase-returnOrderAddPage-storage").widget("readonly1", true);
            }
        } else {
            $("#purchase-returnOrderAddPage-supplier").supplierWidget("readonly", false);
        }
    }
    var tableColJson = $("#purchase-returnOrderAddPage-returnOrdertable").createGridColumnLoad({
        name: 'purchase_returnorder_detail',
		frozenCol : [[
		]],
		commonCol : [[
			{field:'goodsid',title:'商品编码',width:70,sortable:true},
			{field:'name', title:'商品名称', width:220,aliascol:'goodsid',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.name;
					}else{
						return "";
					}
				}
			},
			{field:'spell', title:'助记符',width:80,aliascol:'goodsid',hidden:true,align:'left',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != undefined){
						return rowData.goodsInfo.spell;
					}else{
						return "";
					}
				}
			},
			{field:'barcode', title:'条形码',width:90,aliascol:'goodsid',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.barcode;
					}else{
						return "";
					}
				}
			},
			{field:'model', title:'规格型号',width:80,aliascol:'goodsid',hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.model;
					}else{
						return "";
					}
				}
			},
			{field:'brandName', title:'商品品牌',width:60,aliascol:'goodsid',hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.brandName;
					}else{
						return "";
					}
				}
			},
			{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null && rowData.goodsInfo.boxnum!=null){
						return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					}else if(value!=null){
						return formatterBigNumNoLen(value);
					}
					else{
						return "";
					}
				}
			},
			{field:'unitid', title:'单位',width:35,
				formatter: function(value,row,index){
					return row.unitname;
				}
			},
			{field:'unitnum', title:'数量',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterBigNumNoLen(value);
				}
			},
			{field:'taxprice', title:'单价',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'boxprice', title:'箱价',aliascol:'taxprice',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'taxamount', title:'金额',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'notaxprice', title:'未税单价',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'noboxprice', title:'未税箱价',aliascol:'notaxprice',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'notaxamount', title:'未税金额',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'taxtypename', title:'税种',width:60,align:'right',hidden:true},
			{field:'tax', title:'税额',width:60,align:'right',sortable:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'auxunitid', title:'辅单位',width:60,hidden:true,
				formatter: function(value,row,index){
					return row.auxunitname;
				}
			},
			{field:'auxnumdetail', title:'辅数量',width:80,align:'right'},
			{field:'storagelocationid', title:'所属库位',width:100,
				formatter:function(value,row,index){
					return row.storagelocationname;
				}
			},
			{field:'batchno',title:'批次号',width:80},
			{field:'produceddate',title:'生产日期',width:80},
			{field:'deadline',title:'有效截止日期',width:80,hidden:true},
			{field:'highestbuyprice'},
			{field:'remark', title:'备注',width:150}
		]]
	});
	function detailOnSortColumn(sort, order){
        var goodsInfoArr=["barcode"];
        var issort=false;
        if(sort==null || sort==""){
            return true;
        }
        var data = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid("getData");
        var rows = data.rows;
        var dataArr = [];
        for(var i=0;i<rows.length;i++){
            if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                dataArr.push(rows[i]);
            }
        }
        dataArr.sort(function(a,b){
            var atmp=0;
            var btmp=0;
            if($.inArray(sort,goodsInfoArr)>=0){
                console.log(sort);
                console.log(goodsInfoArr);
                var aGInfo=a.goodsInfo || {};
                var bGInfo=b.goodsInfo || {};
                atmp=aGInfo[sort];
                btmp=bGInfo[sort];

            }else{
                atmp = a[sort];
                btmp = b[sort];
            }
            if(atmp==null || btmp==null){
                return -1;
            }

            if($.isNumeric(atmp)){
                if(order=="asc"){
                    return Number(atmp)>Number(btmp)?1:-1
                }else{
                    return Number(atmp)<Number(btmp)?1:-1
                }
            }else{
                if(order=="asc"){
                    return atmp>btmp?1:-1
                }else{
                    return atmp<btmp?1:-1
                }
            }
        });
        $("#purchase-returnOrderAddPage-returnOrdertable").datagrid("loadData",{rows:dataArr,total:data.total});
        return true;
    };
    $(document).ready(function () {
        $("#purchase-panel-returnOrderPage").panel({
            href: returnOrder_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#purchase-buttons-returnOrderPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/returnorder/returnOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderTempSave.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $("#purchase-returnOrderAddPage-addType").val("temp");
                        var datarows = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
                        if (datarows != null && datarows.length > 0) {
                            $("#purchase-returnOrderAddPage-returnOrderDetails").val(JSON.stringify(datarows));
                        }
                        returnOrder_tempSave_form_submit();
                        $("#purchase-form-returnOrderAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderRealSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购退货通知单商品信息");
                            return false;
                        } else {
                            $("#purchase-returnOrderAddPage-addType").val("real");
                            var datarows = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
                            if (datarows != null && datarows.length > 0) {
                                $("#purchase-returnOrderAddPage-returnOrderDetails").val(JSON.stringify(datarows));
                            }
                            returnOrder_realSave_form_submit();
                            $("#purchase-form-returnOrderAddPage").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核退货通知单信息？", function (r) {
                            if (r) {
                                $("#purchase-returnOrderAddPage-addType").val("real");
                                $("#purchase-returnOrderAddPage-saveaudit").val("saveaudit");
                                var datarows = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#purchase-returnOrderAddPage-returnOrderDetails").val(JSON.stringify(datarows));
                                }
                                returnOrder_realSave_form_submit();
                                $("#purchase-form-returnOrderAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#purchase-buttons-returnOrderPage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#purchase-backid-returnOrderAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + id);
                            }
                        } else if (type == "edit") {
                            var id = $("#purchase-backid-returnOrderAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_purchase_returnorder");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/deleteReturnOrder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#purchase-backid-returnOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否删除该采购退货通知单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'purchase/returnorder/deleteReturnOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");

                                            var nextdata = $("#purchase-buttons-returnOrderPage").buttonWidget("removeData", id);
                                            if (null != nextdata && nextdata.id && nextdata.id != "") {
                                                $("#purchase-backid-returnOrderAddPage").val(nextdata.id);
                                                $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + nextdata.id);
                                                returnOrder_RefreshDataGrid();
                                            } else {
                                                returnOrder_RefreshDataGrid();
                                                parent.closeNowTab();
                                                //$("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderAddPage.do');
                                            }
                                        } else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/auditReturnOrder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#purchase-backid-returnOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购退货通知单商品信息");
                            return false;
                        }

                        $.messager.confirm("提醒", "是否审核通过该采购退货通知单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/returnorder/auditReturnOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#purchase-returnOrderAddPage-status").val("3");
                                            //$("#purchase-buttons-returnOrderPage").buttonWidget("setDataID", {id:json.backid, state:'3', type:'view'});
                                            $.messager.alert("提醒", "审核成功,并自动生成采购退货出库单，单据编号：" + json.billid);
                                            returnOrder_RefreshDataGrid();
                                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + id);
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "审核失败。" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "审核失败。");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/oppauditReturnOrder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#purchase-backid-returnOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var businessdate = $("#purchase-returnOrderAddPage-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审该采购退货通知单信息？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'purchase/returnorder/oppauditReturnOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#purchase-returnOrderAddPage-status").val("2");
                                            $.messager.alert("提醒", "反审成功");
                                            returnOrder_RefreshDataGrid();
                                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + id);
                                        } else {
                                            $.messager.alert("提醒", "反审失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交采购退货通知单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#purchase-backid-returnOrderAddPage").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'purchase/returnorder/submitReturnOrderProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    returnOrder_RefreshDataGrid();
                                                    $("#purchase-panel-returnOrderPage").panel("refresh", 'purchase/returnorder/returnOrderEditPage.do?id=' + id);
                                                }
                                                else {
                                                    if (json.msg != null || json.msg != "") {
                                                        $.messager.alert("提醒", "提交失败!" + json.msg);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "提交失败!");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-addidea',
                            handler: function () {
                                if (arrivalOrder_type == "handle") {
                                    $("#purchase-wfdialog-returnOrderPage").dialog({
                                        title: '填写处理意见',
                                        width: 450,
                                        height: 300,
                                        closed: false,
                                        cache: false,
                                        modal: true,
                                        href: 'workflow/commentAddPage.do?id=' + handleWork_taskId
                                    });
                                }
                            }
                        },
                        {
                            type: 'workflow-viewflow',
                            handler: function () {
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#purchase-wfdialog-arrivalOrderPage").dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/commentListPage.do?id=' + id
                                });
                            }
                        },
                        {
                            type: 'workflow-viewflow-pic',
                            handler: function () {
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#purchase-wfdialog-arrivalOrderPage").dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/showDiagramPage.do?id=' + id
                                });
                            }
                        },
                        {
                            type: 'workflow-recover',
                            handler: function () {

                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-returnOrderAddPage").val(data.id);
                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-returnOrderAddPage").val(data.id);
                            $("#purchase-panel-returnOrderPage").panel('refresh', 'purchase/returnorder/returnOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/returnorder/returnOrderPrintViewBtn.do">
                {
                    id: 'button-printview-returnorder',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderPrintBtn.do">
                {
                    id: 'button-print-returnorder',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'purchase-returnOrderPage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            id: '${id}',
            datagrid: 'purchase-table-returnOrderListPage',
            tname: 't_purchase_returnorder'
        });
        $(document).bind('keydown', 'esc', function () {
            $("#purchase-returnOrderDetail-remark").focus();
            if ($("#purchase-returnOrderAddPage-dialog-DetailOper-content").size() > 0) {
                $("#purchase-returnOrderAddPage-dialog-DetailOper-content").dialog("close");
            }
        });

        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#purchase-returnOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-returnOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-returnOrderDetailEditPage-editSave").trigger("click");
            }, 300);
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#purchase-returnOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-returnOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-returnOrderDetailEditPage-editSave").trigger("click");
            }, 300);
            return false;
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-returnOrder-dialog-print",
            code: "purchase_returnorder",
            url_preview: "print/purchase/returnOrderPrintView.do",
            url_print: "print/purchase/returnOrderPrint.do",
            btnPreview: "button-printview-returnorder",
            btnPrint: "button-print-returnorder",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#purchase-backid-returnOrderAddPage").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                printParam.idarrs = id;
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
