package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.UsersApiServiceFactory;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;

@Path("/users")


@io.swagger.annotations.Api(description = "the users API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class UsersApi  {
   private final UsersApiService delegate = UsersApiServiceFactory.getUsersApi();

    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query all existing users.", notes = "Query all existing users.", response = User.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Users", response = User.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If access was denied.", response = User.class, responseContainer = "List") })
    public Response usersGet(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.usersGet(securityContext);
    }
}
