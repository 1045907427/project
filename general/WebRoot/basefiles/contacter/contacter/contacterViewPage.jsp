<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人查看页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
	<div id="contacter-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
    	<form action="basefiles/addContacter.do" id="contacter-form-contacterAddPage" method="post" style="width:100%;height:100%;">
  			<input type="hidden" name="addType" id="contacter-addType-contacterAddPage" />
	    	<input type="hidden" id="contacter-oldid" value="<c:out value="${contacter.id }"></c:out>" />
	    	<div id="contacter-layout-detail-north" data-options="region:'north',border:false">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
			    	<tr>
			    		<td style="width:80px;">编码：</td>
			    		<td><input value="<c:out value="${contacter.id }"></c:out>" readonly="readonly" /></td>
			    		<td style="width:80px;">姓名：</td>
			    		<td><input value="<c:out value="${contacter.name }"></c:out>" readonly="readonly" /></td>
			    		<td style="width:80px;">状态：</td>
			    		<td>
			    			<select disabled="disabled" class="len130 easyui-combobox">
								<c:forEach items="${stateList }" var="list">
								<c:choose>
									<c:when test="${list.code == contacter.state }">
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
			    </table>
			    <ul class="tags">
		    		<li class="selectTag"><a href="javascript:;">基本信息</a></li>
		    		<li><a href="javascript:;">对应分类</a></li>
		    		<li><a href="javascript:;">业务活动记录</a></li>
		    		<li><a href="javascript:;">自定义信息</a></li>
		    	</ul>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<div class="tagsDiv">
		    		<div class="tagsDiv_item" style="display:block;">
		    			<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    					<tr>
	    						<td>姓名拼音：</td>
	    						<td><input type="text" value="<c:out value="${contacter.spell }"></c:out>" readonly="readonly" /></td>
	    						<td>初次接触时间：</td>
	    						<td><input type="text" value="${contacter.firstcall }" readonly="readonly" /></td>
	    						<td rowspan="7" colspan="2">
	    							<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
	    								<div class="easyui-panel" id="contacter-imgPanel-contacterAddPage" style="width:150px;height:180px;overflow:hidden;">
	    									<img id="contacter-img-preview" <c:choose>
	    											<c:when test="${contacter.image == '' || contacter.image == null}">src="./image/photo_per_default.jpg"</c:when>
	    											<c:otherwise>src="${contacter.image }"</c:otherwise>
	    									</c:choose> style="width:150px;height:180px" />
	    								</div>
	    								<div class="easyui-panel" data-options="border:false" style="width:200px;height:25px;margin-top:5px;">
	    									照片上传：<input type="button" value="浏览" disabled="disabled" /><input type="button" value="查看原图" id="contacter-imgShow-contacterAddPage" />
	    									<input type="hidden" value="${contacter.image}" id="contacter-image-contacterAddPage" />
	    								</div>
	    							</div>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td>默认分类：</td>
	    						<td><input type="text" value="<c:out value="${contacter.linkmansort}"></c:out>" id="contacter-sortName-contacterAddPage" readonly="readonly" /></td>
	    						<td>所属供应商：</td>
	    						<td><input type="text" name="contacter.supplier" value="<c:out value="${contacter.supplier }"></c:out>" text="<c:out value="${contacter.suppliername }"></c:out>" id="contacter-supplier-contacterAddPage" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
	    						<td>所属客户：</td>
	    						<td><input type="text" name="contacter.customer" value="<c:out value="${contacter.customer }"></c:out>" text="<c:out value="${contacter.customername }"></c:out>" id="contacter-customer-contacterAddPage" readonly="readonly"/></td>
	    						<td>职务名称：</td>
	    						<td><input type="text" value="<c:out value="${contacter.job }"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>性别：</td>
	    						<td>
	    							<select disabled="disabled" class="len130 easyui-combobox">
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
	    							<select disabled="disabled" class="len130 easyui-combobox">
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
	    						<td><input type="text" value="${contacter.birthday}" readonly="readonly" /></td>
	    						<td>年龄：</td>
	    						<td><input type="text" value="<c:out value="${contacter.age}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>身份证号码：</td>
	    						<td><input type="text" value="<c:out value="${contacter.idcard}"></c:out>" readonly="readonly" /></td>
	    						<td>民族：</td>
	    						<td>
	    							<select disabled="disabled" class="len130 easyui-combobox">
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
	    						<td><input type="text" value="<c:out value="${contacter.nativeplace}"></c:out>" readonly="readonly" /></td>
	    						<td>政治面貌：</td>
	    						<td>
	    							<select disabled="disabled" class="len130 easyui-combobox">
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
	    						<td><input type="text" value="<c:out value="${contacter.tel}"></c:out>" readonly="readonly" /></td>
	    						<td>传真：</td>
	    						<td><input type="text" value="<c:out value="${contacter.fax}"></c:out>" readonly="readonly" /></td>
	    						<td>邮箱：</td>
	    						<td><input type="text" value="<c:out value="${contacter.email}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>手机号码：</td>
	    						<td><input type="text" value="<c:out value="${contacter.mobile}"></c:out>" readonly="readonly" /></td>
	    						<td>QQ：</td>
	    						<td><input type="text" value="<c:out value="${contacter.qq}"></c:out>" readonly="readonly" /></td>
	    						<td>MSN：</td>
	    						<td><input type="text" value="<c:out value="${contacter.msn}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>最佳联系时间：</td>
	    						<td colspan="3"><input type="text" value="<c:out value="${contacter.bestcall}"></c:out>" readonly="readonly" style="width:386px;" /></td>
	    						<td>最近联系时间：</td>
	    						<td><input type="text" readonly="readonly" value="<c:out value="${contacter.newcalldate }"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>居住地址：</td>
	    						<td colspan="3"><input type="text" value="<c:out value="${contacter.liveaddr}"></c:out>" readonly="readonly" style="width:386px;" /></td>
	    						<td>居住地邮编：</td>
	    						<td><input type="text" value="<c:out value="${contacter.livezip}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>户籍地址：</td>
	    						<td colspan="3"><input type="text" value="<c:out value="${contacter.nativeaddr}"></c:out>" readonly="readonly" style="width:386px;" /></td>
	    						<td>户籍地邮编：</td>
	    						<td><input type="text" value="<c:out value="${contacter.nativezip}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td>家庭状况：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" readonly="readonly" ><c:out value="${contacter.family}"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td>兴趣爱好：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" readonly="readonly" ><c:out value="${contacter.hobby}"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td>备注：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" readonly="readonly" ><c:out value="${contacter.remark}"></c:out></textarea></td>
	    					</tr>
	    				</table>
		    		</div>
		    		<div class="tagsDiv_item">
		    			<table id="contacter-sortList-contacterAddPage"> 
	    					<thead>
	    						<tr>
	    							<th data-options="field:'linkmansortname',width:120">联系人分类</th>
	    							<th data-options="field:'isdefault',width:100">是否默认分类</th>
	    							<th data-options="field:'remark',width:280">备注</th>
	    						</tr>
	    					</thead>
	    					<tbody>
	    						<c:forEach items="${contacter.contacterAndSortList }" var="list">
	    						<tr>
	    							<td>${list.linkmansortname }</td>
	    							<td>
	    								<c:if test="${list.isdefault == 0 }">否</c:if>
	    								<c:if test="${list.isdefault == 1 }">是</c:if>
	    							</td>
	    							<td>${list.remark }</td>
	    						</tr>
	    						</c:forEach>
	    					</tbody>
	    				</table>
		    		</div>
		    		<div class="tagsDiv_item">
		    			
		    		</div>
		    		<div class="tagsDiv_item">
		    			<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px" id="contacter-field-contacterAddPage">
	    					<tr>
	    						<td style="width:100px;"><span class="field-contacter" rel="field01"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field01}"></c:out>" readonly="readonly" /></td>
	    						<td style="width:100px;"><span class="field-contacter" rel="field02"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field02}"></c:out>" readonly="readonly" /></td>
	    						<td style="width:100px;"><span class="field-contacter" rel="field03"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field03}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td><span class="field-contacter" rel="field04"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field04}"></c:out>" readonly="readonly" /></td>
	    						<td><span class="field-contacter" rel="field05"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field05}"></c:out>" readonly="readonly" /></td>
	    						<td><span class="field-contacter" rel="field06"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field06}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td><span class="field-contacter" rel="field07"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field07}"></c:out>" readonly="readonly" /></td>
	    						<td><span class="field-contacter" rel="field08"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field08}"></c:out>" readonly="readonly" /></td>
	    						<td><span class="field-contacter" rel="field09"></span></td>
	    						<td><input type="text" value="<c:out value="${contacter.field09}"></c:out>" readonly="readonly" /></td>
	    					</tr>
	    					<tr>
	    						<td><span class="field-contacter" rel="field10"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" readonly="readonly" ><c:out value="${contacter.field10}"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td><span class="field-contacter" rel="field11"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" readonly="readonly" ><c:out value="${contacter.field11}"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td><span class="field-contacter" rel="field12"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" readonly="readonly" ><c:out value="${contacter.field12}"></c:out></textarea></td>
	    					</tr>
	    				</table>
		    		</div>
		    	</div>
	    	</div>
    	</form>
	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#contacter-imgShow-contacterAddPage").click(function(){
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
    		$("#contacter-customer-contacterAddPage").customerWidget({ //所属客户
    			readonly:true
    		});
    		$("#contacter-supplier-contacterAddPage").supplierWidget({
    			readonly:true
    		});
			$("#contacter-buttons-contacterPage").buttonWidget("setDataID", {id:$("#contacter-oldid").val(), state:'${contacter.state}', type:'view'});
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
			$(".tags li").click(function(){ //选项选择事件
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 1){
					var height = $(window).height()-97;
					if(!$("#contacter-sortList-contacterAddPage").hasClass("create-datagrid")){
						$("#contacter-sortList-contacterAddPage").datagrid({ //客户分类行编辑
							height:height,
							border:false,
							idField:'linkmansort',
							singleSelect:true,
							rownumbers:true
						});
						$("#contacter-sortList-contacterAddPage").addClass("create-datagrid")
					}
				}
			});
    		$("#contacter-sortName-contacterAddPage").dblclick(function(){ //选择对应分类选项
    			selectTags(1)
    		});
    	});
    	function selectTags(index){ //选择第index个选项
   			$(".tags li").eq(index).click();
    	}
    </script>
  </body>
</html>
