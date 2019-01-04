<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>巡店客户拜访计划单列表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="crm-queryDiv-crmPlanListPage" style="padding:0px;height:auto">
            <div class="buttonBG" id="crm-buttons-crmPlanListPage" style="height:26px;"></div>
            <form id="crm-queryForm-crmPlanListPage">
                <table class="querytable">
                    <tr>
                        <td>编号：</td>
                        <td><input type="text" name="id" style="width:180px" /></td>
                        <td>业务属性：</td>
                        <td><input id="crm-customer-crmPlanListPage" class="len180" name="employetype" /></td>
                        <td>状态：</td>
                        <td>
                            <select name="state" style="width:150px;">
                                <option ></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="0">禁用</option>
                                <option value="1">启用</option>
                                <option value="3">暂存</option>
                                <option value="4">新增</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>主管：</td>
                        <td><input type="text" id="crm-leader-crmPlanListPage" name="leadid" style="width:180px" /></td>
                        <td>业 务 员：</td>
                        <td><input id= "crm-employeid-crmPlanListPage" type="text" name="personid" style="width:180px" /></td>
                        <td rowspan="3" colspan="2" class="tdbutton">
                            <a href="javascript:;" id="crm-queay-crmPlanListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="crm-resetQueay-crmPlanListPage" class="button-qr">重置</a>
                            <%--<span id="crm-queryAdvanced-crmPlanListPage"></span>--%>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="crm-datagrid-crmPlanListPage" data-options="border:false"></table>
    </div>
