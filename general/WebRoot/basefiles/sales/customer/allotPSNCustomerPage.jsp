<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分配人员对应客户页面</title>
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
  	<form action="basefiles/allotCustomerToPsn.do" method="post" id="sales-customer-allotCustomerToPsn">
		<input type="hidden" id="sales-customerids-allotCustomerToPsn" name="customerids" value="${customerids }"/>
		<input type="hidden" id="sales-personids-allotCustomerToPsn" name="personids"/>
		<input type="hidden" id="sales-delpersonids-allotCustomerToPsn" name="delpersonids"/>
		<input type="hidden" id="sales-company-allotCustomerToPsn" name="company"/>
		<input type="hidden" id="sales-initpersonjsonstr-allotCustomerToPsn" name="initpersonjsonstr"/>
		<input type="hidden" id="sales-employetype-allotCustomerToPsn" name="employetype" value="${employetype }"/>
		<c:forEach items="${paramlist }" var="paramlist">
			<input id="sales-hddeptid-${paramlist.deptid}" class="deptids" type="hidden" value="${paramlist.deptid}"/>
			<input id="sales-hdinitperson-${paramlist.deptid}" type="hidden" value="${paramlist.personid}"/>
			<input id="sales-hdperson-${paramlist.deptid }" class="personid" type="hidden"/>
			<input id="sales-hddelperson-${paramlist.deptid }" class="delpersonid" type="hidden"/>
			<input id="sales-hdcompany-${paramlist.deptid }" class="company" type="hidden" <c:if test="${type == '1'}">value="${paramlist.deptid }"</c:if>/>
		</c:forEach>
	</form>
	<div align="center">
		<table>
			<tr>
				<td colspan="2">
					<label class="divtext"><input type="radio" class="groupcols checkbox1 employetype" value="3" checked="checked"/>品牌业务员</label>
				</td>
				<td colspan="2">
					<label class="divtext"><input type="radio" class="groupcols checkbox1 employetype" value="7"/>厂家业务员</label>
				</td>
			</tr>
		</table>
		<hr />
		<table cellpadding="2" cellspacing="0" border="0">
			<c:forEach items="${paramlist }" var="paramlist">
				<tr>
					<td>所属<span style="color: red">${paramlist.deptname }</span><c:choose><c:when test="${'3' == employetype}">品牌业务员:</c:when><c:when test="${'7' == employetype}">厂家业务员:</c:when></c:choose></td>
					<td><input id="sales-allotCustomerToPsn-${paramlist.deptid }" type="text" style="width: 200px;"/></td>
				</tr>
			</c:forEach>
		</table>
	</div>
    <script type="text/javascript">
    	$(function(){
    		<c:forEach items="${paramlist }" var="paramlist">
				$('#sales-allotCustomerToPsn-${paramlist.deptid }').widget({
					width:200,
					<c:choose>
						<c:when test="${employetype == '3'}">
							<c:choose>
								<c:when test="${paramlist.deptid != ''}">
									referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
								</c:when>
								<c:otherwise>
									referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER_NODEPT',
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${employetype == '7'}">
					        <c:choose>
					            <c:when test="${paramlist.deptid != ''}">
								  referwid:'RL_T_BASE_PERSONNEL_SUPPLIER',
							    </c:when>
							    <c:otherwise>
								  referwid:'RL_T_BASE_PERSONNEL_SUPPLIER_NODEPT',
							    </c:otherwise>
					        </c:choose>
						</c:when>
					</c:choose>
					param:[{field:'deptid',op:'equal',value:'${paramlist.deptid }'}],
					singleSelect:true,
					<c:if test="${type == '1'}">initValue:'${paramlist.personid}',</c:if>
					onSelect:function(data){
						$("#sales-hdperson-${paramlist.deptid}").val(data.id);
						$("#sales-hddelperson-${paramlist.deptid }").val("");
						$("#sales-hdcompany-${paramlist.deptid }").val("${paramlist.deptid }");
					},
					onClear:function(){
						<!--$("#sales-hddelperson-${paramlist.deptid }").val($("#sales-hdperson-${paramlist.deptid}").val());-->
						$("#sales-hdperson-${paramlist.deptid}").val("");
						$("#sales-hdcompany-${paramlist.deptid }").val("");
					},
					onLoadSuccess:function(data){
						$("#sales-hdperson-${paramlist.deptid}").val($(this).widget('getValue'));
					}
				});
			</c:forEach>

			$(".employetype").each(function(e){
				$(this)[0].checked = false;
				if("${employetype}" == $(this).val()){
					$(this)[0].checked = true;
				}
			});

			//业务属性选择
	    	$(".employetype").click(function(){
	    		var idStr = $("#sales-customerids-allotPSNCustomer").val();
	    		$('#sales-dialog-allotPSNCustomer').dialog('refresh', 'basefiles/showAllotPSNCustomerPage.do?idStr='+idStr+'&employetype='+$(this).val());
	    	});
    	});
    	
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
			 $("#sales-personids-allotCustomerToPsn").val(perids);
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
			 $("#sales-delpersonids-allotCustomerToPsn").val(delperids);
			 return delperids;
    	}
    	
    	//获取初始业务员对应分配后的业务员
    	function getPersonidKeyInitPersonidVal(){
    		var initPersonJson = {};
    		var initPersonJsonStr = "";
    		var deptids = "";
    		$(".deptids").each(function(e){
				if($(this).val() != ""){
					if(deptids == ""){
						deptids = $(this).val();
					}else{
						deptids += "," + $(this).val();
					}
				}
			});
			if(deptids != ""){
				var deptarr = deptids.split(",");
				for(var i=0;i<deptarr.length;i++){
					var initpersonid = $("#sales-hdinitperson-"+deptarr[i]).val();
					var personid = $('#sales-allotCustomerToPsn-'+deptarr[i]).widget('getValue');
					if(initpersonid != "" && null != initpersonid){
						initPersonJson[personid] = initpersonid;
					}
				}
			}
			if(!jQuery.isEmptyObject(initPersonJson)){
				initPersonJsonStr = JSON.stringify(initPersonJson);
			}
			$("#sales-initpersonjsonstr-allotCustomerToPsn").val(initPersonJsonStr);
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
			 $("#sales-company-allotCustomerToPsn").val(company);
    	}
    	
    	function allotCustomerToPsn_form_submit(){
    		$("#sales-customer-allotCustomerToPsn").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
                    if(null != json.msg && "" != json.msg){
                        $.messager.alert("提醒",json.msg);
                    }
                    if(null == json.flag || json.flag){
                        $('#sales-dialog-allotPSNCustomer').dialog('close');
                    }
		  		}
		  	});
    	}
    </script>
  </body>
</html>
