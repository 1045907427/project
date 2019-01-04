<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品品牌首页</title>
  	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="brand-opera"/>
  	<input type="hidden" id="brand-index"/>
   	<div class="easyui-layout" data-options="fit:true" id="goods-layout-brand">
     	<div data-options="region:'north'" style="height: 30px;overflow: hidden">
     		<div class="buttonBG" id="brand-button-layout"></div>
     	</div>
     	<div data-options="region:'west',split:true" style="width:480px;height: auto">
     		<div id="brand-query-showBrandList">
	    		<form action="" id="brand-form-brandListQuery" method="post">
	    			<table cellpadding="1" cellspacing="0" border="0">
	    				<tr>
	    					<td>品牌名称:</td>
	    					<td><input id="brand-widget-id" type="text" name="id" style="width:100px" /></td>
	    					<td>
	    						<a href="javaScript:void(0);" id="brand-query-queryBrandList" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="brand-query-reloadBrandlList" class="button-qr">重置</a>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="brand-table-list"></table>
	    </div>
	    <div data-options="region:'center',split:true,border:true">
	    	<div id="brand-div-brandInfo"></div>
		</div>
     </div>
	<div style="display: none;">
		<div id="brand-dialog-showUpdateBrandInfoForJS"></div>
	</div>
    <script type="text/javascript">
    	var brand_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
		function refreshLayout(title, url,type){
    		$("#goods-layout-brand").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
			$("#brand-opera").attr("value",type);
    	}
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var brandListColJson=$("#brand-table-list").createGridColumnLoad({
	     	name:'base_goods_brand',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true},
				{field:'name',title:'名称',width:80,sortable:true},  
				{field:'state',title:'状态',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.stateName;
					}
				},
				{field:'deptid',title:'所属部门',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptName;
					}
				},
				{field:'supplierid',title:'所属供应商',width:140,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierName;
					}
				},
                {field:'defaulttaxtype',title:'默认税种',width:80,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.defaulttaxtypename;
                    }
                },
				{field:'margin',title:'毛利率',width:60,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(null != val && "" != val){
							return rowData.margin+"%";
						}
					}
				},
				<c:if test="${useHTKPExport=='1'}">
				{field:'jsclusterid',title:'金税簇编码',width:80,sortable:true},
				</c:if>
				{field:'remark',title:'备注',width:130,sortable:true}
			]]
	     });
	     
	    //判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
	    
	    //加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }

        //加载控件
        function loadWidgetMethod(){
            // 毛利率
            $('#wares-numberbox-margin').numberbox({
                min:0,
                max:999,
                precision:2
            });

            //状态
            $('#common-combobox-state').combobox({
                url:'common/sysCodeList.do?type=state',
                valueField:'id',
                textField:'name'
            });

            //所属部门
            $("#wares-widget-Dept").widget({
                width:200,
                referwid:'RL_T_BASE_DEPARTMENT_BUYER',
                singleSelect:true,
                onlyLeafCheck:true
            });

            //所属供应商(修改)
            $("#wares-widget-Supplier").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:200,
                onSelect:function(data){
                    if($("#brand-oldsupplierid").val() != data.id){
                        $.messager.confirm("提醒","是否同时修改商品档案中该品牌的全部所属供应商?",function(r){
                            if(r){
                                $("#wares-hidden-editSupplier").val("1");
                            }else{
                                $("#wares-hidden-editSupplier").val("0");
                            }
                        });
                    }
                }
            });
            //所属供应商(新增)
            $("#wares-widget-addSupplier").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:200
            });

            //默认税种
            $("#wares-widget-defaulttaxtype").widget({
                width:200,
                referwid:'RL_T_BASE_FINANCE_TAXTYPE',
                singleSelect:true,
                required:true
            });
        }

     	$(function(){
     		//品牌名称
     		$("#brand-widget-id").widget({
     			width:120,
				name:'t_base_goods_brand',
				col:'id',
				singleSelect:true,
				onlyLeafCheck:false
     		});
     	
     		$("#brand-button-layout").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/brandAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('商品品牌【新增】','basefiles/showBrandAddPage.do','add');
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/brandEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var brand=$("#brand-table-list").datagrid('getSelected');
				  			if(brand==null){
				  				$.messager.alert("提醒","请选择相应的商品品牌!");
				  				return false;
				  			}
				  			var flag = isDoLockData(brand.id,"t_base_goods_brand");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
							refreshLayout('商品品牌【修改】','basefiles/showBrandEditPage.do?id='+brand.id,'edit');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandHoldBtn.do">
		 			{
		 				type:'button-hold',//暂存
		 				handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#brand-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
					    		$.messager.confirm("提醒","是否暂存新增商品品牌?",function(r){
			 						if(r){
					 					addBrand("hold");//暂存新增商品品牌
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否暂存修改商品品牌?",function(r){
			 						if(r){
										editBrand("hold");//暂存修改商品品牌
									}
			 					});
			 				}
			 			}
			 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/brandSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#brand-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
			 					$.messager.confirm("提醒","是否保存新增商品品牌?",function(r){
			 						if(r){
			 							//addBrand("save");//保存新增商品品牌
			 							$("#wares-hidden-addType").val("save");
										if(!$("#wares-form-brandAdd").form('validate')){
											return false;
										}
			 							loading("提交中..");
							  			$.ajax({
								  			url:'basefiles/addBrand.do',
								  			data:$("#wares-form-brandAdd").serializeJSON(),
								  			dataType:'json',
								  			type:'post',
								  			success:function(retJson){
								  				loaded();
												if(retJson.flag){
													refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+$("#wares-input-brandId").val(),'view');
													$("#brand-table-list").datagrid('reload');
													$.messager.alert("提醒","保存成功!");
												}
												else{
													$.messager.alert("提醒","保存失败!");
												}
								  			}
							  			});
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否保存修改商品品牌?",function(r){
			 						if(r){
			 							//editBrand("save");//保存修改商品品牌
			 							$("#wares-hidden-editType").val("save");
										if(!$("#wares-form-brandEdit").form('validate')){
											return false;
										}
			 							loading("提交中..");
							  			$.ajax({
								  			url:'basefiles/editBrand.do',
								  			data:$("#wares-form-brandEdit").serializeJSON(),
								  			dataType:'json',
								  			type:'post',
								  			success:function(retJson){
								  				loaded();
												if(retJson.flag){
													refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+$("#wares-input-brandId").val(),'view');
													$("#brand-table-list").datagrid('reload');
													$.messager.alert("提醒","保存成功!");
												}
												else{
													$.messager.alert("提醒","保存失败!");
												}
								  			}
							  			});
			 						}
			 					});
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var num = 0;
			 				var strid = "";
			 				var brands=$("#brand-table-list").datagrid('getChecked');
				  			if(brands.length==0){
				  				$.messager.alert("提醒","请勾选相应的商品品牌!");
				  				return false;
				  			}
				  			for(var i=0;i<brands.length;i++){
		  						if("1" == brands[i].state){
		  							num++;
		  							var index = $("#brand-table-list").datagrid('getRowIndex',brands[i]);
		  							$("#brand-table-list").datagrid('uncheckRow',index);
		  						}
		  						else{
		  							strid += brands[i].id + ",";
		  						}
		  					}
		  					if(num != 0){
		  						$.messager.alert("提醒",num+"条记录为启用状态,不允许删除!");
		  					}
		  					if(num < brands.length){
					  			$.messager.confirm("提醒","是否确认删除商品品牌?",function(r){
					  				if(r){
					  					var ret = brand_AjaxConn({strid:strid},'basefiles/deleteBrand.do');
										var retJson = $.parseJSON(ret);
										$.messager.alert("提醒",""+retJson.lockNum+"条记录正在被他人操作,暂不能删除;<br/>"+retJson.userNum+"条记录被引用,不允许删除;<br/>"+retJson.failNum+"条记录删除失败;<br/>"+retJson.sucNum+"条记录删除成功;");
										$("#goods-layout-brand").layout('remove','center');
										$("#brand-table-list").datagrid('reload');
										$("#brand-table-list").datagrid('clearChecked');
					  				}
					  			});
				  			}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var brand=$("#brand-table-list").datagrid('getSelected');
				  			if(brand==null){
				  				$.messager.alert("提醒","请选择相应的商品品牌!");
				  				return false;
				  			}
				  			refreshLayout('商品品牌【新增】','basefiles/showBrandCopyPage.do?id='+brand.id,'add');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandEnableBtn.do">
		 			{
			 			type:'button-open',//启用 
			 			handler:function(){
			 				var brands=$("#brand-table-list").datagrid('getChecked');
			 				var brand=$("#brand-table-list").datagrid('getSelected');
				  			if(brands.length==0){
				  				$.messager.alert("提醒","请勾选相应的商品品牌!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认启用商品品牌?",function(r){
				  				if(r){
				  					var strid = "";
				  					for(var i=0;i<brands.length;i++){
				  						strid += brands[i].id + ",";
				  					}
					  				var ret = brand_AjaxConn({strid:strid},'basefiles/enableBrand.do');
									var retJson = $.parseJSON(ret);
									$.messager.alert("提醒",""+retJson.ivdNum+"条记录启用无效;<br/>"+retJson.sucNum+"条记录启用成功;<br/>"+retJson.failNum+"条记录启用失败;");
									$("#brand-button-layout").buttonWidget('initButtonType','multipleList');
									$("#brand-table-list").datagrid('reload');
									$("#brand-table-list").datagrid('clearChecked');
									$("#brand-table-list").datagrid('clearSelections');
									if(brand != null){
										refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+brand.id,'view');
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandDisableBtn.do">
		 			{
			 			type:'button-close',//禁用 
			 			handler:function(){
			 				var brands=$("#brand-table-list").datagrid('getChecked');
			 				var brand=$("#brand-table-list").datagrid('getSelected');
				  			if(brands.length==0){
				  				$.messager.alert("提醒","请勾选相应的商品品牌!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认禁用商品品牌?",function(r){
				  				if(r){
				  					var strid = "";
				  					for(var i=0;i<brands.length;i++){
				  						strid += brands[i].id + ",";
				  					}
				  					var ret = brand_AjaxConn({strid:strid},'basefiles/disableBrand.do');
									var retJson = $.parseJSON(ret);
									$.messager.alert("提醒",""+retJson.ivdNum+"条记录禁用无效;<br/>"+retJson.sucNum+"条记录禁用成功;<br/>"+retJson.failNum+"条记录禁用失败;");
									$("#brand-button-layout").buttonWidget('initButtonType','multipleList');
									$("#brand-table-list").datagrid('reload');
									$("#brand-table-list").datagrid('clearChecked');
									$("#brand-table-list").datagrid('clearSelections');
									if(brand != null){
										refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+brand.id,'view');
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "goodsService", //spring中注入的类名
					 		method: "addDRBrand", //插入数据库的方法
					 		tn: "t_base_goods_brand", //表名
				            module: 'basefiles', //模块名，
					 		majorkey:'id',
					 		pojo: "Brand", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.Brand。
							onClose: function(){ //导入成功后窗口关闭时操作，
						         //$("#personnel-table-personnelList").datagrid('clearSelections');	  
						         $("#brand-table-list").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#brand-form-brandListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_base_goods_brand', //表名
					 		name:'商品品牌列表'
						}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandPrintViewBtn.do">
		 			{
			 			type:'button-preview',//打印预览
			 			handler:function(){
			 				alert("print");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandPrintBtn.do">
		 			{
			 			type:'button-print',//打印 
			 			handler:function(){
			 				alert("print");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandGiveupBtn.do">
					{
		 				type:'button-giveup',//放弃 
		 				handler:function(){
			 				var type = $("#brand-opera").val();
			 				if(type=="add"){
			 					$("#goods-layout-brand").layout('remove','center');
			 					$("#brand-button-layout").buttonWidget("initButtonType","list");
			 				}else if(type=="edit"){
			 					var brand=$("#brand-table-list").datagrid('getSelected');
			 					var id = brand.id;
			 					var state = brand.state;
			 					$.ajax({   
						            url :'system/lock/unLockData.do',
						            type:'post',
						            data:{id:id,tname:'t_base_goods_brand'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+brand.id,'view');
			 				}
			 			}
		 			},
		 			</security:authorize>
					{}
	 			],
                buttons:[
                    <security:authorize url="/basefiles/showBrandInfoForJSEditPageBtn.do">
                    {
                        id:'button-id-jsconfig',
                        name:'更新金税信息',
                        iconCls:'button-edit',
                        handler:function(){
                            var brandInfoRow=$("#brand-table-list").datagrid('getSelected');
                            if(brandInfoRow == null || brandInfoRow.id==null || brandInfoRow.id==""){
                                $.messager.alert("提醒","请选择品牌!");
                                return false;
                            }
                            $('<div id="brand-dialog-showUpdateBrandInfoForJS-content"></div>').appendTo("#brand-dialog-showUpdateBrandInfoForJS");
                            $('#brand-dialog-showUpdateBrandInfoForJS-content').dialog({
                                title: '更新金税信息',
                                //fit:true,
                                width:450,
                                height:250,
                                closed: true,
                                cache: false,
                                method:'post',
                                queryParams:{id:brandInfoRow.id},
                                href: 'basefiles/showBrandInfoForJSEditPage.do',
                                maximizable:true,
                                resizable:true,
                                modal: true,
                                onLoad:function(){
                                },
                                onClose:function(){
                                    $('#brand-dialog-showUpdateBrandInfoForJS-content').dialog("destroy");
                                }
                            });
                            $('#brand-dialog-showUpdateBrandInfoForJS-content').dialog('open');
                        }
                    },
                    </security:authorize>
                    {},
				],
	 			model:'base',
				type:'multipleList',
				taburl:'/basefiles/showBrandPage.do',
				datagrid:'brand-table-list',
				tname:'t_base_goods_brand',
				id:''
     		});
     		
     		$("#brand-table-list").datagrid({ 
     			authority:brandListColJson,
	  	 		frozenColumns:[[{field:'ck',checkbox:true}]],
				columns:brandListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'商品品牌列表',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
	  	 		toolbar:'#brand-query-showBrandList',
			    url:'basefiles/getBrandListPage.do',
		    	onSelect:function(rowIndex, rowData){
		    		refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+rowData.id,'view');
		    	},
		    	onUnselect:function(rowIndex, rowData){
		    		$("#goods-layout-brand").layout('remove','center');
                    var rows = $(this).datagrid("getChecked");
                    if(rows.length == 0){
                        $("#brand-button-layout").buttonWidget("initButtonType","multipleList");
                        $("#brand-button-layout").buttonWidget("disableButton","button-edit");
                        $("#brand-button-layout").buttonWidget("disableButton","button-hold");
                        $("#brand-button-layout").buttonWidget("disableButton","button-save");
                        $("#brand-button-layout").buttonWidget("disableButton","button-giveup");
                        $("#brand-button-layout").buttonWidget("disableButton","button-copy");
                    }
		    	},
		    	onLoadSuccess: function () {
	                $("#brand-table-list").datagrid('selectRecord',$("#wares-input-brandId").val());
	            }
			}).datagrid("columnMoving");
			
			//回车事件
			controlQueryAndResetByKey("brand-query-queryBrandList","brand-query-reloadBrandlList");
			
			//查询
			$("#brand-query-queryBrandList").click(function(){
	      		var queryJSON = $("#brand-form-brandListQuery").serializeJSON();
	      		$("#brand-table-list").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#brand-query-reloadBrandlList").click(function(){
				$("#brand-form-brandListQuery")[0].reset();
				$("#brand-widget-id").widget('clear');
				$("#brand-table-list").datagrid("load",{});
				
			});
     	});
    </script>
  </body>
</html>
