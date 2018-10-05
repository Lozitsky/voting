package com.kirilo.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    @JsonIgnoreProperties("restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    @JsonIgnoreProperties("restaurant")
    private List<Vote> votes;

    @Column(name = "description", columnDefinition = "VARCHAR(200)")
    private String description;

    public Restaurant() {
    }

    public Restaurant(String name) {
        super(null, name);
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getDate());
    }

    public Restaurant(Integer id, String name, String description, LocalDateTime date) {
        super(id, name, date);
        this.description = description;
    }

    public Restaurant(String name, String description, LocalDateTime date) {
        super(null, name, date);
        this.description = description;
    }

    public Restaurant(String name, String description){
        super(null, name);
        this.description = description;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Vote> getVotes() {
        return votes;
    }

//    https://stackoverflow.com/questions/48132732/tostring-method-loads-lazy-fields-in-hibernate/52547740#52547740
/*    @Override
    public String toString() {
        return "Restaurant{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }*/

    @Override
    public String toString() {
        return "Restaurant{" +
                super.toString() +
                "description='" + description + '\'' +
                '}';
    }
}

