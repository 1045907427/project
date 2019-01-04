<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>商品多价调整单查看页面</title>
<%@include file="/include.jsp" %>   
</head>
<body>
    <div id="goods-layout-adjustMultiPricePage" class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
            <div class="buttonBG" id="goods-buttons-adjustMultiPricePage" style="height:26px;"></div>
        </div>
        <div data-options="region:'center',border:false">
            <div id="goods-panel-adjustMultiPricePage"></div>
        </div>
    </div>
    <input type="hidden" id="goods-adjustMultiPrice-id"/>
    <script type="text/javascript">
   //调价明细列表
    var tableColJson=$("goods-datagrid-adjustMultiPriceAddPage").createGridColumnLoad({
    	frozenCol : [[]],
		commonCol : [[  
		              {field:'ck',checkbox:true},
		              {field:'goodsid',title:'商品编码',width:80},
		              {field:'goodsname',title:'商品名称',width:230},
                      {field:'barcode',title : '条形码',width : 90,align : 'left'},
		              {field:'oldbuyprice',title:'原始采购价',width:80,
		            	  formatter:function(value,row,index){
                              return formatterDefineMoney(value,6);
					      }
		              },
		              {field:'newbuyprice',title:'调整采购价',width:80,
		            	  formatter:function(value,row,index){
                              if(parseFloat(value)<parseFloat(row.oldbuyprice)){
                                  return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                              }else if(parseFloat(value)>parseFloat(row.oldbuyprice)){
                                  return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                              }else{
                                  return formatterDefineMoney(value,6);
                              }
					      }
		              },
                      {field:'oldsalesprice',title:'原始销售价',width:80,
                          formatter:function(value,row,index){
                              return formatterDefineMoney(value,6);
                          }
                      },
                      {field:'newsalesprice',title:'调整销售价',width:80,
                          formatter:function(value,row,index){
                              if(parseFloat(value)<parseFloat(row.oldsalesprice)){
                                  return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                              }else if(parseFloat(value)>parseFloat(row.oldsalesprice)){
                                  return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                              }else{
                                  return formatterDefineMoney(value,6);
                              }
                          }
                      },
                <c:if test="${colMap.price1 != null}">
                    {field:'oldprice1',title:'1号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice1',title:'调整1号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice1)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice1)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price2 != null}">
                    {field:'oldprice2',title:'2号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice2',title:'调整2号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice2)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice2)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price3 != null}">
                    {field:'oldprice3',title:'3号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice3',title:'调整3号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice3)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice3)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price4 != null}">
                    {field:'oldprice4',title:'4号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice4',title:'调整4号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice4)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice4)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price5 != null}">
                    {field:'oldprice5',title:'5号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice5',title:'调整5号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice5)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice5)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price6 != null}">
                    {field:'oldprice6',title:'6号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice6',title:'调整6号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice6)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice6)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price7 != null}">
                    {field:'oldprice7',title:'7号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice7',title:'调整7号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice7)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice7)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price8 != null}">
                    {field:'oldprice8',title:'8号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice8',title:'调整8号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice8)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice8)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price9 != null}">
                    {field:'oldprice9',title:'9号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice9',title:'调整9号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice9)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice9)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                <c:if test="${colMap.price10 != null}">
                    {field:'oldprice10',title:'10号价',width:80,
                        formatter:function(value,row,index){
                            return formatterDefineMoney(value,6);
                        }
                    },
                    {field:'newprice10',title:'调整10号价',width:80,
                        formatter:function(value,row,index){
                            if(parseFloat(value)<parseFloat(row.oldprice10)){
                                return "<font color='blue'>"+formatterDefineMoney(value,6)+"</font>";
                            }else if(parseFloat(value)>parseFloat(row.oldprice10)){
                                return "<font color='red'>"+formatterDefineMoney(value,6)+"</font>";
                            }else{
                                return formatterDefineMoney(value,6);
                            }
                        }
                    },
                </c:if>
                    {field:'remark',title:'备注',width:80}
        ]]
    });
    var page_url = "basefiles/adjustMultiPriceAddPage.do";
	var page_type = '${type}';
	if(page_type == "edit"){
        $("#goods-adjustMultiPrice-id").val('${id}');
		page_url = "basefiles/adjustMultiPriceEditPage.do?id=${id}";
	}
	$(function(){
		$("#goods-panel-adjustMultiPricePage").panel({
			href:page_url,
		    cache:false,
		    maximized:true,
		    border:false,
		});
		$("#goods-buttons-adjustMultiPricePage").buttonWidget({
			initButton:[
                {},
                <security:authorize url="/basefiles/addAdjustMultiPricePage.do">
                {
	                type: 'button-add',
	                handler: function(){
		                page_type="add";
		                $("#goods-panel-adjustMultiPricePage").panel('refresh', 'basefiles/adjustMultiPriceAddPage.do');
            	    }
                },
                </security:authorize>
    			<security:authorize url="/basefiles/saveAdjustMultiPricePage.do">
				{
					type: 'button-save',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写商品多价调整单信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","确定保存该商品多价调整单信息？",function(r){
							if(r){
								var type = $("#goods-buttons-adjustMultiPricePage").buttonWidget("getOperType");
				 				if(type=="add"){
				 					$("#goods-form-adjustMultiPriceAddPage").attr("action", "basefiles/addAdjustMultiPriceSave.do");
				 					$("#goods-form-adjustMultiPriceAddPage").submit();
				 				}else if(type=="edit"){
				 					$("#goods-form-adjustMultiPriceAddPage").attr("action", "basefiles/editAdjustMultiPriceSave.do");
				 					$("#goods-form-adjustMultiPriceAddPage").submit();
				 				}
							}
						});
					}
				},
				 </security:authorize>
    			<security:authorize url="/basefiles/saveauditAdjustMultiPricePage.do">
				{
					type: 'button-saveaudit',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写商品多价调整单商品信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","确定保存并审核该商品多价调整单信息？",function(r){
							if(r){
								var type = $("#goods-buttons-adjustMultiPricePage").buttonWidget("getOperType");
								$("#goods-adjustMultiPrice-saveaudit").val("saveaudit");
				 				if(type=="add"){
				 					$("#goods-form-adjustMultiPriceAddPage").attr("action", "basefiles/addAdjustMultiPriceSave.do");
				 					$("#goods-form-adjustMultiPriceAddPage").submit();
				 				}else if(type=="edit"){
				 					$("#goods-form-adjustMultiPriceAddPage").attr("action", "basefiles/editAdjustMultiPriceSave.do");
                                    $("#goods-adjustMultiPrice-saveaudit").val("saveaudit");
				 					$("#goods-form-adjustMultiPriceAddPage").submit();
				 				}
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/deleteAdjustMultiPrice.do">
				{
					type: 'button-delete',
					handler: function(){
						$.messager.confirm("提醒","是否删除当前商品多价调整单？",function(r){
							if(r){
								var id = $("#goods-adjustMultiPrice-id").val();
								if(id!=""){
									loading("删除中..");
									$.ajax({   
							            url :'basefiles/deletesAdjustMultiPrice.do?ids='+id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	if(json.flag){
							            		$.messager.alert("提醒", json.msg);
							            		var data = $("#goods-buttons-adjustMultiPricePage").buttonWidget("removeData", '');
												if(data!=null){
                                                    $("#goods-panel-adjustMultiPricePage").panel('refresh', 'basefiles/adjustMultiPriceEditPage.do?id='+ data.id);
                                                }else{
		                                            parent.closeNowTab();
		                                        }
							            	}else{
							            		$.messager.alert("提醒", json.msg);
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
				<security:authorize url="/basefiles/auditAdjustMultiPrice.do">
				{
					type: 'button-audit',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写商品多价调整单商品信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","是否审核商品多价调整单？",function(r){
							if(r){
								var id = $("#goods-adjustMultiPrice-id").val();
								if(id!=""){
									loading("审核中..");
									$.ajax({   
										url :'basefiles/auditsAdjustMultiPrice.do',
									    data:{ids:id},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	if(true==json.lock){
								  		  		$.messager.alert("提醒","其他用户正在修改该数据");
								  		  		return false;
								  		  	}
							            	if(json.flag){
												$.messager.alert("提醒","审核成功");
                                                $("#goods-panel-adjustMultiPricePage").panel('refresh', 'basefiles/adjustMultiPriceEditPage.do?id='+ json.id);
//												var d = new Date();
//												var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
//												$("#goods-adjustMultiPrice-status-select").val("3");
//												$("#goods-adjustMultiPrice-businessdate").val(str);
//		    									$("#goods-buttons-adjustMultiPricePage").buttonWidget("setDataID",{id:id,state:'3',type:'view'});
							            	}else{
							            		$.messager.alert("提醒","审核失败"+json.msg);
							            	}
							            },
							            error:function(){
							            	loaded();
							            	$.messager.alert("错误","审核失败");
							            }
							        });
								}
							}
						});
					}
				},
				</security:authorize>
				{
	                type: 'button-back',
	                handler: function(data){
	                	 $("#goods-panel-adjustMultiPricePage").panel('refresh', 'basefiles/adjustMultiPriceEditPage.do?id='+ data.id);
	                }
	            },
			    {
                    type: 'button-next',
                    handler: function(data){
                        $("#goods-panel-adjustMultiPricePage").panel('refresh',  'basefiles/adjustMultiPriceEditPage.do?id='+ data.id);
                    }
		         },
		    ],
            buttons:[
                <security:authorize url="/basefiles/exportAdjustMultiPrice.do">
                {
                    id: 'button-export-detail',
                    name: '全局明细导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var id = $("#goods-adjustMultiPrice-id").val();
                        var url = "basefiles/exportAdjustMultiPriceDetail.do";
                        exportByAnalyse(id,"多价调整单："+id+"商品价格明细","goods-datagrid-adjustMultiPriceAddPage",url);
                    }
                },
                </security:authorize>
            ],
		    layoutid:'goods-layout-adjustMultiPricePage',
			model: 'bill',
			type: 'view',
            tab:'多价调整单',
			taburl:'/basefiles/showAdjustMultiPriceListPage.do',
			id:'${id}',
			datagrid:'goods-table-showAdjustMultiPriceList'
		});
	});
   //批量添加商品
   function beginAddMultiDetailByBrandAndSort() {
       $('<div id="goods-dialog-adjustMultiPriceAddPage-content"></div>').appendTo('#goods-dialog-adjustMultiPriceAddPage');
       $('#goods-dialog-adjustMultiPriceAddPage-content').dialog({
           title: '商品多价调整单批量添加',
           width: 900,
           height: 700,
           collapsible:false,
           minimizable:false,
           maximizable:true,
           resizable:true,
           closed: true,
           cache: false,
           href: 'basefiles/adjustMultiPriceAddByBrandAndSortPage.do',
           modal: true,
           onClose:function(){
               $('#goods-dialog-adjustMultiPriceAddPage-content').dialog("destroy");
           }
       });
       $('#goods-dialog-adjustMultiPriceAddPage-content').dialog("open");
   }
	
	//显示调价商品添加页面
	function beginAddDetail(){
		var flag = $("#goods-form-adjustMultiPriceAddPage").form('validate');
//		if(flag==false){
//			$.messager.alert("提醒",'请先完善商品多价调整单基本信息');
//			return false;
//		}
		$('<div id="goods-dialog-adjustMultiPriceAddPage-content"></div>').appendTo('#goods-dialog-adjustMultiPriceAddPage');
		$('#goods-dialog-adjustMultiPriceAddPage-content').dialog({  
		    title: '多价调整单商品添加',
		    width: 700,
		    height: 420,
		    collapsible:false,
		    minimizable:false,
		    maximizable:true,
		    resizable:true,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    href: 'basefiles/showAdjustMultiPriceDetailAddPage.do',
		    onLoad:function(){
		    	$("#goods-adjustMultiPrice-goodsid").focus();
		    },
		    onClose:function(){
		    	$('#goods-dialog-adjustMultiPriceAddPage-content').dialog("destroy");
		    }
		});
		$('#goods-dialog-adjustMultiPriceAddPage-content').dialog("open");
	}
	//显示调价商品修改页面
	function beginEditDetail(){
		var row = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getSelected');
		if(row == null){
			$.messager.alert("提醒", "请选择一条记录");
			return false;
		}
		
		if(row.goodsid == undefined){
			beginAddDetail();
		}else{
			$('<div id="goods-dialog-adjustMultiPriceAddPage-content"></div>').appendTo('#goods-dialog-adjustMultiPriceAddPage');
    		$('#goods-dialog-adjustMultiPriceAddPage-content').dialog({  
			    title: '商品多价调整单明细修改',
			    width: 700,
			    height: 420,
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,
			    href: 'basefiles/showAdjustMultiPriceDetailEditPage.do',
			    modal: true,
			    onLoad:function(){
			    	$("#goods-adjustMultiPrice-newbuyprice").focus();
			    	$("#goods-adjustMultiPrice-newbuyprice").select();
			    }, 
			    onClose:function(){
			    	$('#goods-dialog-adjustMultiPriceAddPage-content').dialog("destroy");
			    }
			});
			$('#goods-dialog-adjustMultiPriceAddPage-content').dialog("open");
		}
	}

	//保存调价商品
	function addSaveDetail(goFlag){ //添加新数据确定后操作，
	    var flag = $("#goods-form-adjustMultiPriceDetailAddPage").form('validate');
		var nowprice = $("#goods-adjustMultiPrice-nowprice").val();
		var nowboxprice = $("#goods-adjustMultiPrice-nowboxprice").val();
	  	if(flag==false){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
	  	if(nowprice==""){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
	  	if(nowboxprice==""){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
		var form = $("#goods-form-adjustMultiPriceDetailAddPage").serializeJSON();
		var widgetJson = $("#goods-adjustMultiPrice-goodsid").goodsWidget('getObject');
		var rowIndex = 0;
		var rows = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRows');
		var updateFlag = false;
		for(var i=0; i<rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.goodsid==widgetJson.id){
				rowIndex = i;
				updateFlag = true;
				break;
			}
			if(rowJson.goodsid == undefined){
				rowIndex = i;
				break;
			}
		}
		if(rowIndex == rows.length - 1){
			$("#goods-datagrid-adjustMultiPriceAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
		}
		if(updateFlag){
			$.messager.alert("提醒", "此商品已经添加！");
			return false;
		}else{
			$("#goods-datagrid-adjustMultiPriceAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
		}
		if(goFlag){ //go为true确定并继续添加一条
			var url = 'basefiles/showAdjustMultiPriceDetailAddPage.do';
			$("#goods-dialog-adjustMultiPriceAddPage-content").dialog('refresh', url);
		}
		else{ //否则直接关闭
			$("#goods-dialog-adjustMultiPriceAddPage-content").dialog('destroy');
		}
	}
	//修改保存调价商品
	function editSaveDetail(goFlag){
		var flag = $("#goods-form-adjustMultiPriceDetailEditPage").form('validate');
		var nowprice = $("#goods-adjustMultiPrice-nowprice").val();
	  	if(flag==false){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
	  	if(nowprice==""){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
		var form = $("#goods-form-adjustMultiPriceDetailEditPage").serializeJSON();
		var row = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getSelected');
		var rowIndex = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRowIndex', row);
		form.goodsInfo = row.goodsInfo;
		$("#goods-datagrid-adjustMultiPriceAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#goods-dialog-adjustMultiPriceAddPage-content").dialog('destroy');
	}
	//删除调价商品
	function removeDetail(){
		var row = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getSelected');
    	if(row == null){
    		$.messager.alert("提醒", "请选择一条记录");
    		return false;
    	}
    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
	    	if(r){
		   		var rowIndex = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRowIndex', row);
		   		$("#goods-datagrid-adjustMultiPriceAddPage").datagrid('deleteRow', rowIndex);
	    	}
    	});
	}

    //验证是否有商品明细存在
	function checkGoodsDetailEmpty(){
		var rows =  $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRows');
		var count = 0;
		for(var i=0;i<rows.length;i++){
			if(rows[i].goodsid && rows[i].goodsid!=""){
				count ++;
			}
		}
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	
	$(document).keydown(function(event){//alert(event.keyCode);
        switch(event.keyCode){
            case 27: //Esc
                $("#goods-dialog-adjustMultiPriceAddPage-content").dialog('close');
                break;
            case 65: //a
                $("#button-add").click();
                break;
            case 83: //s
                if(event.ctrlKey){
                    $("#button-save").click();
                    return false;
                }
                break;
                
        }
    });
	$(document).bind('keydown', 'ctrl+enter',function (){
        $("#goods-savegoon-adjustMultiPriceDetailAddPage").focus();
        $("#goods-savegoon-adjustMultiPriceDetailAddPage").trigger("click");
        $("#goods-savegoon-adjustMultiPriceDetailEditPage").focus();
        $("#goods-savegoon-adjustMultiPriceDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+',function (){
        $("#goods-savegoon-adjustMultiPriceDetailAddPage").focus();
        $("#goods-savegoon-adjustMultiPriceDetailEditPage").focus();
        setTimeout(function(){
            $("#goods-savegoon-adjustMultiPriceDetailAddPage").trigger("click");
            $("#goods-savegoon-adjustMultiPriceDetailEditPage").trigger("click");
        },300);
        return false;
    });
    </script>
</body>
</html>