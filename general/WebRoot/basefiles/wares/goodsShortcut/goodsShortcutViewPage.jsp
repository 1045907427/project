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
		    			<td align="left"><input name="goodsInfo.id" type="text" style="width: 130px" maxlength="20" value="<c:out value="${goodsInfo.id }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td width="80px" align="right">助记符:</td>
		    			<td align="left"><input name="goodsInfo.spell" type="text" style="width: 130px" maxlength="20" value="<c:out value="${goodsInfo.spell }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td width="80px" align="right">状态:</td>
		    			<td align="left"><select style="width: 136px;" disabled="disabled">
		    				<option value="0" <c:if test="${goodsInfo.state=='0'}">selected="selected"</c:if>>禁用</option>
		    				<option value="1" <c:if test="${goodsInfo.state=='1'}">selected="selected"</c:if>>启用</option>
		    				<option value="2" <c:if test="${goodsInfo.state=='2'}">selected="selected"</c:if>>保存</option>
		    				<option value="3" <c:if test="${goodsInfo.state=='3'}">selected="selected"</c:if>>暂存</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品名称:</td>
		    			<td align="left" colspan="3"><input name="goodsInfo.name" type="text" style="width: 356px" maxlength="100" value="<c:out value="${goodsInfo.name }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">默认分类:</td>
		    			<td align="left"><input id="goodsShortcut-widget-defaultsort" type="text" name="goodsInfo.defaultsort" style="width: 130px" value="<c:out value="${goodsInfo.defaultsort }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
						<td align="left"><input type="text" name="goodsInfo.pinyin" style="width: 130px" value="<c:out value="${goodsInfo.pinyin }"></c:out>" readonly="readonly"/></td>
		    			<td align="right">规格型号:</td>
		    			<td align="left"><input name="goodsInfo.model" type="text" style="width: 130px" maxlength="200" value="<c:out value="${goodsInfo.model }"></c:out>" readonly="readonly"/></td>
		    			<td align="right">条形码:</td>
		    			<td align="left"><input name="goodsInfo.barcode" type="text" style="width: 130px" maxlength="50" value="<c:out value="${goodsInfo.barcode }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱装条形码:</td>
						<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.boxbarcode" maxlength="50" value="<c:out value="${goodsInfo.boxbarcode }"></c:out>" readonly="readonly"/></td>
		    			<td align="right">商品货位:</td>
						<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.itemno" value="<c:out value="${goodsInfo.itemno }"></c:out>" readonly="readonly"/></td>
		    			<td align="right">辅单位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-meteringunitid" type="text" name="goodsMUInfo.meteringunitid" style="width: 130px" value="<c:out value="${goodsMUInfo.meteringunitid }"></c:out>" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱装量:</td>
		    			<td align="left"><input id="goodsInfo-numberbox-rate" name="goodsMUInfo.rate" type="text" style="width: 130px" value="${goodsMUInfo.rate }" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">单位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-mainunit" type="text" name="goodsInfo.mainunit" style="width: 130px" value="<c:out value="${goodsInfo.mainunit }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">商品类型:</td>
		    			<td align="left"><select name="goodsInfo.goodstype" style="width: 136px;" disabled="disabled">
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
    					<td align="left"><input type="text" id="goodsInfo-numberbox-glength" style="width: 130px;" name="goodsInfo.glength" value="${goodsInfo.glength }" readonly="readonly"/></td>
		    			<td align="right">高度(m):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-ghight" style="width: 130px;" name="goodsInfo.ghight" value="${goodsInfo.ghight }" readonly="readonly"/></td>
		    			<td align="right">宽度(m):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-gwidth" style="width: 130px;" name="goodsInfo.gwidth" value="${goodsInfo.gwidth }" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱重(kg):</td>
		    			<td align="left"><input type="text" style="width: 130px;" name="goodsInfo.totalweight" value="${goodsInfo.totalweight }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" readonly="readonly"/></td>
		    			<td align="right">箱体积(m<sup>3</sup>):</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-totalvolume" style="width: 130px;" name="goodsInfo.totalvolume" value="${goodsInfo.totalvolume }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" readonly="readonly"/></td>
		    			<td align="right">默认税种:</td>
		    			<td align="left"><input id="goodsShortcut-widget-defaulttaxtype" type="text" style="width: 130px;" name="goodsInfo.defaulttaxtype" value="<c:out value="${goodsInfo.defaulttaxtype }"></c:out>" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品品牌:</td>
		    			<td align="left"><input id="goodsShortcut-widget-brand" type="text" style="width: 130px;" name="goodsInfo.brand" value="<c:out value="${goodsInfo.brand}"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">所属部门:</td>
		    			<td align="left"><input id="goodsShortcut-widget-deptid" type="text" style="width: 130px;" name="goodsInfo.deptid" value="<c:out value="${goodsInfo.deptid }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">默认供应商:</td>
		    			<td align="left"><input type="text" name="goodsInfo.defaultsupplier" style="width: 130px" value="<c:out value="${goodsInfo.defaultsupplierName }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-highestbuyprice" name="goodsInfo.highestbuyprice" style="width: 130px" 
	    					<c:choose>
								<c:when test="${showMap.highestbuyprice==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="${goodsInfo.highestbuyprice}" </c:otherwise>
							</c:choose>/><font color="red">*</font></td>
		    			<td align="right">基准销售价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.basesaleprice" style="width: 130px" 
		    				<c:choose>
								<c:when test="${showMap.basesaleprice==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="${goodsInfo.basesaleprice}" </c:otherwise>
							</c:choose>/></td>
		    			<td align="right">计划毛利率%:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999,precision:2,groupSeparator:','" name="goodsInfo.planmargin" style="width: 130px" value="${goodsInfo.planmargin }" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">采购箱价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-buyboxprice" name="goodsInfo.buyboxprice" style="width: 130px" readonly="readonly" value="${goodsInfo.buyboxprice}"/></td>
		    			<td align="right">是否采购:</td>
		    			<td align="left"><select name="goodsInfo.isinoutstorage" style="width: 130px;" disabled="disabled">
				    		<option value="1" <c:if test="${goodsInfo.isinoutstorage=='1'}">selected="selected"</c:if>>是</option>
				    		<option value="0" <c:if test="${goodsInfo.isinoutstorage=='0'}">selected="selected"</c:if>>否</option>
				    	</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认仓库:</td>
		    			<td align="left"><input id="goodsShortcut-widget-storage" type="text" style="width: 130px;" name="goodsInfo.storageid" value="<c:out value="${goodsInfo.storageid }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    			<td align="right">默认库位:</td>
		    			<td align="left"><input id="goodsShortcut-widget-storagelocation" type="text" style="width: 130px;" name="goodsInfo.storagelocation" value="<c:out value="${goodsInfo.storagelocation }"></c:out>" readonly="readonly"/></td>
		    			<td align="right"><div id="goodsShortcut-div-boxnumtext" style="visibility: hidden;">库位容量:</div></td>
		    			<td align="left"><div id="goodsShortcut-div-boxnum" style="visibility: hidden;">
		    				<input id="goodsShortcut-numberbox-boxnum" type="text" style="width: 130px;" name="goodsInfo.slboxnum" value="${goodsInfo.slboxnum }"readonly="readonly"/>
		    			</div></td>
		    		</tr>
		    		<tr>
		    			<td align="right">保质期管理:</td>
		    			<td align="left"><select name="goodsInfo.isshelflife" style="width: 136px" disabled="disabled">
		    				<option value="1" <c:if test="${goodsInfo.isshelflife=='1'}">selected="selected"</c:if>>是</option>
		    				<option value="0" <c:if test="${goodsInfo.isshelflife=='0'}">selected="selected"</c:if>>否</option>
		    			</select></td>
		    			<td align="right">保质期:</td>
		    			<td align="left"><span style="float: left;">
						    	<input type="text" name="goodsInfo.shelflife" style="width:90px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','" value="${goodsInfo.shelflife }" readonly="readonly"/>
							    <select name="goodsInfo.shelflifeunit" style="width:40px; float:left;" disabled="disabled">
							      <option value="1" <c:if test="${goodsInfo.shelflifeunit=='1'}">selected="selected"</c:if>>天</option>
								  <option value="2" <c:if test="${goodsInfo.shelflifeunit=='2'}">selected="selected"</c:if>>周</option>
							      <option value="3" <c:if test="${goodsInfo.shelflifeunit=='3'}">selected="selected"</c:if>>月</option>
							      <option value="4" <c:if test="${goodsInfo.shelflifeunit=='4'}">selected="selected"</c:if>>年</option>
							    </select>
							</span>
						</td>
						<td align="right">购销类型:</td>
		    			<td align="left"><select name="goodsInfo.bstype" style="width: 136px;" disabled="disabled">
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
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.normalprice" style="width: 130px" 
		    				<c:choose>
								<c:when test="${showMap.normalprice==null}">disabled="disabled" value=""</c:when>
								<c:otherwise>readonly="readonly" value="${goodsInfo.normalprice}" </c:otherwise>
							</c:choose>/></td>
		    			<td align="right">最新采购价:</td>
		    			<td align="left"><input type="text" id="goodsInfo-numberbox-newbuyprice" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.newbuyprice" style="width: 130px" disabled="disabled"
		    				<c:choose>
								<c:when test="${showMap.newbuyprice==null}"> value=""</c:when>
								<c:otherwise> value="${goodsInfo.newbuyprice}" </c:otherwise>
							</c:choose>/></td>
		    			<td align="right">最新销售价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.newsaleprice" style="width: 130px" disabled="disabled"
		    				<c:choose>
								<c:when test="${showMap.newsaleprice==null}"> value=""</c:when>
								<c:otherwise> value="${goodsInfo.newsaleprice}" </c:otherwise>
							</c:choose>/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注:</td>
		    			<td colspan="5" align="left"><input name="goodsInfo.remark" type="text" style="width: 590px" maxlength="200" value="<c:out value="${goodsInfo.remark }"></c:out>" readonly="readonly"/><font color="red">*</font></td>
		    		</tr>
	    			<div>
	    				<c:forEach var="list" items="${priceList}" varStatus="status">
		    				<c:if test="${(status.index)%3==0}">
		    					<tr>
		    					<input value="${status.index}" type="hidden"/>
		    				</c:if>
								<td align="right">${list.codename}:</td>
								<td align="left"><input name="priceInfoList[${status.index}].taxprice" type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','" style="width: 130px" value="${list.val }" readonly="readonly"/><c:if test="${status.index == 0 || status.index == 1 || status.index == 2}"><font color="red">*</font></c:if>
									<input name="priceInfoList[${status.index}].code" value="${list.code}" type="hidden"/>
									<input name="priceInfoList[${status.index}].name" value="${list.codename}" type="hidden"/>
								</td>
	  						<c:if test="${(status.index+1)%3==0}">
	  							</tr>
	  						</c:if>
  						</c:forEach>
					</div>
		    	</table>
		    </form>
		</div>
     </div>
     <script type="text/javascript">
     	$(function(){
     		loadDropdown();
     		
     	});
     </script>
  </body>
</html>
