package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.LoginApiServiceFactory;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;

@Path("/login")


@io.swagger.annotations.Api(description = "the login API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class LoginApi  {
   private final LoginApiService delegate = LoginApiServiceFactory.getLoginApi();

    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Verify the user, given by the auth header.", notes = "Verify the user, given by the auth header.", response = User.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "logged in User", response = User.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If login failed.", response = User.class) })
    public Response loginPost(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.loginPost(securityContext);
    }
}
