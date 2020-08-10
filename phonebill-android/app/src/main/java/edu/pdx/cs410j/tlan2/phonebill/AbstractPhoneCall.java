package edu.pdx.cs410j.tlan2.phonebill;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractPhoneCall implements Serializable {
    public AbstractPhoneCall() {
    }

    public abstract String getCaller();

    public abstract String getCallee();

    public Date getStartTime() {
        return null;
    }

    public abstract String getStartTimeString();

    public Date getEndTime() {
        return null;
    }

    public abstract String getEndTimeString();

    public final String toString() {
        return "Phone call from " + this.getCaller() + " to " + this.getCallee() + " from " + this.getStartTimeString() + " to " + this.getEndTimeString();
    }
}
