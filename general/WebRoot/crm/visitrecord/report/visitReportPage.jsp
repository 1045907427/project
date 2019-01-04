<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>业务员巡店汇总报表</title>
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
<div id="crm-condition-visitReportPage" >
    <div id="crm-toolbar-visitReportPage" class="buttonBG"></div>
    <form id="crm-form-visitReportPage" method="post">
        <input type="hidden" name="groupcols" id="crm-groupcols-visitReportPage" value="personid"/>
        <table class="querytable">
            <tr>
                <td class="len60">日　　期：</td>
                <td class="len120"><input type="text" id="crm-month-visitReportPage" name="month" style="width:180px;" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="<fmt:formatDate value="${today }" pattern="yyyy-MM" type="date" dateStyle="long" />" data-options="required:true"/></td>
                <td class="len60">业务员：</td>
                <td class="len200"><input type="text" id="crm-personid-visitReportPage" name="personid" style="width: 180px;"/></td>
                <td class="len60">所属部门：</td>
                <td class="len185"><input type="text" id="crm-deptid-visitReportPage" name="deptid" style="width: 180px;"/></td>
                <td></td>
            </tr>
            <tr>
                <td>客户分类：</td>
                <td><input type="text" id="crm-customersort-visitReportPage" name="customersort" style="width: 180px;"/></td>
                <td>销售区域：</td>
                <td><input type="text" id="crm-salesarea-visitReportPage" name="salesarea" style="width: 180px;"/></td>
                <td>主　　管：</td>
                <td><input type="text" id="crm-leadid-visitReportPage" name="leadid" style="width: 180px;"/></td>
                <td></td>
            </tr>
            <tr>
                <td>统计方式：</td>
                <td>
                    <label class="divtext"><input type="radio" class="groupcols radio-style" name="category" value="1"/>线路内</label>
                    <label class="divtext"><input type="radio" class="groupcols radio-style" name="category" value="2"/>线路外</label>
                    <label class="divtext"><input type="radio" class="groupcols radio-style" name="category" value="3" checked="checked"/>全部</label>
                </td>
                <td>小计列：</td>
                <td>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox-style" name="group" value="personid" checked="checked"/>业务员</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox-style" name="group" value="customersort"/>客户分类</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox-style" name="group" value="salesarea"/>销售区域</label>
                </td>
                <td colspan="2" align="right">
                    <div>
                        <a href="javascript:void(0);" id="crm-query-visitReportPage" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="crm-reset-visitReportPage" class="button-qr">重置</a>
                    </div>
                </td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<table id="crm-datagrid-visitReportPage"></table>
