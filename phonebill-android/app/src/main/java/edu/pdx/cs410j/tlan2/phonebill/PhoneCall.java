package edu.pdx.cs410j.tlan2.phonebill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
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

    public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                     String startTime, String startAMPM, String endDate, String endTime,
                     String endAMPM){

        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startDate = startDate;
        this.startTime = startTime;
        this.startAMPM = startAMPM;
        this.endDate = endDate;
        this.endTime = endTime;
        this.endAMPM = endAMPM;

        String sDate = startDate + " " + startTime + " " + startAMPM;
        String eDate = endDate + " " + endTime + " " + endAMPM;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            Date startDateTime = format.parse(sDate);
            this.startDateTime = startDateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date endDateTime = format.parse(eDate);
            this.endDateTime = endDateTime;
        } catch (ParseException e) {
            e.printStackTrace();
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
    public Date getStartTime() { return this.startDateTime; }

    @Override
    public Date getEndTime() {
        return this.endDateTime;
    }

    @Override
    public String getStartTimeString() {
        return this.startDate + " " + this.startTime + " " + this.startAMPM;
    }

    @Override
    public String getEndTimeString() {
        return this.endDate + " " + this.endTime + " " + this.endAMPM;
    }

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
}