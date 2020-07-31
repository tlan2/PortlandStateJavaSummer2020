package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * This class represents a <code>PhoneBillTextDumper</code>.
 * Contains a dump method that takes a PhoneBill object and "dumps" or writes
 * the contents to the URL page.
 */
public class PhoneBillTextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    public PhoneBillTextDumper(PrintWriter writer) {
        this.writer = writer;
    }

    /**
     * Takes a PhoneBill object and places its contents into URL page.
     *
     * @param phoneBill
     * The phone bill to be processed and written to the URL page.
     *
     * @throws IOException
     */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        this.writer.println(phoneBill.getCustomer());
        for(PhoneCall call : phoneBill.sortedPhoneCalls()){
            this.writer.println(call.getAllCallInfo());
        }
    }
}
