<%--
  Created by IntelliJ IDEA.
  User: xuxin
  Date: 2017/6/8 0008
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/include.jsp" %>
    <title>车辆轨迹管理查询</title>
    <style type="text/css">
        body, html, div, ul, li, iframe, dd, dl, dt, p, img {border: none; padding: 0; margin: 0; }
        html {height: 100% }
        body {height: 100%; margin: 0; padding: 0; position: relative; }
        #mapContainer {height: 100% }
        #manageControl {position: absolute; left: 30px; top: 30px; width: 300px; background: #fff; border: 1px solid #0a8cff; box-shadow: 1px 2px 3px 1px #cbcbcb; }
        .tab_head {list-style-type: none; overflow: auto;}
        .tab_head li {float: left; width: 50%; text-align: center; line-height: 2; cursor: pointer; background: #0a8cff; color: #fff;font-size:14px;}
        .tab_head li.current {background: #fff; color: #333; }
        .item {line-height: 2; color: #333; overflow: auto; border-bottom: dashed 1px #eee; cursor: pointer;font-size: 12px;padding:5px;}
        .item_name {background: url(image/yingyan/ico_caroff.png) no-repeat 4px center; padding-left: 30px; width: 190px; float: left; overflow: hidden;white-space: nowrap;}
        .item_speed {float: right; width: 70px;text-align: right;}
        #service_page {text-align: center; padding: 5px; }
        #service_page a, #service_page span {height: auto; line-height: 1.6; margin: 0 3px; padding: 0 5px; }
        #service_page .laypage_curr {border: 1px solid #fff; }
        #track_page {text-align: center; padding: 5px; }
        #track_page a, #track_page span {height: auto; line-height: 1.6; margin: 0 3px; padding: 0 5px; }
        #track_page .laypage_curr {border: 1px solid #fff; }
        .map_info {font-size: 12px; color: #aaa; }
        .u-ipt {font-size: 14px;line-height: 1.4; padding: 2px;border:0;}
        .service_search{text-align: center;width: 280px;margin:5px auto;height: 24px;}
        .track_search {text-align: center;width: 280px;margin:5px auto;}
        #btn_serviceSearch,#btn_trackSearch{border:0;background:url(image/yingyan/searchicon.png) no-repeat center;width:25px;height:25px;vertical-align: middle;}
        #service_key{width:220px;border: 0;height: 25px;}

        .trackpoint_in {height: 8px; width: 8px; background-color: #2398ff; border-radius: 4px; margin: 3px }
        .trackpointOverlay .trackpoint_in {display: none }
        .trackpointonOverlay .trackpoint_in {display: block }
        .trackpointOverlay,.trackpointonOverlay {position: absolute; z-index: 50; background-repeat: no-repeat; background-position: center center; background-size: 12px 12px; border-radius: 8px }
        .trackpointOverlay {height: 12px; width: 12px; background-color: #fff; border: 2px solid #000 }
        .trackpointonOverlay {height: 14px; width: 14px; background-color: #fff; border: 1px solid #2398ff }
        .behaviorOverlay {height: 20px; width: 20px; position: absolute; z-index: 100; background-repeat: no-repeat; background-position: left center; border-radius: 10px; color: #333; font-size: 12px; background-color: #fff; line-height: 20px; text-indent: 22px; font-weight: 700; transition: all .2s ease-in-out; overflow: hidden }
    </style>
</head>

<body>
<div id="mapContainer"></div>
<div id="manageControl" class="mapTabs">
    <ul class="tab_head">
        <li class="current" triger="getEntitys">实时监控</li>
        <li triger="getTrackEntitys">轨迹查询</li>
    </ul>
    <div class="tab_con">
        <div id="con_service">
            <div class="service_search">
                <input type="text" class="u-ipt" id="service_key" />
            </div>
            <div id="service_list"></div>
            <div id="service_page"></div>
        </div>
        <div id="con_track" style="display:none">
            <div class="track_search">
                <input type="text" class="Wdate u-ipt" id="track_day" onFocus="WdatePicker({isShowClear:false,readOnly:true,onpicked:function(){getTrackEntitys();} })" style="width:100px;" />
                <input type="text" class="u-ipt" id="track_key" />
                <button type="button" id="btn_trackSearch"></button>
            </div>
            <div id="track_list"></div>
            <div id="track_page"></div>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/yingyan/mapControl.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=EOeTWoBQTCC3l2VfafhDYR7F306FArfn&callback=mapControl.initMap"></script>
<script type="text/javascript" src="js/yingyan/commonfun.js"></script>
<script type="text/javascript" src="js/laytpl.js"></script>
<script type="text/javascript" src="js/laypage/laypage.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    $(function() {
        $(".mapTabs").each(function(i, item) {
            var wo = $(this);
            var head = wo.find(".tab_head li");
            var con = wo.find(".tab_con>div");
            head.click(function() {
                var i = head.index($(this));
                var triger = $(this).attr("triger");
                if (triger) {
                    eval(triger + '()');
                }
                head.removeClass("current").eq(i).addClass('current');
                con.hide().eq(i).show();
            });
        });
        $("#service_key").widget({
            referwid:'RL_T_SYS_USER',
            width:'280',
            singleSelect:true,
            onSelect:function(data){
                getEntitys();
            },
            onClear:function() {
                getEntitys();
            }
        });
        $("#track_key").widget({
            referwid:'RL_T_SYS_USER',
            width:'140',
            singleSelect:true,
            onSelect:function(data){
                getTrackEntitys();
            },
            onClear:function() {
                getTrackEntitys();
            }
        });
    });
</script>
<script type="text/javascript">
    var day1 = new Date();
    day1.setTime(day1.getTime());
    $("#track_day").val(day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate());

    var beginTime = " ${startTime}";
    var finishTime = " ${endTime}";
    var baseUrl = "http://yingyan.baidu.com/api/v3";
    var data_services = [];
    //var ak = "i5TlrzbmwDbygpliitdF2Fal";
    //var service_id = "142076";
    var ak = "${ak}";
    var service_id = "${service_id}";
    if("${errormsg}"!=""){
        $.messager.alert("错误", "${errormsg}");
    }
    //去噪,绑路,定位精度过滤,交通方式
    var need_denoise = 1, need_mapmatch = 1, radius_threshold = "${radiusThreshold}", transport_mode = "driving";
    function getMapAllEntitys() {
        var bounds = map.getBounds();
        var str_bounds = bounds.getSouthWest().lat+","+bounds.getSouthWest().lng+";"+bounds.getNorthEast().lat+","+bounds.getNorthEast().lng;
        $.ajax({
            url: baseUrl + "/entity/boundsearch",
            type: "get",
            data: {
                ak: ak,
                service_id: service_id,
                bounds: str_bounds,
                filter: "active_time:" + Date.parse(new Date().toLocaleDateString())/1000
            },
            dataType: "jsonp",
            success: function(backInfo) {
                if(backInfo.status != 0) {
                    $.messager.alert("提示", backInfo.message);
                    return;
                }
                for(var i = 0; i < backInfo.entities.length; i++) {
                    var point = new BMap.Point(backInfo.entities[i].latest_location.longitude, backInfo.entities[i].latest_location.latitude);
                    var mapPoint = new map.mapPointOverlay(point, backInfo.entities[i].user_name, backInfo.entities[i].entity_name);
                    map.addOverlay(mapPoint);
                }
            }
        });
    }
    //获取实时监控列表
    function getEntitys(curr) {
        var pageSize = 10;
        var key = $("#service_key").widget("getValue");
        var filter = key == "" ? "" : "user_id:" + key;
        $.ajax({
            url: baseUrl + "/entity/list",
            type: "get",
            data: {
                ak: ak,
                service_id: service_id,
                page_index: curr,
                page_size: pageSize,
                filter: filter
            },
            dataType: "jsonp",
            success: function(backInfo) {
                if(backInfo.status != 0) {
                    $("#service_list").html("<p style='text-align:center;padding:10px;'>" + backInfo.message + "</p>");
                    $("#service_page").html("");
                    return;
                }
                if (backInfo.total == 0) {
                    $("#service_list").html("<p style='text-align:center;padding:10px;'>没有相关信息</p>");
                    $("#service_page").html("");
                    return;
                }
                data_services = backInfo.entities;

                var pageNums = backInfo.total / pageSize + (backInfo.total % pageSize == 0 ? 0 : 1);
                var tpl = document.getElementById('tpl_service').innerHTML;
                laytpl(tpl).render(backInfo, function(render) {
                    $("#service_list").html(render);
                });
                if (curr) return;
                laypage({
                    cont: 'service_page',
                    pages: pageNums,
                    groups: 5,
                    curr: curr || 1,
                    prev: "<",
                    next: ">",
                    last: false,
                    jump: function(obj, first) {
                        if (!first)
                            getEntitys(obj.curr);
                    }
                });
            }
        });
    }
    var entity_track = [];
    function getTrackEntitys(curr) {
        var pageSize = 10;
        var day = $("#track_day").val();
        var starttime = Date.parse(new Date(day + beginTime)) / 1000;
        //var endtime = Date.parse(new Date(day + finishTime)) / 1000;
        var filter = "active_time:" + starttime;
        var key = $("#track_key").widget("getValue");
        if(key != "")
            filter = filter + "|user_id:" + key;
        $.ajax({
            url: baseUrl + "/entity/list",
            type: "get",
            data: {
                ak: ak,
                service_id: service_id,
                page_index: curr,
                page_size: pageSize,
                filter: filter
            },
            dataType: "jsonp",
            success: function(backInfo) {
                if(backInfo.status != 0) {
                    $("#track_list").html("<p style='text-align:center;padding:10px;'>" + backInfo.message + "</p>");
                    $("#track_page").html("");
                    return;
                }
                if (backInfo.total == 0) {
                    $("#track_list").html("<p style='text-align:center;padding:10px;'>没有相关信息</p>");
                    $("#track_page").html("");
                    return;
                }
                entity_track = backInfo.entities;

                var pageNums = backInfo.total / pageSize + (backInfo.total % pageSize == 0 ? 0 : 1);
                var tpl = document.getElementById('tpl_service').innerHTML;
                laytpl(tpl).render(backInfo, function(render) {
                    $("#track_list").html(render);
                });
                getDistanceList();
                if (curr) return;
                laypage({
                    cont: 'track_page',
                    pages: pageNums,
                    groups: 5,
                    curr: curr || 1,
                    prev: "<",
                    next: ">",
                    last: false,
                    jump: function(obj, first) {
                        if (!first)
                            getTrackEntitys(obj.curr);
                    }
                });
            }
        });
    }
    function getDistanceList() {
        var day = $("#track_day").val();
        var starttime = Date.parse(new Date(day + beginTime)) / 1000;
        var endtime = Date.parse(new Date(day + finishTime)) / 1000;
        var items = $("#track_list .item");
        $.each(items, function(i, item) {
            var entityName = $(item).attr("data-entity_name");
            $.ajax({
                url: baseUrl + "/track/getdistance",
                type: "get",
                data: {
                    ak: ak,
                    service_id: service_id,
                    entity_name: entityName,
                    start_time: starttime,
                    end_time: endtime,
                    is_processed: 1,
                    supplement_mode:'driving',
                    process_option: "need_denoise=" + need_denoise + ",need_mapmatch=" + need_mapmatch + ",radius_threshold=" + radius_threshold + ",transport_mode=" + transport_mode
                },
                dataType: "jsonp",
                success: function(backInfo) {
                    $(item).find(".item_speed").text((backInfo.distance/1000).toFixed(2) + "km");
                }
            });
        });
    }
    function getColorBySpeed(speed) {
        var color = '';
        var red = 0;
        var green = 0;
        var blue = 0;
        speed = speed > 100 ? 100 : speed;
        switch (Math.floor(speed / 25)) {
            case 0:
                red = 187;
                green = 0;
                blue = 0;
                break;
            case 1:
                speed = speed - 25;
                red = 187 + Math.ceil((241 - 187) / 25 * speed);
                green = 0 + Math.ceil((48 - 0) / 25 * speed);
                blue = 0 + Math.ceil((48 - 0) / 25 * speed);
                break;
            case 2:
                speed = speed - 50;
                red = 241 + Math.ceil((255 - 241) / 25 * speed);
                green = 48 + Math.ceil((200 - 48) / 25 * speed);
                blue = 48 + Math.ceil((0 - 48) / 25 * speed);
                break;
            case 3:
                speed = speed - 75;
                red = 255 + Math.ceil((22 - 255) / 25 * speed);
                green = 200 + Math.ceil((191 - 200) / 25 * speed);
                blue = 0 + Math.ceil((43 - 0) / 25 * speed);
                break;
            case 4:
                red = 22;
                green = 191;
                blue = 43;
                break;
        }

        red = red.toString(16).length === 1 ? '0' + red.toString(16) : red.toString(16);
        green = green.toString(16).length === 1 ? '0' + green.toString(16) : green.toString(16);
        blue = blue.toString(16).length === 1 ? '0' + blue.toString(16) : blue.toString(16);
        color = '#' + red + green + blue;
        return color;
    }
    var currentEntityPrint = null;
    function drawTrack(data, starttime, endtime) {
        if (!data)
            return;
        var that = this;
        var totalPoints = [];
        var viewportPoints = [];

        if (data.length === 0) {
            return;
        }
        if (!starttime) {
            starttime = data[0].loc_time;
        }
        if (!endtime) {
            endtime = data[data.length -  1].loc_time;
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i].loc_time >= starttime && data[i].loc_time <= endtime) {
                var tempPoint = new BMap.Point(data[i].longitude, data[i].latitude);
                tempPoint.speed = data[i].speed ? data[i].speed : 0;
                tempPoint.loc_time = data[i].loc_time;
                tempPoint.height = data[i].height;
                tempPoint.radius = data[i].radius;
                tempPoint.print = currentEntityPrint.entity_name;
                tempPoint.user_id = currentEntityPrint.user_id;
                tempPoint.user_name = currentEntityPrint.user_name;
                tempPoint.entity_desc = currentEntityPrint.entity_desc;
                tempPoint.printTime = Commonfun.getLocalTime(data[i].loc_time);
                tempPoint.printSpeed = Commonfun.getSpeed(data[i].speed);
                tempPoint.lnglat = data[i].longitude.toFixed(2) + ',' + data[i].latitude.toFixed(2);
                totalPoints.push(tempPoint);
            }
        }
        //if (that.state.first) {
        //    map.setViewport(totalPoints, {margins: [80, 0, 0, 200]});
        //}

        var updatePointer = function () {
            var nextArray = [];
            var ctx = this.canvas.getContext('2d');
            if (!ctx) {
                return;
            }
            ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);

            if (totalPoints.length !== 0) {
                var lines = 1;
                var lineObj = {};
                var pixelPart = 0;
                const pixelPartUnit = 40;
                for (var i = 0, len = totalPoints.length; i < len - 1; i = i + 1) {
                    var pixel = map.pointToPixel(totalPoints[i]);
                    var nextPixel = map.pointToPixel(totalPoints[i + 1]);
                    pixelPart = pixelPart + Math.pow(Math.pow(nextPixel.x - pixel.x, 2) + Math.pow(nextPixel.y - pixel.y, 2), 0.5);
                    if (pixelPart <= pixelPartUnit) {
                        continue;
                    }
                    pixelPart = 0;
                    ctx.beginPath();

                    if (totalPoints[i + 1].loc_time - totalPoints[i].loc_time <= 5 * 60) {
                        // 箭头一共需要5个点：起点、终点、中心点、箭头端点1、箭头端点2

                        var midPixel = new BMap.Pixel(
                            (pixel.x + nextPixel.x) / 2,
                            (pixel.y + nextPixel.y) / 2
                        );

                        // 起点终点距离
                        var distance = Math.pow((Math.pow(nextPixel.x - pixel.x, 2) + Math.pow(nextPixel.y - pixel.y, 2)), 0.5);
                        // 箭头长度
                        var pointerLong = 4;
                        var aPixel = {};
                        var bPixel = {};
                        if (nextPixel.x - pixel.x === 0) {
                            if (nextPixel.y - pixel.y > 0) {
                                aPixel.x = midPixel.x - pointerLong * Math.pow(0.5, 0.5);
                                aPixel.y = midPixel.y - pointerLong * Math.pow(0.5, 0.5);
                                bPixel.x = midPixel.x + pointerLong * Math.pow(0.5, 0.5);
                                bPixel.y = midPixel.y - pointerLong * Math.pow(0.5, 0.5);
                            } else if (nextPixel.y - pixel.y < 0) {
                                aPixel.x = midPixel.x - pointerLong * Math.pow(0.5, 0.5);
                                aPixel.y = midPixel.y + pointerLong * Math.pow(0.5, 0.5);
                                bPixel.x = midPixel.x + pointerLong * Math.pow(0.5, 0.5);
                                bPixel.y = midPixel.y + pointerLong * Math.pow(0.5, 0.5);
                            } else {
                                continue;
                            }
                        } else {
                            var k0 = ((-Math.pow(2, 0.5) * distance * pointerLong + 2 * (nextPixel.y - pixel.y) * midPixel.y) / (2 * (nextPixel.x - pixel.x))) + midPixel.x;
                            var k1 = -((nextPixel.y - pixel.y) / (nextPixel.x - pixel.x));
                            var a = Math.pow(k1, 2) + 1;
                            var b = 2 * k1 * (k0 - midPixel.x) - 2 * midPixel.y;
                            var c = Math.pow(k0 - midPixel.x, 2) + Math.pow(midPixel.y, 2) - Math.pow(pointerLong, 2);

                            aPixel.y = (-b + Math.pow(b * b - 4 * a * c, 0.5)) / (2 * a);
                            bPixel.y = (-b - Math.pow(b * b - 4 * a * c, 0.5)) / (2 * a);
                            aPixel.x = k1 * aPixel.y + k0;
                            bPixel.x = k1 * bPixel.y + k0;
                        }
                        ctx.moveTo(aPixel.x, aPixel.y);
                        ctx.lineWidth = 2;
                        ctx.strokeStyle = '#eee';
                        ctx.lineTo(midPixel.x, midPixel.y);
                        ctx.lineTo(bPixel.x, bPixel.y);
                        ctx.lineCap = 'round';
                    }
                    if (totalPoints[i].loc_time >= starttime && totalPoints[i + 1].loc_time <= endtime) {
                        ctx.stroke();
                    }
                }
            }
        };
        var updateBack = function () {
            var nextArray = [];
            var ctx = this.canvas.getContext('2d');
            if (!ctx) {
                return;
            }
            ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
            if (totalPoints.length !== 0) {
                var lines = 1;
                var lineObj = {};

                for (var i = 0, len = totalPoints.length; i < len - 1; i++) {

                    var pixel = map.pointToPixel(totalPoints[i]);
                    var nextPixel = map.pointToPixel(totalPoints[i + 1]);
                    ctx.beginPath();

                    ctx.moveTo(pixel.x, pixel.y);
                    if (totalPoints[i + 1].loc_time - totalPoints[i].loc_time <= 5 * 60) {
                        // 绘制轨迹的时候绘制两次line，一次是底色，一次是带速度颜色的。目的是实现边框效果
                        ctx.lineWidth = 10;
                        ctx.strokeStyle = '#8b8b89';
                        ctx.lineTo(nextPixel.x, nextPixel.y);
                        ctx.lineCap = 'round';

                    } else {
                        lines = lines + 1;
                        var lineNum = lines;
                        nextArray.push([pixel, nextPixel]);
                    }
                    if (totalPoints[i].loc_time >= starttime && totalPoints[i + 1].loc_time <= endtime) {
                        ctx.stroke();
                    }

                }
            }
        };
        var update = function () {
            var nextArray = [];
            var ctx = this.canvas.getContext('2d');
            if (!ctx) {
                return;
            }
            ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);

            if (totalPoints.length !== 0) {
                var lines = 1;
                var lineObj = {};
                for (var i = 0, len = totalPoints.length; i < len - 1; i++) {

                    var pixel = map.pointToPixel(totalPoints[i]);
                    var nextPixel = map.pointToPixel(totalPoints[i + 1]);
                    ctx.beginPath();
                    ctx.moveTo(pixel.x, pixel.y);
                    if (totalPoints[i + 1].loc_time - totalPoints[i].loc_time <= 5 * 60) {
                        // 绘制带速度颜色的轨迹
                        ctx.lineCap = 'round';
                        ctx.lineWidth = 8;
                        var grd = ctx.createLinearGradient(pixel.x, pixel.y, nextPixel.x, nextPixel.y);
                        var speed = totalPoints[i].speed;
                        var speedNext = totalPoints[i + 1].speed;
                        grd.addColorStop(0, that.getColorBySpeed(speed));
                        grd.addColorStop(1, that.getColorBySpeed(speedNext));
                        ctx.strokeStyle = grd;
                        ctx.lineTo(nextPixel.x, nextPixel.y);
                    } else {
                        lines = lines + 1;
                        var lineNum = lines;
                        nextArray.push([pixel, nextPixel]);
                        if (totalPoints[i + 1].loc_time >= starttime && totalPoints[i + 1].loc_time <= endtime) {
                            var partImgStart = new Image();
                            partImgStart.src = 'image/yingyan/startpoint.png';
                            var next = nextPixel;
                            partImgStart.onload = function () {
                                var width = [4, 8];
                                ctx.drawImage(partImgStart, next.x - 10, next.y - 30);
                                ctx.font = 'lighter 14px arial';
                                ctx.fillStyle = 'white';
                                ctx.fillText(lineNum, next.x - width[lineNum >= 10 ? 1 : 0], next.y - 15);
                            };
                        }
                        if (totalPoints[i].loc_time >= starttime && totalPoints[i].loc_time <= endtime) {
                            var current = pixel;
                            var partImgEnd = new Image();
                            partImgEnd.src = 'image/yingyan/endpoint.png';
                            partImgEnd.onload = function () {
                                var width = [4, 8];
                                ctx.drawImage(partImgEnd, current.x - 10, current.y - 30);
                                ctx.font = 'lighter 14px arial';
                                ctx.fillStyle = 'white';
                                ctx.fillText(lineNum - 1, current.x - width[lineNum >= 10 ? 1 : 0], current.y - 15);
                            };
                        }
                    }
                    if (totalPoints[i].loc_time >= starttime && totalPoints[i + 1].loc_time <= endtime) {
                        ctx.stroke();
                    }

                }
            }

            if (totalPoints[0].loc_time >= starttime) {
                var imgStart = new Image();
                imgStart.src = 'image/yingyan/startpoint.png';
                imgStart.onload = function () {
                    var width = [4, 8];
                    ctx.drawImage(imgStart, map.pointToPixel(totalPoints[0]).x - 10, map.pointToPixel(totalPoints[0]).y - 30);
                    ctx.font = 'lighter 14px arial';
                    ctx.fillStyle = 'white';
                    ctx.fillText('1', map.pointToPixel(totalPoints[0]).x - width[lines >= 10 ? 1 : 0], map.pointToPixel(totalPoints[0]).y - 15);
                };
            }
            if (totalPoints[totalPoints.length - 1].loc_time <= endtime) {
                var imgEnd = new Image();
                imgEnd.src = 'image/yingyan/endpoint.png';
                imgEnd.onload = function () {
                    var width = [4, 8];
                    ctx.drawImage(imgEnd, map.pointToPixel(totalPoints[totalPoints.length - 1]).x - 10, map.pointToPixel(totalPoints[totalPoints.length - 1]).y - 30);
                    ctx.font = 'lighter 14px arial';
                    ctx.fillStyle = 'white';
                    ctx.fillText(lines, map.pointToPixel(totalPoints[totalPoints.length - 1]).x - width[lines >= 10 ? 1 : 0], map.pointToPixel(totalPoints[totalPoints.length - 1]).y - 15);
                };
            }
        };
        if (totalPoints.length > 0) {
            if(typeof(canvasLayer) !== 'undefined' || typeof(canvasLayerBack) !== 'undefined' || typeof(CanvasLayerPointer) !== 'undefined') {
                map.removeOverlay(CanvasLayerPointer);
                map.removeOverlay(canvasLayer);
                map.removeOverlay(canvasLayerBack);
            }
            window.canvasLayerBack =  new CanvasLayer({
                map: map,
                update: updateBack
            });
            window.canvasLayer =  new CanvasLayer({
                map: map,
                update: update
            });
            window.CanvasLayerPointer =  new CanvasLayer({
                map: map,
                update: updatePointer
            });
            map.panTo(new BMap.Point(totalPoints[0].lng,totalPoints[0].lat));
        }
        mapControl.removeBehaviorOverlay();
        //TrackAction.behavioranalysis();
        //TrackAction.getstaypoint();

        if (typeof(pointCollection) !== 'undefined') {
            map.removeOverlay(pointCollection);
        }
        var options = {
            size: BMAP_POINT_SIZE_HUGE,
            shape: BMAP_POINT_SHAPE_CIRCLE,
            color: 'rgba(0, 0, 0, 0)'
        };
        window.pointCollection = new BMap.PointCollection(totalPoints, options);  // 初始化PointCollection
        pointCollection.addEventListener('mouseover', function (e) {
            mapControl.addTrackPointOverlay(e.point, 'trackpointOverlay');
        });
        pointCollection.addEventListener('mouseout', function (e) {
            mapControl.removeTrackPointOverlay('trackpointOverlay');
        });
        pointCollection.addEventListener('click', function (e) {
            mapControl.removeTrackInfoBox();
            var point = e.point;
            var opts = {
                width: 200,
                height: 150,
                title: point.user_name || point.print
            };
            var myGeo = new BMap.Geocoder();
            // 根据坐标得到地址描述
            myGeo.getLocation(point, function(result){
                if (result){
                    var info = [];
                    info.push("<div class='map_info'>");
                    info.push("<p>坐标:" + point.lng.toFixed(6) + "," + point.lat.toFixed(6) + "</p>");
                    info.push("<p>地址:" + result.address + "</p>");
                    info.push("<p>时间:" + point.printTime + "</p>");
                    info.push("<p>速度:" + point.printSpeed + "</p>");
                    info.push("<p>用户编号:" + (point.user_id||"") + "</p>");
                    info.push("<p>用户名称:" + (point.user_name||"") + "</p>");
                    info.push("<p>备注:" + point.entity_desc + "</p>");
                    info.push("</div>");
                    var infoWindow = new BMap.InfoWindow(info.join(""), opts);
                    map.openInfoWindow(infoWindow, point);
                }
            });
            mapControl.removeTrackPointOverlay('trackpointonOverlay');
            mapControl.addTrackPointOverlay(e.point, 'trackpointonOverlay');

        });
        map.addOverlay(pointCollection);  // 添加Overlay
    }
    $(function() {
        getEntitys();
//        $("#btn_serviceSearch").click(function(){
//            getEntitys();
//        });
        $("#btn_trackSearch").click(function(){
            getTrackEntitys();
        });
        //点击获取实时位置
        $("#service_list").on("click", ".item", function(event) {
            map.clearOverlays();
            var entity_name = $(this).attr("data-entity_name");
            var entity = null;
            for(var i = 0;i < data_services.length;i++) {
                if(data_services[i].entity_name == entity_name) {
                    entity = data_services[i];
                    break;
                }
            }
            if(entity == null)
                return;

            var point = new BMap.Point(entity.latest_location.longitude, entity.latest_location.latitude);
            var marker = new BMap.Marker(point);
            var opts = {
                width: 200,
                height: 150,
                title: entity.user_name || entity_name
            };
            var myGeo = new BMap.Geocoder();
            // 根据坐标得到地址描述
            myGeo.getLocation(point, function(result){
                if (result){
                    var info = [];
                    info.push("<div class='map_info'>");
                    info.push("<p>坐标:" + entity.latest_location.longitude.toFixed(6) + "," + entity.latest_location.latitude.toFixed(6) + "</p>");
                    info.push("<p>地址:" + result.address + "</p>");
                    info.push("<p>状态:" + (Commonfun.getOnlineStatus(entity.latest_location.loc_time) == 1 ?"离线":"在线") + "</p>");
                    info.push("<p>时间:" + Commonfun.getLocalTime(entity.latest_location.loc_time) + "</p>");
                    info.push("<p>速度:" + entity.latest_location.speed + "</p>");
                    info.push("<p>用户编号:" + (entity.user_id||"") + "</p>");
                    info.push("<p>用户名称:" + (entity.user_name||"") + "</p>");
                    info.push("<p>备注:" + (entity.entity_desc||"") + "</p>");
                    info.push("</div>");
                    var infoWindow = new BMap.InfoWindow(info.join(""), opts);
                    map.addOverlay(marker);
                    map.openInfoWindow(infoWindow, point);
                    map.centerAndZoom(point, 15);
                }
            });
        });
        //点击获取轨迹
        $("#track_list").on("click", ".item", function(event) {
            map.clearOverlays();
            var entity_name = $(this).attr("data-entity_name");
            var day = $("#track_day").val();
            var starttime = Date.parse(new Date(day + beginTime)) / 1000;
            var endtime = Date.parse(new Date(day + finishTime)) / 1000;
            for(var i=0;i<entity_track.length;i++){
                if(entity_track[i].entity_name == entity_name) {
                    currentEntityPrint = entity_track[i];
                    break;
                }
            }
            if(!currentEntityPrint)
                return;

            $.ajax({
                url: baseUrl + "/track/gettrack",
                type: "get",
                data: {
                    ak: ak,
                    service_id: service_id,
                    entity_name: entity_name,
                    start_time: starttime,
                    end_time: endtime,
                    is_processed: "1",
                    supplement_mode:"driving",
                    process_option: "need_denoise=" + need_denoise + ",need_mapmatch=" + need_mapmatch + ",radius_threshold=" + radius_threshold + ",transport_mode=" + transport_mode,
                    page_size: 5000
                },
                dataType: "jsonp",
                success: function(backInfo) {
                    if (backInfo.status != 0)
                        return;
                    drawTrack(backInfo.points);
                }
            });
        });
    });
</script>
<script type="text/html" id="tpl_service">
    {{# for(var i=0;i<d.entities.length;i++){ }}
    <div class="item" data-entity_name="{{ d.entities[i].entity_name }}">
        <div class="item_name">{{ d.entities[i].user_name || d.entities[i].entity_name }}</div>
        <div class="item_speed">{{ Commonfun.getOnlineStatus(d.entities[i].latest_location.loc_time) == 1 ?"离线":"在线" }}</div>
    </div>
    {{#} }}
</script>
</body>

</html>
