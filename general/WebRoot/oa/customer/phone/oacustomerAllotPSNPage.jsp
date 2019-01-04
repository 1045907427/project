<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分配品牌业务员</title>
</head>
<body>
    <div data-role="page" id="oa-main-oacustomerAllotPSNPage">
        <form action="" id="oa-form-oacustomerAllotPSNPage">
            <a href="#main" id="oa-return-oacustomerAllotPSNPage" style="display: none;"></a>
            <input type="hidden" id="oa-billid-oacustomerAllotPSNPage" name="billid" value="${id }"/>
            <%--  --%>
            <input type="hidden" id="oa-personids-oacustomerAllotPSNPage" name="personids"/>
            <input type="hidden" id="oa-delpersonids-oacustomerAllotPSNPage" name="delpersonids"/>
            <input type="hidden" id="oa-company-oacustomerAllotPSNPage" name="company"/>
            <c:forEach items="${paramlist }" var="paramlist">
                <input id="oa-hdperson-${paramlist.deptid }" class="personid allot-personid" type="hidden"/>
                <input id="oa-hddelperson-${paramlist.deptid }" class="delpersonid" type="hidden"/>
                <input id="oa-hdcompany-${paramlist.deptid }" class="company allot-company" type="hidden" value="${paramlist.deptid }"/>
            </c:forEach>
            <div data-role="header" data-position="fixed" data-tap-toggle="false">
                <h1>分配品牌业务员</h1>
                <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
            </div>
            <div class="ui-body ui-body-a">
                <c:forEach items="${paramlist }" var="paramlist" varStatus="idx">
                    <div class="ui-field-contain">
                        <label><c:out value="${paramlist.deptname }"/>品牌业务员<font color="#F00">*</font>
                            <input type="text" id="oa-oaAllotPSNCustomerPage2-${paramlist.deptid }" value="<c:if test="${not empty paramlist.personid}">${paramlist.personid }:${paramlist.personname}</c:if>" <c:if test="${param.type eq 'handle'}">data-clear-btn="true"</c:if> readonly="readonly"/>
                            <input type="hidden" id="oa-oaAllotPSNCustomerPage-${paramlist.deptid }" class="personid" value="${paramlist.personid }"/>
                            <input type="hidden" class="company" value="${paramlist.deptid }"/>
                        </label>
                    </div>
                    <script type="text/javascript">
                        {
                            $('#oa-oaAllotPSNCustomerPage2-${paramlist.deptid }').off().on('change', function(e) {

                                var v = $(this).val() || ':';
                                $('#oa-oaAllotPSNCustomerPage-${paramlist.deptid }').val(v.split(':')[0]);
                            }).on('click', function(e) {

                                androidWidget({
                                    type: 'widget',
                                    referwid: 'RL_T_BASE_PERSONNEL_BRANDSELLER',
                                    paramRule: [{field:'deptid',op:'equal',value:'${paramlist.deptid }'}],
                                    onSelect: 'selectPerson${idx.index }'
                                });
                            });
                        }

                        function selectPerson${idx.index }(data) {

                            $('#oa-oaAllotPSNCustomerPage2-${paramlist.deptid }').val(data.id + ':' + data.name);
                            $('#oa-oaAllotPSNCustomerPage-${paramlist.deptid }').val(data.id);
                        }

                    </script>
                </c:forEach>
            </div>
            <c:if test="${param.type eq 'handle'}">
                <div id="oa-footer-oacustomerAllotPSNPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
                    <a href="javascript:void(0);" onclick="javascript:return allotPSN();" id="oa-ok-oacustomerAllotPSNPage" data-role="button" data-icon="check">确定</a>
                </div>
            </c:if>
        </form>
    </div>
</body>
</html>
