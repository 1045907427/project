<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分配人员对应客户页面</title>
  </head>
  
  <body>
  	<form action="oa/allotCustomerToPsn.do" method="post" id="oa-customer-oaAllotPSNCustomerPage">
		<input type="hidden" id="oa-billid-oaAllotPSNCustomerPage" name="billid" value="${id }"/>
		<input type="hidden" id="oa-personids-oaAllotPSNCustomerPage" name="personids"/>
		<input type="hidden" id="oa-delpersonids-oaAllotPSNCustomerPage" name="delpersonids"/>
		<input type="hidden" id="oa-company-oaAllotPSNCustomerPage" name="company"/>
		<c:forEach items="${paramlist }" var="paramlist">
			<input id="oa-hdperson-${paramlist.deptid }" class="personid allot-personid" type="hidden"/>
			<input id="oa-hddelperson-${paramlist.deptid }" class="delpersonid" type="hidden"/>
			<input id="oa-hdcompany-${paramlist.deptid }" class="company allot-company" type="hidden" value="${paramlist.deptid }"/>
		</c:forEach>
	</form>
	<table cellpadding="2" cellspacing="0" border="0">
		<c:forEach items="${paramlist }" var="paramlist">
			<tr>
				<td class="len150">所属<span style="color: red">${paramlist.deptname }</span>品牌业务员:</td>
				<td><input id="oa-oaAllotPSNCustomerPage-${paramlist.deptid }" type="text" style="width: 200px;"/></td>
			</tr>
		</c:forEach>
	</table>
    <script type="text/javascript">
    	$(function(){
    		<c:forEach items="${paramlist }" var="paramlist">
				$('#oa-oaAllotPSNCustomerPage-${paramlist.deptid }').widget({
					width:200,
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
					param:[{field:'deptid',op:'equal',value:'${paramlist.deptid }'}],
					singleSelect:true,
					<c:if test="${type != 'temp'}">
						initValue:'${paramlist.personid}',
					</c:if>
					<c:if test="${type == 'temp'}">
						initValue:'${paramlist.personid}',
					</c:if>
					onSelect:function(data){
						$("#oa-hdperson-${paramlist.deptid}").val(data.id);
						$("#oa-hddelperson-${paramlist.deptid }").val("");
						$("#oa-hdcompany-${paramlist.deptid }").val("${paramlist.deptid }");
					},
					onClear:function(){
						//$("#oa-hddelperson-${paramlist.deptid }").val($("#oa-hdperson-${paramlist.deptid}").val());
						$("#oa-hdperson-${paramlist.deptid}").val("");
						$("#oa-hdcompany-${paramlist.deptid }").val("");
					},
					onLoadSuccess:function(data){
						$("#oa-hdperson-${paramlist.deptid}").val($(this).widget('getValue'));
					}
				});
			</c:forEach>
    	});
    	
    	function tempSave() {
    	
    		var pa = new Array();
    		var da = new Array();
    		
    		$('.allot-personid').each(function(){
    		
    			pa.push($(this).val());
    		});
    		
    		$('.allot-company').each(function(){
    		
    			da.push($(this).val());
    		});
    		
    		var pastr = pa.join(',');
    		var dastr = da.join(',');
    		
    		$('#oa-allotpersonids-oacustomerHandlePage').val(pastr);
    		$('#oa-allotcompanies-oacustomerHandlePage').val(dastr);
    	}
    	
    	function getPersonidsValue(){
    		var perids = "";
			$(".personid").each(function(e){
				if($(this).val() != ""){
					if(perids == ""){
						perids = $(this).val();
					}else{
						perids += "," + $(this).val();
					}
				}
			 });
			 $("#oa-personids-oaAllotPSNCustomerPage").val(perids);
			 return perids;
    	}
    	
    	function getDelPersonidsValue(){
    		var delperids = ""
    		 $(".delpersonid").each(function(e){
				if($(this).val() != ""){
					if(delperids == ""){
						delperids = $(this).val();
					}else{
						delperids += "," + $(this).val();
					}
				}
			 });
			 $("#oa-delpersonids-oaAllotPSNCustomerPage").val(delperids);
			 return delperids;
    	}
    	
    	function getCompanyValue(){
    		var company = ""
    		 $(".company").each(function(e){
				if($(this).val() != ""){
					if(company == ""){
						company = $(this).val();
					}else{
						company += "," + $(this).val();
					}
				}
			 });
			 $("#oa-company-oaAllotPSNCustomerPage").val(company);
    	}
    	
    	function allotCustomerToPsn_form_submit(){
    		$("#oa-customer-oaAllotPSNCustomerPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    $.messager.alert("提醒", '分配成功。');
		  		    $('#oa-dialog-oacustomerPage').dialog('close');
		  		}
		  	});
    	}
    </script>
  </body>
</html>
