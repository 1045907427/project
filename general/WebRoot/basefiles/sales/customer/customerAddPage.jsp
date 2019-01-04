<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案添加页面</title>
  </head>
  
  <body>
  	<input type="hidden" id="customer-index-priceList" />
  	<input type="hidden" id="customer-index-sortList" />
  	<div id="customer-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
	  	<form action="basefiles/addCustomer.do" id="sales-form-customerAddPage" method="post">
	  		<input type="hidden" name="addType" id="sales-addType-customerAddPage" />
	  		<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
	  		<input type="hidden" name="customer.salesdeptname" id="sales-salesdeptname-customerAddPage"/>
	  		<input type="hidden" name="customer.salesusername" id="sales-salesusername-customerAddPage"/>
	    	<input type="hidden" name="customer.tallyusername" id="sales-tallyusername-customerAddPage" />
	    	<input type="hidden" id="sales-state-customerAddPage" name="customer.state"/>
	    	<input type="hidden" id="sales-button-clicktype" value="0"/>
	    	<div id="customer-layout-detail-north" data-options="region:'north',border:false" style="height:84px">
	    		<table style="border-collapse:collapse;" cellpadding="2" cellspacing="2" border="0">
	    			<tr>
	    				<td class="len100 right">本级编码：</td>
	    				<td class="len165"><input class="easyui-validatebox" name="customer.thisid" id="sales-thisId-customerAddPage" data-options="required:true,validType:'validLength'" style="width: 130px;"/></td>
	    				<td class="len100 right">上级客户：</td>
	    				<td class="len165"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.id }"></c:out>" name="customer.pid" /></td>
	    				<td class="len100 right">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len130"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="right">编码：</td>
	    				<td><input class="easyui-validatebox" readonly="readonly" name="customer.id" id="sales-id-customerAddPage" style="width: 130px;"/></td>
	    				<td class="right">名称：</td>
	    				<td colspan="3"><input class="easyui-validatebox" style="width:400px;" name="customer.name" required="required" maxlength="50" /></td>
	    			</tr>
	    		</table>
	    		<ul class="tags">
	    			<li class="selectTag"><a href="javascript:;">基本信息</a></li>
	    			<li><a href="javascript:;">控制信息</a></li>
	    			<li><a href="javascript:;">联系人信息</a></li>
	    			<li><a href="javascript:;">对应分类</a></li>
	    			<li><a href="javascript:;">自定义信息</a></li>
	    		</ul>
	    	</div>
	    	<div id="customer-layout-detail-center" data-options="region:'center',border:false">
	    		<div class="tagsDiv">
	    			<div class="tagsDiv_item" style="display:block;">
	    				<table id="sales-customer-tagsDiv0" style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    					<tr>
	    						<td style="width: 110px;" align="right">简称：</td>
	    						<td align="left"><input type="text" name="customer.shortname" maxlength="15" style="width: 130px;" class="easyui-validatebox" data-options="required:true"/></td>
	    						<td style="width: 110px;" align="right">助记码：</td>
	    						<td align="left"><input type="text" name="customer.shortcode" maxlength="10" style="width: 130px;" class="easyui-validatebox" data-options="required:true"/></td>
	    						<td style="width: 110px;" align="right">是否总店：</td>
				    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option value="0">是</option>
						    		<option value="1" selected="selected">否</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">名称拼音：</td>
								<td align="left"><input type="text" name="customer.pinyin" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">店号：</td>
	    						<td align="left"><input type="text" name="customer.shopno" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">税号：</td>
	    						<td align="left"><input type="text" name="customer.taxno" maxlength="30" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">开户银行：</td>
	    						<td align="left"><input type="text" name="customer.bank" maxlength="50" style="width: 130px;"/></td>
	    						<td align="right">开户账号：</td>
	    						<td align="left"><input type="text" name="customer.cardno" maxlength="30" style="width: 130px;"/></td>
	    						<td align="right">开户名：</td>
	    						<td align="left"><input type="text" name="customer.caraccount" maxlength="20" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">注册资金：</td>
	    						<td align="left"><input type="text" name="customer.fund" style="width: 130px;" class="easyui-numberbox" data-options="max:999999999999,precision:0" /></td>
	    						<td align="right">客户开户日期：</td>
	    						<td align="left"><input type="text" name="customer.setupdate" style="width: 130px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    						<td align="right">门店面积m<sup>2</sup>：</td>
	    						<td align="left"><input type="text" name="customer.storearea" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">法人代表：</td>
	    						<td align="left"><input type="text" name="customer.person" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">法人代表电话：</td>
	    						<td align="left"><input type="text" name="customer.personmobile" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">法人身份证号码：</td>
	    						<td align="left"><input type="text" name="customer.personcard" maxlength="20" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司属性：</td>
	    						<td align="left"><select name="customer.nature" class="len136">
	    								<option></option>
	    								<c:forEach items="${natureList }" var="list">
	    								<option value="${list.code }">${list.codename }</option>
	    								</c:forEach>
	    						</select></td>
	    						<td align="right">是否现款：</td>
	    						<td align="left"><select name="customer.iscash" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option value="0">否</option>
						    		<option value="1">是</option>
						    	</select></td>
	    						<td align="right">是否账期：</td>
	    						<td align="left"><select name="customer.islongterm" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option value="0">否</option>
						    		<option value="1">是</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司电话：</td>
	    						<td align="left"><input type="text" name="customer.telphone" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">邮编：</td>
	    						<td align="left"><input type="text" name="customer.zip" class="easyui-validatebox" maxlength="6" validType="zip" style="width: 130px;"/></td>
	    						<td align="right">邮箱：</td>
	    						<td align="left"><input type="text" name="customer.email" class="easyui-validatebox" maxlength="100" validType="email" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">网址：</td>
	    						<td align="left"><input type="text" name="customer.website" class="easyui-validatebox" maxlength="100" style="width: 130px;"/></td>
	    						<td align="right">员工人数：</td>
	    						<td align="left"><input type="text" name="customer.staffnum" maxlength="25" style="width: 130px;"/></td>
	    						<td align="right">年营业额：</td>
	    						<td align="left"><input type="text" name="customer.turnoveryear" class="easyui-validatebox" maxlength="24" validType="intOrFloat" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">初次业务时间：</td>
	    						<td align="left"><input type="text" name="customer.firstbusinessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;"/></td>
	    						<td align="right">联系人：</td>
	    						<td align="left"><input type="text" name="customer.contactname" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">联系人电话:</td>
	    						<td align="left"><input type="text" name="customer.mobile" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">传真：</td>
	    						<td align="left"><input type="text" name="customer.faxno" class="easyui-validatebox" maxlength="20" validType="faxno" style="width: 130px;"/></td>
	    						<td align="right">是否连锁：</td>
						    	<td align="left"><select name="customer.ischain" class="len136">
						    		<option></option>
						    		<option value="0" selected="selected">否</option>
						    		<option value="1">是</option>
						    	</select></td>
	    						<td align="right">ABC等级：</td>
	    						<td align="left">
	    							<select name="customer.abclevel" class="len136" data-options="required:true">
	    								<option></option>
	    								<option value="A">A</option>
	    								<option value="B">B</option>
	    								<option value="C">C</option>
	    								<option value="D">D</option>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td align="right">县级市：</td>
						    	<td align="left"><input type="text" name="customer.countylevel" maxlength="20" style="width: 130px"/></td>
				    			<td align="right">乡镇：</td>
						    	<td align="left"><input type="text" name="customer.villagetown" maxlength="20" style="width: 130px"/></td>
				    			<td align="right">详细地址：</td>
						    	<td align="left"><input type="text" name="customer.address" maxlength="100" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">促销分类：</td>
	    						<td align="left"><select name="customer.promotionsort" class="len136 easyui-validatebox" data-options="required:true">
		    						<option></option>
		    						<c:forEach items="${promotionsortList }" var="list">
					    				<option value="${list.code }">${list.codename }</option>
					    			</c:forEach>
			    				</select></td>
	    						<td align="right">所属区域：</td>
	    						<td align="left"><input type="text" id="sales-area-customerAddPage" name="customer.salesarea"/></td>
	    						<td align="right">默认分类：</td>
	    						<td align="left"><input type="text" readonly="readonly" id="sales-sortName-customerAddPage" class="easyui-validatebox" data-options="required:true"/>
	    							<input type="hidden" name="customer.customersort" id="sales-sort-customerAddPage" /></td>
	    					</tr>
	    					<tr>
				    			<td align="right">对账人：</td>
				    			<td align="left"><input type="text" name="customer.checker" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">对账人电话：</td>
				    			<td align="left"><input type="text" name="customer.checkmobile" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">对账人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.checkemail" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
				    		</tr>
				    		<tr>
	    						<td align="right">付款人：</td>
				    			<td align="left"><input type="text" name="customer.payer" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">付款人电话：</td>
				    			<td align="left"><input type="text" name="customer.payermobile" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">付款人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.payeremail" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
	    					</tr>
				    		<tr>
				    			<td align="right">店长：</td>
				    			<td align="left"><input type="text" name="customer.shopmanager" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">店长联系电话：</td>
				    			<td align="left"><input type="text" name="customer.shopmanagermobile" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">收货人：</td>
				    			<td align="left"><input type="text" name="customer.gsreceipter" maxlength="20" style="width: 130px;"/></td>
				    		</tr>
				    		<tr>
				    			<td align="right">收货人联系电话：</td>
				    			<td align="left"><input type="text" name="customer.gsreceiptermobile" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">备注：</td>
	    						<td align="left" colspan="4"><textarea style="width: 397px;" rows="1" name="customer.remark"></textarea></td>
				    		</tr>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item" >
	    				<table id="sales-customer-tagsDiv1" style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    					<tr>
	    						<td style="width: 110px;" align="right">信用额度：</td>
	    						<td align="left"><input type="text" name="customer.credit" class="easyui-numberbox" data-options="max:999999999999,precision:0,required:true" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right">信用等级：</td>
	    						<td align="left"><select name="customer.creditrating" class="len136 easyui-validatebox" data-options="required:true">
	    								<option></option>
	    								<c:forEach items="${creditratingList }" var="list">
						    				<option value="${list.code }">${list.codename }</option>
						    			</c:forEach>
	    						</select></td>
	    						<td style="width: 110px;" align="right">信用期限：</td>
	    						<td align="left"><input type="text" name="customer.creditdate" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">月销售指标：</td>
	    						<td align="left"><input type="text" name="customer.salesmonth" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px;"/></td>
	    						<td class="len100 right">对账日期：</td>
	    						<td align="left"><input type="text" name="customer.reconciliationdate" style="width: 130px;"/></td>
	    						<td class="len100 right">开票日期：</td>
	    						<td align="left"><input type="text" name="customer.billingdate" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">到款日期：</td>
	    						<td align="left"><input type="text" name="customer.arrivalamountdate" style="width: 130px;"/></td>
	    						<td align="right">票种：</td>
	    						<td align="left"><select name="customer.tickettype" class="len136">
	    								<option></option>
	    								<option value="1">增值税发票</option>
	    								<option value="2">普通发票</option>
	    						</select></td>
	    						<td align="right">目标销售：</td>
	    						<td align="left"><input type="text" name="customer.targetsales" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">年返%：</td>
	    						<td align="left"><input type="text" name="customer.yearback" class="easyui-numberbox" data-options="max:100,precision:2" style="width: 130px;"/></td>
	    						<td align="right">月返%：</td>
	    						<td align="left"><input type="text" name="customer.monthback" class="easyui-numberbox" data-options="max:100,precision:2" style="width: 130px;"/></td>
	    						<td align="right">配送费：</td>
	    						<td align="left"><input type="text" name="customer.dispatchingamount" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">六节一庆：</td>
	    						<td align="left"><input type="text" name="customer.sixone" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px;"/></td>
	    						<td align="right">是否结算：</td>
	    						<td><select name="customer.settlement" class="len136">
	    								<option></option>
	    								<option value="1">是</option>
	    								<option value="0">否</option>
	    						</select></td>
	    						<td align="right">结算方式：</td>
	    						<td><input type="text" name="customer.settletype" id="sales-settletype-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">结算日：</td>
	    						<td><select id="customer-select-settleday" name="customer.settleday" class="len136">
	    								<option></option>
	    								<c:forEach items="${dayList}" var="day">
	    								<option value="${day }">${day }</option>
	    								</c:forEach>
	    						</select></td>
	    						<td align="right">是否开票：</td>
	    						<td><select name="customer.billing" class="len136">
	    								<option></option>
	    								<option value="1">是</option>
	    								<option value="0">否</option>
	    						</select></td>
	    						<td align="right">应收依据：</td>
	    						<td><select name="customer.billtype" class="len136">
	    								<option></option>
	    								<option value="0">发票</option>
	    								<option value="1">发货</option>
	    								<option value="2">发票+发货</option>
	    						</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">超账期控制：</td>
	    						<td><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len136 easyui-validatebox" data-options="required:true" onchange="selectControl()">
	    								<option></option>
	    								<option value="1" selected="selected">是</option>
	    								<option value="0">否</option>
	    						</select></td>
	    						<td align="right">超账期宽限天数：</td>
	    						<td><input type="text" id="customerShortcut-input-overgracedate" class="easyui-validatebox" maxlength="3" validType="integer" onchange="changeValue(this.value)" style="width: 130px;"/>
	    							<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate"/>
	    						</td>
	    						<td align="right">支付方式：</td>
	    						<td><input type="text" name="customer.paytype" id="sales-paytype-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">核销方式：</td>
	    						<td><select name="customer.canceltype" class="len136 easyui-validatebox" data-options="required:true">
	    								<option></option>
	    								<c:forEach items="${canceltypeList }" var="list">
						    				<option value="${list.code }">${list.codename }</option>
						    			</c:forEach>
	    						</select></td>
	    						<td align="right">默认销售部门：</td>
	    						<td><input type="text" name="customer.salesdeptid" id="sales-salesdeptid-customerAddPage" /></td>
	    						<td align="right">默认业务员：</td>
	    						<td><input type="text" name="customer.salesuserid" id="sales-salesuserid-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">默认价格套：</td>
	    						<td><select name="customer.pricesort" class="len136" data-options="required:true">
	    							<option></option>
	    							<c:forEach items="${priceList}" var="list">
	    							<option value="${list.code }">${list.codename }</option>
	    							</c:forEach>
	    						</select></td>
	    						<td align="right">默认理货员：</td>
	    						<td><input type="text" name="customer.tallyuserid" id="sales-tallyuserid-customerAddPage" /></td>
	    						<td align="right" style="display:none;">对应供应商：</td>
	    						<td style="display:none;"><input type="text" name="customer.supplier" id="sales-supplier-customerAddPage" /></td>
	    						<td align="right">默认内勤：</td>
	    						<td><input type="text" name="customer.indoorstaff" id="sales-indoorstaff-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">收款人：</td>
	    						<td><input type="text" name="customer.payeeid" id="sales-payeeid-customerAddPage" /></td>
	    						<td align="right">计划毛利率%：</td>
	    						<td colspan="3"><input type="text" name="customer.margin" class="easyui-validatebox" maxlength="5" validType="intOrFloat" style="width: 130px;"/></td>
	    					</tr>
	    				</table>
	    				<div style="margin-top:10px;margin-left:10px;">
	    					<div class="easyui-panel" title="最新动态" style="height:160px;width:800px;">
		    					<table style="border-collapse:collapse;" border="0" cellpadding="2px" cellspacing="2px">
		    						<tr>
		    							<td align="right">累计销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">累计收款金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">其他应付金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">本年累计销售额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">本年累计收款额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">应收余额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新销售日期：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">最新销售金额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">最新收款日期：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新收款金额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">前30日销售金额：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    							<td align="right">禁用日期：</td>
		    							<td><input type="text" readonly="readonly" style="width: 130px;"/></td>
		    						</tr>
		    					</table>
		    				</div>
	    				</div>
	    			</div>
	    			<div class="tagsDiv_item"></div>
	    			<div class="tagsDiv_item">
	    				<table id="sales-sortList-customerAddPage"></table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2px" cellspacing="2px" id="sales-field-customerAddPage">
	    					<tr>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field01"></span></td>
	    						<td align="left"><input type="text" name="customer.field01" maxlength="50" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field02"></span></td>
	    						<td align="left"><input type="text" name="customer.field02" maxlength="50" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field03"></span></td>
	    						<td align="left"><input type="text" name="customer.field03" maxlength="50" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field04"></span></td>
	    						<td><input type="text" name="customer.field04" maxlength="50" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field05"></span></td>
	    						<td><input type="text" name="customer.field05" maxlength="50" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field06"></span></td>
	    						<td><input type="text" name="customer.field06" maxlength="50" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field07"></span></td>
	    						<td><input type="text" name="customer.field07" maxlength="50" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field08"></span></td>
	    						<td><input type="text" name="customer.field08" maxlength="50" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field09"></span></td>
	    						<td><input type="text" name="customer.field09" maxlength="50" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field10"></span></td>
	    						<td colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field10"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field11"></span></td>
	    						<td colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field11"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field12"></span></td>
	    						<td colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field12"></textarea></td>
	    					</tr>
	    				</table>
	    			</div>
	    		</div>
	    	</div>
    	</form>
	</div>
   	<script type="text/javascript">
   		$(function(){
    		validLengthAndUsed('${len}', "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				//$("#sales-id-customerAddPage").val(data.id + $("#sales-thisId-customerAddPage").val());
   					$("#sales-parentId-customerAddPage").val(data.id);
   					$("#sales-thisId-customer").val(data.id);
   					$("#sales-parentId-customer").val(data.pid);
   					var hasLevel = $("#sales-hasLevel-customerSort").val();
   					if((data.level+1)==hasLevel){
   						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
   						$("#sales-buttons-customer").buttonWidget("disableButton", "button-hold");
   						$("#sales-buttons-customer").buttonWidget("disableButton", "button-save");
   						return false;
   					}
   					else{
   						$("#sales-buttons-customer").buttonWidget("enableButton", "button-hold");
   						$("#sales-buttons-customer").buttonWidget("enableButton", "button-save");
   					}
   					validLengthAndUsed(customer_lenArr[(data.level + 1)], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			},
    			onClear: function(){
    				//$("#sales-id-customerAddPage").val($("#sales-thisId-customerAddPage").val());
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-hold");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(customer_lenArr[0], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#sales-area-customerAddPage").widget({ //销售区域参照窗口
    			name:'t_base_sales_customer',
				col:'salesarea',
    			singleSelect:true,
    			width:128,
    			required:true,
    			onlyLeafCheck:true
    		});
    		$("#sales-settletype-customerAddPage").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
    			singleSelect:true,
    			required:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect:function(data){
    				if(data.type == '1'){//月结
    					$("#customer-select-settleday").combobox({
    						required:true
    					});
    				}else{
    					$("#customer-select-settleday").combobox({
    						required:false
    					});
    				}
    			}
    		});
    		$("#sales-paytype-customerAddPage").widget({ //支付方式参照窗口
    			name:'t_base_sales_customer',
				col:'paytype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		$("#sales-salesdeptid-customerAddPage").widget({ //销售部门参照窗口
    			name:'t_base_sales_customer',
				col:'salesdeptid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect:function(data){
    				$("#sales-salesdeptname-customerAddPage").val(data.name);
    				$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
			    		name:'t_base_sales_customer',
			    		col:'salesuserid',
			    		singleSelect:true,
			    		width:130,
			    		required:true,
			    		onlyLeafCheck:true,
			    		param:[{field:'deptid',op:'equal',value:data.id}],
			    		onSelect:function(data){
			    			$("#sales-salesusername-customerAddPage").val(data.name);
			    		},
			    		onClear:function(){
			    			$("#sales-salesusername-customerAddPage").val("");
			    		}
			    	});
    			},
    			onClear:function(){
	    			$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
			    		name:'t_base_sales_customer',
			    		col:'salesuserid',
			    		singleSelect:true,
			    		width:130,
			    		required:true,
			    		onlyLeafCheck:true,
			    		onSelect:function(data){
			    			$("#sales-salesusername-customerAddPage").val(data.name);
			    		},
			    		onClear:function(){
			    			$("#sales-salesusername-customerAddPage").val("");
			    		}
			    	});
	    		}
    		});
    		
    		//默认业务员
    		$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
	    		name:'t_base_sales_customer',
	    		col:'salesuserid',
	    		singleSelect:true,
	    		width:130,
	    		required:true,
	    		onlyLeafCheck:true,
	    		onSelect:function(data){
	    			$("#sales-salesusername-customerAddPage").val(data.name);
	    		},
	    		onClear:function(){
	    			$("#sales-salesusername-customerAddPage").val("");
	    		}
	    	});
    		
    		$("#sales-tallyuserid-customerAddPage").widget({ //理货员参照窗口
    			name:'t_base_sales_customer',
				col:'tallyuserid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect:function(data){
    				$("#sales-tallyusername-customerAddPage").val(data.name);
    			}
    		});
    		
    		$("#sales-indoorstaff-customerAddPage").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			width:130,
    			required:true,
    			onlyLeafCheck:false
    		});
    		
    		$("#sales-payeeid-customerAddPage").widget({ //收款人
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		$("#sales-supplier-customerAddPage").widget({ //供应商参照窗口
    			name:'t_base_sales_customer',
				col:'supplier',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onLoadSuccess:function(){
    				$("#sales-supplier-customerAddPage").widget('disable');
    			}
    		});
    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
    			$("#sales-id-customerAddPage").val($(this).val());
    		});
			$("#sales-buttons-customer").buttonWidget("initButtonType", 'add'); //按钮添加风格
			$.ajax({ //获取自定义字段的名称，数据来源数据字典
				url:'basefiles/customerDIYFieldName.do',
				dataType:'json',
				type:'post',
				success:function(json){
					$("#sales-field-customerAddPage").find(".field-customer").each(function(){
						var rel = $(this).attr("rel");
						$(this).text(json[rel] + "：");
					});
				}
			});
    		$("#sales-sortName-customerAddPage").dblclick(function(){ //选择对应分类选项
    			selectTags(3);
    		});	
    		$("#sales-contacterName-customerAddPage").dblclick(function(){ //选择联系人选项
    			selectTags(2);
    		});
    		$(".tags li").click(function(){ //选项选择事件
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 3){
					var height = $(window).height()-146;
					if(!$sortList.hasClass("create-datagrid")){
						$sortList.datagrid({ //客户分类行编辑
							height:height,
							border:false,
							idField:'sortid',
							singleSelect:true,
							rownumbers:true,
							columns:[[
										{field:'sortid',title:'客户档案分类',width:150,
											formatter: function(value,row,index){
												return row.sortname + "<input type='hidden' value='"+value+"' name='customerAndSort.sortid' />" + "<input type='hidden' value='"+row.sortname+"' name='customerAndSort.sortname' />";
											},
											editor:{
												type:'comborefer',
												options:{
													name:'t_base_sales_customer',
													col:'customersort',
													singleSelect:true,
													width:110,
													required:true
												}
											}
										},
										{field:'defaultsort',title:'是否默认分类',width:120,
											formatter: function(value,row,index){
												if(value == "0"){
													return "否 <input type='hidden' value='"+value+"' name='customerAndSort.defaultsort' />";
												}
												if(value == "1"){
													return "是 <input type='hidden' value='"+value+"' name='customerAndSort.defaultsort' />";
												}
											},
											editor:{
												type:'defaultSelect',
												options:{
													valueField:'value',  
					                                textField:'text',  
													classid:'customerSortDefault',
					                                data:[
					                                		{value:'1',text:'是'},
					                                		{value:'0',text:'否'}
					                                	]
												}
											}
										},
										{field:'remark',title:'备注',width:280,
											formatter: function(value, row, index){
												if(null == value){
													value = "";
												}
												return value + "<input type='hidden' value='"+value+"' name='customerAndSort.remark' />";
											},
											editor:{
												type:'validatebox'
											}
										}
									]],
							toolbar : [{
				                text : "添加", //添加新行
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditing()){
				                		if(disabledSelectAdd()){
				                			$sortList.datagrid('appendRow',{defaultsort:'disabled'});
				                		}
				                		else{
					                		$sortList.datagrid('appendRow',{defaultsort:'0'});
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
				        			if(endEditing()){return}
				        			if(!$sortList.datagrid('validateRow', editIndex)){return}
				        			var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'sortid'});
						            var sortname = $(ed.target).widget("getText");
						   			$sortList.datagrid('getRows')[editIndex]['sortname'] = sortname;
						   			$sortList.datagrid('endEdit', editIndex);
						   			var data = $sortList.datagrid('getRows');
									var defaultIndex = -1;
									for(var i=0;i<data.length;i++){
										if(data[i]['defaultsort'] == "1"){
											defaultIndex = i;
											$("#sales-sortName-customerAddPage").val(data[i]['sortname']);
    										$("#sales-sort-customerAddPage").val(data[i]['sortid']);
										}
									}
									if(defaultIndex == -1){
										$("#sales-sortName-customerAddPage").val("");
    									$("#sales-sort-customerAddPage").val("");
									}
						   			editIndex = undefined;
						   			$sortList.datagrid('clearSelections');
				        		}
				        	},
				        	{
				        		text: "修改", //修改选中行
				        		iconCls: "button-edit",
				        		handler: function(){
				        			var row = $sortList.datagrid('getSelected');
						            editIndex = $sortList.datagrid('getRowIndex', row);
				        			if(endEditing()){return}
				        			$("#customer-index-sortList").val(editIndex);
				        			var rowData = $sortList.datagrid('getSelected', editIndex);
						   			$sortList.datagrid('beginEdit', editIndex);
				        			disabledSelectEdit(editIndex);
				        		}
				        	},
				        	{
				        		text: "删除", //删除选中行
				        		iconCls: "button-delete",
				        		handler: function(){
				        			if(endEditing()){return}
						            $sortList.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
						   			editIndex = undefined;
				        		}
				        	}],
				        	onClickRow: function(rowIndex, rowData){ //选中行
				        		if(!$sortList.datagrid('validateRow', editIndex)){
				        			$sortList.datagrid('selectRow',editIndex);
				        			return false;
				        		}
				        		var index = parseInt($("#customer-index-sortList").val());
			   					if(!endEditing() && $sortList.datagrid('validateRow', index)){
			   						var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'sortid'});
						            var sortname = $(ed.target).widget("getText");
						   			$sortList.datagrid('getRows')[editIndex]['sortname'] = sortname;
						   			$sortList.datagrid('endEdit', editIndex);
						   			var data = $sortList.datagrid('getRows');
									var defaultIndex = -1;
									for(var i=0;i<data.length;i++){
										if(data[i]['defaultsort'] == "1"){
											defaultIndex = i;
											$("#sales-sortName-customerAddPage").val(data[i]['sortname']);
    										$("#sales-sort-customerAddPage").val(data[i]['sortid']);
										}
									}
									if(defaultIndex == -1){
										$("#sales-sortName-customerAddPage").val("");
    									$("#sales-sort-customerAddPage").val("");
									}
			   					}
			   					$sortList.datagrid('endEdit', editIndex);
						   		editIndex = undefined;
				        	}
						});
						$sortList.addClass("create-datagrid")
					}
				}
			});
   		});
		var editIndex = undefined;
		var priceEditIndex = undefined;
		var $sortList = $("#sales-sortList-customerAddPage"); //分类datagrid的div对象
		var $priceList = $("#sales-priceList-customerAddPage");//合同价datagrid的对象
		function endEditing(){
	  		if (editIndex == undefined){
	  			return true
	  		}
	  		else{
	  			return false;
	  		}
		}
		function priceEndEditing(){
			if (priceEditIndex == undefined){
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
				if(data[i]['defaultsort'] == "1"){
					return true;
				}
			}
			return false;
		}
		function disabledSelectEdit(editIndex){ //修改行时判断默认分类选项是否可用
			var data = $sortList.datagrid('getRows');
			if(data[editIndex]['defaultsort'] == "1"){
				$(".customerSortDefault").removeAttr("disabled");
			}
			else{
				var bl = false;
				for(var i=0;i<data.length;i++){
					if(data[i]['defaultsort'] == "1"){
						bl = true;
					}
				}
				if(bl){
					$(".customerSortDefault").attr("disabled", "disabled");
				}
				else{
					$(".customerSortDefault").removeAttr("disabled");
				}
			}
		}
   		function selectTags(index){ //选择第index个选项
   			$(".tags li").eq(index).click();
   		}
   	</script>
  </body>
</html>
