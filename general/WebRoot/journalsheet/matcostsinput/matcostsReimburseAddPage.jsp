<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收回新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div>
    			<form action="" method="post" id="matcostsReimburse-form-add" style="padding: 10px;">
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="70px" align="right">业务日期:</td>
	    					<td align="left" colspan="3"><input id="matcostsReimburse-date-businesstime" type="text" name="matcostsInput.businessdate" class="easyui-validatebox" required="true" value="${busdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
			   				   					
	    				</tr>
	    				<tr>	    					
	    					<td align="right">供应商:</td>
	    					<td align="left" colspan="3"><input id="matcostsReimburse-widget-supplierid" type="text" name="matcostsInput.supplierid"  style="width:320px;" /></td>	    					
	    					
	    				</tr>
	    				<tr>
	    					<td width="70px" align="right">所属部门:</td>
	    					<td align="left"><input id="matcostsReimburse-widget-supplierdeptid" name="matcostsInput.supplierdeptid" type="text" style="width: 120px;"/></td>	    					
	    					<td width="70px" align="right">收回方式:</td>
	    					<td align="left"><input id="matcostsReimburse-widget-reimbursetype" name="matcostsInput.reimbursetype" type="text" style="width:120px;"/></td>
	    				</tr>
	    				<tr>	    					
	    					<td width="70px" align="right">银行名称:</td>
	    					<td align="left">
	    						<div id="matcostsReimburse-widget-bankid-div"></div>
	    					</td>	    					
	    					<td align="right">收回科目:</td>
	    					<td align="left"><input id="matcostsReimburse-widget-shsubjectid" name="matcostsInput.shsubjectid" type="text" style="width:120px;" /></td>
	    				</tr>
	    				<tr>
	    					<td align="right">单位:</td>
	    					<td align="left"><input id="matcostsReimburse-text-unitid" name="matcostsInput.unitid" type="text" style="width:120px;"/></td>	    					
	    					<td align="right">数量:</td>
	    					<td align="left"><input id="matcostsReimburse-text-unitnum" name="matcostsInput.unitnum" type="text" style="width:120px;" class="easyui-validatebox" required="true" validType="intOrFloatNum[${decimallen}]" value="1"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">单价:</td>
	    					<td align="left"><input id="matcostsReimburse-text-taxprice" name="matcostsInput.taxprice" type="text" style="width:120px;" class="easyui-validatebox" required="true" validType="intOrFloat" value="0.0000"/></td>
	    					<td align="right">金额:</td>
	    					<td align="left"><input id="matcostsReimburse-text-reimburseamount" name="matcostsInput.reimburseamount" type="text" style="width:120px;" class="easyui-validatebox" required="true" validType="intOrFloat"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">备注:</td>
	    					<td align="left" colspan="3">
	    						<textarea id="matcostsReimburse-text-remark" name="matcostsInput.remark" class="easyui-validatebox" validType="maxLen[200]" style="width: 320px;height:60px;"></textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'">
            <div class="buttonDetailBG" style="height:40px;text-align:right;">
                <input type="button" name="savegoon" id="matcostsReimburse-save-saveMenu" value="确定"/>
                <input type="button" name="savegoon" id="matcostsReimburse-saveAgain-saveMenu" value="继续添加"/>
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
        	//添加代垫录入
        	$("#matcostsReimburse-save-saveMenu").click(function(){
        		var reimbursetype=$("#matcostsReimburse-widget-reimbursetype").widget('getValue');
        		if(null==reimbursetype || "" == reimbursetype){
            		$.messager.alert("提醒","请填写收回方式");
            		return ;
        		}
        		
            	if(!$("#matcostsReimburse-form-add").form('validate')){
           			return false;
           		}
            	var reimburseamount=$("#matcostsReimburse-number-reimburseamount").widget('getValue');
            	if(reimburseamount==""){
            		$("#matcostsReimburse-number-reimburseamount").widget('setValue',0);
            	}
           		
           		var ret = matcostsReimburse_AjaxConn($("#matcostsReimburse-form-add").serializeJSON(),'journalsheet/matcostsInput/addMatcostsReimburse.do','提交中..');
            	var retJson = $.parseJSON(ret);
            	if(retJson.flag){
            		$.messager.alert("提醒","新增成功!");
            		$("#matcostsReimburse-query-List").trigger("click");
            		$('#matcostsReimburse-dialog-operate').dialog('close');
            	}
            	else{
            		$.messager.alert("提醒","新增失败!");
            	}
        	});
        	//继续添加代垫录入
        	$("#matcostsReimburse-saveAgain-saveMenu").click(function(){

        		var reimbursetype=$("#matcostsReimburse-widget-reimbursetype").widget('getValue');
        		if(null==reimbursetype || "" == reimbursetype){
            		$.messager.alert("提醒","请填写收回方式");
            		return ;
        		}
        		if(!$("#matcostsReimburse-form-add").form('validate')){
           			return false;
           		}
           		
           		var ret = matcostsReimburse_AjaxConn($("#matcostsReimburse-form-add").serializeJSON(),'journalsheet/matcostsInput/addMatcostsReimburse.do','提交中..');
            	var retJson = $.parseJSON(ret);
            	if(retJson.flag){
            		$.messager.alert("提醒","新增成功!");
            		$("#matcostsReimburse-query-List").trigger("click");
            		
        			$('#matcostsReimburse-dialog-operate').dialog('close');
        			
    				refreshLayout('代垫收回【新增】', 'journalsheet/matcostsInput/showMatcostsReimburseAddPage.do');
            	}
            	else{
            		$.messager.alert("提醒","新增失败!");
            	}
        	});
        	
    		//供应商
		  	$("#matcostsReimburse-widget-supplierid").supplierWidget({
				name:'t_js_matcostsinput',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
		  		onSelect:function(data){
		  			if(data.buydeptid != null){
		  				$("#matcostsReimburse-widget-supplierdeptid").widget("setValue",data.buydeptid);
		  			}
					$(this).select();
		   			$(this).blur();
					$("#matcostsReimburse-widget-reimbursetype").focus();
					$("#matcostsReimburse-widget-reimbursetype").select();
		  		},
		  		onClear:function(){
	  				$("#matcostsReimburse-widget-supplierdeptid").widget("clear");		  			
		  		}
			});
			//所属部门
		  	$("#matcostsReimburse-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_matcostsinput',
				col:'supplierdeptid',
				required:true,
				singleSelect:true
			});
			showBankidWidget(false,'');
		  //收回方式
			$("#matcostsReimburse-widget-reimbursetype").widget({
		  		width:120,
				name:'t_js_matcostsinput',
				col:'reimbursetype',
				singleSelect:true,
				initSelectNull:true,
				onSelect:function(data){
					var bankid=$("#matcostsReimburse-widget-bankid").widget('getValue');
					if(data.id=='1'){
						showBankidWidget(true,bankid);
					}else{
						showBankidWidget(false,bankid);
					}
					$(this).blur();
					$("#matcostsReimburse-widget-bankid").focus();
					$("#matcostsReimburse-widget-bankid").select(); 
				}
			});
		  	
			//收回科目
		    $("#matcostsReimburse-widget-shsubjectid").widget({
		        referwid:'RL_T_BASE_SUBJECT',
		        width:120,
		        singleSelect:true,
		        onlyLeafCheck:false,
				treePName:false,
				treeNodeDataUseNocheck:true,
				param:[
		  			{field:'typecode',op:'equal',value:'DDREIMBURSE_SUBJECT'}
		  		],
				onSelect:function(){
					$(this).blur();
		   			$("#matcostsReimburse-text-unitid").focus();
		   			$("#matcostsReimburse-text-unitid").select(); 
				}
		    });

	  		//主单位
	  		$("#matcostsReimburse-text-unitid").widget({
	   			referwid:'RL_T_BASE_GOODS_METERINGUNIT',
	   			singleSelect:true,
    			width:120,
	   			onlyLeafCheck:true,
	   			onSelect:function(){
					$("#matcostsReimburse-text-unitnum").focus();
                    $("#matcostsReimburse-text-unitnum").select();
	   			}
	   		});
	  		
	  		$("#matcostsReimburse-text-unitnum").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#matcostsReimburse-text-taxprice").focus();
                    $("#matcostsReimburse-text-taxprice").select();
                }
            });
    		$("#matcostsReimburse-text-taxprice").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#matcostsReimburse-text-reimburseamount").focus();
                    $("#matcostsReimburse-text-reimburseamount").select();
                }
            });
            $("#matcostsReimburse-text-reimburseamount").bind('keydown',function(e){
                if(e.keyCode == 13 && $(this).validatebox('isValid')){
                    $("#matcostsReimburse-text-remark").focus();
                    $("#matcostsReimburse-text-remark").select();
                }
            });

            $("#matcostsReimburse-text-remark").bind('keydown',function(e){
                if(e.keyCode == 13 && $("#deptIncome-form-detailAdd").form('validate')){
					$(this).blur();
					$("#deptIncome-detail-addgobutton").focus();
                    $("#deptIncome-detail-addgobutton").click();
                }
            });

  			$("#matcostsReimburse-text-reimburseamount").change(function(){
    			if($(this).validatebox('isValid')){
    				computeUnitnumPriceAmountChange("1");
    			}
    		});
  			$("#matcostsReimburse-text-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeUnitnumPriceAmountChange("2");
    			}
    		});
  			$("#matcostsReimburse-text-taxprice").change(function(){
    			if($(this).validatebox('isValid')){
    				computeUnitnumPriceAmountChange("3");
    			}
    		});
    	});
    </script>
  </body>
</html>
