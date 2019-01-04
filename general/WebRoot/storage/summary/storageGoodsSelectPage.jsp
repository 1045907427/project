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
  	<script type="text/javascript" src="js/jqueryUtils.js"></script>
  	<script type="text/javascript" src="js/jquery.hotkeys.js"></script>
  </head>
  
  <body>
  		<div id="storageGoodsWidget-select-table-tool">
            <input type="text" id="storageGoodsWidget-select-table-button" value="${id}"/>
            <a href="javaScript:void(0);" id="storageGoodsWidget-query-button" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询仓库商品">查询</a>
            <span>使用Ctrl+Enter可以进行查询</span>
        </div>
  		<table id="storageGoodsWidget-select-table"></table>
  		<script type="text/javascript">
  			var keytype = "down"
			$(function(){
				$("#storageGoodsWidget-select-table").datagrid({
					frozenColumns:[[
						{field:'goodsid',title:'商品编码',width:60,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#storageGoodsWidget-select-table-button").val();
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
		     				{field:'goodsname',title:'名称',width:100},
		     				{field:'brand',title:'商品品牌',width:70,
								formatter:function(val,rowData,rowIndex){
									return rowData.brandname;
								}
							},
							{field:'barcode',title:'条形码',width:90,resizable:true,
								formatter:function(val,rowData,rowIndex){
									if(val!=null){
										var id = $("#storageGoodsWidget-select-table-button").val();
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
										var id = $("#storageGoodsWidget-select-table-button").val();
										var str = '<span style="color:red;">'+id+'</span>';
										val = val.replace(id,str);
										return val;
									}else{
										return "";
									}
								}
							},
		     				{field:'model',title:'规格型号',width:70},
							{field:'unitid',title:'单位',width:30,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.unitname;
								}
		     				},
		     				{field:'boxnum',title:'箱装量',width:50,align:'right',
		     					formatter:function(val,rowData,rowIndex){
									return formatterBigNumNoLen(val);
								}
		     				},
		     				<c:if test="${fieldMap.existingnum!=null}">
		     				{field:'existingnum',title:'现存量',width:60,align:'right',
		     					formatter:function(val,rowData,rowIndex){
					        		return formatterBigNumNoLen(val);
						        }
		     				},
		     				</c:if>
		     				<c:if test="${fieldMap.usablenum!=null}">
		     				{field:'usablenum',title:'可用量',width:60,align:'right',
		     					formatter:function(val,rowData,rowIndex){
					        		return formatterBigNumNoLen(val);
						        }
		     				},
		     				</c:if>
		     				
		     				{field:'defaultstoragename',title:'默认仓库',width:80},
		     				{field:'storageid',title:'所属仓库',width:80,
		     					formatter:function(val,rowData,rowIndex){
					        		return rowData.storagename;
						        }
		     				},
		     				{field:'storagelocationid',title:'所属库位',width:80,
		     					formatter:function(val,rowData,rowIndex){
					        		return rowData.storagelocationname;
						        }
		     				},
		     				{field:'batchno',title:'批次号',width:80}
					]],
				    fit:true,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'summarybatchid',
					singleSelect:true,
					pageSize:10,
					pageList:[10,20,30,50],
					toolbar:'#storageGoodsWidget-select-table-tool',
					url:'storage/getStorageGoodsSelectListData.do',
					queryParams:{id:'${id}',paramRule:'${paramRule}'},
					onLoadSuccess:function(data){
						if(data.rows.length>0){
			 				if(keytype=="down"){
			 					$("#storageGoodsWidget-select-table").datagrid("selectRow",0);
			 				}else{
			 					$("#storageGoodsWidget-select-table").datagrid("selectRow",data.rows.length-1);
			 				}
						}
						$("#storageGoodsWidget-select-table-button").focus();
					},
					onDblClickRow:function(rowIndex, rowData){
				   		var objectStr = JSON.stringify(rowData)
				   		parent.$("#${divid}").val(rowData.goodsname);
				   		parent.$("#${divid}-hidden").val(rowData.goodsid);
				   		parent.$("#${divid}-hidden").attr("object",objectStr);
				   		var onSelect = parent.$("#${divid}").data("onSelect");
				   		onSelect(rowData);
				   		parent.$("#${dialog}").dialog("close",true);
					}
				});
				$("#storageGoodsWidget-query-button").click(function(){
					var value = $("#storageGoodsWidget-select-table-button").val();
			    	var query = {id:value,paramRule:'${paramRule}'};
			    	$("#storageGoodsWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#storageGoodsWidget-select-table-button").focus();
				});
		    });
		    $(function(){
				$(document).bind('keydown', 'up',function (){
			   		var datagridObject =  $("#storageGoodsWidget-select-table");
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
			   		var datagridObject =  $("#storageGoodsWidget-select-table");
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
			    	var p = $("#storageGoodsWidget-select-table").datagrid('getPager');  
	 				var pageObject = $(p).pagination("options");
	 				if(pageObject.pageNumber>1){
	 					$(p).pagination("select",pageObject.pageNumber-1);
	 					keytype = "up";
	 				}
			    }).bind('keydown','right',function(){
			    	var p = $("#storageGoodsWidget-select-table").datagrid('getPager');  
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
			   		var object = $("#storageGoodsWidget-select-table").datagrid("getSelected");
			   		var objectStr = JSON.stringify(object)
			   		parent.$("#${divid}").val(object.goodsname);
			   		parent.$("#${divid}-hidden").val(object.goodsid);
			   		parent.$("#${divid}-hidden").attr("object",objectStr);
			   		var onSelect = parent.$("#${divid}").data("onSelect");
			   		onSelect(object);
			   		parent.$("#${dialog}").dialog("close",true);
			    }).bind('keydown', 'esc',function (){
			    	parent.$("#${divid}").focus();
			   		parent.$("#${dialog}").dialog("close",true);
			    });
			    $("#storageGoodsWidget-select-table-button").bind('keydown', 'ctrl+enter',function (evt){
			    	var value = $(evt.target).val();
			    	var query = {id:value,paramRule:'${paramRule}'};
			    	$("#storageGoodsWidget-select-table").datagrid({
			    		queryParams:query
			    	});
			    	$("#storageGoodsWidget-select-table-button").focus();
			    });
			    $("#storageGoodsWidget-select-table-button").focus();
			    $("#storageGoodsWidget-select-table-button").val("${id}");
			});
			parent.$("#${divid}").blur();
  		</script>
  </body>
</html>
