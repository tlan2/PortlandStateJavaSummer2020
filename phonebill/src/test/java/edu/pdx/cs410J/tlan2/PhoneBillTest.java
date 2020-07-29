package edu.pdx.cs410J.tlan2;

import org.junit.Test;
import org.w3c.dom.Text;

import java.util.SortedSet;

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
                "1/15/2020", "19:39","pm", "1/15/2020", "20:00", "pm");
    }

    private PhoneCall createValidPhoneCall2() {
        return new PhoneCall("305-667-3094", "503-867-5309",
                "01/20/2020", "10:50", "am","1/2/2020", "11:50", "am");
    }

    private PhoneCall createSameDate1(){
        return new PhoneCall("123-456-7890", "234-567-8901", "1/1/2020",
                "12:00","am", "1/1/2020", "01:00", "AM");
    }

    private PhoneCall createSameDate2(){
        return new PhoneCall("456-789-0123", "234-567-8901", "1/1/2020",
                "12:00","am", "1/1/2020", "01:00", "AM");
    }

    private PhoneCall createValidCall3() {
        return new PhoneCall("503-787-9988", "503-235-7821", "1/2/2020",
                        "01:00", "pm", "1/2/2020", "02:00", "PM");
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

    @Test
    public void returnPhoneCallsInOrder() {
        billTed.addPhoneCall(callValid1);
        billTed.addPhoneCall(callValid2);
        billTed.addPhoneCall(createValidCall3());
        billTed.addPhoneCall(createSameDate1());
        billTed.addPhoneCall(createSameDate2());

        SortedSet<PhoneCall> calls =  billTed.sortPhoneCalls();
        StringBuilder sb = new StringBuilder();

        for (PhoneCall c: calls){
            sb.append(c.toString());
            sb.append("\n");
        }
        assertThat(sb.toString(), containsString("Phone call from 305-667-3094 to 503-867-5309 from 1/20/20, 10:50 AM to 1/2/20, 11:50 AM\n"));
    }

}
