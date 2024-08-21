package com.example.bsaassistant.datamodels;

public class jkModel {
    public jkModel() {

    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    int sr, amt, type, bal;

    String date, payee, head, details;

    public jkModel(int sr, int amt, int type, int bal, String date, String payee, String head, String details) {
        this.sr = sr;
        this.amt = amt;
        this.type = type;
        this.bal = bal;
        this.date = date;
        this.payee = payee;
        this.head = head;
        this.details = details;
    }
}