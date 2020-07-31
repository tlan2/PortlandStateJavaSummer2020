package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        printCLI();

        String hostName = null;
        String portString = null;
        String customer = null;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String startAMPM = null;
        String endDate = null;
        String endTime = null;
        String endAMPM = null;
        Boolean print = false;
        Boolean isSearch = false;

        if(args.length == 0){
            usage( MISSING_ARGS );
        }

        for (String arg:args){
            if(arg.equals("-search")){
                isSearch = true;
            }
        }

        if(isSearch && args.length == 11) {
            System.out.println("-search option");

            for (String arg : args) {
                if (arg == null) {
                    usage("Missing \"-search\" option arguments.");
                }
            }
            //try catch
            hostName = args[1];
            portString = args[3];
            customer = args[5];
            startDate = args[6];
            startTime = args[7];
            startAMPM = args[8];
            endDate = args[9];
            endTime = args[10];
            endAMPM = args[11];

            int port;
            try {
                port = Integer.parseInt(portString);

            } catch (NumberFormatException ex) {
                usage("Port \"" + portString + "\" must be an integer");
                return;
            }

            PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

            String startDateTimeString = startDate + " " + startTime + " " + startAMPM;
            String endDateTimeString = endDate + " " + endTime + " " + endAMPM;

            Date minDate = stringToDateConverter(startDateTimeString);
            Date maxDate = stringToDateConverter(endDateTimeString);

            //search code
            try {
                PhoneBill clientBill = client.getPhoneBill(customer); //get mapped phonebill and parses it
                SortedSet<PhoneCall> sortedCalls = clientBill.sortedPhoneCalls();
                PhoneBill prettyPrintBill = new PhoneBill(customer);

                for (PhoneCall call : sortedCalls) {
                    Date beginDate = call.getStartTime();

                    if (beginDate.after(minDate) && beginDate.before(maxDate)) {
                        prettyPrintBill.addPhoneCall(call);
                        client.addPhoneCall(customer, call);
                    }
                }

                PrintWriter pw = new PrintWriter(System.out, true);
                PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(pw);
                pretty.dump(prettyPrintBill);

                System.exit(0);

            } catch (ParserException e) {
                System.err.println("\nCould not parse response!\n");
                System.exit(1);
            } catch (PhoneBillRestClient.PhoneBillRestException ex) {
                switch (ex.getHttpStatusCode()) {
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        System.err.print("\nNo phone bill for customer " + customer + "\n");
                        System.exit(1);
                        return;
                    default:
                }
            } catch (IOException ex) {
                error("While contacting server: " + ex);
                return;
            }
        }

        for (String arg : args) {
            if(arg.equals("-README")){
                printReadMe();
                System.exit(0);
            } else if(arg.equals("-print")){
                print = true;
            }
            else if (hostName == null) {
                hostName = arg;
            } else if ( portString == null) {
                portString = arg;
            } else if (customer == null) {
                customer = arg;
            } else if (caller == null) {
                caller = arg;
            } else if (callee == null) {
                callee = arg;
            } else if (startDate == null) {
                startDate = arg;
            } else if (startTime == null) {
                startTime = arg;
            } else if (startAMPM == null) {
                startAMPM = arg;
            } else if (endDate == null) {
                endDate = arg;
            } else if (endTime == null) {
                endTime = arg;
            } else if (endAMPM == null) {
                endAMPM = arg;
            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

//        String message;
        try {
            if (customer == null) {
                usage("\nMissing customer name.\n");
            } else if (caller == null) {
                // Pretty Print result
                try {
                    PhoneBill bill = client.getPhoneBill(customer); //get mapped phonebill and parses it
                    PrintWriter pw = new PrintWriter(System.out, true);
                    PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(pw);
                    pretty.dump(bill);

                    System.exit(0);

                } catch (ParserException e) {
                    System.err.println("\nCould not parse response!\n");
                    System.exit(1);
                } catch (PhoneBillRestClient.PhoneBillRestException ex){
                    switch (ex.getHttpStatusCode()) {
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            System.err.print("\nNo phone bill for customer " + customer + "\n");
                            System.exit(1);
                            return;
                        default:
                    }
                }
            } else if (callee == null){
                usage("\nMissing callee number.\n");
            } else if (startDate == null){
                usage("\nMissing call start date.\n");
            } else if (startTime == null){
                usage("\nMissing call start time.\n");
            } else if (startAMPM == null){
                usage("\nMissing call start time AM/PM designation.\n");
            } else if (endDate == null){
                usage("\nMissing call end date.\n");
            } else if (endTime == null){
                usage("\nMissing call end time.\n");
            } else if (endAMPM == null){
                usage("\nMissing call end time AM/PM designation.\n");
            } else {
                PhoneCall newCall = new PhoneCall(caller, callee, startDate, startTime, startAMPM,
                        endDate, endTime, endAMPM);
                if(print){
                    System.out.println("\n" + newCall.toString() + "\n");
                }
                System.out.println("proj4 - callInfo = " + newCall.getAllCallInfo() );
                client.addPhoneCall(customer, newCall);
                System.out.println("\nPhone call added to customer " + customer);
//                message = Messages.customerNumberIs(customer, caller);
            }
        } catch (IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }
        System.exit(0);
    }

    private static void printReadMe() {
        System.out.println("Tom Lancaster - Project 4" +
                "\nThis program is a RESTful application of our phone bill application." +
                "\nYou can add phone calls to various customers and " +
                "\nsearch for phone calls between two dates. In addtion, you can" +
                "\nconnect to a host server and port and print out the phone call details.");
    }

    /**
     *
     */
    private static void printCLI() {
        System.out.println("\nusage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>" +
                "\n  args are (in this order):" +
                "\n\tcustomer\t\t\tPerson whose phone bill we're modeling" +
                "\n\tcallerNumber\t\t\tPhone number of caller" +
                "\n\tcalleeNumber\t\t\tPhone number of person who was called" +
                "\n\tstart\t\t\t\tDate and time call began"+
                "\n\tend\t\t\t\tDate and time call ended" +
                "\n  options are (options may appear in any order) :" +
                "\n\t-host hostname\t\t\tHost computer on which the server runs" +
                "\n\t-port port\t\t\tPort on which the server is listening" +
                "\n\t-search\t\t\t\tPhone calls should be searched for" +
                "\n\t-print\t\t\t\tPrints a description of the new phone call" +
                "\n\t-README\t\t\t\tPrints a README for this project and exits");

    }

    private static Date stringToDateConverter(String dateString) {
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            date = format.parse(dateString);
        } catch (ParseException ex) {
            System.err.println("\nInvalid start date and/or time format. " +
                    "Correct format: mm/dd/yyyy nn:nn am/pm.");
            System.exit(1);
        }

        return date;
    }


    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("\n** " + message);
        err.println();
        err.println();
        System.exit(1);
    }
}