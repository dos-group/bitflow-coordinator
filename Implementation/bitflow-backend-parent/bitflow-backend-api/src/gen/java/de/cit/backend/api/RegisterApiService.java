package de.cit.backend.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public abstract class RegisterApiService {
      public abstract Response registerGet(String ip,Integer port,SecurityContext securityContext)
      throws NotFoundException;
}