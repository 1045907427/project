<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>捆绑促销单明细增加页面</title>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<form id="sales-form-bundlegroupDetail">
  	<div data-options="region:'north'" style="height: 200px;">
  		<table id="sales-groupDetail-donatedGoods" border="1"></table>
	</div>
	<div data-options="region:'center',border:false">
		<h1>套餐信息</h1>  	
		<table>
		  <tr><td>促销编号:</td>
		  		<td><input id="sales-groupDetail-groupid" name="groupid"  value="${group.groupid}" readonly="true"  type="text" class="len150" /></td>
		  	  <td>促销名称:</td>
		  	  <td><input id="sales-groupDetail-groupName" name="groupname"  value="${group.groupname}" readonly="true"   type="text" class="len150"/>
		  	  <td id="promotionValidate"></td>
		  	  </td>
		   </tr>
		  <tr><td>套餐总价:</td>
		  <td><input id="sales-groupDetail-bundlePrice" name="price" value="${group.price}" type="text" class="readonly2 len150"  readonly="true" /></td>
		  	 <td>促销份数:</td>
		  <td><input id="sales-groupDetail-bundleNum" name="limitnum" value="${group.limitnum}" type="text" class="len150"  readonly="true" /></td>
		  </tr>
		  <tr>
		  <td>备&nbsp;&nbsp;注:</td>
		  <td colspan="3"><input id="sales-groupDetail-billid" name="remar" value="${group.remark}" type="text" style="width:356px;"/></td>
		  <td><input type="hidden" id="oldprice" name="oldprice" /></td>
		  </tr>
		 </table>
  		</div>
  	</form>
  	</div>
  	<script type="text/javascript">
  	 $('#sales-groupDetail-bundlePrice').numberbox({
 	    min:0,
 	    precision:2
 	});
  	$('#sales-groupDetail-bundleNum').numberbox({
 	    min:0,
 	    precision:0
 	});

$(function(){
	$("#sales-savegoon-groupDetailEditPage").click(function(){
			addSaveBundleDetail(false);
		});
	
  	var $EditTable = $("#sales-groupDetail-donatedGoods");
	$EditTable.datagrid({
	fit : true,
	striped : true,
	method : 'post',								
	rownumbers : true,
	singleSelect : true,
	data: JSON.parse('${goodsJson}'),
	columns: [[
	{field:'goodsid',title:'商品编号',width:120,isShow:true},
	{field:'goodsname',title:'商品名称',width:180,
		editor:{
			type:'goodswidget',
			options:{
				required:true
			}
		}		
	},
    {field:'boxnum',title:'箱装量',width:60,isShow:true,
        formatter:function(value,row,index){
            return formatterBigNum(value);
        },
        editor: {
            type: 'span',
            options:{
                precision:0
            }
        }
    },
	{field:'oldprice',title:'参考单价',width:65,isShow:true,
		formatter:function(value,row,index){
			return value;
		}	
	},
	{field:'price',title:'促销价',width:65,isShow:true,
		formatter:function(value,row,index){
			return value;
		},	
		editor: {
				type: 'numberbox',
				options:{
					required: false,
                    precision:6
				}
			}
		},
	{field:'unitnum',title:'捆绑数量',width:60,isShow:true,
			formatter:function(value,row,index){
				return formatterBigNum(value);
			},
		editor: {
			type: 'numberbox',
			options:{
				required: false
			}
		}

	}
	//{field:'unitname',title:'单位',width:40,isShow:true}
	]],
    onLoadSuccess: function(data) {
        if(data.total < 5){
            for(var i = 0; i< 6-i;i++){
                $EditTable.datagrid('appendRow', {});
            }
        }
    }
	}).datagrid('columnMoving');
	
	 });
	
		
  </script>	
  </body>
 </html>