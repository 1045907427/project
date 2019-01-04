<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电脑版表单预览</title>
    <!-- 该页面针对于新版（Ueditor）Form编辑页面 -->
    <%@include file="/include.jsp" %>
    <!-- 该js文件主要用于加密生成fieldid -->
    <script type="text/ecmascript" src="activiti/js/sha1.js"></script>
</head>
<body>
<style type="text/css">
    table.itemlist {
        width: 100%;
        border-collapse: collapse;
        border:1px solid #7babcf;
    }
    table.itemlist th{
        border: 1px solid #CCC;
        padding: 5px;
    }
    table.itemlist td{
        border: 1px solid #CCC;
        padding: 5px;
    }
</style>
<script type="text/javascript">

    function retHtml() {
        return `${html }`;
    }
    function retTemplate() {
        return `${template }`;
    }
    function setFrameHeight(height) {

        $('iframe#viewframe').attr('height', (height + 50) +'px');
    }

    var itemlist = new Array();
    var itemmap = {};

    $(function(){

        $('#activiti-back-formPreviewPage2').click(function(){

            document.backform.submit();
        });

        {
            $('#activiti-type-formPreviewPage2').each(function() {

                var data = $(this).attr('data');
                $(this).removeAttr('data');
                $(this).children().removeAttr('selected');
                $(this).children().each(function() {

                    if($(this).attr('value') == data) {
                        $(this).attr('selected', 'selected');
                    }
                });
            });

        }
        <c:if test="${not empty param.id}">
        $('#activiti-unkey-formPreviewPage2').attr('readonly', 'readonly');
        </c:if>

        // 显示项目一览List
        {
            var count = 0;
            $('div#activiti-dummy-formPreviewPage2 [agentitemid]').each(function() {

                // span類型，可能是單選框或者複選框
                if($(this).is('span[agentitemid]')) {

                    // 单选框radio
                    if($(this).attr('plugins') == 'radios') {

                        var fieldname = $(this).attr('title');
                        var fieldid = $(this).attr('agentitemid');

                        var item = {};
                        item.unkey = '${param.unkey }';
                        item.itemid = fieldid;
                        item.itemname = fieldname;
                        item.type = 'radio';
                        item.remark = 'radio';
                        itemlist.push(item);
                        itemmap[fieldname] = itemlist.length;

                        // 复选框checkbox
                    } else if($(this).attr('plugins') == 'checkboxs') {

                        var fieldname = $(this).attr('title');
                        var fieldid = $(this).attr('agentitemid');

                        var item = {};
                        item.unkey = '${param.unkey }';
                        item.itemid = fieldid;
                        item.itemname = fieldname;
                        item.type = 'checkbox';
                        item.remark = 'checkbox';
                        itemlist.push(item);
                        itemmap[fieldname] = itemlist.length;
                    }
                } else if($(this).is('input[plugins=macros]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');
                    var remark = $(this).attr('data');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'macros';
                    item.remark = remark;
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                    // 运算控件
                } else if($(this).is('input[plugins=compute]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');
                    var remark = $(this).attr('plugins');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'compute';
                    item.remark = remark;
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('input[plugins=widget]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');
                    var refid = $(this).attr('refid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'widget';
                    item.remark = refid;
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('input[plugins=wdate]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');
                    var refid = $(this).attr('refid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'wdate';
                    item.remark = 'wdate';
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('input[plugins=numeric]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'numeric';
                    item.remark = 'numeric';
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('input[type=text]')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'text';
                    item.remark = 'text';
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('textarea')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'textarea';
                    item.remark = 'textarea';
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;

                } else if($(this).is('select')) {

                    var fieldname = $(this).attr('title');
                    var fieldid = $(this).attr('agentitemid');

                    var item = {};
                    item.unkey = '${param.unkey }';
                    item.itemid = fieldid;
                    item.itemname = fieldname;
                    item.type = 'select';
                    item.remark = 'select';
                    itemlist.push(item);
                    itemmap[fieldname] = itemlist.length;
                }

                count++;
            });

            // $('div#activiti-dummy-formPreviewPage2').attr('display', 'none');
            var background = ['#fff', '#ebf6f7'];
            var types = {
                text: '文本框',
                wdate: '日期文本框',
                textarea: '多行文本',
                select: '下拉菜单',
                radio: '单选框',
                checkbox: '复选框',
                macros: '宏控件',
                widget: '参照窗口',
                compute: '运算控件',
                numeric: '数字控件'
            };
            $.each(itemlist, function(index, obj) {

                var line = new Array();
                line.push('<tr style="background: ' + background[index % 2] + ';">');
                line.push('<td align="center">');
                line.push(index + 1);
                line.push('</td>');
                line.push('<td>');
                line.push(obj.itemid);
                line.push('</td>');
                line.push('<td>');
                line.push(obj.itemname);
                line.push('</td>');
                line.push('<td>');
                line.push(types[obj.type]);
                line.push('</td>');
                line.push('<td>');
                line.push(obj.remark);
                line.push('</td>');
                line.push('</tr>');

                $('table#activiti-fieldlist-formPreviewPage2').append(line.join(''));
            })
        }

        // check是否存在相同名称的控件
        var check = true;
        $.each($('#activiti-fieldlist-formPreviewPage2 tr:gt(0)'), function(index, item) {

            var text = $(this).children('td:eq(2)').text();
            var index2 = itemmap[text];
            if(index != (index2 - 1)) {

                check = false;
            }

            if(!check) {

                $('#activiti-fieldlist-formPreviewPage2 tr:eq(' + (index + 1) +')').css({'background': '#ff7870'});
                $('#activiti-fieldlist-formPreviewPage2 tr:eq(' + index2 +')').css({'background': '#ff7870'});
                return false;
            }
        });
        if(check) {

            $('#activiti-save-formPreviewPage2').removeAttr('disabled');
            $('#activiti-export-formPreviewPage2').removeAttr('disabled');
            $('.tips').remove();

            $('#activiti-save-formPreviewPage2').click(function(){

                var flag = $(document.forms[0]).form('validate');
                if(!flag) {
                    return false;
                }

                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    url : 'act/ue/saveForm.do',
                    data: {
                        unkey: $('#activiti-unkey-formPreviewPage2').val(),
                        name: $('#activiti-name-formPreviewPage2').val(),
                        intro: $('#activiti-intro-formPreviewPage2').val(),
                        type: $('#activiti-type-formPreviewPage2').val(),
                        detail: '${html }',
                        template: '${template }',
                        fields: '${param.fieldnum}',
                        list: JSON.stringify(itemlist)
                    },
                    success: function(json) {

                        if(json.flag) {

                            alert('保存成功。');

                            // 跳转至手机表单编辑页面
                            $('#activiti-phonehtml-formPreviewPage2').val(document.viewframe.retItems().join('\r\n'));
                            $('#activiti-formname-formPreviewPage2').val($('#activiti-name-formPreviewPage2').val());
                            document.phoneform.submit();

//                            window.close();
//                            return false;

                        } else {

                            $.messager.alert('提醒', '保存失败！');
                        }
                    },
                    error: function() {
                        $.messager.alert('提醒', '保存失败！');
                    }
                });
            });

            $('#activiti-export-formPreviewPage2').click(function() {

                var title = $('#activiti-name-formPreviewPage2').val();
                var html = '${template }';

                $('#activiti-exportForm-formPreviewPage2 input').first().val(title);
                $('#activiti-exportForm-formPreviewPage2').submit();

                return false;
            });

        }
        $.extend($.fn.validatebox.defaults.rules, {
            validUnkey: {//编号唯一性,最大长度
                validator: function (value, param) {
                    if (value.length <= param[0]) {

                        var check = false;
                        var json = {};
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'act/selectFormInfo.do',
                            data: {unkey: value},
                            async: false,
                            success: function(data) {

                                json = data;
                            },
                            error: function() {

                                json = null;
                            }
                        });

                        if(json == null) {
                            $.fn.validatebox.defaults.rules.validUnkey.message = '表单key检验错误！';
                            return false;
                        } else if (json.form != null && json.form.id != '${param.id }') {
                            $.fn.validatebox.defaults.rules.validUnkey.message = '表单key重复, 请重新输入！';
                            return false;
                        }

                        return true;

                    } else {
                        $.fn.validatebox.defaults.rules.validUnkey.message = '最多可输入{0}个字符!';
                        return false;
                    }
                    return true;
                },
                message: ''
            }
        });
    });

