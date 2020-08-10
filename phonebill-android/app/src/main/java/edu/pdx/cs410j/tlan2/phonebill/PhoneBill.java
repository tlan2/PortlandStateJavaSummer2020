package edu.pdx.cs410j.tlan2.phonebill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{
    private Collection<PhoneCall> calls = new ArrayList<>();

    @Override
    public String getCustomer() {
        return "My Customer";
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
