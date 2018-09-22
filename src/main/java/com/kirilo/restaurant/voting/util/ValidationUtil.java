package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.AbstractEntity;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.kirilo.restaurant.voting.util.ValidationDateTime.alreadyVoted;

public class ValidationUtil {
    public final static Logger logger = Logger.getLogger(VotingController.class);

    public static <T> T checkNotFoundWithName(T object, String name) {
        return checkNotFound(object, "name=" + name);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void assureIdConsistent(AbstractEntity entity, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static void checkVoting(User user, HttpServletResponse response) throws IOException {
        if (!alreadyVoted(user)) {
            logger.info("User " + user.getName() + " not voted yet");
            response.sendRedirect("/rest/restaurants");
        }
    }

    public static void checkNew(AbstractEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }
}
