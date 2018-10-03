package com.kirilo.restaurant.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"}, name = "dishes_unique_restaurant_date_name_idx")})
public class Dish extends AbstractNamedEntity {
    @Column(name = "price", columnDefinition = "int default 0")
    private int price;

    //    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "restaurant_id", nullable = false)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String dishName, int price) {
        super(id, dishName);
        this.price = price;
    }

    public Dish(String dishName, int price) {
        super(null, dishName);
        this.price = price;
    }

    public Dish(Integer id, String dishName, int price, LocalDateTime date) {
        super(id, dishName, date);
        this.price = price;
    }

    public Dish(String dishName, int price, LocalDateTime date) {
        super(null, dishName, date);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

/*    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }*/

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
