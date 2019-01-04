<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>调价单操作</title>
<%@include file="/include.jsp" %>   
</head>
<body>
    <div id="goods-layout-adjustPricePage" class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
            <div class="buttonBG" id="goods-buttons-adjustPricePage" style="height:26px;"></div>
        </div>
        <div data-options="region:'center',border:false">
            <div id="goods-panel-adjustPricePage"></div>
        </div>
    </div>
    <div id="goods-panel-relation-upper"></div>
    <div id="goods-panel-sourceQueryPage"></div>
    <div id="wordfolw-addidea-dialog-page"></div>
 
    <script type="text/javascript">
   //调价明细列表
    var checkflag=false;//是否已锁定选择框的标记，true为已上锁，false为未上锁，可以进行修改状态的操作
    var tableColJson=$("goods-datagrid-adjustPriceAddPage").createGridColumnLoad({
    	frozenCol : [[]],
		commonCol : [[  
		              {field:'ck',checkbox:true},
		              {field:'goodsid',title:'商品编码',width:80},
		              {field:'goodsname',title:'商品名称',width:230},
		              {field:'oldprice',title:'原价',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterDefineMoney(value,6);
					        }  
		              },
		              {field:'oldboxprice',title:'原箱价',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterDefineMoney(value,6);
					        }  
		              },
		              {field:'boxnum',title:'箱装量',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }  
		              },
		              {field:'nowprice',title:'现价',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterDefineMoney(value,6);
					        }  
		              },
		              {field:'nowboxprice',title:'现箱价',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterDefineMoney(value,6);
					        }  
		              },
		              {field:'rate',title:'涨幅(﹪)',width:80,
		            	  formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }  
		              },
		              {field:'remark',title:'备注',width:80},
		            ]]
    });
    var page_url = "basefiles/adjustPriceAddPage.do";
	var page_type = '${type}';
	if(page_type == "edit"){
		page_url = "basefiles/adjustPriceEditPage.do?id=${id}";
		checkflag=true;
	}
	$(function(){
		$("#goods-panel-adjustPricePage").panel({
			href:page_url,
		    cache:false,
		    maximized:true,
		    border:false,
		});
		$("#goods-buttons-adjustPricePage").buttonWidget({
			initButton:[
                {},
                <security:authorize url="/basefiles/addAdjustPricePage.do">
                {
	                type: 'button-add',
	                handler: function(){
		                page_type="add";
		                $("#goods-panel-adjustPricePage").panel('refresh', 'basefiles/adjustPriceAddPage.do');
            	    }
                },
                </security:authorize>
    			<security:authorize url="/basefiles/saveAdjustPricePage.do">
				{
					type: 'button-save',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写调价单商品信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","确定保存该调价单信息？",function(r){
							if(r){
								var type = $("#goods-buttons-adjustPricePage").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//暂存
				 					$("#goods-form-adjustPriceAddPage").attr("action", "basefiles/addAdjustPriceSave.do");
				 					$("#goods-form-adjustPriceAddPage").submit();
				 				}else if(type=="edit"){
				 					$("#goods-form-adjustPriceAddPage").attr("action", "basefiles/editAdjustPriceSave.do");
				 					$("#goods-form-adjustPriceAddPage").submit();
				 				}
							}
						});
					}
				},
				 </security:authorize>
    			<security:authorize url="/basefiles/saveauditAdjustPricePage.do">
				{
					type: 'button-saveaudit',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写调价单商品信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","确定保存并审核该调价单信息？",function(r){
							if(r){
								var type = $("#goods-buttons-adjustPricePage").buttonWidget("getOperType");
								$("#goods-adjustPrice-saveaudit").val("saveaudit");
				 				if(type=="add"){ 
				 					//暂存                                                                                                                                               
				 					$("#goods-form-adjustPriceAddPage").attr("action", "basefiles/addAdjustPriceSave.do");
				 					$("#goods-form-adjustPriceAddPage").submit();
				 					
				 				}else if(type=="edit"){
				 					$("#goods-form-adjustPriceAddPage").attr("action", "basefiles/editAdjustPriceSave.do");
				 					$("#goods-form-adjustPriceAddPage").submit();
				 				}
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/deleteAdjustPrice.do">
				{
					type: 'button-delete',
					handler: function(){
						$.messager.confirm("提醒","是否删除当前调价单？",function(r){
							if(r){
								var id = $("#goods-adjustPrice-id").val();
								if(id!=""){
									loading("删除中..");
									$.ajax({   
							            url :'basefiles/deleteAdjustPrice.do?id='+id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	if(true==json.lock){
								  		  		$.messager.alert("提醒","其他用户正在修改该数据");
								  		  		return false;
								  		  	}
							            	if(json.flag){
							            		$.messager.alert("提醒", "删除成功");
							            		var data = $("#goods-buttons-adjustPricePage").buttonWidget("removeData", '');
												if(data!=null)
													   $("#goods-panel-adjustPricePage").panel('refresh', 'basefiles/adjustPriceEditPage.do?id='+ data.id);
												else{
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
				<security:authorize url="/basefiles/auditAdjustPrice.do">
				{
					type: 'button-audit',
					handler: function(){
						if(checkGoodsDetailEmpty()){
	          		  		$.messager.alert("提醒","抱歉，请填写调价单商品信息");
	           		 		return false;
						}
						$.messager.confirm("提醒","是否审核调价单？",function(r){
							if(r){
								var id = $("#goods-adjustPrice-id").val();
								if(id!=""){
									loading("审核中..");
									$.ajax({   
										url :'basefiles/auditAdjustPrice.do',
									    data:{id:id},
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
												var d = new Date();
												var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
												$("#goods-adjustPrice-status-select").val("3");
												$("#goods-adjustPrice-businessdate").val(str);
		    									$("#goods-buttons-adjustPricePage").buttonWidget("setDataID",{id:id,state:'3',type:'view'});
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
	                    
	                	 $("#goods-panel-adjustPricePage").panel('refresh', 'basefiles/adjustPriceEditPage.do?id='+ data.id);
	                }
	            },
			    {
		                type: 'button-next',
		                handler: function(data){
		        
		                    $("#goods-panel-adjustPricePage").panel('refresh',  'basefiles/adjustPriceEditPage.do?id='+ data.id);
		                }
		         },
		    ],
		    layoutid:'goods-layout-adjustPricePage',
			model: 'bill',
			type: 'view',
			tab:'调价单详情',
			taburl:'/basefiles/showAdjustPriceListPage.do',
			id:'${id}',
			datagrid:'goods-table-showAdjustPriceList'
		});
	});
	//调价类型编号参照窗口随调价类型改变而改变
	function changeCustomerWidget(customertype,customerid,disabled){
        $("#customertd").empty();
        var tdstr = "",disabledstr="";
        if(disabled == "1"){
            disabledstr = 'readonly="readonly"';
        }
        if("4" == customertype){
            tdstr = '<input type="text" id="goods-customer-adjustPriceAddPage" name="adjustPrice.busid"  value="'+customerid+'" '+disabledstr+'/>';
        }
        else if("3" == customertype){
            tdstr = '<input type="text" id="goods-pricelist-adjustPriceAddPage" name="adjustPrice.busid" value="'+customerid+'" '+disabledstr+'/>';
        }
        else if("1" == customertype||"2" == customertype){
        	tdstr = '<input type="text" id="goods-null-adjustPriceAddPage" name="adjustPrice.busid"  value="'+customerid+'" '+disabledstr+'/>';
        }
        $(tdstr).appendTo("#customertd");
        if("4" == customertype){
            $("#goods-customer-adjustPriceAddPage").widget({
                referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT_2",
                singleSelect:true,
                required:true,
                isPageReLoad:false,
                width:160,
            });
        }else if("3" == customertype){
            $("#goods-pricelist-adjustPriceAddPage").widget({
                referwid:"RL_T_SYS_CODE_PRICELIST",
                singleSelect:true,
                required:true,
                width:160,
            });
        }
        else if("1" == customertype||"2" == customertype){
            $("#goods-null-adjustPriceAddPage").widget({
                referwid:"RL_T_SYS_CODE_PRICELIST",
                singleSelect:false,
                required:false,
                width:160,
                readonly:true,
            });
        }
    }
	//显示调价商品添加页面
	function beginAddDetail(){
		var flag = $("#goods-form-adjustPriceAddPage").form('validate');
		if(flag==false){
			$.messager.alert("提醒",'请先完善调价单基本信息');
			return false;
		}
	    var type = $("#goods-adjustPrice-type-select").val();
	    if(type=="4"){
	    	var typeCode = $("#goods-customer-adjustPriceAddPage").widget("getValue");
	    }
	    else  if(type=="3"){
	    	var typeCode = $("#goods-pricelist-adjustPriceAddPage").widget("getValue");
	    }
	    else{
	    	var typeCode = "";
	    }
		$('<div id="goods-dialog-adjustPriceAddPage-content"></div>').appendTo('#goods-dialog-adjustPriceAddPage');
		$('#goods-dialog-adjustPriceAddPage-content').dialog({  
		    title: '调价商品添加',  
		    width: 680,  
		    height: 320,  
		    collapsible:false,
		    minimizable:false,
		    maximizable:true,
		    resizable:true,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    href: 'basefiles/showAdjustPriceDetailAddPage.do?type='+type+'&typeCode='+typeCode,
		    onLoad:function(){
		    	$("#goods-adjustPrice-goodsid").focus();
		    },
		    onClose:function(){
		    	$('#goods-dialog-adjustPriceAddPage-content').dialog("destroy");
		    }
		});
		$('#goods-dialog-adjustPriceAddPage-content').dialog("open");
	}
	//显示调价商品修改页面
	function beginEditDetail(){
		var row = $("#goods-datagrid-adjustPriceAddPage").datagrid('getSelected');
		if(row == null){
			$.messager.alert("提醒", "请选择一条记录");
			return false;
		}
		
		if(row.goodsid == undefined){
			beginAddDetail();
		}else{
			$('<div id="goods-dialog-adjustPriceAddPage-content"></div>').appendTo('#goods-dialog-adjustPriceAddPage');
    		$('#goods-dialog-adjustPriceAddPage-content').dialog({  
			    title: '调价单明细修改',  
			    width: 680,  
			    height: 320,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			
			    href: 'basefiles/showAdjustPriceDetailEditPage.do',  
			    modal: true,
			    onLoad:function(){
			    	$("#goods-adjustPrice-nowprice").focus();
			    	$("#goods-adjustPrice-nowprice").select();
			    }, 
			    onClose:function(){
			    	$('#goods-dialog-adjustPriceAddPage-content').dialog("destroy");
			    }
			});
			$('#goods-dialog-adjustPriceAddPage-content').dialog("open");
		}
	}

	//保存调价商品
	function addSaveDetail(goFlag){ //添加新数据确定后操作，
		var type = $("#goods-adjustPrice-type-select").val();
	    if(type=="4"){
	    	var typeCode = $("#goods-customer-adjustPriceAddPage").widget("getValue");
	    }
	    else{
	    	var typeCode = "";
	    }
	    var flag = $("#goods-form-adjustPriceDetailAddPage").form('validate');
		var nowprice = $("#goods-adjustPrice-nowprice").val();
		var nowboxprice = $("#goods-adjustPrice-nowboxprice").val();
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
		var form = $("#goods-form-adjustPriceDetailAddPage").serializeJSON();
		var widgetJson = $("#goods-adjustPrice-goodsid").goodsWidget('getObject');
		var rowIndex = 0;
		var rows = $("#goods-datagrid-adjustPriceAddPage").datagrid('getRows');
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
			$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
		}
		if(updateFlag){
			$.messager.alert("提醒", "此商品已经添加！");
			return false;
		}else{
			$("#goods-datagrid-adjustPriceAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
		}
		if(goFlag){ //go为true确定并继续添加一条
			var url = 'basefiles/showAdjustPriceDetailAddPage.do?type='+type+'&typeCode='+typeCode; 
			$("#goods-dialog-adjustPriceAddPage-content").dialog('refresh', url);
		}
		else{ //否则直接关闭
			$("#goods-dialog-adjustPriceAddPage-content").dialog('destroy');
		}
		disableChoiceWidget();
	}
	//修改保存调价商品
	function editSaveDetail(goFlag){
		var flag = $("#goods-form-adjustPriceDetailAddPage").form('validate');
		var nowprice = $("#goods-adjustPrice-nowprice").val();
	  	if(flag==false){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
	  	if(nowprice==""){
	  		$.messager.alert("提醒", "请填写基本信息");
	  		return false;
	  	}
		var form = $("#goods-form-adjustPriceDetailAddPage").serializeJSON();
		var row = $("#goods-datagrid-adjustPriceAddPage").datagrid('getSelected');
		var rowIndex = $("#goods-datagrid-adjustPriceAddPage").datagrid('getRowIndex', row);
		form.goodsInfo = row.goodsInfo;
		$("#goods-datagrid-adjustPriceAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
			$("#goods-dialog-adjustPriceAddPage-content").dialog('destroy');
	}
	//删除调价商品
	function removeDetail(){
		var row = $("#goods-datagrid-adjustPriceAddPage").datagrid('getSelected');
    	if(row == null){
    		$.messager.alert("提醒", "请选择一条记录");
    		return false;
    	}
    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
	    	if(r){
		   		var rowIndex = $("#goods-datagrid-adjustPriceAddPage").datagrid('getRowIndex', row);
		   		$("#goods-datagrid-adjustPriceAddPage").datagrid('deleteRow', rowIndex);
		   	   	disableChoiceWidget();
	    	}
    	});
	}
	
	//选择品牌和涨幅自动生成调价商品
	function autoCreateByBrand(){
		var type=$("#goods-adjustPrice-type-select").val();
		if(type=="3"){
			var busid= $("#goods-pricelist-adjustPriceAddPage").widget("getValue");
			if(busid==""){
				$.messager.alert("提醒", "请先选择价格套编号！");
		  		return false;
			}
		}
		$("#goods-dialog-adjustPriceAddPage").dialog({  
		    title: '按商品品牌添加',  
		    width: 400,  
		    height: 450,  
		    closed: false,  
		    cache: false,  
		    href: 'basefiles/showAdjustPriceDetailAddPageByBrand.do',  
		    modal: true,
		    buttons:[
		    	{  
                    text:'确定',  
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){  
                    	var rate = $("#checklist-adjustPrice-rate").val();
                    	if(rate==""){
                    		$.messager.alert("提醒", "请填写涨幅");
                	  		return false;
                    	}
                    		
                    	var rows = $("#checklist-adjustPrice-brandlist").datagrid("getChecked");
                    	
                    	var brands = "";
                    	for(var i=0;i<rows.length;i++){
                    		if(i==0){
                    			brands = rows[i].id;
                    		}else{
                    			brands += ","+rows[i].id;
                    		}
                    	}
                    	autoAddAdjustPriceDetailByBrand(rate,brands);
                    	$("#goods-dialog-adjustPriceAddPage").dialog("close");
                    }  
                }
		    ]
		});
	}
	//通过品牌和涨幅自动生成调价商品
	function autoAddAdjustPriceDetailByBrand(rate,brands){
		var type=$("#goods-adjustPrice-type-select").val();
		if(type=="3"){
			var busid= $("#goods-pricelist-adjustPriceAddPage").widget("getValue");
		}
		else{
			var busid="";
		}
		loading("生成中...");
		$.ajax({   
            url :'basefiles/getAdjustPriceDetailByBrand.do',
            data:{brands:brands,rate:rate,type:type,busid:busid},
            type:'post',
            dataType:'json',
            success:function(json){
            	loaded();
            	$("#goods-datagrid-adjustPriceAddPage").datagrid("loadData",json);
            	if(json.length<13){
            		var j = 13-json.length;
            		for(var i=0;i<j;i++){
            			$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
            		}
					}else{
						$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
					}
					loaded();
					disableChoiceWidget();
            },
            error:function(){
            	loaded();
            }
        });
	
	}
	//自动增加合同价商品
	function autoCreateCustomerPriceByBrand(){
		var customerid = $("#goods-customer-adjustPriceAddPage").widget("getValue");
    	if(customerid==""){
    		$.messager.alert("提醒", "请选择客户！");
	  		return false;
    	}
		$("#goods-dialog-adjustPriceAddPage").dialog({  
		    title: '添加合同商品',  
		    width: 400,  
		    height: 450,  
		    closed: false,  
		    cache: false,  
		    href: 'basefiles/showAdjustCustomerPriceDetailAddPageByBrand.do?customerid='+customerid,  
		    modal: true,
		    buttons:[
		    	{  
                    text:'确定',  
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){  
                    	var rate = $("#checklist-adjustPrice-rate").val();
                    	if(rate==""){
                    		$.messager.alert("提醒", "请填写涨幅");
                	  		return false;
                    	}
                        var rows = $("#checklist-adjustPrice-brandlist").datagrid("getChecked");
                    	var brands = "";
                    	for(var i=0;i<rows.length;i++){
                    		if(i==0){
                    			brands = rows[i].id;
                    		}else{
                    			brands += ","+rows[i].id;
                    		}
                    	}
                    	autoAddAdjustCustomerPriceDetailByBrand(rate,brands);
                    	$("#goods-dialog-adjustPriceAddPage").dialog("close");
                    }  
                }
		    ]
		});
	}
	//通过客户和涨幅自动生成调价商品
	function autoAddAdjustCustomerPriceDetailByBrand(rate,brands){
		var customerid = $("#goods-customer-adjustPriceAddPage").widget("getValue");
		loading("生成中...");
		$.ajax({   
            url :'basefiles/getAdjustCustomerPriceDetailByBrand.do',
            data:{rate:rate,brands:brands,customerid:customerid},
            type:'post',
            dataType:'json',
            success:function(json){
            	loaded();
            	$("#goods-datagrid-adjustPriceAddPage").datagrid("loadData",json);
            	if(json.length<13){
            		var j = 13-json.length;
            		for(var i=0;i<j;i++){
            			$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
            		}
					}else{
						$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
					}
					loaded();
					disableChoiceWidget();
            },
            error:function(){
            	loaded();
            }
        });

	}
	//有商品时锁定参照窗口选择
	function disableChoiceWidget(){
		var rows =  $("#goods-datagrid-adjustPriceAddPage").datagrid('getRows');
		var count = 0;
		for(var i=0;i<rows.length;i++){
			if(rows[i].goodsid && rows[i].goodsid!=""){
				count ++;
			}
		}
		if(count>0){
    		$("#goods-adjustPrice-type-select").attr("disabled","disabled");
    		var type=$("#goods-adjustPrice-type-select").val();
    		if(!checkflag){
    		    if(type=="3"){
    			    $("#goods-pricelist-adjustPriceAddPage").widget("readonly",true);
    			    checkflag=true;
    		    }else if(type=="4"){
    			    $("#goods-customer-adjustPriceAddPage").widget("readonly",true);
    			    checkflag=true;
    		    }
    		}
    	}else{
			$("#goods-adjustPrice-type-select").removeAttr("disabled");
			var type=$("#goods-adjustPrice-type-select").val();
    		if(type=="3"){
    			$("#goods-pricelist-adjustPriceAddPage").widget("readonly",false);
    			checkflag=false;
    		}else if(type=="4"){
    			$("#goods-customer-adjustPriceAddPage").widget("readonly",false);
    			checkflag=false;
    		}
		}
	}
	function checkGoodsDetailEmpty(){
		var rows =  $("#goods-datagrid-adjustPriceAddPage").datagrid('getRows');
		var count = 0;
		for(var i=0;i<rows.length;i++){
			if(rows[i].goodsid && rows[i].goodsid!=""){
				count ++;
			}
		}
		if(count>0){
			return false;
		}
		else{
			return true;
		}
	}

	//选择分类和涨幅自动生成调价商品
	function autoCreateByDefaultSort(){
		var type=$("#goods-adjustPrice-type-select").val();
		if(type=="3"){
			var busid= $("#goods-pricelist-adjustPriceAddPage").widget("getValue");
			if(busid==""){
				$.messager.alert("提醒", "请选择价格套编号！");
		  		return false;
			}
		}
		$("#goods-dialog-adjustPriceAddPage").dialog({  
		    title: '按商品分类添加',  
		    width: 400,  
		    height: 450,  
		    closed: false,  
		    cache: false,  
		    href: 'basefiles/showAdjustPriceDetailAddPageByDefaultSort.do',  
		    modal: true,
		    buttons:[
		    	{  
                    text:'确定',  
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){  
                    	var rate = $("#checklist-adjustPrice-rate").val();
                    	if(rate==""){
                    		$.messager.alert("提醒", "请填写涨幅");
                	  		return false;
                    	}
                    	var waresClassTree=$.fn.zTree.getZTreeObj("checklist-adjustPrice-goodssort");
          				var rows = waresClassTree.getCheckedNodes(true);
                    	var goodssorts = "";
                    	for(var i=0;i<rows.length;i++){
                    		if(i==0){
                    			goodssorts = rows[i].id;
                    		}else{
                    			goodssorts += ","+rows[i].id;
                    		}
                    	}
                    	autoAddAdjustPriceDetailByDefaultSort(rate,goodssorts);
                    	$("#goods-dialog-adjustPriceAddPage").dialog("close");
                    }  
                }
		    ]
		});
	}
	//通过分类和涨幅自动生成调价商品
	function autoAddAdjustPriceDetailByDefaultSort(rate,goodssorts){
		var type=$("#goods-adjustPrice-type-select").val();
		if(type=="3"){
			var busid= $("#goods-pricelist-adjustPriceAddPage").widget("getValue");
		}
		else if(type=="4"){
			var busid= $("#goods-customer-adjustPriceAddPage").widget("getValue");
		}
		else{
			var busid="";
		}
		loading("生成中...");
		$.ajax({   
            url :'basefiles/getAdjustPriceDetailByDefaultSort.do',
            data:{goodssorts:goodssorts,rate:rate,type:type,busid:busid},
            type:'post',
            dataType:'json',
            success:function(json){
            	loaded();
            	$("#goods-datagrid-adjustPriceAddPage").datagrid("loadData",json);
            	if(json.length<13){
            		var j = 13-json.length;
            		for(var i=0;i<j;i++){
            			$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
            		}
					}else{
						$("#goods-datagrid-adjustPriceAddPage").datagrid('appendRow',{});
					}
					loaded();
					disableChoiceWidget();
            },
            error:function(){
            	loaded();
            }
        });
	
	}
	
	$(document).keydown(function(event){//alert(event.keyCode);
        switch(event.keyCode){
            case 27: //Esc
                $("#goods-dialog-adjustPriceAddPage-content").dialog('close');
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
        $("#goods-savegoon-adjustPriceDetailAddPage").focus();
        $("#goods-savegoon-adjustPriceDetailAddPage").trigger("click");
        $("#goods-savegoon-adjustPriceDetailEditPage").focus();
        $("#goods-savegoon-adjustPriceDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+',function (){
        $("#goods-savegoon-adjustPriceDetailAddPage").focus();
        $("#goods-savegoon-adjustPriceDetailEditPage").focus();
        setTimeout(function(){
            $("#goods-savegoon-adjustPriceDetailAddPage").trigger("click");
            $("#goods-savegoon-adjustPriceDetailEditPage").trigger("click");
        },300);
        return false;
    });
    </script>
</body>
</html>