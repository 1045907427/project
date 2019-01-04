<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" title="科目" data-options="fit:true" id="basefiles-layout-subjectType">
    	<div data-options="region:'west',split:true" title="科目分类" style="width:300px;">
    		<table id="basefiles-table-subjectTypeList"></table>
    		<div id="basefiles-table-subjectTypeListBtn" style="padding:2px;height:auto">
    			<form action="" id="basefiles-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>名称：<input type="text" name="name" class="len150"/></td>
	    				</tr>
				   		<tr>
				   			<td>
	    						<a href="javaScript:void(0);" id="basefiles-subjectTypeList-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="basefiles-subjectTypeList-query-reloadList" class="button-qr">重置</a>
	    					</td>	
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'center',border:true" title="">
    		<div class="easyui-panel" data-options="fit:true,cache:false,maximized:true,border:false" id="basefiles-layout-subjectType-panel">
    		</div>
    	</div>
    </div>
	<div style="display:none">
    	<div id="basefiles-subjectTypeListPage-dialog-operate"></div>
    </div>
    <script type="text/javascript">

	   
	    function refreshADMOperDialog(title, url){
	    	$('<div id="basefiles-subjectTypeListPage-dialog-operate-content"></div>').appendTo("#basefiles-subjectTypeListPage-dialog-operate");
	   		$('#basefiles-subjectTypeListPage-dialog-operate-content').dialog({  
			    title: title,  
			    width: 400,  
			    height: 350,  
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#basefiles-subjectTypeListPage-dialog-operate-content').window("destroy");
			    }
			});
			$('#basefiles-subjectTypeListPage-dialog-operate-content').dialog('open');
	   	}
	    function showSubjectTypeViewDialog(id){
	    	refreshADMOperDialog("科目分类【详情】", 'basefiles/subject/showSubjectTypeViewPage.do?id='+id);
	    }
    	$(document).ready(function(){
    		var initQueryJSON=$("#basefiles-form-ListQuery").serializeJSON();
			//回车事件
			controlQueryAndResetByKey("basefiles-subjectTypeList-query-List","basefiles-subjectTypeList-query-reloadList");
			
			//查询
			$("#basefiles-subjectTypeList-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#basefiles-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#basefiles-table-subjectTypeList").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#basefiles-subjectTypeList-query-reloadList").click(function(){
				$("#basefiles-form-ListQuery")[0].reset();
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#basefiles-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#basefiles-table-subjectTypeList").datagrid('load',queryJSON);
			});
			
			$("#basefiles-table-subjectTypeList").datagrid({ 
				columns:[[
		    		{field:'id',title:'编码',width:50,sortable:true},
	  				{field:'name',title:'科目名称',width:150,
		    			formatter:function(val,rowData,rowIndex){
	  						return "<a href=\"javascript:void(0);\" onclick=\"javascript:showSubjectTypeViewDialog('"+rowData.id+"')\" >"+val+"</a>";
	  					}
		    		},
		    		{field:'typecode',title:'类别代码',width:130,hidden:true},
	  				{field:'state',title:'状态',width:60,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '禁用';
	  						}
	  					}
	  				},
					{field:'remark',title:'备注',width:150,sortable:true,hidden:true},
					{field:'adduserid',title:'添加者编码',width:80,sortable:true,hidden:true},
					{field:'addusername',title:'添加者姓名',width:80,sortable:true,hidden:true},
					{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true,
						formatter:function(val,rowData,rowIndex){
							if(val){
								return val.replace(/[tT]/," ");
							}
						}
					}
				]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: false,
	  	 		rownumbers:true,
	  	 		sortName:'id',
	  	 		sortOrder:'asc',
	  	 		pagination:false,
		 		idField:'id',
	  	 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:100,
				toolbar:'#basefiles-table-subjectTypeListBtn',
				pageList:[50,100,200,300],
				url: 'basefiles/subject/showSubjectTypePageList.do?isNoPageflag=true',
		 		queryParams:initQueryJSON,
		    	onClickRow:function(rowIndex, rowData){
		    		if(null==rowData || null==rowData.id || ""==rowData.id){
						$.messager.alert("警告","该科目分类编码信息有误");
						$("#basefiles-layout-subjectType-panel").panel('clear');
						return true;
		    		}
		    		
		    		$("#basefiles-layout-subjectType-panel").panel('refresh', 'basefiles/subject/showSubjectSubPage.do?typeid='+rowData.id);
		    	}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
