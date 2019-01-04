<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品品牌详情页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="wares-form-brandAdd">
    	<input id="brand-oldId" type="hidden" value="<c:out value="${brand.id }"></c:out>"/>
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td>
    				<input id="wares-input-brandId" name="brand.id" type="text" readonly="readonly" value="<c:out value="${brand.id}"></c:out>" style="width: 200px;"/>
    				<input id="wares-hidden-addType" name="type" type="hidden"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input name="brand.name" type="text" readonly="readonly" value="<c:out value="${brand.name}"></c:out>" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>所属部门:</td>
    			<td><input id="wares-widget-Dept" required="true" disabled="disabled" name="brand.deptid" value="<c:out value="${brand.deptid}"></c:out>"/></td>
    		</tr>
            <tr>
                <td>所属供应商:</td>
                <td><input id="wares-widget-Supplier" name="brand.supplierid" value="<c:out value="${brand.supplierid }"></c:out>" readonly="readonly"/></td>
            </tr>
            <tr>
                <td>默认税种:</td>
                <td><input id="wares-widget-defaulttaxtype" name="brand.defaulttaxtype" type="text" value="<c:out value="${brand.defaulttaxtype }"></c:out>" readonly="readonly"/></td>
            </tr>
    		<tr>
    			<td>毛利率%:</td>
    			<td><input id="wares-numberbox-margin" name="brand.margin" type="text" readonly="readonly" value="${brand.margin}" class="easyui-numberbox" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="brand.remark" readonly="readonly" style="height:80px;width: 195px;overflow: hidden"><c:out value="${brand.remark}"></c:out></textarea></td>
    		</tr>
			<c:if test="${useHTKPExport=='1'}">
				<tr>
					<td style="width: 60px;">金税簇编码:</td>
					<td><input id="wares-input-jsclusterid" readonly="readonly"  name="brand.jsclusterid" value="${brand.jsclusterid}" style="width: 200px;"/>
					</td>
				</tr>
			</c:if>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" type="text" disabled="disabled" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
		$(function(){
			$("#brand-button-layout").buttonWidget("setDataID",{id:$("#brand-oldId").val(),state:"${brand.state}",type:'view'});

            // 毛利率
            $('#wares-numberbox-margin').numberbox({
                min:0,
                max:999,
                precision:2
            });

            //状态
            $('#common-combobox-state').widget({
                name:'t_base_goods_brand',
                col:'state',
                initValue:'${brand.state}',
                singleSelect:true
            });

            //所属部门
            $("#wares-widget-Dept").widget({
                referwid:'RL_T_BASE_DEPARTMENT_BUYER',
                singleSelect:true,
                onlyLeafCheck:true
            });

            //所属供应商
            $("#wares-widget-Supplier").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:200
            });

            //默认税种
            $("#wares-widget-defaulttaxtype").widget({
                width:200,
                referwid:'RL_T_BASE_FINANCE_TAXTYPE',
                singleSelect:true
            });
        });
    </script>
  </body>
</html>
