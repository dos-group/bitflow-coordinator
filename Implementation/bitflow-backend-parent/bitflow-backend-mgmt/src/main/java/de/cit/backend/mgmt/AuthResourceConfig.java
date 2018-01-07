package de.cit.backend.mgmt;


import org.glassfish.jersey.server.ResourceConfig;

public class AuthResourceConfig extends ResourceConfig
{
    public AuthResourceConfig()
    {
        System.out.println("AuthResourceConfig");
        packages("de.cit.backend.mgmt");
        register(AuthenticationFilter.class);
    }
}