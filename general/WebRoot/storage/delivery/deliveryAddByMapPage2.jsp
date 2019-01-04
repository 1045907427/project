<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>配送单新增（按地图）</title>
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

        [type=checkbox]{
            cursor: pointer;
        }

        .map-detail-table {
            border-collapse: collapse;
            border: 1px solid #000;
            width: 100%;
        }

        .map-detail-table tr {
            cursor: pointer;
        }

        .map-detail-table th {
            border: 1px solid #000;
            line-height: 28px;
        }

        .map-detail-table td {
            border: 1px solid #000;
            line-height: 28px;
        }

        .statistic-table {
            border-collapse: collapse;
            border: 1px solid #000;
            width: 100%;
            background: #fff;
        }

        .statistic-table th {
            padding: 0px 10px 0px 10px;
            border: 1px solid #000;
            /*text-indent: 5px;*/
        }

        .statistic-table td {
            padding: 5px 5px 5px 5px;
            border: 1px solid #000;
            text-indent: 5px;
        }

        #container {
            height: 100%;
        }

        #storage-orders-deliveryAddByMapPage2 {
            width: 300px;
        }

    </style>
</head>
<body>
<form id="storage-form-deliveryAddByMapPage2">
    <input type="hidden" name="maxvolumn" id="storage-maxvolumn-deliveryAddByMapPage2"/>
    <input type="hidden" name="maxweight" id="storage-maxweight-deliveryAddByMapPage2"/>
    <input type="hidden" name="maxboxnum" id="storage-maxboxnum-deliveryAddByMapPage2"/>
    <input type="hidden" name="lineid" id="storage-lineid-deliveryAddByMapPage2"/>
    <%--<input type="hidden" name="linename" id="storage-linename-deliveryAddByMapPage2"/>--%>
    <input type="hidden" name="carid" id="storage-carid-deliveryAddByMapPage2"/>
    <%--<input type="hidden" name="carname" id="storage-carname-deliveryAddByMapPage2"/>--%>
</form>
<div id="container">
    <div style="margin: 20% auto; text-align: center; color: #F00;">
        地图加载中...
    </div>
</div>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qYlCsNIpqrWPz7MHLjCeTLy5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />

