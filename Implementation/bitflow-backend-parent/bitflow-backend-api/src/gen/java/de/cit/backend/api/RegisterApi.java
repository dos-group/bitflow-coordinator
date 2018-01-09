package de.cit.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.factories.RegisterApiServiceFactory;

@Path("/register")


@io.swagger.annotations.Api(description = "the register API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class RegisterApi  {
   private final RegisterApiService delegate = RegisterApiServiceFactory.getRegisterApi();

    @GET
    
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Register a new agent to the system.", notes = "Register a new agent to the system.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "BasicAuth")
    }, tags={ "infos", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "If registration succeeds, no further pay load.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "If registration failed.", response = Void.class) })
    public Response registerGet( @PathParam("ip") String ip, @PathParam("port") Integer port,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.registerGet(ip,port,securityContext);
    }
}
