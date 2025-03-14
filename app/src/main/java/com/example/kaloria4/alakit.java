package com.example.kaloria4;
import androidx.room.TypeConverter;
import java.util.Date;

public class alakit {


        @TypeConverter
        public static Long fromDate(Date date) {
            return date == null ? null : date.getTime();
        }

        @TypeConverter
        public static Date toDate(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp);
        }

}
