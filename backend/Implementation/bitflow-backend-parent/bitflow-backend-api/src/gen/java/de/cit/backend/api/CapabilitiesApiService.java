package de.cit.backend.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public abstract class CapabilitiesApiService {
	public abstract Response capabilitiesGet(SecurityContext securityContext) throws NotFoundException;

	public abstract Response capabilitiesIdGet(Integer id, SecurityContext securityContext) throws NotFoundException;
}
