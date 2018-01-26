package de.cit.backend.api.impl;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import de.cit.backend.api.model.Capabilities;

import java.util.List;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class CapabilitiesApiServiceImpl extends CapabilitiesApiService {
      @Override
      public Response capabilitiesIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
          return Response.ok().entity(new BitflowException(ExceptionConstants.UNIMPLEMENTED_ERROR).toFrontendFormat()).build();
      }
}
