package edu.pdx.cs410j.tlan2.phonebill;

public class PhoneCall extends AbstractPhoneCall {
    @Override
    public String getCaller() {
        return "Me";
    }

    @Override
    public String getCallee() {
        return "You";
    }

    @Override
    public String getStartTimeString() {
        return "Then";
    }

    @Override
    public String getEndTimeString() {
        return "Now";
    }
}