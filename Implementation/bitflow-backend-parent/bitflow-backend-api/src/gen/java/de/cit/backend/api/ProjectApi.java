package de.cit.backend.api;

import de.cit.backend.api.model.*;
import de.cit.backend.api.ProjectApiService;
import de.cit.backend.api.factories.ProjectApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.api.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/project")


@io.swagger.annotations.Api(description = "the project API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class ProjectApi  {
   private final ProjectApiService delegate = ProjectApiServiceFactory.getProjectApi();

    @GET
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query information about the specified project.", notes = "Returns the specified project.", response = Project.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Project infos", response = Project.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that projectt", response = Project.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Project.class) })
    public Response projectIdGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdGet(id,securityContext);
    }
    @DELETE
    @Path("/{id}/pipeline")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete the specified pipeline.", notes = "Deletes the pipeline from the specified project.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectIdPipelineDelete( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdPipelineDelete(id,securityContext);
    }
    @GET
    @Path("/{id}/pipeline")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query the specified pipeline.", notes = "Returns the pipeline from the specified project.", response = Pipeline.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Pipeline infos", response = Pipeline.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that projectt", response = Pipeline.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Pipeline.class) })
    public Response projectIdPipelineGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdPipelineGet(id,securityContext);
    }
    @POST
    @Path("/{id}/pipeline")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create new pipeline.", notes = "Creates new Pipeline as specified.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "If pipeline validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectIdPipelinePost(@ApiParam(value = "" ,required=true) Pipeline body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdPipelinePost(body,securityContext);
    }
    @GET
    @Path("/{id}/users")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query all users with access to the specified project.", notes = "Returns all users associated with the specified project.", response = User.class, responseContainer = "List", authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Project infos", response = User.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = User.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = User.class, responseContainer = "List") })
    public Response projectIdUsersGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdUsersGet(id,securityContext);
    }
    @DELETE
    @Path("/{projectId}/users/{userId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing a user from the specified project.", notes = "Returns the specified user.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project or the user with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdUsersUserIdDelete( @PathParam("projectId") Integer projectId, @PathParam("userId") Integer userId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdUsersUserIdDelete(projectId,userId,securityContext);
    }
    @POST
    @Path("/{projectId}/users/{userId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding a user to the specified project.", notes = "Returns the specified user.", response = Void.class, authorizations = {@io.swagger.annotations.Authorization(value = "BasicAuth")}, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdUsersUserIdPost( @PathParam("projectId") Integer projectId, @PathParam("userId") Integer userId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdUsersUserIdPost(projectId,userId,securityContext);
    }
}
