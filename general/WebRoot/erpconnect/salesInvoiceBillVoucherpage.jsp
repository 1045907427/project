<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <title>申请开票凭证页面</title>
  </head>

  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
          <form id="Account-form-addacountvourch" action="erpconnect/addSalsInvoiceBillVoucher.do" method="post">
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
                  <td style="text-align: right;">凭证类别：</td>
                  <td>
                      <select id="sales-customer-sign"  style="width: 230px;" name="sign">
                          <%--<c:forEach items="${signList }" var="list">--%>
                              <%--<option value="${list.id }">${list.codesign }</option>--%>
                          <%--</c:forEach>--%>
                      </select>
                  </td>
              </tr>
              <c:if test="${type == 'tax' }">
                  <tr>
                      <td style="text-align: right;">是否含税：</td>
                      <td>
                          <select name="type" style="width: 230px;" >
                              <option value="tax" selected="selected" >是</option>
                              <option value="notax">否</option>
                          </select>
                      </td>
                  </tr>
              </c:if>
            <c:if test="${type == 'cost'}">
                <tr>
                    <td style="text-align: right;">是否含税：</td>
                    <td>
                        <select name="istax" style="width: 230px;" >
                            <option value="1" selected="selected">是</option>
                            <option value="0">否</option>
                        </select>
                        <input type="hidden" name="type" value="cost"  />
                    </td>
                </tr>
            </c:if>
              <tr>
                  <td style="text-align: right;">借方会计科目：</td>
                  <td id="Account-td-dcode">
                      <input type="text" id="Account-dcode" name="debitCode" style="width: 230px;"  />
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">贷方会计科目1：</td>
                  <td>
                      <input type="text" id="Account-ccode1" name="creditCode1" style="width: 230px;"  />
                  </td>
              </tr>
              <c:if test="${type == 'tax'}" >
                  <tr>
                      <td style="text-align: right;">贷方会计科目2：</td>
                      <td>
                          <input type="text" id="Account-ccode2" name="creditCode2" style="width: 230px;" />
                      </td>
                  </tr>
              </c:if>
              <tr>
                  <td style="text-align: right;">操作日期：</td>
                  <td>
                      <input type="text" id="Account-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                  </td>
              </tr>
          </table>
              <input type="hidden" id="Account-ids" name="ids"/>
              <input type="hidden" id="Account-type" name="type" value="${type}" />
          </form>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" value="确 定" name="savenogo" id="Account-button-addSave" />
          </div>
      </div>
  </div>

  	<script type="text/javascript">
        $(function(){

            $("#Account-button-addSave").click(function(){
                var operatedate = $("#Account-date").val();
                var todayyear = '${today}'.substring(0,4);
                var operateyear = operatedate.substring(0,4);
                if(todayyear != operateyear){
                    $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                        if(r){
                            $("#Account-form-addacountvourch").submit();
                        }
                    });
                }else{
                    $("#Account-form-addacountvourch").submit();
                }
            });
            $("#Account-form-addacountvourch").form({
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
                        $("#account-addVoucher-dialog").dialog("close");
                    } else {
                        $.messager.alert("提醒", "生成失败:" + json.msg);
                        $("#account-addVoucher-dialog").dialog("close");
                    }


                }
            });
            loadSubject();
        });

        function loadSubject(){
            var dbid=$("#sales-customer-databaseid").val();
            $("#Account-dcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                width:230,
                required:true,
                onlyLeafCheck:true,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            $("#Account-ccode1").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                width:230,
                required:true,
                onlyLeafCheck:true,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            $("#Account-ccode2").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                width:230,
                required:true,
                onlyLeafCheck:true,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            var type = "${type}";console.info(type)
            $.ajax({
                url :"thirdDb/getThirdDbParam.do",
                data:{
                    dbid:dbid
                },
                type:'post',
                dataType:'json',
                async: false,
                success:function(json){
                    var signList=json.signList;
                    $("#sales-customer-sign").empty();
                    for(var i=0;i<signList.length;i++){
                        var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                        $(tdstr).appendTo("#sales-customer-sign");
                    }
                    if(type != "cost"){
                        var debitcode=json.salesDcode;
                        var creditcode1=json.salesNotaxamountCcode;
                        var creditcode2=json.salesTaxCcode;
                    }else{
                        var debitcode=json.mainCostDcode;
                        var creditcode1=json.mainCostCcode;
                        var creditcode2="";
                    }
                    $("#Account-dcode").widget("setValue",debitcode);
                    $("#Account-ccode1").widget("setValue",creditcode1);
                    $("#Account-ccode2").widget("setValue",creditcode2);

                }
                
            });
        }

  	</script>
  </body>
</html>
