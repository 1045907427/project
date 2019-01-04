$.fn.datagrid.defaults.data = [{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
var isLoadData = false;
$.fn.datagrid.defaults.onBeforeLoad=function(){
    $(this).datagrid('clearChecked');
    $(this).datagrid('clearSelections');
    //初始化时不能查询数据，设置200毫秒时间后可以查询数据
    if(!isLoadData){
        setTimeout(function(){
            isLoadData = true;
        },100);
    }
    return isLoadData;
};