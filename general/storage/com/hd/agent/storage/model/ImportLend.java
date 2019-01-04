package com.hd.agent.storage.model;

/**
 * Created by chenwei on 2017/10/31.
 */
public class ImportLend {
    /**
     * 出入库仓库编码
     */
    private String storageid;
    /**
     *借货还货人编码
     */
    private String lendid;
    /**
     * 借货还货人类型
     */
    private String lendtype;


    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getLendid() {
        return lendid;
    }

    public void setLendid(String lendid) {
        this.lendid = lendid;
    }

    public String getLendtype() {
        return lendtype;
    }

    public void setLendtype(String lendtype) {
        this.lendtype = lendtype;
    }
}
