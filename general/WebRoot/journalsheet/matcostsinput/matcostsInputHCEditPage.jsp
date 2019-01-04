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
    			<form action="" method="post" id="matcostsInput-form-add" style="padding: 10px;">
	    			<input type="hidden" name="matcostsInput.id" value="${matcostsInput.id }" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	                        <td class="len80 right">业务日期:</td>
	                        <td class="len150 left"><input id="journalsheet-date-businesstime" type="text" name="matcostsInput.businessdate" value="${matcostsInput.businessdate }" class="easyui-validatebox Wdate" required="true" value="${businessdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'2000-01-01'})"/></td>
							<td class="right">关联代垫:</td>
							<td align="left">
								<input type="text" class="readonly" readonly="readonly" name="matcostsInput.hcreferid" value="${matcostsInput.hcreferid }"  style="width:120px; font-family: sans-serif;"/>
							</td>
						</tr>
	                    <tr>
	                        <td class="right">支付日期:</td>
	                        <td class="left"><input id="journalsheet-date-paydate" type="text" class="Wdate readonly" value="${matcostsInput.paydate }" style="width:120px"/></td>
							 <td class="len70 right">收回日期:</td>
	                        <td class="left"><input id="journalsheet-date-takebackdate" type="text" class="Wdate readonly" value="${matcostsInput.takebackdate }" style="width:120px"/></td>
	                    </tr>
	                    <tr>
	                        <td class="len80 right">OA编号:</td>
	                        <td class="len150 left"><input id="journalsheet-text-oaid" type="text" name="matcostsInput.oaid" value="${matcostsInput.oaid }" style="width:120px" class="readonly" readonly="readonly" /></td>
							<td class="len70 left">科目名称:</td>
							<td align="left"><input id="journalsheet-widget-subjectid" type="text" name="matcostsInput.subjectid" value="${matcostsInput.subjectid }" style="width: 130px" class="readonly" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="right">供应商:</td>
	                        <td class="left" colspan="3"><input id="journalsheet-widget-supplierid" type="text" name="matcostsInput.supplierid" value="${matcostsInput.supplierid }" text="${matcostsInput.suppliername}" style="width:340px;" class="readonly" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="right">所属部门:</td>
		    					<td class="left"><input id="journalsheet-widget-supplierdeptid" name="matcostsInput.supplierdeptid" type="text" style="width: 120px;" class="readonly" readonly="readonly" /></td>
	                        <td class="left">商品品牌:</td>
	                        <td class="left"><input type="text" id="report-query-brandid" name="matcostsInput.brandid" value="${matcostsInput.brandid }"  class="readonly" readonly="readonly"  /></td>
		    				</tr>
		    				<tr>	    					
	                        <td class="right">客户名称:</td>
	                        <td class="left" colspan="3"><input id="journalsheet-widget-customerid" type="text" style="width: 340px;" name="matcostsInput.customerid" value="${matcostsInput.customerid }" text="${matcostsInput.customername }" class="readonly" readonly="readonly" /></td>
	                    </tr>
						<tr>
							<td class="right">工厂投入:</td>
							<td class="left"><input id="journalsheet-number-factoryamount" type="text" style="width: 120px;" name="matcostsInput.factoryamount" value="${matcostsInput.factoryamount }"/></td>	
							<td class="left">费用金额:</td>
							<td class="left"><input id="journalsheet-number-expense" type="text" style="width: 120px;" name="matcostsInput.expense"  value="${matcostsInput.expense }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	                    </tr>
	                    <tr style="display:none;">
	                    	<td class="right">代垫金额:</td>
							<td class="left" colspan="3"><input id="journalsheet-number-actingmatamount" readonly="readonly" type="text" style="width: 340px;" name="matcostsInput.actingmatamount"  value="${matcostsInput.actingmatamount }"  class="easyui-numberbox readonly" data-options="precision:2,groupSeparator:','"/></td>
	                    </tr>
	                    <tr>
	                        <td class="right">备注:</td>
	                        <td align="left" colspan="3">
	                            <textarea id="journalsheet-text-remark" name="matcostsInput.remark" style="width: 340px;height: 50px;">${matcostsInput.remark }</textarea>
	                        </td>
	                    </tr>
	                </table>
                	<div style="display:none">
						<input id="journalsheet-number-htcompdiscount" type="hidden" style="width: 120px;" name="matcostsInput.htcompdiscount" value="${matcostsInput.htcompdiscount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
						<input id="journalsheet-number-htpayamount" type="hidden" style="width: 130px;" name="matcostsInput.htpayamount" value="${matcostsInput.htpayamount }"/>
						<input id="journalsheet-number-branchaccount" type="hidden"  disabled="disabled"  style="width: 120px;" name="matcostsInput.branchaccount" value="${matcostsInput.branchaccount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
						<input id="journalsheet-text-hcflag" type="hidden" name="matcostsInput.hcflag" value="${matcostsInput.hcflag }" />
					</div>
	    		</form>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 30px;border: 0px;">
        <div align="right">
            <a href="javaScript:void(0);" id="matcostsInput-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" title="添加代垫录入">确定</a>
        </div>
    </div>
