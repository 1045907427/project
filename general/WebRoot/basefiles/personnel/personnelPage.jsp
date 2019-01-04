<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>人员档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <input type="hidden" id="personnel-opera"/>
 	<input type="hidden" id="personnel-id" value="<c:out value="${id}"></c:out>"/>
 	<input type="hidden" id="personnel-belongdeptid" value="<c:out value="${belongdeptid}"></c:out>"/>
 	<input type="hidden" id="personnel-beginedit" value="0"/>
 	<input type="hidden" id="personnel-beginCustomeredit" value="0"/>
 	<input type="hidden" id="personnel-beginBrandedit" value="0"/>
 	<input type="hidden" id="personnel-beginedu" value="0"/>
 	<input type="hidden" id="personnel-beginpost" value="0"/>
 	<input type="hidden" id="personnel-beginwork" value="0"/>
 	<!-- 针对删除前添加的对应客户 -->
 	<input type="hidden" id="personnel-beforeDelCustomerAdd" />
 	<input type="hidden" id="personnel-brandByDept" />
 	<input type="hidden" id="personnel-init-deptid" />
 	<div class="easyui-layout" data-options="fit:true,border:false">
 		<div data-options="region:'north',split:false,border:false" >
    		<div class="buttonBG" id="basefiles-button-personnel"></div>
    	</div>
 		<div data-options="region:'center'" style="border: 0px;">
 			<div class="easyui-panel" data-options="fit:true" id="basefiles-pannel-personnel"></div>
 			<div id="personnel-window-showOldImg"></div>
 		</div>
 	</div>
 	<div id="personnel-dialog-goodsids"></div>
 	<div id="personnel-dialog-customer"></div>
 	<div id="personnel-dialog-brand"></div>
 	<div id="personnel-dialog-file-upload"></div>
 	<script type="text/javascript">
 		var person_title = tabsWindowTitle('/basefiles/showPersonnelListPage.do');
 	
 		var personnel_adjunctid = "";
 		var personnel_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		
		var $dgPersonnel = null,$dgPersonnelWorks = null,$dgPersonnelPost = null,$dgPersonnelCustomer = null,$dgPersonnelBrand = null;
		//加载下拉框 
		function loadDropdown(){
			
			//基础信息 -部门档案 
		  	$("#personnel-widget-personnelDept").widget({
		  		width:120,
				name:'t_base_personnel',
				col:'belongdeptid',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#personnel-init-deptid").val(data.id);
				},
				onClear:function(){
					$("#personnel-init-deptid").val("");
				},
				onLoadSuccess:function(){
					$("#personnel-init-deptid").val($(this).widget('getValue'));
				}
			});
			
			//工作岗位
		  	$("#personnel-widget-personnelPost").widget({
		  		width:120,
				name:'t_base_personnel',
				col:'belongpost',
				singleSelect:true,
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			});
			
			//状态
			$('#personnel-widget-state').widget({
				width:100,
				name:'t_base_personnel',
				col:'state',
				singleSelect:true
			});
			
			//员工类型
			$('#personnel-widget-personnelstyle').widget({
				width:120,
				name:'t_base_personnel',
				col:'personnelstyle',
				singleSelect:true
			});
			
			//最高学历
			$('#personnel-widget-highestdegree').widget({
				width:120,
				name:'t_base_personnel',
				col:'highestdegree',
				singleSelect:true
			});
			
			//上级领导
			$("#personnel-widget-leadid").widget({
				width:120,
				name:'t_base_personnel',
				col:'leadid',
				singleSelect:true
			});
			
			//性别
			$('#personnel-widget-sex').widget({
				width:120,
				name:'t_base_personnel',
				col:'sex',
				singleSelect:true
			});
			
			//婚姻状况
			$('#personnel-widget-maritalstatus').widget({
				width:120,
				name:'t_base_personnel',
				col:'maritalstatus',
				singleSelect:true
			});
			
			//民族
			$('#personnel-widget-nation').widget({
				width:120,
				name:'t_base_personnel',
				col:'nation',
				singleSelect:true
			});
			
			//政治面貌
			$('#personnel-widget-polstatus').widget({
				width:120,
				name:'t_base_personnel',
				col:'polstatus',
				singleSelect:true
			});
			
			//薪资方案
			//$('#personnel-widget-salaryscheme').widget({
			//	width:120,
			//	name:'t_base_personnel',
			//	col:'salaryscheme',
			//	singleSelect:true
			//});
		}
		
		function clickEmployeType(type,initialemptype){
			var employetype = $("#personnel-employetype").val();
			var norepeatemploye = $("#personnel-norepeatemploye").val();
			var empolyetypeArr = employetype.split(",");
			var custoemrids = $("#personnel-personcustomer").val();
			var brandids = $("#personnel-addBrandid").val();
			var flag = true;
			if(undefined != norepeatemploye && "" != norepeatemploye && null != norepeatemploye){
				for(var i = 0;i<empolyetypeArr.length;i++){
					if("" != empolyetypeArr[i] && null != empolyetypeArr[i]){
						if(norepeatemploye.indexOf(empolyetypeArr[i]) != -1){
							flag = false;	
						}
					}
				}
			}
			$(".norepeatemploye").each(function(){
				if(!flag){
					if(employetype.indexOf($(this).val()) == -1){
						$(this).attr("disabled","disabled");
					}
				}
			});
			$('.personnel-employetype').click(function(){
				var classname = $(this)[0].className;
				var employetype = "",checkval = $(this).val();
				var checkflag = true;

				//判断该值是否为品牌业务员or厂家业务员
				if("add != type" && "copy" != type && !$(this)[0].checked && ("3" == checkval || "7" == checkval || "1" == checkval)){
					var ret = personnel_AjaxConn({personid:$("#basefiles-id-personnel").val(),employetype:checkval},'basefiles/checkEmployetypeOfCustomerData.do');
					var retjson = $.parseJSON(ret);
					if(retjson.flag){
						$(this)[0].checked = true;
						$.messager.alert("提醒","对应客户、对应品牌不为空，不允许修改该属性!");
						checkflag = false;
					}
				}
				if(checkflag){
					$('.personnel-employetype').each(function(){
						if(checkflag && $(this).attr("checked")){
							if(employetype==""){
								employetype = $(this).val();
							}else{
								employetype += ","+$(this).val();
							}
						}
					});
					//业务属性中品牌业务员和厂家业务员是否互换，若互换，则将customerids、brandids清空
					if("" != initialemptype){
						if(initialemptype.indexOf("3") != -1){
							if("7" == checkval){
								$("#personnel-addBrandid").val("");
								$("#personnel-personcustomer").val("");
							}else if("3" == checkval){
								$("#personnel-addBrandid").val(brandids);
								$("#personnel-personcustomer").val(custoemrids);
							}
						}else if(initialemptype.indexOf("7") != -1){
							if("3" == checkval){
								$("#personnel-addBrandid").val("");
								$("#personnel-personcustomer").val("");
							}else if("7" == checkval){
								$("#personnel-addBrandid").val(brandids);
								$("#personnel-personcustomer").val(custoemrids);
							}
						}
					}
					$("#personnel-employetype").val(employetype);
					if(classname.indexOf("norepeatemploye") != -1){
						$(this).attr("class","personnel-employetype emptchecked");
						$(".norepeatemploye").each(function(){
							$(this).attr("disabled","disabled");
						});
					}else if(classname.indexOf("emptchecked") > 0){
						$(this).attr("class","personnel-employetype norepeatemploye");
						$(".norepeatemploye").each(function(){
							$(this).removeAttr("disabled");
						});
						$("#personnel-table-personnelCustomerList").removeClass("create-datagrid");
						$("#personnel-table-personnelBrandList").removeClass("create-datagrid");
					}
				}
				return checkflag;
			});
		}
		
		//根据品牌编码集合获取对应品牌列表数据
		function getBrandToPersonList(brandids){
			$.ajax({  
	            url :'basefiles/getBrandToPersonList.do',
	            type:'post',
	            dataType:'json',
	            data:{brandids:brandids,personid:$("#basefiles-id-personnel").val()},
	            success:function(retJson){
					$dgPersonnelBrand.datagrid('loadData',retJson);
	            }
	        });
		}
		
		/**
		 * 生成随机id
		 * @return
		 */
		personnel_getRandomid = function(){
			return parseInt(100000000000*Math.random());
		}
		
		//结束行编辑
		var personnel_editIndexEdu = undefined;
		var personnel_editIndexWork = undefined;
		var personnel_editIndexPost = undefined;
		var personnel_editIndexCustomer = undefined;
		var personnel_editIndexBrand = undefined;
		function endEditingEdu(){
			if (personnel_editIndexEdu == undefined){
				return true
			}
			else{
				return false;
			}
		}
		function endEditingWork(){
			if (personnel_editIndexWork == undefined){
				return true
			}
			else{
				return false;
			}
		}
		function endEditingPost(){
			if (personnel_editIndexPost == undefined){
				return true
			}
			else{
				return false;
			}
		}
		function endEditingCustomer(){
			if (personnel_editIndexCustomer == undefined){
				return true
			}
			else{
				return false;
			}
		}
		function endEditingBrand(){
			if (personnel_editIndexBrand == undefined){
				return true
			}
			else{
				return false;
			}
		}
		
		//获取标签页
		function getTabs(type){
			var height = $(window).height()-124;
			var id = $("#basefiles-hdid-personnel").val();
			var eduUrl = "", workUrl = "",postUrl = "",customerUrl = "",brandUrl = "";
			if(type == "copy"){
				eduUrl = 'basefiles/showPersEducationList.do?personid='+id;
				workUrl = 'basefiles/showPersWorksList.do?personid='+id;
				postUrl = 'basefiles/showPersPostsList.do?personid='+id;
			}
			else if(type == "edit" || type == "view"){
				eduUrl = 'basefiles/showPersEducationList.do?personid='+id;
				workUrl = 'basefiles/showPersWorksList.do?personid='+id;
				postUrl = 'basefiles/showPersPostsList.do?personid='+id;
			}
			$(".tags").find("li").click(function(){
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 1){
	                //对应客户列表
	                $dgPersonnelCustomer = $("#personnel-table-personnelCustomerList");
	                if($("#personnel-employetype_1").attr("checked")){
	                	if(type == "edit"){
	                		customerUrl = 'basefiles/getCustomerListForPsnCstm.do?salesuserid='+$("#basefiles-id-personnel").val();
	                	}
	                	if(!$dgPersonnelCustomer.hasClass("create-datagrid")){
		                	$dgPersonnelCustomer.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:false,
					  			checkOnSelect:true,
					  			selectOnCheck:true,
					  			pageSize:500,
					  			border:false,
					  			pagination:true,
					  			url:customerUrl,
					  			height:height,
					  			columns:[[
					  				{field:'ck',title:'',width:100,checkbox:true},  
					  				{field:'id',title:'编号',width:150,hidden:true},
					  				{field:'personid',title:'人员编号',width:150,hidden:true},
					  				{field:'customerid',title:'客户编码',width:60},
					  				{field:'customername',title:'客户名称',width:210,isShow:true},
					  				{field:'salesareaname',title:'所属区域',width:80,isShow:true},
			        				{field:'customersortname',title:'所属分类',width:80,isShow:true}
					  			]],
								toolbar : [
									<security:authorize url="/basefiles/customerEdit.do">
									{
						            	text : "修改",
						                iconCls : "button-edit",
						                handler : function() {
						                	var row = $dgPersonnelCustomer.datagrid('getSelected');
						                	if (row == null) {
			            						$.messager.alert("提醒","请选择行!");
			            						return false;
			            					}
			            					$("#personnel-beginCustomeredit").val("1");
			            					top.addOrUpdateTab('basefiles/customerPage.do?type=edit&id='+ row.customerid, "客户档案修改");
						                }
					            	}
					            	</security:authorize>
					            ],
					            onDblClickRow:function(rowIndex, rowData){
					            	<security:authorize url="/basefiles/customerEdit.do">
		            					$("#personnel-beginCustomeredit").val("1");
		            					top.addOrUpdateTab('basefiles/customerPage.do?type=edit&id='+ rowData.customerid, "客户档案修改");
	            					</security:authorize>
					            }
					   		});
					   		$dgPersonnelCustomer.addClass("create-datagrid");
		                }
	                }
	                else if($("#personnel-employetype_3").attr("checked") || $("#personnel-employetype_7").attr("checked")){
	                	var delCustomerIds = "";
	                	if(type != "add" && type != "copy"){
	                		if($("#personnel-employetype_3").attr("checked")){
	                			customerUrl = 'basefiles/getPersonCustomerListPageData.do?personid='+id;
	                		}else if($("#personnel-employetype_7").attr("checked")){
	                			customerUrl = 'basefiles/getPersonSupplierCustomerListPageData.do?personid='+id;
	                		}
	                	}
	                	else{
	                		customerUrl = "";
	                	}
	                	if(!$dgPersonnelCustomer.hasClass("create-datagrid")){
		                	$dgPersonnelCustomer.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:false,
					  			checkOnSelect:true,
					  			selectOnCheck:true,
					  			pageSize:500,
					  			border:false,
					  			pagination:true,
					  			url:customerUrl,
					  			height:height,
					  			columns:[[
					  				{field:'ck',title:'',width:100,checkbox:true},
					  				{field:'id',title:'编号',width:150,hidden:true},
					  				{field:'personid',title:'人员编号',width:150,hidden:true},
					  				{field:'customerid',title:'客户编码',width:60},
					  				{field:'customername',title:'客户名称',width:210,isShow:true},
					  				{field:'salesareaname',title:'所属区域',width:80,isShow:true},
			        				{field:'customersortname',title:'所属分类',width:80,isShow:true}
					  			]],
								toolbar : [ {
					                text : "添加",
					                iconCls : "button-add",
					                handler : function() {
					                    var newemployetype = $("#personnel-employetype").val();
					                    var oldemployetype = $("#personnel-employetype_old").val();
					                    if(oldemployetype!=newemployetype){
					                        $.messager.alert("提醒","人员业务属性修改过，需要保存后才能添加客户!");
					                        return false;
                                        }
					                	if(endEditingCustomer()){
					                		var personid = $("#basefiles-id-personnel").val();
					                		if("" == personid){
					                			$.messager.alert("提醒","请先输入人员编码!");
					                			$("#basefiles-id-personnel").focus();
					                			return false;
					                		}
					                		var deptid = $("#personnel-widget-personnelDept").widget('getValue');
					                		$('#personnel-dialog-customer').dialog({  
											    title: '对应客户新增',  
											    width: 500,
											    height: 400,  
											    closed: false,
											    cache: false,
											    href: 'basefiles/showCustomerToGoodsAddPage.do?personid='+personid+'&deptid='+deptid+'&type='+type,  
											    modal: true
											});
					                	}
					                }
					            },{
					            	text : "删除",
					                iconCls : "button-delete",
					                handler : function() {
					            	    var newemployetype = $("#personnel-employetype").val();
					                    var oldemployetype = $("#personnel-employetype_old").val();
					                    if(oldemployetype!=newemployetype){
					                        $.messager.alert("提醒","人员业务属性修改过，需要保存后才能删除客户!");
					                        return false;
                                        }
					                	var personid = $("#personnel-hidden-hdoldIdEdit").val();
					                	var employetype = $("#personnel-employetype").val();
					                	var rows = $dgPersonnelCustomer.datagrid('getChecked');
					                	if(rows.length == 0){
					                		$.messager.alert("提醒","请选择对应客户!");
		            						return false;
					                	}
					                	var customerids = "";
					                	for(var i=0;i<rows.length;i++){
					                		if(customerids == ""){
					                			customerids = rows[i].customerid;
					                		}else{
					                			customerids += "," + rows[i].customerid;
					                		}
					                	}
					                	$.messager.confirm("警告","若删除,将真实清除数据,是否删除该对应客户?",function(r){
					                		if(r){
					                			loading("删除中..");
					                			$.ajax({
					                				url:'basefiles/deletePersonCustomer.do',
										  			data:{customerids:customerids,personid:personid,employetype:employetype},
										  			dataType:'json',
										  			type:'post',
										  			success:function(json){
										  				loaded();
										  				if(json.flag){
										  					$.messager.alert("提醒","删除成功");
										  					//判断是否删除数据,若删除过,将对应客户新增的客户编码去除删除的数据
										  					var newaddcustomerids = $("#personnel-personcustomer").val();//要新增的对应客户数据
										  					if(customerids != "" && newaddcustomerids != ""){
										  						var customeridsArr = customerids.split(",");
										  						for(var i=0;i<customeridsArr.length;i++){
										  							newaddcustomerids = newaddcustomerids.replace(customeridsArr[i]+",","");
										  						}
										  						$("#personnel-personcustomer").val(newaddcustomerids)
										  						//替换掉虚拟新增的数据（删除前添加的对应客户）
										  						var beforeDelCstAdd = $("#personnel-beforeDelCustomerAdd").val();
										  						beforeDelCstAdd = beforeDelCstAdd.replace(customerids+",","");
										  						$("#personnel-beforeDelCustomerAdd").val(beforeDelCstAdd);
										  					}
										  					var json = {};
										  					json['delcustomerids'] = customerids;
										  					json['customerids'] = newaddcustomerids;
										  					// if($("#personnel-beginCustomeredit").val() == "1"){
										  					// 	$dgPersonnelCustomer.datagrid('options').url = 'basefiles/showCustomerListData.do?personid='+personid;
										  					// }else{
										  					// 	if(employetype.indexOf("3") != -1){
												  			// 		$dgPersonnelCustomer.datagrid('options').url = 'basefiles/getPersonCustomerListPageData.do?personid='+personid;
											  				// 	}else if(employetype.indexOf("7") != -1){
											  				// 		$dgPersonnelCustomer.datagrid('options').url = 'basefiles/getPersonSupplierCustomerListPageData.do?personid='+personid;
											  				// 	}
										  					// }
										  		        	$dgPersonnelCustomer.datagrid("reload");
										  				}else{
										  		        	$.messager.alert("提醒","删除失败");
										  		        }
										  			}
					                			});
					                		}
					                	});
						                personnel_editIndexCustomer = undefined;
						                $dgPersonnelCustomer.datagrid('clearSelections');
					                }
					            },{
					            	text : "导入",
					                iconCls : "button-import",
					                id:'import',
					                handler : function(){
					                	var personid = $("#basefiles-id-personnel").val();
				                		if("" == personid){
				                			$.messager.alert("提醒","请先输入人员编码!");
				                			$("#basefiles-id-personnel").focus();
				                			return false;
				                		}
					                	personCustomerImport(personid);
					                }
					            }]
					   		});
					   		$dgPersonnelCustomer.addClass("create-datagrid");
		                }
	                }
	                else{
	                	//$dgPersonnelCustomer.removeClass("create-datagrid");
	                	if(!$dgPersonnelCustomer.hasClass("create-datagrid")){
		                	$dgPersonnelCustomer.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:false,
					  			checkOnSelect:true,
					  			selectOnCheck:true,
					  			pageSize:500,
					  			border:false,
					  			height:height,
					  			columns:[[
					  				{field:'ck',title:'',width:100,checkbox:true},
					  				{field:'id',title:'编号',width:150,hidden:true},
					  				{field:'personid',title:'人员编号',width:150,hidden:true},
					  				{field:'customerid',title:'客户编码',width:60},
					  				{field:'customername',title:'客户名称',width:210,isShow:true},
					  				{field:'salesareaname',title:'所属区域',width:80,isShow:true},
			        				{field:'customersortname',title:'所属分类',width:80,isShow:true}
					  			]]
					   		});
					   		$dgPersonnelCustomer.removeClass("create-datagrid");
		                }
	                }
	             }
	             else if(index == 2){
	             	$dgPersonnelBrand = $("#personnel-table-personnelBrandList");
	             	if($("#personnel-employetype_3").attr("checked") || $("#personnel-employetype_7").attr("checked")){
	             		if($("#personnel-employetype_3").attr("checked")){
                			brandUrl = 'basefiles/showBrandList.do?personid='+id;
                		}else if($("#personnel-employetype_7").attr("checked")){
                			brandUrl = 'basefiles/showSupplierBrandList.do?personid='+id;
                		}
		             	if(!$dgPersonnelBrand.hasClass("create-datagrid")){
		             		$dgPersonnelBrand.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:false,
					  			checkOnSelect:true,
						  		selectOnCheck:true,
					  			pageSize:500,
					  			border:false,
					  			pagination:true,
					  			url:brandUrl,
					  			height:height,
					  			columns:[[
					  				{field:'ck',title:'',width:100,checkbox:true},
					  				{field:'id',title:'编号',width:150,hidden:true},
					  				{field:'personid',title:'人员编号',width:150,hidden:true},
					  				{field:'brandid',title:'品牌编码',width:60},
					  				{field:'brandname',title:'品牌名称',width:320,isShow:true}
					  			]],
					            toolbar : [ {
					                text : "添加",
					                iconCls : "button-add",
					                handler : function() {
					                    var newemployetype = $("#personnel-employetype").val();
					                    var oldemployetype = $("#personnel-employetype_old").val();
					                    if(oldemployetype!=newemployetype){
					                        $.messager.alert("提醒","人员业务属性修改过，需要保存后才能添加品牌!");
					                        return false;
                                        }
					                	var personid = $("#basefiles-id-personnel").val();
				                		if("" == personid){
				                			$.messager.alert("提醒","请先输入人员编码!");
				                			$("#basefiles-id-personnel").focus();
				                			return false;
				                		}
				                		var brandids = $("#personnel-addBrandid").val();
				                		$('#personnel-dialog-brand').dialog({  
										    title: '对应品牌新增',  
										    width: 500,
										    height: 400,  
										    closed: false,
										    cache: false,
										    href: 'basefiles/showBrandToGoodsPage.do?brandids='+brandids,  
										    modal: true
										});
					                }
					            },{
					            	text : "删除",
					                iconCls : "button-delete",
					                handler : function() {
					            	    var newemployetype = $("#personnel-employetype").val();
					                    var oldemployetype = $("#personnel-employetype_old").val();
					                    if(oldemployetype!=newemployetype){
					                        $.messager.alert("提醒","人员业务属性修改过，需要保存后才能删除品牌!");
					                        return false;
                                        }
					                	var personid = $("#basefiles-id-personnel").val();
				                		if("" == personid){
				                			$.messager.alert("提醒","请先输入人员编码!");
				                			$("#basefiles-id-personnel").focus();
				                			return false;
				                		}
					                	var rows = $dgPersonnelBrand.datagrid('getChecked');
					                	if(rows.length == 0){
					                		$.messager.alert("提醒","请勾选对应品牌!");
		            						return false;
					                	}
					                	var brandids = $("#personnel-addBrandid").val();
					                	if("" != brandids){
					                		$.messager.confirm("警告","若删除,将真实清除数据,是否删除该对应品牌,?",function(r){
						                		if(r){
						                			var delbrandids = "";
						                			personid = $("#basefiles-id-personnel").val();
						                			for(var i=0;i<rows.length;i++){
						                				if(delbrandids == ""){
						                					delbrandids = rows[i].brandid;
						                				}else{
						                					delbrandids += "," + rows[i].brandid;
						                				}
								                	}
						                			loading("删除中..");
						                			var ret = personnel_AjaxConn({delbrandids:delbrandids,personid:personid},'basefiles/deletePersonBrand.do');
						                			var retjson = $.parseJSON(ret);
						                			loaded();
						                			if(retjson.flag){
									  					$.messager.alert("提醒","删除成功");
									                	$dgPersonnelBrand.datagrid("reload");
									  				}else{
									  		        	$.messager.alert("提醒","删除失败");
									  		        }
						                		}
						                	});
							                personnel_editIndexCustomer = undefined;
						                	//getBrandToPersonList(brandids);
				                		}
					                }
					            }]
					   		});
					   		$dgPersonnelBrand.addClass("create-datagrid");
		             	}
	                }
	                else{
	                	//$dgPersonnelBrand.removeClass("create-datagrid");
	                	if(!$dgPersonnelBrand.hasClass("create-datagrid")){
		             		$dgPersonnelBrand.datagrid({
					   			method:'post',
					   			title:'',
					   			rownumbers:true,
					  			singleSelect:true,
					  			pageSize:500,
					  			border:false,
					  			height:height,
					  			columns:[[
					  				{field:'id',title:'编号',width:150,hidden:true},
					  				{field:'personid',title:'人员编号',width:150,hidden:true},
					  				{field:'brandid',title:'品牌编码',width:60},
						  			{field:'brandname',title:'品牌名称',width:320,isShow:true}
					  			]],
					  			toolbar : [ {
					                text : "添加",
					                iconCls : "button-add",
					                handler : function() {
					                	var employetype = $("#personnel-employetype").val();
					                	if(employetype.indexOf("3") == -1 && employetype.indexOf("7") == -1){
						                	$.messager.alert("提醒","需勾选人员属性为品牌业务员或厂家业务员的业务属性!");
					                		return false;
					                	}
					                }
					            }]
					   		});
					   		$dgPersonnelBrand.removeClass("create-datagrid");
	             		}
	                }
	             }
				else if(index == 3){
	                //教育经历列表
	                $dgPersonnel = $("#personnel-table-personnelEduList");
	                if(!$dgPersonnel.hasClass("create-datagrid")){
	                	var delEduIds = "";
	                	$dgPersonnel.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:eduUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				  				{field:'personid',title:'人员编号',width:150,hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center',editor:{
					  					type:'dateText',
					  					options:{
					  						startDate:'1900-01',
					  						dateFmt:'yyyy-MM'
					  					}
					  				}
				  				},
				  				{field:'enddate',title:'结束日期',width:80,align:'center',editor:{
				  						type:'dateText',
				  						options:{
				  							startDate:'1900-01',
				  							dateFmt:'yyyy-MM',
				  							maxDate:'%y-%M'
				  						}
				  					}
				  				},
				  				{field:'educname',title:'教育机构名称',width:100,align:'center',editor:'text'},
				  				{field:'type',title:'教育方式',width:150,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return getSysCodeName("eduType",val);
				  					},
				  					editor:{
					  					type:'comborefer',
					        		  	options:{
							    			name:'t_base_perssonnel_edu',
							    			col:'type',
							    			singleSelect:true
							    		}
					  				}
				  				},
				  				{field:'certificate',title:'获得证书',width:150,align:'center',editor:'text'},
				  				{field:'remark',title:'备注',width:150,align:'center',editor:'text'}
				  			]],
							toolbar : [ {
				                text : "添加",
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditingEdu()){
				                		var id = personnel_getRandomid();
				                		$dgPersonnel.datagrid('appendRow', {id:id,startdate:"",enddate:"",educname:"",type:0,certificate:"",remark:""});
					                	personnel_editIndexEdu = parseInt($dgPersonnel.datagrid('getRows').length-parseInt(1));
					                	$("#personnel-index-eduList").val(personnel_editIndexEdu);
					                	$dgPersonnel.datagrid('selectRow', personnel_editIndexEdu).datagrid('beginEdit', personnel_editIndexEdu);
				                	}
				                }
				            },{
				            	text : "确定",
				                iconCls : "button-save",
				                handler : function() {
				                	var row = $dgPersonnel.datagrid('getSelected');
	            					if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					var rowIndex = $dgPersonnel.datagrid('getRowIndex', row);
	            					if($dgPersonnel.datagrid('validateRow', rowIndex)){
	            						$dgPersonnel.datagrid('endEdit', rowIndex);
							   			personnel_editIndexEdu = undefined;
							   			$dgPersonnel.datagrid('clearSelections');
	            					}
				                }
				            },{
				            	text : "修改",
				                iconCls : "button-edit",
				                handler : function() {
				                	var row = $dgPersonnel.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
						            var rowIndex = $dgPersonnel.datagrid('getRowIndex', row);
					                if(endEditingEdu()){
					                	$("#personnel-index-eduList").val(rowIndex);
										$dgPersonnel.datagrid('beginEdit', rowIndex);
										personnel_editIndexEdu = rowIndex;
									}
				                }
				            },{
				            	text : "删除",
				                iconCls : "button-delete",
				                handler : function() {
				                	var row = $dgPersonnel.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					if($("#personnel-hidden-hdEduListDelId").val() == ""){
	            						delEduIds = row.id;
	            					}
	            					else{
	            						delEduIds = $("#personnel-hidden-hdEduListDelId").val() + "," + row.id;
	            					}
	            					$("#personnel-hidden-hdEduListDelId").val(delEduIds);
						            var rowIndex = $dgPersonnel.datagrid('getRowIndex', row);
					                $dgPersonnel.datagrid('deleteRow', rowIndex);
					                personnel_editIndexEdu = undefined;
					                $dgPersonnel.datagrid('clearSelections');
				                }
				            }],
			   				onClickRow:function(rowIndex, rowData){
			   					var index = parseInt($("#personnel-index-eduList").val());
			   					personnel_editIndexEdu = undefined;
			   					$dgPersonnel.datagrid('endEdit', index);
			   					$dgPersonnel.datagrid('selectRow', rowIndex);
			   				}
				   		});
				   		$dgPersonnel.addClass("create-datagrid");
	                }
	             }
	             else if(index == 4){//第三个个标签
	             	$dgPersonnelWorks = $("#personnel-table-personnelWorksList");
	             	if(!$dgPersonnelWorks.hasClass("create-datagrid")){
	             		var delWorkIds = "";
	             		$dgPersonnelWorks.datagrid({//工作经历
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:workUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				  				{field:'personid',title:'人员编号',width:150,hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center',editor:{
					  					type:'dateText',
					  					options:{
					  						startDate:'1900-01',
					  						dateFmt:'yyyy-MM',
					  					}
					  				}
				  				},
				  				{field:'enddate',title:'结束日期',width:80,align:'center',editor:{
				  						type:'dateText',
				  						options:{
				  							startDate:'1900-01',
				  							dateFmt:'yyyy-MM',
				  							maxDate:'%y-%M',
				  						}
				  					}
				  				},
				  				{field:'workname',title:'工作单位名称',width:100,align:'center',editor:'text'},
				  				{field:'post',title:'担任职务',width:70,align:'center',editor:'text'},
				  				{field:'mainachievement',title:'主要业绩',width:150,align:'center',editor:'text'},
				  				{field:'remark',title:'备注',width:150,align:'center',editor:'text'}
				  			]],
							toolbar : [ {
				                text : "添加",
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditingWork()){
				                		var id = personnel_getRandomid();
				                		$dgPersonnelWorks.datagrid('appendRow', {id:id,startdate:"",enddate:"",workname:"",post:"",mainachievement:"",remark:""});
					                	personnel_editIndexWork = parseInt($dgPersonnelWorks.datagrid('getRows').length-1);
					                	$("#personnel-index-workList").val(personnel_editIndexWork);
					                	$dgPersonnelWorks.datagrid('selectRow', personnel_editIndexWork).datagrid('beginEdit', personnel_editIndexWork);
				                	}
				                }
				            },{
				            	text : "确定",
				                iconCls : "button-save",
				                handler : function() {
				                	var row = $dgPersonnelWorks.datagrid('getSelected');
	            					if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					var rowIndex = $dgPersonnelWorks.datagrid('getRowIndex', row);
				                	$dgPersonnelWorks.datagrid('endEdit', rowIndex);
						   			personnel_editIndexWork = undefined;
						   			$dgPersonnelWorks.datagrid('clearSelections');
				                }
				            },{
				            	text : "修改",
				                iconCls : "button-edit",
				                handler : function() {
				                	var row = $dgPersonnelWorks.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
						            var rowIndex = $dgPersonnelWorks.datagrid('getRowIndex', row);
					                if(endEditingWork()){
					                	$("#personnel-index-workList").val(rowIndex);
										$dgPersonnelWorks.datagrid('beginEdit', rowIndex);
										personnel_editIndexWork = rowIndex;
									}
				                }
				            },{
				            	text : "删除",
				                iconCls : "button-delete",
				                handler : function() {
				                	var row = $dgPersonnelWorks.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					if($("#personnel-hidden-hdWorkListDelId").val() == ""){
	            						delWorkIds = row.id;
	            					}
	            					else{
	            						delWorkIds = $("#personnel-hidden-hdWorkListDelId").val() + "," + row.id;
	            					}
	            					$("#personnel-hidden-hdWorkListDelId").val(delWorkIds);
						            var rowIndex = $dgPersonnelWorks.datagrid('getRowIndex', row);
					                $dgPersonnelWorks.datagrid('deleteRow', rowIndex);
					                personnel_editIndexWork = undefined;
					                $dgPersonnelWorks.datagrid('clearSelections');
				                }
				            }],
			   				onClickRow:function(rowIndex, rowData){
			   					var index = parseInt($("#personnel-index-workList").val());
			   					$dgPersonnelWorks.datagrid('endEdit', index);
					   			personnel_editIndexWork = undefined;
					   			$dgPersonnelWorks.datagrid('selectRow', rowIndex);
			   				}
				   		});
				   		$dgPersonnelWorks.addClass("create-datagrid");
	             	}
	             }
	             else if(index == 5){
	             	//岗位变动记录
	             	$dgPersonnelPost = $("#personnel-table-personnelPostList");
	             	if(!$dgPersonnelPost.hasClass("create-datagrid")){
	             		var delPostIds = "";
	             		$dgPersonnelPost.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			url:postUrl,
				  			height:height,
				  			columns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				  				{field:'personid',title:'人员编号',width:150,hidden:true},
				  				{field:'startdate',title:'开始日期',width:80,align:'center',editor:{
					  					type:'dateText',
					  					options:{
					  						startDate:'1900-01',
					  						dateFmt:'yyyy-MM',
					  					}
					  				}
				  				},
				  				{field:'enddate',title:'结束日期',width:80,align:'center',editor:{
				  						type:'dateText',
				  						options:{
				  							startDate:'1900-01',
				  							dateFmt:'yyyy-MM',
				  							maxDate:'%y-%M',
				  						}
				  					}
				  				},
				  				{field:'belongdeptid',title:'所属部门',width:125,align:'center',
				  					formatter:function(value,row){  
				                        return row.belongdeptName;  
				                    }, 
				  					editor:{
					  					type:'comborefer',
					        		  	options:{
							    			name:'t_base_personnel_post',
							    			col:'belongdeptid',
							    			singleSelect:true
							    		}
					  				}
				  				},
				  				{field:'belongpostid',title:'所属岗位',width:160,align:'center',
				  					formatter:function(value,row){  
				                        return row.belongpostName;  
				                    }, 
				  					editor:{
					  					type:'comborefer',
					        		  	options:{
							    			name:'t_base_personnel_post',
							    			col:'belongpostid',
							    			width:'160',
							    			singleSelect:true
							    		}
					  				}
				  				},
				  				{field:'salaryscheme',title:'薪资方案',width:150,align:'center',editor:'text'},
				  				{field:'remark',title:'备注',width:150,align:'center',editor:'text'}
				  			]],
							toolbar : [ {
				                text : "添加",
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditingPost()){
				                		var id = personnel_getRandomid();
				                		$dgPersonnelPost.datagrid('appendRow', {id:id,startdate:"",enddate:"",belongdeptid:"",belongpostid:"",salaryscheme:"",remark:""});
					                	personnel_editIndexPost = parseInt($dgPersonnelPost.datagrid('getRows').length-1);
					                	$("#personnel-index-postList").val(personnel_editIndexPost);
					                	$dgPersonnelPost.datagrid('selectRow', personnel_editIndexPost).datagrid('beginEdit', personnel_editIndexPost);
				                	}
				                }
				             },{
				            	text : "确定",
				                iconCls : "button-save",
				                handler : function() {
				                	var row = $dgPersonnelPost.datagrid('getSelected');
	            					if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					var index = $dgPersonnelPost.datagrid('getRowIndex', row);
				                	var ed = $dgPersonnelPost.datagrid('getEditor', {index:index,field:'belongdeptid'});
						   			var ep = $dgPersonnelPost.datagrid('getEditor', {index:index,field:'belongpostid'});
						   			if(ed == null || ep == null){
										return false;
									}
						            var belongdeptName = $(ed.target).widget("getText");
						            var belongpostName = $(ep.target).widget("getText");
						   			$dgPersonnelPost.datagrid('getRows')[index]['belongdeptName'] = belongdeptName;
						   			$dgPersonnelPost.datagrid('getRows')[index]['belongpostName'] = belongpostName;  
						   			personnel_editIndexPost = undefined;
						   			$dgPersonnelPost.datagrid('clearSelections');
						   			$dgPersonnelPost.datagrid('endEdit', index);
				                }
				            },{
				            	text : "修改",
				                iconCls : "button-edit",
				                handler : function() {
				                	var row = $dgPersonnelPost.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
						            var rowIndex = $dgPersonnelPost.datagrid('getRowIndex', row);
					                if(endEditingPost()){
					                	$("#personnel-index-postList").val(rowIndex);
										$dgPersonnelPost.datagrid('beginEdit', rowIndex);
										personnel_editIndexPost = rowIndex;
									}
				                }
				            },{
				            	text : "删除",
				                iconCls : "button-delete",
				                handler : function() {
				                	var row = $dgPersonnelPost.datagrid('getSelected');
				                	if (row == null) {
	            						$.messager.alert("提醒","请选择行!");
	            						return false;
	            					}
	            					if($("#personnel-hidden-hdPostListDelId").val() == ""){
	            						delPostIds = row.id;
	            					}
	            					else{
	            						delPostIds = $("#personnel-hidden-hdPostListDelId").val() + "," + row.id;
	            					}
	            					$("#personnel-hidden-hdPostListDelId").val(delPostIds);
						            var rowIndex = $dgPersonnelPost.datagrid('getRowIndex', row);
					                $dgPersonnelPost.datagrid('deleteRow', rowIndex);
					                personnel_editIndexPost = undefined;
					                $dgPersonnelPost.datagrid('clearSelections');
				                }
				            }],
			   				onClickRow:function(rowIndex, rowData){
			   					var index = parseInt($("#personnel-index-postList").val());
			   					var ed = $dgPersonnelPost.datagrid('getEditor', {index:index,field:'belongdeptid'});
					   			var ep = $dgPersonnelPost.datagrid('getEditor', {index:index,field:'belongpostid'});
					   			if(ed != null && ep != null){
									var belongdeptName = $(ed.target).widget("getText");
						            var belongpostName = $(ep.target).widget("getText");
						   			$dgPersonnelPost.datagrid('getRows')[index]['belongdeptName'] = belongdeptName;
						   			$dgPersonnelPost.datagrid('getRows')[index]['belongpostName'] = belongpostName;
						   			personnel_editIndexPost = undefined;
					   				$dgPersonnelPost.datagrid('endEdit', index);
					   				$dgPersonnelPost.datagrid('selectRow', rowIndex);
								}
			   				}
				   		});
				   		$dgPersonnelPost.addClass("create-datagrid");
	            	 }
	             }
			});
		}
		
		//教育经历提交
	  	function eduJson(){
			if($dgPersonnel!=null){
				var rows = $dgPersonnel.datagrid('getRows');
				if(rows.length != 0){
					for ( var i = 0; i < rows.length; i++) {
		                $dgPersonnel.datagrid('endEdit', i);
		            }
		            if ($dgPersonnel.datagrid('getChanges').length || $("#personnel-beginedu").val() == "1") {
		   				var effectRow = new Object();
		                effectRow["eduChange"] = JSON.stringify(rows);
		   			}
		   			return  effectRow;
		        }
	        }
	        return null;
	  	}
		//工作经历提交
		function workJson(){
			if($dgPersonnelWorks!=null){
				var rows = $dgPersonnelWorks.datagrid('getRows');
				if(rows.length != 0){
					for( var i = 0; i < rows.length; i++) {
		            	$dgPersonnelWorks.datagrid('endEdit', i);
		          	}
		          	if($dgPersonnelWorks.datagrid('getChanges').length || $("#personnel-beginwork").val() == "1") {
		 				var effectRow = new Object();
		            	effectRow["workChange"] = JSON.stringify(rows);
		            	return  effectRow;
		 			}
		      	}
	      	}
	      	return null;
		}
		//岗位变动提交
		function postJson(){
			if($dgPersonnelPost!=null){
				var rows = $dgPersonnelPost.datagrid('getRows');
				if(rows.length != 0){
					for( var i = 0; i < rows.length; i++) {
			        	$dgPersonnelPost.datagrid('endEdit', i);
			        }
			        if($dgPersonnelPost.datagrid('getChanges').length || $("#personnel-beginpost").val() == "1") {
			 			var effectRow = new Object();
			            effectRow["postChange"] = JSON.stringify(rows);
			            return  effectRow;
			 		}
		      	}
	      	}
	     	return null;
		}
		//对应客户提交
	  	function customerJson(){
			if($dgPersonnelCustomer!=null){
				var rows = $dgPersonnelCustomer.datagrid('getRows');
				if(rows.length != 0 && $("#personnel-beginCustomeredit").val() == "1"){
		            var effectRow = new Object();
	                effectRow["customerChange"] = JSON.stringify(rows);
	                return  effectRow;
		        }
	        }
	        return null;
	  	}
	  	//对应品牌提交
	  	function brandJson(){
			if($dgPersonnelBrand!=null){
				var rows = $dgPersonnelBrand.datagrid('getRows');
				if(rows.length != 0 && $("#personnel-beginBrandedit").val() == "1"){
		           var effectRow = new Object();
	               effectRow["brandChange"] = JSON.stringify(rows);
	               return  effectRow;
		        }
			}
	        return null;
	  	}
		//获取系统当前日期
		function getSystemDateNow(){
		   var myDate = new Date();
	       var year = myDate.getFullYear();
		   var month = myDate.getMonth()+1; //js从0开始取 
		   var date = myDate.getDate();
		   if(month<10){
		    month ="0"+month;
		   }
		   if(date<10){
		    date ="0"+date;
		   }
		   return year+"-"+month+"-"+date;
		}
		
		//计算年龄调用方法
		function getAgeForeign(value){
			var age = "";
			var idCardBirthday=value.substring(6,14);
	   		var birthday = idCardBirthday.substr(0,4)+"-"+idCardBirthday.substr(4,2)+"-"+idCardBirthday.substr(6,2);
			var sysDateNow= new Date(getSystemDateNow());
			if(sysDateNow.getFullYear() >  birthday.substr(0,4)){//系统日期年大于出生日期 
				age=sysDateNow.getFullYear()-birthday.substr(0,4);
				if(sysDateNow.getMonth() < birthday.substr(4,2)){
					age=age-1;
				}
				else if(sysDateNow.getMonth() == birthday.substr(4,2)){
					if(sysDateNow.getDate() < birthday.substr(6,2)){
						age=age-1;
					}
				}
			}
			$("#personnel-input-age").val(age);
		}
		
		//根据出生日期计算年龄
		function getAgeFunc(){
			//出生日期
			var birthday=new Date($dp.cal.getNewDateStr());
			//$("#personnel-hidden-hdBirthday").val($dp.cal.getNewDateStr());
			//系统当前时间比较  
			var sysDateNow= new Date(getSystemDateNow());
			var age=sysDateNow.getFullYear()-birthday.getFullYear();
			if(sysDateNow.getMonth() < birthday.getMonth()){
				age=age-1;
			}
			else if(sysDateNow.getMonth() == birthday.getMonth()){
				if(sysDateNow.getDate() < birthday.getDate()){
					age=age-1;
				}
			}
			$("#personnel-input-age").val(age);
		}
		
		//身份证验证 
		$.extend($.fn.validatebox.defaults.rules, {
			valididcard:{
				validator:function(value){
					if((/^\d{17}(\d|X|x)$/.test(value))){
						var ret = personnel_AjaxConn({id:value.substr(0,6)},'basefiles/getNPname.do');//获取籍贯
						var retJson=JSON.parse(ret);
						var sysDateNow= new Date(getSystemDateNow());
						if(value.substring(6,14).substr(0,4) < sysDateNow.getFullYear()){
							var idCardBirthday=value.substring(6,14);
							var birthday = idCardBirthday.substr(0,4)+"-"+idCardBirthday.substr(4,2)+"-"+idCardBirthday.substr(6,2);
							$("#personnel-input-birthday").val(birthday);
							getAgeForeign(value);
						}
						$("#personnel-input-NPlace").val(retJson.name);
						return true;
					}
					else{
						$.fn.validatebox.defaults.rules.valididcard.message = '身份证号码格式不正确!';
					}
				},
				message:''
			},
			isExistPerId:{
				validator:function(value,param){
					var reg = eval("/^[A-Za-z0-9]{1,20}$/i");
					if(reg.test(value)){
						var ret=personnel_AjaxConn({id:value},'basefiles/isExistPersonnelId.do');
						var json = $.parseJSON(ret);
						if(json.flag){
							$.fn.validatebox.defaults.rules.isExistPerId.message = '编号已使用,请重新输入!';
							return false;
						}else{
							return true;
						}
					}else{
						$.fn.validatebox.defaults.rules.isExistPerId.message = '格式错误!';
					}
				},
				message:''
			},
			validBirthday:{
				validator:function(value){
					if($("#personnel-input-idCard").val() != ""){
						var idCardBirthday=$("#personnel-input-idCard").val().substring(6,14);
						var birthday = idCardBirthday.substr(0,4)+"-"+idCardBirthday.substr(4,2)+"-"+idCardBirthday.substr(6,2);
						if(value != birthday){
							$.fn.validatebox.defaults.rules.validBirthday.message = '与身份证出生日期不对应!';
							return false
						}
						return true;
					}
					else{
						return true;
					}
				},
				message:''
			},
			remark:{
				validator : function(value,param) { 
	         		return value.length <= param[0]; 
	     		}, 
	     		message : '最多可输入200个字符!'
			},
			compcornet:{
				validator : function(value,param) {
					var mes ="";
					if(value.length <= param[0]){
						if(/^\d+$/.test(value)){
							return true;
						}
						else{
							$.fn.validatebox.defaults.rules.compcornet.message = '只允许输入数字!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.compcornet.message = '请输入6个数字!';
						return false;
					}
		     	}, 
		    	message : ''
			}
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
		
		//页面刷新
	   	function panelRefresh(url,title,type){
	   		$("#basefiles-pannel-personnel").panel({
				href:url,
				title:title,
			    cache:false,
			    maximized:true,
			    border:false,
			    loadingMessage:'数据加载中...'
			});
			$("#personnel-opera").attr("value",type);
	   	}
	   	
	   	//回复初值
	   	function retBackIniv(){
	   		$("#personnel-personcustomer").val("");
   			$("#personnel-beforeDelCustomerAdd").val("");
   			//对应客户是否修改状态至为0，未修改
   			$("#personnel-beginCustomeredit").val("0");
   			//对应品牌是否修改状态至为0，未修改
   			$("#personnel-beginBrandedit").val("0");
   			$("#personnel-beginedu").val("0");
   			$("#personnel-beginpost").val("0");
   			$("#personnel-beginwork").val("0");
   			$("#personnel-beginedit").val("0");
	   	}
	   	
	   	function setDatagridIdNull(){
	   		$dgPersonnel = null;
	   		$dgPersonnelWorks = null;
	   		$dgPersonnelPost = null;
	   		$dgPersonnelCustomer = null;
	   		$dgPersonnelBrand = null;
	   	}
	   	
	   	$(function(){
	   		//加载按钮
	   		$("#basefiles-button-personnel").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/showPersonnelAddPage.do">
						{
							type:'button-add',//新增
							handler:function(){
								setDatagridIdNull();
								panelRefresh('basefiles/showPersonnelAddPage.do','人员档案【新增】','add');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/showPersonnelEditPage.do">
			 			{
				 			type:'button-edit',//修改
				 			handler:function(){
				 				var flag = isDoLockData($("#basefiles-id-personnel").val(),"t_base_personnel");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				setDatagridIdNull();
				 				var id = $("#basefiles-id-personnel").val();
				 				panelRefresh('basefiles/showPersonnelEditPage.do?id='+id,'人员档案【修改】','edit');
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelHoldPage.do">
			 			{
			 				type:'button-hold',//暂存
			 				handler:function(){
				 				var type = $("#personnel-opera").val();
				 				if(!$("#basefiles-id-personnel").validatebox('isValid')){
				    				return false;
				    			}
				    			$.messager.confirm("提醒","是否暂存人员档案?",function(r){
			 						if(r){
			 							if(type=="add"){
			 								addPersonnel("hold");//暂存新增人员档案
			 							}
			 							else if(type=="edit"){
			 								editPersonnel("hold");//暂存修改人员档案
			 							}
			 						}
			 					});
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/showPersonnelSavePage.do">
			 			{
				 			type:'button-save',//保存
				 			handler:function(){
				 				var type = $("#personnel-opera").val();
				 				if(!personnelFormValidate()){
				    				return false;
				    			}
			 					$.messager.confirm("提醒","是否保存人员档案?",function(r){
			 						if(r){
			 							if(type=="add" || type=="copy"){
			 								addPersonnel("save");//保存新增人员档案
			 							}
			 							else if(type=="edit"){
			 								editPersonnel("save");//保存修改人员档案
			 							}
			 						}
			 					});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelDeletePage.do">
			 			{
				 			type:'button-delete',//删除
				 			handler:function(){
				 				var flag = isLockData($("#personnel-id").val(),"t_base_personnel");
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
				 					return false;
				 				}
				 				$.messager.confirm("提醒","是否确定删除人员档案?",function(r){
				 					if(r){
										var id = $("#basefiles-id-personnel").val();
										loading("删除中..");
										$.ajax({   
								            url :'basefiles/deletePersonnelInfo.do',
								            type:'post',
								            dataType:'json',
								            data:{ids:id},
								            success:function(retJson){
								            	loaded();
												var msg = retJson.userNum+"条记录被引用,不允许删除;<br/>"+retJson.lockNum+"条记录网络互斥,不允许删除;";
												if(retJson.msg != ""){
													msg += "<br>" + retJson.msg;
												}
												$.messager.alert("提醒",msg);
												if(retJson.retflag){
													if (top.$('#tt').tabs('exists',person_title)){
														tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid("reload");
													}
													var object = $("#basefiles-button-personnel").buttonWidget("removeData",id);
													if(null != object){
														panelRefresh('basefiles/showPersonnelInfoPage.do?id='+object.id,' 人员档案【查看】','view');
													}
													else{
														top.closeTab('人员档案');
													}
												}
								            }
								        });
									}
				 				});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelCopyPage.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				setDatagridIdNull();
			 				var id = $("#basefiles-id-personnel").val();
			 				panelRefresh('basefiles/showPersonnelCopyPage.do?id='+id,' 人员档案【复制】','add');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelEnablePage.do">
			 			{
				 			type:'button-open',//启用
				 			handler:function(){
								$.messager.confirm('提醒','是否确定启用人员档案?',function(r){
									if(r){
										var id = $("#basefiles-id-personnel").val();
										loading("启用中..");
										$.ajax({   
								            url :'basefiles/enablePersonnels.do',
								            type:'post',
								            dataType:'json',
								            data:{ids:id},
								            success:function(retJson){
								            	loaded();
								            	$.messager.alert("提醒",""+retJson.unSucMes+"<br />"+retJson.sucMes+"<br />"+retJson.failMes+"");
								                if (top.$('#tt').tabs('exists',person_title)){
									    			tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid("load",{});
									    		}
							                    panelRefresh('basefiles/showPersonnelInfoPage.do?id='+id,' 人员档案【查看】','view');
								            }
								        });
									}
								});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelDisablePage.do">
			 			{
				 			type:'button-close',//禁用
				 			handler:function(){
								var id = $("#basefiles-id-personnel").val();
								//人员禁用时，提示是否同时禁用系统用户
								$.messager.confirm("提醒","是否同时禁用系统用户?",function(r){
									if(r){
										loading("禁用中..");
										$.ajax({   
								            url :'basefiles/disablePersonnels.do?type=tonbu',
								            type:'post',
								            dataType:'json',
								            data:{ids:id},
								            success:function(retJson){
								            	loaded();
								            	if(retJson.retFlag){
									            	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"");
													if (top.$('#tt').tabs('exists',person_title)){
										    			tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid("reload");
										    		}
													panelRefresh('basefiles/showPersonnelInfoPage.do?id='+id,' 人员档案【查看】','view');
									            }else{$.messager.alert("提醒","人员与系统用户禁用失败!");}
								            }
								        });
									}
									else{
										loading("禁用中..");
										$.ajax({   
								            url :'basefiles/disablePersonnels.do?type=yibu',
								            type:'post',
								            dataType:'json',
								            data:{ids:id},
								            success:function(retJson){
								            	loaded();
								            	if(retJson.retFlag){
									            	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"");
									            	if (top.$('#tt').tabs('exists',person_title)){
														tabsWindowURL('/basefiles/showPersonnelListPage.do').$("#personnel-table-personnelList").datagrid('reload');
													}
													panelRefresh('basefiles/showPersonnelInfoPage.do?id='+id,' 人员档案【查看】','view');
									            }else{$.messager.alert("提醒","人员禁用失败!");}
								            }
								        });
									}
								});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelPrePage.do">
			 			{
				 			type:'button-back',//上一条
				 			handler:function(data){
				 				setDatagridIdNull();
				 				panelRefresh('basefiles/showPersonnelInfoPage.do?id='+data.id,' 人员档案【查看】','view');
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelNextPage.do">
			 			{
				 			type:'button-next',//下一条
				 			handler:function(data){
				 				setDatagridIdNull();
				 				panelRefresh('basefiles/showPersonnelInfoPage.do?id='+data.id,' 人员档案【查看】','view');
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelPrintViewPage.do">
			 			{
				 			type:'button-preview',//打印预览
				 			handler:function(){
				 				return false;
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelPrintPage.do">
			 			{
				 			type:'button-print',//打印
				 			handler:function(){
				 				return false;
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelGiveUpPage.do">
			 			{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#personnel-opera").val();
				 				if(type=="add"){
				 					var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
		   							top.closeTab(currTitle);
				 				}else if(type=="edit"){
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:$("#personnel-id").val(),tname:'t_base_personnel'},
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
					 				panelRefresh('basefiles/showPersonnelInfoPage.do?id='+$("#basefiles-id-personnel").val(),' 人员档案【查看】','view');
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				buttons:[
					{},
					<security:authorize url="/basefiles/showPersonnelFilePage.do">
			 			{
			 				id:'personnel-upload-file',
							name:'附件',
							iconCls:'button-file',
							handler:function(){
								var personid = $("#basefiles-id-personnel").val();
								var type = $("#personnel-type-upload").val();
								$('<div id="personnel-dialog-file-upload1"></div>').appendTo('#personnel-dialog-file-upload');
								$('#personnel-dialog-file-upload1').dialog({
								    title: '附件上传',  
								    width: 620,
								    height: 400,
								    closed: false,
								    cache: false,  
								    href: "common/showWebuploaderPage.do",
								    queryParams:{isimgtype:'0',personid:personid,uploadmode:'person',opeatype:type},
								    modal: true,
								    onClose:function(){
								    	$('#personnel-dialog-file-upload1').dialog("destroy");
								    }
								});
								$("#personnel-dialog-file-upload1").dialog("open");
							}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/showPersonnelDistributePage.do">
						{
							id:'allotSysUserBtn',
							name:'系统用户分配',
							iconCls:'assign-user',
							handler:function(){
								$.messager.confirm("提醒","是否分配系统用户?",function(r){
									if(r){
										var id = $("#basefiles-id-personnel").val();
										loading("分配中..");
										$.ajax({   
								            url :'accesscontrol/allotSystemUser.do',
								            type:'post',
								            dataType:'json',
								            data:{persIDsStr:id},
								            success:function(retJson){
								            	loaded();
								            	$.messager.alert("提醒",""+retJson.failNum+"个人员不为启用状态,分配无效;<br/>"+retJson.existNum+"个人员已分配;<br/>"+retJson.sucNum+"个人员分配成功;<br/>"+retJson.unSucNum+"个人员分配失败;");
								            }
								        });
									}
								});
							}
						},
					</security:authorize>
					{}
				],
				model:'base',
				type:'view',
				taburl:'/basefiles/showPersonnelListPage.do',
				datagrid:'personnel-table-personnelList',
				tname:'t_base_personnel',
				id:$("#personnel-id").val()
			});
			
			//加载新增商品页面
			var personnel_url = "";
			var personnel_opera = "add";
			var personnel_title = "人员档案【新增】";
			if("${type}" == "view"){
				personnel_url = "basefiles/showPersonnelInfoPage.do?id="+$("#personnel-id").val();
				personnel_title = "人员档案【查看】";
				personnel_opera = "view";
			}
			else if("${type}" == "edit"){
				personnel_url = "basefiles/showPersonnelEditPage.do?id="+$("#personnel-id").val();
				personnel_title = "人员档案【修改】";
				personnel_opera = "edit";
			}
			else if("${type}" == "copy"){
				personnel_url = "basefiles/showPersonnelCopyPage.do?id="+$("#personnel-id").val();
				personnel_title = "人员档案【复制】";
				personnel_opera = "copy";
			}
			else{
				personnel_url = "basefiles/showPersonnelAddPage.do?belongdeptid="+$("#personnel-belongdeptid").val();
			}
			$("#basefiles-pannel-personnel").panel({
			    href:personnel_url,
				title:personnel_title,
			    cache:false,
			    maximized:true,
			    border:false,
			    close:true
			});
			$("#personnel-opera").attr("value",personnel_opera);
			$("#basefiles-button-personnel").buttonWidget("initButtonType",personnel_opera);
			$("#basefiles-button-personnel").buttonWidget("setButtonType","${state}");	
	   	});
	   	
	   	function importExcelForm(){
	   		return {
				onSubmit: function(){ 
					var file = $("input[name=excelFile]").val();
					var ext = file.substring(file.lastIndexOf('.'));
					if(ext != ".xls" && ext != ".xlsx"){
						$.messager.alert("提醒", "请上传Excel文件");
						return false;
					}
			    	loading("导入中..");
			    },  
			    success:function(data){
			    	var retjson = $.parseJSON(data);
			    	loaded();
			    	if(retjson.msg != ""){
			    		$.messager.alert("提醒",retjson.msg);
			    	}
			    	$dgPersonnelCustomer.datagrid("reload");
			    	$("#excel-import-dialog").dialog('close', true);
			    }  
			};
	   	}
	   	
	   	function personCustomerImport(id){
	   		if(import_disabled){
				return false;
			}
			if($("#excel-import-dialog").length < 1){
				$("body").append("<div id='excel-import-dialog'></div>");
			}
			$("#excel-import-dialog").dialog({
				href: 'common/importPage.do?version=1',
				width: 400,
				height: 300,
				title: '对应客户导入',
				colsed: false,
				cache: false,
				modal: true,
				onLoad: function(){
					var custoemrids = $("#personnel-personcustomer").val();
					var brandids = $("#personnel-addBrandid").val();
					var employetype = $("#personnel-employetype").val();
					var personid = $("#basefiles-id-personnel").val();
					$("<input type='hidden' name='personid' value='"+personid+"' />").appendTo("#common_form_importExcel");
					$("<input type='hidden' name='customerids' value='"+custoemrids+"' />").appendTo("#common_form_importExcel");
					$("<input type='hidden' name='brandids' value='"+brandids+"' />").appendTo("#common_form_importExcel");
					$("<input type='hidden' name='employetype' value='"+employetype+"' />").appendTo("#common_form_importExcel");
					$("#common_form_importExcel").removeAttr("action").attr("action","basefiles/importPersonBrandCustomerData.do?id="+id);
					$("#common-div-importparam").html("客户编码或助记符必填一个，且判断该人员是否品牌业务员或厂家业务员");

					$("#common_form_importExcel").form(importExcelForm());
				},
				onClose: function(){
					var queryJSON = {};
		    		queryJSON['customerids'] = $("#personnel-personcustomer").val();
		    		$dgPersonnelCustomer.datagrid('options').url = 'basefiles/showCustomerListData.do';
	    			$dgPersonnelCustomer.datagrid('load',queryJSON);
				}
			});
	   	}
 	</script>
  </body>
</html>
