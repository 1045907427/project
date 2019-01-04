<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门信息</title>
  </head>
  
  <body>
    <input type="hidden" id="department-hidden-state" value="${departMent.state}"/>
    <form action="" id="department-form-departmentInfo" method="post">
   			<table class="pageContent" cellpadding="5" cellspacing="0" border="0" align="center">
		    	<tr>
		    		<td align="right" style="width: 120px;">本级编码:</td>
		    		<td><input type="text" name="departMent.thisid" style="width:200px;"
		    			<c:choose>
							<c:when test="${showMap.thisid==null}">disabled="disabled" value=""</c:when>
							<c:otherwise>readonly="readonly" value="<c:out value="${departMent.thisid }"></c:out>" </c:otherwise>
						</c:choose>/>
				    <input type="hidden" id="departMent-hidden-hdType" value="show"/>
				     <input type="hidden" id="departMent-hidden-hddepttype" name="departMent.depttype" value="${departMent.depttype}"/>
				    </td>
		    	</tr>
		    	<tr>
		    		<td align="right">编码:</td>
		    		<td>
		    			<p>
				    		<input type="text" id="departMent-input-id" name="departMent.id" style="width:200px;"
				    		<c:choose>
								<c:when test="${showMap.id==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="<c:out value="${departMent.id }"></c:out>" </c:otherwise>
							</c:choose>/>
				    	</p>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">名称:</td>
		    		<td>
		    			<input type="text" name="departMent.name" style="width:200px;"
		    			<c:choose>
							<c:when test="${showMap.name==null}">disabled="disabled" value=""</c:when>
							<c:otherwise>readonly="readonly" value="<c:out value="${departMent.name }"></c:out>" </c:otherwise>
						</c:choose>/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">部门主管:</td>
		    		<td><input id="departMent-widget-DeptUser" disabled="disabled" name="departMent.manageruserid"
		    			<c:choose>
							<c:when test="${showMap.manageruserid==null}">value=""</c:when>
							<c:otherwise>value="<c:out value="${departMent.manageruserid }"></c:out>" </c:otherwise>
						</c:choose>/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">电话:</td>
		    		<td><input type="text" name="departMent.tel" style="width:200px;"
		    			<c:choose>
							<c:when test="${showMap.tel==null}">disabled="disabled" value=""</c:when>
							<c:otherwise>readonly="readonly" value="${departMent.tel }" </c:otherwise>
						</c:choose>/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">传真:</td>
		    		<td><input type="text" name="departMent.fax" style="width:200px;"
		    			<c:choose>
							<c:when test="${showMap.fax==null}">disabled="disabled" value=""</c:when>
							<c:otherwise>readonly="readonly" value="${departMent.fax }" </c:otherwise>
						</c:choose>/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">工作日历:</td>
		    		<td><input id="departMent-widget-WorkCanlendar" disabled="disabled" type="text" name="departMent.workcalendar"
		    			<c:choose>
							<c:when test="${showMap.workcalendar==null}">value=""</c:when>
							<c:otherwise>value="<c:out value="${departMent.workcalendar }"></c:out>" </c:otherwise>
						</c:choose>/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">业务属性:</td>
		    		<td>
		    			<div id="dept-div-depttype" style="border:1px black solid;width: 198px;height: auto" ></div>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">关联仓库:</td>
		    		<td><input id="departMent-widget-storageid" disabled="disabled" type="text" name="departMent.storageid"
		    			<c:choose>
							<c:when test="${showMap.storageid==null}">value=""</c:when>
							<c:otherwise>value="<c:out value="${departMent.storageid }"></c:out>" </c:otherwise>
						</c:choose>/>
					</td>
		    	</tr>
		    	<tr>
		    		<td align="right">备注:</td>
		    		<td><textarea name="departMent.remark" style="height:40px;width: 195px;overflow: hidden" readonly="readonly"><c:if test="${showMap.remark != null}"></c:if><c:out value="${departMent.remark}"></c:out></textarea></td>
		    	</tr>
		    	<tr>
		    		<td align="right">上级部门:</td>
		    		<td><input id="departMent-widget-pid" type="text" name="departMent.pid" disabled="disabled"
		    			<c:choose>
							<c:when test="${showMap.pid==null}">value=""</c:when>
							<c:otherwise>value="<c:out value="${sId }"></c:out>" </c:otherwise>
						</c:choose>/>
					</td>
		    	</tr>
		    	<tr>
		    		<td align="right">状态:</td>
		    		<td><select id="common-combobox-state" disabled="disabled" style="width: 200px;">
                        <c:forEach items="${statelist}" var="list">
                            <option value="${list.code }" <c:if test="${departMent.state == list.code}">selected="selected"</c:if>>${list.codename }</option>
                        </c:forEach>
                    </select>
				    </td>
		    	</tr>
		    </table>
   		</form>
	<script type="text/javascript">
		//新增页面按钮状态的变化
		function changeButton(){
			//按钮状态变化 
			$("#department-add-addMenu").linkbutton('enable');
			$("#department-edit-editMenu").linkbutton('enable');
			$("#department-hold-holdMenu").linkbutton('disable');
			$("#department-save-saveMenu").linkbutton('disable');
			$("#department-delete-deleteMenu").linkbutton('enable');
			$("#department-copy-copyMenu").linkbutton('enable');
			$("#department-enable-enableMenu").linkbutton('enable');
			$("#department-disable-disableMenu").linkbutton('enable');
			$("#department-printPreView-printPreViewMenu").linkbutton('enable');
			$("#department-print-printMenu").linkbutton('enable');
		}
		
	//上级部门
  	$("#departMent-widget-pid").widget({
 		name:'t_base_department',
 		col:'pid',
 		singleSelect:true,
 		onlyLeafCheck:false
  	});
	//部门主管 
	$("#departMent-widget-DeptUser").widget({
		name:'t_base_department',
		col:'manageruserid',
		singleSelect:true,
		onlyLeafCheck:true
	});
	
	//工作日历
	$("#departMent-widget-WorkCanlendar").widget({
		name:'t_base_department',
		col:'workcalendar',
		singleSelect:true
	});
   	
   	//关联仓库
	$("#departMent-widget-storageid").widget({
		referwid:'RL_T_BASE_STORAGE_INFO',
		singleSelect:true,
		width:200
	});
	$(function(){
		changeButton();

        if("${departMent.state}" != "1"){
            $("#department-add-addMenu").linkbutton('disable');
        }else{
            $("#department-add-addMenu").linkbutton('enable');
        }

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
			htmlStr += '<input type="checkbox" style="width: auto" name="checkbox'+i+'" value="'+retJSON[i-1].id+'" disabled="disabled"/>'+retJSON[i-1].name+brStr+'';
		}
		$("#dept-div-depttype").html(htmlStr);
		
		//复选框值为1时,复选框状态为勾选 
   		$('input:checkbox').each(function() {
   			if("${showMap.depttype}" != null && "${showMap.depttype}" != ""){
   				var depttype = $("#departMent-hidden-hddepttype").val();
   				var depttypeArr = depttype.split(",");
	   			for(var i=0;i<depttypeArr.length;i++){
	   				if(depttypeArr[i] == $(this).val()){
	   					$(this).attr('checked','true');
	   				}
	   			}
   			}
   			else{
   				return false;
   			}
		});
	});
	</script>
  </body>
</html>
