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
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/arrivalorder/addArrivalOrder.do" id="purchase-form-arrivalOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-saveaudit" name="saveaudit"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-referpage" value="0"/>
	  		<div data-options="region:'north',border:false" style="height:110px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:150px;" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-arrivalOrderAddPage-businessdate" name="arrivalOrder.businessdate" value='${today }' style="width:130px;" readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;"><select disabled="disabled" style="width:130px;" ><option>新增</option></select></td>
					</tr>
					<tr>
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="purchase-arrivalOrderAddPage-supplier" style="width:300px;" name="arrivalOrder.supplierid"/>
							<span id="purchase-supplier-showid-arrivalOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-arrivalOrderAddPage-storage" name="arrivalOrder.storageid" style="width:135px;" /></td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>				    		
				    		<select id="purchase-arrivalOrderAddPage-buydept" name="arrivalOrder.buydeptid" style="width:150px;" >
				    		<option value=""></option>
	    						<c:forEach items="${buyDept}" var="list">
	    							<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
				    		</select>
						</td>
						<td style="">采购员：</td>
						<td>
							<input type="text" id="purchase-arrivalOrderAddPage-buyuser" name="arrivalOrder.buyuserid" style="width:130px;" readonly="readonly" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:135px;" id="purchase-arrivalOrderAddPage-remark" name="arrivalOrder.remark"/>
                        </td>
                    </tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-arrivalOrderAddPage-arrivalOrdertable"></table>
				<input type="hidden" id="purchase-arrivalOrderAddPage-arrivalOrderDetails" name="arrivalOrderDetails"/>
	  		</div>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-paytype" style="width:130px;" name="arrivalOrder.paytype" />
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-settletype" style="width:130px;" name="arrivalOrder.settletype" />
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-edit">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
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
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("initButtonType", 'add');
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-print");
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-preview");
  	  	var $arrivalOrdertable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
  	  	$arrivalOrdertable.datagrid({
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
  	 			$arrivalOrdertable.datagrid('reloadFooter',[
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
  	 			$arrivalOrdertable.datagrid('selectRow', rowIndex);
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
			var data=$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getSelected');
			orderDetailEditDialog(data);			
		});
		
		$("#purchase-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$arrivalOrdertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$arrivalOrdertable.datagrid('getRowIndex',dataRow);
							$arrivalOrdertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$arrivalOrdertable.datagrid('deleteRow', rowIndex);
							var rowlen=$arrivalOrdertable.datagrid('getRows').length; 
							if(rowlen<15){
								$arrivalOrdertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							
							$arrivalOrdertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});

		$("#purchase-arrivalOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:130,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$("#purchase-arrivalOrderAddPage-supplier").supplierWidget({ 
			name:'t_purchase_arrivalorder',
			col:'supplierid',
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				$("#purchase-supplier-showid-arrivalOrderAddPage").text("编号："+ data.id);
				$("#purchase-arrivalOrderAddPage-buydept").val(data.buydeptid);
				$("#purchase-arrivalOrderAddPage-settletype").val(data.settletype);
				$("#purchase-arrivalOrderAddPage-paytype").val(data.paytype);
				$("#purchase-arrivalOrderAddPage-buyuser").widget('setValue',data.buyuserid);
    			$("#purchase-arrivalOrderAddPage-remark").focus();
							
			},
			onClear:function(){
				$("#purchase-supplier-showid-arrivalOrderAddPage").text("");
				$("#purchase-arrivalOrderAddPage-handler").val('');
				$("#purchase-arrivalOrderAddPage-buydept").val('');
				$("#purchase-arrivalOrderAddPage-buyuser").widget('clear');
				$("#purchase-arrivalOrderAddPage-settletype").val('');
				$("#purchase-arrivalOrderAddPage-paytype").val('');
			}
		});
		

		$("#purchase-arrivalOrderAddPage-storage").widget({ 
			name:'t_purchase_arrivalorder',
			col:'storageid',
			width:130,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				$("#purchase-arrivalOrderAddPage-remark").focus();
			}
		});
		/*
		$("#purchase-arrivalOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-arrivalOrderAddPage-buyuser").html("");
	    				$("#purchase-arrivalOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-arrivalOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-arrivalOrderAddPage-buyuser").html("");
			}
		});
		*/
		$("#purchase-arrivalOrderAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				var flag = $("#purchase-form-arrivalOrderAddPage").form('validate');
				if(flag==false){
					$.messager.alert("提醒",'请先完善采购进货单基本信息');
					return false;
				}else{
	   				$("#purchase-arrivalOrderAddPage-remark").blur();
	   				orderDetailAddDialog();
				}
			}
	    });
  	});
  </script>
  </body>
</html>
