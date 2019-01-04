<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案新增快捷页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<form action="" method="post" id="sales-add-customerShortcut">
    		<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
    		<input type="hidden" name="customer.salesdeptname" id="customerShortcut-widget-salesdeptname"/>
		  	<input type="hidden" name="customer.salesusername" id="customerShortcut-widget-salesusername"/>
		    <input type="hidden" name="customer.tallyusername" id="customerShortcut-widget-tallyusername" />
		    <input type="hidden" id="sales-state-customerAddPage" name="customer.state"/>
		    <input type="hidden" id="sales-button-clicktype" value="0"/>
    		<div data-options="region:'center'">
 				<table cellpadding="1" cellspacing="1" border="0">
		    		<tr>
		    			<td width="110px" align="right">本级编码：</td>
	    				<td align="left"><input class="easyui-validatebox" name="customer.thisid" id="sales-thisId-customerAddPage" data-options="required:true,validType:'validLength'" style="width: 130px"/><font color="red">*</font></td>
	    				<td width="110px" align="right">上级客户：</td>
	    				<td align="left"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.id }"></c:out>" name="customer.pid" /><font color="red">*</font></td>
		    			<td width="110px" align="right">状态：</td>
		    			<td align="left"><select style="width: 136px;" disabled="disabled">
		    				<option value="4" selected="selected">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">编码：</td>
		    			<td align="left"><input id="sales-id-customerAddPage" name="customer.id" type="text" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">名称：</td>
		    			<td align="left" colspan="3"><input id="sales-name-customershortcut" name="customer.name" type="text" style="width: 400px" maxlength="100" class="easyui-validatebox" validType="validName[100]" data-options="required:true" /><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">简称：</td>
		    			<td align="left"><input type="text" name="customer.shortname" style="width: 130px" maxlength="50" class="easyui-validatebox" data-options="required:true"/><font color="red">*</font></td>
		    			<td align="right">名称拼音：</td>
						<td align="left"><input type="text" name="customer.pinyin" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">助记符：</td>
		    			<td align="left"><input name="customer.shortcode" type="text" style="width: 130px" maxlength="20" class="easyui-validatebox" data-options="required:true"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">是否总店：</td>
		    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option value="0">是</option>
				    		<option value="1" selected="selected">否</option>
				    	</select></td>
				    	<td align="right">店号：</td>
				    	<td align="left"><input type="text" name="customer.shopno" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">税号：</td>
				    	<td align="left"><input type="text" name="customer.taxno" maxlength="30" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
				    	<td align="right">开户银行：</td>
   						<td align="left"><input type="text" name="customer.bank" maxlength="50" style="width: 130px"/></td>
   						<td align="right">开户账号：</td>
   						<td align="left"><input type="text" name="customer.cardno" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">开户名：</td>
   						<td align="left"><input type="text" name="customer.caraccount" maxlength="30" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
   						<td align="right">注册资金：</td>
				    	<td align="left"><input type="text" name="customer.fund" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">门店面积m<sup>2</sup>：</td>
				    	<td align="left"><input type="text" name="customer.storearea" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">联系人：</td>
				    	<td align="left"><input type="text" name="customer.contactname" maxlength="20" style="width: 130px;"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">联系人电话:</td>
				    	<td align="left"><input type="text" name="customer.mobile" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">传真：</td>
				    	<td align="left"><input type="text" name="customer.faxno" class="easyui-validatebox" maxlength="20" validType="faxno" style="width: 130px"/></td>
		    			<td align="right">公司属性：</td>
				    	<td align="left"><select name="customer.nature" class="len136">
				    		<option></option>
				    		<c:forEach items="${natureList}" var="list">
				    			<option value="${list.code }">${list.codename }</option>
				    		</c:forEach>
				    	</select></td>
		    		</tr>
		    		<tr>
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
   						<td align="right">县级市：</td>
				    	<td align="left"><input type="text" name="customer.countylevel" maxlength="20" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
				    	<td align="right">乡镇：</td>
				    	<td align="left"><input type="text" name="customer.villagetown" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">详细地址：</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.address" maxlength="100" style="width: 400px"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">促销分类：</td>
   						<td align="left">
	    					<select name="customer.promotionsort" class="len136 easyui-validatebox" data-options="required:true">
	    						<option></option>
	    						<c:forEach items="${promotionsortList }" var="list">
				    				<option value="${list.code }">${list.codename }</option>
				    			</c:forEach>
	    					</select>
	    				</td>
	    				<td align="right">所属区域：</td>
		    			<td align="left"><input id="customerShortcut-widget-salesarea" name="customer.salesarea" type="text" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">所属分类：</td>
		    			<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" style="width: 130px"/></td>
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
		    			<td align="right">结算方式：</td>
		    			<td align="left"><input type="text" name="customer.settletype" id="customerShortcut-widget-settletype" style="width: 130px"/></td>
		    			<td align="right">结算日：</td>
		    			<td align="left"><select id="customer-select-settleday" name="customer.settleday" class="len136">
							<option></option>
							<c:forEach items="${dayList}" var="day">
							<option value="${day }">${day }</option>
							</c:forEach>
						</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">支付方式：</td>
				    	<td align="left"><input type="text" name="customer.paytype" id="customerShortcut-widget-paytype" style="width: 130px"/></td>
		    			<td align="right">是否现款：</td>
				    	<td align="left"><select name="customer.iscash" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option value="0">否</option>
				    		<option value="1">是</option>
				    	</select><font color="red">*</font></td>
				    	<td align="right">是否账期：</td>
				    	<td align="left"><select name="customer.islongterm" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option value="0">否</option>
				    		<option value="1">是</option>
				    	</select><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">超账期控制：</td>
   						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len136 easyui-validatebox" data-options="required:true" onchange="selectControl()">
   								<option></option>
   								<option value="1" selected="selected">是</option>
   								<option value="0">否</option>
   						</select><font color="red">*</font></td>
   						<td align="right">超账期宽限天数：</td>
   						<td align="left"><input type="text" id="customerShortcut-input-overgracedate" class="easyui-numberbox" data-options="max:999,precision:0" onchange="changeValue(this.value)" style="width: 130px"/>
   							<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate"/>
   						</td>
   						<td align="right">信用额度：</td>
				    	<td align="left"><input type="text" name="customer.credit" class="easyui-numberbox" data-options="max:999999999999,precision:0,required:true" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">信用等级：</td>
				    	<td align="right"><select name="customer.creditrating" class="len136 easyui-validatebox" data-options="required:true">
   								<option></option>
   								<c:forEach items="${creditratingList }" var="list">
				    				<option value="${list.code }">${list.codename }</option>
				    			</c:forEach>
   						</select><font color="red">*</font></td>
   						<td align="right">对账日期：</td>
				    	<td align="left"><input type="text" name="customer.reconciliationdate" style="width: 130px"/></td>
		    			<td align="right">开票日期：</td>
				    	<td align="left"><input type="text" name="customer.billingdate" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">到款日期：</td>
				    	<td align="left"><input type="text" name="customer.arrivalamountdate" style="width: 130px"/></td>
		    			<td align="right">票种：</td>
				    	<td align="left"><select name="customer.tickettype" class="len136">
				    		<option></option>
				    		<option value="1">增值税发票</option>
				    		<option value="2">普通发票</option>
				    	</select></td>
				    	<td align="right">核销方式：</td>
		    			<td align="left"><select name="customer.canceltype" class="len136 easyui-validatebox" data-options="required:true">
		    				<option></option>
							<c:forEach items="${canceltypeList }" var="list">
			    				<option value="${list.code }">${list.codename }</option>
			    			</c:forEach>
		    			</select><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认销售部门：</td>
		    			<td align="left"><input id="customerShortcut-widget-salesdeptid" name="customer.salesdeptid" type="text" style="width: 130px"/></td>
		    			<td align="right">默认业务员：</td>
		    			<td align="left"><input id="customerShortcut-widget-salesuserid" name="customer.salesuserid" type="text" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">默认理货员：</td>
		    			<td align="left"><input id="customerShortcut-widget-tallyuserid" name="customer.tallyuserid" type="text" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认内勤：</td>
		    			<td align="left"><input id="customerShortcut-widget-indoorstaff" name="customer.indoorstaff" type="text" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">收款人：</td>
				    	<td align="left"><input id="customerShortcut-widget-payeeid" name="customer.payeeid" type="text" style="width: 130px"/></td>
		    			<td align="right">默认价格套：</td>
		    			<td align="left"><select name="customer.pricesort" class="len136" data-options="required:true">
							<option></option>
							<c:forEach items="${priceList}" var="list">
							<option value="${list.code }">${list.codename }</option>
							</c:forEach>
						</select><font color="red">*</font></td>
					</tr>
					<tr>
		    			<td align="right">备注：</td>
		    			<td align="left" colspan="6"><textarea style="width: 650px;" rows="1" name="customer.remark"></textarea></td>
		    		</tr>
		    	</table>
	  		</div>
	  		<div data-options="region:'south'" style="height: 30px;" align="right">
	  			<a href="javaScript:void(0);" id="sales-save-customerShortcut" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
	  		</div>
	    </form>
     </div>
     <script type="text/javascript">
     	$(function(){
     		$("#sales-buttons-customershortcut").buttonWidget("initButtonType", 'add');
     		//检验商品档案数据（唯一性，最大长度等）
			$.extend($.fn.validatebox.defaults.rules, {
	   			validName:{//名称唯一性,最大长度
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = customerShortcut_AjaxConn({name:value},'basefiles/customerNameNoUsed.do');//true 重复，false 不重复
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
			validLengthAndUsed('${len}', "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
     		//上级客户
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
   						$("#sales-buttons-customershortcut").buttonWidget("disableButton", "button-hold");
   						$("#sales-buttons-customershortcut").buttonWidget("disableButton", "button-save");
   						return false;
   					}
   					else{
   						$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-hold");
   						$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-save");
   					}
   					validLengthAndUsed(customer_lenArr[(data.level + 1)], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			},
    			onClear: function(){
    				//$("#sales-id-customerAddPage").val($("#sales-thisId-customerAddPage").val());
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
    				//$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-hold");
    				//$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(customer_lenArr[0], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
		
			//所属区域
		  	$("#customerShortcut-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				required:true,
				onlyLeafCheck:true
			});
			
			//所属分类
			$("#customerShortcut-widget-customersort").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'customersort',
				singleSelect:true,
				required:true,
				onlyLeafCheck:false
			});
			
			//默认销售部门
			$("#customerShortcut-widget-salesdeptid").widget({ 
    			name:'t_base_sales_customer',
    			col:'salesdeptid',
    			width:130,
    			singleSelect:true,
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				$("#customerShortcut-widget-salesdeptname").val(data.name);
    				if(data && data.id){
    					salesuserReferWindow(data.id);
    					$("#customerShortcut-widget-salesuserid").widget('clear');
    				}else{
    					salesuserReferWindow(null,null);
    					$("#customerShortcut-widget-salesuserid").widget('clear');
    				}
    			},
    			onClear:function(){
    				$("#customerShortcut-widget-salesdeptname").val("");
    				salesuserReferWindow(null,null);
    				//$("#customerShortcut-widget-salesuserid").widget('clear');
    			}
    		});	
    		
    		salesuserReferWindow(null);
			
			//默认理货员
			$("#customerShortcut-widget-tallyuserid").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'tallyuserid',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#customerShortcut-widget-tallyusername").val(data.name);
				},
				onClear:function(){
					$("#customerShortcut-widget-tallyusername").val("");
				}
			});
			//默认内勤
			$("#customerShortcut-widget-indoorstaff").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			width:130,
    			required:true,
    			onlyLeafCheck:false
    		});
    		
    		//收款人
    		$("#customerShortcut-widget-payeeid").widget({ 
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
			//对方联系人
			$("#customerShortcut-widget-contact").widget({
				width:130,
				name:'t_base_sales_customer',
				col:'contact',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//支付方式
			$("#customerShortcut-widget-paytype").widget({
				width:130,
				name:'t_base_sales_customer',
				col:'paytype',
				singleSelect:true,
				onlyLeafCheck:false
			});
			//结算方式
			$("#customerShortcut-widget-settletype").widget({ //结算方式参照窗口
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
    		
    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
	   			$("#sales-id-customerAddPage").val($(this).val());
	   		});
     		
     		//确定按钮
     		$("#sales-save-customerShortcut").click(function(){
     			if(!$("#sales-add-customerShortcut").form('validate')){
     				return false;
     			}
     			$.messager.confirm("提醒","是否新增客户?",function(r){
					if(r){
						var clicktype = $("#sales-button-clicktype").val();
						if("1" == clicktype){
							$("#sales-state-customerAddPage").val("1");
						}
						loading("提交中..");
						var ret = customerShortcut_AjaxConn($("#sales-add-customerShortcut").serializeJSON(),'basefiles/addCustomerShortcut.do');
    					var retJson = $.parseJSON(ret);
    					if(retJson.flag){
							$.messager.alert("提醒","新增成功!");
							$("#sales-buttons-customershortcut").buttonWidget("addNewDataId",$("#sales-id-customerAddPage").val());
							if (top.$('#tt').tabs('exists',customer_title)){
			    				var queryJSON = tabsWindow(customer_title).$("#sales-queryForm-customerShortcut").serializeJSON();
			    				tabsWindow(customer_title).$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
			    			}
							$("#sales-panel-customershortcut").panel('refresh', 'basefiles/showCustomerShortcutViewPage.do?id='+$("#sales-id-customerAddPage").val());
						}
					}else{
						$("#sales-button-clicktype").val("0");
					}
				});
     		});
     	});
     	var customerShortcut_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
     	function salesuserReferWindow(deptid,initVal){
    		//if(deptid==null){
    		//	deptid=0;
    		//}
    		$("#customerShortcut-widget-salesuserid").widget({
    			name:'t_base_sales_customer',
    			col:'salesuserid',
    			param:[{field:'deptid',op:'equal',value:deptid}],
    			width:130,
    			async:false,
    			singleSelect:true,
    			required:true,
    			onlyLeafCheck:false,
    			initValue:initVal,
    			onSelect:function(data){
    				$("#customerShortcut-widget-salesusername").val(data.name);
    			},
				onClear:function(){
					$("#customerShortcut-widget-salesusername").val("");
				}
    		});
    	}
     </script>
  </body>
</html>
