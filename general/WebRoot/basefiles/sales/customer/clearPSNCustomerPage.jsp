<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>清除人员对应客户页面</title>
	  <style>
		  .checkbox1{
			  float:left;
			  height: 22px;
			  line-height: 22px;
		  }
		  .divtext{
			  height:22px;
			  line-height:22px;
			  float:left;
			  display: block;
		  }
	  </style>
  </head>
  
  <body>
  	<form action="basefiles/doClearCustomerToPsn.do" method="post" id="sales-customer-clearCustomerToPsn">
		<input type="hidden" name="customerids" value="${customerids }"/>
		<div align="center">
			<table>
				<tr>
					<td colspan="2">
						<label class="divtext"><input type="radio" name="employetype" class="groupcols checkbox1" value="3" checked="checked"/>品牌业务员</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label class="divtext"><input type="radio" name="employetype" class="groupcols checkbox1" value="7"/>厂家业务员</label>
					</td>
				</tr>
			</table>
		</div>
	</form>
    <script type="text/javascript">

    	function claerCustomerToPsn_form_submit(){
    		$("#sales-customer-clearCustomerToPsn").form({
			    onSubmit: function(){  
		  		  	loading("清除中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
                    if(json.flag){
						$.messager.alert("提醒","清除成功!");
                        $('#sales-dialog-clearPSNCustomer').dialog('close');
                    }
		  		}
		  	});
    	}
    </script>
  </body>
</html>
