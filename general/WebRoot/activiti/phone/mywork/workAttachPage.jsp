<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>附件(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="attach">
    <script type="text/javascript">

        var attachid = null;

        /**
         * 关联附件和OA
         */
        function addAttach(json) {

            loading('上传中...');
            $.ajax({
                type: 'post',
                url: 'act/addAttach.do',
                data: {processid: '${param.processid }', attachid: json.id, commentid: '${param.commentid }'},
                dataType: 'json',
                success: function (json2) {

                    loaded();
                    if(json2.flag == false) {

                        //androidLoaded();
                        alertMsg('上传文件失败');
                        return false;
                    }

                    //androidLoaded();
                    alertMsg('上传成功。');
                    listAttach();
                }
            });
        }

        /**
         * 附件list
         */
        function listAttach() {

            $.ajax({
                type: 'post',
                url: 'act/listAttach.do',
                data: {processid: '${param.processid }', commentid: '${param.commentid }'},
                dataType: 'json',
                success: function (json3) {

                    var comments = json3.filelist;
                    var html = new Array();

                    $('#activiti-filelist-workAttachPage').empty();
                    if(comments.length == 1) {

                        html.push('<li data-icon="false"><a href="#">无</a></li>');
                        $('#activiti-filelist-workAttachPage').append(html.join(''));
                        $('#activiti-filelist-workAttachPage').listview('refresh');
                        return true;
                    }

                    for(var i in comments) {

                        var comment = comments[i];

                        if(comment.taskkey == 'process_begin') {
                            continue;
                        }

                        var files = comment.files;
                        if(files.length == 0) {
                            continue;
                        }

                        var candelete = files[0].candelete;
                        html.push('<li data-role="list-divider" data-theme="b">节点：' + comment.comment.taskname + '</li>');

                        for(var j in files) {

                            var file = files[j];

                            if(candelete == '1') {
                                html.push('<li><a href="act/phone/workAttachViewPage.do?id=' + file.attachid + '"><h2>' + file.oldfilename + '</h2><p>添加人：' + comment.addusername + '</p></a> <a href="#activiti-delete-workAttachPage" data-rel="popup" data-position-to="window" data-transition="pop" onclick="javascript:attachid=' + file.attachid + '">删除文件</a></li>');
                            } else {
                                html.push('<li><a href="act/phone/workAttachViewPage.do?id=' + file.attachid + '"><h2>' + file.oldfilename + '</h2><p>添加人：' + comment.addusername + '</p></a> </li>');
                            }
                        }

                    }

                    $('#activiti-filelist-workAttachPage').append(html.join(''));
                    try {
                        $('#activiti-filelist-workAttachPage').listview('refresh');
                    } catch(e) {
                        console.log(e)
                    }
                }
            });
        }

        /**
         * 删除附件
         */
        function deleteAttach() {

            loading('删除中...');
//            alert('删除文件...');
            $.ajax({
                type: 'post',
                url: 'act/deleteAttach.do',
                data: {attachid: attachid},
                dataType: 'json',
                success: function(json) {

//                    androidLoaded();
                    if(json.flag == false) {

                        alertMsg('删除失败！');
//                        alert('删除失败！');
                        return true;
                    }

                    alertMsg('删除成功。');
//                    alert('删除成功。');
                    listAttach();
                },
                error: function (e) {
                    alertMsg('删除失败！');
                }
            })
        }

        $(function() {

            $('#activiti-upload-workAttachPage').off().on('click', function(e) {

                androidUpload('addAttach');
            });
        });

    </script>
    <style type="text/css">
    </style>
    <form id="activiti-form-workAttachPage">
        <input type="hidden" name="processid" id="activiti-processid-workAttachPage" value="${param.processid }"/>
        <input type="hidden" name="commentid" id="activiti-commentid-workAttachPage" value="${param.commentid }"/>
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>附件[${param.processid }]</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <ul data-role="listview" id="activiti-filelist-workAttachPage" data-split-icon="delete" data-split-theme="a" data-inset="true">
                    <c:choose>
                        <c:when test="${empty files}">
                            <li data-icon="false"><a href="#">无</a></li>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${files.filelist }" var="comment" varStatus="status">
                                <c:if test="${not empty comment.files }">
                                    <li data-role="list-divider" data-theme="b">节点：<c:out value="${comment.comment.taskname }"/></li>
                                    <c:forEach items="${comment.files }" var="file" varStatus="status2">
                                        <c:choose>
                                            <c:when test="${file.candelete eq 1}">
                                                <li><a href="act/phone/workAttachViewPage.do?id=${file.attachid }"><h2><c:out value="${file.oldfilename }"/></h2><p>添加人：<c:out value="${comment.comment.assigneename }"/></p></a> <a href="#activiti-delete-workAttachPage" data-rel="popup" data-position-to="window" data-transition="pop" onclick="javascript:attachid=${file.attachid }">删除文件</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="act/phone/workAttachViewPage.do?id=${file.attachid }"><h2><c:out value="${file.oldfilename }"/></h2><p>添加人：<c:out value="${comment.comment.assigneename }"/></p></a> </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <input type="button" id="activiti-upload-workAttachPage" value="添加文件">

                <div data-role="popup" id="activiti-delete-workAttachPage" data-theme="a" data-overlay-theme="b" class="ui-content" style="max-width:340px; padding-bottom:2em;">
                    <h3>是否删除该文件？</h3>
                    <p>删除文件后将无法回复</p>
                    <a href="#" data-rel="back" class="ui-shadow ui-btn ui-corner-all ui-btn-b ui-icon-delete ui-btn-icon-left ui-btn-inline ui-mini" onclick="javascript:deleteAttach();">删除</a>
                    <a href="#" data-rel="back" class="ui-shadow ui-btn ui-corner-all ui-btn-inline ui-mini" onclick="javascript:return true;">取消</a>
                </div>
            </div>
         </div>
    </form>
</div>
</body>
</html>
