<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>活动买赠促销单明细查看页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="sales-form-GroupDetailPage" action="" >
		  	<h1>所购商品信息</h1>
			    <table cellpadding="3" cellspacing="3" >			    	 
			    	<tr>
			    		<td>商品名称：</td>
			    		<td>
			    			<input id="sales-groupDetail-goodsName" readonly="readonly"  class="len165"  /></td>
			    		<td>商品编码：</td>
                           <td><a style="color:green">${groupDetail.goodsid}</a>
                        促销可用量：<input id="sales-groupDetail-boxprice-hidden"  style="width:60px;border:none;color:green" readonly="readonly"  />
                        </td>
			    	</tr>
                    <c:if test="${acttype == '1'}">
                    <tr>
			    		<td>商品单价：</td>
			    		<td><input id="sales-groupDetail-price" readonly="readonly" class="len165" value="${groupDetail.price}"/></td>
			    		<td>商品箱价：</td>
			    		<td><input id="box"  class="len165"  value="${boxprice}" readonly="readonly"/></td>
			    		</td>			    		
			    	</tr>
                    </c:if>
			    	<tr>
                    <c:if test="${acttype == '1'}">
                        <td>基准数量：</td>
                        <td><input id="th" value="${groupDetail.unitnum}"  class="len165" readonly="readonly" /></td>
                        <td>辅基准数量：</td>
                    </c:if>

                    <c:if test="${acttype == '3'}">
                        <td>标准数量：</td>
                        <td><input id="th" value="${groupDetail.unitnum}"  class="len165" readonly="readonly" /></td>
                        <td>辅数量：</td>
                    </c:if>

			    		<td>
			    		<input id="one" value="${groupDetail.auxnum }" readonly="readonly" style="width:50px" />
			    		<input id="sales-groupDetail-auxremainder-hidden" style="width:25px;border:none" value="${groupDetail.auxunitname }" readonly="readonly"/>
			    		<input id="two" value="${groupDetail.auxremainder }" readonly="readonly" style="width:50px" />
			    		<input id="sales-groupDetail-auxnum-hidden" style="width:25px;border:none" value="${groupDetail.unitname }" readonly="readonly"/>
			    		</td>
			    	</tr>
			    	<tr>
			    		<td>促销份数：</td>
			    		<td><input id="sales-groupDetail-promotionNum"  class="len165" readonly="readonly"  /></td>
                        <td>备注：</td>
                        <td><input id="sales-groupDetail-remark" type="text"  name="remark" readonly="readonly"  class="len165"/></td>
                    </tr>
			    </table>		    	   
			    	<hr color="blue" width="580px" />
			    	<h1>赠送商品列表</h1>
			    	<table id="sales-groupDetail-donatedGoods">
			    </table>
		    </form>
  		</div>
  	</div>
   <script  type="text/javascript">

   var $EditTable = $('#sales-groupDetail-donatedGoods');
   var donatedJson = $("#sales-groupDetail-donatedGoods").createGridColumnLoad({ 
	   commonCol: [[
					{field:'goodsid',title:'商品编码',width:30,isShow:true},
					{field:'goodsname',title:'商品名称',width:300,isShow:true},
					{field:'brand',title:'品牌',width:60,isShow:true},
					{field:'price',title:'商品单价',width:60,isShow:true,
						formatter:function(value,row,index){								
			        		return value;
				        }
					},
					{field:'unitnum',title:'赠送数量',width:60,isShow:true,
						formatter:function(value,row,index){
			        		return formatterBigNumNoLen(value);
				        }	
					},
					{field:'unitname',title:'单位',width:40,isShow:true}
					]]
   });
   $("#sales-savegoon-groupDetailEditPage").click(function(){
       editSaveGroupDetail(false);
   });
   
   $('#sales-groupDetail-price').numberbox({
	    min:0,
	    precision:6
	});
   $('#box').numberbox({
	    min:0,
	    precision:2
	});
   $('#one').numberbox({
	    min:0,
	    precision:0
	});
   $('#two').numberbox({
	    min:0,
	    precision:0
	});
   $('#th').numberbox({
	    min:0,
	    precision:0
	});
   $('#sales-groupDetail-promotionNum').numberbox({
       min:0,
       precision:0
   });
   </script>
  </body>
</html>
