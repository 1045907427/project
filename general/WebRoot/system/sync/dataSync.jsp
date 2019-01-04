<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>数据同步</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        input[type='checkbox']{vertical-align: middle;}
        fieldset{border:1px solid #333;padding:15px;line-height:1.4;}
        #div-msg{color: #1B5480;}
    </style>
</head>
<body>
<div id="sync-query-dataSyncList" style="padding:0px;height:auto">
    <fieldset>
        <legend>同步操作说明</legend>
        <div style="color:#f00">
            <strong>该功能是将源端数据库的数据同步到当前系统中,请谨慎操作!!!</strong><br>
            1.选择源数据库.(系统设置-基础平台-数据源配置)<br>
            2.点击"测试连接"确保数据库能够正常连接.<br>
            3.基础数据同步采用全表更新的方式,因此同步耗时很长,需要耐心等待<br>
            4.业务数据采用按月同步的方式,同步过来的数据将会以保存形式存在在目标库,审核验收等操作将会影响到目标库中的库存等信息.<br>
            5.<strong>业务数据多次同步将会导致目标库中的库存信息混乱等异常,因此业务数据只能同步一次,并且只能同步之前月份的数据.</strong>
        </div>
    </fieldset>
    <form action="" id="sync-form-dataSyncList" method="post">
        <table cellpadding="3" cellspacing="3" border="0">
            <tr>
                <td align="right">源数据库:</td>
                <td align="left">
                    <input type="text" name="dataSource" id="txt-sync-source"/>
                </td>
                <td>
                    <a href="javaScript:void(0);" id="btn-test-source" class="easyui-linkbutton"
                       data-options="plain:true,iconCls:'button-open'" title="测试连接">测试连接</a>
                </td>
            </tr>
            <tr>
                <td align="right">基础数据同步</td>
                <td>
                    <label><input type="checkbox" value="base" name="code"/>基础数据</label>
                    <label><input type="checkbox" value="sysnumber" name="code"/>单据编号规则</label>
                    <label><input type="checkbox" value="sales_promotion" name="code"/>销售促销数据</label>
                </td>
                <td>
                    <a href="javaScript:void(0);" id="btn-sync-base" class="easyui-linkbutton"
                       data-options="plain:true,iconCls:'button-open'" title="同步基础数据">同步基础数据</a>
                </td>
            </tr>
            <tr>
                <td>业务数据同步：</td>
                <td><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'%y-{%M-1}-%ld'})"/>
                </td>
                <td>
                    <a href="javaScript:void(0);" id="btn-sync-business" class="easyui-linkbutton"
                       data-options="plain:true,iconCls:'button-open'" title="同步业务数据">同步业务数据</a>
                </td>
            </tr>
        </table>
        <fieldset>
            <legend>同步结果说明</legend>
            <div id="div-msg"></div>
        </fieldset>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        $("#btn-test-source").click(function () {
            var value = $("#txt-sync-source").widget("getValue");
            if (value == "") {
                $.messager.alert("提示", "请选择源数据库");
                return;
            }
            $.getJSON("system/sysdatasource/testSysDataSource.do?code=" + value, function (data) {
                $.messager.alert("测试源数据库", data.msg);
            });
        });
        $("#btn-sync-base").click(function () {
            var queryJSON = $("#sync-form-dataSyncList").serializeJSON();
            if (queryJSON.dataSource == "") {
                $.messager.alert("提示", "请选择源端数据源");
                return false;
            }
            delete queryJSON.oldFromData;
            var list = $("input[name='code']:checked");
            if (list.length == 0) {
                $.messager.alert("提示", "请选择要同步的数据");
                return false;
            }
            loading("开始同步数据");
            var backMsg = "";
            var syncLength = 0;
            for (var i = 0; i < list.length; i++) {
                var formData = $.extend({}, queryJSON, {code: $(list[i]).val()});
                loading("同步数据中,预计需要几分钟，请等待...");
                $.ajax({
//                    async: false,
                    url: "system/sync/subDataSync.do",
                    data: formData,
                    dataType: "json",
                    success: function (backInfo) {
                        syncLength ++;
                        if(syncLength==list.length){
                            loaded();
                        }
                        backMsg += backInfo.msg;
                        $("#div-msg").html(backMsg);
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "同步出错");
                    }
                });
            }

        });
        $("#btn-sync-business").click(function () {
            var queryJSON = $("#sync-form-dataSyncList").serializeJSON();
            if (queryJSON.businessdate == "") {
                $.messager.alert("提示", "请选择业务日期范围");
                return false;
            }
            if (queryJSON.dataSource == "") {
                $.messager.alert("提示", "请选择源端数据源");
                return false;
            }
            delete queryJSON.oldFromData;
            loading("开始同步业务数据");
            var formData = $.extend({}, queryJSON, {code: "business" });
            $.ajax({
//                async: false,
                url: "system/sync/subDataSync.do",
                data: formData,
                dataType: "json",
                success: function (backInfo) {
                    $("#div-msg").html(backInfo.msg);
                    loaded();
                },
                error: function () {
                    loaded();
                    $.messager.alert("错误", "同步出错");
                }
            });
        });
        $("#txt-sync-source").widget({
            referwid: 'RL_T_SYS_DATASOURCE',
            singleSelect: true,
            onlyLeafCheck: false,
            width: '300',
            required: true
        });
    });
</script>
</body>
</html>
