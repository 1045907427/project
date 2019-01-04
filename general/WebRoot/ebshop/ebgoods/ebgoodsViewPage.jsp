<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商商品查看页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="ebshop-form-ebgoodsAddPage" action="" method="post">
        <div data-options="region:'north',border:false" style="height:140px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="3px" cellspacing="3px">
                <tr>
                    <td class="len110 left">编号：</td>
                    <td class="len165">
                        <input type="text" id="ebshop-id-ebgoodsAddPage" class="len150 easyui-validatebox" name="ebGoods.id" value="${ebGoods.id}" data-options="required:true" disabled="disabled"/>
                        <input type="hidden" name="ebGoods.oldid" value="${ebGoods.id}"/>
                    </td>
                    <td class="len110 left">电商类型：</td>
                    <td class="len165">
                        <select name="ebGoods.etype" style="width: 150px;" disabled="disabled">
                            <option></option>
                            <c:forEach items="${etypeList }" var="list">
                                <c:choose>
                                    <c:when test="${list.code == ebGoods.etype}">
                                        <option value="${list.code }" selected="selected">${list.codename }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.code }">${list.codename }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="len110 left">状态：</td>
                    <td class="len165">
                        <select name="ebGoods.state" style="width: 150px;" disabled="disabled">
                            <option></option>
                            <c:forEach items="${stateList }" var="list">
                                <c:choose>
                                    <c:when test="${list.code == ebGoods.state}">
                                        <option value="${list.code }" selected="selected">${list.codename }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.code }">${list.codename }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len110 left">名称：</td>
                    <td colspan="3"><input type="text" disabled="disabled" class="easyui-validatebox" style="width: 437px;" name="ebGoods.name" value="${ebGoods.name}" data-options="required:true"/></td>
                    <td class="len110 left">卖家昵称：</td>
                    <td><input type="text" id="ebshop-sellernick-ebgoodsAddPage" readonly="readonly" name="ebGoods.seller_nick" value="${ebGoods.seller_nick}"/></td>
                </tr>
                <tr>
                    <td class="len110 left">电商商品数字编号：</td>
                    <td><input type="text" class="len150" disabled="disabled" name="ebGoods.num_iid" value="${ebGoods.num_iid}"/></td>
                    <td class="len110 left">sku编号：</td>
                    <td><input type="text" class="len150" disabled="disabled" name="ebGoods.sku_id" value="${ebGoods.sku_id}"/></td>
                    <td class="len110 left">sku属性：</td>
                    <td><input type="text" class="len150" disabled="disabled" name="ebGoods.properties" value="${ebGoods.properties}"/></td>
                </tr>
                <tr>
                    <td class="len110 left">金额：</td>
                    <td><input type="text" disabled="disabled" id="ebshop-amount-ebgoodsAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebGoods.price" value="${ebGoods.price}"/></td>
                    <td class="len110 left">自动更新商品库存：</td>
                    <td>
                        <select name="ebGoods.isupdate" class="len150" disabled="disabled">
                            <option></option>
                            <option value="0" <c:if test="${ebGoods.isupdate == '0'}">selected="selected" </c:if>>否</option>
                            <option value="1" <c:if test="${ebGoods.isupdate == '1'}">selected="selected" </c:if>>是</option>
                        </select>
                    </td>
                    <td class="len110 left">是否上架：</td>
                    <td>
                        <select name="ebGoods.islisting" class="len150" disabled="disabled">
                            <option></option>
                            <option value="0" <c:if test="${ebGoods.islisting == '0'}">selected="selected" </c:if>>否</option>
                            <option value="1" <c:if test="${ebGoods.islisting == '1'}">selected="selected" </c:if>>是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len110 left">备注：</td>
                    <td colspan="5"><input type="text" disabled="disabled" style="width: 724px;" name="ebGoods.remark" value="${ebGoods.remark}"/></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <input type="hidden" name="goodsjson" id="ebshop-goodsJson-ebgoodsAddPage" />
            <table id="ebshop-datagrid-ebgoodsAddPage"></table>
            <div id="ebshop-dialog-detial-ebgoodsAddPage" ></div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(function(){
        loadwidgetdown();

        $("#ebshop-datagrid-ebgoodsAddPage").datagrid({ //销售商品明细信息编辑
            authority:ebgoodsListJson,
            columns: ebgoodsListJson.common,
            frozenColumns: ebgoodsListJson.frozen,
            border: true,
            fit: true,
            rownumbers: true,
            striped:true,
            singleSelect: true,
            data: JSON.parse('${goodsJson}'),
            onSortColumn:function(sort, order){
                var rows = $(this).datagrid("getRows");
                var dataArr = [];
                for(var i=0;i<rows.length;i++){
                    if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                        dataArr.push(rows[i]);
                    }
                }
                dataArr.sort(function(a,b){
                    if(order=="asc"){
                        return a[sort]>b[sort]?1:-1
                    }else{
                        return a[sort]<b[sort]?1:-1
                    }
                });
                $(this).datagrid("loadData",dataArr);
                return false;
            },
            onLoadSuccess: function(data){
                if(data.rows.length<12){
                    var j = 12-data.rows.length;
                    for(var i=0;i<j;i++){
                        $(this).datagrid('appendRow',{});
                    }
                }else{
                    $(this).datagrid('appendRow',{});
                }
            }
        }).datagrid('columnMoving');
    });
</script>
</body>
</html>
