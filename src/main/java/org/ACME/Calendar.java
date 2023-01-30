package org.ACME;

import java.util.Date;
import java.util.GregorianCalendar;

public class Calendar {
    private final GregorianCalendar calendar;

    protected Calendar() {
        calendar = new GregorianCalendar();
    }

    protected void addHour() {
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 1);
    }

    protected void setDate(int year, int month, int day, int hours, int minutes, int seconds) {
        calendar.set(year, month, day, hours, minutes, seconds);
    }

    protected int getMonth() {
        return calendar.get(java.util.Calendar.MONTH);
    }

    protected Date getDate() {
        return calendar.getTime();
    }
}
