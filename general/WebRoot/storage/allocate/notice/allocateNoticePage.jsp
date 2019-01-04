<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨通知单单操作页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
  </head>
  <body>
    <div id="storage-layout-allocateNoticePage" class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-allocateNoticePage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="storage-panel-allocateNoticePage"></div>
    	</div>
    </div>
    <div id="storage-panel-relation-upper"></div>
    <div id="storage-panel-sourceQueryPage"></div>
    <div id="workflow-addidea-dialog-page"></div>
    <input type="hidden" id="storage-hidden-billid"/>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
		var tableColJson = $("#storage-datagrid-allocateNoticeAddPage").createGridColumnLoad({
			frozenCol : [[]],
			commonCol : [[ 
							{field:'goodsid',title:'商品编码',width:60},
	    					{field:'goodsname', title:'商品名称', width:220,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.name;
						       		}else{
						       			return "";
					       			}
						        }
	    					},
	    					{field:'barcode', title:'条形码',width:85,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.barcode;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'itemno', title:'商品货位',width:60,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.itemno;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'brandName', title:'商品品牌',width:80,aliascol:'goodsid',hidden:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.brandName;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'model', title:'规格型号',width:80,aliascol:'goodsid',hidden:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.model;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'usablenum', title:'可用量',width:60,align:'right',isShow:true,
	    						formatter:function(value,row,index){
					        		return formatterBigNumNoLen(value);
						        }
					    	},
	    					{field:'unitname', title:'单位',width:35},
	    					{field:'unitnum', title:'数量',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterBigNumNoLen(value);
						        },
						        styler:function(value,row,index){
						        	var status = $("#storage-allocateNotice-status-select").val();
						        	if((status==null ||status=='1' || status=="2") && Number(row.usablenum)<Number(value)){
						        		return 'background-color:red;';
						        	}
						        }
					    	},
				<c:if test="${isAllocateShowBilltype=='1'}">
				            {field:'costprice', title:'成本价',width:60,align:'right',isShow:false,
								formatter:function(value,row,index){
									return formatterMoney(value);
								}
							},
				</c:if>
	    					{field:'taxprice', title:'调拨单价',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
                            {field:'boxprice', title:'调拨箱价',width:60,align:'right',
                                formatter:function(value,row,index){
                                    return formatterMoney(value);
                                }
                            },
	    					{field:'taxamount', title:'调拨金额',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
                            /*
						    {field:'notaxprice', title:'未税单价',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'notaxamount', title:'未税金额',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',align:'right',hidden:true},*/
						    /*{field:'tax', title:'税额',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },*/
						    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
						    {field:'batchno',title:'出库批次号',width:80},
							{field:'produceddate',title:'出库生产日期',width:80},
					        {field:'deadline',title:'出库截止日期',width:80,hidden:true},
                            {field:'storagelocationid', title:'出库库位',width:100,hidden:true,
                                formatter:function(value,row,index){
                                    return row.storagelocationname;
                                }
                            },
                            {field:'enterbatchno',title:'入库批次号',width:80},
                            {field:'enterproduceddate',title:'入库生产日期',width:80},
                            {field:'enterdeadline',title:'入库截止日期',width:80,hidden:true},
                            {field:'enterstoragelocationid', title:'入库库位',width:100,hidden:true,
                                formatter:function(value,row,index){
                                    return row.enterstoragelocationname;
                                }
                            },
						    {field:'remark', title:'备注',width:100}
						]]
		});
    	var page_url = "storage/allocateNoticeAddPage.do";
    	var page_type = '${type}';
    	if(page_type == "view" || page_type=="handle"){
    		page_url = "storage/allocateNoticeViewPage.do?id=${id}";
    	}else if(page_type == "edit"){
    		page_url = "storage/allocateNoticeEditPage.do?id=${id}";
    	}
    	$(function(){
    		$("#storage-panel-allocateNoticePage").panel({
				href:page_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
			$("#storage-buttons-allocateNoticePage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/allocateNoticeAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							$("#storage-panel-allocateNoticePage").panel({
								href:'storage/allocateNoticeAddPage.do',
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/addallocateNoticeHold.do">
					{
						type: 'button-hold',
						handler: function(){
							var type = $("#storage-buttons-allocateNoticePage").buttonWidget("getOperType");
			 				if(type=="add"){
			 					//暂存
			 					$("#storage-form-allocateNoticeAdd").attr("action", "storage/addAllocateNoticeHold.do");
			 					$("#storage-form-allocateNoticeAdd").submit();
			 				}else if(type=="edit"){
			 					//暂存
			 					$("#storage-form-allocateNoticeAdd").attr("action", "storage/editAllocateNoticeHold.do");
			 					$("#storage-form-allocateNoticeAdd").submit();
			 				}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/addallocateNoticeSave.do">
					{
						type: 'button-save',
						handler: function(){
							var billtype=$("#storage-allocateNotice-billtype").val();
							var type = $("#storage-buttons-allocateNoticePage").buttonWidget("getOperType");
							var outisaloneaccount=$("#storage-allocateNotice-outisaloneaccount").val();
							var enterisaloneaccount=$("#storage-allocateNotice-enterisaloneaccount").val();
							if(billtype=='2'&&outisaloneaccount=='0'&&enterisaloneaccount=='0'){
								$.messager.confirm("提醒", "异价调拨类型单据调出仓库和调入仓库中没有独立核算仓库，是否继续？", function (s) {
									if (s) {
										if(type=="add"){
											//暂存
											$("#storage-form-allocateNoticeAdd").attr("action", "storage/addAllocateNoticeSave.do");
											$("#storage-form-allocateNoticeAdd").submit();
										}else if(type=="edit"){
											$("#storage-form-allocateNoticeAdd").attr("action", "storage/editAllocateNoticeSave.do");
											$("#storage-form-allocateNoticeAdd").submit();
										}
									}
								})
							}else{
								if(type=="add"){
									//暂存
									$("#storage-form-allocateNoticeAdd").attr("action", "storage/addAllocateNoticeSave.do");
									$("#storage-form-allocateNoticeAdd").submit();
								}else if(type=="edit"){
									$("#storage-form-allocateNoticeAdd").attr("action", "storage/editAllocateNoticeSave.do");
									$("#storage-form-allocateNoticeAdd").submit();
								}
							}

						}
					},
					</security:authorize>
					<security:authorize url="/storage/addallocateNoticeSaveAudit.do">
					{
						type: 'button-saveaudit',
						handler: function(){
							$.messager.confirm("提醒","确定保存并审核该调拨通知单信息？",function(r){
								if(r){
									var billtype=$("#storage-allocateNotice-billtype").val();
									$("#storage-allocateNotice-saveaudit").val("saveaudit");
									var type = $("#storage-buttons-allocateNoticePage").buttonWidget("getOperType");
									var outisaloneaccount=$("#storage-allocateNotice-outisaloneaccount").val();
									var enterisaloneaccount=$("#storage-allocateNotice-enterisaloneaccount").val();
									if(billtype=='2'&&outisaloneaccount=='0'&&enterisaloneaccount=='0'){
										$.messager.confirm("提醒", "异价调拨类型单据调出仓库和调入仓库中没有独立核算仓库，是否继续？", function (s) {
											if (s) {
												if(type=="add"){
													//暂存
													$("#storage-form-allocateNoticeAdd").attr("action", "storage/addAllocateNoticeSave.do");
													$("#storage-form-allocateNoticeAdd").submit();
												}else if(type=="edit"){
													$("#storage-form-allocateNoticeAdd").attr("action", "storage/editAllocateNoticeSave.do");
													$("#storage-form-allocateNoticeAdd").submit();
												}
											}
										})
									}else{
										if(type=="add"){
											//暂存
											$("#storage-form-allocateNoticeAdd").attr("action", "storage/addAllocateNoticeSave.do");
											$("#storage-form-allocateNoticeAdd").submit();
										}else if(type=="edit"){
											$("#storage-form-allocateNoticeAdd").attr("action", "storage/editAllocateNoticeSave.do");
											$("#storage-form-allocateNoticeAdd").submit();
										}
									}
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeGiveUp.do">
					{
						type:'button-giveup',
						handler:function(){
							var type = $("#storage-buttons-allocateNoticePage").buttonWidget("getOperType");
    						if(type == "add"){
    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
    							top.$('#tt').tabs('close',currTitle);
    						}
    						else if(type == "edit"){
	    						var id = $("#storage-hidden-billid").val();
	    						if(id == ""){
	    							return false;
	    						}
	    						$("#storage-panel-allocateNoticePage").panel({
									href:'storage/allocateNoticeViewPage.do?id='+id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
    						}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/deleteAllocateNotice.do">
					{
						type: 'button-delete',
						handler: function(){
							$.messager.confirm("提醒","是否删除当前调拨通知单？",function(r){
								if(r){
									var id = $("#storage-hidden-billid").val();
									if(id!=""){
										loading("删除中..");
										$.ajax({   
								            url :'storage/deleteAllocateNotice.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		var object = $("#storage-buttons-allocateNoticePage").buttonWidget("removeData",id);
								            		$.messager.alert("提醒", "删除成功");
								            		if(null!=object){
									            		$("#storage-panel-allocateNoticePage").panel({
															href:'storage/allocateNoticeEditPage.do?id='+object.id,
															title:'',
														    cache:false,
														    maximized:true,
														    border:false
														});
													}else{
														parent.closeNowTab();
													}
								            	}else{
								            		$.messager.alert("提醒", "删除失败");
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误", "删除出错")
								            }
								        });
									}
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/auditAllocateNotice.do">
					{
						type: 'button-audit',
						handler: function(){
							$.messager.confirm("提醒","是否审核调拨通知单？",function(r){
								if(r){
									var id = $("#storage-hidden-billid").val();
									if(id!=""){
										loading("审核中..");
										$.ajax({   
								            url :'storage/auditAllocateNotice.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒","审核成功,生成调拨出库单："+json.downid);
								            		$("#storage-allocateNotice-status-select").val("3");
//			    									$("#storage-buttons-allocateNoticePage").buttonWidget("setDataID",{id:id,state:'3',type:'view'});
													$("#storage-panel-allocateNoticePage").panel({
														href:'storage/allocateNoticeViewPage.do?id='+id,
														title:'',
														cache:false,
														maximized:true,
														border:false
													});
								            	}else{
								            		$.messager.alert("提醒","审核失败</br>"+json.msg);
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","审核出错");
								            }
								        });
									}
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/oppauditAllocateNotice.do">
					{
			 			type:'button-oppaudit',
			 			handler:function(){
                            var businessdate = $("#storage-allocateNotice-businessdate").val();
                            var flag = isDoneOppauditBillCaseAccounting(businessdate);
                            if(!flag){
                                $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                return false;
                            }
			 				$.messager.confirm("提醒","是否反审调拨通知单？",function(r){
								if(r){
									var id = $("#storage-hidden-billid").val();
									if(id!=""){
										loading("反审中..");
										$.ajax({   
								            url :'storage/oppauditAllocateNotice.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
													$.messager.alert("提醒","反审成功");
													$("#storage-panel-allocateNoticePage").panel({
														href:'storage/allocateNoticeEditPage.do?id='+id,
														title:'',
													    cache:false,
													    maximized:true,
													    border:false
													});
								            	}else{
								            		$.messager.alert("提醒","反审失败."+json.msg);
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","反审失败");
								            }
								        });
									}
								}
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/storage/allocateNoticeViewBackPage.do">
					{
						type: 'button-back',
						handler: function(data){
							$("#storage-panel-allocateNoticePage").panel({
								href:'storage/allocateNoticeEditPage.do?id='+data.id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticeViewNextPage.do">
					{
						type: 'button-next',
						handler: function(data){
							$("#storage-panel-allocateNoticePage").panel({
								href:'storage/allocateNoticeEditPage.do?id='+data.id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticePreviewPage.do">
					{
						type: 'button-preview',
						handler: function(){
							
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateNoticePrintPage.do">
					{
						type: 'button-print',
						handler: function(){
						
						}
					},
					</security:authorize>
					{}
				],
				layoutid:'storage-layout-allocateNoticePage',
				model: 'bill',
				type: 'view',
				tab:'调拨通知单列表',
				taburl:'/storage/showAllocateNoticeListPage.do',
				id:'${id}',
				datagrid:'storage-datagrid-allocateNoticePage'
			});
    	});
		//显示调拨通知单明细添加页面
    	function beginAddDetail(){
    		//验证表单
			var flag = $("#storage-form-allocateNoticeAdd").form('validate');
			if(flag==false){
				$.messager.alert("提醒",'请先完善调拨通知单基本信息');
				return false;
			}
			var outstorageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
			$('<div id="storage-dialog-allocateNoticeAddPage-content"></div>').appendTo('#storage-dialog-allocateNoticeAddPage');
    		$('#storage-dialog-allocateNoticeAddPage-content').dialog({  
			    title: '调拨通知单明细添加',  
			    width: 680,  
			    height: 400,
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    modal: true,
			    href: 'storage/showAllocateNoticeDetailAddPage.do?outstorageid='+outstorageid,  
			    onLoad:function(){
			    	$("#storage-allocateNotice-goodsid").focus();
			    },
			    onClose:function(){
			    	$('#storage-dialog-allocateNoticeAddPage-content').dialog("destroy");
			    }
			});
			$('#storage-dialog-allocateNoticeAddPage-content').dialog("open");
    	}
        //批量添加商品
        function beginAddDetailByBrandAndSort() { //开始批量添加商品信息
            //验证表单
            var flag = $("#storage-form-allocateNoticeAdd").form('validate');
            if(flag==false){
                $.messager.alert("提醒",'请先完善调拨通知单基本信息');
                return false;
            }
            var outstorageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
            $('<div id="storage-dialog-allocateNoticeAddPage-content"></div>').appendTo('#storage-dialog-allocateNoticeAddPage');
            $('#storage-dialog-allocateNoticeAddPage-content').dialog({
                title: '批量添加商品信息(按ESC退出)',
                maximizable: true,
                width: 1000,
                height: 600,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: 'storage/showAllocateNoticeDetailAddByBrandAndSortPage.do?outstorageid=' + outstorageid,
                onLoad:function(){
                    $("#storage-allocateNotice-goodsid").focus();
                },
                onClose:function(){
                    $('#storage-dialog-allocateNoticeAddPage-content').dialog("destroy");
                }
            });
            $('#storage-dialog-allocateNoticeAddPage-content').dialog("open");
        }
    	//显示盘点单明细修改页面
    	function beginEditDetail(){
    		//验证表单
			var flag = $("#storage-form-allocateNoticeAdd").form('validate');
			if(flag==false){
				$.messager.alert("提醒",'请先选择出库仓库');
				$("#storage-allocateNotice-storageid").focus();
				return false;
			}
			var row = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		if(row.goodsid == undefined){
    			beginAddDetail();
    		}else{
                var storageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
                var outStorage = $("#storage-allocateNotice-outstorageid").widget("getObject");
                var enterStorage = $("#storage-allocateNotice-enterstorageid").widget("getObject");
    			var url =  'storage/showAllocateNoticeDetailEditPage.do?goodsid='+row.goodsid;
    			$('<div id="storage-dialog-allocateNoticeAddPage-content"></div>').appendTo('#storage-dialog-allocateNoticeAddPage');
	    		$('#storage-dialog-allocateNoticeAddPage-content').dialog({  
				    title: '调拨通知单明细修改',  
				    width: 680,  
				    height: 400,
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    href: url,  
				    modal: true,
				    onLoad:function(){
				    	$("#storage-allocateNotice-unitnum").focus();
				    	$("#storage-allocateNotice-unitnum").select();
				    	var isbatch = $("#storage-allocateNotice-isbatch").val();
				    	if(isbatch=="1"){
				    		$("#storage-allocateNotice-enterstoragelocationid").widget("enable");
				    		$("#storage-allocateNotice-batchno").attr("readonly",false);
				    		$("#storage-allocateNotice-batchno").removeClass("no_input");

                            $("#storage-allocateNotice-enterproduceddate").removeClass("WdateRead");
                            $("#storage-allocateNotice-enterproduceddate").addClass("Wdate");
                            $("#storage-allocateNotice-enterproduceddate").removeAttr("readonly");
                            if(enterStorage.isbatch=="1"){
                                $("#storage-allocateNotice-enterproduceddate").validatebox({required:true});
                            }else{
                                $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
                            }

                            $("#storage-allocateNotice-enterdeadline").removeClass("WdateRead");
                            $("#storage-allocateNotice-enterdeadline").addClass("Wdate");
                            $("#storage-allocateNotice-enterdeadline").removeAttr("readonly");

							var storageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
							var param = null;
		                	if(storageid!=null && storageid!=""){
		                		param = [{field:'goodsid',op:'equal',value:row.goodsid},
		                		       {field:'storageid',op:'equal',value:storageid}];
		                	}else{
		                		param = [{field:'goodsid',op:'equal',value:data.id}];
		                	}
                            //批次是否必填
                            var reFlag = false;
                            if(outStorage.isbatch=="1"){
                                reFlag = true;
                            }
		                	$("#storage-allocateNotice-batchno").widget({
		                		referwid:'RL_T_STORAGE_BATCH_LIST',
		                		param:param,
		            			width:165,
		        				singleSelect:true,
		        				required:reFlag,
		        				onSelect: function(obj){
		        					$("#storage-allocateNotice-detail-summarybatchid").val(obj.id);
	            					$("#storage-allocateNotice-storagelocationname").val(obj.storagelocationname);
	            					$("#storage-allocateNotice-storagelocationid").val(obj.storagelocationid);
									if(obj.produceddate==null || obj.produceddate==""){
										$("#storage-allocateNotice-produceddate").validatebox({required:true});
										$("#storage-allocateNotice-produceddate").removeClass("WdateRead");
										$("#storage-allocateNotice-produceddate").addClass("Wdate");
										$("#storage-allocateNotice-produceddate").removeAttr("readonly");
									}else{
										$("#storage-allocateNotice-produceddate").validatebox({required:false});
										$("#storage-allocateNotice-produceddate").removeClass("Wdate");
										$("#storage-allocateNotice-produceddate").addClass("WdateRead");
										$("#storage-allocateNotice-produceddate").attr("readonly","readonly")
										$("#storage-allocateNotice-produceddate").val(obj.produceddate);
										$("#storage-allocateNotice-deadline").val(obj.deadline);
									}
	        						$("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+obj.existingnum+obj.unitname+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +obj.unitname+"</font>");
	        						$("#storage-allocateNotice-usablenum").val(obj.usablenum);
	        	                    computNum();
	            					$("#storage-allocateNotice-unitnum").focus();
	        						$("#storage-allocateNotice-unitnum").select();
		        				},
		        				onClear:function(){
		        					$("#storage-allocateNotice-detail-summarybatchid").val("");
	            					$("#storage-allocateNotice-storagelocationname").val("");
	            					$("#storage-allocateNotice-storagelocationid").val("");
	            					$("#storage-allocateNotice-produceddate").val("");
	            					$("#storage-allocateNotice-deadline").val("");

//                                    $("#storage-allocateNotice-enterproduceddate").removeClass("Wdate");
//                                    $("#storage-allocateNotice-enterproduceddate").addClass("WdateRead");
//                                    $("#storage-allocateNotice-enterproduceddate").attr("readonly","readonly");
//                                    $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
//                                    $("#storage-allocateNotice-enterdeadline").removeClass("Wdate");
//                                    $("#storage-allocateNotice-enterdeadline").addClass("WdateRead");
//                                    $("#storage-allocateNotice-enterdeadline").attr("readonly","readonly");

	        						$("#storage-allocateNotice-loadInfo").html("&nbsp;");
	        						$("#storage-allocateNotice-usablenum").val(0);
		        				}
		                	});
				    	}else{
				    		$("#storage-allocateNotice-batchno").attr("readonly",true);
				    		$("#storage-allocateNotice-batchno").addClass("no_input");

                            $("#storage-allocateNotice-enterproduceddate").removeClass("Wdate");
                            $("#storage-allocateNotice-enterproduceddate").addClass("WdateRead");
                            $("#storage-allocateNotice-enterproduceddate").attr("readonly","readonly");
                            $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
                            $("#storage-allocateNotice-enterdeadline").removeClass("Wdate");
                            $("#storage-allocateNotice-enterdeadline").addClass("WdateRead");
                            $("#storage-allocateNotice-enterdeadline").attr("readonly","readonly");
				    	}

						formaterNumSubZeroAndDot();

						$("#storage-form-allocateNoticeDetailAddPage").form('validate');
				    },
				    onClose:function(){
				    	$('#storage-dialog-allocateNoticeAddPage-content').dialog("destroy");
				    }
				});
				$('#storage-dialog-allocateNoticeAddPage-content').dialog("open");
			}
    	}
    	//保存调拨通知单明细
    	function addSaveDetail(goFlag){ //添加新数据确定后操作，
    		var flag = $("#storage-form-allocateNoticeDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-allocateNoticeDetailAddPage").serializeJSON();
    		var widgetJson = $("#storage-allocateNotice-goodsid").goodsWidget('getObject');
    		var goodsInfo = {id:widgetJson.id,name:widgetJson.name,brandName:widgetJson.brandName,
    						model:widgetJson.model,barcode:widgetJson.barcode,boxnum:widgetJson.boxnum};
    		form.goodsInfo = goodsInfo;
			$.ajax({
				url: 'basefiles/getItemByGoodsAndStorage.do',
				data:{
					goodsid:widgetJson.id,
					storageid:$("#storage-allocateNotice-outstorageid").widget('getValue')
				},
				async: false,
				type: 'post',
				dataType: 'json',
				success: function (json) {
					form.goodsInfo.itemno=json.itemno;
				},
			});
            if(form.batchno==null){
                form.batchno = "";
            }
    		var rowIndex = 0;
    		var rows = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRows');
    		var updateFlag = false;
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
                if(rowJson.goodsid == form.goodsid && rowJson.batchno==form.batchno){
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
            if(rowIndex == 0){
                $("#storage-allocateNotice-outstorageid").widget('readonly',true);
            }
    		if(rowIndex == rows.length - 1){
    			$("#storage-datagrid-allocateNoticeAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		if(updateFlag){
    			$.messager.alert("提醒", "此商品已经添加！");
    			return false;
    		}else{
    			$("#storage-datagrid-allocateNoticeAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		}
			reloadBilltypeSelect();
    		if(goFlag){ //go为true确定并继续添加一条
    			var storageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
				var url ='storage/showAllocateNoticeDetailAddPage.do?outstorageid='+storageid;
    			$("#storage-dialog-allocateNoticeAddPage-content").dialog('refresh', url);
    		}else{ //否则直接关闭
    			$("#storage-dialog-allocateNoticeAddPage-content").dialog('destroy');
    		}
    		countTotal();
    		
    	}
    	//修改保存
    	function editSaveDetail(goFlag){
    		var flag = $("#storage-form-allocateNoticeDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-allocateNoticeDetailAddPage").serializeJSON();
    		var row = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		$("#storage-datagrid-allocateNoticeAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
   			$("#storage-dialog-allocateNoticeAddPage-content").dialog('destroy');
    		countTotal();
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
			   		var rowIndex = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRowIndex', row);
			   		$("#storage-datagrid-allocateNoticeAddPage").datagrid('deleteRow', rowIndex);
			   		countTotal(); 
			   		var rows = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRows');
			   		var index = -1;
			   		for(var i=0; i<rows.length; i++){
			   			if(rows[i].goodsid != undefined){
			   				index = i;
			   				break;
			  			}
			   		}
			   		if(index == -1){
			   			$("#storage-allocateNotice-outstorageid").widget('readonly',false);
			  		}
					reloadBilltypeSelect();
		    	}
	    	});	
    	}
    	//计算合计
    	function countTotal(){
    		var rows =  $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRows');
    		var countNum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		for(var i=0;i<rows.length;i++){
    			countNum = Number(countNum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    		}
    		$("#storage-datagrid-allocateNoticeAddPage").datagrid("reloadFooter",[{goodsid:'合计',unitnum:countNum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}]);
    	}
    	$(function(){
    		//关闭明细添加页面
    		$(document).bind('keydown', 'esc',function (){
    			$("#storage-allocateNoticeDetail-remark").focus();
    			$("#storage-dialog-allocateNoticeAddPage-content").dialog("close");
		    });
		    $(document).bind('keydown', 'ctrl+enter',function (){
		    	$("#storage-allocateNoticeDetail-remark").focus();
		    	$("#storage-savegoon-allocateNoticeDetailAddPage").trigger("click");
		    	$("#storage-savegoon-allocateNoticeDetailEditPage").trigger("click");
		    });
		    $(document).bind('keydown', '+',function (){
		    	$("#storage-allocateNoticeDetail-remark").focus();
		    	setTimeout(function(){
		    		$("#storage-savegoon-allocateNoticeDetailAddPage").trigger("click");
		    		$("#storage-savegoon-allocateNoticeDetailEditPage").trigger("click");
		    	},300);
		    	return false;
		    });
    	});
		function reloadBilltypeSelect(){

			var rows = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRows');
			if(rows[0].goodsid==''||rows[0].goodsid==undefined){
				$("#storage-allocateNotice-billtype").removeAttr('disabled');
			}else{
				$("#storage-allocateNotice-billtype").attr('disabled','disabled');
			}
		}
    </script>
  </body>
</html>
