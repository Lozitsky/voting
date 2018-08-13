package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.AbstractEntity;

public class SecurityUtil {
    private static int authorisedId = AbstractEntity.START_SEQ;

    public static int getAuthorisedId() {
        return authorisedId;
    }

    public static void setAuthorisedId(int authorisedId) {
        SecurityUtil.authorisedId = authorisedId;
    }
}
