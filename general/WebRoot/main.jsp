<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>管理系统</title>
	<%@include file="/include.jsp" %>
	<%@include file="/printInclude.jsp"%>
	<script type="text/javascript" src="js/portal/jquery.portal.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/trip/trip.min.js" charset="UTF-8"></script>
	<link rel="stylesheet" href="js/trip/trip.min.css" type="text/css"></link>
	<link rel="stylesheet" href="js/portal/portal.css" type="text/css"></link>
	<script type="text/javascript">
        var tabRemind = new Array();			// 需要提醒的TAB的标题
        var tabRemindMsg = new Array();			// 需要提示的消息
        var tabRemindCallback = new Array();	// 关闭TAB时需要执行的回调函数
        var tabhidden = false;
        $(function(){
            $("#tt").tabs({
                fit:true,
                border:false,
                plain:false,
                pill:false,
                justified:false,
                narrow:true,
                showHeader:true,
                tabPosition:'right',
                headerWidth:135,
                showHeader:true,
                toolPosition:'left',
                onSelect:function(title,index){
                    $("#tab-navigation .tab-head-nav").hide();
                    $("#tab-navigation .tab-head-nav").eq(index).show();
                },
                onAdd:function(title,index){
                    bindMouse(index);
                },
                onUpdate:function(title,index){
                    $("#tt").show();
                    bindMouse(index);
                },
                onClose:function(title,index ){
                    var tabsArr = $("#tt").tabs("tabs");
                    $("#tab-navigation .tab-head-nav").eq(index).remove();
                    if(null==tabsArr || tabsArr.length==0){
                        $("#tt").hide();
                        $("#mainpage").show();
                        $("#tab-navigation").hide();
                        $("#tab-showhide-button").hide();
                    }
                },
                onBeforeClose: function(title,index){
                    var target = this;
                    $.messager.confirm('确认','是否关闭：'+title,function(r){
                        if (r){
                            var opts = $(target).tabs('options');
                            var bc = opts.onBeforeClose;
                            opts.onBeforeClose = function(){};  // allowed to close now
                            $(target).tabs('close',index);
                            opts.onBeforeClose = bc;  // restore the event function
                        }
                    });
                    return false;	// prevent from closing
                },
                onBeforeClose:function(title){
                    var target = this;
                    var flag = true;
                    var message = "是否关闭("+title+")页面?";
                    var callback = null;
                    for(var i =0;i<tabRemind.length;i++){
                        if(title==tabRemind[i]){
                            flag = false;
                            message = tabRemindMsg[i];
                            callback = tabRemindCallback[i];
                            break;
                        }
                    }
                    if(!flag){
                        $.messager.confirm('提醒', message, function(r){
                            if (r){
                                var opts = $(target).tabs('options');
                                var bc = opts.onBeforeClose;
                                opts.onBeforeClose = function(){};  // allowed to close now
                                removeTabRemind(title);
                                if(callback != undefined && callback != null && callback != '') {
                                    callback();
                                }
                                closeTab(title);
                                opts.onBeforeClose = bc;  			// restore the event function
                            }
                        });
                    }
                    return flag;
                },
                onContextMenu:function(e,title){
                    e.preventDefault();
                    $('#mm').menu('show', {
                        left : e.pageX,
                        top : e.pageY
                    });
                    $('#mm').data("currtab", title);
                }
            });
            bindTabEvent();
            bindTabMenuEvent();
        });
        //添加tab关闭提醒
		/*
		 function addTabRemind(title){
		 tabRemind.push(title);
		 }
		 */
        /**
         * 添加TAB关闭提醒
         * title  TAB标题
         * message  提示消息
         * callback  回调函数， 执行回调函数后TAB将关闭
         */
        function addTabRemind(title, message, callback) {
            var existflag = false;
            // 如果队列中已存在该提示，则进行替换
            for(var i = 0; i < tabRemind.length; i++) {
                if(tabRemind[i] == title) {
                    tabRemind[i] = title;
                    tabRemindMsg[i] = message;
                    tabRemindCallback[i] = callback;
                    existflag = true;
                }
            }
            if(!existflag) {
                tabRemind.push(title);
                tabRemindMsg.push(message);
                tabRemindCallback.push(callback);
            }
        }
        //移除tab关闭提醒
        function removeTabRemind(title){
            var index = 0;
            for(var i=0;i<tabRemind.length;i++){
                if(title==tabRemind[i]){
                    index = i;
                }
            }
            tabRemind = tabRemind.slice(0,index).concat(tabRemind.slice(index+1,tabRemind.length));
            tabRemindMsg = tabRemindMsg.slice(0,index).concat(tabRemindMsg.slice(index+1,tabRemindMsg.length));
            tabRemindCallback = tabRemindCallback.slice(0,index).concat(tabRemindCallback.slice(index+1,tabRemindCallback.length));
        }
        //添加tab项
        function addTabDefault(url,name){
            var basepath = $("#basePath").attr("href");
            //url MD5加密
            var md5url = url;
            if(url.indexOf(".")==0){
                md5url = url.substring(1,url.length);
            }
            var urlmd5 = hex_md5(md5url);
            if(url.indexOf(basepath)==-1){
                url = basepath+url;
            }
            if ($('#tt').tabs('exists',name)){
                $('#tt').tabs('select', name);
                updateTab(md5url,name);
            } else {
                var windowheight = $(window).height()-90;
                var navwidth = $(window).width() - 88;
                if(!tabhidden){
                    navwidth = navwidth -133;
                }
                var pageheight = 30;
                var iframerate = 0.96;
                iframerate = parseFloat((windowheight-pageheight)/windowheight).toFixed(4);
                iframerate = iframerate*100;

                var tab = $('#tt').tabs('getSelected');
                var index = $('#tt').tabs('getTabIndex',tab);
                var navigation =  $("#tab-navigation div").eq(index).attr("text");
                if(null!=navigation && navigation!=""){
                    navigation += "&nbsp;>>&nbsp;"+name;
                }else{
                    navigation = "您的当前位置："+name;
                }
                $(".tab-head-nav").hide();
                $("#tab-navigation").show();
                $("#tab-showhide-button").show();
                $("#tab-navigation").width(navwidth);
                if( $("#tab-navigation .tab-head-nav").size()>0){
                    $("#tab-navigation .tab-head-nav").eq(index).after('<div id="'+urlmd5+'-head" class="tab-head-nav" text="'+navigation+'">'+navigation+'</div>');
                }else{
                    $('<div id="'+urlmd5+'-head" class="tab-head-nav" text="'+navigation+'">'+navigation+'</div>').appendTo($("#tab-navigation"));
                }


                $('#tt').tabs('add',{
                    index:index+1,
                    title:name,
                    iconCls:'tabs-child',
                    content : '<iframe url="'+url+'" tabname="'+name+'" id="'+urlmd5+'" frameborder="0" style="border:0;width:100%;height:'+iframerate+'%;min-width: 800px;margin:'+pageheight+'px 0px 0px 0px;"></iframe>',
                    closable:true
                });
                tabsWindowURLMD5(urlmd5).location.href=url;
            }
        }
        function addTabWithNavigation(url,name,navigation){
            var basepath = $("#basePath").attr("href");
            //url MD5加密
            var md5url = url;
            if(url.indexOf(".")==0){
                md5url = url.substring(1,url.length);
            }
            var urlmd5 = hex_md5(md5url);
            if(url.indexOf(basepath)==-1){
                url = basepath+url;
            }
            if ($('#tt').tabs('exists',name)){
                $('#tt').tabs('select', name);
                updateTab(md5url,name);
            } else {
                var windowheight = $(window).height()-90;
                var navwidth = $(window).width() - 88;
                if(!tabhidden){
                    navwidth = navwidth -133;
                }
                var pageheight = 30;
                var iframerate = 0.96;
                iframerate = parseFloat((windowheight-pageheight)/windowheight).toFixed(4);
                iframerate = iframerate*100;
                $('#tt').tabs('add',{
                    title:name,
                    content : '<iframe url="'+url+'" tabname="'+name+'" id="'+urlmd5+'" frameborder="0" style="border:0;width:100%;height:'+iframerate+'%;min-width: 800px;margin:'+pageheight+'px 0px 0px 0px;overflow-y:hidden;"></iframe>',
                    closable:true
                });
                $(".tab-head-nav").hide();
                var navdes = "您的当前位置："+navigation;
                $("#tab-navigation").show();
                $("#tab-showhide-button").show();
                $("#tab-navigation").width(navwidth);
                $('<div id="'+urlmd5+'-head" class="tab-head-nav" text="'+navdes+'">'+navdes+'</div>').appendTo($("#tab-navigation"));
                tabsWindowURLMD5(urlmd5).location.href=url;
            }
        }
        //刷新当前页面
        function refresh(cfg){
            var refresh_tab = cfg.tabTitle ? $('#tt').tabs('getTab', cfg.tabTitle) : $('#tt').tabs('getSelected');
            if (refresh_tab && refresh_tab.find('iframe').length > 0) {
                var _refresh_ifram = refresh_tab.find('iframe')[0];
                var refresh_url = cfg.url ? cfg.url : $(_refresh_ifram).attr("url");
                _refresh_ifram.contentWindow.location.href = refresh_url;
            }
        }
        //刷新当前页面
        function addNewPage(cfg){
            var refresh_tab = cfg.tabTitle ? $('#tt').tabs('getTab', cfg.tabTitle) : $('#tt').tabs('getSelected');
            if (refresh_tab && refresh_tab.find('iframe').length > 0) {
                var _refresh_ifram = refresh_tab.find('iframe')[0];
                var refresh_url = cfg.url ? cfg.url : $(_refresh_ifram).attr("url");
                window.open(refresh_url);
            }
        }
        function getNowTabTitle(){
            var title = $('#tt').tabs('getSelected').panel('options').title;
            return title;
        }
        //关闭当前标签
        function closeNowTab(){
            var tab = $('#tt').tabs('getSelected').panel('options').title;
            closeTab(tab);
        }
        function closeTab(title){
            $('#tt').tabs('close',title);
        }
        //更新当前tab页面
        function updateTab(url,title){
            var tab = null;
            if(!$('#tt').tabs('exists',title)){
                tab = $('#tt').tabs('getSelected');
            }else{
                $('#tt').tabs('select', title);
                var currTitle = $('#tt').tabs('getSelected').panel('options').title;
                if(currTitle!='主页'&& currTitle != title){
                    closeTab(currTitle);
                }
                if(currTitle==title){
                    tab = $('#tt').tabs('getSelected');
                }else{
                    $('#tt').tabs('select',title);
                    tab = $('#tt').tabs('getTab',title);
                }
            }
            //refresh(tab)
            var basepath = $("#basePath").attr("href");
            var md5url = url;
            if(url.indexOf(".")==0){
                md5url = url.substring(1,url.length);
            }
            var urlmd5 = hex_md5(md5url);
            if(url.indexOf(basepath)==-1){
                url = basepath+url;
            }
            var windowheight = $(window).height()-90;
            var navwidth = $(window).width() - 88;
            if(!tabhidden){
                navwidth = navwidth -135;
            }
            var pageheight = 30;
            var iframerate = 0.96;
            iframerate = parseFloat((windowheight-pageheight)/windowheight).toFixed(4);
            iframerate = iframerate*100;
            $('#tt').tabs('update',{
                tab: tab,
                options: {
                    title: title,
                    content : '<iframe url="'+url+'" tabname="'+title+'" id="'+urlmd5+'" frameborder="0" style="border:0;width:100%;height:'+iframerate+'%;min-width: 800px;min-height:500px;padding:'+pageheight+'px 0px 0px 0px;"></iframe>',
                    closable:true
                }

            });
            $("#tab-navigation").show();
            $("#tab-showhide-button").show();
            tabsWindowURLMD5(urlmd5).location.href=url;
        }

        //刷新当前tab页面
        function updateThisTab(url){
            var title = $('#tt').tabs('getSelected').panel('options').title;
            updateTab(url,title);
        }
        function bindMouse(index){
            $("#tt").show();
            $("#mainpage").hide();
            $("#tt .tabs-close").hide();
            $("#tt .tabs li .tabs-close").eq(index).show();
        }
        //绑定tab的双击事件、右键事件
        function bindTabEvent() {
            $("#tt .tabs-inner").live('dblclick', function() {
                var subtitle = $(this).children("span").text();
                if ($(this).next().is('.tabs-close')) {
                    closeTab(subtitle);
                }
            });
            $("#tt .tabs li").live('mouseover', function() {
                var index = $(this).index();
                if(index>=0){
                    $("#tt .tabs-close").hide();
                    $("#tt .tabs-selected .tabs-close").show();
                    $("#tt .tabs li .tabs-close").eq(index).show();
                }else{
                    $("#tt .tabs-close").hide();
                    $("#tt .tabs-selected .tabs-close").show();
                }
            }).live('mouseout',function(){
                $("#tt .tabs-close").hide();
                $("#tt .tabs-selected .tabs-close").show();
            });
        }
        //绑定tab右键菜单事件
        function bindTabMenuEvent() {
            //刷新当前
            $('#mm-refresh').click(function() {
                var tab = $('#tt').tabs('getSelected');
                refresh(tab);
            });
            $("#mm-tabclose").click(function(){
                closeNowTab();
            });
            //全部关闭
            $('#mm-tabcloseall').click(function() {
                $('.tabs-inner span').each(function(i, n) {
                    if ($(this).parent().next().is('.tabs-close')) {
                        var t = $(n).text();
                        closeTab(t);
                    }
                });
            });
            $("#mm-openNewPage").click(function(){
                var tab = $('#tt').tabs('getSelected');
                addNewPage(tab);
            });
            $("#mm-helpPage").click(function () {
                var cfg = $('#tt').tabs('getSelected');
                var refresh_tab = cfg.tabTitle ? $('#tt').tabs('getTab', cfg.tabTitle) : $('#tt').tabs('getSelected');
                if (refresh_tab && refresh_tab.find('iframe').length > 0) {
                    var _refresh_ifram = refresh_tab.find('iframe')[0];
                    var md5url = $(_refresh_ifram).attr("id");
                    addOrUpdateTab('accesscontrol/showOperateHelpViewPage.do?md5url='+md5url,"帮助文档");
                }
            });
            $("#mm-helpPage-edit").click(function () {
                var cfg = $('#tt').tabs('getSelected');
                var refresh_tab = cfg.tabTitle ? $('#tt').tabs('getTab', cfg.tabTitle) : $('#tt').tabs('getSelected');
                if (refresh_tab && refresh_tab.find('iframe').length > 0) {
                    var _refresh_ifram = refresh_tab.find('iframe')[0];
                    var md5url = $(_refresh_ifram).attr("id");
                    var title = $('#tt').tabs('getSelected').panel('options').title;
                    addOrUpdateTab('accesscontrol/showOperateHelpEditPage.do?md5url='+md5url+'&title='+title,"帮助文档编辑");
                }
            });
            $("#tab-showhide-button").click(function(){
                if($("#tab-showhide-button").hasClass("tabshow")){
                    tabhidden = true;
                    var navwidth = $(window).width() - 88;
                    if(!tabhidden){
                        navwidth = navwidth -133;
                    }
                    $("#tab-navigation").width(navwidth);
                    $('#tt').tabs('hideHeader');
                    $("#tab-showhide-button").removeClass("tabshow");
                    $("#tab-showhide-button").addClass("tabhide");
                }else if($("#tab-showhide-button").hasClass("tabhide")){
                    tabhidden = false;
                    var navwidth = $(window).width() - 88;
                    if(!tabhidden){
                        navwidth = navwidth -133;
                    }
                    $("#tab-navigation").width(navwidth);
                    $('#tt').tabs('showHeader');
                    $("#tab-showhide-button").removeClass("tabhide");
                    $("#tab-showhide-button").addClass("tabshow");
                }
            });
            //关闭除当前之外的TAB
            $('#mm-tabcloseother').click(function() {
                var currtab_title = $('#mm').data("currtab");
                $('.tabs-inner span').each(function(i, n) {
                    if ($(this).parent().next().is('.tabs-close')) {
                        var t = $(n).text();
                        if (t != currtab_title) {
                            closeTab(t);
                        }
                    }
                });
            });
            //关闭当前右侧的TAB
            $('#mm-tabcloseright').click(function() {
                var nextall = $('.tabs-selected').nextAll();
                if (nextall.length == 0) {
                    return false;
                }
                nextall.each(function(i, n) {
                    if ($('a.tabs-close', $(n)).length > 0) {
                        var t = $('a:eq(0) span', $(n)).text();
                        closeTab(t);
                    }
                });
                return false;
            });
            //关闭当前左侧的TAB
            $('#mm-tabcloseleft').click(function() {
                var prevall = $('.tabs-selected').prevAll();
                if (prevall.length == 1) {
                    return false;
                }
                prevall.each(function(i, n) {
                    if ($('a.tabs-close', $(n)).length > 0) {
                        var t = $('a:eq(0) span', $(n)).text();
                        closeTab(t);
                    }
                });
                return false;
            });
            var guidejsonNow = null;
            $("#sys_help").click(function(){
                var title = getNowTabTitle();
                if(title == "主页"){
                    $.getScript("./help/main.js",function(){
                        var tripGuide = new Trip(guidejson, {
                            showNavigation : true,
                            showCloseBox : true,
                            tripTheme : "yeti",
                            delay : -1
                        });
                        tripGuide.start();
                    });
                }else{
                    alert(title);
                }
            });

        }
        var leftMenuTreeSetting = {
            view : {
                dblClickExpand : false,
                showLine : false,
                selectedMulti: false,
                showIcon : false,
                expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast"
            },
            async : {
                enable : true,
                url : "showUserMenuTree.do",
                autoParam : [ "id", "pId", "name", "urlStr", "description" ]
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPId : ""
                }
            },
            callback : {
                //点击树状菜单更新页面按钮列表
                beforeClick : function(treeId, node) {
                    var zTree = $.fn.zTree.getZTreeObj("menu_left_tree");
                    if (node.isParent) {
                        if (node.level == 0) {
                            zTree.expandAll(false);
                            zTree.expandNode(node);
                        } else {
                            zTree.expandNode(node);
                        }

                    }
                    return !node.isParent;
                },
                onClick : function(e, treeId, treeNode) {
                    addTab("." + treeNode.urlStr, treeNode.description);
                }
            }
        };
        //全体用户列表
        var userAllTreeSetting = {
            view : {
                dblClickExpand : false,
                showLine : false,
                showIcon : true,
                showTitle:false,
                expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast",
                addHoverDom:addHoverDom,
                removeHoverDom : removeHoverDom
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPId : ""
                }
            },
            callback : {
                //点击树状菜单更新页面按钮列表
                beforeClick : function(treeId, node) {
                    var zTree = $.fn.zTree.getZTreeObj("user_all_tree");
                    if (node.isParent) {
                        if(node.open){
                            if (node.level == 0) {
                                zTree.expandNode(node,false);
                            } else {
                                zTree.expandNode(node,false);
                            }
                        }else{
                            if (node.level == 0) {
                                zTree.expandAll(false);
                                zTree.expandNode(node);
                            } else {
                                zTree.expandNode(node);
                            }
                        }
                    }
                    return !node.isParent;
                },
                //点击树状菜单更新页面按钮列表
                beforeDblClick : function(treeId, node) {
                    var zTree = $.fn.zTree.getZTreeObj("user_all_tree");
                    if (node.isParent) {
                        if (node.level == 0) {
                            zTree.expandAll(false);
                            zTree.expandNode(node);
                        } else {
                            zTree.expandNode(node);
                        }

                    }
                    return !node.isParent;
                },
                onDblClick : function(e, treeId, treeNode) {
                    showUserInfo(treeNode.id,treeNode.name);
                }
            }
        };
        //在线人员 鼠标焦点 显示发短信 发邮件图片
        function addHoverDom(treeId, treeNode) {
            if(treeNode.isParent==false){
                var aObj = $("#" + treeNode.tId + "_a");
                if ($("#diyBtn_msg_"+treeNode.id).length>0) return;
                var editStr = "<a id='diyBtn_msg_" +treeNode.id+ "' class='msgbutton'>&nbsp;</a><a class='emailbutton' id='diyBtn_email_" +treeNode.id+ "'>&nbsp;</a>";
                aObj.append(editStr);
                //发短信
                $("#diyBtn_msg_"+treeNode.id).bind("click", function(){
                    baseInnerMessageSend(treeNode.id);
                });
                //发邮件
                $("#diyBtn_email_"+treeNode.id).bind("click", function(){
                    baseInnerEmailSend(treeNode.id);
                });
            }
        }
        function removeHoverDom(treeId, treeNode){
            if (treeNode.isParent) return;
            $("#diyBtn_msg_"+treeNode.id).remove();
            $("#diyBtn_email_"+treeNode.id).remove();
            $("#diyBtn_msg_"+treeNode.id).unbind().remove();
            $("#diyBtn_email_"+treeNode.id).unbind().remove();
        }
        //显示用户信息
        function showUserInfo(id,name){
            var url = 'accesscontrol/showSysUserInfoPage.do?type=show&userid='+id;
            if($('#tt').tabs('exists','用户信息详情')){
                updateTab(url, '用户信息详情');
            }
            else{
                addTab(url, '用户信息详情');
            }
        }
        //初始化用户树
        function initUserTreeAllList(){
            var allnodes = null;
            $.ajax({
                url :'accesscontrol/getAllUserTreeList.do',
                type:'post',
                dataType:'json',
                async:false,
                success:function(json){
                    allnodes = json;
                }
            });
            $.fn.zTree.init($("#user_all_tree"), userAllTreeSetting, allnodes);
        }
        function showDiagram(instid){
            $("#activity-diagramPage").dialog({
                title:'查看流程图',
                width:600,
                height:350,
                closed: false,
                resizable:true,
                maximizable:true,
                cache: false,
                href:'workflow/showDiagramPage.do?instid='+instid+"&"+new Date(),
                modal: true
            });
        }
        function showInfoTable(instid){
            if ($('#tt').tabs('exists','工作详情查看')){
                closeTab('工作详情查看');
            }
            addTab('workflow/showInfoPage.do?instid='+instid, '工作详情查看');
        }
        //发送短信
        function sendMsg(type){
            var treeId = "";
            if(type=='online'){
                treeId = "user_online_tree";
            }else if(type=='all'){
                treeId = "user_all_tree";
            }
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            var treeNode = zTree.getSelectedNodes();
            if(treeNode==null||treeNode==""){
                $.messager.alert("提醒","请选择用户！");
                return false;
            }else{
                baseInnerMessageSend(treeNode[0].id);
            }
        }
        //发送邮件
        function sendEmail(type){
            var treeId = "";
            if(type=='online'){
                treeId = "user_online_tree";
            }else if(type=='all'){
                treeId = "user_all_tree";
            }
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            var treeNode = zTree.getSelectedNodes();
            if(treeNode==null||treeNode==""){
                $.messager.alert("提醒","请选择用户！");
                return false;
            }else{
                baseInnerEmailSend(treeNode[0].id);
            }
        }
        //显示当前用户信息
        function showCurrUserInfo(){
            var url = 'accesscontrol/showSysUserInfoPage.do?type=show&userid=${sysUser.userid }';
            if($('#tt').tabs('exists','用户信息详情')){
                updateTab(url, '用户信息详情');
            }
            else{
                addTab(url, '用户信息详情');
            }
        }
        function baseInnerMessageSend(uid,msgtype){

            if(uid==null || $.trim(uid)==""){
                return false;
            }
            var mtype="";
            if(msgtype==null || $.trim(msgtype)!=""){
                mtype="";
            }
            //内部短信发送
            try{
                $('<div id="message-innerMessage-window-sendMessageOper-content"></div>').appendTo("#message-innerMessage-window-sendMessageOper");
                var $messageSendOper=$("#message-innerMessage-window-sendMessageOper-content");
                var url="message/innerMessage/";
                if(mtype!="reply"){
                    url +="messageSendPage.do";
                }else{
                    url +="messageReplyPage.do";
                }
                url +="?touserids="+uid+"&msgsendpageid=message-innerMessage-window-sendMessageOper-content";
                var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
                setTimeout(function(){
                    iframe.attr('src', url);
                }, 1);
                $messageSendOper.window({
                    title:'内部短信发送',
                    width: 700,
                    height: 450,
                    top:($(window).height() - 450) * 0.5,
                    left:($(window).width() - 700) * 0.5,
                    closed: true,
                    cache: false,
                    content : iframe,
                    minimizable:false,
                    modal: true,
                    onClose:function(){
                        $('#message-innerMessage-window-sendMessageOper-content').window("destroy");
                    }
                });
                $messageSendOper.window("open");
            }catch(e){
            }
        }
        function baseInnerEmailSend(uid){
            //内部邮件发送
            if(uid==null || $.trim(uid)==""){
                return false;
            }
            try{
                $('<div id="message-email-window-sendEmailOper-content"></div>').appendTo("#message-email-window-sendEmailOper");
                var $sendEmailOper=$("#message-email-window-sendEmailOper-content");
                var url="message/email/emailAddPage.do?noshowtitle=1&touserids="+uid+"&emlsendpageid=message-email-window-sendEmailOper-content";
                var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
                setTimeout(function(){
                    iframe.attr('src', url);
                }, 1);
                $sendEmailOper.window({
                    title:'内部邮件发送',
                    width: 860,
                    height: 600,
                    top:($(window).height() - 600) * 0.5,
                    left:($(window).width() - 860) * 0.5,
                    closed: true,
                    cache: false,
                    content : iframe,
                    minimizable:false,
                    modal: true,
                    onClose:function(){
                        $('#message-email-window-sendEmailOper-content').window("destroy");
                    }
                });
                $sendEmailOper.window("open");
            }catch(e){
            }
        }
        function baseOpenTabQueryTitle(url,deftitle){
            if(url==null || $.trim(url)==""){
                return false;
            }
            //打开tab页面
            if(deftitle==null || $.trim(deftitle)==""){
                deftitle="查看信息";
            }
            $.ajax({
                url:'common/commonAjax/getOperateByURL.do',
                type:'POST',
                data:{operurl:url},
                dataType:'json',
                cache:false,
                success:function(data, textStatus, jqXHR){
                    if(data.flag==true){
                        var title=data.name;
                        if(title!=null && $.trim(title)!=""){
                            deftitle=title;
                        }
                    }
                    addOrUpdateTab(url,deftitle);
                }
            });
        }
        function innerMessageTip(){
            $.ajax({
                url:'message/innerMessage/showMessageRemindCount.do',
                type:'POST',
                data:'',
                dataType:'json',
                cache:false,
                success:function(data, textStatus, jqXHR){
                    try{
                        var reminds=data.reminds || 0;
                        if(reminds>0){
                            baseUnRemindMessageList();
                        }
                    }catch(e){
                    }
                }
            });
        }
        function baseUnRemindMessageList(){
            $('<div id="message-innerMessage-window-messageRemindOper-content"></div>').appendTo("#message-innerMessage-window-messageRemindOper");
            var $messageRemindOper=$('#message-innerMessage-window-messageRemindOper-content');
            var url="message/innerMessage/messageReceiveReadList.do?rows=10&msgwindowpageid=message-innerMessage-window-messageRemindOper-content";
            var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');

            $messageRemindOper.window({
                title:'您有新消息，请注意查收',
                width: 304,
                height: 202,
                top:($(window).height() - 202) ,
                left:($(window).width() - 304),
                closed: true,
                cache: false,
                content : iframe,
                minimizable:false,
                modal: false,
                onClose:function(){
                    $('#message-innerMessage-window-messageRemindOper-content').window("destroy");
                },
                onOpen:function(){
                    setTimeout(function(){
                        iframe.attr('src', url);
                    }, 5);
                }
            });
            $messageRemindOper.window("open");
        }
        function showMsgNotice(){
            var msgcount = 0;
            var jobcount = 0;
            var mailcount = 0;
            var noticecount = 0;
            var totalcount = 0;
            $.ajax({
                url:'message/innerMessage/getRemindCount.do',
                type:'POST',
                data:'',
                dataType:'json',
                cache:false,
                success:function(data){
                    msgcount = data.msgcount;
                    jobcount = data.jobcount;
                    mailcount = data.mailcount;
                    noticecount = data.noticecount;
                    totalcount = data.totalcount;
                    if(msgcount>0){
                        $("#tools_msg").removeClass("tools_msg");
                        $("#tools_msg").addClass("tools_msg_remind");
                    }else{
                        $("#tools_msg").addClass("tools_msg");
                        $("#tools_msg").removeClass("tools_msg_remind");
                    }
                    if(jobcount>0){
                        $("#tools_job").removeClass("tools_job");
                        $("#tools_job").addClass("tools_job_remind");
                    }else{
                        $("#tools_job").addClass("tools_job");
                        $("#tools_job").removeClass("tools_job_remind");
                    }
                    if(mailcount>0){
                        $("#tools_mail").removeClass("tools_mail");
                        $("#tools_mail").addClass("tools_mail_remind");
                    }else{
                        $("#tools_mail").addClass("tools_mail");
                        $("#tools_mail").removeClass("tools_mail_remind");
                    }
                    if(noticecount>0){
                        $("#tools_notice").removeClass("tools_notice");
                        $("#tools_notice").addClass("tools_notice_remind");
                    }else{
                        $("#tools_notice").addClass("tools_notice");
                        $("#tools_notice").removeClass("tools_notice_remind");
                    }
                    if(totalcount>0){
                        $("#main-bottom-remind").show();
                        $("#main-bottom-remind").html(totalcount);
                    }else{
                        $("#main-bottom-remind").hide();
                        $("#main-bottom-remind").html("");
                    }
                }
            });

        }
        $(document).ready(function() {
            setInterval(function(){showMsgNotice();},60000 );
        });
	</script>
