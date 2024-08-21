package com.example.bsaassistant.budget;

public class budgetExpenseModel {

    public budgetExpenseModel() {

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int amount;
    boolean isChecked;
    String name;

    public budgetExpenseModel(int amount, boolean isChecked, String name) {
        this.amount = amount;
        this.isChecked = isChecked;
        this.name = name;
    }
}
