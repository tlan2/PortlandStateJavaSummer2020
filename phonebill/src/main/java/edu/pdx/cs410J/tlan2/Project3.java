package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
  /**
   *
   * @param args
   * All the elements needed to complete the program through
   * the command line. This includes customer, callerNumber,
   * calleeNumber, start of phone call date/time/AMPM, and
   * end of phone call date/time/AMPM.
   */
  public static void main(String[] args) {
      printCLI();

      // No Command Line Arguments
    if (args.length == 0) {
      System.err.println("\nMissing command line arguments");
        System.exit(1);
    }

      //  If -README called in any argument position then print README file
      for(int i=0; i < args.length; i++){
          if(args[i].equals("-README")){
              try {
                  printReadMe();
              } catch (IOException ex) {
                  System.err.println("\nError: " + ex.getMessage());
              }
              System.exit(0);
          }
      }

    if (args.length == 1){
        System.err.println("\n\nMissing callerNumber, calleeNumber, " +
                "call start and call end date/time/AM/PM.");
    } else if (args.length == 2){
        System.err.println("\n\nMissing calleeNumber, " +
                "call start and call end date/time/AM/PM.");
    } else if (args.length == 3){
        System.err.println("\n\nMissing call start and call end " +
                "date/time/AM/PM.");
    } else if (args.length == 4){
        System.err.println("\n\nMissing call start time/AM/PM, and call end date/time/AM/PM.");
    } else if (args.length == 5){
        System.err.println("\n\nMissing call start AM/PM and end date/time/AM/PM.");
    } else if (args.length == 6){
        System.err.println("\n\nMissing call end date/time/AM/PM.");
    } else if (args.length == 7){
        System.err.println("\n\nMissing call end time/AM/PM.");
    } else if (args.length == 8){
        System.err.println("\n\nMissing call end AM/PM.");
    } // Too Many Arguments check
    else if (args.length > 13){
      System.err.println("\nToo many command line arguments.");
      System.exit(1);
    }

      // Remove leading and trailing white space from arguments
      for (String argument:args){
        argument = argument.trim();
    }

      // Check options in the correct positions
      // 3 is the max position for -pretty and -textFile
      // 4 is the max position for -print
      int prettyArgPosition = 0;
      int textFilePosition = 0;
      int printArgPosition = 0;
      for(int i=0; i < args.length; i++){
          if(args[i].equals("-pretty")){
              prettyArgPosition = i;
              if(prettyArgPosition > 3){
                  System.err.println("\n\nError: -pretty option out of position. Put in the beginning of input.\n");
              }
          } else if(args[i].equals("-textFile")){
              textFilePosition = i;
              if(textFilePosition > 3){
                  System.err.println("\n\nError: -textFile option out of position. Put in the beginning of input.\n");
                  System.exit(1);
              }
          } else if(args[i].equals("-print")){
              printArgPosition = i;
              if(printArgPosition > 4){
                  System.err.println("\n\nError: -print option out of position. Put in the beginning of input.\n");
                  System.exit(1);
              }
          }
      }

      // ============== OPTIONAL ARGUMENTS ========================
      // Print phone call and store in new phone bill
    if (args[0].equals("-print") && args.length == 10){
        PhoneCall newCall = new PhoneCall(args[2], args[3], args[4],
                args[5], args[6], args[7], args[8], args[9]);
        String customerName = args[1];
        printPhoneCall(newCall);
        PhoneBill bill = new PhoneBill(customerName);

        System.exit(0);
    }
    // -PrettyPrint to file or console only
    else if (args[0].equals("-pretty") && args.length == 11){
        PhoneCall newCall = new PhoneCall(args[3],args[4],args[5],args[6],
                                            args[7],args[8],args[9],args[10]);
        String customerName = args[2];
        String prettyFileName = args[1];

        prettyPrintOnly(newCall, customerName, prettyFileName);
        System.exit(0);
    } // -textFile only
    else if (args[0].equals("-textFile") && args.length == 11){
        String fileName = args[1];
        String customerName = args[2];
        PhoneCall newCall = new PhoneCall(args[3], args[4], args[5],
                    args[6], args[7], args[8], args[9], args[10]);
        PhoneBill newBill = new PhoneBill(customerName);
        ReadAndWriteToFile(newCall, customerName, fileName);

        System.exit(0);
    }
    // Prints the phone call entered and inputs it into a customer's text file
    else if (args[0].equals("-print") && args[1].equals("-textFile")){
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9], args[10], args[11]);
        printPhoneCall(newCall);

        String customerName = args[3];
        String fileName = args[2];
        ReadAndWriteToFile(newCall, customerName, fileName);

        System.exit(0);
    }
    //-textFile & -print
    else if(args[0].equals("-textFile")  && args[2].equals("-print")) {
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9], args[10], args[11]);
        printPhoneCall(newCall);
        String customerName = args[3];
        String fileName = args[1];
        ReadAndWriteToFile(newCall, customerName, fileName);
        System.exit(0);
    } // PrettyPrint & -textfile
    else if(args[0].equals("-pretty")  && args[2].equals("-textFile")){
        PhoneCall newCall = new PhoneCall(args[5], args[6], args[7],
                args[8], args[9], args[10], args[11], args[12]);
        String customerName = args[4];
        String prettyFileName = args[1];
        String textFileName = args[3];
        ReadAndWriteToFile(newCall,customerName,textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);
    }
    // -textFile & PrettyPrint
    else if(args[0].equals("-textFile")  && args[2].equals("-pretty")){
        PhoneCall newCall = new PhoneCall(args[5], args[6], args[7],
                args[8], args[9], args[10], args[11], args[12]);
        String customerName = args[4];
        String prettyFileName = args[3];
        String textFileName = args[1];
        ReadAndWriteToFile(newCall, customerName, textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);
    }// -print & PrettyPrint
    else if(args[0].equals("-print")  && args[1].equals("-pretty") & !args[3].equals("-textFile")){
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9], args[10], args[11]);
        printPhoneCall(newCall);
        String customerName = args[3];
        String prettyFileName = args[2];
        prettyPrintOnly(newCall, customerName, prettyFileName);
        System.exit(0);
    }// PrettyPrint & -print
    else if(args[0].equals("-pretty")  && args[2].equals("-print") && !args[3].equals("-textFile")){
        PhoneCall newCall = new PhoneCall(args[4], args[5], args[6],
                args[7], args[8], args[9], args[10], args[11]);
        printPhoneCall(newCall);
        String customerName = args[3];
        String prettyFileName = args[1];
        prettyPrintOnly(newCall, customerName, prettyFileName);
        System.exit(0);
    }// PrettyPrint, -textFile & -print
    else if(args[0].equals("-pretty")  && args[2].equals("-textFile") && args[4].equals("-print")){
        PhoneCall newCall = new PhoneCall(args[6], args[7], args[8],
                args[9], args[10], args[11], args[12], args[13]);
        printPhoneCall(newCall);
        String customerName = args[5];
        String prettyFileName = args[1];
        String textFileName = args[3];
        ReadAndWriteToFile(newCall, customerName, textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);

    } // textFile, PrettyPrint & -print
    else if (args[0].equals("-textFile")  && args[2].equals("-pretty") && args[4].equals("-print")){
        PhoneCall newCall = new PhoneCall(args[6], args[7], args[8],
                args[9], args[10], args[11], args[12], args[13]);
        printPhoneCall(newCall);
        String customerName = args[5];
        String prettyFileName = args[3];
        String textFileName = args[1];
        ReadAndWriteToFile(newCall, customerName, textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);
    } //-print, -textFile & PrettyPrint
    else if (args[0].equals("-print")  && args[1].equals("-pretty") && args[3].equals("-textFile")){
        PhoneCall newCall = new PhoneCall(args[6], args[7], args[8],
                args[9], args[10], args[11], args[12], args[13]);
        printPhoneCall(newCall);
        String customerName = args[5];
        String prettyFileName = args[2];
        String textFileName = args[4];
        ReadAndWriteToFile(newCall, customerName, textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);
    } else if (args[0].equals("-print")  && args[1].equals("-textFile") && args[3].equals("-pretty")){
        PhoneCall newCall = new PhoneCall(args[6], args[7], args[8],
                args[9], args[10], args[11], args[12], args[13]);
        printPhoneCall(newCall);
        String customerName = args[5];
        String prettyFileName = args[4];
        String textFileName = args[2];
        ReadAndWriteToFile(newCall, customerName, textFileName);
        prettyPrintWithTextFile(newCall,customerName,prettyFileName,textFileName);
        System.exit(0);
    }
    // PhoneCall Only
    else if (args.length==9){
        PhoneCall newCall = new PhoneCall(args[1], args[2], args[3],
                args[4], args[5], args[6], args[7], args[8]);
        String customerName = args[1];
        PhoneBill bill = new PhoneBill(customerName);
        bill.addPhoneCall(newCall);

        System.exit(0);
    }
        // Catch All Possibility
        else {
        System.err.println("\n\nError: Program Error. Please try again.");
        System.exit(1);
    }
  }


    //============ Methods Used in Main ========================================

    private static void printPhoneCall(PhoneCall newCall) {
        System.out.println("\n" + newCall.toString());
    }

    private static void printCLI() {
        System.out.println("\n\nusage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>" +
                "\n  args are (in this order):" +
                "\n\tcustomer\t\t\tPerson whose phone bill we're modeling" +
                "\n\tcallerNumber\t\tPhone number of caller" +
                "\n\tcalleeNumber\t\tPhone number of person who was called" +
                "\n\tstart\t\t\t\tDate and time call began (24-hour time)"+
                "\n\tend\t\t\t\t\tDate and time call ended (24-hour time)" +
                "\n  options are (options may appear in any order) :" +
                "\n\t-pretty file\t\t\tPretty print the phone bill to a text file" +
                "\n\t\t\t\t\tor standard out (file -) ." +
                "\n\t-textFile file\t\t\tWhere to read/write the phone bill" +
                "\n\t-print\t\t\t\tPrints a description of the new phone call" +
                "\n\t-README\t\t\t\tPrints a README for this project and exits");
    }

    /**
     * Reads the README.txt file and prints out the program description within the file.
     *
     * @throws IOException  If an input or output exception occurred.
     */
    public static void printReadMe() throws IOException {
        InputStream readme = Project3.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));

        StringBuilder readMeTxt = new StringBuilder("\n\n");
        String line;
        while ((line = reader.readLine()) != null) {
            readMeTxt.append(line).append("\n");
        }
        System.out.println(readMeTxt.toString());
    }

    private static void ReadAndWriteToFile(PhoneCall newCall, String customerName, String fileName) {
        File file = new File(fileName);
        PhoneBill newBill = new PhoneBill();

        //Parse existing file
        if(file.exists()){

            try
            {
                TextParser tp = new TextParser(file);
                newBill = tp.parse();
                String customerOnFile = newBill.getCustomer().trim();

                if(!customerOnFile.equals(customerName)){
                    System.err.println("\nError: Customer name inputted does not match " +
                            "customer name on file.\n\n");
                    System.exit(1);
                }

                System.out.println("\nWriting to existing file.\n");

                newBill.addPhoneCall(newCall);
//                Collection<PhoneCall> allCalls = newBill.getPhoneCalls();



                try {
                    SortedSet<PhoneCall> calls = newBill.sortPhoneCalls();

                    TextDumper td = new TextDumper(fileName);
                    td.dump(newBill);
                } catch (IOException e) {
                    System.err.println("While dumping bill to file.....");
                    System.exit(1);
                }
            } catch (ParserException ex)
            {
                System.err.println("While parsing text file....");
                System.exit(1);
            }

        } else
        {
            System.out.println("\nNew file created.\n");
            try
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException ex)
            {
                System.err.println("While creating a new file.....");
                System.exit(1);
            }
            newBill.addCustomer(customerName);
            newBill.addPhoneCall(newCall);
            TextDumper td = new TextDumper(fileName);
            try
            {
                td.dump(newBill);
            } catch (IOException ex)
            {
                System.err.println("While dumping bill to text file......");
                System.exit(1);
            }
        }
    }

    private static void prettyPrintOnly(PhoneCall newCall, String customerName, String fileName) {
        PhoneBill bill = new PhoneBill();
        PrettyPrinter pp = new PrettyPrinter(fileName);

        bill.addCustomer(customerName);
        bill.addPhoneCall(newCall);

            try {
                pp.dump(bill);
            } catch (IOException ex){
                System.err.println("While pretty printing.......");
                System.exit(1);
            }
    }

    private static void prettyPrintWithTextFile(PhoneCall newCall, String customerName, String prettyFileName,
                                                String textFileName) {
        File textFile = new File(textFileName);
        PhoneBill bill = new PhoneBill();
        PrettyPrinter pp = new PrettyPrinter(prettyFileName);

        if (prettyFileName.equals("-")){
            TextParser tp = new TextParser(textFile);
            try {
                bill = tp.parse();
                bill.addCustomer(customerName);
                bill.addPhoneCall(newCall);
                try {
                    pp.dump(bill);
                } catch (IOException ex){
                    System.err.println("While pretty printing......");
                    System.exit(1);
                }
            } catch (ParserException ex){
                System.err.println("While parsing file......");
                System.exit(1);
            }


        } else {
            TextParser tp = new TextParser(textFile);
            try {
                bill = tp.parse();
            } catch (ParserException ex){
                System.err.println("While parsing file......");
                System.exit(1);
            }
            bill.addPhoneCall(newCall);
            bill.addCustomer(customerName); //only for -pretty only
            try {
                pp.dump(bill);
            } catch (IOException ex){
                System.err.println("While dumping bill to file....");
                System.exit(1);
            }
        }
    }

}
