<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>客户档案查看页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true" id="sales-layout-customerAddPage">
    	<div data-options="region:'center',split:true,border:true">
    		<form action="basefiles/editCustomerShortcut.do" method="post" id="customersimplify-form-add">
		    	<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
		   		<input type="hidden" name="customer.salesdeptname" value="<c:out value="${customer.salesdeptname }"></c:out>" id="customerShortcut-widget-salesdeptname"/>
			  	<input type="hidden" name="customer.salesusername" value="<c:out value="${customer.salesusername }"></c:out>" id="customerShortcut-widget-salesusername"/>
			    <input type="hidden" name="customer.tallyusername" value="<c:out value="${customer.tallyusername }"></c:out>" id="customerShortcut-widget-tallyusername" />
		   		<input type="hidden" value="${customer.state }" id="customerShortcut-hd-state" />
		   		<input type="hidden" name="customer.oldsalesarea" value="<c:out value="${customer.salesarea }"></c:out>"/>
		    	<input type="hidden" id="customer-oldsalesdeptid" name="customer.oldsalesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>"/>
		    	<input type="hidden" name="customer.oldsalesuserid" value="<c:out value="${customer.salesuserid }"></c:out>"/>
		    	<input type="hidden" name="customer.oldindoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>"/>
		    	<input type="hidden" name="customer.oldcustomersort" value="<c:out value="${customer.customersort }"></c:out>"/>
		    	<input type="hidden" id="customer-oldid" name="customer.oldid" value="<c:out value="${customer.id}"></c:out>"/>
		    	<input type="hidden" id="customer-thisid" value="<c:out value="${customer.thisid}"></c:out>"/>
		    	<input type="hidden" id="customer-name" value="<c:out value="${customer.name}"></c:out>"/>
			   	<input type="hidden" id="sales-state-customerAddPage" name="customer.state" value="${customer.state}"/>
			   	<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
		    	<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80px" align="right">本级编码:</td>
		   				<td align="left"><input class="easyui-validatebox" name="customer.thisid" value="<c:out value="${customer.thisid }"></c:out>" id="sales-thisId-customerAddPage" <c:if test="${editFlag == false }">readonly="readonly"</c:if> data-options="required:true,validType:'validLength'" style="width: 130px;" /><font color="red">*</font></td>
		   				<td width="80px" align="right">上级客户:</td>
		   				<td align="left"><input id="sales-parent-customerAddPage" value="<c:out value="${customer.pid }"></c:out>" name="customer.pid" style="width: 130px;"/></td>
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
		    			<td align="left" colspan="3"><input id="sales-name-customershortcut" name="customer.name" value="<c:out value="${customer.name }"></c:out>" type="text" style="width: 352px" maxlength="100" class="easyui-validatebox" validType="validName[100]" data-options="required:true" /><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">是否总店:</td>
		    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="easyui-validatebox" data-options="required:true" style="width: 130px;">
				    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
					    	<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
				    	</select><font color="red">*</font></td>
						<td align="right">所属区域:</td>
						<td align="left"><input id="customerShortcut-widget-salesarea" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>" type="text" style="width: 130px"/><font color="red">*</font></td>
						<td align="right">所属分类:</td>
						<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" style="width: 130px"/></td>

					</tr>
		    		<tr>
		    			<td align="right">店号:</td>
	    				<td align="left"><input type="text" id="sales-shopno-customerAddPage" name="customer.shopno" value="<c:out value="${customer.shopno }"></c:out>" maxlength="20" style="width: 130px;" class="easyui-validatebox" data-options="validType:'repeatshopno'"/></td>
		    			<td align="right">税号:</td>
				    	<td align="left"><input type="text" name="customer.taxno" value="<c:out value="${customer.taxno }"></c:out>" maxlength="30" style="width: 130px"/></td>
		    			<td align="right">开户银行:</td>
				    	<td align="left"><input type="text" name="customer.bank" value="<c:out value="${customer.bank }"></c:out>" maxlength="50" style="width: 130px"/></td>
		    		</tr>

		    		<tr>
		    			<td align="right">门店面积m<sup>2</sup>:</td>
				    	<td align="left"><input type="text" name="customer.storearea" value="${customer.storearea }" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">联系人:</td>
				    	<td align="left"><input type="text" name="customer.contact" value="<c:out value="${customer.contact }"></c:out>" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">联系人电话:</td>
				    	<td align="left"><input type="text" name="customer.mobile" value="<c:out value="${customer.mobile }"></c:out>" style="width: 130px"/></td>
				    </tr>
		    		<tr>
						<td align="right">核销方式:</td>
						<td align="left"><select name="customer.canceltype" <c:if test="${colMap.canceltype == 'canceltype'}">class="easyui-validatebox" data-options="required:true"</c:if> style="width: 130px;">
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
		    			<td align="right">详细地址:</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.address" value="<c:out value="${customer.address }"></c:out>" maxlength="100" style="width: 352px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">结算日:</td>
				    	<td align="left"><select id="customer-select-settleday" name="customer.settleday" <c:if test="${monthorday == '2' }">disabled="disabled" </c:if><c:if test="${monthorday == '1' }">class="easyui-validatebox" data-options="required:true"</c:if> style="width: 130px;">
									<option></option>
									<c:forEach items="${dayList}" var="day">
										<c:choose>
										<c:when test="${day == customer.settleday}"><option value="${day }" selected="selected">${day }</option></c:when>
										<c:otherwise><option value="${day }">${day }</option></c:otherwise>
										</c:choose>
									</c:forEach>
								</select></td>
						<td align="right">超账期控制：</td>
						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" style="width: 130px;">
							<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
							<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
						</select></td>
		    			<td align="right">是否账期:</td>
				    	<td align="left"><select id="customer-combobox-islongterm" name="customer.islongterm" style="width: 130px;">
						    		<option></option>
						    		<option <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if> value="0">否</option>
					    			<option <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if> value="1">是</option>
						    	</select><c:if test="${colMap.islongterm == 'islongterm' }"><font color="red">*</font></c:if></td>
		    		</tr>

		    		<tr>
		    			<td align="right">默认价格套:</td>
				    	<td align="left"><select name="customer.pricesort" class="easyui-validatebox" data-options="required:true" style="width: 130px;">
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
				    	<td align="left" colspan="5"><input type="text" name="customer.remark" value="<c:out value="${customer.remark }"></c:out>" maxlength="100" style="width: 352px"/></td>
		    		</tr>
		    	</table>
		    </form>
    	</div>
    	<div data-options="region:'south'" >
    		<div class="buttonDetailBG" style="text-align:right;">
    			<security:authorize url="/basefiles/customerSimplifySave.do">
    				<input type="button" id="sales-save-customerAddPage" value="保存"/>
	  			</security:authorize>
	  			<security:authorize url="/basefiles/customerSimplifySave.do">
    				<input type="button" id="sales-close-customerAddPage" value="关闭"/>
	  			</security:authorize>
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
                            if($("#customer-name").val() == $("#sales-name-customershortcut").val()){
                                return true;
                            }
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
     		validLengthAndUsed('${len}', "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
            repeatshopno($("#sales-shopno-customerAddPage").val());
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
    				//$("#sales-id-customerAddPage").val($("#sales-thisId-customerAddPage").val());
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-hold");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-save");
                    $("#sales-isparent-customerAddPage").val("0");
    			}
    			
    		});
    		
    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
    			$("#sales-id-customerAddPage").val($(this).val());
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
				required:true,
				singleSelect:true,
				onlyLeafCheck:true
			});
			
			//所属分类
			$("#customerShortcut-widget-customersort").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'customersort',
				required:true,
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
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				$("#customerShortcut-widget-salesdeptname").val(data.name);
    			},
    			onClear:function(){
    				$("#customerShortcut-widget-salesdeptname").val("");
    			}
    		});
            //默认销售业务员
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
    			required:true,
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
    			required:true,
    			width:130,
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
