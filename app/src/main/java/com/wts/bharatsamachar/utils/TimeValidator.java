package com.wts.bharatsamachar.utils;

public class TimeValidator {
    final long MIN_TIME_DIFF = 5 * 60000 ;// minute

    private static volatile TimeValidator sSoleInstance;

    public static TimeValidator newInstance() {
        return new TimeValidator();
    }
    public static TimeValidator getInstance() {
        if (sSoleInstance == null) {
            synchronized (TimeValidator.class) {
                if (sSoleInstance == null) sSoleInstance = new TimeValidator();
            }
        }
        return sSoleInstance;
    }

    private long previousTimeStamp = System.currentTimeMillis();
    private long minTimeDifference = MIN_TIME_DIFF;

    public void setMinTimeDifference(int timeInMinute) {
        this.minTimeDifference = timeInMinute * 60000;
    }

    public void setMinTimeDifference(long minTimeDifference) {
        this.minTimeDifference = minTimeDifference;
    }

    public boolean isValidTimeDiff() {
        long currentTime =  System.currentTimeMillis();
        long timeDiff = currentTime - previousTimeStamp;
        if (timeDiff > minTimeDifference){
            previousTimeStamp = currentTime;
            return true;
        }else {
            return false;
        }
    }
}