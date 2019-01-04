<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员考核扩展新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="branduserAssessExtend-form-add" style="padding: 10px;">
	    			<input id="report-check-branduserAssessExtend" type="hidden" name="check" value="0"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpassamount1" value="${branduserAssessExtend.totalpassamount1 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpassamount2" value="${branduserAssessExtend.totalpassamount2 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpassamount3" value="${branduserAssessExtend.totalpassamount3 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpassamount4" value="${branduserAssessExtend.totalpassamount4 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpasssubamount1" value="${branduserAssessExtend.totalpasssubamount1 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpasssubamount2" value="${branduserAssessExtend.totalpasssubamount2 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpasssubamount3" value="${branduserAssessExtend.totalpasssubamount3 }"/>
	    			<input type="hidden" name="branduserAssessExtend.totalpasssubamount4" value="${branduserAssessExtend.totalpasssubamount4 }"/>
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="80px" align="left">日期:</td>
	    					<td align="left"><input type="text" name="branduserAssessExtend.businessdate" style="width:120px" onclick="WdatePicker({dateFmt:'yyyy-MM',minDate:'1958-01',maxDate:'%y-%M'})" value="${branduserAssessExtend.businessdate }" readonly="readonly"/></td>
	    					<td width="80px" align="right">销售区域:</td>
	    					<td align="left"><input id="report-widget-salesarea" type="text" style="width: 120px;" name="branduserAssessExtend.salesarea" value="${branduserAssessExtend.salesarea }"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">品牌业务员:</td>
	    					<td align="left"><input id="report-widget-branduser" type="text" style="width: 120px;" name="branduserAssessExtend.branduser"  value="${branduserAssessExtend.branduser }"/></td>
	    					<td align="left">回笼指标:</td>
	    					<td align="left"><input id="report-numberbox-wdtargetamount" type="text" name="branduserAssessExtend.wdtargetamount" value="${branduserAssessExtend.wdtargetamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" onfocus="this.select();frm_focus('wdtargetamount');" onblur="frm_blur('wdtargetamount');"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">其他回笼:</td>
	    					<td align="left"><input id="report-numberbox-otherwdamount" type="text" name="branduserAssessExtend.otherwdamount" value="${branduserAssessExtend.otherwdamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" onfocus="this.select();frm_focus('otherwdamount');" onblur="frm_blur('otherwdamount');"/></td>
	    					<td align="left">kpi目标:</td>
	    					<td align="left"><input id="report-numberbox-kpitargetamount" type="text" name="branduserAssessExtend.kpitargetamount" value="${branduserAssessExtend.kpitargetamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" onfocus="this.select();frm_focus('kpitargetamount');" onblur="frm_blur('kpitargetamount');"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">kpi奖金:</td>
	    					<td align="left"><input id="report-numberbox-kpibonusamount" type="text" name="branduserAssessExtend.kpibonusamount" value="${branduserAssessExtend.kpibonusamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" onfocus="this.select();frm_focus('kpibonusamount');" onblur="frm_blur('kpibonusamount');"/></td>
	    					<td align="left">区长:</td>
	    					<td align="left"><input id="report-numberbox-wardenamount" type="text" name="branduserAssessExtend.wardenamount" value="${branduserAssessExtend.wardenamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:0,groupSeparator:','" onfocus="this.select();frm_focus('wardenamount');" onblur="frm_blur('wardenamount');"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">备注:</td>
	    					<td align="left" colspan="3">
	    						<input id="report-textarea-remark" type="text" name="branduserAssessExtend.remark" value="${branduserAssessExtend.remark }" class="easyui-validatebox" validType="maxLen[200]" style="width: 340px;" onfocus="this.select();frm_focus('remark');" onblur="frm_blur('remark');"/>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="branduserAssessExtend-saveAgain-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="继续添加品牌业务员考核">继续添加</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
   
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
    
    	//继续添加品牌业务员考核
    	$("#branduserAssessExtend-saveAgain-saveMenu").click(function(){
    		var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
    		if(!$("#branduserAssessExtend-form-add").form('validate')){
       			return false;
       		}
       		$("#report-check-branduserAssessExtend").val("1");
	    	var addform = $("#branduserAssessExtend-form-add").serializeJSON();
	    	addform['businessdate'] = queryJSON.businessdate;
	    	addform['branduser'] = queryJSON.branduser;
	    	loading("提交中..");
	    	$.ajax({   
	            url :'report/finance/addBranduserAssessExtend.do',
	            type:'post',
	            dataType:'json',
	            data:addform,
	            success:function(retJson){
	            	loaded();
		            if(retJson.flag){
		        		$.messager.alert("提醒","覆盖成功!");
		        		var index = parseInt("${rowindex}");
		        		var row = $("#report-datagrid-branduserAssess").datagrid('selectRow',index);
		        		$("#report-datagrid-branduserAssess").datagrid('updateRow',{
			    			index: index,
			    			row: retJson.dataObject
			    		});
			    		
			    		//合计
			    		SR_footerobject = retJson.footObject;
			    		
			    		var nextindex = index + Number(1);
		        		$("#report-datagrid-branduserAssess").datagrid('selectRow',nextindex);
						$("#report-datagrid-branduserAssess").datagrid('unselectRow',index);
						var nextrow = $("#report-datagrid-branduserAssess").datagrid('getSelected');
						var businessdate = $("#businessdate").val();
	   					var url = 'report/finance/showBranduserAssessExtendPage.do?branduser='+nextrow.branduser+'&rowindex='+nextindex+'&businessdate='+businessdate;
						$("#report-dialog-branduserAssessInfo").dialog({
							title:'编辑品牌业务员分月考核信息',
				    		width:500,
				    		height:240,
				    		closed:true,
				    		modal:true,
				    		cache:false,
				    		resizable:true,
						    href: url,
						    onLoad:function(data){
						    	$("#report-numberbox-wdtargetamount").focus();
						    }
						});
						$("#report-dialog-branduserAssessInfo").dialog("open");
		        	}
		        	else{
		        		$.messager.alert("提醒","覆盖失败!");
		        	}
	            }
	        });
    	});
    	
    	$(function(){
			//销售区域
    		$("#report-widget-salesarea").widget({
    			width:120,
				referwid:'RT_T_BASE_SALES_AREA_LIST',
				singleSelect:true,
				readonly:true
    		});
			//品牌业务员
		  	$("#report-widget-branduser").widget({
		  		width:120,
				referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
				singleSelect:true,
				readonly:true
			});
    	});
    </script>
  </body>
</html>
