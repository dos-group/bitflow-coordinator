package de.cit.backend.mgmt;

public @interface AuthLevel {

    enum Level {
        USER, ADMIN
    }

    Level value();

}
