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
	  	<form action="purchase/buyorder/addBuyOrder.do" id="purchase-form-buyOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-buyOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-buyOrderAddPage-referpage" value="0"/>
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="purchase-buyOrderAddPage-businessdate" name="buyOrder.businessdate" value='${busdate }' readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">
				    		<select disabled="disabled" class="len150"><option>新增</option></select>
				    		<input type="hidden" id="purchase-buyOrderAddPage-status" value="2"/>
				    	</td>
					</tr>
					<tr>
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="purchase-buyOrderAddPage-supplier" style="width:320px;" name="buyOrder.supplierid" />
							<span id="purchase-supplier-showid-buyOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td>订单追加：</td>
                        <td>
                            <select id="purchase-buyOrderAddPage-orderappend" class="len150" name="buyOrder.orderappend">
                                <option value="0" selected="selected">不追加</option>
                                <option value="1">追加</option>
                            </select>
                        </td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-buyOrderAddPage-buydept" class="len150" disabled="disabled" >
				    		<option value=""></option>
	    						<c:forEach items="${buyDept}" var="list">
	    							<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
				    		</select>
				    		<input type="hidden" id="purchase-buyOrderAddPage-buydept-hid" name="buyOrder.buydeptid" />
				    	</td>
						<td style="">采购员：</td>
						<td>
							<input type="text" id="purchase-buyOrderAddPage-buyuser" name="buyOrder.buyuserid" readonly="readonly" />
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-buyOrderAddPage-storage" name="buyOrder.storageid" value="${defaultStorageid }"/></td>
					</tr>
					<tr>
                        <td style="width:60px;">备注：</td>
                        <td colspan="3">
                            <input type="text" style="width:412px;" name="buyOrder.remark" value="${buyOrder.remark}"/>
                        </td>
                        <td>到货日期：</td>
                        <td>
                            <input type="text" class="len150 Wdate" id="purchase-buyOrderAddPage-arrivedate" name="buyOrder.arrivedate" value="${arrivedate }"
                                   onblur="changeArriveDate()" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                        </td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-buyOrderAddPage-buyOrdertable"></table>
				<input type="hidden" id="purchase-buyOrderAddPage-buyOrderDetails" name="buyOrderDetails"/>
	  		</div>
	  		<input type="hidden" id="purchase-buyOrderAddPage-settletype" name="buyOrder.settletype"/>
	  		<input type="hidden" id="purchase-buyOrderAddPage-paytype" name="buyOrder.settletype"/>
	  	</form> 
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
  		var $potable=$("#purchase-buyOrderAddPage-buyOrdertable");
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
  		$("#purchase-buttons-buyOrderPage").buttonWidget("initButtonType", 'add');
  	  	$("#purchase-buyOrderAddPage-buyOrdertable").datagrid({
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
  	 			$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('reloadFooter',[
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
  	 			$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('selectRow', rowIndex);
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
			var data=$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getSelected');
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
			var dataRow=$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRowIndex',dataRow);
							$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('deleteRow', rowIndex);
							var rowlen=$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows').length; 
							if(rowlen<15){
								$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							
							$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});

		$("#purchase-buyOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		
		$("#purchase-buyOrderAddPage-supplier").supplierWidget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			width:320,
			required:true,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				$("#purchase-supplier-showid-buyOrderAddPage").text("编号："+ data.id);
				$("#purchase-buyOrderAddPage-buydept").val(data.buydeptid);
				$("#purchase-buyOrderAddPage-buydept-hid").val(data.buydeptid);
				$("#purchase-buyOrderAddPage-settletype").val(data.settletype);
				$("#purchase-buyOrderAddPage-paytype").val(data.paytype);
				$("#purchase-buyOrderAddPage-buyuser").widget('setValue',data.buyuserid);
    			$.getJSON('basefiles/getContacterBy.do', {type:"2", id:data.id}, function(json){
    				if(json.length>0){
    					$("#purchase-buyOrderAddPage-handler").html("");
    					$("#purchase-buyOrderAddPage-handler").append("<option value=''></option>");
    					for(var i=0;i<json.length;i++){
    						$("#purchase-buyOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
    					}
    					$("#purchase-buyOrderAddPage-handler").val(data.contact);
    				}
    			});
				
				if(data.orderappend && data.orderappend=="1"){
					$("#purchase-buyOrderAddPage-orderappend").val("1");
				}else{
					$("#purchase-buyOrderAddPage-orderappend").val("0");					
				}

                //仓库关联问题
                var storage = $("#purchase-buyOrderAddPage-storage").val();
                if(storage == ""&& data.storageid){
                    $("#purchase-buyOrderAddPage-storage").widget('setValue',data.storageid);
                }else if(flag == 0){
                    $("#purchase-buyOrderAddPage-storage").widget('setValue',data.storageid);
                }
				$("#purchase-buyOrderAddPage-remark").focus();
			},
			onClear:function(){
				$("#purchase-supplier-showid-buyOrderAddPage").text("");
				$("#purchase-buyOrderAddPage-handler").val('');
				$("#purchase-buyOrderAddPage-handler").html('');
				$("#purchase-buyOrderAddPage-buydept").val('');
				$("#purchase-buyOrderAddPage-buydept-hid").val('');
				$("#purchase-buyOrderAddPage-buyuser").widget('clear');
				$("#purchase-buyOrderAddPage-settletype").val('');
				$("#purchase-buyOrderAddPage-paytype").val('');
				//$("#purchase-buyOrderAddPage-storage").widget('clear');
			}
		});	


		$("#purchase-buyOrderAddPage-storage").widget({ 
			name:'t_purchase_buyorder',
			col:'storageid',
			width:150,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				$("#purchase-buyOrderAddPage-remark").focus();
			}
		});
		/*
		$("#purchase-buyOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-buyOrderAddPage-buyuser").html("");
	    				$("#purchase-buyOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-buyOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-buyOrderAddPage-buyuser").html("");
			}
		});
		*/
		$("#purchase-buyOrderAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				var flag = $("#purchase-form-buyOrderAddPage").form('validate');
				if(flag==false){
					$.messager.alert("提醒",'请先完善采购订单基本信息');
					return false;
				}else{
	   				$("#purchase-buyOrderAddPage-remark").blur();
	   				orderDetailAddDialog();
				}
			}
	    });
  	});
  </script>
  </body>
</html>
