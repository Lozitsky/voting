package com.kirilo.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Persistable<Integer> {
    public static final int START_SEQ = 10000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm"    )
    @Column(name = "date", columnDefinition = "timestamp default now()")
    @NotNull
    private Date date = new Date();

    protected AbstractEntity() {
    }

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public AbstractEntity(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
