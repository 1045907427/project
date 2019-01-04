<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应付费用新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="customerCostPayable-form-add" style="padding: 10px;">
                <table cellpadding="2" cellspacing="2" border="0">
                    <tr>
                        <td class="len80 right">业务日期:</td>
                        <td class="len150 left"><input id="customerCostPayable-date-businesstime" type="text" name="customerCostPayable.businessdate" class="easyui-validatebox Wdate readonly" required="true" value="${customerCostPayable.businessdate }" style="width:120px" disabled="disabled"/></td>
						<c:choose>
	                        	<c:when test="${ customerCostPayable.hcflag=='1'}">
	                        		<td class="len70 left">关联代垫:</td>
									<td align="left">
										<input type="text" class="readonly len136" readonly="readonly" name="customerCostPayable.hcreferid" value="${customerCostPayable.hcreferid }"  style="width:120px; font-family: sans-serif;"/>
									</td>
	                        	</c:when>
	                        	<c:otherwise>                        		
	                    			<td colspan="2">&nbsp;</td>
	                        	</c:otherwise>
	                        </c:choose>
                    </tr>
                    <tr>
                        <td class="len80 right">OA编号:</td>
                        <td class="len150 left"><input id="customerCostPayable-text-oaid" type="text" name="customerCostPayable.oaid" value="${customerCostPayable.oaid }" style="width:120px" disabled="disabled"/></td>
						<td class="len70 left">费用类别:</td>
						<td align="left"><input id="customerCostPayable-widget-expensesort" type="text" name="customerCostPayable.expensesort" value="${customerCostPayable.expensesort}" style="width: 130px" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td class="right">客户名称:</td>
                        <td class="left" colspan="3"><input id="customerCostPayable-widget-customerid" type="text" style="width: 340px;" name="customerCostPayable.customerid" value="${customerCostPayable.customerid }" text="${customerCostPayable.customername }" readonly="readonly"/></td>
	    			</tr>
                    <tr>
                        <td class="right">供应商:</td>
                        <td colspan="3">
                        	<input id="customerCostPayable-widget-supplierid" type="text" name="customerCostPayable.supplierid" value="${customerCostPayable.supplierid }" text="${customerCostPayable.suppliername }" style="width:340px;" readonly="readonly" />
                        	<input id="customerCostPayable-hidden-deptid" type="hidden" name="customerCostPayable.deptid" value="${customerCostPayable.deptid }"/>
                        </td>
                    </tr>
					<tr>
						<td class="right">单据类型:</td>
						<td class="left">
							<select disabled="disabled" style="width:120px;" class="readonly">
								<option value=""></option>
								<option value="1" <c:if test="${customerCostPayable.billtype=='1' }">selected="selected"</c:if>>借</option>
								<option value="2" <c:if test="${customerCostPayable.billtype=='2' }">selected="selected"</c:if>>贷</option>
							</select>
						</td>	    					    					
						<td class="left">费用金额:</td>
						<td class="left"><input id="customerCostPayable-number-amount" type="text" style="width: 130px;" value="${customerCostPayable.amount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td class="right" >支付类型:</td>
                        <td align="left">
                            <select style="width:120px;" disabled="disabled" >
								<option value="1" <c:if test="${customerCostPayable.paytype=='1' }">selected="selected"</c:if>>支付</option>
								<option value="2" <c:if test="${customerCostPayable.paytype=='2' }">selected="selected"</c:if>>冲差</option>
							</select>
                        </td>
                        <td class="left">银　　行:</td>
                        <td class="left"><input id="customerCostPayable-widget-bankid" type="text" style="width: 130px;" name="customerCostPayable.bankid" value="${customerCostPayable.bankid }" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right" >备注:</td>
                        <td align="left" colspan="3">
                            <textarea id="customerCostPayable-text-remark" name="customerCostPayable.remark" style="width: 340px; height: 50px;" readonly="readonly" class="readonly">${customerCostPayable.remark }</textarea>
                        </td>
                    </tr>
                </table>          
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">

    	$(function(){
        	
    		//供应商
		  	$("#customerCostPayable-widget-supplierid").supplierWidget({
				name:'t_js_customercost_payable',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
		  		onSelect:function(data){
		  		}
			});

			$("#customerCostPayable-widget-supplierid").addClass("readonly");
			
			//科目编码
			$("#customerCostPayable-widget-expensesort").widget({
		  		width:120,
				name:'t_js_customercost_payable',
				col:'expensesort',
				singleSelect:true,
				onSelect:function(data){
				}
			});
			$("#customerCostPayable-widget-expensesort").addClass("readonly");

            // 银行
            $("#customerCostPayable-widget-bankid").widget({
                width:130,
                referwid: 'RL_T_BASE_FINANCE_BANK',
                singleSelect: true,
                onSelect: function(data){
                    $("#customerCostPayable-text-remark").focus();
                    $("#customerCostPayable-text-remark").select();
                }
            });
            $("#customerCostPayable-widget-bankid").addClass("readonly");
			
			//客户名称
			$("#customerCostPayable-widget-customerid").customerWidget({
				name:'t_js_customercost_payable',
				col:'customerid',
				singleSelect:true,
				isall:true,
				onSelect:function(data){
				}
			});
			$("#customerCostPayable-widget-customerid").addClass("readonly");
   			

    	});
</script>
</body>
</html>
