package edu.pdx.cs410j.tlan2.phonebill;

public class PhoneCall extends AbstractPhoneCall {
    private String callerNumber;
    private String calleeNumber;
    private String startDateTime;
    private String endDateTime;

//    public PhoneCall(String callerNumber, String calleeNumber, String startDate,
//                     String startTime, String startAMPM, String endDate, String endTime,
//                     String endAMPM){

public PhoneCall(String callerNumber, String calleeNumber, String startDate,
                 String startTime, String endDate, String endTime){

        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
//        String sDate = startDate + " " + startTime + " " + startAMPM;
//        String eDate = endDate + " " + endTime + " " + endAMPM;
        String sDate = startDate + " " + startTime;
        String eDate = endDate + " " + endTime;
        this.startDateTime = sDate;
        this.endDateTime = eDate;
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
        return this.startDateTime;
    }

    @Override
    public String getEndTimeString() { return this.endDateTime; }

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