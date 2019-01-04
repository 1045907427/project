<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售日报表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="report-datagrid-jmSalesTargetDayReport"></table>
<div id="report-toolbar-jmSalesTargetDayReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/jmSalesTargetDayExport.do">
            <a href="javaScript:void(0);" id="report-export-jmSalesTargetDayReport" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
        </security:authorize>
        <security:authorize url="/report/sales/importJmSalesTarget.do">
            <a href="javaScript:void(0);" id="report-import-jmSalesTargetDayReport" class="easyui-linkbutton button-list" iconCls="button-import" plain="true">导入销售目标</a>
        </security:authorize>
        <security:authorize url="/report/sales/saveJmSalesTarget.do">
            <a href="javaScript:void(0);" id="report-save-jmSalesTargetDayReport" class="easyui-linkbutton button-list" iconCls="button-save" plain="true">保存</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-jmSalesTargetDayReport" method="post">
        <input type="hidden" name="targetdate"/>
        <table>
            <tr>
                <td>日期:</td>
                <td><input type="text" id="report-query-businessdate"  name="businessdate" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> </td>
                <td>品牌业务员:</td>
                <td><input type="text" id="report-query-branduser" name="branduser" /></td>
                <td>人员部门:</td>
                <td><input type="text" id="report-query-dept" name="dept" /></td>
                <td colspan="2"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-jmSalesTargetDayReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-jmSalesTargetDayReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="report-dialog-jmSalesTargetDayReport"></div>
