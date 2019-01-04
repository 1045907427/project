<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@include file="/include.jsp" %>
    <title>客户应收款冲差添加页面</title>
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
        <form id="account-form-customerPushBanlance" action="account/receivable/addMoreCustomerPushBanlace.do" method="post">
            <input type="hidden" name="detailStr" id="account-detailStr-customerPushBanlance" />
            <input type="hidden" name="saveaudit" id="account-saveaudit-customerPushBanlance" />

            <div data-options="region:'north',border:false">
                <%--<div class="buttonBG" id="account-buttons-customerPushBanlance"></div>--%>
                <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                    <tr>
                        <td style="text-align: left;">业务日期:</td>
                        <td style="text-align: left;">
                            <input type="text"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 200px;" value="${date }" name="customerPushBalance.businessdate" />
                        </td>
                        <td style="text-align: left;">客户名称:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-customerid" name="customerPushBalance.customerid" style="width: 200px;"/>
                        </td>
                        <td style="text-align: left;">状&nbsp;&nbsp;态：</td>
                        <td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
                    </tr>
                    <tr>
                        <td style="text-align: left;">销售部门:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-deptid" name="customerPushBalance.salesdept" style="width: 200px;"/>
                        </td>
                        <td style="text-align: left;">客户业务员:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-salesuser" name="customerPushBalance.salesuser" style="width: 200px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'center',border:false">
                <input id="account-saveaudit-orderDetailAddPage" type="hidden" name="saveaudit" value="save"/>
                <table id="account-datagrid-customerPushBanlance"></table>
            </div>
        </form>
    <div id="account-dialog-customerPushBanlanceAddPage" class="easyui-dialog" data-options="closed:true"></div>
</div>
<script type="text/javascript">
    var $datagrid=$("#account-datagrid-customerPushBanlance");
    var $detailStr=$("#account-detailStr-customerPushBanlance");
    var $saveaudit = $('#account-saveaudit-customerPushBanlance');
    $(function(){
        $("#account-customerPushBanlance-customerid").customerWidget({
            name:'t_account_customer_push_balance',
            width:200,
            col:'customerid',
            singleSelect:true,
            required:true,
            isopen:true,
            onSelect:function(data){
                $("#account-customerPushBanlance-deptid").widget('clear');
                $("#account-customerPushBanlance-salesuser").widget('clear');
                $("#account-customerPushBanlance-deptid").widget('setValue',data.salesdeptid);
                $("#account-customerPushBanlance-salesuser").widget('setValue',data.salesuserid);
                $("#account-customerPushBanlance-brand").focus();
            },
            onClear:function(){
                $("#account-customerPushBanlance-deptid").widget('clear');
                $("#account-customerPushBanlance-salesuser").widget('clear');
            }
        });
        $("#account-customerPushBanlance-subject").widget({
            name:'t_account_customer_push_balance',
            width:200,
            col:'subject',
            singleSelect:true
        });
        $("#account-customerPushBanlance-deptid").widget({
            width:200,
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:true
        });
        $("#account-customerPushBanlance-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            width:200,
            singleSelect:true
        });
        $("#account-datagrid-customerPushBanlance").datagrid({
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            fit: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: true,
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
//                $wareList.datagrid('selectRow', rowIndex);
                $("#account-menu-customerPushBanlance").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onLoadSuccess: function(data){
                if(data.rows.length<12){
                    var j = 12-data.rows.length;
                    for(var i=0;i<j;i++){
                        $(this).datagrid('appendRow',{});
                    }
                }else{
                    $(this).datagrid('appendRow',{});
                }
            },
            onDblClickRow: function(rowIndex, rowData){
                if(rowData.brandid=='' || rowData.brandid==undefined){
                    showCustomerPushBanlanceAddPage();
                }else{
                    showCustomerPushBanlanceEditPage(rowData);
                }
            }
        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');
        //按钮权限
        var holdbtn = $("#account-btn-permission-hold").val();
        var savebtn = $("#account-btn-permission").val();
        if(undefined != holdbtn && undefined != savebtn && "" != holdbtn && "" != savebtn){
            document.getElementById("account-collectionOrder-addbutton-hold").style.display = "none";
            document.getElementById("account-customerPushBanlance-addGoButton-hold").style.display = "none";
            $("#account-btn-permission-hold").val("0");
        }



    });
    function addCustomerPushBanlance(type){
        $("#account-customerPushBanlance-addGoButton-type").val(type);
        $("#account-form-customerPushBanlance-add").submit();
    }

</script>
</body>
</html>
