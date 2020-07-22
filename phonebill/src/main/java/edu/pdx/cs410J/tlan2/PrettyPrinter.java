package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper {

    private boolean printToFile;
    private String fileName;


    public PrettyPrinter(String fileName){this.fileName = fileName;}


    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        PhoneBill bill = (PhoneBill) abstractPhoneBill;
        SortedSet<PhoneCall> calls;

        if(this.fileName.equals("-")){
            //PrettyPrint to console
            calls = bill.sortPhoneCalls();

            for (PhoneCall c:calls){
                int i=1;
                Date sDate = c.getStartTime();
                Date eDate = c.getEndTime();
                long duration;

                duration = getDateDiff(sDate, eDate);
                System.out.println("duration of call" + i + ": " + duration);
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
    public static long getDateDiff(Date date1, Date date2) {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,timeUnit);
    }
}
