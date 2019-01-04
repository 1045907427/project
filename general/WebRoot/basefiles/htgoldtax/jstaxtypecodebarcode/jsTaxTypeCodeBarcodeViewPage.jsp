<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>税收分类关联商品条形码查看</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div align="center" style="padding: 10px;">
            <form method="post" id="htgoldtax-form-editJsTaxTypeCode">
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td width="100px" align="right">税收分类编码:</td>
                        <td align="left">
                            <input type="text" id="htgoldtax-form-addJsTaxTypeCodeBarcode-jstypeid" name="jsTaxTypeCodeBarcode.jstypeid" value="${jsTaxTypeCodeBarcode.jstypeid}" readonly="readonly" style="width:200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">商品条形码:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCodeBarcode.barcode" value="${jsTaxTypeCodeBarcode.barcode}" readonly="readonly" style="width:200px;" maxlength="20"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
            $("#htgoldtax-form-addJsTaxTypeCodeBarcode-jstypeid").widget({
                referwid: 'RL_T_BASE_JSTAXTYPECODE',
                singleSelect: true,
                width: '200',
                required: true,
                readonly:true
            });
        });
</script>
</body>
</html>
