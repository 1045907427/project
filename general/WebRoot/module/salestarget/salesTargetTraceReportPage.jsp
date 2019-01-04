<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售目标跟踪报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<div id="salestarget-table-salestargetTraceReportPageBtn" style="padding:2px;height:auto">
    <form action="" id="salestarget-form-salesTargetTraceReportQuery" method="post">
        <table class="querytable">
            <tr>
                <security:authorize url="/module/salestargetreport/exportSalesTargetTraceReportDataBtn.do">
                    <a href="javaScript:void(0);" id="salestarget-salestargetTraceReportPage-export-btn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    <a href="javaScript:void(0);" id="salestarget-salestargetTraceReportPage-export-excel" style="display:none">导出</a>
                </security:authorize>
            </tr>
            <tr>
                <td style="width: 60px;text-align: right;">月份:</td>
                <td style="width:160px;">
                    <select id="salestarget-salestargetTraceReportPage-query-month" name="month" style="width:150px;">
                        <option value="">不选</option>
                        <c:forEach var="month" begin="1" end="${iMonth}">
                            <c:choose>
                                <c:when test="${month == iMonth}">
                                    <option value="${month}" selected="selected">${month }月</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${month }">${month }月</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td style="text-align: right;">对比年份:</td>
                <td style="width:160px;">
                    <input id="salestarget-salestargetTraceReportPage-query-year" type="text" name="year" style="width:150px;cursor: pointer" class="Wdate" value="${lastyear }" readonly="readonly"/>
                </td>
                <td style="text-align: right;"><span title="业务日期">更新日期</span>:</td>
                <td><input type="text" id="salestarget-salestargetTraceReportPage-query-businessdate" name="businessdate" value="${today }" style="width:150px;cursor: pointer" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',onpicked:pickedBusinessFunc(dp)})" readonly="readonly" /></td>
            </tr>
            <tr>
                <td style="text-align: right;">渠道:</td>
                <td><input id="salestarget-salestargetTraceReportPage-widget-customersort" name="customersort" type="text" style="width: 150px;"/></td>
                <td style="text-align: right;">品牌:</td>
                <td>
                    <input id="salestarget-salestargetTraceReportPage-widget-brandid" name="brandid" type="text" style="width: 150px;"/>
                </td>
                <td style="text-align: right;">客户业务员:</td>
                <td><input id="salestarget-salestargetTraceReportPage-widget-salesuserid" name="salesuserid" type="text" style="width: 150px;"/></td>
            </tr>
            <tr>
                <td style="text-align: right;">月进度</td>
                <td>
                    <input id="salestarget-salestargetTraceReportPage-monthtimeschedule" name="monthtimeschedule" value="${monthTimeSchedule}" type="text" style="width: 150px;" class="readonly" readonly="readonly"/>
                </td>
                <td style="text-align: right;">年进度</td>
                <td>
                    <input id="salestarget-salestargetTraceReportPage-yeartimeschedule" name="yeartimeschedule" value="${yearTimeSchedule}" type="text" style="width: 150px;" class="readonly" readonly="readonly"/>
                </td>
                <td colspan="2" style="text-align: center">
                    <a href="javaScript:void(0);" id="salestarget-query-salestargetTraceReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="salestarget-query-salestargetTraceReportPage-reloadList" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<table id="salestarget-table-salestargetTraceReportPage"></table>
<div style="display:none">
    <div id="salestarget-dialog-add-operate"></div>
    <div id="salestarget-dialog-edit-operate"></div>
    <div id="salestarget-dialog-view-operate"></div>
    <a href="javaScript:void(0);" id="salestarget-salestargetTraceReportPage-buttons-exportclick" style="display: none"title="导出">导出</a>
</div>

