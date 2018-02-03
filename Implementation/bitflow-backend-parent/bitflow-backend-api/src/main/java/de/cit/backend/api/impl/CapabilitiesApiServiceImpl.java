package de.cit.backend.api.impl;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import java.util.List;
import java.util.Set;

import de.cit.backend.api.converter.CapabilityConverter;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class CapabilitiesApiServiceImpl extends CapabilitiesApiService {
      
	protected IInfoService infoService;

	public CapabilitiesApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			infoService = (IInfoService) ctx.lookup("java:module/InfoService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	  @Override
      public Response capabilitiesIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
		  try
		  {
			  Set<CapabilityDTO> capa =infoService.loadAgentCapabilities(id);
			  List<Capability> frontendcapa = new CapabilityConverter().convertToFrontend(capa); 
			  return Response.ok().entity(frontendcapa).build();
		  } catch (BitflowException e) {
			  return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		  } catch (Exception e) {
			  return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		  }
 
      }
}
