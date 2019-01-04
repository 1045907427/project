<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>对应车辆</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true" id="line-layout-cartoline">
	    <div data-options="region:'center',split:true,border:true">
	    	<div id="logistics-toolbar-cartoline" style="width: 483px;">
	    		<form action="" id="logistics-form-cartolineQuery" method="post" style="padding-left: 5px;">
                    <input type="hidden" name="lineid" value="${param.lineid}"/>
	    			<table cellpadding="1" cellspacing="0" border="0">
	    				<tr>
				    		<input type="hidden" name="personid" value="${personid}" />
				    		<td>编码:</td>
				    		<td><input type="text" name="id" id="logistics-car-id" style="width: 70px;"/></td>
	    					<td>名称:</td>
	    					<td><input type="text" name="name" id="logistics-car-name" style="width: 100px;"/></td>
	    					<td colspan="2">
	    						<a href="javaScript:void(0);" id="logistics-query-cartoline" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="logistics-reload-cartoline" class="button-qr">重置</a>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
	    	</div>
	    	<table id="logistics-table-cartoline"></table>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right">
            <a href="javaScript:void(0);" id="logistics-save-cartoline" class="easyui-linkbutton" data-options="plain:false" title="确定">确定</a>
            <a href="javaScript:void(0);" id="logistics-savegoon-cartoline" class="easyui-linkbutton" data-options="plain:false" title="继续添加">继续添加</a>
		</div> 
     </div>
    <script type="text/javascript">
        var lineid='${param.lineid}';
    	$("#logistics-car-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:true,
   			width:110,
   			onlyLeafCheck:false,
   			view: true
   		});
    	
    	$(function(){
    		$("#logistics-addBrandid").val("${brandids}");
    		var carid3 = "";

			$("#logistics-table-cartoline").datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		checkOnSelect:true,
	  	 		selectOnCheck:true,
	  	 		pageSize:100,
	  	 		pagination:true,
				queryParams:$("#logistics-form-cartolineQuery").serializeJSON(),
				toolbar:'#logistics-toolbar-cartoline',
				columns:[[  
					{field:'ck',title:'',width:100,checkbox:true},
			        {field:'id',title:'编码',width:40,sortable:true},
					{field:'name',title:'名称',width:70,sortable:true},  
					{field:'weight',title:'装载限重',width:80,sortable:true
					},
					{field:'volume',title:'装载体积',width:80,sortable:true
					},
					{field:'driverid',title:'默认司机',width:80,sortable:true,
						formatter:function(val,rowData,rowIndex){
								return rowData.driverName;
							}
					},
					{field:'followid',title:'默认跟车',width:80,sortable:true,
						formatter:function(val,rowData,rowIndex){
								return rowData.followName;
							}
					}
			    ]],
			    url:'basefiles/getCarListForCombobox.do'
			});
    		
    		//查询
			$("#logistics-query-cartoline").click(function(){
	      		var queryJSON = $("#logistics-form-cartolineQuery").serializeJSON();
	      		$("#logistics-table-cartoline").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#logistics-reload-cartoline").click(function(){
				$("#logistics-form-cartolineQuery")[0].reset();
				$("#logistics-table-cartoline").datagrid("reload");
				
			});
    		
    		$("#logistics-savegoon-cartoline").click(function(){
    			var carid3="";//carid3获取需要执行添加操作的carid,修改的时候直接添加到数据库
    			var rows = $("#logistics-table-cartoline").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请勾选对应客户名称！");
    				return false;
    			}
    			//$("#logistics-beginCaredit").val("1");
    			for(var i=0;i<rows.length;i++){
    				if(carid3==""){
                        carid3=rows[i].id;
                    }else{
                        carid3+=","+rows[i].id;
					}
    			}

                //如果是修改页面操作，直接在数据库进行添加操作
                if(lineid!=undefined&&lineid!=''){
                    var ret = lineInfo_AjaxConn({lineid:lineid,carids:carid3},'basefiles/saveCarForLine.do');
                    var retJson = $.parseJSON(ret);
                    if(retJson.flag){
                        $dgInfoCar.datagrid('reload');
                        $("#line-dialog-car").dialog('close',true);
					}else{
                        $.messager.alert("提醒","添加车辆信息出错");
                        return false;
					}
                }
                // else{
                 //    //$("#logistics-beforeDelCarAdd").val(carid);
                 //    var queryJSON = $("#logistics-form-cartolineQuery").serializeJSON();
                 //    queryJSON['carids'] = $("#logistics-linecar").val();
                 //    $dgInfoCar.datagrid('options').url = 'basefiles/showCarListForLine.do';
                 //    $dgInfoCar.datagrid('load',queryJSON);
				// }
    		});
    		
    		$("#logistics-save-cartoline").click(function(){
    			var carid3="";
    			var rows = $("#logistics-table-cartoline").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请勾选对应客户名称！");
    				return false;
    			}

    			for(var i=0;i<rows.length;i++){
                    if(carid3==""){
                        carid3=rows[i].id;
                    }else{
                        carid3+=","+rows[i].id;
                    }
    			}

                //如果是修改页面操作，直接在数据库进行添加操作
                if(lineid!=undefined&&lineid!=''){
                    var ret = lineInfo_AjaxConn({lineid:lineid,carids:carid3},'basefiles/saveCarForLine.do');
                    var retJson = $.parseJSON(ret);
                    console.log(retJson.flag);
                    if(retJson.flag){
                        $dgInfoCar.datagrid('reload');
                        $("#line-dialog-car").dialog('close',true);
                    }else{
                        $.messager.alert("提醒","添加车辆信息出错");
                        return false;
                    }
                }
                // else{
                 //    //$("#logistics-beforeDelCarAdd").val(carid);
                 //    var queryJSON = $("#logistics-form-cartolineQuery").serializeJSON();
                 //    //console.log(queryJSON);
                 //    queryJSON['carids'] = $("#logistics-linecar").val();
                 //    //console.log(queryJSON);
                 //    $dgInfoCar.datagrid('options').url = 'basefiles/showCarListForLine.do';
                 //    $dgInfoCar.datagrid('load',queryJSON);
                 //    $("#line-dialog-car").dialog('close',true);
				// }

    		});
    	});
    </script>
  </body>
</html>
