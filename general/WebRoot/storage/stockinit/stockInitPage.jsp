<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>库存初始化新增</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div id="stockinit-form-div" style="padding:0px;height:auto">
        <div id="stockinit-addbutton" class="buttonBG"></div>
		<form action="" id="stockinit-form-add" method="post">
			<table class="querytable">
    			<tr>
    				<td>所属仓库:</td>
    				<td><input id="stockinit-storageid" name="storageid" style="width:130px"/></td>
    				<td>商品:</td>
    				<td><input id="stockinit-query-goodsid" type="text" name="goodsid" style="width: 150px;"/></td>
                    <td>状态:</td>
                    <td>
                        <select name="status" style="width: 150px;">
                            <option></option>
                            <option value="2">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">关闭</option>
                        </select>
                    </td>
    			</tr>
    			<tr>
                    <td>品牌名称:</td>
                    <td>
                        <input id="stockinit-query-brandid" type="text" name="brandid" style="width: 130px;"/>
                    </td>
                    <td colspan="2"></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="storage-queay-stockinit" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="storage-reload-stockinit" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
		</form>
	</div>
 	<table id="stockinit-table-add"></table>
 	<div id="stockInit-add-page"></div>
 	<input type="hidden" id="combobox-goodid">
    <script type="text/javascript">
    	$(function(){
    		//根据初始的列与用户保存的列生成以及字段权限生成新的列
			var tableColJson = $("#stockinit-table-add").createGridColumnLoad({
				name :'storage_stockinit',
				frozenCol : [[]],
				commonCol : [[ 
								{field:'ck',checkbox:true},
								{field:'storageid',title:'所属仓库',width:80,sortable:true,
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.storageInfo!=null){
						        			return rowData.storageInfo.name;
						        		}else{
						        			return "";
						        		}
						        	}
						        },
								{field:'goodsid',title:'商品编码',width:70,sortable:true}, 
						        {field:'goodsInfo.name',title:'商品名称',width:150,aliascol:'goodsid',
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.goodsInfo!=null){
						        			return rowData.goodsInfo.name;
						        		}else{
						        			return "";
						        		}
						        	}
						        },  
						        {field:'goodsInfo.barcode',title:'条形码',width:90,aliascol:'goodsid',
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.goodsInfo!=null){
						        			return rowData.goodsInfo.barcode;
						        		}else{
						        			return "";
						        		}
						        	}
						        },
						        {field:'goodsInfo.brand',title:'商品品牌',width:80,aliascol:'goodsid',
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.goodsInfo!=null){
					        				return rowData.goodsInfo.brandName;
						        		}else{
						        			return "";
						        		}
						        	}
						        },
						        {field:'goodsInfo.model',title:'规格型号',width:100,aliascol:'goodsid',hidden:true,
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.goodsInfo!=null){
						        			return rowData.goodsInfo.model;
						        		}else{
						        			return "";
						        		}
						        	}
						        },
						        {field:'unitname',title:'单位',width:35,isShow:true},
						        {field:'price',title:'库存单价',width:60,align:'right',
						        	formatter:function(val){
						        		return formatterMoney(val);
						        	}
						        },
						        {field:'unitnum',title:'库存数量',width:80,align:'right',
						        	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
						        },
						        {field:'unitamount',title:'库存金额',width:80,align:'right',
						        	formatter:function(val){
						        		return formatterMoney(val);
						        	}
						        },
						        {field:'auxnum',title:'辅数量',width:80,align:'right',
						        	formatter:function(value,rowData,rowIndex){
						        		return rowData.auxnumdetail;
						        	}
						        },
						        {field:'storagelocationid',title:'所属库位',width:80,
						        	formatter:function(value,rowData,rowIndex){
						        		if(rowData.storageLocation!=null){
						        			return rowData.storageLocation.name;
						        		}else{
						        			return "";
						        		}
						        	}
						      },
                            {field:'batchno',title:'批次号',width:80},
                            {field:'produceddate',title:'生产日期',width:70},
                            {field:'addtime',title:'添加日期',width:80,sortable:true},
                            {field:'audittime',title:'审核日期',width:80,sortable:true},
                            {field:'remark',title:'备注',width:80,sortable:true},
                            {field:'status',title:'状态',width:55,
                                formatter:function(value){
                                    return getSysCodeName("status",value);
                                }
                            }
						    ]]
			});
			$('#stockinit-table-add').datagrid({ 
	  	 		authority:tableColJson,
	  	 		frozenColumns: tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		showFooter:true,
	  	 		method:'post',
	  	 		sortName:'goodsid',
	  	 		sortOrder:'asc',
	  	 		rownumbers:true,
	  	 		idField:'id',
		 		pagination:true,
		 		pageSize:100,
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		toolbar:'#stockinit-form-div'
			}).datagrid("columnMoving");
			$("#stockinit-addbutton").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/storage/showStockInitAddPage.do">
					{
						type:"button-add",
						handler:function(){
							//验证表单
							var flag = $("#stockinit-form-add").form('validate');
							if(flag==false){
								$.messager.alert("提醒",'请先选择所属仓库');
								return false;
							}
							$('<div id="stockInit-add-page-content"></div>').appendTo('#stockInit-add-page');
			 				$('#stockInit-add-page-content').dialog({  
							    title: '库存初始化新增',  
							    width: 680,  
							    height: 400,  
							    collapsible:false,
							    minimizable:false,
							    maximizable:true,
							    resizable:true,
							    closed: true,  
							    cache: false,  
							    href: 'storage/showStockInitAddPage.do',  
							    modal: true,
							    onClose:function(){
							    	$('#stockInit-add-page-content').dialog("destroy");
							    },
							    onLoad:function(){
							    	var storageid = $("#stockinit-storageid").widget("getValue");
							    	if(storageid!=null && storageid!=""){
							    		$("#stockInit-goodsid").focus();
							    		$("#stockinit-storageid-add").widget("setValue",storageid);
							    	}
							    	
							    }
							});
			 				$('#stockInit-add-page-content').dialog("open");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/showStorageInfoEditPage.do">
		 			{
			 			type:'button-edit',
			 			handler:function(){
			 				var data = $('#stockinit-table-add').datagrid("getSelected");
			 				if(data==null){
			 					$.messager.alert("提醒",'请先选择行数据');
			 					return false;
			 				}
			 				if(data.status!="1" && data.status!="2"){
			 					$.messager.alert("提醒",'已审核的数据不能修改！');
			 					return false;
			 				}
			 				$('<div id="stockInit-add-page-content"></div>').appendTo('#stockInit-add-page');
			 				$('#stockInit-add-page-content').dialog({  
							    title: '库存初始化修改',  
							    width: 680,  
							    height: 400,  
							    collapsible:false,
							    minimizable:false,
							    maximizable:true,
							    resizable:true,
							    closed: true,  
							    cache: false,  
							    href: 'storage/showStockInitEditPage.do?id='+data.id,  
							    modal: true,
							    onClose:function(){
							    	$('#stockInit-add-page-content').dialog("destroy");
							    }
							});
							$('#stockInit-add-page-content').dialog("open");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/storage/deleteStockInit.do">
		 			{
			 			type:'button-delete',
			 			handler:function(){
			 				var rows = $('#stockinit-table-add').datagrid("getChecked");
			 				var ids = "";
			 				for(var i=0;i<rows.length;i++){
			 					if(ids==""){
			 						ids = rows[i].id;
			 					}else{
			 						ids += ","+rows[i].id
			 					}
			 				}
			 				$.messager.confirm("提醒","是否删除库存初始化信息？",function(r){
								if(r){
					 				loading("删除中..");
					 				$.ajax({   
							            url :'storage/deleteStockInit.do',
							            type:'post',
							            data:{ids:ids},
							            dataType:'json',
							            success:function(json){
							            	loaded();
						            		$.messager.alert("提醒","删除成功："+json.delSuccess+"条<br/>删除失败:"+json.delFail+"条");
						            		$('#stockinit-table-add').datagrid("clearChecked");
						            		$('#stockinit-table-add').datagrid("reload");
							            },
							            error:function(){
							            	$.messager.alert("错误","删除出错");
							            	loaded();
							            }
							        });
							     }
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/storage/auditStockInit.do">
		 			{
			 			type:'button-audit',
			 			handler:function(){
			 				$.messager.confirm("提醒","是否审核库存初始化？",function(r){
								if(r){
					 				var rows = $('#stockinit-table-add').datagrid("getChecked");
					 				if(rows.length>0){
						 				var ids = "";
						 				for(var i=0;i<rows.length;i++){
						 					if(ids==""){
						 						ids = rows[i].id;
						 					}else{
						 						ids += ","+rows[i].id
						 					}
						 				}
						 				loading("审核中..");
						 				$.ajax({   
								            url :'storage/auditStockInit.do',
								            type:'post',
								            data:{ids:ids},
								            dataType:'json',
								            success:function(json){
								            	loaded();
							            		$.messager.alert("提醒","审核成功："+json.auditSuccess+"条<br/>审核失败:"+json.auditFail+"条");
							            		$('#stockinit-table-add').datagrid("reload");
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","审核出错");
								            }
								        });
							        }
							   }
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/storage/oppauditStockInit.do">
		 			{
			 			type:'button-oppaudit',
			 			handler:function(){
			 				$.messager.confirm("提醒","是否反审库存初始化？",function(r){
								if(r){
					 				var rows = $('#stockinit-table-add').datagrid("getChecked");
					 				if(rows.length>0){
						 				var ids = "";
						 				for(var i=0;i<rows.length;i++){
						 					if(ids==""){
						 						ids = rows[i].id;
						 					}else{
						 						ids += ","+rows[i].id
						 					}
						 				}
						 				loading("反审中..");
						 				$.ajax({   
								            url :'storage/oppauditStockInit.do',
								            type:'post',
								            data:{ids:ids},
								            dataType:'json',
								            success:function(json){
								            	loaded();
							            		$.messager.alert("提醒","反审成功："+json.oppauditSuccess+"条<br/>反审失败:"+json.oppauditFail+"条");
							            		$('#stockinit-table-add').datagrid("reload");
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","反审出错");
								            }
								        });
							        }
							    }
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/storage/stockInitImport.do">
		 			{
						type: 'button-import',
						attr: {
                            type:'importUserdefined',
                            version:'1',
                            url:'storage/importStockInit.do'
						}
					},
					</security:authorize>
					<security:authorize url="/storage/stockInitExport.do">
					{
						type: 'button-export',
						attr: {
                            datagrid:"#stockinit-table-add",
                            queryForm:"#stockinit-form-add",
                            type:'exportUserdefined',
                            name:'库存初始化',
                            url:'storage/exportStockInit.do'
						}
					},
					</security:authorize>
					{}
				],
				model:'bill',
				type:'list',
				tname:'t_storage_stockinit'
			});
			
			$("#stockinit-storageid").widget({
    			name:'t_storage_stockinit',
    			width:130,
    			col:'storageid',
    			singleSelect:true
    		});
    		$("#stockinit-query-goodsid").goodsWidget({
                singleSelect: true,
                isHiddenUsenum: true
            });
    		$("#stockinit-query-brandid").widget({
    			referwid:'RL_T_BASE_GOODS_BRAND',
    			width:130,
    			singleSelect:true
    		});
    		
    		//回车事件
			controlQueryAndResetByKey("storage-queay-stockinit","storage-reload-stockinit");
    		
    		$("#storage-queay-stockinit").click(function(){
    			var flag = $("#stockinit-form-add").form('validate');
		    	if(flag==false){
		    		return false;
		    	}
    			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#stockinit-form-add").serializeJSON();
	       		$('#stockinit-table-add').datagrid({
   					url:'storage/showStockInitListByStorageid.do',
   					queryParams:queryJSON
   				});
    		});
    		$("#storage-reload-stockinit").click(function(){
    			$("#stockinit-query-goodsid").goodsWidget("clear");
				$("#stockinit-storageid").widget("clear");
				$("#stockinit-form-add")[0].reset();
	       		$("#stockinit-table-add").datagrid("loadData",[]);
    		});
    	});
    	//ajax调用
		var ajaxCall = function (Data, Action) {
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return ajax.responseText;
		}
    </script>
  </body>
</html>
