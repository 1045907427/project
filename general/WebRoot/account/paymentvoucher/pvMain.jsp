<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>管理系统</title>
	<%@include file="/include.jsp" %>  
    <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/favicon.ico" />
  	<script type="text/javascript" src="js/jquery.cookie.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/portal/jquery.portal.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/syscode.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/trip/trip.min.js" charset="UTF-8"></script>
  	<link rel="stylesheet" href="js/trip/trip.min.css" type="text/css"></link>
  	<link rel="stylesheet" href="js/portal/portal.css" type="text/css"></link>
  	<script type="text/javascript">
	var tabRemind = new Array();			// 需要提醒的TAB的标题
	var tabRemindMsg = new Array();			// 需要提示的消息
	var tabRemindCallback = new Array();	// 关闭TAB时需要执行的回调函数
	
	$(function(){
		$("#tt").tabs({  
  			fit:true,
		    border:false,
			onBeforeClose: function(title,index){
				var target = this;
				$.messager.confirm('Confirm','Are you sure you want to close '+title,function(r){
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
							$('#tt').tabs('close',title);
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
	function addTab(url,name){
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
			$('#tt').tabs('add',{
				title:name,
				content : '<iframe url="'+url+'" tabname="'+name+'" id="'+urlmd5+'" frameborder="0" style="border:0;width:100%;height:100%;min-width: 800px;"></iframe>',
				closable:true
			});
			//刷新iframe的url地址
			tabsWindowURLMD5(urlmd5).location.href=url;
			//$.messager.progress({
			//	text : '页面加载中....',
			//	interval : 200
			//});
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
	function getNowTabTitle(){
		var title = $('#tt').tabs('getSelected').panel('options').title;
		return title;
	}
	//关闭当前标签
	function closeNowTab(){
		var tab = $('#tt').tabs('getSelected').panel('options').title;
		$('#tt').tabs('close', tab);
	}
	function closeTab(title){
		$('#tt').tabs('close', title);
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
				$('#tt').tabs('close',currTitle);
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
		$('#tt').tabs('update',{
			tab: tab,
			options: {
				title: title,
				content : '<iframe url="'+url+'" tabname="'+title+'" id="'+urlmd5+'" frameborder="0" style="border:0;width:100%;height:100%;min-width: 800px;"></iframe>',
				closable:true
			}
			
		});
		tabsWindowURLMD5(urlmd5).location.href=url;
	}
	//添加或者更新tab页面
	function addOrUpdateTab(url,title){
		if(!$('#tt').tabs('exists',title)){
			addTab(url,title);
		}else{
			updateTab(url,title);
		}
	}
	//刷新当前tab页面
	function updateThisTab(url){
		var title = $('#tt').tabs('getSelected').panel('options').title;
		updateTab(url,title);
	}
	//绑定tab的双击事件、右键事件  
	function bindTabEvent() {
		$("#tt .tabs-inner").live('dblclick', function() {
			var subtitle = $(this).children("span").text();
			if ($(this).next().is('.tabs-close')) {
				$('#tt').tabs('close', subtitle);
			}
		});
	}
	//绑定tab右键菜单事件  
	function bindTabMenuEvent() {
		//刷新当前  
		$('#mm-refresh').click(function() {
			var tab = $('#tt').tabs('getSelected');
			refresh(tab);
		});
		//全部关闭  
		$('#mm-tabcloseall').click(function() {
			$('.tabs-inner span').each(function(i, n) {
				if ($(this).parent().next().is('.tabs-close')) {
					var t = $(n).text();
					$('#tt').tabs('close', t);
				}
			});
		});
		//关闭除当前之外的TAB  
		$('#mm-tabcloseother').click(function() {
			var currtab_title = $('#mm').data("currtab");
			$('.tabs-inner span').each(function(i, n) {
				if ($(this).parent().next().is('.tabs-close')) {
					var t = $(n).text();
					if (t != currtab_title) {
						$('#tt').tabs('close', t);
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
					$('#tt').tabs('close', t);
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
					$('#tt').tabs('close', t);
				}
			});
			return false;
		});
		//退出系统
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
			showIcon : true,
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
	//在线用户列表
	var userOnlineTreeSetting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			showIcon : true,
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
				var zTree = $.fn.zTree.getZTreeObj("user_online_tree");
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
			//点击树状菜单更新页面按钮列表
			beforeDblClick : function(treeId, node) {
				var zTree = $.fn.zTree.getZTreeObj("user_online_tree");
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
	//全体用户列表
	var userAllTreeSetting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			showIcon : true,
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
					if (node.level == 0) {
						zTree.expandAll(false);
						zTree.expandNode(node);
					} else {
						zTree.expandNode(node);
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
			var editStr = "<span id='diyBtn_msg_" +treeNode.id+ "' title='短信'><img src='image/msg.png' /></span><span class='emailButton' id='diyBtn_email_" +treeNode.id+ "' title='邮件'><img src='image/email.png'/></span>";
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
	//初始化在线用户与全体用户
	function initUserTreeList(){
		var nodes = null;
		$.ajax({   
            url :'accesscontrol/getOnlineUserTreeList.do',
            type:'post',
            dataType:'json',
            async:false,
            success:function(json){
            	nodes = json;
            }
        });
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
		$.fn.zTree.init($("#user_online_tree"), userOnlineTreeSetting, nodes);
		$.fn.zTree.init($("#user_all_tree"), userAllTreeSetting, allnodes);
	}
	//初始化在线用户
	function initOnlineUserTreeList(){
		var nodes = null;
		$.ajax({   
            url :'accesscontrol/getOnlineUserTreeList.do',
            type:'post',
            dataType:'json',
            async:false,
            success:function(json){
            	nodes = json;
            }
        });
        $.fn.zTree.init($("#user_online_tree"), userOnlineTreeSetting, nodes);
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
	//初始化菜单树
	$(document).ready(function() {
	});
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
			$('#tt').tabs('close','工作详情查看');
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
	//修改密码
	function modifyPwd(){
		var url = "accesscontrol/showModifyPwdPage.do?userid=${sysUser.userid }";
		$('#sysUser-dialog-pwdOperate').dialog({  
		    title: '密码修改',  
		    width: 300,  
		    height: 200,  
		    closed: false,  
		    cache: false,  
		    href: url,  
		    modal: true
		});
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
			var $messageSendOper=$("#message-innerMessage-window-sendMessageOper");
			var url="message/innerMessage/";
			if(mtype!="reply"){
				url +="messageSendPage.do";
			}else{
				url +="messageReplyPage.do";
			}
			url +="?touserids="+uid+"&msgsendpageid=message-innerMessage-window-sendMessageOper";
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
			    modal: true
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
	  	var $messageRemindOper=$("#message-innerMessage-window-messageRemindOper");
		var url="message/innerMessage/messageReceiveReadList.do";
		var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
		setTimeout(function(){  
            iframe.attr('src', url);  
        }, 1);
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
		    modal: false
		});
		$messageRemindOper.window("open");
	}
</script>
</head>
  
  <body style="margin: 1px;" class="main-layout">
  	
  	<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false,plain:true" title="&nbsp;" style="height:80px;" class="cs-north" >
		<div style="position: absolute; right: 0px; bottom: 0px;">
		
			<div class="cs-north-span"><div class="user-icon">欢迎您，${sysUser.name }</div><div class="bumen-icon">所属部门:${sysUser.departmentname }</div>
				<a href="javascript:void(0);" id="theme-menu" class="easyui-menubutton" menu="#layout_north_pfMenu" >更换皮肤</a>
				<a href="javaScript:void(0);" id="sys_logout" class="easyui-linkbutton" data-options="plain:true">安全退出</a>  
<!-- 				<a href="javaScript:void(0);" id="sys_help" class="easyui-linkbutton" data-options="plain:true">帮助</a>   -->
			</div>
			<div id="layout_north_pfMenu" style="width: 120px; display: none;">
				<div id="theme-default" class="theme-menu" onclick="changeTheme('default');" <%if("default".equals(easyuiThemeName)){ %>iconCls="icon-ok"<%}%>>默认</div>
			</div>
			<div id="sysUser-dialog-pwdOperate"></div>
		</div>
	</div>
	<div data-options="region:'center'" class="main-panel">
		<div id="tt" tabsFirst="tabClass">
			<div title="主页" href="account/paymentvoucher/paymentVoucherListPage.do"></div>
		</div>
	</div>
	</div>
	<div id="mm" class="easyui-menu" style="width:150px;">  
        <div id="mm-refresh">刷新</div>  
        <div class="menu-sep"></div>  
        <div id="mm-tabcloseall">关闭全部</div>  
        <div id="mm-tabcloseother">关闭其他</div>  
        <div class="menu-sep"></div>  
        <div id="mm-tabcloseright">关闭右侧标签</div>  
        <div id="mm-tabcloseleft">关闭左侧标签</div>  
    </div> 
    <div id="userTreeList-tools">  
        <a href="javascript:void(0)" class="icon-mini-refresh" title="刷新" onclick="initOnlineUserTreeList()"></a>  
    </div> 
    <div id="userTreeAllList-tools">  
        <a href="javascript:void(0)" class="icon-mini-refresh" title="刷新" onclick="initUserTreeAllList()"></a>  
    </div>  
    <div id="activity-diagramPage"></div>
    <div id="activity-infoTablePage"></div>
    <div id="message-innerMessage-window-messageRemindOper"></div>
    <div id="message-innerMessage-window-sendMessageOper"></div>
    <div id="message-email-window-sendEmailOper"></div>
    <jsp:include page="/isIe.jsp"></jsp:include>
    <script type="text/javascript">
    	<c:if test="${passwordflag=='1'}">
    		$.messager.alert("提醒","密码未修改。请先修改密码后，再进行操作！");
  			modifyPwd();
  		</c:if>
  		<c:if test="${istry=='1'}">
  		setTimeout(function(){
  			$.messager.confirm('提醒',"此版本为试用版本，试用天数还剩${tryday}天",function(r){
  				if (r){
  					
  				}
  			});
  		},100);
  		</c:if>
    </script>
  </body>
</html>
