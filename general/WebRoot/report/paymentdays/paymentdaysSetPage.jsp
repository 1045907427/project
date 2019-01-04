<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>超账期区间设置页面</title>
  </head>
  
  <body>
    	<div class="easyui-layout" data-options="fit:true">
	  		<div data-options="region:'center',border:false">
		   		<table id="report-table-paymentdays-set"></table>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:35px;text-align: right;">
		  			<input type="button" value="确 定" id="report-button-paymentdays" />
	  			</div>
	  		</div>
	  	</div>
	  	<form action="report/paymentdays/addPaymentdays.do" method="post" id="report-form-paymentdays">
	  		<input id="report-paymentdays-detail" type="hidden" name="detail"/>
            <input type="hidden" name="type" value="${type}"/>
	  	</form>
	  	<script type="text/javascript">
	  		$(function(){
	  			$("#report-form-paymentdays").form({  
				    onSubmit: function(){  
				    	//获取盘点单明细json
				    	var json = $("#report-table-paymentdays-set").datagrid('getRows');
						$("#report-paymentdays-detail").val(JSON.stringify(json));
				    	loading("提交中..");
				    },  
				    success:function(data){
				    	//表单提交完成后 隐藏提交等待页面
				    	loaded();
				    	var json = $.parseJSON(data);
				    	if(json.flag){
				    		$.messager.alert("提醒","保存成功");
				    		var tab = top.$('#tt').tabs('getSelected');
							top.refresh(tab);
				    		$("#report-paymentdaysSet-dialog").dialog("close");
				    	}else{
				    		$.messager.alert("提醒","保存失败");
				    	}
				    },
				    error:function(){
				    	$.messager.alert("错误","保存出错");
				    }
				}); 
	  			$("#report-table-paymentdays-set").datagrid({
	  				columns:[[
							  {field:'days',title:'区间天数',width:80,
							  	formatter:function(value,row,index){
							  		if(value>0){
							  			return value;
							  		}else{
							  			return "";
							  		}
						        },
							  	editor:{
						        	type:'numberbox'
						        }
							  },
							  {field:'detail',title:'区间描述',width:200}
					        ]],
	    			rownumbers: true,
	    			striped:true,
	    			fit:true,
	    			singleSelect: true,
	    			data: JSON.parse('${detailList}'),
	    			onClickRow: function(rowIndex, rowData){
    					onClickCell(rowIndex, "days");
	    			},
	    			onLoadSuccess:function(data){
	    				if(data.rows.length<10){
		            		var j = 10-data.rows.length;
		            		for(var i=0;i<j;i++){
		            			$("#report-table-paymentdays-set").datagrid('appendRow',{});
		            		}
	   					}else{
	   						$("#report-table-paymentdays-set").datagrid('appendRow',{});
	   					}
	    			}
	  			});
	  			$("#report-button-paymentdays").click(function(){
	  				$("#report-form-paymentdays").submit();
	  			});
	  		});
	  		var editIndex = undefined;  
	        function endEditing(){  
	            if (editIndex == undefined){return true}  
	            if ($('#report-table-paymentdays-set').datagrid('validateRow', editIndex)){  
	            	var ed = $('#report-table-paymentdays-set').datagrid('getEditor', {index:editIndex,field:'days'});
					var days = $(ed.target).val();
					$('#report-table-paymentdays-set').datagrid('getRows')[editIndex]['days'] = days;
	                $('#report-table-paymentdays-set').datagrid('endEdit', editIndex);  
	                editIndex = undefined;  
	                computeDays();
	                return true;  
	            } else {  
	                return false;  
	            }  
	        }
	        function onClickCell(index, field){  
	            if (endEditing()){
	    			var row = $('#report-table-paymentdays-set').datagrid('getRows')[index];
	            	if(field == "days"){  
		                $('#report-table-paymentdays-set').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});  
		                editIndex = index;  
		                var ed = $('#report-table-paymentdays-set').datagrid('getEditor', {index:editIndex,field:'days'});
		                $(ed.target).focus();
		                $(ed.target).select();
	                }
	            }  
	        }
	        function computeDays(){
	        	var rows = $("#report-table-paymentdays-set").datagrid('getRows');
	    		var leng = rows.length;
	    		var rowObject = null;
	    		var endindex = null;
	    		var seq = 0;
	    		for(var i=0; i<leng; i++){
	    			if(rows[i].days!=null && rows[i].days>0){
	    				seq = seq+1;
	    				if(rowObject==null){
	    					var beginday = 1;
	    					var endday = rows[i].days;
	    					var detail = beginday+"至"+endday+"天";
	    					var object = {days:rows[i].days,detail:detail,beginday:beginday,endday:endday,seq:seq};
	    					rowObject = object;
	    					$("#report-table-paymentdays-set").datagrid('updateRow',{index:i, row:object});
	    				}else{
	    					var beginday = Number(rowObject.endday)+1;
	    					var endday = Number(rowObject.endday)+Number(rows[i].days);
	    					var detail = beginday+"至"+endday+"天";
	    					var object = {days:rows[i].days,detail:detail,beginday:beginday,endday:endday,seq:seq};
	    					rowObject = object;
	    					$("#report-table-paymentdays-set").datagrid('updateRow',{index:i, row:object});
	    				}
	    				endindex = i;
	    				if(endindex == leng - 1){
							$("#report-table-paymentdays-set").datagrid('appendRow',object);
						}
	    			}else{
	    				seq = seq+1;
	    				var beginday = null;
    					var endday = null;
    					var detail = null;
    					var object = {days:null,detail:detail,beginday:beginday,endday:endday,seq:seq};
	    				$("#report-table-paymentdays-set").datagrid('updateRow',{index:i, row:object});
	    			}
	    		}
	    		if(null!=rowObject){
		    		var beginday = Number(rowObject.endday)+1;
					var endday = null;
					var detail = "大于"+beginday+"天";
					var object = {days:null,detail:detail,beginday:beginday,endday:endday};
					$("#report-table-paymentdays-set").datagrid('updateRow',{index:endindex+1, row:object});
					
				}
	        }
	  	</script>
  </body>
</html>
