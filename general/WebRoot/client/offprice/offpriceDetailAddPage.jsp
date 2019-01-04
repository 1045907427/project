<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>预算分析页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	  <div data-options="region:'center',border:false">
		  <form id="client-form-detailAdd" method="post">
			  <table style="border-collapse: collapse;" border="0" cellpadding="5" cellspacing="5">
				  <tr>
					  <td>部门名称:</td>
					  <td>
						  <input type="text" id="client-detailAdd-deptid" name="clientOffprice.deptid" required="true" style="width:150px;" value="${deptid}" readonly="readonly"/>
					  </td>
					  <td>商品名称:</td>
					  <td>
						  <input type="text" id="client-detailAdd-goodsname" name='clientOffprice.goodsid' required="true" style="width:150px;" />
					  </td>
				  </tr>
				  <tr>
					  <td>起始日期:</td>
					  <td>
						  <input type="text" id="client-begindate-offpriceDetailAddPage" name="clientOffprice.begindate" required="true" class="easyui-validatebox Wdate" style="width:150px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  />
					  </td>
					  <td>终止日期:</td>
					  <td>
						  <input type="text" id="client-enddate-offpriceDetailAddPage" name="clientOffprice.enddate" required="true" class="easyui-validatebox Wdate" style="width:150px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
					  </td>
				  </tr>
				  <tr>
					  <td>起始时间:</td>
					  <td><input type="text" id="client-begintime-offpriceDetailAddPage" name="clientOffprice.begintime" required = "true" class="easyui-validatebox Wdate" style="width:150px;" onfocus="WdatePicker({'dateFmt':'HH:mm'})" />
					  </td>
					  <td>终止时间:</td>
					  <td><input type="text" id="client-endtime-offpriceDetailAddPage" name="clientOffprice.endtime" required = "true" class="easyui-validatebox Wdate" style="width:150px;" onfocus="WdatePicker({'dateFmt':'HH:mm'})" /></td>
				  </tr>
				  <tr>
					  <td>基准销售价:</td>
					  <td>
						  <input type="text" id="client-basesaleprice-offpriceDetailAddPage" name="clientOffprice.basesaleprice" class="easyui-numberbox" data-options="min:0,precision:2" validType="intOrFloat" style="width:150px;" readonly="readonly"/>
					  </td>
					  <td>零售价格:</td>
					  <td>
						  <input type="text" id="client-retailprice-offpriceDetailAddPage" name="clientOffprice.retailprice" class="easyui-numberbox" data-options="min:0,precision:2" required="required" validType="intOrFloat" style="width:150px;" />
					  </td>
				  </tr>
			  </table>
		  </form>
	  </div>
	  <div data-options="region:'south',border:false" style="height: 30px;">
		  <div class="buttonDetailBG" style="text-align: right;">
			  <input type="button" value="保存" name="savenogo" id="client-detailAdd-saveData" />
			  <input type="button" value="关闭" name="close" id="client-detailAdd-closeDlg" />
		  </div>
	  </div>
  </div>

  <script type="text/javascript">
	  $(function(){

		  $("#client-detailAdd-closeDlg").click(function(){
			  parent.$.dialog.dialog('close')
		  });

		  //部门名称
		  $("#client-detailAdd-deptid").widget({
			  referwid :'RL_T_BASE_DEPATMENT',
			  singleSelect:true,
			  onlyLeafCheck:true,
			  width:150,
			  required:true,
			  onSelect:function(data){
			  }
		  });

		  $("#client-detailAdd-goodsname").widget({
			  referwid :'RL_T_BASE_GOODS_INFO',
			  width:150,
			  singleSelect:true,
			  onlyLeafCheck:true,
			  required:true,
			  onSelect : function(data){
				  $('#client-basesaleprice-offpriceDetailAddPage').numberbox('setValue', data.basesaleprice);
			  }
		  });

		  //保存
		  $("#client-detailAdd-saveData").click(function(){

			  $('#client-enddate-offpriceDetailAddPage').validatebox({
				  validType: 'dateBiggerOrEqual["' + $('#client-begindate-offpriceDetailAddPage').val() + '"]'
			  });
			  $('#client-endtime-offpriceDetailAddPage').validatebox({
				  validType: 'timeBiggerOrEqual["' + $('#client-begintime-offpriceDetailAddPage').val() + '"]'
			  });

			  if($("#client-form-detailAdd").form('validate')){
				  loading();
				  $.ajax({
					  url:"client/offprice/addOffPrice.do",
					  data:$("#client-form-detailAdd").serialize(),
					  type:'post',
					  dataType:'json',
					  success:function(r){
						  loaded();
						  if(r.flag){
							  $.messager.alert("提醒", r.msg);
							  parent.$.dialog.dialog('close');
							  parent.$.dg.datagrid('reload');
						  }else{
							  $.messager.alert("提醒", r.msg);
						  }
					  }
				  })
			  }
		  })

	  })
  </script>
   
  </body>
</html>
