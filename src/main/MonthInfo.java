package main;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.Date;

public class MonthInfo {
    private LocalDate localDate = LocalDate.now();

    public MonthInfo(LocalDate localDate) {
        this.localDate = localDate;
    }

    public MonthInfo() {
    }

    public String getMonthTitle() {
        return switch (localDate.getMonthValue()) {
            case 1 -> "январь";
            case 2 -> "февраль";
            case 3 -> "март";
            case 4 -> "апрель";
            case 5 -> "май";
            case 6 -> "июнь";
            case 7 -> "июль";
            case 8 -> "август";
            case 9 -> "сентябрь";
            case 10 -> "октябрь";
            case 11 -> "ноябрь";
            default -> "декабрь";
        };
    }

    public int getMonthNumber() {
        return localDate.getMonthValue();
    }

    public String getDayOfWeek() {
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
        return switch (date.getDayOfWeek().getValue()) {
            case 1 -> "пн";
            case 2 -> "вт";
            case 3 -> "ср";
            case 4 -> "чт";
            case 5 -> "пт";
            case 6 -> "сб";
            default -> "вс";
        };
    }

    public LocalDate getLastDayOfMonth() {
        return LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.lengthOfMonth());
    }

    public int getLengthOfMonth() {
        return localDate.lengthOfMonth();
    }

    public String getQuarter() {
        int quarter = switch (localDate.getMonthValue()) {
            case 1, 2, 3 -> 1;
            case 4, 5, 6 -> 2;
            case 7, 8, 9 -> 3;
            default -> 4;
        };
        return localDate.getYear() + " Q" + quarter;

    }

    private static LocalDateTime getNewLocalDateTime(LocalDateTime oldDateTime, ZoneId zoneId) {
        long hours;
        if (zoneId.toString().length() > 3) {
            hours = Long.parseLong(zoneId.getRules().getOffset(oldDateTime).toString().substring(0, 3));
        } else {
            hours = 0;
        }
        LocalDateTime newDateTime;
        if (hours > 0) {
            newDateTime = oldDateTime.plusHours(hours);
        } else if (hours < 0) {
            newDateTime = oldDateTime.minusHours(Math.abs(hours));
        } else {
            newDateTime = oldDateTime;
        }
        return newDateTime;
    }

    public static String differenceBetweenZones(LocalDateTime dateTime, ZoneId firstZone, ZoneId secondZone) {

        LocalDateTime dateTime1 = getNewLocalDateTime(dateTime, firstZone);

        LocalDateTime dateTime2 = getNewLocalDateTime(dateTime, secondZone);

        if (dateTime1.getYear() != dateTime2.getYear()) {
            return "YEAR";
        }
        if (dateTime1.getMonthValue() != dateTime2.getMonthValue()) {
            return "MONTH";
        }
        if (dateTime1.getDayOfMonth() != dateTime2.getDayOfMonth()) {
            return "DAY";
        }
        if (dateTime1.getHour() != dateTime2.getHour()) {
            return "HOUR";
        }
        return "0";
    }

    public static int countOfDaysOff(LocalDate first, LocalDate second) {
        int count = 0;

        do {
            if (first.getDayOfWeek() == DayOfWeek.SUNDAY) {
                count++;
                first = first.plusDays(6);
            } else if (first.getDayOfWeek() == DayOfWeek.SATURDAY) {
                count++;
                first = first.plusDays(1);
            } else {
                first = first.plusDays(1);
            }

        }
        while (first.isBefore(second)|| first.isEqual(second));
        return count;
    }
}