<div id="report-dialog-receivenumDetailPage"></div>
<div id="report-dialog-sendnumDetailPage"></div>
<script type="text/javascript">
    var editfiled = null;
    var editIndex = undefined;
    var $datalist = $('#report-datagrid-jmSalesTargetDayReport');
    function endEditing(){
        if (editIndex == undefined){
            return true
        }
        var row = $datalist.datagrid('getRows')[editIndex];
        var targetamount = row.targetamount;
        var workday = row.workday;
        if(editfiled=='targetamount'){

            var ed = $datalist.datagrid('getEditor', {index:editIndex,field:"targetamount"});
            var newtargetamount=0;
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                newtargetamount=getNumberBoxObject(ed.target).val();
            }
            if(undefined != newtargetamount){
                row.targetamount=newtargetamount;
                $datalist.datagrid('endEdit', editIndex);
            }else{
                return false;
            }
        }else if(editfiled=='workday'){
            var ed = $datalist.datagrid('getEditor', {index:editIndex,field:"workday"});
            var newworkday=0;
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                newworkday=getNumberBoxObject(ed.target).val();
            }
            if(undefined != newworkday){
                row.workday=newworkday;
                $datalist.datagrid('endEdit', editIndex);
            }else{
                return false;
            }

        }
        if(row.inittargetamount != targetamount || row.initworkday != workday){
            row.isedit = "1";
        }else{
            row.isedit = "0";
        }
        $datalist.datagrid('updateRow',{index:editIndex, row:row});
        if(row.isedit == "1"){
            $datalist.datagrid('checkRow',editIndex);
        }else{
            $datalist.datagrid('uncheckRow',editIndex);
        }
        editIndex = undefined;
        return true;
    }

    function onClickRow(index, field){
        if (endEditing()){
            editIndex = index;
            if(field=='targetamount' || field=='workday'){
                editfiled=field;
            }
            $datalist.datagrid('selectRow', index).datagrid('editCell', {index:index,field:editfiled})

            var ed = $datalist.datagrid('getEditor', {index:editIndex,field:editfiled});
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
            }
        }
    }
    
    var initQueryJSON = $("#report-query-form-jmSalesTargetDayReport").serializeJSON();
    var dataListJson = $("#report-datagrid-jmSalesTargetDayReport").createGridColumnLoad({
        commonCol : [[
            {field: 'ck', checkbox: true,rowspan:2},
            {field:'branduser',title:'品牌业务员编码',width:130,sortable:true,rowspan:2,hidden:true},
            {field:'brandusername',title:'品牌业务员',width:130,sortable:true,rowspan:2},
            {field:'dept',title:'人员部门',width:90,rowspan:2,
                formatter:function(value,rowData,rowIndex){
                    if (rowData.branduser == '') {
                        return '小计';
                    }
                    return rowData.deptname;
                }
            },
            {field:'targetamount',title:'${month}月任务(万)',width:80,rowspan:2,
                editor:{
                    type:'numberbox',
                    options:{
                        min: 0,
                        precision: 0
                    }
                },
            },
            {field:'workday',title:'工作天数',width:80,rowspan:2,
                editor:{
                    type:'numberbox',
                    options:{
                        min: 0,
                        precision: 0
                    }
                },
                formatter:function(value,rowData,rowIndex){
                    if (rowData.branduser == '') {
                        return ' ';
                    }
                    return value;
                }
            },
            {field:'daytarget',title:'日均任务（万）',width:100,rowspan:2},
            {title:'今日完成情况',colspan:4},{title:'本月完成情况',colspan:3},
        ],
        [
            {field:'dayorderamount',title:'今日订单金额',width:100,sortable:true,align:'right'},
            {field:'daysaleoutamount',title:'今日发货单金额',width:110,sortable:true,align:'right'},
            {
                field: 'daycompletepercent', title: '今日完成占比', width: 100, sortable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (value == undefined) {
                        return '';
                    }
                    return value;
                }
            },
            {field:'daydifference',title:'今日差额',width:90,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if (value < 0) {
                        return '<span style="color: #C00000">' + -value + '</span>';
                    }
                    return value;
                }
            },
            {field:'monthsaleoutamount',title:'本月累计金额',width:110,sortable:true,align:'right'},
            {field:'monthcompletepercent',title:'本月完成占比',width:110,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if (value == undefined) {
                        return '';
                    }
                    return value;
                }
            },
            {field:'monthdifference',title:'本月累计差额（万）',width:110,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if (value < 0) {
                        return '<span style="color: #C00000">' +-value + '</span>';
                    }
                    return value;
                }
            },
        ]
        ]
    });
    $(function(){
        //保存
        $("#report-save-jmSalesTargetDayReport").click(function(){
            endEditing();
            var rows = $datalist.datagrid('getChecked');
            for(var i=0;i<rows.length;i++){
                if(rows[i].isedit != "1"){
                    var index = $datalist.datagrid('getRowIndex',rows[i]);
                    $datalist.datagrid('uncheckRow',index);
                }
                if(rows[i].workday==undefined||rows[i].workday==''||rows[i].targetamount==undefined||rows[i].targetamount==''){
                    $.messager.alert("提醒",rows[i].brandusername+"工作任务或工作天数为空!");
                    return false;
                }
            }
            rows = $datalist.datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选已修改的数据!");
                return false;
            }
            loading('提交中...');
            $.ajax({
                url :'report/sales/saveJmSalesTargetDay.do',
                type:'post',
                dataType:'json',
                data:{targetdate: $("input[name='targetdate']").val(),rowsjsonstr:JSON.stringify(rows)},
                success:function(json){
                    loaded();
                    $.messager.alert("提醒",json.msg);
                    $datalist.datagrid('reload');
                }
            });
        });


        $("#report-export-jmSalesTargetDayReport").Excel('export', {
            queryForm: "#report-query-form-jmSalesTargetDayReport",
            type: 'exportUserdefined',
            name: '销售日报表',
            url: 'report/sales/exportJmSalesTargetDayReport.do'
        });



        //品牌业务员
        $("#report-query-branduser").widget({
            referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
            singleSelect:false,
            width:120,
            onlyLeafCheck:true
        });
        //部门
        $("#report-query-dept").widget({
            referwid:'RT_T_SYS_DEPT',
            singleSelect:false,
            width:120,
            onlyLeafCheck:true
        });

        $("#report-import-jmSalesTargetDayReport").Excel('import',{
            type:'importUserdefined',
            url:'report/sales/importJmSalesTarget.do',

            onClose: function(){ //导入成功后窗口关闭时操作，
                $datalist.datagrid('reload');
            }
        });
        $("#report-datagrid-jmSalesTargetDayReport").datagrid({
            authority:dataListJson,
            frozenColumns: dataListJson.frozen,
            columns:dataListJson.common,
            method:'post',
            title:'',
            fit:true,
            singleSelect:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            toolbar:'#report-toolbar-jmSalesTargetDayReport',
            onClickCell: function(index, field, value){
                var row = $datalist.datagrid('getRows')[index];
                if(row.branduser=='' || row.branduser=='合计'){
                    return false;
                }
                endEditing();
            },
            onDblClickCell: function(index, field, value){
                var row = $datalist.datagrid('getRows')[index];
                if(row.branduser=='' || row.branduser=='合计'){
                    return false;
                }
                onClickRow(index, field);
            }
        }).datagrid("columnMoving");

        $("#report-query-deptid").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false
        });
        //查询
        $("#report-queay-jmSalesTargetDayReport").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-jmSalesTargetDayReport").serializeJSON();
            $("#report-datagrid-jmSalesTargetDayReport").datagrid({
                url: 'report/sales/showJmSalesTargetDayReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
            $("input[name='targetdate']").val($("#report-query-businessdate").val());
            var oldTime = (new Date($("#report-query-businessdate").val())).getTime();
            var month = new Date(oldTime).Format("MM");
            $(".datagrid-header-row td[field='targetamount']").children().html(parseInt(month)+'月任务(万)');
        });
        //重置
        $("#report-reload-jmSalesTargetDayReport").click(function(){
            $("input[name='targetdate']").val('');
            $("#report-query-branduser").widget("clear");
            $("#report-query-dept").widget("clear");

            $("#report-query-form-jmSalesTargetDayReport").form("reset");
            var queryJSON = $("#report-query-form-jmSalesTargetDayReport").serializeJSON();
            $("#report-datagrid-jmSalesTargetDayReport").datagrid('loadData',{total:0,rows:[]});
        });
    });




</script>
</body>
</html>
