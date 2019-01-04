<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
    <title>巡店客户拜访计划详细</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id = "crm-table-crmAddWayPage"></table>
    <div id="crm-query-crmAddWayPage" style="height: 70px;padding:1px">
        <form id="crm-form-crmAddWayPage" method="post">
            <table>
                <tr>
                    <td>客户:</td>
                    <td><input type="text" id="crm-id-crmAddWayPage" name="customerid" style="width:180px;" />
                    </td>
                    <td>区域:</td>
                    <td>
                        <input type="text" id="personnel-customer-sortarea" name="salesarea1"/>
                        <input type="hidden" name="personid" value="${personid}" />
                        <input type="hidden" name="groupcols"  id="personnel-customer-groupcols" />
                        <%--<input  id="personnel-customerid-crmAddWayPage" value="${cid}" />--%>
                    </td>
                </tr>
                <tr>
                    <td>分类:</td>
                    <td><input type="text" name="customersort" id="personnel-customer-customersort" name="customersort"/></td>
                    </td>
                    <td rowspan="3" colspan="2" class="tdbutton" style="text-align: right">
                        <a href="javaScript:void(0);" id="crm-querything-crmAddWayPage" class="button-qr">查询</a>
                        <%--<a href="javaScript:void(0);" id="crm-reset-crmAddWayPage" class="button-qr">重置</a>--%>
                        <a href="javaScript:void(0);" id="crm-save-crmAddWayPage" class="button-qr">确定</a>
                        <a href="javaScript:void(0);" id="crm-addto-crmAddWayPage" class="button-qr">追加</a>
                    </td>
                    <td>
                        <input type="hidden" id="crm-weekday-crmAddWayPage" value = '${weekday}'/>
                        <input type="hidden" name="personsort" value="${personsort}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

