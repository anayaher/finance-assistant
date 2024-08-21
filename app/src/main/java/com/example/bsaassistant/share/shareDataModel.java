package com.example.bsaassistant.share;

public class shareDataModel {

    public  shareDataModel(){

    }
    public String getPatPedhiName() {
        return patPedhiName;
    }

    public void setPatPedhiName(String patPedhiName) {
        this.patPedhiName = patPedhiName;
    }

    public Integer getEmi() {
        return emi;
    }

    public void setEmi(Integer emi) {
        this.emi = emi;
    }

    public Integer getPrevBal() {
        return prevBal;
    }

    public void setPrevBal(Integer prevBal) {
        this.prevBal = prevBal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    String patPedhiName;
    Integer emi;
    Integer prevBal;
    Integer status;

    public shareDataModel(String patPedhiName, Integer emi, Integer prevBal, Integer status) {
        this.patPedhiName = patPedhiName;
        this.emi = emi;
        this.prevBal = prevBal;
        this.status = status;
    }
}
