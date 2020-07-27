package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PhoneBillTextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    public PhoneBillTextDumper(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        this.writer.println(phoneBill.getCustomer());
        for(PhoneCall call : phoneBill.getPhoneCalls()){
            this.writer.println(call.getCaller());
        }
    }
}
