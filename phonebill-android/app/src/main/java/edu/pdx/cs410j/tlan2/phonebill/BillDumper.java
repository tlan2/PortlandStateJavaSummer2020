package edu.pdx.cs410j.tlan2.phonebill;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;

public class BillDumper implements PhoneBillDumper<PhoneBill> {

    private String fileName;

    public BillDumper(String nameOfFile){ this.fileName = nameOfFile; }

    @Override
    public void dump(PhoneBill bill) throws IOException{
        SortedSet<PhoneCall> phoneCalls = bill.sortPhoneCalls();
//        File dir = getDataDir();
//        File file = new File(dir, "results.txt");
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
