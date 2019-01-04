<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>工作查询</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<style type="text/css">
		.workQueryTable{border-collapse:collapse;/*min-width:850px;*/}
		.workQueryTable td{height:28px;line-height:28px;}
	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div id="activiti-datagrid-workQueryPage"></div>
		<div id="activiti-query-workQueryPage">
			<form action="" id="activiti-form-workQueryPage">
				<table class="workQueryTable">
					<tr>
						<td class="len40 right">OA编号：</td>
						<td class="len80">
                            <input class="easyui-numberbox" type="text" name="id" autocomplete="off" style="width: 85px"/></td>
						<td class="len80 right">流程名称：</td>
						<td class="len200"><input id="activiti-definition-workQueryPage" name="definition" autocomplete="off" style="width: 178px;"/>
                            <input type='hidden' id="activiti-type-workQueryPage" name="type" /></td>
						<td class="len40 right">范围：</td>
						<td class="len180">
							<select name="range" id="activiti-range-workQueryPage" style="width: 160px;">
								<option value="" selected="selected">所有范围</option>
								<option value="1">我发起的</option>
								<option value="2">我经办的</option>
							</select>
							<span id="activiti-assignee-workQueryPage" style="display:none;">
								<input type="text" id="activiti-applyUser-workQueryPage" name="applyuser" />
							</span>
						</td>

					</tr>
					<tr>
						<td class="right">状　态：</td>
						<td>
							<select name="isend" style="width: 84px; margin-left: 0px;">
								<option value="" selected="selected">所有状态</option>
								<option value="0">正在执行</option>
								<option value="1">已经结束</option>
							</select>
						</td>
						<td class="right">日　　期：</td>
						<td>
							<input type="text" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="starttime" class="len80" autocomplete="off"/> 
							- <input type="text" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="endtime" class="len80" autocomplete="off"/>
						</td>
						<td class="right">标题：</td>
						<td><input type="text" name="title" style="width:160px;" autocomplete="off"/></td>
					</tr>
                    <tr>
                        <td class="right">关键字：</td>
                        <td colspan="3"><input type="text" name="keywords" style="width:341px;" autocomplete="off" placeholder="多个关键字之间以空格隔开，关键字查询以模糊查询进行。"/></td>
                        <td colspan="2">
                            <a id="activiti-search-workQueryPage" class="button-qr">查询</a>
                            <a id="activiti-reset-workQueryPage" class="button-qr">重置</a>
                        </td>
                    </tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
		
			var workListJson = $("#activiti-datagrid-workQueryPage").createGridColumnLoad({
//				name:'t_act_process',
                commonCol :[[
                        {field:'id', title:'编号', width:60},
                        {field:'definitionname', title:'流程名称', width:200},
                        {field:'title', title:'标题', width:300,
                            formatter: function(value,row,index){
                                return "<a href='javascript:;' onclick=\"viewDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">"+value+"</a>";
                            }
                        },
                    	{field:'keyword', title:'关键字', width: 150, hidden: true,
                            formatter: function(value,row,index){

                    	    	var keywordArr = new Array();
                                if(row.keyword1) {
                                    keywordArr.push(row.keyword1);
                                }
                                if(row.keyword2) {
                                    keywordArr.push(row.keyword2);
                                }
                                if(row.keyword3) {
                                    keywordArr.push(row.keyword3);
                                }
                                if(row.keyword4) {
                                    keywordArr.push(row.keyword4);
                                }
                                if(row.keyword5) {
                                    keywordArr.push(row.keyword5);
                                }

                                return keywordArr.join(',');
                    		}
                        },
                        {field:'applyusername', title:'申请人', width:100},
                        {field:'taskname', title:'当前节点', width:100},
                        {field:'applydate', title:'开始时间', width:120},
                        {field:'updatedate', title:'结束时间', width:120,
                            formatter: function(value,row,index){
                                if(row.isend != '1'){
                                    return "";
                                }
                                else{
                                    return value;
                                }
                            }
                        },
                        {field:'status', title:'状态', width:60,
                            formatter: function(value,row,index){
                                if(value == '0' ){
                                    return "<font color='red'>已作废</font>";
                                } else if(value == '9' || row.isend == '1'){
                                    return "<font color='green'>结束</font>";
                                } else if(value == '1'){
                                    return "<font color='green'>正在运行</font>";
                                } else if(value == '2') {
                                    return '<font color="red">已挂起</font>';
                                } else if(value == '3') {
                                    return '<font color="red">临时删除</font>';
                                }
                            }
                        }
                ]]
			});
		
			$("#activiti-datagrid-workQueryPage").datagrid({
				authority : workListJson,
				frozenColumns: workListJson.frozen,
				columns:workListJson.common,
				fit:true,
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:true,
				toolbar:'#activiti-query-workQueryPage'
			});
			$("#activiti-definition-workQueryPage").widget({
			referwid:'RT_T_ACT_DEFINATION',
			singleSelect:true,
			width: 172,
			onlyLeafCheck:true,
			onChecked:function(data){
				$("#activiti-type-workQueryPage").val(data.unkey);
			},
			onClear:function(){
				$("#activiti-type-workQueryPage").val("");
			}
		});
		$("#activiti-applyUser-workQueryPage").widget({
			referwid:'RT_T_SYS_USER',
			singleSelect:true,
			rows:20,
			width:80,
			onlyLeafCheck:true
		});
		$("#activiti-range-workQueryPage").change(function(){
			var val = $(this).val();
			if(val == "3"){
				$("#activiti-assignee-workQueryPage").show();
			}
			else{
				$("#activiti-assignee-workQueryPage").hide();
			}
		});
		$("#activiti-search-workQueryPage").click(function(){

            var old = $('[name=keywords]').val();

            old.split

            var replace1 = old.replace(/[\u3000]/g, ' ');
            $('[name=keywords]').val(replace1);
            //var keywords = replace1.split(' ');

			var queryJSON = $("#activiti-form-workQueryPage").serializeJSON();
            $('[name=keywords]').val(old);
			$("#activiti-datagrid-workQueryPage").datagrid({
					url:'act/getWorkQueryList.do',
					queryParams:queryJSON
			});
		});
		$("#activiti-reset-workQueryPage").click(function(){
				$("#activiti-form-workQueryPage").form('clear');
				$("#activiti-form-workQueryPage")[0].reset();
				$("#activiti-definition-workQueryPage").widget('clear');
				$("#activiti-applyUser-workQueryPage").widget('clear');
			$("#activiti-datagrid-workQueryPage").datagrid('load', {});
			});
		//回车事件
			controlQueryAndResetByKey("activiti-search-workQueryPage","activiti-reset-workQueryPage");
		});
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
	</script>
  </body>
</html>