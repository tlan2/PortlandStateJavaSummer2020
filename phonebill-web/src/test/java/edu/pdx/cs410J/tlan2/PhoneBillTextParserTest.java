package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static edu.pdx.cs410J.tlan2.PhoneBillURLParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillTextParser}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillTextParserTest {

    @Test
    public void tpTest0AddMultiplePhoneCalls(){
        String customer = "tpTestCustomer";
        String callerPhoneNumber1 = "503-123-4567";
        String calleePhoneNumber1 = "456-789-0123";
        String startDate1 = "1/1/2020";
        String startTime1 = "1:00";
        String startAMPM1 = "AM";
        String endDate1 = "1/1/2020";
        String endTime1 = "2:00";
        String endAMPM1 = "AM";

        String callerPhoneNumber2 = "503-123-4567";
        String calleePhoneNumber2 = "456-789-0123";
        String startDate2 = "1/15/2020";
        String startTime2 = "5:00";
        String startAMPM2 = "PM";
        String endDate2 = "1/17/2020";
        String endTime2 = "6:00";
        String endAMPM2 = "PM";

        PhoneCall newCall1 = new PhoneCall(callerPhoneNumber1, calleePhoneNumber1, startDate1,startTime1, startAMPM1,
                endDate1, endTime1, endAMPM1);
        PhoneCall newCall2 = new PhoneCall(callerPhoneNumber2, calleePhoneNumber2, startDate2, startTime2, startAMPM2,
                endDate2, endTime2, endAMPM2);

        String content = customer + "\n" + newCall1.getAllCallInfo() + "\n" + newCall2.getAllCallInfo();

        PhoneBillTextParser tp = new PhoneBillTextParser(new StringReader(content));

        PhoneBill bill;

        try {
            bill = tp.parse();
            assertThat(bill.getCustomer(), containsString(customer));
            assertThat(bill.toString(), containsString("2 phone calls"));
        } catch (ParserException e) {
            System.err.println("Test Error: While parsing....");
        }
    }

    @Test
    public void tpTest1ReturnCustomerOnly(){
        String customer = "tpTestCustomer";

        PhoneBillTextParser tp = new PhoneBillTextParser(new StringReader(customer));

        PhoneBill bill;

        try {
            bill = tp.parse();
            assertThat(bill.getCustomer(), containsString(customer));
            assertThat(bill.toString(), containsString("0 phone calls"));
        } catch (ParserException e) {
            System.err.println("Test Error: While parsing....");
        }
    }

    @Test (expected = ParserException.class)
    public void tpTest2PhoneCallHasNullValues() throws ParserException {
        String customer = "tpTestCustomer";
        String callerPhoneNumber1 = "503-123-4567";
        String calleePhoneNumber1 = "456-789-0123";
        String startDate1 = "1/1/2020";
        String startTime1 = "1:00";
        String startAMPM1 = "AM";
        String endDate1 = null;
        String endTime1 = "2:00";
        String endAMPM1 = null;

        String content = customer + "\n" +
                callerPhoneNumber1 + " " + calleePhoneNumber1 + " " + startDate1 + " " + startTime1 +
                " " + startAMPM1 + " " + endDate1 + " " + endTime1 + " " + endAMPM1;

        PhoneBillTextParser tp = new PhoneBillTextParser(new StringReader(content));

        PhoneBill bill = tp.parse();
    }

}
