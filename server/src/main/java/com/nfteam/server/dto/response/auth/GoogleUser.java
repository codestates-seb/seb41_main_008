package com.nfteam.server.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleUser {

    private String id;
    private String email;
    private Boolean verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;

    @Builder
    public GoogleUser(String id,
                      String email,
                      Boolean verifiedEmail,
                      String name,
                      String givenName,
                      String familyName,
                      String picture,
                      String locale) {
        this.id = id;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.locale = locale;
    }

}
