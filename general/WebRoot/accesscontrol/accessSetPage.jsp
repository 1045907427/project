<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>ERP管理系统</title>
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<%@include file="/include.jsp" %>
</head>
<body>
<form action="accesscontrol/addAccessSet.do" method="post" id="accessSetpage-form" autocomplete="off">
	<div class="pageContent" style="width: 400px;">
		<p>
			<label>公司名称:</label>
			<input type="text" name="name" class="easyui-validatebox" required="true" style="width:200px;" />
		</p>
		<p>
			<label>手机用户数:</label>
			<input type="text" name="phonenum" class="easyui-numberbox" data-options="min:0,required:true" style="width:200px;"/>
		</p>
		<p>
			<label>系统用户数:</label>
			<input type="text" name="sysnum" class="easyui-numberbox" data-options="min:0,required:true" style="width:200px;"/>
		</p>
		<p>
			<label>到期日期:</label>
			<input type="text" name="duedate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width:200px;"/>
		</p>
		<p style="height: 80px;">
			<label>序列号:</label>
			<textarea name="serializeid" class="easyui-validatebox" required="true" style="width: 200px;height: 60px;"></textarea>
		</p>
		<p>
		<div id="messageEmail-form-addEmail-attachment-show-div" style="display:none;margin-top:10px;margin-bottom: 5px;">
			<div style="float:left;width:100px;line-height:22px;">菜单配置文件：</div>
			<div style="float:left">
				<div id="messageEmail-form-addEmail-attachment-uplist">
				</div>
			</div>
			<div style="clear:both"></div>
		</div>
		</p>
		<p style="text-align: center;">
			<a href="javascript:void(0);" id="accessSetpage-form-upload" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">+添加配置文件</a>&nbsp;&nbsp;&nbsp;
			<a href="javaScript:void(0);" id="accessSetpage-save-button" class="easyui-linkbutton" data-options="iconCls:'button-save'">确定</a>
		</p>
	</div>

</form>
<script type="text/javascript">
    $(function(){
        $("#accessSetpage-form").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                //转为json对象
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒",'设置成功');
                }else{
                    $.messager.alert("提醒",'序列号不对');
                }
            }
        });
        $("#accessSetpage-save-button").click(function(){
            $("#accessSetpage-form").submit();
        });
        $("#accessSetpage-form-upload").webuploader({
            title: '附件上传',
            filetype:'Files',
            //allowType:"jrxml",
            //mimeTypes:'text/*',
            disableGlobalDnd:true,
            width: 700,
            height: 400,
            url:'common/uploadMenuProperties.do',
            description:'',
            close:true,
            converthtml:true,
            onComplete: function(data){
                console.log(data)
                $("#messageEmail-form-addEmail-attachment-uplist").empty();
                $("#messageEmail-form-addEmail-attachment-show-div").show();

                var files=$.trim($("#messageEmail-form-addEmail-attachment").val() || "");
                var filearr=new Array();
                if(files!=""){
                    filearr=files.split(',');
                }
                for(var i=0;i<data.length;i++){
                    if('true'==data[i].flag){
                        $.messager.alert("提醒",'菜单配置成功！');
                        if(data[i].id==null || data[i].id==""){
                            continue;
                        }
                        filearr.push(data[i].id)

                        var htmlsb=showAttachMenuContent(data[i],false);
                        $("#messageEmail-form-addEmail-attachment-uplist").append(htmlsb);
                    }else{
                        $.messager.alert("提醒",'菜单配置文件解析出错！');
                    }

                }
                $("#messageEmail-form-addEmail-attachment").val(filearr.join(","));


            }
        });
        function showAttachMenuContent(item,isview){
            if(null==item){
                return "";
            }
            if(isview==null || false!=isview){
                isview=true;
            }
            var htmlsb=new Array();

            htmlsb.push("<div style=\"float:left;display:inline;margin-right:10px;position:relative;\" ");

            htmlsb.push(" fileid=\"");
            htmlsb.push(item.id);
            htmlsb.push("\">");
            if(null!=item.oldFileName){
                htmlsb.push(item.oldFileName);
            }else if(null!=item.oldfilename){
                htmlsb.push(item.oldfilename);
            }

            htmlsb.push("</div>");

            return htmlsb.join("");
        }
    });
</script>
</body>
</html>
