<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>单据编号管理</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  <script type="text/javascript">
	$.extend($.fn.datagrid.defaults.editors, {   
	    selectstatebox: {   //编辑定义selectbox_state
	        init: function(container, options){
	        	var selectStr='<select name="sysNumber.state">'+
	        				  '<option value="1" <c:if test="${sysNumber.state==\'1\' }">selected="selected"</c:if> >启用</option>'+ 
	        				  '<option value="0" <c:if test="${sysNumber.state==\'0\' }">selected="selected"</c:if> >禁用</option>'+ 
	        				  '<option value="2" <c:if test="${sysNumber.state==\'2\' }">selected="selected"</c:if> >保存</option>'+ 
	        				  '</select>'; 
	            var select = $('\"'+selectStr+'\"').appendTo(container);   
	            return select;   
	        },  
	        getValue: function(target){   
	            return $(target).val();   
	        },   
	        setValue: function(target, value){   
	            $(target).val(value);   
	        },   
	        resize: function(target, width){   
	            var select = $(target);   
	            if ($.boxModel == true){   
	                select.width(width - (select.outerWidth() - select.width()));   
	            } else {   
	                select.width(width);   
	            }   
	        }   
	    },
	    selectmodifyflagbox: {   //编辑定义selectbox_modifyflag
	        init: function(container, options){
	        	var selectStr='<select name="sysNumber.modifyflag">'+
	        				  '<option value="1" <c:if test="${sysNumber.modifyflag==\'1\' }">selected="selected"</c:if> >允许</option>'+ 
	        				  '<option value="0" <c:if test="${sysNumber.modifyflag==\'2\' }">selected="selected"</c:if> >不允许</option>'+ 
	        				  '</select>'; 
	            var select = $('\"'+selectStr+'\"').appendTo(container);   
	            return select;   
	        },  
	        getValue: function(target){   
	            return $(target).val();   
	        },   
	        setValue: function(target, value){   
	            $(target).val(value);   
	        },   
	        resize: function(target, width){   
	            var select = $(target);   
	            if ($.boxModel == true){   
	                select.width(width - (select.outerWidth() - select.width()));   
	            } else {   
	                select.width(width);   
	            }   
	        }   
	    },
	    selecttypebox: {   //编辑定义selectbox_type
	        init: function(container, options){
	        	var selectStr='<select name="sysNumber.type">'+
	        				  '<option value="1" <c:if test="${sysNumber.type==\'1\' }">selected="selected"</c:if> >系统预制</option>'+ 
	        				  '<option value="0" <c:if test="${sysNumber.type==\'2\' }">selected="selected"</c:if> >系统自建</option>'+ 
	        				  '</select>'; 
	            var select = $('\"'+selectStr+'\"').appendTo(container);   
	            return select;   
	        },  
	        getValue: function(target){   
	            return $(target).val();   
	        },   
	        setValue: function(target, value){   
	            $(target).val(value);   
	        },   
	        resize: function(target, width){   
	            var select = $(target);   
	            if ($.boxModel == true){   
	                select.width(width - (select.outerWidth() - select.width()));   
	            } else {   
	                select.width(width);   
	            }   
	        }   
	    },
	    selectautocreatebox: {   //编辑定义selectbox_autocreate
	        init: function(container, options){
	        	var selectStr='<select name="sysNumber.autocreate">'+
	        				  '<option value="1" <c:if test="${sysNumber.autocreate==\'1\' }">selected="selected"</c:if> >是</option>'+ 
	        				  '<option value="0" <c:if test="${sysNumber.autocreate==\'2\' }">selected="selected"</c:if> >否</option>'+ 
	        				  '</select>'; 
	            var select = $('\"'+selectStr+'\"').appendTo(container);   
	            return select;   
	        },  
	        getValue: function(target){   
	            return $(target).val();   
	        },   
	        setValue: function(target, value){   
	            $(target).val(value);   
	        },   
	        resize: function(target, width){   
	            var select = $(target);   
	            if ($.boxModel == true){   
	                select.width(width - (select.outerWidth() - select.width()));   
	            } else {   
	                select.width(width);   
	            }   
	        }   
	    }  
	});  
	//结束编辑
	//if (typeof sysNumberListPage == "undefined" || !sysNumberListPage) {
    	//var sysNumberListPage = {};
	//}
	var editIndex = undefined;
  	function endEditing(){
  		if(editIndex == undefined){return true}
  			if($('#sysNumber-table-showSysNumberListByBcode').datagrid('validateRow',editIndex)){
  				$('#sysNumber-table-showSysNumberListByBcode').datagrid('endEdit', editIndex); 
  				editIndex = undefined;
  				return true;
	  		}else{
	  			return false;
	  		}
  	}
  	var sysNumber_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false
	    })
	    return MyAjax.responseText;
	}

	//将表单序列化成json 字符串
    $.fn.serializeObject = function(){
	     var obj = {};
	     var count = 0;
	     $.each( this.serializeArray(), function(i,o){
	         var n = o.name, v = o.value;
	         count++;
	         obj[n] = obj[n] === undefined ? v
	         : $.isArray( obj[n] ) ? obj[n].concat( v )
	         : [ obj[n], v ];
	     });
	     obj.nameCounts = count + "";//表单name个数
	     return JSON.stringify(obj);
	 };
	 //form的初始值 
	function startValues(){
		var objectStr=$("#sysNumber-form-serialform").serializeObject();
		var objectJson=JSON.parse(objectStr);
		$("#sysNumber-form-serialform input").each(function() { 
			$(this).attr('_value', $(this).val()); 
		});
	}
	//检查form表单是否修改  
	function is_form_changed() { 
		var is_changed = false; 
		$("#sysNumber-form-serialform input").each(function() { 
			var _v = $(this).attr('_value'); 
			if(typeof(_v) == 'undefined') _v = ''; 
			if(_v != $(this).val()) is_changed = true; 
		}); 
		return is_changed;  
	} 
	
	//勾选单据编号自动生成
	function sysNumberAutoCreate(){
		var billName=$("#sysNumber-table-billName").datagrid('getSelected');
		var checkbox= document.getElementById("sysNumber-checkbox-sysNumberAutoCreate");
	 	var autoCreate = "2";
	 	if(billName == null){
			$.messager.alert("提醒","请选择相应的单据类型!");
			checkbox.checked = false;
 			return false;
		}
		if(checkbox.checked == true){
			autoCreate = "1";
		}
		$("#sysNumber-checkbox-sysNumberAutoCreate").val(autoCreate);
		var retStr=sysNumber_AjaxConn({autocreate:autoCreate,billcode:billName.id},"sysNumber/editSysNumbersAutoCreate.do");
		var retJson=JSON.parse(retStr);
		if(retJson.flag == true){
			return true;
		}
		
	}
	 //允许单据编号修改 
	 function sysNumberIsenablemodify(){
	 	var billName=$("#sysNumber-table-billName").datagrid('getSelected');
	 	var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
	 	var $modifyflagCheckbox = document.getElementById("sysNumber-checkbox-sysNumberChange");
	 	var modifyflag = "2";
		if(sysNumber == null){
			$.messager.alert("提醒","请选择相应的单据编号!");
			$modifyflagCheckbox.checked = false;
 			return false;
		}
		if($modifyflagCheckbox.checked == true){
			modifyflag = "1";
		}
		$("#sysNumber-checkbox-sysNumberChange").val(modifyflag);
		var retStr=sysNumber_AjaxConn({numberid:sysNumber.numberid,modifyflag:modifyflag},"sysNumber/editSysNumbersModifyFlag.do");
		var retJson=JSON.parse(retStr);
		if(retJson.flag){
			$('#sysNumber-table-showSysNumberListByBcode').datagrid("reload");
			return true;
		}
	 }
	 
	 //检查单据编号流水号长度 
	 function checkSerialLength(){
	 	var serialLength=0;
		var rows=$('#sysNumberRule-table-showSysNumberRuleList').datagrid('getRows');  
		for(var i=0;i<rows.length;i++){
			if(rows[i].length == 0){
				serialLength=rows[i].val.length+rows[i].suffix.length+rows[i].prefix.length+serialLength;
			}
			else{
				serialLength=rows[i].length+rows[i].suffix.length+rows[i].prefix.length+serialLength;
			}
		}
		if(serialLength>20){
			$.messager.alert("提醒","该单据编码流水号长度过长,应在20范围内!");
		}
		else{
			$("#seriallength").val(serialLength);
			return serialLength;
		}
	 }
	 
	 //保存流水号数据 
	 function saveSerailSysNumber(){
	 	var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
		if(is_form_changed() == false){
				$.messager.alert("提醒","流水号无数据修改!");
				return false;
			}
			if($("#sysNumber-form-serialform").form('validate')){
				var retSerial= sysNumber_AjaxConn($("#sysNumber-form-serialform").serializeJSON(),'sysNumber/editSerial.do?preview='+sysNumber.preview+'');
				var serialRet = eval("(" + retSerial + ")");//string转化为json对象 
				if(serialRet.flag){
					$("#serialnumber").val(serialRet.serialnumber);
					$('#sysNumber-table-showSysNumberListByBcode').datagrid('reload');
					startValues();
					$.messager.alert("提醒","流水号数据修改成功!");
				}
				else{
					$.messager.alert("提醒","流水号数据修改失败!");
				}
			}else{return false;}
	 }
	 
	 //流水号长度验证 
	$.extend($.fn.validatebox.defaults.rules, {
		validSerailLength:{
			validator:function(value){
				
				return value<=11 && value>0;
			},
			message:'请输入0-12之间的整数!'
		},
		validSerailStart:{//流水号起始值验证 
			validator:function(value){
				var reg=/^([1-9])\d{0,10}$/;
				if(!reg.test(value)){
					$.fn.validatebox.defaults.rules.validSerailStart.message = '最多只可输入11位非零数字!';
				}
				else{
					if(!(value.length <= $("#seriallength").val())){
						$.fn.validatebox.defaults.rules.validSerailStart.message = '输入的起始值长度与所填写的长度不符!';
					}
					else{
						return true;
					}
				}
			},
			message:''
		},
		validSerailStep:{//流水号步长验证 
			validator:function(value){
				var reg=/^[1-9]{1,3}$/;
				return reg.test(value);
			},
			message:'最多只可输入3位非零数字!'
		}
	});
    		
    //状态启用、停用按钮间的变化
    function sysNumber_buttonChange(state){
    	if(state == "1"){
    		sysNunber_enableButtonState();
    	}
    	else{
    		sysNumber_disableButtonState();
    	}
    }
    
    //状态为启用时按钮的状态
    function sysNunber_enableButtonState(){
    	/*------------单据编号列表按钮-----------------*/
		$('#sysNumber-edit-editSysNumber').linkbutton('disable');//修改按钮。
		$('#sysNumber-save-saveSysNumber').linkbutton('disable');//保存按钮 
		$('#sysNumber-open-openSysNumber').linkbutton('disable');//启用按钮 
		$('#sysNumber-close-closeSysNumber').linkbutton('enable');//禁用按钮
		$('#sysNumber-delete-deleteSysNumber').linkbutton('disable');//删除按钮 
		/*------------单据编号规则列表按钮-----------------*/
		$('#sysNumberRule-add-addSysNumberRule').linkbutton('disable');//新增按钮。
		$('#sysNumberRule-edit-editSysNumberRule').linkbutton('disable');//修改按钮 
		$('#sysNumberRule-save-saveSysNumberRule').linkbutton('disable');//保存按钮 
		$('#sysNumberRule-reject-rejectSysNumberRule').linkbutton('disable');//撤销按钮
		$('#sysNumberRule-delete-deleteSysNumberRule').linkbutton('disable');//删除按钮 
		//document.getElementById("seriallength").setAttribute("disabled","disabled");
		$("#seriallength").attr("disabled","disabled");
	  	$("#serialstep").attr("disabled","disabled");
	  	$("#serialstart").attr("disabled","disabled");
	  	$("#serialnumber").attr("disabled","disabled");
    }
    
    //状态为停用时按钮的状态
    function sysNumber_disableButtonState(){
    	/*------------单据编号列表按钮-----------------*/
		$('#sysNumber-edit-editSysNumber').linkbutton('enable');//修改按钮。
		$('#sysNumber-save-saveSysNumber').linkbutton('enable');//保存按钮 
		$('#sysNumber-open-openSysNumber').linkbutton('enable');//启用按钮 
		$('#sysNumber-close-closeSysNumber').linkbutton('disable');//停用按钮
		$('#sysNumber-delete-deleteSysNumber').linkbutton('enable');//删除按钮 
		/*------------单据编号规则列表按钮-----------------*/
		$('#sysNumberRule-add-addSysNumberRule').linkbutton('enable');//新增按钮
		$('#sysNumberRule-edit-editSysNumberRule').linkbutton('enable');//修改按钮 
		$('#sysNumberRule-save-saveSysNumberRule').linkbutton('enable');//保存按钮 
		$('#sysNumberRule-reject-rejectSysNumberRule').linkbutton('enable');//撤销按钮
		$('#sysNumberRule-delete-deleteSysNumberRule').linkbutton('enable');//删除按钮 
		$("#seriallength").removeAttr("disabled");
       	$("#serialstep").removeAttr("disabled");
       	$("#serialstart").removeAttr("disabled");
       	$("#serialnumber").removeAttr("disabled");
    }
  	$(function(){
  		//单据名称列表
 		var type="billtype";
 		$('#sysNumber-table-billName').datagrid({
		    fit:true,
		    fitColumns:true,
			title:'',
			method:'post',
			rownumbers:true,
			pagination:false,
			idField:'billcode',
			singleSelect:true,
			url:'common/getTableList.do?table=sys_code',
			toolbar:"#sysNumberName-toolbar-operateList",
			columns:[[  
				{field:'name',title:'单据名称',width:130},
			]],
			onClickRow:function(){
				var billName=$("#sysNumber-table-billName").datagrid('getSelected');
				if(billName!=null){
					$('#sysNumber-table-showSysNumberListByBcode').datagrid('clearSelections');
					var checkbox= document.getElementById("sysNumber-checkbox-sysNumberAutoCreate");
					var ret = sysNumber_AjaxConn({billcode:billName.id},'sysNumber/getAutoCreateByBillCode.do');
					var retJson = $.parseJSON(ret);
					if(retJson.flag){
						if(retJson.autoCreate == "1"){
							checkbox.checked = true;
						}
						else{
							checkbox.checked = false;
						}
					}
					else{checkbox.checked = false;}
					document.getElementById("sysNumber-checkbox-sysNumberChange").checked = false;
					$("#sysNumber-checkbox-sysNumberAutoCreate").val(retJson.autoCreate);
					var url='sysNumber/showSysNumberListByBcode.do?tablename='+billName.id;
					$('#sysNumber-table-showSysNumberListByBcode').datagrid("options").url=url;
		    		$('#sysNumber-table-showSysNumberListByBcode').datagrid("reload");
		    		//$('#sysNumberRule-table-showSysNumberRuleList').datagrid("options").url='sysNumberRule/showSysNumberRuleList.do';
		    		editIndex = undefined;				
				}
			}
		});
		//根据单据名称显示单据编号列表
  		$('#sysNumber-table-showSysNumberListByBcode').datagrid({ 
  	 		fit:true,
  	 		title:'',
  	 		method:'post',
  	 		rownumbers:true,
  	 		pagination:true,
  	 		idField:'numberid',
  	 		singleSelect:true,
  	 		toolbar:"#sysNumber-toolbar-operateList",
		    columns:[[    
		        {field:'tablename',title:'表名',width:100,sortable:true}, 
		        {field:'name',title:'编号规则名称',width:100,editor:'text'},
		        {field:'remark',title:'备注说明',width:150,editor:'text'},
		        {field:'state',title:'状态',width:80,editor:'selectstatebox',
		        	formatter:function(val){
		        		if(val == '2'){
		        			return  "保存";
		        		}
		        		else if(val == '1'){
		        			return  "启用";
		        		}
		        		else{
		        			return  "停用";
		        		}
		        	}
		        },  
		        {field:'type',title:'类型',width:100,editor:'selecttypebox',
		        	formatter:function(val){
		        		if(val=='1'){
		        			return "系统预制";
		        		}else{
		        			return "系统自建";
		        		}
		        	}
		        }, 
		        {field:'preview',title:'预览效果',width:150},  
		        {field:'serialnumber',title:'当前流水号',width:80},
		        {field:'seriallength',title:'流水号长度',width:80},
		        {field:'serialstep',title:'流水号步长',width:80},
		        {field:'serialstart',title:'流水号起始值',width:80},
		        {field:'testvalue',title:'流水号依据字段值',width:100}
		    ]],
		    onLoadSuccess:function(data){
		    	EUIUtils.mergeCellsByFieldByTimeout('sysNumber-table-showSysNumberListByBcode','tablename',200);                
		    },
		    onClickRow:function(){
		    	if(endEditing()){
		    		var sysNumber = $("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
		    		sysNumber_buttonChange(sysNumber.state);//调用状态为启用、停用按钮间变化函数 
		    		if(sysNumber.modifyflag == "1"){
						document.getElementById("sysNumber-checkbox-sysNumberChange").checked = true;
					}
					else{
						document.getElementById("sysNumber-checkbox-sysNumberChange").checked = false;
					}
		    		$("#sysNumberRule-panel-showSysNumberRuleInfo").panel({'title':sysNumber.name+'('+sysNumber.tablename+')'+'-规则信息'});
		        	$("#hdnumberid").val(sysNumber.numberid);
		        	$("#seriallength").val(sysNumber.seriallength);
		        	$("#serialstep").val(sysNumber.serialstep);
		        	$("#serialstart").val(sysNumber.serialstart);
		        	$("#serialnumber").val(sysNumber.serialnumber);
		        	startValues();
		    		var url = 'sysNumberRule/showSysNumberRuleList.do?numberid='+sysNumber.numberid;
		    		$('#sysNumberRule-table-showSysNumberRuleList').datagrid("options").url=url;
		    		$('#sysNumberRule-table-showSysNumberRuleList').datagrid("reload");	
		    	}
		    }
		});
		//根据单据编号显示编号规则列表
  		$('#sysNumberRule-table-showSysNumberRuleList').datagrid({
		    fit:true,
		    fitColumns:true,
			title:'',
			method:'post',
			rownumbers:false,
			pagination:false,
			idField:'numberruleid',
			singleSelect:true,
			columns:[[
				{field:'sequencing',title:'排序',width:50},  
				{field:'coltype',title:'类型',width:80,
					  formatter:function(val){
					        switch (val) {
								case "1":
									return "固定值";
								case "2":
									return "字段";
								case "3":
									return "系统日期";
								default:	
									break;
							}
					   }
				},  
				{field:'val',title:'值',width:130},
				{field:'prefix',title:'前缀',width:80},
				{field:'suffix',title:'后缀',width:80},
				{field:'length',title:'长度',width:80},  
				{field:'subtype',title:'截取方向',width:100,
					formatter:function(val){
						if(val=='1'){
					        return "从前向后";
						}else{  
							return "从后向前";
					   	}
					}
				}, 
				{field:'substart',title:'截取开始位置',width:100,resizable:true},  
				{field:'cover',title:'补位符',width:80},
				{field:'state',title:'是否以该数据作为流水依据',width:200,
					  formatter:function(val){
					        if(val=='1'){
					        	return "是";
					        }else{
					        	return "否";
					        }
					  }
				}
			]]
		});
		//添加单据编号,添加按钮 
		$("#sysNumber-add-addSysNumber").click(function(){
			var billName=$("#sysNumber-table-billName").datagrid('getSelected');
			if(billName == null){
				$.messager.alert("提醒","该选择单据名称 !");
  				return false;
			}
			if(endEditing() && billName != null){
				$('#sysNumber-save-saveSysNumber').linkbutton('enable');//保存按钮 
				$('#sysNumber-table-showSysNumberListByBcode').datagrid('appendRow',{state:'2',billcode:''+billName.id+'',billname:''+billName.name+'',tablename:''+billName.id+''});
				editIndex=$('#sysNumber-table-showSysNumberListByBcode').datagrid('getRows').length-1;
				$('#sysNumber-table-showSysNumberListByBcode').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);

			}
		});
		//编辑单据编号，修改按钮 
		$("#sysNumber-edit-editSysNumber").click(function(){
			var sysNumber = $("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			if(sysNumber == null){
				$.messager.alert("提醒","请选择单据编号!");
  					return false;
			}
			if(editIndex == undefined && sysNumber.state != 1){
				if(endEditing()){
		    		var rowIndex = $('#sysNumber-table-showSysNumberListByBcode').datagrid('getRowIndex',sysNumber);
		    		$('#sysNumber-table-showSysNumberListByBcode').datagrid('beginEdit',rowIndex);
		    		editIndex=rowIndex;
		    	}else{
		    		$('#sysNumber-table-showSysNumberListByBcode').datagrid('selectRow',editIndex);
		    	}
			}
			else{return false;}
		});
		//保存单据编号
		$("#sysNumber-save-saveSysNumber").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			if(sysNumber == null){
				$.messager.alert("提醒","请进行相应的操作,再保存!");
  					return false;
			}
			if(endEditing() && sysNumber.state != 1){
				var inserted=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getChanges',"inserted");
				var updated=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getChanges',"updated");
				
				var effectRow = new Object();
				if(sysNumber.numberid == undefined){
					$.messager.confirm("提醒","是否添加单据编码?",function(r){
	    				if(r){
	    					if(inserted.length){
								effectRow["inserted"] = JSON.stringify(inserted);
								
							}
							$.post("sysNumber/addSysNumber.do",effectRow,function(rsp){
								var ret = eval("(" + rsp + ")");//string转化为json
								if(ret.flag == true){ 
									$('#sysNumber-table-showSysNumberListByBcode').datagrid('reload');
									$.messager.alert("提醒","添加成功！");
									$('#sysNumber-table-showSysNumberListByBcode').datagrid('acceptChanges');
								}
								else{
									$.messager.alert("提醒","添加失败！");
								}
							});
	    				}
	    				else{
	    					$('#sysNumber-table-showSysNumberListByBcode').datagrid('rejectChanges');  
            				editIndex = undefined;
	    				}
	    			});
				}
				else{
					if(updated.length == 0){
						$.messager.alert("提醒","并无修改数据!");
						$('#sysNumber-table-showSysNumberListByBcode').datagrid('rejectChanges');  
            			editIndex = undefined;
						return false;
					}
					$.messager.confirm("提醒","是否修改单据编号?",function(r){
	    				if(r){
	    					effectRow["updated"] = JSON.stringify(updated);
							$.post("sysNumber/editSysNumber.do",effectRow,function(rsp){
								var ret = eval("(" + rsp + ")");//string转化为json
								if(ret.flag == true){ 
									$('#sysNumber-table-showSysNumberListByBcode').datagrid('reload');
									$.messager.alert("提醒","修改成功！");
									$('#sysNumber-table-showSysNumberListByBcode').datagrid('acceptChanges');
								}
								else{
									$.messager.alert("提醒","修改失败！");
								}
							});
	    				}
	    				else{
	    					$('#sysNumber-table-showSysNumberListByBcode').datagrid('rejectChanges');  
            				editIndex = undefined;
	    				}
	    			});
				}
			}else{return false;}
		});
		//撤销单据编号操作 ，撤销按钮
		$("#sysNumber-reject-rejectSysNumber").click(function(){
			$('#sysNumber-table-showSysNumberListByBcode').datagrid('rejectChanges');  
            editIndex = undefined;
		}); 
		
		//启用单据编号 ，启用按钮
		$("#sysNumber-open-openSysNumber").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			var sysNumberRule = $('#sysNumberRule-table-showSysNumberRuleList').datagrid("getRows");
			if(sysNumber == null){
				$.messager.alert("提醒","请选择单据编号!");
  				return false;
			}
			if(sysNumberRule.length == 0){
				$.messager.alert("提醒","其单据编号规则为空,不能启用!");
  				return false;
			}
			if(sysNumber.state != 1){//状态为禁用时
				var num = 1;
				var sysNumberRows = $("#sysNumber-table-showSysNumberListByBcode").datagrid('getRows');
				for(var i=0;i<sysNumberRows.length;i++){
					if(sysNumberRows[i].state == "1"){
						num=num+1;
					}
				}
				if(num > 1){
					$.messager.confirm('提醒','启用的单据编号不唯一,是否启用该单据编号?',function(r){   
					    if (r){
					     	var ret = sysNumber_AjaxConn({numberid:sysNumber.numberid,tablename:sysNumber.tablename},'sysNumber/enableSysNumber.do?type=notonly');
					    	var retJson = $.parseJSON(ret);
							if(retJson.flag){
								//document.getElementById("sysNumber-checkbox-sysNumberChange").checked = false;
					    		$.messager.alert("提醒","编号启用成功!");
								sysNunber_enableButtonState();//调用状态为启用时按钮就的状态 
								$("#sysNumber-table-showSysNumberListByBcode").datagrid('reload');
					    	}
					    	else{
					    		$.messager.alert("提醒","编号启用失败 !");
					    	}
					    }   
					});  
				}else{
					var ret = sysNumber_AjaxConn({numberid:sysNumber.numberid,tablename:sysNumber.tablename},'sysNumber/enableSysNumber.do?type=only');
					var retJson = $.parseJSON(ret);
					if(retJson.flag){
						//document.getElementById("sysNumber-checkbox-sysNumberChange").checked = false;
			    		$.messager.alert("提醒","编号启用成功!");
						sysNunber_enableButtonState();//调用状态为启用时按钮就的状态 
						$("#sysNumber-table-showSysNumberListByBcode").datagrid('reload');
			    	}
			    	else{
			    		$.messager.alert("提醒","编号启用失败 !");
			    	}
				}
			}
			else{return false;} 
			
		});
		//停用单据编号,停用按钮
		$("#sysNumber-close-closeSysNumber").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			if(sysNumber == null){
				$.messager.alert("提醒","请选择单据编号!");
  					return false;
			}
			if(sysNumber.state != 0){
				$.ajax({
  					url:'sysNumber/disableSysNumber.do?numberid='+sysNumber.numberid,
  					type:'post',
  					dataType:'json',
  					success:function(json){
  						if(json.flag==true){
  							//document.getElementById("sysNumber-checkbox-sysNumberChange").checked = true;
  							$.messager.alert("提醒","编号停用成功!");
  							sysNumber_disableButtonState();//调用停用时按钮的状态 
  							$("#sysNumber-table-showSysNumberListByBcode").datagrid('reload');
  						}else{
  							$.messager.alert("提醒","编号停用失败 !");
  						}
  					}
  				});
			}
			else{return false}
		});
		//删除单据编号，删除按钮
		$("#sysNumber-delete-deleteSysNumber").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			if(sysNumber == null){
				$.messager.alert("提醒","请选择单据编号!");
  					return false;
			}
			else{
				$.messager.confirm('提醒','如果删除,包含其中的单据编码规则也将删除！确定删除吗?',function(r){   
				    if (r){   
				        $.ajax({
			  				url:'sysNumber/deleteSysNumber.do?numberid='+sysNumber.numberid,
			  				type:'post',
			  				dataType:'json',
			  				success:function(json){
			  					if(json.flag==true){
			  						//$("#sysNumber-table-showSysNumberListByBcode").datagrid('deleteRow',rowIndex);
			  						$.messager.alert("提醒","单据编号删除成功!");
			  						$("#sysNumber-table-showSysNumberListByBcode").datagrid('reload');
			  						$('#sysNumberRule-table-showSysNumberRuleList').datagrid('reload');
			  					}else{
			  						$.messager.alert("提醒","单据编号删除失败 !");
			  					}
			  				}
			  			});
				    }   
				});  
			}
		});
		
		//显示添加单据编号规则页面 ，添加按钮
		$("#sysNumberRule-add-addSysNumberRule").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			if(sysNumber == null){
				$.messager.alert("提醒","请选择单据编号,再添加!");
  					return false;
			}
			if(sysNumber.state == 1){
				return false;
			}else{
				$("#sysNumberRule-dialog-NumberRuleOper").dialog({
  					title:'单据编号规则添加信息',
  					width:500,
  					height:450,
  					closed:false,
  					cache:false,
  					href:'sysNumberRule/showSysNumberRuleAddPage.do?tablename='+sysNumber.tablename+"&numberid="+sysNumber.numberid+"&seriallength="+sysNumber.seriallength+"&serialstart="+sysNumber.serialstart,
  					modal:true
  				});
			}
		});
		//显示修改单据编号规则页面
		$("#sysNumberRule-edit-editSysNumberRule").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			var sysNumberRule=$("#sysNumberRule-table-showSysNumberRuleList").datagrid('getSelected');
			if(sysNumber.state == 1){
  					return false;
			}
			if(sysNumberRule == null){
				$.messager.alert("提醒","请选择单据编号规则,再修改!");
				return false;
			}else{
				var url="sysNumberRule/showSysNumberRuleEditPage.do?numberruleid="+sysNumberRule.numberruleid+"&seriallength="+sysNumber.seriallength+"&serialstart="+sysNumber.serialstart;
				$("#sysNumberRule-dialog-NumberRuleOper").dialog({
  					title:'单据编号规则编辑信息',
  					width:500,
  					height:450,
  					closed:false,
  					cache:false,
  					href:url,
  					modal:true
  				});
			}
		});
		
		//撤销单据编号规则
		$("#sysNumberRule-reject-rejectSysNumberRule").click(function(){
			$('#sysNumberRule-table-showSysNumberRuleList').datagrid('rejectChanges');  
            editIndex = undefined;
		});
		//删除单据编号规则
		$("#sysNumberRule-delete-deleteSysNumberRule").click(function(){
			var sysNumber=$("#sysNumber-table-showSysNumberListByBcode").datagrid('getSelected');
			var sysNumberRule=$("#sysNumberRule-table-showSysNumberRuleList").datagrid('getSelected');
			if(sysNumber.state == 1){
  					return false;
			}
			if(sysNumberRule == null){
				$.messager.alert("提醒","请选择单据编号规则!");
				return false;
			}else{
				$.messager.confirm('提醒','确定删除吗?',function(r){   
				    if (r){   
				        var rowIndex=$("#sysNumberRule-table-showSysNumberRuleList").datagrid('getRowIndex',sysNumberRule);
						$.ajax({
			  				url:'sysNumberRule/deleteSysNumberRule.do?numberruleid='+sysNumberRule.numberruleid+"&numberid="+sysNumberRule.numberid,
			  				type:'post',
			  				dataType:'json',
			  				success:function(json){
			  					if(json.flag==true){
			  						$("#sysNumberRule-table-showSysNumberRuleList").datagrid('deleteRow',rowIndex);
			  						$('#sysNumberRule-table-showSysNumberRuleList').datagrid('reload');
			  						$.messager.alert("提醒","单据编号规则删除成功!");
			  						$("#sysNumber-table-showSysNumberListByBcode").datagrid('reload');
			  						//$('#sysNumberRule-table-showSysNumberRuleList').datagrid('reload');	
			  					}else{
			  						$.messager.alert("提醒","单据编号规则删除失败 !");
			  					}
			  				}
			  			});
				    }   
				}); 
			}
		});
		
  	});
  </script>
		<div class="easyui-layout" data-options="fit:true">
			<div title="单据类型" data-options="region:'west',split:true" style="width:200px;">
				<table id="sysNumber-table-billName"></table>
			</div>
			<div title="" data-options="region:'center'" style="padding:2px;">
				<input id="sysNumber-checkbox-sysNumberAutoCreate" type="checkbox" onclick="sysNumberAutoCreate()" >单据编号自动生成</input>&nbsp;&nbsp;&nbsp;
				<input id="sysNumber-checkbox-sysNumberChange" type="checkbox" onclick="sysNumberIsenablemodify()" >允许修改</input>
				<div class="easyui-layout" fit="true" >
					<div title="单据编号列表" data-options="region:'center',split:true,border:true" >
						<div id="sysNumber-toolbar-operateList" class="buttonBG">
							<a href="javaScript:void(0);" id="sysNumber-add-addSysNumber" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增单据编号">新增</a>
							<a href="javaScript:void(0);" id="sysNumber-edit-editSysNumber" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改单据编号">修改</a>
							<a href="javaScript:void(0);" id="sysNumber-save-saveSysNumber" class="easyui-linkbutton" data-options="iconCls:'button-save',plain:true" title="保存单据编号">保存</a>
							<a href="javaScript:void(0);" id="sysNumber-reject-rejectSysNumber" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" title="撤销当前操作">撤销</a>
							<a href="javaScript:void(0);" id="sysNumber-open-openSysNumber" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'" title="启用单据编号">启用</a>
							<a href="javaScript:void(0);" id="sysNumber-close-closeSysNumber" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="禁用单据编号">停用</a>
							<a href="javaScript:void(0);" id="sysNumber-delete-deleteSysNumber" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除单据编号">删除</a>
						</div>
						<table id="sysNumber-table-showSysNumberListByBcode"></table>
					</div>
					<div title="单据编号规则信息" id="sysNumberRule-panel-showSysNumberRuleInfo" data-options="region:'south',split:true,border:true" style="height: 250px;">
						<div id="sysNumberRule-toolbar-menuList" class="buttonBG">
							<a href="javaScript:void(0);" id="sysNumberRule-add-addSysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增单据编号规则">新增</a>
							<a href="javaScript:void(0);" id="sysNumberRule-edit-editSysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改单据编号规则">修改</a>
							<a href="javaScript:void(0);" id="sysNumberRule-save-saveSysNumberRule" onclick="saveSerailSysNumber()" class="easyui-linkbutton" data-options="iconCls:'button-save',plain:true" title="保存单据编号规则">修改保存流水号</a>
							<a href="javaScript:void(0);" id="sysNumberRule-reject-rejectSysNumberRule" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" title="撤销当前操作">撤销</a>
							<a href="javaScript:void(0);" id="sysNumberRule-delete-deleteSysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除单据编号规则">删除</a><br/>
							<form action="" method="post" id="sysNumber-form-serialform">
								<input type="hidden" id="hdnumberid" name="sysNumber.numberid"/>
								<input type="hidden" name="sysNumber.preview" id="sysNumber-hidden-hdPreview"/>
								&nbsp;&nbsp;&nbsp;流水号长度:<input type="text" id="seriallength" class="easyui-validatebox" required="true" validType="validSerailLength" name="sysNumber.seriallength" style="width:100px;"/>
								&nbsp;&nbsp;&nbsp;流水号步长:<input type="text" id="serialstep" class="easyui-validatebox" required="true" validType="validSerailStep" name="sysNumber.serialstep" style="width:100px;"/>
								&nbsp;&nbsp;&nbsp;流水号起始值:<input type="text" id="serialstart" class="easyui-validatebox" required="true" validType="validSerailStart" name="sysNumber.serialstart" style="width:100px;"/>
								&nbsp;&nbsp;&nbsp;当前流水号:<input type="text" readonly="readonly" id="serialnumber" class="easyui-validatebox" validType="validSerialNumber" name="sysNumber.serialnumber" style="width:100px;"/>
							</form>
						</div>
						<table id="sysNumberRule-table-showSysNumberRuleList"></table>
					</div>
					<div id="sysNumberRule-dialog-NumberRuleOper" class="easyui-dialog" closed="true"></div>
				</div>
			</div>
		</div>
  </body>
</html>
