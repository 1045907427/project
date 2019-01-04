\<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>库存初始化添加页面</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="storage/addStockInit.do" method="post" id="stockinitList-form-add" autocomplete="off">
			<table  border="0" class="box_table">
				<tr>
					<td>商品名称:</td>
					<td style="text-align: left;">
						<input type="text" id="stockInit-goodsid" name="stockInit.goodsid"/>
					</td>
					<td>所属仓库:</td>
					<td>
						<input type="text" id="stockinit-storageid-add" name="stockInit.storageid"/>
					</td>
				</tr>
				<tr>
					<td>辅数量:</td>
					<td>
						<input type="text" id="stockInit-unitnum-aux" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="stockInit-auxunitname1" style="float: left;"></span>
						<input type="text" id="stockInit-unitnum-unit" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="stockInit-goodsunitname1" style="float: left;"></span>
						<input type="hidden" name="stockInit.auxnumdetail" id="stockinit-auxnumdetail" class="no_input" readonly="readonly"/>
						<input type="hidden" name="stockInit.auxnum" id="stockinit-auxnum" readonly="readonly"/>
					</td>
					<td>数量:</td>
					<td style="text-align: left;">
						<input type="text" id="stockinit-unitnum" class="formaterNum easyui-validatebox len150" name="stockInit.unitnum" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" />
					</td>
				</tr>
				<tr>
					<td>单价:</td>
					<td style="text-align: left;">
						<input type="text" name="stockInit.price" id="stockinit-price"/>
					</td>
					<td>金额:</td>
					<td style="text-align: left;">
						<input type="text" id="stockinit-amountdetail" class="no_input" readonly="readonly"/>
						<input type="hidden" name="stockInit.unitamount" id="stockinit-amount"/>
					</td>
				</tr>
                <tr>
                    <td>未税金额:</td>
                    <td style="text-align: left;">
                        <input type="text" name="stockInit.notaxamount" id="stockinit-notaxamount" readonly="readonly"/>
                    </td>
                    <td>税额:</td>
                    <td style="text-align: left;">
                        <input type="text" name="stockInit.tax" id="stockinit-tax" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>税种:</td>
                    <td style="text-align: left;">
                        <input type="text" id="stockinit-taxtypename" readonly="readonly"/>
                        <input type="hidden" name="stockInit.taxtype" id="stockinit-taxtype"/>
                    </td>
					<td>箱装量:</td>
					<td>
						<input type="text" id="stockInit-boxnum" class="no_input" readonly="readonly"/>
					</td>
                </tr>
				<tr>
					<td>商品品牌:</td>
					<td>
						<input type="text" id="stockInit-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td>条形码:</td>
					<td>
						<input type="text" id="stockInit-goodsbarcode" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>主单位:</td>
					<td>
						<input type="text" name="stockInit.unitname" id="stockInit-goodsunitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="stockInit-goodsunitid" name="stockInit.unitid"/>
					</td>
					<td>辅单位:</td>
					<td>
						<input type="text" name="stockInit.auxunitname" id="stockinit-auxunitname" class="no_input" readonly="readonly"/>
						<input type="hidden" name="stockInit.auxunitid" id="stockinit-auxunitid"/>
					</td>
				</tr>
				<tr>
					<td>生产日期:</td>
					<td>
						<input type="text" name="stockInit.produceddate" id="stockinit-produceddate" style="height: 20px;" class="WdateRead"/>
					</td>
					<td>截止日期:</td>
					<td>
						<input type="text" name="stockInit.deadline" id="stockinit-deadline" style="height: 20px;" class="WdateRead"/>
					</td>
				</tr>
				<tr>
					<td>批次号:</td>
					<td>
						<input type="text" name="stockInit.batchno" id="stockinit-batchno"/>
					</td>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="text" id="stockinit-storagelocationid" name="stockInit.storagelocationid"/>
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td>
						<input type="text" name="stockInit.remark" id="stockinit-remark"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			<input type="button" name="savegoon" id="stockinitList-save-add" value="确定"/>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#stockinit-produceddate").click(function(){
			if($("#stockinit-produceddate").hasClass("Wdate")){
				WdatePicker({dateFmt:'yyyy-MM-dd',
					onpicked:function(dp){
						if(dp.el.id=="stockinit-produceddate"){
							var produceddate = dp.cal.getDateStr();
							var goodsid = $("#stockInit-goodsid").goodsWidget("getValue");
							$.ajax({
								url :'storage/getBatchno.do',
								type:'post',
								data:{produceddate:produceddate,goodsid:goodsid},
								dataType:'json',
								async:false,
								success:function(json){
									$('#stockinit-batchno').val(json.batchno);
									$('#stockinit-deadline').val(json.deadline);
								}
							});
						}
					}
				});
			}
		});
		$("#stockinit-deadline").click(function(){
			if($("#stockinit-deadline").hasClass("Wdate")){
				WdatePicker({dateFmt:'yyyy-MM-dd',
					onpicked:function(dp){
						if(dp.el.id=="stockinit-deadline"){
							var deadline = dp.cal.getDateStr();
							var goodsid = $("#stockInit-goodsid").goodsWidget("getValue");
							$.ajax({
								url :'storage/getBatchnoByDeadline.do',
								type:'post',
								data:{goodsid:goodsid,deadline:deadline},
								dataType:'json',
								async:false,
								success:function(json){
									$('#stockinit-batchno').val(json.batchno);
									$('#stockinit-produceddate').val(json.produceddate);
								}
							});
						}
					}
				});
			}
		});
		$("#stockInit-goodsid").goodsWidget({
			name:'t_storage_stockinit',
			width:180,
			col:'goodsid',
			singleSelect:true,
			required:true,
			onSelect :function(data){
				$("#stockInit-goodsbrandName").val(data.brandName);
				$("#stockInit-goodsmodel").val(data.model);
				$("#stockInit-goodsbarcode").val(data.barcode);
				$("#stockInit-goodsunitid").val(data.mainunit);
				$("#stockInit-boxnum").val(formatterBigNumNoLen(data.boxnum));
				$("#stockinit-price").numberbox("setValue",data.newbuyprice);

				$("#stockinit-batchno").val("");
				$("#stockinit-produceddate").val("");
				$("#stockinit-deadline").val("");
				//判断商品档案是否进行批次管理
				if(data.isbatch=="1"){
					$("#stockinit-batchno").validatebox({required:true});
					$("#stockinit-produceddate").validatebox({required:true});
					$("#stockinit-storagelocationid").widget("clear");
					$("#stockinit-storagelocationid").widget("enable");
					$("#stockinit-batchno").removeAttr("disabled");
					$("#stockinit-batchno").removeClass("no_input");
					$("#stockinit-produceddate").removeAttr("disabled");
					$("#stockinit-produceddate").removeClass("WdateRead");
					$("#stockinit-produceddate").addClass("Wdate");
					$("#stockinit-deadline").removeAttr("disabled");
					$("#stockinit-deadline").removeClass("WdateRead");
					$("#stockinit-deadline").addClass("Wdate");
				}else{
					$("#stockinit-batchno").validatebox({required:false});
					$("#stockinit-produceddate").validatebox({required:false});
					$("#stockinit-storagelocationid").widget("clear");
					$("#stockinit-storagelocationid").widget("disable");
					$("#stockinit-batchno").attr("disabled","disabled");
					$("#stockinit-batchno").addClass("no_input");
					$("#stockinit-produceddate").attr("disabled","disabled");
					$("#stockinit-produceddate").removeClass("Wdate");
					$("#stockinit-produceddate").addClass("WdateRead");
					$("#stockinit-deadline").attr("disabled","disabled");
					$("#stockinit-deadline").removeClass("Wdate");
					$("#stockinit-deadline").addClass("WdateRead");
				}
				$("#stockinit-unitnum").focus();
				comput_stockinitData();
			},
			onClear:function(){
				$("#stockinit-batchno").validatebox({required:false});
				$("#stockinit-batchno").validatebox("isValid");
				$("#stockinit-produceddate").validatebox({required:false});
				$("#stockinit-produceddate").validatebox("isValid");

				$("#stockInit-goodsbrandName").val("");
				$("#stockInit-goodsmodel").val("");
				$("#stockInit-goodsbarcode").val("");
				$("#stockInit-goodsunitname").val("");
				$("#stockInit-goodsunitname1").val("");

				$("#stockinit-storagelocationid").widget("clear");
				$("#stockinit-storagelocationid").widget("disable");
				$("#stockinit-batchno").attr("disabled","disabled");
				$("#stockinit-batchno").addClass("no_input");
				$("#stockinit-produceddate").attr("disabled","disabled");
				$("#stockinit-produceddate").removeClass("Wdate");
				$("#stockinit-produceddate").addClass("WdateRead");
				$("#stockinit-deadline").attr("disabled","disabled");
				$("#stockinit-deadline").removeClass("Wdate");
				$("#stockinit-deadline").addClass("WdateRead");
			}
		});
		$("#stockinit-storageid-add").widget({
			name:'t_storage_stockinit',
			width:165,
			col:'storageid',
			singleSelect:true,
			required:true
		});
		$("#stockinit-storagelocationid").widget({
			name:'t_storage_stockinit',
			width:165,
			col:'storagelocationid',
			disabled:true,
			singleSelect:true
		});
		$("#stockinitList-save-add").click(function(){
			$("#stockinitList-form-add").submit();
		});
		$("#stockinitList-form-add").form({
			onSubmit: function(){
				var flag = $(this).form('validate');
				if(flag==false){
					return false;
				}
				loading("提交中..");
			},
			success:function(data){
				//表单提交完成后 隐藏提交等待页面
				loaded();
				var json = $.parseJSON(data);
				if(json.flag){
					$('#stockinit-table-add').datagrid("reload");
					$.messager.confirm("提醒", "新增成功，是否继续添加?", function(r){
						if (r){
							$('#stockInit-add-page-content').dialog("refresh","storage/showStockInitAddPage.do");
						}else{
							$('#stockInit-add-page-content').dialog("close");
						}
					});
				}
			}
		});
		$("#stockinit-price").numberbox({
			required:true,
			precision:2,
			groupSeparator:',',
			onChange:function(){
				comput_stockinitData();
			}
		});

		$("#stockInit-unitnum-aux").change(function(){
			computStockInitNumByAux();
		});
		$("#stockInit-unitnum-unit").change(function(){
			computStockInitNumByAux();
		});

		$("#stockinit-unitnum").change(function(){
			comput_stockinitData();
		});
	});
	function comput_stockinitData(){
		var goodsid = $("#stockInit-goodsid").widget("getValue");
		if(goodsid==null ||goodsid==""){
			return false;
		}
		$("#stockinit-price").addClass("inputload");
		$("#stockinit-unitnum").addClass("inputload");
		var auxunitid = $("#stockinit-auxunitid").val();
		var price = Number($("#stockinit-price").numberbox("getValue")).toFixed(2);
		var unitnum = $("#stockinit-unitnum").val();
		//后台对数据进行运算
		$.ajax({
			url :'storage/computeStockInitData.do',
			type:'post',
			data:{goodsid:goodsid,auxunitid:auxunitid,price:price,unitnum:unitnum},
			dataType:'json',
			async:false,
			success:function(json){
				if(json!=null){
					$("#stockInit-goodsunitname").val(json.unitname);
					$("#stockInit-goodsunitname1").html(json.unitname);
					$("#stockinit-auxunitname").val(json.auxunitname);
					$("#stockInit-auxunitname1").html(json.auxunitname);
					$("#stockinit-auxnum").val(json.auxnum);
					$("#stockinit-auxnumdetail").val(json.auxnumdetail);
					$("#stockinit-auxunitid").val(json.auxunitid);
					$("#stockInit-unitnum-aux").val(json.unitnumaux);
					$("#stockInit-unitnum-unit").val(json.auxremainder);

					$("#stockinit-amount").val(json.unitamount);
					$("#stockinit-amountdetail").val(formatterMoney(json.unitamount));
                    $("#stockinit-notaxamount").val(json.notaxamount);
                    $("#stockinit-tax").val(json.tax);
                    $("#stockinit-taxtypename").val(json.taxtypename);
                    $("#stockinit-taxtype").val(json.taxtype);

					$("#stockinit-price").removeClass("inputload");
					$("#stockinit-unitnum").removeClass("inputload");
				}
			}
		});
	}

	function computStockInitNumByAux(){
		var goodsid= $("#stockInit-goodsid").widget('getValue');
		if(goodsid==null ||goodsid==""){
			return false;
		}
		var unitnumaux = $("#stockInit-unitnum-aux").val();
		var auxremainder = $("#stockInit-unitnum-unit").val();
		var price = Number($("#stockinit-price").numberbox("getValue")).toFixed(2);
		var unitname = $("#stockInit-goodsunitname").val();
		var auxunitname = $("#stockinit-auxunitname").val();
		//后台对数据进行运算
		$.ajax({
			url :'storage/computStockInitNumByAux.do',
			type:'post',
			data:{goodsid:goodsid,unitnumaux:unitnumaux,auxremainder:auxremainder,price:price},
			dataType:'json',
			async:false,
			success:function(json){
				if(json!=null){
					$("#stockinit-unitnum").val(json.unitnum);
					$("#stockinit-auxnum").val(json.auxnum);
					var auxnumdetail = json.auxnum + auxunitname + json.auxremainder + unitname;
					$("#stockinit-auxnumdetail").val(auxnumdetail);
					$("#stockInit-unitnum-aux").val(json.auxnum);
					$("#stockInit-unitnum-unit").val(json.auxremainder);
                    $("#stockinit-amount").val(json.unitamount);
                    $("#stockinit-amountdetail").val(formatterMoney(json.unitamount));
                    $("#stockinit-notaxamount").val(json.notaxamount);
                    $("#stockinit-tax").val(json.tax);
                    $("#stockinit-taxtypename").val(json.taxtypename);
                    $("#stockinit-taxtype").val(json.taxtype);
					$("#stockinit-price").removeClass("inputload");
					$("#stockinit-unitnum").removeClass("inputload");
				}
			}
		});
	}
</script>
</body>
</html>
