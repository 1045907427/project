var guidejson = [
	{ sel : $('#theme-menu'), content : '更换皮肤', position : 's' },
	{ sel : $('#control-menu'), content : '个人信息，可以修改密码', position : 's' },
    { sel : $('#sys_logout'), content : '安全退出', position : 'w' },
    { sel : $('.left-panel'), content : '菜单栏，包括菜单列表和在线人员显示',  position : 'e' },
	{ sel : $('#tt .tabs-wrap .tabs-selected'), content : '内容标签页面，可以通过不同的标签页，切换页面', position : 'n' },
	{ sel : $('#tt .tabs-wrap .tabs-selected'), content : '右键标签页，可以刷新，关闭页面等',  position : 'n',
		onTripStart:function(tripIndex){
			var e = $('#tt .tabs-wrap .tabs-selected').offset();
			$('#mm').menu('show', {
				left : e.left,
				top : e.top
			});
		}	
	}
  ]