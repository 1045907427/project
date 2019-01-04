<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>活动买赠促销单明细增加页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="sales-form-GroupDetailPage">
		  	<h1>&nbsp;所购商品信息</h1>
			    <table cellpadding="3" cellspacing="3" >			    	 
			    	<tr>			    	
			    		<td class="len80">选择商品：</td>
			    		<td>
			    			<input id="sales-goodsId-groupDetailAddPage" class="len150" name="goodsname" />
			    			<input type="hidden" name="goodsid" />
			    		</td>
			    		<td id="sales-loading-groupDetailAddPage" colspan="2"></td>
			    	</tr>

                    <c:if test="${acttype == '1'}">
			    	<tr>
			    		<td>商品单价：</td>
			    		<td><input id="sales-groupDetail-price"   class="len150 " name="baseprice" />
                        </td>

			    		<td>商品箱价：</td>
			    		<td><input id="sales-groupDetail-boxprice"   class="len150 " style="width:150px" name="boxprice"/></td>
			    		<td><input id="sales-groupDetail-boxprice-hidden" type="hidden" name="boxnum" style="width:20px" />
			    		</td>
			    	</tr>
                    </c:if>

			    	<tr>
                    <c:if test="${acttype == '1'}">
			    		<td>基准数量：</td>
                        <td><input id="sales-groupDetail-promotionNum" name="unitnum" class="easyui-validatebox len150" data-options="validType:'intOrFloatNum[${decimallen}]'"/></td>
                        <td>辅基准数量：</td>
                    </c:if>

                    <c:if test="${acttype == '3'}">
                            <td>标准数量：</td>
                            <td><input id="sales-groupDetail-promotionNum" name="unitnum" class="easyui-validatebox len150" data-options="validType:'intOrFloatNum[${decimallen}]'"/></td>
                            <td>辅数量：</td>
                    </c:if>

			    		<td>
			    		<input id="sales-groupDetail-auxnum"  name="auxnum" style="width:50px" class="easyui-validatebox" data-options="validType:'integer'"/>
			    		<input id="sales-groupDetail-auxremainder-hidden" name="auxunitname" style="width:20px;border:none" readonly="true"  />
			    		<input id="sales-groupDetail-auxremainder"  name="auxremainder" style="width:50px" class="easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
			    		<input id="sales-groupDetail-auxnum-hidden" name="unitname" style="width:20px;border:none" readonly="true"  />
			    		</td>			    		
			    	</tr>
			    	<tr>
			    		<td>促销份数：</td>
			    		<td><input id="sales-groupDetail-totalPromotionNum" class="len150" name="limitnum"/></td>
			    		</td>
                        <td>备注：</td>
                        <td colspan="3"><input id="sales-groupDetail-remark" type="text" class="len150" style="width:150px" name="remark" />
                        <td><input type="hidden" id="groupIndex"/></td>
                        <td><input type="hidden" id="jsonCount" name="count" /></td>
                        <td><input type="hidden"  name="groupname" value="${groupname}" /></td>
                        </td>
			    	</tr>
			    </table>
			    	<hr color="blue" />
			    	<h1>&nbsp;赠送商品列表</h1>
			    	<table id="sales-groupDetail-donatedGoods" border="1" cellpadding="0" cellspacing="0" toolbar="#tb">
			  	  </table>
                <div id="tb">
                    <a class="easyui-linkbutton" iconCls="button-add" plain="true" onclick="addRow();">新增</a>
                    <a class="easyui-linkbutton" iconCls="button-delete" plain="true" onclick="cutRow();">删除</a>
                    <a class="easyui-linkbutton" iconCls="button-save" plain="true" onclick="saveRow();">保存</a>
                </div>

            </form>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
                <input type="button" value="继续添加" name="savegoon" id="sales-saveaddgood-groupDetailDetailEditPage" />
	  			<input type="button" value="确定" name="savegoon" id="sales-savegoon-groupDetailDetailEditPage" />
  			</div>
  		</div>
  	</div>  	
  <script type="text/javascript">

      var deliverydate = "${date }";
      var addgoodsid = "";

      $('#sales-groupDetail-totalPromotionNum').numberbox({
            min:0,
            precision:0
    });
    $('#sales-groupDetail-auxremainder').change(function(){
        unitnumChange(2);
    });
    $('#sales-groupDetail-auxnum').change(function(){
        unitnumChange(2);
    });
    $('#sales-groupDetail-promotionNum').change(function(){
        unitnumChange(1);
    });
    $('#sales-groupDetail-price').numberbox({
         min:0,
         precision:6,
         onChange:function(){priceChange(1);}
     });
    $('#sales-groupDetail-boxprice').numberbox({
         min:0,
         precision:2,
         onChange:function(){priceChange(2);}
     });
    var $EditTable = $('#sales-groupDetail-donatedGoods');
    $EditTable.datagrid({
         fit: true,
         striped: true,
         method: 'post',
         rownumbers: true,
         singleSelect: true,
         showFooter: true,
         columns: [[
             {field:'goodsid',title:'商品编码',width:90,isShow:true},
             {field: 'goodsname', title: '商品名称', width: 200,
                 editor: {
                     type: 'goodswidget',
                     options: {
                         required: false,
                         onSelect: function (data) {
                             var rows = $EditTable.datagrid('getRows');
                             var pei = donatedEditIndex();
                             var goodsid = data.id;
                             var goodsname = data.name;
                             var rows = $EditTable.datagrid('getRows');
                             for (var i = 0; i < rows.length; i++) {
                                 if (pei == i) {
                                     continue;
                                 }
                                 var row = rows[i];
                                 if (row.goodsid == goodsid) {
                                     $.messager.alert("提醒", "商品编码重复！");
                                     var ed = $EditTable.datagrid('getEditor', {index: pei, field: 'goodsid'});
                                     ed.target.widget('clear');
                                     return false;
                                 }
                                 if (row.goodsname == goodsname) {
                                     $.messager.alert("提醒", "商品名称重复！");
                                     return false;
                                 }
                             }
                         }
                     }
                 }
             },
             {field:'brand',title:'品牌',width:60,isShow:true},
             {field:'price',title:'单价',width:80,isShow:true,
                 formatter:function(value,row,index){
                     if(value != undefined){
                         return 0 ;
                     }else{
                         return value;
                     }

                 }
             },
             {field:'unitnum',title:'赠送数量',width:60,isShow:true,
                 editor: {
                     type: 'numberbox',
                     options:{
                         required:true,
                         precision:${decimallen}
                     }
                 },
                 formatter:function(value,row,index){
                     return formatterBigNumNoLen(value);
                 }
             },
             {field:'unitname',title:'单位',width:50,isShow:true}
         ]],
         onDblClickRow:function(cindex, data){
             var pei = donatedEditIndex();
             if(pei != -1){
                 $EditTable.datagrid('endEdit', pei);
                 $EditTable.datagrid('selectRow', pei);
                 var rowData = $EditTable.datagrid('getSelected');
                 if(rowData.goodsname == undefined){
                     $EditTable.datagrid('cancelEdit', pei);
                 }else{
                     $EditTable.datagrid('endEdit', pei);
                 }
                 getGiveGoodsInfo(pei,rowData.goodsname,rowData.unitnum);
             }
             $EditTable.datagrid('beginEdit', cindex);
             setTimeout(function() {
                 var ed = $EditTable.datagrid('getEditor', {index:cindex, field:'goodsname'});
                 $(ed.target).goodsWidget('setValue', data.goodsid);
                 $(ed.target).goodsWidget('setText', data.goodsname);
             }, 100);
         },
         onClickRow: function (rowIndex, rowData) {
             var pei = donatedEditIndex();
             if(pei == rowIndex){
                 $EditTable.datagrid('endEdit', rowIndex);
                 if(rowData.goodsname != undefined && rowData.unitnum != undefined){
                     getGiveGoodsInfo(rowIndex,rowData.goodsname,rowData.unitnum);
                 }else{
                     $EditTable.datagrid('cancelEdit', rowIndex);
                 }
             }
         }
     }).datagrid("loadData", {'total':1,'rows':[{}]}).datagrid('columnMoving');;

    function priceChange(flag){
         var boxnum = $("#sales-groupDetail-boxprice-hidden").val();
         if(flag == 1){
             var price =  $('#sales-groupDetail-price').val();
             var boxprice = price * boxnum ;
             $('#sales-groupDetail-boxprice').val(boxprice)
             $('#sales-groupDetail-boxprice').numberbox('setValue',boxprice);
         }else{
             var boxprice =  $('#sales-groupDetail-boxprice').val();
             var price = boxprice / boxnum ;
             $('#sales-groupDetail-price').val(price);
             $('#sales-groupDetail-price').numberbox('setValue',price);
         }
     }

      function unitnumChange(type){
          var goodsId = $("input[name=goodsid]").val();
          var auxnum = $("#sales-groupDetail-auxnum").val();;
          var promotionNum = $("#sales-groupDetail-promotionNum").val();
          var auxremainder = $("#sales-groupDetail-auxremainder").val();
          if(goodsId != ""){
              $.ajax({
                  url:'sales/auxUnitNumChange.do',
                  dataType:'json',
                  type:'post',
                  async:false,
                  data:{id:goodsId,promotionNum:promotionNum,auxnum:auxnum,auxremainder:auxremainder,type:type},
                  success:function(json){
                      $("#sales-groupDetail-auxnum").val(json.auxnum);
                      $("#sales-groupDetail-auxremainder").val(json.auxremainder);
                      $("#sales-groupDetail-promotionNum").val(json.promotionNum);
                      $("#sales-groupDetail-totalPromotionNum").focus();
                      //$.messager.alert("提醒", "请填对应的赠送商品 和 总促销数量");
                  }
              });
          }

      }

	$(function(){
          var rows =  $("#sales-datagrid-promotionAddPage").datagrid('getRows');
          for(var i=0;i<rows.length;i++){
              if(rows[i].gtype == 1){
                  if(addgoodsid == ""){
                      addgoodsid = rows[i].goodsid;
                  }else{
                      addgoodsid += "," + rows[i].goodsid;
                  }
              }
          }
		  //所购商品参照窗口
  		$("#sales-goodsId-groupDetailAddPage").goodsWidget({
			name:'t_sales_dispatchbill_detail',
			col:'goodsid',
			width:150,
			required:true,
			onSelect: function(data){
				$("input[name=goodsid]").val(data.id);
				$("input[name=barcode]").val(data.barcode);
				$("input[name=boxnum]").val(data.boxnum);
				$("#sales-loading-groupDetailAddPage").addClass("img-loading");
				var date = $("input[name='saleorder.businessdate']").val();
				$.ajax({
					url:'sales/getGoodsDetail.do',
					dataType:'json',
					type:'post',
					async:false,
					data:{id:data.id,data:date},
					success:function(json){	
						$("#sales-loading-groupDetailAddPage").removeClass("img-loading").html
						("商品编码：<font color='green'>"+json.detail.goodsInfo.id+
								"</font>&nbsp;可用量：<font color='green'>"+ json.total + json.unitname +"</font>");
						$("#sales-groupDetail-price").numberbox('setValue',json.detail.goodsInfo.basesaleprice);
						$("#sales-groupDetail-auxremainder-hidden").val(json.detail.auxunitname);					
						$("#sales-groupDetail-auxnum-hidden").val(json.unitname);
						$("#sales-groupDetail-boxprice-hidden").val(json.detail.goodsInfo.boxnum);						
						$("#sales-groupDetail-boxprice").numberbox('setValue',json.detail.boxprice);
						$("#sales-groupDetail-promotionNum").focus();
					}
				});
			}
		});
		 //赠送商品参照窗口
  		$("#sales-groupDetail-donatedGoodsGroup").goodsWidget({
			name:'t_sales_order_detail',
			col:'goodsid',
			width:200,
			required:true,
            isPromotion:true,
			onSelect: function(data){
				$("input[name=goodsid]").val(data.id);
				$("input[name=barcode]").val(data.barcode);
				$("input[name=boxnum]").val(data.boxnum);
				$("#sales-loading-orderDetailAddPage").addClass("img-loading");
				var date = $("input[name='saleorder.businessdate']").val();
				$.ajax({
					url:'sales/getGoodsDetail.do',
					dataType:'json',
					type:'post',
					async:false,
					data:{id:data.id,data:date},
					success:function(json){
						$("#sales-groupDetail-giveGoodsId").val(json.detail.goodsInfo.id);
						$("#sales-groupDetail-giveGoodBasePrice").val(json.detail.goodsInfo.basesaleprice);
						$("#sales-groupDetail-giveGoodsBrand").val(json.detail.goodsInfo.brandName);
					}
				});
			},
			onClear:function(){
				$(".goodsNum").val("0").attr("readonly", "readonly");
			}
		});

        $("#sales-savegoon-groupDetailDetailEditPage").click(function(){
              addSaveGroupDetail(true);
          });
        $("#sales-saveaddgood-groupDetailDetailEditPage").click(function(){
              addSaveGroupDetail(true);
              try{
                  var givejson = $('#sales-groupDetail-donatedGoods').datagrid('getRows');
              }catch(e) {
                  beginAddGroup1(1);
              }

        });

        $("#sales-savegoon-groupDetailDetailEditPage").die("keydown").live("keydown",function(event){
              //enter
              if(event.keyCode==13){
                  addSaveGroupDetail(true);
              }
        });

        //enter快捷键
        $("#sales-groupDetail-promotionNum").bind("keydown", function(e){
              if(e.keyCode==13){
                  $("#sales-groupDetail-auxnum").focus();
                  $("#sales-groupDetail-auxnum").select();
              }
        });
        $("#sales-groupDetail-auxnum").bind("keydown", function(e){
              if(e.keyCode==13){
                  $("#sales-groupDetail-auxremainder").focus();
                  $("#sales-groupDetail-auxremainder").select();
              }
        });

        $("#sales-groupDetail-auxremainder").bind("keydown", function(e){
              if(e.keyCode==13){
                  getNumberBox("sales-groupDetail-totalPromotionNum").focus();
                  getNumberBox("sales-groupDetail-totalPromotionNum").select();
              }
        });
        getNumberBox("sales-groupDetail-totalPromotionNum").bind("keydown", function(e){
              if(e.keyCode==13){
                  $("#sales-groupDetail-remark").focus();
                  $("#sales-groupDetail-remark").select();
              }
        });

        $("#sales-groupDetail-remark").bind("keydown", function(event){
              if(event.keyCode==13){
                  $("#sales-groupDetail-remark").blur();
                  addRow();
              }
        });
      });

	</script>      
  </body>
</html>
