package edu.pdx.cs410J.tlan2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  private PhoneCall createValidPhoneCall1() {
    return new PhoneCall("503-755-6509", "617-703-7433",
            "1/15/2020", "19:39", "1/15/2020", "20:00");
  }

  private PhoneCall createValidPhoneCall2() {
    return new PhoneCall("305-667-3094", "503-867-5309",
            "01/20/2020", "10:50", "1/2/2020", "11:50");
  }

  private PhoneBill createBillTed() {
    return new PhoneBill("Ted");
  }

  private PhoneCall callValid2 = createValidPhoneCall2();
  private PhoneCall callValid1 = createValidPhoneCall1();
  private PhoneBill billTed = createBillTed();

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    assertThat(callValid2.getStartTime(), is(nullValue()));
  }

  @Test
  public void getCaller(){
    assertThat(callValid1.getCaller(), containsString("503-755-6509"));
  }

  @Test
  public void getCallee(){
    assertThat(callValid1.getCallee(), containsString("617-703-7433"));
  }

  @Test
  public void toStringForExampleInAssignment() {
    assertThat(callValid2.toString(), equalTo("Phone call from 305-667-3094 " +
                                                            "to 503-867-5309 from 01/20/2020 10:50 " +
                                                            "to 1/2/2020 11:50"));
  }

  @Test
  public void onePhoneCallAddedToPhoneBill() {
    billTed.addPhoneCall(callValid1);
    assertThat(billTed.toString(), containsString("1 phone calls"));
  }

  @Test
  public void multiplePhoneCallsAddedToPhoneBill() {
   billTed.addPhoneCall(callValid2);
   billTed.addPhoneCall(callValid1);
    assertThat(billTed.toString(), containsString("2 phone calls"));
  }

  @Test
  public void phoneBillHasCustomerName() {
    assertThat(billTed.toString(), containsString("Ted"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidCallerNumber() {
      PhoneCall newCall = new PhoneCall("x2x-y34-5z6", "503-635-2807", "01/01/20",
              "10:30", "1/1/20", "10:45");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidCalleeNumber() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-6a5-2807", "01/01/2020",
            "10:30", "1/1/2020", "10:45");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartDate() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "a/xx/2020",
            "10:30", "1/1/2020", "10:45");
  }

  @Test
  public void singleDigitMonthDate(){
    assertThat(callValid1.toString(), containsString("1/15/2020"));
  }

  @Test
  public void singleDigitDayDate(){
    assertThat(callValid2.toString(), containsString("1/2/2020"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidEndDate() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "10:30", "1/1/20", "10:45");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartTime() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "xx:3x", "1/1/20", "10:45");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidEndTime() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "1:30", "1/1/20", "aa:45");
  }


}
