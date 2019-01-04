<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>巡店客户拜访计划单</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center'">
            <div id="crm-queryDiv-crmPlanPage" style="padding:0px;height:auto">
        <form id="crm-queryForm-crmPlanPage" action="crm/visitplan/addCrmPlan.do" method="post">
            <table  border="0" cellpadding="5px" cellspacing="5px" style="border-collapse:collapse;">
                <tr>
                    <td>计划单编号：</td>
                    <td><input type="text" id="crm-detailId-crmPlanPage" name="visitPlan.id" style="width:150px;height: 20px"
                               <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> ></td>
                    <td>状态：</td>
                    <td>
                        <select type="text" name="visitPlan.state"   style="width:80px;height: 20px" id="crm-state-crmPlanPage" disabled="disabled">
                            <option value="4" selected="selected">新增</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>业务员：</td>
                    <td><input id="crm-crmPlanPage-employeid" name="visitPlan.personid" style="width:150px;height: 20px" />
                        <input id="sort" type="hidden"/>
                    </td>
                    <td colspan="2"> <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value=""/>显示全部客户</label></td>
                    <td>品牌：</td>
                    <td  colspan="3"><input id="brandids" name="visitPlan.brands" style="width:300px;height:20px" /></td>
                </tr>
                <tr>
                    <td>路线名称：</td>
                    <td><input type="text" id="crm-wayname-crmPlanPage" value="${visitPlan.wayname}" name="visitPlan.wayname"  style="width:150px;height: 20px" /></td>
                    <td>备注：</td>
                    <td colspan="4"><input type="text" id="crm-remark-crmPlanPage" value="${visitPlan.remark}"  name="visitPlan.remark"  style="width:437px;height: 20px" /></td>

                </tr>
            </table>
            <input type="hidden" id="crm-saveJson-crmPlanPage" name="saveJson" />
        </form>
     </div>
    <table id="crm-datagrid-crmPlanPage" data-options="border:false"></table>
  </div>
