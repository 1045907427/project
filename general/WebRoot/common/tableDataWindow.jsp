<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>级联关系数据窗口</title>
  </head>
  
  <body>
    <table id="treeDataWindow-table"></table>
    <div id="treeDataWindow-tools">
    	<form action="" method="post" id="treeDataWindow-form">
    	查询列:<select name="col" style="width: 180px;" id="treeDataWindow-select-querycol">
    				<c:forEach var="list" items="${list}">
    					<option value="${list.columnname }">${list.colchnname}</option>
    				</c:forEach>
    		   </select>
    	查询内容:<span id="treeDataWindow-input-querycontent"><input type="text" name="content" style="width: 180px;"/></span>
    	<a href="javaScript:void(0);" id="treeDataWindow-button-query" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a>
    	<a href="javaScript:void(0);" id="treeDataWindow-button-reset" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">重置</a>
    	</form>
    </div>
    <script type="text/javascript">
    	/**
		 * 初始化被选中的文件
		 */
		function selectedFile(grid,rows){
			for(var j=0;j<rows.length;j++){
				if(rows[j].leaf=='0'){
					$("#${divid} .datagrid-row[datagrid-row-index="+j+"] input[type='checkbox']").remove();
				}
			}
		}
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
		var columnJson = $("#treeDataWindow-table").createGridColumnLoad({
			name :'${name}',
			commonCol : [[
						{field:'ck',checkbox:true}
  	 					<c:forEach var="list" items="${list}" varStatus="status">,{field:'${list.columnname }',title:'${list.colchnname}',width:120}</c:forEach>
    	 	  	 	]]
		});
		$(function(){
	        $("#treeDataWindow-table").datagrid({
	    		authority:columnJson,
	  	 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
	    		method:'post',
	    	 	fit:true,
	   	 	  	rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:${singleSelect},
				url:'common/getWidgetTableDataList.do?name=${name}&tree=${tree}&paramRule=${paramRule}',
				<c:if test="${singleSelect=='true'}">
				onDblClickRow:function(rowIndex, rowData){
					<c:if test="${onlyLeafCheck=='true'}">
					var leaf = rowData.leaf;
					if(leaf=='0'){
						$.messager.alert("提醒",'父节点不能选择!');
						$("#treeDataWindow-table").datagrid("unselectRow",rowIndex);
						return false;
					}
					</c:if>
					<c:if test="${state=='combo'}">
					$("#${vid}").combogrid("clear");
					$("#${vid}").combogrid("setValue",rowData.${key});
					</c:if>
					<c:if test="${state=='combotree'}">
					$("#${vid}").combotree("clear");
					$("#${vid}").combotree("setValue",rowData.${key});
					</c:if>
					$("#${divid}").window("destroy");
				},
				</c:if>
				<c:if test="${onlyLeafCheck=='true'}">
				onClickRow:function(rowIndex, rowData){
					var leaf = rowData.leaf;
					if(leaf=='0'){
						$("#treeDataWindow-table").datagrid("unselectRow",rowIndex);
					}
				},
				rowStyler: function(index,row){  
                    if (row.leaf =='0'){  
                        return 'background-color:#CCC;color:#fff;';  
                    }  
                },
				</c:if>
				toolbar:'#treeDataWindow-tools',
				onLoadSuccess:function(loadData){
					<c:if test="${onlyLeafCheck=='true'}">
					selectedFile($(this),loadData.rows);
					</c:if>
			    	var p = $('#treeDataWindow-table').datagrid('getPager');  
				    $(p).pagination({  
				        buttons:[{  
				        	text:'确定',
				            iconCls:'button-save',
				            handler:function(){  
				                var data = $("#treeDataWindow-table").datagrid('getSelected');
				                if(data==null){
			    					$.messager.alert("提醒","请选择数据！");
						    		return false;
						    	}
					    	<c:if test="${singleSelect=='true'}">
						    	var value = $("#treeDataWindow-table").datagrid('getSelected');
					    		<c:if test="${state=='combo'}">
					    		$("#${vid}").combogrid("clear");
								$("#${vid}").combogrid("setValue",value.${key});
								</c:if>
								<c:if test="${state=='combotree'}">
								$("#${vid}").combotree("clear");
								$("#${vid}").combotree("setValue",value.${key});
								</c:if>
				    		</c:if>
					    	<c:if test="${singleSelect=='false'}">
						    	var rows = $("#treeDataWindow-table").datagrid('getSelections');
						    	//combogrid控件
						    	<c:if test="${state=='combo'}">
						    	var array = new Array();
						    	for(var i=0;i<rows.length;i++){
						    		array.push(rows[i].${key});
						    	}
						    	//执行取消选择的事件
						    	var oldRows = $("#${vid}").combogrid("grid").datagrid('getSelections');
						    	var options = $("#widgetData-${vid}").data("options");
						    	for(var i=0;i<oldRows.length;i++){
						    		options.onUnselect(oldRows[i]);
						    	}
						    	$("#${vid}").combogrid("clear");
								$("#${vid}").combogrid("setValues",array);
								</c:if>
								//下拉树combotree
								<c:if test="${state=='combotree'}">
						    	var array = new Array();
						    	for(var i=0;i<rows.length;i++){
						    		<c:if test="${onlyLeafCheck=='true'}">
						    		if(rows[i].leaf!='0'){
						    			array.push(rows[i].${key});
						    		}
						    		</c:if>
						    		<c:if test="${onlyLeafCheck=='false'}">
						    			array.push(rows[i].${key});
						    		</c:if>
						    	}
						    	$("#${vid}").combotree("clear");
								$("#${vid}").combotree("setValues",array);
								</c:if>
					    	</c:if>
					    		$("#${divid}").window("destroy");
				            }  
				        }]
				    });
		    	}
	        }).datagrid("columnMoving");
	        //查询
	        $("#treeDataWindow-button-query").click(function(){
	        	//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#treeDataWindow-form").serializeJSON();
	       		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	       		$("#treeDataWindow-table").datagrid({
	       			queryParams:queryJSON
	       		});
	        });
	        //重置
	        $("#treeDataWindow-button-reset").click(function(){
	        	$("#treeDataWindow-form")[0].reset();
	       		$("#treeDataWindow-table").datagrid({
	       			queryParams:null
	       		});
	        });
	        //查询列变更
        	$("#treeDataWindow-select-querycol").change(function(){
        		treeDataWindowQuerycontentInit();
        	});
		});
		 //查询内容初始化以及变更
        function treeDataWindowQuerycontentInit(){
        	//获取数据窗口属于编码类型的字段列表
        	var type = [${codeColumn}];
      		var col = $("#treeDataWindow-select-querycol").val();
      		var flag = false;
      		for(var i=0;i<type.length;i++){
      			if(col==type[i]){
      				flag = true;
      				break;
      			}
      		}
      		if(flag){
      			var html = '<input id="treeDataWindow-combogrid-querycontent" type="text" name="content" style="width: 200px;"/>';
      			$("#treeDataWindow-input-querycontent").html(html);
      			$("#treeDataWindow-combogrid-querycontent").combogrid({  
				    panelWidth:400,
	           		url:'common/sysCodeList.do?type='+col,
	           		idField:'id',  
				    textField:'name', 
				    columns:[[  
				        {field:'id',title:'编码',width:150},  
				        {field:'name',title:'编码名称',width:200}
				    ]]
				}); 
				$.parser.parse("#treeDataWindow-input-querycontent");
      		}else{
      			var html = '<input type="text" name="content" style="width: 200px;"/>';
      			$("#treeDataWindow-input-querycontent").html(html);
      		}
        }
        //初始化查询内容
       setTimeout(treeDataWindowQuerycontentInit,50);
    </script>
  </body>
</html>
