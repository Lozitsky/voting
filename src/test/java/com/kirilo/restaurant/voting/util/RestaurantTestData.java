package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.AbstractEntity;
import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.Vote;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.kirilo.restaurant.voting.model.AbstractEntity.START_SEQ;
import static java.time.LocalDateTime.of;

public class RestaurantTestData {
//    private final ValidationDateTime dateTime = new ValidationDateTime();

    public final int RESTAURANT1_ID = START_SEQ + 4;
    public final int RESTAURANT2_ID = START_SEQ + 5;
    public final int RESTAURANT3_ID = START_SEQ + 6;
    public final int RESTAURANT4_ID = START_SEQ + 7;
    public final int RESTAURANT5_ID = START_SEQ + 8;
    public final int RESTAURANT6_ID = START_SEQ + 9;
    public final int RESTAURANT7_ID = START_SEQ + 10;

    public final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Ararat", "some description", of(2018, Month.FEBRUARY, 02, 02, 02, 02));
    public final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "U Vano", "some description", of(2017, Month.NOVEMBER,11,11,11,11));
    public final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Белый Лебедь", "some description", of(2018, Month.JANUARY,02,03,04,05));
    public final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT4_ID, "Черная Каракатица", "some description", of(2018, Month.SEPTEMBER,10,11,12,13));
    public final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT5_ID, "Последний причал", "some description", of(2018, Month.AUGUST,22,23,24,25));
    public final Restaurant RESTAURANT6 = new Restaurant(RESTAURANT6_ID, "Файна страва", "...", of(2017, Month.DECEMBER,13,14,15,16));
    public final Restaurant RESTAURANT7 = new Restaurant(RESTAURANT7_ID, "For Kings", "some description", of(2018, Month.JANUARY,11,23,59,59));


    public final int DISH1_ID = START_SEQ + 18;
    public final int DISH2_ID = START_SEQ + 19;
    public final int DISH3_ID = START_SEQ + 20;
    public final int DISH4_ID = START_SEQ + 21;
    public final int DISH5_ID = START_SEQ + 22;
    public final int DISH6_ID = START_SEQ + 23;
    public final int DISH7_ID = START_SEQ + 24;
    public final int DISH8_ID = START_SEQ + 25;

    public final Dish DISH1 = new Dish(DISH1_ID, "Завтрак", 500, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH2 = new Dish(DISH2_ID, "Обед", 1000, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH3 = new Dish(DISH3_ID, "Ужин", 500, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH4 = new Dish(DISH4_ID, "Завтрак", 500, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH5 = new Dish(DISH5_ID, "Обед", 1000, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH6 = new Dish(DISH6_ID, "Ужин", 510, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH7 = new Dish(DISH7_ID, "Super ланч", 510, of(2018, Month.SEPTEMBER,28,00,00,00));
    public final Dish DISH8 = new Dish(DISH8_ID, "Super ужин", 1500, of(2018, Month.SEPTEMBER,28,00,00,00));

    public final int VOTE1_ID = START_SEQ + 11;
    public final int VOTE2_ID = START_SEQ + 12;
    public final int VOTE3_ID = START_SEQ + 13;
    public final int VOTE4_ID = START_SEQ + 14;
    public final int VOTE5_ID = START_SEQ + 15;
    public final int VOTE6_ID = START_SEQ + 16;
    public final int VOTE7_ID = START_SEQ + 17;

    public List<Dish> sortedByDate(Dish... dishes) {
        return (List<Dish>) sortedByDate(Arrays.asList(dishes));
    }

    public List<Restaurant> sortedByDate(Restaurant... restaurants) {
        return (List<Restaurant>) sortedByDate(Arrays.asList(restaurants));
    }

    public List<Vote> sortedByDate(Vote... votes) {
        return (List<Vote>) sortedByDate(Arrays.asList(votes));
    }

    public List<? extends AbstractEntity> sortedByDate(List<? extends AbstractEntity> entities) {
        return entities.stream()
                .sorted((o1, o2) -> (o2.getDate()).compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

}
