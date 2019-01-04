<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>车辆档案修改页面</title>
	</head>

	<body>
		<form action="" method="post" id="logistics-form-carEdit">
			<table cellpadding="2" cellspacing="2" border="0">
				<tr>
					<td>
						编码:
					</td>
					<td>
						<input id="logistics-input-carId" name="car.id" type="text"
							required="true" class="easyui-validatebox" validType="validID"
							value="<c:out value="${car.id}"></c:out>" style="width: 200px;" <c:if test="${canEdit=='false'}">
							 readonly="readonly"  
							 </c:if>
							/>
						<input id="logistics-hidden-addType" name="type" type="hidden" />
						<input id="logistics-hidden-oldId" value="<c:out value="${car.id}"></c:out>" name="car.oldid" type="hidden" />
						<input id="logistics-name" value="<c:out value="${car.name}"></c:out>" type="hidden" />
					</td>
				</tr>
				<tr>
					<td>
						车牌号:
					</td>
					<td>
						<input id="logistics-input-carName" name="car.name" required="true"
							value="<c:out value="${car.name}"></c:out>" class="easyui-validatebox"
							validType="validName[20]" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						车型:
					</td>
					<td>
						<select name="car.cartype"
							style="width: 200px;" >
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
						<input required="true" name="car.weight" value="${car.weight}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 180px;" /><span style="width: 20px;">&nbsp;kg</span>
					</td>
				</tr>
				<tr>
					<td>
						装载箱数:
					</td>
					<td>
						<input name="car.boxnum" value="${car.boxnum }" data-options="min:0,max:99999,precision:0" class="easyui-numberbox"
							style="width: 180px;" /><span style="width: 20px;">&nbsp;箱</span>
					</td>
				</tr>
				<tr>
					<td>
						装载体积:
					</td>
					<td>
						<input name="car.volume" value="${car.volume }" required="true" data-options="min:0,max:99999,precision:4" class="easyui-numberbox"
							style="width: 180px" /><span style="width: 20px;">&nbsp;m³</span>
					</td>
				</tr>
				<tr>
					<td>
						默认司机:
					</td>
					<td>
						<input id="logistics-widget-driver" name="car.driverid"
							required="true" value="<c:out value="${car.driverid}"></c:out>" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						默认跟车:
					</td>
					<td>
						<input id="logistics-widget-follow" name="car.followid"
							required="true" value="<c:out value="${car.followid}"></c:out>" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td>
						第一次装车:
					</td>
					<td>
						<input name="car.truck1" required="true" value="${car.truck1}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" <c:if test="${showMap.truck1!='truck1'}">
											 readonly="readonly"  </c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						第二次装车:
					</td>
					<td>
						<input name="car.truck2" value="${car.truck2}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" <c:if test="${showMap.truck2!='truck2'}">
											 readonly="readonly"  </c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						第三次装车:
					</td>
					<td>
						<input name="car.truck3" value="${car.truck3}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" <c:if test="${showMap.truck3!='truck3'}">
											 readonly="readonly"  </c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						第四次装车:
					</td>
					<td>
						<input name="car.truck4" value="${car.truck4}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" <c:if test="${showMap.truck4!='truck4'}">
											 readonly="readonly"  </c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						第五次及以上:
					</td>
					<td>
						<input name="car.truck5" value="${car.truck5}" data-options="min:0,max:99999,precision:2" class="easyui-numberbox"
							style="width: 200px;" <c:if test="${showMap.truck5!='truck5'}">
											 readonly="readonly"  </c:if>/>
					</td>
				</tr>

				<tr>
					<td>
						备注:
					</td>
					<td>
						<textarea name="car.remark"
							style="height: 80px; width: 195px; overflow: hidden"><c:out value="${car.remark}"></c:out></textarea>
					</td>
				</tr>
				<tr>
					<td>
						状态:
					</td>
					<td>
						<input name="car.state" id="common-combobox-state"
							style="width: 200px" value="${car.state}" readonly="true"/>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
				/*
					if(${canEdit}==false){
						$("#logistics-input-carId").val("${car.id}");
						$.fn.validatebox.defaults.rules.validID.message = '编码已被引用,不能修改!';
							return false;
					}
					*/
					if($("#logistics-hidden-oldId").val() == $("#logistics-input-carId").val())
						return true;
					var reg=eval("/^[\\w-]+$/");
					if(reg.test(value)){
						var ret = car_AjaxConn({id:value},'basefiles/isRepeatCarId.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '格式错误!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if($("#logistics-name").val() == $("#logistics-input-carName").val())
						return true;
					if(value.length <= param[0]){
						var ret = car_AjaxConn({name:value},'basefiles/isRepeatCarName.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
		
		//新增车辆档案 
		function editCar(type){
			//$("#logistics-hidden-addType").val(type);
			//if(type == "save"){
				if(!$("#logistics-form-carEdit").form('validate')){
					return false;
				}
			//}
			var ret = car_AjaxConn($("#logistics-form-carEdit").serializeJSON(),'basefiles/editCar.do');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){
				refreshLayout('商品品牌【查看】','basefiles/showCarViewPage.do?id='+$("#logistics-input-carId").val(),'view');
				$("#car-table-list").datagrid('reload');
				if(type == "save"){
					$.messager.alert("提醒","保存成功!");
				}
				else{
					$.messager.alert("提醒","暂存成功!");
				}
			}
			else{
				if(type == "save"){
					$.messager.alert("提醒","保存失败!");
				}
				else{
					$.messager.alert("提醒","暂存失败!");
				}
			}
		}
		
		$(function(){
		
			$('#logistics-numberbox-margin').numberbox({   
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
		
			$("#car-button-layout").buttonWidget("initButtonType", 'edit');
    	});
    </script>
	</body>
</html>
