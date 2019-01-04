<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案列表</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="customer-opera"/>
    <div class="easyui-layout" data-options="fit:true,border:true">
    	<div data-options="region:'north',split:false,border:false" style="height:110px;overflow: hidden;">
    		<div class="buttonQueryBG" id="sales-buttons-customerListPage"></div>
    		<div id="sales-queryDiv-customerListPage" style="padding-bottom: 15px;">
				<form id="sales-queryForm-customerListPage">
					<input type="hidden" name="type" id="sales-type-customerListPage" />
					<input type="hidden" name="sortarea" id="sales-sortarea-customerListPage" />
					<table class="querytable">
						<tr>
							<td>编号：</td>
							<td><input type="text" name="id" class="len180"/></td>
							<td>业务员：</td>
							<td><input type="text" name="salesman" id="customerlist-widget-salesman" class="len180"/></td>
                            <security:authorize url="/basefiles/viewCustomerLocation.do">
                                <td>是否有坐标：</td>
                                <td>
                                    <select name="location" style="width: 100px;">
                                        <option value="0"></option>
                                        <option value="1">有</option>
                                        <%--<option value="2">模糊</option>--%>
                                        <option value="3">无</option>
                                    </select>
                                </td>
                                <td class="len100"></td>
                            </security:authorize>
						</tr>
						<tr>
							<td>名称：</td>
							<td><input type="text" name="name" class="len180"/></td>
							<td>助记符：</td>
							<td><input type="text" name="shortcode" class="len180"/></td>
							<td colspan="2" class="tdbutton">
								<a href="javascript:;" id="sales-queryBtn-customerListPage" class="button-qr">查询</a>
				  				<a href="javaScript:;" id="sales-resetQueryBtn-customerListPage" class="button-qr">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
    	</div>
    	<div data-options="region:'west',split:false,border:false" style="width:120px;background: #dfecf5;">
    		<ul class="sorttabs">
    			<li class="selectTag"><a href="javascript:;">区域</a></li>
    			<li><a href="javascript:;">分类</a></li>
    		</ul>
    		<div class="sorttagsDiv">
    			<div class="tagsDiv_item" style="display:block;">
    				<div class="ztree" id="sales-areaTree-customerListPage"></div>
    			</div>
    			<div class="tagsDiv_item" >
    				<div class="ztree" id="sales-sortTree-customerListPage"></div>
    			</div>
	    	</div>
    	</div>
    	<div data-options="region:'center',border:false" >
    		<table id="sales-datagrid-customerListPage"></table>
    	</div>
        <div id="sales-printByModule-customerListPage">
        </div>
    </div>
    <input type="hidden" id="sales-customerids-allotPSNCustomer"/>
    <div id="sales-dialog-customerEditMore"></div>
    <div id="sales-dialog-allotPSNCustomer"></div>
	<div id="sales-dialog-clearPSNCustomer"></div>
    <div id="sales-dialog-customerListPage"></div>
	<div style="display: none;">
		<div id="sales-dialog-searchMap" style="padding: 15px;">
			<input type="text" id="sales-keyword-searchMap" placeholder="查地点" style="width: 300px;"/>
		</div>
	</div>
    <script type="text/javascript">
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
	    
	    var customer_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
	    
	    var customer_AjaxConn = function (Data, Action) {
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
	    
	    //验证长度且验证重复
    	function validLengthAndUsed(len, url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		if(value == initValue){
  							return true;
  						}
			    		var reg=eval("/^[A-Za-z0-9]{0,20}$/");//正则表达式使用变量 
	  					if(reg.test(value) == true){
				    		var data=customer_ajaxContent({id:(id+value)},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLength.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLength.message ='不允许大于20位输入除数字字母以外的字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}

        function repeatshopno(initValue){
            $.extend($.fn.validatebox.defaults.rules, {
                repeatshopno:{
                    validator:function(value){
                        if(value == initValue){
                            return true;
                        }
                        var reg=eval("/^[A-Za-z0-9]{0,20}$/");//正则表达式使用变量
                        if(reg.test(value) == true){
                            var pcustomerid = $("#sales-parent-customerAddPage").widget('getValue');
                            var data=customer_ajaxContent({pcustomerid:pcustomerid,shopno:value},'basefiles/shopnoIsRepeat.do');
                            var json = $.parseJSON(data);
                            if(json.flag == true){
                                $.fn.validatebox.defaults.rules.repeatshopno.message = '店号重复!';
                                return false;
                            }else{
                                return true;
                            }
                        }else{
                            $.fn.validatebox.defaults.rules.repeatshopno.message ='请输入不大于20位字符!';
                            return false;
                        }
                    },
                    message:''
                }
            });
        }
    	function selectControl(){
			var v = $("#customerShortcut-select-overcontrol option:selected").val();
			if(v == "0"){
				$("#customerShortcut-input-overgracedate").val("");
				changeValue("");
				$("#customerShortcut-input-overgracedate").attr("disabled","disabled");
			}
			else{
				$("#customerShortcut-input-overgracedate").removeAttr("disabled");
			}
		}
		
		function changeValue(val){
			$("#customer-hd-overgracedate").val(val);
		}
	    
	    function refreshLayout(title, url,type){
			$('<div id="sales-dialog-customerListPage1"></div>').appendTo('#sales-dialog-customerListPage');
			$('#sales-dialog-customerListPage1').dialog({
				maximizable:true,
				resizable:true,
			    title: title,  
			    width: 740,
			    height: 450,
			    closed: true,
			    cache: false,  
			    href: url,  
			    modal: true,
			    onClose:function(){
			    	$('#sales-dialog-customerListPage1').dialog("destroy");
			    }
			});
            $("#sales-dialog-customerListPage1").dialog("open");
			$("#customer-opera").val(type);
    	}
	    
	    function customer_save_form_submit(){
    		$("#customersimplify-form-add").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
      					$("#sales-datagrid-customerListPage").datagrid("load",queryJSON);
						$("#sales-dialog-customerListPage1").dialog('close');
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
	    
	    function customer_savegoon_form_submit(){
    		$("#customersimplify-form-add").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
      					$("#sales-datagrid-customerListPage").datagrid("load",queryJSON);
      					
      					var area = "",sort = "";
						var areaTree = $.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
						if(areaTree.getSelectedNodes().length > 0){
							area = areaTree.getSelectedNodes()[0].id;
						}
						var sortTree = $.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
						if(sortTree.getSelectedNodes().length > 0){
							sort = sortTree.getSelectedNodes()[0].id;
						}
						var url = 'basefiles/showCustomerSimplifyAddPage.do?area='+area+'&sort='+sort;
						$('#sales-dialog-customerListPage1').dialog('refresh', url);
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
	    
    	$(function(){
    		//业务员
    		$("#customerlist-widget-salesman").widget({
    			width:180,
				referwid:'RL_T_BASE_PERSONNEL_BCS_SELLER',
				singleSelect:true
    		});

    		var customerListColJson = $("#sales-datagrid-customerListPage").createGridColumnLoad({
				name:'t_base_sales_customer',
				frozenCol:[[{field:'ck',checkbox:true}]],
				commonCol:[[{field:'id',title:'编码',sortable:true,width:60},
					{field:'name',title:'客户名称',sortable:true,width:180},
					{field:'pid',title:'上级客户',sortable:true,width:80,
						formatter:function(val,rowData,rowIndex){
							return rowData.pname;
						}
					},
                    {field:'shortcode',title:'助记符',sortable:true,width:60},
                    {field:'shopno',title:'店号',sortable:true,width:100,hidden:true},
					{field:'contact',title:'联系人',sortable:true,width:60},
					{field:'mobile',title:'联系人电话',sortable:true,width:80,isShow:true},
					{field:'payeeid',title:'收款人',sortable:true,width:60,
						formatter:function(val,rowData,rowIndex){
							return rowData.payeename;
						}
					},
					{field:'address',title:'详细地址',sortable:true,width:200},
					{field:'settletype',title:'结算方式',sortable:true,width:80,
						formatter:function(val,rowData,rowIndex){
							return rowData.settletypename;
						}
					},
					{field:'settleday',title:'结算日',sortable:true,width:50},
                    {field:'credit',title:'信用额度',sortable:true,width:70},
					{field:'pricesort',title:'价格套',sortable:true,width:50,
						formatter:function(val,rowData,rowIndex){
							return rowData.pricesortname;
						}
					},
					{field:'promotionsort',title:'促销分类',sortable:true,width:60,
						formatter:function(val,rowData,rowIndex){
							return rowData.promotionsortname;
						}
					},
					{field:'salesdeptid',title:'销售部门',sortable:true,width:60,hidden:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.salesdeptname;
						}
					},
					{field:'salesarea',title:'所属区域',sortable:true,width:60,
						formatter:function(val,rowData,rowIndex){
							return rowData.salesareaname;
						}
					},
					{field:'customersort',title:'所属分类',sortable:true,width:80,
						formatter:function(val,rowData,rowIndex){
							return rowData.customersortname;
						}
					},
					{field:'salesuserid',title:'客户业务员',sortable:true,width:80,
						formatter:function(val,rowData,rowIndex){
							return rowData.salesusername;
						}
					},
					{field:'state',title:'状态',sortable:true,width:40,
						formatter:function(value,row,index){
							return row.statename;
						}
					}
				]]
    		});
    		$("#sales-datagrid-customerListPage").datagrid({
    			authority:customerListColJson,
	  	 		frozenColumns:customerListColJson.frozen,
				columns:customerListColJson.common,
    			fit:true,
				title:'',
				border:false,
				rownumbers:true,
				pagination:true,
				pageSize:100,
				idField:'id',
				singleSelect:false,
				checkOnSelect:true,
				selectOnCheck:true,
				url:'basefiles/getCustomerList.do',
				onDblClickRow:function(rowIndex, rowData){
					<security:authorize url="/basefiles/customerSimplifyView.do">
						var url = 'basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(rowData.id);
						refreshLayout("客户档案【查看】", url,"view");
					</security:authorize>
				}
    		}).datagrid("columnMoving");
    		var salseAreaTreeSetting = { //区域树
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getSalesAreaTree.do",
					autoParam: ["id","pId", "name","state"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							$("#sales-type-customerListPage").val("0");
						}
						else{
							$("#sales-type-customerListPage").val("1");
						}
						$("#sales-sortarea-customerListPage").val(treeNode.id);
						$.fn.zTree.getZTreeObj("sales-sortTree-customerListPage").refresh();
						$("#sales-queryBtn-customerListPage").click();
						var zTree = $.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#sales-areaTree-customerListPage"), salseAreaTreeSetting,null);
    		var customerSortTreeSetting = { //分类树
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getCustomerSortTree.do",
					autoParam: ["id","pid", "name","state"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							$("#sales-type-customerListPage").val("0");
						}
						else{
							$("#sales-type-customerListPage").val("2");
						}
						$("#sales-sortarea-customerListPage").val(treeNode.id);
						$.fn.zTree.getZTreeObj("sales-areaTree-customerListPage").refresh()
						$("#sales-queryBtn-customerListPage").click();
						var zTree = $.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#sales-sortTree-customerListPage"), customerSortTreeSetting,null);
			
			//回车事件
			controlQueryAndResetByKey("sales-queryBtn-customerListPage","sales-resetQueryBtn-customerListPage");
			
			$("#sales-queryBtn-customerListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
	       		$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueryBtn-customerListPage").click(function(){
				$("#sales-queryForm-customerListPage")[0].reset();
				$("#customerlist-widget-salesman").widget('clear');
				$("#sales-type-customerListPage").val("0");
				$("#sales-sortarea-customerListPage").val("");
				var areaTree=$.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
				areaTree.refresh();
				var sortTree=$.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
				sortTree.refresh();
				$("#sales-datagrid-customerListPage").datagrid('load', {});
			});
			//按钮
			$("#sales-buttons-customerListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerSimplifyAdd.do">
						{
							type: 'button-add',
							handler: function(){
								var area = "",sort = "";
								var areaTree = $.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
								if(areaTree.getSelectedNodes().length > 0){
									area = areaTree.getSelectedNodes()[0].id;
								}
								var sortTree = $.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
								if(sortTree.getSelectedNodes().length > 0){
									sort = sortTree.getSelectedNodes()[0].id;
								}
								var url = 'basefiles/showCustomerSimplifyAddPage.do?area='+area+'&sort='+sort;
								refreshLayout("客户档案【新增】", url,"add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyEdit.do">
						{
							type: 'button-edit',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var flag = isDoLockData(con.id,"t_base_sales_customer");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				var url = 'basefiles/showCustomerSimplifyEditPage.do?id='+encodeURIComponent(con.id);
								refreshLayout("客户档案【修改】", url,"edit");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyView.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var url = 'basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(con.id);
								refreshLayout("客户档案【查看】", url,"view");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var rows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请选择客户!");
									return false;
								}
								var idStr = "";
								for(var i=0;i<rows.length;i++){
									idStr += rows[i].id + ",";
								}
								$.messager.confirm("提醒","是否删除客户档案?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteCustomerFromListPage.do',
								  			data:{idStr:idStr},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				$.messager.alert("提醒",""+json.useNum+"条记录被引用,不允许删除;<br/>"+json.lockNum+"条记录网络互斥,不允许删除;<br/>"+json.failNum+"条记录删除失败;<br/>"+json.sucNum+"条记录删除成功;");
												var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
	       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
												$("#sales-datagrid-customerListPage").datagrid('clearSelections');
												$("#sales-datagrid-customerListPage").datagrid('clearChecked');
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyCopy.do">
						{
							type: 'button-copy',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var url = 'basefiles/showCustomerSimplifyCopyPage.do?id='+encodeURIComponent(con.id);
								refreshLayout("客户档案【复制】", url,"add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyOpen.do">
						{
							type: 'button-open',
							handler: function(){
								var datarow = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要启用的客户信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定启用选中客户信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("启用中..");
								    	$.ajax({   
								            url :'basefiles/openMultiCustomer.do',
											data:{ids:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            if(json.flag){
								            		$.messager.alert("提醒", "启用成功数："+ json.sucNum +"<br />启用失败数："+ json.failNum + "<br />不允许启用数："+ json.noHandleNum);
									           		var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
		       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerListPage").datagrid('clearSelections');
													$("#sales-datagrid-customerListPage").datagrid('clearChecked');
									            }else{
									            	$.messager.alert("提醒", "启用失败");
									            }
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyClose.do">
						{
							type: 'button-close',
							handler: function(){
								var datarow = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要禁用的客户信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定禁用选中客户信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("禁用中..");
								    	$.ajax({   
								            url :'basefiles/closeMultiCustomer.do',
											data:{ids:id},
											type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
												if(json.flag){
								            		$.messager.alert("提醒", "禁用成功数："+ json.sucNum +"<br />禁用失败数："+ json.failNum + "<br />不允许禁用数："+ json.noHandleNum);
													var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
		       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerListPage").datagrid('clearSelections');
													$("#sales-datagrid-customerListPage").datagrid('clearChecked');
												}else{	
									            	$.messager.alert("提醒", "禁用失败");
												}
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyImportBtn.do">
						{
							type:"button-import",
							attr:{
								type:'importUserdefined',
								importparam:'是否门店字段设置导出,否则,导入时将默认为门店,编码、客户名称、价格套、所属区域、客户业务员、默认内勤、是否总店必填',//参数描述
								url:'basefiles/importCustomerSimplifyListData.do',
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#sales-datagrid-customerListPage").datagrid('reload');	//更新列表	                                                                                        
								}
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyExportBtn.do">
						{
	    					type:'button-export',
	    					attr:{
	    						datagrid: "#sales-datagrid-customerListPage",
				 				queryForm: "#sales-queryForm-customerListPage",
						 		type:'exportUserdefined',
						 		name:'客户档案列表',
						 		url:'basefiles/exportCustomerSimplifyListData.do'
	    					}
	    				},
    				</security:authorize>
	    				{
							type: 'button-commonquery',
							attr:{
								name:'base_sales_customer',
						 		datagrid:'sales-datagrid-customerListPage'
							}
						}
				],
				buttons:[
					{},
                    <security:authorize url="/basefiles/customerSimplifyExportModBtn.do">
                    {
                        id:'button-export-detail',
                        name:'按模板导出',
                        iconCls:'button-export',
                        handler: function(){
                            var rows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
                            var idsarr=new Array();
                            if(rows.length > 0){
                                for(var i=0;i<rows.length;i++){
                                    idsarr.push(rows[i].id);
                                }
                            }
                            if(idsarr == ""){
                                $.messager.alert("提醒","请至少选择一条记录");
                            }else{
                                $("#sales-printByModule-customerListPage").Excel('export',{
                                    queryForm: "#wares-query-showGoodsInfoList",
                                    type:'exportUserdefined',
                                    name:'客户档案列表',
                                    fieldParam:{idarrs:idsarr.join(",")},
                                    url:'basefiles/getCustomerExportMod.do'
                                });
                                $("#sales-printByModule-customerListPage").trigger("click");
                            }

                        }
                    },
                    </security:authorize>
					<security:authorize url="/basefiles/customerSimplifyEditMoreBtn.do">
					{
						id:'editMore',
						name:'批量修改',
						iconCls:'button-edit',
						handler:function(){
							var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
			 				if(customerRows.length == 0){
			 					$.messager.alert("提醒","请勾选客户!");
								return false;
			 				}
			 				var idStr = "",flagIdStr = "";
			 				var unInvNum = 0;
			 				for(var i=0;i<customerRows.length;i++){
			 					var id = customerRows[i].id;
			 					var flag = isDoLockData(id,"t_base_sales_customer");
			 					if(!flag){
			 						flagIdStr += id + ",";
			 						unInvNum++;
			 						var index = $("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]);
			 						$("#sales-datagrid-customerListPage").datagrid('uncheckRow',index);
			 					}
			 					else{
			 						idStr += id + ",";
			 					}
			 				}
			 				if(flagIdStr != ""){
			 					var unIds = flagIdStr.substring(0, flagIdStr.lastIndexOf(","));
			 					$.messager.alert("警告",""+unIds+"数据正在被其他人操作，暂不能修改！");
			 					return false;
			 				}
			 				$('#sales-dialog-customerEditMore').dialog({  
							    title: '批量修改客户信息',  
							    width: 550,  
							    height: 280,
                                closed: false,
							    cache: false,  
							    href: 'basefiles/showCustomerSimplifyMoreEditPage.do',
								queryParams:{idStr:idStr,unInvNum:unInvNum},
							    modal: true
							});
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/allotPSNCustomerSimplifyBtn.do">
						{
							id:'allotpsncustomer',
							name:'分配业务员',
							iconCls:'assign-user',
							handler:function(){
								var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
				 				if(customerRows.length == 0){
				 					$.messager.alert("提醒","请勾选客户!");
									return false;
				 				}
				 				var idStr = "";
				 				for(var i=0;i<customerRows.length;i++){
				 					if("1" != customerRows[i].state){
				 						$.messager.alert("提醒","启用状态下才可分配!");
				 						$("#sales-datagrid-customerListPage").datagrid('unselectRow',$("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]));
										return false;
				 					}else{
										if(idStr == ""){
											idStr = customerRows[i].id;
										}else{
											idStr += "," + customerRows[i].id;
										}
									}
				 				}
				 				$("#sales-customerids-allotPSNCustomer").val(idStr);
								$('#sales-dialog-allotPSNCustomer').dialog({  
								    title: '分配业务员',  
								    width: 400,  
								    height: 280,  
								    closed: false,  
								    cache: false,
								    resizable:true,
								    href: 'basefiles/showAllotPSNCustomerPage.do',
									queryParams:{idStr:idStr},
								    modal: true,
								    buttons:[
								    	{  
						                    text:'确定',  
						                    iconCls:'button-save',
						                    plain:true,
						                    handler:function(){
						                    	var perids = getPersonidsValue();
						                    	var delperids = getDelPersonidsValue();
						                    	if("" == perids && "" == delperids){
						                    		$.messager.alert("提醒","请选择要分配的业务员!");
						                    		return false;
						                    	}
						                    	getCompanyValue();
						                    	getPersonidKeyInitPersonidVal();
						                    	allotCustomerToPsn_form_submit();
						                    	$("#sales-customer-allotCustomerToPsn").submit();
						                    }  
						                }
								    ]
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/clearPSNCustomerBtn.do">
					{
						id:'clearpsncustomer',
						name:'清除业务员',
						iconCls:'assign-user',
						handler:function(){
							var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
							if(customerRows.length == 0){
								$.messager.alert("提醒","请勾选客户!");
								return false;
							}
							var idStr = "";
							for(var i=0;i<customerRows.length;i++){
								if("1" != customerRows[i].state){
									$.messager.alert("提醒","启用状态下才可清除!");
									$("#sales-datagrid-customerListPage").datagrid('unselectRow',$("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]));
									return false;
								}else{
									if(idStr == ""){
										idStr = customerRows[i].id;
									}else{
										idStr += "," + customerRows[i].id;
									}
								}
							}
							if(idStr != ""){
								$('#sales-dialog-clearPSNCustomer').dialog({
									title: '清除业务员',
									width: 300,
									height: 200,
									closed: false,
									cache: false,
									resizable:true,
									href: 'basefiles/showClearPSNCustomerPage.do',
									queryParams:{customerids:idStr},
									modal: true,
									buttons:[
										{
											text:'确定',
											iconCls:'button-save',
											plain:true,
											handler:function(){
												claerCustomerToPsn_form_submit();
												$("#sales-customer-clearCustomerToPsn").submit();
											}
										}
									]
								});
							}
						}
					},
					</security:authorize>
                    <security:authorize url="/basefiles/viewCustomerLocation.do">
                    {
                        id:'editMap',
                        name:'地图',
                        iconCls:'button-map',
                        handler:function(){

                            var type = 'view';
                            <security:authorize url="/basefiles/editCustomerLocation.do">
                                type = 'edit';
                            </security:authorize>
                            var row = $('#sales-datagrid-customerListPage').datagrid('getSelected');
                            if(row == null){
                                $.messager.alert('提醒','请选择一条记录');
                                return false;
                            }

							$('#sales-keyword-searchMap').val('');
                            $('<div id="sales-dialog-customerListPage1"></div>').appendTo('#sales-dialog-customerListPage');
                            $('#sales-dialog-customerListPage1').dialog({
                                maximizable:true,
                                resizable:true,
                                title: '客户定位【修改】 - ' + row.id + ' - ' + row.name,
                                width: 740,
                                height: 450,
                                closed: true,
                                cache: false,
                                maximized: true,
//                                href: url,
                                content: '<iframe name="mapframe" src="' + 'basefiles/customerSimplifyMapPage.do?type=' + type + '&id='+encodeURIComponent(row.id) + '" style="width: 100%; height: 100%; border: 0px;"></iframe>',
                                modal: true,
                                <security:authorize url="/basefiles/editCustomerLocation.do">
                                buttons:[{
                                    text:'保存',
                                    iconCls:'button-save',
                                    handler:function(){

                                        window.mapframe.saveLocation();
                                    }
                                }],
                                </security:authorize>
                                onClose:function(){
                                    $('#sales-dialog-customerListPage1').dialog("destroy");
                                }
                            });
                            $("#sales-dialog-customerListPage1").dialog("open");
                        }
                    },
                    </security:authorize>
					{}
				],
				model:'base',
				type:'multipleList',
				tname: 't_base_sales_customer'
			});
    	});
    </script>
  </body>
</html>
