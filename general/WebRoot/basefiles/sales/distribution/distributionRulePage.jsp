<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则</title>
    <jsp:include page="/include.jsp"/>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-distributionRulePage"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-distributionRulePage">
        </div>
    </div>
</div>
<div id="sales-dialog-distributionRulePage"></div>
<script type="text/javascript">

    <!--

    var $widgetHtml = null;
    var goodsData = new Array();
    var brandData = new Array();
    var goodssortData = new Array();
    var goodstypeData = new Array();
    var supplierData = new Array();

    var oldGoodstype = null;
    var newGoodstype = null;

    var goodssortArr = new Array();    // 全部商品分类

    $(function () {

        var type = '${param.type }';
        var url = 'basefiles/distribution/distributionRuleEditPage.do?id=${param.id }';
        if(type == 'edit') {
            url = 'basefiles/distribution/distributionRuleEditPage.do?id=${param.id }';
        } else if(type == 'add') {
            url = 'basefiles/distribution/distributionRuleAddPage.do';
        } else {
            url = 'basefiles/distribution/distributionRuleViewPage.do?id=${param.id }';
        }

        $('#sales-panel-distributionRulePage').panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {

                $widgetHtml = $widget;

                $datagrid.datagrid({
                    columns: [[
                        {field: 'rowcheck', checkbox: true},
                        {field: 'goodsid', title: '商品编码', width: 80, align: 'left'},
                        {field: 'goodsname', title: '商品名称', width: 180, align: 'left'},
                        {field: 'barcode', title: '条形码', width: 110, align: 'left'},
                        {field: 'brandid', title: '品牌编码', width: 60, align: 'left'},
                        {field: 'brandname', title: '品牌名称', width: 90, align: 'left'},
                        {field: 'supplierid', title: '供应商编码', width: 80, align: 'left'},
                        {field: 'suppliername', title: '供应商名称', width: 180, align: 'left'},
                        {field: 'goodssort', title: '编码', width: 80, align: 'left', hidden: true,
                            formatter: function (value, row, index) {
                                return value || row.goodssortname;
                            }
                        },
                        {field: 'goodssortname', title: '商品分类', width: 80, align: 'left'},
                        {field: 'goodstype', title: '商品类型', width: 80, align: 'left',
                            formatter: function (value, row, index) {
                                return row.goodstypename;
                            }
                        }
                    ]],
                    fit: true,
                    method: 'post',
                    rownumbers: true,
                    pagination: false,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    data: $.parseJSON('${detailList }'),
                    toolbar:[
                        {text: '添加', iconCls: 'button-add', handler: showAddDetailWindow},
                        {text: '删除', iconCls: 'button-delete', handler: deleteDetails}
                    ]
                });

                // 隐藏无关列
                switchGoodsRuleType($goodsruletype.val());
                $goodsruletype.off().on('change', function(e) {

                    switchGoodsRuleType($(this).val());

                }).on('focusin', function(e) {

                    saveDatagridData($(this).val());
                });

                // 客户群名称切换
                switchCustomerType($customertype.val());
                $customertype.off().on('change', function(e) {
                    switchCustomerType($(this).val());
                });

                $('#sales-buttons-distributionRulePage').buttonWidget({
                    type: '${param.type }',
                    taburl: '/basefiles/distribution/distributionRuleListPage.do',
                    id: '${param.id}',
                    datagrid: 'sales-datagrid-distributionRuleListPage',
                    model: 'base',
                    initButton: [
                        <security:authorize url="/basefiles/distribution/saveDistributionRule.do">
                        {
                            type: 'button-save',
                            handler: saveRule
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/distribution/addDistributionRule.do">
                        {
                            type: 'button-add',
                            handler: addRule
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/distribution/enableDistributionRule.do">
                        {
                            type: 'button-open',
                            handler: function(){

                                if(!'${param.id }') {
                                    $.messager.alert('提醒', '编号未设定！');
                                    return false;
                                }

                                loading('启用中...');
                                $.ajax({
                                    type: 'get',
                                    url: 'basefiles/distribution/enableDistributionRule.do',
                                    data: {ids: '${param.id }'},
                                    dataType: 'json',
                                    success: function (json) {

                                        loaded();
                                        if(json.success > 0) {
                                            $.messager.alert('提醒', '启用成功。');
                                            try {
                                                var listWindow = tabsWindowURL('/basefiles/distribution/distributionRuleListPage.do');
                                                listWindow.$('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                                            } catch (e) {}
                                            window.location.href = 'basefiles/distribution/distributionRulePage.do?type=edit&id=${param.id }';
                                            return true;
                                        }

                                        $.messager.alert('提醒', '启用失败！<br/>' + json.msg);
                                    },
                                    error: function (e) {
                                        loaded();
                                        $.messager.alert('错误', '出错了！');
                                        return false;
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/distribution/disableDistributionRule.do">
                        {
                            type: 'button-close',
                            handler: function(){

                                if(!'${param.id }') {
                                    $.messager.alert('提醒', '编号未设定！');
                                    return false;
                                }

                                loading('禁用中...');
                                $.ajax({
                                    type: 'get',
                                    url: 'basefiles/distribution/disableDistributionRule.do',
                                    data: {ids: '${param.id }'},
                                    dataType: 'json',
                                    success: function (json) {

                                        loaded();
                                        if(json.success > 0) {
                                            $.messager.alert('提醒', '禁用成功。');
                                            try {
                                                var listWindow = tabsWindowURL('/basefiles/distribution/distributionRuleListPage.do');
                                                listWindow.$('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                                            } catch (e) {}
                                            window.location.href = 'basefiles/distribution/distributionRulePage.do?type=edit&id=${param.id }';
                                            return true;
                                        }

                                        $.messager.alert('提醒', '禁用失败！<br/>' + json.msg);
                                    },
                                    error: function (e) {
                                        loaded();
                                        $.messager.alert('错误', '出错了！');
                                        return false;
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/distribution/deleteDistributionRule.do">
                        {
                            type: 'button-delete',
                            handler: function(){

                                if(!'${param.id }') {
                                    $.messager.alert('提醒', '编号未设定！');
                                    return false;
                                }
                                
                                loading('删除中...');
                                $.ajax({
                                    type: 'get',
                                    url: 'basefiles/distribution/deleteDistributionRule.do',
                                    data: {ids: '${param.id }'},
                                    dataType: 'json',
                                    success: function (json) {

                                        loaded();
                                        if(json.success > 0) {
                                            $.messager.alert('提醒', '删除成功。');
                                            try {
                                                var listWindow = tabsWindowURL('/basefiles/distribution/distributionRuleListPage.do');
                                                listWindow.$('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                                            } catch (e) {}
                                            top.closeNowTab();
                                            return true;
                                        }

                                        $.messager.alert('提醒', '删除失败！<br/>' + json.msg);
                                    },
                                    error: function (e) {
                                        loaded();
                                        $.messager.alert('错误', '出错了！');
                                        return false;
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        {
                            type: 'button-back',
                            handler: function(data){

                                window.location.href = 'basefiles/distribution/distributionRulePage.do?type=${param.type }&id=' + data.id;
                                return true;
                            }
                        },
                        {
                            type: 'button-next',
                            handler: function(data){

                                window.location.href = 'basefiles/distribution/distributionRulePage.do?type=${param.type }&id=' + data.id;
                                return true;
                            }
                        },
                        {}
                    ]
                });
                $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-back');
                $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-next');
                <c:choose>
                    <c:when test="${param.type eq 'view'}">
                        $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-save');
                        $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-delete');
                        $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-open');
                        $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-close');
                    </c:when>
                    <c:otherwise>
                        $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-save');
                        $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-delete');
                        $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-open');
                        $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-close');
                    </c:otherwise>
                </c:choose>
                <c:if test="${param.type eq 'add'}">
                    $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-delete');
                    $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-open');
                    $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-close');
                    $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-back');
                    $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-next');
                </c:if>
                <c:if test="${param.type ne 'view'}">
                    <c:choose>
                        <c:when test="${distributionRule.state eq '1'}">
                            $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-delete');
                            $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-open');
                            $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-close');
                        </c:when>
                        <c:when test="${distributionRule.state eq '0'}">
                            $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-delete');
                            $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-open');
                            $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-close');
                        </c:when>
                        <c:when test="${distributionRule.state eq '2'}">
                            $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-delete');
                            $('#sales-buttons-distributionRulePage').buttonWidget('enableButton', 'button-open');
                            $('#sales-buttons-distributionRulePage').buttonWidget('disableButton', 'button-close');
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            }
        });

    });

    /**
     * 切换客户群
     * @param v
     * @returns {boolean}
     */
    function switchCustomerType(v) {
        $widgetBox.html('');
        $widgetBox.html($widgetHtml);
        $widget = $widgetBox.children();
        <c:if test="${param.type ne 'view'}">
            $widget.removeAttr('disabled');
        </c:if>
        if(v == 1) {
            $widget.widget({
                referwid: 'RL_T_BASE_SALES_CUSTOMER',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 2) {
            $widget.widget({
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 3) {
            $widget.widget({
                referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 4) {
            $widget.widget({
                referwid: 'RL_T_SYS_CODE_PROMOTIONSORT',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 5) {
            $widget.widget({
                referwid: 'RT_T_BASE_SALES_AREA',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 6) {
            $widget.widget({
                referwid: 'RL_T_SYS_CODE_CREDITRATING',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 7) {
            $widget.widget({
                referwid: 'RL_T_SYS_CODE_CANCELTYPE',
                required: true,
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        }
        return true;
    }

    /**
     * 切换商品规则
     *
     * @param v
     * @returns {boolean}
     */
    function switchGoodsRuleType(v) {

        newGoodstype = v;
        oldGoodstype = oldGoodstype || v;

        saveDatagridData(oldGoodstype);
        oldGoodstype = newGoodstype;

        $datagrid.datagrid('loadData', []);
        $datagrid.datagrid('hideColumn', 'goodsid');
        $datagrid.datagrid('hideColumn', 'goodsname');
        $datagrid.datagrid('hideColumn', 'barcode');
        $datagrid.datagrid('hideColumn', 'brandid');
        $datagrid.datagrid('hideColumn', 'brandname');
        $datagrid.datagrid('hideColumn', 'supplierid');
        $datagrid.datagrid('hideColumn', 'suppliername');
        $datagrid.datagrid('hideColumn', 'goodssortname');
        $datagrid.datagrid('hideColumn', 'goodstype');

        if(v == 1) {

            $datagrid.datagrid('showColumn', 'goodsid');
            $datagrid.datagrid('showColumn', 'goodsname');
            $datagrid.datagrid('showColumn', 'barcode');
            $datagrid.datagrid('showColumn', 'brandid');
            $datagrid.datagrid('showColumn', 'brandname');
            $datagrid.datagrid('showColumn', 'supplierid');
            $datagrid.datagrid('showColumn', 'suppliername');
            $datagrid.datagrid('showColumn', 'goodssortname');
            $datagrid.datagrid('showColumn', 'goodstype');
            $datagrid.datagrid('loadData', goodsData);
            sortOrder();

        } else if(v == 2) {

            $datagrid.datagrid('showColumn', 'brandid');
            $datagrid.datagrid('showColumn', 'brandname');
            $datagrid.datagrid('loadData', brandData);
            sortOrder();

        } else if(v == 3) {

            $datagrid.datagrid('showColumn', 'goodssort');
            $datagrid.datagrid('showColumn', 'goodssortname');
            $datagrid.datagrid('loadData', goodssortData);
            sortOrder();

        } else if(v == 4) {

            $datagrid.datagrid('showColumn', 'goodstype');
            $datagrid.datagrid('loadData', goodstypeData);
            sortOrder();

        } else if(v == 5) {

            $datagrid.datagrid('showColumn', 'supplierid');
            $datagrid.datagrid('showColumn', 'suppliername');
            $datagrid.datagrid('loadData', supplierData);
            sortOrder();
        }
        return true;
    }

    /**
     * 保存明细数据
     */
    function saveDatagridData(v) {

        if(v == 1) {

            goodsData = $datagrid.datagrid('getRows');

        } else if(v == 2) {

            brandData = $datagrid.datagrid('getRows');

        } else if(v == 3) {

            goodssortData = $datagrid.datagrid('getRows');

        } else if(v == 4) {

            goodstypeData = $datagrid.datagrid('getRows');

        } else if(v == 5) {

            supplierData = $datagrid.datagrid('getRows');
        }
    }

    /**
     * 添加商品规则明细
     *
     * @returns {boolean}
     */
    function showAddDetailWindow() {

        var goodstype = $goodsruletype.val();

        // 按商品
        if(goodstype == 1) {

            $('#sales-dialog-distributionRulePage').dialog({
                title: '添加商品',
                width: 690,
                height: 450,
                closed: false,
                cache: false,
                modal: true,
                href: 'basefiles/distribution/distributionRuleGoodsAddPage.do',
                buttons: [
                    {
                        text: '添加',
                        iconCls: 'button-save',
                        handler: function (e) {

                            var rows = $('#oa-datagrid-distributionRuleGoodsAddPage').datagrid('getChecked');
                            var currentRows = $datagrid.datagrid('getRows');
                            currentRows = currentRows.concat(rows);
                            $datagrid.datagrid('loadData', currentRows);
                            sortOrder();

                            var exceptids = new Array();
                            for(var i in currentRows) {
                                exceptids.push(currentRows[i].goodsid);
                            }
                            $('[name=exceptids]').val(exceptids.join(','));
                            var param = $('#oa-form-distributionRuleGoodsAddPage').serializeJSON();
                            $('#oa-datagrid-distributionRuleGoodsAddPage').datagrid('load', param);
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'button-save',
                        handler: function (e) {
                            $('#sales-dialog-distributionRulePage').dialog('close');
                        }
                    }
                ]
            });

        // 按供应商规则
        } else if(goodstype == 2) {

            $('#sales-dialog-distributionRulePage').dialog({
                title: '添加品牌',
                width: 690,
                height: 450,
                closed: false,
                cache: false,
                modal: true,
                href: 'basefiles/distribution/distributionRuleBrandAddPage.do',
                buttons: [
                    {
                        text: '添加',
                        iconCls: 'button-save',
                        handler: function (e) {

                            var rows = $('#oa-datagrid-distributionRuleBrandAddPage').datagrid('getChecked');
                            var currentRows = $datagrid.datagrid('getRows');
                            var selectRows = new Array();
                            for(var i in rows) {

                                var row = rows[i];
                                var exist = false;
                                for(var j in currentRows) {
                                    var currentRow = currentRows[j];
                                    if(currentRow.brandid == row.id) {
                                        exist = true;
                                        break;
                                    }
                                }
                                if(!exist) {
                                    selectRows.push({brandid: row.id, brandname: row.name});
                                }
                            }

                            for(var k in selectRows) {
                                $datagrid.datagrid('appendRow', selectRows[k]);
                            }
                            sortOrder();
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'button-save',
                        handler: function (e) {
                            $('#sales-dialog-distributionRulePage').dialog('close');
                        }
                    }
                ]
            });

        // 按商品分类
        } else if(goodstype == 3) {

            $('#sales-dialog-distributionRulePage').dialog({
                title: '添加商品分类',
                width: 250,
                height: 350,
                closed: false,
                cache: false,
                modal: true,
                href: 'basefiles/distribution/distributionRuleGoodsSortAddPage.do',
                buttons: [
                    {
                        text: '添加',
                        iconCls: 'button-save',
                        handler: function (e) {

                            var checkedNodes = $.fn.zTree.getZTreeObj("sales-goodssort-distributionRuleGoodsSortAddPage").getCheckedNodes(true);
                            var rows = new Array();
                            var selectRows = new Array();
                            for(var i in checkedNodes) {

                                var checkedNode = checkedNodes[i];
                                var id = checkedNode.id;
                                for(var j in goodssortArr) {

                                    var item = goodssortArr[j];
                                    if(item.id && id && item.id == id) {
                                        rows.push({
                                            goodssort: item.id,
                                            goodssortname: item.name
                                        });
                                        break;
                                    }
                                }
                            }

                            var currentRows = $datagrid.datagrid('getRows');

                            for(var i in rows) {

                                var row = rows[i];
                                var exist = false;
                                for(var j in currentRows) {
                                    var currentRow = currentRows[j];
                                    if(currentRow.goodssort == row.goodssort) {
                                        exist = true;
                                        break;
                                    }
                                }
                                if(!exist) {
                                    selectRows.push({goodssort: row.goodssort, goodssortname: row.goodssortname});
                                }
                            }

                            for(var k in selectRows) {

                                $datagrid.datagrid('appendRow', selectRows[k]);
                            }

                            // 重新排序
                            var newRows = currentRows;
                            newRows.sort(function (o1, o2) {
                                return o1.goodssort.localeCompare(o2.goodssort);
                            });

                            $datagrid.datagrid('loadData', newRows);
                            sortOrder();
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'button-save',
                        handler: function (e) {
                            $('#sales-dialog-distributionRulePage').dialog('close');
                        }
                    }
                ]
            });

        // 按商品类型规则
        } else if(goodstype == 4) {

            $('#sales-dialog-distributionRulePage').dialog({
                title: '添加商品类型',
                width: 200,
                height: 350,
                closed: false,
                cache: false,
                modal: true,
                href: 'basefiles/distribution/distributionRuleGoodsTypeAddPage.do',
                buttons: [
                    {
                        text: '添加',
                        iconCls: 'button-save',
                        handler: function (e) {

                            var rows = $('#sales-datagrid-distributionRuleGoodsTypeAddPage').datagrid('getChecked');
                            var currentRows = $datagrid.datagrid('getRows');
                            for(var i in rows) {

                                var row = rows[i];
                                var exist = false;
                                for(var j in currentRows) {
                                    var currentRow = currentRows[j];
                                    if(currentRow.goodstype && row.code && currentRow.goodstype == row.code) {
                                        exist = true;
                                        break;
                                    }
                                }
                                if(!exist) {
                                    $datagrid.datagrid('appendRow', {goodstype: row.code, goodstypename: row.codename});
                                }
                                sortOrder();
                            }
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'button-save',
                        handler: function (e) {
                            $('#sales-dialog-distributionRulePage').dialog('close');
                        }
                    }
                ]
            });

        // 按供应商规则
        } else if(goodstype == 5) {

            $('#sales-dialog-distributionRulePage').dialog({
                title: '添加供应商',
                width: 450,
                height: 450,
                closed: false,
                cache: false,
                modal: true,
                href: 'basefiles/distribution/distributionRuleSupplierAddPage.do',
                buttons: [
                    {
                        text: '添加',
                        iconCls: 'button-save',
                        handler: function (e) {

                            var rows = $('#oa-datagrid-distributionRuleSupplierAddPage').datagrid('getChecked');
                            var currentRows = $datagrid.datagrid('getRows');
                            var selectRows = new Array();
                            for(var i in rows) {

                                var row = rows[i];
                                var exist = false;
                                for(var j in currentRows) {
                                    var currentRow = currentRows[j];
                                    if(currentRow.supplierid == row.id) {
                                        exist = true;
                                        break;
                                    }
                                }
                                if(!exist) {
                                    selectRows.push({supplierid: row.id, suppliername: row.name});
                                }
                            }

                            for(var k in selectRows) {

                                $datagrid.datagrid('appendRow', selectRows[k]);
                            }
                            sortOrder();
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'button-save',
                        handler: function (e) {
                            $('#sales-dialog-distributionRulePage').dialog('close');
                        }
                    }
                ]
            });

        }
        return true;
    }

    /**
     * 删除选中明细
     *
     * @returns {boolean}
     */
    function deleteDetails() {

        var rows = $datagrid.datagrid('getChecked');

        // 明细是否选中
        if(rows.length == 0) {
            $.messager.alert('提醒', '规则未选中！');
            return true;
        }

        $.messager.confirm('确认', '确定要删除选中的明细？', function(c) {

            if(c) {

                for(var i = rows.length - 1; i >= 0; i--) {

                    var row = rows[i];
                    var index = $datagrid.datagrid('getRowIndex', row);
                    $datagrid.datagrid('deleteRow', index);
                }
            }
        });

        return true;
    }

    /**
     * 保存客户分销规则
     *
     * @returns {boolean}
     */
    function saveRule(rows) {

        $form.form({
            onSubmit: function(param) {

                var flag = $form.form('validate');
                if(!flag) {

                    return false;
                }

                var rows = $datagrid.datagrid('getRows');
                if(rows == null || rows.length == 0) {
                    $.messager.alert('提醒', '商品规则明细为空！');
                    return false;
                }

                $detaillist.val(JSON.stringify(rows));

                var customertype = $customertype.val();

                var selectObjects = $widget.widget('getObjects');
                if(selectObjects != null && selectObjects.length > 20){
                    $.messager.alert('提醒', '客户群名称选择数量超过20，无法保存！');
                    return false;
                }

                var widgetVal = $widget.widget('getValue');
                if(customertype == 1) {
                    $customerid.val(widgetVal);
                } else if(customertype == 2) {
                    $pcustomerid.val(widgetVal);
                } else if(customertype == 3) {
                    $customersort.val(widgetVal);
                } else if(customertype == 4) {
                    $promotionsort.val(widgetVal);
                } else if(customertype == 5) {
                    $salesarea.val(widgetVal);
                } else if(customertype == 6) {
                    $creditrating.val(widgetVal);
                } else if(customertype == 7) {
                    $canceltype.val(widgetVal);
                }

                loading("提交中...");
            },
            success: function(data) {

                loaded();
                var json = $.parseJSON(data);
                if(json.flag) {

                    $.messager.alert('提醒', '保存成功。');
                    try {
                        var listWindow = tabsWindowURL('/basefiles/distribution/distributionRuleListPage.do');
                        listWindow.$('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                    } catch (e) {}
                    window.location.href = 'basefiles/distribution/distributionRulePage.do?type=edit&id=' + json.id;
                    return true;
                }

                $.messager.alert('提醒', '保存失败！');
            }
        }).submit();

        return true;
    }

    /**
     *
     * @returns {boolean}
     */
    function addRule() {

        top.addOrUpdateTab('basefiles/distribution/distributionRulePage.do?type=add', '分销规则新增');
        return true;
    }

    function sortOrder() {

        var goodsruletype = $('#sales-goodsruletype-distributionRuleEditPage').val() || 1;
        var data = $datagrid.datagrid('getRows');
        var colname = 'goodsid';

        if(goodsruletype == 1) {
            colname = 'goodsid'
        } else if(goodsruletype == 2) {
            colname = 'brandid';
        } else if(goodsruletype == 3) {
            colname = 'goodssort';
        } else if(goodsruletype == 4) {
            colname = 'goodstype';
        } else if(goodsruletype == 5) {
            colname = 'supplierid';
        }

        data = data.sort(function (o1, o2) {
            return o1[colname].localeCompare(o2[colname]);
        });

        $datagrid.datagrid('loadData', data);
    }

    -->

</script>
</body>
</html>
