<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品档案列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<input type="hidden" id="goodsInfo-opera"/>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div title="商品档案列表" class="easyui-layout" data-options="fit:true,border:true">
        <div title="" data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
            <div class="buttonBG" id="goodsInfo-button-div"></div>
            <input id="wares-id-hdWaresClassId" type="hidden"/>
            <input id="wares-pid-hdWaresClassPid" type="hidden"/>
        </div>
        <div title="商品分类" data-options="region:'west',split:true,border:false" style="width:160px;">
            <div id="wares-tree-waresClass" class="ztree"></div>
        </div>
        <div title="商品列表" data-options="region:'center',split:true,border:false">
            <div id="wares-query-showGoodsInfoList" style="padding: 0px;">
                <form action="" id="wares-form-goodsInfoListQuery" method="post" style="display: none;">
                    <input type="hidden" name="exportids" id="goodsInfo-exportids-checked"/>
                    <table class="querytable">
                        <tr>
                            <td>编码/条形码:</td>
                            <td><input type="text" name="id" style="width:150px"/>
                                <input id="goods-hddefaultsort" name="defaultsort" type="hidden"/>
                            </td>
                            <td>名称:</td>
                            <td><input type="text" name="name" style="width:150px"/></td>
                            <td>所属供应商:</td>
                            <td><input id="wares-widget-goodsShortcut-supplierid" type="text" name="defaultsupplier"/>
                            </td>
                        </tr>
                        <tr>
                            <td>商品品牌:</td>
                            <td><input id="wares-widget-goodsShortcut-brand" type="text" name="brandid"/></td>
                            <td>所属部门:</td>
                            <td><input id="wares-widget-goodsShortcut-deptid" type="text" name="deptid"/></td>
                            <td>状态:</td>
                            <td><select id="wares-widget-goodsShortcut-state" name="state" style="width:138px;">
                                <option></option>
                                <option value="2">保存</option>
                                <option value="1" selected="selected">启用</option>
                                <option value="0">禁用</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"></td>
                            <td colspan="2">
                                <a href="javaScript:void(0);" id="wares-query-queryGoodsInfoList"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="wares-query-reloadGoodsInfoList"
                                   class="button-qr">重置</a>
                                <span id="goodsInfo-query-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="wares-table-goodsInfoList"></table>
        </div>
        </div>
    </div>
	<div style="display: none;">
        <div id="wares-dialog-showUpdateGoodsInfoForJS"></div>
		<div id="wares-dialog-goodsInfoEditMore"></div>
		<div id="wares-dialog-goodsListPage"></div>
		<div id="wares-div-uploadimag"></div>
		<div id="wares-window-showImgView"></div>
		<div id="test" style="display: none;"></div>
		<a href="javaScript:void(0);" id="wares-goodsSimplifyListPage-buttons-exportclick" style="display: none"title="导出">导出</a>
		<a href="javaScript:void(0);" id="wares-goodsSimplifyListPage-buttons-importclick" style="display: none"title="导入">导入</a>
		<a href="javaScript:void(0);" id="goodsSimplifyListPage-dialog-printByModule" style="display: none"title="按模板导出">按模板导出</a>
		<a href="javaScript:void(0);" id="goodsSimplifyListPage-dialog-htkpexportclick" style="display: none"title="按航天存货格式导出">按航天存货格式导出</a>
		<a href="javaScript:void(0);" id="goodsSimplifyListPage-dialog-htkpsplitbrandexportclick" style="display: none"title="分品牌按金税格式导出">分品牌按金税格式导出</a>
		<div id="goodsSimplifyListPage-dialog-htkpsplitbrandexport">
			<form action="" id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form" method="post">
				<table>
					<tbody>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td style="text-align: right">品牌：</td>
						<td>
							<input type="text" name="brandidarrs" id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-brandid-widget"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right">编码版本：</td>
						<td>
							<input type="text" name="bmbbh" id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-bmbbh" value="${jsGoodsVersion}" style="width:200px;"/>
						</td>
					</tr>
                    <tr>
                        <td style="text-align: right">
                            金税编码：
                        </td>
                        <td>
                            <select id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-jsgoodsidcreatemethod" name="jsgoodsidcreatemethod" style="width:200px">
                                <option value="1">系统</option>
                                <option value="2">自动生成</option>
                                <option value="3">设置空白</option>
                            </select>
                            <a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showGoodsHelpDialog('jsbrandexportjsgoodsid');"> </a>
                        </td>
                    </tr>
                    <tr id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-jsgoodsidcreatemethod-tr" style="display: none;">
                        <td style="text-align: right">自增编号长度：</td>
                        <td>
                            <input type="text" name="jsgoodsidlen" value="${jsGoodsidLen}" id="goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-jsgoodsidlen" value="${jsgoodsidlen}" style="width:200px;"  class="easyui-numberbox" data-options="precision:0,max:7"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr/>
                            过滤条件：
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right">商品状态：</td>
                        <td>
                            <select  name="filtergoodstate" style="width:200px">
                                <option value="">全部</option>
                                <option value="1">启用</option>
                                <option value="0">禁用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right">品牌状态：</td>
                        <td>
                            <select  name="filterbrandstate" style="width:200px">
                                <option value="">全部</option>
                                <option value="1">启用</option>
                                <option value="0">禁用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right">金税编码：</td>
                        <td>
                            <select  name="filterjsgoodsid" style="width:200px">
                                <option value="">全部</option>
                                <option value="1">不为空</option>
                                <option value="2">为空</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right">税收分类编码：</td>
                        <td>
                            <select  name="filterjstaxsortid" style="width:200px">
                                <option value="">全部</option>
                                <option value="1">不为空</option>
                                <option value="2">为空</option>
                            </select>
                        </td>
                    </tr>
					</tbody>
				</table>
			</form>
		</div>
		<a href="javaScript:void(0);" id="goodsSimplifyListPage-buttons-convertexceltoxmlforhtkp-click" style="display: none"title="excel格式转换成金税导入">excel格式转换成金税导入</a>
		<a href="javaScript:void(0);" id="goodsSimplifyListPage-buttons-importAndUpdateJSGoodsHTKP-click" style="display: none"title="导入并更新金税商品字段">导入并更新金税商品字段</a>

	</div>

