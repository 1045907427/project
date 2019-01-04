<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>拜访计划页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="crm-backid-crmPlanPage" value="${id }" />
<input type="hidden" id="report-query-groupcols" value=""/>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="crm-buttons-crmPlanPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="crm-panel-crmPlanPage"></div>
    </div>
</div>
<script type="text/javascript">
    var plan_url = "";
    var type = '${type}';
    var personid =  '${personid}';
    var id = '${id}';
    if(type == 'edit'){
        plan_url = "crm/visitplan/crmEditPage.do?id="+id;
    }else if(type == 'add'){
        plan_url = "crm/visitplan/crmPlanAddPage.do";
    }

    var crmPlanDetailJson = $("#crm-datagrid-crmPlanPage").createGridColumnLoad({
        //name :'t_crm_visit_plan_detail',
        frozenCol : [[
            {field:'id',title:'编号',width:100,hidden:true}
        ]],
        commonCol : [[
//                 {field:'billid',title:'计划单编号',width:100},
//                 {field:'personid',title:'客户编号',width:100},
            {field:'day1',title:'周一客户ID',width:150,hidden:true},
            {field:'day2',title:'周二客户ID',width:150,hidden:true},
            {field:'day3',title:'周三客户ID',width:150,hidden:true},
            {field:'day4',title:'周四客户ID',width:150,hidden:true},
            {field:'day5',title:'周五客户ID',width:150,hidden:true},
            {field:'day6',title:'周六客户ID',width:150,hidden:true},
            {field:'day7',title:'周日客户ID',width:150,hidden:true},
            {field:'day1info',title:'周一',width:150},
            {field:'day2info',title:'周二',width:150},
            {field:'day3info',title:'周三',width:150},
            {field:'day4info',title:'周四',width:150},
            {field:'day5info',title:'周五',width:150},
            {field:'day6info',title:'周六',width:150},
            {field:'day7info',title:'周日',width:150}
        ]]
    });

    $(function(){
        $("#crm-buttons-crmPlanPage").buttonWidget({
    initButton:[
                {},
      <security:authorize url="/crm/visitplan/crmAddPlanPage.do">
        {
            type: 'button-add',
            handler: function(){
                top.addTab('crm/visitplan/crmPlanPage.do?type=add', "客户拜访计划新增");
            }
        },
        </security:authorize>
        <security:authorize url="/crm/planPageSave.do">
        {
            type: 'button-save',
            handler: function(){
                var datarows=$("#crm-datagrid-crmPlanPage").datagrid('getRows');
                var rowsList = new Array();
                var a = 0;
                for(var i = 0 ;i<datarows.length;i++){
                    if(datarows[i].day1 != '' || datarows[i].day2 != '' ||datarows[i].day3 != '' ||datarows[i].day4 != '' ||
                            datarows[i].day5 != '' ||datarows[i].day6 != '' ||datarows[i].day7 != '' ){
                        rowsList.push(datarows[i]);
                    }
                }
                $("#crm-saveJson-crmPlanPage").val(JSON.stringify(rowsList));
                $("#crm-queryForm-crmPlanPage").submit();
            }
        },
        </security:authorize>
        <security:authorize url="/crm/planPageEnablePage.do">
        {
            type:'button-open',//启用
            handler:function(){
                var id = $("#crm-backid-crmPlanPage").val();
                if(id == ''){
                    id = $("#crm-detailId-crmPlanPage").val();
                }
                $.messager.confirm("提醒","确定启用该计划单？",function(r){
                    if(r){
                        loading("启用中……");
                        $.ajax({
                            url:'crm/visitplan/enableCrm.do',
                            dataType:'json',
                            type:'post',
                            data:{pid: id },
                            success:function(json){
                                loaded();
                                if(json.flag == true){
                                    $.messager.alert("提醒","启用成功");
                                    $("#report-query-groupcols").val("");
                                    $("#crm-panel-crmPlanPage").panel('refresh', 'crm/visitplan/crmEditPage.do?id='+id);
                                }
                                else{
                                    $.messager.alert("提醒","启用失败");
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("错误","启用出错");
                            }
                        });
                    }

                });
            }
        },
        </security:authorize>
        <security:authorize url="/crm/planPageDisablePage.do">
        {
            type:'button-close',//禁用
            handler:function(){
                var id = $("#crm-backid-crmPlanPage").val();
                if(id == ''){
                    id = $("#crm-detailId-crmPlanPage").val();
                }
                $.messager.confirm("提醒","确定禁用该计划单？",function(r){
                    if(r){
                        loading("禁用中……");
                        $.ajax({
                            url:'crm/visitplan/disableCrm.do',
                            dataType:'json',
                            type:'post',
                            data:{pid: id },
                            success:function(json){
                                loaded();
                                if(json.flag == true){
                                    $.messager.alert("提醒","禁用成功");
                                    $("#report-query-groupcols").val("");
                                    $("#crm-panel-crmPlanPage").panel('refresh', 'crm/visitplan/crmEditPage.do?id='+id);
                                }
                                else{
                                    $.messager.alert("提醒","禁用失败,保存状态下不允许禁用");
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("错误","禁用出错");
                            }
                        });
                    }

                });
            }
        },
        </security:authorize>
        <security:authorize url="/crm/visitplan/crmDeletePlanPage.do">
        {
            type: 'button-delete',
            handler: function(){
                var id =$("#crm-backid-crmPlanPage").val();
                $.messager.confirm("提醒","确定删除该拜访计划？",function(r){
                    if(r){
                        loading("删除中..");
                        $.ajax({
                            url:'crm/visitplan/deletePlan.do',
                            dataType:'json',
                            type:'post',
                            data:{id:id},
                            success:function(json){
                                loaded();
                                if(json.delFlag == true){
                                    $.messager.alert("提醒","该信息已被其他信息引用，无法删除");
                                    return false;
                                }
                                if(json.flag == true){
                                    $.messager.alert("提醒","删除成功");
                                    var data = $("#crm-buttons-crmPlanPage").buttonWidget("removeData", '');
                                    if(data != null){
                                        $("#crm-panel-crmPlanPage").panel('refresh', 'crm/visitplan/crmEditPage.do?id='+data.id);
                                        $("#crm-backid-crmPlanPage").val(data.id);
                                        $("#crm-datagrid-crmPlanListPage").datagrid('loadData',[]);
                                    }else{
                                        parent.closeNowTab();
                                        $("#crm-datagrid-crmPlanListPage").datagrid('loadData',[]);
                                    }
                                }else{
                                    $.messager.alert("提醒","启用状态下不允许删除");
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("错误","删除出错");
                            }
                        });
                    }
                });
            }
        },
        </security:authorize>
        <security:authorize url="/crm/visitplan/crmPlanBack.do">
        {
            type: 'button-back',
            handler: function(data){
                refreshPanel('crm/visitplan/crmEditPage.do?id='+data.id);
            }
        },
        </security:authorize>
        <security:authorize url="/crm/visitplan/crmPlanNext.do">
        {
            type: 'button-next',
            handler: function(data){
                refreshPanel('crm/visitplan/crmEditPage.do?id='+data.id);
            }
        },
        </security:authorize>
        {}
        ],
        id:'${id}',
        model:'base',
        type:'view',
        taburl : '/crm/visitplan/crmPlanListPage.do',
        datagrid:'crm-datagrid-crmPlanListPage'
    });

    $("#crm-panel-crmPlanPage").panel({//跳转url所指向的页面
        href:plan_url,
        cache:false,
        maximized:true,
        border:false
    });

    function refreshPanel(url){ //更新panel
        $("#crm-panel-crmPlanPage").panel('refresh', url);
    }

    function removeWay(){
        var columns = $('#crm-datagrid-crmPlanPage').datagrid("options").columns;
    }

    //esc 快捷键
    $(document).keydown(function(event){
        switch(event.keyCode){
            case 27: //Esc
                $("#crm-dialog-crmPlanPage1").dialog('close');
                break;
        }
    });


    });
    function addVisitCustomer(title, url,urlparams){
        if(typeof (urlparams)=="undefined" && !isObject(urlparams)){
            urlparams={};
            return false ;
        }
        $('<div id="crm-dialog-crmPlanPage1"></div>').appendTo('#crm-dialog-crmPlanPage');
        $('#crm-dialog-crmPlanPage1').dialog({
            maximizable:true,
            resizable:true,
            title: title,
            width:650,
            height:450,
            closed: true,
            cache: false,
            href: url,
            modal: true,
            method:'post',
            queryParams:urlparams,
            onClose:function(){
                $('#crm-dialog-crmPlanPage1').dialog("destroy");
            }
        });
        $("#crm-dialog-crmPlanPage1").dialog("open");
    }
</script>

</body>
</html>
