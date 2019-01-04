<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>表单（电脑版）编辑页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script type="text/javascript" src="../activiti/js/jquery.js"></script>
    <script type="text/ecmascript" src="../activiti/js/sha1.js"></script>
    <link href="../activiti/form/styles/bootstrap/2.2.2/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../activiti/form/styles/css/site.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .nav-list > li {
            float: left;
            width: 70px;
        }
    </style>
</head>
<body>

<div class="container" style="padding-top: 5px; padding-left: 5px; ">
    <div class="nodisplay">
        <iframe name="dummy_frame" style="display: none;" src="../activiti/form/formUploadPage.jsp">
        </iframe>
        <form name="uploadform" id="activiti-upload-formDesignPage2" action="uploadForm.do" enctype="multipart/form-data" method="post" target="dummy_frame">
            <input name="file" id="activiti-file-formDesignPage2" style="display: none;" type="file">
        </form>
    </div>

    <%-- to phone
    <div style="width: 100%; text-align: center; padding: 8px; color: #ff7870; border: 0px solid #CCC; font-weight: bold;">
        <input type="button" id="activiti-switch-formDesignPage2" class="btn btn-primary" onclick="javascript:switchDiv();" value="切换至手机版"/>
    </div>
     --%>
    <span><b>电脑版表单编辑</b> > 电脑版表单保存 > 手机版表单保存</span>

    <div style="border-top: dashed 1px #ddd; height: 10px;"></div>

    <div style="width: 100%; text-align: center; padding: 8px; color: #ff7870; border: 1px solid #CCC; font-weight: bold;">
        1. 不同控件的名称请不要重复， 设计完成后请先<span style="border: 1px solid #aaa; padding: 0px 5px 1px 5px; background: #eee; color: #f00; font-weight: bold;">预览</span>，然后再保存。<br/>
        2. 单选框、复选框和下拉框，如：<span style="border: 1px solid #aaa; padding: 0px 5px 1px 5px; background: #eee; color: #f00; font-weight: bold;">{|-</span>选项<span style="border: 1px solid #aaa; padding: 0px 5px 1px 5px; background: #eee; color: #f00;">-|}</span>两边边界是防止误删除控件，程序会把它们替换为空，请不要手动删除！
    </div>
    <div style="width: 100%; text-align: center; margin: 8px 0px 8px 0px; color: red; border-top: 2px solid #CCC; height: 3px;"></div>
    <div id="activiti-pc-formDesignPage2">
        <form method="post" id="saveform" name="saveform" action="">

            <input type="hidden" name="id" value="${form.id }"/>
            <c:choose>
                <c:when test="${empty form}">
                    <input type="hidden" name="unkey" value="${param.unkey }"/>
                    <input type="hidden" name="name" value="${param.name }"/>
                    <input type="hidden" name="intro" value="${param.intro }"/>
                    <input type="hidden" name="type" value="${param.type }"/>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="unkey" value="${form.unkey }"/>
                    <input type="hidden" name="name" value="${form.name }"/>
                    <input type="hidden" name="intro" value="${form.intro }"/>
                    <input type="hidden" name="type" value="${form.type }"/>
                </c:otherwise>
            </c:choose>
            <input type="hidden" name="fieldnum" value="0"/>

            <input type="hidden" name="fields" id="fields" value="${form.fieldnum }">
            <div class="nodisplay" style="display: none;">
                <textarea name="temphtml"></textarea>
                <textarea name="template"></textarea>
            </div>
            <div class="row">
                <div class="span2" style="float: none; width: 1000px;">
                    <ul class="nav nav-list">
                        <li class="nav-header">表单控件</li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('text');" class="btn btn-link">文本框</a></li>
                        <!-- <li><a href="javascript:void(0);" onclick="formDesign.exec('date');" class="btn btn-link">日期</a></li> -->
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('wdate');" class="btn btn-link">日期控件</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('textarea');" class="btn btn-link">多行文本</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('select');" class="btn btn-link">下拉菜单</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('radios');" class="btn btn-link">单选框</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('checkboxs');" class="btn btn-link">复选框</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('macros');" class="btn btn-link">宏控件</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('widget');" class="btn btn-link">参照窗口</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('goods');" class="btn btn-link">商品</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('customer');" class="btn btn-link">客户</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();setComputes();formDesign.exec('compute');" class="btn btn-link" title="运算控件可以根据其他运算控件进行求值">运算控件</a></li>
                        <li><a href="javascript:void(0);" onclick="setControls();formDesign.exec('numeric');" class="btn btn-link" title="自动将输入的小写数字转化为大写数字，如输入25，转化为贰拾伍元整">数字控件</a></li>
                        <!--
                        <li><a href="javascript:void(0);" onclick="formDesign.exec('progressbar');" class="btn btn-link">进度条</a></li>
                        <li><a href="javascript:void(0);" onclick="formDesign.exec('qrcode');" class="btn btn-link">二维码</a></li>
                        <li><a href="javascript:void(0);" onclick="formDesign.exec('more');" class="btn btn-link">一起参与...</a></li>
                         -->
                    </ul>
                </div>

                <div class="span10">
                    <script id="formEditor" type="text/plain" style="width:100%;">
                        <c:choose>
                            <c:when test="${empty param.html}">
                                ${template }
                            </c:when>
                            <c:otherwise>
                                ${param.html }
                            </c:otherwise>
                        </c:choose>
                    </script>
                </div>
            </div><!--end row-->
        </form>
    </div>

