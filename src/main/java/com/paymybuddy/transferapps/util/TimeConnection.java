package com.paymybuddy.transferapps.util;


public class TimeConnection {
    private long activatedAt = 0;

    public void activate(Boolean isRightLogs) {
        this.activatedAt = System.currentTimeMillis();
    }

    public boolean isActive() {
        long checkTime = System.currentTimeMillis();
        return checkTime < this.activatedAt + (1000*60*4); // time connection programmed with 5 minutes
    }

    public void desactivate() {
        this.activatedAt = 0;
    }
}
