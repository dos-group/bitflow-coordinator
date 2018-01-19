package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.ProjectsApiServiceFactory;
import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;

@Path("/projects")


@io.swagger.annotations.Api(description = "the projects API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class ProjectsApi  {
   private final ProjectsApiService delegate = ProjectsApiServiceFactory.getProjectsApi();

    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query all projects assigned to the requesting user.", notes = "Query all projects assigned to the requesting user.", response = Project.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "project", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Projects", response = Project.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If the user has no access to that project", response = Project.class, responseContainer = "List") })
    public Response projectsGet(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.projectsGet(securityContext);
    }
}
