<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用分类页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="expensesSort-opera"/>
    <input type="hidden" id="finance-thisId-expensesSort" />
  	<input type="hidden" id="finance-parentId-expensesSort" />
  	<input type="hidden" id="finance-isParent-expensesSort" />
  	<input type="hidden" id="finance-state-expensesSort" />
  	<input type="hidden" id="finance-level-expensesSort" />
  	<input type="hidden" id="finance-hasLevel-expensesSort" value="${len }" />
  	<input type="hidden" id="finance-leaveLen-expensesSort" value="${lenStr }" />
    <div class="easyui-layout" title="费用分类" data-options="fit:true" id="finance-layout-expensesSort">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="finance-buttons-expensesSort"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="费用分类" style="width:160px;">
    		<div id="finance-Tree-expensesSort" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title=""></div>
    </div>
    <script type="text/javascript">
    	var expensesSort_lenArr = $("#finance-leaveLen-expensesSort").val().split(',');

    	function refreshLayout(title, url){
    		$("#finance-layout-expensesSort").layout('remove','center').layout('add',{
				region: 'center',
			    title: title,
			    href:url
			});
    	}
    	var expensesSort_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}

		//加载下拉框
		function loaddropdown(){
			//对应会计科目
			$("#finance-accountingsubject-expensesSortAddPage").widget({
    			name:'t_base_finance_expenses_sort',
    			col:'accountingsubject',
    			singleSelect:true,
    			onlyLeafCheck:true
    		});

    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',
			    valueField:'id',
			    textField:'name'
			});
		}

    	function validLength(len){ //只验证长度
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
			    		return reg.test(value);
			    	},
			    	message:'请输入'+len+'位字符!'
			    }
			});
    	}

    	//最大长度
    	function maxLen(){ //只验证长度
    		var ch = 0;
    		$.extend($.fn.validatebox.defaults.rules, {
				maxLen:{
			    	validator:function(value,param){
			    		ch = parseInt(param[0]/2);
			    		if(value.length <= param[0]){
			    			return true;
			    		}
			    		else{
			    			$.fn.validatebox.defaults.rules.maxLen.message = "最多只可输入{0}位字符或"+ch+"位汉字!";
			    			return false;
			    		}
			    		return ;
			    	},
			    	message:''
			    }
			});
    	}
    	//验证长度且验证重复
    	function validLengthAndUsed(len, url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
	  					if(reg.test(value) == true){
	  						if(value == initValue){
	  							return true;
	  						}
				    		var data=expensesSort_ajaxContent({id:(id+value)},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLength.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLength.message ='请输入'+len+'位字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}
    	function validUsed(url, name,initValue, message){
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value,param){
			    		if(value == initValue){
  							return true;
  						}
  						var retName = value;
  						if(name != ""){
  							retName = name + '/' + value;
  						}
  						if(value.length <= param[0]){
  							var data=expensesSort_ajaxContent({name: retName},url);
		  					var json = $.parseJSON(data);
		    				if(json.flag == true){
			   					$.fn.validatebox.defaults.rules.validUsed.message = message;
			    				return false;
			    			}else{
		    					return true;
			    			}
  						}
  						else{
  							$.fn.validatebox.defaults.rules.validUsed.message = '输入长度过长,请输入{0}个字符!';
  							return false;
  						}
			    	},
			    	message:''
			    }
			});
    	}
    	function zTreeBeforeClick(treeId, treeNode){
    		$("#finance-thisId-expensesSort").val(treeNode.id);
			$("#finance-parentId-expensesSort").val(treeNode.parentid);
			$("#finance-state-expensesSort").val(treeNode.state);
			$("#finance-level-expensesSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#finance-isParent-expensesSort").val("1")
			} else {
				$("#finance-isParent-expensesSort").val("0")
			}
			return true;
    	}
    	$(function(){
    		//树型
			var expensesSortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/finance/getExpensesSortTree.do",
					autoParam: ["id","parentid", "text","state"]
				},
				data: {
					key:{
						title:"text",
						name:"text"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							refreshLayout("费用分类【新增】",'basefiles/finance/expensesSortAddPage.do');
							$("#expensesSort-opera").val("add");
						}
						else{
							refreshLayout("费用分类【详情】", 'basefiles/finance/expensesSortViewPage.do?id='+ treeNode.id);
							$("#expensesSort-opera").val("view");
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
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
			$.fn.zTree.init($("#finance-Tree-expensesSort"), expensesSortTreeSetting,null);
			//按钮
			$("#finance-buttons-expensesSort").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/finance/expensesSortAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#finance-thisId-expensesSort").val();
								var hasLevel = $("#finance-hasLevel-expensesSort").val();
								var level = $("#finance-level-expensesSort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("费用分类【新增】", 'basefiles/finance/expensesSortAddPage.do?id='+ thisId);
								$("#expensesSort-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortEditBtn.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#finance-thisId-expensesSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("费用分类【修改】", "basefiles/finance/expensesSortEditPage.do?id="+ id);
								$("#expensesSort-opera").val("edit");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortHoldBtn.do">
						{
							type: 'button-hold',
							handler: function(){
								var type = $("#expensesSort-opera").val();
								$.messager.confirm("提醒","确定暂存该费用分类？",function(r){
									if(r){
										if(type == "add"){
											addExpensesSort("hold");
										}
										else{
											editExpensesSort("hold");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortSaveBtn.do">
						{
							type: "button-save",
							handler: function(){
								var type = $("#expensesSort-opera").val();
								$.messager.confirm("提醒","确定保存该费用分类？",function(r){
									if(r){
										if(type == "add"){
											addExpensesSort("save");
										}
										else{
											editExpensesSort("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortGiveUpBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#expensesSort-opera").val();
				 				if(type=="add"){
				 					$("#finance-buttons-expensesSort").buttonWidget("initButtonType","list");
				 					$("#finance-layout-expensesSort").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#finance-thisId-expensesSort").val();
				 					var state = $("#finance-state-expensesSort").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_finance_expenses_sort'},
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
					 				refreshLayout("费用分类【详情】","basefiles/finance/expensesSortViewPage.do?id="+ id);
									$("#expensesSort-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#finance-thisId-expensesSort").val();
								var isParent = $("#finance-isParent-expensesSort").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该费用分类信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/finance/deleteExpensesSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.delFlag==true){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","删除成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#finance-parentId-expensesSort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("finance-Tree-expensesSort", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("费用分类【详情】","basefiles/finance/expensesSortViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("费用分类【新增】",'basefiles/finance/expensesSortAddPage.do?id='+ pid);
                                                    }
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","删除失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/waresClassCopyBtn.do">
						{
							type:'button-copy',//复制
							handler:function(){
								var thisId = $("#finance-thisId-expensesSort").val();
								refreshLayout("费用分类【新增】", 'basefiles/finance/expensesSortCopyPage.do?id='+ thisId);
								$("#expensesSort-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortOpenBtn.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#finance-thisId-expensesSort").val();
								var pid = $("#finance-parentId-expensesSort").val();
								var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该费用分类?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/finance/enableExpensesSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = "1";
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#finance-parentId-expensesSort").val();
								  		        	$("#finance-state-expensesSort").val("1")
								  		        	refreshLayout("费用分类【详情】","basefiles/finance/expensesSortViewPage.do?id="+ id);
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","启用失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortCloseBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#finance-thisId-expensesSort").val();
								$.messager.confirm("提醒","禁用该费用分类，将禁用该节点下所有费用分类，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/finance/disableExpensesSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = "0";
													treeObj.updateNode(node); //更新子节点
													$("#finance-state-expensesSort").val("0")
												}
								  		        refreshLayout("费用分类【详情】","basefiles/finance/expensesSortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortImportBtn.do">
						{
                            type:'button-import',//导入
                            attr:{
                                clazz: "financeService",
                                method: "addDRExpensesSortExcel",
                                tn: "t_base_finance_expenses_sort",
                                module: 'basefiles',
                                pojo: "ExpensesSort"
                            }
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/expensesSortExportBtn.do">
						{
                            type:'button-export',//导出
                            attr:{
                                tn: 't_base_finance_expenses_sort',//表名
                                name:'费用分类列表'
                            }
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_finance_expenses_sort'
			});
    	});

    </script>
  </body>
</html>
