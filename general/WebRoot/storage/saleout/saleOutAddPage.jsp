<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>销售出库单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-saleOutAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="saleOut.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="saleOut.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">出库仓库：</td>
	    				<td>
	    					<select id="storage-saleOut-storageid" name="storageOtherEnter.storageid" class="len136" >
	    						<c:forEach items="${storageList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" id="storage-saleOut-hidden-storageid" name="saleOut.storageid" />
	    				</td>
	    				<td class="len80 left">客户:</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="storage-saleOut-customerid" name="saleOut.customerid" style="width: 300px;"/>
	    					<span id="storage-supplier-showid-saleOut" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<select id="storage-saleOut-salesdept" name="saleOut.salesdept" class="len136">
	    						<option></option>
	    						<c:forEach items="${deptList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<select id="storage-saleOut-salesuser" name="saleOut.salesuser" class="len136">
	    						<option></option>
	    						<c:forEach items="${userList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select disabled="disabled" class="len136"><option>无</option></select>
	    					<input type="hidden" name="saleOut.sourcetype" value="0"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售单据：</td>
	    				<td class="len165"><input class="len150" readonly='readonly' value="${saleOut.saleorderid }" /></td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3" style="text-align: left">
	    					<input id="storage-saleOut-bill-remark" type="text" name="saleOut.remark" style="width: 415px;"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-saleOutAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-saleOut-saleOutDetail" name="detailJson"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-saleOutAddPage" style="display: none;">
    	<div id="storage-addRow-saleOutAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="storage-addRow-saleOutAddDiscountPage" data-options="iconCls:'button-add'">添加折扣</div>
    	<div id="storage-editRow-saleOutAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-saleOutAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-saleOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$('#storage-contextMenu-saleOutAddPage').menu({  
			    onClick:function(item){  
			    	var flag = item.disabled;
					if(flag==true){
						return false;
					}
					if(item.text=="添加"){
						beginAddDetail();
					}else if(item.text=="添加折扣"){
						beginAddDiscountDetail();
					}else if(item.text=="编辑"){
						beginEditDetail();
					}else if(item.text=="删除"){
						removeDetail();
					}
	    			
			    }  
			}); 
			$("#storage-datagrid-saleOutAddPage").datagrid({ //发货单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:{'total':12,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-saleOutAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-saleOutAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount!='1'){
    						beginEditDetail();
    					}else{
    						beginEditDetailDiscount();
    					}
    				}
    			},
    			onLoadSuccess:function(){
    				countTotal();
    			}
    		}).datagrid('columnMoving');    		
    		$("#storage-saleOut-storageid").change(function(){
				$("#storage-saleOut-hidden-storageid").val($(this).val());
				$("#storage-saleOut-customerid").focus();
    		});
    		$("#storage-saleOut-customerid").customerWidget({
				required:true,
				onSelect:function(data){
					$("#storage-supplier-showid-saleOut").text("编号："+ data.id);
					$("#storage-saleOut-salesdept").val(data.salesdeptid);
  					var deptid = data.salesdeptid;
	    			$.ajax({   
			            url :'basefiles/getPersonnelListByDeptid.do',
			            type:'post',
			            data:{deptid:deptid},
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	if(json.length>0){
		    					$("#storage-saleOut-salesuser").html("");
		    					for(var i=0;i<json.length;i++){
		    						$("#storage-saleOut-salesuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		    					}
		    				}
			            }
			        });
			        $("#storage-saleOut-salesuser").val(data.salesuserid);
			        $("#storage-saleOut-bill-remark").focus();
				},
				onClear:function(){
					$("#storage-supplier-showid-saleOut").text("");
				}
    		});
    		$("#storage-saleOut-salesdept").change(function(){
    			var deptid = $(this).val();
    			$.ajax({   
		            url :'basefiles/getPersonnelListByDeptid.do',
		            type:'post',
		            data:{deptid:deptid},
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	if(json.length>0){
	    					$("#storage-saleOut-salesuser").html("");
	    					for(var i=0;i<json.length;i++){
	    						$("#storage-saleOut-salesuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
	    				}
		            }
		        });
    		});
    		
    		$("#storage-form-saleOutAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
					$("#storage-saleOut-saleOutDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","保存成功");
			    		$("#storage-buttons-saleOutPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-saleOutPage").panel({
							href:"storage/saleOutViewPage.do?id="+json.id,
							title:'发货单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
			$("#storage-saleOut-bill-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					beginAddDetail();
				}
			});
    	});
    	//显示发货单明细添加页面
    	function beginAddDetail(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var storageid = $("#storage-saleOut-storageid").val();
			var customerid = $("#storage-saleOut-customerid").customerWidget("getValue");
			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
    		$('#storage-dialog-saleOutAddPage-content').dialog({  
			    title: '发货单明细添加',  
			    width: 680,  
			    height: 400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    modal: true,
			    href: 'storage/showSaleOutDetailAddPage.do?storageid='+storageid+"&customerid="+customerid,  
			    onClose:function(){
			    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
			    },
			    onLoad:function(){
			    	$("#storage-saleOut-goodsid").focus();
			    }
			});
			$('#storage-dialog-saleOutAddPage-content').dialog("open");
    	}
    	//显示盘点单明细修改页面
    	function beginEditDetail(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		if(row.goodsid == undefined){
    			beginAddDetail();
    		}else{
    			if(row.isdiscount!='1'){
	    			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
		    		$('#storage-dialog-saleOutAddPage-content').dialog({  
					    title: '发货单明细修改',  
					    width: 680,  
					    height: 400,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'storage/showSaleOutDetailEditPage.do',  
					    modal: true,
					    onClose:function(){
					    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
					    },
					    onLoad:function(){
					    	$("#storage-saleOut-unitnum").focus();
					    	$("#storage-saleOut-unitnum").select();
					    }
					});
					$('#storage-dialog-saleOutAddPage-content').dialog("open");
				}else{
					beginEditDetailDiscount();
				}
			}
    	}
    	//折扣添加页面
    	function beginAddDiscountDetail(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var storageid = $("#storage-saleOut-storageid").val();
			var customerid = $("#storage-saleOut-customerid").customerWidget("getValue");
			var sourceid = $("#storage-saleOut-sourceid").val();
			var id = $("#storage-saleOut-id").val();
			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
    		$('#storage-dialog-saleOutAddPage-content').dialog({  
			    title: '发货单折扣添加',  
			    width: 680,  
			    height: 400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    href: 'storage/showSaleOutDetailDiscountAddPage.do?sourceid='+sourceid+'&id='+id,  
			    buttons:[
			    	{  
	                    text:'确定',  
	                    iconCls:'button-save',  
	                    plain:true,
	                    handler:function(){  
	                    	addSaveDetailDiscount();
	                    }  
	                }
			    ],
			    onClose:function(){
			    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
			    }
			});
			$('#storage-dialog-saleOutAddPage-content').dialog("open");
    	}
    	//折扣修改页面
    	function beginEditDetailDiscount(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
   			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
    		$('#storage-dialog-saleOutAddPage-content').dialog({  
			    title: '发货单折扣修改',  
			    width: 680,  
			    height: 400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    href: 'storage/showSaleOutDetailDiscountEditPage.do',  
			    modal: true,
			    buttons:[
			    	{  
	                    text:'确定',  
	                    iconCls:'button-save',  
	                    plain:true,
	                    handler:function(){  
	                    	editSaveDetailDiscount();
	                    }  
	                }
			    ],
			    onClose:function(){
			    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
			    }
			});
			$('#storage-dialog-saleOutAddPage-content').dialog("open");
    	}
    	//折扣添加
    	function addSaveDetailDiscount(){
    		var flag = $("#storage-form-saleOutDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailAddPage").serializeJSON();
    		var widgetJson = $("#storage-saleOut-goodsid").goodsWidget('getObject');
    		form.goodsInfo = widgetJson;
    		var rowIndex = 0;
    		var rows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
   			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		$("#storage-saleOut-storageid").attr('disabled',"disabled");
    		countTotal();
    	}
    	//折扣修改保存
    	function editSaveDetailDiscount(){
    		var flag = $("#storage-form-saleOutDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailAddPage").serializeJSON();
    		var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-saleOutAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
   			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		countTotal();
    	}
    	//保存发货单明细
    	function addSaveDetail(goFlag){ //添加新数据确定后操作，
    		var flag = $("#storage-form-saleOutDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailAddPage").serializeJSON();
    		var widgetJson = $("#storage-saleOut-goodsid").storageGoodsWidget('getObject');
    		var goodsInfo = {id:widgetJson.goodsid,name:widgetJson.goodsname,brandName:widgetJson.brandname,
    						model:widgetJson.model,barcode:widgetJson.barcode,boxnum:widgetJson.boxnum};
    		form.goodsInfo = goodsInfo;
    		var rowIndex = 0;
    		var rows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		var updateFlag = false;
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			if(rowJson.goodsid==widgetJson.goodsid){
    				rowIndex = i;
    				updateFlag = true;
    				break;
    			}
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		if(updateFlag){
    			$.messager.alert("提醒", "此商品已经添加！");
    			return false;
    		}else{
    			$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		}
    		if(goFlag){ //go为true确定并继续添加一条
    			otherEnterformReset();
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		}
    		$("#storage-saleOut-storageid").attr('disabled',"disabled");
    		countTotal();
    		
    	}
    	//修改保存
    	function editSaveDetail(goFlag){
    		var flag = $("#storage-form-saleOutDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailEditPage").serializeJSON();
    		var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-saleOutAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			beginAddDetail();
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		}
    		countTotal();
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
			   		var rowIndex = $("#storage-datagrid-saleOutAddPage").datagrid('getRowIndex', row);
			   		$("#storage-datagrid-saleOutAddPage").datagrid('deleteRow', rowIndex);
			   		countTotal(); 
			   		var rows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
			   		var index = -1;
			   		for(var i=0; i<rows.length; i++){
			   			if(rows[i].goodsid != undefined){
			   				index = i;
			   				break;
			  			}
			   		}
			   		if(index == -1){
			   			$("#storage-saleOut-storageid").attr('disabled',false);
			  		}
		    	}
	    	});	
    	}
    	//计算合计
    	function countTotal(){
    		var rows =  $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		var countNum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		for(var i=0;i<rows.length;i++){
    			countNum = Number(countNum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    		}
    		$("#storage-datagrid-saleOutAddPage").datagrid("reloadFooter",[{goodsid:'合计',unitnum:countNum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}]);
    	}
    	//控制按钮状态
    	$("#storage-buttons-saleOutPage").buttonWidget("initButtonType","add");
    	$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print");
    	$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-preview");
    	$("#storage-hidden-billid").val("");
    	$("#storage-saleOut-hidden-storageid").val($("#storage-saleOut-storageid").val());
    </script>
  </body>
</html>
