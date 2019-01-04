<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门日常费用查看</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="deptDailyCost-form-detailAdd" method="post">
   		<table>
   			<tr>
                <td class="len80 right">编号：</td>
                <td class="len140"><input class="easyui-validatebox len136" name="deptdailycost.id" value="${deptdailycost.id }" readonly="readonly"/></td>
                <td class="len80 right">业务日期：</td>
                <td class="len140 "><input type="text" id="deptDailyCost-detail-businessdate" class="len136 Wdate" value="${deptdailycost.businessdate }" name="deptdailycost.businessdate" readonly="readonly"/></td>
                <td class="len80 right">状态：</td>
                <td class="len150">
   					<select disabled="disabled" class="len150">
   						<option <c:if test="${deptdailycost.status=='1'}">selected="selected"</c:if>>新增</option>
   						<option <c:if test="${deptdailycost.status=='2'}">selected="selected"</c:if>>保存</option>
   						<option <c:if test="${deptdailycost.status=='3'}">selected="selected"</c:if>>审核通过</option>
   						<option <c:if test="${deptdailycost.status=='4'}">selected="selected"</c:if>>关闭</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">所属供应商：</td>
   				<td colspan="3">
   					<input type="text" id="deptDailyCost-detail-supplier" style="width:280px;" name="deptdailycost.supplierid" value="${deptdailycost.supplierid}" text="${deptdailycost.suppliername }" readonly="readonly"/>
   					<span id="deptDailyCost-detail-supplier-showid" style="margin-left:5px;line-height:25px;"></span>
   				</td>   				
   				<td class="right">所属部门：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-deptid" name="deptdailycost.deptid" value="${deptdailycost.deptid }" class="len150" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">费用科目：</td>
   				<td>
   					<input class="len136" type="text" id="deptDailyCost-detail-costsort" name="deptdailycost.costsort" value="${deptdailycost.costsort }" readonly="readonly"/>
   				</td>
   				<td class="right">品牌：</td>
   				<td>
   					<input class="len150" type="text" id="deptDailyCost-detail-brandid" name="deptdailycost.brandid" value="${deptdailycost.brandid }" readonly="readonly"/>
   				</td>
   				<td class="right">单位：</td>
   				<td>
   					<input class="len136" type="text" id="deptDailyCost-detail-unitid" name="deptdailycost.unitid" value="${deptdailycost.unitid }" readonly="readonly"/>
   				</td>
                <%--
   				<td>OA编号：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-oaid" style="width: 80px;" name="deptdailycost.oaid" autocomplete="off" readonly="readonly"/>
   				</td>
   				--%>
   			</tr>
   			<tr>
   				<td class="right">数量：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-unitnum" name="deptdailycost.unitnum" class="len136" value="${deptdailycost.unitnum }" readonly="readonly"/>
   				</td>
   				<td class="right">单价：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-taxprice" name="deptdailycost.taxprice" class="len136" value="${deptdailycost.taxprice }" readonly="readonly"/>
   				</td>
                <td class="right">金额：</td>
                <td>
                    <input type="text" id="deptDailyCost-detail-amount" name="deptdailycost.amount" autocomplete="off" class="len150" value="${deptdailycost.amount }" readonly="readonly"/>
                </td>
   			</tr>
   			<tr>
                <td class="right">人员：</td>
                <td>
                    <input class="len136" type="text" id="deptDailyCost-detail-salesuser" name="deptdailycost.salesuser" value="${deptdailycost.salesuser }"  readonly="readonly"/>
                </td>
   				<td>银行名称：</td>
   				<td colspan="5">
   					<input class="len136" type="text" id="deptDailyCost-detail-bank" name="deptdailycost.bankid" value="${deptdailycost.bankid }" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea id="deptDailyCost-detail-remark" style="width: 600px;height: 50px;" name="deptdailycost.remark"  readonly="readonly">${deptdailycost.remark }</textarea>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  </div>
    <script type="text/javascript">
    $(function(){

		$("#deptDailyCost-detail-deptid").widget({
			referwid:'RT_T_SYS_DEPT',
			width:150,
			singleSelect:true,
			onlyLeafCheck:false
		});
		$("#deptDailyCost-detail-costsort").widget({
			referwid:'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
			width:137,
			singleSelect:true,
			onlyLeafCheck:true
		});


		//品牌
  		$("#deptDailyCost-detail-brandid").widget({
   			referwid:'RL_T_BASE_GOODS_BRAND',
   			singleSelect:true,
			width:136,
   			onlyLeafCheck:true
   		});
  		//主单位
  		$("#deptDailyCost-detail-unitid").widget({
   			referwid:'RL_T_BASE_GOODS_METERINGUNIT',
   			singleSelect:true,
			width:150,
   			onlyLeafCheck:true
   		});
        //业务员
        $("#deptDailyCost-detail-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL',
            width:136,
            singleSelect:true
        });
		$("#deptDailyCost-detail-bank").widget({
			referwid:'RL_T_BASE_FINANCE_BANK',
			width:136,
			singleSelect:true,
			onSelect:function(){
				$("#deptDailyCost-detail-remark").focus();
			}
		});
		

		$("#deptDailyCost-detail-supplier").widget({ 
			referwid:'RL_T_BASE_BUY_SUPPLIER',
			width:280,
			singleSelect:true,
			onlyLeafCheck:true
		});
		
        initDeptDailyCost();
	});
    function initDeptDailyCost(){
    	var tmp=$("#deptDailyCost-detail-unitnum").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptDailyCost-detail-unitnum").val(formatterBigNumNoLen(tmp));
    	}
    	tmp=$("#deptDailyCost-detail-taxprice").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptDailyCost-detail-taxprice").val(formatterMoney(tmp,4));
    	}
    	tmp=$("#deptDailyCost-detail-amount").val() || 0;
    	if(!isNaN(tmp)){
    		$("#deptDailyCost-detail-amount").val(formatterMoney(tmp));
    	}
    }
    </script>
  </body>
</html>
