<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	    				<td class="len165"><input type="text" id="storage-checkList-thisid" class="len130 easyui-validatebox" name="checkList.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len140 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${checkList.businessdate}" name="checkList.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136"><option>新增</option></select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">所属仓库：</td>
	    				<td>
	    				<input type="text" id="storage-checkList-storageid" name="checkList.storageid" class="len130" required="required" value="${checkList.storageid }"/></td>
	    				<td class="len80 left">生成方式：</td>
	    				<td colspan="3" style="text-align: left"><input type="text" id="storage-checkList-createtype" name="checkList.createtype" value="${checkList.createtype}"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5">
	    					<input type="text" name="checkList.remark" style="width:660px;" value="<c:out value="${checkList.remark }"></c:out>"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-checkListAddPage"></table>
	    	</div>
	    	<div data-options="region:'south',border:false" style="height:100px;">
	    		<div style="margin:10px 0 0 0;">
		    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
		    			<tr>
		    				<td class="len80 left">审核人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="len80 left">修改人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="len80 left">制单人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" value="${userName }" /></td>
		    			</tr>
		    			<tr>
		    				<td class="left">审核日期：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="left">修改日期：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="left">制单日期：</td>
		    				<td><input type="text" class="len130" value="${date }" readonly="readonly" /></td>
		    			</tr>
		    		</table>
	    		</div>
	    	</div>
	    	<input type="hidden" id="storage-checkList-checkListDetail" name="checkListDetailJson"/>
	    </form>
    </div>
    <div id="storage-toolbar-checkListAddPage">
		<a href="javascript:;" class="easyui-linkbutton" id="storage-addButton-checkListAddPage" title="添加新记录" data-options="iconCls:'button-add',plain:true<c:if test="${checkList.createtype=='2'}">,disabled:true</c:if>"></a>
		<a href="javascript:;" class="easyui-linkbutton" id="storage-editButton-checkListAddPage" title="修改选中记录" data-options="iconCls:'button-edit',plain:true"></a>
		<a href="javascript:;" class="easyui-linkbutton" id="storage-removeButton-checkListAddPage" title="删除选中行" data-options="iconCls:'button-delete',plain:true<c:if test="${checkList.createtype=='2'}">,disabled:true</c:if>"></a>
	</div>
    <div class="easyui-menu" id="storage-contextMenu-checkListAddPage">
    	<div id="storage-addRow-checkListAddPage" data-options="iconCls:'button-add'<c:if test="${checkList.createtype=='2'}">,disabled:true</c:if>">添加</div>
    	<div id="storage-editRow-checkListAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-checkListAddPage" data-options="iconCls:'button-delete'<c:if test="${checkList.createtype=='2'}">,disabled:true</c:if>">删除</div>
    </div>
    <div id="storage-dialog-checkListAddPage"></div>
    <script type="text/javascript">
		var CLD_footerobject = null;
    	$(function(){
    		$("#storage-checkList-storageid").widget({
                width:136,
                required:true,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false,
				disabled:true
    		});
    		$("#storage-checkList-createtype").widget({
    			name:'t_storage_checklist',
	    		width:130,
				col:'createtype',
				singleSelect:true,
				disabled:true
    		});
			$("#storage-datagrid-checkListAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: false,
				checkOnSelect:true,
				selectOnCheck:true,
    			<%--data: JSON.parse('${checkListDetailList}'),--%>
				url: 'storage/getCheckListDetailData.do?id=${checkList.id}',
				pagination:true,
				pageSize:500,
    			toolbar:'#storage-toolbar-checkListAddPage',
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-checkListAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-checkListAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
						$(this).datagrid('checkRow',rowIndex);
    					beginEditDetail();
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
    		//盘点单明细添加
    		$("#storage-addButton-checkListAddPage").click(function(){
    			if($(this).linkbutton('options').disabled){
					return false;
				}
    			beginAddDetail();
    		});
    		//盘点单明细修改
    		$("#storage-editButton-checkListAddPage").click(function(){
    			if($(this).linkbutton('options').disabled){
					return false;
				}
    			beginEditDetail();
    		});
    		//盘点单明细删除
    		$("#storage-removeButton-checkListAddPage").click(function(){
    			if($(this).linkbutton('options').disabled){
					return false;
				}
    			removeDetail();
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
			    		$.messager.alert("提醒","保存成功");
			    		$("#storage-panel-checkListPage").panel({
							href:"storage/checkListViewPage.do?id="+json.id,
							title:'盘点单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("警告","保存失败");
			    	}
			    }  
			}); 
    	});
    	//显示盘点单明细添加页面
    	function beginAddDetail(){
    		//验证表单
			var flag = $("#storage-form-chckListAdd").form('validate');
			if(flag==false){
				$.messager.alert("提醒",'请先选择所属仓库');
				$("#storage-checkList-storageid").focus();
				return false;
			}
			var storageid = $("#storage-checkList-storageid").widget("getValue");
			$('<div id="storage-dialog-checkListAddPage-content"></div>').appendTo('#storage-dialog-checkListAddPage');
    		$('#storage-dialog-checkListAddPage-content').dialog({  
			    title: '盘点单明细添加',  
			    width: 680,  
			    height: 400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    href: 'storage/showCheckListDetailAddPage.do?storageid='+storageid,  
			    modal: true,
			    buttons:[
			    	{  
	                    text:'确定',  
	                    iconCls:'button-save',
	                    plain:true,
	                    handler:function(){  
	                    	addSaveDetail(false);
	                    }  
	                },
	                {  
	                    text:'继续添加',
	                    iconCls:'button-add',
	                    plain:true,
	                    handler:function(){  
	                    	addSaveDetail(true);
	                    }  
	                }
			    ],
			    onClose:function(){
			    	$('#storage-dialog-checkListAddPage-content').dialog("destroy");
			    }
			});
			$('#storage-dialog-checkListAddPage-content').dialog("open");
    	}
    	//显示盘点单明细修改页面
    	function beginEditDetail(){
    		//验证表单
			var flag = $("#storage-form-chckListAdd").form('validate');
			if(flag==false){
				$.messager.alert("提醒",'请先选择所属仓库');
				$("#storage-checkList-storageid").focus();
				return false;
			}
			var row = $("#storage-datagrid-checkListAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		if(row.goodsid == undefined){
    			beginAddDetail();
    		}else{
    			$('<div id="storage-dialog-checkListAddPage-content"></div>').appendTo('#storage-dialog-checkListAddPage');
	    		$('#storage-dialog-checkListAddPage-content').dialog({  
				    title: '盘点单明细修改',  
				    width: 680,  
				    height: 400,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    href: 'storage/showCheckListDetailEditPage.do',  
				    modal: true,
				    buttons:[
				    	{  
		                    text:'确定',  
		                    iconCls:'button-save',
		                    plain:true,
		                    handler:function(){  
		                    	editSaveDetail(false);
		                    }  
		                },
		                {  
		                    text:'继续添加',
		                    iconCls:'button-add',
		                    plain:true,
		                    handler:function(){  
		                    	editSaveDetail(true);
		                    }  
		                }
				    ],
				    onClose:function(){
				    	$('#storage-dialog-checkListAddPage-content').dialog("destroy");
				    }
				});
				$('#storage-dialog-checkListAddPage-content').dialog("open");
			}
    	}
    	//保存盘点单
    	function addSaveDetail(goFlag){ //添加新数据确定后操作，
    		var flag = $("#storage-form-checkListDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-checkListDetailAddPage").serializeJSON();
    		var widgetJson = $("#storage-checkList-goodsid").widget('getObject');
    		widgetJson.editFlag=true;
    		var goodsInfo = {id:widgetJson.goodsid,name:widgetJson.goodsname,brandName:widgetJson.brandname,
    						model:widgetJson.model,barcode:widgetJson.barcode};
    		form.goodsInfo = goodsInfo;
    		var rowIndex = 0;
    		var rows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		$("#storage-datagrid-checkListAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			$("#storage-dialog-checkListAddPage-content").dialog('refresh', 'storage/showCheckListDetailAddPage.do');
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-checkListAddPage-content").dialog('close', true);
    		}
    		$("#storage-checkList-storageid").widget('disable');
    		countTotal();
    		
    	}
    	//修改保存
    	function editSaveDetail(goFlag){
    		var flag = $("#storage-form-checkListDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-checkListDetailEditPage").serializeJSON();
    		form.editFlag=true;
    		var row = $("#storage-datagrid-checkListAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-checkListAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		$("#storage-datagrid-checkListAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			beginAddDetail();
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-checkListAddPage-content").dialog('close', true)
    		}
    		countTotal();
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $("#storage-datagrid-checkListAddPage").datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
			   		var rowIndex = $("#storage-datagrid-checkListAddPage").datagrid('getRowIndex', row);
			   		$("#storage-datagrid-checkListAddPage").datagrid('deleteRow', rowIndex);
			   		countTotal(); 
			   		var rows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
			   		var index = -1;
			   		for(var i=0; i<rows.length; i++){
			   			if(rows[i].goodsid != undefined){
			   				index = i;
			   				break;
			  			}
			   		}
			   		if(index == -1){
			   			$("#storage-checkList-storageid").widget('enable');
			  		}
		    	}
	    	});	
    	}
    	//计算合计
    	function countTotal(){
    		var rows =  $("#storage-datagrid-checkListAddPage").datagrid('getRows');
    		var leng = rows.length;
    		var booknum = 0;
    		var realnum = 0;
    		var profitlossnum = 0;
    		var amount = 0;
    		for(var i=0; i<leng; i++){
    			booknum += Number(rows[i].booknum == undefined ? 0 : rows[i].booknum);
    			realnum += Number(rows[i].realnum == undefined ? 0 : rows[i].realnum);
    			profitlossnum += Number(rows[i].profitlossnum == undefined ? 0 : rows[i].profitlossnum);
    			amount += Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    		}
    		 $("#storage-datagrid-checkListAddPage").datagrid('reloadFooter',[{goodsid:'合计', booknum: booknum, realnum: realnum, profitlossnum: profitlossnum, amount: amount}]);
    	}
    	//获取仓库下商品明细
    	function getCheckListDetail(){
    		var storageid = $("#storage-checkList-storageid").widget("getValue");
    		var creattype = $("#storage-checkList-createtype").widget("getValue");
    		if(storageid==null || storageid==""){
    			$.messager.alert("提醒",'请先选择所属仓库');
				$("#storage-checkList-storageid").focus();
				return false;
    		}
    		//生成方式为系统生成时
    		if(creattype=="2"){
	    		$.ajax({   
		            url :'storage/getCheckListDetailList.do?storageid='+storageid,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	$("#storage-datagrid-checkListAddPage").datagrid("loadData",json);
		            	if(json.length<10){
		            		var j = 10-json.length;
		            		for(var i=0;i<j;i++){
		            			$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{});
		            		}
    					}else{
    						$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{});
    					}
		            }
		        });
		        $("#storage-addButton-checkListAddPage").linkbutton("disable");
		        $("#storage-removeButton-checkListAddPage").linkbutton("disable");
		        $("#storage-contextMenu-checkListAddPage").menu('disableItem','#storage-addRow-checkListAddPage');
		        $("#storage-contextMenu-checkListAddPage").menu('disableItem','#storage-removeRow-checkListAddPage');
	        }else{
	        	$("#storage-addButton-checkListAddPage").linkbutton("enable");
		        $("#storage-removeButton-checkListAddPage").linkbutton("enable");
		        $("#storage-contextMenu-checkListAddPage").menu('enableItem','#storage-addRow-checkListAddPage');
		        $("#storage-contextMenu-checkListAddPage").menu('enableItem','#storage-removeRow-checkListAddPage');
	        }
    	}
    	//控制按钮状态
    	$("#storage-buttons-checkListPage").buttonWidget("initButtonType","add");
    	
    	function isCheckListAdd(){
    		var flag=false;
			var oldid = "";
			var storageid = $("#storage-checkList-storageid").widget("getValue");
			$.ajax({   
	            url :'storage/isHaveCheckListByStorageid.do?storageid='+storageid,
	            type:'post',
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	flag = json.flag;
	            	oldid = json.oldid;
	            }
	        });
			if(flag){
				$.messager.confirm("提醒","该仓库存在未关闭的盘点单，暂不能新增盘点单，请审核相关调账单后再操作。<br/>是否跳转到未关闭的盘点单页面？",function(r){
					if(r){
						$("#storage-panel-checkListPage").panel({
							href:"storage/checkListViewPage.do?id="+oldid,
							title:'盘点单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
					}
				});
				$("#storage-checkList-storageid").widget("clear");
				return false;
			}
    	}
    	isCheckListAdd();
    </script>
  </body>
</html>
