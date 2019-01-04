<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
    <title>巡店客户拜访计划详细</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="crm-backid-crmPlanPage" value="${billid }" />
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="crm-queryDiv-crmPlanPage" style="padding:0px;height:auto">
            <form id="crm-queryForm-crmPlanPage" action="crm/visitplan/editCrmPlan.do" method="post">
                <table  border="0" cellpadding="5px" cellspacing="5px" style="border-collapse:collapse;">
                    <tr>
                        <td>计划单编号：</td>
                        <td><input type="text" id="crm-detailId-crmPlanPage" value="${visitPlan.id}"  name="visitPlan.id"  style="width:150px;height: 20px" readonly="readonly"/></td>

                        <td>状态：</td>
                        <td>
                            <select  class="easyui-combobox" id="crm-crmPlanPage-state" name="visitPlan.state" disabled style="width: 80px;height: 20px" >
                                <option value="1" <c:if test="${visitPlan.state =='1' }">selected="selected"</c:if>>启用</option>
                                <option value="2" <c:if test="${visitPlan.state =='2' }">selected="selected"</c:if>>保存</option>
                                <option value="3" <c:if test="${visitPlan.state =='3' }">selected="selected"</c:if>>暂存</option>
                                <option value="4" <c:if test="${visitPlan.state =='4' }">selected="selected"</c:if>>新增</option>
                                <option value="0" <c:if test="${visitPlan.state =='0' }">selected="selected"</c:if>>禁用</option>
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td>业务员：</td>
                        <td>
                            <input id="crm-crmPlanPage-employeid" value='${visitPlan.name}' style="width:150px;height: 20px;" readonly="true" />
                            <input type="hidden" id="crm-crmPlanPage-employeid-hidden" value="${visitPlan.personid}" name="visitPlan.personid">
                        </td>
                        <td colspan="2"> <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value=""/>显示全部客户</label></td>
                        <td>品牌：</td>
                        <td  colspan="3"><input id="brandids" value="${visitPlan.brands}" name="visitPlan.brands" style="height:20px"  /></td>
                    </tr>
                    <tr>
                        <td>路线名称：</td>
                        <td><input type="text" id="crm-wayname-crmPlanPage" value="${visitPlan.wayname}" name="visitPlan.wayname"  style="width:150px;height: 20px" /></td>
                        <td>备注：</td>
                        <td colspan="4"><input type="text" id="crm-remark-crmPlanPage" value="${visitPlan.remark}"  name="visitPlan.remark"  style="width:437px;height: 20px" /></td>
                    </tr>
                </table>
                <input type="hidden" id="crm-saveJson-crmPlanPage" name="saveJson" value="<c:out value="${planDetails }"/>" />
                <input id="sort" type="hidden" value = "${sort}" />
            </form>
        </div>
        <table id="crm-datagrid-crmPlanPage" data-options="border:false"></table>
        <div class="easyui-menu" id="crm-contextMenu-crmPlanPage" style="display: none;">
            <div id="crm-removeRow-crmPlanPage" data-options="iconCls:'icon-remove'">删除</div>
        </div>
    </div>
    <div id="crm-dialog-crmPlanPage"></div>
