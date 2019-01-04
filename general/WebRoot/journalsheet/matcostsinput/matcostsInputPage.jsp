<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫录入页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<%
    boolean isEdit = false;
%>
<security:authorize url="/journalsheet/matcostsInput/matcostsInputEditBtn.do">
    <% isEdit = true; %>
</security:authorize>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div id="journalsheet-button-matcostsInput" class="buttonBG"></div>
    </div>
    <div data-options="region:'center'">
        <div id="journalsheet-matcostsInput-layout-datalist" class="easyui-layout" data-options="fit:true,border:false">
            <div data-options="region:'north',border:false" style="height:140px;">
                <div id="journalsheet-tablequery-matcostsInput" class="easyui-accordion"
                     data-options="fit:true,border:false">
                    <div data-options="iconCls:'icon-search',selected:true,onExpand:queryPanelExpandFunc,onCollapse:queryPanelCollapseFunc">
                        <form action="" id="matcostsInput-form-ListQuery" method="post">
                            <table class="querytable">
                                <tr>
                                    <td class="left">供应商:</td>
                                    <td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/>
                                    </td>
                                    <td>所属部门:</td>
                                    <td><input id="journalsheet-widget-supplierdeptidquery" name="supplierdeptid"
                                               type="text" style="width: 150px;"/></td>
                                    <td style="width:60px;">科目名称:</td>
                                    <td><input id="journalsheet-widget-subjectidquery" name="subjectid" type="text"
                                               style="width: 150px;"/></td>
                                </tr>
                                <tr>
                                    <td class="left">业务日期:</td>
                                    <td><input id="journalsheet-date-matcostsInputPage-begintime" name="begintime"
                                               value="${firstday }" class="Wdate" style="width:100px;"
                                               onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                                        到<input id="journalsheet-date-matcostsInputPage-endtime" name="endtime"
                                                value="${today }" class="Wdate" style="width:100px;"
                                                onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                                    </td>
                                    <td class="left">红冲关联代垫:</td>
                                    <td>
                                        <input type="text" name="hcreferid" style="width:150px"/>
                                    </td>
                                    <td>是否红冲:</td>
                                    <td>
                                        <select name="ishcflag" style="width:150px;">
                                            <option value="">全部</option>
                                            <option value="1">是</option>
                                            <option value="2">否</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="left">核销日期:</td>
                                    <td><input id="journalsheet-text-writeoffbegintime" name="writeoffbegintime"
                                               value="" class="Wdate" style="width:100px;"
                                               onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                                        到<input id="journalsheet-text-writeoffendtime" name="writeoffendtime" value=""
                                                class="Wdate" style="width:100px;"
                                                onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                                    </td>
                                    <td>核销状态:</td>
                                    <td>
                                        <select name="writeoffstatus" style="width:150px;">
                                            <option value="">全部</option>
                                            <option value="1">核销</option>
                                            <option value="2">未核销</option>
                                        </select>
                                    </td>
                                    <td>OA编号:</td>
                                    <td><input id="journalsheetPage-text-oaid" name="oaidarr" type="text"
                                               style="width: 150px"/></td>
                                </tr>
                                <tr>
                                    <td>客户名称:</td>
                                    <td><input id="journalsheet-widget-customerquery" name="customerid" type="text"
                                               style="width: 220px;"/></td>
                                    <td class="left">客户分类:</td>
                                    <td><input id="journalsheet-widget-customersortquery" name="customersort"
                                               type="text" style="width: 150px;"/></td>

                                    <td class="left">经办人:</td>
                                    <td class="left"><input id="journalsheet-widget-transactoridquery" type="text"
                                                            style="width: 150px;" name="transactorid"/></td>
                                </tr>
                                <tr>
                                    <td colspan="3"></td>
                                    <td colspan="3" style="text-align: center">
                                        <a href="javaScript:void(0);" id="matcostsInput-query-List"
                                           class="button-qr">查询</a>
                                        <a href="javaScript:void(0);" id="matcostsInput-query-reloadList"
                                           class="button-qr">重置</a>
                                        <a href="javaScript:void(0);" id="matcostsInput-query-collapsepanel"
                                           class="button-qr">&uarr;折叠&uarr;</a>
                                        <span id="matcostsInput-query-advanced"></span>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div data-options="region:'center'">
                <table id="journalsheet-table-matcostsInput"></table>
            </div>
        </div>
    </div>
    <a href="javaScript:void(0);" id="matcostsInput-buttons-exportclick" style="display: none" title="导出">导出</a>
    <a href="javaScript:void(0);" id="matcostsInput-buttons-importclick" style="display: none" title="导入">导入</a>
