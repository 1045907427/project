package com.hd.agent.basefiles.model;

/**
 * Created by luoq on 2017/11/21.
 */
public class StorageItemGoods {
    /**
     * 编码
     */
    private int id;

    /**
     * 货位编号
     */
    private String itemno;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 仓库编码
     */
    private String storageid;

    /**
     * 仓库名称
     * @return
     */
    private String storagename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }
}
