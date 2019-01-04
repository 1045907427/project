<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>手动分配其他明细数据</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="dataother-form-pumpAssign">
            <table  border="0" class="box_table" style="width: 500px">
                <tr>
                    <td width="120">对接方:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataother-markid-pumpAssign" name="name" style="width: 250px" class="easyui-validatebox"  required="required"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">仓库修改方式:</td>
                    <td style="text-align: left;" >
                        <select id="dataother-storageEditType-pumpAssign" name="storageEditType" style="width:250px;">
                            <option value="0" selected="selected">全部修改</option>
                            <option value="1" >只修改未匹配的</option>

                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="120">业务日期：</td>
                    <td><input type="text" id="dataother-businessdate-pumpAssign" class="Wdate" name="businessdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" style="width: 250px;"/></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="dataother-savegoon-pumpAssign" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var storageid="";
    $(function (){
        //厂家选择
        $("#dataother-markid-pumpAssign").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:250,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
        });

        $("#dataother-savegoon-pumpAssign").click(function(){
            pumpAssigned();
        });
    })

</script>
</body>
</html>

