<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户地图</title>
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
        ul > li:nth-child(even) {
            background: #CCC
        }
        ul > li:nth-child(odd) {
            background: #eee
        }
        li > div {
            line-height: 20px;
        }
        li[index] > div {
            line-height: 20px;
            cursor: pointer;
        }
        li:hover {
            background: #888;
        }
    </style>
</head>
<body>
    <c:choose>
        <c:when test="${empty candidates }">
        </c:when>
        <c:otherwise>
            <div id="candidates" style="float: left; width: 230px; padding: 20px 0px 20px 0px; overflow-y: scroll; height: 50%;">
                <div>
                    <div style="padding: 5px; font-weight: bold;">相似地址：</div>
                    <ul style="padding: 0px;">
                        <c:forEach items="${candidates }" var="item" varStatus="status">
                            <li index="${status.index }" style="padding: 15px 5px 15px 5px; list-style-type: none;">
                                <div onclick="javascript:selectLocation(${status.index });">
                                    <div style="color: #1882cf;"><c:out value="${item.name }"/></div>
                                    <div style="color: #555;"><c:out value="${item.address }"/></div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
<div id="container">
    <div>
        地图加载中...
    </div>
</div>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qYlCsNIpqrWPz7MHLjCeTLy5"></script>
<%--<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>--%>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />

