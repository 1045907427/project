<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌对应商品</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true" id="goods-layout-brandtogoods">
	    <div data-options="region:'center',split:true,border:true">
	    	<div id="personnel-toolbar-brandtogoods" style="width: 780px; padding: 2px;">
	    		<form action="" id="personnel-form-brandtogoodsQuery" method="post" style="padding-left: 5px;">
	    			<input type="hidden" name="brandid" id="brandtogoods-hidden-brandid"/>
	    			<table cellpadding="2" cellspacing="0" border="0">
	    				<tr>
	    					<td>品牌名称:</td>
	    					<td><input name="id" type="text" id="brandtogoods-widget-id"/></td>
	    					<td style="padding-left: 10px">所属部门:</td>
	    					<td><input name="deptid" type="text" id="brandtogoods-widget-deptid"/></td>
	    				</tr>
						<tr>
							<td colspan="2"></td>
							<td colspan="2" align="right">
								<a href="javaScript:void(0);" id="personnel-query-brandtogoods" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="personnel-reload-brandtogoods" class="button-qr">重置</a>
							</td>
						</tr>
	    			</table>
	    		</form>
	    	</div>
	    	<table id="goods-table-brandtogoods"></table>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right" class="buttonDetailBG">
			<a href="javaScript:void(0);" id="personnel-save-brandtogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
			<a href="javaScript:void(0);" id="personnel-savegoon-brandtogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="确定并继续新增">确定并继续新增</a>
		</div> 
     </div>
    <script type="text/javascript">
    	$(function(){
    		var employetype = $("#personnel-employetype").val();

    		$("#brandtogoods-widget-id").widget({
    			width:150,
				referwid:'RL_T_BASE_GOODS_BRAND',
				singleSelect:true
    		});
    		$("#brandtogoods-widget-deptid").widget({
    			width:150,
				referwid:'RT_T_SYS_DEPT',
				singleSelect:false,
				onlyLeafCheck:false,
				onLoadSuccess:function(){
					$(this).widget('setValue',$("#personnel-init-deptid").val());
				}
    		});
    		
			$("#goods-table-brandtogoods").datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		checkOnSelect:true,
	  	 		selectOnCheck:true,
	  	 		pagination:true,
	  	 		pageSize:500,
				queryParams:{brandid:$("#personnel-addBrandid").val(),deptid:$("#personnel-init-deptid").val()},
				toolbar:'#personnel-toolbar-brandtogoods',
				columns:[[  
			        {field:'ck',title:'',width:100,checkbox:true},  
			        {field:'id',title:'编码',width:100},  
			        {field:'name',title:'名称',width:300}
			    ]],
			    url:'basefiles/getBrandListForCombobox.do',
			    onCheck:function(rowIndex,rowData){
                    loading("检测中...");
			    	var ret = personnel_AjaxConn({customeridStr:$("#personnel-personcustomer").val(),brandid:rowData.id,employetype:employetype},'basefiles/checkBrandAndCustomerRepeat2.do');
			   		var retJson = $.parseJSON(ret);
                    loaded();
			   		if(!retJson.flag){
			   			$.messager.alert('提醒','已存在该对应品牌和客户的人员!');
			   			$("#goods-table-brandtogoods").datagrid('uncheckRow',rowIndex);
			   		}
			    },
			    onCheckAll:function(rows){
                    loading("检测中...");
                    setTimeout(function(){
                        var num = 0;
                        for(var i=0;i<rows.length;i++){
                            var ret = personnel_AjaxConn({customeridStr:$("#personnel-personcustomer").val(),brandid:rows[i].id,employetype:employetype},'basefiles/checkBrandAndCustomerRepeat2.do');
                            var retJson = $.parseJSON(ret);
                            if(!retJson.flag){
                                num++;
                                $("#goods-table-brandtogoods").datagrid('uncheckRow',i);
                            }
                        }
                        loaded();
                        if(num > 0){
                            $.messager.alert('提醒','已存在该对应品牌和客户的人员!');
                        }
                    }, 50);
			    }
			});
    		
    		//回车事件
			controlQueryAndResetByKey("personnel-query-brandtogoods","personnel-reload-brandtogoods");
    		
    		//查询
			$("#personnel-query-brandtogoods").click(function(){
				$("#brandtogoods-hidden-brandid").val($("#personnel-addBrandid").val());
	      		var queryJSON = $("#personnel-form-brandtogoodsQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#goods-table-brandtogoods").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#personnel-reload-brandtogoods").click(function(){
				$("#brandtogoods-widget-id").widget('clear');
				$("#brandtogoods-widget-deptid").widget('clear');
				$("#personnel-form-brandtogoodsQuery")[0].reset();
				$("#goods-table-brandtogoods").datagrid("load",{brandid: $("#personnel-addBrandid").val()});
				
			});
    		
    		$("#personnel-savegoon-brandtogoods").click(function(){
				addPersonBrand(true);
    		});
    		
    		$("#personnel-save-brandtogoods").click(function(){
    		    addPersonBrand(false);
    		});
    		
    	});
    	function addPersonBrand(go){
			var rows = $("#goods-table-brandtogoods").datagrid('getChecked');
			if(rows.length == 0){
				$.messager.alert("提醒","请勾选对应品牌名称！");
				return false;
			}
			var brandid2 = "";
			for(var i=0;i<rows.length;i++){
				brandid2 += rows[i].id + ",";
			}
			var brandid = $("#personnel-addBrandid").val()+brandid2;
            $("#personnel-addBrandid").val(brandid);
            $("#personnel-beginBrandedit").val("1");
            getBrandToPersonList(brandid);
			var thispersonid = $("#basefiles-id-personnel").val();
			var employetype = $("#personnel-employetype").val();
			$.messager.confirm("警告","是否添加品牌?",function(r){
					if(r){
						loading("添加中..");
						$.ajax({
							url:'basefiles/addPersonBrand.do',
							data:{brandids:brandid2,personid:thispersonid,employetype:employetype},
							dataType:'json',
							type:'post',
							success:function(json){
								loaded();
								if(json.flag){
									$.messager.alert("提醒","添加成功");
									$dgPersonnelBrand.datagrid("reload");
                                    if(go){
                                        var queryJSON = $("#personnel-form-brandtogoodsQuery").serializeJSON();
                                        queryJSON['brandid'] = brandid;
                                        $("#goods-table-brandtogoods").datagrid("load",queryJSON);
									}else{
                                        $("#personnel-dialog-brand").dialog('close',true);
                                    }
								}else{
									$.messager.alert("提醒","添加失败");
								}
							}
						});
					}
				});

		}
    </script>
  </body>
</html>
