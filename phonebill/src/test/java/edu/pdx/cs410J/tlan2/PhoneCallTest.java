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
  private PhoneCall callWithNoDates = new PhoneCall("Ted","305-667-3094",
                                                    "Fred", "503-867-5309",
                                                    "start", "end");

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    assertThat(callWithNoDates.getStartTime(), is(nullValue()));
  }

  @Test
  public void toStringForExampleInAssignment() {
    PhoneCall call = new PhoneCall("Ted","305-667-3094",
            "Fred", "503-867-5309",
            "start", "end");
    assertThat(callWithNoDates.toString(), equalTo("Phone call from Ted to Fred from start to end"));
  }

//  @Test
//  public void toString


  
}
