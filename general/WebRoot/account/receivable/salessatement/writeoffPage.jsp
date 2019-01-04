<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>收款单核销指定销售发票</title>
</head>

<body>
<form action="" method="post" id="account-form-writeoff">
    <table  border="0" class="box_table">
        <tr>
            <td width="120">客户名称:</td>
            <td style="text-align: left;">
                <input type="hidden" id="writeoffCollectionOrder-hidden-customerid" name="customerid" value="${customerCapital.id}"/>
                <input type="text" id="writeoffCollectionOrder-hidden-customername" width="180" readonly="readonly" value="${customerCapital.customername }"/>
            </td>
            <td width="120">剩余金额:</td>
            <td style="text-align: left;">
                <input type="text" id="account-writeoff-remainderamount" class="easyui-numberbox" data-options="precision:2" readonly="readonly" value="${customerCapital.amount }"/>
            </td>
        </tr>
        <tr>
            <td width="120">核销后剩余金额:</td>
            <td style="text-align: left;">
                <input type="text" id="account-writeoff-last-remainderamount" class="easyui-numberbox" data-options="precision:2" readonly="readonly" value="${customerCapital.amount-invoiceAmount }"/>
            </td>
        </tr>
    </table>
    <table class="tablenormal">
        <tr>
            <td style="width: 120px;">编号</td>
            <td style="width: 80px;">单据类型</td>
            <td style="width: 60px;">应收金额</td>
            <td style="width: 80px;">核销金额</td>
            <td style="width: 80px;">尾差金额</td>
            <td style="width: 80px;">备注</td>
            <td style="width: 120px;">关联收款单</td>
        </tr>
        <c:forEach items="${salesInvoiceList }" var="list" varStatus="status">
            <tr>
                <td>${list.id}
                    <input type="hidden" name="salesStatementList[${status.index }].billid" value="${list.id}"/>
                </td>
                <td>
                    <c:if test="${list.billtype=='1'}">销售核销</c:if>
                    <c:if test="${list.billtype=='2'}">应收款冲差</c:if>
                </td>
                <td>
                    <fmt:formatNumber value="${list.taxamount}" type="currency" pattern="0.00"/>
                    <input type="hidden" id="${list.id}-amount" style="width: 100px;" value="<fmt:formatNumber value="${list.taxamount}" type="currency" pattern="0.00"/>" readonly="readonly"/>
                </td>
                <td>
                    <input type="text" name="salesStatementList[${status.index }].writeoffamount" id="${list.id}-invoiceamount" style="width: 100px;" class="easyui-numberbox invoiceamount"
                           data-options='precision:2,required:true,onChange:function(newValue,oldValue){
  						var amount = $("#${list.id}-amount").val();
  						$("#${list.id}-tailamount").numberbox("setValue",Number(newValue)-Number(amount));
  						countInvocieAmount();
  					}' <c:if test="${list.billtype=='2' || list.isrelate=='1' || list.isrelate=='2'}">readonly="readonly" </c:if> value="${list.invoiceamount}"/>
                </td>
                <td>
                    <input type="text" name="salesStatementList[${status.index }].tailamount" id="${list.id}-tailamount" style="width: 100px;" class="easyui-numberbox tailamount"
                           data-options='precision:2,required:true,onChange:function(newValue,oldValue){
  						var amount = $("#${list.id}-amount").val();
  						$("#${list.id}-invoiceamount").numberbox("setValue",Number(amount)+Number(newValue));
  						countTailAmount();
  					}' <c:if test="${list.billtype=='2' || list.isrelate=='1' || list.isrelate=='2'}">readonly="readonly"</c:if> value="${list.invoiceamount-list.taxamount}"/>
                </td>
                <td><input type="text" name="salesStatementList[${status.index }].remark" style="width: 150px;"/></td>
                <td>
                    <c:if test="${list.isrelate=='0'}">
                        <a id="${list.id}-a-relation" href="javaScript:relateCollectionOrder('${list.id}','${list.billtype}');">关联</a>
                        <a id="${list.id}-a-unrelation" href="javaScript:unrelateCollectionOrder('${list.id}','${list.billtype}');" style="display: none;">取消关联</a>
                        <a id="${list.id}-a-viewrelation" href="javaScript:viewrelateCollectionOrder('${list.id}','${list.billtype}');" style="display: none;">查看关联收款单</a>
                        <input type="hidden" id="${list.id}-hidden-isrelate" class="isrelate" value="0"/>
                    </c:if>
                    <c:if test="${list.isrelate=='1'}">
                        <a id="${list.id}-a-relation" href="javaScript:relateCollectionOrder('${list.id}','${list.billtype}');" style="display: none;">关联</a>
                        <a id="${list.id}-a-unrelation" href="javaScript:unrelateCollectionOrder('${list.id}','${list.billtype}');">取消关联</a>
                        <a id="${list.id}-a-viewrelation" href="javaScript:viewrelateCollectionOrder('${list.id}','${list.billtype}');">查看关联收款单</a>
                        <input type="hidden" id="${list.id}-hidden-isrelate" class="isrelate" value="1"/>
                    </c:if>
                    <c:if test="${list.isrelate=='2'}">
                        <a id="${list.id}-a-relation" href="javaScript:relateCollectionOrderSelect('${list.id}','${list.billtype}');">关联</a>
                        <a id="${list.id}-a-unrelation" href="javaScript:unrelateCollectionOrderSelect('${list.id}','${list.billtype}');" style="display: none;">取消关联</a>
                        <a id="${list.id}-a-viewrelation" href="javaScript:viewrelateCollectionOrder('${list.id}','${list.billtype}');" style="display: none;">查看关联收款单</a>
                        <a id="${list.id}-a-changetail" href="javaScript:changeTailamount('${list.id}','${list.billtype}');">调尾差</a>
                        <input type="hidden" id="${list.id}-hidden-isrelate" class="isrelate" value="0"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td style="text-align: right;padding-right: 15px;">合计</td>
            <td></td>
            <td style="text-align: right;padding-right: 15px;">${totalTaxamount }</td>
            <td id="account-writeoff-invoicetotal" style="text-align: right;padding-right: 15px;">${totalInvoiceAmount }</td>
            <td id="account-writeoff-tailtotal" style="text-align: right;padding-right: 15px;"></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</form>
