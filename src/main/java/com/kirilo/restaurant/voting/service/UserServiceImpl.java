package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.kirilo.restaurant.voting.util.ValidationUtil.checkNotFoundWithName;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
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
        return null;
    }

    @Override
    public User findByName(String name) throws NotFoundException {
        Assert.notNull(name, "the name must not be null");
        return checkNotFoundWithName(repo.findByName(name), name);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithName(repo.save(user), user.getName());
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
