<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货入库单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-saleRejectEnterAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="saleRejectEnter.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="saleRejectEnter.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len130"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3" style="text-align: left;"><input type="text" id="storage-saleRejectEnter-customerid" name="saleRejectEnter.customerid" style="width: 300px;"/>
	    					<span id="storage-supplier-showid-saleRejectEnter" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 left">司机：</td>
	    				<td>
	    					<input id="sales-driverid-saleRejectEnterAddPage" class="len136" name="saleRejectEnter.driverid"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
                            <input type="text" name="saleRejectEnter.storageid" id="sales-storage-saleRejectEnterAddPage"/>
	    					<%--<select class="len136" name="saleRejectEnter.storageid" id="sales-storage-saleRejectEnterAddPage" >--%>
	    						<%--<c:forEach items="${storageList}" var="list">--%>
	    							<%--<option value="${list.id }">${list.name }</option>--%>
	    						<%--</c:forEach>--%>
	    					<%--</select>--%>
	    				</td>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<select id="sales-salesDept-saleRejectEnterAddPage" class="len136" name="saleRejectEnter.salesdept">
	    						<option value=""></option>
	    						<c:forEach items="${salesDept}" var="list">
	    							<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<select id="sales-salesMan-saleRejectEnterAddPage" class="len136" name="saleRejectEnter.salesuser">
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130" disabled="disabled">
	    						<option value="0">无</option>
	    					</select>
	    					<input type="hidden" name="saleRejectEnter.sourcetype" value="0"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3" style="text-align: left;"><input id="storage-saleRejectEnter-remark" type="text" name="saleRejectEnter.remark" style="width: 395px;"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-saleRejectEnterDetail" />
	    		<table id="storage-datagrid-saleRejectEnterAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-saleRejectEnterAddPage" style="display: none;">
    	<div id="sales-addRow-saleRejectEnterAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-saleRejectEnterAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-saleRejectEnterAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-saleRejectEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-saleRejectEnterAddPage").datagrid({ //销售商品明细信息编辑
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
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-saleRejectEnterAddPage").menu('show', {  
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
    			}
    		}).datagrid('columnMoving');
    		$("#storage-saleRejectEnter-customerid").customerWidget({ //客户参照窗口
    			ishead:true,
    			required:true,
    			onSelect:function(data){
    				$("#storage-supplier-showid-saleRejectEnter").text("编号："+ data.id);
    				$("#sales-salesDept-saleRejectEnterAddPage").val(data.salesdeptid);
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.salesdeptid}, function(json){
	    				if(json.length>0){
	    					$("#sales-salesMan-saleRejectEnterAddPage").html("");
	    					$("#sales-salesMan-saleRejectEnterAddPage").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-salesMan-saleRejectEnterAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#sales-salesMan-saleRejectEnterAddPage").val(data.salesuserid);
	    				}	
	    			});
	    			$("#storage-saleRejectEnter-remark").focus();
    			},
    			onClear:function(){
    				$("#storage-supplier-showid-saleRejectEnter").text("");
		    		$("#sales-salesMan-saleRejectEnterAddPage").val("");
		    		$("#sales-salesDept-saleRejectEnterAddPage").val("");
			    	
    			}
    		});
            //入库仓库
            $("#sales-storage-saleRejectEnterAddPage").widget({
                width:150,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false
            });
    		$("#sales-driverid-saleRejectEnterAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:130,
				singleSelect:true
    		});
    		$("#sales-salesDept-saleRejectEnterAddPage").change(function(){
    			var v = $(this).val();
    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#sales-salesMan-saleRejectEnterAddPage").html("");
	    				$("#sales-salesMan-saleRejectEnterAddPage").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#sales-salesMan-saleRejectEnterAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
    		});
    		$("#sales-addRow-saleRejectEnterAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-saleRejectEnterAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-saleRejectEnterAddPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-saleRejectEnterAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-saleRejectEnterDetail").val(JSON.stringify(json));
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
			    		$("#storage-buttons-saleRejectEnterPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-saleRejectEnterPage").panel({
							href:"storage/saleRejectEnterViewPage.do?id="+json.id,
							title:'销售退货入库单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
			$("#storage-saleRejectEnter-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					beginAddDetail();
				}
			});
    		$("#storage-buttons-saleRejectEnterPage").buttonWidget("initButtonType", 'add');
    	});
    	var $wareList = $("#storage-datagrid-saleRejectEnterAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