<script type="text/javascript">

    <!--

    var rows = $.parseJSON('${orderStr }'); // 所有单据
    // 获取已选择行
    rows.getSelections = function() {

        var array = new Array();
        for(var i in rows) {
            var row = rows[i];
            if(row.selected == 1) {
                array.push(row);
            }
        }
        return array;
    };
    // 选择行
    rows.checkRow = function(i) {
        rows[i].selected = 1;
    };
    // 取消选择行
    rows.uncheckRow = function(i) {
        rows[i].selected = 0;
    };
    // 选择单据
    rows.checkBill = function(saleoutid) {
        for(var i in rows) {
            var row = rows[i];
            if(row.saleoutid == saleoutid) {
                rows[i].selected = 1;
                return true;
            }
        }
    };
    // 取消选择单据
    rows.uncheckBill = function(saleoutid) {
        for(var i in rows) {
            var row = rows[i];
            if(row.saleoutid == saleoutid) {
                rows[i].selected = 0;
                return true;
            }
        }
    };

    var unShowedCustomerCount = 0;
    var unShowedCustomerBills = new Array();

    var customerLocations = {}; // 客户坐标集
    var lineLocations = {};     // 线路坐标集
    var customerLineMap = {};   // 客户线路映射关系
    var lineCustomerMap = {};   // 线路客户映射关系

    var customerBills = {};
    var customerBillMakers = new Array();
    var lineBills = {};
    var lineBillMakers = new Array();

    var map = null;                     // 地图
    var drawedOverlays = new Array();   // 覆盖物，圆形、多边形、矩形
    var statisticBar = null;            // 统计栏
    var saveBar = null;                 // 下一步

    for(var i in rows) {
        var row = rows[i];
        if(customerBills[row.customerid] == undefined) {
            customerBills[row.customerid] = {customerid: row.customerid, bills: new Array(), location: customerLocations[row.customerid]};
        }
        row.check = false;
        customerBills[row.customerid].bills.push(row);
    }

    // icon样式
    // 选中标记（部分选中）
    var selectedIcon = new BMap.Icon("image/map/map-inline-unselected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 选中标记（全部选中）
    var selectedIconAll = new BMap.Icon("image/map/map-inline-selected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    // 未选中
    var unselectedIcon = new BMap.Icon("image/map/map-outline-unselected.png", new BMap.Size(16, 34), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });

    $(function(){

        customerLocations = $.parseJSON('${customerLocations }');

        <c:forEach items="${lines }" var="line" varStatus="status">lineLocations['${line.id }']={id:'${line.id }',name:'${line.name }',startpoint:'${line.startpoint }',defaultpoint:'${line.defaultpoint }'};</c:forEach>

        ~(function(){
            <c:forEach items="${lineCustomers }" var="lineCustomer" varStatus="status">if(customerLineMap['${lineCustomer.customerid }']==undefined){customerLineMap['${lineCustomer.customerid }']={customerid:'${lineCustomer.customerid }',lines:new Array()}}customerLineMap['${lineCustomer.customerid }'].lines.push({lineid:'${lineCustomer.lineid }',linename:'${lineCustomer.linename }',linelocation:lineLocations['${lineCustomer.lineid }'].defaultpoint});if(lineCustomerMap['${lineCustomer.lineid }']==undefined){lineCustomerMap['${lineCustomer.lineid }']={lineid:'${lineCustomer.lineid }',customers:new Array()}}if(customerLocations['${lineCustomer.customerid }']==undefined){lineCustomerMap['${lineCustomer.lineid }'].customers.push({customerid:'${lineCustomer.customerid }'})};</c:forEach>
        })();

        map = new BMap.Map("container", {enableMapClick: false});

        map.addEventListener("load",function(){

            initCustomerBillsMarkers(customerBills);
            markLineBills(lineBills)
        });

        map.centerAndZoom(new BMap.Point(${center }), 9);
        map.enableScrollWheelZoom(true);

        // 覆盖物工具
        ~(function(){

            var overlays = [];

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

                for(var i = 0; i < lineBillMakers.length; i++) {

                    var marker = lineBillMakers[i];
                    var position = marker.getPosition();
                    var lineid = marker.getTitle().getId();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        checkDatagridLine(lineid, true);
                        marker.setIcon(selectedIconAll);
                        lineBills[lineid].selectCount = lineBills[lineid].bills.length || 99999999;
                    }
                }

                for(var i = 0; i < customerBillMakers.length; i++) {

                    var marker = customerBillMakers[i];
                    var position = marker.getPosition();
                    var customerid = marker.getTitle().getId();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        checkDatagridCustomer(customerid, true);
                        marker.setIcon(selectedIconAll);
                        customerBills[customerid].selectCount = customerBills[customerid].bills.length || 99999999;
                    }
                }

                refreshStatisticBar();
            });

            // 矩形覆盖物绘制完成触发
            drawingManager.addEventListener('rectanglecomplete', function(polygon) {

                // 将绘制的覆盖物替换成普通覆盖物
                clearAll();
                map.addOverlay(polygon);
                drawedOverlays.push(polygon);

                for(var i = 0; i < lineBillMakers.length; i++) {

                    var marker = lineBillMakers[i];
                    var position = marker.getPosition();
                    var lineid = marker.getTitle().getId();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        checkDatagridLine(lineid, true);
                        marker.setIcon(selectedIconAll);
                        lineBills[lineid].selectCount = lineBills[lineid].bills.length || 99999999;
                    }
                }

                for(var i = 0; i < customerBillMakers.length; i++) {

                    var marker = customerBillMakers[i];
                    var position = marker.getPosition();
                    var customerid = marker.getTitle().getId();

                    var inPolygon = isPointInPolygon(position, polygon.getPath());
                    if(inPolygon) {

                        checkDatagridCustomer(customerid, true);
                        marker.setIcon(selectedIconAll);
                        customerBills[customerid].selectCount = customerBills[customerid].bills.length || 99999999;
                    }
                }
                refreshStatisticBar();
            });

            // 圆形覆盖物绘制完成触发
            drawingManager.addEventListener('circlecomplete', function(circle) {

                // 将绘制的覆盖物替换成普通覆盖物
                clearAll();
                map.addOverlay(circle);
                drawedOverlays.push(circle);

                for(var i = 0; i < lineBillMakers.length; i++) {

                    var marker = lineBillMakers[i];
                    var position = marker.getPosition();
                    var lineid = marker.getTitle().getId();

                    var inCircle = isPointInCircle(position, circle);
                    if(inCircle) {

                        checkDatagridLine(lineid, true);
                        marker.setIcon(selectedIconAll);
                        lineBills[lineid].selectCount = lineBills[lineid].bills.length || 99999999;
                    }
                }

                for(var i = 0; i < customerBillMakers.length; i++) {

                    var marker = customerBillMakers[i];
                    var position = marker.getPosition();
                    var customerid = marker.getTitle().getId();

                    var inCircle = isPointInCircle(position, circle);
                    if(inCircle) {

                        checkDatagridCustomer(customerid, true);
                        marker.setIcon(selectedIconAll);
                        customerBills[customerid].selectCount = customerBills[customerid].bills.length || 99999999;
                    }
                }
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

        // 自定义工具栏
        ~(function(){

            // 定义一个控件类,即function
            function CustomControl(){
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(0, 0);
            }

            // 通过JavaScript的prototype属性继承于BMap.Control
            CustomControl.prototype = new BMap.Control();

            // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
            // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
            CustomControl.prototype.initialize = function(map){

                // 创建一个DOM元素
                $('<div id="customControl" style="transform: scale(0.8); background-color: #fbfbfb;  border: solid 1px gray; border-radius: 5px;">' +
                        '<a onclick="javascript:setLine();" style="border-right: solid 1px gray; float: left; display: block; width: 64px; height: 47px; background: url(image/map/map-truck.png) no-repeat; background-size: 48px 48px; background-position: 9px 0px;" href="javascript:void(0)" title="指定线路/车辆" onfocus="this.blur()"></a>' +
                        '<a onclick="javascript:clearSelection();" style="border-right: solid 1px gray; float: left; display: block; width: 64px; height: 47px; background: url(image/map/map-refresh.png) no-repeat; background-size: 32px 32px; background-position: 16px 8px;" href="javascript:void(0)" title="清除选择" onfocus="this.blur()"></a>' +
                        '<a onclick="javascript:clearOverlays();" style="float: left; display: block; width: 64px; height: 47px; background: url(image/map/map-cancel.png) no-repeat; background-size: 32px 32px; background-position: 16px 8px;" href="javascript:void(0)" title="清除覆盖物" onfocus="this.blur()"></a>' +
                        '</div>').appendTo('body');

                // 添加DOM元素到地图中
                map.getContainer().appendChild($('#customControl')[0]);
                return $('#customControl')[0];
            };

            // 创建控件并添加到地图当中
            map.addControl(new CustomControl());
        })();

        // 保存按钮
        ~(function(){

            // 定义一个控件类,即function
            function SaveControl(){
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
                this.defaultOffset = new BMap.Size(26, 0);
            }

            // 通过JavaScript的prototype属性继承于BMap.Control
            SaveControl.prototype = new BMap.Control();

            // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
            // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
            SaveControl.prototype.initialize = function(map){

                // 创建一个DOM元素
                $('<div id="saveControl" style="transform: scale(0.8); background-color: #fbfbfb;  border: solid 1px gray; border-radius: 5px;">' +
                        '<a onclick="javascript:generateDelivery();" style="float: left; display: block; width: 64px; height: 47px; background: url(image/map/map-next.png) no-repeat; background-size: 36px 36px; background-position: 16px 5px;" href="javascript:void(0)" title="下一步" onfocus="this.blur()"></a>' +
                        '</div>').appendTo('body');

                // 添加DOM元素到地图中
                map.getContainer().appendChild($('#saveControl')[0]);
                return $('#saveControl')[0];
            };

            // 创建控件并添加到地图当中
            map.addControl(new SaveControl());
        })();

        refreshStatisticBar();
    });

    /**
     *
     * @param cb
     * @returns {boolean}
     */
    function initCustomerBillsMarkers(cb) {

        for(var key in cb) {

            var billObject = cb[key];
            var flag = markBill(billObject);
            if(!flag) {
                if(billObject.customerid) {
                    for(var i in billObject.bills) {
                        unShowedCustomerBills.push(billObject.bills[i]);
                    }
                    unShowedCustomerCount++;
                }
            }
        }
        unShowedCustomerBills.sort(function(o1, o2) {
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
        return true;
    }

    /**
     * 标注单据
     */
    function markBill(billObject) {

        var customerid = billObject.customerid;
        var bills = billObject.bills;
        var customerLocation = customerLocations[customerid];
        var selectCount = billObject.selectCount || 0;

        for(var i in customerBillMakers) {

            var marker = customerBillMakers[i];
            if(customerid == marker.getTitle().getId()) {
                return true;
            }
        }

        // 客户坐标存在
        if(customerLocation != null) {

            var icon = unselectedIcon;
            if(selectCount >= bills.length) {
                icon = selectedIconAll;
            } else if(selectCount > 0) {
                icon = selectedIcon;
            } else if(selectCount == 0) {
                icon = unselectedIcon;
            }

            var point = new BMap.Point(customerLocation.lng, customerLocation.lat);
            var customerMarker = new BMap.Marker(point, {icon: icon});  // 创建标注

            customerMarker.setTitle(customerLocation.customerid + ': ' + customerLocation.name);

            (function(marker){

                marker.addEventListener("click", function(e){

                    var selectCount = billObject.selectCount || 0;
                    var html = new Array();
                    html.push('<div style="overflow-y: scroll; height: 172px;">');
                    html.push('<table class="map-detail-table">');
                    html.push('<tr style="background-color: #dedede;">');
                    html.push('<th><input type="checkbox" customerid="' + customerid + '" index="0" ' + (selectCount >= bills.length ? 'checked="checked"' : '') + '/></th>');
                    html.push('<th>编号</th>');
                    html.push('<th>业务日期</th>');
                    html.push('<th>销售额</th>');
                    html.push('<th>箱数</th>');
                    html.push('<th>重量</th>');
                    html.push('<th>体积</th>');
                    html.push('<th>状态</th>');
                    html.push('<th>来源</th>');
                    html.push('</tr>');

                    var index = 1;
                    var selectedRows = rows.getSelections();

                    for(var i in bills) {

                        var bill = bills[i];
                        var billCheck = (billObject.selectCount >= bills.length);
                        for(var j in selectedRows) {

                            var selectedRow = selectedRows[j];
                            if(bill.saleoutid == selectedRow.saleoutid) {
                                billCheck = true;
                                break;
                            }
                        }

                        html.push('<tr style="">');
                        html.push('<td><input type="checkbox" customerid="' + customerid + '" index="' + (index++) + '"' + (billCheck ? 'checked="checked"' : '') + ' billid="' + bill.saleoutid + '"/></td>');
                        html.push('<td>' + bill.saleoutid + '</td>');
                        html.push('<td>' + bill.businessdate + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.salesamount) + '</td>');
                        html.push('<td class="right">' + formatterBigNumNoLen(bill.boxnum) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.weight) + 'kg</td>');
                        html.push('<td class="right">' + formatterMoney(bill.volume, 4) + 'm<sup>3</sup></td>');
                        html.push('<td>' + getSysCodeName('status', bill.status) + '</td>');
                        html.push('<td>' + getBillName(bill.deliverytype)  + '</td>');
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
                        $('[customerid=' + customerid + ']').off('customClick');

                        // 绑定
                        $('[customerid=' + customerid + ']').on('customClick', function(e) {

                            var index = $(this).attr('index');
                            var check = $(this).is(':checked');

                            if(index == '0') {
                                checkDatagridCustomer(customerid, check);
                            }

                            // 头部checkbox状态
                            if(check && index == '0') {

                                $('[type=checkbox][customerid=' + customerid + ']').attr('checked', 'checked');
                                return true;
                            } else if(!check && index == '0') {

                                $('[type=checkbox][customerid=' + customerid + ']').removeAttr('checked');
                                return true;
                            }

                            var billid = $(this).attr('billid');
                            checkDatagridBill(billid, check);
                            if(check) {
                                customerBills[customerid].selectCount = parseInt(customerBills[customerid].selectCount || 0) + 1;
                            } else {
                                customerBills[customerid].selectCount = parseInt(customerBills[customerid].selectCount || 0) - 1;
                            }

                            // 行checkbox状态(行是否全选)
                            var allcheck = true;
                            $('[customerid=' + customerid + ']:gt(0)').each(function(index, item) {
                                allcheck = allcheck && $(this).is(':checked');
                            });

                            // 全选
                            if(allcheck) {
                                $('[customerid=' + customerid + ']:eq(0)').attr('checked', 'checked');
                                return true;
                            }

                            // 非全选
                            $('[customerid=' + customerid + ']:eq(0)').removeAttr('checked');
                            return true;
                        });
                    });

                    // close事件
                    infoWindow.addEventListener('close', function(e) {

                        var customerid = marker.getTitle().getId();
                        var selectCount = customerBills[customerid].selectCount;
                        var bills = customerBills[customerid].bills;
                        if(selectCount == undefined || selectCount <= 0) {
                            marker.setIcon(unselectedIcon);
                        } else if(selectCount >= bills.length) {
                            marker.setIcon(selectedIconAll);
                        } else {
                            marker.setIcon(selectedIcon);
                        }

                    });
                    map.openInfoWindow(infoWindow, marker.getPosition()); //开启信息窗口

                });

            })(customerMarker);

            map.addOverlay(customerMarker);
            customerBillMakers.push(customerMarker);
            return true;
        }

        var tempMap = customerLineMap[customerid];
        if(tempMap) {

            var marked = false;
            var lines = tempMap.lines;
            for(var i in lines) {

                var line = lines[i];
                if(line.linelocation) {

                    marked = true;
                    if(lineBills[line.lineid] == undefined) {

                        lineBills[line.lineid] = {
                            lineid: line.lineid,
                            linename: line.linename,
                            location: lineLocations[line.lineid],
                            bills: new Array()
                        };
                    }
                    lineBills[line.lineid].bills = billObject.bills;
                }
            }

            return marked;
        }
        return false;
    }

    /**
     * 标注线路单据
     */
    function markLineBills(lbs) {

        for(var key in lbs) {

            var lineBill = lbs[key];
            markLineBill(lineBill);
        }

       return true;
    }

    /**
     * 标注线路单据
     */
    function markLineBill(lineBill) {

        var lineid = lineBill.lineid;
        var linename = lineBill.linename;
        var bills = lineBill.bills;
        var location = lineBill.location;
        var selectCount = lineBills[lineid].selectCount;

        if(location) {

            var point = new BMap.Point(location.defaultpoint.split(',')[0], location.defaultpoint.split(',')[1]);

            // icon图标样式
            var icon = unselectedIcon;
            if(selectCount > 0 & selectCount < bills.length) {
                icon = selectedIcon;
            } else if(selectCount >= bills.length) {
                icon = selectedIconAll;
            }
            var lineMarker = new BMap.Marker(point, {icon: icon});  // 创建标注

            lineMarker.setTitle(lineid + ': ' + linename);

            (function(marker, bills, id, name) {

                marker.addEventListener('click', function(e){

                    var selectCount = selectCount || lineBill.selectCount;

                    var html = new Array();
                    html.push('<div style="overflow-y: scroll; height: 172px;">');
                    html.push('<table class="map-detail-table">');
                    html.push('<tr style="background-color: #dedede;">');
                    html.push('<th style="width: 25px;" align="center"><input type="checkbox" lineid="' + id + '" index="0" ' + (selectCount >= bills.length ? 'checked="checked"' : '') + '/></th>');
                    html.push('<th style="width: 122px;">编号</th>');
                    html.push('<th style="width: 64px;">业务日期</th>');
                    html.push('<th style="width: 150px;">客户</th>');
                    html.push('<th style="width: 60px;">销售额</th>');
                    html.push('<th style="width: 50px;">箱数</th>');
                    html.push('<th style="width: 60px;">重量</th>');
                    html.push('<th style="width: 60px;">体积</th>');
                    html.push('<th style="width: 50px;">状态</th>');
                    html.push('<th style="width: 65px;">来源</th>');
                    html.push('</tr>');

                    var index = 1;
                    var selectedRows = rows.getSelections();
                    for(var i in bills) {

                        var bill = bills[i];

                        var billCheck = (selectCount >= bills.length);
                        for(var j in selectedRows) {

                            var selectedRow = selectedRows[j];
                            if(bill.saleoutid == selectedRow.saleoutid) {
                                billCheck = true;
                                break;
                            }
                        }

                        html.push('<tr style="">');
                        html.push('<td align="center"><input type="checkbox" lineid="' + id + '" index="' + (index++) + '"' + (billCheck ? 'checked="checked"' : '') + ' billid="' + bill.saleoutid + '"/></td>');
                        html.push('<td>' + bill.saleoutid + '</td>');
                        html.push('<td>' + bill.businessdate + '</td>');
                        html.push('<td>' + (bill.customerid + ': ' + bill.customername) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.salesamount) + '</td>');
                        html.push('<td class="right">' + formatterBigNumNoLen(bill.boxnum) + '</td>');
                        html.push('<td class="right">' + formatterMoney(bill.weight) + 'kg</td>');
                        html.push('<td class="right">' + formatterMoney(bill.volume, 4) + 'm<sup>3</sup></td>');
                        html.push('<td>' + getSysCodeName('status', bill.status) + '</td>');
                        html.push('<td>' + getBillName(bill.deliverytype)  + '</td>');
                        html.push('</tr>');
                    }

                    html.push('</table>');
                    html.push('</div>');

                    var info = html.join('');
                    var infoWindow = new BMap.InfoWindow(info, {
                        width: 880,
                        height: 200,
                        title: '(' +id + ')' + name,
                        enableMessage: false
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
                        $('[lineid=' + id + ']').off('customClick');

                        // 绑定
                        $('[lineid=' + id + ']').on('customClick', function(e) {

                            var index = $(this).attr('index');
                            var check = $(this).is(':checked');

                            if(index == '0') {
                                checkDatagridLine(id, check);
                            }

                            // 头部checkbox状态
                            if(check && index == '0') {
                                $('[type=checkbox][lineid=' + id + ']').attr('checked', 'checked');
                                lineBills[id].selectCount = lineBills[id].bills.length;
                                return true;
                            } else if(!check && index == '0') {
                                $('[type=checkbox][lineid=' + id + ']').removeAttr('checked');
                                lineBills[id].selectCount = 0;
                                return true;
                            }

                            var billid = $(this).attr('billid');
                            checkDatagridBill(billid, check);
                            if(check) {
                                lineBills[id].selectCount = parseInt(lineBills[id].selectCount || 0) + 1;
                            } else {
                                lineBills[id].selectCount = parseInt(lineBills[id].selectCount || 0) - 1;
                            }

                            // 行checkbox状态(行是否全选)
                            var allcheck = true;
                            $('[lineid=' + id + ']:gt(0)').each(function(index, item) {
                                allcheck = allcheck && $(this).is(':checked');
                            });

                            // 全选
                            if(allcheck) {
                                $('[lineid=' + id + ']:eq(0)').attr('checked', 'checked');
                                return true;
                            }

                            // 非全选
                            $('[lineid=' + id + ']:eq(0)').removeAttr('checked');
                            return true;
                        });
                    });

                    // close事件
                    infoWindow.addEventListener('close', function(e) {

                        var lineid = marker.getTitle().getId();
                        var selectCount = lineBills[lineid].selectCount;
                        var bills = lineBills[lineid].bills;
                        if(selectCount == undefined || selectCount <= 0) {
                            lineMarker.setIcon(unselectedIcon);
                        } else if(selectCount >= bills.length) {
                            lineMarker.setIcon(selectedIconAll);
                        } else {
                            lineMarker.setIcon(selectedIcon);
                        }
                    });
                    map.openInfoWindow(infoWindow, marker.getPosition()); //开启信息窗口

                });
            })(lineMarker, bills, lineid, linename);


            map.addOverlay(lineMarker);
            lineBillMakers.push(lineMarker);
        }
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
     * 选中单据
     *
     * @param saleoutid
     * @param checkFlag true:选中； false:不选
     * @returns {boolean}
     */
    function checkDatagridBill(saleoutid, checkFlag) {

        for(var i = 0; i < rows.length; i++) {

            var row = rows[i];
            if(row.saleoutid == saleoutid) {
                checkFlag ? rows.checkRow(i) : rows.uncheckRow(i);
                break;
                return true;
            }
        }
        refreshStatisticBar();
        return true;
    }

    /**
     * 选中客户所有单据
     *
     * @param customerid
     * @param checkFlag true:选中； false:不选
     * @returns {boolean}
     */
    function checkDatagridCustomer(customerid, checkFlag) {

        for(var i = 0; i < rows.length; i++) {

            var row = rows[i];
            if(row.customerid == customerid) {
                checkFlag ? rows.checkRow(i) : rows.uncheckRow(i);
            }
        }

        customerBills[customerid].selectCount = checkFlag ? customerBills[customerid].bills.length : 0;
        return true;
    }

    /**
     * 选中线路所有单据
     *
     * @param lienid
     * @param checkFlag true:选中； false:不选
     * @returns {boolean}
     */
    function checkDatagridLine(lineid, checkFlag) {

        var customers = lineCustomerMap[lineid].customers;
        for(var i in customers) {

            var customer = customers[i];
            var customerid = customer.customerid;

            for(var j = 0; j < rows.length; j++) {

                var row = rows[j];
                if(row.customerid == customerid) {
                    checkFlag ? rows.checkRow(j) : rows.uncheckRow(j)
                }
            }
        }

        lineBills[lineid].selectCount = checkFlag ? lineBills[lineid].bills.length : 0;
        refreshStatisticBar();
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

        return parseFloat(distance) < parseFloat(radius);
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
     * 设定线路车辆
     *
     * @returns {boolean}
     */
    function setLine() {

        parent.setLine();
        return true;
        var did = 'd' + getRandomid();
        $('<div id="' + did + '"></div>').appendTo('body');

        var html = new Array();
        html.push('<table id="storage-table-deliveryAddByMapPage2">');
        html.push('<tr>');
        html.push('<td>线路：</td>');
        html.push('<td><input type="text" name="lineid" id="storage-lineid-deliveryAddByMapPage2"/></td>');
        html.push('</tr>');
        html.push('<tr>');
        html.push('<td>车辆：</td>');
        html.push('<td><input type="text" name="carid" id="storage-carid-deliveryAddByMapPage2" readonly="readonly" style="width: 250px;"/></td>');
        html.push('</tr>');
        html.push('</table>');


        $('#' + did).dialog({
            maximizable: false,
            resizable: false,
            title: '指定线路/车辆',
            width: 350,
            height: 170,
            closed: false,
            cache: false,
            modal: true,
            content: html.join(''),
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){

                    var flag = $('#storage-form-deliveryAddByMapPage2').form('validate');
                    if(!flag){
                        return false;
                    }

                    $('#' + did).dialog('close');
                    refreshStatisticBar();
                    $('#' + did).dialog('destroy');
                }
            }],
            onClose:function(){
               $('#' + did).dialog('destroy');
            }
        });

        return true;
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

        function StatisticBar(){

            // 默认停靠位置和偏移量
            this.defaultAnchor = BMAP_ANCHOR_BOTTOM_LEFT;
            this.defaultOffset = new BMap.Size(5, 35);
        }

        // 通过JavaScript的prototype属性继承于BMap.Control
        StatisticBar.prototype = new BMap.Control();

        // initialize方法,
        StatisticBar.prototype.initialize = function(map){

            var v = 0;
            var w = 0;
            var b = 0;

            var selectedRows = rows.getSelections();
            for(var i in selectedRows) {

                var selectedRow = selectedRows[i];
                v = v + parseFloat(selectedRow.volume || 0);
                w = w + parseFloat(selectedRow.weight || 0);
                b = b + parseFloat(selectedRow.boxnum || 0);
            }

            var mv = $('#storage-maxvolumn-deliveryAddByMapPage2').val() || 999999.9999;
            var mw = $('#storage-maxweight-deliveryAddByMapPage2').val() || 999999.99;
            var mb = $('#storage-maxboxnum-deliveryAddByMapPage2').val() || 999999;

            var overvolume = parseFloat(v) > parseFloat(mv);
            var overweight = parseFloat(w) > parseFloat(mw);
            var overboxnum = parseFloat(b) > parseFloat(mb);
            if(overvolume) {
                $.messager.alert('提醒', '已超体积！');
            }

            var html = new Array();
            html.push('<div id="statisticbar">');
            html.push('<table class="statistic-table">');
            html.push('<tr><th align="center">线路</th><td>' + (parent.$('#storage-lineid-deliveryAddByMapPage').widget('getText') || '未选择') + '</td></tr>');
            html.push('<tr><th align="center">车辆</th><td>' + (parent.$('#storage-carid-deliveryAddByMapPage').widget('getText') || '未选择') + '</td></tr>');
            html.push('<tr align="center" style="color: ' + (overvolume ? '#F00' : '#000') + ';"><th>体积</th><td>' + formatterMoney(v, 4) + '/' + formatterMoney(mv, 4) + '</td></tr>');
            html.push('<tr align="center" style="color: ' + (overweight ? '#F00' : '#000') + ';"><th>重量</th><td>' + formatterMoney(w) + '/' + formatterMoney(mw) + '</td></tr>');
            html.push('<tr align="center" style="color: ' + (overboxnum ? '#F00' : '#000') + ';"><th>箱数</th><td>' + (formatterMoney(b, 3) || '0') + '/' + (formatterMoney(mb, 3) || '0') + '</td></tr>');
            html.push('<tr align="center" style="color: ' + (unShowedCustomerCount == 0 ? '#080' : '#F00') + ';"><th>未显示<br/>客户数</th><td>' + (unShowedCustomerCount > 0 ? '<a href="javascript:void(0);" onclick="javascript:showUnshowedBills();">' + unShowedCustomerCount + '</a>' : 0) + '</td></tr>');
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

    /**
     * 下一步
     *
     * @returns {boolean}
     */
    function generateDelivery() {

        parent.loading('正在处理...');
        var lineid = $('#storage-lineid-deliveryAddByMapPage2').val() || '';
        var carid = $('#storage-carid-deliveryAddByMapPage2').val() || '';
        if(lineid == '') {
            $.messager.alert('提醒', '未指定线路！');
            setLine();
            parent.loaded();
            return false;
        }
        if(carid == '') {
            $.messager.alert('提醒', '未指定车辆！');
            setLine();
            parent.loaded();
            return false;
        }

        var selectedRows = rows.getSelections();
        if(selectedRows == null || selectedRows.length == 0){
            $.messager.alert('提醒', '未选择单据！');
            parent.loaded();
            return false;
        }

        parent.$('#storage-datagrid-deliveryAddByMapPage').datagrid('loadData', selectedRows);

        var v = 0;
        var w = 0;
        var b = 0;
        var salesamount = 0;

        for(var i in selectedRows) {

            var selectedRow = selectedRows[i];
            v = v + parseFloat(selectedRow.volume || 0);
            w = w + parseFloat(selectedRow.weight || 0);
            b = b + parseFloat(selectedRow.boxnum || 0);
            salesamount = salesamount + parseFloat(selectedRow.salesamount || 0);
        }

        var mv = $('#storage-maxvolumn-deliveryAddByMapPage2').val() || 9999.9999;
        var mw = $('#storage-maxweight-deliveryAddByMapPage2').val() || 999999.99;
        var mb = $('#storage-maxboxnum-deliveryAddByMapPage2').val() || 9999;

        var footer1 = {
            businessdate: '空余重量',
            orderid: formatterMoney(mw - w) + ' kg',
            customerid: '空余体积',
            customername: formatterMoney(mv - v, 4) + ' m³',
            salesamount: formatterMoney(salesamount),
            boxnum: b,
            weight: w,
            volume: v
        };
        var footer2 = {
            businessdate: '装载限重',
            orderid: formatterMoney(mw) + ' kg',
            customerid: '装载体积',
            customername: formatterMoney(mv, 4) + ' m³'
        };

        parent.$('#storage-datagrid-deliveryAddByMapPage').datagrid('reloadFooter', [footer1, footer2]);

        parent.$('#storage-dialog2-deliveryAddByMapPage').dialog({
            maximizable: false,
            resizable: false,
            title: '确定将以下单据生成配送单？',
            closed: false,
            cache: false,
            modal: true,
            maximized: true,
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){

                    var lineid = $('#storage-lineid-deliveryAddByMapPage2').val();
                    var carid = $('#storage-carid-deliveryAddByMapPage2').val();
                    var rows = parent.$('#storage-datagrid-deliveryAddByMapPage').datagrid('getRows');
                    var idArr = new Array();

                    for(var i in rows) {
                        var row = rows[i];
                        idArr.push(row.saleoutid);
                    }

                    if(idArr.length == 0) {
                        $.messager.alert('提醒', '单据未选择！');
                        return true;
                    }

                    loading('提交中...');
                    $.ajax({
                        url: 'storage/delivery/addDeliveryList.do',
                        dataType: 'json',
                        type: 'post',
                        data: {ids: idArr.join(','), lineid: lineid, carid: carid, start: ''},
                        success:function(json){

                            loaded();
                            if(json.flag){

                                $.messager.alert('提醒','生成成功');
                                $('#storage-datagrid-deliverySourcePage').datagrid('reload');
                                var flag = isDoLockData(json.id,'t_storage_logistics_delivery');
                                if(!flag){
                                    $.messager.alert('警告','该数据正在被其他人操作，暂不能修改！');
                                    return false;
                                }
                                setTimeout(function(){parent.location.href = parent.location.href;}, 20);
                                top.addOrUpdateTab('storage/delivery/showDeliveryPage.do?id='+ json.id+'&type=edit', '配送单查看');

                            }else{
                                $.messager.alert('提醒','生成失败<br/>');
                            }
                        },
                        error:function(){
                            loaded();
                            $.messager.alert('错误','生成出错！');
                        }
                    });
                }
            }]
        });

        parent.loaded();
    }

    /**
     * 加锁
     */
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

    String.prototype.getId = function() {

        if(this.indexOf(':')) {
            return this.split(':')[0];
        }
        return '';
    };

    /**
     * 显示未显示单据
     *
     * @param rows
     * @returns {boolean}
     */
    function showUnshowedBills() {

        parent.$('#storage-datagrid2-deliveryAddByMapPage').datagrid('loadData', unShowedCustomerBills);

        parent.$('#storage-dialog3-deliveryAddByMapPage').dialog({
            maximizable: false,
            resizable: false,
            title: '地图未显示单据',
            closed: false,
            cache: false,
            modal: true,
            maximized: true,
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){
                    parent.$('#storage-dialog3-deliveryAddByMapPage').dialog('close');
                    refreshStatisticBar();
                }
            }]
        });
        return true;
    }

    /**
     * 清除选择
     * @returns {boolean}
     */
    function clearSelection() {

        parent.loading('正在清除...');

        setTimeout(function() {

            for(var i in rows) {
                rows.uncheckRow(i);
            }

            parent.$('#storage-datagrid2-deliveryAddByMapPage').datagrid('unselectAll');

            map.clearOverlays();

            unShowedCustomerCount = 0;
            unShowedCustomerBills = new Array();

            customerBillMakers = new Array();
            lineBillMakers = new Array();

            for(var i in customerBills) {

                var customerBill = customerBills[i];
                customerBill.selectCount = 0;
                for(var j in customerBill.bills) {
                    customerBill.bills[j].selected = '0';
                }
            }

            for(var i in lineBills) {

                var lineBill = lineBills[i];
                lineBill.selectCount = 0;
                for(var j in lineBill.bills) {
                    lineBill.bills[j].selected = '0';
                }
            }

            initCustomerBillsMarkers(customerBills);
            markLineBills(lineBills);
            refreshStatisticBar();
            parent.loaded();

        }, 100);
        return true;
    }

    -->

</script>
</body>
</html>