</head>

<body class="main-layout">
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" style="height:60px;" class="main-header">
		<div class="main-logo" style="text-align: right;">
			<div class="cs-north-span iconmouse">
				<span class="userdetail">&nbsp;欢迎您：${sysUser.name }<c:if test="${IsOpenBusDate=='1'}">,操作日期:${busdate}</c:if></span>
				<a href="javascript:void(0);" id="sys_home" text="主页" class="main-home">&nbsp;</a>
				<%--<a href="javascript:void(0);" id="theme-menu" text="皮肤" class="main-skin">&nbsp;</a>--%>
				<%--<a href="javascript:void(0);" id="control-menu" text="修改密码" class="main-pwd">&nbsp;</a>--%>
                <a href="javascript:void(0);" id="sys_set" text="设置" class="main-pwd" >&nbsp;</a>
				<a href="javaScript:void(0);" id="sys_logout" text="退出" class="main-out">&nbsp;</a>
			</div>
			<div id="sysUser-dialog-pwdOperate"></div>
            <div id="sysUser-dialog-accountDate"></div>
		</div>
	</div>
	<div data-options="region:'west',border:false" style="width:86px;padding:0px;overflow:auto;overflow-x: hidden; ">
		<div id="main-left" class="main-left">
			<ul class="main-tree">
				<c:if test="${passwordflag!='1'}">
					<c:forEach var="menuUrl" items="${menuUrlList}" varStatus="status">
						<c:if test="${menuUrl == '/OAModule.do' || isSuperAccess}">
							<security:authorize url="/OAModule.do">
								<li text="OA管理" url="OAModule"><img src="image/default/menu-oa.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/BaseModule.do' || isSuperAccess}">
							<security:authorize url="/BaseModule.do">
								<li text="基础档案" url="BaseModule"><img src="image/default/menu-base.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/CRMModule.do' || isSuperAccess}">
							<security:authorize url="/CRMModule.do">
								<li text="客户关系" url="CRMModule"><img src="image/default/menu-crm.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/BuyModule.do' || isSuperAccess}">
							<security:authorize url="/BuyModule.do">
								<li text="采购管理" url="BuyModule"><img src="image/default/menu-buy.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/SalesModule.do' || isSuperAccess}">
							<security:authorize url="/SalesModule.do">
								<li text="销售管理" url="SalesModule"><img src="image/default/menu-sales.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/StorageModule.do' || isSuperAccess}">
							<security:authorize url="/StorageModule.do">
								<li text="库存管理" url="StorageModule"><img src="image/default/menu-storage.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/DeliveryModule.do' || isSuperAccess}">
							<security:authorize url="/DeliveryModule.do">
								<li text="配送管理" url="DeliveryModule"><img src="image/default/menu-buy.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/ReceiptModule.do' || isSuperAccess}">
							<security:authorize url="/ReceiptModule.do">
								<li text="应收管理" url="ReceiptModule"><img src="image/default/menu-receipt.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/PayableModule.do' || isSuperAccess}">
							<security:authorize url="/PayableModule.do">
								<li text="应付管理" url="PayableModule"><img src="image/default/menu-payable.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/ReportModule.do' || isSuperAccess}">
							<security:authorize url="/ReportModule.do">
								<li text="报表管理" url="ReportModule"><img src="image/default/menu-report.png"/></li>
							</security:authorize>
						</c:if>
						<c:if test="${menuUrl == '/CostModule.do' || isSuperAccess}">
							<security:authorize url="/CostModule.do">
								<li text="费用管理" url="CostModule"><img src="image/default/menu-cost.png"/></li>
							</security:authorize>
						</c:if>
						<%--<c:if test="${menuUrl == '/ManufacturerModule.do' || isSuperAccess}">--%>
						<%--<security:authorize url="/ManufacturerModule.do">--%>
						<%--<li text="厂方数据" url="ManufacturerModule"><img src="image/default/menu-report.png"/></li>--%>
						<%--</security:authorize>--%>
						<%--</c:if>--%>
						<%--<c:if test="${menuUrl == '/EbshopModule.do' || isSuperAccess}">--%>
						<%--<security:authorize url="/EbshopModule.do">--%>
						<%--<li text="电商管理" url="EbshopModule"><img src="image/default/menu-sales.png"/></li>--%>
						<%--</security:authorize>--%>
						<%--</c:if>--%>
						<c:if test="${menuUrl == '/SyssetModule.do' || isSuperAccess}">
							<security:authorize url="/SyssetModule.do">
								<li text="系统设置" url="SyssetModule"><img src="image/default/menu-sys.png"/></li>
							</security:authorize>
						</c:if>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		<div id="main-bottom" class="main-bottom">
			<ul class="main-tree-bottom">
				<li text="工作台" id="main-tree-bottom-li">
					<img src="image/default/menu-workbench.png"/>
					<span id="main-bottom-remind" class="main-bottom-remind" style="display: none;"></span>
				</li>
			</ul>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div id="mainpage" class="mainbackground"></div>
		<div id="tt" tabsFirst="tabClass" style="display: none;"></div>
	</div>
