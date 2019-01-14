<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>客户档案查看页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true" id="sales-layout-customerAddPage">
  		<div data-options="region:'center',border:true">
  			<form action="" method="post" id="customersimplify-form-add">
		    	<input type="hidden" name="customer.salesdeptname" value="<c:out value="${customer.salesdeptname }"></c:out>" id="customerShortcut-widget-salesdeptname"/>
			  	<input type="hidden" name="customer.salesusername" value="<c:out value="${customer.salesusername }"></c:out>" id="customerShortcut-widget-salesusername"/>
			    <input type="hidden" name="customer.tallyusername" value="<c:out value="${customer.tallyusername }"></c:out>" id="customerShortcut-widget-tallyusername" />
			    <input type="hidden" value="${customer.state }" id="customerShortcut-hd-state" />
			    <input type="hidden" id="customer-oldid" name="customer.oldid" value="<c:out value="${customer.id}"></c:out>"/>
			    <input type="hidden" id="sales-button-clicktype" value="0"/>
			    <input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
		    	<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80px" align="right">本级编码:</td>
		   				<td align="left"><input name="customer.thisid" value="<c:out value="${customer.thisid }"></c:out>" id="sales-thisId-customerAddPage" style="width: 130px;" readonly="readonly"/><font color="red">*</font></td>
		   				<td width="80px" align="right">上级客户:</td>
		   				<td align="left"><input id="sales-parent-customerAddPage" name="customer.pid" value="<c:out value="${customer.pid }"></c:out>" style="width: 130px;" readonly="readonly"/></td>
		    			<td width="80" align="right">状态:</td>
		    			<td align="left"><select style="width: 130px;" disabled="disabled">
		   						<c:forEach items="${stateList }" var="list">
				    				<c:choose>
				    					<c:when test="${list.code == customer.state }">
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
		    			<td align="right">编码:</td>
		    			<td align="left"><input id="sales-id-customerAddPage" name="customer.id" value="<c:out value="${customer.id }"></c:out>" type="text" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">名称:</td>
		    			<td align="left" colspan="3"><input id="sales-name-customershortcut" name="customer.name" value="<c:out value="${customer.name }"></c:out>" type="text" style="width: 352px" maxlength="100" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">是否总店:</td>
		    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" style="width: 130px;" disabled="disabled">
				    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
					    	<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
				    	</select><font color="red">*</font></td>
		    			<td align="right">助记符:</td>
				    	<td align="left"><input name="customer.shortcode" value="<c:out value="${customer.shortcode }"></c:out>" type="text" style="width: 130px" maxlength="20" readonly="readonly" /><c:if test="${colMap.shortcode == 'shortcode'}"><font color="red">*</font></c:if></td>
		    			<td align="right">简称:</td>
				    	<td align="left"><input type="text" name="customer.shortname" value="<c:out value="${customer.shortname }"></c:out>" style="width: 130px" maxlength="50" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">店号：</td>
	    				<td align="left"><input type="text" name="customer.shopno" value="<c:out value="${customer.shopno }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input type="text" name="customer.taxno" value="<c:out value="${customer.taxno }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">开户银行:</td>
				    	<td align="left"><input type="text" name="customer.bank" value="<c:out value="${customer.bank }"></c:out>" maxlength="50" style="width: 130px" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">开户账号:</td>
				    	<td align="left"><input type="text" name="customer.cardno" value="<c:out value="${customer.cardno }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">开户名:</td>
				    	<td align="left"><input type="text" name="customer.caraccount" value="<c:out value="${customer.caraccount }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">注册资金:</td>
				    	<td align="left"><input type="text" name="customer.fund" value="${customer.fund }" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">门店面积m<sup>2</sup>:</td>
				    	<td align="left"><input type="text" name="customer.storearea" value="${customer.storearea }" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0" readonly="readonly"/></td>
		    			<td align="right">联系人:</td>
				    	<td align="left"><input type="text" name="customer.contact" value="<c:out value="${customer.contact }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
		    			<td align="right">联系人电话:</td>
				    	<td align="left"><input type="text" name="customer.mobile" value="<c:out value="${customer.mobile }"></c:out>" style="width: 130px" readonly="readonly"/></td>
				    </tr>
		    		<tr>
				    	<td align="right">传真:</td>
				    	<td align="left"><input type="text" name="customer.faxno" value="${customer.faxno }" class="easyui-validatebox" maxlength="20" validType="faxno" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">详细地址:</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.address" value="<c:out value="${customer.address }"></c:out>" maxlength="100" style="width: 352px" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">促销分类:</td>
				    	<td align="left"><select name="customer.promotionsort" style="width: 130px;" disabled="disabled">
			    						<option></option>
		    						<c:forEach items="${promotionsortList }" var="list">
					    				<c:choose>
					    					<c:when test="${list.code == customer.promotionsort }">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    					</c:otherwise>
					    				</c:choose>
					    			</c:forEach></select><c:if test="${colMap.promotionsort == 'promotionsort' }"><font color="red">*</font></c:if></td>
		    			<td align="right">所属区域:</td>
				    	<td align="left"><input id="customerShortcut-widget-salesarea" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">所属分类:</td>
				    	<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" style="width: 130px" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">收款人:</td>
				    	<td align="left"><input id="customerShortcut-widget-payeeid" type="text" name="customer.payeeid" value="<c:out value="${customer.payeeid }"></c:out>" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">默认销售部门:</td>
				    	<td align="left"><input id="customerShortcut-widget-salesdeptid" name="customer.salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><c:if test="${colMap.salesdeptid == 'salesdeptid'}"><font color="red">*</font></c:if></td>
		    			<td align="right">默认业务员:</td>
				    	<td align="left"><input id="customerShortcut-widget-salesuserid" name="customer.salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认理货员:</td>
		    			<td align="left"><input id="customerShortcut-widget-tallyuserid" name="customer.tallyuserid" value="<c:out value="${customer.tallyuserid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">默认内勤:</td>
				    	<td align="left"><input id="customerShortcut-widget-indoorstaff" name="customer.indoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">结算方式:</td>
				    	<td align="left"><input id="customerShortcut-widget-settletype" name="customer.settletype" value="<c:out value="${customer.settletype }"></c:out>" type="text"  style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">结算日:</td>
				    	<td align="left"><select id="customer-select-settleday" name="customer.settleday" style="width: 130px;" disabled="disabled">
									<option></option>
									<c:forEach items="${dayList}" var="day">
										<c:choose>
										<c:when test="${day == customer.settleday}"><option value="${day }" selected="selected">${day }</option></c:when>
										<c:otherwise><option value="${day }">${day }</option></c:otherwise>
										</c:choose>
									</c:forEach>
								</select></td>
						<td align="right">超账期控制：</td>
						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" style="width: 130px;"  disabled="disabled">
							<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
							<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
						</select></td>
		    			<td align="right">是否账期:</td>
				    	<td align="left"><select name="customer.islongterm" style="width: 130px;" disabled="disabled">
						    		<option></option>
						    		<option <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if> value="0">否</option>
					    			<option <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if> value="1">是</option>
						    	</select><c:if test="${colMap.islongterm == 'islongterm' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">信用额度:</td>
				    	<td align="left"><input type="text" name="customer.credit" value="${customer.credit }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">信用等级:</td>
				    	<td align="left"><select name="customer.creditrating" style="width: 130px;" disabled="disabled">
		   								<option></option>
		   								<c:forEach items="${creditratingList }" var="list">
							   				<c:choose>
							   					<c:when test="${list.code == customer.creditrating }">
							   						<option value="${list.code }" selected="selected">${list.codename }</option>
							   					</c:when>
							   					<c:otherwise>
							   						<option value="${list.code }">${list.codename }</option>
							   					</c:otherwise>
							   				</c:choose>
							   			</c:forEach>
		   						</select><c:if test="${colMap.creditrating == 'creditrating'}"><font color="red">*</font></c:if></td>
		    			<td align="right">核销方式:</td>
				    	<td align="left"><select name="customer.canceltype" style="width: 130px;" disabled="disabled">
				    				<option></option>
									<c:forEach items="${canceltypeList }" var="list">
										<c:choose>
					    					<c:when test="${list.code == customer.canceltype }">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    					</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
				    			</select><c:if test="${colMap.canceltype == 'canceltype'}"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认价格套:</td>
				    	<td align="left"><select name="customer.pricesort" style="width: 130px;" disabled="disabled">
									<option></option>
									<c:forEach items="${priceList }" var="list">
					    				<c:choose>
					    					<c:when test="${list.code == customer.pricesort }">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    					</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
								</select><font color="red">*</font></td>
		    			<td align="right">备注:</td>
				    	<td align="left" colspan="5"><input type="text" name="customer.remark" value="<c:out value="${customer.remark }"></c:out>" maxlength="100" style="width: 352px" readonly="readonly"/></td>
		    		</tr>
		    	</table>
		    </form>
  		</div>
  		<div data-options="region:'south'">
  			<div class="buttonDetailBG" style="text-align:right;">
	  			<input type="button" name="savegoon" id="sales-close-customerAddPage" value="关闭"/>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
     		//上级客户
			$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			view: true
    		});
     		//所属区域
		  	$("#customerShortcut-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				onlyLeafCheck:true
			});
			
			//所属分类
			$("#customerShortcut-widget-customersort").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'customersort',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认销售部门
			$("#customerShortcut-widget-salesdeptid").widget({ 
    			name:'t_base_sales_customer',
    			col:'salesdeptid',
				<c:if test="${colMap.salesdeptid == 'salesdeptid' }">required:true,</c:if>
    			width:130,
    			singleSelect:true,
    			setValueSelect:false,
    			onlyLeafCheck:false
    		});	
    		//默认业务员
    		$("#customerShortcut-widget-salesuserid").widget({
    			name:'t_base_sales_customer',
    			col:'salesuserid',
    			width:130,
    			async:false,
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
			
			//默认理货员
			$("#customerShortcut-widget-tallyuserid").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'tallyuserid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认内勤
			$("#customerShortcut-widget-indoorstaff").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		//付款人
			$("#customerShortcut-widget-payeeid").widget({ 
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
			//结算方式
			$("#customerShortcut-widget-settletype").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		//关闭
	   		$("#sales-close-customerAddPage").click(function(){
	   			$("#sales-dialog-customerListPage1").dialog('close');
                $('#sales-dialog-customer').dialog('close');
	   		});
    	});
    	
    </script>
  </body>
</html>
