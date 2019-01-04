<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>业务员拜访汇总报表</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        .radio-style{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .checkbox-style{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:25px;
            float:left;
            display: block;
        }
        .len60 {
            width: 60px;
        }
        .len120 {
            width: 120px;
        }
        .len185 {
            width: 185px;
        }
        .len200 {
            width: 200px;
        }
    </style>
</head>

<body>
<div id="crm-condition-personReportPage" >
    <div id="crm-toolbar-personReportPage" class="buttonBG"></div>
    <form id="crm-form-personReportPage" method="post">
        <input type="hidden" name="groupcols" id="crm-groupcols-personReportPage" value="personid"/>
        <table class="querytable">
            <tr>
                <td class="len60">日　　期：</td>
                <td class="len120"><input type="text" id="crm-month-personReportPage" name="month" style="width:180px;" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="<fmt:formatDate value="${today }" pattern="yyyy-MM" type="date" dateStyle="long" />" data-options="required:true"/></td>
                <td class="len60">业务员：</td>
                <td class="len200"><input type="text" id="crm-personid-personReportPage" name="personid" style="width: 180px;"/></td>
                <td class="len60">所属部门：</td>
                <td class="len185"><input type="text" id="crm-deptid-personReportPage" name="deptid" style="width: 180px;"/></td>
                <td></td>
            </tr>
            <tr>
                <td>客户分类：</td>
                <td><input type="text" id="crm-customersort-personReportPage" name="customersort" style="width: 180px;"/></td>
                <td>销售区域：</td>
                <td><input type="text" id="crm-salesarea-personReportPage" name="salesarea" style="width: 180px;"/></td>
                <td>主　　管：</td>
                <td><input type="text" id="crm-leadid-personReportPage" name="leadid" style="width: 180px;"/></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div>
                        <a href="javascript:void(0);" id="crm-query-personReportPage" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="crm-reset-personReportPage" class="button-qr">重置</a>
                    </div>
                </td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<table id="crm-datagrid-personReportPage"></table>
<script type="text/javascript">

    $(function () {

        // 人员
        $('#crm-personid-personReportPage').widget({
            referwid: 'RL_T_BASE_PERSONNEL',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 部门
        $('#crm-deptid-personReportPage').widget({
            referwid: 'RL_T_BASE_DEPARTMENT_1',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 客户分类
        $('#crm-customersort-personReportPage').widget({
            referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 销售区域
        $('#crm-salesarea-personReportPage').widget({
            referwid: 'RT_T_BASE_SALES_AREA',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 主管
        $('#crm-leadid-personReportPage').widget({
            referwid: 'RL_BASEPERSONNEEL_LEADER',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        var cols = generateCols($('#crm-month-personReportPage').val());

        $('#crm-datagrid-personReportPage').datagrid({
            authority: cols,
            frozenColumns: cols.frozenCol,
            columns: cols.commonCol,
            fit: true,
            title: '',
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            showFooter: true,
            sortName: 'personid',
            sortOrder: 'asc',
            sortable: true,
            toolbar: '#crm-condition-personReportPage'
        }).datagrid("columnMoving");

        $('[type=checkbox][name=group]').click(function(e) {

            var groupcols = new Array();
            $('[type=checkbox][name=group]').each(function(index, item) {

                if($(this).is(':checked')) {
                    groupcols.push($(this).val());
                }
            });

            $('#crm-groupcols-personReportPage').val(groupcols.join(','));
        });

        // 查询
        $('#crm-query-personReportPage').click(function(e) {

            var flag = $('#crm-form-personReportPage').form('validate');
            if(flag == false) {
                return false;
            }

            var cols = generateCols($('#crm-month-personReportPage').val());

            $('#crm-datagrid-personReportPage').datagrid({
                authority: cols,
                frozenColumns: cols.frozenCol,
                columns: cols.commonCol,
                fit: true,
                title: '',
                method: 'post',
                rownumbers: true,
                pagination: true,
                idField: 'id',
                singleSelect: false,
                selectOnCheck: true,
                checkOnSelect: true,
                showFooter: true,
                sortName: ($('#crm-groupcols-personReportPage').val() || 'personid').split(",")[0],
                sortOrder: 'asc',
                toolbar: '#crm-condition-personReportPage',
                url: ''
            }).datagrid("columnMoving");

            var param = $('#crm-form-personReportPage').serializeJSON();
            param.groupcols = param.groupcols || 'personid';
            //setCols(param.groupcols);

            $('#crm-datagrid-personReportPage').datagrid({
                url: 'crm/visit/report/getPersonReportData.do',
                queryParams: param
            });

        });

        // 重置
        $('#crm-reset-personReportPage').click(function(e) {

            $('#crm-personid-personReportPage').widget('clear');
            $('#crm-deptid-personReportPage').widget('clear');
            $('#crm-customersort-personReportPage').widget('clear');
            $('#crm-salesarea-personReportPage').widget('clear');
            $('#crm-leadid-personReportPage').widget('clear');

            $("#crm-form-personReportPage")[0].reset();
            $('#crm-month-personReportPage').val('<fmt:formatDate value="${today }" pattern="yyyy-MM" type="date" dateStyle="long" />');
            $('#crm-groupcols-personReportPage').val('personid');
            $('#crm-datagrid-personReportPage').datagrid('loadData', {total: 0, rows: [], footer: []});

        });

        // 按钮
        $("#crm-toolbar-personReportPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/crm/visit/report/exportPersonReportData.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#crm-form-personReportPage",
                        datagrid:'#crm-datagrid-personReportPage',
                        type: 'exportUserdefined',
                        name: '业务员拜访客户汇总报表',
                        url: 'crm/visit/report/exportPersonReportData.do'
                    }

                },
                </security:authorize>
                {}
            ],
            model:'bill',
            type:'list'
        });

    });

    /**
     * 获得月份天数
     *
     * @param month yyyy-MM
     * @returns {number}
     * @constructor
     */
    function getMonthDay(month) {
        var year = month.substr(0, 4);
        var month = month.substr(5, 4);
        return 32 - new Date(year, parseInt(month) - 1, 32).getDate();
    }

    /**
     *
     * @param month
     * @returns {jQuery}
     */
    function generateCols(month) {
        var oldCols = {
            frozenCol: [[
                {field: 'rowcheck', checkbox: true}
            ]],
            commonCol: [[
                {field: 'month', title: '月份', width: 60, hidden: true},
                {field: 'personid', title: '业务员', width: 90, hidden: false,
                    formatter: function(value, row, index){
                        return row.personname;
                    }
                },
                {field: 'deptid', title: '所属部门', width: 90, hidden: false,
                    formatter: function(value, row, index){
                        return row.deptname;
                    }
                }
            ]]
        };
        // 当月天数
        var days = getMonthDay(month);
        for(var i = 1; i <= days; i++) {
            oldCols.commonCol[0].push({
                field: 'day' + i,
                title: i + '号' + (getWeekday(month + '-' + i)),
                width: 60,
                formatter: function (value, row, index) {
                    return value ? (value/60).toFixed(2) : "";
                    //return value ? (Math.floor(value / 60) + ":" + value % 60) : "";
                }
            });
        }
        oldCols.commonCol[0].push({field: 'total', title: '合计', width: 60, hidden: false, align:'center',
            formatter:function(value,row,index){
                return value ? (value/60).toFixed(2) : "";
                //return s ? (Math.floor(s / 60) + ":" + s % 60) : "";
            }
        });
        return oldCols;
    }

    /**
     * 设置显示的列
     *
     * @returns {boolean}
     */
    function setCols(groupcols) {

        $('#crm-datagrid-personReportPage').datagrid('hideColumn', 'personid');
        $('#crm-datagrid-personReportPage').datagrid('hideColumn', 'deptid');
        $('#crm-datagrid-personReportPage').datagrid('hideColumn', 'customersortname');
        $('#crm-datagrid-personReportPage').datagrid('hideColumn', 'salesareaname');

        if(groupcols.indexOf('customersort') >= 0) {
            $('#crm-datagrid-personReportPage').datagrid('showColumn', 'customersortname');
        }
        if(groupcols.indexOf('salesarea') >= 0) {
            $('#crm-datagrid-personReportPage').datagrid('showColumn', 'salesareaname');
        }
        if(groupcols.indexOf('personid') >= 0) {
            $('#crm-datagrid-personReportPage').datagrid('showColumn', 'personid');
            $('#crm-datagrid-personReportPage').datagrid('showColumn', 'deptid');
        }
        return true;
    }

    /**
     * 填充字符串
     *
     * @param str
     * @param len
     * @param fillChar
     * @returns {string}
     */
    function leftFillStr(str, len, fillChar) {

        str = str + '';
        var fillStr = '';
        for(var i = 0; i < len; i++) {
            fillStr = fillStr + fillChar;
        }

        return fillStr.substr(0, len - str.length) + str;
    }

    /**
     * 判断当天是星期几，如 '2016-09-21' → (三)
     *
     * @param date
     * @returns {string}
     */
    function getWeekday(date) {

        var dateArr = date.split('-');
        var year = parseInt(dateArr[0] || 1970);
        var month = parseInt(dateArr[1] || 1) - 1;
        var day = parseInt(dateArr[2] || 1);

        var d = new Date();
        d.setFullYear(year, month, day);

        var weekday = parseInt(d.getDay());
        switch (weekday) {
            case 0: {
                return '(日)';
            }
            case 1: {
                return '(一)';
            }
            case 2: {
                return '(二)';
            }
            case 3: {
                return '(三)';
            }
            case 4: {
                return '(四)';
            }
            case 5: {
                return '(五)';
            }
            case 6: {
                return '(六)';
            }
        }
    }

    /**
     *
     * @param isplan    1：线路内；0：线路外；空：全部
     * @param index     行号
     * @param day       几号 1~31：1~31号；0：当月全部
     * @returns {boolean}
     */
    function openVisitRecortListPage(isplan, index, day) {

        var row = $('#crm-datagrid-personReportPage').datagrid('getRows')[index];
        var params = $('#crm-datagrid-personReportPage').datagrid('options').queryParams;

        var personid = row.personid || params.personid;
        var deptid = row.deptid || params.deptid;
        var customersort = row.customersort || params.customersort;
        var salesarea = row.salesarea || params.salesarea;
        var startdate = row.month + '-' + leftFillStr(day, 2, '0');
        var enddate = row.month + '-' + leftFillStr(day, 2, '0');

        // 当月全部
        if(day == 0) {

            startdate = row.month + '-01';
            enddate = row.month + '-' + getMonthDay(params.month);
        }

        var url = 'crm/visitrecord/crmVisitRecordListPage.do?startdate=' + startdate
            + '&enddate=' + enddate
            + '&personid=' + personid
            + '&deptid=' + deptid
            + '&customersort=' + customersort
            + '&salesarea=' + salesarea
            + '&isplan=' + isplan
            + '&from=report';
        top.addOrUpdateTab(url, '客户拜访记录');

        return true;
    }

</script>
</body>
</html>
