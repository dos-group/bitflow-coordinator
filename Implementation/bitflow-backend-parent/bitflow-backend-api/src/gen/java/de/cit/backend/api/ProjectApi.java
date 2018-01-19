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

import de.cit.backend.api.factories.ProjectApiServiceFactory;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
import io.swagger.annotations.ApiParam;

@Path("/project")


@io.swagger.annotations.Api(description = "the project API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class ProjectApi  {
   private final ProjectApiService delegate = ProjectApiServiceFactory.getProjectApi();

    @DELETE
    @AuthLevel(UserRoleEnum.ADMIN)
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete specified project.", notes = "Delete specified project.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If project was deleted successfully", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectIdDelete( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdDelete(id,securityContext);
    }
    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query information about the specified project.", notes = "Returns the specified project.", response = Project.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Project infos", response = Project.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that projectt", response = Project.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Project.class) })
    public Response projectIdGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdGet(id,securityContext);
    }
    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}/pipeline")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create new pipeline for that project.", notes = "Create new pipeline for that project.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If successful.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectIdPipelinePost(@ApiParam(value = "" ,required=true) Pipeline body, @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdPipelinePost(body,id,securityContext);
    }
    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}/pipelines")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query all pipeline of this project.", notes = "Returns all pipelines of the specified project.", response = Pipeline.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Pipelines", response = Pipeline.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = Pipeline.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Pipeline.class, responseContainer = "List") })
    public Response projectIdPipelinesGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdPipelinesGet(id,securityContext);
    }
    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}/users")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query all users with access to the specified project.", notes = "Returns all users associated with the specified project.", response = User.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Project infos", response = User.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = User.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = User.class, responseContainer = "List") })
    public Response projectIdUsersGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectIdUsersGet(id,securityContext);
    }
    @DELETE
    @AuthLevel(UserRoleEnum.ADMIN)
    @Path("/{projectId}/pipeline/{pipelineId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete the specified pipeline.", notes = "Deletes the pipeline from the specified project.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdPipelinePipelineIdDelete( @PathParam("projectId") Integer projectId, @PathParam("pipelineId") Integer pipelineId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdPipelinePipelineIdDelete(projectId,pipelineId,securityContext);
    }
    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{projectId}/pipeline/{pipelineId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query the specified pipeline.", notes = "Returns the pipeline from the specified project.", response = Pipeline.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Pipeline infos", response = Pipeline.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that projectt", response = Pipeline.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Pipeline.class) })
    public Response projectProjectIdPipelinePipelineIdGet( @PathParam("projectId") Integer projectId, @PathParam("pipelineId") Integer pipelineId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdPipelinePipelineIdGet(projectId,pipelineId,securityContext);
    }
    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{projectId}/pipeline/{pipelineId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update pipeline.", notes = "Updates specified pipeline.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If successful.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If pipeline validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdPipelinePipelineIdPost(@ApiParam(value = "" ,required=true) Pipeline body, @PathParam("projectId") Integer projectId, @PathParam("pipelineId") Integer pipelineId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdPipelinePipelineIdPost(body,projectId,pipelineId,securityContext);
    }
    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{projectId}/pipeline/{pipelineId}/start")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Start executing the specified pipeline.", notes = "Start executing the specified pipeline.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "pipeline", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If pipeline started successfully", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If pipeline execution failed on startup", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdPipelinePipelineIdStartPost( @PathParam("projectId") Integer projectId, @PathParam("pipelineId") Integer pipelineId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdPipelinePipelineIdStartPost(projectId,pipelineId,securityContext);
    }
    @DELETE
    @AuthLevel(UserRoleEnum.ADMIN)
    @Path("/{projectId}/users/{userId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing a user from the specified project.", notes = "Returns the specified user.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If user was removed successfully.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project or the user with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdUsersUserIdDelete( @PathParam("projectId") Integer projectId, @PathParam("userId") Integer userId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdUsersUserIdDelete(projectId,userId,securityContext);
    }
    @POST
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{projectId}/users/{userId}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding a user to the specified project.", notes = "Returns the specified user.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "User validation failed.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the project with the given id does not exist.", response = Void.class) })
    public Response projectProjectIdUsersUserIdPost( @PathParam("projectId") Integer projectId, @PathParam("userId") Integer userId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectProjectIdUsersUserIdPost(projectId,userId,securityContext);
    }
}