</script>
<br/>
<form>
    <span>电脑版表单编辑 > <b>电脑版表单保存</b> > 手机版表单保存<br/><br/></span>
    <div style="border-top: dashed 1px #ddd; height: 10px;"></div>

    <table style="width: 100%;">
        <tr>
            <td style="width: 60px;" class="left">表单Key：</td>
            <td>
                <input class="easyui-validatebox" id="activiti-unkey-formPreviewPage2" validType="validUnkey[50]" data-options="required:true" style="width: 90%" value="${param.unkey }"/>
            </td>
            <td class="len80 right">表单标题：</td>
            <td>
                <input class="easyui-validatebox" id="activiti-name-formPreviewPage2" validType="maxByteLength[100]" data-options="required:true" style="width: 90%" value="${param.name }"/>
            </td>
            <td class="len80 right">表单类型：</td>
            <td>
                <select id="activiti-type-formPreviewPage2" style="width: 80%;" data="${param.type }">
                    <option></option>
                    <c:forEach items="${list }" var="item">
                        <option value="${item.unkey}">${item.name }</option>
                    </c:forEach>
                </select>
            </td>
            <td class="len80 right">表单描述：</td>
            <td>
                <input class="easyui-validatebox" id="activiti-intro-formPreviewPage2" validType="maxByteLength[100]" data-options="required:false" style="width: 90%" value="${param.intro }"/>
            </td>
        </tr>
    </table>
