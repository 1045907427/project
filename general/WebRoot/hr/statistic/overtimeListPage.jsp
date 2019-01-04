<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>请假列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'">
			<div id="hr-queryDiv-overtimeListPage" style="padding:0px;height:auto">
				<div class="buttonBG" id="hr-buttons-overtimeListPage" style="height: 26px;">
                    <security:authorize url="/hr/statistic/exportRestList.do">
                        <a href="javaScript:void(0);" id="hr-export-overtimeListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </div>
				<form id="hr-queryForm-overtimeListPage">
					<table style="width: 750px;">
						<tr>
							<td class="len80 left">加班时间：</td>
							<td class="left" style="width: 220px;">
								<input type="text" class="len90 easyui-validatebox Wdate" id="hr-val04-overtimeListPage" name="val04" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-01' type='date' dateStyle='long' />"/> ～
								<input type="text" class="len90 easyui-validatebox Wdate" id="hr-val05-overtimeListPage" name="val05" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off"/>
							</td>
                            <td class="len60 right">OA状态：</td>
                            <td class="left" style="width: 130px;">
                                <div>
                                    <label class="divtext"><input type="checkbox" class="checkbox1" name="status" value="1"/>未完结</label>
                                    <label class="divtext"><input type="checkbox" class="checkbox1" name="status" value="9"/>已完结</label>
                                </div>
                            </td>
                            <td>
                                <a href="javascript:void(0);" id="hr-queay-overtimeListPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="hr-resetQueay-overtimeListPage" class="button-qr">重置</a>
                                <span id="hr-queryAdvanced-overtimeListPage"></span>
                            </td>
						</tr>
					</table>
				</form>
			</div>
			<table id="hr-datagrid-overtimeListPage" data-options="border:false"></table>
		</div>
		<div id="hr-dialog-overtimeListPage"></div>
	</div>
	<script type="text/javascript">
	<!--
	
	$(function(){

        <security:authorize url="/hr/statistic/exportOvertimeList.do">
            $("#hr-export-overtimeListPage").Excel('export',{
                queryForm: "#hr-queryForm-overtimeListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type: 'exportUserdefined',
                name: '加班统计表' + new Date().Format('yyyy-MM-dd'),
                url: 'hr/statistic/exportOvertimeList.do'
            });
        </security:authorize>


        var overtimeCols = $("#hr-datagrid-overtimeListPage").createGridColumnLoad({
		
  			frozenCol : [[
		    			]],
		    commonCol : [[//{field:'id',title:'编号',hidden:true},
                          //{field:'val01',title:'申请人',width:70},

                            {field:'val03',title:'申请人'},
                            {field:'val01',title:'所在部门'},
                            {field:'val02',title:'申请时间'},
                            {field:'val04',title:'加班时间(开始)'},
                            {field:'val05',title:'加班时间(结束)'},
                            {field:'val06',title:'加班天数'},
                            {field:'val07',title:'加班地点'},
                            {field:'val08',title:'工作内容'},
                            {field:'val18',title:'OA号',
                                formatter: function(value, row, index){

                                    return '<a href="javascript:void(0);" onclick="viewOa(' + value + ')">' + value + '</a>';
                                }
                            },
                            {field:'val19',title:'OA状态',
                                formatter: function(value, row, index){

                                    if(value == '1') {

                                        return '<font color="red">未完结</font>';
                                    }
                                    return '<font color="green">已完结</font>';                                }
                            }
		    			]]
		});
		
  		$("#hr-datagrid-overtimeListPage").datagrid({
			authority: overtimeCols,
			frozenColumns: overtimeCols.frozen,
			columns: overtimeCols.common,
			fit: true,
			title: '',
			method: 'post',
			rownumbers: true,
			pagination: true,
			idField: 'id',
			singleSelect: true,
			url: 'hr/statistic/selectOvertimeList.do',
            queryParams: $("#hr-queryForm-overtimeListPage").serializeJSON(),
			toolbar: '#hr-queryDiv-overtimeListPage'
		}).datagrid("columnMoving");

		// 查询
		$("#hr-queay-overtimeListPage").click(function(){
       		var queryJSON = $("#hr-queryForm-overtimeListPage").serializeJSON();
       		$("#hr-datagrid-overtimeListPage").datagrid({
       				url: 'hr/statistic/selectOvertimeList.do',
		   			queryParams:queryJSON});
       		//$("#hr-datagrid-overtimeListPage").datagrid('load', queryJSON);
		});
		
		// 重置
		$("#hr-resetQueay-overtimeListPage").click(function(){
       		$("#hr-queryForm-overtimeListPage")[0].reset();
       		$("#hr-queryForm-overtimeListPage").form('clear');
       		//$('#hr-userid-overtimeListPage').widget('clear');
//       		$('#hr-datagrid-overtimeListPage').datagrid('loadData', []);
            var queryJSON = $("#hr-queryForm-overtimeListPage").serializeJSON();
            $("#hr-datagrid-overtimeListPage").datagrid({
                url: 'hr/statistic/selectOvertimeList.do',
                queryParams:queryJSON});
		});

	});
	
	var $line = $('#hr-datagrid-overtimeListPage');

    function viewOa(id) {

        top.addOrUpdateTab('act/workViewPage.do?processid=' + id, '工作查看');
    }

	-->
	</script>
  </body>
</html>
