package com.nfteam.server.dto.response.auth;

import lombok.Getter;

@Getter
public class GoogleUser {

    private String email;
    private String name;

    public GoogleUser() {
    }

    public GoogleUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
