<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>仓库档案添加页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="storageInfo-form-add">
     	<div style="padding-left: 5px; width: 780px;" >
		<table cellspacing="5px" cellpadding="5px" border="0" width="735px">
            <tr>
		      <td width="120px"><div align="right">编码:</div></td>
		      <td style="width: 200px;">
                  <input name="storageInfo.id" id="storageInfo-id" class="easyui-validatebox" required="required" validType="validID[20]" type="text" style="width:180px;" maxlength="20"></td>
			  <td width="120px"><div align="right">名称:</div></td>
		      <td style="width: 200px;"><input name="storageInfo.name" class="easyui-validatebox" required="required" validType="validName[50]" type="text" style="width:180px;" maxlength="50"></td>
		      <td width="120px"><div align="right">状态:</div></td>
		      <td>
			    <select name="storageInfo.state" style="width:180px;" disabled="disabled">
			      <option selected="selected">新增</option>
			      <option>暂存</option>
			      <option>保存</option>
			      <option>启用</option>
			      <option>禁用</option>
			      <option>盘点</option>
			    </select>
		      </td>
		  </tr>
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666; ">
		  <tr>
		      <td width="120px"><div align="right">仓库类型:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px"><input id="storageInfo-storagetype" type="text" name="storageInfo.storagetype"/></td>
			  <td width="120px"><div align="right" class="carsaleuser" style="display: none;">车销人员:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<div class="carsaleuser" style="display: none;">
		      	<input id="storageInfo-carsaleuser" disabled="disabled" type="text" name="storageInfo.carsaleuser" required="required"/>
		     	</div>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">金额管理方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-moneytype" type="text" name="storageInfo.moenytype" style="width:180px;" required="required"/>
		      </td>
			  <td><div align="right">计价方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-pricetype" type="text" name="storageInfo.pricetype" style="width:180px;" required="required"/>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">是否批次管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isbatch" style="width:180px;">
			        <option value="1">是</option>
			        <option value="0" selected="selected">否</option>
			      </select>
		      </td>
			  <td><div align="right">是否库位管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isstoragelocation" style="width:180px;">
			        <option value="1">是</option>
			        <option value="0" selected="selected">否</option>
			      </select>
		      </td>
		  </tr>
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666;" >
            <tr>
                <td width="120px"><div align="right">仓库是否独立核算:</div></td>
                <td style="width: 5px"></td>
                <td width="240px">
                    <select id="storageInfo-isaloneaccount" name="storageInfo.isaloneaccount" style="width:180px;">
                        <option value="1">是</option>
                        <option value="0" selected="selected">否</option>
                    </select>
                </td>
            </tr>
		  <tr>
		      <td width="120px"><div align="right">是否发货仓库:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select id="storageInfo-issendstorage" style="width:180px;">
			    	<option value="1">是</option>
		        	<option value="0" selected="selected">否</option>
				</select>
				<input id="storageInfo-issendstorage-hidden" type="hidden" name="storageInfo.issendstorage" value="0">
		      </td>
			  <td width="120px"><div align="right">参与总量控制:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select id="storageInfo-istotalcontrol" style="width:180px;">
			    	<option value="1" selected>是</option>
		        	<option value="0">否</option>
				</select>
                  <input id="storageInfo-istotalcontrol-hidden" type="hidden" name="storageInfo.istotalcontrol" value="1">
		      </td>
          </tr>

		  <tr>
		  	  <td width="120px"><div align="right">允许负库存:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select name="storageInfo.islosestorage" style="width:180px;" disabled="disabled">
			    	<option value="1">是</option>
		        	<option value="0" selected="selected">否</option>
				</select>
		      </td>
		      <td><div align="right">允许超可用量发货:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.issendusable" style="width:180px;">
			        <option value="1">是</option>
			        <option value="0" selected="selected">否</option>
			      </select>
		      </td>
		  </tr>
