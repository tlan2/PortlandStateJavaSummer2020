package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callerNumber;
  private String callee;
  private String calleeNumber;
  private String start;
  private String end;

  public PhoneCall(String caller, String callerNumber, String callee, String calleeNumber, String start, String end){
    this.caller = caller;
    this.callerNumber = callerNumber;
    this.callee = callee;
    this.calleeNumber = calleeNumber;
    this.start = start;
    this.end = end;
  }

  @Override
  public String getCaller() {
    return this.caller;
  }

  @Override
  public String getCallee() {
    return this.callee;
  }

  @Override
  public String getStartTimeString() {
    return this.start;
  }

  @Override
  public String getEndTimeString() {
    return this.end;
  }

}
