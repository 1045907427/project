<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>内部分摊申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaInnerSharePage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    <!--
    
    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    if(type == 'handle') {
    	if(step == '99'){
    		url = 'oa/oaInnerShareViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }&billtype=${param.billtype }';
		} else if(step == '01'){
    		url = 'oa/oaInnerShareHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }&billtype=${param.billtype }';
    	} else if(step == '02') {
    		url = 'oa/oaInnerShareHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }&billtype=${param.billtype }';
    	} else {
    		url = 'oa/oaInnerShareHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }&billtype=${param.billtype }';
    	}
    } else if(type == 'view') {

        url = 'oa/oaInnerShareViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }&billtype=${param.billtype }';
    } else if(type == 'print') {

        url = 'oa/oaInnerSharePrintPage.do?id=${param.id }&type=print&processid=${param.processid }&billtype=${param.billtype }';
        window.location.href = url;
    }
    
    $(function() {
    
    	$('#oa-panel-oaInnerSharePage').panel({
			href: url,
			cache: false,
			maximized: true,
			border: false,
			onLoad: function() {

				// 附件
				$attach.attach({
					<c:choose>
						<c:when test="${param.type == 'view' }">
							attach: false,
						</c:when>
						<c:otherwise>
							attach: true,
						</c:otherwise>
					</c:choose>
					businessid: '${param.id }', 
					processid: '${param.processid }'
				});

				$comments.comments({
					businesskey: '${param.id }',
					processid: '${param.processid }',
					type: 'vertical',
					width: '120',
					agree: null
				});

                /*
				$comments2.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'apply,departmentManager,financialManager,generalManager,cashier,accountant',
					agree: '1',
					width: 114
				});
				*/

				// 所属部门
				$paydeptid.widget({
					referwid: 'RL_T_BASE_DEPATMENT',
					onlyLeafCheck: false,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				// 费用科目
				$costsort.widget({
					referwid: 'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				/*
				$paybank.widget({
					referwid: 'RL_T_BASE_FINANCE_BANK',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and empty param.step }">
							readonly: false,
							required: false,
						</c:when>
						<c:when test="${param.type eq 'handle' and param.step eq '02' }">
							readonly: false,
							required: true,
						</c:when>
						<c:when test="${param.type eq 'handle' and param.step eq '99' }">
							readonly: true,
							required: false,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				*/
					
				// 所属部门
				$collectdeptid.widget({
					referwid: 'RL_T_BASE_DEPATMENT',
					onlyLeafCheck: false,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				// 大写金额
				<c:if test="${param.type eq 'handle' and param.step != '99' }">
				$amount.focusout(function() {
					setTimeout(function() {
					
						var val = $amount.numberbox('getValue');
						$upamount.val(AmountUnitCnChange(val));
					}, 100);
				});
				</c:if>
								
				// 对所有的select项目根据其data属性进行赋值
				$('select').each(function() {
				
					var data = $(this).attr('data');
					$(this).removeAttr('data');
					$(this).children().removeAttr('selected');

					$(this).children().each(function() {
					
						if($(this).attr('value') == data) {
							$(this).attr('selected', 'selected');
						}
					});
				});
				

			// panel onLoad close
			}
		});
    });
    
    function oainnershare_handle_save_form_submit(callBack, args) {
    
    	$form.form({
    		onSubmit: function(param) {
    		
    			var flag = $form.form('validate');
    			if(!flag) {
    			
    				return false;
    			}
    			
    			$('select').removeAttr('disabled');

    			loading("提交中...");
    		},
    		success: function(data) {

    			loaded();
    			var json;
				try{
					json = $.parseJSON(data);
				}catch(e){
					$.messager.alert("提醒","保存失败！");
    				return false;
				}
    			
    			// 保存失败
    			if(data.flag) {
    				$.messager.alert("提醒","保存失败！");
    				return false;
    			}
    			
    			// 保存成功
				$.messager.alert("提醒","保存成功。");
				if(callBack.data != undefined && callBack.data != null) {
				
					callBack.data(json.backid);
					return false;
				}
				 
    		}
    	});
    	
    	$form.submit();
    }

	// 提交表单(工作页面提交表单接口方法)
	function workFormSubmit(call, args) {

        oainnershare_handle_save_form_submit(call, args);
	}

	-->
	</script>
  </body>
</html>