package de.cit.backend.mgmt;

import de.cit.backend.mgmt.AuthLevel.Level;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
import de.cit.backend.mgmt.services.interfaces.IUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;


@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{

    // TODO: use a logger instance

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_INVALID_CREDENTIALS = Response.status(Status.UNAUTHORIZED)
            .entity("Invalid Credentials").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Status.FORBIDDEN)
            .entity("Access forbidden for this user").build();

    @Context
    private ResourceInfo resourceInfo;

    private IUserService userService;

    public AuthenticationFilter() {
        javax.naming.Context ctx;
        try {
            ctx = new InitialContext();
            userService = (IUserService) ctx.lookup("java:module/UserService");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        System.out.println("AuthenticationFilter");
        Method method = resourceInfo.getResourceMethod();
        System.out.println("Method: "+method.getName());
        final AuthLevel authLevel = method.getDeclaredAnnotation(AuthLevel.class);
        // return if no authorization required
        if(authLevel == null) {
            System.out.println("No authentication required!");
            return;
        } else if (Level.USER.equals(authLevel.value())) {
            System.out.println("User authentication required!");
        } else {
            System.out.println("Admin authentication required!");
        }
        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty())
        {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }

        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        if(username==null || username.isEmpty() || password==null || password.isEmpty())
        {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }

        final UserDTO user = userService.loadUser(username);
        if(user==null) {
            System.out.println("No user found for username "+username);
            requestContext.abortWith(ACCESS_INVALID_CREDENTIALS);
            return;
        }
        if(!user.getPassword().equals(password)) {
            System.out.println("No user found for username "+username);
            requestContext.abortWith(ACCESS_INVALID_CREDENTIALS);
            return;
        }
        if(Level.ADMIN.equals(authLevel.value()) && !UserRoleEnum.ADMIN.equals(user.getRole())) {
            System.out.println("User has no permissions for the requested resource");
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }
    }

}