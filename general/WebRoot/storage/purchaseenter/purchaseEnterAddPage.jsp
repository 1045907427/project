<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>采购入库单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-purchaseEnterAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="purchaseEnter.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-purchaseEnter-businessdate" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="purchaseEnter.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商:</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="storage-purchaseEnter-supplierid" name="purchaseEnter.supplierid" style="width: 320px;"/>
	    					<span id="purchase-supplier-showid-purchaseEnter" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
                            <input id="storage-purchaseEnter-storageid" type="text" name="purchaseEnter.storageid"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">采购部门：</td>
	    				<td>	
	    					<select id="storage-purchaseEnter-buydeptid" name="purchaseEnter.buydeptid" class="len150">
	    						<option></option>
	    						<c:forEach items="${deptList }" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">采购人员：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseEnter-buyuserid" name="purchaseEnter.buyuserid" style="width:130px;" />
	    				</td>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
                            <input id="storage-purchaseEnter-sourcetype" type="text" name="purchaseEnter.sourcetype" value="0"/>
	    				</td>
	    			</tr>
                    <tr><td>手工单号:</td>
                        <td><input id="storage-purchaseEnter-field04" type="text" name="purchaseEnter.field04"  style="width:150px" value="${purchaseEnter.field04 }"/></td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3" style="text-align: left">
                            <input type="text" name="purchaseEnter.remark" style="width: 400px;" value="<c:out value="${purchaseEnter.remark }"></c:out>"/>
                        </td>
                    </tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-purchaseEnterAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-purchaseEnter-purchaseEnterDetail" name="detailJson"/>
	    	<input type="hidden" id="storage-purchaseEnter-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-purchaseEnterAddPage" style="display: none;">
    	<div id="storage-addRow-purchaseEnterAddPage" data-options="iconCls:'button-add'">添加</div>
		<security:authorize url="/storage/purchaseEnterAddGift.do">
			<div id="storage-addGift-purchaseEnterAddPage" data-options="iconCls:'button-add'">添加赠品</div>
		</security:authorize>
    	<div id="storage-editRow-purchaseEnterAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-purchaseEnterAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-purchaseEnterAddPage"></div>
    <div id="storage-dialog-batchno-purchaseEnterAddPage"></div>
    <script type="text/javascript">

        //获取当前用户所在部门是否关联仓库 0不关联 1关联
        var flag = "${flag}";
    	$(function(){
			$("#storage-datagrid-purchaseEnterAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:{'total':10,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-purchaseEnterAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-purchaseEnterAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail("0");
    				}
    				else{
    					beginEditDetail(rowData);
    				}
    			},
    			onLoadSuccess:function(){
    				countTotal();
    			}
    		}).datagrid('columnMoving');
            $("#storage-purchaseEnter-sourcetype").widget({
                name:'t_storage_purchase_enter',
                col:'sourcetype',
                singleSelect:true,
                width:136,
                disabled:true
            });
            $("#storage-purchaseEnter-storageid").widget({
                name:'t_storage_purchase_enter',
                col:'storageid',
                singleSelect:true,
                width:136,
                required:true
            });

			$("#storage-purchaseEnter-buyuserid").widget({
				name:'t_base_buy_supplier',
				col:'buyuserid',
				width:130,
	    		async:false,
				singleSelect:true,
				onlyLeafCheck:false
			}); 		
    		$("#storage-purchaseEnter-supplierid").supplierWidget({
				required:true,
				onSelect:function(data){
					$("#purchase-supplier-showid-purchaseEnter").text("编号："+ data.id);
 					$("#storage-purchaseEnter-buydeptid").val(data.buydeptid);

                    //仓库关联问题
                    var storage = $("#storage-purchaseEnter-storageid").widget("getValue");

                    if(storage == "" && data.storageid){
                        $("#storage-purchaseEnter-storageid").widget("setValue",data.storageid);
                    }else if(flag == 0){
                        $("#storage-purchaseEnter-storageid").widget("setValue",data.storageid);
                    }

					$.ajax({
			            url :'basefiles/getPersonnelListByDeptid.do',
			            type:'post',
			            data:{deptid:data.buydeptid},
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	if(json.length>0){
		    					$("#storage-purchaseEnter-buyuserid").html("");
		    					for(var i=0;i<json.length;i++){
		    						$("#storage-purchaseEnter-buyuserid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		    					}
		    				}
		    				$("#storage-purchaseEnter-buyuserid").val(data.buyuserid);
			            }
			        });

 					$("#storage-purchaseEnter-buyuserid").widget('setValue',data.buyuserid);
				},
				onClear:function(){
					$("#purchase-supplier-showid-purchaseEnter").text("");
					$("#storage-purchaseEnter-buyuserid").widget('clear');
  					$("#storage-purchaseEnter-buydeptid").val("");
				}
    		});

    		//采购入库单明细添加
    		$("#storage-addRow-purchaseEnterAddPage").click(function(){
				var flag = $("#storage-contextMenu-purchaseEnterAddPage").menu('getItem','#storage-addRow-purchaseEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail("0");
    		});
            //采购入库单明细添加
            $("#storage-addGift-purchaseEnterAddPage").click(function(){
                var flag = $("#storage-contextMenu-purchaseEnterAddPage").menu('getItem','#storage-addGift-purchaseEnterAddPage').disabled;
                if(flag){
                    return false;
                }
                beginAddDetail("1");
            });
    		//采购入库单明细修改
    		$("#storage-editRow-purchaseEnterAddPage").click(function(){
    			var flag = $("#storage-contextMenu-purchaseEnterAddPage").menu('getItem','#storage-editRow-purchaseEnterAddPage').disabled;
				if(flag){
					return false;
				}
				var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
				if(row != null && row.goodsid != undefined){
					beginEditDetail(row);
				}
    		});
    		//采购入库单明细删除
    		$("#storage-removeRow-purchaseEnterAddPage").click(function(){
    			var flag = $("#storage-contextMenu-purchaseEnterAddPage").menu('getItem','#storage-removeRow-purchaseEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-purchaseEnterAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-purchaseEnterDetail").val(JSON.stringify(json));
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
			    		if(json.auditflag){
			    			if(json.purchaseEnterId!=null){
		            			msg = "审核成功，采购订单未完成，自动生成新的采购入库单："+json.purchaseEnterId;
		            			$.messager.alert("提醒","审核成功,生成采购进货单:"+json.downid+"."+msg);
		            		}else{
		            			$.messager.alert("提醒","审核成功,生成采购进货单:"+json.downid);
		            		}
			    		}else{
			    			$.messager.alert("提醒","保存成功");
			    		}
			    		$("#storage-buttons-purchaseEnterPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-purchaseEnterPage").panel({
							href:"storage/purchaseEnterEditPage.do?id="+json.id,
							title:'',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-purchaseEnterPage").buttonWidget("initButtonType","add");
    	$("#storage-hidden-billid").val("");
    </script>
  </body>
</html>