<div id="account-dialog-collection-relate_invoice"></div>
<div id="account-panel-collectionOrder-addauditpage"></div>
<input type="hidden" id="account-relate-ok" value="0"/>
<script type="text/javascript">
    var writeoff_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    var cancelflag = true;

    //销售核销核销提交
    function accounFormWriteoffSubmit(){
        var flag = $("#account-form-writeoff").form('validate');
        if(flag==false){
            return false;
        }
        loading("提交中..");
        $.ajax({
            url :'account/receivable/auditWriteoffCollectionOrder.do',
            type:'post',
            dataType:'json',
            data:$("#account-form-writeoff").serializeJSON(),
            success:function(json){
                loaded();
                if(json.flag){
                    $.messager.alert("提醒","核销成功");
                    var iframe = tabsWindowURL("/account/receivable/showSalesInvoiceListPage.do");
                    if(null!=iframe){
                        //刷新列表
                        tabsWindowURL("/account/receivable/showSalesInvoiceListPage.do").reloadSalesInvoiceList();
                    }
                    var title = parent.getNowTabTitle();
                    if (top.$('#tt').tabs('exists',title)){
                        if(tabsWindow(title).$("#account-form-query-salesInvoiceSouceBill").size() > 0){
                            var queryJSON = tabsWindow(title).$("#account-form-query-salesInvoiceSouceBill").serializeJSON();
                            tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('load',queryJSON);
                            tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearSelections');
                            tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearChecked');
                        }
                        if(tabsWindow(title).$("#account-form-query-customerAccountPage").size() > 0){
                            var queryJSON = tabsWindow(title).$("#account-form-query-customerAccountPage").serializeJSON();
                            tabsWindow(title).$("#account-datagrid-customerAccountPage").datagrid('load',queryJSON);
                            tabsWindow(title).$("#account-datagrid-customerAccountPage").datagrid('clearSelections');
                        }
                        if("销售核销查看" == title){
                            top.closeNowTab();
                        }
                    }
                    if($("#account-dialog-writeoff-info-content").size() > 0){
                        $("#account-dialog-writeoff-info-content").dialog('close');
                    }
                    if($("#account-dialog-writeoff").size() > 0){
                        $("#account-dialog-writeoff").dialog('close');
                    }
                }else{
                    $.messager.alert("提醒","核销失败");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                loaded();
                $.messager.alert("提醒","出错了!");
            }
        });
    }

    function writeOffSubmit(){
//   			var isrealteArr = $(".isrelate");
//   			for(var i=0;i<isrealteArr.length;i++){
//   				var isrelate = $(isrealteArr[i]).val();
//   				if(isrelate=='0'){
//	            	$.messager.alert("提醒","单据未全部关联收款单!");
//	            	return false;
//	            }
//   			}
        var invoiceid = "${invoiceid}"
        var flag = true;
        var msg = "";
        if(cancelflag){
            var ret = writeoff_AjaxConn({invoiceid:invoiceid},'account/receivable/getCancelSalesInvoiceFlag.do');
            var json = $.parseJSON(ret);
            flag = json.flag;
            msg = json.msg;
        }
        if(flag){
            var invoiceArr = $(".invoiceamount");
            var invoiceTotal = 0;
            for(var i=0;i<invoiceArr.length;i++){
                var invoice= $(invoiceArr[i]).numberbox("getValue");
                invoiceTotal = Number(invoiceTotal)+Number(invoice);
            }
            var remainderamount = $("#account-writeoff-remainderamount").numberbox("getValue");
            if(remainderamount<invoiceTotal){
                $.messager.confirm("提醒","核销后客户余额为负的，是否确认核销？",function(r){
                    if(r){
                        accounFormWriteoffSubmit();
                    }
                });
            }else{
                $.messager.confirm("提醒","是否确认核销？",function(r){
                    if(r){
                        accounFormWriteoffSubmit();
                    }
                });
            }
        }else{
            if(msg != ""){
                $.messager.alert("提醒",msg);
            }
            $("#account-dialog-writeoff-info-content").dialog("refresh");
        }
    }
    function countInvocieAmount(){
        var invoiceArr = $(".invoiceamount");
        var invoiceTotal = 0;
        $.each(invoiceArr,function(i){
            var invoice= $(invoiceArr[i]).numberbox("getValue");
            invoiceTotal = Number(invoiceTotal)+Number(invoice);
        });
        var remainderamount = $("#account-writeoff-remainderamount").numberbox("getValue");
        if(remainderamount<invoiceTotal){
            $.messager.alert("提醒","收款单余额不足");
        }
        $("#account-writeoff-invoicetotal").html(formatterMoney(invoiceTotal));
        var remainderamount = $("#account-writeoff-remainderamount").numberbox("getValue");
        $("#account-writeoff-last-remainderamount").numberbox("setValue",Number(remainderamount)-Number(invoiceTotal));
    }
    function countTailAmount(){
        var tailArr = $(".tailamount");
        var tailTotal = 0;
        $.each(tailArr,function(i){
            var tail= $(tailArr[i]).numberbox("getValue");
            tailTotal = Number(tailTotal)+Number(tail);
        });
        $("#account-writeoff-tailtotal").html(formatterMoney(tailTotal));
    }
    //关联收款单
    function relateCollectionOrder(billid,type){
        var ret = writeoff_AjaxConn({invoiceid:billid},'account/receivable/getRelateSalesInvoiceFlag.do');
        var retjson = $.parseJSON(ret);
        if(retjson.flag){
            var amount = $("#"+billid+"-amount").val();
            $("#"+billid+"-hidden-isrelate").val("0");
            document.getElementById(""+billid+"-a-relation").style.display = "block";
            document.getElementById(""+billid+"-a-unrelation").style.display = "none";
            document.getElementById(""+billid+"-a-viewrelation").style.display = "none";
            $('<div id="account-dialog-collection-relate_invoice-content"></div>').appendTo('#account-dialog-collection-relate_invoice');
            $("#account-dialog-collection-relate_invoice-content").dialog({
                href:"account/receivable/showInvoiceRelateCollectionPage.do?customerid=${customerCapital.id}&billid="+billid+"&billtype="+type+"&amount="+amount,
                title:"关联收款单",
                width:750,
                height:400,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                buttons:[{
                    text:'确定',
                    iconCls:'button-save',
                    handler:function(){
                        $("#account-relate-ok").val("1");
                        addRelateCollectionOrder();
                    }
                }],
                onClose:function(){
                    var ok = $("#account-relate-ok").val();
                    if(ok == "1"){
                        document.getElementById(""+billid+"-a-relation").style.display = "none";
                        document.getElementById(""+billid+"-a-unrelation").style.display = "block";
                        document.getElementById(""+billid+"-a-viewrelation").style.display = "block";
                        $("#"+billid+"-hidden-isrelate").val("1");
                    }else{
                        $("#account-relate-ok").val("0");
                    }
                    $('#account-dialog-collection-relate_invoice-content').dialog("destroy");
                }
            });
        }else{
            $.messager.alert("提醒",retjson.msg);
            $("#account-dialog-writeoff-info-content").dialog("refresh");
        }
    }

    //取消关联收款单
    function unrelateCollectionOrder(billid,type){
        var ret = writeoff_AjaxConn({invoiceid:billid},'account/receivable/getUnrelateSalesInvoiceFlag.do');
        var retjson = $.parseJSON(ret);
        if(retjson.flag){
            document.getElementById(""+billid+"-a-relation").style.display = "block";
            document.getElementById(""+billid+"-a-unrelation").style.display = "none";
            document.getElementById(""+billid+"-a-viewrelation").style.display = "none";
            $("#"+billid+"-hidden-isrelate").val("0");
            loading("提交中..");
            $.ajax({
                url :'account/receivable/deleteRelateCollectionOrder.do?billid='+billid+'&billtype='+type,
                type:'post',
                dataType:'json',
                success:function(json){
                    loaded();
                    if(json.flag){
                        $.messager.alert("提醒","取消关联成功");
                        $("#account-dialog-writeoff-info-content").dialog("refresh");
                    }else{
                        $.messager.alert("提醒","取消关联失败");
                    }
                },
                error:function(){
                    loaded();
                    $.messager.alert("错误","出错");
                }
            });
        }else{
            $.messager.alert("提醒",retjson.msg);
            $("#account-dialog-writeoff-info-content").dialog("refresh");
        }
    }
    //查看关联的收款单列表
    function viewrelateCollectionOrder(billid,type){
        $('<div id="account-dialog-collection-relate_invoice-list"></div>').appendTo('#account-dialog-collection-relate_invoice');
        $("#account-dialog-collection-relate_invoice-list").dialog({
            href:"account/receivable/showRelateCollectionListPage.do?billid="+billid+"&billtype="+type,
            title:"关联收款单列表",
            width:750,
            height:400,
            modal:true,
            cache:false,
            maximizable:true,
            resizable:true,
            onClose:function(){
                $('#account-dialog-collection-relate_invoice-list').dialog("destroy");
            }
        });
    }
    //关联收款单选择

    function relateCollectionOrderSelect(billid,type){
        var ret = writeoff_AjaxConn({invoiceid:billid},'account/receivable/getRelateSalesInvoiceFlag.do');
        var retjson = $.parseJSON(ret);
        if(retjson.flag){
            var amount = $("#"+billid+"-amount").val();
            $("#"+billid+"-hidden-isrelate").val("0");
            document.getElementById(""+billid+"-a-relation").style.display = "block";
            document.getElementById(""+billid+"-a-unrelation").style.display = "none";
            document.getElementById(""+billid+"-a-changetail").style.display = "none";
            document.getElementById(""+billid+"-a-viewrelation").style.display = "none";
            $('<div id="account-dialog-collection-relate_invoice-content"></div>').appendTo('#account-dialog-collection-relate_invoice');
            $("#account-dialog-collection-relate_invoice-content").dialog({
                href:"account/receivable/showInvoiceRelateCollectionPage.do?customerid=${customerCapital.id}&billid="+billid+"&billtype="+type+"&amount="+amount,
                title:"关联收款单",
                width:750,
                height:400,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                buttons:[{
                    text:'确定',
                    handler:function(){
                        $("#account-relate-ok").val("1");
                        addRelateCollectionOrder();
                    }
                }],
                onClose:function(){
                    var ok = $("#account-relate-ok").val();
                    if(ok == "1"){
                        document.getElementById(""+billid+"-a-relation").style.display = "none";
                        document.getElementById(""+billid+"-a-unrelation").style.display = "block";
                        document.getElementById(""+billid+"-a-changetail").style.display = "none";
                        document.getElementById(""+billid+"-a-viewrelation").style.display = "block";
                        $("#"+billid+"-hidden-isrelate").val("1");
                    }else{
                        $("#account-relate-ok").val("0");
                        $("#account-dialog-writeoff-info-content").dialog("refresh");
                    }
                    $('#account-dialog-collection-relate_invoice-content').dialog("destroy");
                }
            });
        }else{
            $.messager.alert("提醒",retjson.msg);
            $("#account-dialog-writeoff-info-content").dialog("refresh");
        }
    }

    //取消关联收款单选择
    function unrelateCollectionOrderSelect(billid,type){
        var ret = writeoff_AjaxConn({invoiceid:billid},'account/receivable/getUnrelateSalesInvoiceFlag.do');
        var retjson = $.parseJSON(ret);
        if(retjson.flag){
            document.getElementById(""+billid+"-a-relation").style.display = "block";
            document.getElementById(""+billid+"-a-unrelation").style.display = "none";
            document.getElementById(""+billid+"-a-changetail").style.display = "none";
            document.getElementById(""+billid+"-a-viewrelation").style.display = "none";
            $("#"+billid+"-hidden-isrelate").val("0");
            loading("提交中..");
            $.ajax({
                url :'account/receivable/deleteRelateCollectionOrder.do?billid='+billid+'&billtype='+type,
                type:'post',
                dataType:'json',
                success:function(json){
                    loaded();
                    if(json.flag){
                        $.messager.alert("提醒","取消关联成功");
                        $("#account-dialog-writeoff-info-content").dialog("refresh");
                    }else{
                        $.messager.alert("提醒","取消关联失败");
                    }
                },
                error:function(){
                    loaded();
                    $.messager.alert("错误","出错");
                }
            });
        }else{
            $.messager.alert("提醒",retjson.msg);
            $("#account-dialog-writeoff-info-content").dialog("refresh");
        }
    }
    //调尾差
    function changeTailamount(billid,type){
        $("#"+billid+"-hidden-isrelate").val("1");
        $("#"+billid+"-invoiceamount").numberbox('setValue',"0");
        var tailamount = $("#"+billid+"-tailamount").numberbox('getValue');
        if(${beforeTailContr} > tailamount && tailamount < ${afterTailContr}){
            var amount = $("#"+billid+"-amount").val();
            $("#"+billid+"-invoiceamount").numberbox('setValue',amount);
            var tailAmountLimit = "${beforeTailContr}~${afterTailContr}";
            $.messager.alert("提醒","尾差金额为："+tailamount+",超过系统设置的金额:"+tailAmountLimit+"。不能进行调尾差！");
            return false;
        }
        document.getElementById(""+billid+"-a-relation").style.display = "none";
        document.getElementById(""+billid+"-a-unrelation").style.display = "none";
        document.getElementById(""+billid+"-a-changetail").style.display = "block";
        document.getElementById(""+billid+"-a-viewrelation").style.display = "none";
        cancelflag = false;
    }
</script>
</body>
</html>
