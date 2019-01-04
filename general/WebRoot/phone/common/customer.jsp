<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2017/8/17
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户</title>
</head>
<body>
<div data-role="page" id="phone-main-customer">
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
        console.debug('for debug');

        $(document).on('pageshow', '#phone-main-customer', function() {

            $("#phone-datalist-customer").on('filterablebeforefilter', function (e, data ) {
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
                        $('#phone-datalist-customer').listview('refresh');
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

                    $.get('basefiles/getCustomerSelectListData.do', {
                        paramRule: null,
                        ishead: '${param.ishead}',
                        isopen: '${param.isopen}',
                        isdatasql: '${param.isdatasql}',
                        id: $('#phone-keyword-customer').val(),
                        page: this.page,
                        rows: 10
                    }, function(data){

                        data = $.parseJSON(data);
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
                            $('#phone-datalist-customer').listview('refresh');
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

                    $('#phone-back-customer').trigger('click');
                    $(document).off('pagehide','#phone-main-customer').on('pagehide','#phone-main-customer',function(){
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
            $('#phone-back-customer').trigger('click');
            $(document).off('pagehide','#phone-main-customer').on('pagehide','#phone-main-customer',function(){
                eval('${param.callback }(list)');
            });
            return true;
        }

    </script>
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1 id="phone-h1-customer">客户列表</h1>
        <input id="phone-keyword-customer" data-type="search" placeholder="输入关键字进行查询">
        <a data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;" id="phone-back-customer">返回</a>
        <c:if test="${param.checkType eq 2}">
            <a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-check" style="border: 0px; background: #E9E9E9;" onclick="javascript:retMultiData();">确定</a>
        </c:if>
    </div>
    <div role="main" class="ui-content" id="app">
        <ul data-role="listview" data-inset="false" id="phone-datalist-customer" data-filter="true" data-input="#phone-keyword-customer">
            <li v-for="item in items" v-on:click="clickHander($event)" v-bind:class="{ 'item-active': item.active}" class="ui-li-static ui-body-inherit">
                <div v-if="item.more" v-bind:class="[more]" v-show="item.show">
                    加载更多...
                </div>
                <div v-else>
                    <p style="font-weight: bold;">{{item.id }}</p>
                    <h2>{{item.name }}</h2>
                    <p>{{item.shortcode }}</p>
                    <p>地址：{{item.address }}</p>
                    <p>客户业务员：{{item.salesusername }}</p>
                    <p>销售区域：{{item.salesareaname }}</p>
                    <p>客户分类：{{item.customersortname }}</p>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
