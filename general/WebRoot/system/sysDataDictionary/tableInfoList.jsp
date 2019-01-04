<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据字典-表信息管理列表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
  </head>
  
  <body>
  	 <script type="text/javascript">
  	 $(document).ready(function(){
  	 	$('#sysDataDictionary-table-showTableInfoList').datagrid({ 
  	 		fit:true,
            striped: true,
            collapsible:true,
  	 		method:'post',
  	 		title:'',
  	 		rownumbers:true,
  	 		pagination:true,
  	 		idField:'tablename',
  	 		singleSelect:true,
			toolbar:'#sysDataDictionary-query-showTableInfoList',
		    url:'sysDataDictionary/showTableInfoPageList.do',  
		    columns:[[  
		    	{field:'tablename',title:'表名',sortable:true}, 
		    	{field:'tabledescname',title:'描述名'},
		        {field:'tabletype',title:'表类型',width:60,sortable:true,
			        formatter:function(val){
  						if(val=='1'){
  							return '系统';
  						}else if(val=='2'){
  							return '业务';
  						}else if(val=='3'){
  							return '虚拟';
  						}
	  				}
		    	},
		        {field:'moduletype',title:'模块类型',width:60,
			        formatter:function(val,row,index){
	  						if(row.moduletypename!=''){
	  							return row.moduletypename;
	  						}else{
	  							return val;
	  						}
	  			}},
		        {field:'createmethod',title:'创建类型',width:60,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '系统预置';
	  						}
	  						else
	  						{
	  							return '自建';
	  						}
	  			}},
                {field:'isdatarule',title:'支持数据权限',width:80,
                    formatter:function(val){
                        if(val=='1'){
                            return '是';
                        }
                        else
                        {
                            return '否';
                        }
                }},
                {field:'state',title:'使用状态',width:60,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '停用';
	  						}
	  			}}, 
		        {field:'usehistory',title:'使用历史库',width:50,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '是';
	  						}
	  						else
	  						{
	  							return '否';
	  						}
	  			}}, 
		        {field:'useversion',title:'使用版本库',width:50,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '是';
	  						}
	  						else
	  						{
	  							return '否';
	  						}
	  			}},
		        {field:'useautoencoded',title:'使用自动编号',width:50,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '是';
	  						}
	  						else
	  						{
	  							return '否';
	  						}
	  			}},  
		        {field:'usetreelist',title:'是否树形显示',width:50,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '是';
	  						}
	  						else
	  						{
	  							return '否';
	  						}
	  			}}, 
		        {field:'refertreecol',title:'树形父节点列名',width:80},
		        {field:'adddate',title:'建立日期',width:65,sortable:true},
		        {field:'addsignname',title:'建立人',width:60},
		        {field:'modifydate',title:'修改日期',width:65,sortable:true},
		        {field:'modifysignname',title:'修改人',width:60}, 
		    	{field:'remark',title:'备注',width:80},
		        {field:'datasource',title:'数据来源',width:80,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '手工添加';
	  						}
	  						else if(val=='2')
	  						{
	  							return '自动导入';
	  						}
	  						else if(val=='3')
	  						{
	  							return '手工导入';
	  						}
	  						else
	  						{
	  							return '其他';
	  						}
	  			}}
		    ]],
		    view: detailview,  
            detailFormatter:function(index,row){ 
                return '<div style="padding:2px"><table id="sysDataDictionary-ddv-' + index + '"></table>'+
                		'<div id="sysDataDictionary-ddv-oper-'+index+'">'+
                		'<a href="javaScript:void(0);" class="easyui-linkbutton tableColumnAutoImportButton" iconCls="button-add" plain="true" pid="'+row.id+'" pname="'+row.tablename+'" divid="sysDataDictionary-ddv-'+index+'">自动导入</a>'+
                		'<a href="javaScript:void(0);" class="easyui-linkbutton tableColumnAddButton" iconCls="button-add" plain="true" pid="'+row.id+'" pname="'+row.tablename+'" divid="sysDataDictionary-ddv-'+index+'">新增</a>'+
                		'<a href="javaScript:void(0);" class="easyui-linkbutton tableColumnEditButton" iconCls="button-edit" plain="true" pid="'+row.id+'" pname="'+row.tablename+'" divid="sysDataDictionary-ddv-'+index+'">修改</a>'+
                		'<a href="javaScript:void(0);" class="easyui-linkbutton tableColumnDeleteButton" iconCls="button-delete" plain="true" pid="'+row.id+'" pname="'+row.tablename+'" divid="sysDataDictionary-ddv-'+index+'">删除</a>'+
                		'<a href="javaScript:void(0);" class="easyui-linkbutton tableColumnReloadButton" iconCls="button-refresh" plain="true" pid="'+row.id+'" pname="'+row.tablename+'" divid="sysDataDictionary-ddv-'+index+'">刷新</a>'+
                		'</div></div>';  
            },  
            onExpandRow: function(index,row){  
                $('#sysDataDictionary-ddv-'+index).datagrid({ 
                	title:'字段列表', 
                    url:'sysDataDictionary/showTableColumnPageAllList.do?fixtablename='+row.tablename,  
                    toolbar:'#sysDataDictionary-ddv-oper-'+index,
                    singleSelect:true,  
                    rownumbers:true, 
                    height:300,  
                    sortName:'usepk desc,colorder',
                    sortOrder:'asc',
                    columns:[[  
				    	{field:'columnname',title:'列名',width:100,sortable:true},	 
				    	{field:'colchnname',title:'列描述名',width:100},		    	
				        {field:'coldatatype',title:'数据类型',width:60,sortable:true}, 		    	
				        {field:'colwidth',title:'字段长度',width:50}, 		    	
				        {field:'coldecimal',title:'小数位数',width:50},
				        {field:'coldefault',title:'默认值',width:50},
				        {field:'usepk',title:'是否主键',width:55,sortable:true,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '<span style="color:red;">是</span>';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usefixed',title:'固定字段',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}}, 
				        {field:'usecoded',title:'编码字段',width:60,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}}, 
			  			{field:'codedcoltype',title:'编码类型',width:50},
				        {field:'colorder',title:'顺序',width:50,sortable:true},
				        {field:'usenull',title:'NULL值',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usedataprivilege',title:'支持数据权限',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usecolprivilege',title:'支持字段权限',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usecolquery',title:'可做查询条件',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usedataexport',title:'支持数据导出',width:50,
					        formatter:function(val){
			  						if(val=='1'){
			  							return '是';
			  						}
			  						else
			  						{
			  							return '否';
			  						}
			  			}},
				        {field:'usecolrefer',title:'支持参照窗口',width:60,
					        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}else{
		  							return '否';
		  						}
			  				}
			  			},	
			  			{field:'wname',title:'参照窗口名称',width:60}, 
			  			{field:'isshow',title:'是否引用显示',width:60,
					        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}else{
		  							return '否';
		  						}
			  				}
			  			}, 
			  			{field:'isopenedit',title:'启用后是否可以修改',width:50,
					        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}
		  						else
		  						{
		  							return '否';
		  						}
			  				}
			  			},
			  			{field:'isrequired',title:'是否必填',width:50,
					        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}else{
		  							return '否';
		  						}
			  				}
			  			},		
				    	{field:'coldescription',title:'列说明',width:100}
				    ]],  
                    onResize:function(){  
                        $('#sysDataDictionary-table-showTableInfoList').datagrid('fixDetailRowHeight',index);  
                    },  
                    onLoadSuccess:function(){
                        setTimeout(function(){  
                            $('#sysDataDictionary-table-showTableInfoList').datagrid('fixDetailRowHeight',index);  
                        },50);  
                    }
                });  
                $.parser.parse("#sysDataDictionary-ddv-oper-"+index);
                $('#sysDataDictionary-table-showTableInfoList').datagrid('fixDetailRowHeight',index);  
            }
		});  
	});
		$(document).ready(function(){
			//回车事件
			controlQueryAndResetByKey("sysDataDictionary-queay-queryTableInfoList","sysDataDictionary-queay-reloadTableInfoList");
		
			//查询
			$("#sysDataDictionary-queay-queryTableInfoList").click(function(){
				//查询参数直接添加在url中         
				var queryJSON = $("#sysDataDictionary-form-tableinfoList").serializeJSON();
	       		$("#sysDataDictionary-table-showTableInfoList").datagrid("load",queryJSON);
			});
			//重置
			$("#sysDataDictionary-queay-reloadTableInfoList").click(function(){
				$("#sysDataDictionary-form-tableinfoList")[0].reset();
	       		//表结构信息清空
	        	$("#sysDataDictionary-table-showTableInfoList").datagrid("load",{});
			});
			//显示读取数据库导入
			$("#sysDataDictionary-add-addTableInfo").click(function(){
				$('#sysDataDictionary-dialog-tableInfoOper').dialog({ 
				    title: '表信息新增',
				    width: 800,  
				    height: 400, 
				    closed: true,  
				    cache: false,  
				    href: 'sysDataDictionary/showTableInfoAddPage.do',  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-addTableInfo',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#sysDataDictionary-dialog-tableInfoOper').dialog("open");
			});
			//显示表信息导入页面
			$("#sysDataDictionary-add-autoDBImportTableInfo").click(function(){
				$('#sysDataDictionary-window-tableInfoOper').window({  
					title:'表描述导入管理',
				    width: 500,  
				    height: 500,
				    closed: true,  
				    cache: false,  
				    href: 'sysDataDictionary/showTableInfoDBImportListPage.do',  
				    modal: true
				});
				$('#sysDataDictionary-window-tableInfoOper').window("open");
			});
			//显示表信息修改页面
			$("#sysDataDictionary-edit-editTableInfo").click(function(){
				var tableinfo = $("#sysDataDictionary-table-showTableInfoList").datagrid('getSelected');
		    	if(tableinfo==null){
		    		$.messager.alert("提醒","请选择表描述信息！");
		    		return false;
		    	}
		    	var url = "sysDataDictionary/showTableInfoEditPage.do?tablename="+tableinfo.tablename.toLowerCase();
		    	$('#sysDataDictionary-dialog-tableInfoOper').dialog({  
				    title: '表信息修改',  
				    width: 800,   
				    height: 400, 
				    closed: true,
				    cache: false,
				    href: url,
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-editTableInfo',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#sysDataDictionary-dialog-tableInfoOper').dialog("open");
				
			});
			$("#sysDataDictionary-copy-copyTableInfo").click(function(){
				var tableinfo = $("#sysDataDictionary-table-showTableInfoList").datagrid('getSelected');
		    	if(tableinfo==null){
		    		$.messager.alert("提醒","请选择表描述信息！");
		    		return false;
		    	}
		    	var url = "sysDataDictionary/showTableInfoCopyPage.do?tablename="+tableinfo.tablename.toLowerCase();
		    	$('#sysDataDictionary-dialog-tableInfoOper').dialog({  
				    title: '复制表及表字段',  
				    width: 800,   
				    height: 400, 
				    closed: true,
				    cache: false,
				    href: url,
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-copyTableInfo',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#sysDataDictionary-dialog-tableInfoOper').dialog("open");
			});
			///表描述删除
			$("#sysDataDictionary-remove-removeTableInfo").click(function(){
				$.messager.confirm("删除确认","是否删除表描述信息?",function(r){
    				if(r){    					
						var tableinfo = $("#sysDataDictionary-table-showTableInfoList").datagrid('getSelected');
				    	if(tableinfo==null){
				    		$.messager.alert("提醒","请选择表描述信息！");
				    		return false;
				    	}
				    	$.ajax({   
				            url :'sysDataDictionary/deleteTableInfo.do?tablename='+tableinfo.tablename.toLowerCase(),
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","删除成功！");
				            		$('#sysDataDictionary-table-showTableInfoList').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒",(json.msg || "删除失败！"));
				            	}
				            }
				        });
    				}
    			});
			});
			
			//显示表结构导入页面
			$(".tableColumnAutoImportButton").live("click",function(){				
		    	var tablename = $(this).attr("pname");  	
		    	var divid = $(this).attr("divid");
		    	var url = 'sysDataDictionary/showTableColumnDBImportListPage.do?tablename='+tablename+'&divid='+divid;
		    	var tableColumnOper=$('#sysDataDictionary-window-tableColumnOper');
				$('#sysDataDictionary-window-tableColumnOper').window({  
					title:'表结构导入管理',
				    width: 800,  
				    height: 400,
				    closed: true,  
				    cache: false, 
				    href:url,
				    modal: true
				});
				tableColumnOper.window("open");
			});
			//显示表结构添加页面
			$(".tableColumnAddButton").live("click",function(){
				var divid = $(this).attr("divid");
		    	var tablename = $(this).attr("pname");  
		    	var url = 'sysDataDictionary/showTableColumnAddPage.do?tablename='+tablename+"&divid="+divid;
		    	var tableColumnOper=$('#sysDataDictionary-dialog-tableColumnOper');
				tableColumnOper.dialog({  
				    title: '表结构新增',  
				    width: 820,  
				    height: 400,  
				    closed: true,  
				    cache: false,
				    href: url,
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-addTableColumn',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				tableColumnOper.dialog("open");
			});
			//显示表结构修改页面
			$(".tableColumnEditButton").live("click",function(){
				var divid = $(this).attr("divid");
				var tablecolumn = $("#"+divid).datagrid('getSelected');
		    	if(tablecolumn==null){
		    		$.messager.alert("提醒","请选择表结构！");
		    		return false;
		    	}
		    	var url = "sysDataDictionary/showTableColumnEditPage.do?id="+tablecolumn.id+"&divid="+divid;
		    	$('#sysDataDictionary-dialog-tableColumnOper').dialog({  
				    title: '表结构修改',  
				    width: 820,  
				    height: 400,  
				    closed: true,  
				    cache: false,
				    href: url,
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-editTableColumn',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});	
				$('#sysDataDictionary-dialog-tableColumnOper').dialog("open");
			});
			//字段删除
			$(".tableColumnDeleteButton").live("click",function(){
				var divid = $(this).attr("divid");
				var tablecolumn = $("#"+divid).datagrid('getSelected');
		    	if(tablecolumn==null){
		    		$.messager.alert("提醒","请选择表结构信息！");
		    		return false;
		    	}
				$.messager.confirm("删除确认","是否删除表结构信息?",function(r){
    				if(r){
				    	$.ajax({   
				            url :'sysDataDictionary/deleteTableColumn.do?id='+tablecolumn.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","删除成功！");
				            		$('#'+divid).datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","删除失败！");
				            	}
				            }
				        });
    				}
    			});				
			});
			$(".tableColumnReloadButton").live("click",function(){
				var divid = $(this).attr("divid");
				$('#'+divid).datagrid('reload');
			});
		});		
  	 </script>
  	 
		<div class="easyui-layout" fit="true">
    		<div data-options="region:'center',split:true">
			     <table id="sysDataDictionary-table-showTableInfoList"></table>
			     <div id="sysDataDictionary-query-showTableInfoList" style="padding:0px;height:auto;">
                     <form action="" id="sysDataDictionary-form-tableinfoList" method="post">
                         <div class="buttonBG" style="padding:0px;height:auto;">
                             <a href="javaScript:void(0);" id="sysDataDictionary-add-autoDBImportTableInfo" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'">自动导入</a>
                             <a href="javaScript:void(0);" id="sysDataDictionary-add-addTableInfo" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
                             <a href="javaScript:void(0);" id="sysDataDictionary-edit-editTableInfo" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
                             <a href="javaScript:void(0);" id="sysDataDictionary-copy-copyTableInfo" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-copy'">复制</a>
                             <a href="javaScript:void(0);" id="sysDataDictionary-remove-removeTableInfo" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
                         </div>
                     <table class="querytable">
                         <tr>
                             <td>表&nbsp;名:</td>
                             <td><input name="tablename" style="width:180px"></td>
                             <td>描述名:</td>
                             <td><input name="tabledescname" style="width:180px"></td>
                             <td>使用状态:</td>
                             <td>
                                 <select name="state" style="width: 130px;">
                                     <option></option>
                                     <option value="1" selected="selected">启用</option>
                                     <option value="0">停用</option>
                                 </select>
                             </td>
                         </tr>
                         <tr>
                             <td>表类型:</td>
                             <td>
                                 <select name="tabletype" style="width: 180px;">
                                     <option></option>
                                     <option value="2">业务</option>
                                     <option value="1">系统</option>
                                     <option value="3">虚拟</option>
                                 </select>
                             </td>
                             <td>是否支持数据权限:</td>
                             <td>
                                 <select name="isdatarule" style="width: 180px;">
                                     <option></option>
                                     <option value="1">是</option>
                                     <option value="0">否</option>
                                 </select>
                             </td>
                             <td colspan="2">
                                 <a href="javaScript:void(0);" id="sysDataDictionary-queay-queryTableInfoList" class="button-qr">查询</a>
                                 <a href="javaScript:void(0);" id="sysDataDictionary-queay-reloadTableInfoList" class="button-qr">重置</a>
                             </td>
                         </tr>
                     </table>
                    </form>

				</div>
			</div>
		</div>
		<div id="sysDataDictionary-dialog-tableInfoOper" style="position: relative;"></div>
		<div id="sysDataDictionary-window-tableInfoOper" style="position: relative;"></div>
		<div id="sysDataDictionary-dialog-tableColumnOper" style="position: relative;"></div>
		<div id="sysDataDictionary-window-tableColumnOper" style="position: relative;"></div>
  </body>
</html>

