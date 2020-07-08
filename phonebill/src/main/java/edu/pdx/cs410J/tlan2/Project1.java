package edu.pdx.cs410J.tlan2;

import java.io.*;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  private static void printMainMenu(){
    System.out.println("\nusage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>" +
            "\n  args are (in this order):" +
            "\n\tcustomer\t\t\tPerson whose phone bill we're modeling" +
            "\n\tcallerNumber\t\tPhone number of caller" +
            "\n\tcalleeNumber\t\tPhone number of person who was called" +
            "\n\tstart\t\t\t\tDate and time call began (24-hour time)"+
            "\n\tend\t\t\t\t\tDate and time call ended (24-hour time)" +
            "\n  options are (options may appear in any order) :" +
            "\n\t-print\t\t\t\tPrints a description of the new phone call" +
            "\n\t-README\t\t\t\tPrints a README for this project and exits" +
            "\n  Date and time should be in the format: mm/dd/yyyy hh:mm");
  }

  public static void printTextFile(File file)
          throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    try { while (br.ready()) {
        System.out.println(br.readLine());
      }
    } catch (IOException ex) {
      System.err.println(ex);
      throw ex;
    } finally {
      if (br != null) {
        br.close();
      }
    }
  }


  public static void main(String[] args) {
    if(args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    } else if (args[0] == "-README") {
      File readMeFile = new File("C:\\Users\\thoma\\Documents\\6. Summer 2020" +
              "\\1. Advanced Java\\PortlandStateJavaSummer2020" +
              "\\phonebill\\src\\main\\resources\\edu\\pdx" +
              "\\cs410J\\tlan2\\README.txt");
      try {
        printTextFile(readMeFile);
        System.exit(0);
      } catch (IOException e) {
        System.err.println(e);
      }
    } else if (args[0] == "-print" && args.length == 8){
      PhoneCall call = new PhoneCall(args[2], args[3], args[4],
              args[5], args[6], args[7]);
      System.out.println(call.toString());
      PhoneBill bill = new PhoneBill(args[1]);
      bill.addPhoneCall(call);
      System.exit(0);
    }

    printMainMenu();

    if (args.length == 1){
      System.err.println("Missing callerNumber, calleeNumber, " +
                          "call start date and time, and call end date and time.");
      System.exit(1);
    } else if (args.length == 2){
      System.err.println("Missing calleeNumber, " +
              "call start date and time, and call end date and time.");
      System.exit(1);
     } else if (args.length == 3){
      System.err.println("Missing call start date and time, and call end date and time.");
      System.exit(1);
    } else if (args.length == 4){
      System.err.println("Missing call start time, and call end date and time.");
      System.exit(1);
    } else if (args.length == 5){
      System.err.println("Missing call end date and time.");
      System.exit(1);
    } else if (args.length == 6){
      System.err.println("Missing call end time.");
      System.exit(1);
    } else if (args.length > 8){
      System.err.println("Too many command line arguments.");
      System.exit(1);
    } else if (args.length == 7){
      PhoneCall call = new PhoneCall(args[1], args[2], args[3],
              args[4], args[5], args[6]);
        PhoneBill bill = new PhoneBill(args[0]);
        bill.addPhoneCall(call);
    } else {
      System.err.println("Command Line Error: Review command line arguments.");
      System.exit(1);
    }

  }

}