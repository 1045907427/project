<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售订单版本查看页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-id-orderAddPage" type="text" class="len130" readonly="readonly" value="${saleorder.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-orderAddPage" type="text" class="len130" readonly="readonly" value="${saleorder.businessdate }" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-customer-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == saleorder.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="5"><input type="text" id="sales-customer-orderAddPage" readonly="readonly" text="<c:out value="${saleorder.customername }"></c:out>" value="${saleorder.customerid }" style="width:300px;" /><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">编号：${saleorder.customerid }</span></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input id="sales-salesDept-orderAddPage" type="text" class="len136" name="saleorder.salesdept" value="${saleorder.salesdept }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input id="sales-salesMan-orderAddPage" type="text" class="len136" name="saleorder.salesuser" value="${saleorder.salesuser }" readonly="readonly"/>
	    				</td>
	    				<td colspan="2">是否以商品编码排序:是<input type="radio" name="saleorder.isgoodsseq" disabled="disabled" value="1" <c:if test="${saleorder.isgoodsseq =='1' }"> checked="checked"</c:if>/>否<input type="radio" name="saleorder.isgoodsseq" disabled="disabled" value="0" <c:if test="${saleorder.isgoodsseq =='0' }"> checked="checked"</c:if>/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">发货仓库：</td>
	    				<td>
	    				<input id="sales-storageid-orderAddPage" name="saleorder.storageid" value="${saleorder.storageid }"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input type="text" readonly="readonly" value="<c:out value="${saleorder.remark }"></c:out>" style="width:400px;" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" id="sales-printtimes-orderAddPage" value="${saleorder.printtimes }"/>
	    		<input type="hidden" id="sales-phprinttimes-orderAddPage" value="${saleorder.phprinttimes }"/>
	    		<input type="hidden" id="sales-printlimit-orderAddPage" value="${printlimit }"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
    	$(function(){
            var tableColJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
                frozenCol : [[
                    {field:'ck',checkbox:true},
                    {field:'id',hidden:true}
                ]],
                commonCol : [[
                    {field:'goodsid',title:'商品编码',width:70,align:' left',sortable:true},
                    {field:'goodsname', title:'商品名称', width:250,align:'left',aliascol:'goodsid',
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.goodsInfo != null){
                                if(rowData.isdiscount=='1'){
                                    return "（折扣）"+rowData.goodsInfo.name;
                                }else if(rowData.isdiscount=='2'){
                                    return "（折扣）"+rowData.goodsInfo.name;
                                }else{
                                    if(rowData.deliverytype=='1'){
                                        return "<font color='blue'>&nbsp;赠 </font>"+rowData.goodsInfo.name;
                                    }else if(rowData.deliverytype=='2'){
                                        return "<font color='blue'>&nbsp;捆绑 </font>"+rowData.goodsInfo.name;
                                    }else{
                                        return rowData.goodsInfo.name;
                                    }
                                }
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.isdiscount!='1' && rowData.goodsInfo != null){
                                return rowData.goodsInfo.barcode;
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'brandName', title:'商品品牌',width:60,align:'left',aliascol:'goodsid',hidden:true,
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
                                return formatterBigNum(rowData.goodsInfo.boxnum);
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'unitname', title:'单位',width:35,align:'left'},
                    {field:'unitnum', title:'数量',width:60,align:'right',sortable:true,
                        formatter:function(value,row,index){
                            return formatterBigNum(value);
                        }
                    },
                    {field:'taxprice', title:'单价',width:60,align:'right',sortable:true,
                        formatter:function(value,row,index){
                            if(row.isdiscount!='1' && row.isdiscount!='2'){
                                if(parseFloat(value)>parseFloat(row.oldprice)){
                                    if(parseFloat(value)<parseFloat(row.lowestsaleprice)){
                                        return "<font color='blue' style='cursor: pointer;' title='原价:"+formatterMoney(row.oldprice)+",最低销售价:"+formatterMoney(row.lowestsaleprice)+"'>"+ formatterMoney(value)+ "</font>";
                                    }else{
                                        return "<font color='blue' style='cursor: pointer;' title='原价:"+formatterMoney(row.oldprice)+"'>"+ formatterMoney(value)+ "</font>";
                                    }

                                }
                                else if(parseFloat(value)<parseFloat(row.oldprice)){
                                    if(parseFloat(value)<parseFloat(row.lowestsaleprice)){
                                        return "<font color='red' style='cursor: pointer;' title='原价:"+formatterMoney(row.oldprice)+",最低销售价:"+formatterMoney(row.lowestsaleprice)+"'>"+ formatterMoney(value)+ "</font>";
                                    }else{
                                        return "<font color='red' style='cursor: pointer;' title='原价:"+formatterMoney(row.oldprice)+"'>"+ formatterMoney(value)+ "</font>";
                                    }
                                }
                                else{
                                    if(parseFloat(value)<parseFloat(row.lowestsaleprice)){
                                        return "<font style='cursor: pointer;' title='最低销售价:"+formatterMoney(row.lowestsaleprice)+"'>"+ formatterMoney(value)+ "</font>";
                                    }else{
                                        return formatterMoney(value);
                                    }
                                }
                            }else{
                                return "";
                            }
                        },
                        styler:function(value,row,index){
                            if(row.isdiscount!='1' && row.isdiscount!='2'){
                                if(parseFloat(value)<parseFloat(row.lowestsaleprice)){
                                    return 'background-color:yellow;';
                                }
                            }
                        }
                    },
                    {field:'boxprice', title:'箱价',width:60,aliascol:'taxprice',align:'right',
                        formatter:function(value,row,index){
                            if(row.isdiscount!='1' && row.isdiscount!='2'){
                                return formatterMoney(value);
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'taxamount', title:'金额',width:60,align:'right',sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'notaxprice', title:'未税单价',width:60,align:'right', hidden:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'notaxamount', title:'未税金额',width:60,align:'right', hidden:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'taxtype', title:'税种',width:60,align:'left',hidden:true,
                        formatter:function(value,row,index){
                            return row.taxtypename;
                        }
                    },
                    {field:'tax', title:'税额',width:60,align:'right',hidden:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
                    {field:'storagename', title:'指定仓库',width:80,align:'left',hidden:true},
                    {field:'remark', title:'备注',width:200,align:'left'}
                ]]
            });
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品行编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if($.isNumeric(a[sort])){
	    					if(order=="asc"){
	    						return Number(a[sort])>Number(b[sort])?1:-1
	    					}else{
	    						return Number(a[sort])<Number(b[sort])?1:-1
	    					}
    					}else{
    						if(order=="asc"){
	    						return a[sort]>b[sort]?1:-1
	    					}else{
	    						return a[sort]<b[sort]?1:-1
	    					}
    					}
    				});
    				$("#sales-datagrid-orderAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(data){
    				if(data.rows.length<12){
    					var j = 12-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$(this).datagrid('appendRow',{});
	            		}
    				}else{
    					$(this).datagrid('appendRow',{});
    				}
                    groupGoods();
    				countTotal();
    			},
    			onCheckAll:function(){
					countTotal();
				},
				onUncheckAll:function(){
					countTotal();
				},
				onCheck:function(){
					countTotal();
				},
				onUncheck:function(){
					countTotal();
				}
    		}).datagrid('columnMoving');
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			singleSelect:true,
    			width:300
    		});
    		$("#sales-storageid-orderAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:130,
				singleSelect:true,
				readonly:true
    		});
    		$("#sales-salesDept-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesdept',
    			width:130,
				singleSelect:true
    		});
    		$("#sales-salesMan-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesuser',
				required:true,
				width:130,
				singleSelect:true
    		});
    	});
        //买赠捆绑分组
        function groupGoods(){
            var rows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
            var merges = [];
            var groupIDs = "";
            for(var i=0;i<rows.length;i++){
                var groupid = rows[i].groupid;
                if(groupid!=null && groupid!="" && groupIDs.indexOf(groupid)==-1){
                    groupIDs = groupid+",";
                    var count = 0;
                    for(var j=0;j<rows.length;j++){
                        if(groupid == rows[j].groupid){
                            count ++;
                        }
                    }
                    if(count>1){
                        merges.push({index:i,rowspan:count});
                    }
                }
            }
            for(var i=0; i<merges.length; i++){
                $("#sales-datagrid-orderAddPage").datagrid('mergeCells',{
                    index: merges[i].index,
                    field: 'ck',
                    rowspan: merges[i].rowspan
                });
            }
        }
        function countTotal(){ //计算合计
            var checkrows =  $("#sales-datagrid-orderAddPage").datagrid('getChecked');
            var usablenum = 0;
            var unitnum = 0;
            var taxamount = 0;
            var notaxamount = 0;
            var tax = 0;
            for(var i=0; i<checkrows.length; i++){
                usablenum += Number(checkrows[i].usablenum == undefined ? 0 : checkrows[i].usablenum);
                unitnum += Number(checkrows[i].unitnum == undefined ? 0 : checkrows[i].unitnum);
                taxamount += Number(checkrows[i].taxamount == undefined ? 0 : checkrows[i].taxamount);
                notaxamount += Number(checkrows[i].notaxamount == undefined ? 0 : checkrows[i].notaxamount);
                tax += Number(checkrows[i].tax == undefined ? 0 : checkrows[i].tax);
            }
            var foot = [{goodsid:'选中合计',usablenum:usablenum,unitnum:unitnum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}];
            //合计
            var rows =  $("#sales-datagrid-orderAddPage").datagrid('getRows');
            var usablenumSum = 0;
            var unitnumSum = 0;
            var taxamountSum = 0;
            var notaxamountSum = 0;
            var taxSum = 0;
            for(var i=0; i<rows.length; i++){
                usablenumSum += Number(rows[i].usablenum == undefined ? 0 : rows[i].usablenum);
                unitnumSum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
                taxamountSum += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
                notaxamountSum += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
                taxSum += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
            }
            var footSum = {goodsid:'合计',usablenum:usablenumSum,unitnum:unitnumSum,taxamount:taxamountSum,notaxamount:notaxamountSum,tax:taxSum};
            foot.push(footSum);
            $("#sales-datagrid-orderAddPage").datagrid('reloadFooter',foot);
        }
    </script>
  </body>
</html>
