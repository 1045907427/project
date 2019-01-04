<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商商品批量生成页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div id="ebshop-toorbar-ebgoodsAddMutiPage" style="padding:0px;">
            <form id="ebshop-form-ebgoodsAddMutiPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>编码:</td>
                        <td><input type="text" name="id" style="width:120px" /></td>
                        <td>名称:</td>
                        <td><input type="text" name="name" style="width:200px" /></td>
                        <td>商品品牌:</td>
                        <td><input id="ebshop-brand-ebgoodsAddMutiPage" type="text" name="brandid" style="width:120px" /></td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td><input id="ebshop-deptid-ebgoodsAddMutiPage" type="text" name="deptid" style="width:120px" /></td>
                        <td>所属供应商:</td>
                        <td><input id="ebshop-supplierid-ebgoodsAddMutiPage" type="text" name="defaultsupplier" style="width:200px" /></td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="ebshop-query-ebgoodsAddMutiPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ebshop-reload-ebgoodsAddMutiPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="ebshop-table-ebgoodsAddMutiPage"></table>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" id="ebshop-addmuti-ebgoodsAddMutiPage" value="继续添加"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        //商品品牌
        $("#ebshop-brand-ebgoodsAddMutiPage").widget({
            width:120,
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:true
        });

        //默认供应商
        $("#ebshop-supplierid-ebgoodsAddMutiPage").supplierWidget({});

        //所属部门
        $("#ebshop-deptid-ebgoodsAddMutiPage").widget({
            width:120,
            referwid:'RL_T_BASE_DEPATMENT',
            singleSelect:true
        });

        var ebgoodsAddMutiJson=$("#ebshop-table-ebgoodsAddMutiPage").createGridColumnLoad({
            name:'base_goods_info',
            frozenCol:[[]],
            commonCol:[[{field:'id',title:'编码',resizable:true,sortable:true},
                {field:'name',title:'名称',width:250,sortable:true},
                {field:'barcode',title:'条形码',width:95,sortable:true},
                {field:'mainunit',title:'单位',width:45,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.mainunitName;
                    }
                },
                {field:'boxnum',title:'箱装量',width:45,isShow:true},
                {field:'field01',title:'采购价',resizable:true,sortable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                <c:forEach var="list" items="${priceList}" varStatus="status">
                <c:if test="${(status.index) < pricenum}">
                {field:'${list.code}',title:'${list.codename}',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.priceList != null && rowData.priceList[${status.index}] != undefined){
                            return formatterMoney(rowData.priceList[${status.index}].taxprice);
                        }else{
                            return "";
                        }
                    }
                },
                </c:if>
                </c:forEach>
                {field:'brand',title:'商品品牌',width:70,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.brandName;
                    }
                },
                {field:'defaultsort',title:'默认分类',width:80,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.defaultsortName;
                    }
                },
                {field:'storageid',title:'默认仓库',width:60,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.storageName;
                    }
                },
                {field:'storagelocation',title:'默认库位',width:60,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.storagelocationName;
                    }
                },
                {field:'defaultbuyer',title:'默认采购员',width:70,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.defaultbuyerName;
                    }
                },
                {field:'defaultsaler',title:'默认业务员',width:70,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.defaultsalerName;
                    }
                },
                {field:'defaultsupplier',title:'所属供应商',width:160,sortable:true,hidden:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.defaultsupplierName;
                    }
                },
                {field:'state',title:'状态',width:50,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.stateName;
                    }
                }
            ]]
        });

        $('#ebshop-table-ebgoodsAddMutiPage').datagrid({
            authority:ebgoodsAddMutiJson,
            frozenColumns:[[{field:'ck',checkbox:true}]],
            columns:ebgoodsAddMutiJson.common,
            fit:true,
            title:'',
            toolbar:'#ebshop-toorbar-ebgoodsAddMutiPage',
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            onBeforeLoad:function(){}
        }).datagrid("columnMoving");

        //查询
        $("#ebshop-query-ebgoodsAddMutiPage").click(function(){
            var queryJSON = $("#ebshop-form-ebgoodsAddMutiPage").serializeJSON();
            $("#ebshop-table-ebgoodsAddMutiPage").datagrid({
                url:'basefiles/goodsInfoListPage.do',
                pageNumber:1,
                queryParams:queryJSON
            }).datagrid("columnMoving");
        });

        //重置按钮
        $("#ebshop-reload-ebgoodsAddMutiPage").click(function(){
            $("#ebshop-form-ebgoodsAddMutiPage")[0].reset();
            $("#ebshop-brand-ebgoodsAddMutiPage").widget('clear');
            $("#ebshop-supplierid-ebgoodsAddMutiPage").supplierWidget('clear');
            $("#ebshop-deptid-ebgoodsAddMutiPage").widget('clear');
            $("#ebshop-table-ebgoodsAddMutiPage").datagrid('clearChecked');
            $("#ebshop-table-ebgoodsAddMutiPage").datagrid("loadData",[]);
        });

        $("#ebshop-addmuti-ebgoodsAddMutiPage").click(function(){
            var rows = $("#ebshop-table-ebgoodsAddMutiPage").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选商品!");
                return false;
            }
            $.messager.confirm("提醒","是否确定批量生成电商商品?",function(r){
                if(r){
                    var idstr = "";
                    for(var i=0;i<rows.length;i++){
                        if(idstr == ""){
                            idstr = rows[i].id;
                        }else{
                            idstr += "," + rows[i].id;
                        }
                    }
                    loading("生成中..");
                    $.ajax({
                        url :'ebgoods/addMutiEbGoods.do',
                        type:'post',
                        dataType:'json',
                        data:{idstr:idstr},
                        success:function(retJSON){
                            loaded();
                            var msg = "";
                            if(retJSON.sucnum != 0){
                                msg = "电商商品批量生成 编号："+retJSON.sucids+"成功;";
                            }
                            if(retJSON.unsucnum != 0){
                                if("" == msg){
                                    msg = "电商商品批量生成 编号："+retJSON.unsucids+"失败;";
                                }else{
                                    msg += "<br>" + "电商商品批量生成 编号："+retJSON.unsucids+"失败;";
                                }
                            }
                            if(msg != ""){
                                $.messager.alert("提醒",msg);
                            }
                            var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();
                            $('#ebshop-datagrid-ebgoodsListPage').datagrid("load",queryJSON);
                            $("#ebshop-table-ebgoodsAddMutiPage").datagrid('clearChecked');
                            $("#ebshop-dialog-addmuti-ebgoodsListPage1").dialog('close');
                        }
                    });
                }
            });
        });
    });
</script>
</body>
</html>
