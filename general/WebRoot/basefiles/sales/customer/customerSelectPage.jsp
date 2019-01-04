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
  		<div id="customerWidget-select-table-tool">
  			<input type="text" id="customerWidget-select-table-button" value="${id}"/>
  			<a href="javaScript:void(0);" id="customerWidget-query-button" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询客户">查询</a>
  			<span>使用Ctrl+Enter可以进行查询</span>
  		</div>
  		<table id="customerWidget-select-table"></table>
  		<script type="text/javascript">
  			var keytype = "down"
			$(function(){
				$("#customerWidget-select-table").datagrid({
					frozenColumns:[[
						{field:'id',title:'编号',width:50,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var id = $("#customerWidget-select-table-button").val();
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
		     				{field:'name',title:'客户名称',width:100,
		     					formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#customerWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
		     				},
							{field:'shortcode',title:'助记码',width:70,resizable:true,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#customerWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
							},
							{field:'contactname',title:'对方联系人',width:70},
							{field:'salesdeptname',title:'销售部门',width:70},
							{field:'salesusername',title:'客户业务员',width:70},
							{field:'salesareaname',title:'所属区域',width:80,resizable:true},
							{field:'customersortname',title:'所属分类',width:80}
							
					]],
				    fit:true,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					toolbar:'#customerWidget-select-table-tool',
					url:'basefiles/getCustomerSelectListData.do',
					queryParams:{id:'${id}',ishead:'${ishead}',paramRule:'${paramRule}'},
					onLoadSuccess:function(data){
						if(data.rows.length>0){
			 				if(keytype=="down"){
			 					$("#customerWidget-select-table").datagrid("selectRow",0);
			 				}else{
			 					$("#customerWidget-select-table").datagrid("selectRow",data.rows.length-1);
			 				}
						}
						$("#customerWidget-select-table-button").focus();
					},
					onDblClickRow:function(rowIndex, rowData){
				   		var objectStr = JSON.stringify(rowData)
				   		parent.$("#${divid}").val(rowData.name);
				   		parent.$("#${divid}-hidden").val(rowData.id);
				   		parent.$("#${divid}-hidden").attr("object",objectStr);
				   		var onSelect = parent.$("#${divid}").data("onSelect");
				   		onSelect(rowData);
				   		parent.$("#${divid}-search-div").dialog("close",true);
					}
				});
				$("#customerWidget-query-button").click(function(){
					var value = $("#customerWidget-select-table-button").val();
			    	var query = {id:value,ishead:'${ishead}',paramRule:'${paramRule}'};
			    	$("#customerWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#customerWidget-select-table-button").focus();
				});
		    });
		    $(function(){
				$(document).bind('keydown', 'up',function (){
			   		var datagridObject =  $("#customerWidget-select-table");
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
			   		var datagridObject =  $("#customerWidget-select-table");
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
			    	var p = $("#customerWidget-select-table").datagrid('getPager');  
	 				var pageObject = $(p).pagination("options");
	 				if(pageObject.pageNumber>1){
	 					$(p).pagination("select",pageObject.pageNumber-1);
	 					keytype = "up";
	 				}
			    }).bind('keydown','right',function(){
			    	var p = $("#customerWidget-select-table").datagrid('getPager');  
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
			   		var object = $("#customerWidget-select-table").datagrid("getSelected");
			   		var objectStr = JSON.stringify(object)
			   		parent.$("#${divid}").val(object.name);
			   		parent.$("#${divid}-hidden").val(object.id);
			   		parent.$("#${divid}-hidden").attr("object",objectStr);
			   		var onSelect = parent.$("#${divid}").data("onSelect");
			   		onSelect(object);
			   		parent.$("#${divid}-search-div").dialog("close",true);
			    }).bind('keydown', 'esc',function (){
			    	parent.$("#${divid}").focus();
			   		parent.$("#${divid}-search-div").dialog("close",true);
			    });
			    $("#customerWidget-select-table-button").bind('keydown', 'ctrl+enter',function (evt){
			    	var value = $(evt.target).val();
			    	var query = {id:value,ishead:'${ishead}',paramRule:'${paramRule}'};
			    	$("#customerWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#customerWidget-select-table-button").focus();
			    });
			    $("#customerWidget-select-table-button").focus();
			    $("#customerWidget-select-table-button").val("${id}");
			});
			parent.$("#${divid}").blur();
  		</script>
  </body>
</html>
