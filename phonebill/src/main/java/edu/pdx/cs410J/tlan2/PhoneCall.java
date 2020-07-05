package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String callerNumber;
  private String calleeNumber;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;

  public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                   String startTime, String endDate, String endTime){
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
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

}
