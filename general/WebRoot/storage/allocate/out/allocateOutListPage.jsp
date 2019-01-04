<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调拨单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-allocateOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-allocateOutPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-allocateOutPage">
    <form action="" id="storage-form-query-allocateOutPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                <td>编&nbsp;&nbsp;号:</td>
                <td><input type="text" name="id" style="width: 150px;"/></td>
                <td>状&nbsp;&nbsp;态:</td>
                <td>
                    <select name="status" style="width:180px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="7">审核出库</option>
                        <option value="4">关闭</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>调出仓库:</td>
                <td><input id="storage-query-outstorageid" type="text" name="outstorageid" style="width: 225px;"/></td>
                </td>
                <td>调入仓库:</td>
                <td><input id="storage-query-enterstorageid" type="text" name="enterstorageid" style="width: 150px;"/>
                </td>
                <td>打印状态:</td>
                <td>
                    <select name="printsign" style="width: 180px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <option value="4">已打印</option>
                    </select>
                    <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td>商品:</td>
                <td><input id="storage-query-goodsid" type="text" name="goodsid" style="width: 150px;"/></td>
                <c:choose>
                    <c:when test="${deliveryAddAllocate eq '1'}">
                        <td>配货状态:</td>
                        <td>
                            <select name="isdelivery" style="width:180px;">
                                <option></option>
                                <option value="0">未配货</option>
                                <option value="1">配货中</option>
                                <option value="2">已配货</option>
                                <option value="3">配货完毕</option>
                            </select>
                        </td>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="storage-queay-allocateOut" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-allocateOut" class="button-qr">重置</a>
                    <span id="storage-query-advanced-allocateOut"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-allocateOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/allocateOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/showAllocateOutAddPage.do', "调拨单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutEditPage.do">
                <!--					{-->
                <!--						type: 'button-edit',-->
                <!--						handler: function(){-->
                <!--							var con = $("#storage-datagrid-allocateOutPage").datagrid('getSelected');-->
                <!--							if(con == null){-->
                <!--								$.messager.alert("提醒","请选择一条记录");-->
                <!--								return false;-->
                <!--							}	-->
                <!--							top.addOrUpdateTab('storage/showAllocateOutEditPage.do?id='+ con.id, "调拨单修改");-->
                <!--						}-->
                <!--					},-->
                </security:authorize>
                <security:authorize url="/storage/allocateOutViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-allocateOutPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showAllocateOutEditPage.do?id=' + con.id, "调拨单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importUserdefined',
                        url: 'storage/allocateOutImport.do',
                        importparam: '必填项：调出仓库，调入仓库，商品编码，箱数，个数。</br>选填：条形码，批次号，生产日期，备注</br>调出仓库，调入仓库请填写仓库名称</br>批次管理的商品请指定批次号或者生产日期',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#storage-datagrid-allocateOutPage").datagrid('reload');	//更新列表
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#storage-datagrid-allocateOutPage",
                        queryForm: "#storage-form-query-allocateOutPage",
                        type: 'exportUserdefined',
                        name: '调拨单商品列表',
                        url: 'storage/exportAllocateOutList.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_allocate_out',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-allocateOutPage',
                        plain: true
                    }
                },
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_storage_allocate_out'
        });
        var allocateOutJson = $("#storage-datagrid-allocateOutPage").createGridColumnLoad({
            name: 't_storage_allocate_out',
            frozenCol: [[
                {field: 'idck', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'outstorageid', title: '调出仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.outstoragename;
                    }
                },
                {
                    field: 'enterstorageid', title: '调入仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.enterstoragename;
                    }
                },
                {
                    field: 'sourcetype', title: '来源类型', width: 90, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("allocateOut-sourcetype", value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
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
                {field: 'remark', title: '备注', width: 80, sortable: false},
                {field: 'isdelivery', title: '配货状态', width: 80, sortable: false,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return "未配货";
                        } else if (value == "1") {
                            return "配货中";
                        } else if (value == "2") {
                            return "已配货";
                        } else if (value == "3") {
                            return "配货完毕";
                        }
                    }
                }
            ]]
        });
        $("#storage-datagrid-allocateOutPage").datagrid({
            authority: allocateOutJson,
            frozenColumns: allocateOutJson.frozen,
            columns: allocateOutJson.common,
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
            url: 'storage/showAllocateOutList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-allocateOutPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showAllocateOutEditPage.do?id=' + rowData.id, "调拨单查看");
            }
        }).datagrid("columnMoving");
        $("#storage-query-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width: 225,
            singleSelect: false
        })
        $("#storage-query-enterstorageid").widget({
            name: 't_storage_allocate_out',
            width: 150,
            col: 'enterstorageid',
            view: true,
            singleSelect: true
        });
        $("#storage-query-outstorageid").widget({
            name: 't_storage_allocate_out',
            width: 225,
            col: 'outstorageid',
            view: true,
            singleSelect: true
        });
        //通用查询组建调用
//			$("#storage-query-advanced-allocateOut").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_allocate_out',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-allocateOutPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-allocateOut", "storage-reload-allocateOut");

        //查询
        $("#storage-queay-allocateOut").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
            $("#storage-datagrid-allocateOutPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-allocateOut").click(function () {
            $("#storage-query-outstorageid").widget("clear");
            $("#storage-query-enterstorageid").widget("clear");
            $("#storage-query-goodsid").widget('clear');
            $("#storage-form-query-allocateOutPage")[0].reset();
            var queryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
            $("#storage-datagrid-allocateOutPage").datagrid("load", queryJSON);
        });
    });

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "allocateout-dialog-print",
            code: "storage_allocateout",
            tableId: "storage-datagrid-allocateOutPage",
            url_preview: "print/storage/allocateOutPrintView.do",
            url_print: "print/storage/allocateOutPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
