<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>销售目标新增</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" id="salesTargetInput-form-detailAdd" method="post">
			<table>
				<tr>
					<td class="len90 right">编号：</td>
					<td class="len140"><input type="text" class="easyui-validatebox len150 readonly " name="salesTargetInput.id" readonly='readonly' required="required" value="${salesTargetInput.id}" /></td>
					<td class="len100 right">年月：</td>
					<td class="len140"><input class="len150 Wdate" type="text" id="salesTargetInput-form-detail-yearmonth" class="Wdate readonly" readonly='readonly' name="salesTargetInput.yearmonth" value="${salesTargetInput.yearmonth}" /></td>
					<td class="len100 right">状态：</td>
					<td class="len150">
						<select id="salesTargetInput-form-detail-yearmonth-status" disabled="disabled" class="len140">
							<c:forEach items="${statusList }" var="list">
								<c:choose>
									<c:when test="${list.code == salesTargetInput.status}">
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
					<td class="right">门店：</td>
					<td colspan="3">
						<input type="text" id="salesTargetInput-form-detail-customerid" name="salesTargetInput.customerid" value="${salesTargetInput.customerid}" class="readonly" readonly='readonly' />
					</td>
					<td class="right">品牌：</td>
					<td>
						<input class="len150" type="text" id="salesTargetInput-form-detail-brandid" name="salesTargetInput.brandid"  value="${salesTargetInput.brandid}" class="readonly" readonly='readonly' />
					</td>
				</tr>
				<tr>
					<td class="right">第一销售目标：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-firstsalestarget" name="salesTargetInput.firstsalestarget" value="${salesTargetInput.firstsalestarget}" class="len150 readonly" readonly='readonly' />
					</td>
					<td class="right">第一目标毛利：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-firstgrossprofit" name="salesTargetInput.firstgrossprofit" value="${salesTargetInput.firstgrossprofit}" autocomplete="off" class="len150 readonly" readonly='readonly'/>
					</td>
					<td class="right">第一目标毛利率：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-firstgrossprofitrate" name="salesTargetInput.firstgrossprofitrate" value="${salesTargetInput.firstgrossprofitrate}" autocomplete="off" class="len140 readonly" readonly="readonly"/>%
					</td>
				</tr>
				<tr>
					<td class="right">第二销售目标：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-secondsalestarget" name="salesTargetInput.secondsalestarget" value="${salesTargetInput.secondsalestarget}" autocomplete="off" class="len150 readonly" readonly='readonly'/>
					</td>
					<td class="right">第二目标毛利：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-secondgrossprofit" name="salesTargetInput.secondgrossprofit" value="${salesTargetInput.secondgrossprofit}" autocomplete="off" class="len150 readonly" readonly='readonly'/>
					</td>
					<td class="right">第二目标毛利率：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-secondgrossprofitrate" name="salesTargetInput.secondgrossprofitrate" value="${salesTargetInput.secondgrossprofitrate}" class="len140 readonly" readonly="readonly"/>%
					</td>
				</tr>
				<tr>
					<td class="right" rowspan="2">备注：</td>
					<td colspan="3" rowspan="2">
						<textarea id="salesTargetInput-form-detail-remark" style="width: 410px;height: 50px;" name="salesTargetInput.remark" class="readonly" disabled="disabled" >${salesTargetInput.remark}</textarea>
					</td>
					<td class="right">录入人：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-addusername" class="len140 readonly" class="readonly" value="${salesTargetInput.addusername}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="right">录入时间：</td>
					<td>
						<input type="text" id="salesTargetInput-form-detail-addtime" class="len140 readonly" class="readonly" value="<c:if test="${not empty(salesTargetInput.addtime)}"><fmt:formatDate  value="${salesTargetInput.addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></c:if>" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="text-align:right;">
			<input type="button" value="确 定" name="savegoon" id="salesTargetInput-form-detail-addbutton" />
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){


		$("#salesTargetInput-form-detail-customerid").widget({ //分类
			name:'t_salestarget_input',
			col:'customerid',
			singleSelect:true,
			width:420,
			onlyLeafCheck:false,
			required:true,
			onChecked:function(){
				$(this).blur();
				$("#salesTargetInput-form-detail-brandid").focus();
			},
			onSelect:function(){
				$(this).blur();
				$("#salesTargetInput-form-detail-brandid").focus();
			}
		});

		//品牌
		$("#salesTargetInput-form-detail-brandid").widget({
			name:'t_salestarget_input',
			col:'brandid',
			singleSelect:true,
			width:140,
			onlyLeafCheck:true,
			required:true,
			onSelect:function(){
				$(this).blur();
				$("#salesTargetInput-form-detail-firstsalestarget").focus();
				$("#salesTargetInput-form-detail-firstsalestarget").select();
			}
		});


		$("#salesTargetInput-form-detailAdd").form({
			onSubmit: function(){
				var flag = $(this).form('validate');
				if(flag==false){
					return false;
				}
				var tmpd=$("#salesTargetInput-form-detail-firstsalestarget").val()||0;
				if(tmpd==0){
					$.messager.alert("提醒","请填写第一目标值");
					$("#salesTargetInput-form-detail-firstsalestarget").focus();
					return false;
				}
				loading("提交中..");
			},
			success:function(data){
				//表单提交完成后 隐藏提交等待页面
				loaded();
				var json = $.parseJSON(data);
				if(json.flag){
					$.messager.alert("提醒","保存成功");
					$("#salesTargetInputListPage-table-detail").datagrid("reload");
					var savetype = $("#salesTargetInput-form-detail-addtype").val();
					if(savetype=="saveadd"){
						$("#salesTargetInput-form-detail-customersort").widget('clear');
						$("#salesTargetInput-form-detail-salesuserid").widget('clear');
						$("#salesTargetInput-form-detail-brandid").widget('clear');
						$("#purchase-form-arrivalOrderListPage")[0].reset();
					}else{
						$('#salesTargetInput-dialog-detail').dialog("close");
					}
				}else{
					$.messager.alert("提醒","保存失败");
				}
			}
		});
		$("#salesTargetInput-form-detail-addbutton").click(function(){
			$("#salesTargetInput-form-detail-addtype").val("save");
			$("#salesTargetInput-form-detailAdd").attr("action", "module/salestargetinput/addSalesTargetInput.do");
			$("#salesTargetInput-form-detailAdd").submit();
		});
		$("#salesTargetInput-form-detail-addgobutton").click(function(){
			$("#salesTargetInput-form-detail-addtype").val("saveadd");
			$("#salesTargetInput-form-detailAdd").attr("action", "module/salestargetinput/addSalesTargetInput.do");
			$("#salesTargetInput-form-detailAdd").submit();
		});
		$("#salesTargetInput-form-detail-firstsalestarget").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13 && $(this).validatebox('isValid')){
				$(this).blur();
				$("#salesTargetInput-form-detail-firstgrossprofit").focus();
				$("#salesTargetInput-form-detail-firstgrossprofit").select();
			}
		});
		$("#salesTargetInput-form-detail-firstgrossprofit").die("keydown").live("keydown",function(e){
			if(e.keyCode == 13 && $(this).validatebox('isValid')){
				$(this).blur();
				$("#salesTargetInput-form-detail-secondsalestarget").focus();
				$("#salesTargetInput-form-detail-secondsalestarget").select();
			}
		});
		$("#salesTargetInput-form-detail-secondsalestarget").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13 && $(this).validatebox('isValid')){
				$(this).blur();
				$("#salesTargetInput-form-detail-secondgrossprofit").focus();
				$("#salesTargetInput-form-detail-secondgrossprofit").select();
			}
		});
		$("#salesTargetInput-form-detail-secondgrossprofit").die("keydown").live("keydown",function(e){
			if(e.keyCode == 13 && $(this).validatebox('isValid')){
				$(this).blur();
				$("#salesTargetInput-form-detail-remark").focus();
				$("#salesTargetInput-form-detail-remark").select();
			}
		});
		$("#salesTargetInput-form-detail-remark").bind('keydown',function(e){
			if(e.keyCode == 13 && $("#salesTargetInput-form-detailAdd").form('validate')){
				$("#salesTargetInput-form-detail-addgobutton").click();
			}
		});


		$("#salesTargetInput-form-detail-firstsalestarget").change(function(){
			if($(this).validatebox('isValid')){
				calcTargetRate("salesTargetInput-form-detail-firstsalestarget",
						"salesTargetInput-form-detail-firstgrossprofit",
						"salesTargetInput-form-detail-firstgrossprofitrate");
			}
		});
		$("#salesTargetInput-form-detail-firstgrossprofit").change(function(){
			if($(this).validatebox('isValid')){
				calcTargetRate("salesTargetInput-form-detail-firstsalestarget",
						"salesTargetInput-form-detail-firstgrossprofit",
						"salesTargetInput-form-detail-firstgrossprofitrate");
			}
		});
		$("#salesTargetInput-form-detail-secondsalestarget").change(function(){
			if($(this).validatebox('isValid')){
				calcTargetRate("salesTargetInput-form-detail-secondsalestarget",
						"salesTargetInput-form-detail-secondgrossprofit",
						"salesTargetInput-form-detail-secondgrossprofitrate");
			}
		});

		$("#salesTargetInput-form-detail-secondgrossprofit").change(function(){
			if($(this).validatebox('isValid')){
				calcTargetRate("salesTargetInput-form-detail-secondsalestarget",
						"salesTargetInput-form-detail-secondgrossprofit",
						"salesTargetInput-form-detail-secondgrossprofitrate");
			}
		});
		initRate();
	});
	function initRate(){
		var firstrate="${salesTargetInput.firstgrossprofitrate}";
		if(isNaN(firstrate)){
			firstrate=0;
		}
		firstrate=formatterMoney(firstrate);
		var secondrate="${salesTargetInput.secondgrossprofitrate}";
		if(isNaN(secondrate)){
			secondrate=0;
		}
		secondrate=formatterMoney(secondrate);
		$("#salesTargetInput-form-detail-firstgrossprofitrate").val(firstrate);
		$("#salesTargetInput-form-detail-secondgrossprofitrate").val(secondrate);
	}
</script>
</body>
</html>
