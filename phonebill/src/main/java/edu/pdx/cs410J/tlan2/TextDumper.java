package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill> {

    String path = "C:\\Users\\thoma\\Documents" +
            "\\6. Summer 2020\\1. Advanced Java" +
            "\\PortlandStateJavaSummer2020\\phonebill\\src" +
            "\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

    @Override
    public void dump(PhoneBill bill) throws IOException{
        String customer = bill.getCustomer();
        String filePath = path + customer + ".txt";
        Collection<PhoneCall> phoneCalls = bill.getPhoneCalls();
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) phoneCalls;
        File file;
        boolean append = false;

        if ((file = new File(filePath)).isFile())
        {
                append = true;
                FileWriter fw = new FileWriter(file, append);
                BufferedWriter bw = new BufferedWriter(fw);
                for(PhoneCall call:calls)
                {
                    String callInfo = call.getCaller() + " " + call.getCallee() + " " +
                            call.getStartTimeString() + " " + call.getEndTimeString();
                    bw.write(callInfo);
                    bw.write("\n");
                }
                bw.flush();
                bw.close();
        } else if (!(file = new File(filePath)).isFile()) {
            file.createNewFile();
            FileWriter fw = new FileWriter(file, append);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(customer);
            bw.write("\n");
            for (PhoneCall call : calls) {
                String callInfo = call.getCaller() + " " + call.getCallee() + " " +
                        call.getStartTimeString() + " " + call.getEndTimeString();
                bw.write(callInfo);
                bw.write("\n");
            }
            bw.flush();
            bw.close();
        }
    }
}
