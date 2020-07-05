package edu.pdx.cs410J.tlan2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

//  @Test(expected = UnsupportedOperationException.class)
//  public void getStartTimeStringNeedsToBeImplemented() {
//    PhoneCall call = new PhoneCall("Ted","305-667-3094",
//                                    "Fred", "503-867-5309",
//                                    "start", "end");
//    call.getStartTimeString();
//  }

//  @Test
//  public void initiallyAllPhoneCallsHaveTheSameCallee() {
//    PhoneCall call = new PhoneCall("Ted","305-667-3094",
//            "Fred", "503-867-5309",
//            "start", "end");
//    assertThat(call.getCallee(), containsString("not implemented"));
//  }

  private PhoneCall createCallWithNoDates() {
    return new PhoneCall("Ted", "305-667-3094",
            "Fred", "503-867-5309",
            "start", "end");
  }

  private PhoneCall createValidPhoneCall1() {
    return new PhoneCall("Larry","503-755-6509",
            "Betty", "617-703-7433",
            "1/15/2020 19:39", "1/15/2020 20:00");
  }

  private PhoneBill createBillTed() {
    return new PhoneBill("Ted");
  }

  private PhoneCall callWithNoDates = createCallWithNoDates();
  private PhoneCall callValid1 = createValidPhoneCall1();
  private PhoneBill billTed = createBillTed();

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    assertThat(callWithNoDates.getStartTime(), is(nullValue()));
  }

  @Test
  public void toStringForExampleInAssignment() {
    assertThat(callWithNoDates.toString(), equalTo("Phone call from Ted to Fred from start to end"));
  }

  @Test
  public void onePhoneCallAddedToPhoneBill() {
    billTed.addPhoneCall(callValid1);
    assertThat(billTed.toString(), containsString("1 phone calls"));
  }

  @Test
  public void multiplePhoneCallsAddedToPhoneBill() {
   billTed.addPhoneCall(callWithNoDates);
   billTed.addPhoneCall(callValid1);
    assertThat(billTed.toString(), containsString("2 phone calls"));
  }

  @Test
  public void phoneBillHasCustomerName() {
    assertThat(billTed.toString(), containsString("Ted"));
  }

}
