package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.helper.service.PipelineDistributer;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PipelineDistributionTest extends AbstractPipelineTest{

	@Test
	public void testPipelineState(){
		PipelineDTO test1 = createTestPipelineLinear();
		Assert.assertTrue(test1.isSequence());
		Assert.assertTrue(test1.countForks() == 0);
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		Assert.assertTrue(!test2.isSequence());
		Assert.assertTrue(test2.countForks() == 1);
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		Assert.assertTrue(!test3.isSequence());
		Assert.assertTrue(test3.countForks() == 1);
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		Assert.assertTrue(!test4.isSequence());
		Assert.assertTrue(test4.countForks() == 2);
	}
	
	@Test
	public void testPipelineDistributer(){
		PipelineDTO test1 = createTestPipelineLinear();
		PipelineDistributer.distributePipeline(test1);
		Assert.assertTrue(test1.countAgents() == 1);
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		PipelineDistributer.distributePipeline(test2);
		Assert.assertTrue(test2.countAgents() == 4);
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		PipelineDistributer.distributePipeline(test3);
		Assert.assertTrue(test3.countAgents() == 4);
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		PipelineDistributer.distributePipeline(test4);
		Assert.assertTrue(test4.countAgents() == 7);
	}
	
	@Test
	public void testPipelineDistributerWithLimit(){
		PipelineDTO test1 = createTestPipelineLinear();
		PipelineDistributer.distributePipeline(test1, 2);
		Assert.assertTrue(test1.countAgents() == 1);
		
		PipelineDTO test2 = createTestPipelineForkedOnce();
		PipelineDistributer.distributePipeline(test2, 2);
		Assert.assertTrue(test2.countAgents() == 1);
		
		PipelineDTO test3 = createTestPipelineForkedOnce2();
		PipelineDistributer.distributePipeline(test3, 3);
		Assert.assertTrue(test3.countAgents() == 1);
		
		PipelineDTO test4 = createTestPipelineForkedMultiple();
		PipelineDistributer.distributePipeline(test4, 4);
		Assert.assertTrue(test4.countAgents() == 4);
	}
}
