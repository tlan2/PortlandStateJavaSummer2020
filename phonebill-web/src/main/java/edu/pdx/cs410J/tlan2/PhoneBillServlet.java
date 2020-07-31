package edu.pdx.cs410J.tlan2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.family.TextDumper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import static edu.pdx.cs410J.tlan2.PhoneBillURLParameters.*;
import static edu.pdx.cs410J.tlan2.Project4.stringToDateConverter;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.
 *
 * CHANGE TEXT ABVOE!
 */
public class PhoneBillServlet extends HttpServlet
{
    private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the caller number of the
     * customer specified in the "customer" HTTP parameter to the HTTP response.  If the
     * "customer" parameter is not specified, all of the phone calls are written to the
     *  HTTP response.
     */

    // Gets response from server to provide customer specific phone call info
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter( CUSTOMER_PARAMETER, request );
        String caller = getParameter(CALLER_NUMBER_PARAMETER, request);
        String callee = getParameter(CALLER_NUMBER_PARAMETER, request);
        String start = getParameter( START_CALL_PARAMETER, request);
        String end = getParameter ( END_CALL_PARAMETER, request);

//        System.out.println("servlet -doGet- customer = " + customer);
//        System.out.println("servlet -doGet- caller = " + caller);
//        System.out.println("servlet -doGet- callee = " + callee);
//        System.out.println("servlet -doGet- start = " + start);
//        System.out.println("servlet -doGet- end = " + end);

        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;

        } else if(start == null && end == null) {
//            System.out.println("servlet - customer only.");
            PhoneBill bill = getPhoneBill(customer);
            if (bill == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
            } else {
                PhoneBillTextDumper dumper = new PhoneBillTextDumper(response.getWriter());
                dumper.dump(bill);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
//            System.out.println("servlet - search.");
            PhoneBill billToSearch = getPhoneBill(customer);

            Date minDate = stringToDateConverter(start);
            Date maxDate = stringToDateConverter(end);

//            System.out.println("servlet-minDate = " + minDate);
//            System.out.println("servlet-maxDate = " + maxDate);

            if (billToSearch == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
            } else {
                SortedSet<PhoneCall> sortedCalls = billToSearch.sortedPhoneCalls();
                PhoneBill inRangeCalls = new PhoneBill(customer);

                for (PhoneCall call : sortedCalls) {
                    Date beginDate = call.getStartTime();

                    if (beginDate.after(minDate) && beginDate.before(maxDate)) {
                        inRangeCalls.addPhoneCall(call);
                    }
                }

                if(inRangeCalls.sortedPhoneCalls().size() == 0){
                    PrintWriter writer = response.getWriter();
                    writer.println("\nNo phone calls found between " + start + " and " + end);
                    response.setStatus(HttpServletResponse.SC_OK);
                }

                PhoneBillTextDumper dumper = new PhoneBillTextDumper(response.getWriter());
                dumper.dump(inRangeCalls);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    /**
     * Handles an HTTP POST request by storing the phone call entry for the
     * customer, caller, calleer, startDate, and endDate request parameters.
     * It writes the phone call entry to the HTTP response.
     *
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        String customer = getParameter(CUSTOMER_PARAMETER, request );
//        System.out.println("servlet - doPost - customer = " + customer);
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String caller = getParameter(CALLER_NUMBER_PARAMETER, request);
//        System.out.println("servlet - doPost() - caller = " + caller);
        if (caller == null) {
            missingRequiredParameter(response, CALLER_NUMBER_PARAMETER);
            return;
        }

        String callee = getParameter(CALLEE_NUMBER_PARAMETER, request);
//        System.out.println("servlet - do callee = " + callee);
        if (callee == null) {
            missingRequiredParameter(response, CALLEE_NUMBER_PARAMETER);
            return;
        }

        String startCallInfo = getParameter(START_CALL_PARAMETER, request);
//        System.out.println("servlet - startCallInfo = " + startCallInfo);
        if (startCallInfo == null) {
            missingRequiredParameter(response, START_CALL_PARAMETER);
            return;
        }

        String endCallInfo = getParameter(END_CALL_PARAMETER, request);
//        System.out.println("servlet - endCallInfo = " + endCallInfo);
        if (endCallInfo == null) {
            missingRequiredParameter(response, END_CALL_PARAMETER);
            return;
        }

        String[] startCall = startCallInfo.split("\\+");
        String[] endCall = endCallInfo.split("\\+");

        if(this.phoneBills.containsKey(customer)){
            // Customer with phone bill
            PhoneBill bill = this.phoneBills.get(customer);
            bill.addPhoneCall(new PhoneCall(caller, callee, startCall[0], startCall[1], startCall[2],
                    endCall[0], endCall[1], endCall[2]));
            this.phoneBills.put(customer, bill);

        } else {
            // New Customer
            PhoneBill bill = new PhoneBill(customer);
            bill.addPhoneCall(new PhoneCall(caller, callee, startCall[0], startCall[1], startCall[2],
                    endCall[0], endCall[1], endCall[2]));
            this.phoneBills.put(customer, bill);
        }

        response.setStatus( HttpServletResponse.SC_OK);

    }

    /**
     * Handles an HTTP DELETE request by removing all PhoneBill entries.  This
     * behavior is exposed for testing purposes only.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allPhoneBillsDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    /**
     *
     * @param customer The name of the customer in String type.
     *
     * @return the PhoneBill of the given customer.
     */
    public PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    /**
     * Returns modified bill to its mapping in the HashSet.
     *
     * @param bill to be returned to HashSet.
     */
    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }
}
