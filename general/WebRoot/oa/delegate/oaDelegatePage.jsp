<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作委托规则页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <style type="text/css">
        .len200 {
            width: 200px;
        }
        .len230 {
            width: 230px;
        }
        .title {
            background: #eee;
        }
        .content-table {
            border-collapse: collapse;
            width: 700px;
        }
        .content-table td {
            padding: 7px;
            border: 1px solid #aaa;
        }
    </style>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaDelegatePage">
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
    var billtype = '${param.billtype }';

    if(type == 'handle') {
    	
    	url = 'oa/delegate/oaDelegateHandlePage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';

		if(step == '02') {
		    url = 'oa/delegate/oaDelegateHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
        } else if(step == '99'){
       		url = 'oa/delegate/oaDelegateViewPage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
		}
    	
    } else if(type == 'view') {

        url = 'oa/delegate/oaDelegateViewPage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/delegate/oaDelegatePrintPage.do?id=${param.id }&type=handle&processid=${param.processid }';
        window.location.href = url;
    }
    
    $(function() {
    
    	$('#oa-panel-oaDelegatePage').panel({
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

				// 审批信息
                $comments.comments({
					businesskey: '${param.id }',
					processid: '${param.processid }',
					type: 'vertical',
					width: '120',
					agree: null
				});

                // 流程
                $definitionkey2.widget({
                    referwid: 'RT_T_ACT_DEFINATION',
                    onlyLeafCheck: true,
                    <c:if test="${param.type eq 'handle' ne param.step ne '99'}">
                        required: true && !$all.is(':checked'),
                    </c:if>
                    width: 200,
                    onChecked: function(data) {
                        $definitionkey.val(data.id);
                    }
                });

                // 委托人
                $userid.widget({
                    referwid: 'RT_T_SYS_USER',
                    width: 200
                });

                // 被委托人
                $delegateuserid.widget({
                    referwid: 'RT_T_SYS_USER',
                    width: 200
                });

                <c:if test="${param.type eq 'handle' and param.step ne '99'}">
                    // 所有流程
                    $all.off().click(checkAllDefinition);

                    $remain.off().click(function (e) {

                        if($(this).is(':checked')) {

                            $begindate.attr('disabled', 'disabled');
                            $enddate.attr('disabled', 'disabled');
                            $begindate.val('');
                            $enddate.val('');

                            $begindate.validatebox({required: false});
                            $begindate.validatebox('validate');
                            $enddate.validatebox({required: false});
                            $enddate.validatebox('validate');

                        } else {

                            $begindate.removeAttr('disabled');
                            $enddate.removeAttr('disabled');

                            $begindate.validatebox({required: true});
                            $begindate.validatebox('validate');
                            $enddate.validatebox({required: true});
                            $enddate.validatebox('validate');
                        }

                    });
                </c:if>

			} // panel.onLoad close
		});
    });

    /**
    *
    * @param callBack
    * @param args
     */
    function save_delegate(callBack, args) {

        $form.form({
            onSubmit: function(param) {

                var flag = $form.form('validate');
                if(!flag) {

                    return false;
                }

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

        save_delegate(call, args);
	}

	-->
	</script>
  </body>
</html>