<script type="text/javascript">
    $("#crm-addto-crmAddWayPage").hide();
    var selecteRows = [];
    var cids = '${cid}'.split(",");
    var customerListColJson = $("#crm-table-crmAddWayPage").createGridColumnLoad({
        frozenCol:[[{field:'ck',checkbox:true}]],
        commonCol:[[{field:'id',title:'编码',sortable:true,width:60},
            {field:'name',title:'客户名称',sortable:true,width:180},
            {field:'address',title:'详细地址',sortable:true,width:200},
            {field:'salesareaname',title:'区域',width:80},
            {field:'customersortname',title:'分类',width:50}
        ]]
    });
    var groupcols = $("#report-query-groupcols").val();
    $("#personnel-customer-groupcols").val(groupcols);
    $(function(){
        $("#personnel-customer-sortarea").widget({ //区域
            referwid:'RT_T_BASE_SALES_AREA',
            col:'salesarea',
            singleSelect:false,
            width:180,
            onlyLeafCheck:false,
            view: true
        });

        $("#personnel-customer-customersort").widget({ //分类
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            col:'customersort',
            singleSelect:false,
            width:180,
            onlyLeafCheck:false,
            view: true
        });
        var initQueryJSON = $("#crm-form-crmAddWayPage").serializeJSON();
        var day = $("#crm-weekday-crmAddWayPage").val();
        $("#crm-querything-crmAddWayPage").click(function(){
            var queryJSON = $("#crm-form-crmAddWayPage").serializeJSON();
            $("#crm-table-crmAddWayPage").datagrid('load', queryJSON);
            $("#crm-addto-crmAddWayPage").show();
            $("#crm-save-crmAddWayPage").hide();
        });
            //重置
    //    $("#crm-reset-crmAddWayPage").click(function(){
    //        $("#crm-id-crmAddWayPage").widget('clear');
    //        $("#personnel-customer-sortarea").widget('clear');
    //        $("#personnel-customer-customersort").widget('clear');
    //        $("#crm-form-crmAddWayPage").form("reset");
    //        var queryJSON = $("#crm-form-crmAddWayPage").serializeJSON();
    //        $("#crm-table-crmAddWayPage").datagrid('load', queryJSON);
    //    });


        $("#crm-table-crmAddWayPage").datagrid({
            authority:customerListColJson,
            frozenColumns:customerListColJson.frozen,
            columns:customerListColJson.common,
            fit:true,
            border:true,
            idField:'id',
            singleSelect:false,
            checkOnSelect:true,
            url:'crm/visitplan/getCrmAddPageList.do',
            selectOnCheck:true,
            queryParams:initQueryJSON,
            pagination:true,
            rownumbers : true,
            pageSize:30,
            toolbar:'#crm-query-crmAddWayPage',
            onLoadSuccess:function(data){
                for(var i = 0 ; i<cids.length;i++){
                    var id = cids[i];
                   $("#crm-table-crmAddWayPage").datagrid('selectRecord',id);
                }
            },
            onSelect:function(index,data){
                selecteRows.push(data);
            },
            onBeforeLoad:function(){
                selecteRows =[];
            },
            onSelectAll:function(data){
                for(var i = 0 ; i < data.length ; ++ i){
                    selecteRows.push(data[i]);
                }
                $("#crm-addto-crmAddWayPage").show();
                $("#crm-save-crmAddWayPage").hide();
            },
            onUnselectAll:function(data){
                if(cids != ""){
                    for(var i = 0 ; i < cids.length ; ++ i){
                        if(day=="day1info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day1info:"",day1:""} });
                        }else if(day=="day2info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day2info:"",day2:""} });
                        }else if(day=="day3info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day3info:"",day3:""} });
                        }else if(day=="day4info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day4info:"",day4:""} });
                        }else if(day=="day5info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day5info:"",day5:""} });
                        }else if(day=="day6info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day6info:"",day6:""} });
                        }else if(day=="day7info"){
                            $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day7info:"",day7:""} });
                        }
                    }
                    cids = [];
                    $("#crm-addto-crmAddWayPage").show();
                    $("#crm-save-crmAddWayPage").hide();
                }

            },
            onUnselect:function(index,data){
                var idex = selecteRows.indexOf(data);
                selecteRows.splice(idex,1);
                for(var i = 0 ; i < cids.length ; ++ i){
                    if(cids[i] == data.id){
                        cids.splice(i,1);
                        break;
                    }
                }
            }
        });

        if('${sort}' == '1'){
            $("#crm-id-crmAddWayPage").widget({//品牌业务员客户
                referwid: "RL_T_BASE_SALES_CUSTOMER_PARENT_2",
                singleSelect: true,
                onlyLeafCheck: false,
                width: 180
            });
        }else if('${sort}' == '2'){
            $("#crm-id-crmAddWayPage").widget({//客户业务员客户
                referwid: "RL_T_BASE_SALES_CUSTOMER_PARENT",
                singleSelect: true,
                onlyLeafCheck: false,
                width: 180
            });

        }else{
            $("#crm-id-crmAddWayPage").widget({//厂家业务员客户
                referwid: "RL_T_SALE_MANUFACTCUSTOMER",
                singleSelect: true,
                onlyLeafCheck: false,
                width: 180
            });
        }


        $("#crm-save-crmAddWayPage").click(function(){
            var cnames = '${cname}'.split(",");
            if(selecteRows.length == 0){
                $.messager.alert("提醒", "请选择至少一条记录");
                return ;
            }
            if(selecteRows.length > 8 ){
                var a = Number(selecteRows.length) -8;
                for(var i = 0;i<a;i++){
                    $("#crm-datagrid-crmPlanPage").datagrid('appendRow',{});
                }
            }
            cascadingDelete();
            if(cids.length == 0){
                cascadingAdd(selecteRows);
            }else{
                //插入在编辑页已经存在的row
                for(var i = 0 ; i< cids.length ; ++ i){
                    var flag = true;
                    for(var j = 0 ;j < selecteRows.length ; ++ j){
                        if(selecteRows != "" && cids[i] == selecteRows[j].id){
                            flag = false;
                            break;
                        }
                    }

                    if(flag && cids[i] != ""){
                        var obj = {};
                        obj.id = cids[i];
                        obj.name = cnames[i];
                        selecteRows.push(obj);
                    }
                }
                cascadingAdd(selecteRows);
            }
        });

        $("#crm-addto-crmAddWayPage").click(function(){
            var rows = $("#crm-table-crmAddWayPage").datagrid('getChecked');
            var insertrows = [];
            for(var i=0;i<rows.length;i++){
                if(cids.length == 0){
                    insertrows.push(rows[i]);
                }else{
                    var hasflag = false;
                    for(var j=0;j<cids.length;j++){
                        if(rows[i].id==cids[j]){
                            hasflag = true;
                            break;
                        }
                    }
                    if(!hasflag){
                        insertrows.push(rows[i]);
                    }
                }
            }
            var position = 0 ;
            if(cids.length > 0){
                position = cids.length;
            }
            if(rows != ""){
                cascadingAddApend(insertrows,position);
            }else{
                $('#crm-dialog-crmPlanPage1').dialog("close");
            }
        });

        /**
        *添加选中的记录到查看界面
        */
        function cascadingAdd(rows){
            var form = $("#crm-form-crmAddWayPage").serializeJSON();
            for(var i = 0;i<rows.length;i++){
                form.way = "("+rows[i].id + ")"+" "+rows[i].name;
                form.cid = rows[i].id;
                if(day=="day1info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day1info:form.way,day1:form.cid} });
                }else if(day=="day2info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day2info:form.way,day2:form.cid} });
                }else if(day=="day3info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day3info:form.way,day3:form.cid} });
                }else if(day=="day4info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day4info:form.way,day4:form.cid} });
                }else if(day=="day5info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day5info:form.way,day5:form.cid} });
                }else if(day=="day6info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day6info:form.way,day6:form.cid} });
                }else if(day=="day7info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: i,row: {day7info:form.way,day7:form.cid} });
                }
            }
            $('#crm-dialog-crmPlanPage1').dialog("close");
        }

        /**
        *修改时添加此次操作选中的记录到查看界面
        * @param rows
        * @param len
         */
        function cascadingAddApend(rows,len){
            var addrows = $("#crm-table-crmAddWayPage").datagrid('getChecked');
            var rowlen = $("#crm-datagrid-crmPlanPage").datagrid('getRows');
            if((addrows.length + len) >= rowlen.length ){
                var a = Number((addrows.length + len)) - rowlen.length;
                for(var i = 0;i<a;i++){
                    $("#crm-datagrid-crmPlanPage").datagrid('appendRow',{});
                }
            }
            var rowlen = $("#crm-datagrid-crmPlanPage").datagrid('getRows');
            var form = $("#crm-form-crmAddWayPage").serializeJSON();
            for(var i = 0;i<rows.length;i++){
                form.way = "("+rows[i].id + ")"+" "+rows[i].name;
                form.cid = rows[i].id;
                var ind = len +i;
                if(day=="day1info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day1info:form.way,day1:form.cid} });
                }else if(day=="day2info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day2info:form.way,day2:form.cid} });
                }else if(day=="day3info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day3info:form.way,day3:form.cid} });
                }else if(day=="day4info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day4info:form.way,day4:form.cid} });
                }else if(day=="day5info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day5info:form.way,day5:form.cid} });
                }else if(day=="day6info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day6info:form.way,day6:form.cid} });
                }else if(day=="day7info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow', {index: ind,row: {day7info:form.way,day7:form.cid} });
                }
            }
            $('#crm-dialog-crmPlanPage1').dialog("close");
        }

        /**
         * 级联删除
         *删除取消选中的记录
         */
        function cascadingDelete(){
            var oldRows = $("#crm-datagrid-crmPlanPage").datagrid('getRows');
            for(var i = 0;i<oldRows.length;i++){
                if(day == "day1info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day1info': "", 'day1': "" }});
                }else if(day=="day2info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day2info': "", 'day2': "" }});
                }else if(day=="day3info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day3info': "", 'day3': "" }});
                }else if(day=="day4info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day4info': "", 'day4': "" }});
                }else if(day=="day5info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day5info': "", 'day5': "" }});
                }else if(day=="day6info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day6info': "", 'day6': "" }});
                }else if(day=="day7info"){
                    $("#crm-datagrid-crmPlanPage").datagrid('updateRow',{index: i, row: { 'day7info': "", 'day7': "" }});
                }
            }
        }


    });



</script>

</body>
</html>
