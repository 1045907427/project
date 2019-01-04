<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <title>采购进货单凭证页面</title>
  </head>

  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
          <form id="purchaseAccount-form-addacountvourch" action="erpconnect/addPurchaseAccountVouch.do" method="post">
          <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
              <tr>
                  <td style="text-align: right;">账套名称：</td>
                  <td>
                      <select id="sales-customer-databaseid" onchange="loadSubject()"  style="width: 230px;" name="dbid">
                          <c:forEach items="${dbList }" var="list">
                              <option value="${list.id }">${list.dbasename }</option>
                          </c:forEach>
                      </select>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">凭证名称：</td>
                  <td>
                      <select id="purchase-addacountvourch-voucher" onchange="loadSubjectForType()" name="vouchertype" style="width:230px;">
                          <option value="1">采购进货凭证</option>
                          <c:if test="${versionParam == 1}">
                              <option value="2">采购进货冲暂估</option>
                          </c:if>
                      </select>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">凭证类别：</td>
                  <td>
                      <select id="sales-customer-sign"  style="width: 230px;" name="sign">
                          <%--<c:forEach items="${signList }" var="list">--%>
                              <%--<option value="${list.id }">${list.codesign }</option>--%>
                          <%--</c:forEach>--%>
                      </select>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">借方会计科目：</td>
                  <td id="purchaseAccount-td-dcode">
                      <input type="text" id="purchaseAccount-dcode" name="debitCode" style="width: 230px;" value="${purchaseDcode}" />
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">贷方会计科目：</td>
                  <td id="purchaseAccount-td-ccode">
                      <input type="text" id="purchaseAccount-ccode" name="creditCode" style="width: 230px;" value="${purchaseCcode}"/>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">操作日期：</td>
                  <td>
                      <input type="text" id="purchaseAccount-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                  </td>
              </tr>
          </table>
              <input type="hidden" id="purchaseAccount-ids" name="ids"/>
          </form>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" value="确 定" name="savenogo" id="purchaseAccount-button-addSave" />
          </div>
      </div>
  </div>

  	<script type="text/javascript">
        $(function(){
            var dbid=$("#sales-customer-databaseid").val();

            $("#purchaseAccount-button-addSave").click(function(){
                var operatedate = $("#purchaseAccount-date").val();
                var todayyear = '${today}'.substring(0,4);
                var operateyear = operatedate.substring(0,4);
                if(todayyear != operateyear){
                    $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                        if(r){
                            $("#purchaseAccount-form-addacountvourch").submit();
                        }
                    });
                }else{
                    $("#purchaseAccount-form-addacountvourch").submit();
                }
            });
            $("#purchaseAccount-form-addacountvourch").form({
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
                    if (json.flag) {
                        $.messager.alert("提醒", "生成成功");
                        $("#purchase-account-dialog").dialog("close");
                    } else {
                        $.messager.alert("提醒", "生成失败:" + json.msg);
                        $("#purchase-account-dialog").dialog("close");
                    }


                }
            });
            loadSubject();
        });

        function loadSubject(){
            var dbid=$("#sales-customer-databaseid").val();
            $("#purchaseAccount-dcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'1'}],
                width:230,
                required:true,
                onlyLeafCheck:true,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            $("#purchaseAccount-ccode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'0'}],
                width:230,
                required:true,
                onlyLeafCheck:true,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            $.ajax({
                url :"thirdDb/getThirdDbParam.do",
                data:{
                    dbid:dbid
                },
                type:'post',
                dataType:'json',
                async: false,
                success:function(json){
                    var versionParam=json.versionParam;
                    $("#purchase-addacountvourch-voucher").empty();
                    var tdstr = '<option value="1">采购进货凭证</option>';
                    $(tdstr).appendTo("#purchase-addacountvourch-voucher");
                    if(versionParam=='1'){
                        $('<option value="2">采购进货冲暂估</option>').appendTo("#purchase-addacountvourch-voucher");
                    }
                    var vouchertype=$("#purchase-addacountvourch-voucher").val();

                    var debitcode="",creditcode="";
                    if(vouchertype=='1'){
                        debitcode=json.purchaseDcode;
                        creditcode=json.purchaseCcode;
                    }else if(vouchertype=='2'){
                        debitcode=json.purchaseEstimateDcode;
                        creditcode=json.purchaseEstimateCcode;
                    }
                    if(debitcode==undefined){
                        $("#purchaseAccount-dcode").widget("clear");
                    }else{
                        $("#purchaseAccount-dcode").widget("setValue",debitcode);
                    }
                    if(creditcode==undefined){
                        $("#purchaseAccount-ccode").widget("clear");
                    }else{
                        $("#purchaseAccount-ccode").widget("setValue",creditcode);
                    }

                    var signList=json.signList;
                    $("#sales-customer-sign").empty();
                    for(var i=0;i<signList.length;i++){
                        var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                        $(tdstr).appendTo("#sales-customer-sign");
                    }
                }
            });
        }

        function loadSubjectForType(){
            var dbid=$("#sales-customer-databaseid").val();
            var vouchertype=$("#purchase-addacountvourch-voucher").val();
            $.ajax({
                url :"thirdDb/getThirdDbParam.do",
                data:{
                    dbid:dbid
                },
                type:'post',
                dataType:'json',
                async: false,
                success:function(json){
                    var debitcode="",creditcode="";
                    if(vouchertype=='1'){
                        debitcode=json.purchaseDcode;
                        creditcode=json.purchaseCcode;
                    }else if(vouchertype=='2'){
                        debitcode=json.purchaseEstimateDcode;
                        creditcode=json.purchaseEstimateCcode;
                    }

                    if(debitcode==undefined){
                        $("#purchaseAccount-dcode").widget("clear");
                    }else{
                        $("#purchaseAccount-dcode").widget("setValue",debitcode);
                    }
                    if(creditcode==undefined){
                        $("#purchaseAccount-ccode").widget("clear");
                    }else{
                        $("#purchaseAccount-ccode").widget("setValue",creditcode);
                    }

                }
            });
        }
  	</script>
  </body>
</html>
