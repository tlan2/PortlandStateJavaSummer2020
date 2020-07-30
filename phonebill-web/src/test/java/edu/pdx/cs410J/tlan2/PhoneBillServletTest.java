package edu.pdx.cs410J.tlan2;

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
import java.io.StringWriter;

import static edu.pdx.cs410J.tlan2.PhoneBillURLParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void pbsTest0requestWithNoCustomerReturnMissingParameter() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(CUSTOMER_PARAMETER));
  }

  @Test
  public void pbsTest1requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException{
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    String customerName = "Dave ";
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customerName);

    HttpServletResponse response = mock(HttpServletResponse.class);
    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));

  }

  @Test
  public void pbsTest2addPhoneCallToBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Customer";
    String callerPhoneNumber = "503-123-4567";
    String calleePhoneNumber = "456-789-0123";
    String startCallInfo = "1/1/2020 1:00 AM";
    String endCallInfo = "1/1/2020 2:00 AM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(callerPhoneNumber);
    when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(calleePhoneNumber);
    when(request.getParameter(START_CALL_PARAMETER)).thenReturn(startCallInfo);
    when(request.getParameter(END_CALL_PARAMETER)).thenReturn(endCallInfo);

    HttpServletResponse response = mock(HttpServletResponse.class);

    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(pw, times(0)).println(Mockito.any(String.class) );
    verify(response).setStatus(HttpServletResponse.SC_OK);

    PhoneBill phoneBill = servlet.getPhoneBill(customer);
    assertThat(phoneBill, notNullValue());
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(callerPhoneNumber));
  }

  @Test
  public void pbsTest3requestingExistingPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {
    String customer = "Customer";
    String callerPhoneNumber = "503-123-4567";
    String calleePhoneNumber = "456-789-0123";
    String startDate = "1/1/2020";
    String startTime = "1:00";
    String startAMPM = "AM";
    String endDate = "1/1/2020";
    String endTime = "2:00";
    String endAMPM = "AM";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(callerPhoneNumber, calleePhoneNumber, startDate, startTime, startAMPM,
            endDate, endTime, endAMPM));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBill = sw.toString();
    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(callerPhoneNumber));
    assertThat(textPhoneBill, containsString(calleePhoneNumber));
    assertThat(textPhoneBill, containsString(startDate));
    assertThat(textPhoneBill, containsString(startTime));
    assertThat(textPhoneBill, containsString(startAMPM));
    assertThat(textPhoneBill, containsString(endDate));
    assertThat(textPhoneBill, containsString(endTime));
    assertThat(textPhoneBill, containsString(endAMPM));

  }

  @Test
  public void pbsTest3requestingExistingPhoneBillDumpsItToPrintWriterWithMultipleCalls()
          throws IOException, ServletException {
    String customer = "Customer";
    String callerPhoneNumber = "503-123-4567";
    String calleePhoneNumber = "456-789-0123";
    String startDate = "1/1/2020";
    String startTime = "1:00";
    String startAMPM = "AM";
    String endDate = "1/1/2020";
    String endTime = "2:00";
    String endAMPM = "AM";

    String callerPhoneNumber2 = "111-111-1111";
    String calleePhoneNumber2 = "222-222-2222";
    String startDate2 = "1/15/2020";
    String startTime2 = "1:00";
    String startAMPM2 = "PM";
    String endDate2 = "1/15/2020";
    String endTime2 = "2:00";
    String endAMPM2 = "pm";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(callerPhoneNumber, calleePhoneNumber, startDate, startTime, startAMPM,
            endDate, endTime, endAMPM));
    bill.addPhoneCall(new PhoneCall(callerPhoneNumber2, calleePhoneNumber2, startDate2, startTime2, startAMPM2,
            endDate2, endTime2, endAMPM2));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBill = sw.toString();
    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(callerPhoneNumber));
    assertThat(textPhoneBill, containsString(calleePhoneNumber));
    assertThat(textPhoneBill, containsString(startDate));
    assertThat(textPhoneBill, containsString(startTime));
    assertThat(textPhoneBill, containsString(startAMPM));
    assertThat(textPhoneBill, containsString(endDate));
    assertThat(textPhoneBill, containsString(endTime));
    assertThat(textPhoneBill, containsString(endAMPM));

    assertThat(textPhoneBill, containsString(callerPhoneNumber2));
    assertThat(textPhoneBill, containsString(calleePhoneNumber2));
    assertThat(textPhoneBill, containsString(startDate2));
    assertThat(textPhoneBill, containsString(startTime2));
    assertThat(textPhoneBill, containsString(startAMPM2));
    assertThat(textPhoneBill, containsString(endDate2));
    assertThat(textPhoneBill, containsString(endTime2));
    assertThat(textPhoneBill, containsString(endAMPM2));

  }
}
