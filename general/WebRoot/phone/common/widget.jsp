<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2017/7/10
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${wname }</title>
</head>
<body>
<div data-role="page" id="phone-main-widget">
    <style type="text/css">
        .more-link {
            color: #F00;
            text-align: center;
        }

        .item-active {
            background: #fff9af !important;
        }

        /*.item-no-active {*/
            /*background: #FFF;*/
        /*}*/

        .fade-enter-active, .fade-leave-active {
            transition: opacity .5s
        }
        .fade-enter, .fade-leave-active {
            opacity: 0
        }

    </style>
    <script type="text/javascript">

        var list = new Array();

        $(document).on('pageshow', '#phone-main-widget', function() {

            $("#phone-datalist-widget").on('filterablebeforefilter', function (e, data ) {
                vm.resetData();
            });
        });

        var temp;
        var vm = new Vue({
            el: '#app',
            data: {
                items: list,
                more: 'more-link',
                page: 0,
                rows: 10,
                total: 0,
                all: false      // 数据是否已全部显示
            },
            created:function(){
                this.$nextTick(function () {
                    setTimeout(function () {
                        $('#phone-datalist-widget').listview('refresh');
                    }, 0);
                });
                this.showMore();
            },
            methods: {
                showMore: function () {

                    if(this.items.length > 0 && this.items) {
                        this.items.pop();
                    }

                    this.page = this.page + 1;
                    var temp = this;

                    $.get('system/referWindow/getReferWindowData.do', {
                        id:'${wid }',
                        paramRule: null,
                        col:'${column.idvalue}',
                        colname:'${column.namevalue}',
                        content: $('#phone-keyword-widget').val(),
                        page: this.page,
                        rows: 10
                    }, function(data){

                        data = $.parseJSON(data);
                        if(data == null) {
                            return true;
                        }
                        temp.total = temp.total + data.rows.length;
                        if(temp.total < data.total) {
                            temp.all = false;
                        } else {
                            temp.all = true;
                        }

                        temp.pushData(data.rows);
                    })
                },
                resetData: function () {
                    this.clearData();
                    this.showMore();
                },
                pushData: function (thisDataList) {

                    list = list.concat(thisDataList);
                    for(var i in thisDataList) {
                        thisDataList[i].active = false;
                        this.items.push(thisDataList[i]);
                    }
                    if(!this.all) {
                        this.items.push({
                            more: true,
                            show: true,
                            active: false
                        });
                    }

                    this.$nextTick(function () {
                        setTimeout(function () {
                            $('#phone-datalist-widget').listview('refresh');
                        }, 0);
                    });
                    return true;
                },
                clearData: function () {
                    this.items = new Array();
                    this.page = 0;
                    this.total = 0;
                    this.all = true;
                    return true;
                },
                clickHander: function (event) {

                    var el = event.currentTarget;
                    if($(el).index() >= this.total) {
                        this.showMore();
                        return ;
                    }

                    var index = $(el).index();
                    var data = this.items[index];

                    <c:if test="${param.checkType eq 2}">
                        data.active = !data.active;
                        return true;
                    </c:if>

                    $('#phone-back-widget').trigger('click');
                    $(document).off('pagehide','#phone-main-widget').on('pagehide','#phone-main-widget',function(){
                        eval('${param.callback }(data)');
                    });
                    return true;
                }
            }
        });

        /**
         *
         * @returns {boolean}
         */
        function retMultiData() {

            var list = new Array();
            for(var i in vm.items) {
                if(vm.items[i].active) {
                    list.push(vm.items[i]);
                }
            }
            $('#phone-back-widget').trigger('click');
            $(document).off('pagehide','#phone-main-widget').on('pagehide','#phone-main-widget',function(){
                eval('${param.callback }(list)');
            });
            return true;
        }

    </script>
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1 id="phone-h1-widget">${wname }</h1>
        <input id="phone-keyword-widget" data-type="search" placeholder="输入关键字进行查询">
        <a data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;" id="phone-back-widget">返回</a>
        <c:if test="${param.checkType eq 2}">
            <a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-check" style="border: 0px; background: #E9E9E9;" onclick="javascript:retMultiData();">确定</a>
        </c:if>
    </div>
    <div role="main" class="ui-content" id="app">
        <ul data-role="listview" data-inset="false" id="phone-datalist-widget" data-filter="true" data-input="#phone-keyword-widget">
            <li v-for="item in items" v-on:click="clickHander($event)" v-bind:class="{ 'item-active': item.active}" class="ui-li-static ui-body-inherit">
                <div v-if="item.more" v-bind:class="[more]" v-show="item.show">
                    加载更多...
                </div>
                <div v-else>
                    <c:forEach items="${columnList }" var="column">
                        <c:choose>
                            <c:when test="${column.isquote eq 1}">
                                <p style="font-weight: bold;">{{item.${column.col} }}</p>
                            </c:when>
                            <c:when test="${column.isquote eq 2}">
                                <h2>{{item.${column.col} }}</h2>
                            </c:when>
                            <c:when test="${column.isquote eq 3}">
                                <h2>${column.colname}：{{item.${column.col} }}</h2>
                            </c:when>
                            <c:when test="${column.isquote eq 6}">
                                <p>${column.colname}：{{item.${column.col} }}</p>
                            </c:when>
                            <c:when test="${column.isquote eq 0}">
                                <p>${column.colname}：{{item.${column.col} }}</p>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
