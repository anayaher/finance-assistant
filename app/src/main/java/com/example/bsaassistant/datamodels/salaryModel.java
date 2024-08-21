package com.example.bsaassistant.datamodels;

import androidx.annotation.NonNull;

public class salaryModel {
    String date,month;
    Integer basic,hra,da,ta,other,salHeadTotal,it,pt,gpf,janse,vidya,dedOther,dedHeadTotal,payInHand;

    public salaryModel(String date, String month, Integer basic, Integer hra, Integer da, Integer ta, Integer other, Integer salHeadTotal, Integer it, Integer pt, Integer gpf, Integer janse, Integer vidya, Integer dedOther, Integer dedHeadTotal, Integer payInHand) {
        this.date = date;
        this.month = month;
        this.basic = basic;
        this.hra = hra;
        this.da = da;
        this.ta = ta;
        this.other = other;
        this.salHeadTotal = salHeadTotal;
        this.it = it;
        this.pt = pt;
        this.gpf = gpf;
        this.janse = janse;
        this.vidya = vidya;
        this.dedOther = dedOther;
        this.dedHeadTotal = dedHeadTotal;
        this.payInHand = payInHand;
    }

    public salaryModel() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getBasic() {
        return basic;
    }

    public void setBasic(Integer basic) {
        this.basic = basic;
    }

    public Integer getHra() {
        return hra;
    }

    public void setHra(Integer hra) {
        this.hra = hra;
    }

    public Integer getDa() {
        return da;
    }

    public void setDa(Integer da) {
        this.da = da;
    }

    public Integer getTa() {
        return ta;
    }

    public void setTa(Integer ta) {
        this.ta = ta;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public Integer getSalHeadTotal() {
        return salHeadTotal;
    }

    public void setSalHeadTotal(Integer salHeadTotal) {
        this.salHeadTotal = salHeadTotal;
    }

    public Integer getIt() {
        return it;
    }

    public void setIt(Integer it) {
        this.it = it;
    }

    public Integer getPt() {
        return pt;
    }

    public void setPt(Integer pt) {
        this.pt = pt;
    }

    public Integer getGpf() {
        return gpf;
    }

    public void setGpf(Integer gpf) {
        this.gpf = gpf;
    }

    public Integer getJanse() {
        return janse;
    }

    public void setJanse(Integer janse) {
        this.janse = janse;
    }

    public Integer getVidya() {
        return vidya;
    }

    public void setVidya(Integer vidya) {
        this.vidya = vidya;
    }

    public Integer getDedOther() {
        return dedOther;
    }

    public void setDedOther(Integer dedOther) {
        this.dedOther = dedOther;
    }

    public Integer getDedHeadTotal() {
        return dedHeadTotal;
    }

    public void setDedHeadTotal(Integer dedHeadTotal) {
        this.dedHeadTotal = dedHeadTotal;
    }

    public Integer getPayInHand() {
        return payInHand;
    }

    public void setPayInHand(Integer payInHand) {
        this.payInHand = payInHand;
    }

    @NonNull
    @Override
    public String toString() {
        return "salaryModel{" +
                "date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", basic=" + basic +
                ", hra=" + hra +
                ", da=" + da +
                ", ta=" + ta +
                ", other=" + other +
                ", salHeadTotal=" + salHeadTotal +
                ", it=" + it +
                ", pt=" + pt +
                ", gpf=" + gpf +
                ", janse=" + janse +
                ", vidya=" + vidya +
                ", dedOther=" + dedOther +
                ", dedHeadTotal=" + dedHeadTotal +
                ", payInHand=" + payInHand +
                '}';
    }
}
