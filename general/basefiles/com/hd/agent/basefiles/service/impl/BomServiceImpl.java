package com.hd.agent.basefiles.service.impl;

import com.hd.agent.basefiles.dao.BomMapper;
import com.hd.agent.basefiles.model.Bom;
import com.hd.agent.basefiles.model.BomDetail;
import com.hd.agent.basefiles.service.IBomService;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Bom Service实现类
 * @author limin
 * @date Sep 21, 2015
 */
public class BomServiceImpl extends BaseFilesServiceImpl implements IBomService {

    /**
     * bom mapper
     */
    private BomMapper bomMapper;

    public BomMapper getBomMapper() {
        return bomMapper;
    }

    public void setBomMapper(BomMapper bomMapper) {
        this.bomMapper = bomMapper;
    }

    @Override
    public PageData selectBomList(PageMap pageMap) throws Exception {

        List list = bomMapper.selectBomList(pageMap);
        int count = bomMapper.selectBomListCount(pageMap);

        PageData data = new PageData(count, list, pageMap);
        return data;
    }

    @Override
    public int addBom(Bom bom, List<BomDetail> list) {

        int ret = bomMapper.insertBom(bom);

        for(BomDetail detail : list) {

            if(StringUtils.isEmpty(detail.getGoodsid())) {

                continue;
            }

            detail.setBillid(bom.getId());
            bomMapper.insertBomDetail(detail);
        }

        return ret;
    }

    @Override
    public int editBom(Bom bom, List<BomDetail> list) {

        int ret = bomMapper.updateBom(bom);

        bomMapper.deleteBomDetail(bom.getId());
        for(BomDetail detail : list) {

            if(StringUtils.isEmpty(detail.getGoodsid())) {

                continue;
            }

            detail.setBillid(bom.getId());
            bomMapper.insertBomDetail(detail);
        }

        return ret;
    }

    @Override
    public Bom selectBom(String id) {

        return bomMapper.selectBom(id);
    }

    @Override
    public PageData selectBomDetailList(PageMap pageMap) {
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        List<Map> list = bomMapper.selectBomDetailList(pageMap);
        for(Map map : list){
            BigDecimal unitnum = ((BigDecimal)map.get("unitnum")).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
            BigDecimal auxnum = ((BigDecimal)map.get("auxnum")).setScale(0,BigDecimal.ROUND_DOWN);
            BigDecimal auxremainder = ((BigDecimal)map.get("auxremainder")).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
            map.put("unitnum",unitnum);
            map.put("auxnum",auxnum);
            map.put("auxremainder",auxremainder);
        }
        int count = bomMapper.selectBomDetailListCount(pageMap);

        PageData data = new PageData(count, list, pageMap);
        return data;
    }

    @Override
    public int updateBomStatus(Bom bom) {

        return bomMapper.updateBom(bom);
    }

    @Override
    public int deleteBom(String id) {

        bomMapper.deleteBomDetail(id);
        return bomMapper.deleteBom(id);
    }
}
