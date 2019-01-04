<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:useBean id="today" class="java.util.Date"/>
<html>
  <head>
    <title>签到报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <style type="text/css">
        .len60 {
            width: 60px;
        }
    </style>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'">
			<div id="hr-queryDiv-signinReportPage" style="padding:0px;height:auto">
				<div class="buttonBG" id="hr-buttons-signinReportPage" style="height:26px;"></div>
				<form id="hr-form-signinReportPage">
					<table class="queryTable">
						<tr>
							<td class="len60 left">签到日期：</td>
							<td class="left">
                                <input type="text" class="len90 easyui-validatebox Wdate" id="hr-startdate-signinReportPage" name="startdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd', 'minDate': '2014-01-01', 'maxDate': '<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />'})" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" data-options="required:true,validType:'date'"/>
                                到
                                <input type="text" class="len90 easyui-validatebox Wdate" id="hr-enddate-signinReportPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd', 'minDate': '2014-01-01', 'maxDate': '<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />'})" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" data-options="required:true,validType:'date'"/>
							</td>
                            <td class="left">用　户：</td>
                            <td class="left"><input type="text" class="len150" id="hr-userid-signinReportPage" name="userid" autocomplete="off"/></td>
                            <td class="left">部　门：</td>
                            <td class="left"><input type="text" class="len150" id="hr-deptid-signinReportPage" name="deptid" autocomplete="off"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>是否签到：</td>
                            <td>
                                <select name="issignin" id="hr-issignin-signinReportPage" style="width: 204px;">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </td>
                            <td></td>
                            <td></td>
                            <td colspan="2">
								<a href="javascript:void(0);" id="hr-queay-signinReportPage" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="hr-resetQueay-signinReportPage" class="button-qr">重置</a>
								<span id="hr-queryAdvanced-signinReportPage"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table id="hr-datagrid-signinReportPage" data-options="border:false"></table>
		</div>
		<div id="hr-dialog-signinReportPage"></div>
	</div>
	<script type="text/javascript">
    <!--
	$(function(){
		
		// 人员
		$('#hr-personid-signinReportPage').widget({ 
			referwid: 'RL_T_BASE_PERSONNEL',
			singleSelect:true,
			width: 100,
			onlyLeafCheck:false
		});

        // 用户
        $('#hr-userid-signinReportPage').widget({
            referwid: 'RT_T_SYS_USER',
            singleSelect:false,
            width: 150,
            onlyLeafCheck:false
        });

        // 部门
        $('#hr-deptid-signinReportPage').widget({
            referwid: 'RT_T_SYS_DEPT',
            singleSelect:false,
            width: 150,
            onlyLeafCheck:false
        });
		
		$('#hr-buttons-signinReportPage').buttonWidget({

            initButton:[
                {},
                <security:authorize url="/hr/report/exportSignReportData.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#hr-form-signinReportPage",
                        datagrid:'#hr-datagrid-signinReportPage',
                        type: 'exportUserdefined',
                        name: '签到报表',
                        url: 'hr/report/exportSignReportData.do'
                    }

                },
                </security:authorize>
                {}
            ],
	  		buttons: [{}]
		});

        var cols = $("#hr-datagrid-signinReportPage").createGridColumnLoad({
            frozenCol: [[
//                {field: 'rowcheck', checkbox: true}
            ]],
            commonCol: [[{field: 'id', title: '编号', hidden: true},
                {field: 'businessdate', title: '日期', width: 70},
                {field: 'username', title: '用户', width: 80},
                {
                    field: 'deptid', title: '部门', width: 120,
                    formatter: function (value, row, index) {

                        return row.deptname
                    }
                },
                {field: 'ambegin', title: '上午上班', width: 130},
                {field: 'amend', title: '上午下班', width: 130},
                {field: 'pmbegin', title: '下午上班', width: 130},
                {field: 'pmend', title: '下午下班', width: 130},
                {field: 'outtime', title: '外出', width: 130},
                {field: 'remark', title: '备注'}
            ]]
        });
		
  		$("#hr-datagrid-signinReportPage").datagrid({
			authority: cols,
			frozenColumns: cols.frozen,
			columns: cols.common,
			fit: true,
			title: '',
			method: 'post',
			rownumbers: true,
			pagination: true,
			idField: 'id',
			singleSelect: true,
			selectOnCheck: true,
			checkOnSelect: true,
            sortName: 'businessdate,deptid,userid',
            sortOrder: 'asc',
			toolbar: '#hr-queryDiv-signinReportPage',
			<security:authorize url="/hr/signin/signinViewPage.do">
				onDblClickRow: function(index, row) {
					viewSigninInfo(row);
				}
			</security:authorize>
		}).datagrid("columnMoving");

		// 查询
		$("#hr-queay-signinReportPage").click(function(){

            var startdate = $('#hr-startdate-signinReportPage').val() || '';
            var enddate = $('#hr-enddate-signinReportPage').val() || '';
            if(startdate == '') {

                $.messager.alert('警告', '请选择开始日期！');
                return false;
            }
            if(enddate == '') {

                $.messager.alert('警告', '请选择结束日期！');
                return false;
            }

            var flag = $("#hr-form-signinReportPage").form('validate');
            if(!flag) {

                return false;
            }

       		var queryJSON = $("#hr-form-signinReportPage").serializeJSON();
       		$("#hr-datagrid-signinReportPage").datagrid({
       				url: 'hr/report/getSignReportData.do',
		   			queryParams:queryJSON
       		});
		});
		
		// 重置
		$("#hr-resetQueay-signinReportPage").click(function(){
       		$("#hr-form-signinReportPage")[0].reset();
       		$("#hr-form-signinReportPage").form('clear');
            $('#hr-userid-signinReportPage').widget('clear');
            $('#hr-deptid-signinReportPage').widget('clear');
            $('#hr-businessdate-signinReportPage').val('<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />');
       		$('#hr-datagrid-signinReportPage').datagrid('loadData', []);
		});

	});
	
	var $line = $('#hr-datagrid-signinReportPage');

	/**
	 * 查看签到
	 */
	function viewSigninInfo(signin) {
	
		if(signin == null) {
		
			$.messager.alert('提醒', '请选择一条记录！');
			return false;
		}

        if(signin.id == null) {

            $.messager.alert('提醒', '该用户当天无签到信息！');
            return false;
        }

		top.addTab('hr/signin/signinPage.do?type=view&id=' + signin.id, '签到详情');
		return true;
	}

	-->
	</script>
  </body>
</html>