</div>
<div id="mm" class="easyui-menu skin-menu" minWidth="80" style="width:100px;display: none;">
	<div id="mm-refresh">刷新</div>
	<div class="menu-sep"></div>
	<div id="mm-tabclose">关闭</div>
	<div id="mm-tabcloseother">关闭其他</div>
	<div id="mm-tabcloseall">关闭全部</div>
	<div id="mm-openNewPage">在新页面打开</div>
	<div class="menu-sep"></div>
	<div id="mm-helpPage">帮助</div>
	<security:authorize url="/accesscontrol/showOperateHelpPage.do">
		<div id="mm-helpPage-edit">帮助文档编辑</div>
	</security:authorize>
</div>
<div id="menu-skin" class="easyui-menu skin-menu" minWidth="80" style="width:80px;display: none;">
	<div id="theme-default" class="theme-menu" onclick="changeTheme('default');" <%if("default".equals(easyuiThemeName)){ %>iconCls="skin-ok"<%}%>>默认</div>
	<div id="theme-default1" class="theme-menu" onclick="changeTheme('default1');" <%if("blue".equals(easyuiThemeName)){ %>iconCls="skin-ok"<%}%>>旧版</div>
	<div id="theme-default2" class="theme-menu" onclick="changeTheme('default2');" <%if("default2".equals(easyuiThemeName)){ %>iconCls="icon-ok"<%}%>>新版</div>
