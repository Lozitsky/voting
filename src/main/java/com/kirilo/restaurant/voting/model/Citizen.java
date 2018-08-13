package com.kirilo.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "citizens")
public class Citizen {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "citizen_name")
    private String name;
    @Column(name = "has_voted")
    private boolean voted;

    public Citizen() {
    }

    public Citizen(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
