<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆装规则列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <style type="text/css">
        .len70 {
            width: 70px;
        }
        .len90 {
            width: 90px;
        }
        .len200 {
            width: 200px;
        }
        .len220 {
            width: 220px;
        }
    </style>
    <div class="easyui-layout" data-options="fit:true" style="padding: 0px;">
        <div data-options="region:'center'">
            <div id="basefiles-toolbar-bomListPage" style="padding: 0px; height: auto;">
                <div class="buttonBG" id="basefiles-buttons-bomListPage"></div>
                <form id="basefiles-form-bomListPage">
                    <input type="hidden" id="basefiles-type-bomListPage" name="type" value="1"/>
                    <table>
                        <tr>
                            <td class="len70 left">编　号：</td>
                            <td class="len200 left"><input type="text" id="basefiles-id-bomListPage" name="id" class="len180"></td>
                            <td class="len70 right">业务日期：</td>
                            <td class="len200 left">
                                <input type="text" class="len90 Wdate" id="basefiles-startdate-bomListPage" name="startdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})">到<input type="text" class="len90 Wdate" id="basefiles-enddate-bomListPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})">
                            </td>
                            <td class="len70 right">单据状态：</td>
                            <td class="len150 left">
                                <select id="basefiles-status-bomListPage" name="status" class="len150">
                                    <option></option>
                                    <option value="2">保存</option>
                                    <option value="1">启用</option>
                                    <option value="0">关闭</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">商　品：</td>
                            <td class="left"><input type="text" id="basefiles-goodsid-bomListPage" name="goodsid" class="len180"></td>
                            <!--
                            <td class="right">供应商：</td>
                            <td class="left"><input type="text" id="basefiles-supplierid-bomListPage" name="supplierid" style="width: 192px;"></td>
                            -->
                            <td colspan="6" align="right">
                                <a href="javascript:void(0);" id="basefiles-queay-bomListPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="basefiles-reset-bomListPage" class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="basefiles-datagrid-bomListPage"></table>
        </div>
    </div>
    <script type="text/javascript">

        $(function() {

            // 商品
            $('#basefiles-goodsid-bomListPage').goodsWidget({});

            // 按钮
            $('#basefiles-buttons-bomListPage').buttonWidget({
                initButton: [
                    <security:authorize url="/basefiles/saveBom.do">
                    {
                        type: 'button-add',
                        handler: function() {

                            top.addTab('basefiles/bomPage.do?type=add', '拆装规则新增');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/basefiles/editBom.do">
                    {
                        type: 'button-edit',
                        handler: function() {

                            var row = $('#basefiles-datagrid-bomListPage').datagrid('getSelected');

                            if(row == null) {

                                $.messager.alert('提醒', '请选择记录！');
                                return false;
                            }

                            var id = row.id || '';
                            top.addTab('basefiles/bomPage.do?type=edit&id=' + id, '拆装规则编辑');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/basefiles/openBom.do">
                    {
                        type: 'button-open',
                        handler: function() {

                            var row = $('#basefiles-datagrid-bomListPage').datagrid('getSelected');

                            if(row == null) {

                                $.messager.alert('提醒', '请选择拆装规则！');
                                return false;
                            }

                            if(row.status == '1') {

                                $.messager.alert('提醒', '该拆装规则已经启用！');
                                return false;
                            }

                            loading();
                            $.ajax({
                                type: 'post',
                                url: 'basefiles/openBom.do',
                                data: {id: row.id, status: '1'},
                                dataType: 'json',
                                success: function(json) {

                                    loaded();

                                    if(json.flag) {

                                        $.messager.alert('提醒', '启用成功。');
                                        $('#basefiles-datagrid-bomListPage').datagrid('reload');
                                        return true;
                                    }

                                    $.messager.alert('提醒', '启用失败！');
                                    return true;
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/basefiles/closeBom.do">
                    {
                        type: 'button-close',
                        handler: function() {

                            var row = $('#basefiles-datagrid-bomListPage').datagrid('getSelected');

                            if(row == null) {

                                $.messager.alert('提醒', '请选择拆装规则！');
                                return false;
                            }

                            if(row.status == '0') {

                                $.messager.alert('提醒', '该拆装规则已经关闭！');
                                return false;
                            }

                            loading();
                            $.ajax({
                                type: 'post',
                                url: 'basefiles/closeBom.do',
                                data: {id: row.id, status: '0'},
                                dataType: 'json',
                                success: function(json) {

                                    loaded();

                                    if(json.flag) {

                                        $.messager.alert('提醒', '禁用成功。');
                                        $('#basefiles-datagrid-bomListPage').datagrid('reload');
                                        return true;
                                    }

                                    $.messager.alert('提醒', '禁用失败！');
                                    return true;
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/basefiles/deleteBom.do">
                    {
                        type: 'button-delete',
                        handler: function() {

                            $.messager.confirm("提醒","确定要删除该拆装规则吗？",function(r) {
                                if (r) {

                                    var row = $('#basefiles-datagrid-bomListPage').datagrid('getSelected');

                                    if(row == null) {

                                        $.messager.alert('提醒', '请选择记录！');
                                        return false;
                                    }

                                    loading();
                                    $.ajax({
                                        type: 'post',
                                        url: 'basefiles/deleteBom.do',
                                        data: {id: row.id},
                                        dataType: 'json',
                                        success: function(json) {

                                            loaded();

                                            if(json.flag) {

                                                $.messager.alert('提醒', '删除成功。');
                                                $('#basefiles-datagrid-bomListPage').datagrid('reload');
                                                return true;
                                            }

                                            $.messager.alert('警告', json.msg || '删除失败！');
                                            return true;
                                        }
                                    });
                                }
                            });

                        }
                    },
                    </security:authorize>
                    {}
                ],
                buttons: [{}],
                model: 'base',
                type: 'list'
            });

            // datagrid项目
            var cols = $('#basefiles-datagrid-bomListPage').createGridColumnLoad({
                frozenCol : [[
                    {field: 'id', title: '单据编号', width: 135, hidden: false}
                ]],
                commonCol : [[
                    {field: 'businessdate', title: '业务日期', width: 80},
                    {field: 'name', title: '拆装规则名称', width: 180},
                    {field: 'goodsid', title: '商品编码', width: 80},
                    {field: 'goodsname', title: '商品名称', width: 180},
                    {field: 'status', title: '状态', width: 50,
                        formatter: function(value, row, index) {

                            var status = value || '';
                            if(status == '0') {

                                return '禁用';

                            } else if(status == '1') {

                                return '启用';

                            } else if(status == '2') {

                                return '保存';
                            }
                        }
                    },
                    {field: 'addusername', title: '制单人', width: 65, sortable: true},
                    {field: 'addtime', title: '制单时间', width: 130, sortable: true},
                    {field: 'remark', title: '备注', width: 250}
                ]]
            });

            // datagrid
            $('#basefiles-datagrid-bomListPage').datagrid({
                authority: cols,
                frozenColumns: cols.frozen,
                columns: cols.common,
                fit: true,
                title: '',
                method: 'post',
                rownumbers: true,
                pagination: true,
                idField: 'id',
                singleSelect: true,
                url: 'basefiles/selectBomList.do',
                toolbar: '#basefiles-toolbar-bomListPage',
                <security:authorize url="/basefiles/editBom.do">
                onDblClickRow: function(index, row) {

                    var id = row.id;
                    top.addTab('basefiles/bomPage.do?type=edit&id=' + id + '&title=' + top.getNowTabTitle(), '拆装规则编辑');
                },
                </security:authorize>
                onClickRow: function(index, row) {

                    var id = row.id;
                    var status = row.status || '';

                    // disable all button
                    $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-add');
                    $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-edit');
                    $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-open');
                    $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-close');
                    $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-delete');

                    // 禁用
                    if(status == '0') {

                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-edit');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-open');
                        $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-close');
                    }

                    // 启用
                    if(status == '1') {

                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-edit');
                        $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-open');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-close');
                    }

                    // 保存
                    if(status == '2') {

                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-edit');
                        $('#basefiles-buttons-bomListPage').buttonWidget('enableButton', 'button-open');
                        $('#basefiles-buttons-bomListPage').buttonWidget('disableButton', 'button-close');
                    }
                }
            })/*.datagrid("columnMoving")*/;

            // 查询
            $('#basefiles-queay-bomListPage').on('click', function(e) {

                var param = $('#basefiles-form-bomListPage').serializeJSON();

                $("#basefiles-datagrid-bomListPage").datagrid({
                    url: 'basefiles/selectBomList.do',
                    queryParams: param
                });
            });

            // 重置
            $('#basefiles-reset-bomListPage').on('click', function(e) {

                $('#basefiles-goodsid-bomListPage').goodsWidget('clear');
                $('#basefiles-supplierid-bomListPage').supplierWidget('clear');

                $('#basefiles-form-bomListPage')[0].reset();

                var param = $('#basefiles-form-bomListPage').serializeJSON();

                $("#basefiles-datagrid-bomListPage").datagrid({
                    url: 'basefiles/selectBomList.do',
                    queryParams: param
                });
            });

        });

    </script>
</body>
</html>
