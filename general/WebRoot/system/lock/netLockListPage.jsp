<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>网络互斥解锁</title>
	<%@include file="/include.jsp" %>  
  </head>
  
  <body>
    <table id="netLock-table-list"></table>
    <div id="netLock-query" style="height: auto;padding: 0px">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="netLock-button-unlock" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-open'">解锁</a>
        </div>
    	<form action="" id="netLock-form" method="post">
            <table class="querytable">
            <tr>
                <td>业务名称:</td>
                <td><input id="netLock-tablename" name="tablename"></td>
			    <td>数据编号:</td>
                <td><input name="lockid" style="width:200px"></td>
                <%--<td>加锁人所属部门:</td>--%>
                <%--<td><input name="lockdeptname" style="width:120px"></td>--%>
            </tr>
            <tr>
			<td>加 锁 人:</td>
            <td><input name="lockname" style="width:200px"></td>
            <td>是否超时:</td>
            <td>
            <select name="isovertime" style="width: 200px;">
            <option></option>
            <option value="1">是</option>
            <option value="0">否</option>
            </select>
            </td>
                <td colspan="2" style="padding-left: 35px">
                <a href="javaScript:void(0);" id="netLock-button-query" class="button-qr">查询</a>
                <a href="javaScript:void(0);" id="netLock-button-reload" class="button-qr">重置</a>
                </td>
            </tr>
            </table>
		</form>
    </div>
    <script type="text/javascript">
    	$(function(){
            $("#netLock-tablename").widget({
                referwid:'RL_T_BASE_SYS_TABLEINFO',
                singleSelect:true
            });

    		$('#netLock-table-list').datagrid({ 
				columns:[[
						{field:'ck',checkbox:true},
						{field:'name',title:'业务名称',width:100,sortable:true},
						{field:'lockid',title:'数据编号',width:150},
						{field:'locktime',title:'加锁时间',width:120,sortable:true},
						{field:'lockname',title:'加锁人',width:100,sortable:true},
						{field:'lockdeptname',title:'加锁人所属部门',width:150,sortable:true},
						{field:'lockmins',title:'加锁人时长',width:80,
							formatter:function(val){
								return val+'分钟';
							}
						},
						{field:'isovertime',title:'是否超时',width:60,
							formatter:function(val){
				        		if(val=='1'){
				        			return '是';
				        		}else if(val=='0'){
				        			return '否';
				        		}
				        	}
						}
				]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
	  	 		sortName:'locktime',
	  	 		sortOrder:'desc',
				toolbar:'#netLock-query',
			    url:'system/lock/showNetLockList.do'
			});
			
			//回车事件
			controlQueryAndResetByKey("netLock-button-query","netLock-button-reload");
			
			//查询
			$("#netLock-button-query").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#netLock-form").serializeJSON();
	       		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	       		$("#netLock-table-list").datagrid("load",queryJSON);
			});
			//重置
			$("#netLock-button-reload").click(function(){
				$("#netLock-form")[0].reset();
	       		$("#netLock-table-list").datagrid("load",{});
			});
			//解锁
			$("#netLock-button-unlock").click(function(){
				var locks = $("#netLock-table-list").datagrid('getChecked');
		    	if(locks.length==0){
		    		$.messager.alert("提醒","请选择数据！");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否解锁选中的数据?", function(r){
					if (r){
				    	var ids = null;
				    	for(var i=0;i<locks.length;i++){
				    		if(ids==null){
				    			ids = locks[i].id;
				    		}else{
				    			ids += ","+locks[i].id;
				    		}
				    	}
				    	$.ajax({   
				            url :'system/lock/deleteNetLocks.do?ids='+ids,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","解锁成功！");
				            		$('#netLock-table-list').datagrid('clearSelections');
				            		$('#netLock-table-list').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","解锁失败！");
				            	}
				            }
				        });
				    }
				});
			});
    	});
    </script>
  </body>
</html>
