<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>仓库档案修改页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="storageInfo-form-edit">
    	<div style="padding-left: 5px; width: 780px;" >
    	<input type="hidden" id="storageInfo-oldid" name="storageInfo.oldid" value="<c:out value="${storageInfo.id }"></c:out>"/>
    	<input type="hidden" id="storageInfo-oldname" value="<c:out value="${storageInfo.name}"></c:out>"/>
		<table cellspacing="5px" cellpadding="5px" border="0" width="735px">
		  <tr>
		      <td width="120px"><div align="right">编码:</div></td>
		      <td style="width: 200px;"><input name="storageInfo.id" id="storageInfo-id" type="text"  class="easyui-validatebox" required="required" validType="validID[20]" style="width:180px;" value="<c:out value="${storageInfo.id }"></c:out>" maxlength="20" <c:if test="${editFlag==false}">readonly="readonly"</c:if>></td>
			  <td width="120px"><div align="right">名称:</div></td>
		      <td style="width: 200px;"><input name="storageInfo.name" type="text" class="easyui-validatebox" required="required" validType="validName[50]" style="width:180px;" value="<c:out value="${storageInfo.name }"></c:out>" maxlength="50"></td>
		    <td width="120px"><div align="right">状态:</div></td>
		    <td>
			    <select style="width:180px;" disabled="disabled">
			      	<option value="4" <c:if test="${storageInfo.state=='4'}">selected="selected"</c:if>>新增</option>
					<option value="3" <c:if test="${storageInfo.state=='3'}">selected="selected"</c:if>>暂存</option>
					<option value="2" <c:if test="${storageInfo.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="1" <c:if test="${storageInfo.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${storageInfo.state=='0'}">selected="selected"</c:if>>禁用</option>
			    </select>
			    <input type="hidden" name="storageInfo.state" value="${storageInfo.state}">
		    </td>
		  </tr>
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666; ">
		  <tr>
		      <td width="120px"><div align="right">仓库类型:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px"><input id="storageInfo-storagetype" type="text" name="storageInfo.storagetype" value="${storageInfo.storagetype }"/></td>
			  <td width="120px"><div align="right" class="carsaleuser" <c:if test="${storageInfo.storagetype!='20' }">style="display: none;"</c:if>>车销人员:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<div class="carsaleuser" <c:if test="${storageInfo.storagetype!='20' }">style="display: none;"</c:if>>
		      	<input id="storageInfo-carsaleuser" <c:if test="${storageInfo.storagetype!='20' }">disabled="disabled"</c:if> type="text" name="storageInfo.carsaleuser" required="required" value="${storageInfo.carsaleuser }"/>
		     	</div>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">金额管理方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-moneytype" type="text" name="storageInfo.moenytype" style="width:180px;" value="${storageInfo.moenytype}" required="required"/>
		      </td>
			  <td><div align="right">计价方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-pricetype" type="text" name="storageInfo.pricetype" style="width:180px;" value="${storageInfo.pricetype}" required="required"/>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">是否批次管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isbatch" style="width:180px;">
			        <option value="1" <c:if test="${storageInfo.isbatch=='1'}">selected="selected"</c:if>>是</option>
			        <option value="0" <c:if test="${storageInfo.isbatch=='0'}">selected="selected"</c:if>>否</option>
			      </select>
		      </td>
			  <td><div align="right">是否库位管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isstoragelocation" style="width:180px;">
			        <option value="1" <c:if test="${storageInfo.isstoragelocation=='1'}">selected="selected"</c:if>>是</option>
			        <option value="0" <c:if test="${storageInfo.isstoragelocation=='0'}">selected="selected"</c:if>>否</option>
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
                        <option value="1" <c:if test="${storageInfo.isaloneaccount=='1'}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${storageInfo.isaloneaccount=='0'}">selected="selected"</c:if>>否</option>
                    </select>
                </td>
            </tr>
		  <tr>
		      <td width="120px"><div align="right">是否发货仓库:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select id="storageInfo-issendstorage"  style="width:180px;" <c:if test="${storageInfo.storagetype=='20' }">disabled="disabled"</c:if>>
			    	<option value="1" <c:if test="${storageInfo.issendstorage=='1'}">selected="selected"</c:if>>是</option>
		        	<option value="0" <c:if test="${storageInfo.issendstorage=='0'}">selected="selected"</c:if>>否</option>
				</select>
				<input type="hidden" id="storageInfo-issendstorage-hidden" name="storageInfo.issendstorage" value="${storageInfo.issendstorage }">
		      </td>
			  <td width="120px"><div align="right">参与总量控制:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select id="storageInfo-istotalcontrol" style="width:180px;" <c:if test="${storageInfo.isaloneaccount=='1' }">disabled="disabled"</c:if>>
			    	<option value="1" <c:if test="${storageInfo.istotalcontrol=='1'}">selected="selected"</c:if>>是</option>
			        <option value="0" <c:if test="${storageInfo.istotalcontrol=='0'}">selected="selected"</c:if>>否</option>
				</select>
                  <input id="storageInfo-istotalcontrol-hidden" type="hidden" name="storageInfo.istotalcontrol" value="${storageInfo.istotalcontrol}">
		      </td>
          </tr>
		  <tr>
		  	  <td width="120px"><div align="right">允许负库存:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select name="storageInfo.islosestorage" style="width:180px;" disabled="disabled">
			    	<option value="1" <c:if test="${storageInfo.islosestorage=='1'}">selected="selected"</c:if>>是</option>
			        <option value="0" <c:if test="${storageInfo.islosestorage=='0'}">selected="selected"</c:if>>否</option>
				</select>
		      </td>
		      <td><div align="right">允许超可用量发货:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.issendusable" style="width:180px;">
			        <option value="1" <c:if test="${storageInfo.issendusable=='1'}">selected="selected"</c:if>>是</option>
			        <option value="0" <c:if test="${storageInfo.issendusable=='0'}">selected="selected"</c:if>>否</option>
			      </select>
		      </td>
		  </tr>
<!-- 		  <tr> -->
<!-- 		  	  <td width="120px"><div align="right">是否允许配货:</div></td> -->
<!-- 			  <td style="width: 5px"></td> -->
<!-- 		      <td width="240px"> -->
<!-- 		      	<select name="storageInfo.isconfig" style="width:180px;"> -->
<%-- 			    	<option value="1" <c:if test="${storageInfo.isconfig=='1'}">selected="selected"</c:if>>是</option> --%>
<%-- 		        	<option value="0" <c:if test="${storageInfo.isconfig=='0'}">selected="selected"</c:if>>否</option> --%>
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
		      <td style="width: 240px;"><input id="storageInfo-manageruserid" name="storageInfo.manageruserid" type="text" style="width:180px;" value="<c:out value="${storageInfo.manageruserid }"></c:out>"></td>
			  <td width="120px"><div align="right">电话:</div></td>
			  <td style="width: 5px"></td>
		      <td style="width: 240px;"><input id="storageInfo-telphone" name="storageInfo.telphone" type="text" style="width:180px;"  value="<c:out value="${storageInfo.telphone}"></c:out>"></td>
		  </tr>
		  <tr>
		      <td><div align="right">地址:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="4">
		      	<input type="text" name="storageInfo.addr" style="width:580px;" value="<c:out value="${storageInfo.addr }"></c:out>"/>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">备注:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="4"><textarea name="storageInfo.remark" cols="" rows="3" style="width:575px;"><c:out value="${storageInfo.remark }"></c:out></textarea></td>
		  </tr>
		</table>
		</div>
    </form>
    <script type="text/javascript">
    	//控制按钮状态
		$("#storageInfo-button").buttonWidget("setDataID",{id:$("#storageInfo-oldid").val(),state:'${storageInfo.state}',type:'edit'});
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var oldid = $("#storageInfo-oldid").val();
						if(oldid!=value){
							var ret = ajaxCall({id:value},'basefiles/checkStorageInfoID.do');
							var retJson = $.parseJSON(ret);
							if(retJson.flag==false){
								$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
								return false;
							}
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
						var oldname = $("#storageInfo-oldname").val();
						if(oldname!=value){
							var ret = ajaxCall({name:value},'basefiles/checkStorageInfoName.do');
							var retJson = $.parseJSON(ret);
							if(retJson.flag==false){
								$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
								return false;
							}
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
    			width:180,
                required:true,
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
    			singleSelect:false,
    			width:180
    		});
    		$("#storageInfo-moneytype").widget({
    			name:'t_base_storage_info',
    			col:'moenytype',
    			singleSelect:true,
    			width:180
    		});
    		$("#storageInfo-pricetype").widget({
    			name:'t_base_storage_info',
    			col:'pricetype',
    			singleSelect:true,
    			width:180
    		});
    		$("#storageInfo-manageruserid").widget({
    			name:'t_base_storage_info',
    			col:'manageruserid',
    			singleSelect:true,
    			width:180
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
    		$("#storageInfo-form-edit").form({  
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
			    		$("#storageInfo-panel").panel({  
							fit:true, 
							title: '仓库档案详情',
							cache: false,
							href : "basefiles/showStorageInfoViewPage.do?id="+json.id
						});
						$('#storageInfo-table-list').datagrid("reload");
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
