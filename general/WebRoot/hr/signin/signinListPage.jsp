<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>签到列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <style type="text/css">
    	.len160 {
    		width: 160px;
    	}
    </style>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'">
			<div id="hr-div-signinListPage" style="padding:0px;height:auto">
				<div class="buttonBG" id="hr-buttons-signinListPage" style="height:26px;"></div>
				<form id="hr-form-signinListPage">
					<table class="queryTable">
						<tr>
                            <td class="len50 left">签到日期：</td>
                            <td class="left" style="width: 220px;">
                                <input type="text" class="len90 easyui-validatebox Wdate" id="hr-datefrom-signinListPage" name="datefrom" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off"/>&nbsp;～
                                <input type="text" class="len90 easyui-validatebox Wdate" id="hr-dateto-signinListPage" name="dateto" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off"/>
                            </td>
                            <td class="len50 left">用户：</td>
                            <td class="len160 left"><input type="text" class="len150" id="hr-userid-signinListPage" name="userid" autocomplete="off"/></td>
                            <td class="len50 left">部门：</td>
                            <td class="len160 left"><input type="text" class="len150" id="hr-deptid-signinListPage" name="deptid" autocomplete="off"/></td>
						</tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td colspan="3" class="right">
								<a href="javascript:void(0);" id="hr-queay-signinListPage" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="hr-resetQueay-signinListPage" class="button-qr">重置</a>
								<span id="hr-queryAdvanced-signinListPage"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table id="hr-datagrid-signinListPage" data-options="border:false"></table>
		</div>
		<div id="hr-dialog-signinListPage"></div>
	</div>
	<script type="text/javascript">
        <!--
        $(function(){

            // 人员
            $('#hr-personid-signinListPage').widget({
                referwid: 'RL_T_BASE_PERSONNEL',
                singleSelect:true,
                width: 100,
                onlyLeafCheck:false
            });

            // 用户
            $('#hr-userid-signinListPage').widget({
                referwid: 'RT_T_SYS_USER',
                singleSelect:false,
                width: 150,
                onlyLeafCheck:false
            });

            // 部门
            $('#hr-deptid-signinListPage').widget({
                referwid: 'RT_T_SYS_DEPT',
                singleSelect:false,
                width: 150,
                onlyLeafCheck:false
            });

            $('#hr-buttons-signinListPage').buttonWidget({
                initButton: [
                    <security:authorize url='/hr/signin/deleteSignin.do'>
                    {
                        type: 'button-delete',
                        handler: deleteSignin
                    },
                    </security:authorize>
                    <security:authorize url='/hr/signin/exportSignin.do'>
                    {
                        type: 'button-export',
                        attr: {
                            queryForm: '#hr-form-signinListPage',
                            datagrid:'#hr-datagrid-signinListPage',
                            type: 'exportUserdefined',
                            name: '签到记录',
                            url: 'hr/signin/exportSignin.do'
                        }
                    },
                    </security:authorize>
                    {}
                ],
                buttons: [{}],
                model: 'bill',
                type: 'view',
                tname: 't_hr_singin'
            });

            var signinCols = $('#hr-datagrid-signinListPage').createGridColumnLoad({
                frozenCol : [[
                    {field:'rowcheck',checkbox: true}
                ]],
                commonCol : [[{field:'id',title:'编号',hidden:true},
                    {field:'businessdate',title:'日期',width:70},
                    {field:'username',title:'用户',width:80},
                    {field:'deptname',title:'部门',width:100},
                    {field:'ambegin',title:'上午上班',width:130},
                    {field:'amend',title:'上午下班',width:130},
                    {field:'pmbegin',title:'下午上班',width:130},
                    {field:'pmend',title:'下午下班',width:130},
                    {field:'outtime',title:'外出',width:130},
                    {field:'remark',title:'备注'}
                ]]
            });

            $('#hr-datagrid-signinListPage').datagrid({
                authority: signinCols,
                frozenColumns: signinCols.frozen,
                columns: signinCols.common,
                fit: true,
                title: '',
                method: 'post',
                rownumbers: true,
                pagination: true,
                idField: 'id',
                singleSelect: false,
                selectOnCheck: true,
                checkOnSelect: true,
                toolbar: '#hr-div-signinListPage',
                <security:authorize url='/hr/signin/signinViewPage.do'>
                onDblClickRow: function(index, row) {
                    viewSigninInfo(row);
                }
                </security:authorize>
            }).datagrid('columnMoving');

            // 查询
            $('#hr-queay-signinListPage').click(function(){
                var queryJSON = $('#hr-form-signinListPage').serializeJSON();
                $('#hr-datagrid-signinListPage').datagrid({
                    url: 'hr/signin/selectSigninList.do',
                    queryParams:queryJSON
                });
            });

            // 重置
            $('#hr-resetQueay-signinListPage').click(function(){
                $('#hr-form-signinListPage')[0].reset();
                $('#hr-form-signinListPage').form('clear');
                $('#hr-userid-signinListPage').widget('clear');
                $('#hr-datagrid-signinListPage').datagrid('loadData', []);
            });

        });

        var $line = $('#hr-datagrid-signinListPage');

        /**
         * 查看签到
         */
        function viewSigninInfo(signin) {

            if(signin == null) {

                $.messager.alert('提醒', '请选择一条记录！');
                return false;
            }

            top.addTab('hr/signin/signinPage.do?type=view&id=' + signin.id, '签到详情');
            return true;
        }

        /**
         * 删除签到
         * @param id
         * @returns {boolean}
         */
        function deleteSignin() {

            var rows = $line.datagrid('getChecked');
            if(rows == null || rows.length == 0) {
                $.messager.alert('提醒', '请选中需要删除的签到记录！');
                return false;
            }

            var idArr = new Array();
            for(var i in rows) {
                idArr.push(rows[i].id);
            }
            if(idArr.length == 0) {
                $.messager.alert('提醒', '请选中需要删除的签到记录！');
                return false;
            }

            $.messager.confirm('提醒', '是否删除选中的签到记录？', function(e) {

                loading('删除中...');
                if(e) {

                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        url: 'hr/signin/deleteSignin.do',
                        data: {ids: idArr.join(',')},
                        success: function(json) {

                            loaded();

                            if(json.flag == false) {

                                $.messager.alert('提醒', json.msg || '删除失败！');
                                $('#hr-datagrid-signinListPage').datagrid('reload');
                                return false;
                            }

                            var success = json.success || 0;
                            var failure = json.failure || 0;

                            var msg = '删除成功 ' + success + ' 条，删除失败 ' + failure + ' 条。';
                            $.messager.alert('提醒', msg);
                            $('#hr-datagrid-signinListPage').datagrid('reload');
                            return true;
                        },
                        error: function() {
                            $.messager.alert('错误', '系统错误！<br/>如果一直出现该提示，请联系管理员。');
                            return false;
                        }
                    });
                }
            });

            return false;
        }
	
	-->
	</script>
  </body>
</html>
