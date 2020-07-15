package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill> {

    String path = "C:\\Users\\thoma\\Documents" +
            "\\6. Summer 2020\\1. Advanced Java" +
            "\\PortlandStateJavaSummer2020\\phonebill\\src" +
            "\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

    private String fileName;

    public TextDumper(String nameOfFile){ this.fileName = nameOfFile; }

    @Override
    public void dump(PhoneBill bill) throws IOException{
        Collection<PhoneCall> phoneCalls = bill.getPhoneCalls();
        File file = new File(this.fileName);


        file.delete();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//        FileWriter fw = new FileWriter(file); //writes new file with all contents
//        fw.write(customer);
//        fw.write("\n");
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
