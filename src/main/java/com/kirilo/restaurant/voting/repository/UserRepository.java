package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    User getByEmail(String email);

    @Override
    @Modifying
    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Integer integer);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled=?1 WHERE u.id=?2")
    int setStatus(boolean enabled, int id);

//    @Override
//    @Transactional
//    User save(User user);
}
