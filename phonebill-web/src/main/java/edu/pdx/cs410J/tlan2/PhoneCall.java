package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * This class represents a <code>PhoneCall</code>.
 * Has field access methods and methods to validate data
 * passed through including phone number, date, and time format.
 */

    public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
    /**
     * Creates a new <code>PhoneCall</code>
     *
     * @param callerNumber  The person making the phone call's
     *                      phone number.
     * @param calleeNumber  The person receiving the phone call
     *                      phone number.
     * @param startDate     The date when the phone call started.
     * @param startTime     The time when the phone call started.
     * @param startAMPM     The start time am/pm indicator.
     * @param endDate       The date when the phone call started.
     * @param endTime       The time when the phone call started.
     * @param endAMPM       The end time am/pm indicator.
     * @param startDateTime The start date and time in Date object format.
     * @param endDateTime   The end date and time in Date object format.
     */
    private String callerNumber;
    private String calleeNumber;
    private String startDate;
    private String startTime;
    private String startAMPM;
    private String endDate;
    private String endTime;
    private String endAMPM;
    private Date startDateTime;
    private Date endDateTime;

    /**
     *  Creates a new <code>PhoneCall</code>
     * @param callerNumber the caller's phone number in String object type.
     */
    public PhoneCall(String callerNumber){this.callerNumber = callerNumber;}



    // ========== CONSTRUCTOR ===========================================


    public PhoneCall(String caller, String callee, String startDate, String startTime, String startAMPM, String endDate, String endTime, String endAMPM) {

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidPhoneNumber(caller)) {
            this.callerNumber = caller;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid caller phone number format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidPhoneNumber(callee)) {
            this.calleeNumber = callee;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid callee phone number format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidDate(startDate)) {
            this.startDate = startDate;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call start date format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidDate(endDate)) {
            this.endDate = endDate;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call end date format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidTime(startTime)) {
            this.startTime = startTime;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call start time format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidTime(endTime)) {
            this.endTime = endTime;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call end time format.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidAMPM(startAMPM)) {
            this.startAMPM = startAMPM;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call start AM/PM designation.");
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidAMPM(endAMPM)) {
            this.endAMPM = endAMPM;
        } else {
            throw new IllegalArgumentException(
                    "\n\nInvalid call start AM/PM designation.");
        }

        String sDate = startDate + " " + startTime + " " + startAMPM;
        String eDate = endDate + " " + endTime + " " + endAMPM;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidDate(startDate) && isValidTime(startTime) && isValidAMPM(startAMPM)) {
            try {
                Date startDateTime = format.parse(sDate);
                this.startDateTime = startDateTime;
            } catch (ParseException ex) {
                System.err.println("\nInvalid start date and/or time format. " +
                        "Correct format: mm/dd/yyyy nn:nn am/pm.");
                System.exit(1);
            }
        } else {
            System.err.println("\n\nInvalid end date and/or time format. " +
                    "Correct format: mm/dd/yyyy nn:nn am/pm.");
            System.exit(1);
        }

        /**
         * @throw If an illegal argument exception occurred.
         */
        if (isValidDate(endDate) && isValidTime(endTime) && isValidAMPM(endAMPM)) {
            try {
                Date endDateTime = format.parse(eDate);
                this.endDateTime = endDateTime;
            } catch (ParseException ex) {
                System.err.println("\n\nInvalid end date and/or time format. " +
                        "Correct format: mm/dd/yyyy nn:nn am/pm.");
                System.exit(1);
            }
        } else {
            System.err.println("\n\nInvalid end date and/or time format. " +
                    "Correct format: mm/dd/yyyy nn:nn am/pm.");
            System.exit(1);
        }
    }

    /**
     * @return the caller's phone number.
     */
    @Override
    public String getCaller() {
        return this.callerNumber;
    }

    /**
     * @return the person receiving the call's phone number.
     */

    @Override
    public String getCallee() {
        return this.calleeNumber;
    }

    /**
     *
     * @return the call start time in Date type object.
     */
    @Override
    public Date getStartTime() {
        return this.startDateTime;
    }

    /**
     * @return the start date and time of the phone call.
     */
    @Override
    public String getStartTimeString() {

        return this.startDate + " " + this.startTime + " " + this.startAMPM;
    }

    /**
     *
     * @return the call end time in Date type object.
     */
    @Override
    public Date getEndTime() {
        return this.endDateTime;
    }

    /**
     * @return the end date and time of the phone call.
     */
    @Override
    public String getEndTimeString() {
        return this.endDate + " " + this.endTime + " " + this.endAMPM;
    }

    /**
     * @return all the information of a call into text file/command line format.
     */
    public String getAllCallInfo() {
        return this.callerNumber + " " + this.calleeNumber +
                " " + this.startDate + " " + this.startTime + " " + this.startAMPM +
                " " + this.endDate + " " + this.endTime + " " + this.endAMPM;
    }

    /**
     *
     * @param call2 a phone call to be compared to a given call and placed in proper order
     *
     */
    public int compareTo(PhoneCall call2) {
        long callTime1 = this.getStartTime().getTime();
        long callTime2 = call2.getStartTime().getTime();
        String callNumber1 = this.getCaller();
        String callNumber2 = call2.getCaller();

        if (callTime1 > callTime2) {
            return 1;
        } else if (callTime1 < callTime2) {
            return -1;
        } else {
            if ((callNumber1.compareTo(callNumber2)) > 0) {
                return 1;
            } else if ((callNumber1.compareTo(callNumber2)) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    // ======= STRING VALIDATORS ==================
    /**
     * @return            true or false
     * @param phoneNumber The phone number that is being checked
     *                    for correct format.
     */
    public boolean isValidPhoneNumber(String phoneNumber){
        Pattern p = compile("\\d{3}-\\d{3}-\\d{4}");
        return p.matcher(phoneNumber).matches();
    }

    /**
     *  @return       true or false
     * @param   date  The date of the phone call that is being
     *                checked for correct format
     */

    public boolean isValidDate(String date){
        Pattern p = compile("\\d{1,2}/\\d{1,2}/\\d{4}");
        return p.matcher(date).matches();
    }

    /**
     *  @return     true or false
     * @param time  The time of the phone call that is
     *              being checked for correct format
     */
    public boolean isValidTime(String time){
        Pattern p = compile("(\\d{1,2}:\\d{2})");
        return p.matcher(time).matches();
    }

    /**
     * @return      true or false
     * @param amPM  The time of the phone call that is
     *              being checked for correct format
     */
    public boolean isValidAMPM(String amPM){
        Pattern p = compile("([AaPp][Mm])");
        return p.matcher(amPM).matches();
    }
}
