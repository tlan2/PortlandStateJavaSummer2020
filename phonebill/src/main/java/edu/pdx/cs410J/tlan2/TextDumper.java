package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a <code>TextDumper</code>.
 * Contains a dump method that takes a PhoneBill object and "dumps"
 * the contents into a customer's text file.
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {

    String path = "C:\\Users\\thoma\\Documents" +
            "\\6. Summer 2020\\1. Advanced Java" +
            "\\PortlandStateJavaSummer2020\\phonebill\\src" +
            "\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

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
        Collection<PhoneCall> phoneCalls = bill.getPhoneCalls();

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
