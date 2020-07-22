package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    // ======== VARIABLES ========
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
    }

    private String printMainMenu() {
        return "\nusage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>" +
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
                "\n\t-README\t\t\t\tPrints a README for this project and exits" +
                "\n  Date and time should be in the format: mm/dd/yyyy hh:mm";
    }

    private MainMethodResult validCLIWithNoOptions() {
        return invokeMain(
                "Brian Griffin", "503-635-7887", "503-755-2311",
                "01/15/2020", "07:39", "PM", "01/15/2020", "9:39", "pM");
    }

    private MainMethodResult validPrintNoTextFile() {
        return invokeMain("-print","Brian Griffin", "111-111-1111", "222-222-2222",
                "01/15/2020", "7:39", "Pm", "01/15/2020", "21:00", "pM");
    }

    String PATH = "C:\\Users\\thoma\\Documents\\6. Summer 2020" +
            "\\1. Advanced Java\\PortlandStateJavaSummer2020\\phonebill" +
            "\\src\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

// =========== TESTS ===================
    @Test
    public void WritePhoneCallToNewFileWithPrintFirst(){
        MainMethodResult result = invokeMain("-print", "-textFile", PATH + "Bob.txt",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");
        File file = new File(PATH + "Bob.txt");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 234-567-8901 to 123-456-7890 from 1/1/20, 12:00 AM to 1/1/20, 1:00 AM"));
        assertThat(file.exists(), equalTo(true));
        assertThat(file.length() > 0, equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
        file.delete();
    }

    @Test
    public void WritePhoneCallToNewFileWithPrintThird(){
        MainMethodResult result = invokeMain("-textFile", PATH + "Bob.txt", "-print",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");
        File file = new File(PATH + "Bob.txt");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 234-567-8901 to 123-456-7890 from 1/1/20, 12:00 AM to 1/1/20, 1:00 AM"));
        assertThat(file.exists(), equalTo(true));
        assertThat(file.length() > 0, equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
        file.delete();
    }

    @Test
    public void WritePhoneCallToNewFileOnly(){
        MainMethodResult result = invokeMain("-textFile", PATH + "Bob.txt", "-print",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");
        File file = new File(PATH + "Bob.txt");

        assertThat(file.exists(), equalTo(true));
        assertThat(file.length() > 0, equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
        file.delete();
    }

    @Test
    public void AddPhoneCallToExistingFileWithPrintFirst() throws IOException {
        MainMethodResult result = invokeMain("-print", "-textFile", PATH + "existingFile.txt",
                "Will", "234-567-8901", "123-456-7890", "01/01/2020","00:00", "AM",
                "01/01/2020", "01:00", "AM");

        File file = new File(PATH + "existingFile.txt");
        StringBuilder fromFile = new StringBuilder();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            fromFile.append(line);
            fromFile.append("\n");
        }
        myReader.close();

        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 234-567-8901 to 123-456-7890 from 1/1/20, 12:00 AM to 1/1/20, 1:00 AM"));
        assertThat(fromFile.toString(), containsString(
                "234-567-8901 123-456-7890 1/1/20, 12:00 AM 1/1/20, 1:00 AM"));
        assertThat(file.exists(), equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void AddPhoneCallToExistingFileWithPrintThird() throws IOException {
        MainMethodResult result = invokeMain("-textFile", PATH + "existingFileOnly.txt", "-print",
                "Will", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");

        File file = new File(PATH + "Will.txt");
        StringBuilder fromFile = new StringBuilder();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            fromFile.append(line);
            fromFile.append("\n");
        }
        myReader.close();

        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 234-567-8901 to 123-456-7890 from 1/1/20, 12:00 AM to 1/1/20, 1:00 AM"));
        assertThat(fromFile.toString(), containsString(
                "234-567-8901 123-456-7890 1/1/20, 12:00 AM 1/1/20, 1:00 AM"));
        assertThat(file.exists(), equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void AddPhoneCallToExistingFileOnly() throws IOException {
        MainMethodResult result = invokeMain("-textFile", PATH + "existingFileOnly.txt",
                "Will", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");

        File file = new File(PATH + "existingFileOnly.txt");
        StringBuilder fromFile = new StringBuilder();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            fromFile.append(line);
            fromFile.append("\n");
        }
        myReader.close();

        assertThat(fromFile.toString(), containsString("234-567-8901 123-456-7890 1/1/20, " +
                                                                "12:00 AM 1/1/20, 1:00 AM"));
        assertThat(file.exists(), equalTo(true));
//        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void FileNameDoesNotMatchCustomerNameArgument(){
        MainMethodResult result = invokeMain("-print", "-textFile", PATH + "Will.txt",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","12:00", "AM",
                "01/01/2020", "01:00", "AM");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error: Customer name inputted does not match " +
                "customer name on file."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void printCommandLineInterface(){
        MainMethodResult result = invokeMain("Bob", "234-567-8901", "123-456-7890",
                                                    "01/01/2020","00:00", "am", "01/01/2020", "01:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("-pretty file"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString("\nMissing command line arguments"));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void tooManyCommandLineArguments(){
        MainMethodResult result = invokeMain("-pretty", "fileName","-textFile", "otherFileName",
                                                    "-print","Brian Griffin", "103-675-7827", "503-755-2311",
                                                    "01/15/2020", "7:39", "pm", "01/15/2020", "21:39", "PM",
                                                    "extraCommand1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command line arguments."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void printPhoneCallDetails() {
        MainMethodResult result = invokeMain("-print","Bob", "234-567-8901", "123-456-7890",
                "01/01/2020","00:00", "am", "01/01/2020", "01:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from 234-567-8901 to " +
                "123-456-7890 from 1/1/20, 12:00 AM to 1/1/20, 1:00 AM"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    public void missingCallerNumberPlus() {
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing callerNumber, calleeNumber, call start and call end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingCalleeNumberPlus() {
        MainMethodResult result = invokeMain( "Brian Griffin", "503-655-9775");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing calleeNumber, call start and call end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingStartDatePlus() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call start and call end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingStartTimePlus() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020",
                "09/21/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call start time/AM/PM, and call end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingStartAMPM() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020",
                "09/21/2020",
                "15:13");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call start AM/PM and end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    public void missingEndDateTimeAMPM() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775", "503-888-2020",
                "09/21/2020", "15:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call end date/time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingEndTimeAMPM() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775", "503-888-2020",
                "09/21/2020", "15:13", "am", "09/21/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call end time/AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingEndAMPM() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775", "503-888-2020",
                "09/21/2020", "03:13", "am", "09/21/2020", "4:13");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call end AM/PM."));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    public void printReadMe() {
        MainMethodResult result = invokeMain("-README", "Brian Griffin", "503-655-9775",
                                            "503-888-2020", "09/21/2020", "15:13", "PM", "9/21/2020", "15:20", "PM");

        assertThat(result.getTextWrittenToStandardOut(), containsString("Tom Lancaster - Project 3"));
    }

    @Test
    public void prettyOptionOutOfPosition() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775",
                "503-888-2020", "09/21/2020", "-pretty", "3:13", "PM", "9/21/2020", "3:20", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Error: -pretty option out of position. Put in the beginning of input."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void textFileOptionOutOfPosition() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775",
                "503-888-2020", "09/21/2020", "-textFile", "3:13", "PM", "9/21/2020", "3:20", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Error: -textFile option out of position. Put in the beginning of input."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void printOptionOutOfPosition() {
        MainMethodResult result = invokeMain("Brian Griffin", "503-655-9775",
                "503-888-2020", "09/21/2020", "3:13", "-print", "PM", "9/21/2020", "3:20", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Error: -print option out of position. Put in the beginning of input."));
        assertThat(result.getExitCode(), equalTo(1));
    }
}
