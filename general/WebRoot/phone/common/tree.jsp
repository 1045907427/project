<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2017/8/14
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${wname }</title>
</head>
<body>
<div data-role="page" id="phone-main-tree">

    <style type="text/css">
        .ztree * {
            padding: 0;
            margin: 0;
            font-size: 16px;
            font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif;
        }
        /*.ztree * {font-size: 10pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}*/
        .ztree li ul{ margin:0; padding:0 0 0 38px;}
        .ztree li {line-height:30px;}
        .ztree li a {width:100%;height:30px;padding-top: 0px;}
        /*.ztree li a:hover {text-decoration:none; background-color: #E7E7E7;}*/
        /*.ztree li a span.button.switch {visibility:hidden}*/
        /*.ztree.showIcon li a span.button.switch {visibility:visible}*/
        .ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
        .ztree li span {line-height:30px;}
        /*.ztree li span.button {margin-top: -7px;}*/
        .ztree li span.button.switch {width: 32px;height: 32px;}
        /*.ztree li ul {*/
            /*margin: 0;*/
            /*padding: 0 0 0 18px;*/
        /*}*/

        /*.ztree li a.level0 span {font-size: 150%;font-weight: bold;}*/

        /*.ztree li span.button {*/
            /*width: 0px;*/
            /*height: 0px;*/
        /*}*/
        /*.ztree li span.button.switch.level0 {width: 38px; height:38px}*/
        /*.ztree li span.button.switch.level1 {width: 38px; height:38px}*/
        /*.ztree li span.button.noline_open {background-position: 0 0;}*/
        /*.ztree li span.button.noline_close {background-position: -56px 0;}*/
        /*.ztree li span.button.noline_open.level0 {background-position: 0 -36px;}*/
        /*.ztree li span.button.noline_close.level0 {background-position: -36px -36px;}*/

    </style>

    <script type="text/javascript">

        <!--
        var setting = {
            check: {
                enable: true,
                chkStyle: <c:if test="${param.checkType eq 2}">'checkbox'|| </c:if> 'radio',
                radioType: "all"
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                selectedMulti: <c:if test="${param.checkType eq 2}">'true'|| </c:if> false,
                showIcon: false
            },
            callback: {
                onClick: function (event, treeId, treeNode, clickFlag) {
                    var zTree = $.fn.zTree.getZTreeObj("tree");
                    zTree.checkNode(treeNode);

                }
            }
        };

        var zNodes = $.parseJSON('${data }');
        <c:if test="${param.onlyLeafCheck eq '1'}">
            zNodes.map(function(item) {
                item.nocheck = false;
                if(item.isParent == 'true') {
                    item.nocheck = true;
                }
            });
        </c:if>
//
        $(function(){
            var treeObj = $('#tree');
            $.fn.zTree.init(treeObj, setting, zNodes);
            var zTree = $.fn.zTree.getZTreeObj("tree");

            if(zNodes && zNodes.length <= 15) {
                zTree.expandAll(true);
            } else {
                zTree.expandAll(false);
            }
        });

        /**
         *
         * @returns {boolean}
         */
        function retMultiData() {

            var zTree = $.fn.zTree.getZTreeObj("tree");
            var nodes = zTree.getCheckedNodes(zTree);
            var target = {};

            <c:choose>
                <c:when test="${param.checkType eq 1}">

                    if(nodes && nodes.length > 0) {
                        target = nodes[0];
                    }

                </c:when>
                <c:otherwise>
                    target = nodes;
                </c:otherwise>
            </c:choose>

            $('#phone-back-tree').trigger('click');
            $(document).off('pagehide','#phone-main-tree').on('pagehide','#phone-main-tree',function(){
                eval('${param.callback }(target)');
            });
            return true;
        }
        //-->

    </script>
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1 id="phone-h1-tree">${wname }</h1>
        <a data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;" id="phone-back-tree">返回</a>
        <a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-check" style="border: 0px; background: #E9E9E9;" onclick="javascript:retMultiData();">确定</a>
    </div>
    <div role="main" class="ui-content" id="app">
        <span>双击节点可以展开或关闭节点</span>
        <div class="left">
            <ul id="tree" class="ztree"></ul>
        </div>
    </div>
</div>
</body>
</html>
