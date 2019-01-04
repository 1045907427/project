<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发票关联收款单列表</title>
  </head>

  <body>
  <div>
    <ul class="tags" >
       <li class="selectTag"><a href="javascript:void(0)">单据明细</a></li>
       <li><a href="javascript:void(0)">相关单据</a></li>
    </ul>
    <div class="tagsDiv1">
      <div class="tagsDiv_item1" style="display:block;" >
        <div style="display:block;height: 330px;width: 800px">
          <table id="account-relateCollectionOrder-statment-table"></table>
        </div>
      </div>
      <div class="tagsDiv_item1" >
        <div style="display:block;height: 330px;width: 800px">
          <table id="account-datagrid-salesInvoicePage"></table>
        </div>
      </div>
    </div>
  </div>
  <div id="account-panel-collectionOrder-addpage"></div>
  <div id="account-panel-customerPushBanlance-addpage"></div>
  <div id="account-panel-relation-upper"></div>
  <script type="text/javascript">
      $(function(){
          $(".tags").find("li").click(function(){
              var index = $(this).index();
              $(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
              $(".tagsDiv1 .tagsDiv_item1").hide().eq(index).show();
          });
          $("#account-relateCollectionOrder-statment-table").datagrid({
              columns:[[
                  {field:'billtype',title:'单据类型',width:60,
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1'){
                            return "销售发票";
                        }else if(value=='2'){
                            return "冲差单";
                        }else if(value=='3'){
                            return "收款单";
                        }
                    }
                  },
                  {field:'billid',title:'单据编号',width:125,
                    formatter:function(value,rowData,rowIndex){
                        if(value != "合计" && value != "选中合计"){
                            return '<a href="javascript:showBillPage(\''+rowData.billtype+'\',\''+value+'\');" style="text-decoration:underline">'+value+'</a>';
                        }else{
                            return value;
                        }
                    }
                  },
                  {field:'invoiceid',title:'发票号',width:100},
                  {field:'invoiceamount',title:'单据金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                  },
                  {field:'writeoffamount',title:'核销总金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                  },
                  {field:'relateamount',title:'已关联收款金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                  },
                  {field:'customerid',title:'客户编号',width:60},
                  {field:'customername',title:'客户名称',width:125},
                  {field:'businessdate',title:'业务日期',width:80},
                  {field:'unwriteoffamount',title:'收款单未核销金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                  },
                  {field:'writeoffdate',title:'核销日期',width:80},
                  {field:'remark',title:'备注',width:80}
                 ]],
              fit:true,
              method:'post',
              rownumbers:true,
              singleSelect: false,
              checkOnSelect:true,
              selectOnCheck:true,
              data:JSON.parse('${detailList}')
          });

          $("#account-datagrid-salesInvoicePage").datagrid({
              columns:[[{field:'id',title:'编号',width:120, align: 'left'},
                  {field:'businessdate',title:'业务日期',width:80,align:'left'},
                  {field:'saleorderid',title:'订单编号',width:150,align:'left'},
                  {field:'customerid',title:'客户编码',width:60,align:'left'},
                  {field:'customername',title:'客户名称',width:260,align:'left'},
                  {field:'sourceid',title:'客户单号',width:120,align:'left'},
                  {field:'salesdeptname',title:'销售部门',width:60,align:'left',hidden:true},
                  {field:'salesusername',title:'客户业务员',width:70,align:'left'},
                  {field:'field01',title:'含税金额',resizable:true,align:'right',
                      formatter:function(value,row,index){
                          return formatterMoney(value);
                      }
                  },
                  {field:'field02',title:'未税金额',resizable:true,align:'right',hidden:true,
                      formatter:function(value,row,index){
                          return formatterMoney(value);
                      }
                  },
                  {field:'field03',title:'税额',resizable:true,align:'right',hidden:true,
                      formatter:function(value,row,index){
                          return formatterMoney(value);
                      }
                  },
                  {field:'field04',title:'申请金额',resizable:true,align:'right',
                      formatter:function(value,row,index){
                          return formatterMoney(value);
                      }
                  },
                  {field:'addusername',title:'制单人',width:60,align:'left'},
                  {field:'addtime',title:'制单时间',width:100,align:'left',hidden:true},
                  {field:'status',title:'状态',width:60,align:'left',
                      formatter:function(value,row,index){
                          return getSysCodeName('status', value);
                      }
                  },
                  {field:'isinvoicebill',title:'开票状态',width:60,align:'left',
                      formatter:function(value,row,index){
                          if(value == "0"){
                              return "未开票";
                          }else if(value == "1"){
                              return "已开票";
                          }else if(value == "3"){
                              return "未开票";
                          }else if(value == "4"){
                              return "开票中";
                          }
                      }
                  },
                  {field:'isinvoice',title:'抽单状态',width:60,align:'left',
                      formatter:function(value,row,index){
                          if(value == "0"){
                              return "未申请";
                          }
                          else if(value == "1"){
                              return "已申请";
                          }
                          else if(value == "2"){
                              return "已核销";
                          }
                          else if(value == "3"){
                              return "未申请";
                          }else if(value == "4"){
                              return "申请中";
                          }else if(value == "5"){
                              return "部分核销";
                          }
                      }
                  }
              ]],
              fit:true,
              method:'post',
              rownumbers:true,
              singleSelect: false,
              checkOnSelect:true,
              selectOnCheck:true,
              data:JSON.parse('${sourceListStr}')
          })
      });

      function showBillPage(billtype,id){
          if(billtype=='1'){
              top.addOrUpdateTab('account/receivable/showSalesInvoiceViewPage.do?id='+ id, "销售核销查看");

  //                $("#account-panel-relation-upper").dialog({
  //                    href: "account/receivable/showSalesInvoiceSourceListReferPage.do?id=" + id,
  //                    title: "销售核销来源单据",
  //                    fit: true,
  //                    closed: false,
  //                    modal: true,
  //                    cache: false,
  //                    maximizable: true,
  //                    resizable: true,
  //                    onLoad: function () {
  //                        $("#salesinvoice-detail-close-detele").val("0");
  //                    },
  //                    onClose: function () {
  //                        if ($("#salesinvoice-detail-close-detele").val() == "1") {
  //                            top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id=' + id, "销售核销查看");
  //                        }
  //                    }
  //                });
          }else if(billtype=='2'){
              $('#account-panel-customerPushBanlance-addpage').dialog({
                  title: '客户应收款冲差修改',
                  width: 400,
                  height: 350,
                  collapsible:false,
                  minimizable:false,
                  maximizable:true,
                  resizable:true,
                  closed: true,
                  cache: false,
                  href: 'account/receivable/showCustomerPushBanlanceViewPage.do?id='+id,
                  modal: true,
                  onLoad:function(){
                      $("#account-customerPushBanlance-customerid").focus();
                  }
              });
              $('#account-panel-customerPushBanlance-addpage').dialog("open");
          }else if(billtype=='3'){
              $('#account-panel-collectionOrder-addpage').dialog({
                      title: '收款单查看',
                      width: 650,
                      height: 300,
                      collapsible:false,
                      minimizable:false,
                      maximizable:true,
                      resizable:true,
                      closed: true,
                      cache: false,
                      href: 'account/receivable/collectionOrderViewPage.do?id='+id,
                      modal: true
                  });
              $('#account-panel-collectionOrder-addpage').dialog("open");
          }
      }
  </script>
  </body>
</html>
