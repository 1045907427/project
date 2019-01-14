<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>客户档案新增页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true" id="sales-layout-customerAddPage">
  		<div data-options="region:'center',split:true,border:true">
  			<form action="basefiles/addCustomerShortcut.do" method="post" id="customersimplify-form-add">
		    	<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
		   		<input type="hidden" name="customer.salesdeptname" id="customerShortcut-widget-salesdeptname"/>
			  	<input type="hidden" name="customer.salesusername" id="customerShortcut-widget-salesusername"/>
			    <input type="hidden" name="customer.tallyusername" id="customerShortcut-widget-tallyusername" />
			    <input type="hidden" id="sales-state-customerAddPage" name="customer.state"/>
		    	<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate"/>
		    	<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="90px" align="right">本级编码:</td>
		   				<td align="left"><input class="easyui-validatebox" name="customer.thisid" id="sales-thisId-customerAddPage" data-options="required:true,validType:'validLength'" style="width: 130px;" /><font color="red">*</font></td>
		   				<td width="80px" align="right">上级客户:</td>
		   				<td align="left"><input id="sales-parent-customerAddPage" value="<c:out value="${customer.id }"></c:out>" name="customer.pid" style="width: 130px;"/></td>
		    			<td width="80" align="right">状态:</td>
		    			<td align="left"><select style="width: 130px" disabled="disabled">
		    				<option value="4">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">编码:</td>
		    			<td align="left"><input id="sales-id-customerAddPage" name="customer.id" type="text" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">名称:</td>
		    			<td align="left" colspan="3"><input id="sales-name-customershortcut" name="customer.name" type="text" style="width: 352px" maxlength="100" class="easyui-validatebox" validType="validName[100]" data-options="required:true" /><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">是否总店:</td>
		    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="easyui-validatebox" data-options="required:true" style="width: 130px;">
				    		<option value="0">是</option>
				    		<option value="1" selected="selected">否</option>
				    	</select><font color="red">*</font></td>
						<td align="right">所属区域:</td>
						<td align="left"><input id="customerShortcut-widget-salesarea" name="customer.salesarea" value="<c:out value="${area }"></c:out>" type="text" style="width: 130px"/><font color="red">*</font></td>
						<td align="right">所属分类:</td>
						<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" value="<c:out value="${sort }"></c:out>" style="width: 130px"/></td>

					</tr>
		    		<tr>
		    			<td align="right">店号:</td>
	    				<td align="left"><input type="text" id="sales-shopno-customerAddPage" name="customer.shopno" maxlength="20" style="width: 130px;" class="easyui-validatebox" data-options="validType:'repeatshopno'"/></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input type="text" name="customer.taxno" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">开户银行:</td>
				    	<td align="left"><input type="text" name="customer.bank" maxlength="50" style="width: 130px"/></td>
		    		</tr>

		    		<tr>
		    			<td align="right">门店面积m<sup>2</sup>:</td>
				    	<td align="left"><input type="text" name="customer.storearea" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">联系人:</td>
				    	<td align="left"><input type="text" name="customer.contact" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">联系人电话:</td>
				    	<td align="left"><input type="text" name="customer.mobile" style="width: 130px"/></td>
				    </tr>
		    		<tr>
						<td align="right">核销方式:</td>
						<td align="left"><select name="customer.canceltype" <c:if test="${colMap.canceltype == 'canceltype'}">class="easyui-validatebox" data-options="required:true"</c:if> style="width: 130px;">
							<option></option>
							<c:forEach items="${canceltypeList }" var="list">
								<option value="${list.code }">${list.codename }</option>
							</c:forEach>
						</select><c:if test="${colMap.canceltype == 'canceltype'}"><font color="red">*</font></c:if></td>
		    			<td align="right">详细地址:</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.address" maxlength="100" style="width: 352px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">结算日:</td>
				    	<td align="left"><select id="customer-select-settleday" name="customer.settleday" style="width: 130px;">
									<option></option>
									<c:forEach items="${dayList}" var="day">
										<option value="${day }">${day }</option>
									</c:forEach>
								</select></td>
						<td align="right">超账期控制：</td>
						<td><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" style="width: 130px;">
							<option value="1">是</option>
							<option value="0" selected="selected">否</option>
						</select></td>
		    			<td align="right">是否账期:</td>
				    	<td align="left"><select id="customer-combobox-islongterm" name="customer.islongterm" style="width: 130px;">
						    		<option></option>
						    		<option value="0">否</option>
						    		<option value="1">是</option>
						    	</select><c:if test="${colMap.islongterm == 'islongterm' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认价格套:</td>
				    	<td align="left"><select name="customer.pricesort" class="easyui-validatebox" data-options="required:true" style="width: 130px;">
							<option></option>
							<c:forEach items="${priceList}" var="list">
							<option value="${list.code }">${list.codename }</option>
							</c:forEach>
						</select><font color="red">*</font></td>
		    			<td align="right">备注:</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.remark" maxlength="100" style="width: 352px"/></td>
		    		</tr>
		    	</table>
		    </form>
  		</div>
  		<div data-options="region:'south'" >
  			<div class="buttonDetailBG" style="text-align:right;">
  				<security:authorize url="/basefiles/customerSimplifySavegoon.do">
  					<input type="button" name="savegoon" id="sales-savegoon-customerAddPage" value="保存并继续添加"/>
  				</security:authorize>
    			<security:authorize url="/basefiles/customerSimplifySave.do">
    				<input type="button" name="savegoon" id="sales-save-customerAddPage" value="保存"/>
	  			</security:authorize>
   				<input type="button" name="savegoon" id="sales-close-customerAddPage" value="关闭"/>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
     		//检验商品档案数据（唯一性，最大长度等）
			$.extend($.fn.validatebox.defaults.rules, {
	   			validName:{//名称唯一性,最大长度
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = customer_AjaxConn({name:value},'basefiles/customerNameNoUsed.do');//true 重复，false 不重复
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
            repeatshopno($("#sales-shopno-customerAddPage").val());
     		//上级客户
			$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect: function(data){
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
    			    $("#sales-isparent-customerAddPage").val("1");
                },
    			onClear: function(){
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
                    $("#sales-isparent-customerAddPage").val("0");
                }
    		});

            $("#sales-isparent-customerAddPage").change(function(){
                if("0" == $(this).val()){
                    $("#sales-parent-customerAddPage").widget('clear');
                }
            });

			$("#customer-combobox-iscash").combobox({
    			<c:if test="${colMap.iscash == 'iscash' }">
    			required:true,
    			</c:if>
    			onSelect:function(record){
    				if("0" == record.value){
    					$("#customer-combobox-islongterm").combobox('setValue','1');
    				}else if("1" == record.value){
    					$("#customer-combobox-islongterm").combobox('setValue','0');
    				}
    			}
    		});
    		$("#customer-combobox-islongterm").combobox({
    			<c:if test="${colMap.islongterm == 'islongterm' }">
    			required:true,
    			</c:if>
    			onSelect:function(record){
    				if("0" == record.value){
    					$("#customer-combobox-iscash").combobox('setValue','1');
    				}else if("1" == record.value){
    					$("#customer-combobox-iscash").combobox('setValue','0');
    				}
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
				<c:if test="${colMap.salesdeptid == 'salesdeptid' }">required:true,</c:if>
    			width:130,
    			singleSelect:true,
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				$("#customerShortcut-widget-salesdeptname").val(data.name);
    			},
    			onClear:function(){
    				$("#customerShortcut-widget-salesdeptname").val("");
    			}
    		});

            //销售业务员
            $("#customerShortcut-widget-salesuserid").widget({
                name:'t_base_sales_customer',
                col:'salesuserid',
                width:130,
                async:false,
                singleSelect:true,
                required:true,
                onlyLeafCheck:false,
                onSelect:function(data){
                    $("#customerShortcut-widget-salesusername").val(data.name);
                },
                onClear:function(){
                    $("#customerShortcut-widget-salesusername").val("");
                }
            });

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
			//结算方式
			$("#customerShortcut-widget-settletype").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
    			singleSelect:true,
    			width:130,
    			required:true,
    			onlyLeafCheck:false,
    			onSelect:function(data){
    				if(data.type == '1'){//月结
    					$("#customer-select-settleday").combobox({
                            disabled:false,
    						required:true
    					});
    				}else{
    					$("#customer-select-settleday").combobox({
                            disabled:true,
    						required:false
    					});
    				}
    			}
    		});

    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
	   			$("#sales-id-customerAddPage").val($(this).val());
	   		});

	   		//保存并继续添加
	   		$("#sales-savegoon-customerAddPage").click(function(){
	   			if(!$("#customersimplify-form-add").form('validate')){
     				return false;
     			}
				$.messager.confirm("提醒","是否保存并继续添加客户?",function(r){
					if(r){
						var type = $("#customer-opera").val();
						if(type != "edit"){
							$("#sales-state-customerAddPage").val("2");
						}
						customer_savegoon_form_submit();
						$("#customersimplify-form-add").submit();
					}
				});
	   		});
	   		//保存
	   		$("#sales-save-customerAddPage").click(function(){
	   			if(!$("#customersimplify-form-add").form('validate')){
     				return false;
     			}
				$.messager.confirm("提醒","是否保存客户?",function(r){
					if(r){
						var type = $("#customer-opera").val();
						if(type != "edit"){
							$("#sales-state-customerAddPage").val("2");
						}
						customer_save_form_submit();
						$("#customersimplify-form-add").submit();
					}
				});
	   		});
	   		//关闭
	   		$("#sales-close-customerAddPage").click(function(){
	   			$("#sales-dialog-customerListPage1").dialog('close');
	   		});
    	});
    </script>
  </body>
</html>
