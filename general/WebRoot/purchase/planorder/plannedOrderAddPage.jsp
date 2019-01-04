<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>采购</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
  </head>
  <body>
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/planorder/addPlannedOrder.do" id="purchase-form-plannedOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-plannedOrderAddPage-addType" name="addType"/>
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:60px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" /></td>
						<td style="width:80px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="purchase-plannedOrderAddPage-businessdate" name="plannedOrder.businessdate" value='${busdate }' readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;text-align: left;">状态：</td>
				    	<td style="width:165px;"><select disabled="disabled" class="len150" ><option>新增</option></select></td>
					</tr>
					<tr>
						<td>供应商：</td>
						<td colspan="3"><input type="text" id="purchase-plannedOrderAddPage-supplier" style="width:320px;" name="plannedOrder.supplierid" />
							<span id="purchase-supplier-showid-plannedOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-plannedOrderAddPage-storage" name="plannedOrder.storageid" value="${defaultStorageid}"/></td>
					</tr>
					<tr>
						<td style="text-align: left;">采购部门：</td>
				    	<td>
				    		<select id="purchase-plannedOrderAddPage-buydept" class="len150" disabled="disabled">
				    			<option value=""></option>
	    						<c:forEach items="${buyDept}" var="list">
	    							<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
				    		</select>
				    		<input type="hidden" id="purchase-plannedOrderAddPage-buydept-hid" name="plannedOrder.buydeptid" />
				    	</td>
				    	<td style="text-align: left;">采购人员：</td>
				    	<td>
				    		<input type="text" id="purchase-plannedOrderAddPage-buyuser" name="plannedOrder.buyuserid" readonly="readonly" />
				    	</td>
				    	<td>订单追加：</td>
						<td>						
	    					<select id="purchase-plannedOrderAddPage-orderappend" class="len150" name="plannedOrder.orderappend">
	    						<option value="0" selected="selected">不追加</option>
	    						<option value="1">追加</option>
	    					</select>
						</td>	
					</tr>
                    <tr>
                        <td style="width:60px;text-align: left;">备注：</td>
                        <td colspan="3">
                            <input type="text" style="width:412px;" name="plannedOrder.remark" value="<c:out value="${plannedOrder.remark }"></c:out>"/>
                        </td>
                        <td>到货日期：</td>
                        <td>
                            <input type="text" class="len150 Wdate" id="purchase-plannedOrderAddPage-arrivedate" name="plannedOrder.arrivedate" value="${arrivedate }"
                                   onblur="changeArriveDate()" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                        </td>
                    </tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-plannedOrderAddPage-plannedOrdertable"></table>
				<input type="hidden" id="purchase-plannedOrderAddPage-plannedOrderDetails" name="plannedOrderDetails"/>
	  		</div>
	  	</form> 
	  	<input type="hidden" id="purchase-plannedOrderAddPage-formModStatus" value="0"/>
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-add">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
        <div id="purchase-tableMenu-historyPprice" iconCls="button-delete">查看历史采购价</div>
  </div>
  <script type="text/javascript">
    //获取当前用户所在部门是否关联仓库 0不关联 1关联
    var flag = "${flag}";
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-plannedOrderAddPage-plannedOrdertable");
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
  		$("#purchase-buttons-plannedOrderPage").buttonWidget("initButtonType", 'add');
  	  	var $plannedOrdertable=$("#purchase-plannedOrderAddPage-plannedOrdertable");
  	  	$plannedOrdertable.datagrid({
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
  	 			$plannedOrdertable.datagrid('reloadFooter',[
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
  	 			$plannedOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemInsert');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-historyPprice');
  	 		}
  	  	}).datagrid("columnMoving");

  		//查看历史价格按钮事件
		$("#purchase-tableMenu-historyPprice").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			showHistoryGoodsPrice();	
		});
  	  	
  	
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
			var data=$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getSelected');	
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
			var $ordertable=$("#purchase-plannedOrderAddPage-plannedOrdertable");
			var dataRow=$ordertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$ordertable.datagrid('getRowIndex',dataRow);
							$ordertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$ordertable.datagrid('deleteRow', rowIndex);							
							var rowlen=$ordertable.datagrid('getRows').length; 
							if(rowlen<15){
								$ordertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							$ordertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});

		$("#purchase-plannedOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});

		$("#purchase-plannedOrderAddPage-supplier").supplierWidget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			width:320,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				$("#purchase-supplier-showid-plannedOrderAddPage").text("编号："+ data.id);
				$("#purchase-plannedOrderAddPage-buydept").val(data.buydeptid);
				$("#purchase-plannedOrderAddPage-buydept-hid").val(data.buydeptid);
				/*
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.buydeptid}, function(json){
    				if(json.length>0){
    					$("#purchase-plannedOrderAddPage-buyuser").html("");
    					$("#purchase-plannedOrderAddPage-buyuser").append("<option value=''></option>");
    					for(var i=0;i<json.length;i++){
    						$("#purchase-plannedOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
    					}
		    			$("#purchase-plannedOrderAddPage-buyuser").val(data.buyuserid);
		    			$("#purchase-plannedOrderAddPage-buyuser-hid").val(data.buyuserid);
    				}	
    			});
    			*/
    			$("#purchase-plannedOrderAddPage-buyuser").widget('setValue',data.buyuserid);
    			
    			$.getJSON('basefiles/getContacterBy.do', {type:"2", id:data.id}, function(json){
    				if(json.length>0){
    					$("#purchase-plannedOrderAddPage-handler").html("");
    					$("#purchase-plannedOrderAddPage-handler").append("<option value=''></option>");
    					for(var i=0;i<json.length;i++){
    						$("#purchase-plannedOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
    					}
    					$("#purchase-plannedOrderAddPage-handler").val(data.contact);
    				}
    			});
                //仓库关联问题
                var storage = $("#purchase-plannedOrderAddPage-storage").val();
    			if(storage == "" && data.storageid){
    				$("#purchase-plannedOrderAddPage-storage").widget('setValue',data.storageid);
    			}else if(flag == 0){
                    $("#purchase-plannedOrderAddPage-storage").widget('setValue',data.storageid);
                }

    			if(data.orderappend && data.orderappend=="1"){
					$("#purchase-plannedOrderAddPage-orderappend").val("1");
				}else{
					$("#purchase-plannedOrderAddPage-orderappend").val("0");					
				}	
    			$("#purchase-plannedOrderAddPage-remark").focus();
    			
    			
			},
			onClear:function(){
				$("#purchase-supplier-showid-plannedOrderAddPage").text("");
				$("#purchase-plannedOrderAddPage-buydept").val("");
				$("#purchase-plannedOrderAddPage-buydept-hid").val("");
				$("#purchase-plannedOrderAddPage-handler").val("");
				$("#purchase-plannedOrderAddPage-buyuser").widget('clear');
				$("#purchase-plannedOrderAddPage-handler").html("");
				//$("#purchase-plannedOrderAddPage-storage").widget('clear');
			}
		});	
		$("#purchase-plannedOrderAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("#purchase-plannedOrderAddPage-remark").blur();
	   			orderDetailAddDialog();
			}
	    });
		$("#purchase-plannedOrderAddPage-storage").widget({ 
			name:'t_purchase_plannedorder',
			col:'storageid',
			width:150,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				$("#purchase-plannedOrderAddPage-remark").focus();
			}
		});
  	});
  </script>
  </body>
</html>
