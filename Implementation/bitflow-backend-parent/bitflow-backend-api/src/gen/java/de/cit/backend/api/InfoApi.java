package de.cit.backend.api;

import de.cit.backend.api.model.*;
import de.cit.backend.api.InfoApiService;
import de.cit.backend.api.factories.InfoApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import de.cit.backend.api.model.Info;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/info")


@io.swagger.annotations.Api(description = "the info API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public class InfoApi  {
   private final InfoApiService delegate = InfoApiServiceFactory.getInfoApi();

    @GET
    
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Query infos about the running process agents.", notes = "Returns information about the current backend infrastruccture: A list of available process agents and their current state.", response = Info.class, tags={ "infos", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Common infos", response = Info.class) })
    public Response infoGet(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.infoGet(securityContext);
    }
}
