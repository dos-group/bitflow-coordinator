package de.cit.backend.api;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import de.cit.backend.api.model.Info;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public abstract class InfoApiService {
      public abstract Response infoGet(SecurityContext securityContext)
      throws NotFoundException;
}
