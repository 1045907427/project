<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>配送单新增地图页面</title>
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

        .map-detail-table tr {
            cursor: pointer;
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
    // 线路坐标集
    var lineLocations = {};
    // 客户线路对应关系
    var customerLineMap = {};
    // 线路单据
    var lineBills = {};

    // 已标记线路
    var lineMarkers = new Array();
    // 已标记客户
    var customerMarkers = new Array();
    var startMarker = null;
    var drawedOverlays = new Array();
    var statisticBar = null;

    // icon样式
    // 选中标记（线路内未选中）
    var inlineUnselectedIcon = new BMap.Icon("image/map/map-inline-unselected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记（线路内选中）
    var inlineSelectedIcon = new BMap.Icon("image/map/map-inline-selected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记（线路外未选中）
    var outlineUnselectedIcon = new BMap.Icon("image/map/map-outline-unselected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记（线路外选中）
    var outlineSelectedIcon = new BMap.Icon("image/map/map-outline-selected.png", new BMap.Size(16, 34), {
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

        <c:forEach items="${lines }" var="line" varStatus="status">
            lineLocations['${line.id }'] = {
                id: '${line.id }',
                name: '${line.name }',
                defaultpoint: '${line.defaultpoint }'
            };
        </c:forEach>

        <c:forEach items="${lineCustomers }" var="lineCustomer" varStatus="status">
            if(customerLineMap['${lineCustomer.customerid }'] == undefined) {
                customerLineMap['${lineCustomer.customerid }'] = {
                    customerid: '${lineCustomer.customerid }',
                    lines: new Array()
                }
            }
            customerLineMap['${lineCustomer.customerid }'].lines.push({
                lineid: '${lineCustomer.lineid }',
                linename: '<c:out value="${lineCustomer.linename}"/>',
                defaultpoint: '${lineCustomer.defaultpoint }'
            });

        </c:forEach>

        // 中心点
        map.centerAndZoom(new BMap.Point(${center }), 9);

        // 可缩放
        map.enableScrollWheelZoom(true);

        <c:if test="${param.type eq 'edit'}">
        // 覆盖物工具
        ~(function(){

            var overlays = [];
            var overlaycomplete = function(e){

                overlays.push(e.overlay);
            };

            var styleOptions = {
                strokeColor: "red",    //边线颜色。
                fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                strokeWeight: 1,       //边线的宽度，以像素为单位。
                strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                fillOpacity: 0.3,      //填充的透明度，取值范围0 - 1。
                strokeStyle: 'solid' //边线的样式，solid或dashed。
            };

            //实例化鼠标绘制工具
            var drawingManager = new BMapLib.DrawingManager(map, {
                isOpen: false, //是否开启绘制模式
                enableDrawingTool: true, //是否显示工具栏
                drawingToolOptions: {
                    anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                    offset: new BMap.Size(5, 5), //偏离值
                    scale: 0.8, //工具栏缩放比例,
                    drawingModes: [/*BMAP_DRAWING_MARKER, */BMAP_DRAWING_CIRCLE,/*BMAP_DRAWING_POLYLINE,*/BMAP_DRAWING_POLYGON/**/,BMAP_DRAWING_RECTANGLE]
                },
                circleOptions: styleOptions, //圆的样式
                polylineOptions: styleOptions, //线的样式
                polygonOptions: styleOptions, //多边形的样式
                rectangleOptions: styleOptions //矩形的样式
            });

            // 添加鼠标绘制工具监听事件，用于获取绘制结果
            // 多边形覆盖物绘制完成触发
            drawingManager.addEventListener('polygoncomplete', function(polygon) {

                // 将绘制的覆盖物替换成普通覆盖物
                clearAll();
                map.addOverlay(polygon);
                drawedOverlays.push(polygon);

                for(var i = 0; i < customerMarkers.length; i++) {

                    var marker = customerMarkers[i];
                    var position = marker.getPosition();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                        parent.checkCustomer(marker.getTitle().getId(), true);
                    }
                }

                for(var i = 0; i < lineMarkers.length; i++) {

                    var marker = lineMarkers[i];
                    var position = marker.getPosition();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(!inPolygon) {

                        continue;
                    }

                    var lineid = marker.getTitle().getId();
                    var lineBill = lineBills[lineid];
                    for(var j in lineBill.bills) {

                        var customerid = lineBill.bills[j].customerid;
                        parent.checkCustomer(customerid, true);
                    }
                }

                removeLineMarkers();
                removeCustomerMarkers();
                initSelectedCustomerMarker(parent.getCustomerBills());
                refreshStatisticBar();
            });

            // 矩形覆盖物绘制完成触发
            drawingManager.addEventListener('rectanglecomplete', function(polygon) {

                // 将绘制的覆盖物替换成普通覆盖物
                clearAll();
                map.addOverlay(polygon);
                drawedOverlays.push(polygon);

                for(var i = 0; i < customerMarkers.length; i++) {

                    var marker = customerMarkers[i];
                    var position = marker.getPosition();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                        parent.checkCustomer(marker.getTitle().getId(), true);
                    }
                }

                for(var i = 0; i < lineMarkers.length; i++) {

                    var marker = lineMarkers[i];
                    var position = marker.getPosition();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(!inPolygon) {

                        continue;
                    }

                    var lineid = marker.getTitle().getId();
                    var lineBill = lineBills[lineid];
                    for(var j in lineBill.bills) {

                        var customerid = lineBill.bills[j].customerid;
                        parent.checkCustomer(customerid, true);
                    }
                }

                removeLineMarkers();
                removeCustomerMarkers();
                initSelectedCustomerMarker(parent.getCustomerBills());
                refreshStatisticBar();
            });

            // 圆形覆盖物绘制完成触发
            drawingManager.addEventListener('circlecomplete', function(circle) {

                // 将绘制的覆盖物替换成普通覆盖物
                clearAll();
                map.addOverlay(circle);
                drawedOverlays.push(circle);

                for(var i = 0; i < customerMarkers.length; i++) {

                    var marker = customerMarkers[i];
                    var position = marker.getPosition();

                    var inCircle = isPointInCircle(position, circle);
                    if(inCircle) {

                        // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                        parent.checkCustomer(marker.getTitle().getId(), true);
                    }
                }

                for(var i = 0; i < lineMarkers.length; i++) {

                    var marker = lineMarkers[i];
                    var position = marker.getPosition();

                    var inPolygon = isPointInCircle(position, circle);
                    if(!inPolygon) {

                        continue;
                    }

                    var lineid = marker.getTitle().getId();
                    var lineBill = lineBills[lineid];
                    for(var j in lineBill.bills) {

                        var customerid = lineBill.bills[j].customerid;
                        parent.checkCustomer(customerid, true);
                    }
                }

                removeLineMarkers();
                removeCustomerMarkers();
                initSelectedCustomerMarker(parent.getCustomerBills());
                refreshStatisticBar();

            });

            /**
             * 清除鼠标绘制的覆盖物
             */
            function clearAll() {
                for(var i = 0; i < overlays.length; i++){
                    map.removeOverlay(overlays[i]);
                }
                overlays.length = 0;
                return true;
            }

        })();
        </c:if>

        <c:if test="${not empty line.startpoint }">
            <%--addStartMarker('${line.startpoint }');--%>
        </c:if>

        // 自定义控件
        <c:if test="${param.type eq 'edit'}">
        ~(function(){

            // 定义一个控件类,即function
            function CustomControl(){
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(0, -15);
            }

            // 通过JavaScript的prototype属性继承于BMap.Control
            CustomControl.prototype = new BMap.Control();

            // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
            // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
            CustomControl.prototype.initialize = function(map){

                // 创建一个DOM元素
                $('<div id="customControl" style="width: 64px; height: 47px; transform: scale(0.8); position: absolute; background-color: #fbfbfb; border: solid 1px gray; border-radius: 5px;">' +
//                        '<a onclick="javascript:addStartMarker();" style="display: block; width: 64px; height: 47px; background: url(image/map/map-add-start.png) no-repeat; background-size: 42px 42px; background-position: 10px 2px;" href="javascript:void(0)" title="添加配送起点" onfocus="this.blur()"></a>' +
                        '<a onclick="javascript:clearOverlays();" style="display: block; width: 64px; height: 47px; background: url(image/map/map-cancel.png) no-repeat; background-size: 32px 32px; background-position: 17px 8px;" href="javascript:void(0)" title="清除覆盖物" onfocus="this.blur()"></a>' +
                        '</div>').appendTo('body');

                // 添加DOM元素到地图中
                map.getContainer().appendChild($('#customControl')[0]);
            };

            // 创建控件并添加到地图当中
            map.addControl(new CustomControl());
        })();
        </c:if>

        // 选择列表中的客户
        var customerBills = parent.getCustomerBills();
        initSelectedCustomerMarker(customerBills, true);

        // 统计栏
        refreshStatisticBar();
    });

    /**
     *
     * 标注客户
     *
     * @param customerid    客户编号
     * @param bills         单据
     * @param check         boolean 该客户下是否全部选中
     * @param inline        boolean 是否线路内
     * @param empty         boolean 是否未选中，选中单据数量为0
     */
    function markCustomer(customerid, bills, check, inline, empty) {

        if(inline == null) {
            inline = bills[0].inline == '1';
        }

        if(check == null) {

            check = true;
            for(var i in bills) {
                var bill = bills[i];
                check = check && bill.check;
            }
        }

        if(empty == null) {
            var checkNum = 0;
            for(var i in bills) {
                var bill = bills[i];
                if(bill.check) {
                    checkNum++;
                }
            }

            empty = false;
            if(checkNum == 0) {
                empty = true;
            }
        }

        for(var i in customerLocations) {

            var customerLocation = customerLocations[i];
            if (customerid == customerLocation.customerid) {

                var icon = outlineUnselectedIcon;
                if(inline && !empty) {
                    icon = inlineSelectedIcon;
                } else if(inline && empty) {
                    icon = inlineUnselectedIcon;
                } else if(!inline && !empty) {
                    icon = outlineSelectedIcon;
                } else if(!inline && empty) {
                    icon = outlineUnselectedIcon;
                }

                // 创建点坐标
                var point = new BMap.Point(customerLocation.latitude, customerLocation.longitude);
                var customerMarker = new BMap.Marker(point, {icon: icon});  // 创建标注
                var title = customerLocation.customerid;
                customerMarker.setTitle(title + ': ' + customerLocation.name);

                customerMarker.addEventListener("click", function(e){

                    var html = new Array();
                    html.push('<div style="overflow-y: scroll; height: 172px;">');
                    html.push('<table class="map-detail-table">');
                    html.push('<tr style="background-color: #dedede;">');
//                    html.push('<th>#</th>');
                    html.push('<th><input type="checkbox" name="' + customerid + '" index="0" ' + (check ? 'checked="checked"' : '') + '/></th>');
                    html.push('<th>编号</th>');
                    html.push('<th>业务日期</th>');
//                    html.push('<th>客户</th>');
                    html.push('<th>销售额</th>');
                    html.push('<th>箱数</th>');
                    html.push('<th>重量</th>');
                    html.push('<th>体积</th>');
                    html.push('<th>状态</th>');
                    html.push('<th>来源</th>');
                    html.push('<th>线路内</th>');
                    html.push('</tr>');

                    var index = 1;
                    for(var i in bills) {

                        var bill = bills[i];
                        html.push('<tr style="">');
//                        html.push('<td>' + (index++) + '</td>');
                        html.push('<td><input type="checkbox" name="' + customerid + '" index="' + (index++) + '"' + (bill.check ? 'checked="checked"' : '') + ' billid="' + bill.saleoutid + '"/></td>');
                        html.push('<td>' + bill.saleoutid + '</td>');
                        html.push('<td>' + bill.businessdate + '</td>');
//                        html.push('<td>' + bill.customerid + '<br/>' + bill.customername + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.salesamount) + '</td>');
                        html.push('<td class="right">' + formatterBigNumNoLen(bill.boxnum) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.weight) + 'kg</td>');
                        html.push('<td class="right">' + formatterMoney(bill.volume, 4) + 'm<sup>3</sup></td>');
                        html.push('<td>' + getSysCodeName('status', bill.status) + '</td>');
                        html.push('<td>' + getBillName(bill.deliverytype)  + '</td>');
                        html.push('<td>' + (inline ? '是' : '否')  + '</td>');
                        html.push('</tr>');
                    }

                    html.push('</table>');
                    html.push('</div>');

                    var info = html.join('');
                    var infoWindow = new BMap.InfoWindow(info, {
                        width: 650,     		// 信息窗口宽度
                        height: 200,     		// 信息窗口高度
                        title: '(' + e.target.getTitle().getId() + ')' + customerLocations[e.target.getTitle().getId()].name,	// 信息窗口标题
                        enableMessage: false	//设置允许信息窗发送短息
                    });
                    // open事件
                    infoWindow.addEventListener('open', function(e) {

                        $('.map-detail-table tr').off('click');

                        $('.map-detail-table tr').on('click', function(e) {

                            if($(e.toElement).is('td') || $(e.toElement).is('th')) {

                                var checked = $(this).find('input[type=checkbox]').is(':checked');
                                if(checked) {
                                    $(this).find('input[type=checkbox]').removeAttr('checked');
                                } else {
                                    $(this).find('input[type=checkbox]').attr('checked', 'checked');
                                }
                            }
                            $(this).find('input[type=checkbox]').trigger('customClick');
                        });

                        // 解除绑定
                        $('[name=' + customerid + ']').off('customClick');

                        // 绑定
                        $('[name=' + customerid + ']').on('customClick', function(e) {

                            var index = $(this).attr('index');
                            var check = $(this).is(':checked');

                            if(index == '0') {
                                parent.checkCustomer(customerid, check);
                                refreshStatisticBar();
                            }

                            // 头部checkbox状态
                            if(check && index == '0') {

                                $('[type=checkbox][name=' + customerid + ']').attr('checked', 'checked');
                                return true;
                            } else if(!check && index == '0') {

                                $('[type=checkbox][name=' + customerid + ']').removeAttr('checked');
                                return true;
                            }

                            var billid = $(this).attr('billid');
                            parent.checkBill(billid, check);
                            refreshStatisticBar();

                            // 行checkbox状态(行是否全选)
                            var allcheck = true;
                            $('[name=' + customerid + ']:gt(0)').each(function(index, item) {
                                allcheck = allcheck && $(this).is(':checked');
                            });

                            // 全选
                            if(allcheck) {
                                $('[name=' + customerid + ']:eq(0)').attr('checked', 'checked');
                                return true;
                            }

                            // 非全选
                            $('[name=' + customerid + ']:eq(0)').removeAttr('checked');
                            return true;
                        });
                    });

                    // close事件
                    infoWindow.addEventListener('close', function(e) {

                        removeLineMarkers();
                        removeCustomerMarkers();
                        var customerBills = parent.getCustomerBills();
                        initSelectedCustomerMarker(customerBills, false);
                    });
                    map.openInfoWindow(infoWindow, new BMap.Point(customerLocations[e.target.getTitle().getId()].latitude, customerLocations[e.target.getTitle().getId()].longitude)); //开启信息窗口

                });

                // 将标注添加到地图中
                map.addOverlay(customerMarker);
                customerMarkers.push(customerMarker);
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    function markLine(billObject, selectCount) {

        var lineid = billObject.lineid;
        var linename = billObject.linename;
        var totalCount = billObject.totalCount;

        var bills = new Array();
        billObject.selectCount = 0;
        for(var i in billObject.bills) {

            var customerBills = billObject.bills[i];
            for(var j in customerBills.bills) {

                var customerBill = customerBills.bills[j];
                var check = parent.getBillStatus(customerBill.saleoutid);
                if(check) {
                    billObject.selectCount = billObject.selectCount + 1;
                }
                bills.push(customerBill);
            }
        }

        bills.sort(function(o1, o2) {

            var c1 = (o1.salesarea || '').localeCompare((o2.salesarea || ''));
            if(c1 == 0) {
                var c2 = (o1.customerid || '').localeCompare((o2.customerid || ''));
                if(c2 == 0) {
                    return (o1.saleoutid || '').localeCompare((o2.saleoutid || ''));
                }
                return c2;
            }
            return c1;
        });

        var icon = inlineUnselectedIcon;
        if(billObject.selectCount >= billObject.totalCount) {
            icon = inlineSelectedIcon;
        }

        // 创建点坐标
        var point = new BMap.Point(billObject.defaultpoint.split(',')[0], billObject.defaultpoint.split(',')[1]);
        // 创建标注
        var lineMarker = new BMap.Marker(point, {icon: icon});
        lineMarker.setTitle(billObject.lineid + ': ' + billObject.linename);

        lineMarker.addEventListener("click", function(e){

            var html = new Array();
            html.push('<div style="overflow-y: scroll; height: 172px;">');
            html.push('<table class="map-detail-table">');
            html.push('<tr style="background-color: #dedede;">');
//                    html.push('<th>#</th>');
            html.push('<th style="width: 30px;"><input type="checkbox" lineid="' + billObject.lineid + '" index="0" ' + (billObject.selectCount >= totalCount ? 'checked="checked"' : '') + '/></th>');
            html.push('<th style="width: 140px;">编号</th>');
            html.push('<th style="width: 75px;">业务日期</th>');
            html.push('<th style="width: 130px;">客户</th>');
            html.push('<th style="width: 56px;">销售额</th>');
            html.push('<th style="width: 56px;">箱数</th>');
            html.push('<th style="width: 66px;">重量</th>');
            html.push('<th style="width: 66px;">体积</th>');
            html.push('<th style="width: 66px;">状态</th>');
            html.push('<th style="width: 80px;">来源</th>');
//            html.push('<th>线路内</th>');
            html.push('</tr>');

            var index = 1;
            for(var i in bills) {

                var bill = bills[i];
                bill.check = parent.getBillStatus(bill.saleoutid);
                html.push('<tr style="">');
//                        html.push('<td>' + (index++) + '</td>');
                html.push('<td><input type="checkbox" billid="' + bill.saleoutid + '" lineid="' + billObject.lineid + '" index="' + (index++) + '"' + (bill.check ? 'checked="checked"' : '') + ' billid="' + bill.saleoutid + '"/></td>');
                html.push('<td>' + bill.saleoutid + '</td>');
                html.push('<td>' + bill.businessdate + '</td>');
                html.push('<td>' + bill.customerid + ': ' + bill.customername + '</td>');
                html.push('<td class="right">' + formatterMoney(bill.salesamount) + '</td>');
                html.push('<td class="right">' + formatterBigNumNoLen(bill.boxnum) + '</td>');
                html.push('<td class="right">' + formatterMoney(bill.weight) + 'kg</td>');
                html.push('<td class="right">' + formatterMoney(bill.volume, 4) + 'm<sup>3</sup></td>');
                html.push('<td>' + getSysCodeName('status', bill.status) + '</td>');
                html.push('<td>' + getBillName(bill.deliverytype)  + '</td>');
//                html.push('<td>' + (inline ? '是' : '否')  + '</td>');
                html.push('</tr>');
            }

            html.push('</table>');
            html.push('</div>');

            var info = html.join('');
            var infoWindow = new BMap.InfoWindow(info, {
                width: 774,     		// 信息窗口宽度
                height: 200,     		// 信息窗口高度
                title: linename,	    // 信息窗口标题
                enableMessage: false	//设置允许信息窗发送短息
            });
            // open事件
            infoWindow.addEventListener('open', function(e) {

                $('.map-detail-table tr').off('click');

                $('.map-detail-table tr').on('click', function(e) {

                    if($(e.toElement).is('td') || $(e.toElement).is('th')) {

                        var checked = $(this).find('input[type=checkbox]').is(':checked');
                        if(checked) {
                            $(this).find('input[type=checkbox]').removeAttr('checked');
                        } else {
                            $(this).find('input[type=checkbox]').attr('checked', 'checked');
                        }
                    }
                    $(this).find('input[type=checkbox]').trigger('customClick');
                });

                // 解除绑定
                $('[lineid=' + lineid + ']').off('customClick');

                // 绑定
                $('[lineid=' + lineid + ']').on('customClick', function(e) {

                    var index = $(this).attr('index');
                    var check = $(this).is(':checked');

                    if(index == '0') {
                        var lineBill = lineBills[lineid];
                        for(var j in lineBill.bills) {

                            var customerid = lineBill.bills[j].customerid;
                            parent.checkCustomer(customerid, check);
                        }
                        refreshStatisticBar();
                    }

                    // 头部checkbox状态
                    if(check && index == '0') {

                        $('[type=checkbox][lineid=' + lineid + ']').attr('checked', 'checked');
                        billObject.selectCount = billObject.totalCount;
                        return true;
                    } else if(!check && index == '0') {

                        $('[type=checkbox][lineid=' + lineid + ']').removeAttr('checked');
                        billObject.selectCount = 0;
                        return true;
                    }

                    if(check) {
                        billObject.selectCount = billObject.selectCount + 1;
                    } else {
                        billObject.selectCount = billObject.selectCount - 1;
                    }

                    var billid = $(this).attr('billid');
                    parent.checkBill(billid, check);
                    refreshStatisticBar();

                    // 行checkbox状态(行是否全选)
                    var allcheck = true;
                    $('[lineid=' + lineid + ']:gt(0)').each(function(index, item) {
                        allcheck = allcheck && $(this).is(':checked');
                    });

                    // 全选
                    if(allcheck) {
                        $('[lineid=' + lineid + ']:eq(0)').attr('checked', 'checked');
                        return true;
                    }

                    // 非全选
                    $('[lineid=' + lineid + ']:eq(0)').removeAttr('checked');
                    return true;
                });
            });

            // close事件
            infoWindow.addEventListener('close', function(e) {

                removeLineMarkers();
                removeCustomerMarkers();
                var customerBills = parent.getCustomerBills();
                initSelectedCustomerMarker(customerBills, false);
            });
            map.openInfoWindow(infoWindow, point); //开启信息窗口
        });

        // 将标注添加到地图中
        map.addOverlay(lineMarker);
        lineMarkers.push(lineMarker);
        return true;
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
     * 清除所有线路标记
     */
    function removeLineMarkers() {

        for(var i in lineMarkers) {

            map.removeOverlay(lineMarkers[i]);
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

        return true;
        // 已设置起点标志
        if(startMarker != null) {

            $.messager.alert('提醒', '当前地图已设置配送起点，请拖动配送起点进行修改位置。');
            return true;
        }

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

        parent.$('#storage-startpoint-deliveryAddPage').val(start.lng + ',' + start.lat);

        startMarker = new BMap.Marker(start, {icon: startIcon });  // 创建标注

        startMarker.addEventListener("click", function(e){

            map.openInfoWindow(new BMap.InfoWindow('<c:out value="${companyName }" />', {
                width: 100,     		// 信息窗口宽度
                height: 110,     		// 信息窗口高度
                title: '配送起点',	    // 信息窗口标题
                enableMessage: false	//设置允许信息窗发送短息
            }), start); //开启信息窗口
        });

        // 起始点位置拖动时，重新帮点click事件，防止弹出的信息窗口出现在原来的位置
        startMarker.addEventListener("dragend", function(e){

            var newLocation = startMarker.getPosition();
            parent.setStartPoint(newLocation.lng, newLocation.lat);
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
     * 清除全部覆盖物
     *
     * @returns {boolean}
     */
    function clearOverlays() {

        for(var i in drawedOverlays) {

            var overlay = drawedOverlays[i];
            map.removeOverlay(overlay);
        }
        drawedOverlays = new Array();
        return true;
    }

    /**
     * 初始化客户标记
     *
     * @returns {boolean}
     */
    function initSelectedCustomerMarker(customerBills, init) {

        for(var key in customerBills) {

            var customerid = key;
            var bills = customerBills[key].bills;
            var flag = markCustomer(customerid, bills, null, null, null);

            if(flag) {
                continue;
            }

            if(!init) {
                continue;
            }

            var customerLine = customerLineMap[customerid];
            if(customerLine != undefined) {

                var lines = customerLine.lines;
                for(var i in lines) {

                    var line = lines[i];
                    if(lineBills[line.lineid] == undefined) {
                        lineBills[line.lineid] = {
                            lineid: line.lineid,
                            linename: line.linename,
                            defaultpoint: line.defaultpoint,
                            totalCount: 0,
                            selectCount: 0,
                            bills: new Array()
                        }
                    }
                    lineBills[line.lineid].bills.push({
                        customerid: customerid,
                        bills: bills
                    });
                    lineBills[line.lineid].totalCount = lineBills[line.lineid].totalCount + bills.length
                }
            }
        }

        for(var key in lineBills) {

            var lineBill = lineBills[key];
            markLine(lineBill, lineBill.selectCount);
        }
        return true;
    }

    /**
     * 获取单据名称
     *
     * @param type
     * @returns {*}
     */
    function getBillName(type) {
        if (type == '0') {
            return '销售发货单';
        } else if (type == '1') {
            return '代配送出库单';
        }
    }

    /**
     * 刷新统计栏
     *
     * @returns {boolean}
     */
    function refreshStatisticBar() {

        if(statisticBar != null) {
            map.removeControl(statisticBar);
        }

        var billStatus = parent.getBillStatistic();

        function StatisticBar(){

            // 默认停靠位置和偏移量
            this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
            this.defaultOffset = new BMap.Size(10, 10);
        }

        // 通过JavaScript的prototype属性继承于BMap.Control
        StatisticBar.prototype = new BMap.Control();

        // initialize方法,
        StatisticBar.prototype.initialize = function(map){

            var v = billStatus.volume;
            var w = billStatus.weight;
            var b = billStatus.boxnum;

            var mv = '${param.mv }';
            var mw = '${param.mw }';
            var mb = '${param.mb }';

            var overvolume = parseFloat(v) > parseFloat(mv);
            var overweight = parseFloat(w) > parseFloat(mw);
            var overboxnum = parseFloat(b) > parseFloat(mb);

            var html = new Array();
            html.push('<div id="statisticbar">');
            html.push('<table class="statistic-table">');
            html.push('<tr style="color: ' + (overvolume ? '#F00' : '#000') + ';"><th>体积</th><td>' + formatterMoney(v, 4) + '/' + mv + '</td></tr>');
            html.push('<tr style="color: ' + (overweight ? '#F00' : '#000') + ';"><th>重量</th><td>' + formatterMoney(w) + '/' + mw + '</td></tr>');
            html.push('<tr style="color: ' + (overboxnum ? '#F00' : '#000') + ';"><th>箱数</th><td>' + (formatterMoney(b, 3) || '0') + '/' + mb + '</td></tr>');
            html.push('</table>');
            html.push('</div>');

            $(html.join('')).appendTo('body');

            var div = $('#statisticbar')[0];

            // 添加DOM元素到地图中
            map.getContainer().appendChild(div);
            // 将DOM元素返回
            return div;
        };

        // 创建控件并添加到地图当中
        statisticBar = new StatisticBar();
        map.addControl(statisticBar);

        return true;
    }

    String.prototype.getId = function() {

        if(this.indexOf(':')) {
            return this.split(':')[0];
        }
        return '';
    };

    -->
</script>
</body>
</html>
