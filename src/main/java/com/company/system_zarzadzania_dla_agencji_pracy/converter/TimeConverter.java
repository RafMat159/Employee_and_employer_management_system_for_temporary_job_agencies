package com.company.system_zarzadzania_dla_agencji_pracy.converter;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TimeConverter {
    public static BigDecimal convertWorkingHoursFromStringToDouble(String workingHours) {
        BigDecimal first;
        BigDecimal second;
        String[] hoursAndMinutes = workingHours.split("-");
        int index1 = hoursAndMinutes[0].indexOf(':');
        int index2 = hoursAndMinutes[1].indexOf(':');
        hoursAndMinutes[0] = hoursAndMinutes[0].substring(0, index1) + "." + hoursAndMinutes[0].substring(index1 + 1);
        hoursAndMinutes[1] = hoursAndMinutes[1].substring(0, index2) + "." + hoursAndMinutes[1].substring(index2 + 1);
        if (hoursAndMinutes[0].contains("00")) {
            first = new BigDecimal(hoursAndMinutes[0]).subtract(new BigDecimal("0.40"));
        } else {
            first = new BigDecimal(hoursAndMinutes[0]);
        }
        if (hoursAndMinutes[1].contains("00")) {
            second = new BigDecimal(hoursAndMinutes[1]).subtract(new BigDecimal("0.40"));
        } else {
            second = new BigDecimal(hoursAndMinutes[1]);
        }
        return second.subtract(first);
    }

    public static BigDecimal getGrossAmount(Order order) {
        BigDecimal workingHours = TimeConverter.convertWorkingHoursFromStringToDouble(order.getWorkingHours());
        BigDecimal hourlyRate = BigDecimal.valueOf(order.getHourlyRate());
        return workingHours.multiply(hourlyRate).setScale(2, RoundingMode.HALF_UP);
    }
}
