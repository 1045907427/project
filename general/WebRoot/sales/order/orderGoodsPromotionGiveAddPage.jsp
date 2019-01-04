<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售订单商品买赠新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
            <div id="sales-div-orderGoodsPromotionGiveAddPage">
  			<form id="sales-form-orderGoodsPromotionGiveAddPage">
                <table cellpadding="5" cellspacing="5">
                    <tr>
                        <td colspan="4">商品编码：${promotionPackageGroupDetail.goodsid}；
                            名称：${promotionPackageGroupDetail.goodsname}；
                            满<span style="color: #0a8059;">${promotionPackageGroupDetail.auxnumdetail}</span>赠送一份赠品
                            <input id="sales-goodsid-orderGoodsPromotionGiveAddPage" type="hidden" name="goodsid" value="${promotionPackageGroupDetail.goodsid}"/>
                            <input id="sales-groupid-orderGoodsPromotionGiveAddPage" type="hidden" name="groupid" value="${promotionPackageGroupDetail.groupid}"/>
                            <input id="sales-goodsinfo-orderGoodsPromotionGiveAddPage" type="hidden" value="<c:out value="${goodsInfoJson}"></c:out>"/>
                            <input id="sales-goodsinfo-orderGoodsPromotionGiveAddPage" type="hidden" name="usablenum" value="${promotionPackageGroupDetail.usablenum}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>数量</td>
                        <td><input id="sales-unitnum-orderGoodsPromotionGiveAddPage" class="easyui-validatebox len150 goodsNum" value="0" name="unitnum"  data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" /></td>
                        <td>辅数量：</td>
                        <td><input id="sales-auxnum-orderGoodsPromotionGiveAddPage" name="auxnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" data-options="validType:'integer'" /><span id="sales-auxunitname-orderGoodsPromotionGiveAddPage">${promotionPackageGroupDetail.auxunitname}</span>
                            <input id="sales-overnum-orderGoodsPromotionGiveAddPage" name="overnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" data-options="validType:'intOrFloatNum[${decimallen}]'" /><span id="sales-unitname-orderGoodsPromotionGiveAddPage">${promotionPackageGroupDetail.unitname}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>含税单价：</td>
                        <td><input id="sales-taxprice-orderGoodsPromotionGiveAddPage"  class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" onfocus="this.select();frm_focus('taxprice');" onblur="frm_blur('taxprice');" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> value="${promotionPackageGroupDetail.price}"/> </td>
                        <td>含税金额：</td>
                        <td><input class="len150 readonly no_input easyui-numberbox" id="sales-taxamount-orderGoodsPromotionGiveAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" name="taxamount" /></td>
                    </tr>
                    <tr>
                        <td>含税箱价：</td>
                        <td><input class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="boxprice" onfocus="this.select();frm_focus('boxprice');" onblur="frm_blur('boxprice');" id="sales-boxprice-orderGoodsPromotionGiveAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> value="${promotionPackageGroupDetail.boxPrice}"/> </td>
                        <td>箱装量：</td>
                        <td><input name="boxnum" id="sales-boxnum-orderGoodsPromotionGiveAddPage" type="text" class="easyui-numberbox no_input len150 readonly" data-options="precision:0" readonly="readonly" value="${promotionPackageGroupDetail.goodsInfo.boxnum}"/></td>
                    </tr>
                    <tr>
                        <td>未税单价：</td>
                        <td><input class="len150 easyui-validatebox readonly" name="notaxprice" id="sales-notaxprice-orderGoodsPromotionGiveAddPage"  validType="intOrFloat" readonly="readonly" value="${promotionPackageGroupDetail.notaxprice}"/> </td>
                        <td>未税金额：</td>
                        <td><input class="len150 readonly no_input easyui-numberbox" id="sales-notaxamount-orderGoodsPromotionGiveAddPage" readonly="readonly" name="notaxamount" data-options="precision:6,groupSeparator:','" /></td>
                    </tr>
                    <tr>
                        <td>税种：</td>
                        <td><input class="len150 readonly" readonly="readonly" name="taxtypename" value="${promotionPackageGroupDetail.goodsInfo.defaulttaxtypeName}"/><input id="sales-taxtype-orderGoodsPromotionGiveAddPage" type="hidden" name="taxtype" value="${promotionPackageGroupDetail.goodsInfo.defaulttaxtype}"/> </td>
                        <td>税额：</td>
                        <td><input class="len150 readonly no_input easyui-numberbox" id="sales-tax-orderGoodsPromotionGiveAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
                    </tr>
                    <tr>
                        <td>单位：</td>
                        <td>
                            主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" value="${promotionPackageGroupDetail.unitname}"/><input type="hidden" name="unitid" />
                            辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" value="${promotionPackageGroupDetail.auxunitname}"/><input type="hidden" name="auxunitid" /></td>
                        <td>条形码：</td>
                        <td><input class="len150 readonly" readonly="readonly" name="barcode" value="${promotionPackageGroupDetail.goodsInfo.barcode}"/></td>
                    </tr>
                    <tr>
                        <td>备注：</td>
                        <td colspan="3"><input type="text" style="width: 400px;" name="remark" value="${promotionPackageGroupDetail.remar}"/></td>
                    </tr>
                </table>
                <input id="sales-oldprice-orderGoodsPromotionGiveAddPage" type="hidden" name="oldprice" />
		    </form>
                <h1>赠送商品
                    <c:if test="${promotionPackageGroup.limitnum>0}">
                        ,剩余赠品份数<fmt:formatNumber value="${promotionPackageGroup.remainnum}" type="number" pattern="#0"/>份
                    </c:if>
                </h1>
            </div>
            <table id="sales-table-orderGoodsPromotionGiveAddPage"></table>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
	  			<input type="button" value="继续添加" name="savegoon" id="sales-savegoon-orderGoodsPromotionGiveAddPage" />
  			</div>
  		</div>
  	</div>
  	
    <script type="text/javascript">
        $(function(){
            $("#sales-table-orderGoodsPromotionGiveAddPage").datagrid({
                fit : true,
                striped : true,
                rownumbers : true,
                singleSelect : true,
                columns: [[
                    {field:'goodsid',title:'商品编号',width:80},
                    {field:'goodsname',title:'商品名称',width:150 },
                    {field:'brand',title:'品牌',width:50,
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.goodsInfo != null){
                                return rowData.goodsInfo.brandName;
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.isdiscount!='1' && rowData.goodsInfo != null){
                                return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'unitname',title:'单位',width:40},
                    {field:'auxnumdetail',title:'每份赠送数量',width:80,align:'right'},
                    {field:'givecount',title:'赠送份数',width:80,align:'right'},
                    {field:'givenumdetail',title:'合计赠送数量',width:80,align:'right'}
                ]],
                showFooter: true,
                striped:true,
                idField:'id',
                singleSelect: false,
                checkOnSelect:true,
                selectOnCheck:true,
                toolbar:'#sales-div-orderGoodsPromotionGiveAddPage',
                data: JSON.parse('${listJson}')
            });
            $("#sales-unitnum-orderGoodsPromotionGiveAddPage").change(function(){
                giveunitnumChange(1);
            });
            $("#sales-auxnum-orderGoodsPromotionGiveAddPage").change(function(){
                giveunitnumChange(2);
            });
            $("#sales-overnum-orderGoodsPromotionGiveAddPage").change(function(){
                giveunitnumChange(2);
            });
            $("#sales-taxprice-orderGoodsPromotionGiveAddPage").change(function(){
                givepriceChange("1", '#sales-taxprice-orderGoodsPromotionGiveAddPage');
            });
            $("#sales-boxprice-orderGoodsPromotionGiveAddPage").change(function(){
                giveboxpriceChange();
            });
            $("#sales-notaxprice-orderGoodsPromotionGiveAddPage").change(function(){
                givepriceChange("2", '#sales-notaxprice-orderGoodsPromotionGiveAddPage');
            });
            $("#sales-savegoon-orderGoodsPromotionGiveAddPage").click(function(){
                setTimeout(addSaveGiveNumDetail(),200);
            });
        });
        function giveunitnumChange(type){ //数量改变方法
            var goodsId = $("#sales-goodsid-orderGoodsPromotionGiveAddPage").val();
            var unitnum = $("#sales-unitnum-orderGoodsPromotionGiveAddPage").val();
            var auxnum = $("#sales-auxnum-orderGoodsPromotionGiveAddPage").val();
            var overnum =  $("#sales-overnum-orderGoodsPromotionGiveAddPage").val();
            var price = $("#sales-taxprice-orderGoodsPromotionGiveAddPage").val();
            var customerId = $("#sales-customer-orderAddPage").customerWidget("getValue");
            var taxtype = $("#sales-taxtype-orderGoodsPromotionGiveAddPage").val();
            var groupid = $("#sales-groupid-orderGoodsPromotionGiveAddPage").val();

            var url = "";
            var data = "";
            if(type == 1){
                url = "sales/getAuxUnitNumAndAmountByGive.do"
                data = {id:goodsId,unitnum:unitnum,taxtype:taxtype,price:price,groupid:groupid};
            }
            else if(type == 2){
                url = "sales/getUnitNumAndAmountByGive.do"
                data = {id:goodsId,auxnum:auxnum,overnum:overnum,taxtype:taxtype,price:price,groupid:groupid};
            }
            $.ajax({
                url:url,
                dataType:'json',
                type:'post',
                async:false,
                data:data,
                success:function(json){
                    $("#sales-taxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.taxamount);
                    $("#sales-notaxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.notaxamount);
                    $("#sales-taxprice-orderGoodsPromotionGiveAddPage").val(json.taxprice);
                    $("#sales-boxprice-orderGoodsPromotionGiveAddPage").val(json.boxprice);
                    $("#sales-notaxprice-orderGoodsPromotionGiveAddPage").val(json.notaxprice);
                    $("#sales-tax-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.tax);
                    $("#sales-auxnum-orderGoodsPromotionGiveAddPage").val(json.auxnum);
                    $("#sales-overnum-orderGoodsPromotionGiveAddPage").val(json.overnum);
                    $("#sales-unitnum-orderGoodsPromotionGiveAddPage").val(json.unitnum);
                    $("#sales-oldprice-orderGoodsPromotionGiveAddPage").val(json.taxprice);

                    if(json.giveList!=null){
                        var giveList = json.giveList;
                        for(var i=0;i<giveList.length;i++){
                            var fieldid = giveList[i].id;
                            var rowIndex = $("#sales-table-orderGoodsPromotionGiveAddPage").datagrid("getRowIndex",fieldid);
                            var row = $("#sales-table-orderGoodsPromotionGiveAddPage").datagrid('getRows')[rowIndex];
                            row.giveauxnum = giveList[i].giveauxnum;
                            row.giveovernum = giveList[i].giveovernum;
                            row.givenum = giveList[i].givenum;
                            row.givenumdetail = giveList[i].givenumdetail;
                            row.givecount = giveList[i].givecount;
                            $("#sales-table-orderGoodsPromotionGiveAddPage").datagrid('updateRow',{index:rowIndex, row:row});
                        }
                    }
                }
            });
        }
        function givepriceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
            var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
            var price = $(id).val();
            var goodsId = $("#sales-goodsid-orderGoodsPromotionGiveAddPage").val();
            var taxtype = $("#sales-taxtype-orderGoodsPromotionGiveAddPage").val();
            var unitnum = $("#sales-unitnum-orderGoodsPromotionGiveAddPage").val();
            var auxnum = $("sales-auxnum-orderGoodsPromotionGiveAddPage").val();
            $.ajax({
                url:'sales/getAmountChanger.do',
                dataType:'json',
                async:false,
                type:'post',
                data:{type:type,price:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
                success:function(json){
                    $("#sales-taxprice-orderGoodsPromotionGiveAddPage").val(json.taxPrice);
                    $("#sales-boxprice-orderGoodsPromotionGiveAddPage").val(json.boxPrice);
                    $("#sales-taxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.taxAmount);
                    $("#sales-notaxprice-orderGoodsPromotionGiveAddPage").val(json.noTaxPrice);
                    $("#sales-notaxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.noTaxAmount);
                    $("#sales-tax-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.tax);
                }
            });
        }
        function giveboxpriceChange(){
            var $this = $("#sales-boxprice-orderGoodsPromotionGiveAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
            var price = $("#sales-boxprice-orderGoodsPromotionGiveAddPage").val();
            var goodsId = $("#sales-goodsid-orderGoodsPromotionGiveAddPage").val();
            var taxtype = $("#sales-taxtype-orderGoodsPromotionGiveAddPage").val();
            var unitnum = $("#sales-unitnum-orderGoodsPromotionGiveAddPage").val();
            var auxnum = $("sales-auxnum-orderGoodsPromotionGiveAddPage").val();
            $.ajax({
                url:'sales/getAmountChangerByBoxprice.do',
                dataType:'json',
                async:false,
                type:'post',
                data:{boxprice:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
                success:function(json){
                    $("#sales-taxprice-orderGoodsPromotionGiveAddPage").val(json.taxPrice);
                    $("#sales-boxprice-orderGoodsPromotionGiveAddPage").val(json.boxPrice);
                    $("#sales-taxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.taxAmount);
                    $("#sales-notaxprice-orderGoodsPromotionGiveAddPage").val(json.noTaxPrice);
                    $("#sales-notaxamount-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.noTaxAmount);
                    $("#sales-tax-orderGoodsPromotionGiveAddPage").numberbox('setValue',json.tax);
                    $this.css({'background':''});
                }
            });
        }
        //保存买赠
        function addSaveGiveNumDetail(){
            var groupid = $("#sales-groupid-orderGoodsPromotionGiveAddPage").val();
            var flag = $("#sales-form-orderGoodsPromotionGiveAddPage").form('validate');
            if(flag==false){
                return false;
            }
            var addrows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
            var addGiveFlag = true;
            for(var i=0;i<addrows.length;i++){
                if(groupid==addrows[i].groupid){
                    addGiveFlag = false;
                    break;
                }
            }
            if(addGiveFlag==false){
                $.messager.alert("提醒","已添加过此买赠商品，不能重复添加");
                return false;
            }
            var form = $("#sales-form-orderGoodsPromotionGiveAddPage").serializeJSON();
            if(form.unitnum>0){
                var goodsInfoStr = $("#sales-goodsinfo-orderGoodsPromotionGiveAddPage").val();
                form.goodsInfo = $.parseJSON(goodsInfoStr);;
                var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");

                form.fixnum = form.unitnum;
                if(form.overnum!=0){
                    if(form.auxnum==null || form.auxnum==""){
                        form.auxnum = 0;
                    }
                    form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
                }else{
                    form.auxnumdetail = form.auxnum + form.auxunitname;
                }
                form.deliverytype ='0';
                form.groupid = groupid;

                insertRow(form);
                var rows = $("#sales-table-orderGoodsPromotionGiveAddPage").datagrid("getRows");
                if(rows.length>0){
                    for(var i=0;i<rows.length;i++){
                        var goodsid = rows[i].goodsid;
                        var brandid = rows[i].brand;
                        var unitnum = rows[i].givenum;
                        var fixnum = unitnum;
                        var auxnum = rows[i].giveauxnum;
                        var overnum = rows[i].giveovernum;
                        var auxnumdetail = rows[i].givenumdetail;
                        var totalbox = rows[i].givetotalbox;
                        var usablenum = rows[i].usablenum;
                        var taxtype = rows[i].goodsInfo.defaulttaxtype;
                        var taxtypename =rows[i].goodsInfo.defaulttaxtypeName;
                        var data = {goodsid:goodsid,brandid:brandid,unitid:rows[i].unitid,unitname:rows[i].unitname,fixnum:fixnum,
                            unitnum:unitnum,auxunitid:rows[i].auxunitid,auxunitname:rows[i].auxunitname,
                            auxnum:auxnum,overnum:overnum,auxnumdetail:auxnumdetail,totalbox:totalbox,
                            taxtype:taxtype,taxtypename:taxtypename,
                            goodsInfo:rows[i].goodsInfo,deliverytype:'1',groupid:groupid,taxprice:0,taxamount:0,boxprice:0,
                            notaxprice:0,notaxamount:0,tax:0,oldprice:rows[i].oldprice,usablenum:usablenum}
                        if(unitnum>0){
                            insertRow(data);
                        }
                    }
                }
            }
            $("#sales-form-orderDetailAddPage").form("clear");
            $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog('close', true)
            countTotal(); //第添加一条商品明细计算一次合计
        }

    </script>
  </body>
</html>
