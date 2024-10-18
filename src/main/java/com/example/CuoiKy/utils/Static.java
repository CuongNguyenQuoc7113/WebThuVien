package com.example.CuoiKy.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Static {
    public static int numOfDay(Integer page) {
        return page < 100 ? 3 :
                page < 200 ? 5 :
                        page < 300 ? 7 :
                                page < 500 ? 10 : 15;
    }

    public static Date dateReturn(Date borrowDate, Integer numOfPage) {
        LocalDate localDate = borrowDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        localDate = localDate.plusDays(numOfDay(numOfPage));
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
