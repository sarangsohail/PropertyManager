package com.example.propertymanagment;
public class AddingTenants {
//model for adding tenants

    
    String uid, name, deposit, rent, rentDueDate;

    public AddingTenants(){

    }
    public AddingTenants(String uid, String name, String deposit, String rent, String rentDueDate) {
        this.uid = uid;
        this.name = name;
        this.deposit = deposit;
        this.rent = rent;
        this.rentDueDate = rentDueDate;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRentDueDate() {
        return rentDueDate;
    }

    public void setRentDueDate(String rentDueDate) {
        this.rentDueDate = rentDueDate;
    }
}
