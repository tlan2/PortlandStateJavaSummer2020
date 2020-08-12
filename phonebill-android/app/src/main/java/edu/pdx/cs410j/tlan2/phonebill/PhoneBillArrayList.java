package edu.pdx.cs410j.tlan2.phonebill;

import java.util.Collection;
import java.util.HashMap;

public class PhoneBillArrayList {
    private HashMap<String,PhoneBill> map;

    public PhoneBillArrayList() {
        map = new HashMap<>();
    }


    public Collection<PhoneBill> getBills() {
        return this.map.values();
    }

    public void addPhoneBill(String name, PhoneBill bill) {
        this.map.put(name,bill);
    }

    public PhoneBill findPhoneBill(String name) {
        if (map.get(name) == null) {
            return new PhoneBill(name);
        } else {
            return map.get(name);
        }
    }
}
