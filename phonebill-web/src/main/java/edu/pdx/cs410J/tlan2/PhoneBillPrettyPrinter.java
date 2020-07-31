package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.SortedSet;

/**
 * This class represents a <code>PhoneBillPrettyPrinter</code>.
 * Contains a dump method that takes a PhoneBill object and "dumps" or writes
 * the contents to the console in an user-friendly format.
 */
public class  PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    public PhoneBillPrettyPrinter(PrintWriter pw) {
        this.writer = pw;
    }

    /**
     * Takes a PhoneBill object and prints its contents to console.
     *
     * @param phoneBill
     * The phone bill to be processed and written to the console.
     *
     * @throws IOException
     */
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
