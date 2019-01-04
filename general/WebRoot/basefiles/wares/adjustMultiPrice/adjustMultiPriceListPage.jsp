<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>商品多价调整单</title>
	<%@include file="/include.jsp"%>
	<script type="text/javascript" src="js/jquery.excel.js"></script>
</head>

<body>
    <table id="goods-table-showAdjustMultiPriceList"></table>
	<div id="goods-query-showAdjustMultiPriceList" style="padding:0px;height:auto">
		<div class="buttonBG"> 
		    <security:authorize url="/basefiles/addAdjustMultiPricePage.do">
			    <a href="javaScript:void(0);" id="goods-add-addAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
			</security:authorize>
			<security:authorize url="/basefiles/editAdjustMultiPricePage.do">
			    <a href="javaScript:void(0);" id="goods-edit-editAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
			</security:authorize>
			<security:authorize url="/basefiles/deleteAdjustMultiPrice.do">
			    <a href="javaScript:void(0);" id="goods-audit-deleteAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
			</security:authorize>
			<security:authorize url="/basefiles/auditAdjustMultiPrice.do">
			    <a href="javaScript:void(0);" id="goods-audit-auditAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
			</security:authorize>
			
		  <!--
		    <security:authorize url="/basefiles/previewAdjustMultiPricePage.do">
	            <a href="javaScript:void(0);" id="goods-preview-previewAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-preview'" title="打印预览">打印预览</a>
		    </security:authorize>
		   	<security:authorize url="/basefiles/printAdjustMultiPricePage.do">
	            <a href="javaScript:void(0);" id="goods-print-printAdjustMultiPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-print'" title="打印">打印</a>
		    </security:authorize>
		   	-->
		</div>
		<div>
		    <form action="" id="goods-form-showAdjustMultiPriceList" method="post">
		       <table class="querytable">
    	            <tr>
						<td>业务日期：</td>
						<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/>
							到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
						</td>
						<td class="len30 right">状态：</td>
						<td>
							<select name="status" style="width:130px;">
								<option ></option>
								<option value="2" selected="selected">保存</option>
								<option value="3">审核通过</option>
							</select>
						</td>
    	                <td>编号：</td>
    	                <td><input type="text" id="delivery-id-deliveryAogorderSourceQueryPage" name="id" style="width: 160px;"/></td>

    	            </tr>
    	            <tr>
						<td class="len30 right">单据名称：</td>
						<td><input type="text" id="goods-name-showAdjustMultiPriceList" name="name" style="width: 225px;"/></td>
    	                <td colspan="2"></td>
					    <td colspan="2">
							<a href="javaScript:void(0);" id="goods-queay-goodsAogorder" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="goods-reload-goodsAogorder" class="button-qr">重置</a>
					    </td>
    	            </tr>
    	        </table>
		    </form>
		</div>
	</div>
    <script type="text/javascript">
    var initQueryJSON = $("#goods-form-showAdjustMultiPriceList").serializeJSON();
			//数据列表
		var goodsAogorderJson = $("#goods-table-showAdjustMultiPriceList").createGridColumnLoad({
				name :'',
				frozenCol : [[
							  {field:'ck',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
					          {field:'name',title:'调价单名称',width:80,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return getSysCodeName("status",value);
						        	}
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'adddeptname',title:'制单人部门名称',width:100,sortable:true},
							  {field:'addtime',title:'制单时间',width:130,sortable:true,
								  formatter: function(dateValue,row,index){
								  	 if(dateValue!=undefined){
								  		 var newstr=dateValue.replace("T"," ");
								  		 return newstr;
								     }
								     return " ";
								  }
							  },
							  {field:'auditusername',title:'审核人',width:60,sortable:true},
							  {field:'remark',title:'备注',width:80,sortable:true},
				             ]]
			});
    $(function(){
    	$("#goods-table-showAdjustMultiPriceList").datagrid({ 
	 		authority:goodsAogorderJson,
	 		frozenColumns: goodsAogorderJson.frozen,
			columns:goodsAogorderJson.common,
	 		fit:true,
	 		title:"",
	 		method:'post',
	 		rownumbers:true,
	 		pagination:true,
	 		idField:'id',
	 		sortName:'id',
	 		sortOrder:'desc',
	 		singleSelect:false,
	 		checkOnSelect:true,
	 		selectOnCheck:true,
	 		showFooter: false,
            pageSize:100,
			url: 'basefiles/showAdjustMultiPriceData.do',
			queryParams:initQueryJSON,
			toolbar:'#goods-query-showAdjustMultiPriceList',
			onDblClickRow:function(rowIndex, rowData){
				top.addOrUpdateTab('basefiles/showAdjustMultiPriceEditPage.do?id='+rowData.id, "多价调整单查看");
			}
        }).datagrid("columnMoving");
    	//回车时间
    	controlQueryAndResetByKey("goods-queay-goodsAogorder","goods-reload-goodsAogorder");
    	//查询
		$("#goods-queay-goodsAogorder").click(function(){
       		var queryJSON = $("#goods-form-showAdjustMultiPriceList").serializeJSON();
       		$("#goods-table-showAdjustMultiPriceList").datagrid("load",queryJSON);
		});
    	//重置
		$("#goods-reload-goodsAogorder").click(function(){
			$("#goods-form-showAdjustMultiPriceList").form("reset");
			var queryJSON = $("#goods-form-showAdjustMultiPriceList").serializeJSON();
			$("#goods-table-showAdjustMultiPriceList").datagrid('load', queryJSON);
		});
    	//新增
		$("#goods-add-addAdjustMultiPrice").click(function(){
			var url = "basefiles/showAdjustMultiPriceAddPage.do";
			top.addTab(url,'新增多价调整单');
       	});
		//修改
		$("#goods-edit-editAdjustMultiPrice").click(function(){
			var con = $("#goods-table-showAdjustMultiPriceList").datagrid('getSelected');
       		if(null == con){
       			$.messager.alert("提醒","请选择多价调整单!");
       			return false
       		}
			var url = "basefiles/showAdjustMultiPriceEditPage.do?id="+con.id;
			top.addTab(url,'修改多价调整单');
       	});

		//批量审核
		$("#goods-audit-auditAdjustMultiPrice").click(function(){
       		var rows = $("#goods-table-showAdjustMultiPriceList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择多价调整单!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].id;
       			}else{
       				ids += "," + rows[i].id;
       			}
       		}
       		$.messager.confirm('提醒','确定要审核吗?',function(r){   
			    if (r){   
			        $.ajax({   
			            url :'basefiles/auditsAdjustMultiPrice.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			                	$.messager.alert("提醒",json.msg);
			                	 var queryJSON = $("#goods-form-showAdjustMultiPriceList").serializeJSON();
	       						    $("#goods-table-showAdjustMultiPriceList").datagrid("load",queryJSON);
			            },
			            error:function(){
		                	loaded();
		                	$.messager.alert("错误","审核失败");
		                }
			        });
			    }   
			});  
       	});
		//批量删除
		$("#goods-audit-deleteAdjustMultiPrice").click(function(){
       		var rows = $("#goods-table-showAdjustMultiPriceList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择多价调整单!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].id;
       			}else{
       				ids += "," + rows[i].id;
       			}
       		}
       		$.messager.confirm('提醒','确定删除吗?',function(r){   
			    if (r){   
			        $.ajax({   
			            url :'basefiles/deletesAdjustMultiPrice.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			                	$.messager.alert("提醒",json.msg);
			                	 var queryJSON = $("#goods-form-showAdjustMultiPriceList").serializeJSON();
	       						 $("#goods-table-showAdjustMultiPriceList").datagrid("load",queryJSON);
			            },
			            error:function(){
		                	loaded();
		                	$.messager.alert("错误","删除出错");
		                }
			        });
			    }   
			});  
       	});
     });
    </script>
</body>
</html>