</div><!--end container-->
<script type="text/javascript" charset="utf-8" src="../activiti/form/styles/ueditor/ueditor.config.js?v=3.0"></script>
<script type="text/javascript" charset="utf-8" src="../activiti/form/styles/ueditor/ueditor.all.js?v=3.0"> </script>
<script type="text/javascript" charset="utf-8" src="../activiti/form/styles/ueditor/lang/zh-cn/zh-cn.js?v=3.0"></script>
<script type="text/javascript" charset="utf-8" src="../activiti/form/styles/ueditor/formdesign/formdesign.v4.js"></script>

<script type="text/javascript" charset="utf-8" src="../activiti/form/public/js/formbuild/bootstrap/js/bootstrap.min.js?2024"></script>

<!-- script start-->
<script type="text/javascript">

var temphtml = '';
var formEditor = UE.getEditor('formEditor',{
    customtoolbar: true,//是否显示，设计器的 toolbars
    textarea: 'design_content',
    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
    toolbars:[[
        'importform', '|', 'fullscreen', /*'source', '|',*/ 'undo', 'redo', '|','bold', 'italic', 'underline', 'fontborder', 'strikethrough',  'removeformat', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|', 'fontfamily', 'fontsize', '|', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',  'link', 'unlink',  '|',  'horizontal',  'spechars',  'wordimage', '|', 'inserttable', 'deletetable',  'mergecells',  'splittocells']],
    labelMap:{
        'importform': '从文件中导入FORM表单'
    },
    //focus时自动清空初始化时的内容
    //autoClearinitialContent:true,
    //关闭字数统计
    wordCount: false,
    //关闭elementPath
    elementPathEnabled: false,
    //默认的编辑区域高度
    <c:choose>
        <c:when test="${empty param.framewidth }">
            initialFrameWidth:960
        </c:when>
        <c:otherwise>
            initialFrameWidth:parseInt('${param.framewidth }')
        </c:otherwise>
    </c:choose>
    ,initialFrameHeight:320
    //,iframeCssUrl:"css/bootstrap/css/bootstrap.css" //引入自身 css使编辑器兼容你网站css
    ,iframeCssUrl: ''       /*css，用于表单样式*/
    //更多其他参数，请参考ueditor.config.js中的配置项
});
formEditor.commands['importform'] = {
    execCommand : function() {
        var flag = confirm('导入的Form文件内容将会覆盖当前编辑区的内容，是否继续？');
        if(flag) {

            $('#activiti-file-formDesignPage2').click();
        }
        return true;
    },
    queryCommandState : function(){ }
};

$(function() {

    $('#activiti-file-formDesignPage2').live('change', function() {
        $('#activiti-upload-formDesignPage2').submit();
        $('#activiti-upload-formDesignPage2')[0].value = '';
        return false;
    });
});

