<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>销售出库单明细修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" id="storage-form-purchaseEnterDetailEditLocationPage">
		<table border="0" class="box_table">
  			<tr>
  				<td>商品名称:</td>
  				<td>
  					<input type="text" id="storage-goodsname-purchaseEnterDetailEditLocationPage" style="width: 180px;" readonly="readonly">
  				</td>
  				<td colspan="2">
  					<span id="storage-goodsinfo-purchaseEnterDetailEditLocationPage" style="float: left;"></span>
  				</td>
  			</tr>
  			<tr>
  				<td>辅数量:</td>
  				<td>
  					<input type="text" id="storage-auxunitdetail-purchaseEnterDetailEditLocationPage" style="width: 180px;" readonly="readonly"/>
  					<input type="hidden" id="storage-auxunitnum-purchaseEnterDetailEditLocationPage" name="auxnum"/>
  					<input type="hidden" id="storage-auxremainder-purchaseEnterDetailEditLocationPage" name="auxremainder"/>
  				</td>
  				<td>数量:</td>
  				<td>
  					<input type="text" id="storage-unitnumdetail-purchaseEnterDetailEditLocationPage" style="width: 180px;" readonly="readonly"/>
  					<input type="hidden" id="storage-unitnum-purchaseEnterDetailEditLocationPage" name="unitnum"/>
  				</td>
  			</tr>
  		</table>
  		<table id="storage-table-purchaseEnterDetailEditLocationPage"></table>
  		</form>
    </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-purchaseEnterDetailEditLocationPage" />
		</div>
	</div>
</div>
   <script type="text/javascript">
   		var editIndex = null;
   		//加载数据
		var object = $("#storage-datagrid-purchaseEnterAddPage").datagrid("getSelected");
		$("#storage-form-purchaseEnterDetailEditLocationPage").form("load",object);
   		$("#storage-goodsinfo-purchaseEnterDetailEditLocationPage").html("商品编码：<font color='green'>"+object.goodsid+"</font>&nbsp;条形码：<font color='green'>"+ object.goodsInfo.barcode+"</font>");
   		$("#storage-goodsname-purchaseEnterDetailEditLocationPage").val(object.goodsInfo.name);
   		$("#storage-auxunitdetail-purchaseEnterDetailEditLocationPage").val(object.auxnumdetail);
   		$("#storage-unitnumdetail-purchaseEnterDetailEditLocationPage").val(object.unitnum+object.unitname);
   		$(function(){
   			$("#storage-table-purchaseEnterDetailEditLocationPage").datagrid({
  				columns: [[
  					{field:'storagelocationid', title:'所属库位',width:180,
   						formatter:function(value,row,index){
   							return row.storagelocationname;
				        },
				        editor:{
				        	type:'widget',
				        	options:{
				        		name:'t_storage_purchase_enter_detail',
					    		width:160,
								col:'storagelocationid',
								singleSelect:true
				        	}
				        }
			    	},
			    	{field:'auxunitnum', title:'辅数量',width:100,align:'right',
   						formatter:function(value,row,index){
   							if(value>0){
   								return formatterBigNumNoLen(value)+object.auxunitname;
   							}else{
   								return "";
   							}
				        },
				        editor:{
				        	type:'numberbox',
				        	options:{
				        	}
				        }
			    	},
			    	{field:'unitnum', title:'数量',width:100,align:'right',
   						formatter:function(value,row,index){
   							if(value>0){
   								return formatterBigNumNoLen(value)+object.unitname;
   							}else{
   								return "";
   							}
				        },
				        editor:{
				        	type:'numberbox',
				        	options:{
				        	}
				        }
			    	}
  				]],
  				border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:{'total':9,'rows':[{},{},{},{},{},{},{},{},{}]},
    			onClickRow: function(rowIndex, rowData){
    				if (editIndex != rowIndex){
		                if (endEditing()){
		                	$("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
		                    editIndex = rowIndex;
		                    var rows =  $("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('getRows');
				    		var rowLen = 0;
				    		for(var i=0;i<rows.length;i++){
				    			if(rows[i].storagelocationid!=null){
				    				rowLen ++;
				    			}
				    		}
				    		if(rowLen==rows.length-1){
				    			$("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('appendRow',{});
				    		}
		                } else {
		                	$("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('selectRow', editIndex);
		                }
		            }
    			},
    			onLoadSuccess:function(){
    				$("#storage-table-purchaseEnterDetailEditLocationPage").datagrid("resize")
    			}
  			});
   			$("#storage-savegoon-purchaseEnterDetailEditLocationPage").click(function(){
   				endEditing();
   			});
   		});
   		function endEditing(){
  			if (editIndex == null){return true}
            if ($("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('validateRow', editIndex)){
                var ed = $("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('getEditor', {index:editIndex,field:'storagelocationid'});
                var name = $(ed.target).widget('getText');
                $("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('getRows')[editIndex]['storagelocationname'] = name;
                $("#storage-table-purchaseEnterDetailEditLocationPage").datagrid('endEdit', editIndex);
                editIndex = null;
                return true;
            } else {
                return false;
            }
  		}
   </script>
  </body>
</html>
