<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>业务员行程查询</title>
  	<%@include file="/include.jsp" %>  
  	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qYlCsNIpqrWPz7MHLjCeTLy5"></script>
  	<style type="text/css">
		html{height:100%;}
		body{height:100%;width:100%;margin:0px;padding:0px;}
		#container{height:100%;}
	</style>
  </head>
  <body>
	<div id="container"></div>
	<script type="text/javascript">
		var start = null;
		var map = new BMap.Map("container");
		var point = new BMap.Point(${location1},${location2});  // 创建点坐标  
		map.enableScrollWheelZoom(true);
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl());
		map.addControl(new BMap.MapTypeControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));
		map.centerAndZoom(point, 12);   
		//var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
		//var s = new BMap.Point(121.367619, 28.57603);
		//var e = new BMap.Point(121.359492, 28.594054);
		//driving.search(s, e);
		//loading("路线加载中...");
		$(function(){
			$.ajax({
				url:'sales/getRoute.do',
				dataType:'json',
				type:'post',
				data:'u=${userid}&t=${date}',
				success:function(json){
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
					driving.setSearchCompleteCallback(function(){
						//loaded();
					});
				}
			});
		});
	</script>
  </body>
</html>
