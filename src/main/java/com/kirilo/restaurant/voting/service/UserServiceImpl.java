package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Role;
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
    private final UserRepository repository;
    private final ValidationUtil util;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
        return repository.getByEmail(email.toLowerCase());
    }

    @Override
    public User findByName(String name) throws NotFoundException {
        Assert.notNull(name, "the name must not be null");
        return util.checkNotFoundWithName(repository.findByName(name), name);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        util.checkNotFoundWithName(repository.save(user), user.getName());
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void setOnOff(int id, boolean on) {
        User user = getWithRoles(id);
        util.checkRole(user, Role.ROLE_USER);
        repository.setStatus(on, id);
    }

    @Override
    public User getWithRoles(int id) throws NotFoundException{
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found User Entity in database with id=" + id));
    }
}
