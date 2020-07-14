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
   * This method prints the main menu on the command line interface
   * for guidance on how to proceed in the program.
   */
  private static void printMainMenu(){
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
  }

  /**
   * Reads the README.txt file and prints out the program description within the file.
   *
   * @throws IOException  If an input or output exception occurred.
   */
  public static String printReadMe() throws IOException {
    StringBuilder readMeTxt = new StringBuilder();

    try (InputStream readme = Project2.class.getResourceAsStream("README.txt")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));

      String line;
      while ((line = reader.readLine()) != null) {
        readMeTxt.append(line).append("\n");
//        System.out.println(line);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
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
    if (args.length == 0) {
      System.err.println("\nMissing command line arguments");
      System.exit(1);
    }

//    If -README called in any argument position then print README file
    for (String argument : args) {
      if (argument == "-README") {
        try {
          printReadMe();
        } catch (IOException ex) {
          System.err.println("\nCaught IOException: " + ex.getMessage());
        }
        System.exit(0);
      }
    }

    printMainMenu();

    if (args.length > 10)
    {
      System.err.println("\nToo many command line arguments.");
      System.exit(1);
    } else if (args[0] == "-print" && args.length == 8) // Print New Call Information & Exit
    {
      PhoneCall newCall = new PhoneCall(args[2], args[3], args[4],
              args[5], args[6], args[7]);
      System.out.println(newCall.toString());

      PhoneBill bill = new PhoneBill(args[1]);
      bill.addPhoneCall(newCall);
      System.exit(0);
    } else if(args[0] == "-textFile" && args.length == 9) // -textFile only
    {
        PhoneCall newCall = new PhoneCall(args[3], args[4], args[5],
                args[6], args[7], args[8]);
        String fileName = args[1] + ".txt";
        File file = new File(fileName);

        try {
            if(file.createNewFile()){ // Creates new empty file if doesn't exist
                PhoneBill newEmptyBill = new PhoneBill(args[2]);
                newEmptyBill.addPhoneCall(newCall);
                TextDumper td = new TextDumper();
                td.dump(newEmptyBill);
                System.exit(0);
            } else // Add to existing file
                {
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        System.err.println("\nError: Could not read the file.");
                        e.printStackTrace();
                    }
                    String fileCustomer = scanner.nextLine();
                    // Check if customer names match
                    if(fileCustomer == args[2]){
                        TextParser tp = new TextParser(fileName);
                        PhoneBill oldBill = tp.parse();
                        oldBill.addPhoneCall(newCall);
                        PhoneBill newBill = oldBill;

                        TextDumper td = new TextDumper();
                        td.dump(newBill);
                        System.exit(0);
                    } else
                    {
                        System.err.println("\nError: Customer on file does not match customer entered.");
                        System.exit(1);
                    }
                }
        } catch (IOException e) {
            System.err.println("\nError: Cannot create new a file. Please try again.");
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    } else if (args[0] == "-textFile" && args[3] == "-print" && args.length == 10)
    {
        // -textFile called & print method
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9]);
        System.out.println(newCall.toString());
        String fileName = args[1] + ".txt";
        File file = new File(fileName);

        try {
            if(file.createNewFile()){ // Creates new empty file if doesn't exist
                PhoneBill newEmptyBill = new PhoneBill(args[2]);
                newEmptyBill.addPhoneCall(newCall);
                TextDumper td = new TextDumper();
                td.dump(newEmptyBill);
                System.exit(0);
            } else // Add to existing file
            {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    System.err.println("\nError: Could not read the file.");
                    e.printStackTrace();
                }
                String fileCustomer = scanner.nextLine();
                // Check if customer names match
                if(fileCustomer == args[2]){
                    TextParser tp = new TextParser(fileName);
                    PhoneBill oldBill = tp.parse();
                    oldBill.addPhoneCall(newCall);
                    PhoneBill newBill = oldBill;

                    TextDumper td = new TextDumper();
                    td.dump(newBill);
                    System.exit(0);
                } else
                {
                    System.err.println("\nError: Customer on file does not match customer entered.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("\nError: Cannot create new a file. Please try again.");
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }

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
    }  else if (args.length == 7)
    {
        PhoneCall call = new PhoneCall(args[1], args[2], args[3],
                args[4], args[5], args[6]);
        PhoneBill bill = new PhoneBill(args[0]);
        bill.addPhoneCall(call);
        System.exit(0);
    } else
    {
        System.err.println("\n\nDoh! Program couldn't handle your request. Please try entering your information again.");
        System.exit(1);
    }
  }
}