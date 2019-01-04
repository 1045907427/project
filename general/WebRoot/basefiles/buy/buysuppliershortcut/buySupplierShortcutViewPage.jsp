<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案查看快捷页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center'">
	  		<form action="" method="post" id="buy-add-buySupplierShortcut">
	    		<input type="hidden" id="buySupplier-oldid" name="buySupplier.oldid" value="<c:out value="${buySupplier.id }"></c:out>"/>
		   		<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80px" align="right">编码:</td>
		    			<td align="left"><input readonly="readonly" name="buySupplier.id" value="<c:out value="${buySupplier.id }"></c:out>" id="buy-id-buySupplierShortcut" type="text" style="width: 130px" maxlength="20"/><font color="red">*</font></td>
		    			<td width="90px" align="right">助记符:</td>
		    			<td align="left"><input readonly="readonly" name="buySupplier.spell" value="<c:out value="${buySupplier.spell }"></c:out>" type="text" style="width: 130px" maxlength="20"/><c:if test="${colMap.spell == 'spell' }"><font color="red">*</font></c:if></td>
		    			<td width="80px" align="right">状态:</td>
		    			<td align="left"><select style="width: 130px;" disabled="disabled" class="easyui-combobox">
		    				<c:forEach items="${stateList }" var="list">
			    				<c:choose>
			    					<c:when test="${list.code == buySupplier.state }">
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
		    			<td align="right">名称:</td>
		    			<td align="left" colspan="3"><input readonly="readonly" id="buy-name-buySupplierShortcut" name="buySupplier.name" value="<c:out value="${buySupplier.name }"></c:out>" type="text" style="width: 365px" maxlength="100"/><font color="red">*</font></td>
		    			<td align="right">简称:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.shortname" value="<c:out value="${buySupplier.shortname }"></c:out>" style="width: 130px" maxlength="50"/><c:if test="${colMap.shortname == 'shortname' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
						<td align="left"><input readonly="readonly" type="text" id="buy-pinyin-buySupplierShortcut" name="buySupplier.pinyin" value="<c:out value="${buySupplier.pinyin }"></c:out>" style="width: 130px;" maxlength="20"/><c:if test="${colMap.pinyin == 'pinyin' }"><font color="red">*</font></c:if></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input readonly="readonly" type="text" name="buySupplier.taxno" value="<c:out value="${buySupplier.taxno }"></c:out>" style="width: 130px;"/></td>
		    			<td align="right">开户银行:</td>
						<td align="left"><input readonly="readonly" type="text" name="buySupplier.bank" value="<c:out value="${buySupplier.bank }"></c:out>" maxlength="50" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">开户账号:</td>
						<td align="left"><input readonly="readonly" type="text" name="buySupplier.cardno" value="<c:out value="${buySupplier.cardno }"></c:out>" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">注册资金:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.fund" value="<c:out value="${buySupplier.fund }"></c:out>" style="width: 130px" class="easyui-numberbox" data-options="min:0,precision:0"/></td>
		    			<td align="right">法人代表:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.person" value="<c:out value="${buySupplier.person }"></c:out>" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">法人代表电话:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.personmobile" value="<c:out value="${buySupplier.personmobile }"></c:out>" style="width: 130px"/></td>
		    			<td align="right">电话:</td>
					    <td align="left"><input readonly="readonly" type="text" name="buySupplier.telphone" value="<c:out value="${buySupplier.telphone }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">传真:</td>
					    <td align="left"><input readonly="readonly" type="text" name="buySupplier.faxno" value="<c:out value="${buySupplier.faxno }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">邮箱:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.email" value="<c:out value="${buySupplier.email }"></c:out>" class="easyui-validatebox" validType="email" style="width:130px;"/></td>
		    			<td align="right">邮编:</td>
  						<td align="left"><input readonly="readonly" type="text" name="buySupplier.zip" value="<c:out value="${buySupplier.zip }"></c:out>" style="width:130px;"/></td>
  						<td align="right">网址:</td>
  						<td align="left"><input readonly="readonly" type="text" name="buySupplier.website" value="<c:out value="${buySupplier.website }"></c:out>" style="width:130px;"/></td>
		    		</tr>
		    		<tr>
  						<td align="right">员工人数:</td>
  						<td align="left"><input readonly="readonly" type="text" name="buySupplier.staffnum" value="<c:out value="${buySupplier.staffnum}"></c:out>" style="width:130px;"/></td>
  						<td align="right">年产值:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.turnoveryear" value="<c:out value="${buySupplier.turnoveryear }"></c:out>" style="width:130px;"/></td>
		    			<td align="right">业务联系人:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.contactname" value="<c:out value="${buySupplier.contactname }"></c:out>" style="width: 130px"/><c:if test="${colMap.contactname == 'contactname' }"><font color="red">*</font></c:if></td>
  					</tr>
  					<tr>
  						<td align="right">业务联系电话:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.contactmobile" value="<c:out value="${buySupplier.contactmobile }"></c:out>" style="width: 130px"/><c:if test="${colMap.contactmobile == 'contactmobile' }"><font color="red">*</font></c:if></td>
		    			<td align="right">业务联系人邮箱:</td>
		    			<td align="left"><input readonly="readonly" type="text" name="buySupplier.contactemail" value="<c:out value="${buySupplier.contactemail }"></c:out>" class="easyui-validatebox" validType="email" style="width: 130px"/></td>
		    			<td align="right">ABC等级:</td>
   						<td align="left"><select disabled="disabled" name="buySupplier.abclevel" class="easyui-combobox" style="width: 130px">
   								<option></option>
   								<option <c:if test="${buySupplier.abclevel == 'A'}">selected="selected"</c:if> value="A">A</option>
   								<option <c:if test="${buySupplier.abclevel == 'B'}">selected="selected"</c:if> value="B">B</option>
   								<option <c:if test="${buySupplier.abclevel == 'C'}">selected="selected"</c:if> value="C">C</option>
   								<option <c:if test="${buySupplier.abclevel == 'D'}">selected="selected"</c:if> value="D">D</option>
   							</select>
						</td>
  					</tr>
  					<tr>
		    			<td align="right">所属区域:</td>
   						<td align="left"><input type="text" readonly="readonly" type="text" id="buy-buySupplierAddPage-buyarea" name="buySupplier.buyarea" value="<c:out value="${buySupplier.buyarea }"></c:out>"/></td>
   						<td align="right">默认分类:</td>
   						<td align="left"><input type="text" readonly="readonly" type="text" id="buy-buySupplierAddPage-suppliersort" name="buySupplier.suppliersort" value="<c:out value="${buySupplier.suppliersort }"></c:out>"/></td>
		    			<td align="right">默认仓库:</td>
					    <td align="left"><input type="text" readonly="readonly" type="text" id="buy-buySupplierAddPage-storageid" name="buySupplier.storageid" value="<c:out value="${buySupplier.storageid }"></c:out>"/><c:if test="${colMap.storageid == 'storageid' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购员:</td>
		    			<td align="left"><input type="text" readonly="readonly" id="buSupplierShortcut-widget-buyuserid" name="buySupplier.buyuserid" value="<c:out value="${buySupplier.buyuserid }"></c:out>"/><c:if test="${colMap.buyuserid == 'buyuserid' }"><font color="red">*</font></c:if></td>
		    			<td align="right">采购部门:</td>
		    			<td align="left"><input type="text" readonly="readonly" id="buSupplierShortcut-widget-buydeptid" name="buySupplier.buydeptid" value="<c:out value="${buySupplier.buydeptid }"></c:out>"/><c:if test="${colMap.buydeptid == 'buydeptid' }"><font color="red">*</font></c:if></td>
                        <td align="right">订单追加:</td>
                        <td align="left">
                            <select disabled="disabled" name="buySupplier.orderappend" class="easyui-combobox" style="width: 130px">
                                <option value="1" <c:if test="${buySupplier.orderappend == '1' }">selected="selected"</c:if>>是</option>
                                <option value="0" <c:if test="${buySupplier.orderappend == '0' }">selected="selected"</c:if>>否</option>
                            </select><c:if test="${colMap.orderappend == 'orderappend' }"><font color="red">*</font></c:if>
                        </td>
                    </tr>
		    		<tr>
	  					<td align="right">详细地址:</td>
		    			<td align="left" colspan="3"><input readonly="readonly" type="text" name="buySupplier.address" value="<c:out value="${buySupplier.address }"></c:out>" style="width: 365px" maxlength="200"/><c:if test="${colMap.address == 'address' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注:</td>
			    		<td align="left" colspan="3"><input readonly="readonly" style="width: 365px;" name="buySupplier.remark" value="<c:out value="${buySupplier.remark }"></c:out>"/></td>
		    			<td align="right">月销售指标:</td>
			    		<td align="left"><input type="text" readonly="readonly" name="buySupplier.salesmonth" value="${buySupplier.salesmonth }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">资金占用额度:</td>
			    		<td align="left"><input type="text" readonly="readonly" name="buySupplier.ownlimit" value="${buySupplier.ownlimit }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
	  					<td align="right">年度目标:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.annualobjectives" value="${buySupplier.annualobjectives }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">年度返利%:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.annualrebate" value="${buySupplier.annualrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">半年度返利%:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.semiannualrebate" value="${buySupplier.semiannualrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  					<td align="right">季度返利%:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.quarterlyrebate" value="${buySupplier.quarterlyrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
		    			<td align="right">月度返利%:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.monthlyrebate" value="${buySupplier.monthlyrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">破损补贴:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.breakagesubsidies" value="${buySupplier.breakagesubsidies }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">其他费用补贴:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.othersubsidies" value="${buySupplier.othersubsidies }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">供价折扣率%:</td>
		    			<td align="left"><input type="text" readonly="readonly" name="buySupplier.pricediscount" value="${buySupplier.pricediscount }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
					<tr>
						<td align="right">结算方式:</td>
						<td align="left"><input type="text" name="buySupplier.settletype" id="buySupplierShortcut-widget-settletype" value="${buySupplier.settletype }" readonly="readonly" style="width: 130px"/></td>
						<td align="right">结算日:</td>
						<td align="left"><select id="supplier-select-settleday" name="buySupplier.settleday" style="width: 130px;" disabled="disabled">
							<option></option>
							<c:forEach items="${dayList}" var="day">
								<c:choose>
									<c:when test="${day == buySupplier.settleday}"><option value="${day }" selected="selected">${day }</option></c:when>
									<c:otherwise><option value="${day }">${day }</option></c:otherwise>
								</c:choose>
							</c:forEach>
						</select></td>
					</tr>
		    	</table>
	   		</form>
	   	</div>
	   	<div data-options="region:'south'">
            <div class="buttonDetailBG" style="text-align:right;">
                <input type="button" id="buy-close-supplierShortcut" value="关闭"/>
            </div>
		</div>
     </div>
     <script type="text/javascript">
     	$(function(){
     		loadDropdown();
     		
     		//关闭
	   		$("#buy-close-supplierShortcut").click(function(){
	   			$("#buy-dialog-supplierListPage1").dialog('close');
	   		});
     	});
     </script>
  </body>
</html>
