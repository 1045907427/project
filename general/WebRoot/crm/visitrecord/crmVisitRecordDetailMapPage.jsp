<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>拜访记录陈列地图</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qYlCsNIpqrWPz7MHLjCeTLy5" charset="UTF-8"></script>
  	<style type="text/css">
		html{height:100%;}
		body{height:100%;width:100%;margin:0px;padding:0px;}
		#container{height:100%;}
	</style> 
  </head>
  <body>
  <div id="container"></div>
	<script type="text/javascript">
	$(document).ready(function(){
		visitRecordMapWindowShow();
	});

	function visitRecordMapWindowShow(){
		
		var gps="${resultMap.detailgps}";
		var gpsArr=[];
		if(gps!=""){
			gpsArr=gps.split(",");
		}else{
			return false;
		}
		if(gpsArr.length!=2){
			return false;
		}
		
		// 百度地图API功能
		var map = new BMap.Map("container");
		var point = new BMap.Point(gpsArr[1],gpsArr[0]);  // 创建点坐标  //倒过来用的 
		var marker = new BMap.Marker(point);  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		map.centerAndZoom(point, 15);
		
		var category = '商品陈列';
		
		var msg="单据编号：${resultMap.id} <br/>";
		msg=msg+"客户：${resultMap.customerid} ${resultMap.customername} <br/>";
		msg=msg+"品牌：${resultMap.brandname} <br/>";

		var opts = {
		  width : 135,     		// 信息窗口宽度
		  height: 70,     		// 信息窗口高度
		  title: category + '信息',	// 信息窗口标题
		  enableMessage: false	//设置允许信息窗发送短息
		}
		var infoWindow = new BMap.InfoWindow(msg , opts);  // 创建信息窗口对象 
		marker.addEventListener("click", function(){          
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		});
			map.enableScrollWheelZoom(true);
	}
	</script>

</body>
</html>