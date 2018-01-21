package de.cit.backend.api.impl;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.ApiResponseMessage;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.ProjectApiService;
import de.cit.backend.api.converter.PipelineConverter;
import de.cit.backend.api.converter.ProjectConverter;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;
import de.cit.backend.mgmt.services.interfaces.IProjectService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class ProjectApiServiceImpl extends ProjectApiService {

	protected IProjectService projectService;
	protected IPipelineService pipelineService;

	public ProjectApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			projectService = (IProjectService) ctx.lookup("java:module/ProjectService");
			pipelineService = (IPipelineService) ctx.lookup("java:module/PipelineService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response projectIdGet(Integer id, SecurityContext securityContext) throws NotFoundException {
		ProjectDTO project = projectService.loadProject(id);
		if (project == null) {
			return Response.status(404).build();
		}

		return Response.ok().entity(new ProjectConverter().convertToFrontend(project)).build();
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdDelete(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		// do some magic!
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdGet(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		PipelineDTO pipe = pipelineService.loadPipelineFromProject(projectId, pipelineId);
		if (pipe == null) {
			return Response.status(404).build();
		}

		return Response.ok().entity(new PipelineConverter().convertToFrontend(pipe)).build();
	}

	@Override
	public Response projectIdPipelinePost(Pipeline body, Integer id, SecurityContext securityContext) throws NotFoundException {
		PipelineConverter converter = new PipelineConverter();
		try{
			PipelineDTO savedPipe = projectService.saveNewPipeline(converter.convertToBackend(body), id);
			return Response.ok().entity(converter.convertToFrontend(savedPipe)).build();			
		}catch (Exception e) {
			return Response.status(400).build();
		}
	}
	
	@Override
	public Response projectProjectIdPipelinePipelineIdPost(Pipeline body, Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		// TODO Auto-generated method stub
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}

	@Override
	public Response projectIdUsersGet(Integer id, SecurityContext securityContext) throws NotFoundException {
		ProjectDTO pro = projectService.loadProject(id);
		if (pro == null) {
			return Response.status(404).build();
		}
		
		return Response.ok().entity(new ProjectConverter().convertToFrontend(pro).getUsers()).build();
	}

	@Override
	public Response projectProjectIdUsersUserIdDelete(Integer projectId, Integer userId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.removeUserFromProject(projectId, userId);
		} catch (IllegalArgumentException e) {
			return Response.status(404).build();
		} catch (Exception e){
			return Response.status(400).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectProjectIdUsersUserIdPost(Integer projectId, Integer userId, SecurityContext securityContext)
			throws NotFoundException {
		try {
			projectService.assignUserToProject(projectId, userId);
		} catch (IllegalArgumentException e) {
			return Response.status(404).build();
		} catch (Exception e){
			return Response.status(400).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException {
		// TODO Delete project
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}

	@Override
	public Response projectIdPipelinesGet(Integer id, SecurityContext securityContext) throws NotFoundException {
        ProjectDTO pro = projectService.loadProject(id);
        if (pro == null) {
            return Response.status(404).build();
        }

		try {
			List<PipelineDTO> pipes = pipelineService.loadPipelinesFromProject(id);
			return Response.ok().entity(new PipelineConverter().convertToFrontend(pipes)).build();
		} catch (Exception e) {
			return Response.status(404).build();
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdStartPost(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		// TODO execute pipeline
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}
}
