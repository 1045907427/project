<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>预算分析页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
   		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<form id="journalsheet-form-budgetEdit" method="post" style="margin-top: 30px;margin-left: 30px">
					<table style="border-collapse: collapse;" border="0" cellpadding="5" cellspacing="5">
						<tr>
				    		<td style="text-align: right;">预算编码：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-budgetid"  class="len150 readonly" readonly="readonly"  type="text"  name="bugetAnalyzer.budgetid" />
				    		</td>
				    		<td style="text-align: right;" >状态：</td>
				    		<td>
				    			<select id="journalsheet-budgetEdit-state" disabled = "disabled"  name="bugetAnalyzer.state" style="width: 150px">
				    				<option value = '0'>禁用</option>
				    				<option value = '1'>启用</option>
				    			</select>
				    		</td>
				    	</tr>
				    	
				    	<tr>
				    		<td style="text-align: right;">品牌：</td>
				    		<td>																				
				    			<input id="journalsheet-budgetEdit-brand" type="text" class="len150 "  name="bugetAnalyzer.brand"  value="${bugetAnalyzer.brand }" text="<c:out value="${bugetAnalyzer.brandname}" ></c:out>"/>
				    		</td>
				    		<td style="text-align: right;">供应商：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-supplierid" type="text" class="len150 "  name="bugetAnalyzer.supplierid" value="${bugetAnalyzer.supplierid }" text="<c:out value="${bugetAnalyzer.suppliername}" ></c:out>"/>
				    		</td>
				    	</tr>
				    	
				    	<tr>
				    		<td style="text-align: right;" >部门：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-deptid" type="text" class="len150"  name="bugetAnalyzer.deptid" value="${bugetAnalyzer.deptid }" text="<c:out value="${bugetAnalyzer.deptname}" ></c:out>"/> 
				    		</td>
				    		<td style="text-align: right;" >预算类型：</td>
				    		<td>
				    			<select id="journalsheet-budgetEdit-bugettype" name = "bugetAnalyzer.bugettype" class="len150 " required = "true" >
				    				<option></option>
				    				<c:forEach items="${budgettypeList}" var="budget">
										<option value="${budget.code}">${budget.codename}</option>
									</c:forEach>
				    			</select>
				    		</td>
				    	</tr>
						
						<tr>
				    		<td style="text-align: right;" >年月：</td>
				    		<td><input id="journalsheet-budgetEdit-yearMonth" type="text" required='required' name="bugetAnalyzer.yearMonth" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="${bugetAnalyzer.yearMonth}"/> </td>
				    		<td style="text-align: right;" >预算金额：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-budgetnum" name="bugetAnalyzer.budgetnum" value = "0.00"/> 
				    		</td>
				    	</tr>
						
						<tr>
				    		<td style="text-align: right;">录入日期：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-addtime" type="text" class="len150 readonly" readonly="readonly"   />
				    		</td>
				    		<td style="text-align: right;" >录入人：</td>
				    		<td>
				    			<input id="journalsheet-budgetEdit-addusername" type="text" class="len150 readonly" readonly="readonly"  /> 
				    		</td>
				    	</tr>
				    	
				    	<tr>
				    		<td style="text-align: right;" colspan="1">备注：</td>
				    		<td colspan="4">
				    			<textarea  id="journalsheet-budgetEdit-remark"   class="len350" style="width: 380px" name ="bugetAnalyzer.remark"  />
				    		</td>
				    	</tr>
						
					</table>
				</form>
			</div>
			<div data-options="region:'south',border:false" style="margin-bottom: 25px;margin-right: 26px">
				<div class="buttonDetailBG" style="text-align: right;">
					<input  type="button" value="保存" name="savenogo"  id="journalsheet-budgetEdit-saveData" />
					<input  type="button" value="关闭" name="close"  id="journalsheet-budgetEdit-closeDlg" />
				</div>
			</div>
		</div>
	
   <script type="text/javascript">
   	$(function(){
   		$('#journalsheet-budgetEdit-budgetnum').numberbox({
		    min:0,
		    precision:2,
		    required:true
		});
   		//品牌名称
   		$("#journalsheet-budgetEdit-brand").widget({
   			width:150,
			name:'t_base_goods_brand',
			col:'id',
			singleSelect:true,
			onlyLeafCheck:false,
			required:true,
			onSelect:function(data){
			
				if(data.supplierid&&data.deptid){
    					$("#journalsheet-budgetEdit-supplierid").supplierWidget("setValue",data.supplierid);
    					$("#journalsheet-budgetEdit-supplierid").supplierWidget("setText",data.suppliername);
    					
    					$("#journalsheet-budgetEdit-deptid").val(data.buydeptid);
		  				$("#journalsheet-budgetEdit-deptid").widget("setValue",data.deptid);
    			}else{
    					$("#journalsheet-budgetEdit-supplierid").supplierWidget("clear"); 		
    					$("#journalsheet-budgetEdit-deptid").widget("clear"); 	
    			}
			}
   		});
   		
   		//供应商
   		$("#journalsheet-budgetEdit-supplierid").supplierWidget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				
			}
		});
   		
   		//部门
		$("#journalsheet-budgetEdit-deptid").widget({ 
    			name:'t_sales_order',
				col:'salesdept',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:false
    	});
   		
   		
   		
   		if("${type}"=="add"){
   			//新增
   			var date=new Date;
   			var year=date.getFullYear()
   			var month=date.getMonth()+1+"";
   			if(month.length==1){
   				month = "0"+month;
   			}
   			$("#journalsheet-budgetEdit-yearMonth").val(year+"-"+month)
   		}else{
   			//禁用状态可编辑
   				//编码
   				$("#journalsheet-budgetEdit-budgetid").val("${bugetAnalyzer.budgetid}")
   				//状态
   				$("#journalsheet-budgetEdit-state option").eq(parseInt("${bugetAnalyzer.state}")).attr("selected","selected")
   				//预算类型
   				$("#journalsheet-budgetEdit-bugettype option").eq(parseInt("${bugetAnalyzer.bugettype}")+1).attr("selected","selected")
   				//月份
   				$("#journalsheet-budgetEdit-yearMonth").val("${bugetAnalyzer.yearMonth}")
   				//录入人,录入日期
   				$("#journalsheet-budgetEdit-addtime").val("${addtime}")
   				$("#journalsheet-budgetEdit-addusername").val("${bugetAnalyzer.addusername}")
   				//预算金额
   				$("#journalsheet-budgetEdit-budgetnum").numberbox("setValue","${bugetAnalyzer.budgetnum}")
   				//备注
   				$("#journalsheet-budgetEdit-remark").val("${bugetAnalyzer.remark}")
   		    if("${bugetAnalyzer.state}"==0&&!"${type}"=="view"){
   		  	   	//状态
   				$("#journalsheet-budgetEdit-state").removeAttr("disabled")
   		    }	
   				
   		    if("${bugetAnalyzer.state}"==1||"${type}"=="view"){
   		    	//启用状态,type=view不可编辑
   				$("#journalsheet-budgetEdit-brand").attr('disabled','disabled')
   				$("#journalsheet-budgetEdit-supplierid").attr('disabled','disabled')
   				$("#journalsheet-budgetEdit-deptid").attr('disabled','disabled')
   				$("#journalsheet-budgetEdit-bugettype").attr('disabled','disabled')
   				$("#journalsheet-budgetEdit-yearMonth").removeAttr('class')
   				$("#journalsheet-budgetEdit-yearMonth").attr('disabled','disabled')
   				$("#journalsheet-budgetEdit-remark").attr('disabled','disabled')
   				$('#journalsheet-budgetEdit-budgetnum').numberbox('disable')
   				$("#journalsheet-budgetEdit-saveData").attr('hidden',true)
   		   }
   		}
   		$("#journalsheet-budgetEdit-closeDlg").click(function(){
			 parent.$.dialog.dialog('close')
   		})
   		
   		
   		//保存
   		$("#journalsheet-budgetEdit-saveData").click(function(){
   			//前台校验
	   		var flag = $("#journalsheet-form-budgetEdit").form('validate');
	   		if(flag){
		   		if(!$("#journalsheet-budgetEdit-bugettype").val()){
		   			$.messager.alert("提醒","请选择预算类型");
		   			flag=false;
		   		}
	   		}
	   		if(!flag){
	   			return false;
	   		}
   			//新增的保存
   			if("${type}"=="add"){
		   			//新增
		   			loading()
		   			$.ajax({
		   				url:"journalsheet/bugetAnalyzer/addBuget.do",
		   				data:$("#journalsheet-form-budgetEdit").serialize(),
		   				type:'post',
		   				dataType:'json',
		   				success:function(r){
							loaded();
							if(r.flag){
								$.messager.alert("提醒", r.msg);
								parent.$.dialog.dialog('close')
								parent.$.dg.datagrid('reload')
							}else{
								$.messager.alert("提醒", "录入失败");
		   					}
		   				}	
		   			})
   			}
   			//修改的保存
   			if("${type}"=="edit"){
		   			//修改保存
		   			loading()
		   			$.ajax({
		   				url:"journalsheet/bugetAnalyzer/editBugetSave.do",
		   				data:$("#journalsheet-form-budgetEdit").serialize(),
		   				type:'post',
		   				dataType:'json',
		   				success:function(r){
							loaded();
							if(r.flag){
								$.messager.alert("提醒", r.msg);
								parent.$.dialog.dialog('close')
								parent.$.dg.datagrid('reload')
							}else{
								$.messager.alert("提醒", r.msg);
		   					}
		   				}	
		   			})
	   		}
   		})
   			
   	})
   </script>
   
  </body>
</html>