<div id="crm-dialog-crmPlanPage"></div>
</div>
 <script type="text/javascript">
     $("#crm-buttons-crmPlanPage").buttonWidget("initButtonType", 'add');
     $(function(){

         $(".groupcols").click(function(){
             var groupcols = $("#report-query-groupcols").val();
             if(groupcols == "all"){
                 $("#report-query-groupcols").val("");
             }else{
                 $("#report-query-groupcols").val("all");
             }
         });

         $("#crm-crmPlanPage-employeid").widget({
             referwid:"RL_T_BASE_PERSONNEL_BCS_SELLER",
             singleSelect:true,
             onlyLeafCheck:false,
             required:true,
             width:150,
             onSelect:function(data){
                 $("#sort").val(data.sort);
                 $.ajax({
                     url:'crm/visitplan/validateEmploy.do',
                     dataType:'json',
                     type:'post',
                     data:{id: data.id },
                     success:function(json){
                         if(json.flag == true){
                             $.messager.confirm("提醒","该业务员存在计划单，是否跳转到计划单页面？",function(r){
                                 if(r){
                                     $("#crm-backid-crmPlanPage").val(json.id);
                                     $("#crm-panel-crmPlanPage").panel('refresh', 'crm/visitplan/crmEditPage.do?id='+json.id);
                                 }else{
                                     $("#crm-crmPlanPage-employeid").widget("clear");
                                 }
                             });

                         }
                     }
                 });
                 if(data.sort == '1'){//品牌业务员
                     $("#brandids").widget({
                         referwid:"RL_T_BASE_PERSONNEL_BRAND",
                         param:[{field:'pid',op:'like',value:data.id}],
                         singleSelect:false,
                         onlyLeafCheck:false,
                         //required:true,
                         width:300
                     });
                 }else if(data.sort == '3'){// 厂家业务员
                     $("#brandids").widget({
                         referwid:"RL_T_BASE_FACTORY_PERSONNEL_BRAND",
                         param:[{field:'pid',op:'like',value:data.id}],
                         singleSelect:false,
                         onlyLeafCheck:false,
                         //required:true,
                         width:300
                     });
                 }else{//客户业务员
                     $("#brandids").widget({
                         referwid:"RL_T_BASE_GOODS_BRAND",
                         singleSelect:false,
                         onlyLeafCheck:false,
                         //required:true,
                         width:300
                     });
                 }
             }
         });


         $("#crm-datagrid-crmPlanPage").datagrid({
             authority:crmPlanDetailJson,
             frozenColumns:crmPlanDetailJson.frozen,
             columns:crmPlanDetailJson.common,
             fit:true,
             title:"",
             method:'post',
             rownumbers:true,
//             pagination:true,
             idField:'id',
             singleSelect:true,
             toolbar:'#crm-queryDiv-crmPlanPage',
             onDblClickCell:function(rowIndex,field,value){
                 var rows = $("#crm-datagrid-crmPlanPage").datagrid('getRows');
                 var cid = "";
                 for (var i = 0; i < rows.length  ; i++) {
                     if (field.indexOf("1") != -1 &&  rows[i].day1 !="" &&  rows[i].day1 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day1;
                         }else{
                             cid = cid + "," + rows[i].day1;
                         }
                     }
                     if (field.indexOf("2") != -1 &&  rows[i].day2 !="" &&  rows[i].day2 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day2;
                         }else{
                             cid = cid + "," + rows[i].day2;
                         }
                     }
                     if (field.indexOf("3") != -1 &&  rows[i].day3 !="" &&  rows[i].day3 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day3;
                         }else{
                             cid = cid + "," + rows[i].day3;
                         }
                     }
                     if (field.indexOf("4") != -1 &&  rows[i].day4 !="" &&  rows[i].day4 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day4;
                         }else{
                             cid = cid + "," + rows[i].day4;
                         }
                     }
                     if (field.indexOf("5") != -1 &&  rows[i].day5 !="" &&  rows[i].day5 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day5;
                         }else{
                             cid = cid + "," + rows[i].day5;
                         }
                     }
                     if (field.indexOf("6") != -1 &&  rows[i].day6 !="" &&  rows[i].day6 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day6;
                         }else{
                             cid = cid + "," + rows[i].day6;
                         }
                     }
                     if (field.indexOf("7") != -1 &&  rows[i].day7 !="" &&  rows[i].day7 !=undefined ) {
                         if(cid == ""){
                             cid = rows[i].day7;
                         }else{
                             cid = cid + "," + rows[i].day7;
                         }
                     }
                 }
                 var sort = $("#sort").val();
                 var personid = $("#crm-crmPlanPage-employeid").widget("getValue");
                 if(field == "day1info"){ var title = "周一拜访路线" ; }
                 else if(field =="day2info"){var title = "周二拜访路线"; }
                 else if(field =="day3info"){var title = "周三拜访路线";}
                 else if(field =="day4info"){var title = "周四拜访路线";}
                 else if(field =="day5info"){var title = "周五拜访路线";}
                 else if(field =="day6info"){var title = "周六拜访路线";}
                 else if(field =="day7info"){var title = "周日拜访路线";}
                 if(personid === ''){
                     $("#crm-crmPlanPage-employeid").focus();
                     return;
                 }
                 var url = 'crm/visitplan/crmAddWayPage.do';
                 var queryParams={
                     personid:personid,
                     cid:cid,
                     field:field,
                     personsort:sort
                 };
                 addVisitCustomer(title, url,queryParams);
             }

         }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{}]}).datagrid("columnMoving");


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
                 if(json.flag == true){
                     $.messager.alert("提醒","保存成功");
                     $("#crm-wayname-crmPlanPage").val(json.wayname);
                     $("#crm-detailId-crmPlanPage").val(json.id);
                     $("#crm-backid-crmPlanPage").val(json.id);
                     $("#crm-panel-crmPlanPage").panel('refresh', 'crm/visitplan/crmEditPage.do?id='+ json.id);
                     $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-add");
                     $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-delete");
                     $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-open");
                     $("#crm-buttons-crmPlanPage").buttonWidget("enableButton","button-close");
                 }else{
                     $.messager.alert("提醒","保存失败");
                 }
             }
         });


     });



</script>

</body>
</html>
