<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <title></title>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
          <form id="purchaseRejectAccount-form-addacountvourch" action="erpconnect/addPurchaseRejectAccountVouch.do" method="post">
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
              <tr>
                  <td style="text-align: right;">借方会计科目：</td>
                  <td>
                      <input type="text" id="purchaseRejectAccount-dcode" name="debitCode" style="width: 230px;" value="${purchaseRejectDcode}"/>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">贷方会计科目：</td>
                  <td>
                      <input type="text" id="purchaseRejectAccount-ccode" name="creditCode" style="width: 230px;" value="${purchaseRejectCcode}"/>
                  </td>
              </tr>
              <tr>
                  <td style="text-align: right;">操作日期：</td>
                  <td>
                      <input type="text" id="purchaseRejectAccount-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                  </td>
              </tr>
          </table>
              <input type="hidden" id="purchaseRejectAccount-ids" name="ids"/>
          </form>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" value="确 定" name="savenogo" id="purchaseRejectAccount-button-addSave" />
          </div>
      </div>
  </div>
  	<script type="text/javascript">
        $(function(){
            var dbid=$("#sales-customer-databaseid").val();

            $("#purchaseRejectAccount-button-addSave").click(function(){
                var operatedate = $("#purchaseRejectAccount-date").val();
                var todayyear = '${today}'.substring(0,4);
                var operateyear = operatedate.substring(0,4);
                if(todayyear != operateyear){
                    $.messager.alert("注意","请选择当前年份的操作日期！");
                    $("#purchaseRejectAccount-date").focus();
                }else{
                    $("#purchaseRejectAccount-form-addacountvourch").submit();
                }
            });
            $("#purchaseRejectAccount-form-addacountvourch").form({
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
                        $.messager.alert("提醒", "生成成功!");
                        $("#purchaseReject-account-dialog").dialog("close");
                    } else {
                        $.messager.alert("提醒", "生成失败!" + json.msg);
                    }


                }
            });
            loadSubject();
        });

        function loadSubject(){
            var dbid=$("#sales-customer-databaseid").val();
            $("#purchaseRejectAccount-dcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                // param:[{field:'bproperty',op:'like',value:'1'}],
                required:true,
                onlyLeafCheck:true,
                width:230,
                singleSelect:true,
                param:[{field:'dbid',op:'equal',value:dbid}]
            });
            $("#purchaseRejectAccount-ccode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                //param:[{field:'bproperty',op:'like',value:'0'}],
                required:true,
                width:230,
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
                    var debitcode="",creditcode="";
                    debitcode=json.purchaseRejectDcode;
                    creditcode=json.purchaseRejectCcode;

                    if(debitcode==undefined){
                        $("#purchaseRejectAccount-dcode").widget("clear");
                    }else{
                        $("#purchaseRejectAccount-dcode").widget("setValue",debitcode);
                    }
                    if(creditcode==undefined){
                        $("#purchaseRejectAccount-ccode").widget("clear");
                    }else{
                        $("#purchaseRejectAccount-ccode").widget("setValue",creditcode);
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
  	</script>
  </body>
</html>