<script type="text/javascript">

    <!--

    var candidates = new Array();   // 候选

    var selectedMarker = null;
    var candidateMarkers = new Array();
    var customLocationMarker = null;
    var baseMark = null;

    // mark
    // 普通标记
    var normalIcon = new BMap.Icon("image/map/map-location.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    var targetIcon = new BMap.Icon("image/map/map-inline-unselected.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    var selectedIcon = new BMap.Icon("image/map/map-inline-unselected.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });
    var baseIcon = new BMap.Icon("image/map/map-location-marked.png", new BMap.Size(16, 32), {
        offset: new BMap.Size(0, 0),
        imageOffset: new BMap.Size(0, 0)
    });

    var map = new BMap.Map("container", {enableMapClick: false});
    var geoc = new BMap.Geocoder();

    $(function(){

        // 可缩放
        map.enableScrollWheelZoom(true);

        var lng = 0;
        var lat = 0;
        var pointsNum = 0;
        <c:forEach items="${candidates }" var="item" varStatus="status">
            markLocation('${item.location.lng }', '${item.location.lat }', '${item.name }', ${status.index });
            candidates.push({lng: '${item.location.lng }', lat: '${item.location.lat }', name: '${item.name }', address: '${item.address }'});
            lng = lng + parseFloat('${item.location.lng }');
            lat = lat + parseFloat('${item.location.lat }');
            pointsNum++;
        </c:forEach>

        if(pointsNum > 0) {
            var averageLng = parseFloat(lng) / pointsNum;
            var averageLat = parseFloat(lat) / pointsNum;
            map.centerAndZoom(new BMap.Point(averageLng, averageLat), 9);
        } else {
            map.centerAndZoom(new BMap.Point(${center }), 9);
        }

        // 工具栏
        <security:authorize url="/basefiles/editCustomerLocation.do">
            ~(function(){

            // 定义一个控件类,即function
            function CustomControl(){
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(50, 50);
            }

            // 通过JavaScript的prototype属性继承于BMap.Control
            CustomControl.prototype = new BMap.Control();

            // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
            // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
            CustomControl.prototype.initialize = function(map){

                // 创建一个DOM元素
                $('<div id="customControl" class="BMapLib_Drawing BMap_noprint anchorTR" style="transform: scale(0.8); position: absolute; background-color: #fbfbfb; top: 0px;">' +
                        '<div class="BMapLib_Drawing_panel" style="">' +
                        '<a class="BMapLib_box" onclick="javascript:markCustomLocation();" style="border: solid 1px gray; height: 47px; background: url(image/map/map-add-start.png) no-repeat; background-size: 42px 42px; background-position: 10px 2px;" href="javascript:void(0)" title="添加自定义位置" onfocus="this.blur()"></a>' +
                        '<a class="BMapLib_box" onclick="javascript:showSearchDialog();" style="border: solid 1px gray; height: 47px; background: url(image/map/map-search.png) no-repeat; background-size: 35px 35px; background-position: 14px 6px;" href="javascript:void(0)" title="自定义查询" onfocus="this.blur()"></a>' +
                        '<a class="BMapLib_box" onclick="javascript:refreshLocationMarker();" style="border: solid 1px gray; height: 47px; background: url(image/map/map-refresh.png) no-repeat; background-size: 34px 34px; background-position: 15px 6px;" href="javascript:void(0)" title="清除覆盖物" onfocus="this.blur()"></a>' +
                        '</div>' +
                        '</div>').appendTo('body');

                // 添加DOM元素到地图中
                map.getContainer().appendChild($('#customControl')[0]);
            };

            // 创建控件并添加到地图当中
            map.addControl(new CustomControl());
        })();
        </security:authorize>

        // 原始位置
        <c:if test="${not empty location and not empty location.location}">
            ~(function() {

            var point = new BMap.Point(${location.location });

            baseMark = new BMap.Marker(point, {icon: baseIcon});
            baseMark.setTitle('<c:out value="${customer.name }"/>');
            baseMark.addEventListener('click', function(e){

                var currentPoint = baseMark.getPosition();
                geoc.getLocation(currentPoint, function(rs){

                    var addComp = rs.addressComponents;
                    var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber + '附近';
                    var info = address;
                    map.openInfoWindow(new BMap.InfoWindow(info, {
                        width: 100,
                        height: 70,
                        title: '<c:out value="${customer.name }"/>',
                        enableMessage: false
                    }), currentPoint);
                });
            });

            map.addOverlay(baseMark);

            // 中心点
            map.centerAndZoom(point, 13);
        })();
        </c:if>

        $('#candidates').height($('#container').height() - 40);
    });

    /**
     * 清除所有标记的客户
     */
    function clearLocationMarks() {

        for(var i in candidateMarkers) {

            map.removeOverlay(candidateMarkers[i]);
        }

        candidateMarkers = new Array();
        return true;
    }

    function initLocationMarks(candidates) {

        clearLocationMarks();

        for(var i in candidates) {

            var candidate = candidates[i];
            (function(index, lng, lat, name) {
                markLocation(lng, lat, name, index);
            })(i, candidate.lng, candidate.lat, candidate.name);
        }

    }

    /**
     * 选中标注
     *
     * @param index
     */
    function selectLocation(index) {

        if(index < 0) {
            return false;
        }

        if(customLocationMarker) {
            map.removeOverlay(customLocationMarker);
            customLocationMarker = null;
        }

        map.closeInfoWindow();
        initLocationMarks(candidates);
        removeMarker(index);
        if(selectedMarker != null) {
            map.removeOverlay(selectedMarker);
            selectedMarker = null;
        }

        var location = candidates[index];

        map.centerAndZoom(new BMap.Point(location.lng, location.lat), map.getZoom() > 13 ? map.getZoom() : 13);

        // 创建点坐标
        var point = new BMap.Point(location.lng, location.lat);

        var marker = new BMap.Marker(point, {icon: selectedIcon});  // 创建标注

        marker.setTitle(location.name);

        (function (i, m, name) {

            m.addEventListener("click", function(e){

                var info = name + '<br/><a href="javascript:void(0);" onclick="javascript:unselectLocation(' + i + ');">取消选中</a>';
                map.openInfoWindow(new BMap.InfoWindow(info, {
                    width: 100,
                    height: 50,
                    title: '相似位置：',
                    enableMessage: false
                }), point); //开启信息窗口
            });
        })(index, marker, location.name);

        map.addOverlay(marker);
        selectedMarker = marker;
    }

    /**
     * 标注客户
     *
     * @param lng
     * @param lat
     * @param name
     * @param index
     */
    function markLocation(lng, lat, name, index) {

        // 创建点坐标
        var point = new BMap.Point(lng, lat);

        var normalMark = new BMap.Marker(point, {icon: normalIcon});  // 创建标注

        normalMark.setTitle(name);

        (function (i, m) {

            m.addEventListener("click", function(e){

                var info = name;
                <security:authorize url="/basefiles/editCustomerLocation.do">
                    info = info + '<br/><a href="javascript:void(0);" onclick="javascript:selectLocation(' + i + ');">选中</a>';
                </security:authorize>
                map.openInfoWindow(new BMap.InfoWindow(info, {
                    width: 100,
                    height: 50,
                    title: '相似位置：',
                    enableMessage: false
                }), point);
            });
        })(index, normalMark);

        map.addOverlay(normalMark);
        candidateMarkers.push(normalMark);
    }

    /**
     * 标注客户
     *
     * @param lng
     * @param lat
     * @param name
     */
    function markCustomer(lng, lat, name) {
        // 创建点坐标
        var point = new BMap.Point(lng, lat);

        var targetMark = new BMap.Marker(point, {icon: targetIcon});  // 创建标注

        targetMark.setTitle(name);

        targetMark.addEventListener("click", function(e){
            var info = name;
            map.openInfoWindow(new BMap.InfoWindow(info, {
                width: 100,
                height: 70,
                title: null,
                enableMessage: false
            }), point); //开启信息窗口
        });
        map.addOverlay(targetMark);
    }

    /**
     * 移除候选标记
     */
    function removeMarker(index) {

        map.removeOverlay(candidates[index]);
        return true;
    }

    /**
     * 清除標記
     */
    function clearMarkers() {

        for(var i in candidates) {

            var candidateMarker = candidateMarkers[i];
            map.removeOverlay(candidateMarker);
        }

        map.removeOverlay(selectedMarker);
        return null;
    }
    
    /**
     * 添加自定义位置
     *
     * @returns {boolean}
     */
    function markCustomLocation() {

        initLocationMarks(candidates);
        if(selectedMarker != null) {
            map.removeOverlay(selectedMarker);
            selectedMarker = null;
        }

        // 已设置自定义位置
        if(customLocationMarker != null) {

            $.messager.alert('提醒', '当前地图已设置自定义位置。');
            return true;
        }

        var bs = map.getBounds();       //获取可视区域
        var bssw = bs.getSouthWest();   //可视区域左下角
        var bsne = bs.getNorthEast();   //可视区域右上角
        var point =  new BMap.Point((bssw.lng + bsne.lng) / 2, (bssw.lat + bsne.lat) / 2);

        customLocationMarker = new BMap.Marker(point, {icon: targetIcon });  // 创建标注

        customLocationMarker.addEventListener('dragstart', function(e) {
            map.closeInfoWindow();
        });

        customLocationMarker.addEventListener('click', function(e){

            var currentPoint = customLocationMarker.getPosition();
            geoc.getLocation(currentPoint, function(rs){
                var addComp = rs.addressComponents;

                var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber + '附近';
                var info = address + '<br/><a href="javascript:void(0);" onclick="javascript:map.closeInfoWindow();map.removeOverlay(customLocationMarker);customLocationMarker=null;">删除</a>';
                map.openInfoWindow(new BMap.InfoWindow(info, {
                    width: 100,
                    height: 70,
                    title: '自定义位置（可拖动）',
                    enableMessage: false
                }), currentPoint);
            });
        });

        map.addOverlay(customLocationMarker);
        customLocationMarker.enableDragging();
        return true;
    }

    /**
     * 取消选中
     */
    function unselectLocation(index) {

        map.closeInfoWindow();
        initLocationMarks(candidates);
        map.removeOverlay(selectedMarker);
        selectedMarker = null;
        return true;
    }

    /**
     * 还原
     *
     * @returns {boolean}
     */
    function refreshLocationMarker() {

        map.closeInfoWindow();
        map.removeOverlay(customLocationMarker);
        customLocationMarker = null;

        clearLocationMarks();
        initLocationMarks(candidates);
        selectedMarker = null;
        return true;
    }

    /**
     * 保存坐标
     *
     * @returns {boolean}
     */
    function saveLocation() {

        parent.loading();
        var location = null;
        if(selectedMarker != null) {
            location = selectedMarker.getPosition();
        }

        if(customLocationMarker != null) {

            location = customLocationMarker.getPosition();
        }

        if(location == null && baseMark != null) {

            location = baseMark.getPosition();
        }

        if(location == null) {

            parent.loaded();
            $.messager.alert('提醒', '位置未设定！');
            return false;
        }

        geoc.getLocation(location, function(rs){

            var addComp = rs.addressComponents;
            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber + '附近';
            $.ajax({
                type: 'post',
                url: 'basefiles/editCustomerLocation.do',
                data: {customerid: '${param.id }', location: location.lng + ',' + location.lat, address: address},
                dataType: 'json',
                success: function (json) {

                    parent.loaded();
                    if(json.flag) {
                        $.messager.alert('提醒', '保存成功。');
                        parent.$('#sales-dialog-customerListPage1').dialog('close');
                        return true;
                    }
                    $.messager.alert('提醒', '保存出错！');
                    return true;
                },
                error: function (error) {

                    parent.loaded();
                    $.messager.alert('错误', '保存出错！');
                }
            })

        });

        return true;
    }

    function showSearchDialog() {

        parent.$('#sales-dialog-searchMap').dialog({
            maximizable: false,
            resizable: false,
            title: '查地点',
            width: 330,
            height: 120,
            closed: false,
            cache: false,
            modal: true,
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){

                    var keyword = parent.$('#sales-keyword-searchMap').val();
                    loading();
                    $.ajax({
                        type: 'get',
                        url: 'basefiles/searchPlace.do',
                        data: {keyword: keyword},
                        dataType: 'json',
                        success: function (json) {

                            loaded();
                            if(json.length == 0) {
                                $.messager.alert('提醒', '查询不到匹配的地址。');
                                parent.$('#sales-dialog-searchMap').dialog('close');
                                return true;
                            }

                            candidates = new Array();
                            var html = new Array();

                            html.push('<div id="candidates" style="float: left; width: 230px; padding: 20px 0px 20px 0px; overflow-y: scroll; height: 50%;">');
                            html.push('<div>');
                            html.push('<div style="padding: 5px; font-weight: bold;">查询结果：</div>');
                            html.push('<ul style="padding: 0px;">');

                            for(var i in json) {

                                var item = json[i];
                                if((item.address || '') == '' || (item.name || '') == '') {
                                    continue;
                                }
                                candidates.push({
                                    address: item.address || '',
                                    name: item.name || '',
                                    lng: item.location.lng,
                                    lat: item.location.lat
                                });

                                html.push('<li index="' + i + '" style="padding: 15px 5px 15px 5px; list-style-type: none;">');
                                html.push('<div onclick="javascript:selectLocation(' + i + ');">');
                                html.push('<div style="color: #1882cf;">' + (item.address || '') + '</div>');
                                html.push('<div style="color: #555;">' + (item.address || '') + '</div>');
                                html.push('</div>');
                                html.push('</li>');
                            }
                            html.push('</ul>');
                            html.push('</div>');
                            html.push('</div>');

                            $('#candidates').before(html.join('')).remove();
                            $('#candidates').height($('#container').height() - 40);
                            refreshLocationMarker();
                            parent.$('#sales-dialog-searchMap').dialog('close');
                        }
                    })
                }
            }],
            onClose:function(){
            }
        });

    }

    -->
</script>
</body>
</html>
