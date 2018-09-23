package com.kirilo.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "persons", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends Person {

    @Column(name = "last_voting")
    private Date lastVoting;

    @Column(name = "last_id")
    private int lastId;

    public User() {
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles) {
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
}
