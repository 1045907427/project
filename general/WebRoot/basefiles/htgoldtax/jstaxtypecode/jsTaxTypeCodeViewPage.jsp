<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品和服务税收分类与编码查看</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div align="center" style="padding: 10px;">
            <form method="post" id="htgoldtax-form-editJsTaxTypeCode">
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td width="100px" align="right">编码:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCode.id" value="${jsTaxTypeCode.id}" style="width:200px;" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="100px" align="right">合并编码:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCode.mergeid" value="${jsTaxTypeCode.mergeid}" style="width:200px;" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">货物和劳务名称:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCode.goodsname" value="${jsTaxTypeCode.goodsname}" style="width:200px;" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">说明:</td>
                        <td align="left">
                            <textarea rows="0" cols="0" name="jsTaxTypeCode.description" style="width:200px;height:50px" disabled="disabled">${jsTaxTypeCode.description}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">关键字:</td>
                        <td align="left">
                            <textarea rows="0" cols="0" name="jsTaxTypeCode.keyword" style="width:200px;height:50px" disabled="disabled">${jsTaxTypeCode.keyword}</textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
