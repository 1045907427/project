<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>盘点单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-chckListAdd" action="storage/addCheckListSave.do" method="post">
	    	<div data-options="region:'north',border:false" style="height:105px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input type="text" id="storage-checkList-thisid" class="len140 easyui-validatebox" name="checkList.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" style="width: 135px"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="checkList.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-checkList-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
   									<option value="${list.code }" <c:if test="${list.code == '0'}">selected="selected"</c:if>>${list.codename }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">所属仓库：</td>
	    				<td>
                            <input type="text" id="storage-checkList-storageid" name="checkList.storageid"/>
	    				</td>
	    				<td class="len80 left">生成方式：</td>
	    				<td style="text-align: left">
	    					<select id="storage-checkList-createtype" name="checkList.createtype" class="len136">
	    						<option value="1">手工生成</option>
	    						<option value="2">系统生成</option>
	    					</select>
	    				</td>
	    				<td class="len80 left">盘点状态：</td>
	    				<td style="text-align: left">
	    					<select id="storage-checkList-isfinish" class="len136" disabled="disabled">
	    						<option value="0">未完成</option>
	    						<option value="1">完成</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">第几次盘点：</td>
	    				<td style="text-align: left">
	    					<input type="text" name="checkList.checkno" value="1" readonly="readonly" class="len140"/>
	    				</td>
	    				<td class="len80 left">盘点人：</td>
                        <td style="text-align: left">
                            <input id="storage-checkList-checkuserid" type="text" style="width:136px;" name="checkList.checkuserid"/>
                        </td>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td>
	    					<input type="text" name="checkList.remark" style="width:135px;" />
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-checkListAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-checkList-checkListDetail" name="checkListDetailJson"/>
	    	<input type="hidden" id="storage-checkList-checksave-checkListDetail" name="checksave" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-checkListAddPage" style="display: none;">
    	<security:authorize url="/storage/checkListDetailAdd.do">
    	<div id="storage-addRowBrand-checkListAddPage" data-options="iconCls:'button-add'">按品牌添加</div>
    	</security:authorize>
    	<security:authorize url="/storage/checkListDetailAdd.do">
    	<div id="storage-addRowGoodsSort-checkListAddPage" data-options="iconCls:'button-add'">按商品分类添加</div>
    	</security:authorize>
    	<security:authorize url="/storage/checkListDetailAdd.do">
    	<div id="storage-addRow-checkListAddPage" data-options="iconCls:'button-add'">添加</div>
    	</security:authorize>
    	<div id="storage-editRow-checkListAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<security:authorize url="/storage/checkListDetailDelete.do">
    	<div id="storage-removeRow-checkListAddPage" data-options="iconCls:'button-delete'">删除</div>
    	</security:authorize>
    </div>
    <div id="storage-dialog-checkListAddPage"></div>
    <div id="storage-dialog-checkListBrandPage"></div>
    <script type="text/javascript">
		var CLD_footerobject = null;
		$(function(){
    		var storageJson = JSON.parse('${storageListJson}');
            $("#storage-checkList-storageid").widget({
                width:140,
                required:true,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false,
                onSelect:function(data){
                    var storageid = data.id;
                    getCheckListDetail();
                    if(null!=storageJson){
                        var userid=  null;
                        for(var i=0;i<storageJson.length;i++){
                            var object = storageJson[i];
                            if(storageid==object.id){
                                userid = object.manageruserid;
                                break;
                            }
                        }
                        $("#storage-checkList-checkuserid").val(userid);
                    }
                }
            });
    		$("#storage-checkList-createtype").change(function(){
				getCheckListDetail();
    		});
			$("#storage-datagrid-checkListAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			idField:'goodsid',
    			fit:true,
				singleSelect: false,
				checkOnSelect:true,
				selectOnCheck:true,
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-checkListAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-checkListAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
//    			onSortColumn:function(sort, order){
//    				var rows = $("#storage-datagrid-checkListAddPage").datagrid("getRows");
//    				var dataArr = [];
//    				for(var i=0;i<rows.length;i++){
//    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
//    						dataArr.push(rows[i]);
//    					}
//    				}
//    				dataArr.sort(function(a,b){
//    					if(order=="asc"){
//    						return a[sort]>b[sort]?1:-1
//    					}else{
//    						return a[sort]<b[sort]?1:-1
//    					}
//    				});
//    				$("#storage-datagrid-checkListAddPage").datagrid("loadData",dataArr);
//    				return false;
//    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
						$(this).datagrid('checkRow',rowIndex);
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(){
    				var rows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{});
    					}
    				}
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						CLD_footerobject = footerrows[0];
						countTotal();
					}
    			}
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');
            $("#storage-checkList-checkuserid").widget({
                referwid:'RL_T_BASE_PERSONNEL_STORAGER',
                width:136,
                required:true,
                singleSelect:true
            });
    		//盘点单明细按品牌添加
    		$("#storage-addRowBrand-checkListAddPage").click(function(){
				var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-addRowBrand-checkListAddPage').disabled;
				if(flag){
					return false;
				}
				$("#storage-checkList-createtype").val("2");
    			showCheckListDetailByBrand();
    		});
    		$("#storage-addRowGoodsSort-checkListAddPage").click(function(){
    			var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-addRowGoodsSort-checkListAddPage').disabled;
				if(flag){
					return false;
				}
				$("#storage-checkList-createtype").val("2");
				showCheckListDetailByGoodsSort();
    		});
    		//盘点单明细添加
    		$("#storage-addRow-checkListAddPage").click(function(){
				var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-addRow-checkListAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//盘点单明细修改
    		$("#storage-editRow-checkListAddPage").click(function(){
    			var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-editRow-checkListAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//盘点单明细删除
    		$("#storage-removeRow-checkListAddPage").click(function(){
    			var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-removeRow-checkListAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-chckListAdd").form({  
			    onSubmit: function(){
			    	//获取盘点单明细json
			    	var json = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
                    var flag2 = false;
                    for(var i=0;i<json.length;i++){
                        if(!isObjectEmpty(json[i])){
                            flag2 = true;
                            break;
                        }
                    }
			    	var flag = $(this).form('validate');
			    	if(flag==false || flag2 == false){
                        $.messager.alert("提醒","输入必填项或添加商品明细信息!");
			    		return false;
			    	}
//                    $("#storage-checkList-checkListDetail").val(JSON.stringify(json));
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
                        $("#storage-panel-checkListPage").panel({
                            href:'storage/checkListEditPage.do?id='+json.id,
                            title:'',
                            cache:false,
                            maximized:true,
                            border:false
                        });
			    		if(json.auditflag){
			    			$.messager.alert("提醒","盘点完成");
			    		}else{
				    		$.messager.alert("提醒","保存成功");
				    		$("#storage-buttons-checkListPage").buttonWidget("addNewDataId", json.id);
				    		$("#storage-buttons-checkListPage").buttonWidget("setDataID",{id:json.id,state:json.status,type:'edit'});
				    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-finish-button');
		   					$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-button');
				    		$("#storage-checkList-status").val(json.status);
				    		$("#storage-checkList-thisid").val(json.id);
				    		$("#storage-hidden-billid").val(json.id);
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-checkListPage").buttonWidget("initButtonType","add");
    	//$("#storage-panel-checkListPage").panel("setTitle","盘点单新增");
    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-addadjust-button');
        $("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
    </script>
  </body>
</html>
