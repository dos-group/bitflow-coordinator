package de.cit.backend.api.impl;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.ProjectsApiService;
import de.cit.backend.api.converter.ProjectConverter;
import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.services.interfaces.IProjectService;

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
	public Response projectsGet(SecurityContext securityContext) throws NotFoundException {
		try {
			List<ProjectDTO> pros = projectService.loadProjects(securityContext.getUserPrincipal().getName());
			List<Project> frontendProjects = new ProjectConverter().convertToFrontend(pros);
			return Response.ok().entity(frontendProjects).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}
}
