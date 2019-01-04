<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var portal;
	var panels;
	//首页版本。子页面添加删除后更新该版本
	//修改panels后 需要修改该版本号
	var portal_verson= "portal-state_6";
	$(function() {
		
		panels = [ {
			id : 'protal-notice',
			title : '公告通知',
			height : 200,
			collapsible : true,
			href:'message/notice/noticeIndexShowListPage.do',
			tools:[
				{  
                    iconCls:'icon-reload',  
                    handler:function(){
                    	$("#protal-notice").panel("refresh");
                    }
                }
			]
		}, {
			id : 'protal-mywork',
			title : '我的工作',
			height : 200,
			collapsible : true,
			href:'act/workToDoPage.do',
			tools:[
				{
					iconCls: 'icon-reload',
					handler:function(){
						$("#protal-mywork").panel('refresh');
					}
				}
			]
		},{
			id : 'protal-shortcut',
			title : '快捷操作',
			height : 200,
			collapsible : true,
			href:'common/showShortcutPage.do',
			tools:[
				{  
                    iconCls:'button-add',
                    handler:function(){
                    	protal.addShortcut();
                    }
                }
			]
		},{
			id : 'protal-email',
			title : '我的邮件',
			height : 200,
			collapsible : true,
			href:'message/email/emailReceiveIndexListPage.do',
			tools:[
				{  
                    iconCls:'icon-reload',  
                    handler:function(){
                    	$("#protal-email").panel("refresh");
                    }
                }
			]
		},
		{
			id : 'protal-message',
			title : '我的短消息',
			height : 200,
			collapsible : true,
			href:'message/innerMessage/messageReceiveIndexListPage.do',
			tools:[
				{  
                    iconCls:'icon-reload',  
                    handler:function(){
                    	$("#protal-message").panel("refresh");
                    }
                }
			]
		},
		{
			id : 'protal-index3',
			title : ' ',
			height : 200,
			collapsible : true,
			href:'indexmain.jsp'
		}
		];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie(portal_verson, getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie(portal_verson);
		if (!state) {
			state = 'protal-notice,protal-shortcut,protal-index3:protal-message,protal-mywork,protal-email';/*冒号代表列，逗号代表行*/
		}
		addPanels(state);
		portal.portal('resize');

	});

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function getPortalState() {
		var aa=[];
		for(var columnIndex=0;columnIndex<2;columnIndex++) {
			var cc=[];
			var panels=portal.portal('getPanels',columnIndex);
			for(var i=0;i<panels.length;i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	function addPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
<div id="portal" style="position:relative;">
	<div></div>
	<div></div>
</div>