package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.UserApiServiceFactory;
import de.cit.backend.api.model.ChangePassword;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
import io.swagger.annotations.ApiParam;

@Path("/user")


@io.swagger.annotations.Api(description = "the user API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class UserApi  {
   private final UserApiService delegate = UserApiServiceFactory.getUserApi();

   @POST
   @AuthLevel(UserRoleEnum.STANDARD)
   @Path("/{id}/changePassword")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json" })
   @io.swagger.annotations.ApiOperation(value = "Update existing user.", notes = "Returns the specified user.", response = Void.class, authorizations = {
       @io.swagger.annotations.Authorization(value = "BasicAuth")
   }, tags={ "users", })
   @io.swagger.annotations.ApiResponses(value = { 
       @io.swagger.annotations.ApiResponse(code = 200, message = "If successfully changed.", response = Void.class),
       
       @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class),
       
       @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
   public Response userIdChangePasswordPost(@ApiParam(value = "" ,required=true) ChangePassword body, @PathParam("id") Integer id,@Context SecurityContext securityContext)
   throws NotFoundException {
       return delegate.userIdChangePasswordPost(body,id,securityContext);
   }
   
    @DELETE
    @AuthLevel(UserRoleEnum.ADMIN)
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
    @AuthLevel(UserRoleEnum.STANDARD)
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
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update existing user.", notes = "Returns the specified user.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "users", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response userIdPost(@ApiParam(value = "" ,required=true) User body, @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.userIdPost(body,id,securityContext);
    }
    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
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
