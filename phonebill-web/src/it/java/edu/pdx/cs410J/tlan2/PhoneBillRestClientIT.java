package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static edu.pdx.cs410J.tlan2.PhoneBillURLParameters.CUSTOMER_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllPhoneBills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhoneBills();
  }

  @Test
  public void test1NonexistentPhoneBillThrowsException() throws IOException, ParserException{
    PhoneBillRestClient client = newPhoneBillRestClient();
    try{
      client.getPhoneBill("Dave");
      fail("Should have thrown a PhoneBillRestException");
    } catch (PhoneBillRestClient.PhoneBillRestException ex){
      assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
    }

  }

  @Ignore
  @Test
  public void test2AddPhoneCall() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String customer = "Customer";
    String caller = "123-456-7890";
    String callee = "789-012-3456";
    String sDate = "1/1/2020";
    String sTime = "1:00";
    String sAMPM = "am";
    String eDate = "1/1/2020";
    String eTime = "2:00";
    String eAMPM = "am";

    PhoneCall newCall = new PhoneCall(caller, callee, sDate, sTime, sAMPM, eDate, eTime, eAMPM);

    client.addPhoneCall(customer, newCall);

    PhoneBill phoneBill = client.getPhoneBill(customer);
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(caller));
  }

}
