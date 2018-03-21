package de.cit.backend.mgmt.persistence;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.cit.backend.mgmt.helper.service.ScriptGenerator;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.ConfigurationDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;
import de.cit.backend.mgmt.persistence.model.enums.PipelineStateEnum;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

@Ignore
public class PersistenceAccessTest {

	private static EntityManager em;
	
	@BeforeClass
    public static void createInput() throws IOException {
		em = PersistenceManager.instance();
	}
	
	@Test
	public void findUserTest(){
		UserDTO user = em.find(UserDTO.class, 1);
		System.out.println(user.getCreatedProjects().size());
		System.out.println(user.getJoinedProjects().size());
		System.out.println(user.getEmail());
	}
	
	@Test
	public void findProjectTest(){
		ProjectDTO pro = em.find(ProjectDTO.class, 1);
		
		System.out.println(pro.getName());
		System.out.println(pro.getUserdata().getName());
		System.out.println(pro.getProjectMembers().size());
	}
	
	@Test
	public void findPipelineTest() {
		PipelineDTO pipe = em.find(PipelineDTO.class, 13);
		System.out.println("Pipeline:");
		System.out.println(ScriptGenerator.generateScriptForPipeline(pipe));
		for(PipelineStepDTO step : pipe.getPipelineSteps()){
			String stepStr = step.getStepNumber() + ": ";
			for(PipelineStepDTO succ : step.getSuccessors()){
				stepStr += succ.getStepNumber();
			}
			System.out.println(stepStr);
		}
		
	}
	
	@Test
	public void findAgentTest(){
		String hqlQuery = "SELECT agent FROM AgentDTO agent WHERE agent.status = :status";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("status", AgentState.ONLINE);
		
		List<AgentDTO> agentList = (List<AgentDTO>) query.getResultList();
		
		for(AgentDTO a : agentList){
			System.out.println(a.getIpAddress() + ":" + a.getPort());
		}
	}
	
	@Test
	public void loadPipelineHistoryTest(){
		PipelineHistoryDTO hist = em.find(PipelineHistoryDTO.class, 26);
		System.out.println(hist.getScript());
		Assert.assertNotNull(hist.getScript());
	}
	
	@Test
	public void savePipelineHistoryTest(){
		PipelineDTO pipeline = new PipelineDTO();
		pipeline.setLastChanged(new Date());
		pipeline.setName("TestPipe");
		pipeline.setStatus("test");
		
		PipelineHistoryDTO hist = new PipelineHistoryDTO();
		hist.setStatus(PipelineStateEnum.RUNNING);
		hist.setStartedAt(new Date());
		hist.setPipeline(pipeline);
		hist.setScript("source -> avg() -> sink");
		
		pipeline.getPipelineHistory().add(hist);
		
		em.getTransaction().begin();
		em.persist(pipeline);
		
		Assert.assertNotNull(pipeline.getId());
		Assert.assertNotNull(hist.getId());
		
		em.getTransaction().rollback();
	}
	
	@Test
	public void savePipelineTest(){
		PipelineDTO pipeline = new PipelineDTO();
		pipeline.setLastChanged(new Date());
		pipeline.setName("TestPipe");
		pipeline.setStatus("test");
		
		em.getTransaction().begin();
		em.persist(pipeline);
		
		Assert.assertNotNull(pipeline.getId());
		
		em.getTransaction().rollback();
	}
	
	@Test
	public void savePipelineWithStepsTest(){
		PipelineDTO pipeline = new PipelineDTO();
		pipeline.setLastChanged(new Date());
		pipeline.setName("TestPipe");
		pipeline.setStatus("test");
		
		PipelineStepDTO step1 = createPipelineStep(0, StepTypeEnum.SOURCE, "127.0.01");
		PipelineStepDTO step2 = createPipelineStep(1, StepTypeEnum.OPERATION, "avg");
		PipelineStepDTO step3 = createPipelineStep(2, StepTypeEnum.SINK, "127.0.01");
		
		pipeline.getPipelineSteps().addAll(Arrays.asList(step1, step2, step3));
		
		em.getTransaction().begin();
		em.persist(pipeline);
		
		Assert.assertNotNull(pipeline.getId());
		Assert.assertNotNull(pipeline.getPipelineSteps().get(0).getId());
		
		em.getTransaction().rollback();
	}
	
	@Test
	public void getConfigTest(){
		String hqlQuery = "SELECT conf FROM ConfigurationDTO conf WHERE conf.configKey = :key";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("key", ConfigurationService.CONFIG_MONITOR_INTERVAL);
		
		System.out.println(((ConfigurationDTO)query.getSingleResult()).getConfigValue());
	}
	
	private PipelineStepDTO createPipelineStep(int number, StepTypeEnum type, String content){
		PipelineStepDTO step1 = new PipelineStepDTO();
		step1.setContent(content);
		step1.setStepNumber(number);
		step1.setType(type);
		return step1;
	}
}
