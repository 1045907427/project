<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>新增人员</title>
  </head>
  
  <body>
  	<div id="personnel-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
  		<div id="personnel-layout-detail-north" data-options="region:'north',border:false" style="height:62px">
  			<form id="personnel-form-head" action="" method="post" style="padding: 5px;">
		  		<table border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td style="width: 15px"></td>
						<td style="width: 40px">编码:</td>
						<td style="width: 15px"></td>
						<td><input type="text" id="basefiles-id-personnel" class="easyui-validatebox" required="true" name="personnel.id" validType="isExistPerId" style="width:100px;" maxlength="20"/></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">姓名:</td>
						<td style="width: 15px"></td>
						<td><input type="text" class="easyui-validatebox" name="personnel.name" required="true" style="width:105px;" maxlength="20"/></td>
						<td style="width: 15px"></td>
						<td style="width: 40px">状态:</td>
						<td style="width: 15px"></td>
						<td><input id="personnel-widget-state" value="4" disabled="disabled"  /></td>
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
			    	<form action="" method="post" id="personnel-form-addPersonnelBaseInfo" >
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 80px">姓名拼音：</td>
								<td><input type="text" name="personnel.namepinyin" maxlength="20" style="width: 120px"/>
									<input id="personnel-hidden-throughState" type="hidden" name="personnel.state" />
									<input id="personnel-hidden-hdBirthday" type="hidden" value="${birthday }"/>
									<input id="personnel-hidden-hdoldBirth" type="hidden"/>
									<input id="personnel-hidden-hdphotograph" type="hidden" name="personnel.photograph"/>
									<input id="personnel-hidden-hdadjunctpath" type="hidden" name="personnel.adjunctid"/>
									<input type="hidden" id="personnelAdd-hidden-state" value="${state }" />
									<input type="hidden" id="personnel-index-eduList"/>
									<input type="hidden" id="personnel-index-workList"/>
									<input type="hidden" id="personnel-index-postList"/>
									<input type="hidden" id="personnel-index-customerList"/>
									<input type="hidden" id="personnel-index-brandList"/>
									<input type="hidden" id="personnel-addBrandid" />
									<input type="hidden" id="personnel-personcustomer"/>
									<input type="hidden" id="personnel-type-upload" value="add"/>
								</td>
								<td align="right" style="width: 80px">入职日期：</td>
								<td><input type="text" name="personnel.datesemployed" class="easyui-validatebox" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
								<td colspan="2" rowspan="7">
									<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
		   								<div class="easyui-panel" style="width:150px;height:180px;overflow:hidden;">
		   									<img id="personnel-img-preview" width="150px" height="180px" src="./image/photo_per_default.jpg" alt="人员照片"/>
		   								</div>
		   								<div class="easyui-panel" data-options="border:false" style="width:200px;height:30px;margin-top:5px;">
                                            照片上传：<a class="easyui-linkbutton" id="personnel-button-uploadImg" >浏览</a>
                                            <a class="easyui-linkbutton" id="personnel-button-showOldImg" >查看原图</a>
		   								</div>
		   							</div>
								</td>
							</tr>
							<tr>
								<td align="right">员工类型：</td>
								<td>
									<input id="personnel-widget-personnelstyle" name="personnel.personnelstyle" value="3" />
								</td>
								<td align="right">最高学历：</td>
								<td><input id="personnel-widget-highestdegree" name="personnel.highestdegree" value="8"/>
								</td>
							</tr>
							<tr>
								<td align="right">所属部门：</td>
								<td>
					    			<input id="personnel-widget-personnelDept" style="width: 120px;" required="true" name="personnel.belongdeptid" value="<c:out value="${belongdeptid }"></c:out>"/>
								</td>
								<td align="right">所属岗位：</td>
								<td><input type="text" id="personnel-widget-personnelPost" style="width: 120px" name="personnel.belongpost"/></td>
							</tr>
							<tr>
								<td align="right">上级领导：</td>
								<td><input type="text" id="personnel-widget-leadid" name="personnel.leadid"/></td>
								<td align="right">性别：</td>
								<td><input type="text" id="personnel-widget-sex" name="personnel.sex" value="2" />
							</tr>
							<tr>
								<td align="right">婚姻状态：</td>
								<td><input id="personnel-widget-maritalstatus" name="personnel.maritalstatus" value="3" /></td>
								<td align="right">身份证号码：</td>
								<td><input id="personnel-input-idCard" type="text" name="personnel.idcard" class="easyui-validatebox" oninput="getAgeForeign(this.value)" validType="valididcard" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">出生日期：</td>
								<td><input id="personnel-input-birthday" name="personnel.birthday" class="easyui-validatebox" validType="validBirthday" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%d',onpicked:getAgeFunc})"/></td>
								<td align="right">年龄：</td>
								<td><input id="personnel-input-age" type="text" name="personnel.age" style="width: 120px" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right">民族：</td>
								<td><input id="personnel-widget-nation" name="personnel.nation" value="3"/></td>
								<td align="right">籍贯：</td>
								<td><input id="personnel-input-NPlace" type="text" name="personnel.nativeplace" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">政治面貌：</td>
								<td><input type="text" id="personnel-widget-polstatus" name="personnel.polstatus" value="5" /></td>
								<td align="right">电话：</td>
								<td><input type="text" class="easyui-validatebox" validType="phone" name="personnel.tel" style="width: 120px"/></td>
								<td align="right">传真：</td>
								<td><input type="text" class="easyui-validatebox" validType="phone" name="personnel.fax" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">手机号码：</td>
								<td><input type="text" name="personnel.telphone" style="width: 120px"/></td>
								<td align="right">公司短号：</td>
								<td><input type="text" name="personnel.compcornet" class="easyui-validatebox" validType="compcornet[6]" style="width: 120px"/></td>
								<td align="right">邮箱：</td>
								<td><input type="text" class="easyui-validatebox" validType="email" name="personnel.email" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">业务属性：</td>
								<td colspan="3">
									<div>
										<c:forEach var="list" items="${codeList}" varStatus="status">
		    							<input id="personnel-employetype_${list.code}" type="checkbox" class="personnel-employetype <c:if test="${fn:contains(norepeatemploye, list.code)}">norepeatemploye</c:if>" value="${list.code }"/>${list.codename }
		    								<c:if test="${(status.index+1)%5==0}"><br/></c:if>
		    							</c:forEach>
			    						<input type="hidden" id="personnel-employetype" name="personnel.employetype"/>
			    						<input type="hidden" id="personnel-norepeatemploye" value="${norepeatemploye }"/>
									</div>
								</td>
								<td align="right">薪资方案：</td>
								<td><input type="text" id="personnel-widget-Salaryscheme" name="personnel.salaryscheme" style="width: 120px"/>
								</td>
							</tr>
							<tr>
								<td align="right">居住地址：</td>
								<td colspan="3"><input type="text" maxlength="100" name="personnel.address" style="width: 400px"/></td>
								<td align="right">居住地邮编：</td>
								<td><input name="personnel.addrpostcode" class="easyui-validatebox" validType="zip" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">户籍地址：</td>
								<td colspan="3"><input type="text" maxlength="100" name="personnel.householdaddr" style="width: 400px"/></td>
								<td align="right">户籍地邮编：</td>
								<td><input name="personnel.householdcode" class="easyui-validatebox" validType="zip" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right">备注：</td>
								<td colspan="5">
									<textarea rows="" cols="" class="easyui-validatebox" validType="remark[200]" style="width: 635px;height:30px;" name="personnel.remark" style="overflow-y:scroll"></textarea>
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
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelWorksList"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="personnel-table-personnelPostList"></table>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="personnel-form-addPersonnelDefineInfo">
						<table border="0" cellpadding="2px" cellspacing="2px">
							<tr>
								<td align="right"><label>
								<c:choose>
									<c:when test="${fieldmap.field01 != \"\"}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
									<c:otherwise>自定义信息01</c:otherwise>
								</c:choose>							
								</label>：</td>
								<td><input name="personnel.field01" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field02 != \"\"}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
									<c:otherwise>自定义信息02</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field02" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field03 != \"\"}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
									<c:otherwise>自定义信息03</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field03" maxlength="100" type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field04 != \"\"}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field04" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field05 != \"\"}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field05" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field06 != \"\"}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field06" maxlength="100" type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field07 != \"\"}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field07" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field08 != \"\"}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field08" maxlength="100" type="text" style="width: 120px"/></td>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field09 != \"\"}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="personnel.field09" maxlength="100" type="text" style="width: 120px"/></td>
							</tr>
							<tr>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field10 != \"\"}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
									<c:otherwise>自定义信息10</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="personnel.field10" style="width: 625px"/></td>
							</tr>
							<tr>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field11 != \"\"}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
									<c:otherwise>自定义信息11</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="personnel.field11" style="width: 625px"/></td>
							</tr>
							<tr>
								<td align="right"><label><c:choose>
									<c:when test="${fieldmap.field12 != \"\"}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
									<c:otherwise>自定义信息12</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="personnel.field12" style="width: 625px"/></td>
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
	
		function addPersonnel(type){
			loading("提交中..");
  			$.ajax({
	  			url:'basefiles/addPersonnel.do?type='+type,
	  			data:personnel_BaseInfo(),
	  			dataType:'json',
	  			type:'post',
	  			success:function(retJson){
	  				loaded();
	  				if(retJson.flag){
						if (top.$('#tt').tabs('exists',person_title)){
		    				tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid('reload');
		    			}
		    			$("#basefiles-button-personnel").buttonWidget("addNewDataId",$("#basefiles-id-personnel").val());
		    			panelRefresh('basefiles/showPersonnelInfoPage.do?id='+$("#basefiles-id-personnel").val(),' 人员档案【查看】','view');
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
			$("#personnel-init-deptid").val("${belongdeptid}");
			
			$("#basefiles-button-personnel").buttonWidget("initButtonType","add");
			$("#basefiles-button-personnel").buttonWidget("disableButton","allotSysUserBtn");
			$("#basefiles-button-personnel").buttonWidget("disableButton","personnel-upload-file");
			
			loadDropdown();  
			
			clickEmployeType("add","");

    		getTabs("add");
    		
		});
	</script>
  </body>
</html>
