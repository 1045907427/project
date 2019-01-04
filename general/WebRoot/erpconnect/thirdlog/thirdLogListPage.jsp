<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>三方账套凭证对接日志</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="erpconnect-toolbar-thirdLogPage" style="height: 80px">
    <div class="buttonBG">
        <a href="javaScript:void(0);" id="thirdLog-button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">重新发送</a>
        <%--<a href="javaScript:void(0);" id="thirdLog-button-view" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-view'">查看凭证数据</a>--%>
        <a href="javaScript:void(0);" id="erpconnect-export-thirdLogPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    </div>
    <form id="erpconnect-form-thirdLogPage" method="post">
        <table>
            <tr>
                <td>制单时间：</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    到<input type="text" name="businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                <td>单据类型：</td>
                <td>
                    <select name="sourcetype" class="len150">
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
                        <option value="12">其它出库凭证</option>
                        <option value="13">其它入库凭证</option>
                        <option value="14">报溢调账单凭证</option>
                        <option value="15">报损调账单凭证</option>
                        <option value="16">申请开票凭证</option>
                        <option value="17">发货单未税凭证</option>
                    </select>
                </td>
                <td>单据编号：</td>
                <td><input type="text" name="sourceid" class="len150" /> </td>
            </tr>
            <tr>
                <td>对接账套：</td>
                <td><input id="erpconnect-thirdLog-dbid" type="text" name="dbid" style="width: 215px"/> </td>
                <td>凭证编号：</td>
                <td><input type="text" name="voucherid" class="len150" /> </td>
                <td>凭证状态：</td>
                <td>
                    <select name="status" class="len150">
                        <option></option>
                        <option value="0">待验证</option>
                        <option value="1">发送成功</option>
                        <option value="2">发送失败</option>
                        <option value="8">发送异常</option>
                        <option value="9">已失效</option>
                    </select>
                </td>
                <td colspan="2" class="tdbutton">
                    <a href="javascript:void(0);" id="erpconnect-queay-thirdLogPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="erpconnect-resetQueay-thirdLogPage" class="button-qr">重置</a>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td colspan="4"></td>--%>
                <%--<td colspan="2" class="tdbutton">--%>
                    <%--<a href="javascript:void(0);" id="erpconnect-queay-thirdLogPage" class="button-qr">查询</a>--%>
                    <%--<a href="javaScript:void(0);" id="erpconnect-resetQueay-thirdLogPage" class="button-qr">重置</a>--%>
                <%--</td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
