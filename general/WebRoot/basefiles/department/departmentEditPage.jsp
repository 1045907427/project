<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门编辑信息</title>
  </head>
  
  <body>
  <input type="hidden" id="department-hidden-state" value="${departMent.state}"/>
     <form action="basefiles/editDepartMent.do" id="department-form-departmentEdit" method="post">
   			<table class="pageContent" cellpadding="5" cellspacing="0" border="0" align="center">
		    	<tr>
		    		<td align="right" style="width: 120px;">本级编码:</td>
		    		<td><input type="text" id="departMent-input-thisId" name="departMent.thisid" <c:if test="${editFlag == false}">readonly="readonly"</c:if> onchange="getValueEdit(this.value)"  value="<c:out value="${departMent.thisid }"></c:out>" style="width:200px;" class="easyui-validatebox" validType="validLen"/>
				   		<input type="hidden" id="departMent-hidden-hdLenEdit" value="${length }"/>
				   		<input type="hidden" id="departMent-hidden-hdType" value="edit"/>
				   		<input type="hidden" id="departMent-hidden-hdpoldIdEdit" name="departMent.oldId" value="<c:out value="${oldId }"></c:out>"/>
				    	<input type="hidden" id="departMent-hidden-hdClickState" name="departMent.state"/>
				    	<input type="hidden" id="departMent-hidden-hdThisId"/>
				    	<input id="departMent-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
				    	<input id="departMent-hidden-hdPid" type="hidden"/>
				    	<input type="hidden" id="departMent-sid" value="<c:out value="${sId}"></c:out>"/>
				    	<input type="hidden" id="departMent-name" value="<c:out value="${departMent.name}"></c:out>"/>
				    	<input type="hidden" id="departMent-hidden-hddepttype" name="departMent.depttype" value="${departMent.depttype}"/>
				    </td>
		    	</tr>
		    	<tr>
		    		<td align="right">编码:</td>
		    		<td>
		    			<p>
				    		<input type="text" id="departMent-input-id" readonly="readonly" name="departMent.id" value="<c:out value="${departMent.id }"></c:out>" class="easyui-validatebox" required="true" style="width:200px;"/>
				    	</p>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">名称:</td>
		    		<td>
		    			<input type="text" name="departMent.name" <c:if test="${editMap.name==null}">disabled="disabled"</c:if> value="<c:out value="${departMent.name }"></c:out>" class="easyui-validatebox" required="true" validType="checkSoleName" style="width:200px;"/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">部门主管:</td>
		    		<td><input id="departMent-widget-DeptUser" name="departMent.manageruserid" <c:if test="${editMap.manageruserid==null}">disabled="disabled"</c:if> value="<c:out value="${departMent.manageruserid }"></c:out>" required="true"/></td>
		    	</tr>
		    	<tr>
		    		<td align="right">电话:</td>
		    		<td><input type="text" name="departMent.tel" <c:if test="${editMap.tel==null}">disabled="disabled"</c:if> value="${departMent.tel }"  style="width:200px;"/></td>
		    	</tr>
		    	<tr>
		    		<td align="right">传真:</td>
		    		<td><input type="text" value="${departMent.fax }" <c:if test="${editMap.fax==null}">disabled="disabled"</c:if> name="departMent.fax"  style="width:200px;"/></td>
		    	</tr>
		    	<tr>
		    		<td align="right">工作日历:</td>
		    		<td><input id="departMent-widget-WorkCanlendar" type="text" name="departMent.workcalendar" <c:if test="${editMap.workcalendar==null}">disabled="disabled"</c:if> value="<c:out value="${departMent.workcalendar }"></c:out>"/></td>
		    	</tr>
		    	<tr>
		    		<td align="right">业务属性:</td>
		    		<td>
		    			<div id="dept-div-depttype" style="border:1px black solid;width: 198px;height: auto" ></div>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">关联仓库:</td>
		    		<td><input id="departMent-widget-storageid" type="text" name="departMent.storageid" <c:if test="${editMap.storageid==null}">disabled="disabled"</c:if> value="<c:out value="${departMent.storageid }"></c:out>"/></td>
		    	</tr>
		    	<tr>
		    		<td align="right">备注:</td>
		    		<td><textarea name="departMent.remark" class="easyui-validatebox" validType="maxLen[200]" <c:if test="${editMap.remark==null}">disabled="disabled"</c:if> style="height:40px;width: 195px;overflow: hidden"><c:out value="${departMent.remark }"></c:out></textarea></td>
		    	</tr>
		    	<tr>
		    		<td align="right">上级部门:</td>
		    		<td>
		    			<input id="departMent-widget-pid" type="text" name="departMent.pid" <c:if test="${editMap.pid==null}">disabled="disabled"</c:if>  <c:if test="${editFlag == false}">readonly="readonly"</c:if> value="<c:out value="${sId }"></c:out>"/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">状态:</td>
		    		<td><select id="common-combobox-state" disabled="disabled" style="width: 200px;">
                            <c:forEach items="${statelist}" var="list">
                                <option value="${list.code }" <c:if test="${departMent.state == list.code}">selected="selected"</c:if>>${list.codename }</option>
                            </c:forEach>
                        </select></td>
		    	</tr>
		    </table>
   		</form>
	<script type="text/javascript">
	    //状态
	$('#common-combobox-state').combobox({
	    url:'common/sysCodeList.do?type=state',
	    valueField:'id',
	    textField:'name'
	});
	//编辑业务属性 
	function getValByHiddenEdit(ck,val){
		var olddepttype = $("#departMent-hidden-hddepttype").val();
		if(ck.checked){
			$("#departMent-hidden-hddepttype").val(val + "," + olddepttype);
		}
		else{
			var newdepttype = olddepttype.replace(val+',',"");
			$("#departMent-hidden-hddepttype").val(newdepttype);
		}
	}
	
	//inputText级联
	function getValueEdit(value){
		var ret=department_AjaxConn({pId:$("#departMent-widget-pid").widget("getValue")},"basefiles/getNextLenght.do");
    	var jsonPid = $.parseJSON(ret);
		var len=jsonPid.nextLen
		//开始的本级编号检验 
		$.extend($.fn.validatebox.defaults.rules, {
   			validLen:{
   				validator:function(value){
   					var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
    				if(reg.test(value) == true){
    					var retStr=department_AjaxConn({thisid:value,pid:$("#departMent-widget-pid").widget("getValue")},"basefiles/isExistDepartmentId.do");
  						var jsonRet = $.parseJSON(retStr);
    					if(jsonRet.str == null){
    						$('#department-hold-holdMenu').linkbutton('enable');
    						return true;
	    				}else{
	    					if($("#departMent-hidden-hdpoldIdEdit").val() == $("#departMent-input-id").val()){
	    						$('#department-hold-holdMenu').linkbutton('enable');
	    						return true;
	    					}
	    					else{
	    						$('#department-hold-holdMenu').linkbutton('disable');
	    						$.fn.validatebox.defaults.rules.validLen.message = '本级编码已存在,不能重复!';
	    						return false;
	    					}
	    				}
    				}else{
    					$('#department-hold-holdMenu').linkbutton('enable');
    					$.fn.validatebox.defaults.rules.validLen.message ='请输入'+len+'位字符!';
    					return false;
    				}
   				},
   				message:''
   			}
   		});
	   $("#departMent-input-id").val($("#departMent-widget-pid").widget("getValue")+value);
	}
	
	//新增页面按钮状态的变化
	function changeButton(){
		//按钮状态变化 
		$("#department-add-addMenu").linkbutton('disable');
		$("#department-edit-editMenu").linkbutton('disable');
		$("#department-hold-holdMenu").linkbutton('enable');
		$("#department-save-saveMenu").linkbutton('enable');
		$("#department-delete-deleteMenu").linkbutton('enable');
		$("#department-copy-copyMenu").linkbutton('disable');
		$("#department-enable-enableMenu").linkbutton('disable');
		$("#department-disable-disableMenu").linkbutton('disable');
		$("#department-printPreView-printPreViewMenu").linkbutton('disable');
		$("#department-print-printMenu").linkbutton('disable');
	}
	
	//部门主管 
   		$("#departMent-widget-DeptUser").widget({
   			name:'t_base_department',
   			col:'manageruserid',
   			singleSelect:true,
   			onlyLeafCheck:true,
   			onLoadSuccess:function(){
	  			return true;
	  		}
   		});
   		
   		//工作日历
   		$("#departMent-widget-WorkCanlendar").widget({
   			name:'t_base_department',
   			col:'workcalendar',
   			singleSelect:true
   		});
   		
		$(function(){
			//判断是否加锁 
			if("${lockFlag}" == "0"){//加锁
  				$.messager.alert("提醒","数据已加锁!");
  				return false;
  			}
  			else{
  				changeButton();
  				
  				//部门业务属性
				var ret = department_AjaxConn({type:'depttype'},'common/sysCodeList.do');
				var retJSON = $.parseJSON(ret);
				var htmlStr = '',brStr = '';
				for(var i = 1;i<retJSON.length+1;i++){
					if(i%4 == 0){
						brStr = '<br />';
					}
					else{
						brStr = '';
					}
					htmlStr += '<input type="checkbox" style="width: auto" name="checkbox'+i+'" <c:if test="${editMap.depttype==null}">disabled="disabled"</c:if> value="'+retJSON[i-1].id+'" onclick="getValByHiddenEdit(this,'+retJSON[i-1].id+')" />'+retJSON[i-1].name+brStr+'';
				}
				$("#dept-div-depttype").html(htmlStr);
				//复选框值为1时,复选框状态为勾选 
	    		$('input:checkbox').each(function() {
		            var depttype = $("#departMent-hidden-hddepttype").val();
	   				var depttypeArr = depttype.split(",");
		   			for(var i=0;i<depttypeArr.length;i++){
		   				if(depttypeArr[i] == $(this).val()){
		   					$(this).attr('checked','true');
		   				}
		   			}
				});
				
				var ret=department_AjaxConn({pId:$("#departMent-sid").val()},"basefiles/getNextLenght.do");
		    	var jsonPid = $.parseJSON(ret);
				var len=jsonPid.nextLen
				//开始的本级编号检验 
				$.extend($.fn.validatebox.defaults.rules, {
	    			validLen:{
	    				validator:function(value){
	    					var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
		    				if(reg.test(value) == true){
		    					var retStr=department_AjaxConn({thisid:value,pid:$("#departMent-sid").val()},"basefiles/isExistDepartmentId.do");
    							var jsonRet = $.parseJSON(retStr);
		    					if(jsonRet.str == null){
		    						return true;
			    				}else{
			    					if($("#departMent-hidden-hdpoldIdEdit").val() == $("#departMent-input-id").val()){
			    						return true;
			    					}
			    					$.fn.validatebox.defaults.rules.validLen.message = '本级编码已存在,不能重复!';
			    					return false;
			    				}
		    				}else{
		    					$.fn.validatebox.defaults.rules.validLen.message ='请输入'+len+'位字符!';
		    					return false;
		    				}
	    				},
	    				message:''
	    			},
	    			checkSoleName:{
						validator:function(value,param){
							if($("#departMent-name").val() == value){
								return true;
							}
							var ret = department_AjaxConn({name:value},'basefiles/checkSoleName.do');
							var retJson = $.parseJSON(ret);
							return retJson.flag;
						},
						message:'名称重复,请修改!'
					}
	    		});
		    	$("#departMent-widget-pid").widget({
					name:'t_base_department',
					col:'pid',
					singleSelect:true,
					onlyLeafCheck:false,
	    			onSelect:function(record){
	    				$("#departMent-hidden-hdThisId").val(record.id);
                        $("#base-thisId-deptment").val(record.id);
	    				$("#departMent-input-id").val(record.id+$("#departMent-input-thisId").val());
	    				var ret=department_AjaxConn({pId:record.id},"basefiles/getNextLenght.do");
	    				var json = $.parseJSON(ret);
	    				if(json.nextLen == 0){
							$.messager.alert("提醒","已为最大级次,不能再行新增!");
							$('#department-hold-holdMenu').linkbutton('disable');
							$('#department-save-saveMenu').linkbutton('disable');
							return false;
						}
						else{
							$('#department-hold-holdMenu').linkbutton('enable');
							$('#department-save-saveMenu').linkbutton('enable');
						}
	    				//本级编号长度检验 ,选择上级部门后 
						$.extend($.fn.validatebox.defaults.rules, {
			    			validLen:{
			    				validator:function(value){
			    					var reg=eval("/^[A-Za-z0-9]{"+json.nextLen+"}$/");//正则表达式使用变量 
			    					if(reg.test(value) == true){
			    						var retStr=department_AjaxConn({thisid:value,pid:record.id},"basefiles/isExistDepartmentId.do");
	    								var jsonRet = $.parseJSON(retStr);
			    						if(jsonRet.str == null){
			    							return true;
				    					}else{
				    						if($("#departMent-hidden-hdpoldIdEdit").val() == $("#departMent-input-id").val()){
				    							return true;
				    						}
				    						$.fn.validatebox.defaults.rules.validLen.message = '本级编码已存在,不能重复!';
				    						return false;
				    					}
			    					}else{
			    						$.fn.validatebox.defaults.rules.validLen.message ='请输入'+json.nextLen+'位字符!';
			    						return false;
			    					}
			    				},
			    				message:''
			    			}
			    		});
	    			},
	    			onLoadSuccess:function(){
	    				$("#departMent-hidden-hdPid").val($("#departMent-widget-pid").widget("getValue"));
			  			return true;
			  		}
		    	});
		    	
		    	//关联仓库
				$("#departMent-widget-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
					singleSelect:true,
					width:200
				});
  			}
		});
	</script>
  </body>
</html>
