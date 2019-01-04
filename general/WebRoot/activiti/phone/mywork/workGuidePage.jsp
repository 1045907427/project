<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>新建工作向导</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        var list=$.parseJSON('${list }');var parents=new Array();var children=new Array();$(function(){localStorage.setItem("url","act/phone/workGuidePage.do");$.each(list,function(c,g){if(g.isparent=="1"){parents.push(g)}else{children.push(g)}});for(var d=parents.length-1;d>=0;d--){var e=parents[d];var a=false;for(var b in children){var f=children[b];if(f.pid==e.id){a=true;break}}if(!a){parents.splice(d,1)}}$.each(parents,function(c,g){$("#activiti-listview-workGuidePage").append('<li key="'+g.id+'" data-role="list-divider">'+g.name+"</li>")});$("#activiti-listview-workGuidePage").append('<li key="default" data-role="list-divider">&nbsp;</li>');children=children.reverse();$.each(children,function(c,g){g.pid=g.pid||"default";$("li[key="+g.pid+"]").after('<li key="'+g.id+'"><a href="act/phone/workGuidePage2.do?definitionkey='+g.unkey+'" rel="external">'+g.name+"</a></li>")});$("#activiti-listview-workGuidePage").listview("refresh")});

        <%--
        var list = $.parseJSON('${list }');
        var parents = new Array();
        var children = new Array();
        //var keys = ['addOaCustomer', 'addOaCustomerPay', 'addOaAccess', 'applyExpensePush', 'addOaSupplierPay', 'addOaOffPrice', 'applyGoodsPriceAdjustment', 'addOaGoods];

        // var definitions = $.parseJSON('${definitions }');
        // var temp = {};
        // $.each(definitions, function(index, item) {
//
        //     if(item.formkey || '' != '') {
        //         temp[item.unkey] = item.unkey;
        //     }
        // });
        // for(var key in temp) {
        //     keys.push(key);
        // }

        $(function() {

            localStorage.setItem('url', 'act/phone/workGuidePage.do');

//            for(var i = list.length - 1; i >= 0; i--) {
//
//                var item = list[i];
//
//                if(item.isparent == '1') {
//
//                    continue;
//                }
//
//                var include = false;
//                for(var j in keys) {
//
//                    var v = keys[j];
//
//                    if(v == item.unkey) {
//
//                        include = true;
//                        break;
//                    }
//                }
//
//                if(!include) {
//
//                    list.splice(i, 1);
//                }
//            }

            $.each(list, function(i, item) {

                if(item.isparent == '1') {

                    parents.push(item);

                } else {

                    children.push(item);
                }
            });

            for(var i = parents.length - 1; i >= 0; i--) {

                var p = parents[i];
                var include = false;
                for(var j in children) {

                    var c = children[j];
                    if(c.pid == p.id) {

                        include = true;
                        break;
                    }
                }
                if(!include) {

                    parents.splice(i, 1);
                }
            }

            $.each(parents, function(i, item) {

                $('#activiti-listview-workGuidePage').append('<li key="' + item.id + '" data-role="list-divider">' + item.name + '</li>');
            });
            $('#activiti-listview-workGuidePage').append('<li key="default" data-role="list-divider">&nbsp;</li>');

            children = children.reverse();
            $.each(children, function(i, item) {

                item.pid = item.pid || 'default';
                $('li[key=' + item.pid + ']').after('<li key="' + item.id + '"><a href="act/phone/workGuidePage2.do?definitionkey=' + item.unkey + '" rel="external">' + item.name + '</a></li>')
            });

            $('#activiti-listview-workGuidePage').listview('refresh');
        });
        --%>

    </script>
</head>
<body>
    <div data-role="page" id="activiti-main-workGuidePage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>新建工作</h1>
            <a href="javascript:location.href='act/phone/workListPage1.do';" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
            <%--<a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a> --%>
        </div>
        <div data-role="content">
            <ul data-role="listview" data-inset="false" id="activiti-listview-workGuidePage">
            </ul>
        </div>
    </div>
</body>
</html>
