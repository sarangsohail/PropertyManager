package com.example.propertymanagment;

public class Conv {

    //model for landlord and tenant's conversation

    public boolean seen;
    public long timeStamp;

    public Conv(){


    }
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Conv(boolean seen, long timeStamp) {

        this.seen = seen;
        this.timeStamp = timeStamp;
    }
}
