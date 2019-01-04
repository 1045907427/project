<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>品牌列表</title>
  </head>
  
  <body>
        <table  border="0" class="box_table">
            <tr>
                <td class="len80 right">涨幅:</td>
   				<td>
   					<input type="text" id="checklist-adjustPrice-rate"  name="rate"  style="width:150px;"/>
   				</td>
   				<td>1</td>
   		    </tr>
        </table>
		<table id="checklist-adjustPrice-brandlist"></table>
  		<script type="text/javascript">
  			$(function(){
  				$("#checklist-adjustPrice-brandlist").datagrid({ 
					columns:[[
						 {field:'ck',checkbox:true},
						 {field:'id',title:'编号',width:80},
						 {field:'name',title:'品牌名称',width:150}
					]],
			 		fit:true,
			 		method:'post',
			 		rownumbers:true,
			 		idField:'id',
			 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true
				});
				$("#checklist-adjustPrice-brandlist").datagrid("loadData",JSON.parse('${detailList}'));
  			});
  		</script>
  </body>
</html>
