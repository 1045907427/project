<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>对应客户</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true" id="line-layout-customertoline">
		<div data-options="region:'south'" style="height: 30px;" align="right">
			<a href="javaScript:void(0);" id="logistics-save-customertoline" class="easyui-linkbutton" data-options="plain:false" title="确定">确定</a>
		</div>
        <div data-options="region:'center'" align="right" style="width: 420px;">
            <iframe name="mapframe" src="basefiles/customerToLineMapPage.do?lineid=${param.lineid }&type=view" style="width: 100%; height: 100%; border: 0px;">
            </iframe>
        </div>
     </div>
    <script type="text/javascript">

        var showedCustomerids = new Array();
        var selectedCustomerids = new Array();
        var deletedCustomerids = new Array();
        var westCollapsed = false;

    	$(function(){

    	    var customerRows = $customerDatagrid.datagrid('getRows');
            for(var i in customerRows) {

                selectedCustomerids.push(customerRows[i].customerid);
            }

            $("#logistics-save-customertoline").click(function(){
                $("#line-dialog-customer").dialog('close',true);
            });

    	});

        /**
         * 选中客户
         *
         * @param customerid
         * @returns {boolean}
         */
        function checkCustomer(customerid) {
            window.mapframe.selectCustomer(customerid);
            return true;
        }

        /**
         * 设置起始点
         *
         * @param lng
         * @param lat
         * @returns {boolean}
         */
        function setStartPoint(lng, lat) {

            if(lng && lat) {
                $('[name="lineInfo.startpoint"]').val(lng + ',' + lat);
            }
            return true;
        }
    </script>
  </body>
</html>
