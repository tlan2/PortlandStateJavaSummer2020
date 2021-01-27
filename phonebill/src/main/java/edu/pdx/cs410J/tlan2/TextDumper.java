package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

/**
 * This class represents a <code>TextDumper</code>.
 * Contains a dump method that takes a PhoneBill object and "dumps"
 * the contents
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {

    private String fileName;

    /**
     * Creates a new <code>TextDumper</code>.
     *
     * @param nameOfFile
     * The file name to be located and "dumped" into a PhoneBill object.
     */
    public TextDumper(String nameOfFile){ this.fileName = nameOfFile; }

    /**
     * Takes a PhoneBill object and places its contents into a new text file every time.
     *
     * @param bill
     * The phone bill to be processed and entered into the customer's text file.
     *
     * @throws IOException
     */
    @Override
    public void dump(PhoneBill bill) throws IOException{
        SortedSet<PhoneCall> phoneCalls = bill.sortPhoneCalls();

        FileWriter writer = new FileWriter(this.fileName);
        writer.write(bill.getCustomer());
        writer.write("\n");
        for (PhoneCall call : phoneCalls) {
            String callInfo = call.getAllCallInfo();
            writer.write(callInfo);
            writer.write("\n");
        }
        writer.close();
    }
}
