<%-- for Phone & Mobile --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+path+"/";
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>

<base id="basePath" href="<%=basePath%>"/>
<script type="text/javascript" src="phone/js/jquery.js"></script>
<script type="text/javascript" src="phone/js/jquery.form.js"></script>
<script type="text/javascript" src="phone/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="phone/js/jquery.validate.messages_zh.js"></script>
<script type="text/javascript" src="phone/js/jqm/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="phone/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="phone/js/jqmUtils.js"></script>
<script type="text/javascript" src="phone/js/android.js"></script>
<script type="text/javascript" src="phone/js/vue.min.js"></script>

<script type="text/javascript" src="phone/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="phone/js/jquery.ztree.excheck.min.js"></script>
<link rel="stylesheet" href="phone/js/zTreeStyle.css">

<link rel="stylesheet" href="phone/js/jqm/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="phone/js/jquery.mobile.datepicker.css">
<link rel="stylesheet" href="phone/css/phone.css">
<link rel="stylesheet" href="phone/js/jquery.validate.css">
<%--<script type="text/javascript" src="js/jqueryUtils.js" charset="UTF-8"></script>--%>

<script type="text/javascript" defer="defer">
    localStorage.removeItem("WidgetPage");var activePageFormData={};function isWidgetPageBack(){var widget=localStorage.getItem("WidgetPage");if(widget==true||widget=="true"){localStorage.removeItem("WidgetPage");console.log("return from widget page.");return true}console.log("not return from widget page.");return false}function androidWidget(options){jqmWidget(options)}function jqmWidget(options){options=$.extend({type:"widget",checkType:1,ishead:1,isdatasql:2,isHiddenUsenum:2,onlyLeafCheck:1},options);var callback=options.onSelect||options.onCheck;localStorage.setItem("WidgetPage",true);if(options.type==="widget"){$.mobile.changePage("<%=basePath%>phone/widget.do",{data:{id:options.referwid,callback:callback,checkType:options.checkType,onlyLeafCheck:options.onlyLeafCheck,paramRule:JSON.stringify(options.paramRule),name:options.name,col:options.col}})}else{if(options.type==="customerWidget"){$.mobile.changePage("<%=basePath%>phone/customer.do",{data:{callback:callback,checkType:options.checkType,onlyLeafCheck:options.onlyLeafCheck,paramRule:JSON.stringify(options.paramRule)}})}else{if(options.type==="goodsWidget"){$.mobile.changePage("<%=basePath%>phone/goods.do",{data:{callback:callback,checkType:options.checkType,onlyLeafCheck:options.onlyLeafCheck,paramRule:JSON.stringify(options.paramRule)}})}else{if(options.type==="supplierWidget"){$.mobile.changePage("<%=basePath%>phone/supplier.do",{data:{callback:callback,checkType:options.checkType,onlyLeafCheck:options.onlyLeafCheck,paramRule:JSON.stringify(options.paramRule)}})}}}}return true}function jqmDate(date,format,funs){$.mobile.changePage("phone/widget.do",{data:{date:date,callback:callback,format:format}});return true}function loading(text){if(!text){text="加载中..."}var str=$.mobile.loader.prototype.options.theme;$(".ui-loader h1").html(text);var _width=window.innerWidth;var _height=window.innerHeight;var htmlstr='<div style="width:'+_width+"px;height:"+_height+'px;position:fixed;top:0px;left:0px;opacity:0.1;z-index:99999" class="loader-bg"></div>';$("body").append(htmlstr);if(false){$(".ui-loader").removeClass("ui-loader-verbose").addClass("ui-loader-default")}else{$(".ui-loader").removeClass("ui-loader-default").addClass("ui-loader-verbose")}var cla="ui-body-"+str;$("html").addClass("ui-loading");var arr=$(".ui-loader").attr("class").split(" ");var reg=/ui-body-[a-f]/;for(var i in arr){if(reg.test(arr[i])){$(".ui-loader").removeClass(arr[i])}}$(".ui-loader").addClass(cla)}function loaded(){$("html").removeClass("ui-loading");$(".loader-bg").remove()}function loadingAnimationGlobalConfig(){var $this=$(this);var theme=$this.jqmData("theme")||$.mobile.loader.prototype.options.theme;var msgText=$this.jqmData("msgtext")||$.mobile.loader.prototype.options.text;var textVisible=$this.jqmData("textvisible")||$.mobile.loader.prototype.options.textVisible;var textonly=!!$this.jqmData("textonly");var h=$this.jqmData("html")||"";$.mobile.loading("show",{text:msgText,textVisible:textVisible,theme:theme,textonly:textonly,html:h})}function backPrev(){try{var href=location.href;var processidRegex=/processid=\d+.*/g;var processStartPosition=href.search(processidRegex);var processEndPosition=href.substr(processStartPosition).search(/&/g);var processid=href.substr(processStartPosition+10,processEndPosition-10);var typeRegex=/type=\w+.*/g;var typeStartPosition=href.search(typeRegex);var typeEndPosition=href.substr(typeStartPosition).search(/&/g);var type=href.substr(typeStartPosition+5,typeEndPosition-5);if(type=="handle"){$.ajax({type:"post",dataType:"json",data:{id:processid},url:"act/quitProcess.do",success:function(json){}})}}catch(e){}var url=localStorage.getItem("url")||"";localStorage.setItem("url",null);if(url==""){backMain();return true}location.href=url}$.fn.val2=$.fn.val;$.fn.val=function(v){var type=$(this).attr("type")||"text";if(type!="number"){if(v==undefined){return $(this).val2()}return $(this).val2(v)}var precision=$(this).attr("precision")||"2";if(v==undefined){var v2=$(this).val2()||"";if(v2==""){return""}var regex=/^-?[0-9]+(.[0-9]+)?$/;if(regex.test(v2)){return formatterMoney(v2,precision)}return formatterMoney("0",precision)}if(v==""){$(this).val2(v);return true}v=v||"0";var regex=/^-?[0-9]+(.[0-9]+)?$/;if(regex.test(v)){$(this).val2(formatterMoney(v,precision));return true}$(this).val2(formatterMoney("0",precision))};$(document).on("blur","input[type=number]",function(e){var $target=$(e.target);var precision=$target.attr("precision")||"2";var v=$target.val()||"";if(v==""){return true}var regex=/^-?[0-9]+(.[0-9]+)?$/;if(!regex.test(v)){v="0"}v=formatterMoney(v,precision);$target.val(v)});$(document).ready(function(e){$("input[type=number]").each(function(i,v){var precision=$(this).attr("precision")||"2";var v=$(this).val()||"";if(v==""){}else{var regex=/^-?[0-9]+(.[0-9]+)?$/;if(!regex.test(v)){v="0"}v=formatterMoney(v,precision);$(this).val(v)}})});$(document).on("click","input[type=number]",function(e){this.select()});!(function(){setInterval(function(){$.ajax({type:"post",url:"act/phone/connect.do",dataType:"json"})},60000)})();
    <%--
    localStorage.removeItem('WidgetPage');
    var activePageFormData = {};

    function isWidgetPageBack() {
        var widget = localStorage.getItem('WidgetPage');
        if(widget == true || widget == 'true') {
            localStorage.removeItem('WidgetPage');
            console.log('return from widget page.');
            return true;
        }
        console.log('not return from widget page.')
        return false;
    }

    function androidWidget(options){
        jqmWidget(options);
    }

    /**
     * 参照窗口
     */
    function jqmWidget(options) {

        options = $.extend({
            type: 'widget',
            checkType: 1,
            ishead: 1,
            isdatasql: 2,
            isHiddenUsenum: 2,
            onlyLeafCheck: 1
        }, options);

        var callback = options.onSelect || options.onCheck;

        localStorage.setItem('WidgetPage', true);

        if(options.type === 'widget') {
            $.mobile.changePage('<%=basePath%>phone/widget.do', {
                data: {
                    id: options.referwid,
                    callback: callback,
                    checkType: options.checkType,
                    onlyLeafCheck: options.onlyLeafCheck,
                    paramRule: JSON.stringify(options.paramRule),
                    name: options.name,
                    col: options.col
                }
            });
        } else if(options.type === 'customerWidget') {
            $.mobile.changePage('<%=basePath%>phone/customer.do', {
                data: {
                    callback: callback,
                    checkType: options.checkType,
                    onlyLeafCheck: options.onlyLeafCheck,
                    paramRule: JSON.stringify(options.paramRule)
                }
            });
        } else if(options.type === 'goodsWidget') {
            $.mobile.changePage('<%=basePath%>phone/goods.do', {
                data: {
                    callback: callback,
                    checkType: options.checkType,
                    onlyLeafCheck: options.onlyLeafCheck,
                    paramRule: JSON.stringify(options.paramRule)
                }
            });
        } else if(options.type === 'supplierWidget') {
            $.mobile.changePage('<%=basePath%>phone/supplier.do', {
                data: {
                    callback: callback,
                    checkType: options.checkType,
                    onlyLeafCheck: options.onlyLeafCheck,
                    paramRule: JSON.stringify(options.paramRule)
                }
            });
        }

        return true;

    }

    /**
     * 日期控件
     */
    function jqmDate(date,format,funs) {

        $.mobile.changePage('phone/widget.do', {
            data: {
                date: date,
                callback: callback,
                format: format
            }
        });

        return true;
    }

    //打开loading组件
    //text(string): 加载提示文字
    //str(string): load的背景颜色样式(取值:a,b,c,d)
    //flag(boolean): 背景是否透明(取值:true透明,false不透明)
    function loading(text){

        if(!text){
            text = '加载中...';
        }

        var str = $.mobile.loader.prototype.options.theme;

        $(".ui-loader h1").html(text);
        var _width = window.innerWidth;
        var _height = window.innerHeight;
        var htmlstr = '<div style="width:'+_width+'px;height:'+_height+'px;position:fixed;top:0px;left:0px;opacity:0.1;z-index:99999" class="loader-bg"></div>';
        $("body").append(htmlstr);
        if(false){
            $(".ui-loader").removeClass("ui-loader-verbose").addClass("ui-loader-default");
        } else {
            $(".ui-loader").removeClass("ui-loader-default").addClass("ui-loader-verbose");
        }
        var cla = "ui-body-"+str;
        $("html").addClass("ui-loading");
        var arr = $(".ui-loader").attr("class").split(" ");
        var reg = /ui-body-[a-f]/;
        for(var i in arr){
            if(reg.test(arr[i])){
                $(".ui-loader").removeClass(arr[i]);
            }
        }
        $(".ui-loader").addClass(cla);
    }

    //结束loading组件
    function loaded(){
        $("html").removeClass("ui-loading");
        $(".loader-bg").remove();
    }

    function loadingAnimationGlobalConfig() {
        var $this = $(this);
        var theme = $this.jqmData('theme') || $.mobile.loader.prototype.options.theme;
        var msgText = $this.jqmData('msgtext') || $.mobile.loader.prototype.options.text;
        var textVisible = $this.jqmData('textvisible') || $.mobile.loader.prototype.options.textVisible;
        var textonly = !!$this.jqmData('textonly');
        var h = $this.jqmData('html') || '';
        $.mobile.loading('show', {
            text: msgText,
            textVisible: textVisible,
            theme: theme,
            textonly: textonly,
            html: h
        })
    }
    function backPrev() {

        try {
            var href = location.href;
            var processidRegex = /processid=\d+.*/g;
            var processStartPosition = href.search(processidRegex);
            var processEndPosition = href.substr(processStartPosition).search(/&/g);
            var processid = href.substr(processStartPosition + 10, processEndPosition - 10);

            var typeRegex = /type=\w+.*/g;
            var typeStartPosition = href.search(typeRegex);
            var typeEndPosition = href.substr(typeStartPosition).search(/&/g);
            var type = href.substr(typeStartPosition + 5, typeEndPosition - 5);

            if(type == 'handle') {
                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    data: {id: processid},
                    url: 'act/quitProcess.do',
                    success: function(json) {
                    }
                });
            }
        }catch(e){}

        var url = localStorage.getItem('url') || '';
        localStorage.setItem('url', null);
        if (url == '') {
            backMain();
            return true
        }
        location.href = url
    }

    $.fn.val2 = $.fn.val;

    $.fn.val = function(v) {

        var type = $(this).attr('type') || 'text';

        if(type != 'number') {

            if(v == undefined) {

                return $(this).val2();
            }

            return $(this).val2(v);
        }

        var precision = $(this).attr('precision') || '2';

        if(v == undefined) {

            var v2 = $(this).val2() || '';

            if(v2 == '') {

                return '';
            }

            var regex = /^-?[0-9]+(.[0-9]+)?$/;
            if(regex.test(v2)) {

                return formatterMoney(v2, precision);
            }

            return formatterMoney('0', precision);
        }

        if(v == '') {

            $(this).val2(v);
            return true;
        }

        v = v || '0';
        var regex = /^-?[0-9]+(.[0-9]+)?$/;
        if(regex.test(v)) {

            $(this).val2(formatterMoney(v, precision));
            return true;
        }

        $(this).val2(formatterMoney('0', precision));
    };

    {
        $(document).on('blur', 'input[type=number]', function(e) {

            var $target = $(e.target);
            var precision = $target.attr('precision') || '2';
            var v = $target.val() || '';

            if(v == '') {

                return true;
            }

            var regex = /^-?[0-9]+(.[0-9]+)?$/;

            if(!regex.test(v)) {

                v = '0';
            }

            v = formatterMoney(v, precision);
            $target.val(v);
        });

        $(document).ready(function(e) {

            $('input[type=number]').each(function(i, v) {

                var precision = $(this).attr('precision') || '2';
                var v = $(this).val() || '';

                if(v == '') {
                } else {

                    var regex = /^-?[0-9]+(.[0-9]+)?$/;
                    if (!regex.test(v)) {

                        v = '0';
                    }

                    v = formatterMoney(v, precision);
                    $(this).val(v);
                }
            });
        });

        $(document).on('click', 'input[type=number]', function(e) {

            this.select();
        });
    }

    !(function() {

        setInterval(function() {

            $.ajax({
                type: 'post',
                url: 'act/phone/connect.do',
                dataType: 'json'
            });

        }, 6e4);

    })();

    --%>

</script>
<style type="text/css">
    .ui-field-contain>label, .ui-field-contain .ui-controlgroup-label, .ui-field-contain>.ui-rangeslider>label {
        width: 100% !important;
        margin: .5em 2% 0 0;
    }
</style>