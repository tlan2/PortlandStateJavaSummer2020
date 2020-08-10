package edu.pdx.cs410j.tlan2.phonebill;

import java.io.IOException;

public interface PhoneBillDumper<T extends AbstractPhoneBill> {
    void dump(T var1) throws IOException;
}
