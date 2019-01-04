<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String easyuiThemeName = "default";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("easyuiThemeName")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品档案选择列表</title>
	<base href="<%=basePath%>"></base>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>   
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="js/themes/<%=easyuiThemeName%>/easyui.css" id="easyuiTheme"/>
	<link rel="stylesheet" href="js/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="js/ztree/zTreeStyle/zTreeStyle.css" type="text/css"></link>
  	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
  	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="js/jquery.hotkeys.js"></script>
  </head>
  
  <body>
  		<div id="goodsWidget-select-table-tool">
  			<input type="text" id="goodsWidget-select-table-button" value="${id}"/>
  			<a href="javaScript:void(0);" id="goodsWidget-query-button" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询商品">查询</a>
  			<span>使用Ctrl+Enter可以进行查询</span>
  		</div>
  		<table id="goodsWidget-select-table"></table>
  		<script type="text/javascript">
  			var keytype = "down"
			$(function(){
				$("#goodsWidget-select-table").datagrid({
					frozenColumns:[[
						{field:'id',title:'编码',width:50,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#goodsWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
							}
					]],
					columns:[[
		     				{field:'name',title:'名称',width:150,
		     					formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#goodsWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
		     				},
		     				{field:'brand',title:'商品品牌',width:70,
								formatter:function(val,rowData,rowIndex){
									return rowData.brandName;
								}
							},
							{field:'barcode',title:'条形码',width:90,resizable:true,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#goodsWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
							},
							{field:'spell',title:'助记码',width:70,resizable:true,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#goodsWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
							},
		     				{field:'model',title:'规格型号',width:80,resizable:true},
							{field:'mainunit',title:'单位',width:50,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.mainunitName;
								}
		     				},
		     				{field:'boxnum',title:'箱装量',width:50,align:'right',
		     					formatter:function(val,rowData,rowIndex){
									return formatterNum(val);
								}
		     				}
		     				<c:if test="${colMap.usablenum!=null}">
		     				,
		     				{field:'newinventory',title:'可用量',width:60,align:'right',
		     					formatter:function(value,rowData,rowIndex){
									return formatterNum(value);
								}
		     				}</c:if>
					]],
				    fit:true,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					pageSize:10,
					toolbar:'#goodsWidget-select-table-tool',
					url:'basefiles/getGoodsSelectListData.do',
					queryParams:{id:'${id}',paramRule:'${paramRule}'},
					onLoadSuccess:function(data){
						if(data.rows.length>0){
			 				if(keytype=="down"){
			 					$("#goodsWidget-select-table").datagrid("selectRow",0);
			 				}else{
			 					$("#goodsWidget-select-table").datagrid("selectRow",data.rows.length-1);
			 				}
						}
						$("#goodsWidget-select-table-button").focus();
					},
					onDblClickRow:function(rowIndex, rowData){
				   		var objectStr = JSON.stringify(rowData)
				   		parent.$("#${divid}").val(rowData.name);
				   		parent.$("#${divid}-hidden").val(rowData.id);
				   		parent.$("#${divid}-hidden").attr("object",objectStr);
				   		var onSelect = parent.$("#${divid}").data("onSelect");
				   		onSelect(rowData);
				   		parent.$("#${dialog}").dialog("close",true);
					}
				});
				$("#goodsWidget-query-button").click(function(){
					var value = $("#goodsWidget-select-table-button").val();
					var query = {id:value,paramRule:'${paramRule}'};
			    	$("#goodsWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#goodsWidget-select-table-button").focus();
				});
		    });
		    $(function(){
				$(document).bind('keydown', 'up',function (){
			   		var datagridObject =  $("#goodsWidget-select-table");
			 		var rowSelected =datagridObject.datagrid("getSelected");
			 		var rowIndex = 0;
			 		if(null!=rowSelected){
			 			rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
			 			var rows =  datagridObject.datagrid("getRows").length;
			 			if(rowIndex>0){
			 				rowIndex = rowIndex-1;
			 				datagridObject.datagrid("selectRow",rowIndex);
			 			}else{
			 				var p = datagridObject.datagrid('getPager');  
			 				var pageObject = $(p).pagination("options");
			 				if(pageObject.pageNumber>1){
			 					$(p).pagination("select",pageObject.pageNumber-1);
			 					keytype = "up";
			 				}
			 			}
			 		}else{
			 			datagridObject.datagrid("selectRow",0);
			 		}
			    }).bind('keydown', 'down',function (){
			   		var datagridObject =  $("#goodsWidget-select-table");
			 		var rowSelected =datagridObject.datagrid("getSelected");
			 		var rowIndex = 0;
			 		if(null!=rowSelected){
			 			rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
			 			var rows =  datagridObject.datagrid("getRows").length;
			 			if(rowIndex<rows-1){
			 				rowIndex = rowIndex+1;
			 				datagridObject.datagrid("selectRow",rowIndex);
			 			}else{
			 				var p = datagridObject.datagrid('getPager');  
			 				var pageObject = $(p).pagination("options");
			 				var nums = pageObject.total%pageObject.pageSize;
			 				var pages = 0;
			 				if(nums>0){
			 					pages = (pageObject.total-nums)/pageObject.pageSize+1;
			 				}else{
			 					pages = (pageObject.total-nums)/pageObject.pageSize;
			 				}
			 				if(pageObject.pageNumber<pages){
			 					$(p).pagination("select",pageObject.pageNumber+1);
			 					keytype = "down";
			 				}
			 			}
			 		}else{
			 			datagridObject.datagrid("selectRow",0);
			 		}
			    }).bind('keydown','left',function(){
			    	var p = $("#goodsWidget-select-table").datagrid('getPager');  
	 				var pageObject = $(p).pagination("options");
	 				if(pageObject.pageNumber>1){
	 					$(p).pagination("select",pageObject.pageNumber-1);
	 					keytype = "up";
	 				}
			    }).bind('keydown','right',function(){
			    	var p = $("#goodsWidget-select-table").datagrid('getPager');  
	 				var pageObject = $(p).pagination("options");
	 				var nums = pageObject.total%pageObject.pageSize;
	 				var pages = 0;
	 				if(nums>0){
	 					pages = (pageObject.total-nums)/pageObject.pageSize+1;
	 				}else{
	 					pages = (pageObject.total-nums)/pageObject.pageSize;
	 				}
	 				if(pageObject.pageNumber<pages){
	 					$(p).pagination("select",pageObject.pageNumber+1);
	 					keytype = "down";
	 				}
			    }).bind('keydown', 'enter',function (){
			   		var object = $("#goodsWidget-select-table").datagrid("getSelected");
			   		var objectStr = JSON.stringify(object)
			   		parent.$("#${divid}").val(object.name);
			   		parent.$("#${divid}-hidden").val(object.id);
			   		parent.$("#${divid}-hidden").attr("object",objectStr);
			   		var onSelect = parent.$("#${divid}").data("onSelect");
			   		onSelect(object);
			   		parent.$("#${dialog}").dialog("close",true);
			    }).bind('keydown', 'esc',function (){
			    	parent.$("#${divid}").focus();
			   		parent.$("#${dialog}").dialog("close",true);
			    });
			    $("#goodsWidget-select-table-button").bind('keydown', 'ctrl+enter',function (evt){
			    	var value = $(evt.target).val();
			    	var query = {id:value,paramRule:'${paramRule}'};
			    	$("#goodsWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#goodsWidget-select-table-button").focus();
			    });
			    $("#goodsWidget-select-table-button").focus();
			    $("#goodsWidget-select-table-button").val("${id}");
			});
			parent.$("#${divid}").blur();
			//只保存数字
			function formatterNum(val){
				if(val!=null){
					return Number(val).toFixed(0);
				}else{
					return 0;
				}
			}
  		</script>
  </body>
</html>
