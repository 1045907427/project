<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门日常费用新增</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="deptDailyCost-form-detailAdd" method="post">
   		<table>
   			<tr>
   				<td class="len80 right">编号：</td>
   				<td class="len140"><input type="text" class="easyui-validatebox len136" name="deptdailycost.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
   				<td class="len80 right">业务日期：</td>
   				<td class="len140"><input class="len136 Wdate" type="text" id="deptDailyCost-detail-businessdate" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" required="required" name="deptdailycost.businessdate" /></td>
   				<td class="len80 right">状态：</td>
   				<td class="len150"><select disabled="disabled" class="len150"><option>新增</option></select></td>
   			</tr>
   			<tr>
   				<td class="right">所属供应商：</td>
   				<td colspan="3">
   					<input type="text" id="deptDailyCost-detail-supplier" style="width:280px;" name="deptdailycost.supplierid"/>
   					<span id="deptDailyCost-detail-supplier-showid" style="margin-left:5px;line-height:25px;"></span>
   				</td>   				
   				<td class="right">所属部门：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-deptid" name="deptdailycost.deptid" class="len150"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">费用科目：</td>
   				<td>
   					<input class="len136" type="text" id="deptDailyCost-detail-costsort" name="deptdailycost.costsort"/>
   				</td>
   				<td class="right">品牌：</td>
   				<td>
   					<input class="len150" type="text" id="deptDailyCost-detail-brandid" name="deptdailycost.brandid"/>
   				</td>
   				<td class="right">单位：</td>
   				<td>
   					<input class="len136" type="text" id="deptDailyCost-detail-unitid" name="deptdailycost.unitid"/>
   				</td>
                <!--
   				<td>OA编号：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-oaid" style="width: 80px;" name="deptdailycost.oaid" autocomplete="off"/>
   				</td>
   				-->
   			</tr>
   			<tr>
   				<td class="right">数量：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-unitnum" name="deptdailycost.unitnum" class="len136 easyui-validatebox" required="true" validType="intOrFloatNum[${decimallen}]" value="1"/>
   				</td>
   				<td class="right">单价：</td>
   				<td>
   					<input type="text" id="deptDailyCost-detail-taxprice" name="deptdailycost.taxprice" class="len136 easyui-validatebox" required="true" validType="intOrFloat" value="0.0000"/>
   				</td>
                <td class="right">金额：</td>
                <td>
                    <input type="text" id="deptDailyCost-detail-amount" name="deptdailycost.amount" autocomplete="off" class="len150 easyui-validatebox" required="true" validType="intOrFloat"/>
                </td>
   			</tr>
   			<tr>
                <td class="right">人员：</td>
                <td>
                    <input class="len136" type="text" id="deptDailyCost-detail-salesuser" name="deptdailycost.salesuser"/>
                </td>
   				<td class="right">银行名称：</td>
   				<td>
   					<input class="len136" type="text" id="deptDailyCost-detail-bank" name="deptdailycost.bankid"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="right">备注：</td>
   				<td colspan="5">
   					<textarea id="deptDailyCost-detail-remark" style="width: 600px;height: 50px;" name="deptdailycost.remark" ></textarea>
   					<input type="hidden" id="deptDailyCost-detail-addtype" value="save"/>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				<input type="button" value="确 定" name="savegoon" id="deptDailyCost-detail-addbutton" />
	  			<input type="button" value="继续添加" name="savegoon" id="deptDailyCost-detail-addgobutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#deptDailyCost-detail-deptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:150,
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
                onSelect:function(data){
                    var deptid = $(this).widget('getValue');
                    if(data.id != deptid){
                        $("#deptDailyCost-detail-costsort").focus();
                        $(".widgettree").hide();
                    }else{
                        $(".widgettree").show();
                    }
                }
    		});
    		$("#deptDailyCost-detail-costsort").widget({
    			referwid:'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
    			width:137,
				singleSelect:true,
				onlyLeafCheck:true,
				required:true,
				onSelect:function(){
					$("#deptDailyCost-detail-brandid").focus();
					$("#deptDailyCost-detail-brandid").select();
				}
    		});


			//品牌
	  		$("#deptDailyCost-detail-brandid").widget({
	   			referwid:'RL_T_BASE_GOODS_BRAND',
	   			singleSelect:true,
    			width:136,
	   			onlyLeafCheck:true,
	   			onSelect:function(){
					$("#deptDailyCost-detail-unitid").focus();
					$("#deptDailyCost-detail-unitid").select();
	   			}
	   		});
	  		//主单位
	  		$("#deptDailyCost-detail-unitid").widget({
	   			referwid:'RL_T_BASE_GOODS_METERINGUNIT',
	   			singleSelect:true,
    			width:150,
	   			onlyLeafCheck:true,
	   			onSelect:function(){
					$("#deptDailyCost-detail-unitnum").focus();
                    $("#deptDailyCost-detail-unitnum").select();
	   			}
	   		});
            //业务员
            $("#deptDailyCost-detail-salesuser").widget({
                referwid:'RL_T_BASE_PERSONNEL',
                width:136,
                singleSelect:true,
                onSelect:function(){
                    $("#deptDailyCost-detail-bank").focus();
                }
            });
            //银行
    		$("#deptDailyCost-detail-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:136,
				singleSelect:true,
				onSelect:function(){
					$("#deptDailyCost-detail-remark").focus();
				}
    		});


    		$("#deptDailyCost-form-detailAdd").form({  
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
			    		$("#deptdailycostListPage-table-detail").datagrid("reload");
			    		var savetype = $("#deptDailyCost-detail-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#deptDailyCost-detail-costsort").widget("clear");
			    			$("#deptDailyCost-detail-bank").widget("clear");
                            $("#deptDailyCost-detail-salesuser").widget('clear');
			    			$("#deptDailyCost-detail-oaid").val("");
			    			$("#deptDailyCost-detail-amount").val("");
			    			$("#deptDailyCost-detail-deptid").focus();
                            $("#deptDailyCost-detail-deptid").select();
			    		}else{
			    			$('#deptdailycost-dialog-detail').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#deptDailyCost-detail-addbutton").click(function(){
				$("#deptDailyCost-detail-addtype").val("save");
				$("#deptDailyCost-form-detailAdd").attr("action", "journalsheet/deptdailycost/addDeptDailyCost.do");
				$("#deptDailyCost-form-detailAdd").submit();
			});
			$("#deptDailyCost-detail-addgobutton").click(function(){
				$("#deptDailyCost-detail-addtype").val("saveadd");
				$("#deptDailyCost-form-detailAdd").attr("action", "journalsheet/deptdailycost/addDeptDailyCost.do");
				$("#deptDailyCost-form-detailAdd").submit();
			});
			$("#deptDailyCost-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
                    $("#deptDailyCost-detail-salesuser").focus();
				}
			});
			

			$("#deptDailyCost-detail-supplier").supplierWidget({ 
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
					if(data==null){
						return false;
					}
					$("#deptDailyCost-detail-supplier-showid").text("编号："+ data.id);
					$("#deptDailyCost-detail-deptid").widget("setValue",data.buydeptid);
					$("#deptDailyCost-detail-deptid").validatebox('validate');
								
				},
				onClear:function(){
					$("#deptDailyCost-detail-supplier-showid").text("");
					$("#deptDailyCost-detail-deptid").widget("clear");
					$("#deptDailyCost-detail-supplier").validatebox('validate');
					$("#deptDailyCost-detail-deptid").validatebox('validate');
				}
			});
			

  			$("#deptDailyCost-detail-amount").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("1");
    			}
    		});
  			$("#deptDailyCost-detail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("2");
    			}
    		});
  			$("#deptDailyCost-detail-taxprice").change(function(){
    			if($(this).validatebox('isValid')){
    				computeAmountNumChange("3");
    			}
    		});


    		$("#deptDailyCost-detail-unitnum").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptDailyCost-detail-taxprice").focus();
                    $("#deptDailyCost-detail-taxprice").select();
                }
            });
    		$("#deptDailyCost-detail-taxprice").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptDailyCost-detail-amount").focus();
                    $("#deptDailyCost-detail-amount").select();
                }
            });
            $("#deptDailyCost-detail-amount").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#deptDailyCost-detail-bank").focus();
                    $("#deptDailyCost-detail-bank").select();
                }
            });

            $("#deptDailyCost-detail-remark").bind('keydown',function(e){
                if(e.keyCode == 13 && $("#deptDailyCost-form-detailAdd").form('validate')){
                    $("#deptDailyCost-detail-addgobutton").click();
                }
            });
    	});
    </script>
  </body>
</html>