</div>

    <script type="text/javascript">
    	function editMatcostsInput(){
    		var factoryamount=$("#journalsheet-number-factoryamount").numberbox('getValue');
			/**瑞家可以为负数
    		if(factoryamount<0){
        		$.messager.alert("提醒","修改代垫录入时，工厂投入不能为负数。<br/>如确定为负数，请删除本条记录并在代垫收回处新增!");
        		return false;
        	}
			**/
        	var htcompdiscount=$("#journalsheet-number-htcompdiscount").numberbox('getValue');
        	var htpayamount=$("#journalsheet-number-htpayamount").numberbox('getValue');
    		if((factoryamount=="" || factoryamount==0) && (htcompdiscount=="" || htcompdiscount==0) && (htpayamount=="" || htpayamount==0)){        	
	    		//$("#journalsheet-text-oaid").validatebox({required:true});
	    		$("#journalsheet-widget-customerid").validatebox({required:true});
    		}else{
	    		//$("#journalsheet-text-oaid").validatebox({required:false});
	    		$("#journalsheet-widget-customerid").validatebox({required:false});
    		}
    		if(!$("#matcostsInput-form-add").form('validate')){
       			return false;
       		}
       		if(htcompdiscount==""){
       			$("#journalsheet-number-htcompdiscount").numberbox('setValue',0);
       		}
       		if(htpayamount==""){
       			$("#journalsheet-number-htpayamount").numberbox('setValue',0);
       		}
       		
       		var ret = matcostsInput_AjaxConn($("#matcostsInput-form-add").serializeJSON(),'journalsheet/matcostsInput/editMatcostsInput.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","修改成功!");
        		$("#journalsheet-table-matcostsInput").datagrid('reload');
        		$('#matcostsInput-dialog-operate-content').dialog('close');
        	}
        	else{
            	if(retJson.msg){
        			$.messager.alert("提醒","修改失败!"+retJson.msg);
            	}else{
        			$.messager.alert("提醒","修改失败!");
            	}
        	}
    	}
    	$(function(){
    		//供应商
		  	$("#journalsheet-widget-supplierid").supplierWidget({
				name:'t_js_matcostsinput',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:'${matcostsInput.supplierid}',
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
	  				$("#journalsheet-widget-supplierdeptid").widget("clear");		  			
		  		}
			});
			$("#journalsheet-widget-supplierid").addClass("readonly");
			//科目编码
			$("#journalsheet-widget-subjectid").widget({
		  		width:120,
				name:'t_js_matcostsinput',
				col:'subjectid',
				singleSelect:true,
				initValue:'${matcostsInput.subjectid}',
				onSelect:function(data){
					$("#journalsheet-widget-supplierid").focus();
					$("#journalsheet-widget-supplierid").select();
				}
			});
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_matcostsinput',
				col:'supplierdeptid',
				initValue:'${matcostsInput.supplierdeptid}',
				singleSelect:true
			});

		  	$("#journalsheet-widget-supplierdeptid").addClass("readonly");
		  	
		  //客户名称
			$("#journalsheet-widget-customerid").customerWidget({
				name:'t_js_matcostsinput',
				col:'customerid',
				singleSelect:true,
				isall:true,
				initValue:'${matcostsInput.customerid}',
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

			//收回金额
			$("#journalsheet-number-reimburseamount").numberbox({
				 precision:2,
				 groupSeparator:','
			});

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
   			$("#journalsheet-number-factoryamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$(this).blur();
   	   				$("#journalsheet-number-expense").focus();
   	   				$("#journalsheet-number-expense").select();
				}
   			});
   			$("#journalsheet-number-expense").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$(this).blur();
   	   				$("#matcostsInput-saveAgain-saveMenu").focus();
				}
   			});
	    	//修改代垫录入
	    	$("#matcostsInput-save-saveMenu").click(function(){
	    		if(!$("#matcostsInput-form-add").form('validate')){
	       			return false;
	       		}
	    		$.messager.confirm("提醒","是否确认修改代垫录入?",function(r){
	  				if(r){
	  					editMatcostsInput();
	  				}
	    		});
	    		
	    	});

	    	//检验输入值的最大长度
			$.extend($.fn.validatebox.defaults.rules, {
				maxLen:{
		  			validator : function(value,param) { 
			            return value.length <= param[0]; 
			        }, 
			        message : '最多可输入{0}个字符!' 
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
