<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收款单指定客户</title>
  </head>
  
  <body>
  	<form  method="post" id="assignCustomer-form">
  		<table>
  			<tr>
  				<td>收款单金额：</td>
  				<td>
  					<input id="assignCustomer-collectorderid" type="hidden" name="id" value="${id}"/>
  					<input type="text" id="collectAmount" class="easyui-numberbox" data-options="precision:2" style="width: 150px;" readonly="readonly" value="${collectionOrder.amount}"/>
  				</td>
  				<td>未分配金额：</td>
  				<td>
  					<input type="text" id="unusecollectAmount" class="easyui-numberbox" data-options="precision:2" style="width: 150px;" readonly="readonly" value="${collectionOrder.amount}"/>
  				</td>
  				<td>已分配金额：</td>
  				<td>
  					<input type="text" id="usecollectAmount" class="easyui-numberbox" data-options="precision:2" style="width: 150px;" readonly="readonly" value="0"/>
  				</td>
  			</tr>
  		</table>
  	</form>
  	<table id="assignCustomer-table"></table>
  	<script type="text/javascript">
  		var editIndex;
  		$(function(){
  			$("#assignCustomer-table").datagrid({
  				columns: [[
  					{field:'customername', title:'客户编码',width:60,
   						formatter:function(value,row,index){
   							return row.customerid;
				        }
				    },
  					{field:'customerid', title:'客户名称',width:300,
   						formatter:function(value,row,index){
   							return row.customername;
				        },
				        editor:{
				        	type:'customerwidget',
				        	options:{
				        		singleSelect:true,
								ishead:true,
								isall:true,
								isopen:true,
								width:280
				        	}
				        }
			    	},
			    	{field:'amount', title:'指定金额',width:100,align:'right',
   						formatter:function(value,row,index){
   							return formatterMoney(value);
				        },
				        editor:{
				        	type:'numberbox',
				        	options:{
				        		min:0,
				        		precision:2
				        	}
				        }
			    	}
  				]],
  				border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:{'total':20,'rows':[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onClickRow: function(rowIndex, rowData){
    				if (editIndex != rowIndex){
		                if (endEditing()){
		                    $("#assignCustomer-table").datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
		                    editIndex = rowIndex;
		                    
		                    var rows =  $("#assignCustomer-table").datagrid('getRows');
				    		var rowLen = 0;
				    		for(var i=0;i<rows.length;i++){
				    			if(rows[i].customerid!=null){
				    				rowLen ++;
				    			}
				    		}
				    		if(rowLen==rows.length-1){
				    			$("#assignCustomer-table").datagrid('appendRow',{});
				    		}
		                } else {
		                    $("#assignCustomer-table").datagrid('selectRow', editIndex);
		                }
		            }
    			},
    			onLoadSuccess:function(){
    				$("#assignCustomer-table").datagrid("resize")
    			}
  			});
  			$("#assignCustomer").customerWidget({
  				required:true,
  				isall:true
  			});
  		});
  		function assignCustomer(){
  			endEditing();
  			var totalamount = $("#collectAmount").numberbox("getValue");
  			var usecollectAmount = $("#usecollectAmount").numberbox("getValue");
  			if(Number(totalamount)<Number(usecollectAmount)){
  				$.messager.alert("提醒","分配的金额超过收款单金额。不能提交保存");
  				return false;
  			}
  			$.messager.confirm("提醒","是否确定给收款单分配金额?",function(r){
		  		if(r){
		  			 var rows =  $("#assignCustomer-table").datagrid('getRows');
		  			 var detailList = JSON.stringify(rows);
		  			 var id = $("#assignCustomer-collectorderid").val();
		  			 loading("提交中..");
		  			$.ajax({
			   			url:'account/receivable/assignCustomer.do',
			   			dataType:'json',
			   			type:'post',
			   			data:{id:id,detailList:detailList},
			   			success:function(json){
			   				loaded();
			    			if(json.flag == true){
				    			$.messager.alert("提醒","操作成功!");
				    			$('#account-dialog-writeoff-content').dialog("close");
			    			}
			    			else{
			    				$.messager.alert("提醒","操作失败!");
			    			}
		   				},
		   				error:function(){
		   					loaded();
		   					$.messager.alert("提醒","操作失败!");
		   				}
		  			});
		  		}
		  	});
  		}
  		function endEditing(){
  			if (editIndex == undefined){return true}
            if ($("#assignCustomer-table").datagrid('validateRow', editIndex)){
                var ed = $("#assignCustomer-table").datagrid('getEditor', {index:editIndex,field:'customerid'});
                var name = $(ed.target).customerWidget('getText');
                $("#assignCustomer-table").datagrid('getRows')[editIndex]['customername'] = name;
                $("#assignCustomer-table").datagrid('endEdit', editIndex);
                editIndex = undefined;
                countTotal();
                return true;
            } else {
                return false;
            }
  		}
  		function countTotal(){
  			var rows =  $("#assignCustomer-table").datagrid('getRows');
    		var taxamount = 0;
    		var rowLen = 0;
    		for(var i=0;i<rows.length;i++){
    			if(rows[i].customerid!=null){
    				taxamount = Number(taxamount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    				rowLen ++;
    			}
    		}
    		if(rowLen==rows.length){
    			$("#assignCustomer-table").datagrid('appendRow',{});
    		}
    		$("#assignCustomer-table").datagrid("reloadFooter",[{customername:'合计',amount:taxamount}]);
    		$("#usecollectAmount").numberbox("setValue",taxamount);
    		
    		var totalamount = $("#collectAmount").numberbox("getValue");
    		var unusecollectAmount = totalamount - taxamount;
    		$("#unusecollectAmount").numberbox("setValue",unusecollectAmount);
    		if(unusecollectAmount<0){
    			$.messager.alert("提醒","分配的金额超过收款单金额。");
    		}
  		}
  	</script>
  </body>
</html>
