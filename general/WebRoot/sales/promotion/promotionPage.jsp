<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>促销活动页面</title>
<%@include file="/include.jsp"%>
</head>
<body>
	<input type="hidden" id="sales-backid-promotionPage" value="${id }" />
    <input type="hidden" id="sales-promotion-detailId" />
    <input type="hidden" id="sales-promotion-act"  value="${act}"/>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false">
			<div class="buttonBG" id="sales-buttons-promotionPage" style="height: 26px;"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="sales-panel-promotionPage"></div>
		</div>
	</div>
	<div id="sales-dialog-promotionAddPage"></div>
	<script type="text/javascript">
       var promotion_type = '${type}';
       var promotion_url = '';
       if(promotion_type == ''){ 
          promotion_url = "sales/promotionAddPage.do?act=${act}";
       }
       if(promotion_type == 'edit'){
          promotion_url = "sales/promotionEditPage.do?id=${id}&act=${act}";
       }
   	   if(promotion_type == "view"){
   		  promotion_url = "sales/promotionViewPage.do?id=${id}&act=${act}";
   	   }
       //促销活动产品组
        var wareListJson = $("#sales-datagrid-promotionAddPage").createGridColumnLoad({ //买赠商品明细datagrid字段
        //name:'t_sales_promotion_package_group',
            frozenCol : [[
                          {field:'id',hidden:true}
                        ]],
            commonCol: [[
                        <c:if test="${act == 3}">
                            {field:'groupid',title:'活动码',width:160,sortable:true,align:'left'},
                        </c:if>
                        <c:if test="${act == 1}">
                            {field:'groupid',title:'促销产品编号',width:150,sortable:true,align:'left'},
                        </c:if>
                        {field:'groupname',title:'产品组名称',width:120,align:'left',hidden:true},
                        {field:'goodsid', title:'商品编码', width:120,align:'left',
                            formatter:function(value,row,index){
                                return value;
                            }
                        },
                        {field:'goodsname', title:'商品名称', width:200,align:'left',aliascol:'groupname',
                            formatter:function(value,row,index){
                                if(row.gtype == 1){
                                    return row.goodsname;
                                }else if(row.gtype == 2) {
                                    return "<font color='blue'>&nbsp;赠 </font>"+row.goodsname;
                                }
                            }
                        },
                        {field:'ptype',title:'活动类型',width:80,align:'left',hidden:true},
                    <c:if test="${act == '1'}">
                        {field:'unitprice', title:'赠品单价',width:60,align:'left',hidden:true,
                            formatter:function(value,row,index){
                                return formatterMoney(value);
                            }
                        },
                        {field:'price', title:'促销价',width:60,align:'left',
                            formatter:function(value,row,index){
                                if(row.gtype == '2'){
                                    return "";
                                }else if(row.gtype == '1'){
                                    value = formatterMoney(value);
                                    return value;
                                }
                            }
                        },
                    </c:if>
                        {field:'limitnum', title:'促销份数', width:100,
                            formatter:function(value,row,index){
                                if(row.gtype == 2){
                                    return "";
                                }else if(value == "" || value == 0.00 ){
                                    return '不限数量';
                                } else if(value != ''&& value != undefined  ){
                                    return formatterBigNum(value)+ "份" ;
                                }
                            }
                        },
                        {field:'remainnum', title:'促销可用量',width:70,align:'left',
                            formatter:function(value,row,index){
                                if(row.gtype == 2){
                                    return "";
                                }else if(value == "" || value == 0.00 && row.gtype == 1 && row.limitnum == row.remainnum){
                                    return '不限数量';
                                }else if(value != ''&& value != undefined && row.gtype == 1 ){
                                    return formatterBigNum(value)+ "份" ;
                                }
                            }
                        },
                    <c:if test="${act == 1}">
                        {field:'unitnum', title:'基准数量', width:100,
                            formatter:function(value,row,index){
                                if(value != undefined){
                                    return formatterBigNumNoLen(row.unitnum) + row.unitname ;
                                }
                            }
                        },
                    </c:if>
                    <c:if test="${act == 3}">
                        {field:'unitnum', title:'标准数量', width:100,
                            formatter:function(value,row,index){
                                if(value != undefined){
                                    return formatterBigNumNoLen(row.unitnum) + row.unitname ;
                                }
                            }
                        },
                    </c:if>
                        {field:'aux',title:'辅数量',width:80,
                            formatter:function(value,row,index){
                                if(row.gtype == 1 ){
                                    if(row.auxnum == "0"){
                                        return formatterBigNumNoLen(row.auxnum) + row.auxunitname + formatterBigNumNoLen(row.auxremainder) + row.unitname;
                                    }else{
                                        return formatterBigNumNoLen(row.auxnum) + row.auxunitname + formatterBigNumNoLen(row.auxremainder) + row.unitname;
                                    }

                                }else if(row.gtype == 2){
                                    return formatterBigNumNoLen(row.unitnum) + row.unitname;
                                }
                            }
                        },

                        {field:'remark', title:'备注',width:150,align:'left',
                            editor:{
                                type:'validatebox'
                            }
                        },
//                        {field:'boxnum',title:'箱装量',hidden:true},
//                        {field:'printtimes',title:'打印次数',hidden:true},
//                        {field:'auxremainder',title:'辅基准数量1',hidden:true},
//                        {field:'auxnum',title:'辅基准数量2',hidden:true},
//                        {field:'auxunitname',title:'商品箱单位',hidden:true},
//                        {field:'unitname',title:'商品单位',hidden:true},
//                        {field:'brand',title:'品牌',hidden:true}
                      ]]

    	});
        var bundleJson = $("#sales-datagrid-promotionAddPage").createGridColumnLoad({
           frozenCol : [[
               //{field:'ck',checkbox:true},
               {field:'id',hidden:true}
           ]],
           commonCol: [[
               {field:'groupid',title:'促销产品编号',width:120,align:'left'},
               {field:'groupname',title:'捆绑促销名称',width:120,align:'left'},
               {field:'goodsid', title:'商品编号', width:120,align:'left',
                   formatter:function(value,row,index){
                       return value;
                   }
               },
               {field:'goodsname', title:'商品名称', width:200,align:'left'},
               {field:'goodsoldprice', title:'原价',width:60,align:'left',//后台存入detail表中的oldprice
                   formatter:function(value,row,index){
                       return formatterMoney(value);
                   }
               },
               {field:'goodsprice', title:'促销价',width:60,align:'left',//后台存入detail表中的price
                   formatter:function(value,row,index){
                       return formatterMoney(value);
                   }
               },
               {field:'unitnum',title:'捆绑数量',width:60,isShow:true,
                   formatter:function(value,row,index){
                       return formatterBigNumNoLen(value);
                   }
               },
               {field:'unitname',title:'单位',width:40,isShow:true},
               {field:'totalprice',title:'套餐总价',width:60,isShow:true,//该字段后台存入group表中的price字段
                   formatter:function(value,row,index){
                       return formatterMoney(value);
                   }
               },
               {field:'totaloldprice',title:'套餐原价',width:60,hidden:true,//该字段后台存入group表中的oldprice字段
                   formatter:function(value,row,index){
                       return formatterMoney(value);
                   }
               },
               {field:'limitnum',title:'促销份数',width:60,isShow:true,
                   formatter:function(value,row,index){
                       return formatterBigNum(value) ;
                   }
               },
               {field:'remainnum',title:'剩余数量',width:60,isShow:true,
                   formatter:function(value,row,index){
                       return formatterBigNumNoLen(value) ;
                   }
               },
               {field:'ptype',title:'类型',width:80,hidden:true,
                   formatter:function(value,row,index){
                       return value;
                   }
               },
               {field:'remark', title:'备注',width:150,align:'left',
                   editor:{
                       type:'validatebox'
                   }
               },
               {field:'boxnum',title:'箱装量',hidden:true},
               {field:'unitprice',title:'参考单价',hidden:true}
            ]]
       });
       //esc快捷键 关闭明细页面
       $(document).keydown(function(event){
           switch(event.keyCode) {
               case 27: //Esc
                   $("#sales-groupDetail-remark").focus();
                   $("#sales-dialog-promotionPage-content").dialog('close');
                   break;
           }
       });

       //根据客户群类型选择对应的客户名称
       function changeCustomerWidget(customertype,customerid,disabled){
           $("#customertd").empty();
           var tdstr = "",disabledstr="";
           if(disabled == "1"){
               disabledstr = 'disabled="disabled"';
           }
           if("1" == customertype){
               tdstr = '<input type="text" id="sales-customer-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("2" == customertype){
               tdstr = '<input type="text" id="sales-promotionsort-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("3" == customertype){
               tdstr = '<input type="text" id="sales-customersort-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("4" == customertype){
               tdstr = '<input type="text" id="sales-pricelist-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("5" == customertype){
               tdstr = '<input type="text" id="sales-salesarea-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("6" == customertype){
               tdstr = '<input type="text" id="sales-pcustomer-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("7" == customertype){
               tdstr = '<input type="text" id="sales-crenditrating-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("8" == customertype){
               tdstr = '<input type="text" id="sales-canceltype-promotionAddPage" name="promotionPackage.customerid" value="'+customerid+'" '+disabledstr+'/>';
           }else if("0" == customertype){
               tdstr = '<input type="text" id="sales-canceltype-promotionAddPage" name="promotionPackage.customerid" class="no_input len150" readonly="readonly"' +
                       ' value="'+customerid+'" '+disabledstr+'/>';
           }
           $(tdstr).appendTo("#customertd");
           if("1" == customertype){
               $("#sales-customer-promotionAddPage").widget({
                   referwid:"RL_T_BASE_SALES_CUSTOMER",
                   singleSelect:false,
                   isPageReLoad:false,
                   required:true,
                   width:150
               });
           }else if("2" == customertype){
               $("#sales-promotionsort-promotionAddPage").widget({
                   referwid:"RL_T_SYS_CODE_PROMOTIONSORT",
                   singleSelect:false,
                   isPageReLoad:false,
                   required:true,
                   width:150
               });
           }else if("3" == customertype){
               $("#sales-customersort-promotionAddPage").widget({
                   referwid:"RT_T_BASE_SALES_CUSTOMERSORT",
                   singleSelect:false,
                   isPageReLoad:false,
                   required:true,
                   treePName:false,
                   width:150,
                   onlyLeafCheck:false
               });
           }else if("4" == customertype){
               $("#sales-pricelist-promotionAddPage").widget({
                   referwid:"RL_T_SYS_CODE_PRICELIST",
                   singleSelect:false,
                   isPageReLoad:false,
                   required:true,
                   width:150
               });
           }else if("5" == customertype){
               $("#sales-salesarea-promotionAddPage").widget({
                   referwid:"RT_T_BASE_SALES_AREA",
                   singleSelect:false,
                   isPageReLoad:false,
                   onlyLeafCheck:false,
                   treePName:false,
                   required:true,
                   width:150
               });
           }else if("6" == customertype){
               $("#sales-pcustomer-promotionAddPage").widget({
                   referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT",
                   singleSelect:false,
                   isPageReLoad:false,
                   required:true,
                   width:150
               });
           }else if("7" == customertype){
               $("#sales-crenditrating-promotionAddPage").widget({
                   referwid:"RL_T_SYS_CODE_CREDITRATING",
                   required:true,
                   singleSelect:false,
                   isPageReLoad:false,
                   width:150
               });
           }else if("8" == customertype){
               $("#sales-canceltype-promotionAddPage").widget({
                   referwid:"RL_T_SYS_CODE_CANCELTYPE",
                   required:true,
                   singleSelect:false,
                   isPageReLoad:false,
                   width:150
               });
           }
       }

       $(function(){

    	   $("#sales-panel-promotionPage").panel({//跳转url所指向的页面
                href:promotion_url,
                cache:false,
                maximized:true,
                border:false
           });

           //按钮
           $("#sales-buttons-promotionPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/sales/promoAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							var packageId = $("#sales-backid-promotionPage").val();
							if(packageId != undefined){
								$("#sales-panel-promotionPage").panel('refresh','sales/promotionAddPage.do?act=${act}');
							}
						}
					},
					</security:authorize>
					<security:authorize url="/sales/promoSavePage.do">
					{
						type: 'button-save',
						handler: function(){
                            var id = $("#sales-promotionViewPage-id").val();
                            var datarows=$("#sales-datagrid-promotionAddPage").datagrid('getRows');
                            var begindate = $("#sales-beginDate-promotionAddPage").val();
                            var enddate = $("#sales-endDate-promotionAddPage").val();

                            if(begindate == ""){
                                $.messager.alert("提醒","请填写生效日期");
                                return false;
                            }

                            if(enddate == ""){
                                $.messager.alert("提醒","请填写截止日期");
                                return false;
                            }

                            var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
                            var customer = $("#sales-customer-promotionAddPage").val();
                            if(customertype != '0' && customer == '' ){
                                $.messager.alert("提醒","请填写客户群");
                                $("#sales-customertype-promotionAddPage").focus();
                                $("#sales-customer-promotionAddPage").focus();
                                return false;
                            }

                            if(datarows[0].groupid != undefined && datarows.length>0 && id==""  ){
                                $("#sales-goodsJson-promotionAddPage").val(JSON.stringify(datarows));
                                $("#sales-form-promotionAddPage").submit();
                                if(begindate == "" || enddate == ""){
                                    $("#sales-promotionAddPage-status").val(1);
                                }else{
                                    $("#sales-promotionAddPage-status").val(2);
                                }

                            }else if(datarows[0].groupid != undefined && datarows.length>0){
                                $("#sales-goodsJson-promotionAddPage").val(JSON.stringify(datarows));
                                 $("#sales-form-promotionEditPage").submit();

                            }else{
                                $.messager.alert("提醒","请填写商品");

                            }
						}
					},
					</security:authorize>
                    <security:authorize url="/sales/promoSaveAudit.do">
                    {
                        type: 'button-saveaudit',
                        handler: function(){
                            $("#sales-addType-promotionAddPage").val("saveaudit");

                            var id = $("#sales-promotionViewPage-id").val();
                            var datarows=$("#sales-datagrid-promotionAddPage").datagrid('getRows');
                            var begindate = $("#sales-beginDate-promotionAddPage").val();
                            var enddate = $("#sales-endDate-promotionAddPage").val();

                            if(begindate == ""){
                                $.messager.alert("提醒","请填写生效日期");
                                return false;
                            }

                            if(enddate == ""){
                                $.messager.alert("提醒","请填写截止日期");
                                return false;
                            }

                            var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
                            var customer = $("#sales-customer-promotionAddPage").val();
                            if(customertype != '0' && customer == '' ){
                                $.messager.alert("提醒","请填写客户群");
                                $("#sales-customertype-promotionAddPage").focus();
                                $("#sales-customer-promotionAddPage").focus();
                                return false;
                            }

                            if(datarows[0].groupid != undefined && datarows.length>0 && id==""  ){
                                $("#sales-goodsJson-promotionAddPage").val(JSON.stringify(datarows));
                                $("#sales-form-promotionAddPage").submit();

                            }else if(datarows[0].groupid != undefined && datarows.length>0){
                                $("#sales-goodsJson-promotionAddPage").val(JSON.stringify(datarows));
                                $("#sales-form-promotionEditPage").submit();

                            }
                        }
                    },
                    </security:authorize>
					<security:authorize url="/sales/auditPromoPageBtn.do">
					{
						type: 'button-audit',
						handler: function(){
							var packageId = $("#sales-backid-promotionPage").val();
							if( packageId == ''){
								return false;
							}

							$.messager.confirm("提醒","确定审核该促销单据信息？",function(r){
								if(r){

									loading("审核中……");

									$.ajax({
										url:'sales/auditPromotion.do',
										dataType:'json',
										type:'post',
										data:'id='+ packageId +'&type=1',
										success:function(json){
											loaded();

											if(json.flag == true){
												$.messager.alert("提醒","审核成功");
												$("#sales-panel-promotionPage").panel
                                                ('refresh', 'sales/promotionViewPage.do?act=${act}&id='+ packageId);

                                            }else{
												$.messager.alert("提醒","审核失败");
											}
										},
										error:function(){

											loaded();
											$.messager.alert("错误","审核出错");
										}
									});
								}
							});	
						}
					},
					</security:authorize>
					<security:authorize url="/sales/OppauditPromoPageBtn.do">
					{
						type: 'button-oppaudit',
						handler: function(){
							var id = $("#sales-backid-promotionPage").val();
							if(id == ''){
								return false;
							}

							$.messager.confirm("提醒","确定反审该促销单据信息？",function(r){
								if(r){
									loading("反审中..");

									$.ajax({
										url:'sales/auditPromotion.do',
										dataType:'json',
										type:'post',
										data:'id='+ id +'&type=2',
										success:function(json){
											loaded();

											if(json.flag == true){

												$.messager.alert("提醒","反审成功");
												$("#sales-panel-promotionPage").panel('refresh', 'sales/promotionEditPage.do?act=${act}&id='+ id);
											}else  if(json.auditFlag == true){
                                                $.messager.alert("提醒","该促销单已被引用，无法反审");
                                            }else{

												$.messager.alert("提醒","反审失败");
											}
										},
										error:function(){

											loaded();
											$.messager.alert("错误","反审出错");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/promotionDeleteBtn.do">
					{
						type: 'button-delete',
						handler: function(){
							var id = $("#sales-backid-promotionPage").val();
							var act = $("#sales-act-promotionPage").val();
							if(id == ''){
								return false;
							}

							$.messager.confirm("提醒","确定删除该促销活动信息？",function(r){
								if(r){

									loading("删除中..");

									$.ajax({
										url:'sales/deletePromotion.do',
										dataType:'json',
										type:'post',
										data:'ids='+ id,
										success:function(json){
											loaded();

											if(json.auditFlag == true){
												$.messager.alert("提醒","该信息已被其他信息引用，无法删除");
												return false;
											}

											if(json.flag == true){

												$.messager.alert("提醒","删除成功");
												var data = $("#sales-buttons-promotionPage").buttonWidget("removeData", id);
												if(data != null && data.addusername != undefined){
													$("#sales-backid-promotionPage").val(data.id);
													refreshPanel('sales/promotionEditPage.do?act=${act}&id='+ data.id);

                                                }else{
													parent.closeNowTab();
												}
											}else{

												$.messager.alert("提醒","删除失败");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","删除出错");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/promotionBack.do">
					{
						type: 'button-back',
						handler: function(data){
							$("#sales-backid-promotionPage").val(data.id);

							refreshPanel('sales/promotionEditPage.do?act=${act}&id='+ data.id);
						}
					},
					</security:authorize>
					<security:authorize url="/sales/promotionNext.do">
					{
						type: 'button-next',
						handler: function(data){
							$("#sales-backid-promotionPage").val(data.id);
							refreshPanel('sales/promotionEditPage.do?act=${act}&id='+ data.id);
						}
					},
					</security:authorize>
					{}
    	   		],
               buttons:[
                   <security:authorize url="/sales/promotionUncancelBtn.do">
                   <c:if test="${status == 5}">
                   {
                       id:'button-unCancel',
                       name:'取消作废',
                       iconCls:'button-oppaudit',
                       handler:function() {
                           var id = $("#sales-backid-promotionPage").val();
                           if(id == ''){
                               return false;
                           }
                           $.messager.confirm("提醒","确定取消作废该促销信息？",function(r){
                               if(r){
                                   loading("取消作废中..");
                                   $.ajax({
                                       url:'sales/promotionCancel.do',
                                       dataType:'json',
                                       type:'post',
                                       data:'ids='+ id+ '&operate=2&type='+${act} ,
                                       success:function(json){
                                           loaded();
                                           if(json.flag == true){
                                               $.messager.alert("提醒","取消作废成功。");
                                               //$("#sales-customer-status").val("2");
                                               //$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                               $("#sales-promotion-activityType").val(json.type);
                                               $("#sales-panel-promotionPage").panel('refresh', 'sales/promotionEditPage.do?id='+ id +"&act="+json.type);
                                           }else{
                                               $.messager.alert("提醒","取消作废失败<br/>"+json.msg);
                                           }
                                       },
                                       error:function(){
                                           loaded();
                                           $.messager.alert("错误","取消作废出错");
                                       }
                                   });
                               }
                           });
                       }
                   },
                   </c:if>
                   </security:authorize>
               ],
    	   	 	model:'bill',
				type:'view',
                <c:choose>
                   <c:when test="${act == '1' }">
                       taburl:'/sales/buyFreeListPage.do',
                   </c:when>
                   <c:when test="${act == '2' }">
                       taburl:'/sales/bundleListPage.do',
                   </c:when>
                   <c:when test="${act == '3' }">
                        taburl:'/sales/fullBuyFreeListPage.do',
                   </c:when>
                    <c:otherwise>
                        taburl:'/sales/buyFreeListPage.do',
                    </c:otherwise>
                 </c:choose>
                id:'${id}',
                datagrid:'sales-datagrid-promotionListPage'
    		 });
       });

       /**
        * 删除按钮
        */
       function removeDetail(){
           var row = $wareList.datagrid('getSelected');
           var rowIndex = $wareList.datagrid('getRowIndex', row);
           var groupid = row.groupid ;

           if(row.groupname == undefined){
               $.messager.alert("提醒", "请选择一条记录");
               return false;
           }

           $.messager.confirm("提醒","确定删除该条明细及其对应产品组?",function(r){
               if(r){
                   rowIndex = cursorIndex(rowIndex,row);
                   var count = -1;
                   var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');

                   for( var i = 0;i<rows.length ;i++){
                       if(rows[i].groupid == row.groupid){
                           ++ count ;
                       }
                   }

                   for(var i = 0 ;i<= count ;i++){
                       $wareList.datagrid('deleteRow', rowIndex);
                   }

               }
           });
       }

       /**
        * 添加按钮
        */
       function beginAddRow(){
           var type = $("#sales-promotion-act").val();
           var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
           for( var i = 0;i<rows.length ;i++){
               if(rows[i].groupname == undefined && (type == 1 || type == 3) ){
                    beginAddGroup1(type);
                   break;

               }else if(rows[i].groupname == undefined && type == 2){

                   beginAddGroup2(type,i);
                   break;
               }
           }
       }

       /**
        * 编辑按钮
        */
       function editRow(){
           var type = $("#sales-promotion-act").val();
           var row = $wareList.datagrid('getSelected');
           var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
           var rowIndex = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',row);

           if(row.groupname == undefined ){
               beginAddRow();

           }else if(type == 1 || type == 3){

               cursorIndex(rowIndex,row);
               $wareList.datagrid('selectRow', rowIndex);
               var count = -1;
               var rowList = [];

               for(var i=0;i<rows.length;i++){
                   if(rows[i].groupid == row.groupid){
                       rowList.push(rows[i]);
                       ++ count ;
                   }
               }

               beginEditGroup(rowIndex, rowList, type ,count);

           }else if(type == 2){
               cursorIndex(rowIndex,row);
               $wareList.datagrid('selectRow', rowIndex);
               var count = 0;
               var rowList = [];

               for( var i = 0;i<rows.length ;i++){
                   if(rows[i].groupid == row.groupid){
                       rowList.push(rows[i]);
                       ++ count ;
                   }
               }

               beginEditGroup(rowIndex, rowList, type,count);
           }
       }

       /**
        * 买赠明细查看
        */
	    function groupDetailView(type) {

		var row = $wareList.datagrid('getSelected');
		if(row.id == undefined){
			return;
		}
		$('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

		$("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
    		title:'促销信息查看(按ESC退出)',
    		maximizable:true,
    		width:600,
    		height:400,
    		closed:false,
    		modal:true,
    		cache:false,
    		resizable:true,
    		href:'sales/groupDetailViewPage.do?groupid='+row.groupid+'&acttype='+type,
    		onClose:function(){
		    	$('#sales-dialog-promotionPage-content').dialog("destroy");
		    },
    		onLoad: function(){       	
		    	$("#sales-form-GroupDetailPage").form('load', row);
		    	$("#sales-groupDetail-goodsName").val(row.goodsname);
		    	$("#sales-groupDetail-goodsid").val(row.buygoodsid);
		    	$("#sales-groupDetail-id").val(row.id);		    
		    	$("#sales-groupDetail-billid").val(row.billid);

                if(row.limitnum == "0.000000"){
                    $("#sales-groupDetail-promotionNum").val('不限数量');
                }else{
                    $("#sales-groupDetail-promotionNum").numberbox('setValue',row.limitnum);
                }

                if(row.remainnum == "0.000000" && row.limitnum == "0.000000"){
                    $("#sales-groupDetail-boxprice-hidden").val('不限数量');
                }else{
                    var containSpecial = RegExp(".0");
                    //去除小数
                    if(containSpecial.test(row.remainnum) && row.remainnum.indexOf(".0") != -1 ){
                        row.remainnum =row.remainnum.substr(0,row.remainnum.indexOf(".0"));
                    }

                    $("#sales-groupDetail-boxprice-hidden").val(row.remainnum+row.unitname);
                }

		    	var $EditTable = $('#sales-groupDetail-donatedGoods');

				$EditTable.datagrid({					
					fit : true,
					striped : true,
					method : 'post',
					rownumbers : true,
					singleSelect : true,
					showFooter : true,
					url: 'sales/viewGiveDetail.do?id='+row.billid+'&groupid='+row.groupid,
					columns: [[
                        {field:'goodsid',title:'商品编码',width:90,isShow:true},
                        {field:'goodsname',title:'商品名称',width:200},
                        {field:'brand',title:'品牌',width:60,isShow:true},
                        {field:'price',title:'单价',width:80,isShow:true},
                        {field:'unitnum',title:'赠送数量',width:60,isShow:true},
                        {field:'unitname',title:'单位',width:50,isShow:true}
                    ]],

                    onLoadSuccess: function(data) {
                        if(data.total < 3){
                            for(var i = 0; i< 3-i;i++){
                                $EditTable.datagrid('appendRow', {});
                            }

                        }

                    }
				});	
    		}
    	});		
       }

       /**
        * 捆绑明细查看
        */
       function bundleView(){
            var row = $wareList.datagrid('getSelected');
            if(row.id == undefined){
                return;
            }

            $('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

            $("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
                title:'捆绑信息查看(按ESC退出)',
                maximizable:true,
                width:600,
                height:400,
                closed:false,
                modal:true,
                cache:false,
                resizable:true,
                href:'sales/bundleDetailView.do?groupid='+row.groupid,
                onClose:function(){
                    $('#sales-dialog-promotionPage-content').dialog("destroy");
                }

            });
       }

       /**
        * 买赠新增产品组
        */
       var insertIndex = undefined;
       function beginAddGroup1(type){

           var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
    	   var customer = $("#sales-customer-promotionAddPage").val();
           var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
           var groupname = new Array();

		   if(customertype != "0" && customer == ''){
				$.messager.alert("提醒","请先选择客户群再进行添加商品信息");
				$("#sales-customertype-promotionAddPage").focus();
				$("#sales-customer-promotionAddPage").focus();
				return false;
			}
           for(var i = 0; i<rows.length; i++){
               if(rows[i].groupname != undefined){
                   groupname.push(rows[i].groupname);
               }else{
                   insertIndex = i;
                   break;
               }
           }
    	   $('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

    	   $("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
       		title:'明细添加(按ESC退出)',
       		maximizable:true,
       		width:600,
       		height:400,
       		closed:false,
       		modal:true,
       		cache:false,
       		resizable:true,
       		href:'sales/groupDetailAddPage.do?type='+ type+"&groupname="+groupname,
       		onClose:function(){
                 addSaveGroupDetail(false);
		         $('#sales-dialog-promotionPage-content').dialog("destroy");
		    }
    	   });
       }

       /**
        * 明细页的增加按钮
        */
       function addRow(){

           var rows = $EditTable.datagrid('getRows');
           var pei = donatedEditIndex();

           if(pei != -1) {
               $EditTable.datagrid('endEdit', pei);
               $EditTable.datagrid('selectRow', pei);
               var rowData = $EditTable.datagrid('getSelected');
               if(rowData.goodsname == undefined){
                   $EditTable.datagrid('cancelEdit', pei);
               }else{
                   $EditTable.datagrid('endEdit', pei);
               }
               var numAndprice = rowData.unitnum +","+rowData.price;
               getGiveGoodsInfo(pei,rowData.goodsname,numAndprice);
           }

           for(var i = 0;i<rows.length ;i++){
               if(rows[i].goodsid == undefined){
                  $EditTable.datagrid('beginEdit', i);
                   break;
               }
           }
           var editors = $EditTable.datagrid('getEditors', i);
           if (editors.length <= 0) {
               $EditTable.datagrid('appendRow', {});
               $EditTable.datagrid('beginEdit', rows.length-1);
           }
       }

       /**
        * 明细页的删除按钮
        */
       function cutRow(){

           var rows = $EditTable.datagrid('getRows');
           for(var i = 0; i < rows.length; i++) {
               var editors = $EditTable.datagrid('getEditors', i);
               if (editors.length > 0) {
                   $EditTable.datagrid('endEdit', i);
               }
           }

           var row = $EditTable.datagrid('getSelected');
           var rowIndex = $EditTable.datagrid('getRowIndex', row);

           if(rowIndex < 0) {
               $.messager.alert("提醒", "请选择记录！");
               return false;
           }else{
               $EditTable.datagrid('deleteRow', rowIndex);
           }

       }

       /**
        * 明细页的保存按钮
        */
       function saveRow(){

           var rows = $EditTable.datagrid('getRows');

           for(var i = 0; i < rows.length; i++) {
               var editors = $EditTable.datagrid('getEditors', i);

               if(editors.length > 0) {
                   $EditTable.datagrid('endEdit', i);
                   $EditTable.datagrid('selectRow', i);

                   var rowData = $EditTable.datagrid('getSelected');

                   if(rowData.unitnum == "" ||rowData.unitnum == undefined ){
                       $.messager.alert("提醒", "请填写赠送/捆绑数量");
                       $EditTable.datagrid('beginEdit', i);

                   }else {

                       if(rowData.price == undefined){
                           rowData.price = rowData.oldprice;
                       }
                       var dataId = rowData.goodsname +":"+ rowData.price ;

                       getGiveGoodsInfo(i,dataId , rowData.unitnum);
                   }
               }

           }
       }

       /**
        * 返回买赠明细页的编辑行
        */
       function donatedEditIndex() {

           var rows = $EditTable.datagrid('getRows');

           for(var i = 0; i < rows.length; i++) {
               var editors = $EditTable.datagrid('getEditors', i);
               if(editors.length > 0) {
                   return i;
               }
           }
           return -1;
       }

       /**
        * 捆绑新增产品组
        */
       function beginAddGroup2(type,rowindex){

           var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
           var customer = $("#sales-customer-promotionAddPage").val();
		  if(customertype != '0' && customer == '' ){
				$.messager.alert("提醒","请先选择客户群再进行添加商品信息");
				$("#sales-customertype-promotionAddPage").focus();
				$("#sales-customer-promotionAddPage").focus();
				return false;
		  }

    	   var row = $wareList.datagrid('getSelected');
		   insertIndex = $wareList.datagrid('getRowIndex', row);

		   $('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

    	   $("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
       		title:'捆绑明细添加(按ESC退出)',
       		maximizable:true,
       		width:600,
       		height:400,
       		closed:false,
       		modal:true,
       		cache:false,
       		resizable:true,
       		href: 'sales/groupDetailAddPage.do?type='+ type,
       		onClose:function(){
                 addSaveBundleDetail(false);
		    	$('#sales-dialog-promotionPage-content').dialog("destroy");
		    },
            onLoad:function(){
                $("#editIndex").val(rowindex);
            }

    	  });
       }

        /**
        * 根据条件返回赠送商品信息
        */
        function getGiveGoodsInfo(index,dataId,dataNum){

           var promoprice = "";
           var sear=new RegExp(',');

           if(sear.test(dataNum)){
           var depart = dataNum.split(",");
               dataNum = depart[0];
               promoprice = depart[1];
           }

           var dou = new RegExp(':');
           if(dou.test(dataId)){
            var depart = dataId.split(":");
            dataId = depart[0];
            promoprice = depart[1];
           }

           $.ajax({
                url:"sales/getGiveGoodsInfo.do?id="+dataId,
                dataType:'json',
                type:'post',
                async:false,
                success:function(json){
                    if(json.flag){
                        $.messager.alert("提醒","无法获取商品id");
                    }
                    if(promoprice == "" || promoprice == 'undefined'){
                        promoprice = json.oldprice ;
                    }
                    $('#sales-groupDetail-donatedGoods').datagrid('updateRow',{
                        index: index,
                        row: {
                            brand: json.brand,
                            oldprice: json.oldprice ,
                            goodsid: json.goodsid,
                            goodsname:json.goodsname,
                            unitnum: dataNum,
                            unitname:json.unitname,
                            price: promoprice
                        }
                    });
                }
           });
        }

   /**
    * 保存新增的买赠明细
    */
    function addSaveGroupDetail(go){

        var flag = $("#sales-form-GroupDetailPage").form('validate');

        if(flag == false){ return false; }

        if(go == false){
           $("#sales-dialog-promotionPage-content").dialog("destroy");
            return false;
        }

        var form = $("#sales-form-GroupDetailPage").serializeJSON();

		var promotionNum = $("#sales-groupDetail-promotionNum").val();
		if(promotionNum == ""){
             form.unitnum = 1;
			 form.auxnum = 0 ;
             form.auxremainder = 1;
		}

        var pei = donatedEditIndex();
        if(pei != -1){
            $('#sales-groupDetail-donatedGoods').datagrid('endEdit',pei);
            $('#sales-groupDetail-donatedGoods').datagrid('selectRow',pei);
            var row =$('#sales-groupDetail-donatedGoods').datagrid('getSelected');
            getGiveGoodsInfo(pei,row.goodsname ,row.unitnum);
        }

        groupid = autoCreateDetailId();
        form.billid = groupid ;

        var goodsJson = $("#sales-goodsId-groupDetailAddPage").goodsWidget('getObject');
        if( goodsJson == null || goodsJson == ""){
            goodsJson = $wareList.datagrid('getSelected');
        }

        form.goodsInfo = goodsJson;
        if(form.goodsid == ""){
            form.goodsid = form.goodsInfo.buygoodsid;
        }

        var groupnamelist = form.groupname.split(",") ;
        groupname = "";

        for(var i = 0 ;i<groupnamelist.length;i++){
            if(groupnamelist[i] == form.goodsInfo.name){
                groupname = form.goodsInfo.name + "("+i +")";
            }
        }

        if(groupname == ""){
            groupname = form.goodsInfo.name ;
        }
		//赠送商品信息
		var givejson = $('#sales-groupDetail-donatedGoods').datagrid('getRows');

        var a = "";
        var count = 1 ;

        for(var i = 0; a != undefined; i++){

            if(givejson[i].goodsname == undefined){
              $.messager.alert("提醒", "请填写赠送商品");
              return false;
            }

            if(i+1<givejson.length){
                a= givejson[i+1].goodsid;
            }else{
                a=undefined;
            }

            var bindex = Number(insertIndex)+Number(i);

            $wareList.datagrid('insertRow', {index: bindex,
                row: {
                    groupname:groupname,
                    goodsid: givejson[i].goodsid,
                    goodsname: givejson[i].goodsname,
                    unitnum: givejson[i].unitnum,
                    giveunitnum : givejson[i].unitnum,
                    unitname: givejson[i].unitname,
                    price : givejson[i].price,
                    unitprice : givejson[i].price,
                    brand : givejson[i].brand,
                    gtype: '2',
                    ptype : '1',
                    groupid : form.billid
                }
            });
            ++ count ;
        }

        var type = $("#sales-promotion-act").val();
         $wareList.datagrid('insertRow',{index:insertIndex,
              row:{
                  goodsid : form.goodsid,
                  goodsname : form.goodsInfo.name,
                  groupname : groupname,
                  unitprice : form.baseprice,
                  price : form.baseprice,
                  limitnum : form.limitnum,
                  remainnum :form.limitnum,
                  buygoodsid : form.goodsid,
                  printtimes : '0',
                  status : '1',
                  remark :form.remark,
                  unitnum : form.unitnum ,
                  unitname : form.unitname,
                  auxremainder: form.auxremainder,
                  auxnum : form.auxnum,
                  gtype : '1',
                  ptype :type,
                  boxprice : form.boxprice,
                  auxunitname : form.auxunitname,
                  groupid : form.billid,
                  boxnum : form.boxnum
              }
          });

         $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                  {index: insertIndex,field: 'groupid',rowspan: count });

		$("#sales-dialog-promotionPage-content").dialog("destroy");

       }

       /**
        * 捆绑明细页 套餐信息框获取焦点时的事件
        */
       function isFocus(){

           var pei = donatedEditIndex();
           var totalNum = "";
           var totalPrice = "";
           var oldPrice = "";
           var a = "";
           var bundleInfo = $EditTable.datagrid('getRows');

           if(pei != -1){
               saveRow();
           }
           for(var i = 0 ; a != undefined;i++){

               a = bundleInfo[i+1].goodsid;
               if(totalNum == "" ){

                   totalNum = bundleInfo[i].unitnum;
                   totalPrice = bundleInfo[i].price;
                   oldPrice = bundleInfo[i].oldprice;

               } else {

                   totalNum = Number(totalNum) + Number(bundleInfo[i].unitnum);
                   totalPrice = Number(totalPrice) +  Number(bundleInfo[i].price);
                   oldPrice = Number(oldPrice) +  Number(bundleInfo[i].oldprice);
               }
           }

           totalPrice = totalPrice.toFixed(2);
           $("#sales-groupDetail-bundlePrice").val(totalPrice);
           $("#sales-groupDetail-bundleNum").val(totalNum);
           $("#oldprice").val(oldPrice);
       }
        var changeNum = 0;

       /**
        *自动给产品组添加唯一的ID值
        */
        function autoCreateDetailId(){
            var detailId = $("#sales-promotion-detailId").val();//后台是否有设置单据编号规则
            var rows = $("#sales-datagrid-promotionAddPage").datagrid("getRows");
            var testID = "";

            for(var i = 0 ; i<rows.length; i ++){

                if(rows[i].groupid == undefined & rows[i+1].groupid == undefined){

                    testID = undefined ;
                    break;

                }else if(rows[i].groupid != undefined & rows[i+1].groupid == undefined){

                    testID = rows[i].groupid ;
                    break;
                }
            }
            if(testID != undefined && testID.length > 20){
                $.messager.alert("提醒","产品组编号过长,请另建促销单据");
                return false;
            }
            if(detailId == "" && testID == undefined ){
                detailId ="group-"+new Date().getTime();
            }else if(detailId == ""){//设置单据编号规则后，每条记录依次编号
                if(testID.length == 20){
                    changeNum = testID.substring(testID.length-2,testID.length) ;
                    testID = testID.substring(0,testID.length-2);
                    ++changeNum;
                    if(changeNum.length > 2){
                        $.messager.alert("提醒","产品组编号过长,请另建促销单据");
                    }else{
                        detailId =testID+changeNum;
                    }
                }else{
                    changeNum = testID.substring(testID.length-1,testID.length) ;
                    testID = testID.substring(0,testID.length-1);
                    ++changeNum;
                    detailId =testID+changeNum;
                }
            }else{

                changeNum ++ ;
                detailId =detailId +changeNum;
            }

            return detailId;

        }

       /**
        *捆绑明细信息保存
        */
       function addSaveBundleDetail(flag){

           var validate = $("#sales-form-bundlegroupDetail").form('validate');
           if(validate == false){
               return false;
           }

           if(flag == false){
              $("#sales-dialog-promotionPage-content").dialog("destroy");
              return false;
           }

    	   var billName = $("#sales-groupDetail-groupName").val();
   			if(billName == ""){
	   			$.messager.alert("提醒", "请填写促销名称");
	   			 $("#sales-groupDetail-groupName").focus();
	   			return false;
   		    }

           var groupid = $("#sales-groupDetail-groupid").val();
           var data = $("#sales-datagrid-promotionAddPage").datagrid('getRows');

           for(var i = 0 ; i<data.length ; i++){
               if(data[i].groupname == billName && data[i].groupid != groupid){
                   $.messager.alert("提醒", "促销名称重复，请重新填写");
                   $("#sales-groupDetail-groupName").focus();
                   return false;
               }
           }
           var limitnum = $("#sales-groupDetail-limitNum").val();

           if(limitnum == ""){

               $.messager.alert("提醒", "请填写促销数量");
               $("#sales-groupDetail-saleNum").focus();
               return false;

           }

          isFocus();

          var form = $("#sales-form-bundlegroupDetail").serializeJSON();
          var totalprice = $("#sales-groupDetail-bundlePrice").val();
          var totaloldprice = $("#sales-groupDetail-bundleOldPrice").val();
          var unitnum = $("#sales-groupDetail-bundleNum").val();//捆绑数量

          form.unitnum = unitnum;
          form.totalprice = totalprice;
          form.totaloldprice = totaloldprice;
          form.limitnum = limitnum;

          //获取修改行
          insertIndex = $("#editIndex").val();
          if(groupid == "" ){
             groupid = autoCreateDetailId();

          }else{
             //如果是修改当前单据数据 则删除原有数据
             var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');

             for(var i = rows.length-1 ;i>= 0  ;i--){

                 if(rows[i].groupid == groupid){
                     var rowindex = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',rows[i]);
                     $wareList.datagrid('deleteRow', rowindex);
                 }
             }
          }
          form.groupid = groupid;

          var bundleJson = $('#sales-groupDetail-donatedGoods').datagrid('getRows');

          if(bundleJson[0].goodsid == undefined ){
              $('#sales-groupDetail-donatedGoods').datagrid('beginEdit',0);
              return false ;
          }

          var a = "";
          var count = $("#jsonCount").val();
          var jsonlength = 0;//返回的商品条数

          for(var i = 0 ;  a != undefined ; i++){
              if(i + 1 < bundleJson.length){
                  a = bundleJson[i+1].goodsid;
              }else{
                  a = undefined;
              }
              ++jsonlength;
          }

          a = "";
   		  for(var i = 0; a != undefined; i++){

              if(i+1 < bundleJson.length){
                  a = bundleJson[i+1].goodsid;
              }else{
                  a = undefined;
              }

              var pindex = Number(insertIndex) + Number(i) ;
              //为了保持单据中各产品组增删改查的规律性，回显时操作做以下分类
              if(i<count && jsonlength <= count ){
                    $wareList.datagrid('insertRow',{index:pindex ,
                        row:{
                            groupid : form.groupid,
                            groupname : form.groupname,
                            goodsid : bundleJson[i].goodsid,
                            goodsname : bundleJson[i].goodsname,
                            goodsoldprice : bundleJson[i].oldprice,
                            goodsprice : bundleJson[i].price,
                            unitnum : bundleJson[i].unitnum,
                            unitname : bundleJson[i].unitname,
                            totalprice : form.totalprice,
                            totaloldprice: form.totaloldprice,
                            limitnum : form.limitnum,
                            ptype : '2',
                            unitprice : bundleJson[i].unitprice,
                            boxnum : bundleJson[i].boxnum
                        }
                    });
                    if(i == 0){
                        $wareList.datagrid('updateRow',{index:pindex ,
                            row: { remark: form.remark}});
                    }
              }else if(jsonlength >count && i<count){
                    $wareList.datagrid('insertRow',{index:pindex ,
                        row:{
                            groupid : form.groupid,
                            groupname : form.groupname,
                            goodsid : bundleJson[i].goodsid,
                            goodsname : bundleJson[i].goodsname,
                            goodsoldprice : bundleJson[i].oldprice,
                            goodsprice : bundleJson[i].price,
                            unitnum : bundleJson[i].unitnum,
                            unitname : bundleJson[i].unitname,
                            totalprice : form.totalprice,
                            totaloldprice: form.totaloldprice,
                            limitnum : form.limitnum,
                            unitprice : bundleJson[i].unitprice,
                            boxnum : bundleJson[i].boxnum ,
                            ptype : '2'

                        }
                    });
                    if(i == 0){
                        $wareList.datagrid('updateRow',{index:pindex ,
                            row:{ remark :form.remark}});
                    }

              }else if(jsonlength >count){

                    $wareList.datagrid('insertRow',{index:pindex ,
                        row:{
                            groupid : form.groupid,
                            groupname : form.groupname,
                            goodsid : bundleJson[i].goodsid,
                            goodsname : bundleJson[i].goodsname,
                            goodsoldprice : bundleJson[i].oldprice,
                            goodsprice : bundleJson[i].price,
                            unitnum : bundleJson[i].unitnum,
                            unitname : bundleJson[i].unitname,
                            totalprice : form.totalprice,
                            totaloldprice: form.totaloldprice,
                            limitnum : form.limitnum,
                            unitprice : bundleJson[i].unitprice,
                            boxnum : bundleJson[i].boxnum ,
                            ptype : '2'
                        }
                    });

                    if(i == 0){
                        $wareList.datagrid('updateRow',{index:pindex ,
                            row:{ remark :form.remark}});
                    }
              }
   	      }

          $wareList.datagrid('mergeCells', {index: insertIndex,field: 'groupid',rowspan: jsonlength });

   		  $("#sales-dialog-promotionPage-content").dialog('destroy');
       }

       /**
        *根据单据类型进行修改
        */
       function beginEditGroup(rowIndex,row,type,count){
           if(row == null){
               $.messager.alert("提醒", "请选择一条记录");
               return false;
           }
           if(type == 1 || type ==3){//买赠 或 满赠 修改
              buyFreeEdit(row,rowIndex,count,type);

           }else if(type == 2){//捆绑的修改
              bundleEdit(row,rowIndex,count);

           }
       }

       /**
        *捆绑类型明细修改方法
        */
       function bundleEdit(row,rowIndex,count){

           $('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

           $("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
               title: '捆绑明细修改(按ESC退出)',
               maximizable: true,
               width: 600,
               height: 400,
               closed: false,
               modal: true,
               cache: false,
               resizable: true,
               href: 'sales/bundleEditDetail.do',
               onClose: function () {
                   $('#sales-dialog-promotionPage-content').dialog("destroy");

               },
               onLoad: function () {
                   $("#editIndex").val(rowIndex);
                   $("#sales-groupDetail-groupid").val(row[0].groupid);
                   $("#sales-groupDetail-groupName").val(row[0].groupname);
                   $("#sales-groupDetail-bundlePrice").numberbox('setValue',row[0].totalprice);//套餐价格放在totalprice字段中
                   $("#sales-groupDetail-limitNum").numberbox('setValue',row[0].limitnum);
                   $("#sales-groupDetail-unitNum").numberbox('setValue',row[0].unitnum);
                   $("#sales-groupDetail-remark").val(row[0].remark);
                   $("#jsonCount").val(count);
                   $("#editIndex").val(rowIndex);

                   var $EditTable = $("#sales-groupDetail-donatedGoods");

                   for(var i = 0;i< row.length ;i++){

                       $EditTable.datagrid('insertRow',{index : i ,
                           row :{goodsid:row[i].goodsid,
                                 goodsname:row[i].goodsname,
                                 unitprice:row[i].unitprice,
                                 unitname:row[i].unitname,
                                 unitnum:row[i].unitnum,
                                 oldprice:row[i].goodsoldprice,
                                 boxnum:row[i].boxnum,
                                 price:row[i].goodsprice
                           }
                       });
                   }
               }
           });
       }

       /**
        *买赠类型明细修改方法
        */
       function buyFreeEdit(row,rowIndex,count,type){

           $('<div id="sales-dialog-promotionPage-content"></div>').appendTo('#sales-dialog-promotionAddPage');

           var title = "";
           if(type == 1){
               title = "买赠明细修改(按ESC退出)";
           }else if(type == 3){
               title = "满赠明细修改(按ESC退出)";
           }

           $("#sales-dialog-promotionPage-content").dialog({ //弹出窗口
               title:title,
               maximizable: true,
               width: 600,
               height: 400,
               closed: false,
               modal: true,
               cache: false,
               resizable: true,
               href:'sales/groupDetailEdit.do?acttype='+type,
               onClose:function(){
                   $('#sales-dialog-promotionPage-content').dialog("destroy");
               },
               onLoad: function() {
                   if(type == 1){
                       getNumberBox("sales-groupDetail-price").focus();
                   }else{
                       $("#sales-groupDetail-promotionNum").focus();
                   }

                   var containSpecial = RegExp(".0");
                   //去除小数
                   if (containSpecial.test(row[0].limitnum) && row[0].limitnum.indexOf(".0") != -1) {
                       row[0].limitnum = row[0].limitnum.substr(0, row[0].limitnum.indexOf(".0"));
                       row[0].remainnum = row[0].limitnum;
                   }
                   if (row[0].limitnum == '0' || row[0].limitnum == "") {
                       row[0].remainnum = '不限数量';
                   } else if (/.*[\u4e00-\u9fa5]+.*$/.test(row[0].remainnum)) {//判断是否包含汉字
                       row[0].remainnum = row[0].remainnum;
                   } else {
                       row[0].remainnum = row[0].remainnum + "份";
                   }

                   $("#sales-loading-groupDetailAddPage").removeClass("img-loading").html
                   ("商品编码：<font color='green'>" + row[0].buygoodsid +
                           "</font>&nbsp;促销可用量：<font color='green'>" + row[0].remainnum + "</font>");

                   $("#sales-groupDetail-auxremainder-hidden").val(row[0].auxunitname);
                   $("#sales-groupDetail-auxnum-hidden").val(row[0].unitname);
                   $("#sales-groupDetail-remark").val(row[0].remark);
                   $("#sales-goodsId-groupDetailAddPage").goodsWidget("setValue", row[0].buygoodsid);
                   $("#sales-goodsId-groupDetailAddPage").goodsWidget("setText", row[0].goodsname);
                   $("#sales-groupDetail-auxremainder").val(row[0].auxremainder);
                   $("#sales-groupDetail-auxnum").val(row[0].auxnum);
                   if(type == 1){
                       $("#sales-groupDetail-boxprice").numberbox('setValue', row[0].boxprice);
                       $("#sales-groupDetail-price").numberbox('setValue', row[0].price);
                       $("#sales-groupDetail-boxprice-hidden").numberbox('setValue', row[0].boxnum);
                   }

                   $("#sales-groupDetail-promotionNum").val(row[0].unitnum);
                   $("#sales-goodsId-hidden").val(row[0].buygoodsid);
                   $("#groupid").val(row[0].groupid);

                   $("#grougEditIndex").val(rowIndex);
                   $("#jsonCount").val(count);

                   if (row[0].limitnum == '0' || row[0].limitnum == "") {
                       $("#sales-groupDetail-totalPromotionNum").val('不限数量');
                   }else if (row[0].limitnum == '不限数量') {
                       $("#sales-groupDetail-totalPromotionNum").numberbox('setValue', 0);
                   }else{
                       $("#sales-groupDetail-totalPromotionNum").numberbox('setValue', row[0].limitnum);
                   }

                   for (var i = 1; i < row.length; i++) {
//                       if (containSpecial.test(row[i].unitnum)) {
//                           row[i].unitnum = row[i].unitnum.substr(0, row[i].unitnum.indexOf(".0"));
//                       }
//
//                       if (row[i].unitnum == "") {
//                           row[i].unitnum = row[i].giveunitnum;
//                       }

                       var $EditTable = $('#sales-groupDetail-donatedGoods');
                       $EditTable.datagrid('insertRow', {index: i - 1,
                           row: {goodsid: row[i].goodsid, goodsname: row[i].goodsname, brand: row[i].brand,
                               price: row[i].unitprice, unitnum: row[i].unitnum, unitname: row[i].unitname
                           }
                       });
                   }

                   formaterNumSubZeroAndDot();

                   $("#sales-form-GroupDetailEditPage").form('validate');
               }
           });
       }

       /**
        *买赠明细修改方法
        */
       function editSaveGroupDetail(flag){

          var flag = $("#sales-form-GroupDetailEditPage").form('validate');
          if(flag==false){
              return false;
          }

          var pei = donatedEditIndex();
          if(pei != -1){
              $('#sales-groupDetail-donatedGoods').datagrid('endEdit',pei);
              $('#sales-groupDetail-donatedGoods').datagrid('selectRow',pei);
              var row =$('#sales-groupDetail-donatedGoods').datagrid('getSelected');
              getGiveGoodsInfo(pei,row.goodsname ,row.unitnum);
          }

          //返回购买商品的信息
          var form = $("#sales-form-GroupDetailEditPage").serializeJSON();
          //修改改变的数量值
          var auxnum = $("#sales-groupDetail-auxnum").val();
          var auxremainder = $("#sales-groupDetail-auxremainder").val();
          form.auxnum = auxnum;
          form.auxremainder = auxremainder;

          //获取修改行
          insertIndex = $("#grougEditIndex").val();
          var goodsJson = $("#sales-form-GroupDetailEditPage").goodsWidget('getObject');
          if(goodsJson == null || goodsJson == ""){
              goodsJson = $wareList.datagrid('getSelected');
          }

          form.groupname = goodsJson.groupname;
          form.id = goodsJson.buygoodsid;

          //赠送商品信息
          var givejson = $('#sales-groupDetail-donatedGoods').datagrid('getRows');
          var count =  form.count ;
          var a = "";
          var jsonLength = 0 ;

          var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');

          for(var i = rows.length-1 ;i>= 0  ;i--){
              if(rows[i].groupname == form.groupname && rows[i].gtype == '2'){

                  var rowindex = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',rows[i]);
                  $wareList.datagrid('deleteRow', rowindex);
              }
          }

          for(var i = 0; a != undefined ; i++){
              a = givejson[i+1];
              var bindex = Number(insertIndex )+Number(1)+Number(i);

              if(givejson[i].goodsid != undefined){
                  $wareList.datagrid('insertRow', {index: bindex,
                      row: {
                          groupname:form.groupname,
                          goodsid: givejson[i].goodsid,
                          goodsname: givejson[i].goodsname,
                          unitnum: givejson[i].unitnum,
                          giveunitnum : givejson[i].unitnum,
                          unitname: givejson[i].unitname,
                          price : givejson[i].price,
                          unitprice : givejson[i].price,
                          brand : givejson[i].brand,
                          gtype: '2',
                          ptype : '1',
                          billid : form.billid
                      }
                  });

              }else{

                  --bindex;
              }

          }
          form.limitnum = $("#sales-groupDetail-totalPromotionNum").val();

          if(form.limitnum == '不限数量'){
              form.limitnum = 0 ;
          }

          $wareList.datagrid('updateRow',{index:insertIndex,
              row:{
                  price : form.price,
                  limitnum : form.limitnum,
                  remainnum : form.limitnum,
                  buygoodsid : form.buygoodsid,
                  remark :form.remark,
                  billid : form.billid,
                  unitnum : form.unitnum ,
                  unitname : form.unitname,
                  auxremainder: form.auxremainder,
                  auxnum : form.auxnum,
                  boxprice : form.boxprice,
                  auxunitname : form.auxunitname
              }
          });

          var merindex = Number(bindex) - Number(insertIndex) + Number(1);

          $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                  {index: insertIndex,field: 'groupid',rowspan: merindex });

          $("#sales-dialog-promotionPage-content").dialog("destroy");

       }
    /**
    *更新panel
    */
    function refreshPanel(url){
   		$("#sales-panel-promotionPage").panel('refresh', url);
   	}

    /**
    *双击合并行时游离到该产品组第一行
    */
    function cursorIndex(rowIndex,rowData){

       var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');

       for(var i = 0 ;i<= rowIndex ;i++){

           if(rows[i].groupname == rowData.groupname){//筛选行
               rowIndex = i;
               continue;
           }

           if(rowData.groupname == undefined && rows[i].groupname == undefined ){
               rowIndex = i ;
               continue;
           }
       }

        return rowIndex;
     }

       
						
	</script>
</body>
</html>