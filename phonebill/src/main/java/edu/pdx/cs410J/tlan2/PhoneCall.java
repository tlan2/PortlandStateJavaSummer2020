package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * This class represents a <code>PhoneCall</code>.
 * Has field access methods and methods to validate data
 * passed through including phone number, date, and time format.
 */

public class PhoneCall extends AbstractPhoneCall {
  private String callerNumber;
  private String calleeNumber;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;

  /**
   *  Creates a new <code>Phone</code>
   *
   * @param callerNumber  The person making the phone call's
   *                      phone number.
   * @param calleeNumber  The person receiving the phone call
   *                      phone number.
   * @param startDate     The date when the phone call started.
   * @param startTime     The time when the phone call started.
   * @param endDate       The date when the phone call ended.
   * @param endTime       The time when the phone call ended.
   */

  public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                   String startTime, String endDate, String endTime){

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidPhoneNumber(callerNumber)) {
      this.callerNumber = callerNumber;
    } else {
      throw new IllegalArgumentException(
              "Invalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidPhoneNumber(calleeNumber)) {
      this.calleeNumber = calleeNumber;
    } else {
      throw new IllegalArgumentException(
              "Invalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidDate(startDate)){
      this.startDate = startDate;
    } else {
      throw new IllegalArgumentException(
              "Invalid date format. Correct format is either nn/nn/nnnn or n/n/nnnn");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidDate(endDate)){
      this.endDate = endDate;
    } else {
      throw new IllegalArgumentException(
              "Invalid date format. Correct format is either nn/nn/nnnn or n/n/nnnn");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidTime(startTime)){
      this.startTime = startTime;
    } else {
      throw new IllegalArgumentException(
              "Invalid time format. Correct format is nn:nn");
    }
    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidTime(endTime)){
      this.endTime = endTime;
    } else {
      throw new IllegalArgumentException(
              "Invalid time format. Correct format is nn:nn or n:nn");
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
   * @return the start date and time of the phone call.
   */
  @Override
  public String getStartTimeString() {
    return this.startDate + " " + this.startTime;
  }

  /**
   * @return the end date and time of the phone call.
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  /**
   * @return all the information of a call into text file/command line format.
   */
  public String getAllCallInfo() {
    return this.getCaller() + " " + this.getCallee() +
            " " + this.getStartTimeString() + " " + this.getEndTimeString();
  }

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
    Pattern p = compile("\\d{1,2}:\\d{2}");
    return p.matcher(time).matches();
  }
}
