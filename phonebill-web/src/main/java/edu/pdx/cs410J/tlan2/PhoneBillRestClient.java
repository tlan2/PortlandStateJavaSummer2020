package edu.pdx.cs410J.tlan2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.tlan2.PhoneBillURLParameters.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all dictionary entries from the server
     */
    public Map<String, String> getAllPhoneBills() throws IOException {
      Response response = get(this.url, Map.of());
      return Messages.parseDictionary(response.getContent());
    }

    /**
     * Returns the phone bill for the given customer
     */
    public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
      Response response = get(this.url, Map.of(CUSTOMER_PARAMETER, customer));
      throwExceptionIfNotOkayHttpStatus(response);
      String content = response.getContent();
//      System.out.println("restClient-content = " + content);
      PhoneBillTextParser parser = new PhoneBillTextParser(new StringReader(content));
      return parser.parse();
    }

    public void addPhoneCall(String customer, PhoneCall call) throws IOException {
        String sDateTime = call.getStartTimeString().replace(" ", "+");
//        System.out.println("restClient- sDateTime = " + sDateTime);
        String eDateTime = call.getEndTimeString().replace(" ", "+");
//        System.out.println("restClient- eDateTime = " + eDateTime);
        Response response = postToMyURL(Map.of(CUSTOMER_PARAMETER, customer, CALLER_NUMBER_PARAMETER, call.getCaller(),
                CALLEE_NUMBER_PARAMETER, call.getCallee(), START_CALL_PARAMETER, sDateTime,
                END_CALL_PARAMETER, eDateTime));

        throwExceptionIfNotOkayHttpStatus(response);
    }

//    public void addPhoneCallToSearch(String customer, PhoneCall call) throws IOException {
//        String sDateTime = call.getStartTimeString().replace(" ", "+");
//        System.out.println("restClient- callToSearch - sDateTime = " + sDateTime);
//        String eDateTime = call.getEndTimeString().replace(" ", "+");
//        System.out.println("restClient- callToSearch - eDateTime = " + eDateTime);
//        Response response = postToMyURL(Map.of(CUSTOMER_PARAMETER, customer,
//                START_CALL_PARAMETER, sDateTime, END_CALL_PARAMETER, eDateTime));
//
//        throwExceptionIfNotOkayHttpStatus(response);
//    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> phoneBillEntries) throws IOException {
      return post(this.url, phoneBillEntries);
    }

    public void removeAllPhoneBills() throws IOException {
      Response response = delete(this.url, Map.of());
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new PhoneBillRestException(code);
      }
      return response;
    }

    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
        private final int httpStatusCode;

      PhoneBillRestException(int httpStatusCode) {

          super("Got an HTTP Status Code of " + httpStatusCode);
          this.httpStatusCode = httpStatusCode;
      }

      public int getHttpStatusCode() {
          return this.httpStatusCode;
      }
    }

}
