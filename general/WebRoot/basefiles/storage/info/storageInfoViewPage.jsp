<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出入库类型添加页面</title>
  </head>
  
  <body>
  	<div style="padding-left: 5px; width: 780px;" >
  		<input type="hidden" id="storageInfo-oldid" value="<c:out value="${storageInfo.id }"></c:out>"/>
		<table cellspacing="5px" cellpadding="5px" border="0" width="730px">
		  <tr>
		      <td width="120px"><div align="right">编码:</div></td>
		      <td style="width: 200px;"><input name="storageInfo.id" id="storageInfo-id" type="text" style="width:180px;" value="<c:out value="${storageInfo.id }"></c:out>" maxlength="20" readonly="readonly"></td>
			  <td width="120px"><div align="right">名称:</div></td>
		      <td style="width: 200px;"><input name="storageInfo.name" type="text" style="width:120px;" value="<c:out value="${storageInfo.name }"></c:out>" maxlength="50" readonly="readonly"></td>
		      <td width="120px"><div align="right">状态:</div></td>
		      <td>
			    <select name="storageInfo.state" style="width:180px;" disabled="disabled">
			      	<option value="4" <c:if test="${storageInfo.state=='4'}">selected="selected"</c:if>>新增</option>
					<option value="3" <c:if test="${storageInfo.state=='3'}">selected="selected"</c:if>>暂存</option>
					<option value="2" <c:if test="${storageInfo.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="1" <c:if test="${storageInfo.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${storageInfo.state=='0'}">selected="selected"</c:if>>禁用</option>
			    </select>
		      </td>
		  </tr>
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666; ">
		  <tr>
		      <td width="120px"><div align="right">仓库类型:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<input id="storageInfo-storagetype" type="text" name="storageInfo.storagetype" value="${storageInfo.storagetype }" disabled="disabled"/></td>
			  <td width="120px"><div align="right" class="carsaleuser" <c:if test="${storageInfo.storagetype!='20' }">style="display: none;"</c:if>>车销人员:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<div class="carsaleuser" <c:if test="${storageInfo.storagetype!='20' }">style="display: none;"</c:if>>
		      	<input id="storageInfo-carsaleuser" disabled="disabled" type="text" name="storageInfo.carsaleuser" required="required" value="${storageInfo.carsaleuser }"/>
		     	</div>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">金额管理方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-moneytype" type="text" name="storageInfo.moenytype" value="${storageInfo.moenytype}" disabled="disabled"/>
		      </td>
			  <td><div align="right">计价方式:</div></td>
			  <td style="width: 5px"></td>
		      <td>
		      	<input id="storageInfo-pricetype" type="text" name="storageInfo.pricetype" value="${storageInfo.pricetype}" disabled="disabled"/>
		      </td>
		  </tr>
		  <tr>
		      <td><div align="right">是否批次管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isbatch" style="width:180px;" disabled="disabled">
			        <option <c:if test="${storageInfo.isbatch=='1'}">selected="selected"</c:if>>是</option>
			        <option <c:if test="${storageInfo.isbatch=='0'}">selected="selected"</c:if>>否</option>
			      </select>
		      </td>
			  <td><div align="right">是否库位管理:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.isstoragelocation" style="width:180px;" disabled="disabled">
			        <option <c:if test="${storageInfo.isstoragelocation=='1'}">selected="selected"</c:if>>是</option>
			        <option <c:if test="${storageInfo.isstoragelocation=='0'}">selected="selected"</c:if>>否</option>
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
                    <select id="storageInfo-isaloneaccount" disabled="disabled"style="width:180px;">
                        <option value="1" <c:if test="${storageInfo.isaloneaccount=='1'}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${storageInfo.isaloneaccount=='0'}">selected="selected"</c:if>>否</option>
                    </select>
                </td>
            </tr>
		  <tr>
		      <td width="120px"><div align="right">是否发货仓库:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select name="storageInfo.issendstorage" style="width:180px;" disabled="disabled">
			    	<option value="1" <c:if test="${storageInfo.issendstorage=='1'}">selected="selected"</c:if>>是</option>
		        	<option value="0" <c:if test="${storageInfo.issendstorage=='0'}">selected="selected"</c:if>>否</option>
				</select>
		      </td>
			  <td width="120px"><div align="right">参与总量控制:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select name="storageInfo.istotalcontrol" style="width:180px;" disabled="disabled">
			    	<option <c:if test="${storageInfo.istotalcontrol=='1'}">selected="selected"</c:if>>是</option>
			        <option <c:if test="${storageInfo.istotalcontrol=='0'}">selected="selected"</c:if>>否</option>
				</select>
		      </td>
		  <tr>
		  	  <td width="120px"><div align="right">允许负库存:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px">
		      	<select name="storageInfo.islosestorage" style="width:180px;" disabled="disabled">
			    	<option <c:if test="${storageInfo.islosestorage=='1'}">selected="selected"</c:if>>是</option>
			        <option <c:if test="${storageInfo.islosestorage=='0'}">selected="selected"</c:if>>否</option>
				</select>
		      </td>
		      <td><div align="right">允许超可用量发货:</div></td>
			  <td style="width: 5px"></td>
		      <td>
			      <select name="storageInfo.issendusable" style="width:180px;" disabled="disabled">
			        <option <c:if test="${storageInfo.issendusable=='1'}">selected="selected"</c:if>>是</option>
			        <option <c:if test="${storageInfo.issendusable=='0'}">selected="selected"</c:if>>否</option>
			      </select>
		      </td>
		  </tr>
<!-- 		  <tr> -->
<!-- 		  	  <td width="120px"><div align="right">是否允许配货:</div></td> -->
<!-- 			  <td style="width: 5px"></td> -->
<!-- 		      <td width="240px"> -->
<!-- 		      	<select name="storageInfo.isconfig" style="width:180px;" disabled="disabled"> -->
<%-- 			    	<option value="1" <c:if test="${storageInfo.isconfig=='1'}">selected="selected"</c:if>>是</option> --%>
<%-- 		        	<option value="0" <c:if test="${storageInfo.isconfig=='0'}">selected="selected"</c:if>>否</option> --%>
<!-- 				</select> -->
<!-- 		      </td> -->
<!-- 		      <td colspan="3">允许配货，销售订单未指定仓库时，配置库存时，可以配置到该仓库中。不允许配货，只能通过指定仓库发货。</td> -->
<!-- 		  </tr> -->
		</table>
		<br />
		<table cellspacing="5px" cellpadding="5px" style="border:1px solid #666;float:right"  >
		  <tr>
		      <td width="120px"><div align="right">仓库负责人:</div></td>
			  <td style="width:5px"></td>
		      <td width="240px"><input id="storageInfo-manageruserid" name="storageInfo.manageruserid" style="width:210px;" value="<c:out value="${storageInfo.manageruserid }"></c:out>" disabled="disabled"></td>
			  <td width="120px"><div align="right">电话:</div></td>
			  <td style="width: 5px"></td>
		      <td width="240px"><input id="storageInfo-telphone" name="storageInfo.telphone" type="text" style="width:190px;"  value="<c:out value="${storageInfo.telphone}"></c:out>" readonly="readonly"></td>
		  </tr>
		  <tr>
		      <td width="120px"><div align="right">地址:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="5">
		      	<input type="text" name="storageInfo.addr" style="width:590px;" value="<c:out value="${storageInfo.addr }"></c:out>" readonly="readonly"/>
		      </td>
		  </tr>
		  <tr>
		      <td width="120px"><div align="right">备注:</div></td>
			  <td style="width: 5px"></td>
		      <td colspan="5"><textarea name="storageInfo.remark"  rows="3" style="width:585px;" readonly="readonly"><c:out value="${storageInfo.remark }"></c:out></textarea></td>
		  </tr>
		</table>
		</div>
		<script type="text/javascript">
			$(function(){
				//控制按钮状态
				$("#storageInfo-button").buttonWidget("setDataID",{id:$("#storageInfo-oldid").val(),state:'${storageInfo.state}',type:'view'});
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
	    			view:true
	    		});
	    		$("#storageInfo-pricetype").widget({
	    			name:'t_base_storage_info',
	    			col:'pricetype',
	    			singleSelect:true,
	    			width:180,
	    			view:true
	    		});
	    		$("#storageInfo-manageruserid").widget({
	    			name:'t_base_storage_info',
	    			col:'manageruserid',
	    			singleSelect:true,
	    			width:180,
	    			view:true
	    		});
                $("#storageInfo-storagetype").widget({
                    referwid: 'RL_T_BASE_STORAGE_TYPE',
                    required: true,
                    width: 180
                });;
			});
		</script>
  </body>
</html>
