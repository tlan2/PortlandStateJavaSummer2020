package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project2IT extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    // ======== VARIABLES ========
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project2.class, args);
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
                "\n\t-textFile file\t\t\tWhere to read/write the phone bill" +
                "\n\t-print\t\t\t\tPrints a description of the new phone call" +
                "\n\t-README\t\t\t\tPrints a README for this project and exits" +
                "\n  Date and time should be in the format: mm/dd/yyyy hh:mm";
    }

    private MainMethodResult validCLIWithNoOptions() {
        return invokeMain(
                "Brian Griffin", "503-635-7887", "503-755-2311",
                "01/15/2020", "19:39", "01/15/2020", "21:39");
    }

    private MainMethodResult validPrintNoTextFile() {
        return invokeMain(Project1.class,"-print","Brian Griffin", "111-111-1111", "222-222-2222",
                "01/15/2020", "19:39", "01/15/2020", "21:00");
    }

    String PATH = "C:\\Users\\thoma\\Documents\\6. Summer 2020" +
            "\\1. Advanced Java\\PortlandStateJavaSummer2020\\phonebill" +
            "\\src\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

// =========== TESTS ===================
    @Test
    public void WriteNewCallToPhoneBillText(){
        MainMethodResult result = invokeMain("-textfile", "Bob",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","00:00",
                "01/01/2020", "01:00");
        File file = new File(PATH + "Bob.txt");
        assertThat(file.exists(), equalTo(true));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void printNewCallAndWritePhoneBillToText(){
        MainMethodResult result = invokeMain("-textfile", "Bob", "-print",
                "Bob", "234-567-8901", "123-456-7890", "01/01/2020","00:00",
                "01/01/2020", "01:00");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 234-567-8901 to 123-456-7890 from 01/01/2020 00:00 to 01/01/2020 01:00"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void printCommandLineInterface(){
        MainMethodResult result = validCLIWithNoOptions();
        assertThat(result.getTextWrittenToStandardOut(), containsString(printMainMenu()));
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
        MainMethodResult result = invokeMain("-textFile", "fileName", "-print","Brian Griffin", "103-675-7827", "503-755-2311",
                "01/15/2020", "19:39", "01/15/2020", "21:39", "extraCommand1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command line arguments."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void printsPhoneCallDetails(){
        MainMethodResult result = validPrintNoTextFile();
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call " +
                "from 111-111-1111 to 222-222-2222 from 01/15/2020 19:39 to 01/15/2020 21:00"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    public void missingCallerNumberPlus() {
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing callerNumber, calleeNumber, " +
                        "call start date and time, and call end date and time."));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingCalleeNumberPlus() {
        MainMethodResult result = invokeMain( "Brian Griffin", "503-655-9775");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing calleeNumber, " +
                        "call start date and time, and call end date and time."));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingStartDatePlus() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call start date and time, and call end date and time."));
    }
    @Test
    public void missingStartDatePlusExitCode(){
        MainMethodResult result = invokeMain("Brian Griffin","503-655-9775", "503-888-2020");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingStartTimePlus() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020",
                "09/21/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call start time, and call end date and time."));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingEndDateAndTime() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020",
                "09/21/2020",
                "15:13");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call end date and time."));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    public void missingEndTime() {
        MainMethodResult result = invokeMain("Brian Griffin",
                "503-655-9775",
                "503-888-2020",
                "09/21/2020",
                "15:13",
                "9/21/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing call end time."));
        assertThat(result.getExitCode(), equalTo(1));
    }






}
