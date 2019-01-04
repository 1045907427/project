<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>超账期原因编辑页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<form action="account/receivable/addCustomerReceivablePastDueReason.do" method="post" id="report-form-customerReceivablePastDueReason">
   				<input type="hidden" name="receivablePastDueReason.customerid" value="${customerid }"/>
   				<input type="hidden" name="rowindex" value="${rowindex }"/>
   				<input type="hidden" name="receivablePastDueReason.saleamount" value="${saleamount }"/>
   				<input type="hidden" name="receivablePastDueReason.unpassamount" value="${unpassamount }"/>
   				<input type="hidden" name="receivablePastDueReason.totalpassamount" value="${totalpassamount }"/>
   				<table cellpadding="2" cellspacing="2" border="0">
   					<tr>
   						<td width="90px">客户:</td>
   						<td><input type="text" style="width: 200px" id="report-customerid-receivablePastDueReason" value="${customerid }"/></td>
   					</tr>
   					<tr>
   						<td>超账期原因:</td>
   						<td><textarea id="receivablePastDueReason-reason" class="easyui-validatebox" data-options="required:true" rows="3" style="width: 195px" name="receivablePastDueReason.overreason" onfocus="this.select();frm_focus('overreason');" onblur="frm_blur('overreason');"><c:out value="${receivablePastDueReason.overreason }"></c:out></textarea></td>
   					</tr>
   					<tr>
   						<td>承诺到款金额:</td>
   						<td><input id="receivablePastDueReason-commitmentamount" type="text" style="width: 200px" class="easyui-validatebox" name="receivablePastDueReason.commitmentamount" value="${receivablePastDueReason.commitmentamount }" onfocus="this.select();frm_focus('commitmentamount');" onblur="frm_blur('commitmentamount');setInputFormatterMoney(this.id,this.value,2);" data-options="validType:'intOrFloat',required:true"/></td>
   					</tr>
   					<tr>
   						<td>承诺到款时间:</td>
   						<td><input id="receivablePastDueReason-commitmentdate" type="text" style="width: 200px" name="receivablePastDueReason.commitmentdate" value="${receivablePastDueReason.commitmentdate }" onfocus="this.select();frm_focus('commitmentdate');" onblur="frm_blur('commitmentdate');" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'%y-%M-%d',onpicked:frm_focus('save')})" /></td>
   					</tr>
   				</table>
   			</form>
    	</div>
    	<div data-options="region:'south'" style="height: 40px;border: 0px;">
    		<div class="buttonDetailBG" style="text-align:right;">
    			<input type="button" value="确定并继续编辑" onfocus="this.select();frm_focus('save');" onblur="frm_blur('save');" id="sales-save-saveMenu" />
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
			$("#report-customerid-receivablePastDueReason").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER',
	    		width:200,
				singleSelect:true,
				readonly:true
			});

            $("#sales-save-saveMenu").click(function(){
                if(!$("#report-form-customerReceivablePastDueReason").form("validate")){
                    return false;
                }
                $("#report-form-customerReceivablePastDueReason").submit();
            });

            $("#report-form-customerReceivablePastDueReason").form({
                onSubmit: function(){
                    loading("提交中..");
                },
                success:function(data){
                    //表单提交完成后 隐藏提交等待页面
                    loaded();
                    var json = $.parseJSON(data);
                    if(json.flag){
                        $.messager.alert("提醒","编辑成功!");
                        var row = $("#report-datagrid-receivablePastDueReson").datagrid('selectRow',parseInt(json.rowindex));
                        row.overreason = json.overreason;
                        row.commitmentamount = json.commitmentamount;
                        row.commitmentdate = json.commitmentdate;
                        row.actualamount = json.actualamount;
                        row.changenum = json.changenum;
                        $("#report-datagrid-receivablePastDueReson").datagrid('updateRow',{
                            index: parseInt(json.rowindex),
                            row: row
                        });

                        var nextindex = parseInt(json.rowindex) + Number(1);
                        $("#report-datagrid-receivablePastDueReson").datagrid('selectRow',nextindex);
                        $("#report-datagrid-receivablePastDueReson").datagrid('unselectRow',parseInt(json.rowindex));
                        var nextrow = $("#report-datagrid-receivablePastDueReson").datagrid('getSelected');
                        if(null != nextrow){
                            var url = 'account/receivable/showEditCustomerReceivablePastDueReasonPage.do?customerid='+nextrow.customerid+'&rowindex='+nextindex+'&saleamount='+nextrow.saleamount+'&unpassamount='+nextrow.unpassamount+'&totalpassamount='+nextrow.totalpassamount;
                            $("#report-customerReceivablePastDueReason-dialog").dialog({
                                title:'编辑超账期原因',
                                width:350,
                                height:240,
                                closed:true,
                                modal:true,
                                cache:false,
                                resizable:true,
                                href: url,
                                onLoad:function(data){
                                    $("#receivablePastDueReason-reason").focus();
                                }
                            });
                            $("#report-customerReceivablePastDueReason-dialog").dialog("open");
                        }
                    }else{
                        $.messager.alert("提醒",json.msg);
                    }
                }
            });
    	});
    </script>
  </body>
</html>
