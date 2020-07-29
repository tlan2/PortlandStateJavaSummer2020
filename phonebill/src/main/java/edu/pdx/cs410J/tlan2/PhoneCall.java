package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
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
  private String callerNumber;
  private String calleeNumber;
//  private String startDate;
//  private String startTime;
//  private String startAMPM;
//  private String endDate;
//  private String endTime;
//  private String endAMPM;
  private Date startDateTime;
  private Date endDateTime;

  /**
   * Creates a new <code>Phone</code>
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

  // ========== CONSTRUCTOR ===========================================
 public PhoneCall(String callerNumber){this.callerNumber = callerNumber;}

  public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                   String startTime, String startAMPM, String endDate, String endTime,
                   String endAMPM) {

    /**
     * @throw If an illegal argument exception occurred.
     */
    if (isValidPhoneNumber(callerNumber)) {
      this.callerNumber = callerNumber;
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    /**
     * @throw If an illegal argument exception occurred.
     */
    if (isValidPhoneNumber(calleeNumber)) {
      this.calleeNumber = calleeNumber;
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid phone number format. Correct format is nnn-nnn-nnn.");
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

    // To check start and end date time
//    long sdt = startDateTime.getTime();
//    long edt = endDateTime.getTime();
//    long diff = edt - sdt;
//
//      if(diff < 0){
//          System.err.println("\n\nCall start time after call end time.\n");
//          System.exit(1);
//      }

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

  @Override
  public Date getStartTime() {
    return this.startDateTime;
  }

  /**
   * @return the start date and time of the phone call.
   */
  @Override
  public String getStartTimeString() {
    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);
    String formattedDate = df.format(this.startDateTime);
    return formattedDate;
  }

  @Override
  public Date getEndTime() {
    return this.endDateTime;
  }

  /**
   * @return the end date and time of the phone call.
   */
  @Override
  public String getEndTimeString() {
    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);
    String date = df.format(this.endDateTime);
    return date;
  }

  /**
   * @return all the information of a call into text file/command line format.
   */
  public String getAllCallInfo() {
    return this.getCaller() + " " + this.getCallee() +
            " " + this.getStartTimeString() + " " + this.getEndTimeString();
  }

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
    Pattern p = compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
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
