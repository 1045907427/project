<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>库存树状报表</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+path+"/";
        String easyuiThemeName = "default2";
    %>
    <base id="basePath" href="<%=basePath%>"/>
    <script type="text/javascript" src="js/jquery-1.8.3.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/jqueryUtils-ledger.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/jquery.form.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/json2.js" charset="UTF-8"></script>
    <%--<script src="https://cdn.jsdelivr.net/npm/vue"></script>--%>
    <%--<script type="text/javascript" src="../js/vue.element.js" charset="UTF-8"></script>--%>

    <script type="text/javascript" src="js/vue.js" charset="UTF-8"></script>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script src="js/selecttree.js"></script>
    <style type="text/css">
        html,body{
            height: 100%;
            padding: 0px;
            margin: 0px;
        }
        .big-body{
            height: 100%;
        }
        .top-nav{
            height: 260px;
            background: #fff;
            text-align: center;
            /*line-height: 150px;*/
            font-size: 30px;
            position: relative;
            z-index: 10;
        }
        .main-body{
            height: 100%;
            /*background:#d6bef1;*/
            margin-top: -260px;
            position: relative;
            z-index: 5;
            font-size: 30px;
            line-height: 80px;
            overflow:auto
        }
        .main-body-bd{
            padding-top: 260px;
            padding-left: 20px;
        }

        .form_label{
            font-size: 14px;
        }
        .el-row {
            margin-bottom: 10px;
        }
        .demo-table-expand {
            font-size: 0;
        }
        .demo-table-expand label {
            width: 90px;
            color: #99a9bf;
        }
        .demo-table-expand .el-form-item {
            margin-right: 0;
            margin-bottom: 0;
            width: 50%;
        }

        body,table{
            font-size:12px;
        }
        table{
            table-layout:fixed;
            empty-cells:show;
            border-collapse: collapse;
            margin:0 auto;
        }
        td{
            height:20px;
        }
        h1,h2,h3{
            font-size:12px;
            margin:0;
            padding:0;
        }

        .title { background: #FFF; border: 1px solid #9DB3C5; padding: 1px; width:90%;margin:20px auto; }
        .title h1 { line-height: 31px; text-align:center;  background: #2F589C url(image/table/th_bg2.gif); background-repeat: repeat-x; background-position: 0 0; color: #FFF; }
        .title th, .title td { border: 1px solid #CAD9EA; padding: 5px; }



        table.t1{
            table-layout:fixed;
            margin: auto;
            border:1px solid #cad9ea;
            color:#666;
            width: ;
        }
        table.t1 th {
            background-image: url(image/table/th_bg1.gif);
            background-repeat::repeat-x;
            height:30px;
        }
        table.t1 td,table.t1 th{
            border:1px solid #cad9ea;
            padding:0 1em 0;
            overflow:hidden;
            white-space:nowrap;
        }
        table.t1 tr.a1{
            background-color:#f5fafe;
        }

        .tree-folder {
            display: inline-block;
            width: 16px;
            height: 18px;
            vertical-align: top;
            overflow: hidden;
            background: url(js/themes/default2/images/tree_icons.png) no-repeat -208px 0;
        }
        .tree-folder-open {
            display: inline-block;
            width: 16px;
            height: 18px;
            vertical-align: top;
            overflow: hidden;
            background: url(js/themes/default2/images/tree_icons.png) no-repeat -224px 0;
        }
        .tree1{
            padding-left:15px !important;
        }
        .tree2{
            padding-left:30px !important;
        }
        .tree3{
            padding-left:45px !important;
        }
        .tree4{
            padding-left:60px !important;
        }
        .td1{
            width: 300px;
        }
        .td2{
            width: 100px;
        }
        .ms-tree-space {
            position: relative;
            top: 1px;
            display: inline-block;
            font-style: normal;
            font-weight: 400;
            line-height: 1;
            width: 12px;
            height: 14px;
        }
        .tree-table-button {
            padding: 0;
        }
        .ms-tree-space::before {
            content: '';
        }
        .cell{
            height: 22px;
        }
        .fd-select-dropdown{
            position: absolute;
            background: rgb(255, 255, 255);
            z-index: 1000;
            width: 100%;
            border: 1px solid #EFEFEF;
        }
        .el-tag{
            float: left;
            float: left;
        }
        .el-input__inner{
            height: 28px !important;
        }
        /*.el-select__input{*/
            /*display: none;*/
        /*}*/
    </style>
</head>
<body>

<div class="big-body" id="content" >
    <div class="top-nav">
        <div style="padding-left: 10px">
            <el-row style="padding-top: 5px;margin-bottom: 0px">
                <el-col :span="24" >
                    <el-button type="primary" @click="exportList"   size="mini" style="float: left">导出</el-button>
                </el-col>
            </el-row>
        </div>
        <div style="padding-left: 10px">
            <form  action="" method="post" id="report-form-exportAnalysPage">
                <input type="hidden"   name="goodsid"  v-model="goodsidCache"/>
                <input type="hidden"   name="goodssort"  v-model="goodssortCache"/>
                <input type="hidden"   name="existingnum"  v-model="existingnumCache"/>
                <input type="hidden"   name="supplierid"  v-model="supplieridCache"/>
                <input type="hidden"   name="brandid"  v-model="brandidCache"/>
                <input type="hidden"   name="state"  v-model="stateCache"/>
                <input type="hidden"   name="storageid"  v-model="storageidCache"/>
                <input type="hidden"   name="bstype"  v-model="bstypeCache"/>
                <input type="hidden"   name="treetype"  v-model="treetypeCache"/>
                <input type="hidden"   name="columns" id="hidden-columns"/>
            </form>
            <el-row>
                <el-col :span="24" style="text-align: left">
                    <span class="form_label">商品名称:</span>
                    <template>
                        <el-select v-model="form.goodsid"  :filter-method="goodsidQuery"   v-on:focus="goodsidFocus"    clearable   multiple  collapse-tags  filterable  placeholder="" style="width: 480px"  size="mini" >
                            <el-option
                                    v-for="goodsidoption in goodsidListOptions"
                                    :key="goodsidoption.id"
                                    :label="goodsidoption.name"
                                    :value="goodsidoption.id">
                                <span class="el-select-span1">{{ goodsidoption.id }}</span>
                                <span class="el-select-span2">{{ goodsidoption.name }}</span>
                            </el-option>
                        </el-select>
                    </template>
                    <span class="form_label">商品分类:</span>
                    <template>
                        <select-tree v-model="form.goodssort" :filter-method="goodssortQuery"   v-on:focus="goodssortFocus" clearable  :data-list="goodssortListOptions" :multiple="true" size="mini"  collapse-tags style="width: 200px;height:28px"></select-tree>
                        <%--<el-select v-model="form.goodssort"  :filter-method="goodssortQuery"   v-on:focus="goodssortFocus"    clearable  multiple  collapse-tags filterable  placeholder="" style="width: 200px"  size="mini" >--%>
                            <%--<el-option--%>
                                    <%--v-for="goodssortoption in goodssortListOptions"--%>
                                    <%--:key="goodssortoption.id"--%>
                                    <%--:label="goodssortoption.name"--%>
                                    <%--:value="goodssortoption.id">--%>
                                <%--<span class="el-select-span1">{{ goodssortoption.id }}</span>--%>
                                <%--<span class="el-select-span2">{{ goodssortoption.name }}</span>--%>
                            <%--</el-option>--%>
                        <%--</el-select>--%>
                    </template>
                </el-col>
            </el-row>
            <el-row>

                <el-col :span="24" style="text-align: left">
                    <span class="form_label">供&nbsp&nbsp应&nbsp商:</span>
                    <template>
                        <el-select v-model="form.supplierid"  :filter-method="supplieridQuery"   v-on:focus="supplieridFocus"    clearable  multiple  collapse-tags filterable  placeholder="" style="width: 480px"  size="mini" >
                            <el-option
                                    v-for="supplieridoption in supplieridListOptions"
                                    :key="supplieridoption.id"
                                    :label="supplieridoption.name"
                                    :value="supplieridoption.id">
                                <span class="el-select-span1">{{ supplieridoption.id }}</span>
                                <span class="el-select-span2">{{ supplieridoption.name }}</span>
                            </el-option>
                        </el-select>
                    </template>
                    <span class="form_label">仓&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp库:</span>
                    <template>
                        <el-select v-model="form.storageid"  :filter-method="storageidQuery"   v-on:focus="storageidFocus"    clearable  multiple  collapse-tags filterable  placeholder="" style="width: 200px"  size="mini" >
                            <el-option
                                    v-for="storageidoption in storageidListOptions"
                                    :key="storageidoption.id"
                                    :label="storageidoption.name"
                                    :value="storageidoption.id">
                                <span class="el-select-span1">{{ storageidoption.id }}</span>
                                <span class="el-select-span2">{{ storageidoption.name }}</span>
                            </el-option>
                        </el-select>
                    </template>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24" style="text-align: left">
                    <span class="form_label">品牌名称:</span>
                    <template>
                        <el-select v-model="form.brandid"  :filter-method="brandidQuery"   v-on:focus="brandidFocus"    clearable   multiple  collapse-tags filterable  placeholder="" style="width: 200px"  size="mini" >
                            <el-option
                                    v-for="brandidoption in brandidListOptions"
                                    :key="brandidoption.id"
                                    :label="brandidoption.name"
                                    :value="brandidoption.id">
                                <span class="el-select-span1">{{ brandidoption.id }}</span>
                                <span class="el-select-span2">{{ brandidoption.name }}</span>
                            </el-option>
                        </el-select>
                    </template>
                    <span class="form_label">汇总类型:</span>
                    <template>
                        <el-select v-model="form.treetype"   filterable  placeholder="" style="width: 200px"  size="mini" >
                            <el-option
                                    v-for="treetypeoption in treetypeListOptions"
                                    :key="treetypeoption.value"
                                    :label="treetypeoption.label"
                                    :value="treetypeoption.value">
                            </el-option>
                        </el-select>
                    </template>
                    <span class="form_label">购销类型:</span>
                    <template>
                        <el-select v-model="form.bstype"   filterable  placeholder="" style="width: 200px"  size="mini" clearable>
                            <el-option
                                    v-for="bstypeoption in bstypeListOptions"
                                    :key="bstypeoption.code"
                                    :label="bstypeoption.codename"
                                    :value="bstypeoption.code">
                            </el-option>
                        </el-select>
                    </template>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24" style="text-align: left">
                    <span class="form_label">状&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp态:</span>
                    <template>
                        <el-select v-model="form.state"   filterable  placeholder="" style="width: 200px"  size="mini" clearable>
                            <el-option
                                    v-for="stateoption in stateListOptions"
                                    :key="stateoption.value"
                                    :label="stateoption.label"
                                    :value="stateoption.value">
                            </el-option>
                        </el-select>
                    </template>
                    <template>
                        <el-checkbox v-model="form.existingnum" >剔除库存为0的商品显示</el-checkbox>
                    </template>
                    <el-button type="primary" @click="query"  size="mini" style="margin-left: 100px">查询</el-button>
                    <el-button @click="reload"  size="mini">重置</el-button>
                </el-col>
            </el-row>
        </div>
    </div>
    <div class="main-body" id="main-body" v-loading="loading">
        <div class="main-body-bd" >
            <tree-table :data-source="tabledetail" :columns="columns" :table-height="tableHeight" :tree-structure="true" border ></tree-table>
        </div>
    </div>
</div>

<script type="text/x-template" id="treetableTemplate">
    <el-table ref="table" :data="data" border style="width:100%"   size="mini" highlight-current-row @row-click="rowClick" @row-dblclick="showLog"  :height="tableHeight" >
        <el-table-column v-for="(item, index) in columns" :key="item.dataIndex" :label="item.title" :width="item.width"  :fixed="item.fixed" :align="item.align" :show-overflow-tooltip="item.tooltip" :sortable="item.sortable">
            <template slot-scope="scope" v-if="showTrIf(scope)">
                <span v-if="spaceIconShow(index)" v-for="space in scope.row._level" class="ms-tree-space" :key="space"></span>
                <button class="tree-table-button" v-if="toggleIconShow(index,scope.row)" @click="toggle(scope.row._index)">
                    <i v-if="scope.row.state === 'closed'" class="el-icon-caret-right" aria-hidden="true"></i>
                    <i v-if="scope.row.state === 'opened'" class="el-icon-caret-bottom" aria-hidden="true"></i>
                </button>
                <span v-else-if="index===0" class="ms-tree-space"></span>
                <span v-if="item.formatterMoney">{{formatterMoney(item.formatter ? item.formatter(scope.row, null, scope.row[item.field]) : scope.row[item.field])}}</span>
                <span v-else>{{item.formatter ? item.formatter(scope.row, null, scope.row[item.field]) : scope.row[item.field]}}</span>
            </template>
        </el-table-column>
        <slot name="tableRight" slot-scope="scope"></slot>
    </el-table>
</script>
<script type="text/javascript">
    var tableHeight=(document.getElementById("main-body").offsetHeight-260).toString();
    var goodsidReferid = "RL_T_BASE_GOODS_INFO";
    var goodssortReferid = "RL_T_BASE_GOODS_WARESCLASS";
    var brandidReferid = "RL_T_BASE_GOODS_BRAND";
    var storageidReferid = "RL_T_BASE_STORAGE_INFO";
    var supplieridReferid = "RL_T_BASE_BUY_SUPPLIER";


    var goodsidCache='';
    var goodssortCache='';
    var existingnumCache=true;
    var supplieridCache='';
    var brandidCache='';
    var stateCache='';
    var storageidCache='';
    var bstypeCache='';
    var treetypeCache='allstorage';

    Vue.component('treeTable', {
        template: "#treetableTemplate",
        props: {
            // 该属性是确认父组件传过来的数据是否已经是树形结构了，如果是，则不需要进行树形格式化
            treeStructure: {
                type: Boolean,
                default: function _default() {
                    return false;
                }
            },
            // 这是相应的字段展示
            columns: {
                type: Array,
                default: function _default() {
                    return [];
                }
            },
            // 这是数据源
            dataSource: {
                type: Array,
                default: function _default() {
                    return [];
                }
            },
            // 这个是是否展示操作列
            treeType: {
                type: String,
                default: function _default() {
                    return 'normal';
                }
            },
            // 是否默认展开所有树
            defaultExpandAll: {
                type: Boolean,
                default: function _default() {
                    return false;
                }
            },
            // 表格的高
            tableHeight: {
                type: String,
                default: function _default() {
                    return '';
                }
            },
        },

        data:function() {
            return {
                dataCache:[],
                do:true,
            };
        },

        computed: {
            // 格式化数据源
            data: function data() {
                var me = this;
                var do1 = me.do;
//                if(me.dataCache.length==0){
                    if (me.treeStructure) {
                        var data = this.markData(me.dataSource, null, null);
                        me.dataCache = data;
                    }
//                }
                return me.dataToTreeData();
            },
        },
        methods: {
            dataToTreeData:function () {
                var me = this;
                var temp = [];
                var dataCache=me.dataCache;
                var _index = 0;
                dataCache.forEach(function (record) {
                    if(record.pid=="" || record._parent.state=="opened"){
                        me.$set(record, '_index', _index);
                        temp.push(record);
                    }
                    _index++;
                });
                return temp;
            },
            markData: function markData(data, parent, level) {
                var _this = this;

                var temp = [];
                if (data) {
                    data.forEach(function (record) {
                        if (parent) {
                            _this.$set(record, '_parent', parent);
                        }
                        var _level = 0;
                        if (level !== undefined && level !== null) {
                            _level = level + 1;
                        }
                        _this.$set(record, '_level', _level);
                        temp.push(record);
                        if (record.children && record.children.length > 0) {
                            var children = _this.markData(record.children, record, _level);
                            temp = temp.concat(children);
                        }
                    });
                }
                return temp;
            },

            // 显示行
            showTr: function showTr(_ref) {
                var row = _ref.row,
                    index = _ref.index;
                var show = row._parent ? row._parent.state === 'opened' && row._parent._show : true;
                // this.$set(row, '_show', show)
                row._show = show;
                return show ? '' : 'display:none';
            },
            // 显示行
            showTrIf: function showTr(_ref) {
                var row = _ref.row,
                    index = _ref.index;
                var show = row._parent ? row._parent.state === 'opened' && row._parent._show : true;
                // this.$set(row, '_show', show)
                row._show = show;
                return show ;
            },

            // 展开下级
            toggle: function toggle(trIndex) {
                var me = this;
                var record = me.dataCache[trIndex];
                if(record.state == "opened"){
                    record.state = "closed";
                    me.closeChilren(trIndex)
                }else if(record.state == "closed"){
                    record.state = "opened";
                }
                if(record.datatype === 'brandid' && !record.children){
                    detailTable.startloading();
                    $.ajax({
                        url:'report/storage/showStorageTreeReportListDataByVue.do',
                        dataType:'json',
                        type:'post',
                        async: true,
                        data:{
                            goodsid:goodsidCache,
                            goodssort:goodssortCache,
                            existingnum:existingnumCache,
                            supplierid:supplieridCache,
                            brandid:brandidCache,
                            state:stateCache,
                            storageid:storageidCache,
                            bstype:bstypeCache,
                            treetype:treetypeCache,
                            expandid:record.data,
                            expandpid:record.pid,
                            isexpand:'isexpand'
                        },
                        success:function(json){
                            var index = trIndex;
                            me.dataCache[trIndex].children = json.list;
                            json.list.forEach(function (record) {
                                index++;
                                me.$set(record, '_parent', me.dataCache[trIndex]);
                                me.$set(record, '_level', me.dataCache[trIndex]._level+1);
                                me.dataCache.splice(index,0,record);
                            });
                            detailTable.endloading();
                        }
                    });
                }
                if(me.do){
                    me.do = false;
                }else{
                    me.do = true;
                }
            },
            closeChilren:function (index) {
                var me = this;
                var level = me.dataCache[index]._level;
                index++;
                while (me.dataCache[index]._level>level)
                {
                    me.dataCache[index].state = "closed";
                    index++;
                    if(me.dataCache.length==index){
                        break;
                    }
                }
            },
            // 显示层级关系的空格和图标
            spaceIconShow: function spaceIconShow(index) {
                var me = this;
                if (me.treeStructure && index === 0) {
                    return true;
                }
                return false;
            },

            // 点击展开和关闭的时候，图标的切换
            toggleIconShow: function toggleIconShow(index, record) {
                var me = this;
                if (me.treeStructure && index === 0 && ( record.children && record.children.length > 0 || record.datatype === 'brandid') ) {
                    return true;
                }
                return false;
            },

            rowClick: function rowClick(row, event, column) {
                this.$emit('row-click', row, event, column);
            },
            showLog: function showLog(row, event, column) {
                console.log(row.datatype)
                if("goodsid"==row.datatype){
                    var url = '';
                    if("supplier"==treetypeCache){
                        url = "storage/showStorageSummaryLogPage.do?goodsid=" + row.goodsid;
                    }else{
                        url = "storage/showStorageSummaryLogPageByStorage.do?goodsid=" + row.goodsid + "&storageid=" + row.storageid;
                    }
                    console.log(url)
                    top.addTab(url, "库存明细查看");
                }
            },
            formatterMoney:function formatterMoney(val,fixed){
                if(typeof(fixed)=="undefined" || fixed==null || fixed == "" || isNaN(fixed) || fixed <0){
                    fixed=2;
                }
                if(val!=null && (val!="" || val===0)){
                    if(Number(val)<0){
                        //return "-"+fNumber((-Number(val))+"",2);
                        var newdata= Number(val).toFixed(fixed);
                        if(newdata==0){
                            //return newdata.toFixed(fixed);
                            return Number(newdata).toFixed(fixed);
                        }
                        return newdata;
                    }else if(Number(val) == 0){
                        return Number(val).toFixed(fixed);
                    }else{
                        //return fNumber(val,2);
                        return Number(val).toFixed(fixed);
                    }
                }else{
                    return "";
                }
            }
        }
    });

    var detailTable = new Vue({
        components: {selectTree: SelectTree.default },
        el: '#content',
        data:function() {
            return {
                value: '3',
                dataList: [],
                tableHeight:tableHeight,
                columns: [
                    {title: '名称',field: 'name',width:'350',isexport:true,fixed:true},
                    {title: '商品编码',field: 'goodsid',width:'100',isexport:false},
                    {title: '助记符',field: 'spell',width:'80',isexport:false},
                    {title: '条形码',field: 'barcode',width:'85',isexport:false},
                    {title: '商品分类',field: 'waresclassname',width:'120',isexport:false},
                    {title: '商品品牌',field: 'brandname',width:'80',isexport:false},
                    {title: '规格型号',field: 'model',width:'100',isexport:false},
                    {title: '箱装量',field: 'boxnum',width:'80',isexport:false},
                    {title: '单位',field: 'unitname',width:'80',isexport:false},
                    {title: '单价',field: 'price',width:'50',isexport:false},
                    {title: '基准价',field: 'basesaleprice',width:'80',isexport:false},
                    {title: '成本价',field: 'costprice',width:'80',isexport:false},
//                    {title: '仓库未分摊金额',field: 'storageamount',width:'100',isexport:true},
                    {title: '现存量',field: 'existingnum',width:'140',align:'right',isexport:true},
                    {title: '现存辅数量',field: 'auxexistingnum',width:'140',formatterMoney:true,align:'right',isexport:true},
                    {title: '现存箱数',field: 'auxexistingdetail',width:'140',align:'right',isexport:true},
                    {title: '现存金额',field: 'existingamount',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '基准金额',field: 'basesaleamount',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '成本金额',field: 'costamount',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '可用量',field: 'usablenum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '可用箱数',field: 'auxusabledetail',width:'150',align:'right',isexport:true},
                    {title: '可用金额',field: 'usableamount',width:'150',align:'right',formatterMoney:true,isexport:true},
                    {title: '待发量',field: 'waitnum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '待发箱数',field: 'auxwaitdetail',width:'150',align:'right',isexport:true},
                    {title: '待发金额',field: 'waitamount',width:'150',align:'right',formatterMoney:true,isexport:true},
                    {title: '在途量',field: 'transitnum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '在途箱数',field: 'auxtransitdetail',width:'150',align:'right',isexport:true},
                    {title: '调拨待发量',field: 'allotwaitnum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '调拨待发箱数',field: 'auxallotwaitdetail',width:'150',align:'right',isexport:true},
                    {title: '调拨待入量',field: 'allotenternum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '调拨待入箱数',field: 'auxallotenterdetail',width:'150',align:'right',isexport:true},
                    {title: '预计可用量',field: 'projectedusablenum',width:'140',align:'right',formatterMoney:true,isexport:true},
                    {title: '预计可用量箱数',field: 'auxprojectedusabledetail',width:'150',align:'right',isexport:true}
//                    {title: '安全库存',field: 'safenum',width:'140',align:'right',isexport:true},
//                    {title: '安全箱数',field: 'auxsafedetail',width:'150',align:'right',isexport:true}
                ],
                form: {
                    goodsid: '',
                    goodssort: '',
                    existingnum: true,
                    supplierid: '',
                    brandid: '',
                    state: '',
                    storageid: '',
                    bstype: '',
                    treetype: 'allstorage'
                },

                goodsidListOptionsCache: '',
                goodsidListOptions: '',
                goodssortListOptionsCache: '',
                goodssortListOptions: [],
                brandidListOptionsCache: '',
                brandidListOptions: '',
                storageidListOptionsCache: '',
                storageidListOptions: '',
                supplieridListOptionsCache: '',
                supplieridListOptions: '',
                bstypeListOptions: JSON.parse('${bstypeList }'),
                treetypeListOptions: [{value: 'allstorage',label: '总仓库-分仓库-品牌-商品'}, {value: 'storage', label: '仓库-品牌-商品'}, {value: 'supplier', label: '供应商-品牌-商品'}],
                stateListOptions: [{value: '0',label: '禁用'}, {value: '1', label: '启用'}],

                tabledetail: [],

                goodsidCache: '',
                goodssortCache:'',
                existingnumCache:true,
                supplieridCache:'',
                brandidCache:'',
                stateCache:'',
                storageidCache:'',
                bstypeCache:'',
                treetypeCache:'allstorage',

                loading:false,
            }
        },
        methods:{
            goodsidFocus:function () {
                this.getGoodsidOption(this.form.goodsid);
            },
            goodsidQuery:function(val){
                this.getGoodsidOption(val);
            },
            getGoodsidOption:function (val) {
                var listStr="";
                if(val == undefined){
                    val="";
                }
                $.ajax({
                    url:'basefiles/getGoodsSelectListDataSimple.do?id='+val,
                    dataType:'json',
                    type:'post',
                    async: false,
                    success:function(json){
                        listStr=  json.listStr;
                    }
                });
                this.$nextTick(function(){
                    this.goodsidListOptions = listStr;
                })
            },
            goodssortFocus:function () {
                console.log(111)
                if(this.goodssortListOptionsCache == ""){
                    var listStr=getWidgetDataByReferid(goodssortReferid);
                    this.goodssortListOptionsCache = listStr;
                    this.goodssortListOptions = listStr;
                }

            },
            goodssortQuery:function(val){
                if (val) {
                    this.goodssortListOptions = this.goodssortListOptionsCache.filter((item) => {
                            if (!!~item.name.indexOf(val) || !!~item.id.indexOf(val)) {
                        return true
                    }
                })
                } else {
                    this.goodssortListOptions = this.goodssortListOptionsCache;
                }
            },
            brandidFocus:function () {
                if(this.brandidListOptionsCache == ""){
                    var listStr=getWidgetDataByReferid(brandidReferid);
                    this.brandidListOptionsCache = listStr;
                }
                this.brandidListOptions = this.brandidListOptionsCache;
            },
            brandidQuery:function(val){
                if (val) {
                    this.brandidListOptions = this.brandidListOptionsCache.filter((item) => {
                            if (!!~item.name.indexOf(val) || !!~item.id.indexOf(val)) {
                        return true
                    }
                })
                } else {
                    this.brandidListOptions = this.brandidListOptionsCache;
                }
            },
            storageidFocus:function () {
                if(this.storageidListOptionsCache == ""){
                    var listStr=getWidgetDataByReferid(storageidReferid);
                    this.storageidListOptionsCache = listStr;
                }
                this.storageidListOptions = this.storageidListOptionsCache;
            },
            storageidQuery:function(val){
                if (val) {
                    this.storageidListOptions = this.storageidListOptionsCache.filter((item) => {
                            if (!!~item.name.indexOf(val) || !!~item.id.indexOf(val)) {
                        return true
                    }
                })
                } else {
                    this.storageidListOptions = this.storageidListOptionsCache;
                }
            },
            supplieridFocus:function () {
                if(this.supplieridListOptionsCache == ""){
                    var listStr=getWidgetDataByReferid(supplieridReferid);
                    this.supplieridListOptionsCache = listStr;
                }
                this.supplieridListOptions = this.supplieridListOptionsCache;
            },
            supplieridQuery:function(val){
                if (val) {
                    this.supplieridListOptions = this.supplieridListOptionsCache.filter((item) => {
                            if (!!~item.name.indexOf(val) || !!~item.id.indexOf(val)) {
                        return true
                    }
                })
                } else {
                    this.supplieridListOptions = this.supplieridListOptionsCache;
                }
            },
            exportList:function () {
                this.$confirm('是否导出商品?', '提示', {
                        confirmButtonText: '是',
                        cancelButtonText: '否',
                        type: 'warning'
                    })
                .then(() => {
                    $("#hidden-columns").val(JSON.stringify(this.columns));
                    $("#report-form-exportAnalysPage").attr("action","report/storage/exportStorageTreeReportListDataByVue.do?exportgoods=true");
                    $("#report-form-exportAnalysPage").submit();
                }).catch(() => {
                    $("#hidden-columns").val(JSON.stringify(this.columns));
                    $("#report-form-exportAnalysPage").attr("action","report/storage/exportStorageTreeReportListDataByVue.do?exportgoods=false");
                    $("#report-form-exportAnalysPage").submit();
                });



            },
            query:function(){
                this.loading=true;
                this.$nextTick(function(){
                    this.doquery();
                })
            },
            doquery:function () {
                var listStr="";
                var goodsid = "";
                var goodssort = "";
                var supplierid = "";
                var brandid = "";
                var storageid = "";
                goodsid = idArrToString(this.form.goodsid);
                goodssort = idArrToString(this.form.goodssort);
                supplierid = idArrToString(this.form.supplierid);
                brandid = idArrToString(this.form.brandid);
                storageid = idArrToString(this.form.storageid);

                goodsidCache =  goodsid;
                goodssortCache =  goodssort;
                existingnumCache = this.form.existingnum;
                supplieridCache =  supplierid;
                brandidCache =  brandid;
                stateCache =  this.form.state;
                storageidCache =  storageid;
                bstypeCache =  this.form.bstype;
                treetypeCache = this.form.treetype;

                this.goodsidCache =  goodsidCache;
                this.goodssortCache =  goodssortCache;
                this.existingnumCache = existingnumCache;
                this.supplieridCache =  supplieridCache;
                this.brandidCache =  brandidCache;
                this.stateCache =  stateCache;
                this.storageidCache =  storageidCache;
                this.bstypeCache =  bstypeCache;
                this.treetypeCache = treetypeCache;
                $.ajax({
                    url:'report/storage/showStorageTreeReportListDataByVue.do',
                    dataType:'json',
                    type:'post',
                    async: true,
                    data:{
                        goodsid:goodsidCache,
                        goodssort:goodssortCache,
                        existingnum:existingnumCache,
                        supplierid:supplieridCache,
                        brandid:brandidCache,
                        state:stateCache,
                        storageid:storageidCache,
                        bstype:bstypeCache,
                        treetype:treetypeCache
                    },
                    success:function(json){
                        listStr= json.list;
                        detailTable.end(listStr);
                    }
                });
            },
            end:function (val) {
                this.tabledetail = val;
                this.loading=false;
            },
            startloading:function () {
                this.loading=true;
            },
            endloading:function () {
                this.loading=false;
            },
            reload:function () {
                location.reload();
            },
        }
    })


    $(function () {

    })

    function idArrToString(ids){
        var id=""
        if(ids.length == 0){
            id = "";
        }else {
            for(var i = 0; i < ids.length; i++){
                if(id == ""){
                    id = ids[i];
                }else{
                    id = id + "," + ids[i];
                }
            }
        }
        return id;
    }

    function setWidgetDataByReferidAndData(referid,vueName,data,model,options,optionvalue,sqlformat,paramRule){
        if(window[vueName][options] == ""){
            var listStr=getWidgetDataByReferid(referid,sqlformat,paramRule);
            window[vueName][options] = listStr;
        }
        if(""!=data){
            for(var i = 0 ; i<window[vueName][options].length;i++){
                var  option  = window[vueName][options][i];
                if(data == option[optionvalue]){
                    window[vueName][model]=option;
                }
            }
        }
    }

    function getWidgetDataByReferid(referid,sqlformat,paramRule){
        var listStr = ""
        $.ajax({
            url:'system/referWindow/getWidgetDataByReferid.do',
            dataType:'json',
            type:'post',
            async: false,
            data:{referid:referid,sqlformat:sqlformat,paramRule:paramRule},
            success:function(json){
                listStr=json.listStr;
            }
        });
        return listStr;
    }





    //数字格式金额 默认两位小数
    function formatterMoney(val,fixed){
        if(typeof(fixed)=="undefined" || fixed==null || fixed == "" || isNaN(fixed) || fixed <0){
            fixed=2;
        }
        if(val!=null && (val!="" || val===0)){
            if(Number(val)<0){
                //return "-"+fNumber((-Number(val))+"",2);
                var newdata= Number(val).toFixed(fixed);
                if(newdata==0){
                    //return newdata.toFixed(fixed);
                    return Number(newdata).toFixed(fixed);
                }
                return newdata;
            }else if(Number(val) == 0){
                return Number(val).toFixed(fixed);
            }else{
                //return fNumber(val,2);
                return Number(val).toFixed(fixed);
            }
        }else{
            return "";
        }
    }
</script>

</body>
</html>