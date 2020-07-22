package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper {

    private boolean printToFile;
    private String fileName;
    private TimeUnit timeUnit = TimeUnit.MINUTES;


    public PrettyPrinter(String fileName){this.fileName = fileName;}


    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        PhoneBill bill = (PhoneBill) abstractPhoneBill;
        SortedSet<PhoneCall> calls;

        if(this.fileName.equals("-")){
            //PrettyPrint to console
            String customerName = bill.getCustomer();
            calls = bill.sortPhoneCalls();

            System.out.println("\n===================================================== Phone Bill ===================" +
                    "=====================================================");
            System.out.println("\nCustomer: " + customerName);
            System.out.println("\nCall #\tCaller Number\tReceiver Number\t\tStart Date\t\t\tEnd Date\t\tCall Duration");
            System.out.println("----------------------------------------------------------------------------------" +
                    "-----------------------------------");
            for (PhoneCall c:calls){
                int i=1;
                Date sDate = c.getStartTime();
                Date eDate = c.getEndTime();
                String prettySDate;
                String prettyEDate;
                double duration;


                int f = DateFormat.MEDIUM;
                DateFormat df = DateFormat.getDateTimeInstance(f, f);
                prettySDate = df.format(sDate);
                prettyEDate = df.format(eDate);
                duration = getDateDiff(sDate, eDate);

//                System.out.println("\n\nduration of call" + i + ": " + duration);
//                System.out.println("\n\nprettySDate of call" + i + ": " + prettySDate);
//                System.out.println("\n\nprettyEDate of call" + i + ": " + prettyEDate);
                System.out.println("  " + i + "\t" + c.getCaller() + "\t " + c.getCallee() + "\t    " +
                                    prettySDate + "\t" + prettyEDate  + "\t    " + duration);
                System.out.println();

                i++;
            }

        } else {
            //PrettyPrint to File
            return;
        }
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
//     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    // From: https://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
    public static double getDateDiff(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return minutes;
    }
}
