<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨通知单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-allocateNoticePage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="storage-datagrid-allocateNoticePage"></table>
            <div id="storage-datagrid-toolbar-allocateNoticePage" style="padding:2px;height:auto">
                <form action="" id="storage-form-query-allocateNoticePage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                               	到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                            <td>调出仓库:</td>
                            <td><input id="storage-query-outstorageid" type="text" name="outstorageid"/></td>
                            <td>状态:</td>
                            <td>
                                <select name="status" style="width: 165px;"><option></option><option value="2" selected="selected">保存</option><option value="3">审核通过</option><option value="4">关闭</option></select>
                            </td>
                        </tr>
                        <tr>
                            <td>编号:</td>
                            <td><input type="text" name="id" style="width: 225px;"/></td>
                            <td>调入仓库:</td>
                            <td><input id="storage-query-enterstorageid" type="text" name="enterstorageid"/></td>
                            <td colspan="2">
                                <a href="javaScript:void(0);" id="storage-queay-allocateNotice" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="storage-reload-allocateNotice" class="button-qr">重置</a>
                                <span id="storage-query-advanced-allocateNotice"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
    	</div>
    </div>
     <script type="text/javascript">
     	var initQueryJSON = $("#storage-form-query-allocateNoticePage").serializeJSON();
    	$(function(){
    		//按钮
			$("#storage-buttons-allocateNoticePage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/allocateNoticeAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							top.addOrUpdateTab('storage/showAllocateNoticeAddPage.do', "调拨通知单新增");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeEditPage.do">
<!--					{-->
<!--						type: 'button-edit',-->
<!--						handler: function(){-->
<!--							var con = $("#storage-datagrid-allocateNoticePage").datagrid('getSelected');-->
<!--							if(con == null){-->
<!--								$.messager.alert("提醒","请选择一条记录");-->
<!--								return false;-->
<!--							}	-->
<!--							top.addOrUpdateTab('storage/showAllocateNoticeEditPage.do?id='+ con.id, "调拨通知单修改");-->
<!--						}-->
<!--					},-->
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeViewPage.do">
					{
						type: 'button-view',
						handler: function(){
							var con = $("#storage-datagrid-allocateNoticePage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							top.addOrUpdateTab('storage/showAllocateNoticeEditPage.do?id='+ con.id, "调拨通知单查看");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeImport.do">
					{
						type: 'button-import',
						attr: {
							type:'importUserdefined',
							url:'storage/allocateNoticeImport.do',
					 		importparam:'必填项：调出仓库，调入仓库，商品编码，箱数，个数。</br>选填：条形码，批次号，生产日期，备注</br>调出仓库，调入仓库请填写仓库名称</br>批次管理的商品请指定批次号或者生产日期',
							onClose: function(){ //导入成功后窗口关闭时操作，
						         $("#storage-datagrid-allocateNoticePage").datagrid('reload');	//更新列表	                                                                                        
							}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeExport.do">
					{
						type: 'button-export',
						attr: {
                            datagrid:"#storage-datagrid-allocateNoticePage",
                            queryForm: "#storage-form-query-allocateNoticePage",
                            type:'exportUserdefined',
                            name:'调拨通知单明细列表',
                            url:'storage/exportAllocateNoticeList.do'
						}
					},
					</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            name:'t_storage_allocate_notice',
                            //查询针对的表格id
                            datagrid:'storage-datagrid-allocateNoticePage',
                            plain:true
                        }
                    },
					{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_storage_allocate_notice'
			});
			var allocateNoticeJson = $("#storage-datagrid-allocateNoticePage").createGridColumnLoad({
				name :'t_storage_allocate_notice',
				frozenCol : [[
								{field:'idck',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'outstorageid',title:'调出仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.outstoragename;
					        	}
							  },
							  {field:'enterstorageid',title:'调入仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.enterstoragename;
					        	}
							  },
							  {field:'sourcetype',title:'来源类型',width:90,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("allocateNotice-sourcetype",value);
					        	}
							  },
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:120,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true},
							  {field:'audittime',title:'审核时间',width:120,sortable:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#storage-datagrid-allocateNoticePage").datagrid({ 
		 		authority:allocateNoticeJson,
		 		frozenColumns: allocateNoticeJson.frozen,
				columns:allocateNoticeJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:true,
				url: 'storage/showAllocateNoticeList.do',
				queryParams:initQueryJSON,
				toolbar:'#storage-datagrid-toolbar-allocateNoticePage',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('storage/showAllocateNoticeEditPage.do?id='+ rowData.id, "调拨通知单查看");
				}
			}).datagrid("columnMoving");
			$("#storage-query-enterstorageid").widget({
				name:'t_storage_allocate_notice',
	    		width:150,
				col:'enterstorageid',
				view:true,
				singleSelect:true
			});
			$("#storage-query-outstorageid").widget({
				name:'t_storage_allocate_notice',
	    		width:150,
				col:'outstorageid',
				view:true,
				singleSelect:true
			});
			//通用查询组建调用
//			$("#storage-query-advanced-allocateNotice").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_allocate_notice',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-allocateNoticePage',
//		 		plain:true
//			});
			
			//回车事件
			controlQueryAndResetByKey("storage-queay-allocateNotice","storage-reload-allocateNotice");
			
			//查询
			$("#storage-queay-allocateNotice").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#storage-form-query-allocateNoticePage").serializeJSON();
	       		$("#storage-datagrid-allocateNoticePage").datagrid("load",queryJSON);
			});
			//重置
			$("#storage-reload-allocateNotice").click(function(){
				$("#storage-query-outstorageid").widget("clear");
				$("#storage-query-enterstorageid").widget("clear");
				$("#storage-form-query-allocateNoticePage")[0].reset();
				var queryJSON = $("#storage-form-query-allocateNoticePage").serializeJSON();
	       		$("#storage-datagrid-allocateNoticePage").datagrid("load",queryJSON);
			});
    	});
    </script>
  </body>
</html>
