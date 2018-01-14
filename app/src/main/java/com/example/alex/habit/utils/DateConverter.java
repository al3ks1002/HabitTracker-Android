package com.example.alex.habit.utils;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if (value == null) {
            return null;
        } else {
            Timestamp timestamp = new Timestamp(value);
            return new Date(timestamp.getTime());
        }
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            Timestamp timestamp = new Timestamp(date.getTime());
            System.out.println(date);
            System.out.println(date.getTime());
            System.out.println(timestamp);
            System.out.println(timestamp.getTime());
            return timestamp.getTime();
        }
    }
}
