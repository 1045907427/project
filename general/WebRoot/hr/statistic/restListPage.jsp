<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.CommonUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="today" class="java.util.Date"/>
<%
	String format = request.getParameter("format");
	if(StringUtils.isEmpty(format)) {
		format = "yyyy-MM-dd";
	}
	String month = CommonUtils.getCurrentYearStr() + "-" + CommonUtils.getCurrentMonthStr() + "-01";
	month = CommonUtils.dateStringChange(month, format);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>请假列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'">
			<div id="hr-queryDiv-restListPage" style="padding:0px;height:auto">
				<div class="buttonBG" id="hr-buttons-restListPage" style="height: 26px;">
                    <security:authorize url="/hr/statistic/exportRestList.do">
                        <a href="javaScript:void(0);" id="hr-export-restListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </div>
				<form id="hr-queryForm-restListPage">
					<table style="">
						<tr>
							<td class="len80 left">请假时间：</td>
							<td class="left" style="width: 280px;">
								<input type="text" class="len120 easyui-validatebox Wdate" id="hr-val08-restListPage" name="begindate" onclick="WdatePicker({'dateFmt': '${param.format}' || 'yyyy-MM-dd'})" autocomplete="off" value="<%=month %>"/> ～
								<input type="text" class="len120 easyui-validatebox Wdate" id="hr-val09-restListPage" name="enddate" onclick="WdatePicker({'dateFmt':'${param.format}' || 'yyyy-MM-dd'})" autocomplete="off"/>
							</td>
                            <td class="len80 right">请假类型：</td>
                            <td class="left" style="width: 160px; border-right: solid 1px #aaa;">
								<div>
									<label class="divtext"><input type="checkbox" class="checkbox1" name="val07" value="事假"/>事假</label>
									<label class="divtext"><input type="checkbox" class="checkbox1" name="val07" value="公出"/>公出</label>
									<label class="divtext"><input type="checkbox" class="checkbox1" name="val07" value="年假"/>年假</label>
								</div>
                            </td>
                            <td class="len60 right">OA状态：</td>
                            <td class="left" style="width: 130px;">
								<div>
									<label class="divtext"><input type="checkbox" class="checkbox1" name="status" value="1"/>未完结</label>
									<label class="divtext"><input type="checkbox" class="checkbox1" name="status" value="9"/>已完结</label>
								</div>
                            </td>
						</tr>
						<tr>
							<td colspan="6" align="right">
								<div>
									<a href="javascript:void(0);" id="hr-queay-restListPage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="hr-resetQueay-restListPage" class="button-qr">重置</a>
									<span id="hr-queryAdvanced-restListPage"></span>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table id="hr-datagrid-restListPage" data-options="border:false"></table>
		</div>
		<div id="hr-dialog-restListPage"></div>
	</div>
	<script type="text/javascript">
	<!--
	
	$(function(){
		
//		// 人员
//		$('#hr-personid-restListPage').widget({
//			referwid: 'RL_T_BASE_PERSONNEL',
//			singleSelect:true,
//			width: 100,
//			onlyLeafCheck:false
//		});
//
//		// 用户
//		$('#hr-userid-restListPage').widget({
//			referwid: 'RT_T_SYS_USER',
//			singleSelect:false,
//			width: 150,
//			onlyLeafCheck:false
//		});

        <security:authorize url="/hr/statistic/exportRestList.do">
            $("#hr-export-restListPage").Excel('export',{
                queryForm: "#hr-queryForm-restListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type: 'exportUserdefined',
                name: '请假统计表' + new Date().Format('yyyy-MM-dd'),
                url: 'hr/statistic/exportRestList.do'
            });
        </security:authorize>


        var restCols = $("#hr-datagrid-restListPage").createGridColumnLoad({
		
  			frozenCol : [[
		    			]],
		    commonCol : [[//{field:'id',title:'编号',hidden:true},
                          //{field:'val01',title:'申请人',width:70},

                            {field:'applyuser',title:'申请人'},
                            {field:'applydate',title:'申请时间'},
                            {field:'dept',title:'所在部门'},
                            {field:'workjob',title:'岗位'},
                            {field:'delegateuser',title:'职务代理人'},
                            {field:'delegatephone',title:'代理人电话'},
                            {field:'resttype',title:'请假类型'},
                            {field:'begindate',title:'请假时间（开始）'},
                            {field:'enddate',title:'请假时间（结束）'},
                            {field:'day',title:'请假天数'},
                            {field:'reason',title:'请假理由'},
                            {field:'processid',title:'OA号',
                                formatter: function(value, row, index){

                                    return '<a href="javascript:void(0);" onclick="viewOa(' + value + ')">' + value + '</a>';
                                }
                            },
                            {field:'status',title:'OA状态',
                                formatter: function(value, row, index){

                                    if(value == '1') {

                                        return '<font color="red">未完结</font>';
                                    }
                                    return '<font color="green">已完结</font>';
								}
                            }
		    			]]
		});
		
  		$("#hr-datagrid-restListPage").datagrid({
			authority: restCols,
			frozenColumns: restCols.frozen,
			columns: restCols.common,
			fit: true,
			title: '',
			method: 'post',
			rownumbers: true,
			pagination: true,
			idField: 'id',
			singleSelect: true,
			url: 'hr/statistic/selectRestList.do',
            queryParams: $("#hr-queryForm-restListPage").serializeJSON(),
			toolbar: '#hr-queryDiv-restListPage'
		}).datagrid("columnMoving");

		// 查询
		$("#hr-queay-restListPage").click(function(){
       		var queryJSON = $("#hr-queryForm-restListPage").serializeJSON();
       		$("#hr-datagrid-restListPage").datagrid({
       				url: 'hr/statistic/selectRestList.do',
		   			queryParams:queryJSON});
       		//$("#hr-datagrid-restListPage").datagrid('load', queryJSON);
		});
		
		// 重置
		$("#hr-resetQueay-restListPage").click(function(){
       		$("#hr-queryForm-restListPage")[0].reset();
       		$("#hr-queryForm-restListPage").form('clear');
       		//$('#hr-userid-restListPage').widget('clear');
//       		$('#hr-datagrid-restListPage').datagrid('loadData', []);
            var queryJSON = $("#hr-queryForm-restListPage").serializeJSON();
            $("#hr-datagrid-restListPage").datagrid({
                url: 'hr/statistic/selectRestList.do',
                queryParams:queryJSON});
		});

	});
	
	var $line = $('#hr-datagrid-restListPage');

    function viewOa(id) {

        top.addOrUpdateTab('act/workViewPage.do?processid=' + id, '工作查看');
    }

	-->
	</script>
  </body>
</html>
