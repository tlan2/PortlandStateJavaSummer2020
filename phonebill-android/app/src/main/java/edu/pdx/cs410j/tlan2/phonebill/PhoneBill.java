package edu.pdx.cs410j.tlan2.phonebill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{
    private String customer;
    private Collection<PhoneCall> calls = new ArrayList<>();

    public PhoneBill(String customer) {
        this.customer = customer;
        this.calls = new ArrayList<>();
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }

    public SortedSet<PhoneCall> sortPhoneCalls() {
        Collection<PhoneCall> calls = this.getPhoneCalls();
        SortedSet<PhoneCall> set = new TreeSet<PhoneCall>();

        int i = 1;
        for (PhoneCall c:calls){
            set.add(c);
            System.out.println("PhoneBill - sortedCalls - call #" + i);
            i++;
        }

        return set;
    }
}
