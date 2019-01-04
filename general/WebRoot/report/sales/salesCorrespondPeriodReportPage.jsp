<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售情况基础统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-salesCorrespondPeriod">
    <thead>
    <tr>
        <th data-options="field:'customerid',width:60,sortable:true" rowspan="2">客户编号</th>
        <th data-options="field:'customername',width:130" rowspan="2">客户名称</th>
        <th data-options="field:'pcustomerid',width:130,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.pcustomername;
					}" rowspan="2">总店名称</th>
        <th data-options="field:'salesuser',width:80,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesusername;
					}" rowspan="2">客户业务员</th>
        <th data-options="field:'salesarea',width:80,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesareaname;
					}" rowspan="2">销售区域</th>
        <th data-options="field:'salesdept',width:80,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.salesdeptname;
					}" rowspan="2">销售部门</th>
        <th data-options="field:'branddept',width:80,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.branddeptname;
					}" rowspan="2">品牌部门</th>
        <th data-options="field:'branduser',width:80,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.brandusername;
					}" rowspan="2">品牌业务员</th>
        <th data-options="field:'brandid',width:130,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.brandname;
					}" rowspan="2">品牌名称</th>
        <th data-options="field:'goodsid',width:60,sortable:true,hidden:true" rowspan="2">商品编码</th>
        <th data-options="field:'goodsname',width:130,hidden:true" rowspan="2">商品名称</th>
        <th data-options="field:'barcode',width:90,hidden:true" rowspan="2">条形码</th>
        <th data-options="field:'goodssort',width:130,sortable:true,hidden:true,
					formatter:function(value,rowData,rowIndex){
						return rowData.goodssortname;
					}" rowspan="2">商品分类</th>
        <th data-options="field:'supplierid',width:70,sortable:true,hidden:true" rowspan="2">供应商编码</th>
        <th data-options="field:'suppliername',width:150,hidden:true" rowspan="2">默认供应商名称</th>
        <th colspan="3">销售箱数</th>
        <th colspan="3">销售金额</th>
        <th colspan="3">毛利金额</th>
        <th colspan="3">毛利率%</th>
        <th colspan="3">回笼金额</th>
        <th colspan="3">回笼毛利金额</th>
        <th colspan="3">回笼毛利率%</th>
    </tr>
    <tr>
        <%--销售箱数 --%>
        <th data-options="field:'qqsaletotalbox',width:80,sortable:true,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">前期</th>
        <th data-options="field:'saletotalbox',width:80,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">本期</th>
        <th data-options="field:'saletotalboxgrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>

    <%--销售金额 --%>
        <th data-options="field:'qqsaleamount',width:80,sortable:true,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">前期</th>
        <th data-options="field:'saleamount',width:80,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">本期</th>
        <th data-options="field:'salegrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>

        <%--毛利金额 --%>
        <th data-options="field:'qqsalegrossamount',width:80,sortable:true,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">前期</th>
        <th data-options="field:'salegrossamount',width:80,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">本期</th>
        <th data-options="field:'salegrossgrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>

        <%--毛利率 --%>
        <th data-options="field:'qqsalegrossrate',width:80,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">前期</th>
        <th data-options="field:'salegrossrate',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">本期</th>
        <th data-options="field:'salegrossrategrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>

        <%--回笼金额 --%>
        <th data-options="field:'qqwriteoffamount',width:80,sortable:true,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">前期</th>
        <th data-options="field:'writeoffamount',width:80,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">本期</th>
        <th data-options="field:'writeoffgrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>

        <%--回笼毛利金额 --%>
        <th data-options="field:'qqwriteoffgrossamount',width:80,sortable:true,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">前期</th>
        <th data-options="field:'writeoffgrossamount',width:80,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value);
							}
							return '';
						}">本期</th>
        <th data-options="field:'writeoffgrossgrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return formatterMoney(value)+'%';
							}
							return '';
						}">增长比例</th>

        <%--回笼毛利率 --%>
        <th data-options="field:'qqwriteoffgrate',width:80,align:'right',
						styler: function(value,row,index){
							if (null != value){
								return 'background-color:#E4F7CE;';
							}
						},
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">前期</th>
        <th data-options="field:'writeoffgrate',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">本期</th>
        <th data-options="field:'writeoffgrategrowth',width:80,align:'right',
						formatter:function(value,rowData,rowIndex){
							if(value!=null){
								return value+'%';
							}
							return '';
						}">增长比例</th>
    </tr>
    </thead>
</table>
<div id="report-toolbar-salesCorrespondPeriod">
    <a href="javaScript:void(0);" id="report-advancedQuery-salesCorrespondPeriod" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
    <security:authorize url="/report/buy/salesCorrespondPeriodExport.do">
        <a href="javaScript:void(0);" id="report-advancedQuery-exportSalesCorrespondPeriodData" class="easyui-linkbutton" iconCls="button-export" plain="true" title="[Alt+E]导出">导出</a>
    </security:authorize>
