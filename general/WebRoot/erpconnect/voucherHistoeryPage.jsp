<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>凭证记录日志</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="erpconnect-toolbar-voucherHistoeryPage" style="height:auto;padding:0px">
    <%--<security:authorize url="/journalsheet/costsFee/exportCustomerCostPayableDetail.do">--%>
    <a href="javaScript:void(0);" id="erpconnect-export-voucherHistoeryPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    <%--</security:authorize>--%>
    <form id="erpconnect-form-voucherHistoeryPage" method="post">
        <table>
            <tr>
                <td>业务日期：</td>
                <td><input type="text" name="businessdate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    到<input type="text" name="businessdate1" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                <td>单据编号：</td>
                <td><input type="text" name="billid" class="len150" /> </td>
                <%--<td>系统版本：</td>--%>
                <%--<td>--%>
                    <%--<select name="systemversion" style="width: 135px">--%>
                        <%--<option VALUE="T3" <c:if test="${ERPVersion=='T3'}">selected="selected"</c:if>>用友T3</option>--%>
                        <%--<option VALUE="T6" <c:if test="${ERPVersion=='T6'}">selected="selected"</c:if>>用友T6</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <td>制单时间：</td>
                <td>
                    <input type="text" name="addtime" style="width:135px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                </td>
                <td>账套名：</td>
                <td>
                    <input type="text" name="dbname" style="width:150px;" />
                </td>
            </tr>
            <tr>
                <td>凭证类型：</td>
                <td>
                    <select name="type" style="width: 215px">
                        <option></option>
                        <option value="0">采购进货凭证</option>
                        <option value="1">采购退货凭证</option>
                        <option value="2">采购冲暂估凭证</option>
                        <option value="3">采购发票冲差凭证</option>
                        <option value="4">收款支付凭证(收款单)</option>
                        <option value="5">货款支付凭证(付款单)</option>
                        <option value="6">货款支付凭证(采购发票)</option>
                        <option value="7">销售凭证</option>
                        <option value="8">收款支付凭证(回笼表)</option>
                        <option value="9">费用支付凭证(日常费用)</option>
                        <option value="10">费用支付凭证(货款录入)</option>
                        <option value="11">代垫录入凭证</option>
                    </select>
                </td>
                <td>凭证编号：</td>
                <td><input type="text" name="voucherid" class="len150" /> </td>
                <td>版本参数：</td>
                <td>
                    <select name="versionParam"  style="width: 135px" >
                        <option></option>
                        <option VALUE="0" <c:if test="${erpp.versionParam=='0'}">selected="selected"</c:if>>通用参数0</option>
                        <option VALUE="1" <c:if test="${erpp.versionParam=='1'}">selected="selected"</c:if>>通用参数1</option>
                    </select>
                </td>
                <td colspan="2" class="tdbutton">
                    <a href="javascript:void(0);" id="erpconnect-queay-voucherHistoeryPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="erpconnect-resetQueay-voucherHistoeryPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<table id="erpconnect-table-voucherHistoeryPage"></table>
<script type="text/javascript">
    var checkListJson = null;
    $(function() {
        var initQueryJSON = $("#erpconnect-form-voucherHistoeryPage").serializeJSON();

        $("#erpconnect-export-voucherHistoeryPage").Excel('export',{
            queryForm: "#erpconnect-form-voucherHistoeryPage",
            type:'exportUserdefined',
            name:'凭证日志',
            url:'erpconnect/exportVoucherHistoeryData.do'
        });
        checkListJson =  $("#erpconnect-table-voucherHistoeryPage").createGridColumnLoad({
            commonCol :[[
                {field:'id',title:'编号',width:70,hidden:true},
                {field:'businessdate',title:'业务日期',width:80},
                {field:'billid',title:'单据编号',width:250},
                {field:'dbname',title:'生成账套',width:150},
//                {field:'voucherid',title:'凭证编号',width:100,isShow:true},
                {field:'amountStr',title:'凭证金额',width:150,isShow:true},
                {field:'type',title:'类型',width:160,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == "0"){
                            return "采购进货";
                        }else if(value == "1"){
                            return "采购退货";
                        }else if(value == "2"){
                            return "采购冲暂估";
                        }else if(value == "3"){
                            return "采购发票冲差";
                        }else if(value == "4"){
                            return "收款支付";
                        }else if(value == "5"){
                            return "货款支付凭证（付款单）";
                        }else if(value == "6"){
                            return "货款支付凭证（采购发票）";
                        }else if(value == "7"){
                            return "销售凭证";
                        }else if(value == "8"){
                            return "收款支付凭证(回笼表)";
                        }else if(value == "9"){
                            return "费用支付凭证(日常费用)";
                        }else if(value == "10"){
                            return "费用支付凭证(货款录入)";
                        }else if(value == "11"){
                            return "代垫录入凭证";
                        }
                    }
                },
                {field:'versionparam',title:'版本参数',width:100,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value == "0"){
                            return "通用参数0";
                        }else if(value == "1"){
                            return "通用参数1";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'addusername',title:'制单人',width:60,isShow:true},
                {field:'addtime',title:'制单时间',width:80,isShow:true,sortable:true},
                {field:'remark',title:'备注',width:80,isShow:true}

            ]],
        });
        $("#erpconnect-table-voucherHistoeryPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:false,
            pageSize:100,
            singleSelect:false,
            sortName: 'addtime',
            sortOrder: 'desc',
            url:'erpconnect/getVoucherHistoeryData.do',
            queryParam:initQueryJSON,
            toolbar:'#erpconnect-toolbar-voucherHistoeryPage'
        });
        //查询
        $("#erpconnect-queay-voucherHistoeryPage").click(function(){
            var queryJSON = $("#erpconnect-form-voucherHistoeryPage").serializeJSON();
            $("#erpconnect-table-voucherHistoeryPage").datagrid('load', queryJSON);
        });
        //重置
        $("#erpconnect-resetQueay-voucherHistoeryPage").click(function(){
            $("#erpconnect-form-voucherHistoeryPage").form("reset");
            var queryJSON = $("#erpconnect-form-voucherHistoeryPage").serializeJSON();
            $("#erpconnect-table-voucherHistoeryPage").datagrid('load', queryJSON);
        });

    });

</script>

</body>
</html>