</div>
<script type="text/javascript">
    $(function(){

        $(".groupcols").click(function(){
            var groupcols = $("#report-query-groupcols").val();
            if(groupcols == "all"){
                $("#report-query-groupcols").val("");
            }else{
                $("#report-query-groupcols").val("all");
            }
        });

        $("#crm-buttons-crmPlanPage").buttonWidget("setDataID",
                {id:'${visitPlan.id}',state:'${visitPlan.state}',type:'view'});
        $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-save");

        $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-close");
        if("2" == "${visitPlan.state}" || "0" == "${visitPlan.state}"){
            $("#crm-buttons-crmPlanPage").buttonWidget("disableButton","button-close");
        }

        if('${sort}' == '1'){
            $("#brandids").widget({
                referwid:"RL_T_BASE_PERSONNEL_BRAND",
                param:[{field:'pid',op:'like',value:'${visitPlan.personid}'}],
                singleSelect:false,
                onlyLeafCheck:false,
                //required:true,
                width:300
            });
        }else if('${sort}' == '2'){
            $("#brandids").widget({
                referwid:"RL_T_BASE_GOODS_BRAND",
                singleSelect:false,
                onlyLeafCheck:false,
                //required:true,
                width:300
            });
        }else if('${sort}' == '3'){
            $("#brandids").widget({
                referwid:"RL_T_BASE_FACTORY_PERSONNEL_BRAND",
                param:[{field:'pid',op:'like',value:'${visitPlan.personid}'}],
                singleSelect:false,
                onlyLeafCheck:false,
                //required:true,
                width:300
            });
        }

        $("#crm-datagrid-crmPlanPage").datagrid({
            authority:crmPlanDetailJson,
            frozenColumns:crmPlanDetailJson.frozen,
            columns:crmPlanDetailJson.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            idField:'id',
            singleSelect:true,
            data: JSON.parse('${planDetails}'),
            toolbar:'#crm-queryDiv-crmPlanPage',
            onDblClickCell:function(rowIndex,field,value) {

                var state = $("#crm-crmPlanPage-employeid").val();
                if(state == 0){
                    return false ;
                }
                var rows = $("#crm-datagrid-crmPlanPage").datagrid('getRows');
                var cid = "",cname="";
                for (var i = 0; i < rows.length; i++) {
                    if (field.indexOf("1") != -1 &&  rows[i].day1 !="" &&  rows[i].day1 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day1;
                            cname = rows[i].day1info;
                        }else{
                            cid = cid + "," + rows[i].day1;
                            cname = cname + ","+ rows[i].day1info;
                        }
                    }
                    if (field.indexOf("2") != -1 &&  rows[i].day2 !="" &&  rows[i].day2 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day2;
                            cname = rows[i].day2info;
                        }else{
                            cid = cid + "," + rows[i].day2;
                            cname = cname + ","+ rows[i].day2info;
                        }
                    }
                    if (field.indexOf("3") != -1 &&  rows[i].day3 !="" &&  rows[i].day3 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day3;
                            cname = rows[i].day3info;
                        }else{
                            cid = cid + "," + rows[i].day3;
                            cname = cname + ","+ rows[i].day3info;
                        }
                    }
                    if (field.indexOf("4") != -1 &&  rows[i].day4 !="" &&  rows[i].day4 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day4;
                            cname = rows[i].day4info;
                        }else{
                            cid = cid + "," + rows[i].day4;
                            cname = cname + ","+ rows[i].day4info;
                        }
                    }
                    if (field.indexOf("5") != -1 &&  rows[i].day5 !="" &&  rows[i].day5 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day5;
                            cname = rows[i].day5info;
                        }else{
                            cid = cid + "," + rows[i].day5;
                            cname = cname + ","+ rows[i].day5info;
                        }
                    }
                    if (field.indexOf("6") != -1 &&  rows[i].day6 !="" &&  rows[i].day6 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day6;
                            cname = rows[i].day6info;
                        }else{
                            cid = cid + "," + rows[i].day6;
                            cname = cname + ","+ rows[i].day6info;
                        }
                    }
                    if (field.indexOf("7") != -1 &&  rows[i].day7 !="" &&  rows[i].day7 !=undefined ) {
                        if(cid == ""){
                            cid = rows[i].day7;
                            cname = rows[i].day7info;
                        }else{
                            cid = cid + "," + rows[i].day7;
                            cname = cname + ","+ rows[i].day7info;
                        }
                    }
                }
                var employid = $("#crm-crmPlanPage-employeid-hidden").val();
                var sort = $("#sort").val();
                if(field == "day1info"){
                    var title = "周一拜访路线(按ESC退出)" ;
                }else if(field =="day2info"){
                    var title = "周二拜访路线(按ESC退出)";
                }else if(field =="day3info"){
                    var title = "周三拜访路线(按ESC退出)" ;
                }else if(field =="day4info"){
                    var title = "周四拜访路线(按ESC退出)" ;
                }else if(field =="day5info"){
                    var title = "周五拜访路线(按ESC退出)" ;
                }else if(field =="day6info"){
                    var title = "周六拜访路线(按ESC退出)" ;
                }else if(field =="day7info"){
                    var title = "周日拜访路线(按ESC退出)" ;
                }
                if(employid == ''){
                    $("#crm-crmPlanPage-employeid").focus();
                }
                var url = 'crm/visitplan/crmAddWayPage.do';
                var queryParams={
                    personid:'${visitPlan.personid}',
                    cid:cid,
                    field:field,
                    personsort:sort
                };
                addVisitCustomer(title, url,queryParams);
            },
            onLoadSuccess:function(){
                for (var i = 0; i < 8; i++) {
                    $("#crm-datagrid-crmPlanPage").datagrid('appendRow',{});
                }
            }

        }).datagrid("columnMoving");

        //提交表单
        $("#crm-queryForm-crmPlanPage").form({
            onSubmit:function(){
                var flag =$(this).form('validate');
                if(flag == false) return false;
                loading("提交中……");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag != false){
                    $.messager.alert("提醒","保存成功");
                    $("#crm-wayname-crmPlanPage").val(json.wayname);
                    $("#crm-detailId-crmPlanPage").val(json.id);
                    $("#crm-state-crmPlanPage").val(json.state);

                }else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });

    });

</script>
</body>
</html>

