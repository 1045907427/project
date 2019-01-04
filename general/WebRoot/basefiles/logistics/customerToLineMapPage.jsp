<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>位置信息</title>
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

        #selected {
            float: left;
            width: 220px;
            height: 100%;
            float: left;
            overflow-y: scroll;
            color: #2a5caa;
            padding: 10px 0px 10px 0px;
        }
        #customers {
            padding: 0px;
        }
        .customer-item {
            list-style: none;
            padding: 3px;
        }
        .customer-item:nth-child(even) {
            background: #CCC
        }
        .customer-item:nth-child(odd) {
            background: #EEE
        }
        .customer-name {
            color: #1882cf;
        }
        .customer-detail {
            color: #555555;
        }
        li[customerid]:hover {
            background-color: #999;
        }
    </style>
</head>
<body>
<div id="selected" style="display: none;">
    　已选客户：
    <ul id="customers">
    </ul>
</div>
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
    var lineCustomersMap = {};

    var srcCustomerids = new Array();

    // 已选择客户

    var showedCustomerMarks = new Array();
    var selectedCustomerMarks = new Array();

    var startMark = null;
    var defaultMark = null;

    var drawedOverlays = new Array();

    // mark
    // 普通标记
    var normalIcon1 = new BMap.Icon("image/map/map-location.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 普通标记
    var normalIcon2 = new BMap.Icon("image/map/map-outline-selected.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });

    // 选中标记(空心)
    var selectedIcon1 = new BMap.Icon("image/map/map-selected1-yellow.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记(空心)big
    var selectedIcon1big = new BMap.Icon("image/map/map-selected1-yellow-24.png", new BMap.Size(24, 48), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记
    var selectedIcon2 = new BMap.Icon("image/map/map-selected2-yellow.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记big
    var selectedIcon2big = new BMap.Icon("image/map/map-selected2-yellow-24.png", new BMap.Size(24, 48), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });

    // 选中标记(空心)
    var originSelectedIcon1 = new BMap.Icon("image/map/map-inline-unselected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记(空心)big
    var originSelectedIcon1big = new BMap.Icon("image/map/map-inline-unselected-24.png", new BMap.Size(24, 48), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记
    var originSelectedIcon2 = new BMap.Icon("image/map/map-inline-selected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记big
    var originSelectedIcon2big = new BMap.Icon("image/map/map-inline-selected-24.png", new BMap.Size(24, 48), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });

    // 起点标记
    var startIcon = new BMap.Icon("image/map/map-start-point-small-pink.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 默认配送标记
    var defaultIcon = new BMap.Icon("image/map/map-location-default-small.png", new BMap.Size(16, 32), {
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

        ~(function(){
            <c:forEach items="${lineCustomers }" var="lineCustomer" varStatus="status">
                if(lineCustomersMap['${lineCustomer.customerid }'] == undefined) {
                    lineCustomersMap['${lineCustomer.customerid }'] = {lines: new Array(), lineids: new Array()};
                }
                lineCustomersMap['${lineCustomer.customerid }'].lines.push('${lineCustomer.linename }');
                lineCustomersMap['${lineCustomer.customerid }'].lineids.push('${lineCustomer.lineid }');
            </c:forEach>
        })();

        // 中心点
        map.centerAndZoom(new BMap.Point(${center }), 9);

        // 可缩放
        map.enableScrollWheelZoom(true);

        // 标记列表中显示的客户
        try {
            markCustomer(null);
        } catch (e) { }

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

                //添加鼠标绘制工具监听事件，用于获取绘制结果
                // 多边形覆盖物绘制完成触发
                drawingManager.addEventListener('polygoncomplete', function(polygon) {

                    // 将绘制的覆盖物替换成普通覆盖物
                    clearAll();
                    map.addOverlay(polygon);
                    drawedOverlays.push(polygon);

                    var selectedIndex = 1;
                    for(var i = 0; i < showedCustomerMarks.length; i++) {

                        var marker = showedCustomerMarks[i];
                        var position = marker.getPosition();

                        var inPolygon = isPointInPolygon(position, polygon.getPath());
                        if(inPolygon) {

                            // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                            var code = 'setTimeout(function(){parent.checkCustomer(\'' + marker.getTitle().getCustomerid() + '\');/*selectCustomer(\'' + marker.getTitle().getCustomerid() + '\');*/}, 10 * ' + (selectedIndex ++) + ');'
                            eval(code);
                        }
                    }
                });

                // 矩形覆盖物绘制完成触发
                drawingManager.addEventListener('rectanglecomplete', function(polygon) {

                    // 将绘制的覆盖物替换成普通覆盖物
                    clearAll();
                    map.addOverlay(polygon);
                    drawedOverlays.push(polygon);

                    var selectedIndex = 1;
                    for(var i = 0; i < showedCustomerMarks.length; i++) {

                        var marker = showedCustomerMarks[i];
                        var position = marker.getPosition();

                        var inPolygon = isPointInPolygon(position, polygon.getPath());
                        if(inPolygon) {

                            // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                            var code = 'setTimeout(function(){parent.checkCustomer(\'' + marker.getTitle().getCustomerid() + '\');/*selectCustomer(\'' + marker.getTitle().getCustomerid() + '\');*/}, 10 * ' + (selectedIndex ++) + ');'
                            eval(code);
                        }
                    }
                });

                // 圆形覆盖物绘制完成触发
                drawingManager.addEventListener('circlecomplete', function(circle) {

                    // 将绘制的覆盖物替换成普通覆盖物
                    clearAll();
                    map.addOverlay(circle);
                    drawedOverlays.push(circle);

                    var selectedIndex = 1;
                    for(var i = 0; i < showedCustomerMarks.length; i++) {

                        var marker = showedCustomerMarks[i];
                        var position = marker.getPosition();

                        var inPolygon = isPointInCircle(position, circle);
                        if(inPolygon) {

                            // 直接标记会遗漏(百度地图BUG？)，所以采用setTimeout方式
                            var code = 'setTimeout(function(){parent.checkCustomer(\'' + marker.getTitle().getCustomerid() + '\');/*selectCustomer(\'' + marker.getTitle().getCustomerid() + '\');*/}, 10 * ' + (selectedIndex ++) + ');'
                            eval(code);
                        }
                    }

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

        <c:if test="${not empty start }">
            if(parent.$('[name="lineInfo.startpoint"]').val()) {
                addStartMarker(parent.$('[name="lineInfo.startpoint"]').val());
            }
        </c:if>

        (function(){
            var defaultpoint = parent.$('[name="lineInfo.defaultpoint"]').val();
            if(defaultpoint) {
                console.log('****', defaultpoint);
                addDefaultMarker(defaultpoint)
            } else {
                <c:if test="${param.type eq 'edit'}">
                    $.messager.alert('提醒', '当前线路未设定默认配送点，现已将地图中心点设为配送点，拖动图标可以修改配送点位置。');
                    addDefaultMarker();
                </c:if>
            }
        })();

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
                    $('<div id="customControl" style="height: 198px; transform: scale(0.8); background-color: #fbfbfb; border-radius: 5px; border: solid 1px gray; ">' +
                            '<a onclick="javascript:addDefaultMarker();" style="display: block; border-bottom: solid 1px gray; width: 64px; height: 47px; background: url(image/map/map-location-default.png) no-repeat; background-size: 42px 42px; background-position: 10px 2px;" href="javascript:void(0)" title="设置默认位置，没有位置信息的客户将按默认位置配送。" onfocus="this.blur()"></a>' +
//                            '<a onclick="javascript:addStartMarker();" style="display: block; border-bottom: solid 1px gray; width: 64px; height: 47px; background: url(image/map/map-add-start.png) no-repeat; background-size: 42px 42px; background-position: 10px 2px;" href="javascript:void(0)" title="添加配送起点" onfocus="this.blur()"></a>' +
                            '<a onclick="javascript:clearOverlays();" style="display: block; border-bottom: solid 1px gray; width: 64px; height: 47px; background: url(image/map/map-cancel.png) no-repeat; background-size: 32px 32px; background-position: 17px 7px;" href="javascript:void(0)" title="清除覆盖物" onfocus="this.blur()"></a>' +
                            //                        '<a class="BMapLib_box" onclick="javascript:computeRoute();" style="border-right: solid 1px gray; height: 47px; background: url(image/map/map-line.png) no-repeat; background-size: 38px 38px; background-position: 14px 5px;" href="javascript:void(0)" title="规划路线" onfocus="this.blur()"></a>' +
                            '<a onclick="javascript:parent.cancelCheckCustomerThisTime();" style="display: block; border-bottom: solid 1px gray; width: 64px; height: 47px; background: url(image/map/map-remove-mark.png) no-repeat; background-size: 38px 38px; background-position: 10px 4px;" href="javascript:void(0)" title="取消本次选择" onfocus="this.blur()"></a>' +
                            '<a onclick="javascript:parent.collapseWest();" style="display: block; width: 64px; height: 47px; background: url(image/map/map-screen.png) no-repeat; background-size: 38px 38px; background-position: 14px 9px;" href="javascript:void(0)" title="切换全屏/原始大小" onfocus="this.blur()"></a>' +
                            '</div>').appendTo('body');

                    // 添加DOM元素到地图中
                    map.getContainer().appendChild($('#customControl')[0]);
                    return $('#customControl')[0];
                };

                // 创建控件并添加到地图当中
                map.addControl(new CustomControl());
            })();
        </c:if>

        // 图例
        ~(function(){

            function LegendControl(){
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
                this.defaultOffset = new BMap.Size(10, 10);
            }

            LegendControl.prototype = new BMap.Control();

            LegendControl.prototype.initialize = function(map){

                // 创建一个DOM元素
                var html = new Array();
                html.push('<div id="legend-bar">');
                html.push('<table class="statistic-table">');
//                html.push('<tr><th><image src="image/map/map-start-point-small-pink.png"/></th><td>配送起点</td></tr>');
                html.push('<tr><th><image src="image/map/map-location-default-small.png"/></th><td>线路客户默认配送点</td></tr>');
                html.push('<tr><th><image src="image/map/map-outline-unselected.png"/></th><td>当前查询结果未选</td></tr>');
                html.push('<tr><th><image src="image/map/map-outline-selected.png"/></th><td>当前查询结果未选，其他线路已添加</td></tr>');
                html.push('<tr><th><image src="image/map/map-selected1-yellow.png"/></th><td>当前线路已选</td></tr>');
                html.push('<tr><th><image src="image/map/map-selected2-yellow.png"/></th><td>当前线路已选，其他线路已添加</td></tr>');
                html.push('<tr><th><image src="image/map/map-inline-unselected.png"/></th><td>当前线路已添加</td></tr>');
                html.push('<tr><th><image src="image/map/map-inline-selected.png"/></th><td>当前线路已添加，其他线路已添加</td></tr>');
//                html.push('<tr><th><image src="image/map/map-location-mark-pushpin.png"/></th><td>已添加至当前线路</td></tr>');
                html.push('</table>');
                html.push('</div>');

                $(html.join('')).appendTo('body');

                // 添加DOM元素到地图中
                map.getContainer().appendChild($('#legend-bar')[0]);
                return $('#legend-bar')[0];
            };

            // 创建控件并添加到地图当中
            map.addControl(new LegendControl());

        })();

        $(document).on('mouseover', 'li[customerid]', function(e){

            var customerid = $(this).attr('customerid');

            map.closeInfoWindow();

            for(var i in selectedCustomerMarks) {

                var marker = selectedCustomerMarks[i];
                if(marker.getTitle().getCustomerid() == customerid) {

                    if(lineCustomersMap[customerid] == undefined || lineCustomersMap[customerid].lines == undefined || lineCustomersMap[customerid].lines.length == 0) {
                        marker.setIcon(selectedIcon1big);
                    } else {
                        marker.setIcon(selectedIcon2big);
                    }
                }
            }

        });

        $(document).on('mouseleave', 'li[customerid]', function(e){

            var customerid = $(this).attr('customerid');

            map.closeInfoWindow();

            for(var i in selectedCustomerMarks) {

                var marker = selectedCustomerMarks[i];
                if(marker.getTitle().getCustomerid() == customerid) {

                    if(lineCustomersMap[customerid] == undefined || lineCustomersMap[customerid].lines == undefined || lineCustomersMap[customerid].lines.length == 0) {
                        marker.setIcon(selectedIcon1);
                    } else {
                        marker.setIcon(selectedIcon2);
                    }
                }
            }
        });

        // 选择列表中的客户
        initSelectedCustomerMarker();
    });

    /**
     * 清除所有标记的客户
     */
    function clearCustomerMarks() {

        for(var i in showedCustomerMarks) {

            map.removeOverlay(showedCustomerMarks[i]);
        }

        showedCustomerMarks = new Array();
        return true;
    }

    /**
     * 标注客户
     *
     * @param customerid
     */
    function markCustomer(customerids) {

        var showedCustomerids = customerids || parent.showedCustomerids;

        for(var i in showedCustomerids) {

            var customerid = showedCustomerids[i];
            var location = customerLocations[customerid];
            if(location) {

                // 创建点坐标
                var point = new BMap.Point(location.latitude, location.longitude);

                var normalMark = new BMap.Marker(point, {icon: normalIcon1});
                var lines = new Array();
                if(lineCustomersMap[customerid] && lineCustomersMap[customerid].lines.length > 0) {
                    normalMark = new BMap.Marker(point, {icon: normalIcon2});
                    lines = lineCustomersMap[customerid].lines;
                }

                var title = location.customerid;
                normalMark.setTitle(title + ': ' + location.name);

                (function(marker, lines){
                    marker.addEventListener("click", function(e){

                        var info = '客户编码：' + e.target.getTitle().getCustomerid() + '</br>'
                                + '客户名称：' + customerLocations[e.target.getTitle().getCustomerid()].name + '</br>'
                                + '销售区域：' + customerLocations[e.target.getTitle().getCustomerid()].salesarea + '</br>'
                                + '客户分类：' + customerLocations[e.target.getTitle().getCustomerid()].customersort + '</br>'
                                + '客户业务员：' + customerLocations[e.target.getTitle().getCustomerid()].salesusername + '</br>'
                                + (lines.length > 0 ? '已关联线路：' + lines.join(', ') + '</br>' : '')
                                + ' <a href="javascript:void(0);" onclick="javascript:map.closeInfoWindow();parent.checkCustomer(\'' + e.target.getTitle().getCustomerid() + '\')">选择</a>';;
                        map.openInfoWindow(new BMap.InfoWindow(info, {
                            width: 100,     		// 信息窗口宽度
                            height: 140,     		// 信息窗口高度
                            title: customerLocations[e.target.getTitle().getCustomerid()].name,	// 信息窗口标题
                            enableMessage: false	//设置允许信息窗发送短息
                        }), new BMap.Point(customerLocations[e.target.getTitle().getCustomerid()].latitude, customerLocations[e.target.getTitle().getCustomerid()].longitude)); //开启信息窗口
                    });
                })(normalMark, lines);

                map.addOverlay(normalMark);              // 将标注添加到地图中
                showedCustomerMarks.push(normalMark);
            }
        }
    }

    /**
     * 选择客户(选中标志)
     *
     * @param customerid
     * @param base
     */
    function selectCustomer(customerid, base) {

        // 是否已选中
        for(var i in selectedCustomerMarks) {

            var marker = selectedCustomerMarks[i];
            if(customerid == marker.getTitle().getCustomerid()) {
                return true;
            }
        }

        for(var i in customerLocations) {

            var customerLocation = customerLocations[i];
            if (customerid == customerLocation.customerid) {

                // 创建点坐标
                var point = new BMap.Point(customerLocation.latitude, customerLocation.longitude);

                var selectedMark = new BMap.Marker(point, {icon: selectedIcon1});
                if(base) {
                    selectedMark = new BMap.Marker(point, {icon: originSelectedIcon1});
                }
                var lines = new Array();
                if(lineCustomersMap[customerid] && lineCustomersMap[customerid].lines.length > 0) {
                    if(base) {
                        var other = false;
                        for(var i in lineCustomersMap[customerid].lineids) {
                            var tempid = lineCustomersMap[customerid].lineids[i];
                            if(tempid != '${param.lineid}'){
                                other = true;
                                break;
                            }
                        }
                        if(other) {
                            selectedMark = new BMap.Marker(point, {icon: originSelectedIcon2});
                        }
                    } else {
                        selectedMark = new BMap.Marker(point, {icon: selectedIcon2});
                    }
                    lines = lineCustomersMap[customerid].lines;
                }

                var title = customerLocation.customerid;
                selectedMark.setTitle(title + ': ' + customerLocation.name);

                // 从showedCustomerMarks 中移除
                for (var i in showedCustomerMarks) {

                    var customerMark = showedCustomerMarks[i];
                    if (customerMark.getTitle().getCustomerid() == title) {

                        map.removeOverlay(customerMark);
                        showedCustomerMarks.splice(i, 1);
                        break;
                    }
                }

                (function(marker, lines){
                    marker.addEventListener("click", function(e){

                        var info = '客户编码：' + e.target.getTitle().getCustomerid() + '</br>'
                                + '客户名称：' + customerLocations[e.target.getTitle().getCustomerid()].name + '</br>'
                                + '销售区域：' + customerLocations[e.target.getTitle().getCustomerid()].salesarea + '</br>'
                                + '客户分类：' + customerLocations[e.target.getTitle().getCustomerid()].customersort + '</br>'
                                + '客户业务员：' + customerLocations[e.target.getTitle().getCustomerid()].salesusername + '</br>'
                                + (lines.length > 0 ? '已关联线路：' + lines.join(', ') + '</br>' : '')
                                + '<span style="color: #F00;">(已选择)</span>';
                        <c:if test="${param.type eq 'edit'}">
                            info = info + ' <a href="javascript:void(0);" onclick="javascript:map.closeInfoWindow();parent.cancelCheckCustomer(\'' + e.target.getTitle().getCustomerid() + '\')">取消选择</a>';
                        </c:if>
                        map.openInfoWindow(new BMap.InfoWindow(info, {
                            width: 100,     		// 信息窗口宽度
                            height: 140,     		// 信息窗口高度
                            title: customerLocations[e.target.getTitle().getCustomerid()].name,	// 信息窗口标题
                            enableMessage: false	//设置允许信息窗发送短息
                        }), new BMap.Point(customerLocations[e.target.getTitle().getCustomerid()].latitude, customerLocations[e.target.getTitle().getCustomerid()].longitude)); //开启信息窗口
                    });
                })(selectedMark, lines);

                map.addOverlay(selectedMark);              // 将标注添加到地图中
                selectedCustomerMarks.push(selectedMark);

                if(base != true) {

                    var html = new Array();
                    html.push('<li customerid="' + customerid + '" class="customer-item">' +
                            '<div class="customer-name">' +
                            '<div>' + customerLocation.customerid + '： ' + customerLocation.name + '</div>' +
                            '</div>' +
                            '<div class="customer-detail">' +
                            '<div>区域：' + customerLocation.salesarea + '</div>' +
                            '<div>分类：' + customerLocation.customersort + '</div>' +
                            '<div>客户业务员：' + customerLocation.salesusername + '</div>' +
                            '<div><a href="javascript:void(0);" onclick="javascript:parent.cancelCheckCustomer(\'' + customerid + '\')">取消选择</a></div>' +
                            '</div>' +
                            '</li>');

                    $('#customers').append(html.join(''));
                }
            }
        }
    }

    /**
     * 取消选择客户
     *
     * @param customerid
     */
    function cancelCustomer(customerid) {

        var selected = false;
        for(var i in parent.selectedCustomerids) {

            if(customerid == parent.selectedCustomerids[i]) {
                selected = true;
                break;
            }
        }

        if(selected) {
            return false;
        }

        for(var i in customerLocations) {

            var customerLocation = customerLocations[i];
            if (customerid == customerLocation.customerid) {

                // 创建点坐标
//                var point = new BMap.Point(customerLocation.latitude, customerLocation.longitude);
//                var selectedMark = new BMap.Marker(point, {icon: selectedIcon1});  // 创建标注
                var title = customerLocation.customerid/* + ': ' + customerLocation.name*/;

                // 从showedCustomerMarks 中移除
                for (var i in selectedCustomerMarks) {

                    var selectedMark = selectedCustomerMarks[i];
                    if (selectedMark.getTitle().getCustomerid() == title) {

                        map.removeOverlay(selectedMark);
                        selectedCustomerMarks.splice(i, 1);
                        break;
                    }
                }

                $('ul#customers').find('[customerid="' + customerid + '"]').remove();
                markCustomer(customerid.split(' '));
            }
        }
    }

    /**
     * 移除客户标记
     *
     * @param customerid
     */
    function removeCustomer(customerid) {

        // 从showedCustomerMarks 中移除
        for (var i in showedCustomerMarks) {

            var showedMarker = showedCustomerMarks[i];
            if (showedMarker.getTitle().getCustomerid() == customerid) {

                map.removeOverlay(showedMarker);
                showedCustomerMarks.splice(i, 1);
                break;
            }
        }

        // 从selectedCustomerMarks 中移除
        for (var i in selectedCustomerMarks) {

            var selectedMark = selectedCustomerMarks[i];
            if (selectedMark.getTitle().getCustomerid() == customerid) {

                map.removeOverlay(selectedMark);
                selectedCustomerMarks.splice(i, 1);
                break;
            }
        }
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
        if(startMark != null) {

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

        parent.setStartPoint(start.lng, start.lat);

        startMark = new BMap.Marker(start, {icon: startIcon });  // 创建标注

        startMark.addEventListener("click", function(e){

            map.openInfoWindow(new BMap.InfoWindow('<c:out value="${companyName }" />', {
                width: 100,
                height: 60,
                title: '配送起点',
                enableMessage: false
            }), start); //开启信息窗口
        });

        // 起始点位置拖动时，重新帮点click事件，防止弹出的信息窗口出现在原来的位置
        startMark.addEventListener("dragend", function(e){

            var newLocation = startMark.getPosition();
            parent.setStartPoint(newLocation.lng, newLocation.lat);
            startMark.removeEventListener('click');
            startMark.addEventListener("click", function(e){

                map.openInfoWindow(new BMap.InfoWindow('<c:out value="${companyName }" />', {
                    width: 100,
                    height: 60,
                    title: '配送起点',
                    enableMessage: false
                }), newLocation); //开启信息窗口
            });
        });

        map.addOverlay(startMark);
        <c:if test="${param.type eq 'edit' }">
            startMark.enableDragging();
        </c:if>

        return true;
    }

    /**
     * 添加线路默认点
     */
    function addDefaultMarker(strLocation) {

        // 已设置
        if(defaultMark != null) {

            $.messager.alert('提醒', '当前地图已设置客户默认位置，请拖动默认位置进行修改位置。');
            return true;
        }

        var defaultPoint = null;
        if(strLocation) {

            var lng = strLocation.split(",")[0];
            var lat = strLocation.split(",")[1];
            defaultPoint =  new BMap.Point(lng, lat);

        } else {

            <c:if test="${param.type ne 'edit' }">
                return true;
            </c:if>
            var bs = map.getBounds();       //获取可视区域
            var bssw = bs.getSouthWest();   //可视区域左下角
            var bsne = bs.getNorthEast();   //可视区域右上角
            defaultPoint =  new BMap.Point((bssw.lng + bsne.lng) / 2, (bssw.lat + bsne.lat) / 2);
        }

        parent.setDefaultPoint(defaultPoint.lng, defaultPoint.lat);
        defaultMark = new BMap.Marker(defaultPoint, {icon: defaultIcon });  // 创建标注

        defaultMark.addEventListener("click", function(e){

            map.openInfoWindow(new BMap.InfoWindow('线路客户默认配送点，未设定坐标的客户线路客户将按该坐标进行配送。', {
                width: 100,
                height: 60,
                title: '线路客户默认配送点',
                enableMessage: false
            }), defaultPoint); //开启信息窗口
        });

        // 起始点位置拖动时，重新帮点click事件，防止弹出的信息窗口出现在原来的位置
        defaultMark.addEventListener('dragend', function(e){

            var newLocation = defaultMark.getPosition();
            parent.setDefaultPoint(newLocation.lng, newLocation.lat);
            defaultMark.removeEventListener('click');
            defaultMark.addEventListener('click', function(e){

                map.openInfoWindow(new BMap.InfoWindow('线路客户默认配送点，未设定坐标的客户线路客户将按该坐标进行配送。', {
                    width: 100,
                    height: 60,
                    title: '线路客户默认配送点',
                    enableMessage: false
                }), newLocation);
            });
        });

        map.addOverlay(defaultMark);
        <c:if test="${param.type eq 'edit' }">
            defaultMark.enableDragging();
        </c:if>

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
    function initSelectedCustomerMarker() {

        for(var i in parent.selectedCustomerids) {

            var customerid = parent.selectedCustomerids[i];
            selectCustomer(customerid, true);
            srcCustomerids.push(customerid);
        }

        return true;
    }

    /**
     * 显示客户列表
     * @param flag  true：显示；false：不显示；
     * @returns {boolean}
     */
    function showCustomerList(flag) {

        flag ? $('#selected').show(200) : $('#selected').hide(200);
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
