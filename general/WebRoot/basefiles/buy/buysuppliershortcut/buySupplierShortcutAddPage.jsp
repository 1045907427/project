<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案新增快捷页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
	  	<div data-options="region:'center'">
			<form action="basefiles/addBuySupplierShortcut.do" method="post" id="buy-add-buySupplierShortcut">
		    	<input type="hidden" id="buy-detailsort-buySupplierShortcut" name="buySupplier.buySupplierDetailsort.suppliersort"/>
		   		<input type="hidden" id="buy-isdefault-buySupplierShortcut" name="buySupplier.buySupplierDetailsort.isdefault" value="1"/>
		    	<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80px" align="right">编码:</td>
		    			<td align="left"><input name="buySupplier.id" id="buy-id-buySupplierShortcut" type="text" style="width: 130px" maxlength="20" class="easyui-validatebox" validType="validId[20]" data-options="required:true" /><font color="red">*</font></td>
		    			<td width="90px" align="right">助记符:</td>
		    			<td align="left"><input name="buySupplier.spell" type="text" style="width: 130px" maxlength="20" <c:if test="${colMap.spell == 'spell' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.spell == 'spell' }"><font color="red">*</font></c:if></td>
		    			<td width="80px" align="right">状态:</td>
		    			<td align="left"><select style="width: 130px;" disabled="disabled" class="easyui-combobox">
                            <option></option>
                            <option value="4" selected="selected">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称:</td>
		    			<td align="left" colspan="3"><input id="buy-name-buySupplierShortcut" name="buySupplier.name" type="text" style="width: 363px" maxlength="100" class="easyui-validatebox" validType="validName[100]" data-options="required:true" /><font color="red">*</font></td>
		    			<td align="right">简称:</td>
		    			<td align="left"><input type="text" name="buySupplier.shortname" style="width: 130px" maxlength="50" <c:if test="${colMap.shortname == 'shortname' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.shortname == 'shortname' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
						<td align="left"><input type="text" id="buy-pinyin-buySupplierShortcut" name="buySupplier.pinyin" style="width: 130px;" maxlength="20" <c:if test="${colMap.pinyin == 'pinyin' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.pinyin == 'pinyin' }"><font color="red">*</font></c:if></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input type="text" name="buySupplier.taxno" style="width: 130px;"/></td>
		    			<td align="right">开户银行:</td>
						<td align="left"><input type="text" name="buySupplier.bank" maxlength="50" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">开户账号:</td>
						<td align="left"><input type="text" name="buySupplier.cardno" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">注册资金:</td>
		    			<td align="left"><input type="text" name="buySupplier.fund" style="width: 130px" class="easyui-numberbox" data-options="min:0,precision:0"/></td>
		    			<td align="right">法人代表:</td>
		    			<td align="left"><input type="text" name="buySupplier.person" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">法人代表电话:</td>
		    			<td align="left"><input type="text" name="buySupplier.personmobile" style="width: 130px"/></td>
		    			<td align="right">电话:</td>
					    <td align="left"><input type="text" name="buySupplier.telphone" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">传真:</td>
					    <td align="left"><input type="text" name="buySupplier.faxno" maxlength="20" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">邮箱:</td>
		    			<td align="left"><input type="text" name="buySupplier.email" class="easyui-validatebox" validType="email" style="width:130px;"/></td>
		    			<td align="right">邮编:</td>
  						<td align="left"><input type="text" name="buySupplier.zip" style="width:130px;"/></td>
  						<td align="right">网址:</td>
  						<td align="left"><input type="text" name="buySupplier.website" style="width:130px;"/></td>
		    		</tr>
		    		<tr>
  						<td align="right">员工人数:</td>
  						<td align="left"><input type="text" name="buySupplier.staffnum" style="width:130px;"/></td>
  						<td align="right">年产值:</td>
		    			<td align="left"><input type="text" name="buySupplier.turnoveryear" style="width:130px;"/></td>
		    			<td align="right">业务联系人:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactname" style="width: 130px" <c:if test="${colMap.contactname == 'contactname' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.contactname == 'contactname' }"><font color="red">*</font></c:if></td>
  					</tr>
  					<tr>
  						<td align="right">业务联系电话:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactmobile" style="width: 130px" <c:if test="${colMap.contactmobile == 'contactmobile' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.contactmobile == 'contactmobile' }"><font color="red">*</font></c:if></td>
		    			<td align="right">业务联系人邮箱:</td>
		    			<td align="left"><input type="text" name="buySupplier.contactemail" class="easyui-validatebox" validType="email" style="width: 130px"/></td>
		    			<td align="right">ABC等级:</td>
   						<td align="left"><select name="buySupplier.abclevel" class="easyui-combobox" style="width: 130px">
   								<option></option>
   								<option value="A">A</option>
   								<option value="B">B</option>
   								<option value="C">C</option>
   								<option value="D">D</option>
   							</select>
						</td>
  					</tr>
  					<tr>
		    			<td align="right">所属区域:</td>
   						<td align="left"><input type="text" id="buy-buySupplierAddPage-buyarea" name="buySupplier.buyarea"/></td>
   						<td align="right">默认分类:</td>
   						<td align="left"><input type="text" id="buy-buySupplierAddPage-suppliersort" name="buySupplier.suppliersort"/></td>
		    			<td align="right">默认仓库:</td>
					    <td align="left"><input type="text" id="buy-buySupplierAddPage-storageid" name="buySupplier.storageid"/><c:if test="${colMap.storageid == 'storageid' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购员:</td>
		    			<td align="left"><input id="buSupplierShortcut-widget-buyuserid" name="buySupplier.buyuserid" type="text" style="width: 130px"/><c:if test="${colMap.buyuserid == 'buyuserid' }"><font color="red">*</font></c:if></td>
		    			<td align="right">采购部门:</td>
		    			<td align="left"><input id="buSupplierShortcut-widget-buydeptid" name="buySupplier.buydeptid" type="text" style="width: 130px"/><c:if test="${colMap.buydeptid == 'buydeptid' }"><font color="red">*</font></c:if></td>
                        <td align="right">订单追加:</td>
                        <td align="left">
                            <select name="buySupplier.orderappend" class="easyui-combobox" <c:if test="${colMap.orderappend == 'orderappend' }">data-options="required:true"</c:if> style="width: 130px">
                                <option value="1" selected="selected">是</option>
                                <option value="0">否</option>
                            </select><c:if test="${colMap.orderappend == 'orderappend' }"><font color="red">*</font></c:if>
                        </td>
                    </tr>
		    		<tr>
	  					<td align="right">详细地址:</td>
		    			<td align="left" colspan="3"><input type="text" name="buySupplier.address" style="width: 368px" maxlength="200" <c:if test="${colMap.address == 'address' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.address == 'address' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注:</td>
			    		<td align="left" colspan="3"><input style="width: 368px;" rows="1" name="buySupplier.remark" /></td>
		    			<td align="right">月销售指标:</td>
			    		<td align="left"><input type="text" name="buySupplier.salesmonth" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">资金占用额度:</td>
			    		<td align="left"><input type="text" name="buySupplier.ownlimit" style="width:130px;" class="easyui-numberbox" data-options="precision:0"/></td>
	  					<td align="right">年度目标:</td>
		    			<td align="left"><input type="text" name="buySupplier.annualobjectives" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">年度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.annualrebate" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">半年度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.semiannualrebate" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  					<td align="right">季度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.quarterlyrebate" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
		    			<td align="right">月度返利%:</td>
		    			<td align="left"><input type="text" name="buySupplier.monthlyrebate" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
	  				<tr>
	  					<td align="right">破损补贴:</td>
		    			<td align="left"><input type="text" name="buySupplier.breakagesubsidies" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">其他费用补贴:</td>
		    			<td align="left"><input type="text" name="buySupplier.othersubsidies" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
		    			<td align="right">供价折扣率%:</td>
		    			<td align="left"><input type="text" name="buySupplier.pricediscount" class="easyui-numberbox" data-options="max:999,precision:2" style="width: 130px"/></td>
	  				</tr>
					<tr>
						<td align="right">结算方式:</td>
						<td align="left"><input type="text" name="buySupplier.settletype" id="buySupplierShortcut-widget-settletype" style="width: 130px"/></td>
						<td align="right">结算日:</td>
						<td align="left"><select id="supplier-select-settleday" name="buySupplier.settleday" style="width: 130px;">
							<option></option>
							<c:forEach items="${dayList}" var="day">
								<option value="${day }">${day }</option>
							</c:forEach>
						</select></td>
					</tr>
		    	</table>
	    	</form>
  		</div>
    	<div data-options="region:'south'" >
            <div class="buttonDetailBG" style="text-align:right;">
                <security:authorize url="/basefiles/buySupplierSavegoonBtn.do">
                    <input type="button" id="buy-savegoon-supplierShortcut" value="继续添加"/>
                </security:authorize>
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

     		$("#buy-name-buySupplierShortcut").change(function(){
    			var val = $("#buy-name-buySupplierShortcut").val();
    			if(val != ""){
    				var str = convertToFirstPinyinLower(val);
    				$("#buy-pinyin-buySupplierShortcut").val(str);
    			}else{
    				$("#buy-pinyin-buySupplierShortcut").val("");
    			}
    		});
     		
			//保存并继续添加
	   		$("#buy-savegoon-supplierShortcut").click(function(){
	   			if(!$("#buy-add-buySupplierShortcut").form('validate')){
	   				$.messager.alert("提醒","请填写必填项!");
    				return false;
    			}
				$.messager.confirm("提醒","是否保存并继续添加供应商?",function(r){
					if(r){
						supplier_savegoon_form_submit();
						$("#buy-add-buySupplierShortcut").submit();
					}
				});
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
