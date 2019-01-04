<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>Form权限设定页面</title>
  </head>
  
  <body>
    <style type="text/css">
        .form_rule_content {

        }
        .form_rule_content th {
            padding: 3px;
            border: 1px solid #7babcf;
        }
        .form_rule_content td {
            padding: 5px;
            border: 1px solid #7babcf;
        }
    </style>
    <table cellpadding="0px" cellspacing="0px" style="border-collapse: collapse; border:1px solid #7babcf; width: 98%; margin: 5px;" class="form_rule_content">
        <tr>
            <th>&nbsp;</th>
            <th>项目名称</th>
            <th><label><input type="checkbox" visible onclick="javascript:setFormItemRule(null, 'visible', $(this));"/>是否可见</label></th>
            <th><label><input type="checkbox" writable onclick="javascript:setFormItemRule(null, 'writable', $(this));"/>是否可编辑</label></th>
            <th><label><input type="checkbox" required onclick="javascript:setFormItemRule(null, 'required', $(this));"/>是否必填</label></th>
        </tr>
        <c:forEach items="${list}" var="item" varStatus="idx">
            <tr>
                <td align="center"><c:out value="${idx.index + 1 }"></c:out></td>
                <td><c:out value="${item.itemname}"></c:out></td>
                <td><label><input type="checkbox" visible <c:if test="${item.visible eq '1'}">checked="checked"</c:if> onclick="javascript:setFormItemRule('${item.itemid }', 'visible', $(this));"/>是</label></td>
                <td><label><input type="checkbox" writable <c:if test="${item.writable eq '1'}">checked="checked"</c:if> onclick="javascript:setFormItemRule('${item.itemid }', 'writable', $(this));"/>是</label></td>
                <td><label><input type="checkbox" required <c:if test="${item.required eq '1'}">checked="checked"</c:if> onclick="javascript:setFormItemRule('${item.itemid }', 'required', $(this));"/>是</label></td>
            </tr>
        </c:forEach>
    </table>
	<script type="text/javascript">

        function setFormItemRule(itemid, col, d) {

            var v = d[0].checked ? '1' : '0';
            loading('设定中...');
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: 'act/setFormItemRule.do',
                data: {definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', itemid: itemid, col: col, val: v},
                success: function(json) {

                    loaded();
                    if(json.flag) {

                        $.messager.alert('提醒', '设置成功。');

                        //点击header的场合
                        if(itemid == null) {

                            $('input[type=checkbox][' + col + ']').each(function() {
                                $(this)[0].checked = d[0].checked;
                            });

                        // 点击head以外checkbox的场合
                        } else {

                            var total = true;
                            $('input[type=checkbox][' + col + ']:gt(0)').each(function() {
                               if($(this)[0].checked == false) {
                                   total = false;
                                   return false;
                               }
                            });
                            $('input[type=checkbox][' + col + ']:eq(0)')[0].checked = total;
                        }

                        return true;
                    }

                    $.messager.alert('警告', '设置失败！');
                    return false;
                },
                error: function() {}
            })


        }

		var rightClickId = "";
		$(function(){

            // 初始化header部分的checkbox状态
            $.each(['visible', 'writable', 'required'], function(index, col){

                var total = true;
                $('input[type=checkbox][' + col + ']:gt(0)').each(function() {
                    if($(this)[0].checked == false) {
                        total = false;
                        return false;
                    }
                });
                $('input[type=checkbox][' + col + ']:eq(0)')[0].checked = total;
            });


			//流程分类树
			var formTypeTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "act/getFormTypeTree.do",
					autoParam: ["id", "pid", "name", "key"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						$("#activiti-datagrid-formSelectPage").datagrid({
							queryParams:{type:treeNode.key}
						}).datagrid('reload');
					}
				}
			};
			$.fn.zTree.init($("#activiti-typeTree-formSelectPage"), formTypeTreeSetting, null);
			$("#activiti-datagrid-formSelectPage").datagrid({
				columns:[[
						{checkbox:true},
						{field:"id", title:"编号", width:60},
						{field:"name", title:"表单名称", width:200},
						{field:"unkey", title:"表单标识", width:150}
					]],
				url:'act/getFormList.do',
				fit:true,
				fitColumns:true,
				border:false,
				checkOnSelect:true,
				selectOnCheck:true,
				rownumbers:true,
				pagination:true,
		 		idField:'id',
		 		singleSelect:true
			});
		});
	</script>
  </body>
</html>
