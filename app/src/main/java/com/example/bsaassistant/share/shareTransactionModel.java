package com.example.bsaassistant.share;

public class shareTransactionModel {

    int amt;
    String  date;

    public shareTransactionModel(){}

    public shareTransactionModel(int amt, String date) {
        this.amt = amt;
        this.date = date;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
