package de.cit.backend.api.impl;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.ProjectApiService;
import de.cit.backend.api.converter.DeploymentInfoConverter;
import de.cit.backend.api.converter.PipelineConverter;
import de.cit.backend.api.converter.PipelineHistoryConverter;
import de.cit.backend.api.converter.ProjectConverter;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
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
		try {
			if(!isMemberOfProject(id, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			ProjectDTO project = projectService.loadProject(id);
			return Response.ok().entity(new ProjectConverter().convertToFrontend(project)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}
	
	@Override
	public Response projectIdPost(Integer id, Project project, SecurityContext securityContext)
			throws NotFoundException {
		try {
			if(!isMemberOfProject(id, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			ProjectDTO pro = projectService.updateProject(id, new ProjectConverter().convertToBackend(project));
			return Response.ok().entity(new ProjectConverter().convertToFrontend(pro)).build();
		} catch(BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();			
		} catch(Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response projectPost(Project project, SecurityContext securityContext) throws NotFoundException {
		try {
			ProjectDTO pro = projectService.createProject(new ProjectConverter().convertToBackend(project),securityContext.getUserPrincipal().getName());
			return Response.ok().entity(new ProjectConverter().convertToFrontend(pro)).build();
		} catch(BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();			
		} catch(Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdDelete(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.deletePipeline(pipelineId);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdGet(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		PipelineDTO pipe;
		try {
			if(!isMemberOfProject(projectId, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			pipe = pipelineService.loadPipelineFromProject(projectId, pipelineId);
			return Response.ok().entity(new PipelineConverter().convertToFrontend(pipe, projectId)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response projectIdPipelinePost(Pipeline body, Integer id, SecurityContext securityContext) throws NotFoundException {
		PipelineConverter converter = new PipelineConverter();
		try{
			if(!isMemberOfProject(id, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			PipelineDTO savedPipe = projectService.saveNewPipeline(converter.convertToBackend(body), id);
			return Response.ok().entity(converter.convertToFrontend(savedPipe, id)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}
	
	@Override
	public Response projectProjectIdPipelinePipelineIdPost(Pipeline body, Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		PipelineConverter converter = new PipelineConverter();
		ProjectDTO pro;
		try {
			if(!isMemberOfProject(projectId, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			pro = projectService.loadProject(projectId);
			for (PipelineDTO pipeline : pro.getPipelines()) {
				if (pipeline.getId().equals(pipelineId)) {
					pipeline.setLastChanged(new Date());
					pipeline.setName(body.getName());
					// TODO set script? update pipeline steps?
					break;
				}
			}
			return Response.ok().build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response projectIdUsersGet(Integer id, SecurityContext securityContext) throws NotFoundException {
		ProjectDTO pro;
		try {
			if(!isMemberOfProject(id, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			pro = projectService.loadProject(id);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		
		return Response.ok().entity(new ProjectConverter().convertToFrontend(pro).getUsers()).build();
	}

	@Override
	public Response projectProjectIdUsersUserIdDelete(Integer projectId, Integer userId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.removeUserFromProject(projectId, userId);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectProjectIdUsersUserIdPost(Integer projectId, Integer userId, SecurityContext securityContext)
			throws NotFoundException {
		try {
			if(!isMemberOfProject(projectId, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			projectService.assignUserToProject(projectId, userId);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.deleteProject(id);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response projectIdPipelinesGet(Integer id, SecurityContext securityContext) throws NotFoundException {
        ProjectDTO pro;
		try {
			if(!isMemberOfProject(id, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			pro = projectService.loadProject(id);
			List<PipelineDTO> pipes = pipelineService.loadPipelinesFromProject(id);
			return Response.ok().entity(new PipelineConverter().convertToFrontend(pipes, id)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdStartPost(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {

		try {
			if(!isMemberOfProject(projectId, securityContext.getUserPrincipal().getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			DeploymentInformation[] deployment = pipelineService.executePipeline(projectId, pipelineId);
			return Response.ok().entity(new DeploymentInfoConverter().convertToFrontend(deployment)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
    public Response projectProjectIdPipelinePipelineIdHistoryGet(Integer projectId,Integer pipelineId,SecurityContext securityContext)
    throws NotFoundException {
		try{
			if(!isMemberOfProject(projectId, securityContext.getUserPrincipal().getName())) {
				final BitflowException e = new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
				return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
			}
			List<PipelineHistoryDTO> hist = pipelineService.loadPipelineHistory(projectId, pipelineId);
			
			return Response.ok().entity(new PipelineHistoryConverter().convertToFrontend(hist)).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}
	
	@Override
	public Response projectProjectIdPipelinePipelineIdHistoryLastGet(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());
			PipelineHistoryDTO hist = pipelineService.loadPipelineHistoryLast(projectId, pipelineId);

			return Response.ok().entity(new PipelineHistoryConverter().convertToFrontend(hist)).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	private boolean isMemberOfProject(final Integer projectId, final String username) throws BitflowException {
		final ProjectDTO project = projectService.loadProject(projectId);
		if(project.getUserdata().getName().equals(username)) return true;
		for(UserDTO user : project.getProjectMembers()) {
			if(user.getName().equals(username)) return true;
		}
		return false;
	}
	
	private void checkIfProjectMember(Integer projectId, String username) throws BitflowException {
		final ProjectDTO project = projectService.loadProject(projectId);
		if(project.getUserdata().getName().equals(username)){
			return;
		}
		for(UserDTO user : project.getProjectMembers()) {
			if(user.getName().equals(username)){
				return;
			}
		}
		throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
	}


}
