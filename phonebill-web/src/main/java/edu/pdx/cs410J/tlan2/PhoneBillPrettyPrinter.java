package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

public class  PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    public PhoneBillPrettyPrinter(PrintWriter pw) {
        this.writer = pw;
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        this.writer.println(phoneBill.getCustomer());

        for(PhoneCall call: phoneBill.getPhoneCalls()){
            this.writer.println("  " + call.getAllCallInfo());
        }

        this.writer.flush();
    }
}
