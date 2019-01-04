<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作委托-委托记录查询</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div id="activiti-datagrid-delegateLogPage"></div>
		<div id="activiti-div-delegateLogPage" style="padding:8px;">
			<form action="" id="activiti-form-delegateLogPage">
                <table>
                    <tr>
                        <td>流　程：</td>
                        <td><input id="activiti-definitionkey-delegateLogPage" name="definitionkey" class="len150"/></td>
                        <td>日期：</td>
                        <td>
                            <input type="text" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="starttime" style="width:90px;"/>
                            -
                            <input type="text" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="endtime" style="width:90px;"/></td>
                        <td>状态：</td>
                        <td>
                            <select id="activiti-type-delegateLogPage" name="type" class="len80">
                                <option value="0" selected="selected">委托</option>
                                <option value="1">被委托</option>
                            </select>
                        </td>
                        <td>
                            <a href="javascript:;" id="activiti-search-delegateLogPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="activiti-reset-delegateLogPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#activiti-datagrid-delegateLogPage').datagrid({
				columns:[[
					{field:'id', title:'编号', width:60, align:'center',hidden: true},
					{field:'definitionname', title:'流程名称', width:200},
					{field:'title', title:'标题', width:350,
                        formatter: function(value, row, index){
                            return '<a href="javascript:;" onclick="top.addOrUpdateTab(\'act/workViewPage.do?processid=' + row.processid + '\', \'工作查看\');" class="process-title">' + value + '</a>'
                        }
                    },
					{field:'taskname', title:'步骤', width:160},
					{field:'username', title:'委托人', width:80},
					{field:'delegateusername', title:'被委托人', width:80},
					{field:'adddate', title:'时间', width:120}
				]],
				fit:true,
				border:false,
				method:'post',
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				pageSize:10,
				toolbar:'#activiti-div-delegateLogPage',
                onClickRow: function(index, row) {

                    $('.process-title').css({color: '#0450a0'});
                    $('.process-title:eq(' + index + ')').css({color: '#fff'});
                }
			});
            $('#activiti-definitionkey-delegateLogPage').widget({
                name:'t_act_delegate_log',
                col:'definitionkey',
                singleSelect:true,
                width:150,
                onlyLeafCheck:true
            });
//			$('#activiti-definition-delegateLogPage').widget({
//    			referwid:'RT_T_ACT_DEFINITION_1',
//    			singleSelect:true,
//    			width:200,
//    			onlyLeafCheck:true,
//    			onChecked:function(data){
//    				$('#activiti-key-delegateLogPage').val(data.unkey);
//    			}
//    		});
    		$('#activiti-search-delegateLogPage').click(function(){

	       		var queryJSON = $('#activiti-form-delegateLogPage').serializeJSON();
	       		$('#activiti-datagrid-delegateLogPage').datagrid({
		   			url:'act/getDelegateLogList.do',
		   			queryParams:queryJSON
	       		});
    		});
    		$('#activiti-reset-delegateLogPage').click(function(){

				$('#activiti-form-delegateLogPage')[0].reset();
				$('#activiti-definitionkey-delegateLogPage').widget('clear');
//				$('#activiti-datagrid-delegateLogPage').datagrid('load', {type: 0});
                var queryJSON = $('#activiti-form-delegateLogPage').serializeJSON();
                $('#activiti-datagrid-delegateLogPage').datagrid({
                    url: 'act/getDelegateLogList.do',
                    queryParams: queryJSON
                });
			});
    		//回车事件
			controlQueryAndResetByKey('activiti-search-delegateLogPage', 'activiti-reset-delegateLogPage');
		});
	</script>
  </body>
</html>
