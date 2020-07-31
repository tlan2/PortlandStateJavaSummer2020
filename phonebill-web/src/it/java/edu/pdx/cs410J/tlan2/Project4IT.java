package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.tlan2.PhoneBillRestClient.PhoneBillRestException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testng.reporters.jq.Main;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;



/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllPhoneBills();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void test2PrintsCommandLineInterface() {
        String customer = "Customer";
        String caller = "123-456-7890";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer);
        assertThat(result.getTextWrittenToStandardOut(), containsString("-host hostname"));
    }

    @Test
    public void test3UnknownPhoneBillIssueUnknownPhoneBillError() throws Throwable {
        String customer = "Customer";
        MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, customer);
        assertThat(result.getTextWrittenToStandardError(), containsString("No phone bill for customer " + customer));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void test4AddPhoneCall() {
        String customer = "testCustomer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/2004";
        String startTime = "11:45";
        String startAMPM = "AM";
        String endDate = "01/02/2004";
        String endTime = "12:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer, caller,
                                                callee, startDate, startTime, startAMPM, endDate, endTime,
                                                endAMPM);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("Phone call added to customer testCustomer"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test5AddAnotherPhoneCall() {
        String customer = "testCustomer";
        String caller2 = "111-111-1111";
        String callee2 = "222-222-2222";
        String startDate2 = "1/15/2020";
        String startTime2 = "1:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/15/2020";
        String endTime2 = "2:00";
        String endAMPM2 = "pm";

        MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, customer, caller2,
                callee2, startDate2, startTime2, startAMPM2, endDate2, endTime2,
                endAMPM2);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("Phone call added to customer testCustomer"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test6PhoneBillIsPrettyPrinted() {
        String customer = "testCustomer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/2004";
        String startTime = "11:45";
        String startAMPM = "AM";
        String endDate = "01/02/2004";
        String endTime = "12:00";
        String endAMPM = "pm";

        String caller2 = "111-111-1111";
        String callee2 = "222-222-2222";
        String startDate2 = "1/15/2020";
        String startTime2 = "1:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/15/2020";
        String endTime2 = "2:00";
        String endAMPM2 = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer);
        assertThat(result.getExitCode(), equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString(customer));
        assertThat(pretty, containsString("  " + caller + " " + callee + " " +
                startDate + " " + startTime + " " + startAMPM + " " + endDate + " " + endTime +
                 " " + endAMPM));
        assertThat(pretty, containsString("  " + caller2 + " " + callee2 + " " +
                startDate2 + " " + startTime2 + " " + startAMPM2 + " " + endDate2 + " " + endTime2 +
                " " + endAMPM2));
    }

    @Test
    public void test7PrintReadMe() {
        MainMethodResult result = invokeMain( Project4.class,"-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project 4"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test8MissingCustomerName() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing customer name."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test9MissingCalleeNumber() {
        String customer = "Customer";
        String caller = "234-567-8901";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing callee number."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test10MissingCallStartDate() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller, callee);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call start date."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test11MissingCallStartTime() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller, callee, startDate);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call start time."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test12MissingCallStartAMPM() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        String startTime = "11:45";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller,
                callee, startDate, startTime);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call start time AM/PM designation."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test13MissingCallEndDate() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        String startTime = "11:45";
        String startAMPM = "AM";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller,
                callee, startDate, startTime, startAMPM);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call end date."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test14MissingCallEndTime() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        String startTime = "11:45";
        String startAMPM = "AM";
        String endDate = "01/02/04";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller,
                callee, startDate, startTime, startAMPM, endDate);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call end time."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test15MissingCallEndAMPM() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        String startTime = "11:45";
        String startAMPM = "AM";
        String endDate = "01/02/04";
        String endTime = "12:00";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller,
                callee, startDate, startTime, startAMPM, endDate, endTime);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call end time AM/PM designation."));
        assertThat(result.getExitCode(),equalTo(1));
    }
}