package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;
    private ArrayList<PhoneCall> phoneCalls;

    public PhoneBill(String customer) {
        this.customer = customer;
        this.phoneCalls = new ArrayList<>();
    }


    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.phoneCalls;
    }
}
