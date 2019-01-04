<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>修改人员</title>
  </head>
  
  <body>
  	<div id="personnel-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
  		<div id="personnel-layout-detail-north" data-options="region:'north',border:false" style="height:62px">
  			<form id="personnel-form-head" action="" method="post" style="padding: 5px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 15px"></td>
						<td style="width: 40px">编码:</td>
						<td style="width: 15px"></td>
						<td><input type="text" id="basefiles-id-personnel"  class="easyui-validatebox" name="personnel.id" value="<c:out value="${personnel.id }"></c:out>" <c:if test="${editMap.id==null}">readonly="readonly"</c:if> required="true" validType="isExistPerId[20]"  style="width:100px;" /></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">姓名:</td>
						<td style="width: 15px"></td>
						<td><input type="text" id="personnel-input-personnel_name" class="easyui-validatebox" name="personnel.name" value="<c:out value="${personnel.name }"></c:out>" <c:if test="${editMap.name==null}">readonly="readonly"</c:if> required="true" maxlength="20" style="width:105px;"/></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">状态:</td>
						<td style="width: 15px"></td>
						<td><input id="personnel-widget-state" name="personnel.state" value="${personnel.state }"  disabled="disabled"/>
						</td>
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
  			<div class="tagsDiv">
				<div class="tagsDiv_item" style="display:block;">
					<form action="" method="post" id="personnel-form-addPersonnelBaseInfo">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 80px">姓名拼音：</td>
								<td><input type="text" name="personnel.namepinyin" value="<c:out value="${personnel.namepinyin }"></c:out>" <c:if test="${editMap.namepinyin==null}">readonly="readonly"</c:if> maxlength="20" style="width: 120px"/>
									<input id="personnel-hidden-throughState" type="hidden" name="personnel.state" value="${personnel.state }"/>
									<input id="personnel-hidden-hdoldIdEdit" name="personnel.oldId" type="hidden" value="<c:out value="${oldId }"></c:out>"/>
									<input id="basefiles-hdid-personnel" type="hidden" value="<c:out value="${personnel.id }"></c:out>"/>
									<input id="basefiles-hdname-personnel" type="hidden" value="<c:out value="${personnel.name }"></c:out>"/>
									<input id="personnel-hidden-hdBirthday" type="hidden" value="${birthday }"/>
									<input id="personnel-hidden-hdType" type="hidden" value="${type }"/>
									<input id="personnel-hidden-hdoldBirth" type="hidden"/>
									<input id="personnel-hidden-hdEduListDelId" type="hidden" name ="personnel.eduListDelId"/>
									<input id="personnel-hidden-hdPostListDelId" type="hidden" name ="personnel.postListDelId"/>
									<input id="personnel-hidden-hdWorkListDelId" type="hidden" name ="personnel.workListDelId"/>
									<input id="personnel-hidden-hdphotograph" type="hidden" name="personnel.photograph" value="${personnel.photograph }"/>
