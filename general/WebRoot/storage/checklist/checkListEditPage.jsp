<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>盘点单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-chckListAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:105px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input type="text" id="storage-checkList-thisid" class="len140 easyui-validatebox" name="checkList.id" readonly='readonly' value="${checkList.id}" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len150"><input type="text" id="storage-checkList-businessdate" class="Wdate" style="width: 135px"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${checkList.businessdate}" name="checkList.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-checkList-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == checkList.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="checkList.status" value="${checkList.status }" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">所属仓库：</td>
	    				<td>
                            <input type="text" id="storage-checkList-storageid" name="checkList.storageid" value="${checkList.storageid}" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">生成方式：</td>
	    				<td style="text-align: left">
	    					<select name="checkList.createtype" class="len136" disabled="disabled">
	    						<option value="1" <c:if test="${checkList.createtype=='1'}">selected="selected"</c:if>>手工生成</option>
	    						<option value="2" <c:if test="${checkList.createtype=='2'}">selected="selected"</c:if>>系统生成</option>
	    					</select>
	    					<input type="hidden" id="storage-checkList-createtype" name="checkList.createtype" value="${checkList.createtype}"/>
							<input type="hidden" id="storage-checkList-printtimes" value="${checkList.printtimes}"/>
	    				</td>
	    				<td class="len80 left">盘点状态：</td>
		    				<td style="text-align: left">
		    					<select id="storage-checkList-isfinish" name="checkList.isfinish" class="len136" disabled="disabled">
		    						<option value="0" <c:if test="${checkList.isfinish=='0'}">selected="selected"</c:if>>未完成</option>
		    						<option value="1" <c:if test="${checkList.isfinish=='1'}">selected="selected"</c:if>>完成</option>
		    					</select>
		    				</td>
	    				</tr>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">第几次盘点：</td>
	    				<td style="text-align: left">
	    					<input type="text" name="checkList.checkno" value="${checkList.checkno }" class="len140" readonly="readonly"/>
	    					<input type="hidden" name="checkList.sourceid" value="${checkList.sourceid}"/>
	    				</td>
	    				<td class="len80 left">盘点人：</td>
	    				<td style="text-align: left">
                            <input id="storage-checkList-checkuserid" type="text" style="width:140px;" name="checkList.checkuserid" value="${checkList.checkuserid}"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td>
	    					<input type="text" name="checkList.remark" style="width: 135px" value="<c:out value="${checkList.remark }"></c:out>"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-checkListAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-checkList-checkListDetail" name="checkListDetailJson" />
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
    		//盘点单明细按品牌添加
    		$("#storage-addRowBrand-checkListAddPage").click(function(){
				var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-addRowBrand-checkListAddPage').disabled;
				if(flag){
					return false;
				}
    			showCheckListDetailByBrand();
    		});
    		$("#storage-addRowGoodsSort-checkListAddPage").click(function(){
    			var flag = $("#storage-contextMenu-checkListAddPage").menu('getItem','#storage-addRowGoodsSort-checkListAddPage').disabled;
				if(flag){
					return false;
				}
				showCheckListDetailByGoodsSort();
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
    			<%--data: JSON.parse('${checkListDetailList}'),--%>
				url: 'storage/getCheckListDetailData.do?id=${checkList.id}',
				pagination:true,
				pageSize:500,
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
                    $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
    				if(rowData.goodsid == undefined){
    					//判断盘点单添加权限
    					<security:authorize url="/storage/checkListDetailAdd.do">
    					beginAddDetail();
    					</security:authorize>
    				}
    				else{
    					beginEditDetail(rowData);
    				}
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{});
	            		}
   					}
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						CLD_footerobject = footerrows[0];
						countTotal();
					}
    			}
    		}).datagrid('columnMoving');
            $("#storage-checkList-checkuserid").widget({
                referwid:'RL_T_BASE_PERSONNEL_STORAGER',
                width:136,
                required:true,
                singleSelect:true
            });
            //所属仓库
            $("#storage-checkList-storageid").widget({
                width:140,
                referwid:'RL_T_BASE_STORAGE_INFO',
                required:true,
                singleSelect:true,
                onlyLeafCheck:false
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
				    		$("#storage-buttons-checkListPage").buttonWidget("setDataID",{id:json.id,state:json.status,type:'edit'});
				    		$("#storage-checkList-status").val(json.status);
				    		$("#storage-checkList-isfinish").val("0");
				    		if(json.status=='2'){
				    			$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-button');
				    			$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-finish-button');
				    		}
			    		}
			    	}else{
			    		$.messager.alert("警告","保存失败");
			    	}
			    }  
			}); 
    	});
    	var finishEdit = true;
    	<security:authorize url="/storage/auditCheckList.do">
    	finishEdit = false;
    	</security:authorize>
    	//控制按钮状态
    	$("#storage-buttons-checkListPage").buttonWidget("setDataID",{id:'${checkList.id}',state:'${checkList.status}',type:'edit'});
    	$("#storage-hidden-billid").val("${checkList.id}");
    	//$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
    	//$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
    	<c:if test="${checkList.status=='2' }">
    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-finish-button');
    		<c:if test="${checkList.isfinish=='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'button-import');
	    		import_disabled = true;
	    		if(finishEdit){
	    			$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'button-save');
	    		}
	    	</c:if>
	    	<c:if test="${checkList.isfinish!='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'button-import');
	    		import_disabled = false;
	    	</c:if>
	    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
	    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-addadjust-button');
    	</c:if>
    	<c:if test="${checkList.status!='2' }">
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
    		<c:if test="${checkList.status=='3' }">
	    		<c:if test="${checkList.istrue=='1' }">
		    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
		    	</c:if>
		    	<c:if test="${checkList.istrue=='0' }">
		    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-add-button');
		    	</c:if>
	    	</c:if>
	    	$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-addadjust-button');
    	</c:if>
    </script>
  </body>
</html>
