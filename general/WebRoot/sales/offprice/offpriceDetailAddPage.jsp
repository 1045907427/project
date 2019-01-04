<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>特价调整单明细新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="sales-form-offpriceDetailAddPage">
			    <table cellpadding="5" cellspacing="5">
			    	<tr>
			    		<td class="len80">选择商品：</td>
			    		<td colspan="3" class="textalignleft">
			    			<input id="sales-goodsId-offpriceDetailAddPage" name="goodsid" style="width:300px;" />
			    			<span id="sales-idtip-offpriceDetailAddPage" style="margin-left:8px;line-height:23px;color:green;"></span>
			    		</td>
			    	</tr>
			    	<tr>
			    		<td>数量区间：</td>
			    		<td class="textalignleft">
			    			<input id="sales-lownum-offpriceDetailAddPage" name="lownum" style="width:55px" data-options="min:0" />
			    			<span>-</span>
			    			<input id="sales-upnum-offpriceDetailAddPage" name="upnum" style="width:55px" data-options="min:1" />
			    			<span class="auxUnitName"></span>
			    		</td>
			    		<td class="len80">特价：</td>
			    		<td class="textalignleft"><input id="sales-offprice-offprice" class="len150 easyui-validatebox" name="offprice" data-options="required:true" /> </td>
			    	</tr>
			    	<tr>
			    		<td>商品品牌：</td>
			    		<td class="textalignleft"><input id="sales-offprice-brandname" readonly="readonly" class="len150 readonly" /></td>
			    		<td>原价：</td>
			    		<td class="textalignleft"><input id="sales-offprice-oldprice" class="len150 readonly" readonly="readonly" name="oldprice" /></td>
			    	</tr>
			    	<tr>
			    		<td>条形码：</td>
			    		<td class="textalignleft"><input id="sales-offprice-barcode" class="len150 readonly" readonly="readonly" /></td>
			    		<td>单位：</td>
			    		<td class="textalignleft"><input id="sales-offprice-unitname" readonly="readonly" class="len150 readonly" /></td>
			    	</tr>
			    	<tr>
			    		<td>备注：</td>
			    		<td colspan="3" class="textalignleft">
                         <input id="sales-remark-offpriceDetailAddPage" type="text" style="width:410px;" name="remark" />
                        </td>
			    	</tr>
			    </table>
		    </form>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="sales-savegoon-offpriceDetailAddPage" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
        var selectedGoodsid=getFilterGoodsid();

        function getFilterGoodsid(){
            var tmpgoodsid=[];
            var goodsids="";
            var selectDetailRow=[];
            if($("#sales-datagrid-offpriceAddPage").size()>0){
                selectDetailRow=$("#sales-datagrid-offpriceAddPage").datagrid('getRows');
            }
            for(var i=0; i<selectDetailRow.length; i++){
                var rowJson = selectDetailRow[i];
                if(rowJson.goodsid != undefined && rowJson.goodsid!=""){
                    tmpgoodsid.push(rowJson.goodsid);
                }
            }
            if(tmpgoodsid.length>0){
                goodsids=tmpgoodsid.join(',');
            }
            return goodsids;
        }
    	$(function(){
    		$("#sales-goodsId-offpriceDetailAddPage").goodsWidget({
    			name:'t_sales_dispatchbill_detail',
    			col:'goodsid',
    			width:150,
    			required:true,
                param:[
                    {field:'id',op:'notin',value:selectedGoodsid}
                ],
    			onSelect: function(data){
    				$("#sales-offprice-brandname").val(data.brandName);
    				$("#sales-offprice-barcode").val(data.barcode);
    				$("#sales-offprice-unitname").val(data.mainunitName);
    				$(".auxUnitName").text(data.mainunitName);
    				$("#sales-idtip-offpriceDetailAddPage").addClass("img-loading")
    				$.ajax({
    					url:'sales/getGoodsDetail.do',
    					dataType:'json',
    					type:'post',
    					data:'id='+ data.id +'&cid=${customerId}&type=reject',
    					success:function(json){
    						$("#sales-idtip-offpriceDetailAddPage").removeClass("img-loading").html("商品编码："+ data.id);
    						$("#sales-offprice-oldprice").val(json.detail.taxprice);
    					}
    				});
                    $("#sales-lownum-offpriceDetailAddPage").textbox('textbox').focus();
    			}
    		});
    		$("#sales-savegoon-offpriceDetailAddPage").click(function(){
    			addSaveDetail(true);
    		});
            $("#sales-lownum-offpriceDetailAddPage").numberbox({
                min:0,
				precision:general_bill_decimallen
            });
            $("#sales-upnum-offpriceDetailAddPage").numberbox({
                min:1,
				precision:general_bill_decimallen
            });
            $("#sales-lownum-offpriceDetailAddPage").textbox('textbox').bind('keydown', function(e){
                if(e.keyCode==13){
                    $("#sales-upnum-offpriceDetailAddPage").textbox('textbox').focus();
                    $("#sales-upnum-offpriceDetailAddPage").textbox('textbox').select();
                }
            });
    		$("#sales-upnum-offpriceDetailAddPage").textbox('textbox').bind("keydown", function(event){
    			if(event.keyCode==13){
		   			$("#sales-offprice-offprice").focus();
		   			$("#sales-offprice-offprice").select();
				}
    		});
    		$("#sales-offprice-offprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#sales-remark-offpriceDetailAddPage").focus();
				}
			});
			$("#sales-remark-offpriceDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#sales-savegoon-offpriceDetailAddPage").focus();
				}
			});
			$("#sales-savegoon-offpriceDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
    	});
    </script>
  </body>
</html>
