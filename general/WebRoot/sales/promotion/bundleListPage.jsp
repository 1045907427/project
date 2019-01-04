<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title> 捆绑促销页面</title>
    <%@include file="/include.jsp" %>   
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="sales-queryDiv-promotionListPage" style="padding:0px;height:auto">
    			<div class="buttonBG" id="sales-buttons-promotionListPage" style="height:26px;"></div>
    			<form id="sales-queryForm-promotionListPage">
                    <table class="querytable">
                        <tr>
                            <td>业务日期：</td>
                            <td>
                            <input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                                到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                            <td>客 户 群：</td>
                            <td class="tdinput">
                                <input type="text"  name="customertype"  id="sales-customertype-promotionListPage" style="width: 183px;" />
                            </td>
                            <td>状态：</td>
                            <td class="tdinput">
                                <select name="status" style="width:163px;">
                                    <option></option>
                                    <option value="2" selected="selected">保存</option>
                                    <option value="3">审核通过</option>
                                    <option value="4">关闭</option>
                                    <option value="5">中止</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>商 品：</td>
                            <td class="tdinput"><input type="text" id="sales-goodsid-promotionListPage" name="goodsid" style="width:225px" /></td>
                            <td>客户群名称：</td>
                            <td  id="customertd"><input id="sales-customer-promotionListPage"  style="width:183px" name="customerid" /></td>
                            <td rowspan="3" colspan="2" style="padding-left: 2px">
                                <a href="javascript:;" id="sales-queay-promotionListPage"  class="button-qr">查询</a>
                                <a href="javaScript:;" id="sales-resetQueay-promotionListPage"  class="button-qr">重置</a>
                                <%--<span id="sales-queryAdvanced-promotionListPage"></span>--%>
                            </td>
                        </tr>
                    </table>
    			</form>
    		</div>
    		<table id="sales-datagrid-promotionListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
    $(function(){
        var queryJSON = $("#sales-queryForm-promotionListPage").serializeJSON();

        //客户群
        $("#sales-customertype-promotionListPage").widget({
            name:'t_sales_promotion_package',
            col:'customertype',
            singleSelect:false,
            width:150,
            onLoadSuccess:function(){
                $("#sales-customertype-promotionListPage").prepend("<option value='' selected></option>");
            },
            onSelect:function(data){
                changeCustomerWidget(data.id,"","0");
            }
        });
        //客户群名称
        $("#sales-customer-promotionListPage").widget({
            referwid:"RL_T_BASE_SALES_CUSTOMER",
            singleSelect:true,
            width:150,
            onlyLeafCheck:true
        });
        function changeCustomerWidget(customertype,customerid,disabled){
            $("#customertd").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            if("1" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("2" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("3" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("4" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("5" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("6" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("7" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("8" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" value="'+customerid+'" '+disabledstr+'/>';
            }else if("0" == customertype){
                tdstr = '<input type="text" id="sales-customer-promotionListPage" name="customerid" class="no_input" readonly="readonly"' +
                        ' value="'+customerid+'" '+disabledstr+'/>';
            }
            $(tdstr).appendTo("#customertd");
            if("1" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_BASE_SALES_CUSTOMER",
                    singleSelect:true,
                    width:150
                });
            }else if("2" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_SYS_CODE_PROMOTIONSORT",
                    width:150
                });
            }else if("3" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RT_T_BASE_SALES_CUSTOMERSORT",
                    singleSelect:true,
                    width:150,
                    onlyLeafCheck:false
                });
            }else if("4" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_SYS_CODE_PRICELIST",
                    width:150
                });
            }else if("5" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RT_T_BASE_SALES_AREA",
                    singleSelect:true,
                    onlyLeafCheck:false,
                    width:150
                });
            }else if("6" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT",
                    singleSelect:true,
                    width:150
                });
            }else if("7" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_SYS_CODE_CREDITRATING",
                    width:150
                });
            }else if("8" == customertype){
                $("#sales-customer-promotionListPage").widget({
                    referwid:"RL_T_SYS_CODE_CANCELTYPE",
                    width:150
                });
            }
        }

        //商品
        $("#sales-goodsid-promotionListPage").goodsWidget();
    
	controlQueryAndResetByKey("sales-queay-promotionListPage","sales-resetQueay-promotionListPage");
	
	$("#sales-queay-promotionListPage").click(function(){
   		var queryJSON = $("#sales-queryForm-promotionListPage").serializeJSON();
   		$("#sales-datagrid-promotionListPage").datagrid('load', queryJSON);
	});
	$("#sales-resetQueay-promotionListPage").click(function(){
        if("" == $("#sales-customer-promotionListPage").val()){
            $("#sales-customer-promotionListPage").removeClass("no_input");
            $("#sales-customer-promotionListPage").widget({
                referwid:"RL_T_BASE_SALES_CUSTOMER",
                singleSelect:true,
                width:150
            });
        }else{
            $("#sales-customer-promotionListPage").widget("clear");
        }
		$("#sales-queryForm-promotionListPage").form("reset");
        $("#sales-customertype-promotionAddPage").val();
		$("#sales-customer-promotionListPage").widget("clear");
        $("#sales-goodsid-promotionListPage").goodsWidget("clear");
        $("#sales-groupname-promotionListPage").val();
        var queryJSON = $("#sales-queryForm-promotionListPage").serializeJSON();
		$("#sales-datagrid-promotionListPage").datagrid('load', queryJSON);
	});
	$("#sales-queryAdvanced-promotionListPage").advancedQuery({ //通用查询
		//查询针对的表
 		name:'t_sales_promotion_package',
 		//查询针对的表格id
 		datagrid:'sales-datagrid-promotionListPage',
 		plain:true
	});
	//按钮控件
	$("#sales-buttons-promotionListPage").buttonWidget({
		initButton:[
					{},
					<security:authorize url="/sales/promoAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							top.addTab('sales/promotionPage.do?act=2', "捆绑促销新增");
						}
					},
					</security:authorize>
					<security:authorize url="/sales/promotionViewPage.do">
					{
						type: 'button-view',
						handler: function(){
							var con = $("#sales-datagrid-promotionListPage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}
                            top.addOrUpdateTab('sales/promotionPage.do?type=edit&act=2&id='+con.id, '捆绑促销查看');
						}
					},
					</security:authorize>
                    <security:authorize url="/sales/promotionDeleteBtn.do">
                    {
                        type: 'button-delete',
                        handler: function(){
                            var con = $("#sales-datagrid-promotionListPage").datagrid('getChecked');
                            if(con.length == 0){
                                $.messager.alert("提醒","请选择一条记录");
                                return false;
                            }
                            var ids = "" ;
                            for(var i = 0;i < con.length; ++i){
                                if(ids == ""){
                                    ids = con[i].id;
                                }else{
                                    ids = ids +"," + con[i].id;
                                }
                            }
                            $.messager.confirm("提醒","确定删除该促销活动信息？",function(r){
                                if(r){
                                    loading("删除中..");
                                    $.ajax({
                                        url:'sales/deletePromotion.do',
                                        dataType:'json',
                                        type:'post',
                                        data:'ids='+ ids,
                                        success:function(json){
                                            loaded();
                                            if(json.flag == true){
                                                $.messager.alert("提醒", "删除成功.<br/>"+json.msg);
                                                $("#sales-datagrid-promotionListPage").datagrid('reload');
                                            }else{
                                                $.messager.alert("提醒", "删除失败.<br/>"+json.msg);
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
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'t_sales_promotion_package',
                            plain:true,
                            //查询针对的表格id
                            datagrid:'sales-datagrid-promotionListPage'
                        }
                    },
            {}
		],
        buttons:[
            {},
            <security:authorize url="/sales/promotionCancelBtn.do">
            {
                id:'button-promotionCancel',
                name:'作废',
                iconCls:'button-delete',
                handler:function() {
                    var con = $("#sales-datagrid-promotionListPage").datagrid('getSelected');
                    if(con == null){
                        $.messager.alert("提醒","请选择至少一条非中止的记录");
                        return false;
                    }
                    $.messager.confirm("提醒","确定作废选中的捆绑促销信息？",function(r){
                        if(r){
                            var rows = $("#sales-datagrid-promotionListPage").datagrid('getChecked');
                            var ids = "";
                            var failnum = 0;
                            for(var i=0;i<rows.length;i++){
                                if(ids=="" && rows[i].status != '5'){
                                    ids = rows[i].id;
                                }else if(rows[i].status != '5'){
                                    ids += ","+rows[i].id;
                                }else{
                                    failnum += 1 ;
                                }
                            }
                            if(ids != ''){
                                $.ajax({
                                    url:'sales/promotionCancel.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'ids='+ ids +'&type=2&operate=1',
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒","作废成功："+json.success+"<br/>作废失败："+(json.failure+failnum));
                                            $("#sales-datagrid-promotionListPage").datagrid('reload');
                                        }
                                        else{
                                            $.messager.alert("提醒","作废失败");
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","作废失败");
                                    }
                                });
                            }else{
                                $.messager.alert("错误","请选择非中止状态的记录进行作废");
                            }
                        }
                    });
                }
            },
            </security:authorize>
            {}
        ],
		model:'bill',
		type:'list',
		datagrid:'sales-datagrid-promotionListPage',
		tname:'t_sales_promotion_package'
	
	});
	
    //表头标题
    var bundleSaleJson = $("#sales-datagrid-promotionListPage").createGridColumnLoad({
		name :'t_sales_promotion_package',
		frozenCol : [[
            {field:'idok',checkbox:true,isShow:true}
	    			]],
		commonCol : [[{field:'id',title:'活动编号',width:120, align: 'left'},
					  {field:'businessdate',title:'业务日期',width:80,align:'left'},					 
					  {field:'customertype',title:'客户群',width:80,align:'left',
						  	formatter:function(value,row,index){
                                return getSysCodeName('customertype', value);
						    }  
					  },						
					  {field:'customerid',title:'客户群编码',width:80,align:'left'},
					  {field:'customername',title:'客户群名称',width:200,align:'left',isShow:true},
					  {field:'ptype',title:'活动类型',width:80,align:'left',
						  formatter:function(value,row,index){
					        	if(value == 1){
					        		return '买赠';
					        	}else if(value == 2){
					        		return '捆绑';
					        	}else if(value == 3){
                                    return '满赠';
                                }
						  }
					  },
					  {field:'begindate',title:'生效日期',width:80,align:'left'},
					  {field:'enddate',title:'截止日期',width:80,align:'left'},
					  {field:'status',title:'状态',width:60,align:'left',
					  	formatter:function(value,row,index){
				        	return getSysCodeName('status', value);
					    }
					  },
					  {field:'addusername',title:'制单人',width:80,sortable:true},
					  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
					  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
					  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
					  {field:'remark',title:'备注',width:150,sortable:true,hidden:true}
		              ]]
	});
	$("#sales-datagrid-promotionListPage").datagrid({ 
 		authority:bundleSaleJson,
 		frozenColumns:bundleSaleJson.frozen,
		columns:bundleSaleJson.common,
 		fit:true,
 		title:"",
 		method:'post',
 		rownumbers:true,
 		pagination:true,
 		idField:'id',
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
		url: 'sales/getPromotionList.do?ptype=2',
        queryParams:queryJSON,
		toolbar:'#sales-queryDiv-promotionListPage',
	    onDblClickRow:function(index, data){
			top.addOrUpdateTab('sales/promotionPage.do?type=edit&act=2&id='+data.id, '捆绑促销查看');
    	}
	}).datagrid("columnMoving");
});


    </script>    
    </body>
    </html>