</div>
<div id="activity-diagramPage"></div>
<div id="activity-infoTablePage"></div>
<div id="message-innerMessage-window-messageRemindOper"></div>
<div id="message-innerMessage-window-sendMessageOper"></div>
<div id="message-email-window-sendEmailOper"></div>
<div id="main-tree2" class="mian-tree2" style="display: none;margin: 0px;">
	<div id="menu_left_tree" class="ztree"></div>
	<input type="hidden" id="menu-module"/>
</div>
<div id="menu-set" class="easyui-menu skin-menu" minWidth="130" style="width:130px;display: none;">
    <div id="theme-menu">切换皮肤</div>
    <div class="menu-sep"></div>
    <div id="ledger-set">修改操作日期</div>
    <div class="menu-sep"></div>
    <div id="control-menu">修改密码</div>
</div>
<div id="main-bottom-div" class="main-bottom-div" style="display: none;">
	<div id="main-bottom-a" class="iconmouse">
		<a href="javascript:void(0);" id="tools_store" text="快捷收藏" class="tools_store">&nbsp;</a>
		<a href="javascript:void(0);" id="tools_notice" text="公告通知" class="tools_notice">&nbsp;</a>
		<a href="javascript:void(0);" id="tools_job" text="待办工作" class="tools_job">&nbsp;</a>
		<a href="javascript:void(0);" id="tools_msg" text="我的消息" class="tools_msg">&nbsp;</a>
		<a href="javascript:void(0);" id="tools_mail" text="邮件管理" class="tools_mail">&nbsp;</a>
		<a href="javascript:void(0);" id="tools_user" text="人员组织" class="tools_user">&nbsp;</a>
	</div>
