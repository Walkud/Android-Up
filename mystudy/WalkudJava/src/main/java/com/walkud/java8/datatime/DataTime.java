package com.walkud.java8.datatime;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Java 8引入了新的Date-Time API(JSR 310)来改进时间、日期的处理。
 * 时间和日期的管理一直是最令Java开发者痛苦的问题。
 * java.util.Date和后来的java.util.Calendar一直没有解决这个问题（甚至令开发者更加迷茫）。
 * 因为上面这些原因，诞生了第三方库Joda-Time，可以替代Java的时间管理API。
 * Java 8中新的时间和日期管理API深受Joda-Time影响，并吸收了很多Joda-Time的精华。
 * 新的java.time包包含了所有关于日期、时间、时区、Instant（跟日期类似但是精确到纳秒）、
 * duration（持续时间）和时钟操作的类。新设计的API认真考虑了这些类的不变性（从java.util.Calendar吸取的教训），
 * 如果某个实例需要修改，则返回一个新的对象。
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/8/2.
 */
public class DataTime {


    public static void main(String[] args) {

        System.out.println("\n----------------Clock----------------\n");

        systemUTC();

        System.out.println("\n----------------Instant----------------\n");

        instant();

        System.out.println("\n----------------LocalDate And LocalTime----------------\n");

        localDateAndLocalTime();

        System.out.println("\n----------------LocalDateTime----------------\n");

        localDateTime();

        System.out.println("\n----------------ZoneDateTime----------------\n");

        zoneDateTime();

        System.out.println("\n----------------Duration----------------\n");

        duration();
    }


    /**
     * Clock学习
     */
    private static void systemUTC() {
        Clock clock = Clock.systemUTC();

        System.out.println(clock.instant());
        System.out.println(clock.millis());

        //使用老方法输出时间
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 学习Instant
     */
    private static void instant() {
        Instant instant = Instant.now();//与Clock.SystemUTC().instant()方法一致

        //即当前时间时间轴上的时间点是从原点前进ssssssssss nnnnnnnnn纳秒的这个时刻。
        System.out.println(instant.getEpochSecond());// 输出秒值
        System.out.println(instant.getNano()); // 输出纳秒值
    }

    /**
     * 学习LocalData和LocalTime
     * LocalDate仅仅包含ISO-8601日历系统中的日期部分；
     * LocalTime则仅仅包含该日历系统中的时间部分。
     * 这两个类的对象都可以使用Clock对象构建得到
     */
    private static void localDateAndLocalTime() {
        Clock clock = Clock.systemDefaultZone();
        //这两种方法是一致的
        LocalDate localDate = LocalDate.now();
        LocalDate localDateFromClock = LocalDate.now(clock);

        //输出日期
        System.out.println(localDate);
        System.out.println(localDateFromClock);

        //这两种方法是一致的
        LocalTime localtime = LocalTime.now();
        LocalTime localTimeFromClock = LocalTime.now(clock);

        //输出时间
        System.out.println(localtime);
        System.out.println(localTimeFromClock);

    }

    /**
     * 学习LocalDataTime
     */
    private static void localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimeFromClock = LocalDateTime.now(Clock.systemDefaultZone());

        System.out.println(localDateTime);
        System.out.println(localDateTimeFromClock);

    }

    /**
     * 学习ZonedDateTime
     * 如果需要特定时区的data/time信息，则可以使用ZoneDateTime，
     * 它保存有ISO-8601日期系统的日期和时间，而且有时区信息
     */
    private static void zoneDateTime() {
        ZonedDateTime zoneDateTime = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeFromClock = ZonedDateTime.now(Clock.systemDefaultZone());
        ZonedDateTime zonedDateTimeFromZoneId = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println(zoneDateTime);
        System.out.println(zonedDateTimeFromClock);
        System.out.println(zonedDateTimeFromZoneId);
    }

    /**
     * 学习Duration
     * 它持有的时间精确到秒和纳秒。这使得我们可以很容易得计算两个日期之间的不同
     */
    private static void duration() {
        LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 23, 59, 59);

        Duration duration = Duration.between(from, to);
        //输出两个时间相差多少天
        System.out.println("Duration in days " + duration.toDays());
        //输出两个时间相差多少小时
        System.out.println("Duration in hours " + duration.toHours());
    }

}