</div>
<div style="display:none">
    <div id="matcostsInput-dialog-operate"></div>
    <div id="matcostsInputSettle-dialog-operate"></div>
    <div id="matcostsInputRewriteoff-dialog-operate"></div>
    <div id="matcostsReimburseWriteoffQuery-dialog-operate"></div>
    <div id="journalsheet-account-dialog"></div>
</div>
<style>
    a.matcinview {
        color: #00f;
        text-decoration: underline;
        cursor: pointer;
    }
</style>
<script type="text/javascript">
    var footerobject = null;
    var matcostsPageInitOkFlag = false;
    var matcostsInput_AjaxConn = function (Data, Action, Str) {
        if (null != Str && "" != Str) {
            loading(Str);
        }
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        });
        return MyAjax.responseText;
    }
    var queryPanelCollapseFunc = function () {
        if (matcostsPageInitOkFlag) {
            var $accHeadObj = $("#journalsheet-tablequery-matcostsInput").find("div[class^='panel-header accordion-header']");
            var defheight = 26;
            if ($accHeadObj.size() > 0) {
                defheight = $accHeadObj.first().parent().height();
            }
            if (defheight == null) {
                defheight = 26;
            }
            $("#journalsheet-matcostsInput-layout-datalist").layout("panel", "north").panel("resize", {height: defheight});
            $("#journalsheet-matcostsInput-layout-datalist").layout("resize", {height: '100%'});
        }
    }

    var queryPanelExpandFunc = function () {
        var $accHeadObj = $("#journalsheet-tablequery-matcostsInput").find("div[class^='panel-header accordion-header']");
        if ($accHeadObj.size() && $accHeadObj.hasClass("accordion-header-selected")) {
            $accHeadObj.removeClass("accordion-header-selected")
        }
        if (matcostsPageInitOkFlag) {
            $("#journalsheet-matcostsInput-layout-datalist").layout("panel", "north").panel("resize", {height: 185});
            $("#journalsheet-matcostsInput-layout-datalist").layout("resize", {height: '100%'});

        } else {
            /*
             if($accHeadObj.size()>0){
             var colorv=$accHeadObj.css("background-color")||"";
             if(colorv!="") {
             $accHeadObj.css({"background-color":colorv});
             }
             }
             */
        }
    }
    var pageLoadSuccessFunc = function () {
        setTimeout(function () {
            matcostsPageInitOkFlag = true;
        }, 500);
    }
    pageLoadSuccessFunc();
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var matcostsInputListColJson = $("#journalsheet-table-matcostsInput").createGridColumnLoad({
        name: 't_js_matcostsinput',
        frozenCol: [[
            {field: 'idok', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'id', title: '编码', width: 125, sortable: true},
            {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
            {field: 'paydate', title: '支付日期', width: 80, sortable: true},
            {field: 'takebackdate', title: '收回日期', width: 80, sortable: true},
            {
                field: 'oaid', title: 'OA编号', width: 80, sortable: true,
                formatter: function (value, row, index) {

                    if (value != undefined
                        && value != null
                        && value != ''
                        && value != '合计'
                        && value != '选中金额') {

                        return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
                    }

                    return value;
                }
            },
            {
                field: 'brandid', title: '商品品牌', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.brandname;
                }
            },
            {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
            {field: 'suppliername', title: '供应商名称', width: 210, sortable: true, isShow: true},
            {
                field: 'supplierdeptid', title: '所属部门', width: 70, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.supplierdeptname;
                }
            },
            {
                field: 'customerid', title: '客户名称', width: 130, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.customername;
                }
            },
            {
                field: 'customersort', title: '客户分类名称', width: 80, sortable: true, aliascol: 'customerid',
                formatter: function (val, rowData, rowIndex) {
                    return rowData.customersortname;
                }
            },
            {
                field: 'transactorid', title: '经办人名称', width: 130, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.transactorname;
                }
            },
            {
                field: 'subjectid', title: '科目名称', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.subjectname;
                }
            },
            {
                field: 'hcflag', title: '是否红冲', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val == '0') {
                        return "否";
                    } else if (val == '1' || val == '2') {
                        return "是";
                    }
                }
            },
            {
                field: 'factoryamount', title: '工厂投入', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterMoney(val);
                    }
                    else {
                        return "0.00";
                    }
                }
            }
            <%--
            通用版没有
            ,
            {field:'htcompdiscount',title:'电脑折让',resizable:true,sortable:true,align:'right',
                formatter:function(val,rowData,rowIndex){
                    if(val != "" && val != null){
                        return formatterMoney(val);
                    }
                    else{
                        return "0.00";
                    }
                }
            },
            {field:'htpayamount',title:'支付金额',resizable:true,sortable:true,align:'right',
                formatter:function(val,rowData,rowIndex){
                    if(val != "" && val != null){
                        return formatterMoney(val);
                    }
                    else{
                        return "0.00";
                    }
                }
            },
            {field:'branchaccount',title:'转入分公司',resizable:true,sortable:true,align:'right',
                formatter:function(val,rowData,rowIndex){
                    if(val != "" && val != null){
                        return formatterMoney(val);
                    }
                    else{
                        return "0.00";
                    }
                }
            }
            -%>
            <%--,
            {field:'reimbursetype',title:'收回方式',width:60,sortable:true,hidden:true,
                formatter:function(val,rowData,rowIndex){
                    return rowData.reimbursetypename;
                }
            }
            --%>
            <%--
            <c:forEach items="${reimbursetypeList }" var="list">
              ,{field:'reimburse_${list.code}',title:'${list.codename}',align:'right',resizable:true,isShow:true,
                  formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
              }
              </c:forEach>
             --%>
            ,
            {
                field: 'expense', title: '费用金额', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    return formatterMoney(val);
                }
            },
            {
                field: 'reimburseamount', title: '收回金额', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (rowData.writeoffamount != null && rowData.writeoffamount != "") {
                        return formatterMoney(rowData.writeoffamount);
                    }
                    else {
                        return "0.00";
                    }
                }
            },
            {
                field: 'actingmatamount', title: '代垫金额', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    return formatterMoney(val);
                }
            },
            {
                field: 'iswriteoff', title: '核销状态', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val == "1") {
                        return "核销";
                    }
                }
            },
            {
                field: 'writeoffdate', title: '核销日期', resizable: true, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (rowData.iswriteoff == '1' && val) {
                        if (val.length >= 10) {
                            return val.substring(0, 10);
                        } else {
                            return val;
                        }
                    }
                }
            },
            {field: 'duefromdate', title: '应收日期', resizable: true, sortable: true},
            {
                field: 'writeoffer', title: '核销人员', resizable: true, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (rowData.iswriteoff == '1' && rowData.writeoffername) {
                        return rowData.writeoffername;
                    }
                }
            },
            {field: 'remark', title: '备注', width: 80, sortable: true},
            {
                field: 'hcreferid', title: '红冲关联代垫', width: 125, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val != null && val != "") {
                        return "<a class=\"matcinview\" herf=\"javascript:void(0);\" onclick=\"javascript:showMatcostsInputView('" + val + "')\">" + val + "</a>";
                    }
                }
            },
            {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
            {field: 'adduserid', title: '制单人编码', width: 80, sortable: true, hidden: true},
            {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
            {
                field: 'addtime', title: '制单时间', width: 130, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val) {
                        return val.replace(/[tT]/, " ");
                    }
                }
            },
            {
                field: 'sourcefrome', title: '数据来源', resizable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val == "0") {
                        return "手动添加";
                    } else if (val == "1") {
                        return "导入";
                    } else if (val == "2" || val == '3') {
                        return "系统生成";
                    }
                }
            }
        ]]
    });

    //判断是否加锁
    function isLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }
    //加锁
    function isDoLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isDoLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    function refreshLayout(title, url, onLoadFunc) {

        $('<div id="matcostsInput-dialog-operate-content"></div>').appendTo('#matcostsInput-dialog-operate');
        $('#matcostsInput-dialog-operate-content').dialog({
            title: title,
            width: 540,
            height: 390,
            closed: true,
            cache: false,
            href: url,
            maximizable: true,
            resizable: true,
            modal: true,
            onLoad: function () {
                if (onLoadFunc != null && typeof(onLoadFunc) == "function") {
                    onLoadFunc();
                }
            },
            onClose: function () {
                $('#matcostsInput-dialog-operate-content').dialog("destroy");
            }
        });
        $('#matcostsInput-dialog-operate-content').dialog('open');
    }

    function closeMatcostsInputDialog() {
        var $detailOperDialog = $("#matcostsInput-dialog-operate-content");
        if ($detailOperDialog.size() > 0) {
            try {
                $detailOperDialog.dialog("close");
            } catch (e) {

            }
        }
    }

    //通用查询组建调用
    //		$("#matcostsInput-query-advanced").advancedQuery({
    //			//查询针对的表
    //	 		name:'t_js_matcostsinput',
    //	 		//查询针对的表格id
    //	 		datagrid:'journalsheet-table-matcostsInput'
    //		});

    $(function () {
        //供应商查询
        $("#journalsheet-widget-supplierquery").widget({
            width: 220,
            name: 't_js_matcostsinput',
            col: 'supplierid',
            singleSelect: true,
            view: true
        });

        //科目查询
        $("#journalsheet-widget-subjectidquery").widget({
            width: 150,
            name: 't_js_matcostsinput',
            col: 'subjectid',
            singleSelect: true,
            initSelectNull: true
        });

        //客户查询
        $("#journalsheet-widget-customerquery").customerWidget({
            width: 220,
            name: 't_js_matcostsinput',
            col: 'customerid',
            isall: true,
            singleSelect: true
        });

        $("#journalsheet-widget-supplierdeptidquery").widget({
            width: 150,
            name: 't_js_matcostsinput',
            col: 'supplierdeptid',
            singleSelect: true
        });
        $("#journalsheet-widget-customersortquery").widget({ //分类
            referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
            width: 150,
            singleSelect: true,
            onlyLeafCheck: false,
            view: true
        });
        //经办人
        $("#journalsheet-widget-transactoridquery").widget({
            width: 150,
            name: 't_js_matcostsinput',
            col: 'transactorid',
            singleSelect: true
        });

        //回车事件
        controlQueryAndResetByKey("matcostsInput-query-List", "matcostsInput-query-reloadList");

        //查询
        $("#matcostsInput-query-List").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#matcostsInput-form-ListQuery").serializeJSON();
            //调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
            $("#journalsheet-table-matcostsInput").datagrid({
                url: 'journalsheet/matcostsInput/getMatcostsInputPageList.do',
                pageNumber: 1,
                queryParams: queryJSON
            }).datagrid("columnMoving");
        });

        //重置按钮
        $("#matcostsInput-query-reloadList").click(function () {
            $("#matcostsInput-form-ListQuery")[0].reset();
            $("#journalsheet-table-matcostsInput").datagrid('loadData', {total: 0, rows: []});
            $("#journalsheet-widget-supplierquery").widget('clear');
            $("#journalsheet-widget-subjectidquery").widget('clear');
            $("#journalsheet-widget-customerquery").customerWidget('clear');
            $("#journalsheet-widget-customersortquery").widget('clear');
            $("#journalsheet-widget-transactoridquery").widget('clear');
        });
        $("#matcostsInput-query-collapsepanel").click(function () {
            $("#journalsheet-tablequery-matcostsInput").accordion("unselect", 0);
        });

        $("#journalsheet-button-matcostsInput").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 't_js_matcostsinput',
                        //查询针对的表格id
                        datagrid: 'journalsheet-table-matcostsInput'
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputAddBtn.do">
                {
                    id: 'button-id-add',
                    name: '新增 ',
                    iconCls: 'button-add',
                    handler: function () {
                        var onLoadFunc = function () {
                            $("#journalsheet-widget-transactorid").focus();
                            $("#journalsheet-widget-transactorid").select();
                        };
                        refreshLayout('代垫录入【新增】', 'journalsheet/matcostsInput/showMatcostsInputAddPage.do', onLoadFunc);
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputEditBtn.do">
                {
                    id: 'button-id-edit',
                    name: '修改 ',
                    iconCls: 'button-edit',
                    handler: function () {
                        var matcostsInput = $("#journalsheet-table-matcostsInput").datagrid('getSelected');
                        if (matcostsInput == null) {
                            $.messager.alert("提醒", "请选择相应的代垫录入!");
                            return false;
                        }
                        /*
                         var flag = isDoLockData(matcostsInput.id,"t_js_matcostsinput");
                         if(!flag){
                         $.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
                         return false;
                         }
                         */

                        if (matcostsInput.iswriteoff == '1' || matcostsInput.iswriteoff == '2') {
                            $.messager.alert("提醒", "抱歉，核销后的代垫不能被修改");
                            return false;
                        }

                        if (matcostsInput.hcflag == '1' || matcostsInput.hcflag == '2') {
                            $.messager.alert("提醒", "抱歉，红冲不能修改");
                            return false;
                        }

                        if (!(matcostsInput.sourcefrome == '0' || matcostsInput.sourcefrome == '1')) {
                            $.messager.alert("提醒", "只有手动添加或者导入的代垫才可以修改");
                            return false;
                        }
                        refreshLayout("代垫录入【修改】", 'journalsheet/matcostsInput/showMatcostsInputEditPage.do?id=' + matcostsInput.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputAddHChongBtn.do">
                {
                    id: 'button-id-addhc',
                    name: '红冲 ',
                    iconCls: 'button-add',
                    handler: function () {
                        var url = 'journalsheet/matcostsInput/showMatcostsInputHCAddPage.do';
                        var dataRow = $("#journalsheet-table-matcostsInput").datagrid('getSelected');
                        if (dataRow == null || dataRow.id == null || $.trim(dataRow.id) == "") {
                            $.messager.alert("提醒", "请选择相应的代垫录入!");
                            return false;
                        }
                        if (dataRow.iswriteoff == '1' || dataRow.iswriteoff == '2') {
                            $.messager.alert("提醒", "抱歉，已经核销的代垫不能被红冲。<br/>请选择相应的代垫。");
                            return false;
                        }
                        if (dataRow.hcflag != null && ($.trim(dataRow.hcflag) == "1" || $.trim(dataRow.hcflag) == "2")) {
                            $.messager.alert("提醒", "抱歉，你选择的" + dataRow.id + " 已经为红冲。<br/>请选择相应的代垫。");
                            return false;
                        }
                        refreshLayout('代垫红冲【新增】', url = url + "?id=" + dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputRemoveHChongBtn.do">
                {
                    id: 'button-id-removehc',
                    name: '撤销红冲 ',
                    iconCls: 'button-oppaudit',
                    handler: function () {
                        var dataRow = $("#journalsheet-table-matcostsInput").datagrid('getSelected');
                        if (dataRow == null || dataRow.id == null || $.trim(dataRow.id) == "") {
                            $.messager.alert("提醒", "请选择相应的红冲!");
                            return false;
                        }
                        if (dataRow.hcflag != "1" && dataRow.hcflag != "2") {
                            $.messager.alert("提醒", "抱歉，你选择的" + dataRow.id + " 不为红冲单据。<br/>请选择相应的红冲单据。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否撤销代垫红冲?", function (r) {
                            if (r) {
                                $.ajax({
                                    url: 'journalsheet/matcostsInput/removeMatcostsInputHC.do',
                                    type: 'post',
                                    dataType: 'json',
                                    data: {id: dataRow.id},
                                    success: function (json) {
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "撤销代垫红冲成功!");
                                            $("#matcostsInput-query-List").trigger('click');
                                        }
                                        else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "撤销代垫红冲失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "撤销代垫红冲失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseWriteoffBtn.do">
                {
                    id: 'button-id-writeoff',
                    name: '代垫核销 ',
                    iconCls: 'button-audit',
                    handler: function () {

                        var rows = $("#journalsheet-table-matcostsInput").datagrid('getChecked');
                        var supplierid = "";
                        var isgo = true;
                        if (rows != null && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].iswriteoff == '1') {
                                    continue;
                                }
                                if (supplierid == "") {
                                    supplierid = rows[i].supplierid;
                                }
                                if (supplierid == "") {
                                    supplierid = rows[i].supplierid;
                                }
                                if (supplierid != rows[i].supplierid) {
                                    isgo = false;
                                    break;
                                }
                            }
                        }
                        if (!isgo) {
                            $.messager.alert("提醒", "代垫核销时，请选择相同的供应商的数据!");
                            return false;
                        }
                        var reqparams = {};
                        reqparams.supplierid = supplierid;

                        var tmpv = $("#journalsheet-date-matcostsInputPage-begintime").val();
                        reqparams.begintime = tmpv;
                        tmpv = $("#journalsheet-date-matcostsInputPage-endtime").val();
                        reqparams.endtime = tmpv;

                        $('<div id="matcostsReimburseWriteoffQuery-dialog-operate-content"></div>').appendTo('#matcostsReimburseWriteoffQuery-dialog-operate');
                        $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog({
                            title: '代垫收回核销：第一步、选择收回',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            method: 'post',
                            queryParams: reqparams,
                            href: 'journalsheet/matcostsInput/matcostsReimburseWriteoffQueryPage.do',
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {
                                createReimburseWriteoffQueryDataGrid();
                            },
                            onClose: function () {
                                $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog('destroy');
                            }
                        });
                        $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog('open');
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputRewriteoffBtn.do">
                {
                    id: 'button-id-rewriteoff',
                    name: '反核销',
                    iconCls: 'button-oppaudit',
                    handler: function () {
                        var dataRow = $("#journalsheet-table-matcostsInput").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的代垫录入!");
                            return false;
                        }
                        if (dataRow.iswriteoff != '1') {
                            $.messager.alert("提醒", "代垫" + dataRow.id + "未核销，不能进行反核销");
                            return false;
                        }
                        if (dataRow.hcflag == '1' || dataRow.hcflag == '2') {
                            $.messager.alert("提醒", "抱歉，红冲的代垫" + dataRow.id + "不能被反核销。<br/>请选择相应的代垫。");
                            return false;
                        }
                        $('#matcostsInputRewriteoff-dialog-operate').dialog({
                            title: '代垫' + dataRow.id + ' 反核销',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            href: 'journalsheet/matcostsInput/matcostsInputRewriteoffPage.do?id=' + dataRow.id,
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {
                            }
                        });
                        $('#matcostsInputRewriteoff-dialog-operate').dialog('open');
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputDelBtn.do">
                {
                    id: 'button-id-delete',
                    name: '删除',
                    iconCls: 'button-delete',
                    handler: function () {
                        var rows = $("#journalsheet-table-matcostsInput").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择相应的代垫录入!");
                            return false;
                        }
                        var idarrs = new Array();
                        var errorIdarr = new Array();
                        var cndidarrs = new Array();
                        if (null != rows && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].id && rows[i].id != "" && rows[i].sourcefrome) {
                                    if (rows[i].sourcefrome == '0' || rows[i].sourcefrome == '1') {
                                        idarrs.push(rows[i].id);
                                    } else {
                                        cndidarrs.push(rows[i].id);
                                    }
                                }
                                if (rows[i].oaid) {
                                    if (rows[i].iswriteoff == '1') {
                                        errorIdarr.push(rows[i].oaid);
                                    }
                                }
                            }
                        }
                        if (errorIdarr.length > 0) {
                            $.messager.alert("提醒", "已核销的代垫不能删除，下列代垫已经核销：" + errorIdarr.join(","));
                            return false;
                        }
                        if (cndidarrs.length > 0) {
                            $.messager.alert("提醒", "只有手动添加或者导入的代垫可以删除，以下代垫不能删除：" + cndidarrs.join(","));
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确认删除代垫录入?", function (r) {
                            if (r) {
                                loading();
                                $.ajax({
                                    type: 'post',
                                    cache: false,
                                    url: 'journalsheet/matcostsInput/deleteMatcostsInputMore.do',
                                    data: {idarrs: idarrs.join(",")},
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "删除成功数：" + json.isuccess + "<br />删除失败数：" + json.ifailure);
                                            $("#journalsheet-table-matcostsInput").datagrid('reload');
                                            $("#journalsheet-table-matcostsInput").datagrid('clearSelections');
                                        }
                                        else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputImportBtn.do">
                {
                    id: 'button-import-excel',
                    name: '导入',
                    iconCls: 'button-import',
                    handler: function () {
                        $("#matcostsInput-buttons-importclick").Excel('import', {
                            type: 'importUserdefined',
                            url: 'journalsheet/matcostsInput/importMatcostsInputListData.do',
                            onClose: function () { //导入成功后窗口关闭时操作，
                                var queryJSON = $("#matcostsInput-form-ListQuery").serializeJSON();
                                $("#journalsheet-table-matcostsInput").datagrid("load", queryJSON);
                            }
                        });
                        $("#matcostsInput-buttons-importclick").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputExportBtn.do">
                {
                    id: 'button-export-excel',
                    name: '导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var rows = $("#journalsheet-table-matcostsInput").datagrid('getChecked');

                        //查询参数直接添加在url中
                        var idarrs = new Array();
                        if (null != rows && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].id && rows[i].id != "") {
                                    idarrs.push(rows[i].id);
                                }
                            }
                        }
                        $("#matcostsInput-buttons-exportclick").Excel('export', {
                            queryForm: "#matcostsInput-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                            type: 'exportUserdefined',
                            name: '代垫录入列表',
                            fieldParam: {idarrs: idarrs.join(",")},
                            url: 'journalsheet/matcostsInput/exportMatcostsInputData.do'
                        });
                        $("#matcostsInput-buttons-exportclick").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/erpconnect/addMatCostsInputVouch.do">
                {
                    id: 'journalsheet-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#journalsheet-table-matcostsInput").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].hcflag != '0') {
                                $.messager.alert("提醒", "请选择非红冲的数据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }

                        $("#journalsheet-account-dialog").dialog({
                            title: '代垫凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showJournalsheetVouchPage.do',
                            onLoad: function () {
                                $("#journalsheet-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputPrintView.do">
                {
                    id: 'printview-matcostsInput',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsInputPrint.do">
                {
                    id: 'print-matcostsInput',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'journalsheet-table-matcostsInput',
            tname: 't_js_matcostsinput',
            id: ''
        });

        $("#journalsheet-table-matcostsInput").datagrid({
            authority: matcostsInputListColJson,
            frozenColumns: matcostsInputListColJson.frozen,
            columns: matcostsInputListColJson.common,
            fit: true,
            method: 'post',
            showFooter: true,
            rownumbers: true,
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            //toolbar:'#journalsheet-table-matcostsInputBtn',
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                }
            },
            rowStyler: function (index, row) {
                if (row.hcflag && row.hcflag == '1') {
                    return "color:#f00";
                }
                if (row.hcflag
                    && row.hcflag == '2') {
                    return "color:#00f";
                }
            },
            onSelect: function (rowIndex, rowData) {
                if (rowData.iswriteoff == "1"
                    || !(rowData.sourcefrome == '0' || rowData.sourcefrome == '1')
                ) {
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-edit');
                    $("#journalsheet-button-matcostsInput").buttonWidget("enableButton", 'button-id-rewriteoff');
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-delete');
                } else {
                    $("#journalsheet-button-matcostsInput").buttonWidget("enableButton", 'button-id-edit');
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-rewriteoff');
                    $("#journalsheet-button-matcostsInput").buttonWidget("enableButton", 'button-id-delete');
                }
                if (rowData.hcflag == '1' || rowData.hcflag == '2') {
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-addhc');
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-rewriteoff');
                    $("#journalsheet-button-matcostsInput").buttonWidget("enableButton", 'button-id-removehc');
                } else {
                    $("#journalsheet-button-matcostsInput").buttonWidget("enableButton", 'button-id-addhc');
                    $("#journalsheet-button-matcostsInput").buttonWidget("disableButton", 'button-id-removehc');
                }
            },
            onDblClickRow: function (rowIndex, rowData) {
                var title = "代垫录入";
                if (rowData.hcflag == '1' || rowData.hcflag == '2') {
                    title = "代垫红冲";
                }
                <%if(isEdit){%>
                if (rowData.iswriteoff != '1'
                    && ( rowData.sourcefrome == '0' || rowData.sourcefrome == '1')
                    && rowData.hcflag != '1'
                    && rowData.hcflag != '2') {
                    refreshLayout("代垫录入【修改】", 'journalsheet/matcostsInput/showMatcostsInputEditPage.do?id=' + rowData.id);
                } else {
                    refreshLayout(title + "【详情】", 'journalsheet/matcostsInput/showMatcostsInputViewPage.do?id=' + rowData.id);
                }
                <% }else { %>
                refreshLayout(title + "【详情】", 'journalsheet/matcostsInput/showMatcostsInputViewPage.do?id=' + rowData.id);
                <%}%>
            },
            onCheckAll: function () {
                settleCountTotalAmount();
            },
            onUncheckAll: function () {
                settleCountTotalAmount();
            },
            onCheck: function () {
                settleCountTotalAmount();
            },
            onUncheck: function () {
                settleCountTotalAmount();
            }
        }).datagrid("columnMoving");
    });
    function settleCountTotalAmount() {
        var rows = $("#journalsheet-table-matcostsInput").datagrid('getChecked');
        if (null == rows || rows.length == 0) {
            var foot = [];
            if (null != footerobject) {
                foot.push(emptyChooseObjectFoot());
                foot.push(footerobject);
            }
            $("#journalsheet-table-matcostsInput").datagrid("reloadFooter", foot);
            return false;
        }
        var factoryamount = 0;
        var htcompdiscount = 0;
        var htpayamount = 0;
        var branchaccount = 0;
        var actingmatamount = 0;
        var reimburseamount = 0;
        var writeoffamount = 0;
        var expense=0;
        <%--
        <c:forEach items="${reimbursetypeList }" var="list">
            var reimburse_${list.code}=0;
        </c:forEach>
        --%>
        for (var i = 0; i < rows.length; i++) {
            factoryamount = Number(factoryamount) + Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
            htcompdiscount = Number(htcompdiscount) + Number(rows[i].htcompdiscount == undefined ? 0 : rows[i].htcompdiscount);
            htpayamount = Number(htpayamount) + Number(rows[i].htpayamount == undefined ? 0 : rows[i].htpayamount);
            branchaccount = Number(branchaccount) + Number(rows[i].branchaccount == undefined ? 0 : rows[i].branchaccount);
            actingmatamount = Number(actingmatamount) + Number(rows[i].actingmatamount == undefined ? 0 : rows[i].actingmatamount);
            reimburseamount = Number(reimburseamount) + Number(rows[i].reimburseamount == undefined ? 0 : rows[i].reimburseamount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            expense = Number(expense) + Number(rows[i].expense == undefined ? 0 : rows[i].expense);

            <%--
            <c:forEach items="${reimbursetypeList }" var="list">
                reimburse_${list.code} = Number(reimburse_${list.code})+Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
            </c:forEach>
            --%>
        }
        var foot = [{
            suppliername: '选中金额', factoryamount: factoryamount, htcompdiscount: htcompdiscount,
            htpayamount: htpayamount, branchaccount: branchaccount, actingmatamount: actingmatamount,
            reimburseamount: reimburseamount, writeoffamount: writeoffamount,expense:expense
            <%--
            <c:forEach items="${reimbursetypeList }" var="list">
                ,reimburse_${list.code} : reimburse_${list.code}
            </c:forEach>
            --%>
        }];
        if (null != footerobject) {
            foot.push(footerobject);
        }
        $("#journalsheet-table-matcostsInput").datagrid("reloadFooter", foot);
    }
    function emptyChooseObjectFoot() {
        var factoryamount = 0;
        var htcompdiscount = 0;
        var htpayamount = 0;
        var branchaccount = 0;
        var actingmatamount = 0;
        var reimburseamount = 0;
        var writeoffamount = 0;
        <%--
        <c:forEach items="${reimbursetypeList }" var="list">
            var reimburse_${list.code}=0;
        </c:forEach>
        --%>
        var foot = {
            suppliername: '选中金额', factoryamount: factoryamount, htcompdiscount: htcompdiscount,
            htpayamount: htpayamount, branchaccount: branchaccount, actingmatamount: actingmatamount,
            reimburseamount: reimburseamount, writeoffamount: writeoffamount
            <%--
            <c:forEach items="${reimbursetypeList }" var="list">
                ,reimburse_${list.code} : reimburse_${list.code}
            </c:forEach>
            --%>
        };
        return foot;
    }
    function showMatcostsInputView(id) {
        refreshLayout("代垫录入" + id + "【详情】", 'journalsheet/matcostsInput/showMatcostsInputViewPage.do?id=' + id);
    }
    function viewOa(id) {

        top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "matcostsInput-dialog-print",
            code: "journalsheet_matcostsinput",
            tableId: "journalsheet-table-matcostsInput",
            url_preview: "print/journalsheet/matcostsInputPrintView.do",
            url_print: "print/journalsheet/matcostsInputPrint.do",
            btnPreview: "printview-matcostsInput",
            btnPrint: "print-matcostsInput"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