</div>
<div id="tab-navigation" class="tabhead-div" style="display: none;"></div>
<div id="group-user" class="tab-user" style="display: none;">
	<div id="user_all_tree" class="ztree"></div>
</div>
<div id="tools-div-dialog"></div>
<div id="tab-showhide-button" class="tabshow" style="display: none;">&nbsp;</div>
<jsp:include page="isIe.jsp"></jsp:include>
<script type="text/javascript">
    $(function(){
        $("#sys_set").click(function(e){
            $('#menu-set').menu('show', {
                left : e.pageX,
                top : e.pageY
            });
        });
        $("#ledger-set").click(function(e){
            $('#sysUser-dialog-accountDate').dialog({
                title: '切换操作日期',
                width: 300,
                height: 200,
                closed: false,
                cache: false,
                href: "accesscontrol/showAccountDatePage.do",
                modal: true
            });
        });
        $(".iconmouse a").mouseover(function(e){
            var title = $(this).attr("text");
            var classs = $(this).attr('class');
            showTip(e,title);
            $(this).removeClass(classs);
            $(this).addClass(classs+"1");
        }).mouseout(function(){
            var classs = $(this).attr('class');
            removeTip();
            $(this).removeClass(classs);
            $(this).addClass(classs.replace("1",""));
        }).mousemove(function(e){
            moveTip(e);
        });
        $("#tools_user").click(function(e){
            initUserTreeAllList();
            var X = $(window).width();
            var Y = $(window).height();
            var height = $('#main-left').height();
            $("#group-user").css({"bottom": "15px","left":"265px"});
            $("#group-user").fadeToggle();
        });
        $("#tools_store").click(function(){
            $('<div id="tools-div-dialog-content"></div>').appendTo('#tools-div-dialog');
            $("#tools-div-dialog-content").dialog({
                title: '快捷收藏',
                width: 600,
                height: 400,
                closed: false,
                cache: false,
                href: "common/showShortcutPage.do",
                modal: true,
                buttons:[
                    {
                        iconCls:'button-add',
                        text:'添加',
                        handler:function(){
                            protal.addShortcut();
                        }
                    }
                ],
                onClose:function(){
                    $("#tools-div-dialog-content").dialog("destroy");
                }
            });
        });
        $("#tools_notice").click(function(){
            $('<div id="tools-div-dialog-content"></div>').appendTo('#tools-div-dialog');
            $("#tools-div-dialog-content").dialog({
                title: '公告通知',
                width: 600,
                height: 400,
                closed: false,
                cache: false,
                href: "message/notice/noticeIndexShowListPage.do?iscurusernotread=true",
                modal: true,
                onClose:function(){
                    showMsgNotice();
                    $("#tools-div-dialog-content").dialog("destroy");
                }
            });
        });
        $("#tools_job").click(function(){
            $('<div id="tools-div-dialog-content"></div>').appendTo('#tools-div-dialog');
            $("#tools-div-dialog-content").dialog({
                title: '待办工作',
                width: 800,
                height: 400,
                closed: false,
                cache: false,
                href: "act/workToDoPage.do",
                modal: true,
                onClose:function(){
                    showMsgNotice();
                    $("#tools-div-dialog-content").dialog("destroy");
                }
            });
        });
        $("#tools_msg").click(function(){
            $('<div id="tools-div-dialog-content"></div>').appendTo('#tools-div-dialog');
            $("#tools-div-dialog-content").dialog({
                title: '我的消息',
                width: 600,
                height: 400,
                closed: false,
                cache: false,
                href: "message/innerMessage/messageReceiveIndexListPage.do",
                modal: true,
                onClose:function(){
                    showMsgNotice();
                    $("#tools-div-dialog-content").dialog("destroy");
                }
            });
        });
        $("#tools_mail").click(function(){
            $('<div id="tools-div-dialog-content"></div>').appendTo('#tools-div-dialog');
            $("#tools-div-dialog-content").dialog({
                title: '邮件管理',
                width: 600,
                height: 400,
                closed: false,
                cache: false,
                href: "message/email/emailReceiveIndexListPage.do",
                modal: true,
                onClose:function(){
                    showMsgNotice();
                    $("#tools-div-dialog-content").dialog("destroy");
                }
            });
        });
        $("#sys_home").click(function() {
            $("#tab-navigation").hide();
            $("#tab-showhide-button").hide();
            $("#mainpage").show();
        });;
        $("#theme-menu").click(function(e){
            $('#menu-skin').menu('show', {
                left : e.pageX,
                top : e.pageY
            });
        });
        $("#control-menu").click(function(){
            var url = "accesscontrol/showModifyPwdPage.do?userid=${sysUser.userid }";
            $('#sysUser-dialog-pwdOperate').dialog({
                title: '修改密码',
                width: 300,
                height: 200,
                closed: false,
                cache: false,
                href: url,
                modal: true
            });
        });
        $("#sys_logout").click(function() {
            $.messager.confirm("提醒", "是否退出系统?", function(r) {
                if (r) {
                    var url = "logout.do";
                    $.ajax( {
                        url : url,
                        type : 'post',
                        dataType : 'json',
                        success : function(json) {
                            if (json.flag == true) {
                                location.href = "logout_safe";
                            }
                        }
                    });
                }
            });
        });
        $(".main-tree li").mouseover(function(e){
            var title = $(this).attr("text");
            showTip(e,title);
        }).mouseout(function(){
            removeTip();
        }).mousemove(function(e){
            moveTip(e)
        }).click(function(e){
            var url = $(this).attr("url");
            var module = $("#menu-module").val();
            if(module!=url && url!=""){
                loadUrl(url);
            }
            var X = $('#main-left').offset().top;
            var Y = $('#main-left').offset().left;
            var height = $('#main-left').height();
            $(".main-select").removeClass("main-select");
            $(this).addClass("main-select");
            $("#main-tree2").css({"top": (Y+60) + "px","left":(X+25)+ "px","height":height+"px","z-index":"2"});
            $("#main-tree2").animate({width: 'show'}, 500);
        });
        $(".main-tree-bottom li").mouseover(function(e){
            var title = $(this).attr("text");
            showTip(e,title);
        }).mouseout(function(){
            removeTip();
        }).mousemove(function(e){
            moveTip(e)
        });
    });
    //显示标签
    function showTip(e,title){
        var x =10;
        var y = 20;
        var tooltip="<div id='tooltip' class='main-title'>"+title+"</div>";
        $("body").append(tooltip); //追加到文档中
        var px = e.pageX;
        var py = e.pageY;
        var width = $(window).width();
        var heigth = $(window).height();
        if((px+100)>width){
            px = px-50;
        }else{
            px = px+x;
        }
        if((py+50)>heigth){
            py = py-50;
        }else{
            py = py+y;
        }
        $("#tooltip").css({"top": py + "px","left": px + "px"}).show();
    }
    //移除标签
    function removeTip(){
        $("#tooltip").remove();
    }
    //移动标签
    function moveTip(e){
        var x =10;
        var y = 20;
        var px = e.pageX;
        var py = e.pageY;
        var width = $(window).width();
        var heigth = $(window).height();
        if((px+100)>width){
            px = px-50;
        }else{
            px = px+x;
        }
        if((py+50)>heigth){
            py = py-50;
        }else{
            py = py+y;
        }
        $("#tooltip").css({"top": py + "px","left": px + "px"}).show();
    }
    function loadUrl(url){
        var leftMenuTreeSetting = {
            view : {
                dblClickExpand : false,
                showLine : false,
                showTitle:false,
                selectedMulti: false,
                showIcon : false,
                expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast"
            },
            async : {
                enable : true,
                url : "showUserMenuTree.do?url="+url,
                autoParam : [ "id", "pId", "name", "urlStr", "description","navigation" ]
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPId : ""
                }
            },
            callback : {
                //点击树状菜单更新页面按钮列表
                beforeClick : function(treeId, node) {
                    var zTree = $.fn.zTree.getZTreeObj("menu_left_tree");
                    if (node.isParent) {
                        if (node.level == 0) {
                            zTree.expandNode(node);
                        } else {
                            zTree.expandNode(node);
                        }

                    }
                    return !node.isParent;
                },
                onClick : function(e, treeId, treeNode) {
                    addTabWithNavigation("." + treeNode.urlStr, treeNode.description,treeNode.navigation);
                }
            }
        };
        setTimeout(function(){
            $.fn.zTree.init($("#menu_left_tree"), leftMenuTreeSetting, null);
            $("#menu-module").val(url);
        },100);
    }
    <c:if test="${passwordflag=='1'}">
    $.messager.alert("提醒","密码为初始密码。请先修改密码后，再进行操作！");
    modifyPwd();
    </c:if>
    <c:if test="${istry=='1'}">
    setTimeout(function(){
        $.messager.confirm('提醒',"系统未激活，正常使用天数还剩${tryday}天，将于${trydate}后限制使用。</br>请尽快联系奋德软件公司，获取永久激活使用权。",function(r){
            if (r){}
        });
    },100);
    </c:if>
    showMsgNotice();
    function adjustNavigation(){
        var navwidth = $(window).width() - 88;
        if(!tabhidden){
            navwidth = navwidth -133;
        }
        $("#tab-navigation").width(navwidth);
    }
    window.onload=function(){
        window.onresize = adjustNavigation;
        adjustNavigation();
    }
