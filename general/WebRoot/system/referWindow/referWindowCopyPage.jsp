<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>参照窗口修改页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
  	<div title="参照窗口复制" data-options="region:'center'" style="overflow: auto;">
	<div style="padding: 20px 20px 20px 20px;">
		<form action="" method="post" id="referWindow-form-referwindow">
		<table style="width: 98%">
			<tr>
				<td>编号</td>
				<td>
					<input type="text" class="easyui-validatebox" required="true"  validType="referWindomID" name="referWindow.id" style="width:200px;" maxlength="50"/>
				</td>
				<td>名称:</td>
				<td>
					<input type="text" class="easyui-validatebox" id="referWindow-input-wname" name="referWindow.wname" style="width:200px;" value="${referWindow.wname }"  required="true" maxlength="50"/>
				</td>
				<td>状态:</td>
				<td>
					<select name="referWindow.state" style="width: 200px;" disabled="disabled">
						<option value="4" <c:if test="${referWindow.state=='4'}">selected="selected"</c:if>>新增</option>
						<option value="3" <c:if test="${referWindow.state=='3'}">selected="selected"</c:if>>暂存</option>
						<option value="2" <c:if test="${referWindow.state=='2'}">selected="selected"</c:if>>保存</option>
						<option value="1" <c:if test="${referWindow.state=='1'}">selected="selected"</c:if>>启用</option>
						<option value="0" <c:if test="${referWindow.state=='0'}">selected="selected"</c:if>>禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>操作SQL:</td>
				<td colspan="5">
					<textarea class="easyui-validatebox" required="true" validType="sqlCheck" id="referWindow-textarea-sql" name="referWindow.sqlstr" style="height: 60px;width: 80%">${referWindow.sqlstr }</textarea>
					<a href="javaScript:void(0);" id="referWindow-sql-addwindow" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">生成明细</a>
					<input type="hidden" id="referWindow-addwindow-flag" value="1"/>
				</td>
			</tr>
			<tr>
				<td>查看SQL:</td>
				<td colspan="5">
					<textarea class="easyui-validatebox" required="true" validType="sqlCheck" name="referWindow.viewsql" style="height: 60px;width: 80%">${referWindow.viewsql }</textarea>
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td>涉及到的表:</td>--%>
				<%--<td colspan="3">--%>
					<%--<input type="text" id="referWindow-input-tables" required="true" style="width: 400px;"/>--%>
					<%--<input type="hidden" id="referWindow-hidden-tables" name="referWindow.tables" value="${referWindow.tables }">--%>
				<%--</td>--%>
			<%--</tr>--%>
			<tr>
                <td>数据结构</td>
                <td>
                    <select id="referWindow-select-model" name="referWindow.model" style="width: 200px;">
                        <option value="normal" <c:if test="${referWindow.model=='normal'}">selected="selected"</c:if>>普通</option>
                        <option value="tree" <c:if test="${referWindow.model=='tree'}">selected="selected"</c:if>>树形</option>
                    </select>
                </td>
				<td>备注:</td>
				<td colspan="5">
					<input type="text" name="referWindow.remark" style="width: 70%" value="${referWindow.remark }" maxlength="100"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="referWindow-div-detail" class="easyui-tabs" data-options='border:false'>
		<div title="列明细">  
			<form action="" method="post" id="referWindow-form-column">
	        <table id="referWindow-table-collumnlist"></table>
	        </form>
	        <input type="hidden" id="referWindow-column-index"/>
	    </div>
	</div>
    </div> 
  	<div data-options="region:'south'" style="height: 40px;">
  		<div class="buttonDivR">
    		<a href="javaScript:void(0);" id="referWindow-button-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
    	</div>
  	</div>
  </div>
  <script type="text/javascript">
  		//获取编码类型列表
  		var codetypejson = null;
  		$.ajax({   
	            url :'common/sysCodeType.do',
	            type:'post',
	            dataType:'json',
	            success:function(json){
	            	codetypejson = json;
	            }
	    });
	    function createCode(val,index){
  			if(null!=codetypejson){
	  			var html = '<select name="referWColumnList['+index+'].codetype" style="width:90px"><option value="">无</option>';
	           	for(var i=0;i<codetypejson.length;i++){
	           		if(codetypejson[i].type==val){
	           			html+='<option value="'+codetypejson[i].type+'" selected="selected">'+codetypejson[i].typename+'</option>';
	           		}else{
	           			html+='<option value="'+codetypejson[i].type+'">'+codetypejson[i].typename+'</option>';
	           		}
	           	}
		        return html;
	        }
  		}
	  	$.extend($.fn.validatebox.defaults.rules, {
			referWindomID:{
		    	validator : function(value, param){ 
	    			var flag = false;
			  		$.ajax({   
			            url :'system/referWindow/checkReferWindowID.do?id='+value,
			            type:'post',
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	if(json.flag){
			            		flag = true;
			            	}else{
			            		flag = false;
			            	}
			            }
			        });
			        return flag;
			    }, 
			    message : '编号重复,请重新输入!'
		    }
		});
  		$("#referWindow-input-tables").combogrid({  
		    url:'common/getTableList.do',
  	 		rownumbers:true,
		    idField:'id',  
		    textField:'id', 
		    multiple:true,
		    columns:[[  
		        {field:'id',title:'表名',width:150},  
		        {field:'name',title:'描述名',width:200}
		    ]]
		});  
  		//列明细
  		$("#referWindow-table-collumnlist").datagrid({ 
  			columns:[[
  				{field:'col',title:'列名',width:100,
  					formatter:function(val,rowData,rowIndex){
		        		var html ='<input type="text" name="referWColumnList['+rowIndex+'].col" value="'+val+'"/>';
		        		return html;
		        	}
  				},
  				{field:'colname',title:'列描述',width:200,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						var html = '<input type="text" id="referWindow-input-columnname'+rowIndex+'" name="referWColumnList['+rowIndex+'].colname" class="easyui-validatebox" required="true" value="'+val+'" style="width:180px;"/>';
		        		return html;
		        	}
  				},
  				{field:'isquote',title:'引用情况',width:100,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						var model = $("#referWindow-select-model").val();
  						var html = '<select id="referWindow-select-isquote'+rowIndex+'" class="referWindow-isquote" name="referWColumnList['+rowIndex+'].isquote" style="width:80px;"><option value="1"';
  						if(model=='normal'){
	  						if(val=='1'){
	  							html += ' selected="selected">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="6">下拉显示</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='2'){
	  							html += '">主键</option><option value="2" selected="selected">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="6">下拉显示</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='3'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3" selected="selected">主键并显示</option><option value="4">不显示</option><option value="6">下拉显示</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='4'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4" selected="selected">不显示</option><option value="6">下拉显示</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='6'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="6" selected="selected">下拉显示</option><option value="0">窗口显示</option></select>';
	  						}else{
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="6">下拉显示</option><option value="0" selected="selected">窗口显示</option></select>';
	  						}
  						}else if(model=='tree'){
  							if(val=='1'){
	  							html += ' selected="selected">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="5">父节点</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='2'){
	  							html += '">主键</option><option value="2" selected="selected">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="5">父节点</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='3'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3" selected="selected">主键并显示</option><option value="4">不显示</option><option value="5">父节点</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='4'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4" selected="selected">不显示</option><option value="5">父节点</option><option value="0">窗口显示</option></select>';
	  						}else if(val=='5'){
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="5" selected="selected">父节点</option><option value="0">窗口显示</option></select>';
	  						}else{
	  							html += '">主键</option><option value="2">文本框显示</option><option value="3">主键并显示</option><option value="4">不显示</option><option value="5">父节点</option><option value="0" selected="selected">窗口显示</option></select>';
	  						}

  						}
		        		return html;
		        	}
  				},
  				{field:'seq',title:'排序',width:100,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						if(val==null){
  							val = rowIndex;
  						}
  						var html = '<input id="referWindow-input-seq'+rowIndex+'" name="referWColumnList['+rowIndex+'].seq" type="text" style="width:90px;" value="'+val+'"/>';
		        		return html;
  					}
  				},
  				{field:'width',title:'宽度',width:100,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						if(val==null){
  							val = 100;
  						}
  						var html = '<input id="referWindow-input-width'+rowIndex+'" name="referWColumnList['+rowIndex+'].width" type="text" style="width:90px;" value="'+val+'"/>';
		        		return html;
  					}
  				},
  				{field:'codetype',title:'编码类型',width:100,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						var html = createCode(val,rowIndex);
		        		return html;
		        	}
  				},
  				{field:'orderbyseq',title:'字段排序',width:100,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						var htmlsb=new Array();
  						htmlsb.push('<select id="referWindow-select-orderbyseq');
  						htmlsb.push(rowIndex);
  						htmlsb.push('" name="referWColumnList[');
  						htmlsb.push(rowIndex);
  						htmlsb.push('].orderbyseq" style="width:80px;">');
						htmlsb.push('<option value="0">无</option>');
						htmlsb.push('<option value="1"');
						if(val=="1"){
							htmlsb.push(' selected="selected" ');
						}
						htmlsb.push('>从小到大</option>');
  						htmlsb.push('"<option value="2"');
  						if(val=="2"){
							htmlsb.push(' selected="selected" ');
						}
  						htmlsb.push('>从大到小</option>');
  						htmlsb.push('</select>');
  						return htmlsb.join('');
		        	}
  				},
  				{field:'remark',title:'备注',width:200,align:'center',
  					formatter:function(val,rowData,rowIndex){
  						if(val==null){
  							val = "";
  						}
		        		return '<input name="referWColumnList['+rowIndex+'].remark" type="text" style="width:180px;" value="'+val+'"/>';
		        	}
  				}
  			]],
  			border:false,
  			rownumbers:true,
  			singleSelect:true,
  			idField:'col',
  			url:'system/referWindow/getReferWindowColumn.do?referWid=${id}',
  			onSelect:function(rowIndex, rowData){
  				$("#referWindow-column-index").attr("value",rowIndex);
  			}
  		});
  		$(function(){
  			//根据sql语句生成参照窗口
  			$("#referWindow-sql-addwindow").click(function(){
//  				var tables = $('#referWindow-input-tables').combogrid('getValues');
//  				if(null==tables||""==tables){
//  					$.messager.alert("提醒","请选择涉及到的表!");
//  					return false;
//  				}
  				var sql = $("#referWindow-textarea-sql").val();
  				if(null==sql||sql.trim()==""){
  					$.messager.alert("提醒","请先输入SQL语句才能生成参照窗口!");
  					return false;
  				}
  				
  				$.ajax({   
		            url :'system/referWindow/getReferWindowBySQL.do',
		            type:'post',
		            data:{'sql':sql},
		            dataType:'json',
		            success:function(json){
		            	var columns = json.colList;
		            	var rows = $("#referWindow-table-collumnlist").datagrid('getRows');
		            	for(var i=0;i<rows.length;i++){
		            		var addflag = true;
		            		for(var j=0;j<columns.length;j++){
		            			if(columns[j].col==rows[i].col){
		            				addflag = false;
		            				break;
		            			}
		            		}
		            		if(addflag){
		            			var index = $("#referWindow-table-collumnlist").datagrid('getRowIndex',rows[i].col);
		            			$("#referWindow-table-collumnlist").datagrid('deleteRow',index);
		            		}
		            	}
		            	for(var i=0;i<columns.length;i++){
		            		var addflag = true;
		            		for(var j=0;j<rows.length;j++){
		            			if(columns[i].col==rows[j].col){
		            				addflag = false;
		            				break;
		            			}
		            		}
		            		if(addflag){
		            			$("#referWindow-table-collumnlist").datagrid('appendRow',columns[i]);
		            		}
		            	}
		            	
		            },
		            error:function(){
		            	$.messager.alert("提醒","SQL语句错误,请重新输入!");
		            }
		        });
  				//修改生成明细的状态 表示该sql已经检验过
	            $("#referWindow-addwindow-flag").attr("value","1");
  			});
  			
  			$(".referWindow-tableselect").live("change",function(){
  				var targetid = $(this).attr("targetid");
  				var name = $(this).val();
  				$("#"+targetid).combogrid({
            		panelWidth:400,
            		url:'common/getTableColList.do?name='+name,
            		idField:'id',  
				    textField:'name', 
				    columns:[[  
				        {field:'id',title:'字段名',width:150},  
				        {field:'name',title:'描述名',width:200}
				    ]],
				    onSelect:function(rowIndex,rowData){
				    	var index  =  $("#referWindow-column-index").val();
				    	$("#referWindow-input-columnname"+index).attr("value",rowData.name);
				    }
            	});
  			});
  			//保存
  			$("#referWindow-button-save").click(function(){
  				//列明细引用情况验证
  				var keyFlag = false;
  				var nameFlag = false;
  				for(var i=0;i<$(".referWindow-isquote").length;i++){
					var thisval =  $("#referWindow-select-isquote"+i).val();
					if(thisval=='1'){
						keyFlag = true;
					}else if(thisval=='2'){
						nameFlag = true;
					}else if(thisval=='3'){
						keyFlag = true;
						nameFlag = true;
					}
				}
				if(!(keyFlag&&nameFlag)){
					$.messager.alert("提醒",'列明细中，各个列当中引用情况必须得有主键和显示！');
					return false;
				}
  			
  	  			//涉及到的表名
//  				var tables = $('#referWindow-input-tables').combogrid('getValues');
//				var tablesStr = "";
//				for(var i=0;i<tables.length;i++){
//					if(i==0){
//						tablesStr = tables[i];
//					}else{
//						tablesStr += ","+tables[i];
//					}
//            	}
//				$("#referWindow-hidden-tables").attr("value",tablesStr);
  	  			
  	  			var flag = $("#referWindow-addwindow-flag").val();
  	  			if(flag=='0'){
  	  				$.messager.alert("提醒",'请先根据sql生成明细后再保存!');
					return false;
  	  	  		}
  	  			//验证表单
  				if(!$("#referWindow-form-referwindow").form('validate')||!$("#referWindow-form-column").form('validate')){
					return false;
  	  			}
				var base = $("#referWindow-form-referwindow").serializeJSON();
				var column = $("#referWindow-form-column").serializeJSON();
				for(var key in column){
					base[key] = column[key];
				}
				
				loading("提交中..");
				$.ajax({
					type: 'POST',
					url: 'system/referWindow/addReferWindow.do',
					data: base,
					dataType: 'json',
					success: function(json){
						loaded();
						if(json.flag){
							top.$.messager.alert("提醒",'添加成功');
							tabsWindowURL("/system/referWindow/showReferenceWindowPage.do").$("#referWindow-table-list").datagrid("reload");
							//关闭新增窗口
							top.closeTab('参照窗口复制');
						}else{
							$.messager.alert("提醒",'添加失败');
						}
					},
					error:function(){
						loaded();
						$.messager.alert("提醒",'出现错误！');
					}
				});
  			});
  			//引用情况变更
  			$(".referWindow-isquote").live("change",function(){
  				var val =  $(this).val();
  				var id = $(this).attr("id");
  				//引用情况为主键时
  				if(val=="1"){
  					for(var i=0;i<$(".referWindow-isquote").length;i++){
  						var thisid = $("#referWindow-select-isquote"+i).attr("id");
  						if(id!=thisid){
  							var thisval =  $("#referWindow-select-isquote"+i).val();
  							if(thisval=="1" || thisval=="3"){
  								$("#referWindow-select-isquote"+i).attr("value","0");
  							}
  						}
  					}
  				}else if(val=="2"){ //引用情况为显示时
  					for(var i=0;i<$(".referWindow-isquote").length;i++){
  						var thisid = $("#referWindow-select-isquote"+i).attr("id");
  						if(id!=thisid){
  							var thisval =  $("#referWindow-select-isquote"+i).val();
  							if(thisval=="2" || thisval=="3"){
  								$("#referWindow-select-isquote"+i).attr("value","0");
  							}
  						}
  					}
  				}else if(val=="3"){ //引用情况为主键并显示时
  					for(var i=0;i<$(".referWindow-isquote").length;i++){
  						var thisid = $("#referWindow-select-isquote"+i).attr("id");
  						if(id!=thisid){
  							var thisval =  $("#referWindow-select-isquote"+i).val();
  							if(thisval=="1" || thisval=="2"){
  								$("#referWindow-select-isquote"+i).attr("value","0");
  							}
  						}
  					}
  				}else if(val=='5'){	//引用情况为父节点时
  					for(var i=0;i<$(".referWindow-isquote").length;i++){
  						var thisid = $("#referWindow-select-isquote"+i).attr("id");
  						if(id!=thisid){
  							var thisval =  $("#referWindow-select-isquote"+i).val();
  							if(thisval=="5"){
  								$("#referWindow-select-isquote"+i).attr("value","0");
  							}
  						}
  					}
  				}
  			});
  		});
  		
  		var referWindowTables = '${referWindow.tables }';
  		var referWindowTablesArr = referWindowTables.split(",");
  		$("#referWindow-input-tables").combogrid("setValues",referWindowTablesArr);
  </script>
  </body>
</html>
