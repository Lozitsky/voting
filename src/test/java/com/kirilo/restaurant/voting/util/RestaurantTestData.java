package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;

import java.time.Month;

import static com.kirilo.restaurant.voting.model.AbstractEntity.START_SEQ;
import static java.time.LocalDateTime.of;

public class RestaurantTestData {
    private final ValidationDateTime dateTime = new ValidationDateTime();

    public final int RESTAURANT1_ID = START_SEQ + 4;
    public final int RESTAURANT2_ID = START_SEQ + 5;
    public final int RESTAURANT3_ID = START_SEQ + 6;
    public final int RESTAURANT4_ID = START_SEQ + 7;
    public final int RESTAURANT5_ID = START_SEQ + 8;
    public final int RESTAURANT6_ID = START_SEQ + 9;
    public final int RESTAURANT7_ID = START_SEQ + 10;

    public final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Ararat", "some description", dateTime.convertToDate(of(2018, Month.FEBRUARY, 02, 02, 02, 02)));
    public final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "U Vano", "some description", dateTime.convertToDate(of(2017, Month.NOVEMBER,11,11,11,11)));
    public final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Белый Лебедь", "some description", dateTime.convertToDate(of(2018, Month.JANUARY,02,03,04,05)));
    public final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT4_ID, "Черная Каракатица", "some description", dateTime.convertToDate(of(2018, Month.SEPTEMBER,10,11,12,13)));
    public final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT5_ID, "Последний причал", "some description", dateTime.convertToDate(of(2018, Month.AUGUST,22,23,24,25)));
    public final Restaurant RESTAURANT6 = new Restaurant(RESTAURANT6_ID, "Файна страва", "...", dateTime.convertToDate(of(2017, Month.DECEMBER,13,14,15,16)));
    public final Restaurant RESTAURANT7 = new Restaurant(RESTAURANT7_ID, "For Kings", "some description", dateTime.convertToDate(of(2018, Month.JANUARY,11,23,59,59)));


    public final int DISH1_ID = START_SEQ + 18;
    public final int DISH2_ID = START_SEQ + 19;
    public final int DISH3_ID = START_SEQ + 20;
    public final int DISH4_ID = START_SEQ + 21;
    public final int DISH5_ID = START_SEQ + 22;
    public final int DISH6_ID = START_SEQ + 23;
    public final int DISH7_ID = START_SEQ + 24;
    public final int DISH8_ID = START_SEQ + 25;

    public final Dish DISH1 = new Dish(DISH1_ID, "Завтрак", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH2 = new Dish(DISH2_ID, "Обед", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH3 = new Dish(DISH3_ID, "Ужин", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH4 = new Dish(DISH4_ID, "Завтрак", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH5 = new Dish(DISH5_ID, "Обед", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH6 = new Dish(DISH6_ID, "Ужин", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH7 = new Dish(DISH7_ID, "Super ланч", 500, dateTime.convertToDate("2018-09-28"));
    public final Dish DISH8 = new Dish(DISH8_ID, "Super ужин", 500, dateTime.convertToDate("2018-09-28"));

}