<!-- 		  <tr> -->
<!-- 		  	  <td width="120px"><div align="right">是否允许配货:</div></td> -->
<!-- 			  <td style="width: 5px"></td> -->
<!-- 		      <td width="240px"> -->
<!-- 		      	<select name="storageInfo.isconfig" style="width:180px;"> -->
<!-- 			    	<option value="1" selected="selected">是</option> -->
<!-- 		        	<option value="0">否</option> -->
<!-- 				</select> -->
<!-- 		      </td> -->
<!-- 		      <td colspan="3">允许配货，销售订单未指定仓库时，配置库存时，可以配置到该仓库中。不允许配货，只能通过指定仓库发货。</td> -->
<!-- 		  </tr> -->
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666;" >
		  <tr>
		      <td width="120px"><div align="right">仓库负责人:</div></td>
			  <td style="width: 5px"></td>
		      <td style="width: 240px;"><input id="storageInfo-manageruserid" name="storageInfo.manageruserid" type="text" style="width:180px;"></td>
			  <td width="120px"><div align="right">电话:</div></td>
			  <td style="width: 5px"></td>
		      <td style="width: 240px;"><input id="storageInfo-telphone" name="storageInfo.telphone" type="text" style="width:190px;"></td>
		  </tr>
		  <tr>
		      <td><div align="right">地址:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="5">
		      	<input type="text" name="storageInfo.addr" style="width:590px;"/>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">备注:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="5"><textarea name="storageInfo.remark" cols="" rows="3" style="width:585px;"></textarea></td>
		  </tr>
		</table>
		</div>
	</form>
    <script type="text/javascript">
    	$("#storageInfo-button").buttonWidget("initButtonType","add");
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = ajaxCall({id:value},'basefiles/checkStorageInfoID.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag==false){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var ret = ajaxCall({name:value},'basefiles/checkStorageInfoName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag==false){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
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
	  		}
		});
    	$(function(){
    		$("#storageInfo-storagetype").widget({
                referwid:'RL_T_BASE_STORAGE_TYPE',
                required:true,
    			width:180,
    			onSelect:function(data){
    				//车销
    				if(data.id=='20'){
    					$(".carsaleuser").show();
    					$("#storageInfo-carsaleuser").widget("enable");
    					$("#storageInfo-issendstorage").val("0");
    					$("#storageInfo-issendstorage-hidden").val("0");
    					$("#storageInfo-issendstorage").attr("disabled","disabled");
    				}else{
    					$(".carsaleuser").hide();
    					$("#storageInfo-carsaleuser").widget("disable");
						$("#storageInfo-carsaleuser").widget("clear");
    					//$("#storageInfo-issendstorage").val("1");
    					//$("#storageInfo-issendstorage-hidden").val("1");
    					$("#storageInfo-issendstorage").attr("disabled",false);
    				}
    			}
    		});
    		$("#storageInfo-carsaleuser").widget({
    			name:'t_base_storage_info',
    			col:'carsaleuser',
    			singleSelect:true,
    			width:180
    		});
    		$("#storageInfo-moneytype").widget({
    			name:'t_base_storage_info',
    			col:'moenytype',
    			singleSelect:true,
    			width:180,
    			initValue:'1'
    		});
    		$("#storageInfo-pricetype").widget({
    			name:'t_base_storage_info',
    			col:'pricetype',
    			singleSelect:true,
    			width:180,
    			initValue:'1'
    		});
    		$("#storageInfo-manageruserid").widget({
    			name:'t_base_storage_info',
    			col:'manageruserid',
    			singleSelect:true,
    			width:180,
    			onSelect : function(data,checked){
   					$("#storageInfo-telphone").val(data.tel);
    			},
    			onClear:function(){
    				$("#storageInfo-telphone").val("");
    			}
    		});
    		$("#storageInfo-issendstorage").change(function(){
    			$("#storageInfo-issendstorage-hidden").val($(this).val());
    		});
            $("#storageInfo-istotalcontrol").change(function(){
                $("#storageInfo-istotalcontrol-hidden").val($(this).val());
            });
            $("#storageInfo-isaloneaccount").change(function(){
                var val = $(this).val();
                if(val=="1"){
                    $("#storageInfo-istotalcontrol").attr("disabled",true);
                    $("#storageInfo-istotalcontrol").val("0");
                    $("#storageInfo-istotalcontrol-hidden").val("0");
                }else{
                    $("#storageInfo-istotalcontrol").attr("disabled",false);
                }
            });
    		$("#storageInfo-form-add").form({  
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
			    		$("#storageInfo-panel").panel({  
							fit:true, 
							title: '出入库类型详情',
							cache: false,
							href : "basefiles/showStorageInfoViewPage.do?id="+json.id
						});
						$('#storageInfo-table-list').datagrid("reload");
			    	}else{
			    		$.messager.alert("警告",'新增失败');
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
