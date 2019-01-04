<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>客户应付费用新增</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="customerCostPayableInit-form-detailAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td>编号：</td>
   				<td><input type="text" class="len150" name="customerCostPayableInit.id" readonly="readonly" /></td>
   				<td>业务日期：</td>
   				<td><input type="text" class="len150" id="customerCostPayableInit-detail-businessdate" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" required="required" name="customerCostPayableInit.businessdate" /></td>
   			</tr>
   			<tr>
   				<td>客户名称：</td>
   				<td colspan="3">
   					<input type="text" id="customerCostPayableInit-detail-customerid" name="customerCostPayableInit.customerid" style="width: 280px;"/>
   					<span id="customerCostPayableInit-detail-customerid-showid" style="margin-left:5px;line-height:25px;"></span>
   				</td>
   			</tr>
   			<tr>
   				<td>费用分类：</td>
   				<td>
   					<input type="text" id="customerCostPayableInit-detail-expensesort" name="customerCostPayableInit.expensesort"/>
   				</td>
   				<td>金额：</td>
   				<td>
   					<input type="text" class="len150" id="customerCostPayableInit-detail-amount" name="customerCostPayableInit.amount" autocomplete="off"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">备注：</td>
   				<td colspan="3" style="text-align: left">
   					<textarea id="customerCostPayableInit-detail-remark" style="width: 380px;height: 50px;" name="customerCostPayableInit.remark" ></textarea>
   					<input type="hidden" id="customerCostPayableInit-detail-addtype" value="save"/>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				<input type="button" value="确&nbsp;定" name="savegoon" id="customerCostPayableInit-detail-addbutton" />
	  			<input type="button" value="继续添加" name="savegoon" id="customerCostPayableInit-detail-addgobutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#customerCostPayableInit-detail-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#customerCostPayableInit-detail-customerid").customerWidget({
    			isall:true,
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#customerCostPayableInit-detail-customerid-showid").text("编号："+ data.id);
					$("#customerCostPayableInit-detail-expensesort").focus();
				}
    		});
    		$("#customerCostPayableInit-detail-expensesort").widget({
	   			 referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
	       		 width:150,
	       		 onlyLeafCheck:false,
	       		 singleSelect:true,
				 onSelect:function(data){
                     var expensesort = $(this).widget('getValue');
                     if(data.id != expensesort){
                         getNumberBox("customerCostPayableInit-detail-amount").focus();
                         $(".widgettree").hide();
                     }else{
                         $(".widgettree").show();
                     }
				 }
    		});

            getNumberBox("customerCostPayableInit-detail-amount").bind('keydown',function(e){
                if(e.keyCode == 13 && $("#customerCostPayableInit-form-detailAdd").form('validate')){
                    $("#customerCostPayableInit-detail-addgobutton").click();
                }
            });

    		$("#customerCostPayableInit-form-detailAdd").form({  
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
			    		$.messager.alert("提醒","保存成功");
			    		$("#customerCostPayableInit-table-detail").datagrid("reload");
			    		var savetype = $("#customerCostPayableInit-detail-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#customerCostPayableInit-detail-amount").numberbox("clear");
			    			$("#customerCostPayableInit-detail-customerid").focus();
                            $("#customerCostPayableInit-detail-customerid").select();
			    		}else{
			    			$('#customerCostPayableInit-dialog-detail').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#customerCostPayableInit-detail-addbutton").click(function(){
				$("#customerCostPayableInit-detail-addtype").val("save");
				$("#customerCostPayableInit-form-detailAdd").attr("action", "journalsheet/customercost/addCustomerCostPayableInit.do");
				$("#customerCostPayableInit-form-detailAdd").submit();
			});
			$("#customerCostPayableInit-detail-addgobutton").click(function(){
				$("#customerCostPayableInit-detail-addtype").val("saveadd");
				$("#customerCostPayableInit-form-detailAdd").attr("action", "journalsheet/customercost/addCustomerCostPayableInit.do");
				$("#customerCostPayableInit-form-detailAdd").submit();
			});
			$("#customerCostPayableInit-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#customerCostPayableInit-detail-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
