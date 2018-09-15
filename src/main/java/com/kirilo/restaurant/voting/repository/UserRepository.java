package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    User getByEmail(String email);
}
