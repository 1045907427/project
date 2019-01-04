<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>处理工作</title>
    <%@include file="/phone/common/include.jsp"%>
    <script src="activiti/js/sha1.js"></script>
    <script type="text/javascript">

        var DEFAULT_FIX = 2;    // 默认保留小数位

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

        // 字符串编码
        String.prototype.encodeHtml = function() {

            var s = '';
            var str = this || '';

            if (str.length == 0) {

                return s;
            }
            s = str.replace(/&/g, "&amp;");
            s = s.replace(/</g, "&lt;");
            s = s.replace(/>/g, "&gt;");
            s = s.replace(/ /g, "&nbsp;");
            s = s.replace(/\'/g, "&#39;");
            s = s.replace(/\"/g, "&quot;");
            s = s.replace(/\//g, "&#47;");
            s = s.replace(/\\/g, "&#92;");
            s = s.replace(/\n/g, "<br>");
            return s;
        };

        // 字符串解码
        String.prototype.decodeHtml = function() {

            var s = '';
            var str = this || '';

            if (str.length == 0) {

                return s;
            }

            s = str;
            do {
                var src = s;
                s = s.replace(/<br>/g, "\n");
                s = s.replace(/&#92;/g, "\\");
                s = s.replace(/&#47;/g, "\/");
                s = s.replace(/&quot;/g, "\"");
                s = s.replace(/&#39;/g, "\'");
                s = s.replace(/&nbsp;/g, " ");
                s = s.replace(/&gt;/g, ">");
                s = s.replace(/&lt;/g, "<");
                s = s.replace(/&amp;/g, "&");
            } while (s != src);
            return s;
        };

        // cval方法执行时，同时执行val()和change()
        $.fn.cval = function(v) {

            if(v == undefined) {
                return $(this).val();
            }

            if(/*$(this).attr('disabled') == 'disabled' || */$(this).css('display') == 'none') {
                return ;
            }

            if(v == '') {
                v = 0;
            }

            var fix = DEFAULT_FIX;
            if($(this).attr('orgfix')) {
                fix = $(this).attr('orgfix');
            }

            var old = $(this).val();
            v = parseFloat(v).toFixed(fix);
            $(this).val(v);
            if(v != old) {
                $(this).change();
            }
            return $(this).val();
        };

        // 对Date的扩展，将 Date 转化为指定格式的String
        // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
        // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
        // 例子：
        // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
        // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
        Date.prototype.Format = function(fmt) {

            var o = {
                "M+" : this.getMonth()+1,                 //月份
                "d+" : this.getDate(),                    //日
                "h+" : this.getHours(),                   //小时
                "m+" : this.getMinutes(),                 //分
                "s+" : this.getSeconds(),                 //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S"  : this.getMilliseconds()             //毫秒
            };

            if(/(y+)/.test(fmt)) {

                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            }

            for(var k in o) {
                if(new RegExp("("+ k +")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                }
            }

            return fmt;
        };

        /**
         * 小写数字转大写数字
         * @param n
         * @returns {string}
         * @constructor
         */
        function capitalizeNumber(n) {
            if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
                return "数据非法";
            var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
            n += "00";
            var p = n.indexOf('.');
            if (p >= 0)
                n = n.substring(0, p) + n.substr(p + 1, 2);
            unit = unit.substr(unit.length - n.length);
            for (var i = 0; i < n.length; i++)
                str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
            return str.replace(/零(仟|佰|拾|角)/g, "零")
                .replace(/(零)+/g, "零")
                .replace(/零(万|亿|元)/g, "$1")
                .replace(/(亿)万|壹(拾)/g, "$1$2")
                .replace(/^元零?|零分/g, "")
                .replace(/元$/g, "元整")
                .replace(/角$/, '角整');
        };

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

        var items = $.parseJSON('${items }');
        var rules = $.parseJSON('${rules }');
        var rulesObj = {};

        var editor = {macro: {}, wdate: {}};
        var macro = $.parseJSON('${macro }');
        var wdate = $.parseJSON('${wdate }');
        editor.macro = macro;
        editor.wdate = wdate;

        var functions = new Array();
        var tempFunction = function(){};

        var events = {};            // 项目事件

        var ja = $.parseJSON('${json }' || '[]');   // json array
        var jo = {};                                // json object

        // json array → json object
        (function(){

            for(var i in ja) {

                var element = ja[i];
                var itemid = element.agentitemid;
                jo[itemid] = element;
            }

        })();

        (function(){

            <c:choose>
                <c:when test="${param.type eq 'handle'}">

                for(var i in items) {

                    var item = items[i];
                    var itemid = item.itemid;
                    var regex = new RegExp(itemid);
                    if(!regex.test('${rules }')) {

                        var rule = {};
                        rule.itemid = itemid;
                        rule.itemname = items[i].itemname;
                        rule.writable = 1;
                        rule.visible = 1;
                        rule.required = 0;

                        rules.push(rule);
                    }
                }

                // array → object
                for(var i in rules) {

                    var rule = rules[i];
                    var itemid = rule.itemid;
                    rulesObj[itemid] = rule;
                }

                // 根据rules对页面做控制
                $(document).on('pagebeforecreate', function(e){

                    initForm($.parseJSON('${json }' || '[]'));
                    initEvent();

                    var $form = $('#activiti-form-workFormPage');

                    for(var i in rules) {

                        var rule = rules[i];
                        var $d = $('[agentitemid=' + rule.itemid + ']');
                        if($d.length > 0) {

                            if(rule.visible == '0') {

                                if($d.is('input')) {

                                    $d.css({display: 'none'});
                                    $d.before('<font size="0.6em">(不可见)</font>');
                                    $d.after('<input type="text" value="***" readonly="readonly"/>');
                                    $form.append($d[0].outerHTML.replace(/type=\"text\"/g, 'type="hidden"'));

                                    $d.remove();

                                } else if($d.is('textarea')) {

                                    $d.css({display: 'none'});
                                    $d.before('<font size="0.6em">(不可见)</font>');
                                    $d.after('<input type="text" value="***" readonly="readonly"/>');

                                    $form.append($d[0].outerHTML);
                                    $d.remove();

                                } else if($d.is('select')) {

                                    $d.css({display: 'none'});
                                    $d.before('<font size="0.6em">(不可见)</font>');
                                    $d.after('<input type="text" value="***" readonly="readonly"/>');

                                    $form.append('<div style="display: none;">' + $d[0].outerHTML + '</div>');
                                    $d.remove();

                                } else if($d.is('fieldset')) {

                                    var legend = $d.find('legend').html();
                                    $d.after('<label>' + legend + '<font size="0.6em">(不可见)</font><input type="text" value="***" readonly="readonly"/></label>');
                                    $d.css({display: 'none'});
                                    $form.append('<div style="display: none;">' + $d[0].outerHTML + '</div>');
                                    $d.remove();
                                }
                            } else {

                                var plugins = $d.attr('plugins') || '';
                                var orgtype = $d.attr('orgtype') || '';

                                if($d.is('input[type=text]') && (plugins == 'compute' || orgtype == 'int' || orgtype == 'float')) {

                                    $d.attr('type', 'number');
                                }
                            }

                            if(rule.visible == '1' && rule.writable == '0') {

                                if($d.is('input')) {

                                    $d.attr('disabled', 'disabled');

                                } else if($d.is('textarea')) {

                                    $d.attr('disabled', 'disabled');

                                } else if($d.is('select')) {

                                    var item = $d.attr('agentitemid');
                                    $d.css({display: 'none'});
//                                    $form.append($d[0].outerHTML);
                                    $d.css({display: ''});
                                    $d.attr('disabled', 'disabled');
//                                    $d.removeAttr('agentitemid');

                                } else if($d.is('fieldset')) {

                                    var item = $d.attr('agentitemid');
                                    $d.css({display: 'none'});
                                    $form.append($d[0].outerHTML);
                                    $d.css({display: ''});
                                    $d.removeAttr('agentitemid');
                                    $d.find('input').attr('disabled', 'disabled');
                                }
                            }

                            if(rule.visible == '1' && rule.writable == '1' && rule.required == '1') {

                                $d.addClass('required');
                            }

                            // 设定宏控件初始值
                            if($d.attr('plugins') == 'macros' && rule.visible == '1' && rule.writable == '1') {

                                var init = $d.attr('data') || '';
                                var initVal = eval(init);
                                var inited = $d.attr('inited') || '';
                                var agentitemid = $d.attr('agentitemid') || '';
                                var valObj = searchVal(agentitemid);

                                $d.attr('donotinit', '1');

                                if(valObj && valObj.inited == 'inited') {

                                    if(init.indexOf('editor.macro.sys_signature') == 0) {

                                        $d.val(valObj.vals);
                                        $d.attr('inited', 'inited');
                                        $d.attr('readonly', 'readonly');
                                    }

                                    if(inited == '') {
                                        $d.val(valObj.vals);
                                        $d.attr('inited', 'inited');
                                    }
                                } else {

                                    if(init.indexOf('editor.macro.sys_signature') == 0) {

                                        $d.val(initVal);
                                        $d.attr('inited', 'inited');
                                        $d.attr('readonly', 'readonly');
                                    }

                                    if(inited == '') {
                                        $d.val(initVal);
                                        $d.attr('inited', 'inited');
                                    }
                                }
                            }

                            // 初始化My97DatePicker控件
                            if($d.attr('plugins') == 'wdate' && rule.visible == '1' && rule.writable == '1') {

                                var init = $d.attr('data') || '';
                                $d.attr('readonly', 'readonly');
                                var agentitemid = $d.attr('agentitemid') || '';
                                var valObj = searchVal(agentitemid);

                                var mindate = $d.attr('mindate');
                                var maxdate = $d.attr('maxdate');
                                var fmt = $d.attr('orgfmt') || '';
                                fmt = eval(fmt);

                                $d.attr('donotinit', '1');

                                if(valObj && valObj.inited == 'inited') {

                                    if(init.indexOf('editor.macro.sys_signature') == 0) {

                                        $d.val(valObj.vals);
                                        $d.attr('inited', 'inited');
                                        $d.attr('readonly', 'readonly');
                                    }

                                    if(inited == '') {
                                        $d.val(valObj.vals);
                                        $d.attr('inited', 'inited');
                                    }

                                } else {

                                    var initvalue = $d.attr('data') || '';
                                    if(initvalue != '') {

                                        initvalue = eval(initvalue);
                                        initvalue = new Date(Date.parse(initvalue.replace(/-/g, "/"))).Format(fmt);
                                        $d.val(initvalue);
                                        $d.attr('inited', 'inited');
                                    } else {

                                        initvalue = new Date().Format(fmt)
                                    }
                                }

                                var funIndex = functions.length;
                                eval('functions[funIndex]=function(data){$("[agentitemid=' + rule.itemid + ']").val(data);$("[agentitemid=' + rule.itemid + ']").blur();}');

                                $(document).on('click', '[agentitemid=' + rule.itemid + ']', {idx: funIndex, initval: $d.val() || initvalue, fmt: fmt}, function(e) {

                                    tempFunction = eval(functions[e.data.idx]);
                                    androidDateWidget(e.data.initval, e.data.fmt, 'tempFunction');
                                });

                            }

                            // 初始化参照窗口事件绑定
                            if($d.attr('plugins') == 'widget' && rule.visible == '1' && rule.writable == '1') {

                                var refid = $d.attr('refid') || '';
                                var idcol = $d.attr('widgetvaluecol') || '';
                                var namecol = $d.attr('widgettextcol') || '';

                                var funIndex = functions.length;

                                var widgetmap = $d.attr('widgetmap') || '';
                                var mapArray = widgetmap.split(',');
                                var script = 'functions[funIndex]=function(data){' +
                                        '$("[agentitemid=' + rule.itemid + ']").val(data.' + idcol + '+":"+data.' + namecol + ');' +
                                        '$("[agentitemid=' + rule.itemid + ']").attr("widgetselectval", data.' + idcol + ');' +
                                        '$("[agentitemid=' + rule.itemid + ']").attr("widgetselecttext", data.' + namecol + ');try{';

                                if(widgetmap != '') {

                                    for(var i in mapArray) {

                                        var tempMap = mapArray[i].split("=");
                                        var key = tempMap[0];
                                        var dest = tempMap[1];
                                        script = script + '$("[agentitemid=' + dest + ']").val(data.' + key + ' || "");';
                                    }
                                }

                                script = script + '}catch(e){alert("控件出错");}$("[agentitemid=' + rule.itemid + ']").blur();}';
                                eval(script);

                                $(document).off('click', '[agentitemid=' + rule.itemid + ']').on('click', '[agentitemid=' + rule.itemid + ']', {idx: funIndex, refid: refid}, function(e) {

                                    tempFunction = eval(functions[e.data.idx]);

                                    androidWidget({
                                        type: 'widget',
                                        referwid: e.data.refid,
                                        onSelect: 'tempFunction'
                                    });
                                });
                            }

                            // 商品
                            if($d.attr('plugins') == 'goods' && rule.visible == '1' && rule.writable == '1') {

                                var refid = $d.attr('refid') || '';
                                var idcol = $d.attr('widgetvaluecol') || '';
                                var namecol = $d.attr('widgettextcol') || '';

                                var funIndex = functions.length;

                                var widgetmap = $d.attr('widgetmap') || '';
                                var mapArray = widgetmap.split(',');
                                var script = 'functions[funIndex]=function(data){' +
                                    '$("[agentitemid=' + rule.itemid + ']").val(/*data.id+":"+*/data.name);' +
                                    '$("[agentitemid=' + rule.itemid + ']").attr("widgetselectval", data.id);' +
                                    '$("[agentitemid=' + rule.itemid + ']").attr("widgetselecttext", data.name);try{';

                                if(widgetmap != '') {

                                    for(var i in mapArray) {

                                        var tempMap = mapArray[i].split("=");
                                        var key = tempMap[0];
                                        var dest = tempMap[1];
                                        script = script + '$("[agentitemid=' + dest + ']").val(data.' + key + ' || "");';
                                    }
                                }

                                script = script + '}catch(e){alert("控件出错");}$("[agentitemid=' + rule.itemid + ']").blur();}';
                                eval(script);

                                $(document).off('click', '[agentitemid=' + rule.itemid + ']').on('click', '[agentitemid=' + rule.itemid + ']', {idx: funIndex}, function(e) {

                                    tempFunction = eval(functions[e.data.idx]);

                                    androidWidget({
                                        type: 'goodsWidget',
                                        onSelect: 'tempFunction'
                                    });
                                });
                            }

                            // 客户
                            if($d.attr('plugins') == 'customer' && rule.visible == '1' && rule.writable == '1') {

                                var refid = $d.attr('refid') || '';
                                var idcol = $d.attr('widgetvaluecol') || '';
                                var namecol = $d.attr('widgettextcol') || '';

                                var funIndex = functions.length;

                                var widgetmap = $d.attr('widgetmap') || '';
                                var mapArray = widgetmap.split(',');
                                var script = 'functions[funIndex]=function(data){' +
                                    '$("[agentitemid=' + rule.itemid + ']").val(/*data.id+":"+*/data.name);' +
                                    '$("[agentitemid=' + rule.itemid + ']").attr("widgetselectval", data.id);' +
                                    '$("[agentitemid=' + rule.itemid + ']").attr("widgetselecttext", data.name);try{';

                                if(widgetmap != '') {

                                    for(var i in mapArray) {

                                        var tempMap = mapArray[i].split("=");
                                        var key = tempMap[0];
                                        var dest = tempMap[1];
                                        script = script + '$("[agentitemid=' + dest + ']").val(data.' + key + ' || "");';
                                    }
                                }

                                script = script + '}catch(e){alert("控件出错");}$("[agentitemid=' + rule.itemid + ']").blur();}';
                                eval(script);

                                $(document).off('click', '[agentitemid=' + rule.itemid + ']').on('click', '[agentitemid=' + rule.itemid + ']', {idx: funIndex}, function(e) {

                                    tempFunction = eval(functions[e.data.idx]);

                                    androidWidget({
                                        type: 'customerWidget',
                                        onSelect: 'tempFunction'
                                    });
                                });
                            }

                            // 数字控件
                            if($d.attr('plugins') == 'numeric' && rule.visible == '1' && rule.writable == '1') {

                                $d.change(function(){

                                    var newVal = $(this).val();// ^0|[1-9][0-9]*$;
                                    var oldVal = $(this).attr('oldVal') ^0|[1-9][0-9]*$;

                                    if(/^[0-9]*(\.\d+){0,1}$/g.test(newVal)) {
                                        $(this).attr('oldVal', parseFloat(newVal).toFixed(2));
                                    } else {
                                        newVal = parseFloat(oldVal);
                                    }

                                    $(this).val(capitalizeNumber(newVal));
                                });

                            }
                        }
                    }

                });

                </c:when>
                <c:otherwise>
                    $(document).on('pagebeforecreate', function(e) {
                        initForm($.parseJSON('${json }' || '[]'));
                        $('input[type=text],input[type=radio],input[type=checkbox],select,textarea').each(function() {

                            $(this).attr('readonly', 'readonly');
                            $(this).attr('disabled', 'disabled');
                        });
                    });
                </c:otherwise>
            </c:choose>

        })();

        <c:if test="${param.type eq 'handle'}">

        /**
         *
         */
        function parseForm() {

            var datas = new Array();    // 用于parseform

            var check = checkForm();
            if(!check) {

                return [];
            }

            $('input[type=number]').attr('type', 'text');

            $('#activiti-form-workFormPage [agentitemid]').each(function(index, item) {

                var $d = $(this);

                // checkboxs and radios
                if($d.is('fieldset')) {

                    var title = $d.find('legend').html();
                    var itemid = $d.attr('agentitemid');
                    var type = '';
                    var vals = new Array();

                    $d.find('input[type=radio],input[type=checkbox]').each(function(index2, item2) {

                        if($(this).is(':checked')) {

                            vals.push({val: $(this).attr('value'), text: $(this).attr('text')});
                        }
                        type = type || ($(this).attr('type') + 's');
                    });

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: type,
                        vals: vals,
                        taskkey: '${process.taskkey }'
                    });

                    // widget
                } else if($d.is('input[plugins=widget]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text][plugins=widget]',
                        vals: {value: $d.attr('widgetselectval') || '', text: $d.attr('widgetselecttext') || ''},
                        taskkey: '${process.taskkey }'
                    });

                } else if($(this).is('input[type=text][plugins=goods]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');
                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text][plugins=goods]',
                        vals: {value: $d.attr('widgetselectval') || '', text: $d.attr('widgetselecttext') || ''},
                        taskkey: '${process.taskkey }'
                    });
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text][plugins=customer]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');
                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text][plugins=customer]',
                        vals: {value: $d.attr('widgetselectval') || '', text: $d.attr('widgetselecttext') || ''},
                        taskkey: '${process.taskkey }'
                    });
                    $(this).attr('value', $(this).val().encodeHtml());

                    // compute
                } else if($(this).is('input[type=text][plugins=compute]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text][plugins=compute]',
                        vals: $d.val(),
                        taskkey: '${process.taskkey }'
                    });

                    // text
                } else if($(this).is('input[type=text][plugins=numeric]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text][plugins=numeric]',
                        vals: $d.val(),
                        taskkey: '${process.taskkey }'
                    });

                    // text
                } else if($(this).is('input[type=text]')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'input[type=text]',
                        vals: $d.val(),
                        inited: $d.attr('inited') || '',
                        taskkey: '${process.taskkey }'
                    });

                    // textarea
                } else if($(this).is('textarea')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'textarea',
                        vals: $d.val().encodeHtml(),
                        taskkey: '${process.taskkey }'
                    });

                    // select
                } else if($(this).is('select')) {

                    var title = $d.attr('title');
                    var itemid = $d.attr('agentitemid');

                    datas.push({
                        title: title,
                        agentitemid: itemid,
                        type: 'select',
                        vals: $d.val(),
                        taskkey: '${process.taskkey }'
                    });
                }
            });

            return datas;
        }

        /**
         * check form
         */
        function checkForm() {

            // orgtype, 添加jQueryValidate验证规则
            // text
            // email
            // int
            // float
            $('#activiti-form-workFormPage').find('input[type=text][orgtype]').each(function(index, item) {

                var type = $(this).attr('orgtype') || '';

                // email
                if(type == 'email') {

                    $(this).addClass('email');

                    // int
                } else if(type == 'int') {

                    $(this).addClass('digits');

                    // float
                } else if(type == 'float') {

                    $(this).addClass('number');
                }
            });

            $('#activiti-form-workFormPage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#activiti-form-workFormPage").submit();
            var flag = $('#activiti-form-workFormPage').validate().form();

            $('#activiti-form-workFormPage').validate({
                debug: false
            });

            return flag;
        }

        /**
         * 初始化compute控件事件
         */
        function initEvent() {

            $('input[plugins=compute]').each(function(index, item) {

                var relate = $(this).attr('expressionrelate') || '';
                var itemid = $(this).attr('agentitemid');
                var fun = $(this).attr('expression') || '';

                $(this).blur(function() {

                    var v = $(this).val();

                    var regex = /^-?[0-9]+(.[0-9]{1,})?$/g;

                    if(regex.test(v)) {

                        $(this).cval(v);
                        return ;
                    }
                    $(this).cval('');
                });

                if(fun != '') {

                    fun = fun.replace(/[\u002c]/gi, '');

                    if($(this).val() == '') {
                        $(this).cval(eval(fun));
                    }

                    eval('fun = function (){$("input[agentitemid=' + itemid + ']").cval(' + fun + ');}');
                }

                if(relate != '') {

                    relate = relate.split(',');

                    $.each(relate,  function(index, value) {

                        if(value != '') {

                            var agentid = hex_sha1(value);
                            if(events[agentid] == undefined || events[agentid] == null) {
                                events[agentid] = new Array();
                            }
                            events[agentid].push(fun);
                        }
                    });
                }
            });

            //
            for(var key in events) {

                var funs = events[key];
                for(var i in funs) {
                    var fun = funs[i];
                    $('[agentitemid=' + key + ']').change(fun);
                }

            }
        }

        function toNumeric(v) {

            if(v == undefined || v == null || v == '') {
                return 0;
            }

            return parseFloat(v);
        }

        /**
         * 保存表单
         */
        function workFormSubmit(call, args) {

            var check = checkForm();

            if(!check) {

                return false;
            }

            var data = parseForm();

            $.ajax({
                type: 'post',
                url: 'act/updateNewWork.do',
                data: {
                    'json': JSON.stringify(data),
                    'process.definitionkey': '${process.definitionkey }',
                    'type': '',
                    'process.id': '${param.processid }',
                    'process.title': '${fn:replace(title, "\'", "\\\'")}'
                },
                dataType: 'json',
                success: function(json) {

                    if(json.flag) {

                        call(json.backid);
                    }
                },
                error: function() {}
            });
        }

        </c:if>

        /**
         * 根据data初始化form
         * @param data
         */
        function initForm(data) {

            $.each(data, function(index, item) {

                if(item.type == 'textarea') {

                    $('[agentitemid=' + item.agentitemid + ']').text(item.vals.decodeHtml());

                } else if(item.type == 'select') {

                    $('[agentitemid=' + item.agentitemid + ']').val(item.vals);
                    $('[agentitemid=' + item.agentitemid + '] option[text="' + item.vals + '"]').attr('selected', 'selected');

                } else if(item.type == 'input[type=text][plugins=widget]') {

                    if(item.vals.value) {

                        $('[agentitemid=' + item.agentitemid + ']').val(item.vals.value + ':' + item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselectval', item.vals.value.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselecttext', item.vals.text.decodeHtml());
                    }

                } else if(item.type == 'input[type=text][plugins=goods]') {

                    if(($('[agentitemid=' + item.agentitemid + ']').val() || '') == '') {

                        $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselectval', item.vals.value.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselecttext', item.vals.text.decodeHtml());
                    }

                } else if(item.type == 'input[type=text][plugins=customer]') {

                    if(($('[agentitemid=' + item.agentitemid + ']').val() || '') == '') {

                        $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselectval', item.vals.value.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('widgetselecttext', item.vals.text.decodeHtml());
                    }

                } else if(item.type == 'radios' || item.type == 'checkboxs') {

                    var itemid = item.agentitemid;
                    $.each(item.vals, function(index2, item2) {

                        $('fieldset[agentitemid=' + itemid + ']').find('input[type=radio][value=' + item2.val + '],input[type=checkbox][value=' + item2.val + ']').attr('checked', 'checked');
                    });

                } else if(item.type == 'input[type=text][plugins=numeric]') {
                    $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                } else if(item.type == 'input[type=text]') {

                    $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').val(item.vals.decodeHtml());

                    var inited = item.inited || '';
                    if(inited != '') {

                        $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').attr('inited', 'inited');
                    }
                } else if(item.type == 'input[type=text][plugins=compute]') {

                    $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').val(item.vals.decodeHtml());

                    var inited = item.inited || '';
                    if(inited != '') {

                        $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').attr('inited', 'inited');
                    }
                } else if(item.type == 'select') {

                    $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').val(item.vals.decodeHtml());

                    var inited = item.inited || '';
                    if(inited != '') {

                        $('[agentitemid=' + item.agentitemid + '][donotinit!=1]').attr('inited', 'inited');
                    }
                }
            });
        }

        /**
         *
         * @param agentitemid
         * @returns {*}
         */
        function searchVal(agentitemid) {

            for(var i in jo) {

                var obj = jo[i];
                if(obj.agentitemid == agentitemid) {
                    return obj;
                }
            }

            return null;
        }

    </script>
</head>
<body>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>
            <c:choose>
                <c:when test="${param.type eq 'handle'}">
                    处理工作
                </c:when>
                <c:otherwise>
                    查看工作
                </c:otherwise>
            </c:choose>
            [${param.processid }]
        </h1>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <%-- 表单html代码 --%>
    <form id="activiti-form-workFormPage">
        ${phonehtml }
    </form>
    </br>
    </br>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-workFormPage" data-role="footer" data-position="fixed">
            <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp">
                <jsp:param name="taskid" value="${taskid }"/>
                <jsp:param name="id" value="${param.processid }"/>
                <jsp:param name="sign" value="${param.sign }"/>
            </jsp:include>
        </div>
    </c:if>
</div>
</body>
</html>