<script type="text/javascript">

    $(function () {

        // 人员
        $('#crm-personid-visitReportPage').widget({
            referwid: 'RL_T_BASE_PERSONNEL',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 部门
        $('#crm-deptid-visitReportPage').widget({
            referwid: 'RL_T_BASE_DEPARTMENT_1',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 客户分类
        $('#crm-customersort-visitReportPage').widget({
            referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 销售区域
        $('#crm-salesarea-visitReportPage').widget({
            referwid: 'RT_T_BASE_SALES_AREA',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        // 主管
        $('#crm-leadid-visitReportPage').widget({
            referwid: 'RL_BASEPERSONNEEL_LEADER',
            singleSelect:true,
            width: 180,
            onlyLeafCheck:false
        });

        var cols = generateCols($('#crm-month-visitReportPage').val(), $('[type=radio][name=category]:checked').val());

        $('#crm-datagrid-visitReportPage').datagrid({
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
            toolbar: '#crm-condition-visitReportPage'
        }).datagrid("columnMoving");

        $('[type=checkbox][name=group]').click(function(e) {

            var groupcols = new Array();
            $('[type=checkbox][name=group]').each(function(index, item) {

                if($(this).is(':checked')) {
                    groupcols.push($(this).val());
                }
            });

            $('#crm-groupcols-visitReportPage').val(groupcols.join(','));
        });

        // 查询
        $('#crm-query-visitReportPage').click(function(e) {

            var flag = $('#crm-form-visitReportPage').form('validate');
            if(flag == false) {
                return false;
            }

            var cols = generateCols($('#crm-month-visitReportPage').val(), $('[type=radio][name=category]:checked').val());

            $('#crm-datagrid-visitReportPage').datagrid({
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
                sortName: ($('#crm-groupcols-visitReportPage').val() || 'personid').split(",")[0],
                sortOrder: 'asc',
                toolbar: '#crm-condition-visitReportPage',
                url: ''
            }).datagrid("columnMoving");

            var param = $('#crm-form-visitReportPage').serializeJSON();
            param.groupcols = param.groupcols || 'personid';
            setCols(param.groupcols);

            $('#crm-datagrid-visitReportPage').datagrid({
                url: 'crm/visit/report/getVisitReportData.do',
                queryParams: param
            });

        });

        // 重置
        $('#crm-reset-visitReportPage').click(function(e) {

            $('#crm-personid-visitReportPage').widget('clear');
            $('#crm-deptid-visitReportPage').widget('clear');
            $('#crm-customersort-visitReportPage').widget('clear');
            $('#crm-salesarea-visitReportPage').widget('clear');
            $('#crm-leadid-visitReportPage').widget('clear');

            $("#crm-form-visitReportPage")[0].reset();
            $('#crm-month-visitReportPage').val('<fmt:formatDate value="${today }" pattern="yyyy-MM" type="date" dateStyle="long" />');
            $('#crm-groupcols-visitReportPage').val('personid');
            $('#crm-datagrid-visitReportPage').datagrid('loadData', {total: 0, rows: [], footer: []});

        });

        // 按钮
        $("#crm-toolbar-visitReportPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/crm/visit/report/exportVisitReportData.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#crm-form-visitReportPage",
                        datagrid:'#crm-datagrid-visitReportPage',
                        type: 'exportUserdefined',
                        name: '业务员巡店汇总报表',
                        url: 'crm/visit/report/exportVisitReportData.do'
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
     * @param category
     * @returns {jQuery}
     */
    function generateCols(month, category) {

        category = category || '3';

        var oldCols = {
            frozenCol: [[
                {field: 'rowcheck', checkbox: true}
            ]],
            commonCol: [[
                {field: 'month', title: '月份', width: 60, rowspan: category == '1' ? 2 : 1, hidden: true},
                {field: 'personid', title: '业务员', width: 90, rowspan: category == '1' ? 2 : 1, hidden: false,
                    formatter: function(value, row, index){
                        return row.personname;
                    }
                },
                {field: 'deptid', title: '所属部门', width: 90, rowspan: category == '1' ? 2 : 1, hidden: false,
                    formatter: function(value, row, index){
                        return row.deptname;
                    }
                },
                {field: 'customersortname', title: '客户分类', width: 90, rowspan: category == '1' ? 2 : 1, hidden: false},
                {field: 'salesareaname', title: '销售区域', width: 90, rowspan: category == '1' ? 2 : 1, hidden: false}
            ]]
        };

        // 当月天数
        var days = getMonthDay(month);

        // 线路内
        if(category == '1') {

            oldCols.commonCol.push(new Array());
            for(var i = 1; i <= days; i++) {
                oldCols.commonCol[0].push({field: 'day' + (leftFillStr(i, 2, '0')), title: i +'号' + (getWeekday(month + '-' + i)), width: 350, rowspan: 1, colspan:3, hidden: false, align:'center'});
                oldCols.commonCol[1].push((function(i){
                    return {field: 'isplan' + (leftFillStr(i, 2, '0')), title: '线路内数量', width: 70, rowspan: 1, hidden: false, align:'right',
                        formatter: function(value, row, index){
                            if(row.month) {
                                return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(1, ' + index + ', ' + i + ')">' + value + '</a>';
                            } else {
                                return value;
                            }
                        }
                    };
                })(i));
                oldCols.commonCol[1].push({field: 'plan' + (leftFillStr(i, 2, '0')), title: '计划数量', width: 70, rowspan: 1, hidden: false, align:'right',});
                oldCols.commonCol[1].push({field: 'rate' + (leftFillStr(i, 2, '0')), title: '完成率', width: 70, rowspan: 1, hidden: false, align:'right',});
            }
            oldCols.commonCol[0].push({field: 'total', title: '合计', width: 350, rowspan: 1, colspan:3, hidden: false, align:'center'});
            oldCols.commonCol[1].push({field: 'isplan', title: '线路内数量', width: 70, rowspan: 1, hidden: false, align:'right',
                formatter: function(value, row, index){
                    if(row.month) {
                        return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(1, ' + index + ', 0)">' + value + '</a>';
                    } else {
                        return value;
                    }
                }
            });
            oldCols.commonCol[1].push({field: 'plan', title: '计划数量', width: 70, rowspan: 1, hidden: false, align:'right'});
            oldCols.commonCol[1].push({field: 'rate', title: '完成率', width: 70, rowspan: 1, hidden: false, align:'right'});

        // 线路外
        } else if(category == '2') {

            for(var i = 1; i <= days; i++) {

                oldCols.commonCol[0].push(function(i){
                    return {field: 'noplan' + (leftFillStr(i, 2, '0')), title: i +'号' + getWeekday(month + '-' + i), width: 70, rowspan: 1, colspan:1, hidden: false, align:'right',
                        formatter: function(value, row, index){
                            if(row.month) {
                                return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(0, ' + index + ', ' + i + ')">' + value + '</a>';
                            } else {
                                return value;
                            }
                        }
                    };
                }(i));
            }
            oldCols.commonCol[0].push({field: 'total_noplan', title: '合计', width: 70, rowspan: 1, colspan:1, hidden: false, align:'right',
                formatter: function(value, row, index){
                    if(row.month) {
                        return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(0, ' + index + ', 0)">' + value + '</a>';
                    } else {
                        return value;
                    }
                }
            });

        // 全部
        } else if(category == '3') {

            for(var i = 1; i <= days; i++) {

                oldCols.commonCol[0].push((function(i){

                    return {field: 'day' + (leftFillStr(i, 2, '0')) + '_sum', title: i +'号' + getWeekday(month + '-' + i), width: 70, rowspan: 1, colspan:1, hidden: false, align:'right',
                        formatter: function(value, row, index){
                            if(row.month) {
                                return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(\'\', ' + index + ', ' + i + ')">' + value + '</a>';
                            } else {
                                return value;
                            }
                        }
                    };

                })(i));
            }
            oldCols.commonCol[0].push({field: 'total_sum', title: '合计', width: 70, rowspan: 1, colspan:1, hidden: false, align:'right',
                formatter: function(value, row, index){
                    if(row.month) {
                        return '<a href="javascript:void(0);" onclick="javascript:openVisitRecortListPage(\'\', ' + index + ', 0)">' + value + '</a>';
                    } else {
                        return value;
                    }
                }
            });

        }

        return oldCols;
    }

    /**
     * 设置显示的列
     *
     * @returns {boolean}
     */
    function setCols(groupcols) {

        $('#crm-datagrid-visitReportPage').datagrid('hideColumn', 'personid');
        $('#crm-datagrid-visitReportPage').datagrid('hideColumn', 'deptid');
        $('#crm-datagrid-visitReportPage').datagrid('hideColumn', 'customersortname');
        $('#crm-datagrid-visitReportPage').datagrid('hideColumn', 'salesareaname');

        if(groupcols.indexOf('customersort') >= 0) {
            $('#crm-datagrid-visitReportPage').datagrid('showColumn', 'customersortname');
        }
        if(groupcols.indexOf('salesarea') >= 0) {
            $('#crm-datagrid-visitReportPage').datagrid('showColumn', 'salesareaname');
        }
        if(groupcols.indexOf('personid') >= 0) {
            $('#crm-datagrid-visitReportPage').datagrid('showColumn', 'personid');
            $('#crm-datagrid-visitReportPage').datagrid('showColumn', 'deptid');
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

        var row = $('#crm-datagrid-visitReportPage').datagrid('getRows')[index];
        var params = $('#crm-datagrid-visitReportPage').datagrid('options').queryParams;

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
