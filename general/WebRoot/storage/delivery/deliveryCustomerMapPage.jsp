<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>配送单查看/修改地图页面</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        html {
            height: 100%;
        }

        body {
            height: 100%;
            width: 100%;
            margin: 0px;
            padding: 0px;
        }

        #container {
            height: 100%;
        }

        .map-detail-table {
            border-collapse: collapse;
            border: 1px solid #000;
            width: 100%;
        }

        .map-detail-table th, td {
            border: 1px solid #000;
            line-height: 28px;
            text-indent: 5px;
        }

        .statistic-table {
            border-collapse: collapse;
            border: 1px solid #000;
            width: 100%;
        }

        .statistic-table th,td {
            border: 1px solid #000;
            /*line-height: 28px;*/
            text-indent: 5px;
        }

    </style>
</head>
<body>
<div id="container">
    <div style="margin: 180px auto; text-align: center; color: #F00;">
        地图加载中...
    </div>
</div>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qYlCsNIpqrWPz7MHLjCeTLy5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />

<script type="text/javascript">

    <!--
    // 客户坐标集
    var customerLocations = {};

    // 已标记客户
    var customerMarkers = new Array();

    var startMarker = null;

    // icon样式
    // 选中标记
    var markedIcon = new BMap.Icon("image/map/map-location-marked.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 起点标记
    var startIcon = new BMap.Icon("image/map/map-start-point-small-pink.png", new BMap.Size(17, 46), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    
    var map = new BMap.Map("container", {enableMapClick: false});

    $(function(){

        <c:forEach items="${locations }" var="location" varStatus="status">

            customerLocations['${location.customerid }'] = {
                customerid: '${location.customerid }',
                latitude: '${location.location }'.split(',')[0],
                longitude: '${location.location }'.split(',')[1],
                name: '<c:out value="${location.name }"/>',
                salesarea: '<c:out value="${location.salesarea }"/>',
                customersort: '<c:out value="${location.customersort }"/>',
                salesusername: '<c:out value="${location.salesusername }"/>'
            };

        </c:forEach>

        // 中心点
        map.centerAndZoom(new BMap.Point(${center }), 9);

        // 可缩放
        map.enableScrollWheelZoom(true);

        <c:if test="${not empty delivery.startpoint }">
            addStartMarker('${delivery.startpoint }');
        </c:if>

        // 选择列表中的客户
        var customerBills = parent.getCustomerBills();
        initSelectedCustomerMarker(customerBills);

    });

    /**
     * 标注客户
     *
     * @param customerid    客户编号
     * @param bills         单据
     * @param check         boolean 该客户下是否全部选中
     * @param inline        boolean 是否线路内
     * @param empty         boolean 是否未选中，选中单据数量为0
     */
    function markCustomer(customerid, bills) {

        for(var i in customerLocations) {

            var customerLocation = customerLocations[i];
            if (customerid == customerLocation.customerid) {

                var icon = markedIcon;

                // 创建点坐标
                var point = new BMap.Point(customerLocation.latitude, customerLocation.longitude);
                var customerMarker = new BMap.Marker(point, {icon: icon});  // 创建标注
                var title = customerLocation.customerid + ': ' + customerLocation.name;
                customerMarker.setTitle(title);

                customerMarker.addEventListener("click", function(e){

                    var html = new Array();
                    html.push('<div style="overflow-y: scroll; height: 172px;">');
                    html.push('<table class="map-detail-table">');
                    html.push('<tr style="background-color: #dedede;">');
//                    html.push('<th>#</th>');
//                    html.push('<th><input type="checkbox" name="' + customerid + '" index="0" ' + (check ? 'checked="checked"' : '') + '/></th>');
                    html.push('<th>发货单编号</th>');
                    html.push('<th>订单编号</th>');
                    html.push('<th>业务日期</th>');
//                    html.push('<th>客户</th>');
                    html.push('<th>发货金额</th>');
                    html.push('<th>重量(kg)</th>');
                    html.push('<th>体积(m<sup>3</sup>)</th>');
                    html.push('<th>箱数</th>');
//                    html.push('<th>状态</th>');
//                    html.push('<th>来源</th>');
//                    html.push('<th>线路内</th>');
                    html.push('<th>客户业务员</th>');
                    html.push('</tr>');

                    var index = 1;
                    for(var i in bills) {

                        var bill = bills[i];
                        html.push('<tr style="">');
//                        html.push('<td>' + (index++) + '</td>');
//                        html.push('<td><input type="checkbox" name="' + customerid + '" index="' + (index++) + '"' + (bill.check ? 'checked="checked"' : '') + ' billid="' + bill.saleoutid + '"/></td>');
                        html.push('<td>' + bill.saleoutid + '</td>');
                        html.push('<td>' + bill.orderid + '</td>');
                        html.push('<td>' + bill.businessdate + '</td>');
//                        html.push('<td>' + bill.customerid + '<br/>' + bill.customername + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.salesamount) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.weight) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.volume, 4) + '</td>');
                        html.push('<td class="right">' + formatterBigNumNoLen(bill.boxnum) + '</td>');
//                        html.push('<td>' + getSysCodeName('status', bill.status) + '</td>');
//                        html.push('<td>' + getBillName(bill.deliverytype)  + '</td>');
//                        html.push('<td>' + (inline ? '是' : '否')  + '</td>');
                        html.push('<td>' + bill.salesusername + '</td>');
                        html.push('</tr>');
                    }

                    html.push('</table>');
                    html.push('</div>');

                    var info = html.join('');
                    var infoWindow = new BMap.InfoWindow(info, {
                        width: 650,     		// 信息窗口宽度
                        height: 200,     		// 信息窗口高度
                        title: '(' + e.target.getTitle().getCustomerid() + ')' + customerLocations[e.target.getTitle().getCustomerid()].name,	// 信息窗口标题
                        enableMessage: false	//设置允许信息窗发送短息
                    });
                    // open事件
//                    infoWindow.addEventListener('open', function(e) {
//
//                        // 解除绑定
//                        $('[name=' + customerid + ']').off('click');
//
//                        // 绑定
//                        $('[name=' + customerid + ']').click(function(e) {
//
//                            var index = $(this).attr('index');
//                            var check = $(this).is(':checked');
//
//                            if(index == '0') {
//                                parent.checkCustomer(customerid, check);
//                            }
//
//                            // 头部checkbox状态
//                            if(check && index == '0') {
//
//                                $('[type=checkbox][name=' + customerid + ']').attr('checked', 'checked');
//                                return true;
//                            } else if(!check && index == '0') {
//
//                                $('[type=checkbox][name=' + customerid + ']').removeAttr('checked');
//                                return true;
//                            }
//
//                            var billid = $(this).attr('billid');
//                            parent.checkBill(billid, check);
//
//                            // 行checkbox状态(行是否全选)
//                            var allcheck = true;
//                            $('[name=' + customerid + ']:gt(0)').each(function(index, item) {
//                                allcheck = allcheck && $(this).is(':checked');
//                            });
//
//                            // 全选
//                            if(allcheck) {
//                                $('[name=' + customerid + ']:eq(0)').attr('checked', 'checked');
//                                return true;
//                            }
//
//                            // 非全选
//                            $('[name=' + customerid + ']:eq(0)').removeAttr('checked');
//                            return true;
//                        });
//                    });
                    // close事件
                    infoWindow.addEventListener('close', function(e) {

                        removeCustomerMarkers();
                        var customerBills = parent.getCustomerBills();
                        initSelectedCustomerMarker(customerBills);
                    });
                    map.openInfoWindow(infoWindow, new BMap.Point(customerLocations[e.target.getTitle().getCustomerid()].latitude, customerLocations[e.target.getTitle().getCustomerid()].longitude)); //开启信息窗口

                });

                // 将标注添加到地图中
                map.addOverlay(customerMarker);
                customerMarkers.push(customerMarker);
            }
        }
    }

    /**
     * 清除所有客户标记
     */
    function removeCustomerMarkers() {

        for(var i in customerMarkers) {

            map.removeOverlay(customerMarkers[i]);
        }

        return true;
    }

    /**
     * 标记点是否在多边形内部
     *
     * @param point1 目标点
     * @param points1 多边形Polygon所有顶点，可通过Polygon.getPath()方法返回
     * @returns {boolean}
     * @author limin
     * @date Sep 7, 2016
     */
    function isPointInPolygon(point1, points1) {

        var crossLeft = 0;       // 交点数（位于左边）
        var crossRight = 0;      // 交点数（位于右边）

        // 将坐标*1e7倍进行计算，否则浮点数运算可能会有误差
        var point = new BMap.Point(point1.lng * 1e7, point1.lat * 1e7);
        var points = new Array();
        for(var i in points1) {

            var old = points1[i];
            var temp = new BMap.Point(old.lng * 1e7, old.lat * 1e7);
            points.push(temp);
        }

        // 遍历所有points
        for(var i = 0; i < points.length; i++) {

            var point1 = points[i];
            var point2;
            if(i == points.length - 1) {
                point2 = points[0];
            } else {
                point2 = points[i + 1];
            }

            // p1p2 与 y=p0.lat平行
            if (point1.lat == point2.lat) {

                continue;
            }

            // 交点在p1p2延长线上
            if (point.lat < min(point1.lat, point2.lat)) {

                continue;
            }

            // 交点在p1p2延长线上
            if (point.lat >= max(point1.lat, point2.lat)) {

                continue;
            }

            // 交点位于右边
            if(point.lng < min(point1.lng, point2.lng)) {

                crossRight++;
                continue;
            }

            // 交点位于左边
            if(point.lng > max(point1.lng, point2.lng)) {

                crossLeft++;
                continue;
            }

            // 焦点经度lng
            var crossX = (point.lat - point1.lat) * (point2.lng - point1.lng) / (point2.lat - point1.lat) + point1.lng;
            crossX = parseFloat(crossX.toFixed(2));
            if(crossX > point.lng) {
                crossRight++;
                continue;
            } else {
                crossLeft++;
                continue;
            }

        }

        // 左右焦点数都为奇数
        return (crossLeft % 2 == 1) && (crossRight % 2 == 1);
    }

    /**
     * 求两数中的较小值
     *
     * @param x
     * @param y
     * @returns {Number}
     * @author limin
     * @date Sep 7, 2016
     */
    function min(x, y) {

        var xFloat = parseFloat(x);
        var yFloat = parseFloat(y);

        if(xFloat < yFloat) {

            return xFloat;
        }

        return yFloat;
    }

    /**
     * 求两数中的较大值
     *
     * @param x
     * @param y
     * @returns {Number}
     * @author limin
     * @date Sep 7, 2016
     */
    function max(x, y) {

        var xFloat = parseFloat(x);
        var yFloat = parseFloat(y);

        if(xFloat > yFloat) {

            return xFloat;
        }

        return yFloat;
    }

    /**
     * 标记点是否在圆形内部
     *
     * @param point 目标点
     * @param circle 圆形覆盖物
     * @returns {boolean}
     * @author limin
     * @date Sep 7, 2016
     */
    function isPointInCircle(point, circle) {

        var center = circle.getCenter();

        var distance = map.getDistance(point, circle.getCenter()).toFixed(2);
        var radius = circle.getRadius();

        return parseFloat(distance) <parseFloat(radius);
    }

    /**
     * 添加配送起点标志
     *
     * @returns {boolean}
     */
    function addStartMarker(strLocation) {

        // 已设置起点标志
        if(startMarker != null) {

            $.messager.alert('提醒', '当前地图已设置配送起点，请拖动配送起点进行修改位置。');
            return true;
        }

        console.log(strLocation)

        var start = null;

        if(strLocation) {

            var lng = strLocation.split(",")[0];
            var lat = strLocation.split(",")[1];
            start =  new BMap.Point(lng, lat);

        } else {

            var bs = map.getBounds();       //获取可视区域
            var bssw = bs.getSouthWest();   //可视区域左下角
            var bsne = bs.getNorthEast();   //可视区域右上角
            start =  new BMap.Point((bssw.lng + bsne.lng) / 2, (bssw.lat + bsne.lat) / 2);
        }

        parent.$('#storage-startpoint-deliveryEditPage').val(start.lng + ',' + start.lat);

        startMarker = new BMap.Marker(start, {icon: startIcon });  // 创建标注

        startMarker.addEventListener("click", function(e){

            map.openInfoWindow(new BMap.InfoWindow('<c:out value="${companyName }" />', {
                width: 100,     		// 信息窗口宽度
                height: 60,     		// 信息窗口高度
                title: '配送起点',	    // 信息窗口标题
                enableMessage: false	//设置允许信息窗发送短息
            }), start); //开启信息窗口
        });

        // 起始点位置拖动时，重新帮点click事件，防止弹出的信息窗口出现在原来的位置
        startMarker.addEventListener("dragend", function(e){

            var newLocation = startMarker.getPosition();
            parent.$('#storage-startpoint-deliveryEditPage').val(newLocation.lng + ',' + newLocation.lat);
            startMarker.removeEventListener('click');
            startMarker.addEventListener("click", function(e){

                map.openInfoWindow(new BMap.InfoWindow('<c:out value="${companyName }" />', {
                    width: 100,     		// 信息窗口宽度
                    height: 110,     		// 信息窗口高度
                    title: '配送起点',	    // 信息窗口标题
                    enableMessage: false	//设置允许信息窗发送短息
                }), newLocation); //开启信息窗口
            });
        });

        map.addOverlay(startMarker);              // 将标注添加到地图中
        startMarker.enableDragging();

        return true;
    }

    /**
     * 初始化客户标记
     *
     * @returns {boolean}
     */
    function initSelectedCustomerMarker(customerBills) {

        for(var key in customerBills) {

            var customerid = key;
            var bills = customerBills[key].bills;
            markCustomer(customerid, bills, null, null, null);
        }

        return true;
    }

    String.prototype.getCustomerid = function() {

        if(this.indexOf(':')) {
            return this.split(':')[0];
        }
        return '';
    };

    -->
</script>
</body>
</html>
