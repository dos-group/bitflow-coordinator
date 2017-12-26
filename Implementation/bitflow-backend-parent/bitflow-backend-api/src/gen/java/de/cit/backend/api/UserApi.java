package de.cit.backend.api;

import de.cit.backend.api.model.*;
import de.cit.backend.api.UserApiService;
import de.cit.backend.api.factories.UserApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import de.cit.backend.api.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/user")


@io.swagger.annotations.Api(description = "the user API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class UserApi  {
   private final UserApiService delegate = UserApiServiceFactory.getUserApi();

    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete an existing user.", notes = "Returns the specified user.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response userIdDelete( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.userIdDelete(id,securityContext);
    }
    @GET
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query an existing user.", notes = "Returns the specified user.", response = User.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "User infos", response = User.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = User.class) })
    public Response userIdGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.userIdGet(id,securityContext);
    }
    @POST
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update existing user.", notes = "Returns the specified user.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response userIdPost(@ApiParam(value = "" ,required=true) User body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.userIdPost(body,securityContext);
    }
    @POST
    
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create new user.", notes = "Returns the specified user.", response = Void.class, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class) })
    public Response userPost(@ApiParam(value = "" ,required=true) User body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.userPost(body,securityContext);
    }
}
