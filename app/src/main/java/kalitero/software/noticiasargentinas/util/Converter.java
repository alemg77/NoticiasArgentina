package kalitero.software.noticiasargentinas.util;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {

        @TypeConverter
        public Date deFecha (Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public Long dateToTimestamp(Date date) {
            if (date == null) {
                return null;
            } else {
                return date.getTime();
            }
        }
    }

