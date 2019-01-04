<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>仓库下的品牌列表</title>
  </head>
  
  <body>
		<div id="checklist-storage-goodssort" class="ztree"></div>
  		<script type="text/javascript">
	  	//树型
	  		var waresClassTreeSetting = {
	  			view: {
	  				dblClickExpand: false,
	  				showLine: true,
	  				selectedMulti: false,
	  				showIcon:true,
	  				expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
	  			},
	  			check: {
					enable: true,
					chkStyle: 'checkbox',
					chkboxType: { "Y": "s", "N": "s" },
					radioType:'all'
				},
	  			async: {
	  				enable: true,
	  				url: "basefiles/getWaresClassTreeOpenList.do",
	  				autoParam: ["id","parentid", "text","state"]
	  			},
	  			data: {
	  				key:{
	  					name:"text",
	  					title:"text"
	  				},
	  				simpleData: {
	  					enable:true,
	  					idKey: "id",
	  					pIdKey: "parentid",
	  					rootPId: null
	  				}
	  			}
	  		};
  			$(function(){
  				$.fn.zTree.init($("#checklist-storage-goodssort"), waresClassTreeSetting,null);
  			});
  		</script>
  </body>
</html>
