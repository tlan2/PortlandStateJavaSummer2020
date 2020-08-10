package edu.pdx.cs410j.tlan2.phonebill;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractPhoneBill<T extends AbstractPhoneCall> implements Serializable {
    public AbstractPhoneBill() {
    }

    public abstract String getCustomer();

    public abstract void addPhoneCall(T var1);

    public abstract Collection<T> getPhoneCalls();

    public final String toString() {
        return this.getCustomer() + "'s phone bill with " + this.getPhoneCalls().size() + " phone calls";
    }
}