<table id="erpconnect-table-thirdLogPage"></table>
<div id="erpconnect-dialog-thirdLogPage"></div>
<div id="erpconnect-dialog-sourceDataPage"></div>
<script type="text/javascript">
    var checkListJson = null;
    $(function() {
        var initQueryJSON = $("#erpconnect-form-thirdLogPage").serializeJSON();
        checkListJson =  $("#erpconnect-table-thirdLogPage").createGridColumnLoad({
            commonCol :[[
                {field:'id',title:'编码',hidden:true,width:80},
                {field:'dbid',title:'账套名称',width:80,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.dbname;
                    }
                },
                {field:'ymonth',title:'年月',width:70},
                {field:'iid',title:'凭证编号',width:70},
                {field:'vouchername',title:'凭证名称',width:70,hidden:true},
                {field:'sourcetype',title:'单据类型',width:120,
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
                        }else if(value == "12"){
                            return "其它出库凭证";
                        }else if(value == "13"){
                            return "其它入库凭证";
                        }else if(value == "14"){
                            return "报溢调账单凭证";
                        }else if(value == "15"){
                            return "报损调账单凭证";
                        }else if(value == "16"){
                            return "申请开票凭证";
                        }else if(value == "17"){
                            return "发货单未税凭证";
                        }
                    }
                },
                {field:'sourceids',title:'单据编号',width:250,
                    formatter:function(value,rowData){
                        return '<label onclick="showSourceData('+rowData.id+')" style="text-decoration:underline;cursor:hand;">'+value+'</label>';
                    }
                },
                {field: 'amount', title: '金额',align: 'right', sortable: false, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        if(value!=''){
                            return formatterMoney(value);
                        }
                    }
                },
                {field:'status',title:'凭证状态',width:80,
                    formatter:function(value){
                        if(value == "0"){
                            return "待验证";
                        }else if(value == "1"){
                            return "发送成功";
                        }else if(value == "2"){
                            return "发送失败";
                        }else if(value == "8"){
                            return "发送异常";
                        }else if(value == "9"){
                            return "已失效";
                        }
                    }
                },
                {field:'remark',title:'备注',width:80},
                {field:'tradeid',title:'交易号',width:120,hidden:true},
                {field:'addusername',title:'制单人',width:60},
                {field:'addtime',title:'制单时间',width:130,sortable:true},
                {field:'checktime',title:'验证时间',width:130,sortable:true,hidden:true},
                {field:'repeatusername',title:'重复发送人',width:80,hidden:true},
                {field:'repeattime',title:'重复发送时间',width:130,sortable:true,hidden:true},
                {field:'repeatnum',title:'重复发送次数',width:80,sortable:true,hidden:true}

            ]],
        });
        $("#erpconnect-table-thirdLogPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            pageSize:100,
            singleSelect:true,
            sortName: 'addtime',
            sortOrder: 'desc',
            url:'erpconnect/thirdlog/getThirdLogListData.do',
            queryParam:initQueryJSON,
            toolbar:'#erpconnect-toolbar-thirdLogPage',
            onDblClickRow:function (index,row) {
                showRequestData(row.id);
            }
        });
        $("#thirdLog-button-add").click(function(){
            var con = $("#erpconnect-table-thirdLogPage").datagrid('getSelected');
            if (con == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            if(con.status!='2' && con.status=='8'){
                $.messager.alert("提醒", "该记录不需要重新发送凭证");
                return false;
            }
            $.messager.confirm("提醒", "是否重新发送凭证？", function (r) {
                if (r) {
                    loading("重新发送中..");
                    $.ajax({
                        url: 'erpconnect/thirdlog/repeatThirdVoucher.do',
                        dataType: 'json',
                        type: 'post',
                        data: {id:con.id,dbid:con.dbid},
                        success: function (json) {
                            loaded();
                            if(json.flag){
                                $.messager.alert("提醒", "发送成功");
                                $("#erpconnect-table-thirdLogPage").datagrid("reload");
                            }else{
                                $.messager.alert("提醒", "发送失败。"+json.msg);
                            }
                        },
                        error: function () {
                            loaded();
                            $.messager.alert("错误", "发送失败");
                        }
                    });
                }
            });
        });
        $("#thirdLog-button-view").click(function(){
            var con = $("#erpconnect-table-thirdLogPage").datagrid('getSelected');
            if (con == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            showRequestData(id);

        });

        $("#erpconnect-thirdLog-dbid").widget({
            referwid:'RL_T_THIRDDB',
            onlyLeafCheck:true,
            singleSelect:true,
            width:215
        });
        //查询
        $("#erpconnect-queay-thirdLogPage").click(function(){
            var queryJSON = $("#erpconnect-form-thirdLogPage").serializeJSON();
            $("#erpconnect-table-thirdLogPage").datagrid('load', queryJSON);
        });
        //重置
        $("#erpconnect-resetQueay-thirdLogPage").click(function(){
            $("#erpconnect-thirdLog-dbid").widget("clear");
            $("#erpconnect-form-thirdLogPage").form("reset");
            var queryJSON = $("#erpconnect-form-thirdLogPage").serializeJSON();
            $("#erpconnect-table-thirdLogPage").datagrid('load', queryJSON);
        });

        //导出
        $("#erpconnect-export-thirdLogPage").Excel('export',{
            queryForm: "#erpconnect-form-thirdLogPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'三方账套凭证对接日志',
            url:'erpconnect/thirdlog/exportThirdlogData.do'
        });

    });
    function showRequestData(id){
        $("#erpconnect-dialog-thirdLogPage").dialog({
            title: '凭证数据查看',
            width: 600,
            height: 500,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            href: 'erpconnect/thirdlog/showThirdLogRequestDataPage.do?id='+id,
            modal: true
        });
        $("#erpconnect-dialog-thirdLogPage").dialog("open");
    }
    function showSourceData(id){
        $("#erpconnect-dialog-sourceDataPage").dialog({
            title: '凭证来源单据查看',
            width: 650,
            height: 500,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            queryParams:{
                id:id
            },
            href: 'erpconnect/thirdlog/showLogSourceDataPage.do',
            modal: true
        });
        $("#erpconnect-dialog-sourceDataPage").dialog("open");
    }
</script>

</body>
</html>
