<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单新增页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="sales/addOrderCar.do" method="post">
	    	<div data-options="region:'north',border:false" style="height:135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-billid-orderAddPage" type="text" name="ordercar.id" class="len150" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="sales-businessdate-orderAddPage" class="len150" readonly="readonly" value="${busdate }" name="ordercar.businessdate"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-orderAddPage" name="ordercar.customerid" style="width: 300px;" required="required"/><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;"></span></td>
                        <td class="len80 left">仓库：</td>
                        <td><input name="ordercar.storageid" id="sales-storageid-orderAddPage" value="${defaultStorageid }" /></td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-orderAddPage" class="len150" name="ordercar.salesdept" readonly="readonly"/></td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><select id="sales-salesMan-orderAddPage" class="len150" name="ordercar.salesuser" disabled="disabled"></select></td>
						<td class="len80 left">车销人员：</td>
						<td><input id="sales-caruser-orderAddPage" class="len150" name="ordercar.caruser"/></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input type="text" name="ordercar.remark" style="width:680px;" /></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-orderAddPage"/>
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-orderCarAddPage" style="display: none;">
    	<div id="sales-addRow-orderCarAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-orderCarAddPage" data-options="iconCls:'button-edit'">修改</div>
    	<div id="sales-removeRow-orderCarAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
	<div id="sales-dialog-orderCarAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品行编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			fitColumns:false,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
                onLoadSuccess: function(data){
                    if(data.rows.length<12){
                        var j = 12-data.rows.length;
                        for(var i=0;i<j;i++){
                            $(this).datagrid('appendRow',{});
                        }
                    }else{
                        $(this).datagrid('appendRow',{});
                    }
                    countTotal();
                },
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-orderCarAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined && rowData.isdiscount==null){
    					beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount=='1'){
    						beginEditDetailDiscount();
    					}else if(rowData.isdiscount=='2'){
    						beginEditDetailBrandDiscount();
    					}else{
    						beginEditDetail(rowData);
    					}
    				}
    				
    			}
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');
    		$("#sales-addRow-orderCarAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-orderCarAddPage").click(function(){
	    		var row = $wareList.datagrid('getSelected');
	    		if(row.isdiscount=='1'){
					beginEditDetailDiscount();
				}else if(row.isdiscount=='2'){
					beginEditDetailBrandDiscount();
				}else{
					beginEditDetail(row);
				}
    		});
    		$("#sales-removeRow-orderCarAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-form-orderAddPage").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.lock == true){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	$("#sales-backid-orderPage").val(json.id);
		  		      	$("#sales-panel-orderPage").panel('refresh', 'sales/orderCarEditPage.do?id='+json.id);
		  		    }
		  		    else{
						if(json.msg != undefined){
							$.messager.alert("提醒",json.msg);
						}else{
							$.messager.alert("提醒","保存失败");
						}
		  		    }
		  		}
		  	});
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order_car',
				col:'customerid',
    			singleSelect:true,
    			width:130,
    			isopen:true,
    			onSelect:function(data){
					var html = "编号："+'<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
    				$("#sales-customer-showid-orderAddPage").html(html);
					$("#sales-salesMan-orderAddPage").val(data.salesuserid);
					if(data.salesdeptid!=null && data.salesdeptid!=""){
						$("#sales-salesDept-orderAddPage").widget("setValue",data.salesdeptid);
					}else{
						$("#sales-salesDept-orderAddPage").widget("clear");
					}
    			},
    			onClear:function(){
    				$("#sales-customer-showid-orderAddPage").text("");
    			}
    		});
    		$("#sales-storageid-orderAddPage").widget({ //仓库参照窗口
				referwid: "RL_T_BASE_STORAGE_INFO",
    			singleSelect:true,
    			required:true,
    			width:150,
				onSelect:function(){
					$("#sales-remark-orderDetailAddPage").focus();
				}
    		});
			$("#sales-salesDept-orderAddPage").widget({
				referwid:'RL_T_BASE_DEPARTMENT_SELLER',
				width:150,
				singleSelect:true
			});
			$("#sales-caruser-orderAddPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_CARUSER',
				width:150,
				required:true,
				singleSelect:true
			});
    		$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: "${order.salesdept}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-salesMan-orderAddPage").html("");
	    			$("#sales-salesMan-orderAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-salesMan-orderAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
			  			$("#sales-salesMan-orderAddPage").val("${order.salesuser }");
	    		}	
	    	});
    		$("#sales-buttons-orderPage").buttonWidget("initButtonType", 'add');
    	});

    	var $wareList = $("#sales-datagrid-orderAddPage"); //商品datagrid的div对象
   		<c:if test="${order.status=='9'}">
   			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-audit');
   		</c:if>
    	<c:if test="${order.status!='2'}">
   			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-auditDemand');
   		</c:if>
    </script>
  </body>
</html>
