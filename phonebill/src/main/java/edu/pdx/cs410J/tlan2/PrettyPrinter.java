package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        File file = new File(this.fileName);
        SortedSet<PhoneCall> calls = bill.sortPhoneCalls();


        if(this.fileName.equals("-")){
            //PrettyPrint to console
            String customerName = bill.getCustomer();

            System.out.println("\n===================================================== Phone Bill ===================" +
                    "=================================");
            System.out.println("\nCustomer: " + customerName);
            System.out.println("\nCall #\tCaller Number\tReceiver Number\t\tStart Date\t\t\tEnd Date\t\tCall Duration (hrs)");
            System.out.println("----------------------------------------------------------------------------------" +
                    "-----------------------------------------");
            int i=1;
            for (PhoneCall c:calls){
                Date sDate = c.getStartTime();
                Date eDate = c.getEndTime();

                double duration;

//                int long = DateFormat.LONG;
//                int medium = DateFormat.MEDIUM;
//                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String prettySDate = df.format(sDate);
                String prettyEDate = df.format(eDate);
                prettySDate = prettySDate.replace("0020", "2020");
                prettyEDate = prettyEDate.replace("0020", "2020");
                duration = getDateDiff(sDate, eDate);

                System.out.println("  " + i + "\t" + c.getCaller() + "\t " + c.getCallee() + "\t     " +
                                    prettySDate + "\t  " + prettyEDate  + "\t\t    " + duration);
                System.out.println();

                i++;
            }

        } else {
            //PrettyPrint to File
            if(file.exists()){
                file.delete();
            }

            try
            {
                file.createNewFile();
            } catch (IOException ex)
            {
                System.err.println("While creating a new file");
                System.exit(1);
            }
            System.out.println("\nNew file created.\n");

            String customerName = bill.getCustomer();
            FileWriter writer = new FileWriter(this.fileName);
            calls = bill.sortPhoneCalls();

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();

            writer.write("\n============================================ Phone Bill ===================" +
                    "====================================");
            writer.write("\n\nCustomer: " + customerName + "\n\n");

            writer.write("Bill Date: " + formatter.format(date) + "\n");

            writer.write("\nCall #\tCaller Number\tReceiver Number\t\t Start Date  \t\t\t\t\tEnd Date\t\t\t\tCall Duration");
            writer.write("\n-------------------------------------------------------------------------------------" +
                    "-----------------------------");
            int i=1;
            for (PhoneCall c:calls){
                Date sDate = c.getStartTime();
                Date eDate = c.getEndTime();
                double duration;

                int f = DateFormat.MEDIUM;
                DateFormat df = DateFormat.getDateTimeInstance(f, f);
                String prettySDate = df.format(sDate);
                String prettyEDate = df.format(eDate);
                prettySDate = prettySDate.replace(" 20,", " 2020,");
                prettyEDate = prettyEDate.replace(" 20,", " 2020,");
                duration = getDateDiff(sDate, eDate);

                writer.write("\n  " + i + "\t\t" + c.getCaller() + "\t " + c.getCallee() + "\t\t" +
                        prettySDate + "\t\t" + prettyEDate  + "\t\t" + duration + "\n");

                i++;
            }
            writer.close();
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
