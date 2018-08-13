package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.util.exception.NotFoundException;

public class ValidationUtil {
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
}
