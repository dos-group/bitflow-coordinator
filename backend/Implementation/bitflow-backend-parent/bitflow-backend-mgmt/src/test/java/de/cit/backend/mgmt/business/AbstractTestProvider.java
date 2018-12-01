package de.cit.backend.mgmt.business;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.PipelineStateEnum;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;
import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;


@RunWith(CdiRunner.class)
public abstract class AbstractTestProvider {

	protected static final String ADMIN_NAME = "adminTestUser";
	
    @Inject
    private CdiTestExtension cdiTestExtension;
    private EntityManager entityManager;
    protected EntityTransaction tx;
    
    @Before
    public void initTest() {
        entityManager = cdiTestExtension.getEntityManager();
        tx = entityManager.getTransaction();
        tx.begin();

        beforeTest();

        tx.commit();
        tx.begin();
    }
    
    @After
    public void cleanTest() {
        tx.rollback();
        tx.begin();
        
        afterTest();

        entityManager.flush();
        tx.commit();
		entityManager.clear();
    }
    
    private void afterTest() {
		Query queryPipe = entityManager.createQuery("delete from PipelineDTO");
		Query queryProject = entityManager.createQuery("delete from ProjectDTO");
		Query queryUser = entityManager.createQuery("delete from UserDTO");
		
		queryPipe.executeUpdate();
		queryProject.executeUpdate();
		queryUser.executeUpdate();
		
    }

    private void beforeTest() {
        UserDTO user = createTestUser(ADMIN_NAME);
    	entityManager.persist(user);
    }
   
    
    protected UserDTO createTestUser(String name){
    	UserDTO user = new UserDTO();
    	user.setEmail("cit@test.de");
    	user.setName(name);
    	user.setPassword("adminPwd");
    	user.setRegisteredSince(new Date());
    	user.setRole(UserRoleEnum.ADMIN);
    	
    	return user;
    }
    
    protected ProjectDTO createTestProject(String projectName) {
		ProjectDTO pro = new ProjectDTO();
		pro.setCreatedAt(new Date());
		pro.setName(projectName);
		
		return pro;
	}
    
    protected PipelineDTO createTestPipeline(String name) {
		PipelineDTO pipe = new PipelineDTO();
		pipe.setLastChanged(new Date());
		pipe.setName(name);
		pipe.setStatus(PipelineStateEnum.FINISHED.name());
		
		PipelineStepDTO step1 = createPipelineStep(0, StepTypeEnum.SOURCE, "127.0.01");
		PipelineStepDTO step2 = createPipelineStep(1, StepTypeEnum.OPERATION, "avg");
		PipelineStepDTO step3 = createPipelineStep(2, StepTypeEnum.SINK, "127.0.01");
		
		pipe.getPipelineSteps().addAll(Arrays.asList(step1, step2, step3));

		return pipe;
	}
    
    protected PipelineStepDTO createPipelineStep(int number, StepTypeEnum type, String content){
		PipelineStepDTO step1 = new PipelineStepDTO();
		step1.setContent(content);
		step1.setStepNumber(number);
		step1.setType(type);
		return step1;
	}
    
    protected void associateWith(ProjectDTO pro, PipelineDTO pipe){
    	pro.getPipelines().add(pipe);
    	pipe.getProjects().add(pro);
    }
}
