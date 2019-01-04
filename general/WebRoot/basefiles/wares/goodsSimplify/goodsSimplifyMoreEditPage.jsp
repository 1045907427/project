<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>商品档案批量修改信息页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="basefiles/editGoodsInfoMore.do" method="post" id="wares-form-editMoreGoodsInfo" style="padding: 10px;">
    				<input type="hidden" name="idStr" value="${idStr }"/>
    				<input type="hidden" id="ware-unInvNum-goodsInfo" value="${unInvNum }"/>
    				<table cellpadding="2" cellspacing="2" border="0">
    					<tr>
    						<td align="left" class="len100">商品品牌:</td>
    						<td class="len165"><input id="goodsInfo-widget-brand" type="text" name="goodsInfo.brand" /></td>
    						<td align="left" class="len100">默认供应商:</td>
    						<td class="len165"><input id="goodsInfo-supplierWidget-defaultsupplier" type="text" class="len150" name="goodsInfo.defaultsupplier"/></td>
    					</tr>
    					<tr>
    						<td>默认仓库:</td>
    						<td><input id="goodsInfo-widget-storage" type="text" name="goodsInfo.storageid"/></td>
    						<%--<td>默认库位:</td>--%>
    						<%--<td><input id="goodsInfo-widget-storagelocation" type="text" name="goodsInfo.storagelocation"/></td>--%>
    					</tr>
                        <!-- <tr>
                            <td>默认采购员:</td>
                            <td><input id="goodsInfo-widget-defaultbuyer" type="text" class="len150" name="goodsInfo.defaultbuyer"/></td>
                            <td>默认业务员:</td>
                            <td><input id="goodsInfo-widget-defaultsaler" type="text" class="len150" name="goodsInfo.defaultsaler"/></td>
                        </tr> -->
    					<tr>
    						<td>商品分类:</td>
    						<td><input id="goodsInfo-widget-waresClass" type="text" name="goodsInfo.defaultsort"/></td>
    						<td>默认税种:</td>
    						<td><input id="goodsInfo-widget-defaulttaxtype" type="text" name="goodsInfo.defaulttaxtype"/></td>
    					</tr>
    					<tr>
    						<td>商品类型:</td>
			  				<td><select name="goodsInfo.goodstype" id="goodsInfo-select-goodstype" class="len150">
			  						<option></option>
			  						<c:forEach items="${goodstypeList }" var="list">
		    							<option value="${list.code }">${list.codename }</option>
					    			</c:forEach>
			  					</select>
			  				</td>
                            <td>第二供应商:</td>
                            <td><input id="goodsInfo-widget-secondsupplier" type="text" name="goodsInfo.secondsupplier"/></td>
    					</tr>
                        <tr>
                            <td>批次管理:</td>
                            <td><select name="goodsInfo.isbatch" class="len150">
                                <option></option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select></td>
                            <td>库位管理:</td>
                            <td><select name="goodsInfo.isstoragelocation" class="len150">
                                <option></option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td>保质期管理:</td>
                            <td><select id="goodsInfo-numberbox-isshelflife" name="goodsInfo.isshelflife" class="len150">
                                <option></option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select></td>
                            <td>保质期:</td>
                            <td><span style="float: left;">
                                <input id="goodsInfo-numberbox-shelflife" type="text" name="goodsInfo.shelflife" style="width:85px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','"/>
                                <select name="goodsInfo.shelflifeunit" style="width:60px; float:left;" class="easyui-combobox" >
                                    <option></option>
                                    <option value="1">天</option>
                                    <option value="2">周</option>
                                    <option value="3">月</option>
                                    <option value="4">年</option>
                                </select>
                            </span></td>
                        </tr>
						<tr>
							<td>商品货位:</td>
							<td><input type="text" class="len150" name="goodsInfo.itemno" /></td>
							<td>购销类型:</td>
							<td>
								<select name="goodsInfo.bstype" class="easyui-combobox len150">
									<option></option>
									<c:forEach items="${bstypeList }" var="list">
										<option value="${list.code }">${list.codename }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>产地:</td>
							<td><input type="text" style="width: 150px;" name="goodsInfo.productfield"/></td>
						</tr>
    				</table>
    			</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="wares-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="修改">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$("#wares-save-saveMenu").click(function(){
    		$.messager.confirm("提醒","确认修改?",function(r){
				if(r){
					$("#wares-form-editMoreGoodsInfo").submit();
				}
			});
    	});
    
    	$("#wares-form-editMoreGoodsInfo").form({  
		    onSubmit: function(){  
		    	loading("数据处理中..");
		    },  
		    success:function(data){
		    	//表单提交完成后 隐藏提交等待页面
		    	loaded();
		    	var json = $.parseJSON(data);
		    	$.messager.alert("提醒",""+json.lockNum+"条数据被其他人操作,暂不能修改;<br/>"+json.unEditNum+"条数据不允许修改;<br/>"+json.sucNum+"条记录修改成功;<br/>"+json.failNum+"条数据修改失败");
		    	$('#wares-dialog-goodsInfoEditMore').dialog('close',true);
		        $("#wares-table-goodsInfoList").datagrid('reload');
		        $("#wares-table-goodsInfoList").datagrid('clearChecked');
		    }  
		});
    	
    	$(function(){
    		//商品品牌
			$("#goodsInfo-widget-brand").widget({
				width:150,
				name:'t_base_goods_info',
				col:'brand',
				singleSelect:true,
				onlyLeafCheck:false,
		  		onSelect:function(data){
		  			$("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget('setValue',data.supplierid);
		  		},
		  		onClear:function(){
		  			$("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget('clear');
		  		}
			});

			//默认分类
			$("#goodsInfo-widget-waresClass").widget({
				width:150,
				name:'t_base_goods_info',
				col:'defaultsort',
				singleSelect:true,
				onlyLeafCheck:true
			});

			//默认仓库
			$("#goodsInfo-widget-storage").widget({
				width:150,
				name:'t_base_goods_info',
				col:'storageid',
				singleSelect:true,
				onlyLeafCheck:false
			});

			//默认库位
			$("#goodsInfo-widget-storagelocation").widget({
				width:150,
				name:'t_base_goods_info',
				col:'storagelocation',
				singleSelect:true,
				onlyLeafCheck:false
			});

			//默认采购员
			$("#goodsInfo-widget-defaultbuyer").widget({
				width:150,
				name:'t_base_goods_info',
				col:'defaultbuyer',
				singleSelect:true,
				onlyLeafCheck:false
			});

			//默认业务员
			$("#goodsInfo-widget-defaultsaler").widget({
				width:150,
				name:'t_base_goods_info',
				col:'defaultsaler',
				singleSelect:true,
				onlyLeafCheck:false
			});

			//默认供应商
			$("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget({});

			//默认税种
			$("#goodsInfo-widget-defaulttaxtype").widget({
				width:150,
				name:'t_base_goods_info',
				col:'defaulttaxtype',
				singleSelect:true,
				onlyLeafCheck:false
			});

            //第二供应商
            $("#goodsInfo-widget-secondsupplier").widget({
                width:150,
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                singleSelect:false,
                onSelect:function(data){
                    var objs = $(this).widget('getObjects');
                    if(objs.length > Number("${secondSupplierNum}")){
                        $.messager.alert("提醒","最多允许输入超过${secondSupplierNum}个第二供应商！");
                        $(this).widget('clear');
                        return false;
                    }
                }
            });
    	});
    </script>
  </body>
</html>
