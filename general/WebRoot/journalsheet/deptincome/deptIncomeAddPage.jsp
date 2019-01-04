<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门收入新增</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="deptIncome-form-detailAdd" method="post">
   		<table>
   			<tr>
   				<td class="len80 right">编号：</td>
   				<td class="len140"><input type="text" class="easyui-validatebox len136" name="deptIncome.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
   				<td class="len80 right">业务日期：</td>
   				<td class="len140"><input class="len136 Wdate" type="text" id="deptIncome-detail-businessdate" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" required="required" name="deptIncome.businessdate" /></td>
   				<td class="len80 right">状态：</td>
   				<td class="len150"><select disabled="disabled" class="len150"><option>新增</option></select></td>
   			</tr>
   			<tr>
   				<td class="right">所属供应商：</td>
   				<td colspan="3">
   					<input type="text" id="deptIncome-detail-supplier" style="width:280px;" name="deptIncome.supplierid"/>
   					<span id="deptIncome-detail-supplier-showid" style="margin-left:5px;line-height:25px;"></span>
   				</td>   				
   				<td class="right">所属部门：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-deptid" name="deptIncome.deptid" class="len150"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">收入科目：</td>
   				<td>
   					<input class="len136" type="text" id="deptIncome-detail-costsort" name="deptIncome.costsort"/>
   				</td>
   				<td class="right">品牌：</td>
   				<td>
   					<input class="len150" type="text" id="deptIncome-detail-brandid" name="deptIncome.brandid"/>
   				</td>
   				<td class="right">单位：</td>
   				<td>
   					<input class="len136" type="text" id="deptIncome-detail-unitid" name="deptIncome.unitid"/>
   				</td>
                <!--
   				<td>OA编号：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-oaid" style="width: 80px;" name="deptIncome.oaid" autocomplete="off"/>
   				</td>
   				-->
   			</tr>
   			<tr>
   				<td class="right">数量：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-unitnum" name="deptIncome.unitnum" class="len136 easyui-validatebox" required="true" validType="intOrFloatNum[${decimallen}]" value="1"/>
   				</td>
   				<td class="right">单价：</td>
   				<td>
   					<input type="text" id="deptIncome-detail-taxprice" name="deptIncome.taxprice" class="len136 easyui-validatebox" required="true" validType="intOrFloat" value="0.0000"/>
   				</td>
                <td class="right">金额：</td>
                <td>
                    <input type="text" id="deptIncome-detail-amount" name="deptIncome.amount" autocomplete="off" class="len150 easyui-validatebox" required="true" validType="intOrFloat"/>
                </td>
   			</tr>
   			<tr>
   				<td class="right">银行名称：</td>
   				<td colspan="5">
   					<input class="len136" type="text" id="deptIncome-detail-bank" name="deptIncome.bankid"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">备注：</td>
   				<td colspan="5">
   					<textarea id="deptIncome-detail-remark" style="width: 600px;height: 50px;" name="deptIncome.remark" ></textarea>
   					<input type="hidden" id="deptIncome-detail-addtype" value="save"/>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				<input type="button" value="确 定" name="savegoon" id="deptIncome-detail-addbutton" />
	  			<input type="button" value="继续添加" name="savegoon" id="deptIncome-detail-addgobutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#deptIncome-detail-deptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:150,
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
                onSelect:function(data){
                    var deptid = $(this).widget('getValue');
                    if(data.id != deptid){
                        $("#deptIncome-detail-costsort").focus();
                        $(".widgettree").hide();
                    }else{
                        $(".widgettree").show();
                    }
                }
    		});
    		$("#deptIncome-detail-costsort").widget({
    			referwid:'RL_T_BASE_SUBJECT',
    			width:137,
				singleSelect:true,
				onlyLeafCheck:true,
				required:true,
    			treePName:false,
    			treeNodeDataUseNocheck:true,
	   			param:[
	   			       {field:'typecode',op:'equal',value:'INCOME_SUBJECT'}
	   			],
				onSelect:function(){
					$("#deptIncome-detail-brandid").focus();
					$("#deptIncome-detail-brandid").select();
				}
    		});


			//品牌
	  		$("#deptIncome-detail-brandid").widget({
	   			referwid:'RL_T_BASE_GOODS_BRAND',
	   			singleSelect:true,
    			width:136,
	   			onlyLeafCheck:true,
	   			onSelect:function(){
					$("#deptIncome-detail-unitid").focus();
					$("#deptIncome-detail-unitid").select();
	   			}
	   		});
	  		//主单位
	  		$("#deptIncome-detail-unitid").widget({
	   			referwid:'RL_T_BASE_GOODS_METERINGUNIT',
	   			singleSelect:true,
    			width:150,
	   			onlyLeafCheck:true,
	   			onSelect:function(){
					$("#deptIncome-detail-unitnum").focus();
                    $("#deptIncome-detail-unitnum").select();
	   			}
	   		});

    		$("#deptIncome-detail-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:136,
				singleSelect:true,
				onSelect:function(){
					$("#deptIncome-detail-remark").focus();
				}
    		});

    		$("#deptIncome-form-detailAdd").form({  
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
			    		$("#deptIncomeListPage-table-detail").datagrid("reload");
			    		var savetype = $("#deptIncome-detail-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#deptIncome-detail-costsort").widget("clear");
			    			$("#deptIncome-detail-bank").widget("clear");
			    			$("#deptIncome-detail-oaid").val("");
			    			$("#deptIncome-detail-amount").val("");
			    			$("#deptIncome-detail-deptid").focus();
                            $("#deptIncome-detail-deptid").select();
			    		}else{
			    			$('#deptIncome-dialog-detail').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#deptIncome-detail-addbutton").click(function(){
				$("#deptIncome-detail-addtype").val("save");
				$("#deptIncome-form-detailAdd").attr("action", "journalsheet/deptincome/addDeptIncome.do");
				$("#deptIncome-form-detailAdd").submit();
			});
			$("#deptIncome-detail-addgobutton").click(function(){
				$("#deptIncome-detail-addtype").val("saveadd");
				$("#deptIncome-form-detailAdd").attr("action", "journalsheet/deptincome/addDeptIncome.do");
				$("#deptIncome-form-detailAdd").submit();
			});
			$("#deptIncome-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#deptIncome-detail-bank").focus();
				}
			});
			

			$("#deptIncome-detail-supplier").supplierWidget({ 
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
					if(data==null){
						return false;
					}
					$("#deptIncome-detail-supplier-showid").text("编号："+ data.id);
					$("#deptIncome-detail-deptid").widget("setValue",data.buydeptid);
					$("#deptIncome-detail-deptid").validatebox('validate');
								
				},
				onClear:function(){
					$("#deptIncome-detail-supplier-showid").text("");
					$("#deptIncome-detail-deptid").widget("clear");
					$("#deptIncome-detail-supplier").validatebox('validate');
					$("#deptIncome-detail-deptid").validatebox('validate');
				}
			});
			

  			$("#deptIncome-detail-amount").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("1");
    			}
    		});
  			$("#deptIncome-detail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("2");
    			}
    		});
  			$("#deptIncome-detail-taxprice").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("3");
    			}
    		});


    		$("#deptIncome-detail-unitnum").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptIncome-detail-taxprice").focus();
                    $("#deptIncome-detail-taxprice").select();
                }
            });
    		$("#deptIncome-detail-taxprice").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptIncome-detail-amount").focus();
                    $("#deptIncome-detail-amount").select();
                }
            });
            $("#deptIncome-detail-amount").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptIncome-detail-bank").focus();
                    $("#deptIncome-detail-bank").select();
                }
            });

            $("#deptIncome-detail-remark").bind('keydown',function(e){
                if(e.keyCode == 13 && $("#deptIncome-form-detailAdd").form('validate')){
                    $("#deptIncome-detail-addgobutton").click();
                }
            });
    	});
    </script>
  </body>
</html>
