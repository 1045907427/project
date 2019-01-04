<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>档案级次定义页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
	    <div data-options="region:'center'">
	    	<form action="basefiles/saveFilesLevel.do" method="post" id="basefiles-fileslevel-form">
	    		<input type="hidden" name="filesLevel" id="basefiles-filesLevel-value"/>
	    		<input type="hidden" name="name" value="${name }"/>
	    	</form>
   			<table style="margin-left: 20px;" id="basefiles-filesLevel-definetable">
   				<tr>
   					<td colspan="3">
                        <a href="javaScript:void(0);" id="addFilesLevel" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="添加">添加</a>
   					</td>
   				</tr>
   				<c:forEach var="list" items="${list}" varStatus="status">
   				<tr id="tr${status.index+1}" class="leveltr">
   					<td width="100px;" class="leveltd"></td>
   					<td width="250px;">
   						<select class="len" name="len" style="width: 200px;" <c:if test="${list.flag=='1'}">disabled="disabled"</c:if>>
   							<option value="1" <c:if test="${list.len=='1'}">selected="selected"</c:if>>1</option>
   							<option value="2" <c:if test="${list.len=='2'}">selected="selected"</c:if>>2</option>
   							<option value="3" <c:if test="${list.len=='3'}">selected="selected"</c:if>>3</option>
   							<option value="4" <c:if test="${list.len=='4'}">selected="selected"</c:if>>4</option>
   							<option value="5" <c:if test="${list.len=='5'}">selected="selected"</c:if>>5</option>
   							<option value="6" <c:if test="${list.len=='6'}">selected="selected"</c:if>>6</option>
   						</select>
   					</td>
   					<td>
   						<input type="hidden" name="level" value="${list.level}"/>
   						<c:if test="${list.flag!='1'}">
   						<input type="button" class="deleteFilesLevel" value="删除" trid="tr${status.index+1 }"/>
   						</c:if>
   					</td>
   				</tr>
   				</c:forEach>
   			</table>
	    </div>
	    <div data-options="region:'south'" style="height: 40px;">
	    	<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="accesscontrol-button-saveEditAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
	    </div>
	 </div>
	 <script type="text/javascript">
	 	var size = ${size};
	 	$(function(){
	 		//添加一级档案编码级次
	 		$("#basefiles-filesLevel-definetable #addFilesLevel").click(function(){
	 			size = Number(size) +Number(1);
	 			var num = 0;
	 			var len = $("#basefiles-filesLevel-definetable .leveltr select").each(function(){
	 				num = Number(num)+Number($(this).val());
	 			});
	 			var size1 = $("#basefiles-filesLevel-definetable tr").size();
	 			var level = Utils.numberToChinese(size1);
	 			var tablehtml = '<tr id="tr'+size+'" class="leveltr"><td width="100px;" class="leveltd">级次'+level+'长度</td><td width="250px;">'+
	 							'<select class="len" name="len" style="width: 200px;">';
	 			if(20-num>6){
	   				tablehtml += '<option value="1">1</option><option value="2" selected="selected">2</option>'+
	   						'<option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option>';
					tablehtml += '</select></td><td><input type="hidden" name="level" value="'+size+'"/><input type="button" class="deleteFilesLevel" value="删除" trid="tr'+size+'"/></td></tr>';
	 				$("#basefiles-filesLevel-definetable").append(tablehtml);
	 			}else if(20>num){
	 				for(var i=1;i<=20-num;i++){
	 					if(i==2){
	 						tablehtml +='<option value="'+i+'" selected="selected">'+i+'</option>';
	 					}else{
	 						tablehtml +='<option value="'+i+'">'+i+'</option>';
	 					}
	 				}
	 				tablehtml += '</select></td><td><input type="hidden" name="level" value="'+size+'"/><input type="button" class="deleteFilesLevel" value="删除" trid="tr'+size+'"/></td></tr>';
	 				$("#basefiles-filesLevel-definetable").append(tablehtml);
	 			}else{
	 				$.messager.alert("提醒","长度不能超过20位,不能再增加级次");
	 			}
	 		});
	 		//删除一级档案编码级次
	 		$("#basefiles-filesLevel-definetable .deleteFilesLevel").live("click",function(){
	 			var trid = $(this).attr("trid");
	 			$("#basefiles-filesLevel-definetable #"+trid).remove();
	 			//从新生成编码级次
	 			var i = 1;
	 			var len = $("#basefiles-filesLevel-definetable .leveltr .leveltd").each(function(){
	 				var level = Utils.numberToChinese(i);
	 				$(this).html("级次"+level+"长度");
	 				i++;
	 			});
	 		});
	 		//编码级次长度改变后，重新生成编码级次定义
	 		$("#basefiles-filesLevel-definetable .len").live("change",function(){
	 			var num = 0;
	 			var level = $("#basefiles-filesLevel-definetable .leveltr select").each(function(){
	 				num = Number(num)+Number($(this).val());
	 			});
	 			var excess = num-20;
	 			if(excess>0){
	 				setFilesLevel(excess);
	 			}
	 			var num1 = 0;
	 			var level1 = $("#basefiles-filesLevel-definetable .leveltr select").each(function(){
	 				num1 = Number(num1)+Number($(this).val());
	 			});
	 			num1 = num1 - $("#basefiles-filesLevel-definetable tr:last").find("select").val();
	 			if(20-num1>0){
		 			var tablehtml = "";
		 			var len = $("#basefiles-filesLevel-definetable tr:last").find("select").val();
	 				for(var i=1;i<=20-num1;i++){
	 					if(i<=6){
		 					if(i==len){
		 						tablehtml +='<option value="'+i+'" selected="selected">'+i+'</option>';
		 					}else{
		 						tablehtml +='<option value="'+i+'">'+i+'</option>';
		 					}
	 					}
	 				}
 					$("#basefiles-filesLevel-definetable tr:last").find("select").html(tablehtml);
 				}
	 		});
	 		//保存
	 		$("#accesscontrol-button-saveEditAuthority").click(function(){
	 			
	 			var array = new Array();
	 			var len = $("#basefiles-filesLevel-definetable .leveltr").each(function(){
	 				var len = $(this).find("select").val();
	 				var level = $(this).find("input").val();
	 				json = {len:len,level:level};
	 				array.push(json);
	 			});
	 			if(len&&len.length>0){
	 				$("#basefiles-filesLevel-value").attr("value",JSON.stringify(array));
	 				$("#basefiles-fileslevel-form").submit();
	 			}else{
	 				$.messager.alert("提醒","请先配置档案级次定义");
	 			}
	 			
	 		});
	 		$("#basefiles-fileslevel-form").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("保存中..");
			    },  
			    success:function(data){
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","保存成功");
			        }else{
			        	$.messager.alert("提醒","保存失败");
			        }
			    }  
			}); 
	 	});
	 	function setFilesLevel(excess){
	 		var level = $("#basefiles-filesLevel-definetable tr:last").find("select option");
	 		if(level&&excess>0){
	 			if(excess-level.length>=0){
	 				$("#basefiles-filesLevel-definetable tr:last").remove();
	 				excess = excess -level.length;
	 				setFilesLevel(excess);
	 			}else{
	 				$("#basefiles-filesLevel-definetable tr:last").remove();
	 				var tablehtml = "";
	 				for(var i=1;i<=excess;i++){
	 					if(i==2){
	 						tablehtml +='<option value="'+i+'" selected="selected">'+i+'</option>';
	 					}else{
	 						tablehtml +='<option value="'+i+'">'+i+'</option>';
	 					}
	 				}
	 				$("#basefiles-filesLevel-definetable tr:last").find("select").html(tablehtml);
	 			}
	 		}
	 	}
	 	
	 	var pagestart = 1;
		$("#basefiles-filesLevel-definetable .leveltr .leveltd").each(function(){
			var level = Utils.numberToChinese(pagestart);
			$(this).html("级次"+level+"长度");
			pagestart++;
		});
	 </script>
  </body>
</html>
