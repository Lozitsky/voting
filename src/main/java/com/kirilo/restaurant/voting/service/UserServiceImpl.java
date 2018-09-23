package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final ValidationUtil util;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
        util = new ValidationUtil();
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void delete(int id) throws NotFoundException {

    }

    @Override
    public User get(int id) throws NotFoundException {
        return null;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return repo.getByEmail(email.toLowerCase());
    }

    @Override
    public User findByName(String name) throws NotFoundException {
        Assert.notNull(name, "the name must not be null");
        return util.checkNotFoundWithName(repo.findByName(name), name);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        util.checkNotFoundWithName(repo.save(user), user.getName());
    }

    @Override
    public List<User> getAll() {
        return null;
    }
/*
    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }*/
}