</script>
<%-- 与打印相关开始 --%>
<script type="text/javascript">
    function isPrintJobStillWork(){
        if($("div[id^='agreportprint-print-div']").size()>0){
            var d = new Date();
            var expired=5 * 60 * 1000; //最大过期时间
            var nowutc = Date.UTC(d.getFullYear()
                , d.getMonth()
                , d.getDate()
                , d.getHours()
                , d.getMinutes()
                , d.getSeconds()
                , d.getMilliseconds());
            var flag=false;
            $("div[id^='agreportprint-print-div']").each(function(i){
                var autc=$(this).attr("utc") || 0;
                autc=autc*1;
                if(autc==0){
                    return false;
                }
                if(nowutc-autc<expired){
                    flag=true;
                }
                return false;
            });
            if(flag){
                return confirm("如果您正在打印，最好不要刷新。\n如果打印已经结束，可以刷新。\n您需要强制刷新吗？");
            }
        }
        return true;
    }
</script>
<%-- 与打印相关结束 --%>
<%-- 键盘按钮相关开始 --%>
<script type="text/javascript">
    $(document).ready(function(){

        $(document).bind('keydown', 'f5',function (){
            return isPrintJobStillWork();
        });

		/*
		 $(window).unbind('beforeunload');
		 window.onbeforeunload = null;
		 $(window).bind('beforeunload',function(){
		 return '您输入的内容尚未保存，确定离开此页面吗？';
		 });
		 */
    });
