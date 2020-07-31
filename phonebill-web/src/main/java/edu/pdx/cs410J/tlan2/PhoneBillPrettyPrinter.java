package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.SortedSet;

public class  PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    public PhoneBillPrettyPrinter(PrintWriter pw) {
        this.writer = pw;
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        this.writer.println("\n===== Pretty Print =====\n");
        this.writer.println(phoneBill.getCustomer());
        SortedSet<PhoneCall> sortedCalls = phoneBill.sortedPhoneCalls();

        for(PhoneCall call: sortedCalls){
            this.writer.println("  " + call.getAllCallInfo());
        }

        this.writer.flush();
    }
}
