<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>位置信息</title>
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
	<!--
	
	$(function(){

		$.ajax({
			url:'hr/signin/selectSigninInfo.do',
			dataType:'json',
			type:'post',
			data:{id:'${param.id }',index:'${param.index }'},
			success:function(json){
			
				// 百度地图API功能
				var map = new BMap.Map("container");
				var point = new BMap.Point(json.y, json.x);  // 创建点坐标  
				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);              // 将标注添加到地图中
				map.centerAndZoom(point, 15);
				
				var category = '';
				<c:choose>
					<c:when test="${param.out eq '1'}">
				  		category = "外出" ; 
				  	</c:when>
					<c:otherwise>
						category = "签到" ;
					</c:otherwise>
				</c:choose>

				var opts = {
				  width : 100,     		// 信息窗口宽度
				  height: 70,     		// 信息窗口高度
				  title: category + '信息',	// 信息窗口标题
				  enableMessage: false	//设置允许信息窗发送短息
				}
				var infoWindow = new BMap.InfoWindow(category + "时间：" + json.time, opts);  // 创建信息窗口对象 
				marker.addEventListener("click", function(){          
					map.openInfoWindow(infoWindow,point); //开启信息窗口
				});
				<c:if test="${param.zoom eq '1'}">
					map.enableScrollWheelZoom(true);
				</c:if>
			}
		});
	});

	-->
	</script>
  </body>
</html>
