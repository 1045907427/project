<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>新品登录申请单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">


    ///////////////////////////////////////////////

    // 验证客户编号是否被占用
    $.validator.addMethod('goodsidExisted', function(value, element, param) {

        value = value || '';
        if(value == '') {

            return this.optional(element) || true;
        }

        var data = {};

        $.ajax({
            type: 'post',
            url: 'oa/selectExistedGoodsid2.do',
            data: {goodsid: value, billid: '${param.id }'},
            dataType: 'json',
            async: false,
            success: function(json) {

                data = json;
            }
        });

        if(!data.flag) {

            return this.optional(element) || false;
        }

        return this.optional(element) || true;

    }, $.validator.format('商品编号已被占用'));
    ///////////////////////////////////////////////

    var goodsList = $.parseJSON('${goodsList }');
    localStorage.setItem('goodsList', '${goodsList }');

    $(document).on('pageshow', '#main', function(e) {

        refreshGoods(e);
        numberPrecision();

//        $('#oa-adddetail-oagoodsHandlePage').attr('href', 'oa/oagoodsDetailPage.do?random=' + new Date().getTime())
    });

    $(document).on('pageshow', '#oa-goodsdetail-oagoodsDetailPage,#main', function(e) {

        $('[readonly=readonly]').css({background: '#f8f8f8'});
    });

    $(function() {

        // 供应商
        $('#oa-supplierid2-oagoodsHandlePage').on('click', function() {

            androidWidget({
                type: 'supplierWidget',
                onSelect: 'selectSupplier'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-supplierid-oagoodsHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 计算毛利率
        $(document).on('change', '#oa-buytaxprice-oagoodsDetailPage', function(e) {

            <c:forEach items="${priceList}" varStatus="idx">
                computeProfitrate(${idx.index + 1});
            </c:forEach>
        });
        <c:forEach items="${priceList}" varStatus="idx">
            $(document).on('change', '#oa-price${idx.index + 1}-oagoodsDetailPage', function(e) {

                computeProfitrate(${idx.index + 1});
            });
        </c:forEach>

        // 计算箱体积
        $(document).on('change', '#oa-glength-oagoodsDetailPage,#oa-gwidth-oagoodsDetailPage,#oa-gheight-oagoodsDetailPage', computeVolume);

    });

    /**
     * 选择供应商
     */
    function selectSupplier(data) {

        $('#oa-supplierid2-oagoodsHandlePage').val(data.id + ':' + data.name);
        $('#oa-supplierid-oagoodsHandlePage').val(data.id);

        $('#oa-supplierid2-oagoodsHandlePage').blur();
        $('#oa-supplierid2-oagoodsHandlePage').change();
    }

    /**
     * 选择品牌
     */
    function selectBrand(data) {

        $('#oa-brandid2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-brandid-oagoodsDetailPage').val(data.id);

        $('#oa-brandid2-oagoodsDetailPage').blur();
        $('#oa-brandid2-oagoodsDetailPage').change();
    }

    /**
     * 选择商品分类
     */
    function selectGoodssort(data) {

        $('#oa-goodssort2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-goodssort-oagoodsDetailPage').val(data.id);

        $('#oa-goodssort2-oagoodsDetailPage').blur();
        $('#oa-goodssort2-oagoodsDetailPage').change();
    }

    /**
     * 选择主单位
     */
    function selectUnit(data) {

        $('#oa-unitid2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-unitid-oagoodsDetailPage').val(data.id);

        $('#oa-unitid2-oagoodsDetailPage').blur();
        $('#oa-unitid2-oagoodsDetailPage').change();
    }

    /**
     * 选择辅单位
     */
    function selectAuxunitid(data) {

        $('#oa-auxunitid2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-auxunitid-oagoodsDetailPage').val(data.id);

        $('#oa-auxunitid2-oagoodsDetailPage').blur();
        $('#oa-auxunitid2-oagoodsDetailPage').change();
    }

    /**
     * 选择仓库
     */
    function selectStorage(data) {

        $('#oa-storageid2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-storageid-oagoodsDetailPage').val(data.id);

        $('#oa-storageid2-oagoodsDetailPage').blur();
        $('#oa-storageid2-oagoodsDetailPage').change();
    }

    /**
     * 选择税种
     */
    function selectTaxtype(data) {

        $('#oa-taxtype2-oagoodsDetailPage').val(data.id + ':' + data.name);
        $('#oa-taxtype-oagoodsDetailPage').val(data.id);

        $('#oa-taxtype2-oagoodsDetailPage').blur();
        $('#oa-taxtype2-oagoodsDetailPage').change();
    }

    /**
     * 小数位截取
     */
    function numberPrecision() {

        $('span[precision]').each(function(index) {

            var text = $(this).text();

            var precision = $(this).attr('precision');

            if(text != undefined && text != '' && text != null) {

                text = parseFloat(text).toFixed(parseInt(precision));
            }

            $(this).text(text);
        });
    }

    /**
     * 计算毛利率
     */
    function computeProfitrate(idx) {

        var price = parseFloat($('#oa-price' + idx + '-oagoodsDetailPage').val() || '0.00');
        var cost = parseFloat($('#oa-buytaxprice-oagoodsDetailPage').val() || '0.00');

        if(cost != 0) {

            var rate = formatterMoney((price - cost) * 100 / price, 2);
            $('#oa-pricerate' + idx + '-oagoodsDetailPage').val(rate);
        }

        return true;
    }

    /**
     * 计算箱体积
     */
    function computeVolume() {

        var length = parseFloat($('#oa-glength-oagoodsDetailPage').val() || '0.00');
        var width = parseFloat($('#oa-gwidth-oagoodsDetailPage').val() || '0.00');
        var height = parseFloat($('#oa-gheight-oagoodsDetailPage').val() || '0.00');

        var volume = length * width * height;
        $('#oa-totalvolume-oagoodsDetailPage').val(volume);
        return true;
    }

    /**
     * 保存调价明细
     */
    function saveGoods(e) {

        var flag = $('#oa-form-oagoodsDetailPage').validate().form();

        if(!flag) {

            return flag;
        }

        <c:forEach items="${priceList}" varStatus="idx">
            computeProfitrate(${idx.index + 1});
        </c:forEach>

        var data = $('#oa-form-oagoodsDetailPage').serializeJSON();

        data.brandname = data.brandname || data.brandid2.substr(data.brandid.length + 1);
        data.goodssortname = data.goodssortname || data.goodssort2.substr(data.goodssort.length + 1);
        data.storagename = data.storagename || data.storageid2.substr(data.storageid.length + 1);
        data.taxname = data.taxname || data.taxtype2.substr(data.taxtype.length + 1);
        data.unitname = data.unitname || data.unitid2.substr(data.unitid.length + 1);
        data.auxunitname = data.auxunitname || data.auxunitid2.substr(data.auxunitid.length + 1);

        $('#oa-form-oagoodsDetailPage').form('clear');
        $('#oa-form-oagoodsDetailPage')[0].reset();
        var index = parseInt(localStorage.getItem('index'));

        if(index == -1) {
            goodsList.push(data);
        } else {
            goodsList[index] = data;
        }
        localStorage.setItem('goodsList', JSON.stringify(goodsList));
        localStorage.setItem('index', -1);

        refreshGoods();
        return true;
    }

    /**
     * 移除明细
     */
    function removeGoods(e) {

        var index = localStorage.getItem('index');

        if(index == -1) {

            return true;
        }

        goodsList.splice(index, 1);
        localStorage.setItem("goodsList", JSON.stringify(goodsList));
        return true;
    }

    /*
     * 刷新价格明细
     */
    function refreshGoods(e) {

        var goodsList = $.parseJSON(localStorage.getItem("goodsList"));

        var html = new Array();

        for (var i in goodsList) {

            var goods = goodsList[i];

            html.push('<li>');
            html.push('<a href="oa/oagoodsDetailPage.do?id=${param.id }" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100; padding: 3px;">');
            html.push('<table>');

            html.push('<tr>');
            html.push('<th>商品编号:</th>');
            html.push('<td><span>' + goods.goodsid + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>商品名称:</th>');
            html.push('<td><span>' + goods.goodsname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>品牌:</th>');
            html.push('<td><span>' + goods.brandname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>条形码:</th>');
            html.push('<td><span>' + goods.barcode + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>箱装条形码:</th>');
            html.push('<td><span>' + goods.boxbarcode + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>商品分类:</th>');
            html.push('<td><span>' + goods.goodssortname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>主单位:</th>');
            html.push('<td><span>' + goods.unitname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>辅单位:</th>');
            html.push('<td><span>' + goods.auxunitname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>规格型号:</th>');
            html.push('<td><span>' + goods.model + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>箱装量:</th>');
            html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + goods.boxnum + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>箱重:</th>');
            html.push('<td><span precision="2">' + goods.totalweight + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>长度(m):</th>');
            html.push('<td><span precision="2">' + goods.glength + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>宽度(m):</th>');
            html.push('<td><span precision="2">' + goods.gwidth + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>高度(m):</th>');
            html.push('<td><span precision="2">' + goods.gheight + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>箱体积(m<sup>3</sup>):</th>');
            html.push('<td><span precision="6">' + goods.totalvolume + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>仓库:</th>');
            html.push('<td><span>' + goods.storagename + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>最高采购价:</th>');
            html.push('<td><span precision="6">' + goods.buytaxprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>基准销售价:</th>');
            html.push('<td><span precision="6">' + goods.basesaleprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>税种:</th>');
            html.push('<td><span>' + goods.taxname + '</span></td>');
            html.push('</tr>');

            <c:forEach items="${priceList }" var="item" varStatus="idx">

                html.push('<tr>');
                html.push('<th><c:out value="${item.codename }" />:</th>');
                html.push('<td><span precision="6">' + goods.price${idx.index + 1} + '</span></td>');
                html.push('</tr>');

                var price = parseFloat(goods.price${idx.index + 1} || '0.00');
                var cost = parseFloat(goods.buytaxprice || '0.00');

                var rate = 0;
                if(cost != 0) {

                    rate = formatterMoney((price - cost) * 100 / price, 2);
                }
                goods.pricerate${idx.index + 1} = rate;

                html.push('<tr>');
                html.push('<th><c:out value="${item.codename }" />毛利率%:</th>');
                html.push('<td><span precision="2">' + goods.pricerate${idx.index + 1} + '</span></td>');
                html.push('</tr>');

            </c:forEach>

            html.push('<tr>');
            html.push('<th>产地:</th>');
            html.push('<td><span>' + goods.productfield + '</span></td>');
            html.push('</tr>');

            var shelflifeunit = '';
            if(goods.shelflifeunit == 1) {
                shelflifeunit = '天';
            } else if(goods.shelflifeunit == 2) {
                shelflifeunit = '周';
            } else if(goods.shelflifeunit == 3) {
                shelflifeunit = '月';
            } else if(goods.shelflifeunit == 4) {
                shelflifeunit = '年';
            }

            html.push('<tr>');
            html.push('<th>保质期:</th>');
            html.push('<td><span>' + goods.shelflife + shelflifeunit + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>说明:</th>');
            html.push('<td><span>' + goods.remark + '</span></td>');
            html.push('</tr>');

            html.push('</table>');
            html.push('</a>');
            html.push('</li>');
        }

        localStorage.setItem('goodsList', JSON.stringify(goodsList));

        $('#oa-goodsdetail-oagoodsHandlePage').find(':not(:eq(0))').remove();
        $('#oa-goodsdetail-oagoodsHandlePage').append(html.join(''));
        numberPrecision();

        try {

            $('#oa-goodsdetail-oagoodsHandlePage').listview('refresh');
        } catch(e) {}
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oagoodsHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oagoodsHandlePage").submit();
        var flag = $('#oa-form-oagoodsHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oagoodsHandlePage').validate({
            debug: false
        });

        $('#oa-gooddetail-oagoodsHandlePage').val(JSON.stringify(goodsList));

        $('#oa-form-oagoodsHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                <c:choose>
                    <c:when test="${empty goods or empty goods.id}">
                        url: 'oa/addGoods.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/editGoods.do',
                    </c:otherwise>
                </c:choose>
                success: function(data) {

                    var json = $.parseJSON(data);
                    if(json.flag) {

                        call(json.backid);
                    }
                },
                error: function(data) {

                    alertMsg(data);
                }
            });

            return false; //此处必须返回false，阻止常规的form提交

        }).submit();
    }
</script>
</head>
<body>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>处理工作[${param.processid }]</h1>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oagoodsHandlePage" method="post">
        <input type="hidden" name="goods.id" id="oa-id-oagoodsHandlePage" value="${param.id }"/>
        <input type="hidden" name="goodsBrandJSON" id="oa-gooddetail-oagoodsHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商<font color="#F00">*</font>
                        <textarea name="goods.supplierid2" id="oa-supplierid2-oagoodsHandlePage" class="required" readonly="readonly"><c:if test='${not empty supplier }'><c:out value='${goods.supplierid }'/>:<c:out value='${supplier.name }'/></c:if></textarea>
                        <input type="hidden" name="goods.supplierid" id="oa-supplierid-oagoodsHandlePage" value="${goods.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>进场商家数<font color="#F00">*</font>
                        <input type="number" precision="0" class="required" name="goods.customernum" id="oa-customernum-oaSupplierPayHandlePage" value="${goods.customernum }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>进场费用<font color="#F00">*</font>
                        <input type="number" precision="2" class="required" name="goods.costamount" id="oa-costamount-oaSupplierPayHandlePage" value="${goods.costamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="goods.remark" id="oa-remark-oagoodsHandlePage"><c:out value="${goods.remark }"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <a href="oa/oagoodsDetailPage.do" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-goodsdetail-oagoodsHandlePage" data-inset="false">
                        <li data-role="list-divider">商品明细</li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oagoodsHandlePage" data-role="footer" data-position="fixed">
        <c:choose>
            <c:when test="${empty param.taskid }">
                <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                    <jsp:param name="taskid" value="${process.taskid }"/>
                    <jsp:param name="id" value="${param.processid }"/>
                    <jsp:param name="sign" value="${param.sign }"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                    <jsp:param name="taskid" value="${param.taskid }"/>
                    <jsp:param name="id" value="${param.processid }"/>
                    <jsp:param name="sign" value="${param.sign }"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--<jsp:include page="oagoodsDetailPage.jsp">--%>
    <%--<jsp:param name="random" value=""/>--%>
<%--</jsp:include>--%>
<%--<%@include file="oa/oagoodsDetailPage.do"%>--%>
</body>
</html>
