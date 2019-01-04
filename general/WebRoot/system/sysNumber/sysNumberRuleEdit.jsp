<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>单据编号规则修改信息</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',fit:true" align="center">
			<form action="" method="post" id="sysNumberRule-form-addNumberRule">
				<input id="sysNumberRule-select-hdsubtype" name="sysNumberRule.subtype" value="${sysNumberRule.subtype }" type="hidden" />
				<input id="system-valName-sysNumberRule" name="sysNumberRule.valName" type="hidden"/>
				<input name="tablename" type="hidden" value="${tablename }"/>
				<input name="sysNumberRule.numberruleid" type="hidden" value="${sysNumberRule.numberruleid}"/>
				<input id="system-coldatatype-sysNumberRule" type="hidden"/>
		    	<table cellpadding="2" cellspacing="2" border="0">
		    		<tr>
		    			<td><label>类型:</label></td>
		    			<td><select id="system-coltype-sysNumberRule" name="sysNumberRule.coltype" style="width:120px;">
		    				<option value="1" <c:if test="${sysNumberRule.coltype == '1'}">selected="selected"</c:if>>固定值</option>
		    				<option value="2" <c:if test="${sysNumberRule.coltype == '2'}">selected="selected"</c:if>>字段</option>
		    				<option value="3" <c:if test="${sysNumberRule.coltype == '3'}">selected="selected"</c:if>>系统日期</option>
		    			</select></td>
				    	<td><label>值:</label></td>
		    			<td>
			    			<div id="sysNumber-div-inputhtml">
			    				<input type="text" id="system-fixedval-sysNumberRule" class="easyui-validatebox" validType="fixedval" name="sysNumberRule.val" value="${sysNumberRule.val }" required="true" style="width: 120px;" maxlength="20"/>
			    			</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td><label>排序:</label></td>
		    			<td>
		    				<input type="text" id="sysNumber-sysNumberRule-sequencing" name="sysNumberRule.sequencing" value="${sysNumberRule.sequencing }" required="true" validType="validIntOnly" style="width:120px;" class="easyui-numberbox" data-options="min:1,max:999"/>
		    			</td>
		    			<td><label>前缀:</label></td>
		    			<td><input type="text" name="sysNumberRule.prefix" value="${sysNumberRule.prefix }" style="width:120px;"/></td>
		    		</tr>
		    		<tr>
		    			<td><label>后缀:</label></td>
		    			<td><input type="text" name="sysNumberRule.suffix" value="${sysNumberRule.suffix }" style="width:120px;"/></td>
		    			<td><label>长度:</label></td>
		    			<td><input type="text" id="sysNumber-sysNumberRule-length" <c:if test="${sysNumberRule.coltype == '1'}">readonly="readonly"</c:if> value="${sysNumberRule.length }" name="sysNumberRule.length" style="width: 120px;" class="easyui-validatebox" validType="numspaceone"/></td>
		    		</tr>
		    		<tr>
		    			<td><label>截取方向:</label></td>
		    			<td><select id="sysNumberRule-select-subtype" disabled="disabled" style="width: 120px;">
				    				<option value="1" <c:if test="${sysNumberRule.subtype == '1'}">selected="selected"</c:if>>从前向后</option>
				    				<option value="2" <c:if test="${sysNumberRule.subtype == '2'}">selected="selected"</c:if>>从后向前</option>
				    			</select></td>
		    			<td><label>截取开始位置:</label></td>
		    			<td><input type="text" id="sysNumber-sysNumberRule-substart" readonly="readonly" name="sysNumberRule.substart" value="${sysNumberRule.substart }" style="width: 120px;" class="easyui-validatebox" validType="numspacezore"/></td>
		    		</tr>
		    		<tr>
		    			<td><label>补位符:</label></td>
		    			<td><input id="system-cover-sysNumberRule" type="text" name="sysNumberRule.cover" value="0" readonly="readonly" style="width: 120px;"/></td>
		    			<td><label>流水依据:</label></td>
		    			<td><select id="sysNumber-sysNumberRule-state" onchange="checkRuleStateCanChange('edit')" name="sysNumberRule.state" style="width: 120px;">
			    			<option value="0">否</option>
			    			<option value="1">是</option>
			    		</select></td>
		    		</tr>
		    	</table>
		    </form>
  		</div>
  		<div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="text-align:right;">
                <input type="button" value="确定" name="savegoon" id="system-save-addNumberRule" />
            </div>
  		</div>
  	</div>
    <script type="text/javascript">
    //单据编号规则ajax提交
    function sysNumberRuleSubmit(){
    	var row = $('#system-table-sysNumberRule').datagrid('getSelected');
    	var index = $('#system-table-sysNumberRule').datagrid('getRowIndex',row);
    	$sysNumberRuleInfo = $("#sysNumberRule-form-addNumberRule");
    	var sysNumberRuleJson = $sysNumberRuleInfo.serializeJSON();
		var ret = sysNumber_AjaxConn(sysNumberRuleJson,'sysNumber/editSysNumberRuleToList.do');
		var retJson = $.parseJSON(ret);
		sysNumberRuleJsonStr = JSON.stringify(retJson.sysNumberRule);
		$("#system-numberruleid-sysNumberRuleList").val(retJson.sysNumberRule.numberruleid);
		$('#system-table-sysNumberRule').datagrid('updateRow',{index:index,row:retJson.sysNumberRule});
		var seqRows = $("#system-table-sysNumberRule").datagrid('getRows');
		var temp = new Object();
		for(var i=0;i<seqRows.length;i++){
			for(var j=0;j<seqRows.length;j++){
				if(seqRows[i].sequencing < seqRows[j].sequencing){
					temp = seqRows[i];
					seqRows[i]=seqRows[j];
					seqRows[j]=temp;
				}
			}
		}
		$("#system-table-sysNumberRule").datagrid('loadData',seqRows);
		//获取预览效果
		var preViewObj = new Object();
		if(seqRows.length != 0){
			preViewObj["preView"] = JSON.stringify(seqRows);
		}
		var sysNumberJson = $("#sysNumber-form-billInfo").serializeJSON();
		for(key in sysNumberJson){
			preViewObj[key] = sysNumberJson[key];
		};
		var preViewRet = sysNumber_AjaxConn(preViewObj,'sysNumber/getPreViewSysNumber.do');
		var preViewJSON = $.parseJSON(preViewRet);
		$("#system-preView-sysNumber").val(preViewJSON.preViewNo);
		getTestValueAndName(seqRows);
    }
    
	$("#sysNumberRule-select-subtype").change(function(){
		var value = $("#sysNumberRule-select-subtype option:selected").val();
		$("#sysNumberRule-select-hdsubtype").val(value);
	});

	//验证
	$.extend($.fn.validatebox.defaults.rules, {
		validIntOnly:{
			validator:function(value){
				if(value == $("#sysNumber-sysNumberRule-sequencing").numberbox("getValue")){
					return true;
				}
				var booleanreg=true;
				var rowflag = new Array();
				var rows=$("#system-table-sysNumberRule").datagrid('getRows');
				if(rows.length != 0){
					for(var i=0;i<rows.length;i++){
						if(rows[i].sequencing != value){
							rowflag[i]=true;
						}
						else{
							rowflag[i]=false;
						}
						booleanreg=booleanreg && rowflag[i]
					}
				}
				return booleanreg;
			},
			message:'请输入与顺序列不同的整数类型数据!'
		},
		fixedval: {
			validator: function(value, param){
				if($("input[name='sysNumberRule.val']").val() != value){
					var ret = sysNumber_AjaxConn({val:value},'sysNumber/isRepeatFixedVal.do');
					var retJson = $.parseJSON(ret);
					return !retJson.flag;
				}
				return true;
			},
			message: '固定值重复,请重新输入!'
		}
	});

    $(function(){
		var row = $("#system-table-sysNumberRule").datagrid('getSelected');
		var coltype = row.coltype;
    	var l = document.getElementById("sysNumber-sysNumberRule-length");//长度
    	var s = document.getElementById("sysNumber-sysNumberRule-substart");//截取开始位置
    	var t = document.getElementById("sysNumberRule-select-subtype");//截取方向
    	var textvalue = $("#system-testvalue-sysNumber").val();//流水依据字段
    	if(coltype == "2"){//字段
    		var inputhtml='<input id="system-val-sysNumberRule" class="easyui-combobox" required="true" type="text" name="sysNumberRule.val" value="${sysNumberRule.val}" style="width:120px;"/>';
    		$("#sysNumber-div-inputhtml").html(inputhtml);
    		$('#system-val-sysNumberRule').combobox({   
    			url:'common/getDictTableColumnList.do?tablename=${tablename}',   
    			valueField:'columnname',   
    			textField:'colchnname',
    			onLoadSuccess:function(){
    				l.removeAttribute("readonly");
    				s.removeAttribute("readonly");
    				//t.removeAttribute("disabled");
    			},
    			onSelect:function(record){
    				//字段名称
    				$("#system-valName-sysNumberRule").val(record.colchnname);

    			}
			});
    	}
    	
    	//焦点移除
    	fixedvalBlur();

   		$("#system-save-addNumberRule").click(function(){
   			$sysNumberRuleInfo = $("#sysNumberRule-form-addNumberRule");
   			if(!$sysNumberRuleInfo.form("validate")){
	    		return false;
	    	}
   			sysNumberRuleSubmit();
   			$("#system-dialog-sysNumberRule-edit").dialog("close");
   			$("#system-isEdit-sysNumberRuleList").val("1");
   			$("#system-serialNumber-sysNumber").val("");
   		});
   		
	    //类型转变值的变化
	    $("#system-coltype-sysNumberRule").change(function(){
			var l = document.getElementById("sysNumber-sysNumberRule-length");//长度
			var s = document.getElementById("sysNumber-sysNumberRule-substart");//截取开始位置
			var t = document.getElementById("sysNumberRule-select-subtype");//截取方向
			var textvalue = $("#system-testvalue-sysNumber").val();//流水依据字段
			if("1" == this.value){//固定值
				var inputhtml='<input id="system-fixedval-sysNumberRule" class="easyui-validatebox" validType="fixedval" type="text" name="sysNumberRule.val" required="true" style="width:120px;" maxlength="20"/>';
				$("#sysNumber-div-inputhtml").html(inputhtml);
				var sequencing = $("#sysNumber-sysNumberRule-sequencing").numberbox('getValue');
				$(':input','#sysNumberRule-form-addNumberRule').not(':button,:submit,:reset').val('');
				$("#system-coltype-sysNumberRule").val("1");
				$("#system-cover-sysNumberRule").val("*");
				$("#sysNumber-sysNumberRule-sequencing").numberbox('setValue',sequencing);
				t.options[0].selected = true;
				$("#sysNumberRule-select-hdsubtype").val(t.options[0].value);
				l.setAttribute("readonly","readonly");
				s.setAttribute("readonly","readonly");
				t.setAttribute("disabled","disabled");
				$("#sysNumber-sysNumberRule-state").val("0");
				$("#system-coldatatype-sysNumberRule").val("");
				fixedvalBlur();
			}
			else if("2" == this.value){//字段
				var inputhtml='<input id="system-val-sysNumberRule" class="easyui-combobox" required="true" type="text" name="sysNumberRule.val" style="width:120px;"/>';
				$("#sysNumber-div-inputhtml").html(inputhtml);
				$("#sysNumber-sysNumberRule-substart").val(0);
				$("#system-cover-sysNumberRule").val("0");
				$("#sysNumber-sysNumberRule-state").val("0");
				$('#system-val-sysNumberRule').combobox({
					url:'common/getDictTableColumnList.do?tablename=${tablename}',
					valueField:'columnname',
					textField:'colchnname',
					onSelect:function(record){
						//字段名称
						$("#system-valName-sysNumberRule").val(record.colchnname);
						l.removeAttribute("readonly");
						s.removeAttribute("readonly");
						//t.removeAttribute("disabled");
						$("#system-coldatatype-sysNumberRule").val(record.coldatatype);
						if("datetime" != record.coldatatype){
							$("#sysNumber-sysNumberRule-state").val("0");
						}
					}
				});
			}
			else{//系统日期
				var inputhtml='<input id="system-dateval-sysNumberRule" type="text" value="yyyyMMdd" name="sysNumberRule.val" style="width:120px;"/>';
				$("#sysNumber-div-inputhtml").html(inputhtml);
				l.removeAttribute("readonly");
				s.removeAttribute("readonly");
				$("#system-valName-sysNumberRule").val($("#system-dateval-sysNumberRule").val());
				$("#sysNumber-sysNumberRule-substart").val(0);
				$("#system-cover-sysNumberRule").val("0");
				var dateval = $("#system-dateval-sysNumberRule").val();
				var len = 0;
				if(dateval != ""){
					len = dateval.length;
				}
				$("#sysNumber-sysNumberRule-length").val(len);
				t.removeAttribute("disabled");
				$("#system-coldatatype-sysNumberRule").val("");
			}
		});
    });
    </script>
  </body>
</html>
