package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class PhoneCall extends AbstractPhoneCall {
  private String callerNumber;
  private String calleeNumber;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;

  public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                   String startTime, String endDate, String endTime){

    if(isValidPhoneNumber(callerNumber)) {
      this.callerNumber = callerNumber;
    } else {
      throw new IllegalArgumentException(
              "Invalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    if(isValidPhoneNumber(calleeNumber)) {
      this.calleeNumber = calleeNumber;
    } else {
      throw new IllegalArgumentException(
              "Invalid phone number format. Correct format is nnn-nnn-nnn.");
    }

    if(isValidDate(startDate)){
      this.startDate = startDate;
    } else {
      throw new IllegalArgumentException(
              "Invalid date format. Correct format is either nn/nn/nnnn or n/n/nnnn");
    }

    if(isValidDate(endDate)){
      this.endDate = endDate;
    } else {
      throw new IllegalArgumentException(
              "Invalid date format. Correct format is either nn/nn/nnnn or n/n/nnnn");
    }

    if(isValidTime(startTime)){
      this.startTime = startTime;
    } else {
      throw new IllegalArgumentException(
              "Invalid time format. Correct format is nn:nn");
    }

    if(isValidTime(endTime)){
      this.endTime = endTime;
    } else {
      throw new IllegalArgumentException(
              "Invalid time format. Correct format is nn:nn or n:nn");
    }

  }

  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return this.startDate + " " + this.startTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  public boolean isValidPhoneNumber(String phoneNumber){
    Pattern p = compile("\\d{3}-\\d{3}-\\d{4}");
    return p.matcher(phoneNumber).matches();
  }

  public boolean isValidDate(String date){
    Pattern p = compile("\\d{1,2}/\\d{1,2}/\\d{4}");
    return p.matcher(date).matches();
  }

  public boolean isValidTime(String time){
    Pattern p = compile("\\d{1,2}:\\d{2}");
    return p.matcher(time).matches();
  }
}
