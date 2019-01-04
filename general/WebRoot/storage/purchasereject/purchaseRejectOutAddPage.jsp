<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购退货出库单单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-purchaseRejectOutAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="purchaseRejectOut.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="purchaseRejectOut.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136"><option>新增</option></select>
	    					<input type="hidden" id="storage-purchaseRejectOut-status" name="purchaseRejectOut.status"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商：</td>
	    				<td colspan="3" style="text-align: left;">
	    					<input type="text" id="storage-purchaseRejectOut-supplierid" name="purchaseRejectOut.supplierid" style="width: 320px;"/>
	    					<span id="purchase-supplier-showid-purchaseRejectOut" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
                        <td class="len80 left">来源类型：</td>
                        <td>
                            <input id="storage-purchaseRejectOut-sourcetype" type="text" name="purchaseRejectOut.sourcetype" value="0"/>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">退货仓库：</td>
	    				<td>
	    					<select id="storage-purchaseRejectOut-storageid" name="purchaseRejectOut.storageid" class="len150" >
	    						<c:forEach items="${storageList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input id="storage-purchaseRejectOut-storageid-hidden" type="hidden" name="purchaseRejectOut.storageid"/>
	    				</td>
	    				<td class="len80 left">采购部门：</td>
	    				<td>
	    					<select id="storage-purchaseRejectOut-buydeptid" name="purchaseRejectOut.buydeptid" class="len136">
	    						<option></option>
	    						<c:forEach items="${deptList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">采购员：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseRejectOut-buyuserid" name="purchaseRejectOut.buyuserid" style="width:135px;" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left;"><input id="storage-purchaseEnterBill-remark" type="text" name="purchaseRejectOut.remark" style="width: 600px;"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-purchaseRejectOutDetail" />
	    		<table id="storage-datagrid-purchaseRejectOutAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-purchaseRejectOutAddPage" style="display: none;">
    	<!-- <div id="storage-addRow-purchaseRejectOutAddPage" data-options="iconCls:'icon-add'">添加</div> -->
    	<div id="storage-editRow-purchaseRejectOutAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<%--<div id="storage-removeRow-purchaseRejectOutAddPage" data-options="iconCls:'button-delete'">删除</div>--%>
    </div>
    <div id="storage-dialog-purchaseRejectOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-purchaseRejectOutAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:{'total':10,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-purchaseRejectOutAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-purchaseRejectOutAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(){
    				countTotal();
    			}
    		}).datagrid('columnMoving');
            $("#storage-purchaseRejectOut-sourcetype").widget({
                name:'t_storage_purchasereject_out',
                col:'sourcetype',
                singleSelect:true,
                width:136,
                disabled:true
            });
    		$("#storage-purchaseRejectOut-storageid").change(function(){
    			$("#storage-purchaseRejectOut-storageid-hidden").val($(this).val());
    		});
    		$("#storage-purchaseRejectOut-buyuserid").widget({
				name:'t_base_buy_supplier',
				col:'buyuserid',
				width:135,
	    		async:false,
				singleSelect:true,
				onlyLeafCheck:false
			}); 	
    		$("#storage-purchaseRejectOut-supplierid").supplierWidget({
	    		required:true,
	    		onSelect:function(data){
	    			$("#purchase-supplier-showid-purchaseRejectOut").text("编号："+ data.id);
	    			$("#storage-purchaseRejectOut-buydeptid").val(data.buydeptid);
	    			/*
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.buydeptid}, function(json){
	    				if(json.length>0){
	    					$("#storage-purchaseRejectOut-buyuserid").html("");
	    					$("#storage-purchaseRejectOut-buyuserid").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#storage-purchaseRejectOut-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#storage-purchaseRejectOut-buyuserid").val(data.buyuserid);
	    				}	
	    			});
	    			*/
	    			$("#storage-purchaseRejectOut-buyuserid").widget('setValue',data.buyuserid);
	    			$("#storage-purchaseEnterBill-remark").focus();
	    		},
	    		onClear:function(){
	    			$("#purchase-supplier-showid-purchaseRejectOut").text("");
		    		$("#storage-purchaseRejectOut-buyuserid").widget('clear');
		  			$("#storage-purchaseRejectOut-buydeptid").val("");
	    		}
    		});
    		/*
    		$("#storage-purchaseRejectOut-buydeptid").change(function(){
    			var deptid = $(this).val();
    			$.ajax({   
		            url :'basefiles/getPersonnelListByDeptid.do',
		            type:'post',
		            data:{deptid:deptid},
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	if(json.length>0){
	    					$("#storage-purchaseRejectOut-buyuserid").html("");
	    					for(var i=0;i<json.length;i++){
	    						$("#storage-purchaseRejectOut-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
	    				}
		            }
		        });
    		});
    		*/
    		$("#storage-addRow-purchaseRejectOutAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#storage-editRow-purchaseRejectOutAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#storage-removeRow-purchaseRejectOutAddPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-purchaseRejectOutAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-purchaseRejectOutDetail").val(JSON.stringify(json));
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
			    		$("#storage-buttons-purchaseRejectOutPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-purchaseRejectOutPage").panel({
							href:"storage/purchaseRejectOutViewPage.do?id="+json.id,
							title:'采购退货出库单单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    		$("#storage-buttons-purchaseRejectOutPage").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
