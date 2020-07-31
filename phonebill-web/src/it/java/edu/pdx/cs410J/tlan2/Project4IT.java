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

//    @Ignore
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
        String customer = "testCustomer";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer);
        assertThat(result.getTextWrittenToStandardOut(), containsString("-host hostname"));
    }

    @Test
    public void test3PrintReadMe() {
        MainMethodResult result = invokeMain( Project4.class,"-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project 4"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test4UnknownPhoneBillIssueUnknownPhoneBillError() throws Throwable {
        String customer = "testCustomer";
        MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, customer);
        assertThat(result.getTextWrittenToStandardError(), containsString("No phone bill for customer " + customer));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void test5AddPhoneCall() {
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
    public void test6PrintAndAddPhoneCall() {
        String customer = "testCustomer";
        String caller2 = "111-111-1111";
        String callee2 = "222-222-2222";
        String startDate2 = "1/15/2004";
        String startTime2 = "1:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/15/2004";
        String endTime2 = "2:00";
        String endAMPM2 = "pm";

        MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, "-print", customer, caller2,
                callee2, startDate2, startTime2, startAMPM2, endDate2, endTime2,
                endAMPM2);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("Phone call added to customer testCustomer"));
        assertThat(out, containsString("Phone call from " + caller2 + " to " + callee2 + " from " +
                startDate2 + " " + startTime2 + " " + startAMPM2 + " to " + endDate2 + " " + endTime2 + " " + endAMPM2));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test7PhoneBillIsPrettyPrinted() {
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
        String startDate2 = "1/15/2004";
        String startTime2 = "1:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/15/2004";
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
    public void test8AddCallsAndSearch() {
//        String customer = "testCustomer";
//        String caller = "234-567-8901";
//        String[] callee = new String[]{"333-333-3333", "444-444-4444","555-555-5555",
//        "777-777-7777", "888-888-8888", "999-999-9999", "101-101-1010"};
//        String[] startDate = new String[]{"3/3/2004", "4/4/2004","5/5/2004","7/7/2004","8/8/2004","9/9/2004","10/10/2004"};
//        String[] startTime = new String[]{"3:00", "4:00", "5:00", "7:00", "8:00", "9:00", "10:00"};
//        String[] startAMPM = new String[]{"pm","am","pm","am","pm","am","pm"};
//        String[] endDate = new String[]{"3/3/2004", "4/4/2004","5/5/2004","7/7/2004","8/8/2004","9/9/2004","10/10/2004"};
//        String[] endTime = new String[]{"4:00","5:00", "7:00", "8:00", "9:00", "10:00", "11:00"};
//        String[] endAMPM = new String[]{"pm","am","pm","am","pm","am","pm"};
//        int numberOfCalls = 7;

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
        String startDate2 = "1/15/2004";
        String startTime2 = "1:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/15/2004";
        String endTime2 = "2:00";
        String endAMPM2 = "pm";

        String searchStartDate = "1/1/2004";
        String searchStartTime = "12:00";
        String searchStartAMPM = "AM";
        String searchEndDate = "1/14/2004";
        String searchEndTime = "11:59";
        String searchEndAMPM = "PM";

//        // Add 6 calls
//        for (int i=0; i < numberOfCalls; i++){
//           invokeMain(Project4.class, "-hostname", HOSTNAME, "-port", PORT, customer,
//                    caller, callee[i], startDate[i], startTime[i], startAMPM[i], endDate[i], endTime[i], endAMPM[i]);
//        }

        MainMethodResult result = invokeMain(Project4.class, "-hostname", HOSTNAME, "-port", PORT, "-search",
                customer, searchStartDate, searchStartTime, searchStartAMPM, searchEndDate, searchEndTime, searchEndAMPM);

        assertThat(result.getExitCode(), equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString(customer));

        assertThat(pretty, containsString("  " + caller + " " + callee + " " +
                startDate + " " + startTime + " " + startAMPM + " " + endDate + " " + endTime +
                " " + endAMPM));

//        assertThat(pretty, containsString("  " + caller2 + " " + callee2 + " " +
//                startDate2 + " " + startTime2 + " " + startAMPM2 + " " + endDate2 + " " + endTime2 +
//                " " + endAMPM2));

//        for(int j=2; j < 5; j++){
//            assertThat(pretty, containsString("  " + caller + " " + callee[j] + " " +
//                    startDate[j] + " " + startTime[j] + " " + startAMPM[j] + " " + endDate[j] + " " +
//                    endTime[j] + " " + endAMPM[j]));
//        }
    }

    @Test
    public void test40MissingCustomerName() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing customer name."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test41MissingCalleeNumber() {
        String customer = "Customer";
        String caller = "234-567-8901";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing callee number."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test42MissingCallStartDate() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller, callee);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call start date."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test43MissingCallStartTime() {
        String customer = "Customer";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/02/04";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customer,caller, callee, startDate);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing call start time."));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test44MissingCallStartAMPM() {
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
    public void test45MissingCallEndDate() {
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
    public void test46MissingCallEndTime() {
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
    public void test47MissingCallEndAMPM() {
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