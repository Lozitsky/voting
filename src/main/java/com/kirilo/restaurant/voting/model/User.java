package com.kirilo.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumSet;
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


    public User(Integer id, String name, String email, String password, LocalDateTime registered, Role role, Role... roles) {
        this(id, name, email, password, true, EnumSet.of(role, roles), registered);
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Set<Role> roles, LocalDateTime registered) {
        super(id, name, email, password, enabled, registered, roles);
    }

    public User(User user) {
        super(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getDate(), user.getRoles());
        this.lastVoting = user.getLastVoting();
        this.lastId = user.getLastId();
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

/*    @Override
    public String toString() {
        return "User{" +
                "lastVoting=" + lastVoting +
                ", lastId=" + lastId +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }*/

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                "lastVoting=" + lastVoting +
                ", lastId=" + lastId +
                '}';
    }
}
