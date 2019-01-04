<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则</title>
    <jsp:include page="/include.jsp"/>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-div-distributionRuleListPage" style="padding:0px; height:auto">
            <div class="buttonBG" id="sales-buttons-distributionRuleListPage" style="height:26px;"></div>
            <form id="sales-form-distributionRuleListPage">
                <input queryparam type="hidden" name="customerids" id="sales-customerids-distributionRuleListPage"/>
                <input queryparam type="hidden" name="pcustomerids" id="sales-pcustomerids-distributionRuleListPage"/>
                <input queryparam type="hidden" name="customersorts" id="sales-customersorts-distributionRuleListPage"/>
                <input queryparam type="hidden" name="promotionsorts" id="sales-promotionsorts-distributionRuleListPage"/>
                <input queryparam type="hidden" name="salesareas" id="sales-salesareas-distributionRuleListPage"/>
                <input queryparam type="hidden" name="creditratings" id="sales-creditratings-distributionRuleListPage"/>
                <input queryparam type="hidden" name="canceltypes" id="sales-canceltypes-distributionRuleListPage"/>

                <input queryparam type="hidden" name="goodsids" id="sales-goodsids-distributionRuleListPage"/>
                <input queryparam type="hidden" name="brandids" id="sales-brandids-distributionRuleListPage"/>
                <input queryparam type="hidden" name="goodssorts" id="sales-goodssorts-distributionRuleListPage"/>
                <input queryparam type="hidden" name="goodstypes" id="sales-goodstypes-distributionRuleListPage"/>
                <input queryparam type="hidden" name="supplierids" id="sales-supplierids-distributionRuleListPage"/>

                <table class="queryTable">
                    <tr>
                        <td class="len50 left">业务日期：</td>
                        <td class="left" style="width: 220px;">
                            <input type="text" class="len90 easyui-validatebox Wdate" id="sales-startdate-distributionRuleListPage" name="startdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off"/> 到
                            <input type="text" class="len90 easyui-validatebox Wdate" id="sales-enddate-distributionRuleListPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off"/>
                        </td>
                        <td class="len50 left">客 户 群：</td>
                        <td class="len160 left">
                            <select class="len150" id="sales-customertype-distributionRuleListPage" name="customertype">
                                <option></option>
                                <option value="1">单一客户</option>
                                <option value="2">总店</option>
                                <option value="3">客户分类</option>
                                <option value="4">促销分类</option>
                                <option value="5">销售区域</option>
                                <option value="6">信用等级</option>
                                <option value="7">核销方式</option>
                            </select>
                        </td>
                        <td class="len50 left">客户群名称：</td>
                        <td class="len160 left" id="sales-customerWidgetBox-distributionRuleListPage"><input type="text" id="sales-customerWidget-distributionRuleListPage" class="len150" autocomplete="off" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td>状态：</td>
                        <td>
                            <select id="sales-state-distributionRuleListPage" name="state" style="width: 205px;">
                                <option></option>
                                <option value="2">保存</option>
                                <option value="1">已启用</option>
                                <option value="0">已禁用</option>
                            </select>
                        </td>
                        <td>商品规则：</td>
                        <td>
                            <select id="sales-goodsruletype-distributionRuleListPage" name="goodsruletype" class="len150">
                                <option></option>
                                <option value="1">单一商品</option>
                                <option value="2">品牌</option>
                                <option value="3">商品分类</option>
                                <option value="4">商品类型</option>
                                <option value="5">供应商编码</option>
                            </select>
                        </td>
                        <td>规则详情：</td>
                        <td id="sales-goodsWidgetBox-distributionRuleListPage"><input type="text" id="sales-goodsWidget-distributionRuleListPage" class="len150" autocomplete="off" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td colspan="3" class="right">
                            <a href="javascript:void(0);" id="sales-queay-distributionRuleListPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="sales-reset-distributionRuleListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-distributionRuleListPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">

    <!--

    $(function () {

        $('#sales-buttons-distributionRuleListPage').buttonWidget({
            initButton: [
                <security:authorize url="/basefiles/distribution/addDistributionRule.do">
                {
                    type: 'button-add',
                    handler: addRule
                },
                </security:authorize>
                <security:authorize url="/basefiles/distribution/editDistributionRule.do">
                {
                    type: 'button-edit',
                    handler: editRule
                },
                </security:authorize>
                <security:authorize url="/basefiles/distribution/deleteDistributionRule.do">
                {
                    type: 'button-delete',
                    handler: deleteRules
                },
                </security:authorize>
                <security:authorize url="/basefiles/distribution/enableDistributionRule.do">
                {
                    type: 'button-open',
                    handler: enableRules
                },
                </security:authorize>
                <security:authorize url="/basefiles/distribution/disableDistributionRule.do">
                {
                    type: 'button-close',
                    handler: disableRules
                },
                </security:authorize>
                {}
            ],
            buttons: [{}],
            model: 'base',
            type: 'list',
            tname: 't_base_sales_distribution_rule'
        });

        $('#sales-datagrid-distributionRuleListPage').datagrid({
            columns: [[
                {field: 'checkbox', checkbox: true},
                {field: 'id', title: '编号', width: 135, align: 'left'},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left'},
                {field: 'customertype', title: '客户群', width: 80, align: 'left',
                    formatter: function(value, row, index){
                        if(value == 1) {
                            return '单一客户';
                        } else if(value == 2) {
                            return '总店客户';
                        } else if(value == 3) {
                            return '客户分类';
                        } else if(value == 4) {
                            return '促销分类';
                        } else if(value == 5) {
                            return '销售区域';
                        } else if(value == 6) {
                            return '信用等级';
                        } else if(value == 7) {
                            return '核销方式';
                        }
                    }
                },
                {field: 'customertypename', title: '客户群名称', width: 180, align: 'left',
                    formatter: function(value, row, index){
                        return row.customername
                                || row.pcustomername
                                || row.customersortname
                                || row.promotionsortname
                                || row.salesareaname
                                || row.creditratingname
                                || row.canceltypename;
                    }
                },
                {field: 'goodsruletypename', title: '商品规则', width: 90, align: 'left',
                    formatter: function(value, row, index){
                        if(row.goodsruletype == 1) {
                            return '单一商品';
                        } else if(row.goodsruletype == 2) {
                            return '品牌';
                        } else if(row.goodsruletype == 3) {
                            return '商品分类';
                        } else if(row.goodsruletype == 4) {
                            return '商品类型';
                        } else if(row.goodsruletype == 5) {
                            return '供应商';
                        }
                        return '其他';
                    }
                },
                {field: 'canbuy', title: '可购买', width: 60, align: 'left',
                    formatter: function(value, row, index){
                        return getSysCodeName('yesorno', value);
                    }
                },
                {field: 'state', title: '状态', width: 60, align: 'left',
                    formatter: function(value, row, index){
                        return getSysCodeName('state', value);
                    }
                },
                {field: 'remark', title: '备注', width: 180, align: 'left'},
                {field: 'addusername', title: '制单人', width: 75, align: 'left'},
                {field: 'addtime', title: '制单时间', width: 130, align: 'left'}
            ]],
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#sales-div-distributionRuleListPage',
            url: 'basefiles/distribution/selectDistributionRulePageData.do',
            onUncheckAll: function () {
                $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-edit');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-delete');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-open');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-close');
            },
            onCheckAll: function () {
                $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-edit');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-delete');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-open');
                $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-close');
            },
            onClickRow: function () {
                var row = $('#sales-datagrid-distributionRuleListPage').datagrid('getChecked');
                if(row == null || row.length == 0) {
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-edit');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-delete');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-open');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('disableButton', 'button-close');
                } else {
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-edit');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-delete');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-open');
                    $('#sales-buttons-distributionRuleListPage').buttonWidget('enableButton', 'button-close');
                }
                return true;
            },
            onDblClickRow: function(index, row) {
                viewRule(row);
            },
            <security:authorize url="/basefiles/distribution/editDistributionRule.do">
            onDblClickRow: function(index, row) {
                editRule(row);
            }
            </security:authorize>
        }).datagrid('columnMoving');

        $('#sales-customertype-distributionRuleListPage').change(function (e) {
            switchCustomerType($(this).val());
        });

        $('#sales-goodsruletype-distributionRuleListPage').change(function (e) {
            switchGoodsRuleType($(this).val());
        });

        // 查询
        $('#sales-queay-distributionRuleListPage').click(function (e) {

            $('#sales-form-distributionRuleListPage > [queryparam]').val('');
            var customertype = $('#sales-customertype-distributionRuleListPage').val();
            if(customertype == 1) {
                $('#sales-customerids-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 2) {
                $('#sales-pcustomerids-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 3) {
                $('#sales-customersorts-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 4) {
                $('#sales-promotionsorts-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 5) {
                $('#sales-salesareas-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 6) {
                $('#sales-creditratings-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            } else if(customertype == 7) {
                $('#sales-canceltypes-distributionRuleListPage').val($('#sales-customerWidget-distributionRuleListPage').widget('getValue'));
            }

            var goodsruletype = $('#sales-goodsruletype-distributionRuleListPage').val();
            if(goodsruletype == 1) {
                $('#sales-goodsids-distributionRuleListPage').val($('#sales-goodsWidget-distributionRuleListPage').widget('getValue'));
            } else if(goodsruletype == 2) {
                $('#sales-brandids-distributionRuleListPage').val($('#sales-goodsWidget-distributionRuleListPage').widget('getValue'));
            } else if(goodsruletype == 3) {
                $('#sales-goodssorts-distributionRuleListPage').val($('#sales-goodsWidget-distributionRuleListPage').widget('getValue'));
            } else if(goodsruletype == 4) {
                $('#sales-goodstypes-distributionRuleListPage').val($('#sales-goodsWidget-distributionRuleListPage').widget('getValue'));
            } else if(goodsruletype == 5) {
                $('#sales-supplierids-distributionRuleListPage').val($('#sales-goodsWidget-distributionRuleListPage').widget('getValue'));
            }

            var param = $('#sales-form-distributionRuleListPage').serializeJSON();
            $('#sales-datagrid-distributionRuleListPage').datagrid('load', param);
        });

        // 重置
        $('#sales-reset-distributionRuleListPage').click(function (e) {

            $('#sales-form-distributionRuleListPage')[0].reset();
            $('#sales-form-distributionRuleListPage > [queryparam]').val('');
            switchCustomerType();
            switchGoodsRuleType();
            $('#sales-queay-distributionRuleListPage').click();
        });
    });

    /**
     * 新增规则
     * @returns {boolean}
     */
    function addRule() {

        top.addOrUpdateTab('basefiles/distribution/distributionRulePage.do?type=add', '分销规则新增');
        return true;
    }

    /**
     * 修改规则
     * @returns {boolean}
     */
    function editRule(row) {

        row = row || $('#sales-datagrid-distributionRuleListPage').datagrid('getSelected');
        if(row == null) {
            $.messager.alert('提醒', '请选择规则！');
            return false;
        }
        top.addOrUpdateTab('basefiles/distribution/distributionRulePage.do?type=edit&id=' + row.id, '分销规则修改');
        return true;
    }

    /**
     * 修改规则
     * @returns {boolean}
     */
    function viewRule(row) {

        row = row || $('#sales-datagrid-distributionRuleListPage').datagrid('getSelected');
        if(row == null) {
            $.messager.alert('提醒', '请选择规则！');
            return false;
        }
        top.addOrUpdateTab('basefiles/distribution/distributionRulePage.do?type=view&id=' + row.id, '分销规则查看');
        return true;
    }

    /**
     * 删除规则
     * @returns {boolean}
     */
    function deleteRules() {

        var rows = $('#sales-datagrid-distributionRuleListPage').datagrid('getChecked');
        if(rows == null || rows.length == 0) {
            $.messager.alert('提醒', '请选择规则！');
            return false;
        }

        $.messager.confirm('确认', '确定要删除选中的规则？', function (c) {

            if(c) {

                var idArr = new Array();
                for(var i in rows) {
                    idArr.push(rows[i].id);
                }

                loading('正在删除...');
                $.ajax({
                    type: 'post',
                    url: 'basefiles/distribution/deleteDistributionRule.do',
                    data: {ids: idArr.join(',')},
                    dataType: 'json',
                    success: function (json) {

                        loaded();
                        if(json.fail == 0 && !json.msg) {
                            $.messager.alert('提醒', '删除成功。');
                            $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                            return true;
                        }

                        $.messager.alert('提醒', '删除成功： ' + (json.success || 0) + ' 条。<br/>删除失败： ' + (json.fail || 0) + ' 条，可能由于以下原因导致：<br/>' + json.msg);
                        $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                        return true;
                    },
                    error: function (e) {
                        loaded();
                        $.messager.alert('错误', '删除出错，如果一直出现该提示，请联系系统管理员！');
                    }
                })
            }
        });

        return true;
    }

    /**
     * 启用规则
     * @returns {boolean}
     */
    function enableRules() {

        var rows = $('#sales-datagrid-distributionRuleListPage').datagrid('getChecked');
        if(rows == null || rows.length == 0) {
            $.messager.alert('提醒', '请选择规则！');
            return false;
        }

        $.messager.confirm('确认', '确定要启用选中的规则？', function (c) {

            if(c) {

                var idArr = new Array();
                for(var i in rows) {
                    idArr.push(rows[i].id);
                }

                loading('正在启用...');
                $.ajax({
                    type: 'post',
                    url: 'basefiles/distribution/enableDistributionRule.do',
                    data: {ids: idArr.join(',')},
                    dataType: 'json',
                    success: function (json) {

                        loaded();
                        if(json.fail == 0 && !json.msg) {
                            $.messager.alert('提醒', '启用成功。');
                            $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                            return true;
                        }

                        $.messager.alert('提醒', '启用成功： ' + (json.success || 0) + ' 条。<br/>启用失败： ' + (json.fail || 0) + ' 条，可能由于以下原因导致：<br/>' + json.msg);
                        $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                        return true;
                    },
                    error: function (e) {
                        loaded();
                        $.messager.alert('错误', '启用出错，如果一直出现该提示，请联系系统管理员！');
                    }
                })
            }
        });

        return true;
    }

    /**
     * 禁用规则
     * @returns {boolean}
     */
    function disableRules() {

        var rows = $('#sales-datagrid-distributionRuleListPage').datagrid('getChecked');
        if(rows == null || rows.length == 0) {
            $.messager.alert('提醒', '请选择规则！');
            return false;
        }

        $.messager.confirm('确认', '确定要禁用选中的规则？', function (c) {

            if(c) {

                var idArr = new Array();
                for(var i in rows) {
                    idArr.push(rows[i].id);
                }

                loading('正在禁用...');
                $.ajax({
                    type: 'post',
                    url: 'basefiles/distribution/disableDistributionRule.do',
                    data: {ids: idArr.join(',')},
                    dataType: 'json',
                    success: function (json) {

                        loaded();
                        if(json.fail == 0 && !json.msg) {
                            $.messager.alert('提醒', '禁用成功。');
                            $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                            return true;
                        }

                        $.messager.alert('提醒', '禁用成功： ' + (json.success || 0) + ' 条。<br/>禁用失败： ' + (json.fail || 0) + ' 条，可能由于以下原因导致：<br/>' + json.msg);
                        $('#sales-datagrid-distributionRuleListPage').datagrid('reload');
                        return true;
                    },
                    error: function (e) {
                        loaded();
                        $.messager.alert('错误', '禁用出错，如果一直出现该提示，请联系系统管理员！');
                    }
                })
            }
        });

        return true;
    }

    /**
     * 切换客户群
     * @param v
     * @returns {boolean}
     */
    function switchCustomerType(v) {

        v = v || '';
        $('#sales-customerWidgetBox-distributionRuleListPage').html('<input type="text" id="sales-customerWidget-distributionRuleListPage" class="len150" autocomplete="off" readonly="readonly"/>');
        if(v) {
            $('#sales-customerWidget-distributionRuleListPage').removeAttr('readonly');
        }

        if(v == 1) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_SALES_CUSTOMER',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 2) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 3) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 4) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_SYS_CODE_PROMOTIONSORT',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 5) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RT_T_BASE_SALES_AREA',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 6) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_SYS_CODE_CREDITRATING',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(v == 7) {
            $('#sales-customerWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_SYS_CODE_CANCELTYPE',
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
     * @param goodsruletype
     */
    function switchGoodsRuleType(goodsruletype) {

        goodsruletype = goodsruletype || '';
        $('#sales-goodsWidgetBox-distributionRuleListPage').html('<input type="text" id="sales-goodsWidget-distributionRuleListPage" class="len150" autocomplete="off" readonly="readonly"/>');

        if(goodsruletype) {
            $('#sales-goodsWidget-distributionRuleListPage').removeAttr('readonly');
        }

        if(goodsruletype == 1) {
            $('#sales-goodsWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_GOODS_INFO',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(goodsruletype == 2) {
            $('#sales-goodsWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_GOODS_BRAND',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(goodsruletype == 3) {
            $('#sales-goodsWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_GOODS_WARESCLASS',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(goodsruletype == 4) {
            $('#sales-goodsWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_GOODS_TYPE',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        } else if(goodsruletype == 5) {
            $('#sales-goodsWidget-distributionRuleListPage').widget({
                referwid: 'RL_T_BASE_BUY_SUPPLIER',
                ishead: true,
                singleSelect: false,
                onlyLeafCheck: false,
                width: 150
            });
        }
    }

    -->

</script>
</body>
</html>
