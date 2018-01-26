package de.cit.backend.api.impl;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;
import de.cit.backend.mgmt.services.interfaces.IProjectService;

import java.util.List;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.converter.ProjectConverter;

import java.io.InputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class ProjectsApiServiceImpl extends ProjectsApiService {
      
	protected IProjectService projectService;

	public ProjectsApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			projectService = (IProjectService) ctx.lookup("java:module/ProjectService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
      public Response projectsGet(SecurityContext securityContext)
      throws NotFoundException {

      List<ProjectDTO> pros = projectService.loadProjects(securityContext.getUserPrincipal().getName());
      return Response.ok().entity(new ProjectConverter().convertToFrontend(pros)).build();
  }
}
