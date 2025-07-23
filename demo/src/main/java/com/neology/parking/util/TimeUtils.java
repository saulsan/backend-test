package com.neology.parking.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static int difEnMinutos(Calendar start, Calendar end) {
        long diff = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) TimeUnit.MILLISECONDS.toMinutes(diff);
    }
}