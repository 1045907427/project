<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>在线表单正文页面</title>
    <script type="text/javascript" src="../activiti/js/jquery.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/ecmascript" src="../activiti/js/sha1.js"></script>
    <style type="text/css">
        table {
            border-collapse: collapse;
            /*border:1px solid #7babcf;*/
        }
    </style>
    <style>
        html,body{
            /*height:100%;*/
            /*width:100%;*/
            padding:0;
            margin:0;
        }
        #preview{
            /*width:100%;*/
            /*height:100%;*/
            padding:0;
            margin:0;
        }
        #preview *{font-family:sans-serif;/*font-size:16px;*/}
    </style>
    <style id="table">
        #preview table.noBorderTable td,#preview table.noBorderTable th,#preview table.noBorderTable caption{border:1px dashed #ddd !important}#preview table.sortEnabled tr.firstRow th,#preview table.sortEnabled tr.firstRow td{padding-right:20px; background-repeat: no-repeat;background-position: center right; background-image:url(../../themes/default/images/sortable.png);}#preview table.sortEnabled tr.firstRow th:hover,#preview table.sortEnabled tr.firstRow td:hover{background-color: #EEE;}#preview table{margin-bottom:10px;border-collapse:collapse;display:table;}#preview td,#preview th{ background:white; padding: 5px 10px;border: 1px solid #DDD;}#preview caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}#preview th{border-top:1px solid #BBB;background:#F7F7F7;}#preview table tr.firstRow th{border-top:2px solid #BBB;background:#F7F7F7;}#preview tr.ue-table-interlace-color-single td{ background: #fcfcfc; }#preview tr.ue-table-interlace-color-double td{ background: #f7faff; }#preview td p{margin:0;padding:0;}
        #preview table.noBorderTable td,#preview table.noBorderTable th,#preview table.noBorderTable caption{border:1px dashed #ddd !important}#preview table.sortEnabled tr.firstRow th,#preview table.sortEnabled tr.firstRow td{padding-right:20px; background-repeat: no-repeat;background-position: center right; background-image:url(../../themes/default/images/sortable.png);}#preview table.sortEnabled tr.firstRow th:hover,#preview table.sortEnabled tr.firstRow td:hover{background-color: #EEE;}#preview table{margin-bottom:10px;border-collapse:collapse;display:table;}#preview td,#preview th{ background:white; padding: 5px 10px;border: 1px solid #DDD;}#preview caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}#preview th{border-top:1px solid #BBB;background:#F7F7F7;}#preview table tr.firstRow th{border-top:2px solid #BBB;background:#F7F7F7;}#preview tr.ue-table-interlace-color-single td{ background: #fcfcfc; }#preview tr.ue-table-interlace-color-double td{ background: #f7faff; }#preview td p{margin:0;padding:0;}</style><style id="chartsContainerHeight">
        .edui-chart-container { height:500px}
        .edui-chart-container { height:500px}
    </style>
    <script type="text/javascript">

        <!--

        var inited = '0';

        // 字符串编码
        String.prototype.encodeHtml = function() {

            var s = '';
            var str = this || '';

            if (str.length == 0) {

                return s;
            }
            s = str;
            s = s.replace(/&/g, "&amp;");
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
            if(!isNaN(v)){
                v = parseFloat(v).toFixed(fix);
            }

            $(this).val(v);
            if(v != old) {
                $(this).change();
            }
            return $(this).val();
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

        var widgetOption = {};

        var editor = {macro: {}, wdate: {}};
        var macro = $.parseJSON('${macro }');
        var wdate = $.parseJSON('${wdate }');
        editor.macro = macro;
        editor.wdate = wdate;
        var datalist = new Array();

        var DEFAULT_FIX = 2;    // 默认保留小数位

        var events = {};        // 项目事件

        $(function() {

            <c:if test="${not empty json}">
                initForm($.parseJSON('${json }'));
            </c:if>

            initEvent();

            // 根据rulelist规则控制页面显示
            // 设定初始值
            <c:if test="${param.type eq 'handle'}">
            {
                var rules = $.parseJSON('${rules }');
                var items = $.parseJSON('${items }');

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

                //var rules = new Array();
                for(var i = 0; i < rules.length; i++) {

                    var rule = rules[i];
                    var $d = $('[agentitemid=' + rule.itemid + ']');

                    if($d.length == 0) {

                        continue;
                    }

                    // set visible
                    if(rule.visible == '0') {
                        $d.css({display: 'none'});
                        // 不显示的项目用“***”代替显示
                        // TODO 提交时是否需要去除该项目
                        $d.after('<font class="items_nodisplay" title="该项目不可见。">***</font>');
                    } else {
                        $d.css({display: ''});
                    }
                    // set required
                    if(rule.required == '1') {
                        $d.attr('required', '1');
                    } else {
                        $d.removeAttr('required');
                    }
                    // set writable
                    if(rule.writable == '0') {

                        // radio
                        if($d.is('span') && $d.attr('plugins') == 'radios') {

                            $d.children().attr('disabled', 'disabled');

                        //checkbox
                        } else if($d.is('span') && $d.attr('plugins') == 'checkboxs') {

                            $d.children().attr('disabled', 'disabled');

                        // input[type=text]
                        } else if($d.is('input[type=text]')) {

                            $d.attr('disabled', 'disabled');

                        // textarea
                        } else if($d.is('textarea')) {

                            $d.attr('disabled', 'disabled');
                        // select
                        } else if($d.is('select')) {

                            $d.attr('disabled', 'disabled');
                        }
                    } else {

                        // radio
                        if($d.is('span') && $d.attr('plugins') == 'radios') {

                            $d.children().removeAttr('disabled');

                            //checkbox
                        } else if($d.is('span') && $d.attr('plugins') == 'checkboxs') {

                            $d.children().removeAttr('disabled');

                            // input[type=text]
                        } else if($d.is('input[type=text]')) {

                            $d.removeAttr('disabled');

                            // textarea
                        } else if($d.is('textarea')) {

                            $d.removeAttr('disabled');
                        // select
                        } else if($d.is('select')) {

                            $d.removeAttr('disabled', 'disabled');
                        }

                    }

                    // 设定macros初始值
                    if($d.attr('plugins') == 'macros' && rule.visible == '1' && rule.writable == '1'/* && $d.attr('value') == ''*/) {

                        var init = $d.attr('data') || '';
                        var initVal = eval(init);
                        var inited = $d.attr('inited');

                        if(init.indexOf('editor.macro.sys_signature') == 0) {

                            $d.val(initVal);
                            $d.attr('inited', 'inited');
                            $d.attr('readonly', 'readonly');
                        }

                        if(inited == undefined) {
                            $d.val(initVal);
                            $d.attr('inited', 'inited');
                        }
                    }

                    // 初始化My97DatePicker控件
                    if($d.attr('plugins') == 'wdate' && rule.visible == '1' && rule.writable == '1') {

                        var onfocus = new Array();
                        var mindate = $d.attr('mindate');
                        var maxdate = $d.attr('maxdate');
                        var fmt = $d.attr('orgfmt');

                        onfocus.push("WdatePicker({'dateFmt':");
                        onfocus.push("" + fmt + "");
                        if(mindate != null && mindate != '') {

                            onfocus.push(",minDate:'" + eval(mindate) + "'");
                        }
                        if(maxdate != null && maxdate != '') {

                            onfocus.push(",maxDate:'" + eval(maxdate) + "'");
                        }
                        onfocus.push("})");

                        $d.attr('onfocus', onfocus.join(''));

                        // 初始化wdate控件默认值
                        var initvalue = $d.attr('data');

                        if(initvalue != null && initvalue != '') {

                            initvalue = eval(initvalue);

                            var fmt = $d.attr('orgfmt');
                            var initvalue = new Date(Date.parse(initvalue.replace(/-/g, "/"))).Format(fmt);

                            var inited = $d.attr('inited');
                            if(inited == undefined) {
                                $d.val(eval(initvalue));
                                $d.attr('inited', 'inited');
                            }
                        }
                    }

                    // 初始化参照窗口事件绑定
                    if($d.attr('plugins') == 'widget' && rule.visible == '1' && rule.writable == '1') {

                        $d.click(function(){

                            var refid = $(this).attr('refid');
                            var title = $(this).attr('title');
                            var itemid = $(this).attr('agentitemid');

                            var option = {
                                refid: refid,
                                title: title,
                                widget: $('input[agentitemid=' + itemid + ']').attr('plugins') || '',
                                formWidgetCallback: function(data, obj) {

                                    $('input[agentitemid=' + itemid + ']').attr('widgetselectval', data.value);
                                    $('input[agentitemid=' + itemid + ']').attr('widgetselecttext', data.text);
                                    $('input[agentitemid=' + itemid + ']').val(data.text);

                                    if(($('input[agentitemid=' + itemid + ']').attr('widgetmap') || '') == '') {

                                        return ;
                                    }

                                    var map = ($('input[agentitemid=' + itemid + ']').attr('widgetmap') || '').split(',');

                                    // 根据映射关系，对目标项目设置
                                    $.each(map, function(index, item) {

                                        var key = item.split('=')[0];
                                        var target = item.split('=')[1];

                                        if($('[agentitemid=' + target + ']').attr('plugins') != 'widget') {

                                            if($('[agentitemid=' + target + ']').attr('plugins') == 'compute') {
                                                $('[agentitemid=' + target + ']').cval(obj[key]);
                                            } else {
                                                $('[agentitemid=' + target + ']').val(obj[key]);
                                            }
                                        }
                                    });
                                }
                            };
                            widgetOption = option;

                            parent.showFormWidgetWindow(widgetOption);
                        });

                    }

                    // 初始化参照窗口事件绑定
                    if(($d.attr('plugins') == 'goods' || $d.attr('plugins') == 'customer') && rule.visible == '1' && rule.writable == '1') {

                        $d.click(function(){

                            var refid = $(this).attr('refid');
                            var title = $(this).attr('title');
                            var itemid = $(this).attr('agentitemid');

                            var option = {
                                refid: refid,
                                title: title,
                                widget: $('input[agentitemid=' + itemid + ']').attr('plugins') || '',
                                formWidgetCallback: function(data, obj) {

                                    $('input[agentitemid=' + itemid + ']').attr('widgetselectval', data.value);
                                    $('input[agentitemid=' + itemid + ']').attr('widgetselecttext', data.text);
                                    $('input[agentitemid=' + itemid + ']').val(obj[$('input[agentitemid=' + itemid + ']').attr('show')]);

                                    if(($('input[agentitemid=' + itemid + ']').attr('widgetmap') || '') == '') {

                                        return ;
                                    }

                                    var map = ($('input[agentitemid=' + itemid + ']').attr('widgetmap') || '').split(',');

                                    // 根据映射关系，对目标项目设置
                                    $.each(map, function(index, item) {

                                        var key = item.split('=')[0];
                                        var target = item.split('=')[1];

                                        if($('[agentitemid=' + target + ']').attr('plugins') != 'widget') {

                                            if($('[agentitemid=' + target + ']').attr('plugins') == 'compute') {
                                                $('[agentitemid=' + target + ']').cval(obj[key]);
                                            } else {
                                                $('[agentitemid=' + target + ']').val(obj[key]);
                                            }
                                        }
                                    });
                                }
                            };
                            widgetOption = option;

                            parent.showFormWidgetWindow(widgetOption);
                        });

                    }

                    // 数字控件
                    if($d.attr('plugins') == 'numeric' && rule.visible == '1' && rule.writable == '1') {

                        $d.change(function(){

//                            var newVal = $(this).val() ^0|[1-9][0-9]*$;
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
            } // 设定rule结束
            </c:if>

            // 切换到View模式
            <c:if test="${param.type eq 'view'}">
                {
                    $('input[type=text]').each(function() {

                        var text = $(this).val() + '&nbsp;';
                        var width = $(this).css('width');
                        var style = $(this).attr('style');
                        var align = $(this).css('text-align');

                        var span = '<span style="display:inline-block; ' + style + '">' + text + '</span>';
                        if(align != 'left' || align != 'center') {

                            span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                        }

                        $(this).after(span);
                        $(this).next().css({height: ''});
                        //$(this).after('&nbsp;' + text + '&nbsp;');
                        $(this).hide();
                    });

                    $('input[type=radio]').each(function() {

                        $(this).attr('onclick', 'javascript:return false;');
                    });
                    $('input[type=checkbox]').each(function() {

                        $(this).attr('onclick', 'javascript:return false;');
                    });

                    $('textarea').each(function() {

                        var text = $(this).val() + '&nbsp;';
                        var width = $(this).css('width');
                        var style = $(this).attr('style');
                        var align = $(this).css('text-align');

                        var span = '<span style="display:inline-block;  ' + style + '">' + text + '</span>';
                        if(align != 'left' || align != 'center') {

                            span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                        }

                        $(this).after(span);
                        $(this).next().css({height: ''});
                        //$(this).after('&nbsp;' + text + '&nbsp;');
                        $(this).hide();

                        /*
                        var text = $(this).text();
                        $(this).after(' ' + text + ' ');
                        $(this).hide();
                        */
                    });
                    $('select').each(function() {

                        var text = $(this).children('[selected]').text();
                        var width = $(this).css('width');
                        var style = $(this).attr('style');
                        var align = $(this).css('text-align');

                        var span = '<span style="display:inline-block;  ' + style + '">' + text + '</span>';
                        if(align != 'left' || align != 'center') {

                            span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                        }

                        $(this).after(span);
                        $(this).next().css({height: ''});
                        //$(this).after('&nbsp;' + text + '&nbsp;');
                        $(this).hide();

                        /*
                        var text = $(this).children('[selected]').text();
                        $(this).after(' ' + text + ' ');
                        $(this).hide();
                        */
                    });
                }
            </c:if>

            $('body').children().css({display: 'block'});

            // 设定父页面iframe高度
            parent.setFrameHeight($('body')[0].clientHeight);
        });

        // parse form
        function parseForm() {

            // check form
            var check = checkForm();
            if(!check) {

                return false;
            }

            $('.items_nodisplay').remove();

            $('form#activiti-form-workFormContentPage [agentitemid]').each(function() {

                if($(this).attr('plugins') == 'radios') {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), type: 'radios', taskkey: '${process.taskkey }'};
                    var vals = new Array();
                    $(this).children().each(function() {

                        var checked = $(this)[0].checked;
                        $(this).removeAttr('checked');
                        if(checked) {

                            vals.push({val: $(this).attr('value'), text: $(this).attr('text')});
                            $(this).attr('checked', 'checked');
                        }
                    });
                    content.vals = vals;

                    datalist.push(content);

                } else if($(this).attr('plugins') == 'checkboxs') {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), type: 'checkboxs', taskkey: '${process.taskkey }'};
                    var vals = new Array();
                    $(this).children().each(function() {

                        var checked = $(this)[0].checked;
                        $(this).removeAttr('checked');
                        if(checked) {

                            vals.push({val: $(this).attr('value'), text: $(this).attr('text')});
                            $(this).attr('checked', 'checked');
                        }
                    });
                    content.vals = vals;

                    datalist.push(content);

                } else if($(this).is('input[type=text][plugins=widget]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: {value: $(this).attr('widgetselectval') == undefined ? '' : $(this).attr('widgetselectval'), text: $(this).attr('widgetselecttext') == undefined ? '' : $(this).attr('widgetselecttext').encodeHtml()}, type: 'input[type=text][plugins=widget]', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text][plugins=goods]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: {value: $(this).attr('widgetselectval') == undefined ? '' : $(this).attr('widgetselectval'), text: $(this).attr('widgetselecttext') == undefined ? '' : $(this).attr('widgetselecttext').encodeHtml()}, type: 'input[type=text][plugins=goods]', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text][plugins=customer]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: {value: $(this).attr('widgetselectval') == undefined ? '' : $(this).attr('widgetselectval'), text: $(this).attr('widgetselecttext') == undefined ? '' : $(this).attr('widgetselecttext').encodeHtml()}, type: 'input[type=text][plugins=customer]', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text][plugins=compute]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: $(this).val().encodeHtml(), type: 'input[type=text][plugins=compute]', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text][plugins=numeric]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: $(this).val(), type: 'input[type=text][plugins=numeric]', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('input[type=text]')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: $(this).val().encodeHtml(), type: 'input[type=text]', inited: $(this).attr('inited') || '', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('value', $(this).val().encodeHtml());

                } else if($(this).is('textarea')) {

                    var v = $(this).val().encodeHtml();

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: v, type: 'textarea', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                    $(this).attr('text', v);

                } else if($(this).is('select')) {

                    var content = {title: $(this).attr('title'), agentitemid: $(this).attr('agentitemid'), vals: $(this).val(), type: 'select', taskkey: '${process.taskkey }'};
                    datalist.push(content);
                }
            });

            return {
                data: datalist,
                html: $('form#activiti-form-workFormContentPage')[0].innerHTML
            };

        }

        // check form
        function checkForm() {

            var check = true;
            var chkReg = '';
            var item = '';

            $('[agentitemid]').each(function() {

                item = $(this).attr('title');
                if($(this).attr('plugins') == 'radios' || $(this).attr('plugins') == 'checkboxs') {

                    var required = false;
                    var checked = 0;
                    if($(this).attr('required') == 'required') {

                        required = true;
                    }

                    if(required) {

                        $(this).children().each(function() {

                            if($(this)[0].checked) {
                                checked ++;
                                return false;
                            }
                        });

                        if(checked == 0) {

                            check = false;
                            return false;
                        }

                    }

                } else if($(this).is('input[type=text]')) {

                    var required = false;
                    if($(this).attr('required') == 'required') {

                        required = true;
                    }
                    if(required) {

                        if($(this).val() == '') {

                            check = false;
                            return false;
                        }
                    }

                    //var regex = $(this).attr('regex');
                    //var replaceRegex = $(this).attr('replaceRegex');
                    var regType = $(this).attr('orgtype');

                    var regex = '';
                    var replaceRegex = '';
                    if(regType == 'email') {
                        regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                    } else if(regType == 'int') {
                        regex = '^[0-9]*$';
                        replaceRegex = '^[0-9]+(.[0-9]{1,})?$';
                    } else if(regType == 'float') {
                        regex = '^[0-9]+(.[0-9]*)?$';
                    }

                    // 正则表达式为空或者内容为空时不做验证
                    if(regex == undefined || regex == null || regex == '' || $(this).val() == '') {
                    } else {

                        var v = $(this).val();
                        regex = new RegExp(regex);
                        if(!regex.test(v)) {

                            chkReg = '普通文本';
                            if(regType == 'email') {
                                chkReg = '电子邮箱地址';
                            } else if(regType == 'int') {
                                //chkReg = '整数';
                                // 判断是否为小数，如果是小数，则转换为整数
                                replaceRegex = RegExp(replaceRegex);
                                if(replaceRegex.test(v)) {

                                    v = Math.round(v);
                                    $(this).val(v);
                                    chkReg = '';
                                } else {

                                    chkReg = '整数';
                                }
                            } else if(regType == 'float') {
                                chkReg = '小数';
                            }

                            if(chkReg == '') {
                            } else {
                                return false;
                            }
                        }
                    }


                }/* else if($(this).is('input[type=text]') && $(this).attr('plugins') == 'compute') {

                    var regex = '^[0-9]+(.[0-9]*)?$';
                    regex = new RegExp(regex);
                    if(regex.test($(this).val())) {
                        $(this).val('');
                    }

                    var required = false;
                    if($(this).attr('required') == 'required') {

                        required = true;
                    }
                    if(required) {

                        if($(this).val() == '') {

                            check = false;
                            return false;
                        }
                    }


                }*/ else if($(this).is('input[type=text]') || $(this).is('textarea')) {

                    var required = false;
                    if($(this).attr('required') == 'required') {

                        required = true;
                    }
                    if(required) {

                        if($(this).val() == '') {

                            check = false;
                            return false;
                        }
                    }

                } else if($(this).is('select')) {

                    var required = false;
                    if($(this).attr('required') == 'required') {

                        required = true;
                    }

                    if(required) {

                        if ($(this).find('option:selected').text() == '') {

                            check = false;
                            return false;
                        }
                    }
                }
            });

            if(!check) {

                alert('[' + item + '] 不能为空！');
                return false;
            }
            if(chkReg != '') {

                alert('[' + item + '] 中的内容不是' + chkReg + '！');
                return false;
            }
            return true;

        }

        function initForm(data) {

            $.each(data, function(index, item) {

                if(item.type == 'textarea') {

                    $('[agentitemid=' + item.agentitemid + ']').text(item.vals.decodeHtml());

                } else if(item.type == 'select') {

                    $('[agentitemid=' + item.agentitemid + ']').find('[text="' + item.vals + '"]').attr('selected', 'selected');

                } else if(item.type == 'input[type=text][plugins=widget]') {

                    if(($('[agentitemid=' + item.agentitemid + ']').val() || '') == '') {

                        $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                        $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
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

                } else if(item.type == 'radios') {

                    var vals = item.vals;
                    for(var i in vals) {
                        var val = vals[i];
                        $('[type=radio][name=' + item.agentitemid + '][value="' + val.val +'"]').attr('checked', 'checked');
                    }
                } else if(item.type == 'checkboxs') {

                    var vals = item.vals;
                    for(var i in vals) {
                        var val = vals[i];
                        $('[type=checkbox][name=' + item.agentitemid + '][value="' + val.val +'"]').attr('checked', 'checked');
                    }

                } else if(item.type == 'input[type=text][plugins=numeric]') {

                    $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                    $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());

                } else if(item.type == 'input[type=text]') {

                    $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                    $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());

                } else if(item.type == 'input[type=text][plugins=compute]') {

                    $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                    $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());
                }
            });
        }

        // init event for relate controls
        //  and set init-value for controls
        function initEvent() {

            $('input[plugins=compute]').each(function() {

                var relate = $(this).attr('expressionrelate');
                var id = $(this).attr('agentitemid');
                var fun = $(this).attr('expression');

                //
                $(this).blur(function() {

                    var v = $(this).val();

                    var regex = /^-?[0-9]+(.[0-9]{1,})?$/g;
                    //var regex = new RegExp('^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$')
                    if(regex.test(v)) {
                        $(this).cval(v);
                        return ;
                    }
                    $(this).cval('');
                });

                if(fun == undefined || fun == null || fun == '') {
                    fun = function(){};
                } else {
                    //fun.replace(/\,/g, '');
                    fun = fun.replace(/[\u002c]/gi, '');
                    // set init-value
                    //if($(this).val() == '') {
                    //    $(this).val(eval(fun));
                    //}
                    if($(this).val() == '') {
                        $(this).cval(eval(fun));
                    }

                    eval('fun = function (){$("input[agentitemid=' + id + ']").cval(' + fun + ');}');
                }
                if(relate == undefined || relate == null || relate == '') {
                } else {

                    relate = relate.split(',');
                    //events[id] = new Array();
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

            for(var key in events) {

                var funs = events[key];
                for(var i = 0; i < funs.length; i++) {
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
        *
        * @param selector
         */
        function retDom(selector) {

            return $(selector);
        }

        -->
    </script>
    <script type="text/javascript">
        inited = '1';
    </script>
</head>
<body class="view">
    <div id="preview" style="margin:8px">
        <form id="activiti-form-workFormContentPage">
            <c:choose>
                <c:when test="${empty html}">
                    ${detail }
                </c:when>
                <c:otherwise>
                    ${html }
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</body>
</html>
