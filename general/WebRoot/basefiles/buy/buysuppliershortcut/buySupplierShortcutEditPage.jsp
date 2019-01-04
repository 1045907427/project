<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案修改快捷页面</title>
  </head>
  
  <body>
  	<input type="hidden" id="buySupplier-buydeptid" value="<c:out value="${buySupplier.buydeptid}"></c:out>"/>
  	<input type="hidden" id="buySupplier-filiale" value="<c:out value="${buySupplier.filiale}"></c:out>"/>
  	<input type="hidden" id="buySupplier-buyuserid" value="<c:out value="${buySupplier.buyuserid}"></c:out>"/>
  	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
			<form action="basefiles/editBuySupplierShortcut.do" method="post" id="buy-add-buySupplierShortcut">
	    		<input type="hidden" id="buySupplier-oldid" name="buySupplier.oldid" value="<c:out value="${buySupplier.id }"></c:out>"/>
		  		<input type="hidden" name="buySupplier.state" value="${buySupplier.state }"/>
		  		<input type="hidden" id="buySupplierShortcut-name" value="<c:out value="${buySupplier.name }"></c:out>"/>
		   		<input type="hidden" id="buy-detailsort-buySupplierShortcut" name="buySupplier.buySupplierDetailsort.suppliersort" value="${buySupplier.suppliersort }"/>
		   		<input type="hidden" id="buy-isdefault-buySupplierShortcut" name="buySupplier.buySupplierDetailsort.isdefault" value="1"/>
		   		<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80px" align="right">编码:</td>
		    			<td align="left"><input name="buySupplier.id" value="<c:out value="${buySupplier.id }"></c:out>" id="buy-id-buySupplierShortcut" type="text" style="width: 130px" maxlength="20" class="easyui-validatebox" validType="validId[20]" data-options="required:true" <c:if test="${editFlag == false }">readonly="readonly"</c:if>/><font color="red">*</font></td>
		    			<td width="90px" align="right">助记符:</td>
		    			<td align="left"><input name="buySupplier.spell" value="<c:out value="${buySupplier.spell }"></c:out>" type="text" style="width: 130px" maxlength="20" <c:if test="${colMap.spell == 'spell' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.spell == 'spell' }"><font color="red">*</font></c:if></td>
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
		    			<td align="left" colspan="3"><input id="buy-name-buySupplierShortcut" name="buySupplier.name" value="<c:out value="${buySupplier.name }"></c:out>" type="text" style="width: 363px" maxlength="100" class="easyui-validatebox" validType="validName[100]" data-options="required:true" /><font color="red">*</font></td>
		    			<td align="right">简称:</td>
		    			<td align="left"><input type="text" name="buySupplier.shortname" value="<c:out value="${buySupplier.shortname }"></c:out>" style="width: 130px" maxlength="50" <c:if test="${colMap.shortname == 'shortname' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.shortname == 'shortname' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
						<td align="left"><input type="text" id="buy-pinyin-buySupplierShortcut" name="buySupplier.pinyin" value="<c:out value="${buySupplier.pinyin }"></c:out>" style="width: 130px;" maxlength="20" <c:if test="${colMap.pinyin == 'pinyin' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.pinyin == 'pinyin' }"><font color="red">*</font></c:if></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input type="text" name="buySupplier.taxno" value="<c:out value="${buySupplier.taxno }"></c:out>" style="width: 130px;"/></td>
		    			<td align="right">开户银行:</td>
						<td align="left"><input type="text" name="buySupplier.bank" value="<c:out value="${buySupplier.bank }"></c:out>" maxlength="50" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">开户账号:</td>
						<td align="left"><input type="text" name="buySupplier.cardno" value="<c:out value="${buySupplier.cardno }"></c:out>" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">注册资金:</td>
		    			<td align="left"><input type="text" name="buySupplier.fund" value="<c:out value="${buySupplier.fund }"></c:out>" style="width: 130px" class="easyui-numberbox" data-options="min:0,precision:0"/></td>
		    			<td align="right">法人代表:</td>
		    			<td align="left"><input type="text" name="buySupplier.person" value="<c:out value="${buySupplier.person }"></c:out>" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">法人代表电话:</td>
		    			<td align="left"><input type="text" name="buySupplier.personmobile" value="<c:out value="${buySupplier.personmobile }"></c:out>" style="width: 130px"/></td>
		    			<td align="right">电话:</td>
					    <td align="left"><input type="text" name="buySupplier.telphone" value="<c:out value="${buySupplier.telphone }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">传真:</td>
					    <td align="left"><input type="text" name="buySupplier.faxno" value="<c:out value="${buySupplier.faxno }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">邮箱:</td>
		    			<td align="left"><input type="text" name="buySupplier.email" value="<c:out value="${buySupplier.email }"></c:out>" class="easyui-validatebox" validType="email" style="width:130px;"/></td>
		    			<td align="right">邮编:</td>
  						<td align="left"><input type="text" name="buySupplier.zip" value="<c:out value="${buySupplier.zip }"></c:out>" style="width:130px;"/></td>
  						<td align="right">网址:</td>
  						<td align="left"><input type="text" name="buySupplier.website" value="<c:out value="${buySupplier.website }"></c:out>" style="width:130px;"/></td>
		    		</tr>
		    		<tr>
  						<td align="right">员工人数:</td>
  						<td align="left"><input type="text" name="buySupplier.staffnum" value="<c:out value="${buySupplier.staffnum }"></c:out>" style="width:130px;"/></td>
  						<td align="right">年产值:</td>
		    			<td align="left"><input type="text" name="buySupplier.turnoveryear" value="<c:out value="${buySupplier.turnoveryear }"></c:out>" style="width:130px;"/></td>
		    			<td align="right">业务联系人:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactname" value="<c:out value="${buySupplier.contactname }"></c:out>" style="width: 130px" <c:if test="${colMap.contactname == 'contactname' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.contactname == 'contactname' }"><font color="red">*</font></c:if></td>
  					</tr>
  					<tr>
  						<td align="right">业务联系电话:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactmobile" value="<c:out value="${buySupplier.contactmobile }"></c:out>" style="width: 130px" <c:if test="${colMap.contactmobile == 'contactmobile' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.contactmobile == 'contactmobile' }"><font color="red">*</font></c:if></td>
		    			<td align="right">业务联系人邮箱:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactemail" value="<c:out value="${buySupplier.contactemail }"></c:out>" class="easyui-validatebox" validType="email" style="width: 130px"/></td>
		    			<td align="right">ABC等级:</td>
   						<td align="left"><select name="buySupplier.abclevel" class="easyui-combobox" style="width: 130px">
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
   						<td align="left"><input type="text" id="buy-buySupplierAddPage-buyarea" name="buySupplier.buyarea" value="<c:out value="${buySupplier.buyarea }"></c:out>"/></td>
   						<td align="right">默认分类:</td>
   						<td align="left"><input type="text" id="buy-buySupplierAddPage-suppliersort" name="buySupplier.suppliersort" value="<c:out value="${buySupplier.suppliersort }"></c:out>"/></td>
		    			<td align="right">默认仓库:</td>
					    <td align="left"><input type="text" id="buy-buySupplierAddPage-storageid" name="buySupplier.storageid" value="<c:out value="${buySupplier.storageid }"></c:out>"/><c:if test="${colMap.storageid == 'storageid' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购员:</td>
		    			<td align="left"><input id="buSupplierShortcut-widget-buyuserid" name="buySupplier.buyuserid" value="<c:out value="${buySupplier.buyuserid }"></c:out>" type="text" style="width: 130px"/><c:if test="${colMap.buyuserid == 'buyuserid' }"><font color="red">*</font></c:if></td>
		    			<td align="right">采购部门:</td>
		    			<td align="left"><input id="buSupplierShortcut-widget-buydeptid" name="buySupplier.buydeptid" value="<c:out value="${buySupplier.buydeptid }"></c:out>" type="text" style="width: 130px"/><c:if test="${colMap.buydeptid == 'buydeptid' }"><font color="red">*</font></c:if></td>
                        <td align="right">订单追加:</td>
                        <td align="left">
                            <select name="buySupplier.orderappend" class="easyui-combobox" <c:if test="${colMap.orderappend == 'orderappend' }">data-options="required:true"</c:if> style="width: 130px">
                                <option value="1" <c:if test="${buySupplier.orderappend == '1' }">selected="selected"</c:if>>是</option>
                                <option value="0" <c:if test="${buySupplier.orderappend == '0' }">selected="selected"</c:if>>否</option>
                            </select><c:if test="${colMap.orderappend == 'orderappend' }"><font color="red">*</font></c:if>
                        </td>
                    </tr>
		    		<tr>

	  					<td align="right">详细地址:</td>
		    			<td align="left" colspan="3"><input type="text" name="buySupplier.address" value="<c:out value="${buySupplier.address }"></c:out>" style="width: 363px" maxlength="200" <c:if test="${colMap.address == 'address' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.address == 'address' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注:</td>
			    		<td align="left" colspan="3"><input style="width: 363px;" rows="1" name="buySupplier.remark" value="<c:out value="${buySupplier.remark }"></c:out>"/></td>
		    			<td align="right">月销售指标:</td>
			    		<td align="left"><input type="text" name="buySupplier.salesmonth" value="${buySupplier.salesmonth }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">资金占用额度:</td>
			    		<td align="left"><input type="text" name="buySupplier.ownlimit" value="${buySupplier.ownlimit }" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
	  					<td align="right">年度目标:</td>
		    			<td align="left"><input type="text" name="buySupplier.annualobjectives" value="${buySupplier.annualobjectives }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">年度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.annualrebate" value="${buySupplier.annualrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">半年度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.semiannualrebate" value="${buySupplier.semiannualrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  					<td align="right">季度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.quarterlyrebate" value="${buySupplier.quarterlyrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
		    			<td align="right">月度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.monthlyrebate" value="${buySupplier.monthlyrebate }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">破损补贴:</td>
		    			<td align="left"><input type="text" name="buySupplier.breakagesubsidies" value="${buySupplier.breakagesubsidies }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">其他费用补贴:</td>
		    			<td align="left"><input type="text" name="buySupplier.othersubsidies" value="${buySupplier.othersubsidies }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">供价折扣率%:</td>
		    			<td align="left"><input type="text" name="buySupplier.pricediscount" value="${buySupplier.pricediscount }" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
					<tr>
						<td align="right">结算方式:</td>
						<td align="left"><input type="text" name="buySupplier.settletype" id="buySupplierShortcut-widget-settletype" value="${buySupplier.settletype }" style="width: 130px"/></td>
						<td align="right">结算日:</td>
						<td align="left"><select id="supplier-select-settleday" name="buySupplier.settleday" <c:if test="${monthorday == '2' }">disabled="disabled" </c:if><c:if test="${monthorday == '1' }">class="easyui-validatebox" data-options="required:true"</c:if> style="width: 130px;">
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
                <security:authorize url="/basefiles/buySupplierSaveBtn.do">
                    <input type="button" id="buy-save-supplierShortcut" value="保存"/>
                </security:authorize>
                <input type="button" id="buy-close-supplierShortcut" value="关闭"/>
            </div>
		</div>
     </div>
     <script type="text/javascript">
     	$(function(){
     		loadDropdown();
     		//检验商品档案数据（唯一性，最大长度等）
			$.extend($.fn.validatebox.defaults.rules, {
	   			validId:{//编号唯一性,最大长度
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						if($("#buySupplier-oldid").val() == $("#buy-id-buySupplierShortcut").val()){
	   							return true;
	   						}
	   						var ret = buySupplierShortcut_AjaxConn({id:value},'basefiles/isBuySupplierIdExist.do');//true 重复，false 不重复
	   						var retJson = $.parseJSON(ret);
	   						if(retJson.flag){
	   							$.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
	   							return false;
	   						}
	   					}
	   					else{
	   						$.fn.validatebox.defaults.rules.validId.message = '最多可输入{0}个字符!';
							return false;
	   					}
	   					return true;
	   				},
	   				message:''
	   			},
	   			validName:{//名称唯一性,最大长度
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						if($("#buySupplierShortcut-name").val() == $("#buy-name-buySupplierShortcut").val()){
	   							return true;
	   						}
	   						var ret = buySupplierShortcut_AjaxConn({name:value},'basefiles/isBuySupplierNameExist.do');//true 重复，false 不重复
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
	   				message:''
	   			}
			});
     		
     		//保存
	   		$("#buy-save-supplierShortcut").click(function(){
	   			if(!$("#buy-add-buySupplierShortcut").form('validate')){
	   				$.messager.alert("提醒","请填写必填项!");
    				return false;
    			}
				$.messager.confirm("提醒","是否保存供应商?",function(r){
					if(r){
						supplier_save_form_submit();
						$("#buy-add-buySupplierShortcut").submit();
					}
				});
	   		});
	   		//关闭
	   		$("#buy-close-supplierShortcut").click(function(){
	   			$("#buy-dialog-supplierListPage1").dialog('close');
	   		});
     	});
     </script>
  </body>
</html>