</div>

<div style="display: none">
    <div id="report-dialog-advancedQueryPage">
        <form action="" id="report-query-form-salesCorrespondPeriod" method="post">
            <table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
                <tr>
                    <td>本期日期:</td>
                    <td><input type="text" name="bqstartdate" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="bqenddate" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                </tr>
                <tr>
                    <td>前期日期:</td>
                    <td><input type="text" name="qqstartdate" value="${prevyearfirstday }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="qqenddate" value="${prevyearcurday }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                </tr>
                <tr>
                    <td>商品:</td>
                    <td><input type="text" name="goodsid" id="report-query-goodsid"/></td>
                </tr>
                <tr>
                    <td>商品分类:</td>
                    <td>
                        <input id="report-query-goodssort" type="text" name="goodssort"/>
                    </td >
                </tr>
                <tr>
                    <td>供应商:</td>
                    <td><input type="text" name="supplierid" id="report-query-supplierid"/></td>
                </tr>
                <tr>
                    <td>品牌:</td>
                    <td><input type="text" name="brandid" id="report-query-brandid"/></td>
                </tr>
                <tr>
                    <td>品牌部门:</td>
                    <td><input type="text" name="branddept" id="report-query-branddept"/></td>
                </tr>
                <tr>
                    <td>品牌业务员:</td>
                    <td><input type="text" name="branduser" id="report-query-branduser"/></td>
                </tr>
                <tr>
                    <td>客户业务员:</td>
                    <td><input type="text" name="salesuser" id="report-query-salesuser"/></td>
                </tr>
                <tr>
                    <td>客户名称</td>
                    <td><input type="text" id="report-query-customerid" name="customerid"/></td>
                </tr>
                <tr>
                    <td>总店名称:</td>
                    <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                </tr>
                <tr>
                    <td>销售区域:</td>
                    <td>
                        <input id="report-query-salesarea" type="text" name="salesarea"/>
                    </td >
                </tr>
                <tr>
                    <td>小计列：</td>
                    <td>
                        <div style="margin-top:3px">
                            <div style="line-height: 25px;">
                                <input type="checkbox" class="groupcols" value="customerid"/>客户
                                <input type="checkbox" class="groupcols" value="pcustomerid"/>总店
                                <input type="checkbox" class="groupcols" value="salesarea"/>销售区域
                                <input type="checkbox" class="groupcols" value="salesdept"/>销售部门
                            </div>
                            <div style="line-height: 25px; margin-top: 2px;">
                                <input type="checkbox" class="groupcols" value="goodsid"/>商品
                                <input type="checkbox" class="groupcols" value="goodssort"/>商品分类
                                <input type="checkbox" class="groupcols" value="branddept"/>品牌部门
                                <input type="checkbox" class="groupcols" value="branduser"/>品牌业务员
                            </div>
                            <div style="line-height: 25px; margin-top: 2px;">
                                <input type="checkbox" class="groupcols" value="brandid"/>品牌
                                <input type="checkbox" class="groupcols" value="supplierid"/>供应商
                                <input type="checkbox" class="groupcols" value="salesuser"/>客户业务员
                            </div>
                            <input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var report_advanced_cols = null;
    var initQueryJSON = $("#report-query-form-salesCorrespondPeriod").serializeJSON();
    var loadData = false;
    $(function(){
        $(".groupcols").click(function(){
            var cols = "";
            $("#report-query-groupcols").val("");
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                }
            });
            if(cols==''){
                cols='customerid';
            }
            $("#report-query-groupcols").val(cols);
        });
        $("#report-datagrid-salesCorrespondPeriod").datagrid({
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            toolbar:'#report-toolbar-salesCorrespondPeriod',
            url:'report/sales/showSalesCorrespondPeriodReportData.do',
            queryParams:initQueryJSON,
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                //初始化时不能查询数据，设置200毫秒时间后可以查询数据
                return loadData;
            },
        }).datagrid("columnMoving");

        $("#report-query-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:210,
            onlyLeafCheck:false,
            singleSelect:false
        });
        $("#report-query-goodssort").widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            width:210,
            onlyLeafCheck:false,
            singleSelect:false
        });
        $("#report-query-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:210,
            onlyLeafCheck:false,
            singleSelect:false
        });
        //品牌
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:'210',
            onlyLeafCheck:true
        });
        //品牌部门
        $("#report-query-branddept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            singleSelect:false,
            width:'210',
            onlyLeafCheck:true
        });
        //品牌业务员
        $("#report-query-branduser").widget({
            referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
            singleSelect:false,
            width:'210',
            onlyLeafCheck:true
        });
        //客户业务员
        $("#report-query-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect:false,
            width:'210',
            onlyLeafCheck:true
        });
        $("#report-query-customerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            width:210,
            singleSelect:false
        });
        $("#report-query-pcustomerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width:210,
            singleSelect:false
        });
        $("#report-query-salesarea").widget({
            referwid:'RT_T_BASE_SALES_AREA',
            width:210,
            onlyLeafCheck:false,
            singleSelect:false,
            allSelect:false
        });
        //导出
        $("#report-advancedQuery-exportSalesCorrespondPeriodData").Excel('export',{
            queryForm: "#report-query-form-salesCorrespondPeriod",
            type:'exportUserdefined',
            name:'销售同比环比情况统计表',
            url:'report/sales/exportSalesCorrespondPeriodReportData.do'
        });
        //高级查询
        $("#report-advancedQuery-salesCorrespondPeriod").click(function(){
            $("#report-dialog-advancedQueryPage").dialog({
                maximizable:true,
                resizable:true,
                title: '销售同比环比情况统计高级查询',
                top:30,
                width: 500,
                height: 530,
                closed: false,
                cache: false,
                modal: true,
                buttons:[
                    {
                        text:'确定',
                        handler:function(){
                            searchAdvancedQueryForm();
                        }
                    },
                    {
                        text:'重置',
                        handler:function(){
                            $("#report-query-goodsid").widget('clear');
                            $("#report-query-goodssort").widget('clear');
                            $("#report-query-supplierid").widget('clear');
                            $("#report-query-brandid").widget('clear');
                            $("#report-query-branddept").widget('clear');
                            $("#report-query-branduser").widget('clear');
                            $("#report-query-salesuser").widget('clear');
                            $("#report-query-customerid").widget('clear');
                            $("#report-query-pcustomerid").widget('clear');
                            $("#report-query-salesarea").widget('clear');
                            $("#report-query-form-salesCorrespondPeriod").form("reset");
                            $(".groupcols").each(function(){
                                if($(this).attr("checked")){
                                    $(this)[0].checked = false;
                                }
                            });
                            $("#report-query-groupcols").val("customerid");


                            $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customerid");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customername");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "pcustomerid");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesuser");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesarea");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesdept");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsid");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsname");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "barcode");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "supplierid");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "suppliername");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "brandid");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branduser");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branddept");
                            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodssort");

                            $("#report-datagrid-salesCorrespondPeriod").datagrid('loadData',{total:0,rows:[]});
                        }
                    }
                ],
                onClose:function(){
                }
            });
        });
    });
    function setColumn(qtype){
        if(qtype==null || typeof(qtype)=="undefined"){
            qtype=0;
        }else if(qtype!=1){
            type=0;
        }
        var cols = "";
        if(qtype==1){
            cols=$("#report-advancedQuery-groupcols").val();
        }else{
            cols=$("#report-query-groupcols").val();
        }
        if(cols!="" && cols!='customerid'){
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "customerid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "customername");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "pcustomerid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesuser");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesarea");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesdept");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsname");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "barcode");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "brandid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branduser");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branddept");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodssort");
        }else{
            $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customerid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customername");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "pcustomerid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesuser");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesarea");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "salesdept");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodsname");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "barcode");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "supplierid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "suppliername");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "brandid");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branduser");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "branddept");
            $("#report-datagrid-salesCorrespondPeriod").datagrid('hideColumn', "goodssort");
            return false;
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col=='customerid'){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customerid");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "customername");
            }else if(col=="pcustomerid"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "pcustomerid");
            }else if(col=="salesuser"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "salesuser");
            }else if(col=="salesarea"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "salesarea");
            }else if(col=="salesdept"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "salesdept");
            }else if(col=="goodsid"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "goodsid");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "goodsname");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "barcode");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "brandid");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "goodssort");
            }else if(col=="brandid"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "brandid");
            }else if(col=="branduser"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "branduser");
            }else if(col=="branddept"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "branddept");
            }else if(col=="supplierid"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "supplierid");
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "suppliername");
            }else if(col=="goodssort"){
                $("#report-datagrid-salesCorrespondPeriod").datagrid('showColumn', "goodssort");
            }
        }
    }
    function searchAdvancedQueryForm(){
        setColumn(0);
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#report-query-form-salesCorrespondPeriod").serializeJSON();
        $("#report-datagrid-salesCorrespondPeriod").datagrid("load",queryJSON);
        //导出
        $("#report-advancedQuery-exportSalesCorrespondPeriodData").Excel('export',{
            queryForm: "#report-query-form-salesCorrespondPeriod",
            type:'exportUserdefined',
            name:'销售同比环比情况统计表',
            url:'report/sales/exportSalesCorrespondPeriodReportData.do'
        });
        $("#report-dialog-advancedQueryPage").dialog('close');
    }
    //设置200毫秒后可以查询
    setTimeout(function(){
        loadData = true;
    },200);
</script>
</body>
</html>