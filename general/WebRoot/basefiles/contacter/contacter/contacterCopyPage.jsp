<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人复制页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
  	<form action="basefiles/addContacter.do" id="contacter-form-contacterAddPage" method="post" style="width:100%;height:100%;">
  		<input type="hidden" name="addType" id="contacter-addType-contacterAddPage" />
  		<input type="hidden" id="contacter-id" value="<c:out value="${contacter.id }"></c:out>"/>
	    <div class="easyui-layout" data-options="fit:true">
	    	<div data-options="region:'north',border:false">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
			    	<tr>
			    		<td style="width:80px;">编码：</td>
			    		<td><input class="easyui-validatebox" name="contacter.id" validType="validUsed" id="contacter-id-contacterAddPage" data-options="required:true" /></td>
			    		<td style="width:80px;">姓名：</td>
			    		<td><input name="contacter.name" /></td>
			    		<td style="width:80px;">状态：</td>
			    		<td><select class="len130 easyui-combobox" disabled="disabled"><option>新增</option></select></td>
			    	</tr>
			    </table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<div class="easyui-tabs" id="contacter-tabs-contacterAddPage" data-options="fit:true,border:false">
	    			<div title="基本信息" data-options="fit:true">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    					<tr>
	    						<td>姓名拼音：</td>
	    						<td><input type="text" name="contacter.spell" /></td>
	    						<td>初次接触时间：</td>
	    						<td><input type="text" name="contacter.firstcall" value="${contacter.firstcall }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    						<td rowspan="7" colspan="2">
	    							<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
	    								<div class="easyui-panel" id="contacter-imgPanel-contacterAddPage" style="width:150px;height:180px;overflow:hidden;">
	    									<img id="contacter-img-preview" <c:choose>
	    											<c:when test="${contacter.image == '' || contacter.image == null}">src="./image/photo_per_default.jpg"</c:when>
	    											<c:otherwise>src="${contacter.image }"</c:otherwise>
	    									</c:choose> style="width:150px;height:180px" />
	    								</div>
	    								<div class="easyui-panel" data-options="border:false" style="width:200px;height:25px;margin-top:5px;">
	    									照片上传：<input type="button" value="浏览" id="contacter-imgBrowse-contacterAddPage" /><input type="button" value="查看原图" id="contacter-imgShow-contacterAddPage" />
	    									<input type="hidden" name="contacter.image" value="${contacter.image }" id="contacter-image-contacterAddPage" />
	    								</div>
	    							</div>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td>默认分类：</td>
	    						<td>
	    							<input type="text" readonly="readonly" id="contacter-sortName-contacterAddPage" value="<c:out value="${contacterSortName }"></c:out>" />
	    							<input type="hidden" name="contacter.linkmansort" readonly="readonly" id="contacter-sort-contacterAddPage" value="<c:out value="${contacter.linkmansort }"></c:out>" />
	    						</td>
	    						<td>所属供应商：</td>
	    						<td><input type="text" name="contacter.supplier" value="<c:out value="${contacter.supplier }"></c:out>" text="<c:out value="${contacter.suppliername }"></c:out>" id="contacter-supplier-contacterAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td>所属客户：</td>
	    						<td><input type="text" name="contacter.customer" value="<c:out value="${contacter.customer }"></c:out>" text="<c:out value="${contacter.customername }"></c:out>" id="contacter-customer-contacterAddPage" /></td>
	    						<td>职务名称：</td>
	    						<td><input type="text" name="contacter.job" value="<c:out value="${contacter.job }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>性别：</td>
	    						<td>
	    							<select name="contacter.sex" class="len130 easyui-combobox">
	    								<c:forEach items="${sexList }" var="list">
	    								<c:choose>
	    								<c:when test="${list.code == contacter.sex }">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    								</c:choose>
	    								</c:forEach>
	    							</select>
	    						</td>
	    						<td>婚姻状态：</td>
	    						<td>
	    							<select name="contacter.maritalstatus" class="len130 easyui-combobox">
	    								<c:forEach items="${marryList }" var="list">
	    								<c:choose>
	    								<c:when test="${list.code == contacter.maritalstatus }">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    								</c:choose>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td>出生日期：</td>
	    						<td><input type="text" name="contacter.birthday" value="${contacter.birthday }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    						<td>年龄：</td>
	    						<td><input type="text" name="contacter.age" value="<c:out value="${contacter.age }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>身份证号码：</td>
	    						<td><input type="text" name="contacter.idcard" value="<c:out value="${contacter.idcard }"></c:out>" /></td>
	    						<td>民族：</td>
	    						<td>
	    							<select name="contacter.nation" class="len130 easyui-combobox">
	    								<c:forEach items="${nationList }" var="list">
	    								<c:choose>
	    								<c:when test="${list.code == contacter.nation }">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    								</c:choose>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td>籍贯：</td>
	    						<td><input type="text" name="contacter.nativeplace" value="<c:out value="${contacter.nativeplace }"></c:out>" /></td>
	    						<td>政治面貌：</td>
	    						<td>
	    							<select name="contacter.polstatus" class="len130 easyui-combobox">
	    								<c:forEach items="${polList }" var="list">
	    								<c:choose>
	    								<c:when test="${list.code == contacter.polstatus}">
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    								</c:choose>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td>电话：</td>
	    						<td><input type="text" name="contacter.tel" value="<c:out value="${contacter.tel }"></c:out>" /></td>
	    						<td>传真：</td>
	    						<td><input type="text" name="contacter.fax" value="<c:out value="${contacter.fax }"></c:out>" /></td>
	    						<td>邮箱：</td>
	    						<td><input type="text" name="contacter.email" value="<c:out value="${contacter.email }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>手机号码：</td>
	    						<td><input type="text" name="contacter.mobile" value="<c:out value="${contacter.mobile }"></c:out>" /></td>
	    						<td>QQ：</td>
	    						<td><input type="text" name="contacter.qq" value="<c:out value="${contacter.qq }"></c:out>" /></td>
	    						<td>MSN：</td>
	    						<td><input type="text" name="contacter.msn" value="<c:out value="${contacter.msn }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>最佳联系时间：</td>
	    						<td colspan="3"><input type="text" name="contacter.bestcall" value="<c:out value="${contacter.bestcall }"></c:out>" style="width:386px;" /></td>
	    						<td>最近联系时间：</td>
	    						<td><input type="text" readonly="readonly" value="<c:out value="${contacter.newcalldate }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>居住地址：</td>
	    						<td colspan="3"><input type="text" name="contacter.liveaddr" value="<c:out value="${contacter.liveaddr }"></c:out>" style="width:386px;" /></td>
	    						<td>居住地邮编：</td>
	    						<td><input type="text" name="contacter.livezip" value="<c:out value="${contacter.livezip }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>户籍地址：</td>
	    						<td colspan="3"><input type="text" name="contacter.nativeaddr" value="<c:out value="${contacter.nativeaddr }"></c:out>" style="width:386px;" /></td>
	    						<td>户籍地邮编：</td>
	    						<td><input type="text" name="contacter.nativezip" value="<c:out value="${contacter.nativezip }"></c:out>" /></td>
	    					</tr>
	    					<tr>
	    						<td>家庭状况：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.family"><c:out value="${contacter.family }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td>兴趣爱好：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.hobby"><c:out value="${contacter.hobby }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td>备注：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.remark"><c:out value="${contacter.remark }"></c:out></textarea></td>
	    					</tr>
	    				</table>
	    			</div>
	    			<div title="对应分类" data-options="fit:true">
	    				
	    			</div>
	    			<div title="业务活动记录" data-options="fit:true">
	    			
	    			</div>
	    			<div title="自定义信息" data-options="fit:true">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px" id="contacter-field-contacterAddPage">
		    					<tr>
		    						<td style="width:100px;"><span class="field-contacter" rel="field01"></span></td>
		    						<td><input type="text" name="contacter.field01" value="<c:out value="${contacter.field01 }"></c:out>" /></td>
		    						<td style="width:100px;"><span class="field-contacter" rel="field02"></span></td>
		    						<td><input type="text" name="contacter.field02" value="<c:out value="${contacter.field02 }"></c:out>" /></td>
		    						<td style="width:100px;"><span class="field-contacter" rel="field03"></span></td>
		    						<td><input type="text" name="contacter.field03" value="<c:out value="${contacter.field03 }"></c:out>" /></td>
		    					</tr>
		    					<tr>
		    						<td><span class="field-contacter" rel="field04"></span></td>
		    						<td><input type="text" name="contacter.field04" value="<c:out value="${contacter.field04 }"></c:out>" /></td>
		    						<td><span class="field-contacter" rel="field05"></span></td>
		    						<td><input type="text" name="contacter.field05" value="<c:out value="${contacter.field05 }"></c:out>" /></td>
		    						<td><span class="field-contacter" rel="field06"></span></td>
		    						<td><input type="text" name="contacter.field06" value="<c:out value="${contacter.field06 }"></c:out>" /></td>
		    					</tr>
		    					<tr>
		    						<td><span class="field-contacter" rel="field07"></span></td>
		    						<td><input type="text" name="contacter.field07" value="<c:out value="${contacter.field07 }"></c:out>" /></td>
		    						<td><span class="field-contacter" rel="field08"></span></td>
		    						<td><input type="text" name="contacter.field08" value="<c:out value="${contacter.field08 }"></c:out>" /></td>
		    						<td><span class="field-contacter" rel="field09"></span></td>
		    						<td><input type="text" name="contacter.field09" value="<c:out value="${contacter.field09 }"></c:out>" /></td>
		    					</tr>
		    					<tr>
		    						<td><span class="field-contacter" rel="field10"></span></td>
		    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field10"><c:out value="${contacter.field10 }"></c:out></textarea></td>
		    					</tr>
		    					<tr>
		    						<td><span class="field-contacter" rel="field11"></span></td>
		    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field11"><c:out value="${contacter.field11 }"></c:out></textarea></td>
		    					</tr>
		    					<tr>
		    						<td><span class="field-contacter" rel="field12"></span></td>
		    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field12"><c:out value="${contacter.field12 }"></c:out></textarea></td>
		    					</tr>
		    				</table>
	    			</div>
	    		</div>
	    	</div>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		//$("#contacter-sort-contacterAddPage").widget({ //联系人分类
    		//	name:'t_base_linkman_info',
			//	col:'linkmansort',
    		//	singleSelect:true,
    		//	width:128,
    		//	onlyLeafCheck:true
    		//});
    		$("#contacter-sortName-contacterAddPage").dblclick(function(){
    			$("#contacter-tabs-contacterAddPage").tabs("select","对应分类");
    		});
    		$("#contacter-id-contacterAddPage").change(function(){
    			validUsed("basefiles/contacterNoUsed.do", $(this).val(), $("#contacter-id").val(), "该编号已被使用，请另输入编号！");
    		});
    		$("#contacter-customer-contacterAddPage").customerWidget({ //所属客户
    			onSelect: function(data){
    				$("#contacter-supplier-contacterAddPage").supplierWidget({disabled: true}).supplierWidget('clear');
    			}
    		});
    		$("#contacter-supplier-contacterAddPage").supplierWidget({ //所属供应商
    		});
    		$("#contacter-imgBrowse-contacterAddPage").upload({
    			auto: true,
				del: false,
				type: 'upload',
				onComplete:function(json){
					$("#contacter-image-contacterAddPage").val(json.fullPath);
					$("#contacter-imgPanel-contacterAddPage").html("<img src='"+json.fullPath+"' style='width:150px;height:180px;' />");
					$("#z-upload-dialog").dialog('close',true);
				}
    		});
    		$("#contacter-imgShow-contacterAddPage").click(function(){ //查看原图
    			var photograph = document.getElementById("contacter-img-preview").getAttribute("src");
				$('#contacter-window-showOldImg').window({  
					    title: '查看原图',  
					    width: $(window).width(),  
					    height: $(window).height(),  
					    closed: true,  
					    cache: false,  
					    modal: true 
					});
				$("#contacter-window-showOldImg").window("open");
				$("#contacter-window-showOldImg").window("refresh","basefiles/showContacterOldImgPage.do?photograph="+photograph);
    			//window.open($("#contacter-image-contacterAddPage").val(),"原图查看");
    		});
    		$("#contacter-buttons-contacterPage").buttonWidget("initButtonType", 'add');
			$.ajax({ //获取自定义字段的名称，数据来源数据字典
				url:'basefiles/contacterDIYFieldName.do',
				dataType:'json',
				type:'post',
				success:function(json){
					$("#contacter-field-contacterAddPage").find(".field-contacter").each(function(){
						var rel = $(this).attr("rel");
						$(this).text(json[rel] + "：");
					});
				}
			});
    	});
    </script>
  </body>
</html>
