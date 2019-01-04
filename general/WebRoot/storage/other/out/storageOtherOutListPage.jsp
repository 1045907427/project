<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>其他出库单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-storageOtherOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-storageOtherOutPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-storageOtherOutPage">
    <form action="" id="storage-form-query-storageOtherOutPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                </td>
                <td>编&nbsp;&nbsp;号:</td>
                <td><input type="text" name="id" style="width: 150px;"/></td>
                <td>状&nbsp;&nbsp;态:</td>
                <td><select name="status" style="width:150px;">
                    <option></option>
                    <option value="2" selected="selected">保存</option>
                    <option value="4">关闭</option>
                </select></td>
            </tr>
            <tr>
                <td>出库仓库:</td>
                <td>
                    <input type="text" id="storage-storageid-storageOtherOutPage" name="storageid"/>
                    <%--<select name="storageid" style="width: 180px;">--%>
                    <%--<option></option>--%>
                    <%--<c:forEach items="${storageList }" var="list">--%>
                    <%--<option value="${list.id }">${list.name }</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                </td>
                <td>来源类型:</td>
                <td><input id="storage-otherOut-sourcetype" name="sourcetype"></td>
                <td>来源单据编号:</td>
                <td>
                    <input id="storage-otherOut-sourceid" name="sourceid" class="len150">
                </td>
            </tr>
            <tr>
                <td>相关部门:</td>
                <td>
                    <input id="storage-otherOut-deptid" name="deptid" style="width:225px;"/>
                </td>
                <td>打印状态:</td>
                <td>
                    <select name="printsign" style="width:150px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <%-- 瑞家特别
                        <option value="2">小于</option>
                        <option value="3">小于等于</option>
                         --%>
                        <option value="4">已打印</option>
                        <%-- 瑞家特别
                        <option value="5">大于等于</option>
                         --%>
                    </select>
                    <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                </td>
                <td>出库类型:</td>
                <td><input id="storage-otherOut-outtype" name="outtype"></td>
                <td>
                    <a href="javaScript:void(0);" id="storage-queay-storageOtherOut" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-storageOtherOut" class="button-qr">重置</a>
                    <span id="storage-query-advanced-storageOtherOut"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="storageOtherOut-account-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-storageOtherOutPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-storageOtherOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/storageOtherOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/showStorageOtherOutAddPage.do', "其他出库单新增");
                    },
                    url: '/storage/storageOtherOutAddPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutEditPage.do">
                <!--					{-->
                <!--						type: 'button-edit',-->
                <!--						handler: function(){-->
                <!--							var con = $("#storage-datagrid-storageOtherOutPage").datagrid('getSelected');-->
                <!--							if(con == null){-->
                <!--								$.messager.alert("提醒","请选择一条记录");-->
                <!--								return false;-->
                <!--							}	-->
                <!--							top.addTab('storage/showStorageOtherOutEditPage.do?id='+ con.id, "其他出库单修改");-->
                <!--						},-->
                <!--						url:'/storage/storageOtherOutEditPage.do'-->
                <!--					},-->
                </security:authorize>
                <security:authorize url="/storage/showStorageOtherOutViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-storageOtherOutPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showStorageOtherOutEditPage.do?id=' + con.id, "其他出库单查看");
                    },
                    url: '/storage/showStorageOtherOutViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importUserdefined',
                        url: 'storage/storageOtherOutImport.do',
                        importparam: '必填项：出库仓编码，商品编码，数量。<br/>选填项：单价，金额',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#storage-datagrid-storageOtherOutPage").datagrid('reload');	//更新列表
                        }
                    },
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-query-storageOtherOutPage",
                        datagrid: "#storage-datagrid-storageOtherOutPage",
                        type: "exportUserdefined",
                        name: "其它出库单",
                        url: 'storage/storageOtherOutExport.do'
                    },
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_other_out',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-storageOtherOutPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/erpconnect/addStorageOtherOutVouch.do">
                {
                    id: 'button-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-storageOtherOutPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if(rows[i].status!='3'&& rows[i].status!='4'){
                                $.messager.alert("提醒", "请选择审核通过和关闭状态的单据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#storageOtherOut-account-dialog").dialog({
                            title: '其它出库单凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showStorageOtherOutVouchPage.do',
                            onLoad: function () {
                                $("#storageOtherOut-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutPrintBtn.do">
                {
                    id: 'button-print-order',
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
            tname: 't_storage_other_out'
        });
        var tableColumnListJson = $("#storage-datagrid-storageOtherOutPage").createGridColumnLoad({
            name: 't_storage_other_out,t_storage_other_out_detail',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '出库仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {
                    field: 'outtype', title: '出库类型', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.outtypename;
                    }
                },
                <security:authorize url="/storage/storageOtherOutShowAmount.do">
                {
                    field: 'taxamount', title: '金额', width: 80, align: 'right', isShow: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税金额', width: 80, align: 'right', isShow: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                {
                    field: 'deptid', title: '相关部门', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.deptname;
                    }
                },
                {
                    field: 'userid', title: '相关人员', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.username;
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'sourcetype', title: '来源类型', width: 80,sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '4') {
                            return '组装单';
                        } else if (value == '3') {
                            return '拆分单'
                        } else if (value == '2') {
                            return '借货还货单';
                        } else {
                            return '正常单据'
                        }
                    }
                },
                {field: 'sourceid', title: '来源单据编号', width: 80},
                <%-- 打印提示特别 --%>
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', width: 80, hidden: true},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-storageOtherOutPage").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            url: 'storage/showStorageOtherOutList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-storageOtherOutPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showStorageOtherOutEditPage.do?id=' + rowData.id, "其他出库单查看");
            }
        }).datagrid("columnMoving");

        //出库仓库
        $("#storage-storageid-storageOtherOutPage").widget({
            width: 225,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });
        //部门控件
        $("#storage-otherOut-deptid").widget({
            name: 't_storage_other_out',
            col: 'deptid',
            singleSelect: true,
            isdatasql: false,
            width: 225,
            onlyLeafCheck: false
        });
        $("#storage-otherOut-sourcetype").widget({
            referwid:'RL_T_SYS_CODESOURCETYPE',
            // name: 't_storage_other_Out',
            // col: 'sourcetype',
            singleSelect: true,
            isdatasql: false,
            width:150,
            onlyLeafCheck: false
        });
        $("#storage-otherOut-outtype").widget({
            referwid:'RL_T_SYS_CODE_OUT_TYPE',
            singleSelect: true,
            isdatasql: false,
            width: 150,
            onlyLeafCheck: false
        });
        //通用查询组建调用
//			$("#storage-query-advanced-storageOtherOut").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_other_out',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-storageOtherOutPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-storageOtherOut", "storage-reload-storageOtherOut");

        //查询
        $("#storage-queay-storageOtherOut").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-storageOtherOutPage").serializeJSON();
            $("#storage-datagrid-storageOtherOutPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-storageOtherOut").click(function () {
            $("#storage-storageid-storageOtherOutPage").widget('clear');
            $("#storage-otherOut-deptid").widget('clear');
            $("#storage-form-query-storageOtherOutPage").form("reset");
            var queryJSON = $("#storage-form-query-storageOtherOutPage").serializeJSON();
            $("#storage-datagrid-storageOtherOutPage").datagrid("load", queryJSON);
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "otherout-dialog-print",
            code: "storage_otherout",
            tableId: "storage-datagrid-storageOtherOutPage",
            url_preview: "print/storage/storageOtherOutPrintView.do",
            url_print: "print/storage/storageOtherOutPrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
