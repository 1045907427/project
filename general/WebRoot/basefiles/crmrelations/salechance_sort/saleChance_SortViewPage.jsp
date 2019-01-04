<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售机会来源详情页面</title>
  </head>
  
  <body>
  	<form action="" id="saleChance-form-view" method="post">
  		<input type="hidden" id="saleChance_Sort-oldid" value="<c:out value="${saleChance_Sort.id }"></c:out>"/>
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" value="<c:out value="${saleChance_Sort.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" value="<c:out value="${saleChance_Sort.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" value="<c:out value="${saleChance_Sort.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" value="<c:out value="${saleChance_Sort.name}"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="crmrelations-parent-saleChance" disabled="disabled" value="<c:out value="${saleChance_Sort.pid }"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="saleChance_Sort.remark" readonly="readonly"><c:out value="${saleChance_Sort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${saleChance_Sort.state }" disabled="disabled" class="easyui-combobox" style="width: 206px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//状态
		$('#common-combobox-state').combobox({
		    url:'common/sysCodeList.do?type=state',   
		    valueField:'id',   
		    textField:'name'  
		});
		$("#crmrelations-parent-saleChance").widget({
   			name:'t_base_crm_salechance_sort',
			col:'pid',
   			singleSelect:true,
   			width:200,
   			onlyLeafCheck:true,
   			view:true
   		});
    	$(function(){
			$("#crmrelations-buttons-salesChanceSort").buttonWidget("setDataID", {id:$("#saleChance_Sort-oldid").val(), state:'${saleChance_Sort.state}', type:'view'});
			if('${saleChance_Sort.state}' != '1'){
				$("#crmrelations-buttons-salesChanceSort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
