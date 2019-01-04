<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案复制</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div id="buySupplier-layout-detail" class="easyui-layout" data-options="fit:true,border:false">  		
  		<form action="basefiles/addBuySupplier.do" id="buy-form-buySupplierAddPage" method="post">	    	
	    	<div id="buySupplier-layout-detail-north" data-options="region:'north',border:false" style="height:62px">
	    		<input type="hidden" name="addType" id="buy-addType-buySupplierAddPage" />
  				<input type="hidden" id="buySupplier-oldid" value="<c:out value="${buySupplier.id }"></c:out>"/>
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
			    	<tr>
			    		<td style="width:100px;text-align: right;">编码：</td>
			    		<td style="width:165px;"><input class="easyui-validatebox" name="buySupplier.id" validType="validUsed[20]" id="buy-id-buySupplierAddPage" <c:if test="${editFlag == false }">readonly="readonly"</c:if> data-options="required:true"  style="width:130px;"/></td>
			    		<td style="width:100px;text-align: right;">名称：</td>
			    		<td style="width:165px;"><input class="easyui-validatebox" name="buySupplier.name" style="width:130px;" /></td>
			    		<td style="width:120px;text-align: right;">状态：</td>
			    		<td><select disabled="disabled" class="len130 easyui-combobox"><option>新增</option></select></td>
			    	</tr>
			    </table>
			    <ul class="tags">
		    		<li class="selectTag"><a href="javascript:;">基本信息</a></li>
		    			<li><a href="javascript:;">控制信息</a></li>
		    			<li><a href="javascript:;">联系人信息</a></li>
		    			<li><a href="javascript:;">对应分类</a></li>
		    			<li><a href="javascript:;">可供商品</a></li>
		    			<li><a href="javascript:;">自定义信息</a></li>
		    	</ul>
	    	</div>
	    	<div id="buySupplier-layout-detail-center" data-options="region:'center',border:false">
	    		<div class="tagsDiv">
		    		<div class="tagsDiv_item" style="display:block;">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
	    					<tr>
	    						<td style="text-align: right;">简称：</td>
	    						<td style=""><input type="text" name="buySupplier.shortname" style="width:130px;"/></td>
	    						<td style="text-align: right;">名称拼音:</td>
								<td><input type="text" name="buySupplier.pinyin" style="width:130px;" maxlength="20" /></td>
	    						<td style="text-align: right;">助记码：</td>
	    						<td style=""><input type="text" name="buySupplier.spell" style="width:130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">税号：</td>
	    						<td><input type="text" name="buySupplier.taxno" style="width:130px;"/></td>
	    						<td style="text-align: right;">开户银行：</td>
	    						<td><input type="text" name="buySupplier.bank" value="<c:out value="${buySupplier.bank }"></c:out>" maxlength="20" style="width:130px;"/></td>
	    						<td style="text-align: right;">开户账号：</td>
	    						<td><input type="text" name="buySupplier.cardno" value="<c:out value="${buySupplier.cardno }"></c:out>" maxlength="20" style="width:130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">注册资金：</td>
	    						<td><input type="text" name="buySupplier.fund" value="${buySupplier.fund }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width:130px;"/></td>
	    						<td style="text-align: right;">开户日期：</td>
	    						<td><input type="text" name="buySupplier.setupdate" value="${buySupplier.setupdate }" style="width:130px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" maxlength="10"/></td>
	    						<td style="text-align: right;">法人代表：</td>
	    						<td><input type="text" name="buySupplier.person" value="<c:out value="${buySupplier.person }"></c:out>" maxlength="20" style="width:130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">法人代表电话：</td>
	    						<td><input type="text" name="buySupplier.personmobile" value="<c:out value="${buySupplier.personmobile }"></c:out>" maxlength="20" style="width:130px;"/></td>
	    						<td style="text-align: right;">法人身份证号码：</td>
	    						<td><input type="text" name="buySupplier.personcard" value="<c:out value="${buySupplier.personcard }"></c:out>" maxlength="20" style="width:130px;"/></td>
	    						<td style="text-align: right;">公司属性：</td>
	    						<td><select name="buySupplier.nature" class="len130 easyui-combobox">
	    								<option></option>
	    								<c:forEach items="${natureList }" var="list">
				    						<c:choose>
					    						<c:when test="${buySupplier.nature == list.code}">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    						</c:when>
					    						<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    						</c:otherwise>
				    						</c:choose>
			    						</c:forEach>
	    						</select></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">电话：</td>
	    						<td><input type="text" name="buySupplier.telphone" value="<c:out value="${buySupplier.telphone }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">传真：</td>
	    						<td><input type="text" name="buySupplier.faxno" value="<c:out value="${buySupplier.faxno }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">邮箱：</td>
	    						<td><input type="text" name="buySupplier.email" value="${buySupplier.email }" class="easyui-validatebox" validType="email" style="width:130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">邮编：</td>
	    						<td><input type="text" name="buySupplier.zip" value="<c:out value="${buySupplier.zip }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">网址：</td>
	    						<td><input type="text" name="buySupplier.website" value="<c:out value="${buySupplier.website }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">员工人数：</td>
	    						<td><input type="text" name="buySupplier.staffnum" value="<c:out value="${buySupplier.staffnum }"></c:out>" style="width:130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">年产值：</td>
	    						<td><input type="text" name="buySupplier.turnoveryear" value="<c:out value="${buySupplier.turnoveryear }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">初次业务日期：</td>
	    						<td><input type="text" name="buySupplier.firstbusinessdate" value="${buySupplier.firstbusinessdate }" style="width:130px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" maxlength="10"/></td>
	    						<td style="text-align: right;">业务联系人：</td>
	    						<td><input type="text" style="width: 130px;" name="buySupplier.contactname" value="<c:out value="${buySupplier.contactname }"></c:out>" maxlength="20"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">业务联系电话:</td>
		    					<td><input type="text" name="buySupplier.contactmobile" value="<c:out value="${buySupplier.contactmobile }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">业务联系人邮箱:</td>
		    					<td><input type="text" name="buySupplier.contactemail" value="${buySupplier.contactemail }" class="easyui-validatebox" validType="email" maxlength="50" /></td>
	    						<td style="width:120px;text-align: right;">ABC等级：</td>
	    						<td>	    							
	    							<select name="buySupplier.abclevel" class="len130 easyui-combobox">
	    								<option></option>
		    								<option <c:if test="${buySupplier.abclevel == 'A'}">selected="selected"</c:if> value="A">A</option>
		    								<option <c:if test="${buySupplier.abclevel == 'B'}">selected="selected"</c:if> value="B">B</option>
		    								<option <c:if test="${buySupplier.abclevel == 'C'}">selected="selected"</c:if> value="C">C</option>
		    								<option <c:if test="${buySupplier.abclevel == 'D'}">selected="selected"</c:if> value="D">D</option>
	    							</select>
								</td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">详细地址：</td>
							    <td><input type="text" name="buySupplier.address" value="<c:out value="${buySupplier.address }"></c:out>" maxlength="100" style="width: 210px"/></td>
	    						<td style="text-align: right;">所属区域：</td>
	    						<td><input type="text" id="buy-buySupplierAddPage-buyarea" name="buySupplier.buyarea" value="<c:out value="${buySupplier.buyarea }"></c:out>" style="width:130px;"/></td>
	    						<td style="text-align: right;">默认分类：</td>
	    						<td><input type="text" id="buy-buySupplierAddPage-sortName" style="width:130px;" value="<c:out value="${buySupplier.suppliersortname }"></c:out>" readonly="readonly"/>
	    							<input type="hidden" name="buySupplier.suppliersort" value="<c:out value="${buySupplier.suppliersort }"></c:out>" id="buy-buySupplierAddPage-sort" style="width:130px;" readonly="readonly"/>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">财务联系人：</td>
	    						<td><input type="text" style="width: 130px;" name="buySupplier.financiallink" value="<c:out value="${buySupplier.financiallink }"></c:out>" maxlength="20"/></td>
	    						<td style="text-align: right;">财务联系电话:</td>
		    					<td><input type="text" name="buySupplier.financialmobile" value="<c:out value="${buySupplier.financialmobile }"></c:out>" maxlength="20" /></td>
	    						<td style="text-align: right;">财务联系人邮箱:</td>
		    					<td><input type="text" name="buySupplier.financialemail" value="${buySupplier.financialemail }" class="easyui-validatebox" validType="email" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">区域主管：</td>
	    						<td><input type="text" style="width: 130px;" name="buySupplier.contactarea" value="<c:out value="${buySupplier.contactarea }"></c:out>" maxlength="20"/></td>
	    						<td style="text-align: right;">区域主管联系电话:</td>
		    					<td><input type="text" name="buySupplier.contactareamobile" value="<c:out value="${buySupplier.contactareamobile }"></c:out>" maxlength="20" /></td>
	    						<td style="text-align: right;">区域主管邮箱:</td>
		    					<td><input type="text" name="buySupplier.contactareaemail" value="${buySupplier.contactareaemail }" class="easyui-validatebox" validType="email" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">大区/省区经理：</td>
	    						<td><input type="text" style="width: 130px;" name="buySupplier.region" value="<c:out value="${buySupplier.region }"></c:out>" maxlength="20"/></td>
	    						<td style="text-align: right;">大区/省区经理联系电话:</td>
		    					<td><input type="text" name="buySupplier.regionmobile" value="<c:out value="${buySupplier.regionmobile }"></c:out>" maxlength="20" /></td>
	    						<td style="text-align: right;">大区/省区经理邮箱:</td>
		    					<td><input type="text" name="buySupplier.regionemail" value="${buySupplier.regionemail }" class="easyui-validatebox" validType="email" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">备注：</td>
	    						<td colspan="5">
	    							<textarea rows="0" cols="0" style="height:50px;width:720px;" class="easyui-validatebox" validType="maxByteLength[500]" name="buySupplier.remark"><c:out value="${buySupplier.remark }"></c:out></textarea>
								</td>
	    					</tr>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<div>
		    				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
		    					<tr>
		    						<td style="width:100px;text-align: right;">信用额度：</td>
		    						<td><input type="text" name="buySupplier.credit" value="${buySupplier.credit }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    						<td style="width:100px;text-align: right;">信用期限：</td>
		    						<td><input type="text" name="buySupplier.creditdate" value="${buySupplier.creditdate }" style="width:130px;" maxlength="10" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
		    						<td style="width:100px;text-align: right;">月销售指标：</td>
		    						<td><input type="text" name="buySupplier.salesmonth" value="${buySupplier.salesmonth }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">结算方式：</td>
		    						<td><input type="hidden" id="buy-buySupplierAddPage-settletype" name="buySupplier.settletype" value="<c:out value="${buySupplier.settletype }"></c:out>" style="width:130px;" /></td>
		    						<td style="width:100px;text-align: right;">结算日：</td>
		    						<td>
		    						<select name="buySupplier.settleday" class="len130 easyui-combobox">		    							
		    								<option></option>
		    								<c:forEach items="${dayList}" var="day">
			    								<c:choose>
				    								<c:when test="${day == buySupplier.settleday}"><option value="${day }" selected="selected">${day }</option></c:when>
				    								<c:otherwise><option value="${day }">${day }</option></c:otherwise>
			    								</c:choose>
		    								</c:forEach>
		    						</select></td>
		    						<td style="width:100px;text-align: right;">资金占用额度：</td>
		    						<td><input type="text" name="buySupplier.ownlimit" value="${buySupplier.ownlimit }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">年度目标：</td>
		    						<td><input type="text" name="buySupplier.annualobjectives" value="${buySupplier.annualobjectives }" style="width:130px;" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    						<td style="width:100px;text-align: right;">年度返利%：</td>
		    						<td><input type="text" name="buySupplier.annualrebate" value="${buySupplier.annualrebate }" style="width:130px;" class="easyui-numberbox" data-options="max:100,precision:2"/></td>
		    						<td style="width:100px;text-align: right;">半年度返利%：</td>
		    						<td><input type="text" name="buySupplier.semiannualrebate" value="${buySupplier.semiannualrebate }" style="width:130px;" class="easyui-numberbox" data-options="max:100,precision:2"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">季度返利%：</td>
		    						<td><input type="text" name="buySupplier.quarterlyrebate" value="${buySupplier.quarterlyrebate }" style="width:130px;" class="easyui-numberbox" data-options="max:100,precision:2"/></td>
		    						<td style="width:100px;text-align: right;">月度返利%：</td>
		    						<td><input type="text" name="buySupplier.monthlyrebate" value="${buySupplier.monthlyrebate }" style="width:130px;" class="easyui-numberbox" data-options="max:100,precision:2"/></td>
		    						<td style="width:100px;text-align: right;">破损补贴：</td>
		    						<td><input type="text" name="buySupplier.breakagesubsidies" value="${buySupplier.breakagesubsidies }" style="width:130px;" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">其他费用补贴：</td>
		    						<td><input type="text" name="buySupplier.othersubsidies" value="${buySupplier.othersubsidies }" style="width:130px;" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    						<td style="width:100px;text-align: right;">收回方式：</td>
		    						<td><select name="buySupplier.recoverymode" class="len130 easyui-combobox">
		    								<option></option>
		    								<c:forEach items="${recoverymodeList }" var="list">
					    						<c:choose>
						    						<c:when test="${buySupplier.recoverymode == list.code}">
						    						<option value="${list.code }" selected="selected">${list.codename }</option>
						    						</c:when>
						    						<c:otherwise>
						    						<option value="${list.code }">${list.codename }</option>
						    						</c:otherwise>
					    						</c:choose>
				    						</c:forEach>
		    						</select></td>
		    						<td style="width:100px;text-align: right;">供价折扣率%：</td>
		    						<td><input type="text" name="buySupplier.pricediscount" value="${buySupplier.pricediscount }" style="width:130px;" class="easyui-numberbox" data-options="max:100,precision:2"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">其他条件：</td>
		    						<td><textarea cols="18" rows="1" name="buySupplier.otherconditions"><c:out value="${buySupplier.otherconditions }"></c:out></textarea></td>
		    						<td style="width:100px;text-align: right;">促销员名额：</td>
		    						<td><input type="text" name="buySupplier.promotersplaces" value="${buySupplier.promotersplaces }" style="width:130px;" class="easyui-numberbox" data-options="max:9999,precision:0"/></td>
		    						<td style="width:100px;text-align: right;">促销员工资：</td>
		    						<td><input type="text" name="buySupplier.promoterssalary" value="${buySupplier.promoterssalary }" style="width:130px;" class="easyui-numberbox" data-options="max:999999999999,precision:2"/></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">业务员名额：</td>
		    						<td><input type="text" name="buySupplier.salesmanplaces" value="${buySupplier.salesmanplaces }" style="width:130px;" class="easyui-numberbox" data-options="max:9999,precision:0"/></td>
		    						<td style="width:100px;text-align: right;">业务员工资：</td>
		    						<td><input type="text" name="buySupplier.salesmansalary" value="${buySupplier.salesmansalary }" style="width:130px;" class="easyui-numberbox" data-options="max:999999999999,precision:2"/></td>
		    						<td style="width:100px;text-align: right;">核销方式:</td>
					    			<td><select name="buySupplier.canceltype" class="len130 easyui-combobox" >
					    				<option></option>
										<c:forEach items="${canceltypeList }" var="list">
						    				<c:choose>
						    					<c:when test="${list.code == buySupplier.canceltype }">
						    						<option value="${list.code }" selected="selected">${list.codename }</option>
						    					</c:when>
						    					<c:otherwise>
						    						<option value="${list.code }">${list.codename }</option>
						    					</c:otherwise>
						    				</c:choose>
						    			</c:forEach>
					    			</select></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;display: none;">支付方式：</td>
		    						<td style="display: none;"><input type="hidden" id="buy-buySupplierAddPage-paytype" name="buySupplier.paytype" value="<c:out value="${buySupplier.paytype }"></c:out>" style="width:130px;" /></td>
		    						<td style="width:100px;text-align: right;">默认采购部门：</td>
		    						<td><input type="text" id="buy-buySupplierAddPage-buydeptid" name="buySupplier.buydeptid" value="<c:out value="${buySupplier.buydeptid }"></c:out>" style="width:130px;" /></td>
		    						<td style="width:100px;text-align: right;">所属部门：</td>
		    						<td><input type="text" id="buy-buySupplierAddPage-filiale" name="buySupplier.filiale" value="<c:out value="${buySupplier.filiale }"></c:out>" style="width:130px;"/></td>
		    						<td style="width:100px;text-align: right;">默认采购员：</td>
		    						<td><input type="text" id="buy-buySupplierAddPage-buyuserid" name="buySupplier.buyuserid" value="<c:out value="${buySupplier.buyuserid }"></c:out>" style="width:130px;" /></td>
		    					</tr>
		    					<tr>
		    						<td style="width:100px;text-align: right;">默认仓库：</td>
		    						<td><input type="text" id="buy-buySupplierAddPage-storageid" name="buySupplier.storageid" value="<c:out value="${buySupplier.storageid }"></c:out>" style="width:130px;" /></td>
		    						<td style="width:100px;text-align: right;">订单追加：</td>
		    						<td colspan="3">
		    							<select id="" name="buySupplier.orderappend" style="width:130px;" >
		    								<option value="1" <c:if test="${buySupplier.orderappend == '1' }">selected="selected"</c:if>>是</option>
		    								<option value="0" <c:if test="${buySupplier.orderappend == '0' }">selected="selected"</c:if>>否</option>
		    							</select>
		    						</td>
		    					</tr>
		    				</table>
	    				</div>
    					<div style="margin-top:10px;margin-left:10px;">
		    				<div class="easyui-panel" title="最新动态" style="width:850px;"> 					
			    				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
			    					<tr>
			    						<td colspan="4" style="width:120px;text-align: right;">&nbsp;</td>
			    						<td style="width:120px;text-align: right; color: #f00;">其他应收金额:</td>
			    						<td><input type="text" name="buySupplier.otherduefromsum" value="${buySupplier.otherduefromsum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    					</tr>
			    					<tr>
			    						<td style="width:120px;text-align: right;">累计采购金额：</td>
			    						<td><input type="text" name="buySupplier.buysum" value="${buySupplier.buysum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">累计付款金额：</td>
			    						<td><input type="text" name="buySupplier.paysum" value="${buySupplier.paysum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">应付金额：</td>
			    						<td><input type="text" name="buySupplier.payable" value="${buySupplier.payable }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    					</tr>
			    					<tr>
			    						<td style="width:120px;text-align: right;">本年累计采购金额：</td>
			    						<td><input type="text" name="buySupplier.buysumyear" value="${buySupplier.buysumyear }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">本年累计付款金额：</td>
			    						<td><input type="text" name="buySupplier.paysumyear" value="${buySupplier.paysumyear }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">最新采购日期：</td>
			    						<td><input type="text" name="buySupplier.payable" value="${buySupplier.payable }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
			    					</tr>
			    					<tr>
			    						<td style="width:120px;text-align: right;">最新采购金额：</td>
			    						<td><input type="text" name="buySupplier.newbuysum" value="${buySupplier.newbuysum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">最新付款日期：</td>
			    						<td><input type="text" name="buySupplier.newpaydate" value="${buySupplier.newpaydate }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
			    						<td style="width:120px;text-align: right;">最新付款金额：</td>
			    						<td><input type="text" name="buySupplier.newpaysum" value="${buySupplier.newpaysum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    					</tr>
			    					<tr>
			    						<td style="width:120px;text-align: right;">实际占用金额：</td>
			    						<td><input type="text" name="buySupplier.realownsum" value="${buySupplier.realownsum }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">前30日采购金额：</td>
			    						<td><input type="text" name="buySupplier.buysummonth" value="${buySupplier.buysummonth }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/></td>
			    						<td style="width:120px;text-align: right;">最新付款日期：</td>
			    						<td><input type="text" name="buySupplier.disabledate" value="${buySupplier.disabledate }" style="width:130px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
			    					</tr>
			    				</table>
		    				</div>
    					</div>
   						<div style="clear:both"></div>
   					</div>
			    	<div class="tagsDiv_item">
	    				<table id="buy-buySupplierAddPage-contactor"></table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table id="buy-buySupplierAddPage-buySupplierDetailSortList"></table>
	    			</div>
	    			<div class="tagsDiv_item">			    			
	    				<table id="buy-buySupplierAddPage-buySupplierGoodsList"></table>
   					</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">    			
	    					<tr>
	    						<td style="width:100px;text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field01)}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
										<c:otherwise>自定义信息01</c:otherwise>
								</c:choose>
	    						：
	    						</td>
	    						<td style="width:165px;"><input type="text" name="buySupplier.field01" value="<c:out value="${buySupplier.field01 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="width:100px;text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field02)}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
										<c:otherwise>自定义信息02</c:otherwise>
								</c:choose>
	    						：
	    						</td>
	    						<td style="width:165px;"><input type="text" name="buySupplier.field02" value="<c:out value="${buySupplier.field02 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="width:100px;text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field03)}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
										<c:otherwise>自定义信息03</c:otherwise>
								</c:choose>
	    						：
								</td>
	    						<td style="width:165px;"><input type="text" name="buySupplier.field03" value="<c:out value="${buySupplier.field03 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field04)}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
										<c:otherwise>自定义信息04</c:otherwise>
								</c:choose>
	    						：
								</td>
	    						<td><input type="text" name="buySupplier.field04" value="<c:out value="${buySupplier.field04 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field05)}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
										<c:otherwise>自定义信息05</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td><input type="text" name="buySupplier.field05" value="<c:out value="${buySupplier.field05 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field06)}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
										<c:otherwise>自定义信息06</c:otherwise>
								</c:choose>
	    						：
								</td>
	    						<td><input type="text" name="buySupplier.field06" value="<c:out value="${buySupplier.field06 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field07)}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
										<c:otherwise>自定义信息07</c:otherwise>
								</c:choose>
	    						：
								</td>
	    						<td><input type="text" name="buySupplier.field07" value="<c:out value="${buySupplier.field07 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field08)}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
										<c:otherwise>自定义信息08</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td><input type="text" name="buySupplier.field08" value="<c:out value="${buySupplier.field08 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field09)}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
										<c:otherwise>自定义信息09</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td><input type="text" name="buySupplier.field09" value="<c:out value="${buySupplier.field09 }"></c:out>" style="width:130px;" class="easyui-validatebox" validType="maxByteLength[100]" maxlength="100"/></td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field10)}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
										<c:otherwise>自定义信息10</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td colspan="5" >
	    							<textarea name="buySupplier.field10" style="width:625px;height:50px;"><c:out value="${buySupplier.field10 }"></c:out></textarea>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field11)}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
										<c:otherwise>自定义信息11</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td colspan="5" >
	    							<textarea name="buySupplier.field11" style="width:625px;height:50px;"><c:out value="${buySupplier.field11 }"></c:out></textarea>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td style="text-align: right;">
	    						<c:choose>
										<c:when test="${not empty(fieldmap.field12)}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
										<c:otherwise>自定义信息12</c:otherwise>
								</c:choose>
	    						：</td>
	    						<td colspan="5" >
	    							<textarea name="buySupplier.field12" style="width:625px;height:50px;"><c:out value="${buySupplier.field12 }"></c:out></textarea>
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
	    			</div>
	    		</div>
		   	</div>
	    </form>
	</div>
	    <script type="text/javascript">
	    	var editIndex = undefined;
			var $sortList = $("#buy-buySupplierAddPage-buySupplierDetailSortList"); //分类datagrid的div对象
			function endEditing(){
		  		if (editIndex == undefined){
		  			return true
		  		}
		  		else{
		  			return false;
		  		}
			}
			function disabledSelectAdd(){ //添加行时判断默认分类选项是否可用
				var data = $sortList.datagrid('getRows');
				if(data.length == 0){return false}
				for(var i=0;i<data.length;i++){
					if(data[i]['isdefault'] == "1"){
						return true;
					}
				}
				return false;
			}
			function disabledSelectEdit(editIndex){ //修改行时判断默认分类选项是否可用
				var data = $sortList.datagrid('getRows');
				if(data[editIndex]['isdefault'] == "1"){
					$(".buySupplierSortDefault").removeAttr("disabled");
				}
				else{
					var bl = false;
					for(var i=0;i<data.length;i++){
						if(data[i]['isdefault'] == "1"){
							bl = true;
						}
					}
					if(bl){
						$(".buySupplierSortDefault").attr("disabled", "disabled");
					}
					else{
						$(".buySupplierSortDefault").removeAttr("disabled");
					}
				}
			}
	    	function selectTags(index){ //选择第index个选项
	   			$(".tags li").eq(index).click();
	    	}
	    	
	    	function buyuserReferWindow(deptid){
	    		//if(deptid==null){
	    		//	deptid=0;
	    		//}
	    		$("#buy-buySupplierAddPage-buyuserid").widget({
	    			name:'t_base_buy_supplier',
	    			col:'buyuserid',
	    			//param:[{field:'deptid',op:'equal',value:deptid}],
	    			width:130,
	        		async:false,
	    			singleSelect:true,
	    			onlyLeafCheck:false
	    		});
	    	}
	    	
	    	//所属部门
	    	function filialeReferWindow(deptid){
	    		//if(deptid==null){
	    		//	deptid=0;
	    		//}
	    		$("#buy-buySupplierAddPage-filiale").widget({ 
	    			name:'t_base_buy_supplier',
	    			col:'filiale',
	    			param:[{field:'id',op:'equal',value:deptid}],
	    			width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false
	    		});
	    	}
	    	
	    	$(document).ready(function(){

	    		$("#buy-buttons-buySupplierPage").buttonWidget("initButtonType", 'add');
		    	
				$("#buy-buySupplierAddPage-sortName").dblclick(function(){
	    			$("#buy-buySupplierAddPage-tabs").tabs("select","对应分类");
	    		});
	
				//$("#buy-id-buySupplierAddPage").blur(function(){
	    		//	validUsed("basefiles/isBuySupplierIdExist.do", $(this).val(), '', "该编号已被使用，请另输入编号！");
	    		//});
	
				$("#buy-buySupplierAddPage-nature").widget({
	    			name:'t_base_buy_supplier',
	    			col:'nature',
	    			width:130
	    		});
				$("#buy-buySupplierAddPage-buyarea").widget({ 
	    			name:'t_base_buy_supplier',
					col:'buyarea',
					width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false,
	    			onCheck: function(data, checked){
					}
	    		});		
				$("#buy-buySupplierAddPage-sortSelectId").widget({ 
	    			name:'t_base_buy_supplier',
					col:'suppliersort',
					width:150,
	    			singleSelect:true,
	    			onlyLeafCheck:false,
	    			onCheck: function(data, checked){
					}
	    		});	
				
				$("#buy-buySupplierAddPage-settletype").widget({ 
	    			name:'t_base_buy_supplier',
					col:'settletype',
					width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false,
	    			onCheck: function(data, checked){
					}
	    		});	
				$("#buy-buySupplierAddPage-paytype").widget({ 
	    			name:'t_base_buy_supplier',
					col:'paytype',
					width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false,
	    			onCheck: function(data, checked){
					}
	    		});	
	
				$("#buy-buySupplierAddPage-buydeptid").widget({ 
	    			name:'t_base_buy_supplier',
	    			col:'buydeptid',
	    			width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false,
	    			onSelect: function(data){
	    				if(data && data.id){
	    					//buyuserReferWindow(data.id);
	    					filialeReferWindow(data.pid);	
	    					//$("#buy-buySupplierAddPage-buyuserid").widget('clear');
	    				}else{
	    					//buyuserReferWindow(null);
	    					filialeReferWindow(null);		
	    					//$("#buy-buySupplierAddPage-buyuserid").widget('clear');
	    				}
	    			},
	    			onClear:function(){
	    				//buyuserReferWindow(null);
	    				filialeReferWindow(null);
	    				//$("#buy-buySupplierAddPage-buyuserid").widget('clear');		
	    			}
	    		});
				
				$("#buy-buySupplierAddPage-buyuserid").widget({
	    			name:'t_base_buy_supplier',
	    			col:'buyuserid',
	    			singleSelect:true,
	    			width:130,
	    			onChecked : function(data,checked){
	    			}
	    		});
	    		
	    		$("#buy-buySupplierAddPage-filiale").widget({ 
	    			name:'t_base_buy_supplier',
	    			col:'filiale',
	    			width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false
	    		});
		    	
		    	$("#buy-buySupplierAddPage-storageid").widget({ 
	    			name:'t_base_buy_supplier',
					col:'storageid',
					width:130,
	    			singleSelect:true,
	    			onlyLeafCheck:false
	    		});
		    	
		    	$("#buy-buySupplierAddPage-contactor").datagrid({
			 		singleSelect:true,
			 		//fit:true,
			 		rownumbers:true,
			 		border:false,
			 		idField:'id',
			 		url:"basefiles/getContacterListCustomer.do?type=2&id="+$("#buySupplier-oldid").val(),
			 		columns:[[
								{field:'id', title:'编码', width:80,
									formatter: function(value,row,index){
										return value+ "<input type='hidden' name='contacterId' value='"+value+"' />"
									}
								},
								{field:'name', title:'姓名', width:80},
								{field:'state', title:'状态', width:80},
								{field:'isdefault', title:'默认联系人', width:100,
									formatter: function(value,row,index){
										return "<select style='width:80px;' onchange=\"javascript:changeContacterDefault(this,'"+row.id+"','"+row.name+"');\" class='contacter-isdefault' name='contacterIsdefault'><option value='0' "+(value=="0"?"selected='selected'":"")+">否</option><option value='1' "+(value=="1"?"selected='selected'":"")+">是<option></select>"
									}
								},
								{field:'tel', title:'电话', width:80},
								{field:'fax', title:'传真', width:80},
								{field:'mobile', title:'手机号码', width:80},
								{field:'email', title:'邮箱', width:80},
								{field:'job', title:'职务名称', width:80}
					]]			    	
		    	});
		    	$("#buy-buySupplierAddPage-buySupplierDetailSortList").datagrid({
			 		singleSelect:true,
			 		//fit:true,
			 		rownumbers:true,
			 		border:false,
			 		idField:'suppliersort',
			 		url:"basefiles/showBuySupplierDetailSortList.do?supplierid="+$("#buySupplier-oldid").val(),
					columns:[[
								{field:'suppliersort',title:'供应商分类编码',width:200,
									formatter: function(value,row,index){
										return row.suppliersortname + "<input type='hidden' value='"+value+"' name='buySupplierDetailsort.suppliersort' />" + "<input type='hidden' value='"+row.suppliersortname+"' name='buySupplierDetailsort.suppliersortname' />";
									},
									editor:{
										type:'comborefer',
										options:{
											name:'t_base_buy_supplier',
											col:'suppliersort',
											singleSelect:true
										}
									}
								},
								{field:'isdefault',title:'是否默认分类',width:120,
									formatter: function(value,row,index){
										if(value == "0"){
											return "否 <input type='hidden' value='"+value+"' name='buySupplierDetailsort.isdefault' />";
										}
										if(value == "1"){
											return "是 <input type='hidden' value='"+value+"' name='buySupplierDetailsort.isdefault' />";
										}
									},
									editor:{
										type:'defaultSelect',
										options:{
											valueField:'value',  
			                                textField:'text',  
											class:'buySupplierSortDefault',
			                                data:[
			                                		{value:'1',text:'是'},
			                                		{value:'0',text:'否'}
			                                	]
										}
									}
								},
								{field:'remark',title:'备注',width:280,
									formatter: function(value,row,index){
										return value+"<input type='hidden' style='width:250px' name='buySupplierDetailsort.remark' value='"+value+"' />";
									},
									editor:{
										type:'validatebox'
									}
								}
					]],
					toolbar :[
								{
								    text : "添加", //添加新行
								    iconCls : "button-add",
								    handler : function() {
								    	if(endEditing()){
								    		if(disabledSelectAdd()){
								    			$sortList.datagrid('appendRow',{isdefault:'disabled'});
								    		}
								    		else{
								        		$sortList.datagrid('appendRow',{isdefault:'0'});
								        	}  
								            editIndex = $sortList.datagrid('getRows').length-1;  
								            $sortList.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
								        }
								    }
								},
					        	{
					        		text: "确定", //确定行编辑完成，并回写默认项
					        		iconCls: "button-save",
					        		handler: function(){
					        			if(endEditing()){return false;}
					        			var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'suppliersort'});
							            var suppliersortname = $(ed.target).widget("getText");
							   			$sortList.datagrid('getRows')[editIndex]['suppliersortname'] = suppliersortname;
							   			$sortList.datagrid('endEdit', editIndex);
							   			var data = $sortList.datagrid('getRows');
										var defaultIndex = -1;
										for(var i=0;i<data.length;i++){
											if(data[i]['isdefault'] == "1"){
												defaultIndex = i;
												$("#buy-buySupplierAddPage-sortName").val(data[i]['suppliersortname']);
												$("#buy-buySupplierAddPage-sort").val(data[i]['suppliersort']);
											}
										}
										if(defaultIndex == -1){
											$("#buy-buySupplierAddPage-sortName").val("");
											$("#buy-buySupplierAddPage-sort").val("");
										}
							   			editIndex = undefined;
							   			$sortList.datagrid('clearSelections');
					        		}
					        	},
					        	{
					        		text: "修改", //修改选中行
					        		iconCls: "button-edit",
					        		handler: function(){
					        			if(endEditing()){return false;}
							   			$sortList.datagrid('beginEdit', editIndex);
					        			disabledSelectEdit(editIndex);
					        		}
					        	},
					        	{
					        		text: "删除", //删除选中行
					        		iconCls: "button-delete",
					        		handler: function(){
					        			if(endEditing()){return false;}
							            $sortList.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
							   			editIndex = undefined;
					        		}
		        				}
					],
		        	onClickRow: function(rowIndex, rowData){ //选中行
		        		if(rowIndex != editIndex){
			        		if(endEditing()){
			        			$sortList.datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex); 
			        			editIndex = rowIndex;
			        			disabledSelectEdit(editIndex);
			        		}
			        		else{
			        			$sortList.datagrid('selectRow', editIndex);
			        		}
		        		}
		        	}
			    });
		    	$("#buy-buySupplierAddPage-buySupplierGoodsList").datagrid({
			 		singleSelect:true,
			 		//fit:true,
			 		rownumbers:true,
			 		border:false,
			 		idField:'id',
			 		//url:"basefiles/goodsInfoListPage.do?state=1&defaultsupplier="+$("#buySupplier-oldid").val(),
			 		columns:[[
								{field:'goodsid', title:'商品编码', width:100},
								{field:'name', title:'商品名称', width:100},
								{field:'brandName', title:'商品品牌', width:100},
								{field:'model', title:'规格型号', width:100},
								{field:'barcode', title:'条形码', width:80},
								{field:'remark', title:'备注', width:100}
					]]			    	
		    	});
		    	$("#buy-buySupplierAddPage-sortName").dblclick(function(){ //选择对应分类选项
	    			selectTags(3);
	    		});	
	    		$("#buy-buySupplierAddPage-contacterName").dblclick(function(){ //选择联系人选项
	    			selectTags(2);
	    		});
	    		$(".tags li").click(function(){ //选项选择事件
					var index = $(this).index();
					$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
					$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
					if(index==2){
						var height = $(window).height()-97;
						if(!$("#buy-buySupplierAddPage-contactor").hasClass("create-datagrid")){
							$("#buy-buySupplierAddPage-contactor").datagrid({
								height:height,
								border:false,
								idField:'id',
								singleSelect:true,
								rownumbers:true
							});
							$("#buy-buySupplierAddPage-contactor").addClass("create-datagrid");
						}
					}else if(index == 3){
						var height = $(window).height()-124;
						if(!$("#buy-buySupplierAddPage-buySupplierDetailSortList").hasClass("create-datagrid")){
							$("#buy-buySupplierAddPage-buySupplierDetailSortList").datagrid({
								height:height,
								border:false,
								idField:'suppliersort',
								singleSelect:true,
								rownumbers:true
							});
							$("#buy-buySupplierAddPage-buySupplierDetailSortList").addClass("create-datagrid");
						}
					}else if(index==4){
						var height = $(window).height()-97;
						if(!$("#buy-buySupplierAddPage-buySupplierGoodsList").hasClass("create-datagrid")){
							$("#buy-buySupplierAddPage-buySupplierGoodsList").datagrid({
								height:height,
								border:false,
								idField:'goodsid',
								singleSelect:true,
								rownumbers:true
							});
							$("#buy-buySupplierAddPage-buySupplierGoodsList").addClass("create-datagrid");
						}
					}
				});
	    	});
	    </script>
  </body>
</html>
