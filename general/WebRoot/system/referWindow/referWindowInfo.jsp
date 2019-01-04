<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>参照窗口</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  
  <body>
    <table id="referWindow-table-viewdata"></table>
    <div id="referWindow-tools-viewdata">
    	<form action="" method="post" id="referWindow-form-viewdata">
    	查询列:<select name="col" style="width: 180px;" id="referWindow-input-querycol">
    				<c:forEach var="list" items="${columnList}">
    					<c:if test="${list.isquote !='4'}">
    					<option value="${list.col }">${list.colname}</option>
    					</c:if>
    				</c:forEach>
    		   </select>
    	查询内容:<span id="referWindow-input-querycontent"><input type="text" name="content" style="width: 180px;"/></span>
    	<a href="javaScript:void(0);" id="referWindow-button-viewdata-query" class="button-qr" style="width: 60px;">查询</a>
    	<a href="javaScript:void(0);" id="referWindow-button-viewdata-reset" class="button-qr" style="width: 60px;">重置</a>
    	</form>
    </div>
    <script type="text/javascript">
   		//根据初始的列与用户保存的列生成以及字段权限生成新的列
        $(function(){
        	$("#referWindow-table-viewdata").datagrid({
	  	 		frozenColumns: [[{field:'ck',checkbox:true}]],
				columns:[[
                    <c:forEach var="list" items="${columnList}" varStatus="status">
                        <c:if test="${(status.index+1)<size}">{field:'${list.col }',title:'${list.colname}',width:${list.width}<c:if test="${list.isquote =='4'}">,hidden:true</c:if>},</c:if>
                        <c:if test="${(status.index+1)==size}">{field:'${list.col }',title:'${list.colname}',width:${list.width}<c:if test="${list.isquote =='4'}">,hidden:true</c:if>}</c:if>
                    </c:forEach>
                ]],
	    		method:'post',
	    	 	fit:true,
	   	 	  	rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'${key}',
	  	 		singleSelect:${singleSelect},
				pageSize:10,
				checkOnSelect:true,
				selectOnCheck:true,
				pageList:[10,20,30,50],
				url:'system/referWindow/getReferWindowData.do?id=${id}&paramRule=${paramRule}',
				<c:if test="${vid!=null}">
				<c:if test="${singleSelect=='true'}">
				onDblClickRow:function(rowIndex, rowData){
					<c:if test="${state=='combo'}">
		    		var data = $("#${vid}").combogrid("grid").datagrid("getData").rows;
		    		var flag = true;
		    		for(var i=0;i<data.length;i++){
		    			if(data[i].${key}==rowData.${key}){
		    				flag = false;
		    				break;
		    			}
		    		}
		    		if(flag){
		    			data.push(rowData);
		    		}
	 		 		
	 		 		$("#${vid}").combogrid("grid").datagrid("loadData",data);
					$("#${vid}").combogrid("clear");
					$("#${vid}").combogrid("setValue",rowData.${key});
					</c:if>
					$("#${divid}").window("destroy");
				},
				</c:if>
				onLoadSuccess:function(loadData){
			    	var p = $('#referWindow-table-viewdata').datagrid('getPager');  
				    $(p).pagination({  
				        buttons:[{  
				        	text:'确定',
				            iconCls:'button-save',
				            handler:function(){  
				                var refer = $("#referWindow-table-viewdata").datagrid('getSelected');
				                if(refer==null){
			    					$.messager.alert("提醒","请选择数据！");
						    		return false;
						    	}
					    	<c:if test="${singleSelect=='true'}">
						    	var value = $("#referWindow-table-viewdata").datagrid('getSelected');
					    		<c:if test="${state=='combo'}">
					    		var data = $("#${vid}").combogrid("grid").datagrid("getData").rows;
					    		var flag = true;
					    		for(var i=0;i<data.length;i++){
					    			if(data[i].${key}==value.${key}){
					    				flag = false;
					    				break;
					    			}
					    		}
					    		if(flag){
					    			data.push(value);
					    		}
				 		 		$("#${vid}").combogrid("grid").datagrid("loadData",data);
					    		$("#${vid}").combogrid("clear");
								$("#${vid}").combogrid("setValue",value.${key});
								</c:if>
				    		</c:if>
					    	<c:if test="${singleSelect=='false'}">
					    		var data = $("#${vid}").combogrid("grid").datagrid("getData").rows;
						    	var rows = $("#referWindow-table-viewdata").datagrid('getSelections');
						    	<c:if test="${state=='combo'}">
						    	var array = new Array();
						    	for(var i=0;i<data.length;i++){
						    		var addFlag = true;
					    			for(var j=0;j<rows.length;j++){
					    				if(data[i].${key}==rows[j].${key}){
					    					addFlag = false;
					    				}
							    	}
							    	if(addFlag){
							    		array.push(rows[i].${key});
							    	}
					    		}
						    	
						    	//执行取消选择的事件
						    	var oldRows = $("#${vid}").combogrid("grid").datagrid('getSelections');
						    	var options = $("#widgetData-${vid}").data("options");
						    	for(var i=0;i<oldRows.length;i++){
						    		options.onUnselect(oldRows[i]);
						    	}
				 		 		data.concat(rows);
				 		 		$("#${vid}").combogrid("grid").datagrid("loadData",data);
						    	$("#${vid}").combogrid("clear");
								$("#${vid}").combogrid("setValues",array);
								</c:if>
					    	</c:if>
					    	$("#${divid}").window("destroy");
				            }  
				        }]
				    });
		    	},
		    	</c:if>
		    	toolbar:'#referWindow-tools-viewdata'
	        }).datagrid("columnMoving");
        	//查询
        	$("#referWindow-button-viewdata-query").click(function(){
        		//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#referWindow-form-viewdata").serializeJSON();
	       		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	       		$("#referWindow-table-viewdata").datagrid({
	       			queryParams:queryJSON
	       		});
        	});
        	//重置
        	$("#referWindow-button-viewdata-reset").click(function(){
        		$("#referWindow-form-viewdata")[0].reset();
	       		$("#referWindow-table-viewdata").datagrid({
	       			queryParams:null
	       		});
	       		referWindowQuerycontentInit();
        	});
        	//查询列变更
        	$("#referWindow-input-querycol").change(function(){
        		referWindowQuerycontentInit();
        	});
        });
        //初始化查询内容
        setTimeout(referWindowQuerycontentInit,50);
        //查询内容初始化以及变更
        function referWindowQuerycontentInit(){
        	//获取数据窗口属于编码类型的字段列表
        	var type = [
      		<c:forEach var="list" items="${columnList}"><c:if test="${list.codetype!=null&&list.codetype!=''}">'${list.col}',</c:if></c:forEach>null
      		];
      		var col = $("#referWindow-input-querycol").val();
      		var flag = false;
      		for(var i=0;i<type.length;i++){
      			if(col==type[i]){
      				flag = true;
      				break;
      			}
      		}
      		if(flag){
      			var html = '<input id="referWindow-combogrid-querycontent" type="text" name="content" style="width: 200px;"/>';
      			$("#referWindow-input-querycontent").html(html);
      			$("#referWindow-combogrid-querycontent").combogrid({  
			    panelWidth:400,
           		url:'common/sysCodeList.do?type='+col,
           		idField:'id',  
			    textField:'name', 
			    columns:[[  
			        {field:'id',title:'编码',width:150},  
			        {field:'name',title:'编码名称',width:200}
			    ]]
			}); 
			$.parser.parse("#referWindow-input-querycontent");
      		}else{
      			var html = '<input type="text" name="content" style="width: 200px;"/>';
      			$("#referWindow-input-querycontent").html(html);
      		}
        }
    </script>
  </body>
</html>
