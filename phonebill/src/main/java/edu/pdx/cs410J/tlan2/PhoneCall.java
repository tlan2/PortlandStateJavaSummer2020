package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;
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
  private String startAMPM;
  private String endDate;
  private String endTime;
  private String endAMPM;
  private Date startDateTime;
  private Date endDateTime;

  /**
   *  Creates a new <code>Phone</code>
   *
   * @param callerNumber  The person making the phone call's
   *                      phone number.
   * @param calleeNumber  The person receiving the phone call
   *                      phone number.
   * @param startDateTime The start date and time in Date object format.
   * @param endDateTime   The end date and time in Date object format.
   *
   */
//  @param startDate     The date when the phone call started.
//          * @param startTime     The time when the phone call started.
//          * @param endDate       The date when the phone call ended.
//          * @param endTime       The time when the phone call ended.

  public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                   String startTime, String startAMPM, String endDate, String endTime,
                   String endAMPM){

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidPhoneNumber(callerNumber)) {
      this.callerNumber = callerNumber;
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidPhoneNumber(calleeNumber)) {
      this.calleeNumber = calleeNumber;
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidDate(startDate) && isValidTime(startTime) && isValidAMPM(startAMPM)){//      this.startDate = startDate;

      String myDate = startDate + " " + startTime + " " + startAMPM;
      //      this.startTime = startTime;
//
//      Date this.startDateTime =
//      String[] dateInfo = startDate.split("/");
//      String[] timeInfo = startTime.split(":");

//      this.startDateTime = new Date((int) dateInfo[2], (int) dateInfo[1],
//                                    (int) dateInfo[0]), (int) timeInfo[0],
//                                    (int) timeInfo[1]);
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid date/time format. Correct format is (nn/nn/nnnn or n/n/nnnn) OR (nn:nn or n:nn)");
    }

    /**
     * @throw   If an illegal argument exception occurred.
     */
    if(isValidDate(endDate) && isValidTime(endTime) && isValidAMPM(endAMPM)){
//      this.endDate = endDate;
      //
    } else {
      throw new IllegalArgumentException(
              "\n\nInvalid date/time format. Correct format is (nn/nn/nnnn or n/n/nnnn) OR (nn:nn am/pm or n:nn AM/PM)");
    }

//    /**
//     * @throw   If an illegal argument exception occurred.
//     */
//    if(isValidTime(startTime)){
//      this.startTime = startTime;
//    } else {
//      throw new IllegalArgumentException(
//              "\n\nInvalid time format. Correct format is nn:nn");
//    }
//    /**
//     * @throw   If an illegal argument exception occurred.
//     */
//    if(isValidTime(endTime)){
//      this.endTime = endTime;
//    } else {
//      throw new IllegalArgumentException(
//              "\n\nInvalid time format. Correct format is nn:nn or n:nn");
//    }
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

    return this.startDate + " " + this.startTime;
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
    return this.endDate + " " + this.endTime;
  }

  /**
   * @return all the information of a call into text file/command line format.
   */
  public String getAllCallInfo() {
    return this.getCaller() + " " + this.getCallee() +
            " " + this.getStartTimeString() + " " + this.getEndTimeString();
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
   *  @return     true or false
   * @param time  The time of the phone call that is
   *              being checked for correct format
   */
  public boolean isValidAMPM(String amPM){
    Pattern p = compile("([AaPp][Mm])");
    return p.matcher(amPM).matches();
  }
}
