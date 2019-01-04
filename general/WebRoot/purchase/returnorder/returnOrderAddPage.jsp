<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/returnorder/addReturnOrder.do" id="purchase-form-returnOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-returnOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-returnOrderAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:100px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;text-align: left;">编号：</td>
						<td><input type="text" class="len150" readonly="readonly" /></td>
						<td style="width:80px;text-align: left;">业务日期：</td>
						<td><input type="text" class="len150" name="returnOrder.businessdate" value='${busdate }' readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;text-align: left;">状态：</td>
				    	<td><select disabled="disabled" class="len150"><option>新增</option></select></td>
					</tr>
					<tr>
						<td>供应商：</td>
						<td colspan="3"><input type="text" id="purchase-returnOrderAddPage-supplier" style="width:320px;" name="returnOrder.supplierid" />
							<span id="purchase-supplier-showid-returnOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td>退货仓库：</td>
                        <td><input type="text" id="purchase-returnOrderAddPage-storage" name="returnOrder.storageid" value="${defaultStorageid }"/></td>
					</tr>
					<tr>					
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-returnOrderAddPage-buydept" name="returnOrder.buydeptid" class="len150" >
				    		<option value=""></option>
	    						<c:forEach items="${buyDept}" var="list">
	    							<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
				    		</select>
						</td>
						<td style="">采购员：</td>
						<td>
							<input type="text" id="purchase-returnOrderAddPage-buyuser" name="returnOrder.buyuserid" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:150px;" id="purchase-returnOrderAddPage-remark" name="returnOrder.remark"/>
                        </td>
                    </tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-returnOrderAddPage-returnOrdertable"></table>
				<input type="hidden" id="purchase-returnOrderAddPage-returnOrderDetails" name="returnOrderDetails"/>
	  		</div>
	  		<input type="hidden" id="purchase-returnOrderAddPage-settletype" name="returnOrder.settletype" />
	  		<input type="hidden" id="purchase-returnOrderAddPage-paytype" name="returnOrder.paytype" />
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-add">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <script type="text/javascript">
    //获取当前用户所在部门是否关联仓库 0不关联 1关联
    var flag = "${flag}";
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-returnOrderAddPage-returnOrdertable");
  		var dataRows=$potable.datagrid('getRows');
  		
  		var rindex=0;
  		for(rindex=0;rindex<dataRows.length;rindex++){
  	  		if(dataRows[rindex].goodsid==null || dataRows[rindex].goodsid==""){
  	  	  		break;
  	  		}
  		}
  		if(rindex==dataRows.length){
  	  		$potable.datagrid('appendRow',{});
  		}
  		return rindex;
  	}
	
  	$(document).ready(function(){
  		$("#purchase-buttons-returnOrderPage").buttonWidget("initButtonType", 'add');
  	  	var $returnOrdertable=$("#purchase-returnOrderAddPage-returnOrdertable");
  	  	$returnOrdertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data:[{},{},{},{},{},{},{},{},{},{},{},{}],
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
  	 			$returnOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
        		editRowIndex=rowIndex;
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				orderDetailEditDialog(rowData);
  	 			}else{
  	 				orderDetailAddDialog();
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			e.preventDefault();
  	 			var $contextMenu=$('#purchase-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$returnOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemInsert');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
  	 		}
  	  	}).datagrid("columnMoving");

  		//添加按钮事件
		$("#purchase-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			orderDetailAddDialog();			
		});
		
  		//编辑按钮事件
		$("#purchase-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getSelected');
			if(null!=data && data.goodsid !=null && data.goodsid!=""){
				orderDetailEditDialog(data);		
			}else{
				orderDetailAddDialog();	
			}				
		});
		$("#purchase-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			$returnOrdertable=$("#purchase-returnOrderAddPage-returnOrdertable");
			var dataRow=$returnOrdertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$returnOrdertable.datagrid('getRowIndex',dataRow);
							$returnOrdertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$returnOrdertable.datagrid('deleteRow', rowIndex);							
							var rowlen=$returnOrdertable.datagrid('getRows').length; 
							if(rowlen<15){
								$returnOrdertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							$returnOrdertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});

		$("#purchase-returnOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
				
		$("#purchase-returnOrderAddPage-supplier").supplierWidget({ 
			name:'t_purchase_returnorder',
			col:'supplierid',
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				$("#purchase-supplier-showid-returnOrderAddPage").text("编号："+ data.id);
				$("#purchase-returnOrderAddPage-buydept").val(data.buydeptid);
				$("#purchase-returnOrderAddPage-settletype").val(data.settletype);
				$("#purchase-returnOrderAddPage-paytype").val(data.paytype);
				/*
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.buydeptid}, function(json){
    				if(json.length>0){
    					$("#purchase-returnOrderAddPage-buyuser").html("");
    					$("#purchase-returnOrderAddPage-buyuser").append("<option value=''></option>");
    					for(var i=0;i<json.length;i++){
    						$("#purchase-returnOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
    					}
		    			$("#purchase-returnOrderAddPage-buyuser").val(data.buyuserid);
    				}	
    			});
    			*/

                //仓库关联问题
                var storage = $("#purchase-returnOrderAddPage-storage").val();
                if(storage == "" && data.storageid){
                    $("#purchase-returnOrderAddPage-storage").widget('setValue',data.storageid);
                }else if(flag == 0){
                    $("#purchase-returnOrderAddPage-storage").widget('setValue',data.storageid);
                }

                $("#purchase-returnOrderAddPage-buyuser").widget('setValue',data.buyuserid);
    			$("#purchase-returnOrderAddPage-remark").focus();
			},
			onClear:function(){
				$("#purchase-supplier-showid-returnOrderAddPage").text("");
				$("#purchase-returnOrderAddPage-buydept").val('');
				$("#purchase-returnOrderAddPage-buyuser").widget('clear');
				$("#purchase-returnOrderAddPage-settletype").val('');
				$("#purchase-returnOrderAddPage-paytype").val('');	
			}
		});	

		$("#purchase-returnOrderAddPage-storage").widget({ 
			name:'t_purchase_returnorder',
			col:'storageid',
			width:150,
			view:true,
			//required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				$("#purchase-returnOrderAddPage-remark").focus();
			}
		});	
		$("#purchase-returnOrderAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				var flag = $("#purchase-form-returnOrderAddPage").form('validate');
				if(flag==false){
					$.messager.alert("提醒",'请先完善采购退货通知单基本信息');
					return false;
				}else{
	   				$("#purchase-returnOrderAddPage-remark").blur();
	   				orderDetailAddDialog();
				}
			}
	    });
  	});
  </script>
  </body>
</html>