var formDesign = {
    /*执行控件*/
    exec : function (method) {
        formEditor.execCommand(method);
    },
    /*
     Javascript 解析表单
     template 表单设计器里的Html内容
     fields 字段总数
     */
    parse_form:function(template,fields)
    {
        //正则  radios|checkboxs|select 匹配的边界 |--|  因为当使用 {} 时js报错 (plugins|fieldname|fieldflow)
        var preg =  /(\|-<span(((?!<span).)*plugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\/span>-\||<(img|input|textarea|select).*?(<\/select>|<\/textarea>|\/>))/gi,preg_attr =/(\w+)=\"(.?|.+?)\"/gi,preg_group =/<input.*?\/>/gi;
        if(!fields) fields = 0;

        var template_parse = template,template_data = new Array(),add_fields=new Object(),checkboxs=0;

        var pno = 0;
        template.replace(preg, function(plugin,p1,p2,p3,p4,p5,p6){
            var parse_attr = new Array(),attr_arr_all = new Object(),name = '', select_dot = '' , is_new=false;
            var p0 = plugin;
            var tag = p6 ? p6 : p4;

            if(tag == 'radios' || tag == 'checkboxs')
            {
                plugin = p2;
            }else if(tag == 'select')
            {
                plugin = plugin.replace('|-','');
                plugin = plugin.replace('-|','');
            }
            plugin.replace(preg_attr, function(str0,attr,val) {
                if(attr=='name')
                {
                    if(val=='NEWFIELD')
                    {
                        is_new=true;
                        fields++;
                        val = 'data_'+fields;
                    }
                    name = val;
                }

                if(tag=='select' && attr=='value')
                {
                    if(!attr_arr_all[attr]) attr_arr_all[attr] = '';
                    attr_arr_all[attr] += select_dot + val;
                    select_dot = ',';
                }else
                {
                    attr_arr_all[attr] = val;
                }
                var oField = new Object();
                oField[attr] = val;
                parse_attr.push(oField);
            })
            /*alert(JSON.stringify(parse_attr));return;*/
            if(tag =='checkboxs') /*复选组  多个字段 */
            {
                plugin = p0;
                plugin = plugin.replace('|-','');
                plugin = plugin.replace('-|','');
                var name = 'checkboxs_'+checkboxs;
                attr_arr_all['parse_name'] = name;
                attr_arr_all['name'] = '';
                attr_arr_all['value'] = '';

                //attr_arr_all['content'] = '<span plugins="checkboxs"  title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '">';
                // 修复BUG：复选框设置为竖排时，在预览时显示为横排
                //attr_arr_all['content'] = '<span plugins="checkboxs"  title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '">';
                attr_arr_all['content'] = '<span plugins="checkboxs"  title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '" orgchecked="' + attr_arr_all['orgchecked'] + '">';

                var dot_name ='', dot_value = '';
                p5.replace(preg_group, function(parse_group) {
                    var is_new=false,option = new Object();
                    parse_group.replace(preg_attr, function(str0,k,val) {
                        if(k=='name')
                        {
                            if(val=='NEWFIELD')
                            {
                                is_new=true;
                                fields++;
                                val = 'data_'+fields;
                            }

                            attr_arr_all['name'] += dot_name + val;
                            dot_name = ',';

                        }
                        else if(k=='value')
                        {
                            attr_arr_all['value'] += dot_value + val;
                            dot_value = ',';

                        }
                        option[k] = val;
                    });

                    if(!attr_arr_all['options']) attr_arr_all['options'] = new Array();
                    attr_arr_all['options'].push(option);
                    if(!option['checked']) option['checked'] = '';
                    var checked = option['checked'] ? 'checked="checked"' : '';
                    //attr_arr_all['content'] +='<input type="checkbox" name="'+option['name']+'" value="'+option['value']+'" fieldname="' + attr_arr_all['fieldname'] + option['fieldname'] + '" fieldflow="' + attr_arr_all['fieldflow'] + '" '+checked+'/>'+option['value']+'&nbsp;';
                    attr_arr_all['content'] +='<input type="checkbox" name="'+option['agentitemid']+'" value="'+option['value']+'" checkboxoption="'+option['value']+'" fieldflow="' + attr_arr_all['fieldflow'] + '" '+checked+' text="' + option['text'] + '"/>'+option['text']+'&nbsp;';
                    // 修复BUG：复选框设置为竖排时，在预览时显示为横排
                    if(attr_arr_all['orgchecked'] == 'orgchecked1') {
                        attr_arr_all['content'] += '<br/>';
                    }

                    if(is_new)
                    {
                        var arr = new Object();
                        arr['name'] = option['name'];
                        arr['plugins'] = attr_arr_all['plugins'];
                        arr['fieldname'] = attr_arr_all['fieldname'] + option['fieldname'];
                        arr['fieldflow'] = attr_arr_all['fieldflow'];
                        add_fields[option['name']] = arr;
                    }

                });
                attr_arr_all['content'] += '</span>';

                //parse
                template = template.replace(plugin,attr_arr_all['content']);
                template_parse = template_parse.replace(plugin,'{'+name+'}');
                template_parse = template_parse.replace('{|-','');
                template_parse = template_parse.replace('-|}','');
                template_data[pno] = attr_arr_all;
                checkboxs++;

            } else if(name)
            {
                if(tag =='radios') /*单选组  一个字段*/
                {
                    plugin = p0;
                    plugin = plugin.replace('|-','');
                    plugin = plugin.replace('-|','');
                    attr_arr_all['value'] = '';
                    //attr_arr_all['content'] = '<span plugins="radios" name="'+attr_arr_all['name']+'" title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '">';
                    // 修复BUG：单选框设置为竖排时，在预览时显示为横排
                    // attr_arr_all['content'] = '<span plugins="radios" name="'+attr_arr_all['agentitemid']+'" title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '">';
                    attr_arr_all['content'] = '<span plugins="radios" name="'+attr_arr_all['agentitemid']+'" title="'+attr_arr_all['title']+'" agentitemid="' + hex_sha1(attr_arr_all['title']) + '" orgchecked="' + attr_arr_all['orgchecked'] + '">';

                    var dot_name ='', dot_value = '', dot='';
                    p5.replace(preg_group, function(parse_group) {
                        var option = new Object();
                        parse_group.replace(preg_attr, function(str0,k,val) {

                            if(k=='value')
                            {
                                attr_arr_all['value'] += dot + val;
                                dot = ',';
                            // 修复BUG：单选框设置为竖排时，在预览时显示为横排
                            }
                            option[k] = val;
                        });
                        option['name'] = attr_arr_all['name'];
                        if(!attr_arr_all['options']) attr_arr_all['options'] = new Array();
                        attr_arr_all['options'].push(option);
                        if(!option['checked']) option['checked'] = '';
                        var checked = option['checked'] ? 'checked="checked"' : '';
                        // 增加属性fieldname -- by limin at 20150209
                        //attr_arr_all['content'] +='<input type="radio" name="'+attr_arr_all['name']+'" value="'+option['value']+'"  '+checked+' />'+option['value']+'&nbsp;';
                        attr_arr_all['content'] +='<input type="radio" name="'+attr_arr_all['agentitemid']+'" value="'+option['value']+'" radiooption="'+option['value']+'" '+checked+' text="' + option['text'] + '"/>'+option['text']+'&nbsp;';
                        // 修复BUG：单选框设置为竖排时，在预览时显示为横排
                        if(attr_arr_all['orgchecked'] == 'orgchecked1') {
                            attr_arr_all['content'] += '<br/>';
                        }
                    });
                    attr_arr_all['content'] += '</span>';

                }else
                {
                    attr_arr_all['content'] = is_new ? plugin.replace(/NEWFIELD/,name) : plugin;
                }
                template = template.replace(plugin,attr_arr_all['content']);
                template_parse = template_parse.replace(plugin,'{'+name+'}');
                template_parse = template_parse.replace('{|-','');
                template_parse = template_parse.replace('-|}','');
                if(is_new)
                {
                    var arr = new Object();
                    arr['name'] = name;
                    arr['plugins'] = attr_arr_all['plugins'];
                    arr['title'] = attr_arr_all['title'];
                    arr['orgtype'] = attr_arr_all['orgtype'];
                    arr['fieldname'] = attr_arr_all['fieldname'];
                    arr['fieldflow'] = attr_arr_all['fieldflow'];
                    add_fields[arr['name']] = arr;
                }
                template_data[pno] = attr_arr_all;


            }
            pno++;
        })
        var view = template.replace(/{\|-/g,'');
        view = view.replace(/-\|}/g,'');
        // remove 'value' attr
        view = view.replace(/value=\"(.?|.+?)\"/gi,'');
        // remove 'expressiontext' attr
        view = view.replace(/expressiontext=\"(.?|.+?)\"/gi,'');
        // set value attr, just for radios and checkboxes
        view = view.replace(/checkboxoption/gi, 'value');
        view = view.replace(/radiooption/gi, 'value');
        var parse_form = new Object({
            'fields':fields,        //总字段数
            'template':template,    //完整html，用于编辑表单
            'parse':view,           //显示用的html
            'data':template_data,   //控件属性
            'add_fields':add_fields //新增控件
        });
        return JSON.stringify(parse_form);
    },
    /*type  =  save 保存设计 versions 保存版本  close关闭 */
    fnCheckForm : function ( type ) {

        alert('请在预览页面中保存！');
        return false;

        if(formEditor.queryCommandState( 'source' ))
            formEditor.execCommand('source');//切换到编辑模式才提交，否则有bug

        if(formEditor.hasContents()){
            formEditor.sync();/*同步内容*/

            //--------------以下仅参考-----------------------------------------------------------------------------------------------------
            var type_value='',formid=0,fields=$("#fields").val(),formeditor='';

            if( typeof type!=='undefined' ){
                type_value = type;
            }
            //获取表单设计器里的内容
            formeditor=formEditor.getContent();
            //解析表单设计器控件
            var parse_form = this.parse_form(formeditor,fields);
            var html = $.parseJSON(parse_form);
            //alert(parse_form);
            //异步提交数据
            $.ajax({
                type: 'POST',
                url : '../act/ue/saveForm.do',
                dataType : 'json',
                // data : {'type' : type_value,'formid':'${form.id}','parse_form':parse_form},
                data: {unkey: '${form.unkey }', name: '${form.name }', detail: html.parse, template: html.template, fields: html.data.length },
                success : function(json){

                    alert('保存成功。');
                    window.location.href =  window.location.href;
                }
            });

        } else {
            alert('表单内容不能为空！')
            $('#submitbtn').button('reset');
            return false;
        }
    } ,
    /*预览表单*/
    fnReview : function (){
        if(formEditor.queryCommandState( 'source' ))
            formEditor.execCommand('source');/*切换到编辑模式才提交，否则部分浏览器有bug*/

        if(formEditor.hasContents()){

            formEditor.sync();       /*同步内容*/
            temphtml = formEditor.getContent();

            var type_value='',formid=0,fields=$("#fields").val(),formeditor='';

            if( typeof type!=='undefined' ){
                type_value = type;
            }
            //获取表单设计器里的内容
            formeditor=formEditor.getContent();
            //解析表单设计器控件
            var parse_form = this.parse_form(formeditor,fields);
            var html = $.parseJSON(parse_form);

            $('textarea[name=temphtml]').val(html.parse);
            $('textarea[name=template]').val(html.template);

            $('input[name=fieldnum]').val(html.data.length);

            document.saveform.action='../act/formPreviewPage2.do';
            document.saveform.submit(); //提交表单
        } else {
            alert('表单内容不能为空！');
            return false;
        }
    }
};

function setComputes() {

    var temp = formEditor.getContent();
    $('<div id="activiti-temp-formDesignPage2"></div>').appendTo('body');
    $('#activiti-temp-formDesignPage2').append(temp);
    UE.computes = new Array();
    $('#activiti-temp-formDesignPage2 input[plugins=compute]').each(function() {

        var item = $(this).attr('title');
        UE.computes.push(item);
    });
    $('#activiti-temp-formDesignPage2').remove();
}

function setControls() {

    var temp = formEditor.getContent();
    $('<div id="activiti-temp-formDesignPage2"></div>').appendTo('body');
    $('#activiti-temp-formDesignPage2').append(temp);
    UE.controls = new Array();
    $('#activiti-temp-formDesignPage2 [title]').each(function() {

        var item = $(this).attr('title');
        UE.controls.push(item);
    });
    $('#activiti-temp-formDesignPage2').remove();
}

</script>
<!-- script end -->
</body>
</html>
