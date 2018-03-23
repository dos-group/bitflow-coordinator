package de.cit.backend.mgmt.business;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.PipelineService;
import de.cit.backend.mgmt.services.ProjectService;
import de.cit.backend.mgmt.services.UserService;

public class ProjectPipelineBusinessTest extends AbstractTestProvider {

	@Inject
	private UserService userService;
	
	@Inject
	private ProjectService projectService;
	
	@Inject
	private PipelineService pipelineService;
	
	private static int projectId;
	private static int pipelineId;
	
	@Test
	public void createProject() throws BitflowException{
		ProjectDTO pro = createTestProject("testPro");
		
		pro = projectService.createProject(pro, ADMIN_NAME);
		Assert.assertTrue(pro.getId() > 0);
		
		projectId = pro.getId();
	}
	
	@Test
	public void createProjectWithPipeline() throws BitflowException{
		createProject();
		
		PipelineDTO pipe = createTestPipeline("testPipeline");
		pipe = pipelineService.saveNewPipeline(pipe, projectId);
		
		Assert.assertTrue(pipe.getId() > 0);
		Assert.assertTrue(pipe.getPipelineSteps().size() == 3);
		
		pipelineId = pipe.getId();
	}
	
	@Test
	public void deletePipelineTest() throws BitflowException{
		createProjectWithPipeline();
		
		projectService.deletePipeline(pipelineId);
		
		try{
			PipelineDTO pipe = pipelineService.loadPipelineFromProject(projectId, pipelineId);
			Assert.fail();
		}catch (BitflowException e) {
			Assert.assertEquals(ExceptionConstants.OBJECT_NOT_FOUND_ERROR.getCode(), e.getErrorCode());
		}
	}
	
	@Test
	public void deletePipelineWithUsersTest() throws BitflowException{
		createProjectWithPipeline();
		
		UserDTO user = userService.createUser(createTestUser("testUser"));
		
		projectService.assignUserToProject(projectId, user.getId());
		
		projectService.deleteProject(projectId);
		
		try{
			ProjectDTO pro = projectService.loadProject(projectId);
			Assert.fail();
		}catch (BitflowException e) {
			Assert.assertEquals(ExceptionConstants.OBJECT_NOT_FOUND_ERROR.getCode(), e.getErrorCode());
		}
	}
}
