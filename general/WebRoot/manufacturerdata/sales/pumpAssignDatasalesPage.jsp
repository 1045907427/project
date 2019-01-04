<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>添加厂家信息</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="datasales-form-pumpAssign">
            <table  border="0" class="box_table" style="width: 500px">
                <tr>
                    <td width="120">对接方:</td>
                    <td style="text-align: left;">
                        <input type="text" id="datasales-markid-pumpAssign" name="name" style="width: 250px" class="easyui-validatebox"  required="required"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">客户修改方式:</td>
                    <td style="text-align: left;" >
                        <select id="datasales-customerEditType-pumpAssign" name="customerEditType" style="width:250px;">
                            <option value="0" selected="selected">全部修改</option>
                            <option value="1" >只修改未匹配的</option>

                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="120">选择客户:</td>
                    <td style="text-align: left;" id="customerid">
                        <input type="text" id="datasales-customerid-pumpAssign" name="customerid" style="width: 250px" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">业务日期：</td>
                    <td><input type="text" id="datasales-businessdate-pumpAssign" class="Wdate" name="businessdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" style="width: 250px;"/></td>
                </tr>
                <input type="hidden" id="hidden-customertype-pumpAssign"  />
                <input type="hidden" id="hidden-ctypeids-pumpAssign"  />
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="datasales-savegoon-pumpAssign" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var ctypeids="";
    $(function (){
        //厂家选择
        $("#datasales-markid-pumpAssign").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:250,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
            onSelect: function(data) {
                ctypeids=data.ctypeids;
                changeCustomerWidget(data.customertype,"","0");
            }
        });

        function changeCustomerWidget(sourcetype,sourceid,disabled){
            $("#customerid").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="datasales-customerid-pumpAssign" name="customerid" style="width: 250px" value="'+sourceid+'"  '+disabledstr+'/>';
            $(tdstr).appendTo("#customerid");
            if("1" == sourcetype){
                $("#datasales-customerid-pumpAssign").customerWidget({
                    width:250,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                });
            }else if("2" == sourcetype){
                $("#datasales-customerid-pumpAssign").customerWidget({
                    width:250,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesdeptid',op:'in',value:ctypeids}],
                });
            }else if("3" == sourcetype){
                $("#datasales-customerid-pumpAssign").customerWidget({
                    width:250,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesarea',op:'in',value:ctypeids}],
                });
            }else if("4" == sourcetype){
                $("#datasales-customerid-pumpAssign").customerWidget({
                    width:250,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'customersort',op:'in',value:ctypeids}],
                });
            }
        }

        $("#datasales-savegoon-pumpAssign").click(function(){
            pumpAssigned();
        });
    })

</script>
</body>
</html>

