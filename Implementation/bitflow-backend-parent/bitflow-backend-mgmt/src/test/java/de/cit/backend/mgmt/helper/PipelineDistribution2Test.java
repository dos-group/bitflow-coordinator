package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.helper.service.PipelineDistributer2;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PipelineDistribution2Test extends AbstractPipelineTest{

	
	@Test
	public void testPipelineDistributer(){
		PipelineDTO test1 = createTestPipelineLinear();
		PipelineDistributer2.distributePipeline(test1);
		Assert.assertTrue(test1.countAgents() == 1);
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		PipelineDistributer2.distributePipeline(test2);
		Assert.assertTrue(test2.countAgents() == 1);
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		PipelineDistributer2.distributePipeline(test3);
		Assert.assertTrue(test3.countAgents() == 1);
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		PipelineDistributer2.distributePipeline(test4);
		Assert.assertTrue(test4.countAgents() == 1);
		
		PipelineDTO test5 = createTestPipeline2SinksSimple();
		PipelineDistributer2.distributePipeline(test5);
		Assert.assertTrue(test5.countAgents() == 3);
		
		PipelineDTO test6 = createTestPipeline3Sinks();
		PipelineDistributer2.distributePipeline(test6);
		Assert.assertTrue(test6.countAgents() == 5);
		
	}
	
	@Test
	public void testPipelineDistributerWithLimit(){
		PipelineDTO test1 = createTestPipelineLinear();
		PipelineDistributer2.distributePipeline(test1, 2);
		Assert.assertTrue(test1.countAgents() == 1);
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		PipelineDistributer2.distributePipeline(test2, 2);
		Assert.assertTrue(test2.countAgents() == 1);
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		PipelineDistributer2.distributePipeline(test3, 3);
		Assert.assertTrue(test3.countAgents() == 1);
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		PipelineDistributer2.distributePipeline(test4, 4);
		Assert.assertTrue(test4.countAgents() == 1);
		
		PipelineDTO test5 = createTestPipeline2SinksSimple();
		PipelineDistributer2.distributePipeline(test5, 2);
		Assert.assertTrue(test5.countAgents() == 2);
		
		PipelineDTO test6 = createTestPipeline3Sinks();
		PipelineDistributer2.distributePipeline(test6, 3);
		Assert.assertTrue(test6.countAgents() == 3);
	}
}
