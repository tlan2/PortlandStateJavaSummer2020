package edu.pdx.cs410J.tlan2;

import org.junit.Test;
import org.w3c.dom.Text;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  private PhoneCall createValidPhoneCall1() {
    return new PhoneCall("503-755-6509", "617-703-7433",
            "1/15/2020", "7:39", "pm","1/15/2020", "8:00", "pm");
  }

  private PhoneCall createValidPhoneCall2() {
    return new PhoneCall("305-667-3094", "503-867-5309",
            "01/20/2020", "10:50", "am", "1/2/2020", "11:50", "am");
  }

  private PhoneCall callValid1 = createValidPhoneCall1();
  private PhoneCall callValid2 = createValidPhoneCall2();


  @Test
  public void getCaller(){
    assertThat(callValid1.getCaller(), containsString("503-755-6509"));
  }

  @Test
  public void getCallee(){
    assertThat(callValid1.getCallee(), containsString("617-703-7433"));
  }

  @Test
  public void toStringCall() {
    assertThat(callValid2.toString(), equalTo("Phone call from 305-667-3094 " +
                                                            "to 503-867-5309 from 1/20/20, 10:50 AM " +
                                                            "to 1/2/20, 11:50 AM"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidCallerNumber() {
      PhoneCall newCall = new PhoneCall("x2x-y34-5z6", "503-635-2807", "01/01/20",
              "10:30", "am", "1/1/20", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidCalleeNumber() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-6a5-2807", "01/01/2020",
            "10:30", "am","1/1/2020", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartDate() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "a/xx/2020",
            "10:30", "am", "1/1/2020", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartTime() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "xx:3x", "am", "1/1/20", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartAMPM() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "10:30", "xx", "1/1/20", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidEndDate() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "10:30", "am", "1/1/20", "10:45", "am");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidEndTime() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "1:30", "pm","1/1/20", "aa:45", "PM");
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidEndAMPM() {
    PhoneCall newCall = new PhoneCall("123-334-576", "503-635-2807", "01/01/20",
            "1:30", "pm","1/1/20", "02:45", "xx");
  }


}