</form>
<form id="activiti-exportForm-formPreviewPage2" action="act/exportForm.do" target="" method="post">
    <div class="nodisplay">
        <input name="title" value=""/>
        <textarea name="html"><c:out value="${template }"/></textarea>
    </div>
</form>
<form name="backform" action="act/formDesignPage2.do?key=${param.unkey }&name=${param.name }&intro=${param.intro }" method="post">
    <input type="hidden" name="unkey" value="${param.unkey }"/>
    <input type="hidden" name="name" value="${param.name }"/>
    <input type="hidden" name="intro" value="${param.intro }"/>
    <input type="hidden" name="type" value="${param.type }"/>
    <input type="hidden" name="fieldnum" value="0"/>
    <div style="display: none;">
        <textarea name="html"><c:out value="${template }"/></textarea>
    </div>
</form>
<form name="phoneform" action="act/formDesignPage3.do" method="post">
    <textarea id="activiti-phonehtml-formPreviewPage2" name="phonehtml" style="display: none;"></textarea>
    <input type="hidden" id="activiti-formname-formPreviewPage2" name="formname"/>
    <input type="hidden" id="activiti-formkey-formPreviewPage2" name="formkey" value="${param.unkey }"/>
</form>
<hr/>
<iframe id="viewframe" name="viewframe" width="100%" height="200px;" frameborder="0" src="act/formPreviewPage3.do?flag=1&unkey=${param.key }&name=${param.name }&intro=${param.intro }">
</iframe>
<hr/>
<div id="activiti-dummy-formPreviewPage2" class="nodisplay">
    ${html }
</div>
<table cellspacing="0" id="activiti-fieldlist-formPreviewPage2" class="itemlist">
    <tr style="background: #b0cce0;">
        <th style="width: 5%;">&nbsp;</th>
        <th style="width: 20%;">项目ID(自动生成)</th>
        <th style="width: 25%;">项目名称</th>
        <th style="width: 10%;">项目类型</th>
        <th>备注</th>
    </tr>
</table>
<table style="width: 100%">
    <tr class="tips">
        <td align="center">
            <font color="red">存在名称相同的控件，请在项目一览中查看详细信息！</font>
        </td>
    </tr>
    <tr>
        <td align="center" colspan="6">
            <div>
                <input type="button" id="activiti-back-formPreviewPage2" name="back" style="margin: 10px;" value=" 返回 "/>
                <input type="button" id="activiti-save-formPreviewPage2" name="save" style="margin: 10px;" value=" 保存 " disabled="disabled"/>
                <input type="button" id="activiti-export-formPreviewPage2" name="export" style="margin: 10px;" value=" 导出 " disabled="disabled"/>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
