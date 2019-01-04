<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	  	<form action="purchase/limitpriceorder/addLimitPriceOrder.do" id="purchase-form-limitPriceOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-limitPriceOrderAddPage-addType" name="addType"/>
	  		<div data-options="region:'north',border:false" style="height:120px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:90px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:130px;" readonly="readonly" /></td>
						<td style="width:90px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input type="text" name="limitPriceOrder.businessdate" value='<fmt:formatDate  value="${today }" pattern="yyyy-MM-dd" />' style="width:130px;" readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:60px;text-align: left;">状态：</td>
				    	<td style="width:165px;"><select disabled="disabled" style="width:130px;" ><option>新增</option></select></td>
					</tr>
					<tr>
						<td style="text-align: left;">申请人：</td>
						<td><input type="text" id="purchase-limitPriceOrderAddPage-applyuser" value="${user.personnelid }"  style="width:130px;" name="limitPriceOrder.applyuserid" /></td>
						<td style="text-align: left;">申请部门：</td>
						<td colspan="3"><input type="text" id="purchase-limitPriceOrderAddPage-applydept" value="${user.departmentname }"  style="width:130px;" readonly="readonly"/><input id="purchase-limitPriceOrderAddPage-applydeptid" type="hidden" value="${user.departmentid }" name="limitPriceOrder.applydeptid" /></td>
					</tr>
					<tr>
						<td style="width:80px;text-align: left;">开始生效日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-limitPriceOrderAddPage-effectstartdate" name="limitPriceOrder.effectstartdate" value='<fmt:formatDate  value="${today }" pattern="yyyy-MM-dd" />' style="width:130px;" readonly="readonly" onfocus="WdatePicker({minDate:'<fmt:formatDate  value="${today }" pattern="yyyy-MM-dd" />','dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;text-align: left;">有效截止日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-limitPriceOrderAddPage-effectenddate" name="limitPriceOrder.effectenddate" style="width:130px;" readonly="readonly" onfocus="WdatePicker({minDate:'<fmt:formatDate  value="${today }" pattern="yyyy-MM-dd" />','dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:60px;text-align: left;">备注：</td>
						<td>
							<input type="text" style="width:130px;" name="limitPriceOrder.remark"/>
						</td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-limitPriceOrderAddPage-limitPriceOrdertable"></table>
				<input type="hidden" id="purchase-limitPriceOrderAddPage-limitPriceOrderDetails" name="limitPriceOrderDetails"/>
	  		</div>
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-edit">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <div id="purchase-limitPriceOrderAddPage-dialog-DetailOper"></div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
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
  		$("#purchase-buttons-limitPriceOrderPage").buttonWidget("initButtonType", 'add');
  	  	var $limitPriceOrdertable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
  	  	var tableColJson = $limitPriceOrdertable.createGridColumnLoad({
	  	  	name :'purchase_limitPriceorder_detail',
			frozenCol : [[
	 		]],
			commonCol : [[
						{field:'goodsid',title:'商品编码',width:60,isShow:true,align:'right'},
 						{field:'name',title:'商品名称',width:220,isShow:true},
						{field:'barcode',title:'条形码',width:90,isShow:true},
 						{field:'model',title:'规格型号',width:80,isShow:true,hidden:true,},
 						{field:'brandName',title:'商品品牌',width:60,isShow:true,hidden:true},
    					{field:'unitid', title:'单位',width:40,
    						formatter: function(value,row,index){
								return row.unitname;
							}
						},
    					{field:'auxunitid', title:'辅单位',width:40,
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
						{field:'priceasfound',title:'调整前采购价',width:80,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'priceasleft',title:'调整后采购价',width:80,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'remark',title:'备注',width:200},
						{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true},
						{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true},
						{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true},
						{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true},
						{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true},
						{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true},
						{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true},
						{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true},
						{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true}
			]]
  	  	});
  	  	$limitPriceOrdertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data:[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}],
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
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
  	 			$limitPriceOrdertable.datagrid('selectRow', rowIndex);
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
			var data=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('getSelected');
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
			var dataRow=$limitPriceOrdertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$limitPriceOrdertable.datagrid('getRowIndex',dataRow);
							$limitPriceOrdertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$limitPriceOrdertable.datagrid('deleteRow', rowIndex);							
							var rowlen=$limitPriceOrdertable.datagrid('getRows').length; 
							if(rowlen<15){
								$limitPriceOrdertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							$limitPriceOrdertable.datagrid('clearSelections');
	        			}
					}
				});	
			}		
		});
		$("#purchase-limitPriceOrderAddPage-applyuser").widget({ 
			name:'t_purchase_limitpriceorder',
			col:'applyuserid',
			width:130,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect: function(data){
				$("#purchase-limitPriceOrderAddPage-applydept").val(data.pname);
				$("#purchase-limitPriceOrderAddPage-applydeptid").val(data.pid);
			},
			onClear:function(){
				$("#purchase-limitPriceOrderAddPage-applydept").val("");
				$("#purchase-limitPriceOrderAddPage-applydeptid").val("");
			}
		});	
  	});
  </script>
  </body>
</html>
