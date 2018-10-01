package com.kirilo.restaurant.voting.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kirilo.restaurant.voting.web.json.DateHandler;
import com.kirilo.restaurant.voting.web.json.DateSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "persons", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends Person {

    @Column(name = "last_voting")
    @JsonDeserialize(using = DateHandler.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date lastVoting;

    @Column(name = "last_id")
    private int lastId;

    public User() {
    }


    public User(Integer id, String name, String email, String password, Date registered, Role role, Role... roles) {
        this(id, name, email, password, true, EnumSet.of(role, roles), registered);
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Set<Role> roles, Date registered) {
        super(id, name, email, password, enabled, registered, roles);
    }

    public Date getLastVoting() {
        return lastVoting;
    }

    public void setLastVoting(Date lastVoting) {
        this.lastVoting = lastVoting;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastVoting=" + lastVoting +
                ", lastId=" + lastId +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