</script>

<script type="text/javascript">

    //开始的服务
    var removeExpiredAgPrintTagInterval=null;
    //删除操作未进行的次数
    var removeExpiredAgPrintTagUndoCount=0;

    var removeExpiredPrintTagFunc=function(){
        var countFlag=removeExpiredAgPrintTagUndoCount;
        countFlag=(countFlag!=null ? countFlag : 0);


        var $printDiv=$("div[id^='agreportprint-print-div']");
        var expired=5 * 60 * 1000; //最大过期时间
        var diffCount=$printDiv - 500; //一次最大标签数量

        var d = new Date();
        var nowutc = Date.UTC(d.getFullYear()
            , d.getMonth()
            , d.getDate()
            , d.getHours()
            , d.getMinutes()
            , d.getSeconds()
            , d.getMilliseconds());

        if($printDiv.size()>0){
            countFlag=0;
            if(diffCount>0){
                $printDiv.each(function(i){
                    if(i<diffCount){
                        $(this).remove();
                    }else{
                        return false;
                    }

                });
            }
            $printDiv=$("div[id^='agreportprint-print-div']");
            $printDiv.each(function(i){
                var autc=$(this).attr("utc") || 0;
                autc=autc*1;
                if(autc==0){
                    return false;
                }
                if(nowutc-autc>expired){
                    $(this).remove();
                }else{
                    return false;
                }
            });
        }else{
            countFlag=countFlag+1;
        }
        //一小时进行时，停删除服务
        if(countFlag>10 && removeExpiredAgPrintTagInterval>0 ){
            countFlag=0;
            removeExpiredAgPrintTagInterval=0;
            clearInterval(removeExpiredAgPrintTagInterval);
        }
        removeExpiredAgPrintTagUndoCount=countFlag;
    };
    var startRemoveExpiredPrintTagFunc=function(){
        if(removeExpiredAgPrintTagInterval==null || removeExpiredAgPrintTagInterval <=0){
            removeExpiredAgPrintTagInterval = setInterval(function(){removeExpiredPrintTagFunc();},5*60*1000 );
        }
    }
</script>
<%-- 键盘按钮相关结束 --%>

</body>
</html>
