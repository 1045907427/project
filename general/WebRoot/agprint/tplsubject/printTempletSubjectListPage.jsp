<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>公共代码列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>  
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
   			<div class="buttonBG" id="tplsubject-button-printTempletSubjectList"></div>
  	 	</div>
        <div data-options="region:'center'">
  			<table id="tplsubject-table-printTempletSubjectList"></table>
		   	<div id="tplsubject-query-printTempletSubjectList" style="padding:0px;height:auto">
				<form action="" id="tplsubject-form-printTempletSubjectList" method="post" style="padding-top: 2px;">
					<table class="querytable">
						<tr>
							<td>模板代码:</td>
							<td><input type="text" name="code" style="width:120px" /></td>
							<td>分类名称:</td>
							<td><input type="text" name="name" style="width:100px" /></td>
							<td>状态:</td>
							<td><select name="state" style="width:100px;">
				   				<option></option>
				   				<option value="1">启用</option>
				   				<option value="0">禁用</option>
				   			</select></td>
				   			<td>
				   				<a href="javaScript:void(0);" id="tplsubject-query-queryPrintTempletSubjectList" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="tplsubject-query-reloadPrintTempletSubjectList" class="button-qr">重置</a>
				   			</td>
						</tr>
					</table>
		  		</form>
		   </div>
   		</div>
   </div>
   <div style="display:none">
   	<div id="tplsubject-dialog-printTempletSubjectOper" ></div>
   </div>
   	<script type="text/javascript">
   		$(function(){
  			$.extend($.fn.validatebox.defaults.rules, {
    			validSeqInt:{
    				validator:function(value,param){
    					var isInt='int';
    					if(param[0]==isInt){
    						var reg=/^[1-9][0-9]{0,4}$/;
    						return reg.test(value);
    					} 
    				},
    				message:'请输入1-99999的整数类型数据!'
    			}
    		});
    		$.extend($.fn.validatebox.defaults.rules, {
				validPrintTemletSubjectCode:{
			    	validator:function(value){
			    		var reg=/^([a-zA-Z0-9_]|-)+$/;
			    		if(!reg.test(value)){
			    			$.fn.validatebox.defaults.rules.validPrintTemletSubjectCode.message='代码名规则：英文字母、数字、中划线  - 或下划线  _'; 
			    		}
			    		if(value.length>0){
			    			var data=templetSubject_getAjaxContent({code: value},'agprint/tplsubject/isUsedPrintTempletSubjectCode.do');
			    			var json = $.parseJSON(data);
		    				if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validPrintTemletSubjectCode.message = '代码名已经使用，请重新填写';
			    				return false;
			    			}else{
		    					return true;
			    			}
			    		}
			    		return false;
			    	},
			    	message:'代码名规则：英文字母、数字、中划线  - 或下划线  _ '
			    }
			});
   			
   		//根据初始的列与用户保存的列生成以及字段权限生成新的列
   	    	var templetSubjectListColJson=$("#tplsubject-table-printTempletSubjectList").createGridColumnLoad({
   		     	name:'print_templet_subject',
   		     	frozenCol:[[
   					{field:'idok',checkbox:true,isShow:true}
   		     	]],
   		     	commonCol:[[
   		    		{field:'code',title:'代码',width:150},
	  				{field:'name',title:'代码名称',width:180},
	  				{field:'seq',title:'排序',width:80,sortable:true},
	  				{field:'uselinktype',title:'使用模板方式',width:60,isShow:true,
	  					formatter:function(val){
	  						if(val=='0'){
	  							return '系统默认';
	  						}else if(val=="1"){
								return "使用关联";
							}else if(val=="2"){
								return "手动";
							}
	  					}
	  				},
	  				{field:'linktypeseqname',title:'关联执行顺序',width:120,isShow:true},
	  				{field:'state',title:'状态',width:100,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}else{
	  							return '禁用';
	  						}
	  					}
	  				},
	  				{field:'remark',title:'备注',width:150}
   				]]
   		     });
   			
   			$("#tplsubject-table-printTempletSubjectList").datagrid({
     			authority:templetSubjectListColJson,
	  	 		frozenColumns:templetSubjectListColJson.frozen,
				columns:templetSubjectListColJson.common,
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'code',
	  			singleSelect:false,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
	  			toolbar:'#tplsubject-query-printTempletSubjectList',
	  			url:'agprint/tplsubject/showPrintTempletSubjectPageListData.do',
	  			onDblClickRow:function(index, dataRow){
	  				subjectADMOperDialog("模板代码【查看】", 'agprint/tplsubject/showPrintTempletSubjectViewPage.do?code='+dataRow.code);
	  			}
	   		});
   			
   			//回车事件
			controlQueryAndResetByKey("tplsubject-query-queryPrintTempletSubjectList","tplsubject-query-reloadPrintTempletSubjectList");
   			
   			//查询
  			$("#tplsubject-query-queryPrintTempletSubjectList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplsubject-form-printTempletSubjectList").serializeJSON();
	       		$("#tplsubject-table-printTempletSubjectList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplsubject-query-reloadPrintTempletSubjectList').click(function(){
  				$("#tplsubject-form-printTempletSubjectList")[0].reset();
	       		$("#tplsubject-table-printTempletSubjectList").datagrid('loadData',{total:0,rows:[]});
  			});
  			
  			
  			$("#tplsubject-button-printTempletSubjectList").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplsubject/printTempletSubjectAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							subjectADMOperDialog('模板代码【新增】', 'agprint/tplsubject/showPrintTempletSubjectAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplsubject/printTempletSubjectEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null || dataRow.code==""){
								$.messager.alert("提醒","请选择相应的打印模板代码!");
								return false;
							}
							subjectADMOperDialog("模板代码【修改】", 'agprint/tplsubject/showPrintTempletSubjectEditPage.do?code='+dataRow.code);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplsubject/printTempletSubjectDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#tplsubject-table-printTempletSubjectList").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的打印模板代码!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].code && rows[i].code!=""){
							    		idarrs.push(rows[i].code);
						    		}
						    		if(rows[i].state!=null){
							    		if(rows[i].state =='1' ){
							    			errorIdarr.push(rows[i].code);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","启用的打印模板代码不能被删除，下列选中为已经启用："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除打印模板代码 ?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'agprint/tplsubject/deletePrintTempletSubjectMore.do',
							        data: {codearrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
											$("#tplsubject-table-printTempletSubjectList").datagrid('clearSelections');
											showListPagePrintTempletSubjectWidget();
						  		        }
						  		        else{
						  		        	$.messager.alert("提醒","删除失败");
						  		        }
							        }
							    });
						    }
							});
						}
					},
					</security:authorize>				
					<security:authorize url="/agprint/tplsubject/printTempletSubjectEnableBtn.do">
					{
						id:'button-id-enable',
						name:'启用',
						iconCls:'button-open',
						handler: function(){
							var dataRow =  $("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择启用的打印模板代码!");
			  					return false;
			  				}
			  				if(dataRow.code == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要启用的打印模板代码!");
			  					return false;
			  				}
			  				if(dataRow.state == "1"){
			  					$.messager.alert("提醒","启用状态不能启用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'agprint/tplsubject/enablePrintTempletSubject.do?code='+dataRow.code,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","打印模板代码启用成功!");
			  							$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","打印模板代码启用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>				
					<security:authorize url="/agprint/tplsubject/printTempletSubjectDisableBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler: function(){
							var dataRow =  $("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择要禁用的打印模板代码!");
			  					return false;
			  				}
			  				if(dataRow.code == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要禁用的打印模板代码!");
			  					return false;
			  				}
			  				if(dataRow.state == "0"){
			  					$.messager.alert("提醒","禁用状态不能禁用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'agprint/tplsubject/disablePrintTempletSubject.do?code='+dataRow.code,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","打印模板代码禁用成功!");
			  							$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","打印模板代码禁用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'tplsubject-table-printTempletSubjectList',
				tname:'print_templet_subject',
				id:''
     		});
  			
   		});
	    function subjectADMOperDialog(title, url){
	    	$('<div id="tplsubject-dialog-printTempletSubjectOper-content"></div>').appendTo("#tplsubject-dialog-printTempletSubjectOper");
	   		$('#tplsubject-dialog-printTempletSubjectOper-content').dialog({  
			    title: title,  
			    //fit:true,
			    width: 450,  
			    height: 450,  
			    closed: true,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#tplsubject-dialog-printTempletSubjectOper-content').window("destroy");
			    }
			});
			$('#tplsubject-dialog-printTempletSubjectOper-content').dialog('open');
	   	}
	    var templetSubject_getAjaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
   	</script>
  </body>
</html>