<%-- 									<input id="personnel-hidden-hdadjunctpath" type="hidden" name="personnel.adjunctid" value="${personnel.adjunctid }"/> --%>
									<input type="hidden" id="personnel-hidden-rowsNum" value="0"/>
									<input id="personnel-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
									<input type="hidden" id="personnel-index-eduList"/>
									<input type="hidden" id="personnel-index-workList"/>
									<input type="hidden" id="personnel-index-postList"/>
									<input type="hidden" id="personnel-index-customerList"/>
									<input type="hidden" id="personnel-index-brandList"/>
									<input type="hidden" id="personnel-copy-belongdeptid" value="<c:out value="${personnel.belongdeptid}"></c:out>"/>
									<input type="hidden" id="personnel-addBrandid" value="${personnel.brandids }"/>
									<input type="hidden" id="personnel-personcustomer" value="${personnel.customerids }"/>
									<input type="hidden" id="personnel-iseditlist" name="personnel.iseditlist"/>
									<input type="hidden" id="personnel-type-upload" value="edit"/>
								</td>
								<td align="right" style="width: 80px">入职日期：</td>
								<td><input type="text" name="personnel.datesemployed" value="${dateSemployed }" <c:if test="${editMap.datesemployed==null}">disabled="disabled"</c:if> class="easyui-validatebox"  style="width: 120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
								<td colspan="2" rowspan="7">
									<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
		   								<div class="easyui-panel" style="width:150px;height:180px;overflow:hidden;">
		   									<img id="personnel-img-preview" width="150px" height="180px" alt="人员照片" 
		   										<c:choose>
		   											<c:when test="${personnel.photograph == '' || personnel.photograph == null}">src="./image/photo_per_default.jpg"</c:when>
		   											<c:otherwise>src="${personnel.photograph }"</c:otherwise>
		   									</c:choose>/>
		   								</div>
		   								<div class="easyui-panel" data-options="border:false" style="width:200px;height:30px;margin-top:5px;">
		   									照片上传：<a class="easyui-linkbutton" id="personnel-button-uploadImg" <c:if test="${lockFlag==0}">disabled="disabled"</c:if>>浏览</a>
		   											<a class="easyui-linkbutton" id="personnel-button-showOldImg" <c:if test="${lockFlag==0}">disabled="disabled"</c:if>>查看原图</a>
		   								</div>
		   							</div>
								</td>
							</tr>
							<tr>
								<td align="right">员工类型：</td>
								<td><input id="personnel-widget-personnelstyle" name="personnel.personnelstyle" value="${personnel.personnelstyle }" <c:if test="${editMap.personnelstyle==null}">disabled="disabled"</c:if> />
								</td>
								<td align="right">最高学历：</td>
								<td><input id="personnel-widget-highestdegree" name="personnel.highestdegree" value="${personnel.highestdegree }" <c:if test="${editMap.highestdegree==null}">disabled="disabled"</c:if> />
								</td>
							</tr>
							<tr>
								<td align="right">所属部门：</td>
								<td>
					    			<input id="personnel-widget-personnelDept" required="true" name="personnel.belongdeptid" value="<c:out value="${personnel.belongdeptid }"></c:out>" <c:if test="${editMap.belongdeptid==null}">disabled="disabled"</c:if> />	
					    		</td>
								<td align="right">所属岗位：</td>
								<td><input type="text" id="personnel-widget-personnelPost" style="width: 120px" value="<c:out value="${personnel.belongpost }"></c:out>" name="personnel.belongpost" <c:if test="${editMap.belongpost==null}">disabled="disabled"</c:if> /></td>
							</tr>
							<tr>
								<td align="right">上级领导：</td>
								<td><input type="text" id="personnel-widget-leadid" name="personnel.leadid" value="${personnel.leadid }"/></td>
								<td align="right">性别：</td>
								<td><input id="personnel-widget-sex" name="personnel.sex" value="${personnel.sex }" <c:if test="${editMap.sex==null}">disabled="disabled"</c:if> /></td>
							</tr>
							<tr>
								<td align="right">婚姻状态：</td>
								<td><input id="personnel-widget-maritalstatus" name="personnel.maritalstatus" value="${personnel.maritalstatus }" <c:if test="${editMap.maritalstatus==null}">disabled="disabled"</c:if> /></td>
								<td align="right">身份证号码：</td>
								<td><input id="personnel-input-idCard" type="text" name="personnel.idcard" value="${personnel.idcard }" oninput="getAgeForeign(this.value)" <c:if test="${editMap.idcard==null}">readonly="readonly"</c:if> class="easyui-validatebox" validType="valididcard"  style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">出生日期：</td>
								<td><input id="personnel-input-birthday" name="personnel.birthday" value="${birthday }" <c:if test="${editMap.birthday==null}">readonly="readonly"</c:if> class="easyui-validatebox" validType="validBirthday"  style="width: 120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%d',onpicked:getAgeFunc})"/></td>
								<td align="right">年龄：</td>
								<td><input id="personnel-input-age" value="${personnel.age }" <c:if test="${editMap.age==null}">readonly="readonly"</c:if> type="text" name="personnel.age" style="width: 120px" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right">民族：</td>
								<td><input id="personnel-widget-nation" name="personnel.nation" value="${personnel.nation }" <c:if test="${editMap.nation==null}">readonly="readonly"</c:if> /></td>
								<td align="right">籍贯：</td>
								<td><input id="personnel-input-NPlace" type="text" name="personnel.nativeplace" value="${personnel.nativeplace }" <c:if test="${editMap.nativeplace==null}">readonly="readonly"</c:if> style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">政治面貌：</td>
								<td><input id="personnel-widget-polstatus" name="personnel.polstatus" value="${personnel.polstatus }" <c:if test="${editMap.polstatus==null}">disabled="disabled"</c:if> /></td>
								<td align="right">电话：</td>
								<td><input type="text" class="easyui-validatebox" value="${personnel.tel }" <c:if test="${editMap.tel==null}">readonly="readonly"</c:if> validType="phone" name="personnel.tel" style="width: 120px"/></td>
								<td align="right">传真：</td>
								<td><input type="text" class="easyui-validatebox" value="${personnel.fax }" <c:if test="${editMap.fax==null}">readonly="readonly"</c:if>  validType="phone" name="personnel.fax" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">手机号码：</td>
								<td><input type="text" value="<c:out value="${personnel.telphone }"></c:out>" <c:if test="${editMap.telphone==null}">readonly="readonly"</c:if> name="personnel.telphone" style="width: 120px"/></td>
								<td align="right">公司短号：</td>
								<td><input type="text" name="personnel.compcornet" value="${personnel.compcornet }" <c:if test="${editMap.compcornet==null}">readonly="readonly"</c:if> class="easyui-validatebox" validType="compcornet[6]" style="width: 120px"/></td>
								<td align="right">邮箱：</td>
								<td><input type="text" class="easyui-validatebox" value="${personnel.email }" <c:if test="${editMap.email==null}">readonly="readonly"</c:if> validType="email"  name="personnel.email" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">业务属性:</td>
								<td colspan="3">
									<div>
										<c:forEach var="list" items="${codeList}" varStatus="status">
		    							<input id="personnel-employetype_${list.code}" type="checkbox" class="personnel-employetype <c:if test="${fn:contains(norepeatemploye, list.code)}"><c:choose><c:when test="${fn:contains(personnel.employetype, list.code)}">emptchecked</c:when><c:otherwise>norepeatemploye</c:otherwise></c:choose></c:if>" value="${list.code }" 
		    							<c:if test="${fn:contains(personnel.employetype,list.code)}">checked="true"</c:if>/>${list.codename }<c:if test="${(status.index+1)%5==0}"><br/></c:if>
		    							</c:forEach>
			    						<input type="hidden" id="personnel-employetype" name="personnel.employetype" value="${personnel.employetype }"/>
										<input type="hidden" id="personnel-employetype_old" value="${personnel.employetype }"/>
										<input type="hidden" id="personnel-norepeatemploye" value="${norepeatemploye }"/>
									</div>
								</td>
								<td align="right">薪资方案：</td>
								<td><input type="text" id="personnel-widget-salaryscheme" name="personnel.salaryscheme" value="<c:out value="${personnel.salaryscheme }"></c:out>" <c:if test="${editMap.salaryscheme==null}">disabled="disabled"</c:if> style="width: 120px"/>
								</td>
							</tr>
							<tr>
								<td align="right">居住地址：</td>
								<td colspan="3"><input type="text" value="<c:out value="${personnel.address }"></c:out>" <c:if test="${editMap.address==null}">readonly="readonly"</c:if> name="personnel.address" maxlength="100" style="width: 400px"/></td>
								<td align="right">居住地邮编：</td>
								<td><input name="personnel.addrpostcode" value="${personnel.addrpostcode }" <c:if test="${editMap.addrpostcode==null}">readonly="readonly"</c:if> class="easyui-validatebox" validType="zip" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">户籍地址：</td>
								<td colspan="3"><input type="text" value="<c:out value="${personnel.householdaddr }"></c:out>" <c:if test="${editMap.householdaddr==null}">readonly="readonly"</c:if> name="personnel.householdaddr" maxlength="100" style="width: 400px"/></td>
								<td align="right">户籍地邮编：</td>
								<td><input name="personnel.householdcode" value="${personnel.householdcode }" <c:if test="${editMap.householdcode==null}">readonly="readonly"</c:if> class="easyui-validatebox" validType="zip" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">备注：</td>
								<td colspan="5">
									<textarea rows="" cols="" class="easyui-validatebox" validType="remark[200]" style="width: 635px;height:30px;" name="personnel.remark" <c:if test="${editMap.remark==null}">readonly="readonly"</c:if> style="overflow-y:scroll"><c:out value="${personnel.remark }"></c:out></textarea>
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
					<form action="" method="post" id="personnel-form-personnelEdu">
						<table id="personnel-table-personnelEduList"></table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="personnel-form-personnelWorks">
						<table id="personnel-table-personnelWorksList"></table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="personnel-form-personnelPost">
						<table id="personnel-table-personnelPostList"></table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="personnel-form-addPersonnelDefineInfo">
						<table border="0" cellpadding="2px" cellspacing="2px">
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field01 != null}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
									<c:otherwise>自定义信息01</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field01" maxlength="100" value="<c:out value="${personnel.field01 }"></c:out>" <c:if test="${editMap.field01==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field02 != null}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
									<c:otherwise>自定义信息02</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field02" maxlength="100" value="<c:out value="${personnel.field02 }"></c:out>" <c:if test="${editMap.field02==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field03 != null}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
									<c:otherwise>自定义信息03</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field03" maxlength="100" value="<c:out value="${personnel.field03 }"></c:out>" <c:if test="${editMap.field03==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field04 != null}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field04" maxlength="100" value="<c:out value="${personnel.field04 }"></c:out>" <c:if test="${editMap.field04==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field05 != null}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field05" maxlength="100" value="<c:out value="${personnel.field05 }"></c:out>" <c:if test="${editMap.field05==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field06 != null}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field06" maxlength="100" value="<c:out value="${personnel.field06 }"></c:out>" <c:if test="${editMap.field06==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field07 != null}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field07" maxlength="100" value="<c:out value="${personnel.field07 }"></c:out>" <c:if test="${editMap.field07==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field08 != null}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field08" maxlength="100" value="<c:out value="${personnel.field08 }"></c:out>" <c:if test="${editMap.field08==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field09 != null}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="personnel.field09" maxlength="100" value="<c:out value="${personnel.field09 }"></c:out>" <c:if test="${editMap.field09==null}">readonly="readonly"</c:if> type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field10 != null}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
									<c:otherwise>自定义信息10</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td colspan="5"><input type="text" name="personnel.field10" value="<c:out value="${personnel.field10 }"></c:out>" <c:if test="${editMap.field10==null}">readonly="readonly"</c:if> style="width: 625px"/></td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field11 != null}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
									<c:otherwise>自定义信息11</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td colspan="5"><input type="text" name="personnel.field11" value="<c:out value="${personnel.field11 }"></c:out>" <c:if test="${editMap.field11==null}">readonly="readonly"</c:if> style="width: 625px"/></td>
							</tr>
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field12 != null}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
									<c:otherwise>自定义信息12</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td colspan="5"><input type="text" name="personnel.field12" value="<c:out value="${personnel.field12 }"></c:out>" <c:if test="${editMap.field12==null}">readonly="readonly"</c:if> style="width: 625px"/></td>
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
	var person_title = tabsWindowTitle('/basefiles/showPersonnelListPage.do');
	
	//身份证验证 
	$.extend($.fn.validatebox.defaults.rules, {
   			valididcard:{
   				validator:function(value){
   					if((/^\d{17}(\d|X|x)$/.test(value))){
						var ret = personnel_AjaxConn({id:value.substr(0,6)},'basefiles/getNPname.do');//获取籍贯
						var retJson=JSON.parse(ret);
						var sysDateNow= new Date(getSystemDateNow());
						if(value.substring(6,14).substr(0,4) < sysDateNow.getFullYear()){
							var idCardBirthday=value.substring(6,14);
							var birthday = idCardBirthday.substr(0,4)+"-"+idCardBirthday.substr(4,2)+"-"+idCardBirthday.substr(6,2);
							$("#personnel-input-birthday").val(birthday);
							getAgeForeign(value);
						}
						$("#personnel-input-NPlace").val(retJson.name);
						return true;
					}
					else{
						$.fn.validatebox.defaults.rules.valididcard.message = '身份证号码格式不正确!';
					}
   				},
   				message:''
   			},
   			isExistPerId:{
   				validator:function(value,param){
   					if($("#personnel-hidden-hdoldIdEdit").val() == value){
   						return true;
   					}
   					var reg = eval("/^[A-Za-z0-9]{1,20}$/i");
   					if(reg.test(value)){
   						var ret=personnel_AjaxConn({id:value},'basefiles/isExistPersonnelId.do');
	   					var json = $.parseJSON(ret);
	   					if(json.flag){
	   						$.fn.validatebox.defaults.rules.isExistPerId.message = '编号已使用,请重新输入!';
	   						return false;
	   					}
	   					else{
	   						return true;
	   					}
   					}
   				},
   				message:'该人员编号已使用!'
   			},
   			validBirthday:{
   				validator:function(value){
   					if($("#personnel-input-idCard").val() != ""){
   						var idCardBirthday=$("#personnel-input-idCard").val().substring(6,14);
   						var birthday = idCardBirthday.substr(0,4)+"-"+idCardBirthday.substr(4,2)+"-"+idCardBirthday.substr(6,2);
   						if(value != birthday){
   							$.fn.validatebox.defaults.rules.validBirthday.message = '与身份证出生日期不对应!';
   							return false;
   						}
   						return true;
   					}
   					else{
   						return true;
   					}
   				},
   				message:''
   			},
   			remark:{
   				validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入200个字符!' 
   			},
   			compcornet:{
   				validator : function(value,param) {
   					var mes ="";
   					if(value.length <= param[0]){
   						if(/^\d+$/.test(value)){
   							return true;
   						}
   						else{
   							$.fn.validatebox.defaults.rules.compcornet.message = '只允许输入数字!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.compcornet.message = '请输入6个数字!';
   						return false;
   					}
		        }, 
		        message : ''
   			}
   		});
   		
   	//基本档案数据表单集合
	var $headInfo = $("#personnel-form-head");
	var $baseInfo = $("#personnel-form-addPersonnelBaseInfo");
	var $defineInfo = $("#personnel-form-addPersonnelDefineInfo");
	
	function personnelFormValidate(){
		return $headInfo.form('validate') && $baseInfo.form('validate') && $defineInfo.form('validate');
	}
	//人员基本档案数据 
	function personnel_BaseInfo(){
	 	var baseInfo = $baseInfo.serializeJSON();//基本信息
	 	var edulistInfo = eduJson();//教育经历
	 	var worksInfo = workJson();//工作经历
	 	var postInfo = postJson();//岗位变动记录
	 	var customerInfo = customerJson();//对应客户
		var brandInfo = brandJson();//对应品牌
	 	var defineInfo = $defineInfo.serializeJSON();//自定义信息 
	 	var headInfo = $headInfo.serializeJSON();
	 	for(key in headInfo){
	 		baseInfo[key] = headInfo[key];
	 	}
	 	for (key in edulistInfo) {//遍历第二个json对象添加到第一个json中
	 		baseInfo[key] = edulistInfo[key];
	 	};
	 	for(key in worksInfo){
	 		baseInfo[key] = worksInfo[key];
	 	}
	 	for(key in postInfo){
	 		baseInfo[key] = postInfo[key];
	 	}
	 	for(key in customerInfo){
	 		baseInfo[key] = customerInfo[key];
	 	}
	 	for(key in brandInfo){
	 		baseInfo[key] = brandInfo[key];
	 	}
	 	for(key in defineInfo){
	 		baseInfo[key] = defineInfo[key];
	 	}
	 	return baseInfo;
	}
	
	function editPersonnel(type){
		loading("提交中..");
   		if("1" == $("#personnel-beginCustomeredit").val() || "1" == $("#personnel-beginBrandedit").val()){
   			$("#personnel-iseditlist").val("1");
   		}else{
   			$("#personnel-iseditlist").val("0");
   		}
		$.ajax({
 			url:'basefiles/editPersonnel.do?type='+type+'&employetype='+$("#personnel-employetype_1").val(),
 			data:personnel_BaseInfo(),
 			dataType:'json',
 			type:'post',
 			success:function(retJson){
 				loaded();
 				if(retJson.isLock == "0"){
		   			$.messager.alert("提醒","该人员被加锁,暂不能修改!");
		   			return false;
		   		}
		   		else if(!retJson.editFlag){
		   			$.messager.alert("提醒","该人员不允许进行修改操作!");
		   			return false;
		   		}
		   		else if(retJson.flag){
		   			if (top.$('#tt').tabs('exists',person_title)){
		   				tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid('reload');
		   			}
		   			panelRefresh('basefiles/showPersonnelInfoPage.do?id='+$("#basefiles-id-personnel").val(),' 人员档案【查看】','view');
		   			$dgPersonnel = null,$dgPersonnelWorks = null,$dgPersonnelPost = null,$dgPersonnelCustomer = null,$dgPersonnelBrand = null;
		   			retBackIniv();
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
		});
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
		$("#personnel-init-deptid").val($("#personnel-copy-belongdeptid").val());
		
		$("#basefiles-button-personnel").buttonWidget("setDataID",{id:$("#personnel-hidden-hdoldIdEdit").val(),state:"${personnel.state}",type:"edit"});
		if(!"1" == "${personnel.state}"){
			$("#basefiles-button-personnel").buttonWidget("disableButton","allotSysUserBtn");
		}
		else{
			$("#basefiles-button-personnel").buttonWidget("enableButton","allotSysUserBtn");
		}
		$("#basefiles-button-personnel").buttonWidget("enableButton","personnel-upload-file");
		
		loadDropdown();
		
		clickEmployeType("edit","${personnel.employetype}");
		
		getTabs("edit");
		//默认人员照片 
		var photographSrc = document.getElementById("personnel-img-preview").getAttribute("src");
		if(photographSrc == ""){
			$("#personnel-img-preview").attr("src","image/photo_per_default.jpg");
		}
		
		//附件按钮
// 		$("#button-file").upload({
// 			auto: false,
// 			del: true,
// 			ids: '${personnel.adjunctid}',
// 			type: '',
// 			virtualDel:true,
// 			attaInput: "#personnel-hidden-hdadjunctpath"
// 		});
		
	});
	</script>
  </body>
</html>
