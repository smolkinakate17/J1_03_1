package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


class MonthInfoTest {

    private static MonthInfo monthInfo1;
    private static MonthInfo monthInfo2;
    private static MonthInfo monthInfo3;

    @BeforeAll
    static void setup() {
        monthInfo1 = new MonthInfo(LocalDate.parse("2023-11-12"));
        monthInfo2 = new MonthInfo(LocalDate.parse("1900-01-01"));
        monthInfo3 = new MonthInfo(LocalDate.parse("2020-02-12"));
    }

    @Test
    void getMonthTitle() {
        Assertions.assertEquals(monthInfo1.getMonthTitle(), "ноябрь");
        Assertions.assertEquals(monthInfo2.getMonthTitle(), "январь");
        Assertions.assertEquals(monthInfo3.getMonthTitle(), "февраль");
    }

    @Test
    void getMonthNumber() {
        Assertions.assertEquals(monthInfo1.getMonthNumber(), 11);
        Assertions.assertEquals(monthInfo2.getMonthNumber(), 1);
        Assertions.assertEquals(monthInfo3.getMonthNumber(), 2);
    }

    @Test
    void getDayOfWeek() {
        Assertions.assertEquals(monthInfo1.getDayOfWeek(), "ср");
        Assertions.assertEquals(monthInfo2.getDayOfWeek(), "пн");
        Assertions.assertEquals(monthInfo3.getDayOfWeek(), "сб");
    }

    @Test
    void getLastDayOfMonth() {
        Assertions.assertEquals(monthInfo1.getLastDayOfMonth(), LocalDate.parse("2023-11-30"));
        Assertions.assertEquals(monthInfo2.getLastDayOfMonth(), LocalDate.parse("1900-01-31"));
        Assertions.assertEquals(monthInfo3.getLastDayOfMonth(), LocalDate.parse("2020-02-29"));
    }

    @Test
    void getLengthOfMonth() {
        Assertions.assertEquals(monthInfo1.getLengthOfMonth(), 30);
        Assertions.assertEquals(monthInfo2.getLengthOfMonth(), 31);
        Assertions.assertEquals(monthInfo3.getLengthOfMonth(), 29);
    }

    @Test
    void getQuarter() {
        Assertions.assertEquals(monthInfo1.getQuarter(), "2023 Q4");
        Assertions.assertEquals(monthInfo2.getQuarter(), "1900 Q1");
        Assertions.assertEquals(monthInfo3.getQuarter(), "2020 Q1");
    }

    @Test
    void differenceBetweenZones() {
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2023-11-12T00:00:00"), ZoneId.of("UTC+00"),
                ZoneId.of("UTC+00")), "0");
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2020-06-01T14:25:16"), ZoneId.of("UTC+01"),
                ZoneId.of("UTC+02")), "HOUR");
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2020-06-01T14:25:16"), ZoneId.of("UTC+05"),
                ZoneId.of("UTC+01")), "HOUR");
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2023-05-10T23:00:00"), ZoneId.of("UTC-03"),
                ZoneId.of("UTC+04")), "DAY");
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2024-10-01T04:59:59"), ZoneId.of("UTC-08"),
                ZoneId.of("UTC-04")), "MONTH");
        Assertions.assertEquals(MonthInfo.differenceBetweenZones(
                LocalDateTime.parse("2010-12-31T20:15:00"), ZoneId.of("Europe/Moscow"),
                ZoneId.of("Asia/Vladivostok")), "YEAR");

    }

    @Test
    void countOfDaysOff() {
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-11-01"),
                LocalDate.parse("2023-11-01")), 0);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-01"),
                LocalDate.parse("2023-05-03")), 0);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-01-01")), 1);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-01"),
                LocalDate.parse("2023-05-06")), 1);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-07"),
                LocalDate.parse("2023-05-10")), 1);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-01"),
                LocalDate.parse("2023-05-07")), 2);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-01"),
                LocalDate.parse("2023-05-08")), 2);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-06"),
                LocalDate.parse("2023-05-07")), 2);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-06"),
                LocalDate.parse("2023-05-09")), 2);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-05-01"),
                LocalDate.parse("2023-05-16")), 4);
        Assertions.assertEquals(MonthInfo.countOfDaysOff(LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-12-31")), 105);
    }
}