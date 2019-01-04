<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库位档案新增页面</title>
  </head>
  
  <body>
  	<form action="" id="storageLocation-form-edit" method="post">
  		<input type="hidden" name="parentId" id="storageLocation-parentID" value="<c:out value="${storageLocation.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="storageLocation-parentName" />
        <input type="hidden" id="storageLocation-hidden-hdState" name="storageLocation.state" value="${storageLocation.state}"/>
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label style="text-align: right;">本级编码：</label>
	    		<input type="text" class="easyui-validatebox"  name="storageLocation.thisid" id="storageLocation-thisid" required="required" validType="validLength[${nextLen}]" maxlength="${nextLen}" value="<c:out value="${storageLocation.thisid }"></c:out>" <c:if test="${editFlag==false}">readonly="readonly"</c:if>/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">编码：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${storageLocation.id }"></c:out>" name="storageLocation.id" id="storageLocation-id" />
	    		<input type="hidden" id="storageLocation-oldid" name="storageLocation.oldid" value="<c:out value="${storageLocation.id }"></c:out>"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">本级名称：</label>
	    		<input type="text" class="easyui-validatebox"  name="storageLocation.thisname" id="storageLocation-thisname" required="true" validType="isRepeatName[20]" value="<c:out value="${storageLocation.thisname}"></c:out>"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${storageLocation.name }"></c:out>" name="storageLocation.name" id="storageLocation-name" />
	    	</p>
	    	<p>
	    		<label style="text-align: right;">上级库位：</label>
	    		<input type="text" id="storageLocation-parent" value="<c:out value="${storageLocation.pid }"></c:out>" name="storageLocation.pid"<c:if test="${editFlag==false}"> disabled="disabled"</c:if>/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">所属仓库：</label>
	    		<input type="text" id="storageLocation-storageid" name="storageLocation.storageid" value="<c:out value="${storageLocation.storageid }"></c:out>"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">库位体积(m<sup>3</sup>)：</label>
	    		<input type="text" id="storageLocation-volume" name="storageLocation.volume" value="${storageLocation.volume }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">当前商品箱数：</label>
	    		<input type="text" id="storageLocation-boxnum" name="storageLocation.boxnum" value="${storageLocation.boxnum }" readonly="readonly" class="easyui-numberbox" data-options="min:0,precision:0,groupSeparator:','"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">是否空置：</label>
	    		<select id="storageLocation-isempty" name="storageLocation.isempty" disabled="disabled">
	    			<option></option>
	    			<option value="0" <c:if test="${storageLocation.isempty=='0'}">selected="selected"</c:if>>否</option>
	    			<option value="1" <c:if test="${storageLocation.isempty=='1'}">selected="selected"</c:if>>是</option>
	    		</select>
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label style="text-align: right;">备注：</label>
	    		<textarea name="storageLocation.remark" style="height: 100px;width: 195px;"><c:out value="${storageLocation.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == storageLocation.state}">
	    						<option value="${list.code }" selected="selected">${list.codename }</option>
	    					</c:when>
	    					<c:otherwise>
	    						<option value="${list.code }">${list.codename }</option>
	    					</c:otherwise>
	    				</c:choose>
	    			</c:forEach>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$("#storageLocation-button").buttonWidget("setDataID", {id:$("#storageLocation-oldid").val(), state:${storageLocation.state},type:'edit'});
    	//验证编码长度和是否重复
    	validLengthAndUsed('${nextLen}', "basefiles/checkStorageLocationID.do", $("#storageLocation-parentID").val(), $("#storageLocation-thisid").val(), "编码重复请重新输入！"); 
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = ajaxCall({id:value},'basefiles/checkStorageLocationID.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag==false){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '请输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		},
	  		isRepeatName:{//true 不重复，false 重复
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						if($("#storageLocation-thisname").val() == value){
   							return true;
   						}
   						var ret = ajaxCall({thisname:value},'basefiles/isRepeatStorageLocationThisname.do');
   						var retJson = $.parseJSON(ret);
   						if(!retJson.flag){
   							$.fn.validatebox.defaults.rules.isRepeatName.message ='名称重复,请修改!';
   							return false;
   						}
   						else{return true;}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.isRepeatName.message ='请输入{0}个字符!';
   						return false;
   					}
   				},
   				message:''
   			}
		});
    	$(function(){
    		$("#storageLocation-storageid").widget({
    			name:'t_base_storage_location',
				col:'storageid',
    			singleSelect:true,
    			required:true,
    			width:200
    		});
    		$("#storageLocation-parent").widget({
    			name:'t_base_storage_location',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#storageLocation-id").val(data.id + $("#storageLocation-thisid").val());
    					$("#storageLocation-name").val(data.name + '/' + $("#storageLocation-thisname").val());
    					$("#storageLocation-parentID").val(data.id);
                        $("#storageLocation-viewid").val(data.id);
    					$("#storageLocation-parentName").val(data.name);
    					//树状编码长度控制
    					var ret = ajaxCall({id:data.id,tname:'base_storage_location'},'common/getBaseFilesLevelLength.do');
						var retJson = $.parseJSON(ret);
						if(retJson.len==0){
							$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#storageLocation-button").buttonWidget("disableButton", "button-hold");
    						$("#storageLocation-button").buttonWidget("disableButton", "button-save");
    						return false;
						}else{
							$("#storageLocation-button").buttonWidget("enableButton", "button-hold");
    						$("#storageLocation-button").buttonWidget("enableButton", "button-save");
						}
    					$("#storageLocation-thisid").attr("maxlength",retJson.len);
    					validLengthAndUsed(retJson.len, "basefiles/checkStorageLocationID.do", $("#storageLocation-parentID").val(), '', "编码重复请重新输入！"); 
    				}
    				else{
    					$("#storageLocation-id").val($("#storageLocation-thisid").val());
    					$("#storageLocation-name").val($("#storageLocation-thisname").val());
    					$("#storageLocation-parentID").val("");
                        $("#storageLocation-viewid").val("");
    					$("#storageLocation-parentName").val("");
    					//获取下级编码长度
    					var ret = ajaxCall({id:"",tname:'base_storage_location'},'common/getBaseFilesLevelLength.do');
						var retJson = $.parseJSON(ret);
    					$("#storageLocation-thisid").attr("maxlength",retJson.len);
    					$("#storageLocation-button").buttonWidget("enableButton", "button-hold");
   						$("#storageLocation-button").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(retJson.len, "basefiles/checkStorageLocationID.do", $("#storageLocation-parentID").val(), '', "编码重复请重新输入！"); 
    				}
    			},
    			onClear :function(){
    				$("#storageLocation-id").val($("#storageLocation-thisid").val());
   					$("#storageLocation-name").val($("#storageLocation-thisname").val());
   					$("#storageLocation-parentID").val("");
                    $("#storageLocation-viewid").val("");
   					$("#storageLocation-parentName").val("");
   					var ret = ajaxCall({id:"",tname:'base_storage_location'},'common/getBaseFilesLevelLength.do');
					var retJson = $.parseJSON(ret);
   					$("#storageLocation-thisid").attr("maxlength",retJson.len);
   					$("#storageLocation-button").buttonWidget("enableButton", "button-hold");
					$("#storageLocation-button").buttonWidget("enableButton", "button-save");
   					validLengthAndUsed(retJson.len, "basefiles/checkStorageLocationID.do", $("#storageLocation-parentID").val(), '', "编码重复请重新输入！"); 
    			},
    			onLoadSuccess :function(){
    				var pname = $("#storageLocation-parent").widget("getText");
    				$("#storageLocation-parentName").val(pname);
    			}
    		});
    		//本级编码改变 改变编码
    		$("#storageLocation-thisid").change(function(){
    			$("#storageLocation-id").val($("#storageLocation-parentID").val() + $(this).val());
    		});
    		//本级名称改变
    		$("#storageLocation-thisname").change(function(){
    			var name = $("#storageLocation-parentName").val();
    			if(name == ""){
    				$("#storageLocation-name").val($(this).val());
    			}
    			else{
    				$("#storageLocation-name").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#storageLocation-name").val(name);
    			}
    		});
    		$("#storageLocation-form-edit").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒",'修改成功');
			    		var oldid = $("#storageLocation-oldid").val();
			    		var map = json.nodes;
		       			var treeObj = $.fn.zTree.getZTreeObj("storageLocation-tree");
		       			for(var key in map){
		       				var object = map[key];
		       				var node = treeObj.getNodeByParam("id", key, null);
		       				node.id = object.id;
							node.name = object.text;
							node.parentid = object.parentid;
							node.state = object.state;
							treeObj.updateNode(node);
							if(key == oldid){
		       					$("#storageLocation-button").buttonWidget("setDataID", {id:object.id, state:object.state,type:'view'});
		       				}
		       			}
                        refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+json.id);
			    	}else{
			    		if(json.lockFlag == false){
			    			$.messager.alert("警告",'该数据正在被其他人修改，暂不能操作');
			    		}else{
			    			$.messager.alert("警告",'修改失败');
			    		}
			    	}
			    }  
			}); 
    	});
    	
    </script>
  </body>
</html>
