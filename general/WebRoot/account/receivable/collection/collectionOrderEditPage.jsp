<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>收款单</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  	<form action="" id="account-form-collectionOrderAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td class="len100 left">编号：</td>
   				<td><input class="len130 easyui-validatebox" name="collectionOrder.id" value="${collectionOrder.id }" readonly="readonly"/></td>
   				<td class="len100 left">业务日期：</td>
   				<td><input type="text" id="account-collectionOrder-businessdate" class="Wdate" style="width: 100px;" value="${collectionOrder.businessdate }" name="collectionOrder.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
   				<td class="len100 left">状态：</td>
   				<td style="width: 110px;">
   					<select disabled="disabled" class="len100">
   						<c:forEach items="${statusList }" var="list">
   							<c:choose>
   								<c:when test="${list.code == collectionOrder.status}">
   									<option value="${list.code }" selected="selected">${list.codename }</option>
   								</c:when>
   								<c:otherwise>
   									<option value="${list.code }">${list.codename }</option>
   								</c:otherwise>
   							</c:choose>
   						</c:forEach>
   					</select>
   					<input type="hidden" name="collectionOrder.status" value="${collectionOrder.status }"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">客户：</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="account-collectionOrder-customerid" name="collectionOrder.customerid" style="width: 250px;" value="${collectionOrder.customerid }" text="<c:out value="${collectionOrder.customername }"></c:out>"/>
   					<span style="margin-left:5px;line-height:25px;">编码：${collectionOrder.customerid }</span>
   				</td>
   				<td class="len100 left">收款人：</td>
   				<td>
   					<input type="text" id="account-collectionOrder-collectionuser" name="collectionOrder.collectionuser" class="len100" value="${collectionOrder.collectionuser }"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">收款金额：</td>
   				<td >
   					<input type="text" id="account-collectionOrder-amount" name="collectionOrder.amount" class="len130" autocomplete="off" value="${collectionOrder.amount }" <c:if test="${collectionOrder.status=='3' &&( collectionOrder.customerid ==null || collectionOrder.customerid=='' )}">readonly="readonly"</c:if>/>
   				</td>
   				<td class="len100 left">收款类型：</td>
   				<td>
   					<input type="text" id="account-collectionOrder-collectiontype" name="collectionOrder.collectiontype" class="len130" value="${collectionOrder.collectiontype }"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">银行名称：</td>
   				<td><input type="text" id="account-collectionOrder-bank" name="collectionOrder.bank" value="${collectionOrder.bank }"/></td>
   				<td class="len100 left">银行部门：</td>
   				<td colspan="3"><input type="text" id="account-collectionOrder-bankdeptid" name="collectionOrder.bankdeptid" value="${collectionOrder.bankdeptid }"/></td>
   			</tr>
   			<tr>
   				<td class="len100 left">已核销金额：</td>
   				<td >
   					<input type="text" id="account-collectionOrder-writeoffamount" class="len130" value="${collectionOrder.writeoffamount }" readonly="readonly"/>
   				</td>
   				<td class="len100 left">未核销金额：</td>
   				<td >
   					<input type="text" id="account-collectionOrder-remainderamount" class="len130" value="${collectionOrder.remainderamount}" readonly="readonly"/>
   				</td>
   				<c:if test="${collectionOrder.customerid==null || collectionOrder.customerid==''}">
   				<td class="len100 left">初始金额：</td>
   				<td >
   					<input type="text" id="account-collectionOrder-initamount" readonly="readonly" class="len100" value="${collectionOrder.initamount}" readonly="readonly"/>
   				</td>
   				</c:if>
   				<c:if test="${collectionOrder.customerid!=''}">
   				<td colspan="2"></td>
   				</c:if>
   			</tr>
   			<tr>
   				<td class="len80 left">备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea style="width: 400px;height: 50px;" name="collectionOrder.remark"><c:out value="${collectionOrder.remark }"></c:out></textarea>
   				</td>
   			</tr>
   			<tr>
   		</table>
   	</form>
   	</div>
   		<security:authorize url="/account/receivable/addCollectionOrderSave.do">
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<input type="button" value="确 定" name="savegoon" id="account-collectionOrder-addbutton" />
  			</div>
  		</div>
  		</security:authorize>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-collectionOrder-customerid").customerWidget({
    			name:'t_account_collection_order',
	    		width:380,
				col:'customerid',
				singleSelect:true,
				ishead:true,
				isall:true,
				isopen:true,
				required:true,
				onSelect:function(data){
		  			$("#account-collectionOrder-collectionuser").widget('setValue', data.payeeid);
                    getNumberBox("account-collectionOrder-amount").focus();
                    getNumberBox("account-collectionOrder-amount").select();
				},
				onClear:function(){
  					$("#account-collectionOrder-collectionuser").widget('clear');
				}
    		});
			$("#account-collectionOrder-collectionuser").widget({
                referwid:'RT_T_BASE_PERSONNEL',
                singleSelect:true,
                width:100,
                onlyLeafCheck:true
    		});
    		$("#account-collectionOrder-collectiontype").widget({
    			name:'t_account_collection_order',
	    		width:130,
				col:'collectiontype',
				singleSelect:true,
				required:true
    		});
    		$("#account-collectionOrder-amount").numberbox({
    			precision:2,
				required:true,
				onChange:function(newValue,oldValue){
					$("#account-collectionOrder-remainderamount").numberbox("setValue",newValue);
				}
    		});
    		$("#account-collectionOrder-remainderamount").numberbox({
    			precision:2,
				groupSeparator:','
    		});
    		
    		$("#account-collectionOrder-bankdeptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:130,
				singleSelect:true,
				onlyLeafCheck:false
    		});
    		$("#account-collectionOrder-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:130,
				singleSelect:true,
				required:true,
				onSelect:function(data){
					if(null != data.bankdeptid && "" != data.bankdeptid){
						$("#account-collectionOrder-bankdeptid").widget('setValue',data.bankdeptid);
					}else{
						$("#account-collectionOrder-bankdeptid").widget('clear');
					}
				}
    		});
            getNumberBox("account-collectionOrder-amount").bind("keydown", function(e){
                //enter
                if(e.keyCode==13){
                    $("#account-collectionOrder-addbutton").focus();
                    $("#account-collectionOrder-addbutton").select();
                }
            });
//            $("#account-collectionOrder-bankdeptid").die("keydown").live("keydown",function(event){
//                //enter
//                if(event.keyCode==13){
//                    $("#account-collectionOrder-bankdeptid").blur();
//                    $("#account-collectionOrder-addbutton").focus();
//                    $("#account-collectionOrder-addbutton").select();
//                }
//            });
    		$("#account-form-collectionOrderAdd").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$("#account-datagrid-collectionOrderPage").datagrid("reload");
			    		$.messager.alert("提醒","保存成功");
			    		$('#account-panel-collectionOrder-addpage').dialog("close");
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#account-collectionOrder-addbutton").click(function(){
				$("#account-collectionOrder-addtype").val("save");
				$("#account-form-collectionOrderAdd").attr("action", "account/receivable/editCollectionOrderSave.do");
				$("#account-form-collectionOrderAdd").submit();
			});
    	});
    	
    </script>
  </body>
</html>
