package edu.pdx.cs410J.tlan2;

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
  public static void main(String[] args) {
    printMainMenu();

    PhoneCall call = new PhoneCall(args[1], args[2], args[3], args[4],
                                  args[5], args[6]);
    PhoneBill bill = new PhoneBill(args[0]);
    bill.addPhoneCall(call);

    if(args.length == 0) {
      System.err.println("Missing command line arguments");
    }
//    } else if()
    System.exit(1);
  }

}