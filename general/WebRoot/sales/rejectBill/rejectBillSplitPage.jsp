<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单拆分页面</title>
  </head> 
  <body>
    <table id="sales-datagrid-rejectBillSplitPage"></table>
    <form id="sales-form-rejectBillSplitPage" action="sales/updateRejectBillSplit.do" method="post">
    	<input type="hidden" name="rejectid" value="${id }"/>
    	<input type="hidden" id="sales-detail-rejectBillSplitPage" name="detailJson"/>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-form-rejectBillSplitPage").form({  
			    onSubmit: function(){  
			    	endEditing(editfiled);
			    	var json = $("#sales-datagrid-rejectBillSplitPage").datagrid('getChecked');
					$("#sales-detail-rejectBillSplitPage").val(JSON.stringify(json));
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","拆分成功。生成新的退货通知单："+json.id);
			    		$("#sales-panel-rejectBill").panel("refresh");
			    		$("#sales-dialog-split").dialog("close");
			    	}else{
			    		$.messager.alert("提醒","拆分失败.");
			    	}
			    }  
			}); 
    		$("#sales-datagrid-rejectBillSplitPage").datagrid({ //销售商品明细信息编辑
    			columns: [[
    					{field:'ck',checkbox:true},
    					{field:'goodsid',title:'商品编码',width:70,align:' left'},
    					{field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
    					},
    					{field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitname', title:'单位',width:35,align:'left'},
    					{field:'unitnum', title:'单据数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'splitnum', title:'拆分数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        },
					        editor:{
					        	type:'numberbox',
					        	options:{
					        		validType:'receiptMax',
									precision:${decimallen}
					        	}
					        }
					    },
				    	{field:'taxprice', title:'单价',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'taxamount', title:'金额',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    }
    				  ]],
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
				selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onDblClickRow: function(rowIndex, rowData){
    				onClickCell(rowIndex, "splitnum");
    			},
    			onClickCell: function(index, field, value){
    				onClickCell(index, field);
    			}
    		});
    	});
    	var $wareSplitList = $("#sales-datagrid-rejectBillSplitPage"); //商品datagrid的div对象
    	var editIndex = undefined;  
    	var thisIndex = undefined;
    	var editfiled = null;
        function endEditing(field){  
            if (editIndex == undefined){return true}  
            if(field == "splitnum"){
	            if ($wareSplitList.datagrid('validateRow', editIndex)){  
	            	var ed = $wareSplitList.datagrid('getEditor', {index:editIndex,field:'splitnum'});
					var splitnum = $(ed.target).val();
	                $wareSplitList.datagrid('endEdit', editIndex);  
	                var row = $wareSplitList.datagrid('getRows')[editIndex];
	                row.splitnum = splitnum;
		            $wareSplitList.datagrid('updateRow',{index:editIndex, row:row});
	                editIndex = undefined;  
	                return true;  
	            } else {  
	                return false;  
	            }  
            }
        }
        function onClickCell(index, field){  
            if (endEditing(editfiled)){
    			var row = $wareSplitList.datagrid('getRows')[index];
    			if(row.goodsid == undefined){
    				return false;
    			}
    			editfiled = field;
            	if(field == "splitnum"){  
	                $wareSplitList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});  
	                receiptMax(row.unitnum);
	                editIndex = index;  
	                thisIndex = index;
	                var ed = $wareSplitList.datagrid('getEditor', {index:editIndex,field:'splitnum'});
                    getNumberBoxObject(ed.target).focus();
                    getNumberBoxObject(ed.target).select();
                }
            }  
        }
        function receiptMax(max){ //验证接收数最大值
    		$.extend($.fn.validatebox.defaults.rules, {
				receiptMax:{
			    	validator:function(value){
			    		return Number(value) <= Number(max);
			    	},
			    	message:'请输入小于等于'+formatterBigNumNoLen(max)+'的数字!'
			    }
			});
    	}
    	<c:if test="${split==false}">
    	$.messager.confirm('确认','当前销售退货通知单已经关联。不能拆分?',function(r){
			if(r){
				$("#sales-dialog-split").dialog("close");
			}else{
				$("#sales-dialog-split").dialog("close");
			}
		});
    	</c:if>
    </script>
  </body>
</html>
