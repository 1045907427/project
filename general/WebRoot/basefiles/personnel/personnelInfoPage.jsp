<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>人员信息详情</title>
  </head>
  
  <body>
  	<div id="personnel-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
  		<div id="personnel-layout-detail-north" data-options="region:'north',border:false" style="height:62px">
  			<form action="" id="personnel-form-head" method="post" style="padding: 5px">
		  		<input id="personnel-hidden-hdoldIdEdit" type="hidden" value="<c:out value="${personnel.id }"></c:out>"/>
		  		<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 15px"></td>
						<td style="width: 40px">编码:</td>
						<td style="width: 15px"></td>
						<td><input type="text" id="basefiles-id-personnel" style="width:200px;"
							<c:choose>
								<c:when test="${showMap.id==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="<c:out value="${personnel.id }"></c:out>" </c:otherwise>
							</c:choose>/></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">姓名:</td>
						<td style="width: 15px"></td>
						<td><input type="text" style="width:200px;"
							<c:choose>
								<c:when test="${showMap.name==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="<c:out value="${personnel.name }"></c:out>" </c:otherwise>
							</c:choose>/></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">状态:</td>
						<td style="width: 15px"></td>
						<td><input id="personnel-widget-state" value="${personnel.state}" disabled="disabled" /></td>
					</tr>
				</table>
		  	</form>
		  	<ul class="tags">
				<security:authorize url="/basefiles/showPersonnelBaseInfoPage.do">
					<li class="selectTag"><a href="javascript:void(0)">基本信息</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelCustomerInfoPage.do">
					<li><a href="javascript:void(0)">对应客户</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelBrandInfoPage.do">
					<li><a href="javascript:void(0)">对应品牌</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelEdtInfoPage.do">
					<li><a href="javascript:void(0)">教育经历</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelWorkInfoPage.do">
					<li><a href="javascript:void(0)">工作经历</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelPostInfoPage.do">
					<li><a href="javascript:void(0)">岗位变动记录</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/showDepartMentdefinePage.do">
					<li><a href="javascript:void(0)">自定义信息</a></li>
				</security:authorize>
			</ul>
  		</div>
  		<div id="personnel-layout-detail-center" data-options="region:'center',border:false">
  			<div class="tagsDiv" style="min-width: 1024px">
				<div class="tagsDiv_item" style="display:block;">
					<form action="" method="post" id="personnel-form-addPersonnelBaseInfo">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 80px">姓名拼音：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.namepinyin==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.namepinyin }"></c:out>" </c:otherwise>
									</c:choose>/>
									<input id="personnel-hidden-throughId" type="hidden" name="personnel.id" value="<c:out value="${personnel.id }"></c:out>" />
									<input id="basefiles-hdname-personnel" type="hidden" value="<c:out value="${personnel.name }"></c:out>"/>
									<input id="personnel-hidden-hdType" type="hidden" value="${type }"/>
									<input id="personnel-hidden-hdphotograph" type="hidden" name="personnel.photograph" value="${personnel.photograph }"/>
									<%--<input id="personnel-hidden-hdadjunctpath" type="hidden" name="personnel.adjunctid" value="${personnel.adjunctid }"/>--%>
									<input id="personnel-hidden-hdemployetype" type="hidden" name="personnel.employetype" value="${personnel.employetype }"/>
									<input type="hidden" id="personnel-type-upload" value="view"/>
								</td>
								<td align="right" style="width: 80px">入职日期：</td>
								<td><input type="text" style="width: 120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"
									<c:choose>
										<c:when test="${showMap.datesemployed==null}"> value=""</c:when>
										<c:otherwise>disabled="disabled" value="${personnel.datesemployed }" </c:otherwise>
									</c:choose>/>
								</td>
								<td colspan="2" rowspan="7">
									<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
		   								<div class="easyui-panel" style="width:150px;height:180px;overflow:hidden;">
		   									<img id="personnel-img-preview" width="150px" height="180px" alt="人员照片"
												<c:choose>
													<c:when test="${showMap.photograph==null}">src="./image/photo_per_default.jpg"</c:when>
													<c:otherwise>src="${personnel.photograph }"</c:otherwise>
												</c:choose>/>
		   								</div>
		   								<div class="easyui-panel" data-options="border:false" style="width:200px;height:30px;margin-top:5px;">
		   									照片上传：<input type="button" value="浏览 " id="personnel-button-uploadImg" style="border-radius:5px;" disabled="disabled"/>
                                            <a class="easyui-linkbutton" id="personnel-button-showOldImg" >查看原图</a>
		   								</div>
		   							</div>
								</td>
							</tr>
							<tr>
								<td align="right">员工类型：</td>
								<td><input id="personnel-widget-personnelstyle" disabled="disabled" name="personnel.personnelstyle"
									<c:choose>
										<c:when test="${showMap.personnelstyle==null}">value=""</c:when>
										<c:otherwise>value="${personnel.personnelstyle }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">最高学历：</td>
								<td><input id="personnel-widget-highestdegree" disabled="disabled" name="personnel.highestdegree"
									<c:choose>
										<c:when test="${showMap.highestdegree==null}">value=""</c:when>
										<c:otherwise>value="${personnel.highestdegree }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">所属部门：</td>
								<td>
					    			<input id="personnel-widget-personnelDept" disabled="disabled" required="true" name="personnel.belongdeptid"
					    			<c:choose>
										<c:when test="${showMap.belongdeptid==null}">value=""</c:when>
										<c:otherwise>value="<c:out value="${personnel.belongdeptid }"></c:out>" </c:otherwise>
									</c:choose>/>
					    		</td>
								<td align="right">所属岗位：</td>
								<td><input type="text" id="personnel-widget-personnelPost" disabled="disabled" name="personnel.belongpost"
									<c:choose>
										<c:when test="${showMap.belongpost==null}">value=""</c:when>
										<c:otherwise>value="<c:out value="${personnel.belongpost }"></c:out>" </c:otherwise>
									</c:choose>/></td>
							</tr>
							<tr>
								<td align="right">上级领导：</td>
								<td><input type="text" id="personnel-widget-leadid" name="personnel.leadid" value="${personnel.leadid }" disabled="disabled"/></td>
								<td align="right">性别：</td>
								<td><input id="personnel-widget-sex" name="personnel.sex" disabled="disabled"
									<c:choose>
										<c:when test="${showMap.sex==null}">value=""</c:when>
										<c:otherwise>value="${personnel.sex }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">婚姻状态：</td>
								<td><input id="personnel-widget-maritalstatus" disabled="disabled" name="personnel.maritalstatus"
									<c:choose>
										<c:when test="${showMap.maritalstatus==null}">value=""</c:when>
										<c:otherwise>value="${personnel.maritalstatus }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">身份证号码：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.idcard==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.idcard }" </c:otherwise>
									</c:choose>/></td>
							</tr>
							<tr>
								<td align="right">出生日期：</td>
								<td><input id="personnel-input-birthday" type="text" style="width: 120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld',onpicking:getAgeFunc})"
									<c:choose>
										<c:when test="${showMap.birthday==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.birthday }" </c:otherwise>
									</c:choose>/></td>
								<td align="right">年龄：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.age==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.age }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">民族：</td>
								<td><input id="personnel-widget-nation" name="personnel.nation" disabled="disabled"
									<c:choose>
										<c:when test="${showMap.nation==null}">value=""</c:when>
										<c:otherwise>value="${personnel.nation }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">籍贯：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.nativeplace==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.nativeplace }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">政治面貌：</td>
								<td><input id="personnel-widget-polstatus" disabled="disabled" name="personnel.polstatus"
									<c:choose>
										<c:when test="${showMap.polstatus==null}">value=""</c:when>
										<c:otherwise>value="${personnel.polstatus }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">电话：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.tel==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.tel }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">传真：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.fax==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.fax }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">手机号码：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.telphone==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.telphone }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">公司短号：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.compcornet==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.compcornet }" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">邮箱：</td>
								<td><input type="text" readonly="readonly" value="${personnel.email }" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.email==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.email }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">业务属性:</td>
								<td colspan="3">
									<div>
										<c:forEach var="list" items="${codeList}" varStatus="status">
		    							<input id="personnel-employetype_${list.code}" type="checkbox" value="${list.code }" <c:if test="${fn:contains(personnel.employetype,list.code)}">checked="true"</c:if> disabled="disabled"/>${list.codename }<c:if test="${(status.index+1)%5==0}"><br/></c:if>
		    							</c:forEach>
			    						<input type="hidden" id="personnel-employetype" name="personnel.employetype" value="${personnel.employetype }"/>
										<input type="hidden" id="personnel-norepeatemploye" value="${norepeatemploye }"/>
									</div>
								</td>
								<td align="right">薪资方案：</td>
								<td><input type="text" id="personnel-widget-salaryscheme" disabled="disabled" name="personnel.salaryscheme" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.salaryscheme==null}">value=""</c:when>
										<c:otherwise>value="<c:out value="${personnel.salaryscheme }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">居住地址：</td>
								<td colspan="3"><input type="text" style="width: 400px"
									<c:choose>
										<c:when test="${showMap.address==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.address }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">居住地邮编：</td>
								<td><input type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.addrpostcode==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.addrpostcode }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">户籍地址：</td>
								<td colspan="3"><input type="text" style="width: 400px"
									<c:choose>
										<c:when test="${showMap.householdaddr==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.householdaddr }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">户籍地邮编：</td>
								<td><input type="text" readonly="readonly" value="${personnel.householdcode }" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.householdcode==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="${personnel.householdcode }" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">备注：</td>
								<td colspan="5">
									<textarea rows="" cols="" style="width: 640px;height:30px;" readonly="readonly" style="overflow-y:scroll"><c:if test="${showMap.remark != null}"><c:out value="${personnel.remark }"></c:out></c:if></textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelCustomerList"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelBrandList"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelEduList"></table>
				</div>
				<div class="tagsDiv_item" style="">
					<table id="personnel-table-personnelWorksList"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelPostList"></table>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="personnel-form-addPersonnelDefineInfo">
						<table border="0" cellpadding="2px" cellspacing="2px">
							<tr>
								<td align="right">
									<label>
										<c:choose>
											<c:when test="${fieldmap.field01 != \"\"}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
											<c:otherwise>自定义信息01</c:otherwise>
										</c:choose>									
									</label>：
								</td>
								<td>
									<input name="personnel.field01" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field01==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field01 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">
									<label>
										<c:choose>
											<c:when test="${fieldmap.field02 != \"\"}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
											<c:otherwise>自定义信息02</c:otherwise>
										</c:choose>									
									</label>：
								</td>
								<td><input name="personnel.field02" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field02==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field02 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right">
									<label>
									<c:choose>
										<c:when test="${fieldmap.field03 != \"\"}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
										<c:otherwise>自定义信息03</c:otherwise>
									</c:choose>									
									</label>：
								</td>
								<td><input name="personnel.field03" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field03==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field03 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field04 != \"\"}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field04" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field04==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field04 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field05 != \"\"}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field05" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field05==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field05 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field06 != \"\"}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field06" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field06==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field06 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field07 != \"\"}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field07" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field07==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field07 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field08 != \"\"}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field08" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field08==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field08 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field09 != \"\"}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field09" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field09==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field09 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field10 != \"\"}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
									<c:otherwise>自定义信息10</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td colspan="5"><input type="text" name="personnel.field10" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field10==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field10 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field11 != \"\"}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
									<c:otherwise>自定义信息11</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td colspan="5"><input type="text" name="personnel.field11" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field11==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field11 }"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label>
										<c:choose>
											<c:when test="${fieldmap.field12 != \"\"}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
											<c:otherwise>自定义信息12</c:otherwise>
										</c:choose>									
									</label>：
								</td>
								<td colspan="5">
									<input type="text" name="personnel.field12" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field12==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${personnel.field12 }"></c:out>" </c:otherwise>
									</c:choose>/>
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
		var id = $("#basefiles-id-personnel").val();
		var eduUrl = "basefiles/showPersEducationList.do?personid="+id;
		var postUrl = "basefiles/showPersPostsList.do?personid="+id;
		var workUrl = "basefiles/showPersWorksList.do?personid="+id;
		var customerUrl = "",brandUrl = "";
		var employetype = $("#personnel-employetype").val();
		if(employetype.indexOf("1") != -1){
        	customerUrl = 'basefiles/getCustomerListForPsnCstm.do?salesuserid='+id;
        }else if(employetype.indexOf("3") != -1){
        	customerUrl = 'basefiles/getPersonCustomerListPageData.do?personid='+id;
        	brandUrl = "basefiles/showBrandList.do?personid="+id;
        }else if(employetype.indexOf("7") != -1){
        	customerUrl = 'basefiles/getPersonSupplierCustomerListPageData.do?personid='+id;
        	brandUrl = 'basefiles/showSupplierBrandList.do?personid='+id;
        }
	
		//上传图片按钮，浏览 
		$("#personnel-button-uploadImg").webuploader({
            title: '图片上传',
            filetype:'Images',
            allowType:"gif,jpg,jpeg,bmp,png",
            mimeTypes:'txt/*',
            attaInput:"#personnel-hidden-hdadjunctpath",
            close:true,//完成上传后是否自动关闭上传窗口
            fileNumLimit:1,//验证文件总数量, 超出则不允许加入队列，默认300
            disableGlobalDnd:false,//禁用拖动文件功能
            onComplete: function(data){
                $("#personnel-hidden-hdphotograph").val(data[0].fullPath);
                $("#personnel-img-preview").attr("src",data[0].fullPath);
            }
		});
		
		//查看原图
		$("#personnel-button-showOldImg").click(function(){
			var photograph = document.getElementById("personnel-img-preview").getAttribute("src");
			$('#personnel-window-showOldImg').window({  
				    title: '查看原图',  
				    width: $(window).width(),  
				    height: $(window).height(),  
				    closed: true,  
				    cache: false,  
				    modal: true 
				});
			$("#personnel-window-showOldImg").window("open");
			$("#personnel-window-showOldImg").window("refresh","basefiles/showPersonnelOldImgPage.do?photograph="+photograph);
		});
		
		$(function(){
			if("${showMap}" == ""){
				$.messager.alert('提醒','请联系管理员分配用户权限!');
				return false;
			}
			
			$("#basefiles-button-personnel").buttonWidget("setDataID",{id:$("#personnel-hidden-hdoldIdEdit").val(),state:"${personnel.state}",type:"view"});
			if(!"1" == "${personnel.state}"){
				$("#basefiles-button-personnel").buttonWidget("disableButton","allotSysUserBtn");
			}
			else{
				$("#basefiles-button-personnel").buttonWidget("enableButton","allotSysUserBtn");
			}
			$("#basefiles-button-personnel").buttonWidget("enableButton","personnel-upload-file");
			loadDropdown();
			//默认人员照片 
			var photographSrc = document.getElementById("personnel-img-preview").getAttribute("src");
			if(photographSrc == ""){
				$("#personnel-img-preview").attr("src","image/photo_per_default.jpg");
			}
			
			$(".tags").find("li").click(function(){
				var height = $("#personnel-layout-detail-center").height()-5;
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 1){
					//对应客户列表
					$dgPersonnelCustomer = $("#personnel-table-personnelCustomerList");
					if(!$dgPersonnelCustomer.hasClass("create-datagrid")){
						if(customerUrl != ""){
							$dgPersonnelCustomer.datagrid({
								method:'post',
								title:'',
								rownumbers:true,
								singleSelect:true,
								border:false,
								pagination:true,
								pageSize:500,
								url:customerUrl,
								height:height,
								columns:[[
									{field:'id',title:'编号',width:150,hidden:true},
									{field:'personid',title:'人员编号',width:150,hidden:true},
									{field:'customerid',title:'客户编码',width:60},
									{field:'customername',title:'客户名称',width:210,isShow:true},
									{field:'salesareaname',title:'所属区域',width:80,isShow:true},
									{field:'customersortname',title:'所属分类',width:80,isShow:true}
								]]
							});
						}else{
							$dgPersonnelCustomer.datagrid({
								method:'post',
								title:'',
								rownumbers:true,
								singleSelect:true,
								border:false,
								height:height,
								columns:[[
									{field:'id',title:'编号',width:150,hidden:true},
									{field:'personid',title:'人员编号',width:150,hidden:true},
									{field:'customerid',title:'客户编码',width:60},
									{field:'customername',title:'客户名称',width:210,isShow:true},
									{field:'salesareaname',title:'所属区域',width:80,isShow:true},
									{field:'customersortname',title:'所属分类',width:80,isShow:true}
								]]
							});
						}
						$dgPersonnelCustomer.addClass("create-datagrid");
					}
	             }
	             else if(index == 2){
	                //对应品牌
	                $dgPersonnelBrand = $("#personnel-table-personnelBrandList");
	                if(!$dgPersonnelBrand.hasClass("create-datagrid")){
						if(brandUrl != ""){
							$dgPersonnelBrand.datagrid({
								method:'post',
								title:'',
								rownumbers:true,
								singleSelect:true,
								border:false,
								pageSize:500,
								pagination:true,
								url:brandUrl,
								height:height,
								columns:[[
									{field:'id',title:'编号',width:150,hidden:true},
									{field:'personid',title:'人员编号',width:150,hidden:true},
									{field:'brandid',title:'品牌编码',width:60},
									{field:'brandname',title:'品牌名称',width:320,isShow:true}
								]]
							});
						}else{
							$dgPersonnelBrand.datagrid({
								method:'post',
								title:'',
								rownumbers:true,
								singleSelect:true,
								border:false,
								height:height,
								columns:[[
									{field:'id',title:'编号',width:150,hidden:true},
									{field:'personid',title:'人员编号',width:150,hidden:true},
									{field:'brandid',title:'品牌编码',width:60},
									{field:'brandname',title:'品牌名称',width:320,isShow:true}
								]]
							});
						}
				   		$dgPersonnelBrand.addClass("create-datagrid");
	                }
	             }
				else if(index == 3){
					var $dgPersonnel = $("#personnel-table-personnelEduList");
					if(!$dgPersonnel.hasClass("create-datagrid")){
						$dgPersonnel.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:eduUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				  				{field:'personid',title:'人员编号',width:150,hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center'},
				  				{field:'enddate',title:'结束日期',width:80,align:'center'},
				  				{field:'educname',title:'教育机构名称',width:130,align:'center'},
				  				{field:'type',title:'教育方式',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return getSysCodeName("eduType",val);
				  					}
				  				},
				  				{field:'certificate',title:'获得证书',width:300,align:'center'},
				  				{field:'remark',title:'备注',width:300,align:'center'}
				  			]]
				   		});
						$dgPersonnel.addClass("create-datagrid");
					}
				}
				else if(index == 4){
					var $dgPersonnelWorks = $("#personnel-table-personnelWorksList");
					if(!$dgPersonnelWorks.hasClass("create-datagrid")){
						$dgPersonnelWorks.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:workUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				  				{field:'personid',title:'人员编号',width:150,hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center'},
				  				{field:'enddate',title:'结束日期',width:80,align:'center'},
				  				{field:'workname',title:'工作单位名称',width:100,align:'center'},
				  				{field:'post',title:'担任职务',width:70,align:'center'},
				  				{field:'mainachievement',title:'主要业绩',width:150,align:'center'},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				  			]]
				   		});
						$dgPersonnelWorks.addClass("create-datagrid");
					}
				}
				else if(index == 5){
					var $dgPersonnelPost = $("#personnel-table-personnelPostList");
					if(!$dgPersonnelPost.hasClass("create-datagrid")){
						$dgPersonnelPost.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:postUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',hidden:true},
				  				{field:'personid',title:'人员编号',hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center'},
				  				{field:'enddate',title:'结束日期',width:80,align:'center'},
				  				{field:'belongdeptid',title:'所属部门',width:125,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.belongdeptName;
				  					}
				  				},
				  				{field:'belongpostid',title:'所属岗位',width:160,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.belongpostName;
				  					}
				  				},
				  				{field:'salaryscheme',title:'薪资方案',width:150,align:'center'},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				  			]]
				   		});
						$dgPersonnelPost.addClass("create-datagrid");
					}
				}
			});
		});
		//附件按钮
// 		$("#button-file").upload({
// 			auto: false,
// 			del: false,
// 			virtualDel: false,
// 			ids: '${personnel.adjunctid}',
// 			type: 'list',
// 			attaInput: "#personnel-hidden-hdadjunctpath"
// 		});
		
	</script>
  </body>
</html>
