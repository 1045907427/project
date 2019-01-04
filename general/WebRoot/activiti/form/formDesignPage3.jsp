<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>表单（手机版）保存页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script type="text/javascript" src="../activiti/js/jquery.js"></script>
    <link href="../activiti/form/public/css/bootstrap/css/bootstrap.min.css?2027" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #components{
            min-height: 600px;
        }
        #target{
            min-height: 400px;
            border: 1px solid #aaa;
            padding: 5px;
        }
        #target .component{
            border: 1px solid #fff;
        }
        #temp{
            width: 500px;
            background: white;
            border: 1px dotted #ccc;
            border-radius: 10px;
        }
        .popover-content form {
            margin: 0 auto;
            width: 213px;
        }
        .popover-content form .btn{
            margin-right: 10px
        }
        #source{
            min-height: 500px;
        }
        /* Page headers */
        .bs-header {
            padding: 10px 15px 5px; /* side padding builds on .container 15px, so 30px */
            margin:20px 0;
            font-size: 16px;
            text-align: left;
            text-shadow: 0 1px 0 rgba(0,0,0,.15);
            background-color: #d4d4d4;
            color: #fff;
            background-repeat:repeat-x;
            background-image:-webkit-linear-gradient(45deg, #245184, #607d91);
            background-image:-moz-linear-gradient(45deg, #245184, #607d91);
            background-image:linear-gradient(45deg, #245184, #607d91)

        }
        .bs-header p {
            font-weight: 300;
            line-height: 1;
            text-align: center;
        }
        .navibar{
            padding: 5px;
            height: 0px;
        }
    </style>
</head>
<body>
<div id="activiti-all-formDesignPage3">

<!-- navi bar -->
<div class="container navibar">
    <span>电脑版表单编辑 > 电脑版表单预览 > <b>手机版表单保存</b></span>
</div>

<!-- Docs page layout -->
<div class="bs-header" id="content">
    <div class="container">
        <p>
            <a class="btn btn-primary" href="#" onclick="javascript:saveHtml();">保 存</a>
        </p>
    </div>
</div>

<div class="container" style="padding-top: 5px; padding-left: 5px;">
    <div class="row clearfix">
        <div class="span6">
            <div class="clearfix">
                <h2>我的表单（手机）</h2>
                <hr>
                <div id="build">
                    <form id="target" class="form-vertical">
                        <fieldset>
                            <div id="legend" class="component" rel="popover" title="编辑属性" trigger="manual"
                                 data-content="
                    <form class='form'>
                      <div class='controls'>
                        <label class='control-label'>表单名称</label> <input type='text' id='orgvalue' placeholder='请输入表单名称'>
                        <hr/>
                        <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
                      </div>
                    </form>"
                                    >
                                <input type="hidden" name="form_name" value="<c:out value="${param.formname}" />" class="agentplugins" agentplugins="form_name"/>
                                <legend class="agentplugins-orgvalue"><c:out value="${param.formname}" /></legend>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
        <form class="form-vertical" id="components">
            <div class="span6" id="activiti-items-formDesignPage3">
                <h2>拖拽下面的控件到左侧</h2>
                <hr>
            </div>
        </form>
    </div>

</div><!--end container-->

<div id="activiti-dummy-formDesignPage3" style="display: none;">

    <script type="text/javascript">
        var items = new Array();
        var html = new Array();
        var temp = '';
        var regex = /title=\"\S+\"/;
    </script>

    <c:forEach items="${phonehtml }" var="item" varStatus="idx">
        ${item }
        <script type="text/javascript">
            (function(){

                temp = $('#activiti-dummy-formDesignPage3').children().last().prev()[0].outerHTML;
                var title = temp.match(regex)[0];

                title = title.substring(7, title.length - 1);

                html = new Array();
                html.push('<div class="control-group component" rel="popover" title="' + title + '" trigger="manual" data-content=""');
                html.push('>');

                html.push('<label class="control-label agentplugins-orgname">' + title + '</label>');
                html.push('<div class="controls">');
                html.push(temp);
                html.push('</div>');

                $('#activiti-items-formDesignPage3').append(html.join(''));

                $('div.controls > textarea,div.controls > input[type=text]').css({'width': '400px'});
                $('div.controls > select').css({'width': '414px'});
            })();

        </script>
    </c:forEach>
    <script type="text/javascript">
        (function(){

            $('#target').css({'min-height': $('#activiti-items-formDesignPage3')[0].clientHeight + 'px'});
            $('#components').css({'min-height': $('#activiti-items-formDesignPage3')[0].clientHeight + 200 + 'px'});
        })();
    </script>
</div>

<div id="activiti-dummy2-formDesignPage3" style="display: none;">
</div>

<form id="activiti-saveform-formDesignPage3" name="saveform" method="post">
    <textarea id="activiti-phonehtml-formDesignPage3" name="phonehtml" style="display: none;"></textarea>
    <input type="hidden" id="activiti-unkey-formDesignPage3" name="unkey" value="${param.formkey }"/>
</form>

<!-- script start-->
<script type="text/javascript">

    /**
     * 保存手机html代码
     */
    function saveHtml() {

        var html = AGB.genSource();

        $('#activiti-dummy2-formDesignPage3').html(html);

        $('#activiti-dummy2-formDesignPage3 label').removeClass('control-label');
        $('#activiti-dummy2-formDesignPage3 label').removeClass('agentplugins-orgname');
        $('#activiti-dummy2-formDesignPage3 label').removeAttr('class');

        $('#activiti-dummy2-formDesignPage3 label').each(function() {

            $(this).parent().removeAttr('class');
            $(this).parent().attr('class', 'ui-field-contain');

            $(this).append($(this).next().children().clone());
            $(this).next().remove();
        });

        $('#activiti-dummy2-formDesignPage3 fieldset').after('<div class="ui-corner-all custom-corners"></div>');
        $('#activiti-dummy2-formDesignPage3 fieldset').next().append('<div class="ui-body ui-body-a"></div>');
        $('#activiti-dummy2-formDesignPage3 fieldset').next().children().append($('#activiti-dummy2-formDesignPage3 fieldset').html());
        $('#activiti-dummy2-formDesignPage3 fieldset').remove();

        /////////////////////////////////////////////
        // 将checkboxs和radios转换为jQueryMobile代码
        /////////////////////////////////////////////
        $('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs],#activiti-dummy2-formDesignPage3 span[plugins=radios]').parents('.component').after($('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs],#activiti-dummy2-formDesignPage3 span[plugins=radios]').clone());
        $('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs],#activiti-dummy2-formDesignPage3 span[plugins=radios]').prev().remove();

        $('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs]').parents('.ui-field-contain').append($('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs]').clone());
        $('#activiti-dummy2-formDesignPage3 span[plugins=checkboxs]').parents('.ui-field-contain').find('label').remove();

        $('#activiti-dummy2-formDesignPage3 span[plugins=radios]').parents('.ui-field-contain').append($('#activiti-dummy2-formDesignPage3 span[plugins=radios]').clone());
        $('#activiti-dummy2-formDesignPage3 span[plugins=radios]').parents('.ui-field-contain').find('label').remove();

        $('#activiti-dummy2-formDesignPage3 .ui-field-contain').each(function(index, item) {

            if($(this).find('input[type=checkbox],input[type=radio]').length > 0) {

                var $p = $(this);
                var $c = $(this).find('input[type=checkbox],input[type=radio]');

                var title = $p.find('span').attr('title');
                var itemid = $p.find('span').attr('agentitemid');

                $p.append('<fieldset data-role="controlgroup" agentitemid="' + itemid + '"></fieldset>');
                $p.children().last().append('<legend>' + title + '</legend>');

                $c.each(function(index2, item2) {

                    var text = $(this).attr('text');
                    $p.children().last().append('<label>' + $(this)[0].outerHTML + text + '</label>');
                });

                $p.find('span').remove();
            }
        });
        /////////////////////////////////////////////

        /////////////////////////////////////////////
        // 将textarea的width属性去除
        /////////////////////////////////////////////
        $('#activiti-dummy2-formDesignPage3 textarea').css({width: ''});
        /////////////////////////////////////////////

        /////////////////////////////////////////////
        // legend替换
        /////////////////////////////////////////////
        var legend = $('#activiti-dummy2-formDesignPage3 div#legend').find('legend').html();
        $('#activiti-dummy2-formDesignPage3 div.ui-body').before('<div class="ui-bar ui-bar-b"><h1>' + legend + '</h1></div>');
        $('#activiti-dummy2-formDesignPage3 div#legend').find('legend').remove();
        /////////////////////////////////////////////

        html = $('#activiti-dummy2-formDesignPage3').html();
        $('#activiti-phonehtml-formDesignPage3').val(html);

        $.ajax({
            type: 'post',
            url: 'act/savePhoneHtml.do',
            data: {unkey: '${param.formkey }', phonehtml: html/*.replaceAll(/\r\n/g, '').replaceAll(/\r/g, '').replaceAll(/\n/g, '')*/},
            dataType: 'json',
            success: function(json) {

                if(json.flag) {

                    alert('保存成功。');
                    window.close();
                    return true;
                }

                alert('保存失败！');
            },
            error: function(ex) {}
        })
    }

</script>
<!-- script end -->

<script type="text/javascript" charset="utf-8" src="../activiti/form/public/js/formbuild/bootstrap/js/bootstrap.min.js?2027"></script>
<script type="text/javascript" charset="utf-8" src="../activiti/form/public/js/formbuild/agent.form.build.core.js?2027"></script>
<%--
<script type="text/javascript" charset="utf-8" src="../activiti/form/public/js/formbuild/agent.form.build.plugins.js?2027"></script>
--%>
</div>
</body>
</html>
