package edu.pdx.cs410J.tlan2;

import org.junit.Test;
import org.w3c.dom.Text;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the {@link PhoneBill} class.
 */

public class PhoneBillTest {

    private PhoneCall createValidPhoneCall1() {
        return new PhoneCall("503-755-6509", "617-703-7433",
                "1/15/2020", "19:39", "1/15/2020", "20:00");
    }

    private PhoneCall createValidPhoneCall2() {
        return new PhoneCall("305-667-3094", "503-867-5309",
                "01/20/2020", "10:50", "1/2/2020", "11:50");
    }
    private PhoneCall callValid1 = createValidPhoneCall1();
    private PhoneCall callValid2 = createValidPhoneCall2();

    private PhoneBill createBillTed() {
        return new PhoneBill("Ted");
    }
    private PhoneBill billTed = createBillTed();

    @Test
    public void onePhoneCallAddedToPhoneBill() {
        billTed.addPhoneCall(callValid1);
        assertThat(billTed.toString(), containsString("1 phone calls"));
    }

    @Test
    public void multiplePhoneCallsAddedToPhoneBill() {
        billTed.addPhoneCall(callValid1);
        billTed.addPhoneCall(callValid2);
        assertThat(billTed.toString(), containsString("2 phone calls"));
    }

    @Test
    public void phoneBillHasCustomerName() {
        assertThat(billTed.toString(), containsString("Ted"));
    }

}
