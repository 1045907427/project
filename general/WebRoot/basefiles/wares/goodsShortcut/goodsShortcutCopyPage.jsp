<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品档案新增快捷页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true" id="wares-layout-shortcutAddPage">
	    <div data-options="region:'center',split:true,border:true">
	    	<form action="" method="post" id="wares-add-goodsShortcut">
		    	<table cellpadding="1" cellspacing="1" border="0">
		    		<tr>
		    			<td width="80px" align="right">商品编码:</td>
		    			<td align="left"><input name="goodsInfo.id" type="text" style="width: 130px" maxlength="20" class="easyui-validatebox" validType="validId[20]"/><font color="red">*</font></td>
		    			<td width="80px" align="right">助记符:</td>
		    			<td align="left"><input name="goodsInfo.spell" type="text" style="width: 130px" maxlength="20" value="<c:out value="${goodsInfo.spell }"></c:out>"/><font color="red">*</font></td>
		    			<td width="80px" align="right">状态:</td>
		    			<td align="left"><select style="width: 136px;" disabled="disabled">
		    				<option value="4" selected="selected">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品名称:</td>
		    			<td align="left" colspan="3"><input name="goodsInfo.name" type="text" style="width: 356px" maxlength="100" class="easyui-validatebox" validType="validName[100]" required="true" /><font color="red">*</font></td>
		    			<td align="right">默认分类:</td>
		    			<td align="left"><input id="goodsShortcut-widget-defaultsort" type="text" name="goodsInfo.defaultsort" style="width: 130px" value="<c:out value="${goodsInfo.defaultsort }"></c:out>"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
						<td align="left"><input type="text" name="goodsInfo.pinyin" style="width: 130px"/></td>
						<td align="right">规格型号:</td>
		    			<td align="left"><input name="goodsInfo.model" type="text" style="width: 130px" maxlength="200" value="<c:out value="${goodsInfo.model }"></c:out>"/></td>
		    			<td align="right">条形码:</td>
		    			<td align="left"><input name="goodsInfo.barcode" type="text" style="width: 130px" maxlength="50" value="<c:out value="${goodsInfo.barcode }"></c:out>" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱装条形码:</td>
						<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.boxbarcode" maxlength="50" value="<c:out value="${goodsInfo.boxbarcode }"></c:out>"/></td>
		    			<td align="right">商品货位:</td>
						<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.itemno" /></td>
		    			<td align="right">辅单位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-meteringunitid" type="text" name="goodsMUInfo.meteringunitid" style="width: 130px" value="<c:out value="${goodsMUInfo.meteringunitid }"></c:out>"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱装量:</td>
		    			<td align="left"><input id="goodsInfo-numberbox-rate" name="goodsMUInfo.rate" type="text" style="width: 130px" value="${goodsMUInfo.rate }"/><font color="red">*</font></td>
		    			<td align="right">单位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-mainunit" type="text" name="goodsInfo.mainunit" style="width: 130px" value="<c:out value="${goodsInfo.mainunit }"></c:out>"/><font color="red">*</font></td>
		    			<td align="right">商品类型:</td>
		    			<td align="left"><select name="goodsInfo.goodstype" style="width: 130px;" class="easyui-validatebox" required="true">
		    				<option></option>
				    		<c:forEach items="${goodstypeList }" var="list">
			    				<c:choose>
			    					<c:when test="${list.code == goodsInfo.goodstype}">
			    						<option value="${list.code }" selected="selected">${list.codename }</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${list.code }">${list.codename }</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">长度(m):</td>
    					<td align="left"><input type="text" id="goodsInfo-numberbox-glength" style="width: 130px;" name="goodsInfo.glength"></td>
		    			<td align="right">高度(m):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-ghight" style="width: 130px;" name="goodsInfo.ghight"></td>
		    			<td align="right">宽度(m):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-gwidth" style="width: 130px;" name="goodsInfo.gwidth"></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱重(kg):</td>
		    			<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.totalweight" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
		    			<td align="right">箱体积(m<sup>3</sup>):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-totalvolume" style="width: 130px;" name="goodsInfo.totalvolume" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
		    			<td align="right">默认税种:</td>
		    			<td align="left"><input id="goodsShortcut-widget-defaulttaxtype" type="text" style="width: 130px;" name="goodsInfo.defaulttaxtype" value="<c:out value="${goodsInfo.defaulttaxtype }"></c:out>"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品品牌:</td>
		    			<td align="left"><input id="goodsShortcut-widget-brand" type="text" style="width: 130px;" name="goodsInfo.brand" value="<c:out value="${goodsInfo.brand}"></c:out>"/><font color="red">*</font></td>
		    			<td align="right">所属部门:</td>
		    			<td align="left"><input id="goodsShortcut-widget-deptid" type="text" style="width: 130px;" name="goodsInfo.deptid" value="<c:out value="${goodsInfo.deptid }"></c:out>"/><font color="red">*</font></td>
		    			<td align="right">默认供应商:</td>
		    			<td align="left"><input id="goodsShortcut-supplierWidget-defaultsupplier" type="text" name="goodsInfo.defaultsupplier" style="width: 130px" text="<c:out value="${goodsInfo.defaultsupplierName }"></c:out>" value="<c:out value="${goodsInfo.defaultsupplier}"></c:out>"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-highestbuyprice" name="goodsInfo.highestbuyprice" style="width: 130px" value="${goodsInfo.highestbuyprice }"/><font color="red">*</font></td>
		    			<td align="right">基准销售价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="required:true,min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.basesaleprice" style="width: 130px" value="${goodsInfo.basesaleprice }"/></td>
		    			<td align="right">计划毛利率%:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999,precision:2,groupSeparator:','" name="goodsInfo.planmargin" style="width: 130px" value="${goodsInfo.planmargin }"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购箱价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-buyboxprice" name="goodsInfo.buyboxprice" style="width: 130px" value="${goodsInfo.buyboxprice }"/></td>
		    			<td align="right">是否采购:</td>
		    			<td align="left"><select name="goodsInfo.isinoutstorage" style="width: 130px;">
				    		<option value="1" <c:if test="${goodsInfo.isinoutstorage=='1'}">selected="selected"</c:if>>是</option>
				    		<option value="0" <c:if test="${goodsInfo.isinoutstorage=='0'}">selected="selected"</c:if>>否</option>
				    	</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认仓库:</td>
		    			<td align="left"><input id="goodsShortcut-widget-storage" type="text" style="width: 130px;" name="goodsInfo.storageid" value="<c:out value="${goodsInfo.storageid }"></c:out>"/><font color="red">*</font></td>
		    			<td align="right">默认库位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-storagelocation" type="text" style="width: 130px;" name="goodsInfo.storagelocation" value="<c:out value="${goodsInfo.storagelocation }"></c:out>"/></td>
		    			<td align="right"><div id="goodsShortcut-div-boxnumtext" style="visibility: hidden;">库位容量:</div></td>
		    			<td align="left"><div id="goodsShortcut-div-boxnum" style="visibility: hidden;">
		    				<input id="goodsShortcut-numberbox-boxnum" type="text" style="width: 130px;" name="goodsInfo.slboxnum" value="${goodsInfo.slboxnum }"/>
		    			</div></td>
		    		</tr>
		    		<tr>
		    			<td align="right">保质期管理:</td>
		    			<td align="left"><select name="goodsInfo.isshelflife" style="width: 136px">
		    				<option value="1">是</option>
		    				<option value="0" selected="selected">否</option>
		    			</select></td>
		    			<td align="right">保质期:</td>
		    			<td align="left"><span style="float: left;">
						    	<input type="text" name="goodsInfo.shelflife" style="width:90px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','"/>
							    <select name="goodsInfo.shelflifeunit" style="width:40px; float:left;">
							      <option></option>
							      <option value="1" <c:if test="${goodsInfo.shelflifeunit=='1'}">selected="selected"</c:if>>天</option>
								  <option value="2" <c:if test="${goodsInfo.shelflifeunit=='2'}">selected="selected"</c:if>>周</option>
							      <option value="3" <c:if test="${goodsInfo.shelflifeunit=='3'}">selected="selected"</c:if>>月</option>
							      <option value="4" <c:if test="${goodsInfo.shelflifeunit=='4'}">selected="selected"</c:if>>年</option>
							    </select>
							</span>
						</td>
						<td align="right">购销类型:</td>
		    			<td align="left"><select name="goodsInfo.bstype" style="width: 130px;">
		    				<option></option>
				    		<c:forEach items="${bstypeList }" var="list">
			    				<c:choose>
			    					<c:when test="${list.code == goodsInfo.bstype}">
			    						<option value="${list.code }" selected="selected">${list.codename }</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${list.code }">${list.codename }</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
		    			</select><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">标准价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.normalprice" style="width: 130px" value="${goodsInfo.normalprice }"/></td>
		    			<td align="right">最新采购价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-newbuyprice" class="easyui-numberbox no_input" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.newbuyprice" style="width: 130px" readonly="readonly"/></td>
		    			<td align="right">最新销售价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.newsaleprice" style="width: 130px" disabled="disabled"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注:</td>
		    			<td colspan="5" align="left"><input name="goodsInfo.remark" type="text" style="width: 590px" maxlength="200" value="<c:out value="${goodsInfo.remark }"></c:out>"/><font color="red">*</font></td>
		    		</tr>
	    			<c:forEach var="list" items="${priceList}" varStatus="status">
	    				<c:if test="${(status.index)%3==0}">
	    					<tr>
	    					<input value="${status.index}" type="hidden"/>
	    				</c:if>
							<td align="right">${list.codename}:</td>
							<td align="left"><input name="priceInfoList[${status.index}].taxprice" type="text" class="easyui-numberbox" data-options="<c:if test="${status.index == 0 || status.index == 1 || status.index == 2}">required:true,</c:if>min:0,max:999999999999,precision:4,groupSeparator:','" style="width: 130px" value="${list.val }"/><c:if test="${status.index == 0 || status.index == 1 || status.index == 2}"><font color="red">*</font></c:if>
								<input name="priceInfoList[${status.index}].code" value="${list.code}" type="hidden"/>
								<input name="priceInfoList[${status.index}].name" value="${list.codename}" type="hidden"/>
							</td>
  						<c:if test="${(status.index+1)%3==0}">
  							</tr>
  						</c:if>
 					</c:forEach>
		    	</table>
		    </form>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right">
			<a href="javaScript:void(0);" id="goodsShortcut-save-brandtogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
			<a href="javaScript:void(0);" id="goodsShortcut-close-brandtogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="关闭">关闭</a>
		</div> 
     </div>
     <script type="text/javascript">
     	$(function(){
     		loadDropdown();
     		
     		//确定按钮
     		$("#goodsShortcut-save-brandtogoods").click(function(){
     			if(!$("#wares-add-goodsShortcut").form('validate')){
     				return false;
     			}
     			$.messager.confirm("提醒","是否新增商品?",function(r){
					if(r){
						loading("提交中..");
 						var ret = goodsShortcut_AjaxConn($("#wares-add-goodsShortcut").serializeJSON(),'basefiles/addGoodsInfoShortcut.do');
    					var retJson = $.parseJSON(ret);
    					if(retJson.flag){
							$.messager.alert("提醒","新增成功!");
							var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
	      					$("#wares-table-goodsShortcutList").datagrid("load",queryJSON);
							$("#wares-dialog-goodsShortcut").dialog('close');
						}
					}
				});
     		});
     		
     		//关闭按钮
     		$("#goodsShortcut-close-brandtogoods").click(function(){
     			$("#wares-dialog-goodsShortcut").dialog('close');
     		});
     		
     	});
     </script>
  </body>
</html>
