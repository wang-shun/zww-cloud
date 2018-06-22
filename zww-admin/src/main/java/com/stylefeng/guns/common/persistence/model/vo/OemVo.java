package com.stylefeng.guns.common.persistence.model.vo;

import com.stylefeng.guns.common.persistence.model.TOem;
import com.stylefeng.guns.common.persistence.model.TOemBanner;

import java.util.List;

public class OemVo{

     TOem oem;

     List<TOemBanner> oemBanner;

    public List<TOemBanner> getOemBanner() {
        return oemBanner;
    }

    public void setOemBanner(List<TOemBanner> oemBanner) {
        this.oemBanner = oemBanner;
    }

    public TOem getOem() {
        return oem;
    }

    public void setOem(TOem oem) {
        this.oem = oem;
    }
}