</div>
<a href="javaScript:void(0);" id="crm-buttons-importclick" style="display: none"title="导入">导入</a>
<a href="javaScript:void(0);" id="crm-export-crmPlanListPage" style="display: none"title="导出">导出</a>
<script type="text/javascript">
    $(function(){
        var queryJSON = $("#crm-queryForm-crmPlanListPage").serializeJSON();
        $("#crm-employeid-crmPlanListPage").widget({//业务员
            referwid: "RL_T_BASE_PERSONNEL_BCS_SELLER",
            singleSelect: true,
            onlyLeafCheck: false,
            width: 180
        });

        $("#crm-leader-crmPlanListPage").widget({//主管
            referwid: "RL_BASEPERSONNEEL_LEADER",
            singleSelect: true,
            onlyLeafCheck: false,
            width: 180
        });

        controlQueryAndResetByKey("crm-queay-crmPlanListPage","crm-resetQueay-crmPlanListPage");
//        $("#crm-queryAdvanced-crmPlanListPage").advancedQuery({ //通用查询
//            //查询针对的表
//            name:'t_crm_visit_plan',
//            //查询针对的表格id
//            datagrid:'crm-datagrid-crmPlanListPage',
//            plain:true
//        });
        $("#crm-queay-crmPlanListPage").click(function(){
            var queryJSON = $("#crm-queryForm-crmPlanListPage").serializeJSON();
            $("#crm-datagrid-crmPlanListPage").datagrid('load', queryJSON);
        });
        $("#crm-resetQueay-crmPlanListPage").click(function(){
            $("#crm-queryForm-crmPlanListPage").form("reset");
            $("#crm-leader-crmPlanListPage").widget('clear');
            $("#crm-employeid-crmPlanListPage").widget('clear');
            var queryJSON = $("#crm-queryForm-crmPlanListPage").serializeJSON();
            $("#crm-datagrid-crmPlanListPage").datagrid('load', queryJSON);
        });
        //按钮控件
        $("#crm-buttons-crmPlanListPage").buttonWidget({
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
                <security:authorize url="/crm/visitplan/crmViewPlanPage.do">
                {
                    type: 'button-view',
                    handler: function(){
                        var con = $("#crm-datagrid-crmPlanListPage").datagrid('getSelected');
                        if(con == null){
                            $.messager.alert("提醒","请选择一条记录");
                            return false;
                        }
                        top.addTab('crm/visitplan/crmPlanPage.do?type=edit&id='+con.id+'&personid='+ con.personid,"客户拜访计划查看");
                    }
                },
                </security:authorize>

                <security:authorize url="/crm/visitplan/crmDeletePlanPage.do">
                {
                    type: 'button-delete',
                    handler: function(){
                        var con = $("#crm-datagrid-crmPlanListPage").datagrid('getChecked');
                        if(con.length == 0){
                            $.messager.alert("提醒","请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        if(con[0].state == "1"){
                            $.messager.alert("提醒","启用状态的记录不允许删除");
                            return false;
                        }
                        for(var i=0; i < con.length; i++){
                            ids += con[i].id + ',';
                        }
                        $.messager.confirm("提醒","确定删除该拜访计划？",function(r){
                            if(r){
                                loading("删除中..");
                                $.ajax({
                                    url:'crm/visitplan/deleteMultiPlan.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'ids='+ ids,
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒", "删除成功:"+json.success+"&nbsp删除失败："+json.failure);
                                            $("#crm-datagrid-crmPlanListPage").datagrid("reload");
                                        }else{
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    },
                                    error:function(){
                                        $.messager.alert("错误", "删除出错");
                                        loaded();
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/crm/planPageEnablePage.do">
                {
                    type:'button-open',//启用
                    handler:function(){
                        $.messager.confirm("提醒","确定启用该计划单？",function(r){
                            var con = $("#crm-datagrid-crmPlanListPage").datagrid('getChecked');
                            if(con == null){
                                $.messager.alert("提醒","请选择至少一条记录");
                                return false;
                            }
                            if(con[0].state == "1"){
                                $.messager.alert("提醒","选中记录为启用状态，不允许重复启用");
                                return false;
                            }
                            var ids = "";
                            for(var i=0; i < con.length; i++){
                                ids += con[i].id + ',';
                            }
                            if(r){
                                loading("启用中……");
                                $.ajax({
                                    url:'crm/visitplan/multiEnableCrm.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{ids: ids },
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒","启用成功</br>"+json.count+"条记录</br>启用失败编号："+json.errorids);
                                            $("#crm-datagrid-crmPlanListPage").datagrid("reload");
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
                        var con = $("#crm-datagrid-crmPlanListPage").datagrid('getChecked');
                        if(con == null){
                            $.messager.alert("提醒","请选择至少一条记录");
                            return false;
                        }
                        if(con[0].state == "0"){
                            $.messager.alert("提醒","选中记录为禁用状态，不允许重复禁用");
                            return false;
                        }
                        var ids = "";
                        for(var i=0; i < con.length; i++){
                            ids += con[i].id + ',';
                        }
                        $.messager.confirm("提醒","确定禁用该计划单？",function(r){
                            if(r){
                                loading("禁用中……");
                                $.ajax({
                                    url:'crm/visitplan/multiDisableCrm.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{ids: ids },
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒","禁用成功</br>"+json.count+"条记录</br>禁用失败编号："+json.errorids);
                                            $("#crm-datagrid-crmPlanListPage").datagrid("reload");
                                        }
                                        else{
                                            $.messager.alert("提醒","禁用失败");
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
//                {
//                    type: 'button-commonquery',
//                    attr:{
//                        //查询针对的表
//                        name:'t_crm_visit_plan',
//                        //查询针对的表格id
//                        datagrid:'crm-datagrid-crmPlanListPage',
//                        plain:true
//                    }
//                }
            ],
            buttons:[
                <security:authorize url="/crm/visitplan/importCrmDataBtn.do">
                {
                    id:'button-import-excel',
                    name:'导入',
                    iconCls:'button-import',
                    handler: function(){
                        var importparam="批量导入客户计划<br/>";
                        importparam=importparam+"<a href=\"basefiles/exceltemplet/CrmPlanSample.xls\">"+"点击下载导入模板样式</a><br/><br/>";
                        $("#crm-buttons-importclick").Excel('import',{
                            type:'importUserdefined',
                            version:'1',
                            importparam:importparam,
                            importPageRequestParam:{hideExportTip:true},
                            url:'crm/visitplan/importCrmData.do',
                            onClose: function(){ //导入成功后窗口关闭时操作，
                                $("#crm-datagrid-crmPlanListPage").datagrid('reload');
                                $("#crm-datagrid-crmPlanListPage").datagrid('clearSelections');
                            }
                        });
                        $("#crm-buttons-importclick").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/crm/visitplan/exportCrmDataBtn.do">
                {
                    id:'button-export-excel',
                    name:'导出',
                    iconCls:'button-export',
                    handler: function(){
                        $("#crm-export-crmPlanListPage").Excel('export',{
                            queryForm: "#crm-queryDiv-crmPlanListPage",
                            datagrid:"#crm-datagrid-crmPlanListPage",
                            type:'exportUserdefined',
                            name:'客户拜访计划列表',
                            url:'crm/visitplan/exportCrmPlanList.do'
                        });
                        $("#crm-export-crmPlanListPage").trigger("click");
                    }
                },
                </security:authorize>

            ],
            model:'base',
            type:'view',
            datagrid:'crm-datagrid-crmPlanListPage',
            tname:'t_crm_visit_plan'
        });


        //表头标题
        var crmPlanJson = $("#crm-datagrid-crmPlanListPage").createGridColumnLoad({
            name :'crm_visit_plan,t_base_personnel',
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[
                {field:'id',title:'编号',width:150,sortable:true},
                {field:'personid',title:'员工编码',width:80,hidden:true},
                {field:'name',title:'业务员',width:80,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.name;
                    }
                },
                {field:'leadid',title:'主管',width:80,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.leadname;
                    }},
               // {field:'leadname',title:'主管',width:80},
                {field:'state',title:'状态',width:50,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        if(val == 0){
                            return '禁用';
                        }else  if(val == 1){
                            return '启用';
                        }else  if(val == 2){
                            return '保存';
                        }else  if(val == 3){
                            return '暂存';
                        }else  if(val == 4){
                            return '新增';
                        }
                    }
                },
                {field:'brands',title:'品牌',width:250,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'wayname',title:'路线',width:150,sortable:true},
                {field:'employetype',title:'业务属性',width:70,
                    formatter:function(val,rowData,rowIndex){
                        var val = val+"";
                        if(val.indexOf(1) > -1){
                            return "客户业务员";
                        }else if(val.indexOf(3) > -1){
                            return "品牌业务员";
                        }else if(val.indexOf(7) > -1){
                            return "厂家业务员";
                        }
                    }
                },
                {field:'addusername',title:'建档人',width:70,resizable:true,sortable:true},
                {field:'remark',title:'备注',width:100,sortable:true}
            ]]
        });

        $("#crm-datagrid-crmPlanListPage").datagrid({
            authority:crmPlanJson,
            frozenColumns:crmPlanJson.frozen,
            columns:crmPlanJson.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            pageSize:20,
            pageList:[10,20,30,50],
            checkOnSelect:true,
            selectOnCheck:true,
            queryParams:queryJSON,
            url: 'crm/visitplan/getCrmPlanListPage.do',
            toolbar:'#crm-queryDiv-crmPlanListPage',
            onDblClickRow:function(index, data){
                if (top.$('#tt').tabs('exists','客户拜访计划查看')){
                    top.$('#tt').tabs('close','客户拜访计划查看');
                }
                top.addTab('crm/visitplan/crmPlanPage.do?type=edit&id='+data.id+'&personid='+ data.personid, '客户拜访计划修改');
            }
        }).datagrid("columnMoving");





    });


</script>


</body>
</html>
