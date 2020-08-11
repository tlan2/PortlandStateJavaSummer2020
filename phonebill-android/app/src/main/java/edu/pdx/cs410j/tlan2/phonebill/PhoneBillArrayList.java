package edu.pdx.cs410j.tlan2.phonebill;

import java.util.ArrayList;

public class PhoneBillArrayList {
    private ArrayList<PhoneBill> bills = new ArrayList<>();

    public ArrayList<PhoneBill> getBills() {
        return this.bills;
    }

    public void addPhoneBill(PhoneBill bill) {
        this.bills.add(bill);
    }

    public PhoneBill findPhoneBill(String name){
        for(PhoneBill bill:bills){
            if(name.equals(bill.getCustomer())){
                return bill;
            }
        }
        return null;
    }
}
