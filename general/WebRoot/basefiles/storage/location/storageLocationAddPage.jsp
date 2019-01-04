<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库位档案新增页面</title>
  </head>
  
  <body>
  	<form action="" id="storageLocation-form-add" method="post">
  		<input type="hidden" name="parentId" id="storageLocation-parentID" value="<c:out value="${storageLocation.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="storageLocation-parentName" value="<c:out value="${storageLocation.name }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label style="text-align: right;">本级编码：</label>
	    		<input type="text" class="easyui-validatebox" name="storageLocation.thisid" id="storageLocation-thisid" required="required" validType="validLength[${nextLen}]" maxlength="${nextLen}"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">编码：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${storageLocation.id }"></c:out>" name="storageLocation.id" id="storageLocation-id" />
	    	</p>
	    	<p>
	    		<label style="text-align: right;">本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="storageLocation.thisname" id="storageLocation-thisname" required="true" validType="isRepeatName[20]"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${storageLocation.name }"></c:out>" name="storageLocation.name" id="storageLocation-name" />
	    	</p>
	    	<p>
	    		<label style="text-align: right;">上级库位：</label>
	    		<input type="text" id="storageLocation-parent" value="<c:out value="${storageLocation.id }"></c:out>" name="storageLocation.pid" />
	    	</p>
	    	<p>
	    		<label style="text-align: right;">所属仓库：</label>
	    		<input type="text" id="storageLocation-storageid" name="storageLocation.storageid" />
	    	</p>
	    	<p>
	    		<label style="text-align: right;">库位体积(m<sup>3</sup>)：</label>
	    		<input type="text" id="storageLocation-volume" name="storageLocation.volume" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">当前商品箱数：</label>
	    		<input type="text" id="storageLocation-boxnum" name="storageLocation.boxnum" readonly="readonly" class="easyui-numberbox" data-options="min:0,precision:0,groupSeparator:','"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">是否空置：</label>
	    		<select id="storageLocation-isempty" name="storageLocation.isempty" disabled="disabled">
	    			<option></option>
	    			<option value="0">否</option>
	    			<option value="1" selected="selected">是</option>
	    		</select>
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label style="text-align: right;">备注：</label>
	    		<textarea name="storageLocation.remark" style="height: 100px;width: 195px;"></textarea>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="storageLocation.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$("#storageLocation-button").buttonWidget("initButtonType","add");
    	
    	//验证编码长度和是否重复
    	validLengthAndUsed('${nextLen}', "basefiles/checkStorageLocationID.do", $("#storageLocation-parentID").val(), '', "编码重复请重新输入！"); 
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
    		$("#storageLocation-form-add").form({  
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
			    		$.messager.alert("提醒",'新增成功');
                        var id = $("#storageLocation-viewid").val();
                        var pid = $("#sales-parentId-salesArea").val();
						var treeObj = $.fn.zTree.getZTreeObj("storageLocation-tree");
		  		      	var node = treeObj.getNodeByParam("id", id, null);
		  		      	treeObj.addNodes(node, json.node); //增加子节点
                        var snode = treeObj.getNodeByParam("id", json.node.id, null);
                        treeObj.selectNode(snode, false); //选中节点
                        zTreeBeforeClick("storageLocation-tree", snode);
                        refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+json.id);
		  		      	$("#storageLocation-button").buttonWidget("setDataID", {id:json.node.id, state:json.node.state,type:'view'});
			    	}else{
			    		$.messager.alert("警告",'新增失败');
			    	}
			    }  
			}); 
    	});
    	
    </script>
  </body>
</html>
