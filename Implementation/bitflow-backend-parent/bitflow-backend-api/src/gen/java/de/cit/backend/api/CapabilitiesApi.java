package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.CapabilitiesApiServiceFactory;
import de.cit.backend.api.model.Capabilities;
import de.cit.backend.mgmt.AuthLevel;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;

@Path("/capabilities")


@io.swagger.annotations.Api(description = "the capabilities API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class CapabilitiesApi  {
   private final CapabilitiesApiService delegate = CapabilitiesApiServiceFactory.getCapabilitiesApi();

    @GET
    @AuthLevel(UserRoleEnum.STANDARD)
    @Path("/{id}")
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query capabilities of a specific agents", notes = "Returns information about the specified agent and its capabilities.", response = Capabilities.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "infos", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Common infos", response = Capabilities.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "If the agent with the specified id does not exist.", response = Capabilities.class) })
    public Response capabilitiesIdGet( @PathParam("id") Integer id,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.capabilitiesIdGet(id,securityContext);
    }
}
