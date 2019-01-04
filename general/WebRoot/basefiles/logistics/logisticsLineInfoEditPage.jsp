<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>线路档案详情页面</title>
	</head>

	<body>
		<div id="line-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
			<div id="line-layout-detail-north" data-options="region:'north',border:false" style="height:82px;">
				<form id="line-form-head" action="" method="post" style="padding: 5px">
                    <input type="hidden" name="lineInfo.startpoint" value="<c:out value="${lineInfo.startpoint}"></c:out>"/>
                    <input type="hidden" name="lineInfo.defaultpoint" value="<c:out value="${lineInfo.defaultpoint}"></c:out>"/>
					<table cellspacing="5px" cellpadding="5px" border="0">
						<tbody>
							<tr>
								<td width="80px">
									<div align="right">
										编码:
									</div>
								</td>
								<td style="width: 5px"></td>
								<td>
									<input id="lineInfo-id-baseInfo" type="text"
										style="width: 120px;" name="lineInfo.id" value="<c:out value="${lineInfo.id}"></c:out>"
										class="easyui-validatebox" required="true" validType="validID" <c:if test="${canEdit=='false'}">
									 readonly="readonly"  
									 </c:if>/>
									<input id="lineInfo-oldid-baseInfo" type="hidden" name="lineInfo.oldid" value="<c:out value="${lineInfo.id}"></c:out>" />
									<input id="linePriceInfo-hidden-delIds" type="hidden">
									<input id="linePriceInfo-hidden-addClickNum" type="hidden"
										value="0">
									<input id="lineMeteringUnitInfo-hidden-delIds" type="hidden">
									<input id="lineMeteringUnitInfo-hidden-addClickNum" type="hidden"
										value="0">
									<input id="lineStorageInfo-hidden-delIds" type="hidden">
									<input id="lineStorageInfo-hidden-addClickNum" type="hidden"
										value="0">
									<input id="lineWaresClass-hidden-delIds" type="hidden">
									<input id="lineWaresClass-hidden-addClickNum" type="hidden"
										value="0">
									<input id="lineInfo-hidden-addType" type="hidden" name="type">
									<input id="lineInfo-hidden-operateType" type="hidden">
									<input id="lineInfo-dblclick-hdstorageid" type="hidden">
									<input id="lineInfo-dblclick-hdwaresClassid" type="hidden" />
									<input id="lineInfo-dblclick-hdstoragelocation" type="hidden" />
		
									<input id="logistics-linecar" type="hidden" />
									<input type="hidden" id="logistics-linecustomer" value="${customerids }"/>
									<!-- 
									<input id="lineInfo-carlist" type="hidden" value="${carlist}"/>
									 -->
								</td>
								<td width="80px">
									<div align="right">
										名称:
									</div>
								</td>
								<td style="width: 5px"></td>
								<td>
									<input type="text" style="width: 200px;" name="lineInfo.name"
										value="<c:out value="${lineInfo.name}"></c:out>" class="easyui-validatebox"
										required="true" />
								</td>
								<td width="80px">
									<div align="right">
										状态:
									</div>
								</td>
								<td style="width: 5px"></td>
								<td>
									<input id="lineInfo-widget-state" value="${lineInfo.state}"
										name="lineInfo.state" readonly="true" style="width: 200px" />
								</td>
								<!-- <td><select disabled="disabled" class="len130 easyui-combobox"><option>新增</option></select></td>
						 	-->
							</tr>
						</tbody>
					</table>
				</form>
				<ul class="tags">
					<security:authorize url="/basefiles/infoInfoBaseInfoTab.do">
						<li class="selectTag">
							<a href="javascript:void(0)">基本信息</a>
						</li>
					</security:authorize>
					<security:authorize url="/basefiles/infoInfoCarInfoTab.do">
						<li>
							<a href="javascript:void(0)">所属车辆</a>
						</li>
					</security:authorize>
					<security:authorize url="/basefiles/infoInfoCustomerInfoTab.do">
						<li>
							<a href="javascript:void(0)">线路客户</a>
						</li>
					</security:authorize>
					<security:authorize url="/basefiles/infoInfoDefineInfoTab.do">
						<li>
							<a href="javascript:void(0)">自定义信息</a>
						</li>
					</security:authorize>
				</ul>
			</div>
			<div id="line-layout-detail-center" data-options="region:'center',border:false">
				<div class="tagsDiv">
					<div class="tagsDiv_item" style="display: block;">
						<form action="" method="post" id="line-form-base">
							<table cellspacing="5px" cellpadding="5px" border="0">
								<tbody>
									<tr>
										<td width="120px">
											<div align="right">
												所属地区:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<input type="text" name="lineInfo.salesarea"
												value="<c:out value="${lineInfo.salesarea}"></c:out>" style="width: 120px"
												class="easyui-validatebox" id="line-customer-sortarea" />
										</td>
										<td width="120px">
											<div align="right">
												区域客户家数:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<input type="text" id="lineInfo-input-totalcustomers" value="${lineInfo.totalcustomers}"
												name="lineInfo.totalcustomers" readonly="readonly"
												style="width: 120px"/>
										</td>
										<td width="120px">
											<div align="right">
												默认车辆:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<select type="text" id="lineInfo-select-car"
												name="lineInfo.carid" value="<c:out value="${lineInfo.carname}"></c:out>"
												style="width: 120px">
												<option value="<c:out value="${lineInfo.carid}"></c:out>">
													<c:out value="${lineInfo.carname}"></c:out>
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td width="120px">
											<div align="right">
												线路暂估路程:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<input id="infoInfo-widget-distance" type="text"
												style="width: 120px" name="lineInfo.distance"
												value="<c:out value="${lineInfo.distance}"></c:out>" class="easyui-validatebox" />
										</td>
										<td width="120px">
											<div align="right">
												路程复杂度:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<select id="infoInfo-widget-linetype" type="text"
												style="width: 120px" name="lineInfo.linetype"
												value="${lineInfo.linetype}" class="easyui-validatebox">
												<option>
													简单
												</option>
												<option>
													普通
												</option>
												<option>
													复杂
												</option>
											</select>
										</td>
									</tr>
								</tbody>
							</table>
							<div style="margin-top: 25px; margin-left: 0px;">
								<div class="easyui-panel" title="控制信息">
									<table cellspacing="5px" cellpadding="5px" border="0">
										<tbody>
											<tr>
												<td width="120px">
													<div align="right">
														家数指标
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														name="lineInfo.basecustomers"
														value="${lineInfo.basecustomers}" class="easyui-validatebox"
														validType="number" required="true" <c:if test="${showMap.basecustomers!='basecustomers'}">
													 readonly="readonly"  </c:if>/>
												</td>
												<td width="120px">
													<div align="right">
														基本补贴:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														name="lineInfo.baseallowance"
														value="${lineInfo.baseallowance}" class="easyui-numberbox"
														data-options="min:0,max:999,precision:2" required="true"<c:if test="${showMap.baseallowance!='baseallowance'}">
													 readonly="readonly"  </c:if> />
												</td>
												<td width="120px">
													<div align="right">
														单家补贴:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														name="lineInfo.singleallowance"
														value="${lineInfo.singleallowance}" class="easyui-numberbox"
														data-options="min:0,max:999,precision:2" required="true"<c:if test="${showMap.singleallowance!='singleallowance'}">
													 readonly="readonly"  </c:if> />
												</td>
											</tr>
											<tr>
												<td width="120px">
													<div align="right">
														出车补贴:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input id="infoInfo-widget-bstype" name="lineInfo.carsubsidy"
														value="${lineInfo.carsubsidy}" style="width: 120px"
														class="easyui-numberbox"
														data-options="min:0,max:999,precision:2" required="true"<c:if test="${showMap.carsubsidy!='carsubsidy'}">
													 readonly="readonly"  </c:if> />
												</td>
												<td width="120px">
													<div align="right">
														出车津贴:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														name="lineInfo.carallowance"
														value="${lineInfo.carallowance}" class="easyui-numberbox"
														required="true" data-options="min:0,max:999,precision:2"<c:if test="${showMap.carallowance!='carallowance'}">
													 readonly="readonly"  </c:if> />
												</td>
											</tr>
                                            <tr>
                                                <td>
                                                    <div align="right">
                                                        备注:
                                                    </div>
                                                </td>
                                                <td style="width: 5px"></td>
                                                <td colspan="7">
                                                    <textarea name="lineInfo.remark" cols="" rows="3"
                                                              style="width: 700px"><c:out value="${lineInfo.remark}"></c:out></textarea>
                                                </td>
                                            </tr>
										</tbody>
									</table>
								</div>
							</div>
							<div style="margin-top: 25px; margin-left: 0px;">
								<div class="easyui-panel" title="回写信息">
									<table cellspacing="5px" cellpadding="5px" border="0">
										<tbody>
											<tr>
												<td width="120px">
													<div align="right">
														出车次数总计:
													</div>
												</td>
												<td></td>
												<td width="120px">
													<input type="text" style="width: 120px"
														class="easyui-numberbox" name="lineInfo.totalcars"
														value="${lineInfo.totalcars}" readonly="readonly" />
												</td>
												<td width="120px">
													<div align="right">
														出车家数总计:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														class="easyui-numberbox" name="lineInfo.totalcarcustomers"
														value="${lineInfo.totalcarcustomers}" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													<div align="right">
														线路收款总计金额:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														data-options="min:0,max:999,precision:2"
														class="easyui-numberbox" name="lineInfo.totalamount"
														value="${lineInfo.totalamount}" readonly="readonly" />
												</td>
												<td>
													<div align="right">
														最新收款金额:
													</div>
												</td>
												<td style="width: 5px"></td>
												<td>
													<input type="text" style="width: 120px"
														data-options="min:0,max:999,precision:2"
														class="easyui-numberbox" name="lineInfo.newamount"
														value="${lineInfo.newamount}" readonly="readonly" />
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</form>
					</div>
					<div class="tagsDiv_item">
						<table id="line-table-car"></table>
					</div>
					<div class="tagsDiv_item">
						<table id="line-table-customer"></table>
					</div>
					<div class="tagsDiv_item">
						<form action="" method="post" id="line-form-define">
							<table border="0" cellpadding="2px" cellspacing="2px">
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field01 != null}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
												<c:otherwise>自定义信息01</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field01" type="text" style="width: 120px;"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field01==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field01}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field02 != null}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
												<c:otherwise>自定义信息02</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field02" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field02==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field02}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field03 != null}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
												<c:otherwise>自定义信息03</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field03" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field03==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field03}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field04 != null}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
												<c:otherwise>自定义信息04</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field04" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field04==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field04}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field05 != null}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
												<c:otherwise>自定义信息05</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field05" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field05==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field05}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field06 != null}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
												<c:otherwise>自定义信息06</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field06" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field06==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field06}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field07 != null}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
												<c:otherwise>自定义信息07</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field07" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field07==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field07}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field08 != null}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
												<c:otherwise>自定义信息08</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field08" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field08==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field08}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field09 != null}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
												<c:otherwise>自定义信息09</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td>
										<input name="lineInfo.field09" type="text" style="width: 120px"
											class="easyui-validatebox" validType="maxLen[100]"
											<c:choose>
										<c:when test="${showMap.field09==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field09}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field10 != null}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
												<c:otherwise>自定义信息10</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td colspan="5">
										<input type="text" name="lineInfo.field10" style="width: 625px;"
											<c:choose>
										<c:when test="${showMap.field10==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field10}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field11 != null}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
												<c:otherwise>自定义信息11</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td colspan="5">
										<input type="text" name="lineInfo.field11" style="width: 625px;"
											<c:choose>
										<c:when test="${showMap.field11==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field11}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											<c:choose>
												<c:when test="${fieldmap.field12 != null}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
												<c:otherwise>自定义信息12</c:otherwise>
											</c:choose>
										</label>
										：
									</td>
									<td colspan="5">
										<input type="text" name="lineInfo.field12" style="width: 625px;"
											<c:choose>
										<c:when test="${showMap.field12==null}"> value=""</c:when>
										<c:otherwise>value="<c:out value="${lineInfo.field12}"></c:out>" </c:otherwise>
									</c:choose> />
									</td>
								</tr>
                                <!-- 该行没有意义，保证页面对齐设置-->
                                <tr>
                                    <td><label></label> </td>
                                    <td colspan="5">
                                        <input type="text" name="lineInfo.field12" style="width: 650px;border: 2px solid snow;"
                                                <c:choose>
                                                    <c:when test="${showMap.field12==null}"> value=""</c:when>
                                                    <c:otherwise>value="<c:out value="${lineInfo.field12}"></c:out>" </c:otherwise>
                                                </c:choose> />
                                    </td>
                                </tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
    //检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
				/*
				if(${canEdit}==false){
					$("#lineInfo-id-baseInfo").val("${lineInfo.id}");
					$.fn.validatebox.defaults.rules.validID.message = '编码已被引用,不能修改!';
						return false;
				}
				*/
				if($("#lineInfo-oldid-baseInfo").val() == $("#lineInfo-id-baseInfo").val())
						return true;
					var reg=eval("/^[\\w-]+$/");
					if(reg.test(value)){
						var ret = lineInfo_AjaxConn({id:value},'basefiles/isRepeatLineInfoID.do');//true 重复，false 不重复
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
    
    	$("#line-customer-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:true,
   			width:120,
   			onlyLeafCheck:false,
   			view: true
   		});
    
    	var $lineInfo_headInfo = $("#line-form-head");//线路档案基本主信息 
		var $lineInfo_baseInfo = $("#line-form-base");//基本信息
		var $lineInfo_carlInfo = $("#line-table-car");//车辆信息
		var $lineInfo_customerInfo = $("#line-table-customer");//客户信息
		var $lineInfo_defineInfo = $("#line-form-define");//自定义信息
		
		function lineInfoFormValidate(){//true验证成功，false验证失败
			return $lineInfo_headInfo.form('validate') && $lineInfo_baseInfo.form('validate') && $lineInfo_defineInfo.form('validate');
		}
		
		function table2Json(table,name){
   			if(table!=null){
	   			if(!table.hasClass("create-datagrid"))
	   				return null;
   				var effectRow = new Object();
   				var date = table.datagrid('getRows');
   				if(date!=null){
	   				effectRow[name] = JSON.stringify(date);
	   				return effectRow;
   				}
   			}
	        return null;
   		}
   		
		//商品档案数据表单json对象集合
		function lineInfo_JSONs(){
			var wholeInfo = $lineInfo_headInfo.serializeJSON();
			var baseInfo = $lineInfo_baseInfo.serializeJSON();
			var carInfo = table2Json($lineInfo_carlInfo,'carInfo');
			var customerInfo = table2Json($lineInfo_customerInfo,'customerInfo');
			var defineInfo = $lineInfo_defineInfo.serializeJSON();
			
			for(key in baseInfo){
				wholeInfo[key] = baseInfo[key];
			};
			for(key in carInfo){
				wholeInfo[key] = carInfo[key];
			};
			for(key in customerInfo){
				wholeInfo[key] = customerInfo[key];
			};
			for(key in defineInfo){
				wholeInfo[key] = defineInfo[key];
			};
			return wholeInfo;
		}
    	
    	function editLineInfo(type){
    		var ret = lineInfo_AjaxConn(lineInfo_JSONs(),'basefiles/editLineInfo.do');
    		var retJson = $.parseJSON(ret);
    		loaded();
    		if(!retJson.lockFlag){
    			$.messager.alert("提醒","该数据被其他人操作,暂不能修改!");
    			return false;
    		}
    		if(retJson.flag){
    			if (top.$('#tt').tabs('exists','线路档案列表')){
    				tabsWindow('线路档案列表').$("#logistics-table-logisticsLineInfoList").datagrid('reload');
    			}
    			//$("#lineInfo-div-button").buttonWidget("addNewDataId",$("#lineInfo-id-baseInfo").val());
    			panelRefresh('basefiles/showLineInfoViewPage.do?id='+$("#lineInfo-id-baseInfo").val(),' 线路档案【详情】','view');
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
    	
    	//默认车辆combox
		function carNameSelect()
		{
			var rows=${carlist};
			
			if($dgInfoCar != null && $dgInfoCar.hasClass("create-datagrid"))
			{
				rows = $dgInfoCar.datagrid('getRows');
			}
			var optionarr=new Array();
			var selectid=$("#lineInfo-select-car").val();
			if(rows != null)
			{
				for(var i=0; i<rows.length; i++){
					optionarr.push("<option");
					optionarr.push(" value='");
					optionarr.push(rows[i].id);
					optionarr.push("'");
					if(selectid==rows[i].id){
						optionarr.push("selected='selected'");
					}
					optionarr.push(">");
					optionarr.push(rows[i].name);
					optionarr.push("</option>");
				}
				$("#lineInfo-select-car").html(optionarr.join(""));
			}
		}
    	
    	var $dgInfoCustomer = $("#line-table-customer");
    	var $dgInfoCar =$("#line-table-car");
		var carids="";
		var seqcustomerid = "";
		var oldseq = 0;
		var insertmap = {};
		
		var lineInfo_editIndexSeq = undefined;
		function lineInfo_editEditSeq(){
   			if (lineInfo_editIndexSeq == undefined){
   				return true
   			}else{return false;}
   		}
   		
   		//冒泡排序-升序线路客户
   		function sortLineCustomerList(customerid,editseq,oldseq){
   			var lineid = $("#lineInfo-id-baseInfo").val();
   			var customerids = $("#logistics-linecustomer").val();
   			$.ajax({   
	            url :'basefiles/sortLineCustomerList.do',
	            type:'post',
	            dataType:'json',
	            data:{lineid:lineid,customerid:customerid,editseq:editseq,oldseq:oldseq,customerids:customerids,insertmap:JSON.stringify(insertmap)},
	            success:function(retJson){
	            	$dgInfoCustomer.datagrid('loadData',retJson);
	            }
	        });
   		}
		
    	$(function(){
    		$("#lineInfo-div-button").buttonWidget('initButtonType','edit');
    		loadDropdown();
    		carNameSelect();
    		
    		var id = $("#lineInfo-id-baseInfo").val();
	    	var carUrl = "basefiles/showCarInfoList.do?lineid="+id;
			var customerUrl = "basefiles/showCustomerInfoList.do?lineid="+id;
    		
    		$(".tags").find("li").click(function(){
    			var height = $(window).height()-142;
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					if($dgInfoCustomer != null && $dgInfoCustomer.hasClass("create-datagrid"))
						// $("#lineInfo-input-totalcustomers").val($dgInfoCustomer.datagrid('getRows').length);
					carNameSelect();
				}
				if(index == 1){
					//var $dgInfoCar = $("#line-table-car");
					if(!$dgInfoCar.hasClass("create-datagrid")){
						$dgInfoCar.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:false,
				  	 		checkOnSelect:true,
				  	 		selectOnCheck:true,
				  	 		height:height,
				  			url:carUrl,
				  			pageSize:500,
					  		border:false,
					  		pagination:true,
                            sortName:'id',
				   			columns:[[
				   				//{field:'id',title:'编号',width:150,hidden:true},
				   				//{field:'lineid',title:'线路编码',width:150,hidden:true},
				   				{field:'ck',title:'',width:100,checkbox:true},
				   				{field:'id',title:'车辆编码',width:80},
				  				{field:'name',title:'车辆名称',width:100,align:'left'},
				  				{field:'remark',title:'备注',width:200,align:'left'}
				   			]],
				   			toolbar : [ {
					                text : "添加",
					                iconCls : "button-add",
					                handler : function() {
					                	//if(endEditingCustomer()){
					                	
				                		var lineid = $("#lineInfo-id-baseInfo").val();
				                		if("" == lineid){
				                			$.messager.alert("提醒","请先输入线路编码!");
				                			$("#lineInfo-id-baseInfo").focus();
				                			return false;
				                		}
				                		
				                		carids="";
				                		var rows = $dgInfoCar.datagrid('getRows');
					                	for(var i=0;i<rows.length;i++){
					                		if(carids == ""){
					                			carids = rows[i].id;
					                		}else{
					                			carids += "," + rows[i].id;
					                		}
					                	}
					                	
					                	$("#logistics-linecar").val(carids);
				                		
				                		$('#line-dialog-car').dialog({  
										    title: '对应车辆新增',  
										    width: 500,
										    height: 400,  
										    closed: false,
										    cache: false,
										    href: 'basefiles/showCarToLineAddPage.do',
											queryParams:{
                                                lineid:lineid
											},
										    modal: true
										});
					                }
					            },{
					            	text : "删除",
					                iconCls : "button-delete",
					                handler : function() {
                                        var lineid = $("#lineInfo-id-baseInfo").val();
					                	var rows = $dgInfoCar.datagrid('getChecked');
					                	if(rows.length == 0){
					                		$.messager.alert("提醒","请选择对应车辆!");
		            						return false;
					                	}
					                	$.messager.confirm("警告","是否删除勾选车辆?",function(r){
					                		if(r){
					                		    var carids="";
						                		for(var i=0;i<rows.length;i++){
						                		    if(carids==''){
						                		        carids=rows[i].id;
													}else{
						                		        carids+=","+rows[i].id;
													}
						                		}
                                                var ret = lineInfo_AjaxConn({lineid:lineid,carids:carids},'basefiles/deleteCarForLine.do');
                                                var retJson = $.parseJSON(ret);
                                                if(retJson.flag){
                                                    $.messager.alert("提醒","删除车辆成功");
                                                    $dgInfoCar.datagrid('reload');
                                                }else{
                                                    $.messager.alert("提醒","删除线路车辆出错");
                                                    return false;
                                                }
					                		}
					                	});
						                personnel_editIndexCustomer = undefined;
						                $dgInfoCar.datagrid('clearSelections');
					                }
					            }],
					            onLoadSuccess:function(data){
					            	carNameSelect();
					            }
				    	});
						$dgInfoCar.addClass("create-datagrid");
					}
				}
				else if(index == 2){
					if(!$dgInfoCustomer.hasClass("create-datagrid")){
		                	$dgInfoCustomer.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:false,
					  			checkOnSelect:true,
					  			selectOnCheck:true,
					  			height:height,
					  			pageSize:500,
					  			border:false,
					  			pagination:true,
					  			url:customerUrl,
					  			columns:[[
					  				{field:'ck',title:'',width:100,checkbox:true},
			        				{field:'customerid',title:'客户编码',width:80},
				  					{field:'customername',title:'客户名称',width:220,align:'left'},
                                    {field:'carnum',title:'装车次数',width:60},
				  					{field:'seq',title:'排序',width:60,align:'left',isShow:true,
				  						editor:{
						  					type:'numberbox',
						  					options:{
						  						precision:0,
						  						min:1
						  					}
					  					}
				  					}
					  			]],
								toolbar : [ {
					                text : "添加",
					                iconCls : "button-add",
					                handler : function() {
				                		var lineid = $("#lineInfo-id-baseInfo").val();
				                		if("" == lineid){
				                			$.messager.alert("提醒","请先输入线路编码!");
				                			$("#lineInfo-id-baseInfo").focus();
				                			return false;
				                		}
				                		var row = $dgInfoCustomer.datagrid('getSelected');
				                		var insertcustomerid = "";
				                		if(null != row){
				                			insertcustomerid = row.customerid;
				                		}
				                		$('#line-dialog-customer').dialog({  
										    title: '对应客户新增',
										    width: 600,
										    height: 400,  
										    closed: false,
										    cache: false,
										    href: 'basefiles/showCustomerToLineAddPage.do?lineid=' + lineid + '&insertcustomerid='+insertcustomerid,
											queryParams:{
                                                type:'edit'
											},
										    modal: true,
                                            maximized: true
										});
					                }
					            },{
					            	text : "删除",
					                iconCls : "button-delete",
					                handler : function() {
                                        var lineid = $("#lineInfo-id-baseInfo").val();
					                	var rows = $dgInfoCustomer.datagrid('getChecked');
					                	if(rows.length == 0){
					                		$.messager.alert("提醒","请选择对应客户!");
		            						return false;
					                	}
                                        $.messager.confirm("警告","是否删除勾选客户?",function(r){
                                            if(r){
                                                var customerstr = $("#logistics-linecustomer").val();
                                                var customerids="";
                                                if("" != customerstr){
                                                    for(var i=0;i<rows.length;i++){
                                                        customerstr = customerstr.replace(rows[i].customerid+",","");
                                                        // var index=$dgInfoCustomer.datagrid('getRowIndex',rows[i]);
                                                        // $dgInfoCustomer.datagrid('deleteRow',index);
                                                        if(customerids==''){
                                                            customerids=rows[i].customerid;
                                                        }else{
                                                            customerids+=","+rows[i].customerid;
                                                        }
                                                    }

                                                    $("#logistics-linecustomer").val(customerstr);
                                                    // var queryJSON = {};
                                                    // queryJSON['customerids'] = customerstr;
                                                    // queryJSON['lineid'] = $("#lineInfo-id-baseInfo").val();
                                                    // queryJSON['insertmap'] = JSON.stringify(insertmap);
                                                    // $dgInfoCustomer.datagrid('options').url = 'basefiles/showLineCustomerListDataByDel.do';
                                                    // $dgInfoCustomer.datagrid('load',queryJSON);
                                                    $("#lineInfo-input-totalcustomers").val(customerstr.split(",").length-Number(1));
                                                    loading("删除中..");
                                                    var ret = lineInfo_AjaxConn({lineid:lineid,customerids:customerids},'basefiles/deleteCustomerForLine.do');
                                                    loaded();
                                                    var retJson = $.parseJSON(ret);
                                                    if(retJson.flag){
                                                        $.messager.alert("提醒","删除成功!");
                                                        $dgInfoCustomer.datagrid('reload');
                                                    }
                                                }
                                                personnel_editIndexCustomer = undefined;
                                                $dgInfoCustomer.datagrid('clearSelections');
                                            }
                                        });
					                }
					            }],
					            onDblClickRow:function(rowIndex, rowData){
					            	if(lineInfo_editEditSeq()){
					            		$("#lineInfo-index-seq").val(rowIndex);
					            		$dgInfoCustomer.datagrid('beginEdit', rowIndex);
										lineInfo_editIndexSeq = rowIndex;
										$dgInfoCustomer.datagrid('selectRow',rowIndex);
										seqcustomerid = rowData.customerid;
										oldseq = rowData.seq;
					            	}
					            },
				   				onClickRow:function(rowIndex, rowData){
				   					if(!lineInfo_editEditSeq()){
				   						var eseq = $dgInfoCustomer.datagrid('getEditor', {index:lineInfo_editIndexSeq,field:'seq'});
				   						if(null != eseq){
				   							var newseq = $(eseq.target).numberbox("getValue");
				   							if(oldseq != newseq){
				   								sortLineCustomerList(seqcustomerid,newseq,oldseq);
				   							}
				   						}
				   						$dgInfoCustomer.datagrid('endEdit', lineInfo_editIndexSeq);
				   						lineInfo_editIndexSeq = undefined;
				   					}
				   				}
					   		});
					   		$dgInfoCustomer.addClass("create-datagrid");
		                }
				}
			});
    	});
    </script>
	</body>
</html>