<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>我的工作-工作统计图</title>
	<link rel="stylesheet" href="../activiti/css/flot.css" type="text/css"></link>
    <script type="text/javascript" src="../activiti/js/jquery.js"></script>
    <script type="text/javascript" src="../activiti/js/jquery.flot.js"></script>
    <script type="text/javascript" src="../activiti/js/jquery.flot.pie.js"></script>
  </head>
  
  <body id="activiti-body-workPiePage">
	<div style="width: 380px; height: 210px;">
		<div style="height: 10px;"></div>
		<div id="activiti-placeholder-workPiePage" style="width: 100%; height: 100%;"></div>
		<div style="height: 10px;"></div>
	</div>
	
	<script type="text/javascript">
	
		var type6_text = '未接收';
		var type7_text = '办理中';
		var type8_text = '已办结';
		var type9_text = '已删除';
		var type12_text = '已挂起';
		
		var x = 0;
		var y = 0;

		$(function() {
		
			$("#activiti-body-workPiePage").mousemove(function(e){

				if(x != e.pageX || y != e.pageY) {
			  
					$('#tips').remove();
				}
			});
		
			$("#activiti-body-workPiePage").mouseleave(function(e){

				//if(x != e.pageX || y != e.pageY) {
			  
					$('#tips').remove();
				//}
			});
			
			var data = [
				{ label: type6_text,   data: ${param.type6 }},
				{ label: type7_text,   data: ${param.type7 }},
				{ label: type8_text,   data: ${param.type8 }},
				{ label: type9_text,   data: ${param.type9 }},
				{ label: type12_text,  data: ${param.type12 }}
			];
			
			var placeholder = $('#activiti-placeholder-workPiePage');
		
			placeholder.unbind();

			$.plot(placeholder, data, {
				series: {
					pie: { 
						show: true
					}
				},
				grid: {
					hoverable: true,
					clickable: true
				}
			});

			placeholder.bind("plothover", function(event, pos, obj) {

				if (!obj) {
					return;
				}

				$('#tips').remove();
				$('body').after('<div id="tips" style="position: absolute; top: ' + (pos.pageY + 5) + 'px; left: ' + (pos.pageX + 5) + 'px; background: #FFF; font-size: 13px; border: 1px solid #EEE;">' + '&nbsp;&nbsp;' + obj.series.label + ': ' + obj.series.data[0][1] + '</div>');
				
				y = pos.pageY;
				x = pos.pageX;
			});

			placeholder.bind("plotclick", function(event, pos, obj) {

				if (!obj) {
					return;
				}
				
				if(obj.series.label == type6_text) {
				
					top.addOrUpdateTab('act/myWorkPage6.do?definitionkey=${param.definitionkey }', '未接收');
				} else if(obj.series.label == type7_text) {
				
					top.addOrUpdateTab('act/myWorkPage7.do?definitionkey=${param.definitionkey }', '办理中');
				} else if(obj.series.label == type8_text) {
				
					top.addOrUpdateTab('act/myWorkPage8.do?definitionkey=${param.definitionkey }', '已办结');
				} else if(obj.series.label == type9_text) {
				
					top.addOrUpdateTab('act/myWorkPage9.do?definitionkey=${param.definitionkey }', '已删除');
				} else if(obj.series.label == type12_text) {
				
					top.addOrUpdateTab('act/myWorkPage12.do?definitionkey=${param.definitionkey }', '已挂起');
				}
				
			});
			
		});		
		
	</script>
  </body>
</html>