<script type="text/javascript">
    var footerobject=null;
    var report_lastyear="${lastyear}";

    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var costsFeeListColJson=$("#salestarget-table-salestargetTraceReportPage").createGridColumnLoad({
        //name:'t_salestarget_tracereport',
        frozenCol:[[
            //{field:'idok',checkbox:true,isShow:true}
        ]],
        commonCol:[[
            {field:'subject',title:'科目值',isShow:true,hidden:true},
            {field:'subjectname',title:'月份',isShow:true},
            <c:forEach var="month" begin="1" end="12">
            {field:'month_${month }',title:'${month}月',align:'right',resizable:true,
                styler: function(value,row,index){
                    return "min-width:75px;";
                }
            },
            </c:forEach>
            {field:'summarycolumn',title:'合计',resizable:true,sortable:true,align:'right',isShow:true}
        ]]
    });
    function getGridRowStyle(subject){
        if(subject==null || $.trim(subject)==""){
            return "";
        }
        subject= $.trim(subject);
        var subject_Color1=["JNDYXSMB","JNDEXSMB"];
        var subject_Color2=["JNXS","JNXSLJ","YDCL_DYXSMB"];
        var subject_Color3=["YCYE_DYXSMB","YCYE_DEXSMB"];
        var subject_Color4=["LJCYE_DYXSMB","LJCYE_DEXSMB"];
        if($.inArray(subject, subject_Color1)>=0){
            return "background-color:#CEFFCE;";
        }else if($.inArray(subject, subject_Color2)>=0){
            return "background-color:#FFF4C1;";
        }else if($.inArray(subject, subject_Color3)>=0){
            return "background-color:#FFD9EC";
        }else if($.inArray(subject, subject_Color4)>=0){
            return "background-color:#FFB5B5";
        }
        return "";
    }
    $(function(){

        var initQueryJSON = $("#salestarget-form-salesTargetTraceReportQuery").serializeJSON()
        $("#salestarget-table-salestargetTraceReportPage").datagrid({
            authority:costsFeeListColJson,
            frozenColumns:costsFeeListColJson.frozen,
            columns:costsFeeListColJson.common,
            fit:true,
            method:'post',
            title:'',
            showFooter: true,
            rownumbers:true,
            pagination:false,
            idField:'subject',
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            queryParams:initQueryJSON,
            //pageSize:100,
            toolbar:'#salestarget-table-salestargetTraceReportPageBtn',
            loadMsg:'正在计算报表数据，请耐心等待！',
            onLoadSuccess:function(){
            },
            rowStyler: function(index,row){
                if (row.subject!=null && row.subject!=""){
                    return getGridRowStyle(row.subject); // return inline style
                }
            }
        }).datagrid("columnMoving");

        //查询
        $("#salestarget-query-salestargetTraceReportPage").click(function(){
            var year=$("#salestarget-salestargetTraceReportPage-query-year").val()||"";
            if(null==year || ""==$.trim(year)){
                $.messager.alert("提醒","请选择要查询的年份!");
                return false;
            }
            /*
            var month=$("#salestarget-salestargetTraceReportPage-query-month").val() || "";
            for(var i=1; i<=12;i++){
                if(month!="" && 1<=month && month<=12){
                    if(i<=month){
                        $("#salestarget-table-salestargetTraceReportPage").datagrid('showColumn', "month_"+i);
                    }else{
                        $("#salestarget-table-salestargetTraceReportPage").datagrid('hideColumn', "month_"+i);
                    }
                }else{
                    $("#salestarget-table-salestargetTraceReportPage").datagrid('showColumn', "month_"+i);
                }
            }
            */
            calcYearAndMonthTimeSchedule();
            var queryJSON = $("#salestarget-form-salesTargetTraceReportQuery").serializeJSON();
            $("#salestarget-table-salestargetTraceReportPage").datagrid({
                url: 'module/salestargetreport/showSalesTargetTraceReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            }).datagrid("columnMoving");
        });

        //重置按钮
        $("#salestarget-query-salestargetTraceReportPage-reloadList").click(function(){
            $("#salestarget-form-salesTargetTraceReportQuery")[0].reset();
            $("#salestarget-salestargetTraceReportPage-widget-customersort").widget('clear');
            $("#salestarget-salestargetTraceReportPage-widget-salesuserid").widget('clear');
            $("#salestarget-salestargetTraceReportPage-widget-brandid").widget('clear');
            $("#salestarget-table-salestargetTraceReportPage").datagrid('loadData',{total:0,rows:[]});
            //$("#salestarget-table-salestargetTraceReportPage").datagrid('clearSelections');
        });
        <security:authorize url="/module/salestargetreport/exportSalesTargetTraceReportDataBtn.do">
        $("#salestarget-salestargetTraceReportPage-export-btn").click(function(){

            var year=$("#salestarget-salestargetTraceReportPage-query-year").val();
            var businessdate=$("#salestarget-salestargetTraceReportPage-query-businessdate").val();
            if(null==businessdate || "" == businessdate){
                $.messager.alert("提醒","抱歉，请填写更新日期");
                return false;
            }
            if(null==year || "" == year){
                $.messager.alert("提醒","抱歉，请填写对比年份");
                return false;
            }
            var title="销售目标跟踪报表 " + businessdate;

            $("#salestarget-salestargetTraceReportPage-export-excel").Excel('export',{
                queryForm: "#salestarget-form-salesTargetTraceReportQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type:'exportUserdefined',
                exportparam:'销售目标跟踪报表导出时，请耐心等待',
                name:title,
                url:'module/salestargetreport/exportSalesTargetTraceReportData.do'
            });
            $("#salestarget-salestargetTraceReportPage-export-excel").trigger("click");
        });
        </security:authorize>

        $("#salestarget-salestargetTraceReportPage-widget-customersort").widget({ //分类
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view: true
        });
        $("#salestarget-salestargetTraceReportPage-widget-salesuserid").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false
        });
        $("#salestarget-salestargetTraceReportPage-widget-brandid").widget({
            //referwid:'RL_T_BASE_GOODS_BRAND',
            name:'t_salestarget_input',
            col:'brandid',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false
        });
        $("#salestarget-salestargetTraceReportPage-query-year").off("click").on("click",function(event){
            WdatePicker({'dateFmt':'yyyy',maxDate:report_lastyear});
        });
        $("#salestarget-salestargetTraceReportPage-query-businessdate").off("click").on("click",function(event){
            WdatePicker({'dateFmt':'yyyy-MM-dd',
                maxDate:'${today }',
                onpicking:function(dp){
                    pickedBusinessFunc(dp,"businessdate");
                }});
        });
    });
    function pickedBusinessFunc(dp){
        if(dp==null || dp.el==null || dp.el.id!="salestarget-salestargetTraceReportPage-query-businessdate"){
            return false;
        }
        var year=dp.cal.getP('y');
        var month=dp.cal.getP('M');
        report_lastyear=year-1;
        initTimeSelectFunc(year-1,month);
        return true;
    }

    function initTimeSelectFunc(reflastyear,refmonth){
        var month="${iMonth}" || 0;
        if(refmonth!=null && !isNaN(refmonth)){
            month=refmonth;
        }
        var lastyear="${lastyear}" || 0;
        if(reflastyear!=null && !isNaN(reflastyear)){
            lastyear=reflastyear;
        }
        $("#salestarget-salestargetTraceReportPage-query-year").val(lastyear);
        $("#salestarget-salestargetTraceReportPage-query-year").off("focus").on("focus",function(){
            WdatePicker({'dateFmt':'yyyy',maxDate:lastyear});
        });

        $("#salestarget-salestargetTraceReportPage-query-month").html("");
        var htmlsb=new Array();
        htmlsb.push("<option value=\"\">不选</option>");
        for(var i=1;i<=month;i++){
            htmlsb.push("<option value=\"");
            htmlsb.push(i);
            htmlsb.push("\"");
            if(i==month) {
                htmlsb.push("  selected=\"selected\"");
            }
            htmlsb.push(">");
            htmlsb.push(i);
            htmlsb.push("月</option>");
        }
        $("#salestarget-salestargetTraceReportPage-query-month").html(htmlsb.join(""));
    }
    function calcYearAndMonthTimeSchedule(){
        var businessdate=$("#salestarget-salestargetTraceReportPage-query-businessdate").val();
        if(null==businessdate || "" == businessdate){
            $.messager.alert("提醒","抱歉，请填写更新日期");
            return false;
        }
        var $monthTimeSchedule=$("#salestarget-salestargetTraceReportPage-monthtimeschedule");
        var $yearTimeSchedule=$("#salestarget-salestargetTraceReportPage-yeartimeschedule");
        $monthTimeSchedule.addClass("inputload");
        $monthTimeSchedule.val("计算中...");
        $yearTimeSchedule.addClass("inputload");
        $yearTimeSchedule.val("计算中...");
        $.ajax({
            url :'module/salestargetreport/getCalcYearAndMonthTimeSchedule.do',
            type:'post',
            dataType:'json',
            //async: false,
            cache:false,
            data:{businessdate:businessdate},
            success:function(json){
                $monthTimeSchedule.removeClass("inputload");
                $monthTimeSchedule.val("");
                $yearTimeSchedule.removeClass("inputload");
                $yearTimeSchedule.val("");
                if(json.flag==true){
                    var monthTimeSchedule=json.monthTimeSchedule||0;
                    var yearTimeSchedule=json.yearTimeSchedule||0;
                    $monthTimeSchedule.val(monthTimeSchedule+"%");
                    $yearTimeSchedule.val(yearTimeSchedule+"%");
                }
            },
            error:function(){
                $monthTimeSchedule.removeClass("inputload");
                $yearTimeSchedule.removeClass("inputload");
            }
        });
        var monthTimeScheduleVal=$monthTimeSchedule.val() ||"";
        if(monthTimeScheduleVal.indexOf("计算中")>=0){
            $monthTimeSchedule.val("");
        }
        var yearTimeScheduleVal=$yearTimeSchedule.val() ||"";
        if(yearTimeScheduleVal.indexOf("计算中")>=0){
            $yearTimeSchedule.val("");
        }
    }
</script>
</body>
</html>
