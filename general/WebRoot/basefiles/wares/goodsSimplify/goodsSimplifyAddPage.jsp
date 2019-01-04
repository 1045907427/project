<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	 <title>商品档案新增页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true" id="wares-layout-goodsAddPage">
  		<div data-options="region:'center',split:true,border:true">
  			<form action="basefiles/addGoodsInfoShortcut.do" method="post" id="goodssimplify-form-add">
  				<input type="hidden" id="goodsSimplify-hidden-attaids" name="goodsInfo.imageids"/>
		    	<input type="hidden" id="goodinfo-hidden-hdimage" name="goodsInfo.image"/>
		    	<table cellpadding="1" cellspacing="1" border="0" >
		    		<tr>
		    			<td width="80" align="right">编码:</td>
		    			<td align="left"><input type="text" id="goodsInfo-id-baseInfo" style="width: 135px;" name="goodsInfo.id" class="easyui-validatebox" validType="validId[20]" required="true"/><font color="red">*</font></td>
		    			<td width="80" align="right">助记符:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.spell" maxlength="20" <c:if test="${colMap.spell == 'spell' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.spell == 'spell' }"><font color="red">*</font></c:if></td>
		    			<td width="80" align="right">状态:</td>
		    			<td align="left"><select class="easyui-combobox" readonly="readonly" style="width: 135px;">
		    				<option value="4">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
						<td align="right">名称:</td>
		    			<td align="left" colspan="3"><input id="goodsSimplify-input-name" type="text" style="width: 363px;" name="goodsInfo.name" class="easyui-validatebox" validType="validName[100]" required="true"/><font color="red">*</font></td>
		    			<td align="right">规格型号:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.model" maxlength="200" <c:if test="${colMap.model == 'model' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.model == 'model' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">名称拼音:</td>
		    			<td align="left"><input type="text" id="goodsSimplify-input-pinyin" style="width: 135px" name="goodsInfo.pinyin" <c:if test="${colMap.pinyin == 'pinyin' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.pinyin == 'pinyin' }"><font color="red">*</font></c:if></td>
		    			<td align="right">条形码:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.barcode" <c:if test="${colMap.barcode == 'barcode' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.barcode == 'barcode' }"><font color="red">*</font></c:if></td>
		    			<td align="right">箱装条形码:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.boxbarcode" <c:if test="${colMap.boxbarcode == 'boxbarcode' }">class="easyui-validatebox" data-options="required:true"</c:if>/><c:if test="${colMap.boxbarcode == 'boxbarcode' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品分类:</td>
		    			<td align="left"><input id="goodsSimplify-widget-defaultsort" value="<c:out value="${WCid }"></c:out>" type="text" name="goodsInfo.defaultsort"/><c:if test="${colMap.defaultsort == 'defaultsort' }"><font color="red">*</font></c:if></td>
		    			<td align="right">商品品牌:</td>
		    			<td align="left"><input id="goodsSimplify-widget-brand" type="text" name="goodsInfo.brand"/><c:if test="${colMap.brand == 'brand' }"><font color="red">*</font></c:if></td>
		    			<td align="right">所属部门:</td>
		    			<td align="left"><input id="goodsSimplify-widget-deptid" type="text" name="goodsInfo.deptid"/><c:if test="${colMap.deptid == 'deptid' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">商品类型:</td>
		    			<td align="left"><select name="goodsInfo.goodstype" style="width: 135px;" <c:if test="${colMap.goodstype == 'goodstype' }">class="easyui-combobox" required="true"</c:if>>
				    				<option></option>
		    						<c:forEach items="${goodstypeList }" var="list">
		    							<c:choose>
					    					<c:when test="${list.code == '1'}">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    					</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
				    			</select><c:if test="${colMap.goodstype == 'goodstype' }"><font color="red">*</font></c:if></td>
		    			<td align="right">默认供应商:</td>
		    			<td align="left"><input id="goodsSimplify-supplierWidget" type="text" name="goodsInfo.defaultsupplier"/><c:if test="${colMap.defaultsupplier == 'defaultsupplier' }"><font color="red">*</font></c:if></td>
		    			<td align="right">第二供应商:</td>
		    			<td align="left"><input id="goodsSimplify-secondsupplierWidget" type="text" name="goodsInfo.secondsupplier"/><c:if test="${colMap.secondsupplier == 'secondsupplier' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">长度(m):</td>
		    			<td align="left"><input type="text" id="goodsSimplify-numberbox-glength" style="width: 135px;" name="goodsInfo.glength"><c:if test="${colMap.glength == 'glength' }"><font color="red">*</font></c:if></td>
		    			<td align="right">高度(m):</td>
		    			<td align="left"><input type="text" id="goodsSimplify-numberbox-ghight" style="width: 135px;" name="goodsInfo.ghight"><c:if test="${colMap.ghight == 'ghight' }"><font color="red">*</font></c:if></td>
		    			<td align="right">宽度(m):</td>
		    			<td align="left"><input type="text" id="goodsSimplify-numberbox-gwidth" style="width: 135px;" name="goodsInfo.gwidth"><c:if test="${colMap.gwidth == 'gwidth' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">箱重(kg):</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.totalweight" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','<c:if test="${colMap.totalweight == 'totalweight' }">,required:true</c:if>"><c:if test="${colMap.totalweight == 'totalweight' }"><font color="red">*</font></c:if></td>
		    			<td align="right">箱体积(m<sup>3</sup>):</td>
		    			<td align="left"><input type="text" id="goodsSimplify-numberbox-totalvolume" style="width: 135px;" name="goodsInfo.totalvolume" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','<c:if test="${colMap.totalvolume == 'totalvolume' }">,required:true</c:if>"><c:if test="${colMap.totalvolume == 'totalvolume' }"><font color="red">*</font></c:if></td>
		    			<td align="right">ABC码:</td>
					    <td align="left"><select style="width: 135px;" name="goodsInfo.abclevel">
					    		<option></option>
					    		<option value="A">A</option>
					    		<option value="B">B</option>
					    		<option value="C">C</option>
					    	</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">主单位:</td>
		    			<td align="left"><input id="goodsSimplify-widget-mainunit" type="text" name="goodsInfo.mainunit"/><c:if test="${colMap.mainunit == 'mainunit' }"><font color="red">*</font></c:if></td>
		    			<td align="right">箱装量:</td>
		    			<td align="left"><input id="goodsSimplify-numberbox-rate" type="text" name="goodsMUInfo.rate" style="width: 135px;"/><c:if test="${colMap.rate == 'rate' }"><font color="red">*</font></c:if></td>
		    			<td align="right">辅单位:</td>
		    			<td align="left"><input id="goodsSimplify-widget-meteringunitid" type="text" name="goodsMUInfo.meteringunitid"/><c:if test="${colMap.meteringunitid == 'meteringunitid' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			
		    			<td align="right">默认仓库:</td>
		    			<td align="left"><input id="goodsSimplify-widget-storage" type="text" name="goodsInfo.storageid"/><c:if test="${colMap.storageid == 'storageid' }"><font color="red">*</font></c:if></td>
		    			<td align="right">库位管理:</td>
					    <td align="left"><select name="goodsInfo.isstoragelocation" style="width: 135px;" <c:if test="${colMap.isstoragelocation == 'isstoragelocation' }">class="easyui-combobox" required="true"</c:if>>
					    		<option></option>
					    		<option value="1">是</option>
					    		<option value="0" selected="selected">否</option>
					    	</select><c:if test="${colMap.isstoragelocation == 'isstoragelocation' }"><font color="red">*</font></c:if></td>
					    <td align="right">商品货位:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.itemno" <c:if test="${colMap.itemno == 'itemno' }">class="easyui-validatebox" required="true"</c:if>/><c:if test="${colMap.itemno == 'itemno' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
		    			<td align="right">购销类型:</td>
		    			<td align="left"><select name="goodsInfo.bstype" style="width: 135px;"  class="easyui-combobox">
			    				<option></option>
			    				<c:forEach items="${bstypeList }" var="list">
				    				<c:choose>
				    					<c:when test="${list.code == '1'}">
				    						<option value="${list.code }" selected="selected">${list.codename }</option>
				    					</c:when>
				    					<c:otherwise>
				    						<option value="${list.code }">${list.codename }</option>
				    					</c:otherwise>
				    				</c:choose>
				    			</c:forEach>
			    			</select></td>
			    		<td align="right">批次管理:</td>
					    <td align="left"><select name="goodsInfo.isbatch" style="width: 135px;" <c:if test="${colMap.isbatch == 'isbatch' }">class="easyui-combobox" required="true"</c:if>>
					    		<option></option>
					    		<option value="1">是</option>
					    		<option value="0" selected="selected">否</option>
					    	</select><c:if test="${colMap.isbatch == 'isbatch' }"><font color="red">*</font></c:if></td>
		    			<td align="right">默认采购员:</td>
					    <td align="left"><input id="goodsInfo-widget-defaultbuyer" type="text" style="width: 135px;" name="goodsInfo.defaultbuyer"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">保质期管理:</td>
		    			<td align="left"><select id="goodsInfo-numberbox-isshelflife" name="goodsInfo.isshelflife" style="width: 135px;"  <c:if test="${colMap.isshelflife == 'isshelflife' }">required="true"</c:if>>
				    				<option value="1">是</option>
				    				<option value="0" selected="selected">否</option>
				    			</select><c:if test="${colMap.isshelflife == 'isshelflife' }"><font color="red">*</font></c:if></td>
		    			<td align="right">保质期:</td>
		    			<td align="left"><span style="float: left;">
								    	<input id="goodsInfo-numberbox-shelflife" type="text" name="goodsInfo.shelflife" style="width:69px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','<c:if test="${colMap.shelflife == 'shelflife' }">,required:true</c:if>"/>
									    <select name="goodsInfo.shelflifeunit" style="width:60px; float:left;" class="easyui-combobox" >
									      <option value="1" selected="selected">天</option>
										  <option value="2">周</option>
									      <option value="3">月</option>
									      <option value="4">年</option>
									    </select>
									</span><div id="goods-red-shelflife" style="display: none;"><font color="red">*</font></div></td>
						<td align="right">默认税种:</td>
		    			<td align="left"><input id="goodsSimplify-widget-defaulttaxtype" type="text" name="goodsInfo.defaulttaxtype" value="<c:out value="${defaulttaxtype }"></c:out>"/><c:if test="${colMap.defaulttaxtype == 'defaulttaxtype' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
			    		<td align="right">采购价:</td>
		    			<td align="left"><input id="goodsSimplify-numberbox-highestbuyprice" type="text" name="goodsInfo.highestbuyprice" style="width: 135px;"/><c:if test="${colMap.highestbuyprice == 'highestbuyprice' }"><font color="red">*</font></c:if></td>
		    			<td align="right">最低销售价:</td>
						<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','<c:if test="${colMap.lowestsaleprice == 'lowestsaleprice' }">,required:true</c:if>" name="goodsInfo.lowestsaleprice" style="width: 135px;"><c:if test="${colMap.lowestsaleprice == 'lowestsaleprice' }"><font color="red">*</font></c:if></td>
		    			<td align="right">基准销售价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','<c:if test="${colMap.basesaleprice == 'basesaleprice' }">,required:true</c:if>" name="goodsInfo.basesaleprice" style="width: 135px;"/><c:if test="${colMap.basesaleprice == 'basesaleprice' }"><font color="red">*</font></c:if></td>
		    		</tr>
		    		<tr>
					 	<td align="right">最高库存:</td>
					    <td align="left"><input type="text" style="width: 135px;" name="goodsInfo.highestinventory" class="easyui-numberbox" data-options="min:0,max:99999999,precision:${decimallen},groupSeparator:','"></td>
						<td align="right">最低库存:</td>
					    <td align="left"><input type="text" style="width: 135px;" name="goodsInfo.lowestinventory" class="easyui-numberbox" data-options="min:0,max:99999999,precision:${decimallen},groupSeparator:','"></td>
						<td align="right">安全库存:</td>
					    <td align="left"><input type="text" style="width: 135px;" name="goodsInfo.safeinventory" class="easyui-numberbox" data-options="min:0,max:99999999,precision:${decimallen},groupSeparator:','"></td>
					</tr>
		    		<tr>
				    	<td align="right">采购箱价:</td>
				    	<td align="left"><input type="text" id="goodsSimplify-numberbox-buyboxprice" name="goodsInfo.buyboxprice" style="width: 135px;"/></td>
		    			<td align="right">最小发货单位:</td>
						<td align="left"><input type="text" name="goodsInfo.minimum" style="width:135px;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','<c:if test="${colMap.minimum == 'minimum' }">,required:true</c:if>"/></td>
		    			<td align="right">产地:</td>
		    			<td align="left"><input type="text" style="width: 135px;" name="goodsInfo.productfield"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">最新采购价:</td>
		    			<td align="left"><input type="text" id="goodsSimplify-numberbox-newbuyprice" class="easyui-numberbox no_input" data-options="min:0,max:999999999999,precision:6,groupSeparator:','" name="goodsInfo.newbuyprice" style="width: 135px;" readonly="readonly"/></td>
		    			<td align="right">最新库存价:</td>
		    			<td align="left"><input type="text" class="easyui-numberbox" data-options="max:999999999999,precision:6,groupSeparator:','"style="width: 135px;" disabled="disabled"/></td>
                        <td align="right">成本未分摊<br/>金额:</td>
                        <td align="left"><input readonly="readonly" type="text" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"  style="width: 135px;" disabled="disabled"/></td>
                    </tr>
                    <tr>
						<td align="right">备注:</td>
						<td align="left" colspan="5"><input name="goodsInfo.remark" type="text" style="width: 598px;" value="${goodsInfo.remark }" maxlength="200"/></td>
                    </tr>
					<c:if test="${useHTKPExport=='1'}">
					<tr>
							<td align="right">金税商品编码：</td>
							<td>
								<input type="text" id="goodsInfo-jsgoodsid-baseInfo" style="width: 135px;" name="goodsInfo.jsgoodsid" class="easyui-validatebox" validType="maxLen[30]"/>
							</td>
							<td align="right">金税税收<br/>分类编码：</td>
							<td>
								<input type="text" id="goodsInfo-jstaxsortid-baseInfo" style="width: 135px;" name="goodsInfo.jsgoodsid" class="easyui-validatebox" validType="maxLen[30]"/>
							</td>
					</tr>
					</c:if>
		    		<div>
		   				<c:forEach var="list" items="${priceList}" varStatus="status">
		    				<c:if test="${(status.index)%3==0}">
		    					<tr>
		    				</c:if>
							<td align="right">${list.codename}-箱价:</td>
							
							<td align="left">
                                <input id="goodsSimplify-numberbox-taxprice-${status.index}" name="priceInfoList[${status.index}].taxprice" type="text" style="width: 65px;"/>-<input id="goodsSimplify-numberbox-boxprice-${status.index}" name="priceInfoList[${status.index}].boxprice" type="text" style="width: 65px;"/><c:if test="${status.index == 0 || status.index == 1 || status.index == 2}"><font color="red">*</font></c:if>
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
  		<div data-options="region:'south'">
            <div class="buttonDetailBG" style="text-align:right;">
                <security:authorize url="/basefiles/goodesSimplifyUploadBtn.do">
                    <input type="button" id="wares-upload-goodsAddPage" value="上传图片"/>
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifySavegoonBtn.do">
                    <input type="button" id="wares-savegoon-goodsAddPage" value="继续添加"/>
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifySaveBtn.do">
                    <input type="button" id="wares-save-goodsAddPage" value="保存"/>
                </security:authorize>
                    <input type="button" id="wares-close-goodsAddPage" value="关闭"/>
            </div>
  		</div>
  	</div>
    <script type="text/javascript">
    	var imgids = "";
    	var imgpathmap = {};
    	$(function(){
    		loadDropdown();
    		
    		$("#goodsSimplify-input-name").change(function(){
    			var val = $("#goodsSimplify-input-name").val();
    			if(val != ""){
    				var str = getPinYingJCLen(val);
    				$("#goodsSimplify-input-pinyin").val(str);
    			}else{
    				$("#goodsSimplify-input-pinyin").val("");
    			}
    		});
    		
    		//上传图片按钮，浏览 
			$("#wares-upload-goodsAddPage").click(function(){
                if(!checkSencondSupplierNum()){
                    return false;
                }
				var ret = goodsInfo_AjaxConn({id:$("#goodsInfo-id-baseInfo").val()},'basefiles/isRepeatGoodsInfoID.do');
				var retjson = $.parseJSON(ret);
				if(retjson.flag){//已存在该商品
					var wholeInfo = $("#goodssimplify-form-add").serializeJSON();
					wholeInfo["goodsInfo.oldId"] = $("#goodsInfo-id-baseInfo").val();
					ret = goodsInfo_AjaxConn(wholeInfo,'basefiles/editGoodsInfoShortcut.do');
					retjson = $.parseJSON(ret);
					if(retjson.flag){
						goods_open_upload_dialog("edit");
					}
				}else{
					if(!$("#goodssimplify-form-add").form('validate')){
						$.messager.alert("提醒","请填写必填项!");
						return false;
					}
					ret = goodsInfo_AjaxConn($("#goodssimplify-form-add").serializeJSON(),'basefiles/addGoodsInfoShortcut.do');
					retjson = $.parseJSON(ret);
					if(retjson.flag){
						var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
      					$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
						goods_open_upload_dialog("add");
					}
				}
			});
    		
    		//保存并继续添加
	   		$("#wares-savegoon-goodsAddPage").click(function(){
                if(!checkSencondSupplierNum()){
                    return false;
                }
	   			if(!$("#goodssimplify-form-add").form('validate')){
	   				$.messager.alert("提醒","请填写必填项!");
    				return false;
    			}
				$.messager.confirm("提醒","是否保存并继续添加商品?",function(r){
					if(r){
						goods_savegoon_form_submit();
						$("#goodssimplify-form-add").submit();
					}
				});
	   		});
	   		//保存
	   		$("#wares-save-goodsAddPage").click(function(){
                if(!checkSencondSupplierNum()){
                    return false;
                }
	   			if(!$("#goodssimplify-form-add").form('validate')){
	   				$.messager.alert("提醒","请填写必填项!");
    				return false;
    			}
				$.messager.confirm("提醒","是否保存商品?",function(r){
					if(r){
						goods_save_form_submit();
						$("#goodssimplify-form-add").submit();
					}
				});
	   		});
	   		//关闭
	   		$("#wares-close-goodsAddPage").click(function(){
	   			$("#wares-dialog-goodsListPage1").dialog('close');
	   		});
        jstaxsortWidget();
    	});
    function jstaxsortWidget(){
		<c:if test="${useHTKPExport=='1'}">
        $("#goodsInfo-jstaxsortid-baseInfo").widget({
            referwid:'RL_T_BASE_JSTAXTYPECODE',
            singleSelect:true,
            width:135
        });
		</c:if>
	}
</script>
</body>
</html>
