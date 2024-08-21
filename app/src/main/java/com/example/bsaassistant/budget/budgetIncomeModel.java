package com.example.bsaassistant.budget;

public class budgetIncomeModel {
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
    public budgetIncomeModel(){

    }



    public budgetIncomeModel(int amount, boolean isChecked, String name) {
        this.amount = amount;
        this.isChecked = isChecked;
        this.name = name;
    }
}
