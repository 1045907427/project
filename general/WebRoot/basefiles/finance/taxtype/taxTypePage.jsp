<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>税种档案页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false" id="finance-layout-taxtype">
    	<div data-options="region:'north',border:false" style="height: 30px;overflow: hidden">
    		<div class="buttonBG" id="finance-button-taxType"></div>
    		<input type="hidden" id="taxtype-opera"/>
    	</div>
    	<div data-options="region:'west',split:true" style="width: 310px;">
			<div style="height: 1px;"></div>
    		<table id="finance-table-taxtypeList"></table>
    	</div>
    	<div data-options="region:'center',border:true">
    	</div>
    </div>
    <script type="text/javascript">
    	var taxType_AjaxConn = function (Data, Action) {
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        beforeSend:function(){
	            	loading("提交中..");
	            },
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
     var taxTypeListColJson=$("#finance-table-taxtypeList").createGridColumnLoad({
     	name:'t_base_finance_taxtype',
     	frozenCol:[[]],
     	commonCol:[[
			{field:'id',title:'编码',width:80,sortable:true,align:'center'},
			{field:'name',title:'名称',width:80,sortable:true,align:'center'},
			{field:'type',title:'类型',width:60,sortable:true,align:'center',
				formatter:function(val,rowData,rowIndex){
					return getSysCodeName("taxtype",val);
				}
			},
			{field:'rate',title:'税率',width:60,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val)+"%";
					}
				}
			},
            <c:if test="${useHTKPExport=='1'}">
            {field:'jsrateid',title:'金税编码',width:80,sortable:true,align:'center'},
            {field:'jsflag',title:'金税标识',width:80,sortable:true,align:'center',
                formatter:function(val,rowData,rowIndex){
                    if(val=='1'){
                        return "免税";
                    }else{
                        return "默认";
                    }
                }
            },
            </c:if>
			{field:'state',title:'状态',width:60,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					return getSysCodeName("state",val);
				}
			},
			{field:'remark',title:'备注',width:100,sortable:true,align:'center'},
			{field:'adduserid',title:'建档人',width:80,sortable:true,hidden:true},
			{field:'adddeptid',title:'建档部门',width:80,sortable:true,hidden:true},
			{field:'addtime',title:'建档时间',width:80,sortable:true,hidden:true},
			{field:'modifyuserid',title:'最后修改人',width:80,sortable:true,hidden:true},
			{field:'modifytime',title:'最后修改时间',width:80,sortable:true,hidden:true},
			{field:'openuserid',title:'启用人',width:80,sortable:true,hidden:true},
			{field:'opentime',title:'启用时间',width:80,sortable:true,hidden:true},
			{field:'closeuserid',title:'禁用人',width:80,sortable:true,hidden:true},
			{field:'closetime',title:'禁用时间',width:80,sortable:true,hidden:true}
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
    function refreshLayout(title, url){
   		$("#finance-layout-taxtype").layout('remove','center').layout('add',{
			region: 'center',  
		    title: title,
		    href:url
		});
   	}
   	//验证长度且验证重复
   	function validLengthAndUsed(initID,initName,initRate){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
   		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						if(value == initID){
  							return true;
  						}
						var ret = taxType_AjaxConn({id:value},'basefiles/finance/isUsedId.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						if(value == initName){
  							return true;
  						}
						var ret = taxType_AjaxConn({name:value},'basefiles/finance/isUsedName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		},
	  		validRate:{
	  			validator : function(value,param) {
	  				if(value >= 0){
	  					if(value == initRate){
  							return true;
  						}
	  					var ret = taxType_AjaxConn({name:value},'basefiles/finance/isUsedRate.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validRate.message = '该税率已存在, 请重新输入!';
							return false;
						}
	  				}
	  				else{
	  					$.fn.validatebox.defaults.rules.validRate.message = '请输入大于等于零的值!';
						return false;
	  				} 
					return true;
		        }, 
		        message : '' 
	  		}
		});
   	}
    $(function(){
    	$("#finance-button-taxType").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/finance/taxTypeAddBtn.do">
						{
							type:'button-add',//新增 
							handler:function(){
				     			refreshLayout("税种【新增】", 'basefiles/finance/showTaxTypeAddPage.do');
								$("#taxtype-opera").attr("value","add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeEditBtn.do">
			 			{
				 			type:'button-edit',//修改 
				 			handler:function(){
								var taxtype=$("#finance-table-taxtypeList").datagrid('getSelected');
					  			if(taxtype==null){
					  				$.messager.alert("提醒","请选择相应的税种!");
					  				return false;
					  			}
					  			var flag = isDoLockData(taxtype.id,"t_base_finance_taxtype");
								if(!flag){
									$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
									return false;
								}
				     			refreshLayout("税种【修改】", 'basefiles/finance/showTaxTypeEditPage.do?id='+taxtype.id);
								$("#taxtype-opera").attr("value","edit");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeHoldBtn.do">
			 			{
			 				type:'button-hold',//暂存
			 				handler:function(){
				 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
				 				var type = $("#taxtype-opera").val();
				 				$.messager.confirm("提醒","是否暂存税种?",function(r){
			 						if(r){
			 							if(type == "add"){
			 								addTaxType("hold");//新增暂存税种
			 							}
			 							else{
			 								editTaxType("hold");//修改暂存税种
			 							}
			 						}
			 					});
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeSaveBtn.do">
			 			{
				 			type:'button-save',//保存
				 			handler:function(){
				 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
				 				var type = $("#taxtype-opera").val();
				 				$.messager.confirm("提醒","是否保存税种?",function(r){
			 						if(r){
			 							if(type == "add"){
			 								addTaxType("save");//新增保存税种
			 							}
			 							else{
			 								editTaxType("save");//修改保存税种
			 							}
			 						}
			 					});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeDelBtn.do">
			 			{
				 			type:'button-delete',//删除
				 			handler:function(){
				 				var taxType=$("#finance-table-taxtypeList").datagrid('getSelected');
					  			if(taxType==null){
					  				$.messager.alert("提醒","请选择相应的税种!");
					  				return false;
					  			}
					  			var flag = isLockData(taxType.id,"t_base_finance_taxtype");
								if(flag){
									$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
									return false;
								}
								if("1" == taxType.state){
				 					$.messager.alert("提示","启用状态不能删除!");
				 					return false;
				 				}
					  			$.messager.confirm("提醒","是否确认删除税种?",function(r){
					  				if(r){
					  					var ret = taxType_AjaxConn({id:taxType.id},'basefiles/finance/deleteTaxType.do');
										var retJson = $.parseJSON(ret);
										if(retJson.delFlag){
						  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
						  					return false;
						  				}
										if(retJson.flag){
											$("#finance-layout-taxtype").layout('remove','center');
											$("#finance-table-taxtypeList").datagrid('reload');
											$("#finance-table-taxtypeList").datagrid('clearSelections');
											$.messager.alert("提醒","删除成功!");
										}
										else{
											$.messager.alert("提醒","删除失败!");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeEnableBtn.do">
			 			{
				 			type:'button-open',//启用 
				 			handler:function(){
				 				var taxType=$("#finance-table-taxtypeList").datagrid('getSelected');
					  			if(taxType==null){
					  				$.messager.alert("提醒","请选择相应的税种!");
					  				return false;
					  			}
					  			$.messager.confirm("提醒","是否确认启用税种?",function(r){
					  				if(r){
						  				var ret = taxType_AjaxConn({id:taxType.id},'basefiles/finance/enableTaxType.do');
										var retJson = $.parseJSON(ret);
										if(retJson.flag){
											$("#finance-table-taxtypeList").datagrid('reload');
											refreshLayout("税种【详情】", 'basefiles/finance/showTaxTypeViewPage.do?id='+taxType.id);
											$.messager.alert("提醒","启用成功!");
										}
										else{
											$.messager.alert("提醒","启用失败!");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeDisableBtn.do">
			 			{
				 			type:'button-close',//禁用 
				 			handler:function(){
				 				var taxType=$("#finance-table-taxtypeList").datagrid('getSelected');
					  			if(taxType==null){
					  				$.messager.alert("提醒","请选择相应的税种!");
					  				return false;
					  			}
					  			$.messager.confirm("提醒","是否确认禁用税种?",function(r){
					  				if(r){
					  					var ret = taxType_AjaxConn({id:taxType.id},'basefiles/finance/disableTaxType.do');
										var retJson = $.parseJSON(ret);
										if(retJson.flag){
											$("#finance-table-taxtypeList").datagrid('reload');
											refreshLayout("税种【详情】", 'basefiles/finance/showTaxTypeViewPage.do?id='+taxType.id);
											$.messager.alert("提醒","禁用成功!");
										}
										else{
											$.messager.alert("提醒",""+retJson.Mes+"禁用失败!");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeImportBtn.do">
			 			{
				 			type:'button-import',//导入
				 			attr:{
				 				clazz: "financeService", //spring中注入的类名
						 		method: "addDRTaxType", //插入数据库的方法
						 		tn: "t_base_finance_taxtype", //表名
					            module: 'basefiles', //模块名，
					            majorkey:'id',
						 		pojo: "TaxType", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.TaxType。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#finance-table-taxtypeList").datagrid('reload');	//更新列表	                                                                          
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeExportBtn.do">
			 			{
				 			type:'button-export',//导出 
				 			attr:{
								queryForm: "", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		tn: 't_base_finance_taxtype', //表名
						 		name:'税种档案列表'
							}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypePrintViewBtn.do">
			 			{
				 			type:'button-preview',//打印预览
				 			handler:function(){
				 				alert("print");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypePrintBtn.do">
			 			{
				 			type:'button-print',//打印 
				 			handler:function(){
				 				alert("print");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/taxTypeGiveupBtn.do">
			 			{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#taxtype-opera").val();
				 				if(type=="add"){
				 					$("#finance-button-taxType").buttonWidget("initButtonType","list");
				 					$("#finance-layout-taxtype").layout('remove','center');
				 				}else if(type=="edit"){
				 					var taxType=$("#finance-table-taxtypeList").datagrid('getSelected');
				 					var id = taxType.id;
				 					var state = taxType.state;
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_finance_taxtype'},
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
					 				refreshLayout("税种【详情】", 'basefiles/finance/showTaxTypeViewPage.do?id='+taxType.id);
									$("#taxtype-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_base_finance_taxtype',
				id:''
     		});
     		
     		$("#finance-table-taxtypeList").datagrid({ 
     			authority:taxTypeListColJson,
	  	 		frozenColumns:[[]],
				columns:taxTypeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'税种档案列表',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
			    url:'basefiles/finance/getTaxTypeList.do',
			    onLoadSuccess:function(){
			    	var p = $('#finance-table-taxtypeList').datagrid('getPager');  
				    $(p).pagination({  
				        //pageSize: 20,//每页显示的记录条数，默认为10  
				        beforePageText: '',//页数文本框前显示的汉字  
				        afterPageText: '',  
				        //showPageList:false,
				        displayMsg: ''
				    });
		    	},
		    	onClickRow:function(rowIndex, rowData){
	     			refreshLayout("税种【详情】", 'basefiles/finance/showTaxTypeViewPage.do?id='+rowData.id);
					$("#meteringUnit-opera").attr("value","view");
					
		    	}
			}).datagrid("columnMoving");
    });
    </script>
  </body>
</html>
