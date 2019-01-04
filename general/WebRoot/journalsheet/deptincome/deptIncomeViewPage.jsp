<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门收入查看</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="deptIncome-form-detailAdd" method="post">
   		<table>
   			<tr>
                <td class="len80 right">编号：</td>
                <td class="len140"><input class="easyui-validatebox len136" name="deptIncome.id" value="${deptIncome.id }" readonly="readonly"/></td>
                <td class="len80 right">业务日期：</td>
                <td class="len140 "><input type="text" id="deptIncome-detail-businessdate" class="len136 Wdate" value="${deptIncome.businessdate }" name="deptIncome.businessdate" readonly="readonly"/></td>
                <td class="len80 right">状态：</td>
                <td class="len150">
   					<select disabled="disabled" class="len150">
   						<option <c:if test="${deptIncome.status=='1'}">selected="selected"</c:if>>新增</option>
   						<option <c:if test="${deptIncome.status=='2'}">selected="selected"</c:if>>保存</option>
   						<option <c:if test="${deptIncome.status=='3'}">selected="selected"</c:if>>审核通过</option>
   						<option <c:if test="${deptIncome.status=='4'}">selected="selected"</c:if>>关闭</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">所属供应商：</td>
   				<td colspan="3">
   					<input type="text" id="deptIncome-detail-supplier" style="width:280px;" name="deptIncome.supplierid" value="${deptIncome.supplierid}" text="${deptIncome.suppliername }" readonly="readonly"/>
   					<span id="deptIncome-detail-supplier-showid" style="margin-left:5px;line-height:25px;"></span>
   				</td>   				
   				<td class="right">所属部门：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-deptid" name="deptIncome.deptid" value="${deptIncome.deptid }" class="len150" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">收入科目：</td>
   				<td>
   					<input class="len136" type="text" id="deptIncome-detail-costsort" name="deptIncome.costsort" value="${deptIncome.costsort }" readonly="readonly"/>
   				</td>
   				<td class="right">品牌：</td>
   				<td>
   					<input class="len150" type="text" id="deptIncome-detail-brandid" name="deptIncome.brandid" value="${deptIncome.brandid }" readonly="readonly"/>
   				</td>
   				<td class="right">单位：</td>
   				<td>
   					<input class="len136" type="text" id="deptIncome-detail-unitid" name="deptIncome.unitid" value="${deptIncome.unitid }" readonly="readonly"/>
   				</td>
                <%--
   				<td>OA编号：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-oaid" style="width: 80px;" name="deptIncome.oaid" autocomplete="off" readonly="readonly"/>
   				</td>
   				--%>
   			</tr>
   			<tr>
   				<td class="right">数量：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-unitnum" name="deptIncome.unitnum" class="len136" value="${deptIncome.unitnum }" readonly="readonly"/>
   				</td>
   				<td class="right">单价：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-taxprice" name="deptIncome.taxprice" class="len136" value="${deptIncome.taxprice }" readonly="readonly"/>
   				</td>
                <td class="right">金额：</td>
                <td>
                    <input type="text" id="deptIncome-detail-amount" name="deptIncome.amount" autocomplete="off" class="len150" value="${deptIncome.amount }" readonly="readonly"/>
                </td>
   			</tr>
   			<tr>
   				<td class="right">银行名称：</td>
   				<td colspan="5">
   					<input class="len136" type="text" id="deptIncome-detail-bank" name="deptIncome.bankid" value="${deptIncome.bankid }" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea id="deptIncome-detail-remark" style="width: 600px;height: 50px;" name="deptIncome.remark"  readonly="readonly">${deptIncome.remark }</textarea>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  </div>
    <script type="text/javascript">
    $(function(){

		$("#deptIncome-detail-deptid").widget({
			referwid:'RT_T_SYS_DEPT',
			width:150,
			singleSelect:true,
			onlyLeafCheck:false
		});
		$("#deptIncome-detail-costsort").widget({
			referwid:'RL_T_BASE_SUBJECT',
			width:137,
			singleSelect:true,
			treePName:false,
			treeNodeDataUseNocheck:true,
   			param:[
   			       {field:'typecode',op:'equal',value:'INCOME_SUBJECT'}
   			],
			onlyLeafCheck:true
		});


		//品牌
  		$("#deptIncome-detail-brandid").widget({
   			referwid:'RL_T_BASE_GOODS_BRAND',
   			singleSelect:true,
			width:136,
   			onlyLeafCheck:true
   		});
  		//主单位
  		$("#deptIncome-detail-unitid").widget({
   			referwid:'RL_T_BASE_GOODS_METERINGUNIT',
   			singleSelect:true,
			width:150,
   			onlyLeafCheck:true
   		});

		$("#deptIncome-detail-bank").widget({
			referwid:'RL_T_BASE_FINANCE_BANK',
			width:136,
			singleSelect:true,
			onSelect:function(){
				$("#deptIncome-detail-remark").focus();
			}
		});
		

		$("#deptIncome-detail-supplier").widget({ 
			referwid:'RL_T_BASE_BUY_SUPPLIER',
			width:280,
			singleSelect:true,
			onlyLeafCheck:true
		});
		
        initDeptIncome();
	});
    function initDeptIncome(){
    	var tmp=$("#deptIncome-detail-unitnum").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptIncome-detail-unitnum").val(formatterBigNumNoLen(tmp));
    	}
    	tmp=$("#deptIncome-detail-taxprice").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptIncome-detail-taxprice").val(formatterMoney(tmp,4));
    	}
    	tmp=$("#deptIncome-detail-amount").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptIncome-detail-amount").val(formatterMoney(tmp));
    	}
    }
    </script>
  </body>
</html>
