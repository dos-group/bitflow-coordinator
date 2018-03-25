package de.cit.backend.api.impl;

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
import de.cit.backend.api.model.DeploymentInfo;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;
import de.cit.backend.mgmt.helper.model.DeploymentResponse;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;
import de.cit.backend.mgmt.services.interfaces.IProjectService;
import de.cit.backend.mgmt.validation.Validator;

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
			checkIfProjectMember(id, securityContext.getUserPrincipal().getName());
			
			ProjectDTO project = projectService.loadProject(id);
			return Response.ok().entity(new ProjectConverter().convertToFrontend(project)).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}
	
	@Override
	public Response projectIdPost(Integer id, Project project, SecurityContext securityContext)
			throws NotFoundException {
		try {
			checkIfProjectMember(id, securityContext.getUserPrincipal().getName());
			ProjectDTO pro = new ProjectConverter().convertToBackend(project);
			Validator.validate(Validator.getProjectValidators(pro));
			pro = projectService.updateProject(id, pro);
			return Response.ok().entity(new ProjectConverter().convertToFrontend(pro)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch(Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectPost(Project project, SecurityContext securityContext) throws NotFoundException {
		try {
			ProjectDTO pro = new ProjectConverter().convertToBackend(project);
			Validator.validate(Validator.getProjectValidators(pro));
			pro = projectService.createProject(pro,securityContext.getUserPrincipal().getName());
			return Response.ok().entity(new ProjectConverter().convertToFrontend(pro)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch(Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdDelete(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.deletePipeline(pipelineId);
			return Response.ok().build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdGet(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		PipelineDTO pipe;
		try {
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());
			
			pipe = pipelineService.loadPipelineFromProject(projectId, pipelineId);
			return Response.ok().entity(new PipelineConverter().convertToFrontend(pipe, projectId)).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectIdPipelinePost(Pipeline body, Integer id, SecurityContext securityContext) throws NotFoundException {
		PipelineConverter converter = new PipelineConverter();
		try{
			checkIfProjectMember(id, securityContext.getUserPrincipal().getName());

			PipelineDTO pip = converter.convertToBackend(body);
			Validator.validate(Validator.getPipelineValidators(pip));
			PipelineDTO tmp = null;
			try {
				tmp = pipelineService.loadPipelineByName(pip.getName());

			} catch (BitflowException e) {
				// pipeline was not found
			}
			if(tmp!=null) throw new ValidationException("A Pipeline with identical name does already exist.");
			PipelineDTO savedPipe = pipelineService.saveNewPipeline(pip, id);
			return Response.ok().entity(converter.convertToFrontend(savedPipe, id)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}
	
	@Override
	public Response projectProjectIdPipelinePipelineIdPost(Pipeline body, Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {
		PipelineConverter converter = new PipelineConverter();
		ProjectDTO pro;
		try {
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());

			PipelineDTO pip = converter.convertToBackend(body);
			Validator.validate(Validator.getPipelineValidators(pip));
			pipelineService.updatePipeline(projectId, pipelineId, pip);
			return Response.ok().build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectIdUsersGet(Integer id, SecurityContext securityContext) throws NotFoundException {
		ProjectDTO pro;
		try {
			checkIfProjectMember(id, securityContext.getUserPrincipal().getName());
			
			pro = projectService.loadProject(id);
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
		
		return Response.ok().entity(new ProjectConverter().convertToFrontend(pro).getUsers()).build();
	}

	@Override
	public Response projectProjectIdUsersUserIdDelete(Integer projectId, Integer userId,
			SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.removeUserFromProject(projectId, userId);
			return Response.ok().build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectProjectIdUsersUserIdPost(Integer projectId, Integer userId, SecurityContext securityContext)
			throws NotFoundException {
		try {
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());
			
			projectService.assignUserToProject(projectId, userId);
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
		return Response.ok().build();
	}

	@Override
	public Response projectIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			projectService.deleteProject(id);
			return Response.ok().build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectIdPipelinesGet(Integer id, SecurityContext securityContext) throws NotFoundException {
        ProjectDTO pro;
		try {
			checkIfProjectMember(id, securityContext.getUserPrincipal().getName());
			
			pro = projectService.loadProject(id);
			List<PipelineDTO> pipes = pipelineService.loadPipelinesFromProject(id);
			return Response.ok().entity(new PipelineConverter().convertToFrontend(pipes, id)).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
	public Response projectProjectIdPipelinePipelineIdStartPost(Integer projectId, Integer pipelineId,
			SecurityContext securityContext) throws NotFoundException {

		try {
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());
			
			DeploymentResponse deployment = pipelineService.executePipeline(projectId, pipelineId);
			DeploymentInfo response = new DeploymentInfoConverter().convertToFrontend(deployment.getPartialDeployments());
			response.setHistoryID(deployment.getHistoryId());
			return Response.ok().entity(response).build();
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
	}

	@Override
    public Response projectProjectIdPipelinePipelineIdHistoryGet(Integer projectId,Integer pipelineId,SecurityContext securityContext)
    throws NotFoundException {
		try{
			checkIfProjectMember(projectId, securityContext.getUserPrincipal().getName());
			
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
