package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.helper.service.PipelineDistributer;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

public class PipelineDistributionTest extends AbstractPipelineTest{

	
	@Test
	public void testPipelineDistributer() throws BitflowException{
		PipelineDistributer builder = new PipelineDistributer();
		
		PipelineDTO test1 = createTestPipelineLinear();
		builder.distributePipeline(test1);
		Assert.assertTrue(test1.countAgents() == 1);
		
		builder.clear();
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		builder.distributePipeline(test2);
		System.out.println(test2.getPipelineSteps());
		Assert.assertTrue(test2.countAgents() == 3);
		isSourceAndSinkOnAgent(test2, 0, 2);
		
		builder.clear();
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		builder.distributePipeline(test3);
		System.out.println(test3.getPipelineSteps());
		Assert.assertTrue(test3.countAgents() == 3);
		isSourceAndSinkOnAgent(test3, 0, 2);
		
		builder.clear();

		PipelineDTO test4 = createTestPipelineForkedMultiple();
		builder.distributePipeline(test4);
		System.out.println(test4.getPipelineSteps());
		Assert.assertTrue(test4.countAgents() == 5);
		isSourceAndSinkOnAgent(test4, 0, 4);
		
		builder.clear();
		
		PipelineDTO test5 = createTestPipeline2SinksSimple();
		builder.distributePipeline(test5);
		System.out.println(test5.getPipelineSteps());
		Assert.assertTrue(test5.countAgents() == 2);
		
		builder.clear();
		
		PipelineDTO test6 = createTestPipeline3Sinks();
		builder.distributePipeline(test6);
		System.out.println(test6.getPipelineSteps());
		Assert.assertTrue(test6.countAgents() == 3);
		
		builder.clear();

		PipelineDTO test7 = createTestPipelineForkedMultipleSeparat();
		builder.distributePipeline(test7);
		System.out.println(test7.getPipelineSteps());
		Assert.assertTrue(test7.countAgents() == 5);
		isSourceAndSinkOnAgent(test7, 0, 4);
		
		builder.clear();

		PipelineDTO test8 = createTestPipeline2Sinks();
		builder.distributePipeline(test8);
		System.out.println(test8.getPipelineSteps());
		Assert.assertTrue(test8.countAgents() == 4);
	}
	
	private void isSourceAndSinkOnAgent(PipelineDTO test2, int source, int sink) {
		for(PipelineStepDTO step : test2.getPipelineSteps()){
			if(step.getType() == StepTypeEnum.SOURCE){
				Assert.assertEquals(source, step.getAgentAdvice());
			}else if(step.getType() == StepTypeEnum.SINK){
				Assert.assertEquals(sink, step.getAgentAdvice());
			}
		}
	}

	private void isSourceAndSinkOnSameAgent(PipelineDTO test2) {
		for(PipelineStepDTO step : test2.getPipelineSteps()){
			if(step.getType() != StepTypeEnum.OPERATION){
				Assert.assertEquals(0, step.getAgentAdvice());
			}
		}
	}

	@Test
	public void testPipelineDistributerWithLimit() throws BitflowException{
		System.out.println("Test with Limits");
		PipelineDistributer builder = new PipelineDistributer(2);
		
		PipelineDTO test1 = createTestPipelineLinear();
		builder.distributePipeline(test1);
		Assert.assertTrue(test1.countAgents() == 1);
		
		builder.clear();
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		builder.distributePipeline(test2);
		System.out.println(test2.getPipelineSteps());
		Assert.assertTrue(test2.countAgents() == 1);
		
		builder.clear();
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		builder.distributePipeline(test3);
		System.out.println(test3.getPipelineSteps());
		Assert.assertTrue(test3.countAgents() == 1);
		
		builder.clear();
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		builder.distributePipeline(test4);
		System.out.println(test4.getPipelineSteps());
		Assert.assertTrue(test4.countAgents() == 1);
		
		builder.clear();
		
		PipelineDTO test5 = createTestPipeline2SinksSimple();
		builder.distributePipeline(test5);
		System.out.println(test5.getPipelineSteps());
		Assert.assertTrue(test5.countAgents() == 2);
		
		builder.clear();
		
		PipelineDTO test6 = createTestPipeline3Sinks();
		builder.distributePipeline(test6);
		System.out.println(test6.getPipelineSteps());
		Assert.assertTrue(test6.countAgents() == 2);
		
		builder.clear();

		PipelineDTO test7 = createTestPipelineForkedMultipleSeparat();
		builder.distributePipeline(test7);
		System.out.println(test7.getPipelineSteps());
		Assert.assertTrue(test7.countAgents() == 1);
		isSourceAndSinkOnAgent(test7, 0, 0);
		
		builder.clear();

		PipelineDTO test8 = createTestPipeline2Sinks();
		builder.distributePipeline(test8);
		System.out.println(test8.getPipelineSteps());
		Assert.assertTrue(test8.countAgents() == 2);
	}
}