<div style="display: none;">
    <div id="goodsSimplifyListPage-help-simple-jsbrandexportjsgoodsid" style="display: none">
        金税编码相关帮助<br/>
        1）如果选择“设置空白”，则导出来的“金税编码”为空。<br/>
        2）<b style="color: #f00">新编码会覆盖金税系统中商品编码。</b><br/>
        &nbsp;&nbsp;&nbsp; 3）如果选择“自动生成”，则系统会生成“金税编码”。<br/>
        &nbsp;金税编码注意：<br/>
        &nbsp;3-1）自动生成编码用于初始化更新“金税系统”时使用。<br/>
        &nbsp;3-2）“金税编码”由“簇编码”+“自增编号”组成。<br/>
        &nbsp;3-3）“金税簇编码”在品牌档案中的“金税簇编码”设置，可理解为分类，推荐使用“055010”开始编码<br/>
        &nbsp;3-3）“自增编号”，一般是使用自增形式，默认4位，从"0001"开始。<br/>
        &nbsp;3-4）“自增编号”长度，必须保证所有金税商品自增编号长度一置。<br/>
    </div>
</div>

	<script type="text/javascript">
		var rootpath = '<%=basePath %>';
		var goodsInfo_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}

        function getPinYingJCLen(str){
            var ret = goodsInfo_AjaxConn({str:str},'basefiles/getPinYingJCLen.do');
            var json = $.parseJSON(ret);
            return json.pinyin;
        }

        //检验允许输入第二供应商个数
        function checkSencondSupplierNum(){
            var flag = true;
            var secSupIds = $("#goodsSimplify-secondsupplierWidget").widget('getValue');
            if(null != secSupIds && secSupIds != ""){
                var secSupIdArr = secSupIds.split(",");
                if(secSupIdArr.length > Number("${secondSupplierNum}")){
                    $.messager.alert("提醒","最多允许输入超过${secondSupplierNum}个第二供应商！");
                    $("#goodsSimplify-secondsupplierWidget").widget('clear');
                    flag = false;
                }
            }
            return flag;
        }

        //初始化第二供应商
        function initSencondSupplierWidget(noval){
            $("#goodsSimplify-secondsupplierWidget").widget({
                <c:if test="${colMap.secondsupplier == 'secondsupplier' }">required:true,</c:if>
                param:[{field:'id',op:'notequal',value:noval}],
                width:135,
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                singleSelect:false,
                onSelect:function(data){
                    var objs = $(this).widget('getObjects');
                    if(objs.length > Number("${secondSupplierNum}")){
                        $.messager.alert("提醒","最多允许输入超过${secondSupplierNum}个第二供应商！");
                        setTimeout("$('#goodsSimplify-secondsupplierWidget').widget('clear')",10)
                        return false;
                    }
                }
            });
        }

		//加载下拉框 
		function loadDropdown(){
			//商品分类
			$("#goodsSimplify-widget-defaultsort").widget({
				width:135,
				name:'t_base_goods_info',
				col:'defaultsort',
				singleSelect:true,
				<c:if test="${colMap.defaultsort == 'defaultsort' }">required:true,</c:if>
				onlyLeafCheck:true
			});
			//商品品牌
			$("#goodsSimplify-widget-brand").widget({
				width:135,
				name:'t_base_goods_info',
				col:'brand',
				singleSelect:true,
				<c:if test="${colMap.brand == 'brand' }">required:true,</c:if>
				onlyLeafCheck:false,
		  		onSelect:function(data){
		  			$("#goodsSimplify-widget-deptid").widget('setValue',data.deptid);
		  			$("#goodsSimplify-supplierWidget").widget('setValue',data.supplierid);
		  			//第二供应商
                    initSencondSupplierWidget(data.supplierid);
		  		},
		  		onClear:function(){
		  			$("#goodsSimplify-widget-deptid").widget('clear');
		  			$("#goodsSimplify-supplierWidget").widget('clear');
		  			$("#goodsSimplify-secondsupplierWidget").widget('clear');
		  		}
			});
			
			//所属部门
			$("#goodsSimplify-widget-deptid").widget({
				width:135,
				name:'t_base_goods_info',
				col:'deptid',
				singleSelect:true,
				<c:if test="${colMap.deptid == 'deptid' }">required:true,</c:if>
				onlyLeafCheck:true
			});
			
			//默认供应商
            $("#goodsSimplify-supplierWidget").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
				<c:if test="${colMap.defaultsupplier == 'defaultsupplier' }">required:true,</c:if>
                width:135,
				onSelect:function(data){
					//第二供应商
                    initSencondSupplierWidget(data.id);
				}
			});
			//第二供应商
            initSencondSupplierWidget($("#goodsSimplify-supplierWidget").widget('getValue'));

			//主单位
			$("#goodsSimplify-widget-mainunit").widget({
		  		width:135,
				name:'t_base_goods_info',
				col:'mainunit',
				singleSelect:true,
				<c:if test="${colMap.mainunit == 'mainunit' }">required:true,</c:if>
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#goodsSimplify-widget-meteringunitid").widget({
				  		width:135,
						referwid:'RL_T_BASE_GOODS_METERINGUNIT',
						singleSelect:true,
						<c:if test="${colMap.meteringunitid == 'meteringunitid' }">required:true,</c:if>
						param:[{field:'id',op:'notequal',value:data.id}],
						onlyLeafCheck:false
					});
				}
			});
			//辅单位
			$("#goodsSimplify-widget-meteringunitid").widget({
		  		width:135,
				referwid:'RL_T_BASE_GOODS_METERINGUNIT',
				singleSelect:true,
				<c:if test="${colMap.meteringunitid == 'meteringunitid' }">required:true,</c:if>
				param:[{field:'id',op:'equal',value:$("#goodsInfo-mainunit").val()}],
				onlyLeafCheck:false
			});
			
			//箱装量
			$("#goodsSimplify-numberbox-rate").numberbox({
				<c:if test="${colMap.rate == 'rate' }">required:true,</c:if>
				min:0,
				max:999999999999,
				precision:general_bill_decimallen,
				groupSeparator:',',
                onChange:function(newValue,oldValue){
					if(newValue == Number(0)){
						$(this).numberbox('setValue',"9999");
					}
                    var highestbuyprice = $("#goodsSimplify-numberbox-highestbuyprice").numberbox('getValue');
                    if("" != highestbuyprice && Number(highestbuyprice) != 0){
                        var buyboxprice = highestbuyprice*newValue;
                        $("#goodsSimplify-numberbox-buyboxprice").numberbox('setValue',buyboxprice);
                    }
                    //价格套箱价
                    <c:forEach var="list" items="${priceList}" varStatus="status">
                        var taxprice_${status.index} = $("#goodsSimplify-numberbox-taxprice-${status.index}").numberbox('getValue');
                        if("" != taxprice_${status.index} && Number(taxprice_${status.index}) != 0){
                            var boxprice_${status.index} = taxprice_${status.index}*newValue;
                            $("#goodsSimplify-numberbox-boxprice-${status.index}").numberbox('setValue',boxprice_${status.index});
                        }else{
                            var boxprice_${status.index} = $("#goodsSimplify-numberbox-boxprice-${status.index}").numberbox('getValue');
                            if("" != boxprice_${status.index} && Number(boxprice_${status.index}) != 0){
                                if("" == newValue || Number(newValue) == 0){
                                    newValue = Number(1);
                                }
                                var taxprice_${status.index} = boxprice_${status.index}/newValue;
                                $("#goodsSimplify-numberbox-taxprice_-${status.index}").numberbox('setValue',taxprice_${status.index});
                            }
                        }
                    </c:forEach>
                }
			});
			
			//长度
			$("#goodsSimplify-numberbox-glength").numberbox({
				<c:if test="${colMap.glength == 'glength' }">required:true,</c:if>
				min:0,
				max:999999999999,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					var ghight = $("#goodsSimplify-numberbox-ghight").numberbox('getValue');
					var gwidth = $("#goodsSimplify-numberbox-gwidth").numberbox('getValue');
					var totalvolume = newValue*ghight*gwidth;
					$("#goodsSimplify-numberbox-totalvolume").numberbox('setValue',totalvolume);
				}
			});
			//高度
			$("#goodsSimplify-numberbox-ghight").numberbox({
				<c:if test="${colMap.ghight == 'ghight' }">required:true,</c:if>
				min:0,
				max:999999999999,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					var glength = $("#goodsSimplify-numberbox-glength").numberbox('getValue');
					var gwidth = $("#goodsSimplify-numberbox-gwidth").numberbox('getValue');
					var totalvolume = newValue*glength*gwidth;
					$("#goodsSimplify-numberbox-totalvolume").numberbox('setValue',totalvolume);
				}
			});
			//宽度
			$("#goodsSimplify-numberbox-gwidth").numberbox({
				<c:if test="${colMap.gwidth == 'gwidth' }">required:true,</c:if>
				min:0,
				max:999999999999,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					var glength = $("#goodsSimplify-numberbox-glength").numberbox('getValue');
					var ghight = $("#goodsSimplify-numberbox-ghight").numberbox('getValue');
					var totalvolume = newValue*glength*ghight;
					$("#goodsSimplify-numberbox-totalvolume").numberbox('setValue',totalvolume);
				}
			});
			
			//最高采购价
			$("#goodsSimplify-numberbox-highestbuyprice").numberbox({
				<c:if test="${colMap.highestbuyprice == 'highestbuyprice' }">required:true,</c:if>
				min:0,
				max:999999999999,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
                    $("#goodsSimplify-numberbox-newbuyprice").numberbox('setValue',newValue);
					var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
					if("" == boxnum || Number(boxnum) == 0){
						boxnum = Number(1);
					}
					var buyboxprice = boxnum*newValue;
					$("#goodsSimplify-numberbox-buyboxprice").numberbox('setValue',buyboxprice);
				}
			});
			
			//采购箱价
			$("#goodsSimplify-numberbox-buyboxprice").numberbox({
				min:0,
				max:999999999999,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
					if("" == boxnum || Number(boxnum) == 0){
						boxnum = Number(1);
					}
					var highestbuyprice = newValue/boxnum;
					$("#goodsSimplify-numberbox-highestbuyprice").numberbox('setValue',highestbuyprice);
				}
			});
			
			//默认仓库
			$("#goodsSimplify-widget-storage").widget({
				width:135,
				name:'t_base_goods_info',
				col:'storageid',
				singleSelect:true,
				<c:if test="${colMap.storageid == 'storageid' }">required:true,</c:if>
				onlyLeafCheck:false
			});
			//默认采购员
			$("#goodsInfo-widget-defaultbuyer").widget({
				width:135,
				name:'t_base_goods_info',
				col:'defaultbuyer',
				singleSelect:true,
				onlyLeafCheck:false
			});
			//保质期
			$("#goodsInfo-numberbox-isshelflife").combobox({
    			onSelect:function(record){
    				if("1" == record.value){
    					$("#goodsInfo-numberbox-shelflife").numberbox({
    						min:0,
    						max:9999999999,
    						groupSeparator:',',
    						required:true
    					});
    					document.getElementById("goods-red-shelflife").style.display="block";
    				}else if("0" == record.value){
    					$("#goodsInfo-numberbox-shelflife").numberbox({
    						min:0,
    						max:9999999999,
    						groupSeparator:',',
    						<c:choose>
	    						<c:when test="${colMap.shelflife == 'shelflife'}">
	    							required:true
	    						</c:when>
	    						<c:otherwise>
	    							required:false
	    						</c:otherwise>
	    					</c:choose>
    					});
    					<c:choose>
    						<c:when test="${colMap.shelflife == 'shelflife'}">
    							document.getElementById("goods-red-shelflife").style.display="block";
    						</c:when>
    						<c:otherwise>
    							document.getElementById("goods-red-shelflife").style.display="none";
    						</c:otherwise>
    					</c:choose>
    				}
    			}
    		});
			//默认库位
			$("#goodsSimplify-widget-storagelocation").widget({
				width:135,
				name:'t_base_goods_info',
				col:'storagelocation',
				singleSelect:true,
				onlyLeafCheck:false,
				onChecked:function(data,checked){
					if(checked){
						document.getElementById("goodsSimplify-div-boxnumtext").style.visibility="visible";
						document.getElementById("goodsSimplify-div-boxnum").style.visibility="visible";
						$("#goodsShortcut-numberbox-boxnum").numberbox({
						    required:true,
						    min:0,
						    precision:0,
						    groupSeparator:','
						});
						$("#goodsShortcut-numberbox-boxnum").focus();
					}else{
						document.getElementById("goodsSimplify-div-boxnumtext").style.visibility="hidden";
						document.getElementById("goodsSimplify-div-boxnum").style.visibility="hidden";
						$("#goodsShortcut-numberbox-boxnum").removeClass();
					}
				},
				onLoadSuccess:function(){
					var val = $(this).widget('getValue');
					if(null != val && val != ""){
						document.getElementById("goodsSimplify-div-boxnumtext").style.visibility="visible";
						document.getElementById("goodsSimplify-div-boxnum").style.visibility="visible";
						$("#goodsShortcut-numberbox-boxnum").numberbox({
						    required:true,
						    min:0,
						    precision:0,
						    groupSeparator:','
						});
						$("#goodsShortcut-numberbox-boxnum").focus();
					}else{
						document.getElementById("goodsSimplify-div-boxnumtext").style.visibility="hidden";
						document.getElementById("goodsSimplify-div-boxnum").style.visibility="hidden";
						$("#goodsShortcut-numberbox-boxnum").removeClass();
					}
				},
				onClear:function(){
					document.getElementById("goodsSimplify-div-boxnumtext").style.visibility="hidden";
					document.getElementById("goodsSimplify-div-boxnum").style.visibility="hidden";
					$("#goodsShortcut-numberbox-boxnum").removeClass();
				}
			});
			//默认税种
			$("#goodsSimplify-widget-defaulttaxtype").widget({
				width:135,
				name:'t_base_goods_info',
				col:'defaulttaxtype',
				singleSelect:true,
				<c:if test="${colMap.defaulttaxtype == 'defaulttaxtype' }">required:true,</c:if>
				onlyLeafCheck:false
			});
            //价格套-箱价转换
            <c:forEach var="list" items="${priceList}" varStatus="status">
                $("#goodsSimplify-numberbox-taxprice-${status.index}").numberbox({
                    <c:if test="${status.index == 0 || status.index == 1 || status.index == 2}">required:true,</c:if>
                    min:0,
                    max:999999999999,
                    precision:6,
                    groupSeparator:',',
                    onChange:function(newValue,oldValue){
                        var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
                        if("" == boxnum || Number(boxnum) == 0){
                            boxnum = Number(1);
                        }
                        var boxprice = newValue*boxnum;
                        $("#goodsSimplify-numberbox-boxprice-${status.index}").numberbox('setValue',boxprice);
                    }
                });

                $("#goodsSimplify-numberbox-boxprice-${status.index}").numberbox({
                    <c:if test="${status.index == 0 || status.index == 1 || status.index == 2}">required:true,</c:if>
                    min:0,
                    max:999999999999,
                    precision:2,
                    groupSeparator:',',
                    onChange:function(newValue,oldValue){
                        var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
                        if("" == boxnum || Number(boxnum) == 0){
                            boxnum = Number(1);
                        }
                        var taxprice = newValue/boxnum;
                        $("#goodsSimplify-numberbox-taxprice-${status.index}").numberbox('setValue',taxprice);
                    }
                });
            </c:forEach>

		}
		
		//检验商品档案数据（唯一性，最大长度等）
		$.extend($.fn.validatebox.defaults.rules, {
   			validId:{//编号唯一性,最大长度
   				validator:function(value,param){
   					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
   						var ret = goodsInfo_AjaxConn({id:value},'basefiles/isRepeatGoodsInfoID.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validId.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			validName:{//名称唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						var ret = goodsInfo_AjaxConn({name:value},'basefiles/isRepeatGoodsInfoName.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			maxLen:{//最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						return true;
   					}
   					else{
   						$.fn.validatebox.defaults.rules.maxLen.message = '最多可输入{0}个字符!';
   						return false;
   					}
   				},
   				message:''
   			},
   			isRepeatBoxbarcode:{//唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						var ret = goodsInfo_AjaxConn({boxbarcode:value},'basefiles/isRepeatGoodsInfoBoxbarcode.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '箱装条形码重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			isRepeatItemno:{//唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						var ret = goodsInfo_AjaxConn({itemno:value},'basefiles/isRepeatGoodsInfoItemno.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.isRepeatItemno.message = '商品货位重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.isRepeatItemno.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			minVal:{//最小值大于0
   				validator:function(value,param){
   					return parseFloat(value) > 0 || parseInt(value);
   				},
   				message:'输入的值必须大于0'
   			},
   			barcodeVal:{//条形码规则
   				validator:function(value,param){
   					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
   					return reg.test(value)
   				},
   				message:'条形码错误,请重新输入'
   			}
		});
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var goodsInfoListColJson=$("#wares-table-goodsInfoList").createGridColumnLoad({
	     	name:'base_goods_info',
	     	frozenCol:[[{field:'ck',checkbox:true}]],
	     	commonCol:[[{field:'id',title:'编码',resizable:true,sortable:true},
	     				{field:'name',title:'名称',width:250,sortable:true},
                        {field:'spell',title:'助记符',width:95,sortable:true},
	     				{field:'barcode',title:'条形码',width:95,sortable:true},
	     				{field:'mainunit',title:'单位',width:45,sortable:true,
	     					formatter:function(val,rowData,rowIndex){
								return rowData.mainunitName;
							}
	     				},
	     				{field:'boxnum',title:'箱装量',width:45,isShow:true},
	     				{field:'field01',title:'采购价',resizable:true,sortable:true,isShow:true,
	     					formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
	     				},
						{field:'newbuyprice',title:'最新采购价',resizable:true,sortable:true,isShow:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
	     				<c:forEach var="list" items="${priceList}" varStatus="status">
	     					<c:if test="${status.index < pricenum}">
		     					{field:'${list.code}',title:'${list.codename}',resizable:true,isShow:true,
			     					formatter:function(value,rowData,rowIndex){
                                        if(rowData.priceList != null && undefined != rowData.priceList){
                                            for(var i=0;i<rowData.priceList.length;i++){
                                                var priceInfo = rowData.priceList[i];
                                                if(priceInfo.code == '${list.code}'){
                                                    return formatterMoney(priceInfo.taxprice);
                                                    break;
                                                }
                                            }
                                        }else{
                                            return "0.00";
                                        }
						        	}
			     				},
		     				</c:if>
	     				</c:forEach>
						{field:'brand',title:'商品品牌',width:70,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.brandName;
							}
						},
						{field:'defaultsort',title:'默认分类',width:80,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.defaultsortName;
							}
						},
						{field:'storageid',title:'默认仓库',width:80,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.storageName;
							}
						}, 
						{field:'defaultbuyer',title:'默认采购员',width:80,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.defaultbuyerName;
							}
						},
						{field:'state',title:'状态',width:50,sortable:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.stateName;
							}
						},
                        {field:'addtime',title:'建档时间',width:130,sortable:true,hidden:true},
                		{field:'remark',title:'备注',width:100,sortable:true}
						<c:if test="${useHTKPExport=='1'}">
						,{field:'jsgoodsid',title:'金税商品编码',width:80,sortable:true},
                         {field:'jstaxsortid',title:'金税税收分类编码',width:80,sortable:true},
                		{field:'jsgoodsmodifyusername',title:'金税信息最新修改人',width:100,sortable:true},
						{field:'jsgoodsmodifytime',title:'金税信息最新修改时间',width:180,sortable:true}
                		</c:if>
		     	]]
	     });

    //加锁
    function isDoLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isDoLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    function refreshLayout(title, url, type) {
        $('<div id="wares-dialog-goodsListPage1"></div>').appendTo('#wares-dialog-goodsListPage');
        $('#wares-dialog-goodsListPage1').dialog({
            maximizable: true,
            resizable: true,
            title: title,
            width: 740,
            height: 450,
			    closed: true,
			    cache: false,  
			    href: url,  
			    modal: true,
			    onClose:function(){
			    	$('#wares-dialog-goodsListPage1').dialog("destroy");
			    }
			});
			$("#wares-dialog-goodsListPage1").dialog("open");
			$("#goods-opera").val(type);
    	}
	    
	    function goods_open_upload_dialog(type){
	    	var goodsid = $("#goodsInfo-id-baseInfo").val();
			$('<div id="wares-div-uploadimag1"></div>').appendTo('#wares-div-uploadimag');
			$('#wares-div-uploadimag1').dialog({
			    title: '图片上传',  
			    width: 500,
			    height: 400,
			    closed: false,
			    cache: false,
			    href: 'basefiles/showGoodsUploadImgPage.do?type='+type+'&goodsid='+goodsid,
			    modal: true,
			    onClose:function(){
			    	if(type != "view"){
			    		var url = "basefiles/showGoodsSimplifyEditPage.do?id="+goodsid;
						$('#wares-dialog-goodsListPage1').dialog('refresh', url);
			    	}
			    	$('#wares-div-uploadimag1').dialog("destroy");
			    }
			});
			$("#wares-div-uploadimag1").dialog("open");
	    }
	    
	    function goods_save_form_submit(){
    		$("#goodssimplify-form-add").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
                    if(undefined != json.lockFlag &&  !json.lockFlag){
                        $.messager.alert("提醒","该数据被其他人操作,暂不能修改!");
                        return false;
                    }else if(undefined != json.unEditFlag && !json.unEditFlag){
                        $.messager.alert("提醒","该数据被引用,不允许修改!");
                        return false;
                    }else if(json.flag){
   						var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
      					$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
						$("#wares-dialog-goodsListPage1").dialog('close');
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
	    
	    function goods_savegoon_form_submit(){
    		$("#goodssimplify-form-add").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
      					$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
      					
      					var WCid="";
      					var goodsInfo_WCTreeObj = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
						var WCTree = goodsInfo_WCTreeObj.getSelectedNodes();
						if(WCTree.length != 0){
			 				if("".localeCompare(WCTree[0].id) != 0){
								WCid = WCTree[0].id;
							}
						}
						var url = "basefiles/showGoodsSimplifyAddPage.do?WCid="+WCid;
						$('#wares-dialog-goodsListPage1').dialog('refresh', url);
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
	    
	     $(function(){
	     	//商品品牌
			$("#wares-widget-goodsShortcut-brand").widget({
				width:150,
				referwid:'RL_T_BASE_GOODS_BRAND',
				col:'brand',
				singleSelect:true,
				onlyLeafCheck:false
			});
	     
	     	//默认供应商
             $("#wares-widget-goodsShortcut-supplierid").widget({
                 referwid:'RL_T_BASE_BUY_SUPPLIER',
                 width:138
             });
			
			//所属部门
			$("#wares-widget-goodsShortcut-deptid").widget({
				width:150,
				referwid:'RL_T_BASE_DEPATMENT',
				singleSelect:true,
				onlyLeafCheck:false
			});
	     
	     	var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
	     	
			var goodsInfoWaresClassTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/goodsInfoWaresClassTree.do",
					autoParam: ["id","parentid", "text","state"]
				},
				data: {
					key:{
						name:"text"
						//title:"text"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: ""
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						$("#goods-hddefaultsort").val(treeNode.id);
						$("#wares-id-hdWaresClassId").val(treeNode.id);
						$("#wares-pid-hdWaresClassPid").val(treeNode.pId);
                        $("#wares-query-queryGoodsInfoList").click();
	      				var zTree = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					},
					onClick:function(){
						$("#wares-table-goodsInfoList").datagrid('clearSelections');
					},
					onAsyncSuccess: function(event, treeId, treeNode, msg){
						$("#wares-form-goodsInfoListQuery").show();
						$('#wares-table-goodsInfoList').datagrid({
				  			authority:goodsInfoListColJson,
				  	 		frozenColumns:goodsInfoListColJson.frozen,
							columns:goodsInfoListColJson.common,
						    fit:true,
							title:'',
							toolbar:'#wares-query-showGoodsInfoList',
							method:'post',
							rownumbers:true,
							pagination:true,
							idField:'id',
							pageSize:100,
							singleSelect:false,
							checkOnSelect:true,
							selectOnCheck:true,
							queryParams:queryJSON,
							url:'basefiles/goodsInfoListPage.do',
							onDblClickRow:function(rowIndex, rowData){
								<security:authorize url="/basefiles/goodsSimplifyViewBtn.do">
									var url = "basefiles/showGoodsSimplifyViewPage.do?id="+encodeURIComponent(rowData.id);
									refreshLayout("商品档案【查看】", url,"view");
								</security:authorize>
							}
						}).datagrid("columnMoving");
					}
				}
			};
			$.fn.zTree.init($("#wares-tree-waresClass"), goodsInfoWaresClassTreeSetting,null);
			
			var goodsInfo_WCTreeObj = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
			//加载按钮
			$("#goodsInfo-button-div").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/goodesSimplifyAddBtn.do">
					{
						type:'button-add',//新增
						handler:function(){
							var WCid="";
							var WCTree = goodsInfo_WCTreeObj.getSelectedNodes();
							if(WCTree.length != 0){
				 				if("".localeCompare(WCTree[0].id) != 0){
									WCid = WCTree[0].id;
								}
							}
							var url = "basefiles/showGoodsSimplifyAddPage.do?WCid="+WCid;
							refreshLayout("商品档案【新增】", url,"add");
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/goodesSimplifyEditBtn.do">
		 			{
			 			type:'button-edit',//修改
			 			handler:function(){
			 				var goodsInfoRow = $("#wares-table-goodsInfoList").datagrid('getSelected');
			 				if(goodsInfoRow == null){
			 					$.messager.alert("提醒","请选择商品!");
								return false;
			 				}
			 				var flag = isDoLockData(goodsInfoRow.id,"t_base_goods_info");
			 				if(!flag){
			 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改!");
			 					return false;
			 				}
			 				var url = "basefiles/showGoodsSimplifyEditPage.do?id="+encodeURIComponent(goodsInfoRow.id);
							refreshLayout("商品档案【修改】", url,"edit");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/goodesSimplifyDeleteBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
							var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
							if(goodsInfoRows.length == 0){
								$.messager.alert("提醒","请选择商品!");
								return false;
							}
							$.messager.confirm("提醒","是否确定删除商品档案?",function(r){
								if(r){
									var idStr = "";
									for(var i=0;i<goodsInfoRows.length;i++){
										idStr += goodsInfoRows[i].id + ",";
									}
									loading("删除中..");
									$.ajax({   
							            url :'basefiles/deleteGoodsInfos.do',
							            type:'post',
							            dataType:'json',
							            data:{idStr:idStr},
							            success:function(retJSON){
							            	loaded();
							            	if(retJSON.flag){
												$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>"+retJSON.lockNum+"条记录网络互斥,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
												var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
			      								$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
												$("#wares-table-goodsInfoList").datagrid('clearSelections');
												$("#wares-table-goodsInfoList").datagrid('clearChecked');
											}
											else{
												$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>"+retJSON.lockNum+"条记录网络互斥,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
											}
							            }
							        });
								}
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/goodesSimplifyCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var goodsInfoRow=$("#wares-table-goodsInfoList").datagrid('getSelected');
							if(goodsInfoRow == null){
								$.messager.alert("提醒","请选择一个商品!");
								return false;
							}
							var url = "basefiles/showGoodsSimplifyCopyPage.do?id="+encodeURIComponent(goodsInfoRow.id);
							refreshLayout("商品档案【复制】", url,"add");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/goodsSimplifyViewBtn.do">
		 			{
		 				type:'button-view',//查看
		 				handler:function(){
		 					var goodsInfoRow=$("#wares-table-goodsInfoList").datagrid('getSelected');
							if(goodsInfoRow == null){
								$.messager.alert("提醒","请选择一个商品!");
								return false;
							}
							var url = "basefiles/showGoodsSimplifyViewPage.do?id="+encodeURIComponent(goodsInfoRow.id);
							refreshLayout("商品档案【查看】", url,"view");
		 				}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/goodesSimplifyEnableBtn.do">
		 			{
			 			type:'button-open',//启用
			 			handler:function(){
							var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
							if(goodsInfoRows.length == 0){
								$.messager.alert("提醒","请勾选商品!");
								return false;
							}
							$.messager.confirm("提醒","是否确定启用商品档案?",function(r){
								if(r){
									var idStr = "";
									for(var i=0;i<goodsInfoRows.length;i++){
										idStr += goodsInfoRows[i].id + ",";
									}
									loading("启用中..");
									$.ajax({   
							            url :'basefiles/enableGoodsInfos.do',
							            type:'post',
							            dataType:'json',
							            data:{idStr:idStr},
							            success:function(retJSON){
							            	loaded();
							            	if(retJSON.flag){
												$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用成功"+retJSON.num+"条记录;");
												var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
			      								$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
												$("#wares-table-goodsInfoList").datagrid('clearSelections');
												$("#wares-table-goodsInfoList").datagrid('clearChecked');
											}
											else{
												$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用失败"+retJSON.num+"条记录;");
											}
							            }
							        });
								}
							});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/goodesSimplifyDisableBtn.do">
		 			{
			 			type:'button-close',//禁用
			 			handler:function(){
							var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
							if(goodsInfoRows.length == 0){
								$.messager.alert("提醒","请勾选商品!");
								return false;
							}
							$.messager.confirm("提醒","是否确定禁用商品档案?",function(r){
								if(r){
									var idStr = "";
									for(var i=0;i<goodsInfoRows.length;i++){
										idStr += goodsInfoRows[i].id + ",";
									}
									loading("禁用中..");
									$.ajax({   
							            url :'basefiles/disableGoodsInfos.do',
							            type:'post',
							            dataType:'json',
							            data:{idStr:idStr},
							            success:function(retJSON){
							            	loaded();
							            	if(retJSON.flag){
												$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.num+"条记录;");
												var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
			      								$("#wares-table-goodsInfoList").datagrid("load",queryJSON);
												$("#wares-table-goodsInfoList").datagrid('clearSelections');
												$("#wares-table-goodsInfoList").datagrid('clearChecked');
											}
											else{
												$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用失败"+retJSON.num+"条记录;");
											}
							            }
							        });
								}
							});
			 			}
		 			},
		 			</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'base_goods_info',
                            //查询针对的表格id
                            datagrid:'wares-table-goodsInfoList',
                            plain:true
                        }
                    },
					{}
				],
				buttons:[
					{},
                    <security:authorize url="/basefiles/goodesSimplifyIMExportMenuBtn.do">
                    {id:'imexportMenuButton',
                        type:'menu',
                        name:'导入导出',
                        iconCls:'button-export',
                        button:[
                            <security:authorize url="/basefiles/goodesSimplifyImportBtn.do">
                            {
                                id:'ieportMenuButton-id-import',
                                name:'导入',
                                iconCls:'button-import',
                                handler:function(){
                                    $("#wares-goodsSimplifyListPage-buttons-importclick").Excel('import',{
                                        type:'importUserdefined',
                                        importparam:'商品编码、商品名称、条件码、商品品牌、默认供应商、主单位、默认仓库、默认税种必填',//参数描述
                                        version:'1',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
                                        url:'basefiles/importGoosSimplifyListData.do',
                                        onClose: function(){ //导入成功后窗口关闭时操作，
                                            $("#wares-table-goodsInfoList").datagrid('reload');	//更新列表
                                        }
                                    });
                                    $("#wares-goodsSimplifyListPage-buttons-importclick").trigger("click");
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodesSimplifyExportBtn.do">
                            {
                                id:'ieportMenuButton-id-export',
                                name:'导出',
                                iconCls:'button-export',
                                handler:function(){
                                    $("#wares-goodsSimplifyListPage-buttons-exportclick").Excel('export',{
                                        datagrid: "#wares-table-goodsInfoList",
                                        queryForm: "#wares-form-goodsInfoListQuery",
                                        type:'exportUserdefined',
                                        name:'商品档案列表',
                                        url:'basefiles/exportGoosSimplifyListData.do'
                                    });
                                    $("#wares-goodsSimplifyListPage-buttons-exportclick").trigger("click");
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodesSimplifyExportModBtn.do">
                            {
                                id:'button-export-detail',
                                name:'按模板导出',
                                iconCls:'button-export',
                                handler: function(){
                                    var rows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                                    var idsarr=new Array();
                                    if(rows.length > 0){
                                        for(var i=0;i<rows.length;i++){
                                            idsarr.push(rows[i].id);
                                        }
                                    }
                                    if(idsarr == ""){
                                        $.messager.alert("提醒","请至少选择一条记录");
                                    }else{
                                        $("#goodsSimplifyListPage-dialog-printByModule").Excel('export',{
                                            queryForm: "#wares-query-showGoodsInfoList",
                                            type:'exportUserdefined',
                                            name:'商品档案列表',
                                            fieldParam:{idarrs:idsarr.join(",")},
                                            url:'basefiles/getExportMod.do'
                                        });
                                        $("#goodsSimplifyListPage-dialog-printByModule").trigger("click");
                                    }
                                }
                            },
                            {
                                type:'menu-sep'
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsSimplifyExportForHTKPBtn.do">
                            {
                                id:'button-export-htkp',
                                name:'按航天存货格式导出',
                                iconCls:'button-export',
                                handler: function(){
                                    var rows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                                    var idsarr=new Array();
                                    if(rows.length > 0){
                                        for(var i=0;i<rows.length;i++){
                                            idsarr.push(rows[i].id);
                                        }
                                    }
                                    var param={};
                                    if(idsarr.length>0){
                                        param.idarrs=idsarr.join(",");
                                    }

                                    $("#goodsSimplifyListPage-dialog-htkpexportclick").Excel('export',{
                                        queryForm: "#wares-query-showGoodsInfoList",
                                        exportparam:"可以勾选部分商品导出，不勾选导出全部商品。<b>请维护好金税商品编码</b>",
                                        type:'exportUserdefined',
                                        name:'商品存货信息表',
                                        fieldParam:param,
                                        url:'basefiles/exportGoodsListDataForHTKP.do'
                                    });
                                    $("#goodsSimplifyListPage-dialog-htkpexportclick").trigger("click");

                                }
                            },
                            {
                                type:'menu-sep'
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsSimplifyExportGoodsListDataSplitByBrandForHTKPBtn.do">
                            {
                                id:'button-export-htkp-splitbybrand',
                                name:'1分品牌按金税格式导出',
                                iconCls:'button-export',
                                handler: function(){
                                    $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport").dialog({
                                        title: '分品牌按金税格式导出',
                                        width: 450,
                                        height: 350,
                                        closed: true,
                                        cache: false,
                                        modal: true,
                                        buttons:[
                                            {
                                                text:'确定',
                                                iconCls:'button-ok',
                                                handler:function(){
                                                    $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport").dialog("close");
                                                    var param={};
                                                    var exportformflag = $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport-form").form('validate');
                                                    if (!exportformflag) {
                                                        $.messager.alert("提醒", "抱歉，请填写相关参数");
                                                        return false;
                                                    }
                                                    var exportformparm = $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport-form").serializeJSON();
                                                    exportformparm.oldFromData = "";
                                                    delete exportformparm.oldFromData;
                                                    param = jQuery.extend({}, exportformparm, param);

                                                    $("#goodsSimplifyListPage-dialog-htkpexportclick").Excel('export',{
                                                        queryForm: "#wares-query-showGoodsInfoList",
                                                        type:'exportUserdefined',
                                                        name:'分品牌按金税格式导出',
                                                        fieldParam:param,
                                                        url:'basefiles/exportGoodsListDataSplitByBrandForHTKP.do'
                                                    });
                                                    $("#goodsSimplifyListPage-dialog-htkpexportclick").trigger("click");
                                                }
                                            },
                                            {
                                                text:'取消',
                                                iconCls:'button-cancel',
                                                handler:function(){
                                                    $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport").dialog("close");
                                                    return false;
                                                }
                                            }
                                        ],
                                        onClose:function(){
                                            //$("#goodsSimplifyListPage-dialog-htkpsplitbrandexport").dialog("destroy");
                                        }
                                    });
                                    $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport").dialog("open");
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsSimplifyConvertGoodsExcelToXMLForHTKPBtn.do">
                            {
                                id:'button-import-htkp-converttoxml',
                                name:'2EXCEL转换金税格式',
                                iconCls:'button-export',
                                handler: function(){
                                    $("#goodsSimplifyListPage-buttons-convertexceltoxmlforhtkp-click").Excel('import',{
                                        type:'importUserdefined',
                                        importparam:'<br/>&nbsp;&nbsp;&nbsp;数据模板来自“分品牌按金税格式导出”，其中“商品编码”就ERP档案里的商品编码<br/>',//参数描述
                                        version:'1',//导入页面显示哪个版本1
                                        importPageRequestParam:{hideExportTip:'true'},
                                        url:'basefiles/convertGoodsExcelToXMLForHTKP.do',
                                        onClose: function(){ //导入成功后窗口关闭时操作，
                                        }
                                    });
                                    $("#goodsSimplifyListPage-buttons-convertexceltoxmlforhtkp-click").trigger("click");
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsSimplifyImportAndUpdateJSGoodsHTKPBtn.do">
                            {
                                id:'button-import-htkp-updatejsgoodsid',
                                name:'3导入更新金税信息',
                                iconCls:'button-export',
                                handler: function(){
                                    var msg="数据模板来自“分品牌按金税格式导出”。";
                                    msg=msg+"<br/>更新使用模板中字段有“商品编码”，“金税编码”，“税收分类编码”。";
                                    msg=msg+"<br/>其中<b style=\"\">“商品编码”</b>为系统中商品档案编码，为关键值。";
                                    $("#goodsSimplifyListPage-buttons-importAndUpdateJSGoodsHTKP-click").Excel('import',{
                                        type:'importUserdefined',
                                        importparam:msg,//参数描述
                                        version:'1',//导入页面显示哪个版本1
                                        url:'basefiles/importAndUpdateJSGoodsHTKP.do',
                                        onClose: function(){ //导入成功后窗口关闭时操作，
                                        }
                                    });
                                    $("#goodsSimplifyListPage-buttons-importAndUpdateJSGoodsHTKP-click").trigger("click");
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsSimplifyShowGoodsInfoForJSEditPageBtn.do">
                            {
                                id:'button-id-jsconfig',
                                name:'手动更新金税信息',
                                iconCls:'button-edit',
                                handler:function(){
                                    var goodsInfoRow=$("#wares-table-goodsInfoList").datagrid('getSelected');
                                    if(goodsInfoRow == null || goodsInfoRow.id==null || goodsInfoRow.id==""){
                                        $.messager.alert("提醒","请选择一个商品!");
                                        return false;
                                    }
                                    $('<div id="wares-dialog-showUpdateGoodsInfoForJS-content"></div>').appendTo("#wares-dialog-showUpdateGoodsInfoForJS");
                                    $('#wares-dialog-showUpdateGoodsInfoForJS-content').dialog({
                                        title: '手动更新金税信息',
                                        //fit:true,
                                        width:450,
                                        height:250,
                                        closed: true,
                                        cache: false,
                                        method:'post',
                                        queryParams:{id:goodsInfoRow.id},
                                        href: 'basefiles/showGoodsInfoForJSEditPage.do',
                                        maximizable:true,
                                        resizable:true,
                                        modal: true,
                                        onLoad:function(){
                                        },
                                        onClose:function(){
                                            $('#wares-dialog-showUpdateGoodsInfoForJS-content').dialog("destroy");
                                        }
                                    });
                                    $('#wares-dialog-showUpdateGoodsInfoForJS-content').dialog('open');
                                }
                            },
                            {
                                type: 'menu-sep'
                            },
                            </security:authorize>
                            {}
                        ]
                    },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyEditMoreBtn.do">
                {
                    id: 'editMore',
                    name: '批量修改',
                    iconCls: 'button-edit',
                    handler: function () {
                        var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                        if (goodsInfoRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        var idStr = "", flagIdStr = "";
                        var unInvNum = 0;
                        for (var i = 0; i < goodsInfoRows.length; i++) {
                            var id = encodeURIComponent(goodsInfoRows[i].id);
                            var flag = isDoLockData(id, "t_base_goods_info");
                            if (!flag) {
                                flagIdStr += id + ",";
                                unInvNum++;
                                var index = $("#wares-table-goodsInfoList").datagrid('getRowIndex', goodsInfoRows[i]);
                                $("#wares-table-goodsInfoList").datagrid('uncheckRow', index);
                            }
                            else {
                                idStr += id + ",";
                            }
                        }
                        if (flagIdStr != "") {
                            var unIds = flagIdStr.substring(0, flagIdStr.lastIndexOf(","));
                            $.messager.alert("警告", "" + unIds + "数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        $('#wares-dialog-goodsInfoEditMore').dialog({
                            title: '批量修改商品信息',
                            width: 550,
                            height: 330,
                            closed: false,
                            cache: false,
                            href: 'basefiles/showGoodsSimplifyMoreEditPage.do?idStr=' + idStr + '&unInvNum=' + unInvNum,
                            modal: true
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyImgsMuiBtn.do">
                {
                    id: 'goodesSimplify-upload-imgs',
                    name: '图片批量上传',
                    iconCls: 'button-file',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyPrintBtn.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/basefiles/goodsSimplifyLocationPrintViewBtn.do">
                        {
                            id: 'printview-id-goodslocation',
                            name: '货位打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/goodsSimplifyLocationPrintBtn.do">
                        {
                            id: 'print-id-goodslocation',
                            name: '货位信息打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                    ]
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyViewCostpriceChage.do">
                {
                    id: 'button-ViewCostpriceChage',
                    name: '成本变更记录',
                    iconCls: 'button-view',
                    handler: function () {
                        var goodsInfoRow = $("#wares-table-goodsInfoList").datagrid('getSelected');
                        $('<div id="wares-dialog-viewCostpriceChage"></div>').appendTo('#wares-dialog-goodsListPage');
                        $('#wares-dialog-viewCostpriceChage').dialog({
                            maximizable: true,
                            resizable: true,
                            title: "商品成本变更记录",
                            width: 740,
                            height: 450,
                            closed: true,
                            cache: false,
                            href: "basefiles/goodesSimplifyViewCostpriceChage.do",
							queryParams:{id:goodsInfoRow.id},
                            modal: true,
                            onClose: function () {
                                $('#wares-dialog-viewCostpriceChage').dialog("destroy");
                            }
                        });
                        $("#wares-dialog-viewCostpriceChage").dialog("open");
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'base',
            type: 'multipleList',
            taburl: '/basefiles/showGoodsSimplifyListPage.do',
            datagrid: 'wares-table-goodsInfoList',
            tname: 't_base_goods_info',
            id: ''
        });

        //回车事件
        controlQueryAndResetByKey("wares-query-queryGoodsInfoList", "wares-query-reloadGoodsInfoList");

        //查询
        $("#wares-query-queryGoodsInfoList").click(function () {
            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
        });

        //重置按钮
        $("#wares-query-reloadGoodsInfoList").click(function () {
            $("#wares-form-goodsInfoListQuery")[0].reset();
            $("#wares-widget-goodsShortcut-brand").widget('clear');
            $("#wares-widget-goodsShortcut-supplierid").widget('clear');
            $("#wares-widget-goodsShortcut-deptid").widget('clear');
            var waresClassTree = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
            waresClassTree.refresh();
            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);

        });

        $("#goodesSimplify-upload-imgs").webuploader({
            title: '图片批量上传',
            filetype: 'Images',
            allowType: "gif,jpg,jpeg,bmp,png",
            mimeTypes: 'image/*',
            disableGlobalDnd: true,
            width: 700,
            height: 400,
            url: 'common/uploadGoodsImage.do',
            description: '上传图片的名称必须与商品编码或者条形码相对应，否则将无法上传至服务器',
            close: true,
            onComplete: function (data) {
                var nogoodsidimgs = "";
                for (var i = 0; i < data.length; i++) {
                    if (undefined != data[i].nogoodsidimg) {
                        if ("" == nogoodsidimgs) {
                            nogoodsidimgs = data[i].nogoodsidimg;
                        } else {
                            nogoodsidimgs += "," + data[i].nogoodsidimg;
                        }
                    }
                }
                if ("" != nogoodsidimgs) {
                    $.messager.alert("提醒", "图片" + nogoodsidimgs + "不存在对应商品，无法上传至服务器!");
                }
            }
        });

        //通用查询组建调用
//			$("#goodsInfo-query-advanced").advancedQuery({
//				//查询针对的表
//		 		name:'base_goods_info',
//		 		//查询针对的表格id
//		 		datagrid:'wares-table-goodsInfoList'
//			});
             //商品品牌
             $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-brandid-widget").widget({
                 width:200,
                 name:'t_base_goods_info',
                 col:'brand',
                 singleSelect:false,
                 onlyLeafCheck:false
             });
        $("#goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-jsgoodsidcreatemethod").change(function(){
            var val=$(this).val() || "";
            var $methodtr=$("#goodsSimplifyListPage-dialog-htkpsplitbrandexport-form-jsgoodsidcreatemethod-tr");
            if(val==2){
                $methodtr.show()
            }else {
                $methodtr.hide();
            }
        });
    });


        function showGoodsHelpDialog(type) {
            if(type==null || type==""){
                return false;
            }
            var msg='';
            if("jsbrandexportjsgoodsid"==type){
                msg=$("#goodsSimplifyListPage-help-simple-jsbrandexportjsgoodsid").html();
                easyuiMessagerAlert({
                    width:500,
                    title:"提醒",
                    msg:msg,
                    icon:'info'
                });
            }
        }

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-goodslocation-dialog-print",
            code: "goods_goodslocation",
            tableId: "wares-table-goodsInfoList",
            url_preview: "print/basefiles/goodsLocationPrintView.do",
            url_print: "print/basefiles/goodsLocationPrint.do",
            libtype: 'withbarcode',
            btnPreview: "printview-id-goodslocation",
            btnPrint: "print-id-goodslocation"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
