<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫录入红冲页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="matcostsInput-form-add" style="padding: 10px 5px;">
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	                        <td class="right" style="width:95px;">业务日期:</td>
	                        <td class="left" style="width:130px;"><input id="journalsheet-date-businesstime" type="text" name="matcostsInput.businessdate"  class="easyui-validatebox Wdate" required="true" value="${businessdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'2000-01-01'})"/></td>
							<td class="right" style="width:95px;">关联代垫:</td>
							<td align="left">
								<input type="text" class="readonly" readonly="readonly" id="journalsheet-text-hcreferid" name="matcostsInput.hcreferid" value="${matcostsInput.id }" style="width:120px; font-family: sans-serif;" />
							</td>
						</tr>
	                    <tr>
	                        <td class="right">支付日期:</td>
	                        <td class="left"><input id="journalsheet-date-paydate" type="text" class="Wdate readonly" readonly="readonly"  value="${matcostsInput.paydate }" style="width:120px"/></td>
							 <td class="right">收回日期:</td>
	                        <td class="left"><input id="journalsheet-date-takebackdate" type="text" class="Wdate readonly" readonly="readonly"  value="${matcostsInput.takebackdate }" style="width:120px"/></td>
	                    </tr>
	                    <tr>
	                        <td class="right">OA编号:</td>
	                        <td class="left"><input id="journalsheet-text-oaid" type="text"  readonly="readonly" value="${matcostsInput.oaid }" class="readonly" style="width:120px"/></td>
							<td class="right">科目名称:</td>
							<td align="left"><input id="journalsheet-widget-subjectid" type="text" readonly="readonly" value="${matcostsInput.subjectid }" class="readonly" style="width: 130px"/></td>
	                    </tr>
	                    <tr>
	                        <td class="right">供应商:</td>
	                        <td class="left" colspan="3"><input id="journalsheet-widget-supplierid" type="text" readonly="readonly" value="${matcostsInput.supplierid }" text="${matcostsInput.suppliername}" style="width:360px;"/></td>
	                    </tr>
	                    <tr>
	                        <td class="right">所属部门:</td>
		    					<td class="left"><input id="journalsheet-widget-supplierdeptid" readonly="readonly" value="${matcostsInput.supplierdeptid }" type="text" class="readonly" style="width: 120px;"/></td>
	                        <td class="right">商品品牌:</td>
	                        <td class="left"><input type="text" id="report-query-brandid" value="${matcostsInput.brandid }" readonly="readonly" class="readonly" /></td>
		    				</tr>
		    				<tr>	    					
	                        <td class="right">客户名称:</td>
	                        <td class="left" colspan="3"><input id="journalsheet-widget-customerid" type="text" style="width: 360px;"  readonly="readonly" value="${matcostsInput.customerid }" text="${matcostsInput.customername }" class="readonly"/></td>
	                    </tr>
						<tr>
							<td class="right">工厂投入(代垫):</td>
							<td class="left"><input id="journalsheet-number-factoryamount-org" type="text" style="width: 120px;" value="${matcostsInput.factoryamount}"  class="readonly" readonly="readonly"  /></td>	    					    					
							<td class="right">费用金额(代垫):</td>
							<td class="left"><input id="journalsheet-number-expense-org" type="text" style="width: 120px;" value="${matcostsInput.expense}"  class="readonly" readonly="readonly" /></td>
	                    </tr>
						<tr>
							<td class="right">工厂投入(红冲):</td>
							<td class="left"><input id="journalsheet-number-factoryamount" type="text" style="width: 120px;" value="${factory }" readonly="readonly" class="readonly"/></td>	    					    					
							<td class="right">费用金额(红冲):</td>
							<td class="left"><input id="journalsheet-number-expense" type="text" style="width: 120px;" value="${expense }" readonly="readonly" class="readonly"/></td>
	                    </tr>
	                    <tr style="display:none;">
	                    	<td class="right">代垫金额:</td>
							<td class="left" colspan="3"><input id="journalsheet-number-actingmatamount" readonly="readonly" type="text" style="width: 340px;" value="${matcostsInput.actingmatamount }"  class="easyui-numberbox readonly" data-options="precision:2,groupSeparator:','"/></td>
	                    </tr>
	                    <tr>
	                        <td class="right">备注:</td>
	                        <td align="left" colspan="3">
	                            <textarea id="journalsheet-text-remark" name="matcostsInput.remark" style="width: 360px;height: 50px;">关联代垫编号：${matcostsInput.id }</textarea>
	                        </td>
	                    </tr>
	                </table>
                	<div style="display:none">
						<input id="journalsheet-number-htcompdiscount" type="hidden" style="width: 120px;" value="${matcostsInput.htcompdiscount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
						<input id="journalsheet-number-htpayamount" type="hidden" style="width: 130px;" value="${matcostsInput.htpayamount }"/>
						<input id="journalsheet-number-branchaccount" type="hidden"  disabled="disabled"  style="width: 120px;" value="${matcostsInput.branchaccount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
						<%--<input id="journalsheet-text-hcflag" type="hidden" name="matcostsInput.hcflag" value="1" /> --%>
					</div>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'">
            <div class="buttonDetailBG" style="text-align:right;">
                <input type="button" name="savegoon" id="matcostsInput-save-saveMenu" title="添加代垫红冲" value="确定"/>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
	
	    	$(function(){
	
	    		//检验输入值的最大长度
	    		$.extend($.fn.validatebox.defaults.rules, {
	    			maxLen:{
	    	  			validator : function(value,param) { 
	    		            return value.length <= param[0]; 
	    		        }, 
	    		        message : '最多可输入{0}个字符!' 
	    	  		}
	    		});
	        	//添加代垫红冲
	        	$("#matcostsInput-save-saveMenu").click(function(){
	        		$("#journalsheet-text-remark").blur();
	        		

	        		var hcreferid= $("#journalsheet-text-hcreferid").val()||"";
	        		if($.trim(hcreferid)=="" ){    		
	        			$.messager.alert("提醒","抱歉，未能找到相关代垫信息!");
	        			return false;
	        		}
	           		
	        		
	            	var factoryamount=$("#journalsheet-number-factoryamount").numberbox('getValue');
	            	var htcompdiscount=$("#journalsheet-number-htcompdiscount").numberbox('getValue');
	            	var htpayamount=$("#journalsheet-number-htpayamount").numberbox('getValue');
	            	var expense=$("#journalsheet-number-expense").numberbox('getValue');
	        		if((factoryamount=="" || factoryamount==0) && (expense=="" || expense==0)){    		
	        			$.messager.alert("提醒","红冲时，工厂投入或者费用金额至少有一个不为零!");
	        			return false;
	        		}
	        		if(!$("#matcostsInput-form-add").form('validate')){
	           			return false;
	           		}
	        		$.messager.confirm("提醒","是否新增代垫红冲?",function(r){
						if(r){
			           		var ret = matcostsInput_AjaxConn($("#matcostsInput-form-add").serializeJSON(),'journalsheet/matcostsInput/addMatcostsInputHC.do','提交中..');
			            	var retJson = $.parseJSON(ret);
			            	if(retJson.flag){
			            		$.messager.alert("提醒","新增代垫红冲成功!");
			            		//$("#journalsheet-table-matcostsInput").datagrid('reload');
			            		$("#matcostsInput-query-List").trigger('click');
			            		closeMatcostsInputDialog();
			            	}
			            	else{
			                	if(retJson.msg){
			            			$.messager.alert("提醒","新增代垫红冲失败!"+retJson.msg);
			                	}else{
			            			$.messager.alert("提醒","新增代垫红冲失败!");
			                	}
			            	}
						}
	        		});
	        	});
	
	        	
	    		//供应商
			  	$("#journalsheet-widget-supplierid").supplierWidget({
					name:'t_js_matcostsinput',
					col:'supplierid',
					singleSelect:true,
					onlyLeafCheck:false,
			  		onSelect:function(data){
			  			if(data.buydeptid == undefined){
			  				$("#journalsheet-widget-supplierdeptid").widget("clear");
			  			}else{
			  				$("#journalsheet-widget-supplierdeptid").widget("setValue",data.buydeptid);
			  			}
	
			  			$("#report-query-brandid").widget('clear');
			  			//品牌名称
						var brandParam=[{field:'supplierid',op:'equal',value:$("#journalsheet-widget-supplierid").widget('getValue')}];
			  			brandWidget(brandParam);
			  			
						$(this).select();
			   			$(this).blur();
	            		$("#report-query-brandid").focus();
	            		$("#report-query-brandid").select();
			  		},
			  		onClear:function(){
		  				$("#journalsheet-widget-supplierdeptid").supplierWidget("clear");		  			
			  		}
				});

				$("#journalsheet-widget-supplierid").addClass("readonly");
				
				//科目编码
				$("#journalsheet-widget-subjectid").widget({
			  		width:120,
					name:'t_js_matcostsinput',
					col:'subjectid',
					singleSelect:true,
					onSelect:function(data){
						$("#journalsheet-widget-supplierid").focus();
						$("#journalsheet-widget-supplierid").select();
					}
				});

				$("#journalsheet-widget-subjectid").addClass("readonly");
				//所属部门
			  	$("#journalsheet-widget-supplierdeptid").widget({
			  		width:120,
					name:'t_js_matcostsinput',
					col:'supplierdeptid',
					singleSelect:true
				});

			  	$("#journalsheet-widget-supplierdeptid").addClass("readonly");
				
				//客户名称
				$("#journalsheet-widget-customerid").customerWidget({
					name:'t_js_matcostsinput',
					col:'customerid',
					singleSelect:true,
					isall:true,
					onSelect:function(data){
						$(this).select();
			   			$(this).blur();
	            $("#journalsheet-number-factoryamount").focus();
	            $("#journalsheet-number-factoryamount").select();
					}
				});


			  	$("#journalsheet-widget-customerid").addClass("readonly");
			  	
				//品牌名称
				brandWidget();

	    		$("#report-query-brandid").addClass("readonly");
	    		
				//工厂投入
				$("#journalsheet-number-factoryamount").numberbox({
					 precision:2,
					 groupSeparator:',',
					 onChange:function(newValue,oldValue){
					 	if(""!=newValue){
						 	var htpayamount=$("#journalsheet-number-htpayamount").numberbox('getValue');
						 	if(""==htpayamount){
							 	htpayamount=0;
						 	}
						 	$("#journalsheet-number-branchaccount").numberbox('setValue',newValue-htpayamount);
					 	}else{
					 		$("#journalsheet-number-branchaccount").numberbox('setValue',0);
					 	}
	
					 	var reimburseamount=0;
					 	$("#journalsheet-number-actingmatamount").numberbox('setValue',newValue-reimburseamount);
					 }
				});
				getNumberBox("journalsheet-number-factoryamount").addClass("readonly");
				//支付金额
				$("#journalsheet-number-htpayamount").numberbox({
					 precision:2,
					 groupSeparator:',',
					 onChange:function(newValue,oldValue){
					 	var payamount=newValue;
					 	if(""==payamount){
						 	payamount=0;
					 	}
				 		var factoryamount=$("#journalsheet-number-factoryamount").numberbox('getValue');
					 	if(factoryamount!=""){
						 	$("#journalsheet-number-branchaccount").numberbox('setValue',factoryamount-payamount);
					 	}else{
					 		$("#journalsheet-number-branchaccount").numberbox('setValue',0);
					 	}
					 }
				});
				//费用
				$("#journalsheet-number-expense").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				getNumberBox("journalsheet-number-expense").addClass("readonly");
				//工厂投入代垫
				$("#journalsheet-number-factoryamount-org").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				getNumberBox("journalsheet-number-factoryamount-org").addClass("readonly");
				//费用 代垫
				$("#journalsheet-number-expense-org").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				getNumberBox("journalsheet-number-expense-org").addClass("readonly");
				$("#journalsheet-text-oaid").die("keydown").live("keydown",function(event){
					//enter
					if(event.keyCode==13){
			   			$(this).blur();
			   			$("#journalsheet-widget-subjectid").focus();
					}
				});
	
	   			$("#journalsheet-widget-subjectid").change(function(){
	   	   				$(this).blur();
			   			$("#journalsheet-widget-supplierid").focus();
			   			$("#journalsheet-widget-supplierid").select(); 	   				
	   			});
	   			$("#journalsheet-number-factoryamount").keydown(function(event){
					//enter
					if(event.keyCode==13){
						$(this).blur();
	   	   				$("#journalsheet-number-expense").focus();
	   	   				$("#journalsheet-number-expense").select();
					}
	   			});
	   			$("#journalsheet-number-expense").keydown(function(event){
					//enter
					if(event.keyCode==13){
						$(this).blur();
	   	   				$("#matcostsInput-saveAgain-saveMenu").focus();
					}
	   			});
	   			
	
	    	});
	
			//品牌名称
	    	function brandWidget(param){
	    		if(typeof param == "undefined"){
	    			param=[];
	    		}
	    		$("#report-query-brandid").widget({
	    			param:param,
					name:'t_js_reimburseinput',
					col:'brandid',
	        		width:124,
					singleSelect:true,
					onSelect:function(data){
						$(this).select();
			   			$(this).blur();
	            		$("#journalsheet-widget-customerid").focus();
	            		$("#journalsheet-widget-customerid").select();
	        		}
	    		});
			}
	</script>
  </body>
</html>
