package com.kirilo.restaurant.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date", "votes"}, name = "votes_unique_restaurant_date_votes_idx")})
public class Vote extends AbstractEntity {
    @Column(name = "votes")
    private int numberOfVotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Vote() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                super.toString() +
                "numberOfVotes=" + numberOfVotes +
                ", restaurant=" + restaurant +
                '}';
    }
}
