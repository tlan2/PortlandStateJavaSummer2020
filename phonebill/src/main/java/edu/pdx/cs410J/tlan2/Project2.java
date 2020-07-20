package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Text;

import java.io.*;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

  /**
   * Reads the README.txt file and prints out the program description within the file.
   *
   * @throws IOException  If an input or output exception occurred.
   */
  public static String printReadMe() throws IOException {
      StringBuilder readMeTxt = new StringBuilder();

      InputStream readme = Project2.class.getResourceAsStream("README.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line;
      while ((line = reader.readLine()) != null) {
          readMeTxt.append(line).append("\n");
      }

    return readMeTxt.toString();
  }
  /**
   *
   * @param args
   * All the elements needed to complete the program through
   * the command line. This includes customer, callerNumber,
   * calleeNumber, start of phone call date and time, and
   * end of phone call date and time.
   */
  public static void main(String[] args) {
      System.out.println("\nusage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>" +
              "\n  args are (in this order):" +
              "\n\tcustomer\t\t\tPerson whose phone bill we're modeling" +
              "\n\tcallerNumber\t\tPhone number of caller" +
              "\n\tcalleeNumber\t\tPhone number of person who was called" +
              "\n\tstart\t\t\t\tDate and time call began (24-hour time)"+
              "\n\tend\t\t\t\t\tDate and time call ended (24-hour time)" +
              "\n  options are (options may appear in any order) :" +
              "\n\t-textFile file\t\t\tWhere to read/write the phone bill" +
              "\n\t-print\t\t\t\tPrints a description of the new phone call" +
              "\n\t-README\t\t\t\tPrints a README for this project and exits" +
              "\n  Date and time should be in the format: mm/dd/yyyy hh:mm");

      // No Command Line Arguments
    if (args.length == 0) {
      System.err.println("\nMissing command line arguments");
      System.exit(1);
    }

    //    If -README called in any argument position then print README file
    for(int i=0; i < args.length; i++){
        if(args[i].equals("-README")){
            try {
                System.out.println("\n\n" + printReadMe());
        } catch (IOException ex) {
                System.err.println("\nError: " + ex.getMessage());
        }
        System.exit(0);
        }
    }

      if (args.length > 10)
    {
      System.err.println("\nToo many command line arguments.");
      System.exit(1);
    } else if (args.length == 1)
    {
        System.err.println("\nMissing callerNumber, calleeNumber, " +
                "call start date and time, and call end date and time.");
        System.exit(1);
    } else if (args.length == 2)
    {
        System.err.println("\nMissing calleeNumber, " +
                "call start date and time, and call end date and time.");
        System.exit(1);
    } else if (args.length == 3)
    {
        System.err.println("\nMissing call start date and time, and call end date and time.");
        System.exit(1);
    } else if (args.length == 4)
    {
        System.err.println("\nMissing call start time, and call end date and time.");
        System.exit(1);
    } else if (args.length == 5)
    {
        System.err.println("\nMissing call end date and time.");
        System.exit(1);
    } else if (args.length == 6)
    {
        System.err.println("\nMissing call end time.");
        System.exit(1);
    }

    for (String argument:args){
        argument = argument.trim();
    }
    if (args[0].equals("-print") && args.length == 8){
        PhoneCall newCall = new PhoneCall(args[2], args[3], args[4],
                args[5], args[6], args[7]);
        System.out.println(newCall.toString());
        PhoneBill newBill = new PhoneBill();
        System.exit(0);
    }
    // Prints the phone call entered and inputs it into a customer's text file
    else if (args[0].equals("-print") && args[1].equals("-textFile")) // OPTIONAL ARGUMENTS
    {
        // -print process
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9]);
        System.out.println(newCall.toString());
        PhoneBill newBill = new PhoneBill();

        // -textfile process
        String fileName = args[2];
        File file = new File(fileName);

        //Parse existing file
        if(file.exists()){
            TextParser tp = new TextParser(file);

            try
            {
                newBill = tp.parse();
            } catch (ParserException ex)
            {
                ex.printStackTrace();
            }

            String customerOnFile = newBill.getCustomer();

            if(!customerOnFile.equals(args[3])){
                System.err.println("\nError: Customer name inputted does not match " +
                        "customer name on file.");
                System.exit(1);
            }

            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        } else
        {
            try
            {
                file.createNewFile();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
//            PhoneBill newBill = new PhoneBill(args[3]);
            newBill.addCustomer(args[3]);
            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(args[2]);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.exit(0);

        //Another option order possibility
    } else if(args[0].equals("-textFile")  && args[2].equals("-print") )
    {
        // -print process
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9]);
        System.out.println(newCall.toString());
        PhoneBill newBill = new PhoneBill();

        // -textfile process
        String fileName = args[1];
        File file = new File(fileName);

        //Parse existing file
        if(file.exists()){
            TextParser tp = new TextParser(file);

            try
            {
                newBill = tp.parse();
            } catch (ParserException ex)
            {
                ex.printStackTrace();
            }

            String customerOnFile = newBill.getCustomer();

            if(!customerOnFile.equals(args[3])){
                System.err.println("\nError: Customer name inputted does not match " +
                        "customer name on file.");
                System.exit(1);
            }

            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        } else
        {
            try
            {
                file.createNewFile();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
//            PhoneBill newBill = new PhoneBill(args[3]);
            newBill.addCustomer(args[3]);
            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.exit(0);

        //Valid command line with the -textFile option
    } else if (args[0].equals("-textFile") && args.length == 9)
    {
        PhoneCall newCall = new PhoneCall(args[3], args[4], args[5],
                args[6], args[7], args[8]);
        PhoneBill newBill = new PhoneBill();

        // -textfile process
        String fileName = args[1];
        File file = new File(fileName);

        //Parse existing file
        if(file.exists()){
            TextParser tp = new TextParser(file);

            try
            {
                newBill = tp.parse();
            } catch (ParserException ex)
            {
                ex.printStackTrace();
            }

            String customerOnFile = newBill.getCustomer();

            if(!customerOnFile.equals(args[2])){
                System.err.println("\nError: Customer name inputted does not match " +
                        "customer name on file.");
                System.exit(1);
            }

            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        } else
        {
            try
            {
                file.createNewFile();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
//            PhoneBill newBill = new PhoneBill(args[3]);
            newBill.addCustomer(args[2]);
            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.exit(0);


    } else if (args.length == 7)
    {
        //Valid Command Line Entry with no options
        PhoneCall call = new PhoneCall(args[1], args[2], args[3],
                args[4], args[5], args[6]);
        PhoneBill bill = new PhoneBill(args[0]);
        bill.addPhoneCall(call);
        System.exit(0);
    } else
    {
        // Catch All Possibility
        System.err.println("\n\nError: Program Error. Please try again.");
        System.exit(1);
    }
  }
}