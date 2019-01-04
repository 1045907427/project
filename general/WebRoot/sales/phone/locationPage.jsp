<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>业务员位置查询</title>
  	<%@include file="/include.jsp" %>  
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=qYlCsNIpqrWPz7MHLjCeTLy5"></script>
	<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
	<style type="text/css">
		html{height:100%;}
		body{height:100%;width:100%;margin:0px;padding:0px;}
		#container{height:100%;}
	</style>
	<script type="text/javascript">
		var track_index = 1;
		$(function(){

			$("#map_dialog_saleser").widget({ //客户业务员参照窗口
		   		name:'t_phone_location',
		   		col:'userid',
		   		singleSelect:true,
		   		width:180,
		   		onlyLeafCheck:true
		   	});
            $("#map_person_dialog_leadid").widget({ //客户业务员参照窗口
                referwid:"RL_BASEPERSONNEEL_LEADER",
                singleSelect:true,
                width:180,
                onlyLeafCheck:true
            });
            $("#map_person_dialog_dept").widget({ //部门参照窗口
                referwid:"RL_T_BASE_DEPATMENT",
                singleSelect:true,
                width:180,
                onlyLeafCheck:true
            });
		   	$(".track_flash").click(function(){
		   		var index = $(".track_flash").index($(this));
				if(track_index == index) return;
		   		$(".track_flash").css({"border":"none", "background":"none"}).eq(index).css({"border":"1px solid #aaaaaa", "background":"#efefef"});
		   		track_index = index;
		   	});
		});
	</script>
  </head>
  
  <body>
    <div id="container"></div>
    <div id="map_dialog" style="padding:10px;" class="easyui-dialog" data-options="closed:true">
    	<form id="map_form">
	    	<div style="line-height: 22px;text-indent:5px;border:1px solid #aaaaaa;background:#F9FCE5;">
	    		轨迹查询需选择业务员和日期或时间点中的一个。
	    	</div>
		    <div style="line-height: 22px;margin-top: 5px;">
		    	<span style="float:left; width:60px;text-align:right;">业务员：</span>
		    	<input id="map_dialog_saleser" name="userid" />
		    	<div class="clear"></div>
		    </div>
		    <div style="line-height: 22px;margin-top: 5px;">
		    	<span style="float:left; width:60px;text-align:right;">日期：</span>
		    	<input onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="Wdate" name="date" style="padding:0;height:18px;margin:0;width: 180px;" value="${today }"/>
		    	<div class="clear"></div>
		    </div>
		    <div style="line-height: 22px;text-indent:5px;margin-top:5px;border:1px solid #aaaaaa;background:#F9FCE5;">
	    		时间点查询显示业务员在该时间点前后半小时的位置信息。
	    	</div>
		    <div style="line-height: 22px;margin-top: 5px;">
		    	<span style="float:left; width:60px;text-align:right;">时间点：</span>
		    	<input onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm'})" class="Wdate" name="time" style="padding:0;height:18px;margin:0;width: 180px;" />
		    	<div class="clear"></div>
		    </div>
		    <div style="line-height: 22px;margin-top: 5px;">
		    	轨迹动画：<span style="padding:3px;cursor:pointer;" class="track_flash">否</span> 
		    			 <span style="padding:3px;border:1px solid #aaaaaa;background:#efefef;cursor:pointer;" class="track_flash">是</span>
		    </div>
	    </form>
    </div>
    <div id="map_person_dialog" style="padding:10px;" class="easyui-dialog" data-options="closed:true">
        <form id="map_person_form">
            <div style="line-height: 22px;margin-top: 5px;">
                <span style="float:left; width:60px;text-align:right;">部&nbsp;&nbsp;门：</span>
                <input id="map_person_dialog_dept" name="deptid" />
                <div class="clear"></div>
            </div>
            <div style="line-height: 22px;margin-top: 5px;">
                <span style="float:left; width:60px;text-align:right;">业务属性：</span>
                <select name="employetype" style="width: 180px">
                    <option></option>
                    <c:forEach items="${codeList }" var="list">
                    <option value="${list.code }">${list.codename }</option>
                    </c:forEach>
                </select>
                <div class="clear"></div>
            </div>
            <div style="line-height: 22px;margin-top: 5px;">
                <span style="float:left; width:60px;text-align:right;">上级领导：</span>
                <input id="map_person_dialog_leadid" name="leadid" />
                <div class="clear"></div>
            </div>
        </form>
    </div>

    </div>
    <script type="text/javascript">
    	var map = new BMap.Map("container");          // 创建地图实例
		var point = new BMap.Point(${location1},${location2});  // 创建点坐标
		var zIndex = 1;
		map.enableScrollWheelZoom(true);
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl());
		map.addControl(new BMap.MapTypeControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));
		map.centerAndZoom(point, 11);
        //业务员标签
		function ComplexCustomOverlay(point, text, mouseoverText){
	      this._point = point;
	      this._text = text;
	      this._overText = mouseoverText;
	    }
	    ComplexCustomOverlay.prototype = new BMap.Overlay();
	    ComplexCustomOverlay.prototype.initialize = function(map){
	      this._map = map;
	      var div = this._div = document.createElement("div");
	      div.style.position = "absolute";
	      div.style.zIndex = ++zIndex;
	      div.style.backgroundColor = "#EE5D5B";
	      div.style.border = "1px solid #BC3B3A";
	      div.style.color = "white";
	      div.style.height = "18px";
	      div.style.padding = "2px";
	      div.style.lineHeight = "18px";
	      div.style.whiteSpace = "nowrap";
	      div.style.MozUserSelect = "none";
	      div.style.fontSize = "12px"
	      var span = this._span = document.createElement("span");
	      div.appendChild(span);
	      span.appendChild(document.createTextNode(this._text));
	      var that = this;
	      var arrow = this._arrow = document.createElement("div");
	      arrow.style.background = "url(image/label.png) no-repeat";
	      arrow.style.position = "absolute";
	      arrow.style.width = "11px";
	      arrow.style.height = "10px";
	      arrow.style.top = "22px";
	      arrow.style.left = "10px";
	      arrow.style.overflow = "hidden";
	      div.appendChild(arrow);
	      div.onmouseover = function(){
	        this.style.backgroundColor = "#6BADCA";
	        this.style.borderColor = "#0000ff";
	        this.getElementsByTagName("span")[0].innerHTML = that._overText;
	        arrow.style.backgroundPosition = "0px -20px";
	      }
	      div.onmouseout = function(){
	        this.style.backgroundColor = "#EE5D5B";
	        this.style.borderColor = "#BC3B3A";
	        this.getElementsByTagName("span")[0].innerHTML = that._text;
	        arrow.style.backgroundPosition = "0px 0px";
	      }
	      div.onclick = function(){
	      	this.style.zIndex = ++zIndex;
	      }
	      map.getPanes().labelPane.appendChild(div);
	      return div;
	    }
	    ComplexCustomOverlay.prototype.draw = function(){
	      var map = this._map;
	      var pixel = map.pointToOverlayPixel(this._point);
	      this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
	      this._div.style.top  = pixel.y - 30 + "px";
	    }
        //其他人员标签
        function ComplexCustomOverlayOther(point, text, mouseoverText){
            this._point = point;
            this._text = text;
            this._overText = mouseoverText;
        }
        ComplexCustomOverlayOther.prototype = new BMap.Overlay();
        ComplexCustomOverlayOther.prototype.initialize = function(map){
            this._map = map;
            var div = this._div = document.createElement("div");
            div.style.position = "absolute";
            div.style.zIndex = ++zIndex;
            div.style.backgroundColor = "#ff9625";
            div.style.border = "1px solid #ff9625";
            div.style.color = "white";
            div.style.height = "18px";
            div.style.padding = "2px";
            div.style.lineHeight = "18px";
            div.style.whiteSpace = "nowrap";
            div.style.MozUserSelect = "none";
            div.style.fontSize = "12px"
            var span = this._span = document.createElement("span");
            div.appendChild(span);
            span.appendChild(document.createTextNode(this._text));
            var that = this;
            var arrow = this._arrow = document.createElement("div");
            arrow.style.background = "url(image/label.png) 0px -10px no-repeat";
            arrow.style.position = "absolute";
            arrow.style.width = "11px";
            arrow.style.height = "10px";
            arrow.style.top = "22px";
            arrow.style.left = "10px";
            arrow.style.overflow = "hidden";
            div.appendChild(arrow);
            div.onmouseover = function(){
                this.style.backgroundColor = "#ff9625";
                this.style.borderColor = "#ff9625";
                this.getElementsByTagName("span")[0].innerHTML = that._overText;
//                arrow.style.backgroundPosition = "0px -20px";
            }
            div.onmouseout = function(){
                this.style.backgroundColor = "#ff9625";
                this.style.borderColor = "#ff9625";
                this.getElementsByTagName("span")[0].innerHTML = that._text;
//                arrow.style.backgroundPosition = "0px -10px";
            }
            div.onclick = function(){
                this.style.zIndex = ++zIndex;
            }
            map.getPanes().labelPane.appendChild(div);
            return div;
        }
        ComplexCustomOverlayOther.prototype.draw = function(){
            var map = this._map;
            var pixel = map.pointToOverlayPixel(this._point);
            this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
            this._div.style.top  = pixel.y - 30 + "px";
        }
        //物流人员标签
        function ComplexCustomOverlayCar(point, text, mouseoverText){
            this._point = point;
            this._text = text;
            this._overText = mouseoverText;
        }
        ComplexCustomOverlayCar.prototype = new BMap.Overlay();
        ComplexCustomOverlayCar.prototype.initialize = function(map){
            this._map = map;
            var div = this._div = document.createElement("div");
            div.style.position = "absolute";
            div.style.zIndex = ++zIndex;
            div.style.backgroundColor = "#6caeca";
            div.style.border = "1px solid #6caeca";
            div.style.color = "white";
            div.style.height = "18px";
            div.style.padding = "2px";
            div.style.lineHeight = "18px";
            div.style.whiteSpace = "nowrap";
            div.style.MozUserSelect = "none";
            div.style.fontSize = "12px"
            var span = this._span = document.createElement("span");
            div.appendChild(span);
            span.appendChild(document.createTextNode(this._text));
            var that = this;
            var arrow = this._arrow = document.createElement("div");
            arrow.style.background = "url(image/label.png) 0px -20px no-repeat";
            arrow.style.position = "absolute";
            arrow.style.width = "11px";
            arrow.style.height = "10px";
            arrow.style.top = "22px";
            arrow.style.left = "10px";
            arrow.style.overflow = "hidden";
            div.appendChild(arrow);
            div.onmouseover = function(){
                this.style.backgroundColor = "#6caeca";
                this.style.borderColor = "#0000ff";
                this.getElementsByTagName("span")[0].innerHTML = that._overText;
//                arrow.style.backgroundPosition = "0px -20px";
            }
            div.onmouseout = function(){
                this.style.backgroundColor = "#6caeca";
                this.style.borderColor = "#6caeca";
                this.getElementsByTagName("span")[0].innerHTML = that._text;
//                arrow.style.backgroundPosition = "0px 0px";
            }
            div.onclick = function(){
                this.style.zIndex = ++zIndex;
            }
            map.getPanes().labelPane.appendChild(div);
            return div;
        }
        ComplexCustomOverlayCar.prototype.draw = function(){
            var map = this._map;
            var pixel = map.pointToOverlayPixel(this._point);
            this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
            this._div.style.top  = pixel.y - 30 + "px";
        }
	    updateLocation();
	    function updateLocation(){
	    	map.clearOverlays()
	    	var points = [];
	    	var names= [];
	    	var txts = [];
            var employetypes= [];
	    	$.getJSON("phone/getLocationList.do",{},function(json){
		    	for(var i=0; i<json.length; i++){
		    		points.push(new BMap.Point(json[i].y, json[i].x));
		    		names.push(json[i].name);
                    employetypes.push(json[i].employetype);
		    		txts.push(json[i].name+ " "+ json[i].updatetime);
		    	}
		    	for(var i=0; i<points.length; i++){
			    	var mouseoverTxt = txts[i];
			    	var name = names[i];
		    		var gpsPoint = new BMap.Point(points[i].lng, points[i].lat);
                    callbackEmployetype(gpsPoint, name, mouseoverTxt,employetypes[i]);
		    	}
		    });
	    }
        function callbackEmployetype(point, name, mouseoverTxt,employetype){
            if(employetype != null){
                if(employetype.indexOf("1")>=0 || employetype.indexOf("3")>=0){
                    var myCompOverlay = new ComplexCustomOverlay(point, name, mouseoverTxt);
                    map.addOverlay(myCompOverlay);
                }else if(employetype.indexOf("6")>=0){
                    var myCompOverlay = new ComplexCustomOverlayCar(point, name, mouseoverTxt);
                    map.addOverlay(myCompOverlay);
                }else{
                    var myCompOverlay = new ComplexCustomOverlayOther(point, name, mouseoverTxt);
                    map.addOverlay(myCompOverlay);
                }
            }else{
                var myCompOverlay = new ComplexCustomOverlayOther(point, name, mouseoverTxt);
                map.addOverlay(myCompOverlay);
            }

        }
	    function callback(point, name, mouseoverTxt){
	    	//BMap.Convertor.translate(point,0,function(point){
		    	var myCompOverlay = new ComplexCustomOverlay(point, name, mouseoverTxt);
				map.addOverlay(myCompOverlay);     
		   //});    
	    }
	    var prevPoint = null;
	    var transFlag = true;
	    var points = [];
	    var times = [];
	    var txts = [];
	    var interval = null;
        var start = null;
        //根据部门，姓名，业务属性等查找路径
	    function queryInfoLocation(){
            prevPoint = null;
            transFlag = true;
            points = [];
            times = [];
            txts = [];
	    	map.clearOverlays();
	    	var deptid = $("#map_person_dialog_dept").widget('getValue');
            var date = $("input[name=date]").val();
            var employetype = $("select[name=employetype]").val();
            var leadid = $("input[name=leadid]").val();
	    	$.getJSON("phone/getNewLocationByInfo.do",
                    {"deptid":deptid, "date":date ,"employetype":employetype ,"leadid":leadid},function(json){
	    		if(json.length < 1){
	    			return ;
	    		}
                for(var i=0; i<json.length; i++){
                    points.push(new BMap.Point(json[i].y, json[i].x));
                    times.push(json[i].name);
                    txts.push(json[i].name+ "："+ json[i].updatetime);
                }
                var delay = 20;
                var index = 0;
                interval = setInterval(function(){
                    if(transFlag){
                        transFlag = false;
                        var mouseoverTxt = txts[index];
                        var time = times[index];
                        var gpsPoint = points[index];
                        index++;
                        callbackNoLine(gpsPoint, time, mouseoverTxt);
                    }
                    if(index == points.length){
                        stopInterval();
                    }
                }, delay);

	    	});
	    }
        //根据业务员和日期查找路线
        function queryLocation(){
            prevPoint = null;
            transFlag = true;
            points = [];
            times = [];
            txts = [];
            map.clearOverlays()
            var saleser = $("#map_dialog_saleser-hidden").val();
            var date = $("input[name=date]").val();
            var time = $("input[name=time]").val();
            $.getJSON("phone/getLocationHistoryList.do",{"userid":saleser, "date":date, "time":time},function(json){
                if(json.length < 1){
                    return ;
                }
                if(track_index==1){
                    map.clearOverlays();
                    for(var i=0; i<json.length; i++){
                        var data = json[i];
                        if(start == null){
                            start = new BMap.Point(parseFloat(data.y), parseFloat(data.x));
                            var time = data.updatetime.substring(11,16);
                            var lab = new BMap.Label(time,{position:start});
                            map.addOverlay(lab);
                        }
                        else{
                            var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: false}});
                            driving.setPolicy(BMAP_DRIVING_POLICY_LEAST_DISTANCE|BMAP_DRIVING_POLICY_AVOID_HIGHWAYS);
                            driving.setPolylinesSetCallback(function(){

                            });
                            var end = new BMap.Point(parseFloat(data.y), parseFloat(data.x));
                            driving.search(start, end);
                            var time = data.updatetime.substring(11,16);
                            var lab = new BMap.Label(time,{position:end});
                            map.addOverlay(lab);
                            start = end;
                        }
                    }
                }else{
                    for(var i=0; i<json.length; i++){
                        points.push(new BMap.Point(json[i].y, json[i].x));
                        times.push(json[i].name + "："+ json[i].updatetime.substring(11,16));
                        txts.push(json[i].name+ "："+ json[i].updatetime+ " 我在这里");
                    }
                    var delay = 300;
                    var index = 0;
                    interval = setInterval(function(){
                        if(transFlag){
                            transFlag = false;
                            var mouseoverTxt = txts[index];
                            var time = times[index];
                            var gpsPoint = points[index];
                            index++;
                            callback2(gpsPoint, time, mouseoverTxt);
                        }
                        if(index == points.length){
                            stopInterval();
                        }
                    }, delay);
                }

            });
        }

        function callbackNoLine(point, time, mouseoverTxt){
            transFlag = true;
            if(prevPoint == null) prevPoint = point;
            var myCompOverlay = new ComplexCustomOverlay(point, time, mouseoverTxt);
            map.addOverlay(myCompOverlay);
            prevPoint = point;
        }

	    function callback2(point, time, mouseoverTxt){
	    	//BMap.Convertor.translate(gpsPoint,0,function(point){
	    		transFlag = true;
	    		if(prevPoint == null) prevPoint = point;
		    	var myCompOverlay = new ComplexCustomOverlay(point, time, mouseoverTxt);
				map.addOverlay(myCompOverlay);   
                var polyline = new BMap.Polyline([
                    prevPoint,
                    point
                ], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});
                map.addOverlay(polyline);
	    		prevPoint = point;
		    //});
	    }
	    function stopInterval(){
	    	if(interval != null){
	    		clearInterval(interval);
	    	}
	    }
	    //右键菜单
	    var menu = new BMap.ContextMenu();
		var txtMenuItem = [
			{
				text:'轨迹查询',
		   		callback:function(){
		   			$("#map_dialog").dialog({
		   				width:400,
		   				height:260,
		   				title:"业务员轨迹查询",
		   				closed:false,
		   				modal: true,
		   				cache:true,
		   				buttons:[{
		   					text:"确定",
		   					handler:function(){
		   						var flag = $("#map_form").form("validate");
		   						if(!flag){
		   							return false;
		   						}
		   						var date = $("input[name=date]").val();
		   						var time = $("input[name=time]").val();
		   						if(date == "" && time == ""){
		   							alert("日期、时间点至少输入一个");
		   							return false;
		   						}
		   						queryLocation();
		   						$("#map_dialog").dialog('close', true);
		   					}	
		   				},{
		   					text:"取消",
		   					handler:function(){
		   						updateLocation();
		   						$("#map_dialog").dialog('close', true);
		   					}
		   				},
//                            {
//		   					text:"关闭",
//		   					handler:function(){
//		   						$("#map_dialog").dialog('close', true);
//		   					}
//		   				}
                        ]
		   			});
		   		}
		  	},
            {
                text:'人员筛选',
                callback:function () {
                    $("#map_person_dialog").dialog({
                        width:300,
                        height:200,
                        title:"人员路线查询",
                        closed:false,
                        modal: true,
                        cache:true,
                        buttons:[{
                            text:"查询",
                            handler:function(){
                                var flag = $("#map_person_form").form("validate");
                                if(!flag){
                                    return false;
                                }
                                var date = $("input[name=date]").val();
                                if(date == ""){
                                    alert("请选择要查询的日期");
                                    return false;
                                }
                                queryInfoLocation();
                                $("#map_person_dialog").dialog('close', true);
                            }
                        },{
                            text:"取消",
                            handler:function(){
                                updateLocation();
                                $("#map_person_dialog").dialog('close', true);
                            }
                        },
                        ]
                    });
                }
            }
		 ];
		 for(var i=0; i < txtMenuItem.length; i++){
		  menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
		 }
		 map.addContextMenu(menu);

        setInterval(function(){updateLocation();},5*60*1000 );
    </script>
  </body>
</html>
