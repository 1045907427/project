<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/手机模块</title>
    <%@include file="/include.jsp" %>
    
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 100px" id = 'PhoneRouteTime'>手机行程计算：</td>
								<td>
									<input type="text" style="width: 180px" name='PhoneRouteTime'>
								</td>
								
								<td align="right" style="width: 100px" id = 'LocationPoint'>地图焦点坐标：</td>
								<td>
									<input type="text" style="width: 180px" name='LocationPoint'>
								</td>
								<td align="right" style="width: 100px" id = 'PhoneGPSTime'>定位起始时间：</td>
								<td>
									<input type="text" style="width: 180px" name='PhoneGPSTime'>
								</td>
								
							</tr>
							
							<tr>
								<td align="right" style="width: 100px" id = 'PhoneOutURL'>手机访问地址：</td>
								<td colspan="4">
									<input type="text" style="width:490px" name='PhoneOutURL'>
								</td>
								
							</tr>
							<tr>
								<td align="right" style="width: 140px" id='OrderCarPhoneEditPrice'>车销价格修改：</td>
								<td>
									<select style="width: 180px" name='OrderCarPhoneEditPrice'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								<td align="right" style="width: 140px" id = 'OrderDemandPhoneEditPrice'>手机要货修改：</td>
								<td>
									<select style="width: 180px" name='OrderDemandPhoneEditPrice'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								<td align="right" style="width: 140px" id = 'InnerMessagePushToMobile'>启用消息推送：</td>
								<td>
									<select style="width: 180px" name='InnerMessagePushToMobile'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
						</table>
					</form>
					
					
	<script>
			$(function(){
			
				loadDetailData(8)
     			
			})
					
					
	</script>
  </body>
</html>
