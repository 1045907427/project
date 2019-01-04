<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>车辆档案详情页面</title>
	</head>

	<body>
		<form action="" method="post" id="logistics-form-carAdd">
			<table cellpadding="2" cellspacing="2" border="0">
				<tr>
					<td>
						编码:
					</td>
					<td>
						<input id="logistics-input-carId" name="car.id" type="text"
							readonly="readonly" value="<c:out value="${car.id}"></c:out>" style="width: 200px;" />
						<input id="logistics-hidden-addType" name="type" type="hidden" />
						<input id="logistics-oldId" value="<c:out value="${car.id}"></c:out>" type="hidden" />
					</td>
				</tr>
				<tr>
					<td>
						车牌号:
					</td>
					<td>
						<input name="car.name" readonly="readonly" value="<c:out value="${car.name}"></c:out>"
							style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						车型:
					</td>
					<td>
						<select name="car.cartype" disabled="disabled"
							style="width: 200px;" >
							<option></option>
						    		<option value="1" <c:if test="${car.cartype=='1'}">selected="selected"</c:if> >大车</option>
						    		<option value="2" <c:if test="${car.cartype=='2'}">selected="selected"</c:if> >中车</option>
						    		<option value="3" <c:if test="${car.cartype=='3'}">selected="selected"</c:if> >小车</option>
							</select>
					</td>
				</tr>
				
				<tr>
					<td>
						装载重量:
					</td>
					<td>
						<input readonly="readonly" name="car.weight" value="${car.weight}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 180px;" /><span style="width: 20px;">&nbsp;kg</span>
					</td>
				</tr>
				<tr>
					<td>
						装载箱数:
					</td>
					<td>
						<input readonly="readonly" name="car.boxnum" value="${car.boxnum }" data-options="min:0,max:99999,precision:0" class="easyui-numberbox"
							style="width: 180px;" /><span style="width: 20px;">&nbsp;箱</span>
					</td>
				</tr>
				<tr>
					<td>
						装载体积:
					</td>
					<td>
						<input name="car.volume" value="${car.volume }" data-options="min:0,max:99999,precision:4" class="easyui-numberbox"
							readonly="readonly" style="width: 180px" /><span style="width: 20px;">&nbsp;m³</span>
					</td>
				</tr>
				<tr>
					<td>
						默认司机:
					</td>
					<td>
						<input id="logistics-widget-driver" name="car.driverid"
							readonly="readonly" value="<c:out value="${car.driverid}"></c:out>" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						默认跟车:
					</td>
					<td>
						<input id="logistics-widget-follow" name="car.followid"
							readonly="readonly" value="<c:out value="${car.followid}"></c:out>" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第一次装车:
					</td>
					<td>
						<input name="car.truck1" required="true" value="${car.truck1}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第二次装车:
					</td>
					<td>
						<input name="car.truck2" value="${car.truck2}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第三次装车:
					</td>
					<td>
						<input name="car.truck3" value="${car.truck3}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第四次装车:
					</td>
					<td>
						<input name="car.truck4" value="${car.truck4}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第五次及以上:
					</td>
					<td>
						<input name="car.truck5" value="${car.truck5}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" />
					</td>
				</tr>

				<tr>
					<td>
						备注:
					</td>
					<td>
						<textarea name="car.remark" readonly="readonly"
							style="height: 80px; width: 195px; overflow: hidden"><c:out value="${car.remark}"></c:out></textarea>
					</td>
				</tr>
				<tr>
					<td>
						状态:
					</td>
					<td>
						<input id="common-combobox-state" type="text" disabled="disabled"
							style="width: 200px" value="${car.state}" />
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		$(function(){
		
			$('#wares-numberbox-margin').numberbox({   
			    min:0,
			    max:99999,
			    precision:2   
			});
			
			//状态
			$('#common-combobox-state').widget({
			   name:'t_base_logistics_car',
				col:'state',
				singleSelect:true
			});
			
			//司机
			$("#logistics-widget-driver").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
				width:200,
				singleSelect:true
    		});
    		
    		//跟车
    		$("#logistics-widget-follow").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
				width:200,
				singleSelect:true
    		});
		
			$("#car-button-layout").buttonWidget("setDataID",{id:$("#logistics-oldId").val(),state:"${car.state}",type:'view'});
    	});
    </script>
	</body>
</html>
