<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>查看签到详情</title>
    <%@include file="/include.jsp" %>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=72a3befb0f0e332109e8d9dc7c1961d6"></script>
  </head>
  
  <body>
  	<div style="padding-top: 5px; padding-left: 5px; padding-bottom: 5px;">
  		<table class="signin-table">
  			<tr>
  				<td class="left" style="width: 60px; background: rgb(222, 233, 244);">日期</td><td class="len120"><c:out value="${signin.businessdate }"></c:out></td>
  				<td class="left" style="width: 60px; background: rgb(222, 233, 244);">人员</td><td class="len120"><c:out value="${signin.personname }"></c:out></td>
  				<td class="left" style="width: 60px; background: rgb(222, 233, 244);">用户</td><td class="len120"><c:out value="${signin.username }"></c:out></td>
  				<td class="left" style="width: 60px; background: rgb(222, 233, 244);">备注</td><td><c:out value="${signin.remark }"></c:out></td>
  			</tr>
			<c:if test="${!empty signin.ambegin }">
				<tr>
					<td class="left" style="width: 60px; background: rgb(222, 233, 244);">上午上班</td>
					<td class="left"><fmt:formatDate value="${signin.ambegin }" type="both"/></td>
					<td class="len120" colspan="3"><div><img src="${signin.ambeginfile }" class="max-size viewdetail"/></div></td>
					<td class="len120 loadmap" colspan="3">地图加载中...
						<input type="hidden" name="coordinateX" id="coordinateX" value="${signin.ambeginx }"/>
						<input type="hidden" name="coordinateY" id="coordinateY" value="${signin.ambeginy }"/>
						<input type="hidden" name="coordinateIndex" value="0"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty signin.amend }">
				<tr>
					<td class="left" style="width: 60px; background: rgb(222, 233, 244);">上午下班</td>
					<td class="left"><fmt:formatDate value="${signin.amend }" type="both"/></td>
					<td class="len120" colspan="3"><div><img src="${signin.amendfile }" class="max-size viewdetail"/></div></td>
					<td class="len120 loadmap" colspan="3">地图加载中...
						<input type="hidden" name="coordinateX" id="coordinateX" value="${signin.amendx }"/>
						<input type="hidden" name="coordinateY" id="coordinateY" value="${signin.amendy }"/>
						<input type="hidden" name="coordinateIndex" value="1"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty signin.pmbegin }">
				<tr>
					<td class="left" style="width: 60px; background: rgb(222, 233, 244);">下午上班</td>
					<td class="left"><fmt:formatDate value="${signin.pmbegin }" type="both"/></td>
					<td class="len120" colspan="3"><div><img src="${signin.pmbeginfile }" class="max-size viewdetail"/></div></td>
					<td class="len120 loadmap" colspan="3">地图加载中...
						<input type="hidden" name="coordinateX" id="coordinateX" value="${signin.pmbeginx }"/>
						<input type="hidden" name="coordinateY" id="coordinateY" value="${signin.pmbeginy }"/>
						<input type="hidden" name="coordinateIndex" value="2"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty signin.pmend }">
				<tr>
					<td class="left" style="width: 60px; background: rgb(222, 233, 244);">下午下班</td>
					<td class="left"><fmt:formatDate value="${signin.pmend }" type="both"/></td>
					<td class="len120" colspan="3"><div><img src="${signin.pmendfile }" class="max-size viewdetail"/></div></td>
					<td class="len120 loadmap" colspan="3">地图加载中...
						<input type="hidden" name="coordinateX" id="coordinateX" value="${signin.pmendx }"/>
						<input type="hidden" name="coordinateY" id="coordinateY" value="${signin.pmendy }"/>
						<input type="hidden" name="coordinateIndex" value="3"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty signin.outtime }">
				<tr>
					<td class="left" style="width: 60px; background: rgb(222, 233, 244);"><font color="#f00">外出</font></td>
					<td class="left"><fmt:formatDate value="${signin.outtime }" type="both"/></td>
					<td class="len120" colspan="3"><div><img src="${signin.outpic }" class="max-size viewdetail"/></div></td>
					<td class="len120 loadmap" colspan="3">地图加载中...
						<input type="hidden" name="out" value="1"/>
						<input type="hidden" name="coordinateIndex" value="4"/>
					</td>
				</tr>
			</c:if>
  		</table>
  	</div>
    <style type="text/css">
    	.signin-table{border-collapse:collapse;border:1px solid #B8D1E2;width:100%;bckground:#F0F0F0;}
    	.signin-table td{border:1px solid #B8D1E2;line-height:22px; padding-left: 5px;}
    	.max-size{
			margin: 5px 5px 5px 5px;
    		max-width: 230px;
    		max-height: 410px;
			width: expression(this.width > 230 ? "230px" : this.width);
			height: expression(this.height > 410 ? "410px" : this.height);
    	}
    	.viewdetail {
    		cursor: pointer;
    	}
    </style>
	<script type="text/javascript">
	<!--
	
	$(function(){

		$('#signin-dialog-signinPage').after('<div id="hr-dialog-signinViewPage"></div>');

		$('.viewdetail').unbind().click(function(){

			var image_src = $(this).attr('src');
			$('#hr-dialog-signinViewPage').html('<img id="hr-image-signinViewPage" style="margin: 10px 10px 10px 10px;" src="' + image_src + '"/>');

			$('#hr-dialog-signinViewPage').window({
			
				title:'查看图片',
				width:600,
				height:450,
				closed:false,
				cache:false,
				modal: true,
				maximizable:true,
				maximized: true,
				resizable:true
			});
		});

		setTimeout(function(){
		
			$('.loadmap').each(function(){

				var coordinateIndex = $(this).children().last().val();
				var out = $(this).children().first().val();
		
				$(this).html('<div id="hr-map-signinViewPage" class="map" style="padding-top: 5px; padding-left: 5px; padding-bottom: 5px; float:left; width: 80%;"><iframe width="100%" height="100%" src="hr/signin/signinLocationPage.do?id=${param.id }&index=' + coordinateIndex + '&out=' + out + '"  frameborder="0"></iframe></div>');
				$(this).append('<div class="viewmap" style="float:left;position: relative;"><a href="javascript:void(0);" class="easyui-linkbutton viewMap" data="' + coordinateIndex + '" data-options="plain:true,iconCls:\'button-view\'">查看</a></div>');
				var height = $(this).prev().children().children().css('height');
					
				height = height.substring(0, height.length - 2);
				if(height < 300) {
					height = 300;
				}
				$(this).children('.map').first().css('height', height);
				$(this).children('.viewmap').first().css('top', height - 20);
			});
			
			$('a').linkbutton({});

			$('.viewMap').click(function(){
				
				var index = $(this).attr('data');
				
				viewMap('${signin.id }', index);
			});

		}, 500);
		
	});
	
	function viewMap(id, index) {

		if(index == 4) {
			$('#hr-dialog-signinViewPage').html('<iframe width="100%" height="100%" src="hr/signin/signinLocationPage.do?id=' + id + '&index=' + index + '&zoom=1&out=1" frameborder="0"></iframe>');
		} else {
			$('#hr-dialog-signinViewPage').html('<iframe width="100%" height="100%" src="hr/signin/signinLocationPage.do?id=' + id + '&index=' + index + '&zoom=1" frameborder="0"></iframe>');
		}

		$('#hr-dialog-signinViewPage').window({

			title:'查看位置',
			width:600,
			height:450,
			closed:false,
			cache:false,
			modal: true,
			maximizable:true,
			maximized: true,
			resizable:true
		});

	}
	
	-->
	</script>
  </body>
</html>
