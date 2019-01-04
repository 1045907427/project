<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>其他出库单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-storageOtherOutAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="easyui-validatebox" style="width: 135px" name="storageOtherOut.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="storageOtherOut.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">出库仓库：</td>
	    				<td>
                            <input type="text" id="storage-storageOtherOut-storageid" name="storageOtherOut.storageid"/>
	    				</td>
						<td class="len80 left">出库类型：</td>
						<td><input id="storage-otherOut-outtype" name="storageOtherOut.outtype" class="len130">
						</td>
	    				<td class="len80 left">相关部门：</td>
	    				<td><input id="storage-otherOut-deptid" name="storageOtherOut.deptid"/>
	    				</td>
	    			</tr>
					<tr>
						<td class="len80 left">来源类型：</td>
						<td><input id="storage-otherOut-sourcetype" name="storageOtherOut.sourcetype" disabled="disabled" class="len136"></td>
						<td class="len100 left">来源单据编号：</td>
						<td>
							<input id="storage-otherOut-sourceid" name="storageOtherOut.sourceid" class="len130" disabled="disabled">
						</td>
						<td class="len80 left">相关人员：</td>
						<td>
							<select id="storage-otherOut-userid" class="len136" name="storageOtherOut.userid">
							</select>
						</td>
					</tr>

	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left;"><input type="text" name="storageOtherOut.remark" style="width: 686px;"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-storageOtherOutDetail" />
	    		<table id="storage-datagrid-storageOtherOutAddPage"></table>
	    		<input type="hidden" name="saveaudit" id="storage-purchaseEnter-saveaudit" value="save"/>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-storageOtherOutAddPage" style="display: none;">
    	<div id="sales-addRow-storageOtherOutAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-storageOtherOutAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-storageOtherOutAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-storageOtherOutAddPage"></div>
    <script type="text/javascript">
        var oldouttype='',oldouttypename='';
    	$(function(){
    		$("#storage-datagrid-storageOtherOutAddPage").datagrid({ //销售商品明细信息编辑
    			columns: tableColJson,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:{'total':10,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-storageOtherOutAddPage").datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-storageOtherOutAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
                    var outtype=$("#storage-otherOut-outtype").widget("getValue");
    				if(rowData.goodsid == undefined){
                        if(outtype=='99'){
                            beginCostAddDetail();
                        }else{
                            beginAddDetail();
                        }
    				}
    				else{
                        if(outtype=='99'){
                            beginCostEditDetail();
                        }else{
                            beginEditDetail();
                        }
    				}
    			}
    		}).datagrid('columnMoving');
            //出库仓库
            $("#storage-storageOtherOut-storageid").widget({
                width:136,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false,
                required:true
            });
			$("#storage-otherOut-outtype").widget({
				referwid:'RL_T_SYS_CODE_OUT_TYPE',
				singleSelect: true,
				isdatasql: false,
				width: 130,
				onlyLeafCheck: false,
                onSelect:function(data){
                    var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
                    if(oldouttype!=data.code && rows[0].goodsid!=undefined){
                        if( oldouttype=='99' ){
                            if(oldouttype==''){
                                $("#storage-otherOut-outtype").widget("clear");
                            }else{
                                $("#storage-otherOut-outtype").widget("setValue",oldouttype);
                            }
                            $.messager.alert("提醒","出库类型为"+oldentertypename+"变更的时候需要先删除商品");
                            return;
                        }
                        if(data.code=='99'){
                            if(oldouttype==''){
                                $("#storage-otherOut-outtype").widget("clear");
                            }else{
                                $("#storage-otherOut-outtype").widget("setValue",oldouttype);
                            }
                            $.messager.alert("提醒","变更出库类型为"+data.codename+"的时候需要先删除商品");
                            return;
                        }
                    }
                    oldouttype=data.code;
                    oldentertypename=data.codename;
                },
                onClear:function(){
                    var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
                    if( rows[0].goodsid!=undefined){
                        if( oldouttype=='99' ){
                            $("#storage-otherOut-outtype").widget("setValue",oldouttype);
                            $.messager.alert("提醒","出库类型为"+oldentertypename+"变更的时候需要先删除商品");
                            return;
                        }
                    }
                    oldouttype='';
                    oldouttypename='';
                }
			});
			$("#storage-otherOut-deptid").widget({
				referwid:'RL_T_BASE_DEPARTMENT_1',
				singleSelect: true,
				isdatasql: false,
				width: 136,
				onlyLeafCheck: false,
				onSelect : function(data){
					$.ajax({
						url :'basefiles/getPersonnelListByDeptid.do',
						type:'post',
						data:{deptid:data.id},
						dataType:'json',
						async:false,
						success:function(json){
							if(json.length>0){
								$("#storage-otherOut-userid").html("");
								$("#storage-otherOut-userid").append("<option value=''></option>");
								for(var i=0;i<json.length;i++){
									$("#storage-otherOut-userid").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
								}
							}
						}
					});
				}
			});
    		$("#sales-addRow-storageOtherOutAddPage").click(function(){
                var outtype=$("#storage-otherOut-outtype").widget("getValue");
                if(outtype=='99'){
                    beginCostAddDetail();
                }else{
                    beginAddDetail();
                }

    		});
    		$("#sales-editRow-storageOtherOutAddPage").click(function(){
                var outtype=$("#storage-otherOut-outtype").widget("getValue");
                if(outtype=='99'){
                    beginCostEditDetail();
                }else{
                    beginEditDetail();
                }


    		});
    		$("#sales-removeRow-storageOtherOutAddPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-storageOtherOutAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
					$("#storage-purchaseEnter-storageOtherOutDetail").val(JSON.stringify(json));
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
			    			$.messager.alert("提醒","保存并审核成功");
			    		}else{
			    			$.messager.alert("提醒","保存成功");
			    		}
			    		$("#storage-buttons-storageOtherOutPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-storageOtherOutPage").panel({
							href:"storage/storageOtherOutEditPage.do?id="+json.id,
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    		$("#storage-buttons-storageOtherOutPage").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
