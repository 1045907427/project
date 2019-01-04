<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>手动分配采购明细数据</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="datapurchase-form-pumpAssign">
            <table  border="0" class="box_table" style="width: 500px">
                <tr>
                    <td width="120">对接方:</td>
                    <td style="text-align: left;">
                        <input type="text" id="datapurchase-markid-pumpAssign" name="name" style="width: 250px" class="easyui-validatebox"  required="required"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">供应商修改方式:</td>
                    <td style="text-align: left;" >
                        <select id="datapurchase-supplierEditType-pumpAssign" name="supplierEditType" style="width:250px;">
                            <option value="0" selected="selected">全部修改</option>
                            <option value="1" >只修改未匹配的</option>

                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="120">选择供应商:</td>
                    <td style="text-align: left;" id="supplierid">
                        <input type="text" id="datapurchase-supplierid-pumpAssign" name="supplierid" style="width: 250px" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">业务日期：</td>
                    <td><input type="text" id="datapurchase-businessdate-pumpAssign" class="Wdate" name="businessdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" style="width: 250px;"/></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="datapurchase-savegoon-pumpAssign" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var supplierid="";
    $(function (){
        //厂家选择
        $("#datapurchase-markid-pumpAssign").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:250,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
            onSelect: function(data) {
                supplierid=data.supplierid;
                changeCustomerWidget("1","","0");
            }
        });

        function changeCustomerWidget(sourcetype,sourceid,disabled){
            $("#supplierid").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="datapurchase-supplierid-pumpAssign" name="supplierid" style="width: 150px" value="'+sourceid+'"  '+disabledstr+'/>';
            $(tdstr).appendTo("#supplierid");
            if("1" == sourcetype){
                $("#datapurchase-supplierid-pumpAssign").widget({
                    referwid:'RL_T_BASE_BUY_SUPPLIER',
                    width:250,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'id',op:'in',value:supplierid}],
                });
            }
        }
        $("#datapurchase-savegoon-pumpAssign").click(function(){
            pumpAssigned();
        });
    })

</script>
</body>
</html>

