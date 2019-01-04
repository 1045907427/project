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
	    <div data-options="region:'center'<security:authorize url="/basefiles/showLineInfoMapPage.do">,region:'west'</security:authorize>,split:false,border:true,collapsible:false,onCollapse:westCollapse,onExpand:westExpand" style="width: 440px;">
	    	<div id="logistics-toolbar-customertoline" style="width: 440px;">
	    		<form action="" id="logistics-form-customertolineQuery" method="post" style="padding-left: 5px;">
	    			<table cellpadding="1" cellspacing="0" border="0">
	    				<tr>
	    					<td>区域:</td>
	    					<td><input type="text" name="salesarea" id="logistics-customer-sortarea" />
				    		</td>
				    		<td>客户分类:</td>
				    		<td><input type="text" name="customersort" id="logistics-customer-customersort" /></td>
                        </tr>
                        <tr>
                            <td>客户:</td>
                            <td><input type="text" name="id" id="logistics-customer-id" /></td>
							<td>客户业务员:</td>
							<td><input type="text" name="salesuserid" id="logistics-customer-salesuser" /></td>
                        </tr>
                        <tr>
	    					<td colspan="4" align="right">
	    						<a href="javaScript:void(0);" id="logistics-query-customertoline" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="logistics-reload-customertoline" class="button-qr">重置</a>
							</td>
	    				</tr>
	    			</table>
	    		</form>
	    	</div>
	    	<table id="logistics-table-customertoline"></table>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right">
			<a href="javaScript:void(0);" id="logistics-save-customertoline" class="easyui-linkbutton" data-options="plain:false" title="确定">确定</a>
			<a href="javaScript:void(0);" id="logistics-savegoon-customertoline" class="easyui-linkbutton" data-options="plain:false" title="继续添加">继续添加</a>
		</div>
        <security:authorize url="/basefiles/showLineInfoMapPage.do">
            <div data-options="region:'center'" align="right">
                <iframe name="mapframe" src="basefiles/customerToLineMapPage.do?lineid=${param.lineid }&type=edit" style="width: 100%; height: 100%; border: 0px;">
                </iframe>
            </div>
        </security:authorize>
     </div>
    <script type="text/javascript">
		var type='${param.type}';
        function westCollapse() {
            westCollapsed = !westCollapsed;
            <security:authorize url="/basefiles/showLineInfoMapPage.do">
                window.mapframe.showCustomerList(true);
            </security:authorize>
        }

        function westExpand() {
            westCollapsed = !westCollapsed;
            <security:authorize url="/basefiles/showLineInfoMapPage.do">
                window.mapframe.showCustomerList(false);
            </security:authorize>
        }

        var showedCustomerids = new Array();
        var selectedCustomerids = new Array();
        var deletedCustomerids = new Array();
        var westCollapsed = false;

    	$("#logistics-customer-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:true,
   			width:170,
   			onlyLeafCheck:false,
   			view: true
   		});
   		
    	$("#logistics-customer-customersort").widget({ //分类
   			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
			//col:'customersort',
   			singleSelect:true,
   			width:110,
   			onlyLeafCheck:false,
   			view: true
   		});
   		
   		$("#logistics-customer-salesuser").widget({ //客户业务员
   			referwid:'RL_T_BASE_PERSONNEL_SELLER',
   			singleSelect:true,
   			width:110,
   			onlyLeafCheck:false,
   			view: true
   		});
   		
   		$("#logistics-customer-id").widget({ //客户
   			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
			col:'id',
   			singleSelect:true,
   			width:170,
   			onlyLeafCheck:false
   		});
   		
    	$(function(){
    		var customerid3 = $("#logistics-linecustomer").val();
			$("#logistics-table-customertoline").datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		pageSize:100,
	  	 		pagination:true,
				checkOnSelect:true,
				selectOnCheck:true,
				queryParams:{customerid:customerid3},
				toolbar:'#logistics-toolbar-customertoline',
				columns:[[  
			        {field:'ck',title:'',width:100,checkbox:true},
			        {field:'id',title:'编码',width:60},  
			        {field:'name',title:'名称',width:160},
			        {field:'salesareaname',title:'所属区域',width:70,isShow:true},
			        {field:'customersortname',title:'所属分类',width:70,isShow:true},
			        {field:'salesusername',title:'客户业务员',width:70,isShow:true}
			    ]],
                onBeforeLoad: function() {
                    var o = $("#logistics-table-customertoline").datagrid('options').queryParams;
                    try {
                        if(o.id != undefined) {

                            cancelCheckCustomerThisTime();
                            <security:authorize url="/basefiles/showLineInfoMapPage.do">
                                window.mapframe.loading('正在处理...');
                            </security:authorize>
                        }
                    } catch (e) {}
                },
			    onSelect:function(rowIndex, rowData){
                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        window.mapframe.selectCustomer(rowData.id);
                    </security:authorize>
			    },
			    onUnselect:function(rowIndex, rowData){
                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        window.mapframe.cancelCustomer(rowData.id);
                    </security:authorize>
			    },
				onSelectAll: function(rows) {
                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        for(var i in rows) {
                            window.mapframe.selectCustomer(rows[i].id);
                        }
                    </security:authorize>
                },
				onUnselectAll: function(rows) {
                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        for(var i in rows) {
                            window.mapframe.cancelCustomer(rows[i].id);
                        }
                    </security:authorize>
                },
                onLoadSuccess: function (data) {

                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        try {
                            window.mapframe.loaded();
                        } catch (e) {}
                    </security:authorize>

                    var rows = data.rows || [];
                    var temp = new Array();

                    for(var i in rows) {

                        temp.push(rows[i].id);
                    }

                    showedCustomerids = temp;

                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        try {
                            window.mapframe.clearCustomerMarks();
                            window.mapframe.markCustomer(showedCustomerids);
                        } catch (e) {}
                    </security:authorize>
                }
			});

    		//查询
			$("#logistics-query-customertoline").click(function(){
	      		var queryJSON = $("#logistics-form-customertolineQuery").serializeJSON();
	   			queryJSON['customerid'] = $("#logistics-linecustomer").val();

                $("#logistics-table-customertoline").datagrid({

                    url:'basefiles/getCustomerListForCombobox.do',
                    queryParams: queryJSON
                });
			});
			
			//重置按钮
			$("#logistics-reload-customertoline").click(function(){
				$("#logistics-form-customertolineQuery")[0].reset();
				$("#logistics-customer-sortarea").widget('clear');
				$("#logistics-customer-customersort").widget('clear');
				$("#logistics-customer-salesuser").widget('clear');
                $("#logistics-customer-id").widget('clear');
				$("#logistics-table-customertoline").datagrid('loadData', []);
//				$("#logistics-table-customertoline").datagrid("load",{customerid: customerid3});
				
			});
    		
    		$("#logistics-savegoon-customertoline").click(function(){
                <security:authorize url="/basefiles/showLineInfoMapPage.do">
                    window.mapframe.clearOverlays();
                </security:authorize>
                // 删除客户
                var srcRows = $dgInfoCustomer.datagrid('getRows');
                for(var i in deletedCustomerids) {

                    for(var j in srcRows) {

                        if(srcRows[j].customerid == deletedCustomerids[i]) {

                            $dgInfoCustomer.datagrid('deleteRow', j);
                            break;
                        }
                    }
                }
    			var customerid = "",customerid2 = $("#logistics-linecustomer").val();
    			var rows = $("#logistics-table-customertoline").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请勾选对应客户名称！");
    				return false;
    			}
    			var customerkey = "";
    			for(var i=0;i<rows.length;i++){
    				customerid += rows[i].id + ",";
    				if("" == customerkey){
    					customerkey = rows[i].id;
    				}else{
    					customerkey += "|" + rows[i].id;
    				}
                    selectedCustomerids.push(rows[i].id);
    			}
                <security:authorize url="/basefiles/showLineInfoMapPage.do">
    			    window.mapframe.initSelectedCustomerMarker();
                </security:authorize>


    			if("" == $("#logistics-linecustomer").val()){
    				$("#logistics-linecustomer").val(customerid2 + customerid);
    			}else{
    				$("#logistics-linecustomer").val($("#logistics-linecustomer").val() + customerid);
    			}
    			var totalcustomerStr = $("#logistics-linecustomer").val();

    			if("" != totalcustomerStr){
    				$("#lineInfo-input-totalcustomers").val(totalcustomerStr.split(",").length-Number(1));
    			}

                //修改页面的客户操作直接进行数据库添加或删除
                if(type=='edit'){
                    //临时使用的map
                    var insertmap1={};
                    insertmap1[customerkey] = "${insertcustomerid}";
                    var queryJSON = $("#logistics-form-customertolineQuery").serializeJSON();
                    queryJSON['customerids'] = $("#logistics-linecustomer").val();
                    queryJSON['customerid'] = $("#logistics-linecustomer").val();
                    var ret = lineInfo_AjaxConn({lineid:'${param.lineid}',customerids:$("#logistics-linecustomer").val(),insertmap:JSON.stringify(insertmap1)},'basefiles/saveCustomerForLine.do');
                    var retJson = $.parseJSON(ret);
                    if(retJson.flag){
                        $.messager.alert("提醒","保存成功!");
                        $dgInfoCustomer.datagrid('reload');
                        $("#logistics-table-customertoline").datagrid("load",queryJSON);
                    }
                }else{
                    insertmap[customerkey] = "${insertcustomerid}";
                    var queryJSON = $("#logistics-form-customertolineQuery").serializeJSON();
                    queryJSON['customerids'] = $("#logistics-linecustomer").val();
                    queryJSON['lineid'] = $("#lineInfo-id-baseInfo").val();
                    queryJSON['insertmap'] = JSON.stringify(insertmap);
                    $dgInfoCustomer.datagrid('options').url = 'basefiles/showLineCustomerListData.do';
                    $dgInfoCustomer.datagrid('load',queryJSON);
                    queryJSON['customerid'] = $("#logistics-linecustomer").val();
                    $("#logistics-table-customertoline").datagrid("load",queryJSON);
                }

    		});
    		
    		$("#logistics-save-customertoline").click(function(){
                <security:authorize url="/basefiles/showLineInfoMapPage.do">
    		        window.mapframe.clearOverlays();
                </security:authorize>
                // 删除客户
                var srcRows = $dgInfoCustomer.datagrid('getRows');
                for(var i in deletedCustomerids) {

                    for(var j in srcRows) {

                        if(srcRows[j].customerid == deletedCustomerids[i]) {

                            $dgInfoCustomer.datagrid('deleteRow', j);
                            break;
                        }
                    }
                }
    			var customerid = "",customerid2 = $("#logistics-linecustomer").val();
    			var rows = $("#logistics-table-customertoline").datagrid('getChecked');
    			if(rows.length == 0){
                    $("#line-dialog-customer").dialog('close',true);
                    return false;
    				$.messager.alert("提醒","请勾选对应客户名称！");
    			}
    			var customerkey = "";
    			for(var i=0;i<rows.length;i++){
    				customerid += rows[i].id + ",";
    				if("" == customerkey){
    					customerkey = rows[i].id;
    				}else{
    					customerkey += "|" + rows[i].id;
    				}
    			}

    			if("" == $("#logistics-linecustomer").val()){
    				$("#logistics-linecustomer").val(customerid2 + customerid);
    			}else{
    				$("#logistics-linecustomer").val($("#logistics-linecustomer").val() + customerid);
    			}
    			var totalcustomerStr = $("#logistics-linecustomer").val();
    			if("" != totalcustomerStr){
                    $("#lineInfo-input-totalcustomers").val(totalcustomerStr.split(",").length-Number(1));
    			}

    			//修改页面的客户操作直接进行数据库添加或删除
    			if(type=='edit'){
    			    //临时使用的map
                    var insertmap1={};
                    insertmap1[customerkey] = "${insertcustomerid}";
                    var queryJSON = $("#logistics-form-customertolineQuery").serializeJSON();
                    queryJSON['customerids'] = $("#logistics-linecustomer").val();
                    var ret = lineInfo_AjaxConn({lineid:'${param.lineid}',customerids:$("#logistics-linecustomer").val(),insertmap:JSON.stringify(insertmap1)},'basefiles/saveCustomerForLine.do');
                    var retJson = $.parseJSON(ret);
                    if(retJson.flag){
                        $.messager.alert("提醒","保存成功!");
                        $dgInfoCustomer.datagrid('reload');
                        $("#line-dialog-customer").dialog('close',true);
                    }
                }else{
                    insertmap[customerkey] = "${insertcustomerid}";
                    var queryJSON = $("#logistics-form-customertolineQuery").serializeJSON();
                    queryJSON['customerids'] = $("#logistics-linecustomer").val();
                    queryJSON['lineid'] = $("#lineInfo-id-baseInfo").val();
                    queryJSON['insertmap'] = JSON.stringify(insertmap);
                    $dgInfoCustomer.datagrid('options').url = 'basefiles/showLineCustomerListData.do';
                    $dgInfoCustomer.datagrid('load',queryJSON);
                    $("#line-dialog-customer").dialog('close',true);
                }

    		});

            var srcRows = $dgInfoCustomer.datagrid('getRows');
            for(var i in srcRows) {

                var srcRow = srcRows[i];
                selectedCustomerids.push(srcRow.customerid);
            }
    	});

        /**
         *
         * @param customerid
         * @returns {boolean}
         */
        function checkCustomer(customerid) {

            var rows = $("#logistics-table-customertoline").datagrid('getRows');
            for(var i in rows) {

                var row = rows[i];
                if(row.id == customerid) {

                    $("#logistics-table-customertoline").datagrid('selectRow', i);
                    return true;
                }
            }

            <security:authorize url="/basefiles/showLineInfoMapPage.do">
                window.mapframe.selectCustomer(customerid);
            </security:authorize>
            return false;
        }

        /**
         * 取消选择
         *
         * @returns {boolean}
         */
        function cancelCheckCustomer(customerid) {

            var rows = $("#logistics-table-customertoline").datagrid('getRows');
            for(var i in rows) {

                var row = rows[i];
                if(row.id == customerid) {

                    $("#logistics-table-customertoline").datagrid('unselectRow', i);
                    return true;
                }
            }

            var srcRows = $dgInfoCustomer.datagrid('getRows');
            for(var i in srcRows) {

                var srcRow = srcRows[i];
                if(srcRow.customerid == customerid) {

                    deletedCustomerids.push(customerid);

                    var customeridArr = $("#logistics-linecustomer").val().split(',');
                    for(var j in customeridArr) {

                        if(customerid == customeridArr[j]) {
                            customeridArr.splice(j, 1);
                            break;
                        }
                    }

                    $("#logistics-linecustomer").val(customeridArr.join(','));

                    // 从selectedCustomerids 中去除
                    for(var i in selectedCustomerids) {
                        if(customerid == selectedCustomerids[i]) {
                            selectedCustomerids.splice(i, 1);
                        }
                    }

                    <security:authorize url="/basefiles/showLineInfoMapPage.do">
                        window.mapframe.cancelCustomer(customerid);
                    </security:authorize>
                    return true;
                }
            }

            return false;
        }

        /**
         * 取消本次选择
         *
         * @returns {boolean}
         */
        function cancelCheckCustomerThisTime() {

            var srcRows = $dgInfoCustomer.datagrid('getRows');
            var rows = $("#logistics-table-customertoline").datagrid('getRows');
            var checkRows = $("#logistics-table-customertoline").datagrid('getChecked');

            for(var i in rows) {

                var check = false;
                var row = rows[i];

                for(var k in checkRows) {

                    var checkRow = checkRows[k];
                    if(row.id == checkRow.id) {
                        check = true;
                        break;
                    }
                }

                if(check) {
                    $("#logistics-table-customertoline").datagrid('unselectRow', i);
                }
            }

            return false;
        }

        /**
         * 折叠
         *
         * @returns {boolean}
         */
        function collapseWest() {

            westCollapsed ? $('#line-layout-customertoline').layout('expand', 'west') : $('#line-layout-customertoline').layout('collapse', 'west');
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

        /**
         * 设置客户默认位置
         *
         * @param lng
         * @param lat
         * @returns {boolean}
         */
        function setDefaultPoint(lng, lat) {

            if(lng && lat) {
                $('[name="lineInfo.defaultpoint"]').val(lng + ',' + lat);
            }
            return true;
        }

        /**
         * 判断对象是否为{} 或 null
         *
         * @param obj
         * @returns {boolean}
         */
        function isEmpty(obj) {
            for(var name in obj) {
                return false;
            }
            return true;
        }

    </script>
  </body>
</html>
