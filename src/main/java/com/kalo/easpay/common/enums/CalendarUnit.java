package com.kalo.easpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 09月 10日 星期四 11:50:16
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum CalendarUnit {

    /***/
    YEAR(0, Calendar.YEAR),
    MONTH(1, Calendar.MONTH),
    DATE(2, Calendar.DATE),
    HOUR(3, Calendar.HOUR),
    MINUTE(4, Calendar.MINUTE),
    SECOND(5, Calendar.SECOND);
    /***/
    private final Integer unit;
    /***/
    private final Integer field;

    public static Integer field(Integer unit) {
        Integer field = null;
        CalendarUnit[] values = values();
        for (CalendarUnit calendarUnit : values) {
            if (calendarUnit.getUnit().equals(unit)){
                field = calendarUnit.getField();
                break;
            }
        }
        return field;
    }

}
