package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.helper.service.CycleChecker;
import de.cit.backend.mgmt.helper.service.PipelineSort;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PipelineOrderTest extends AbstractPipelineTest{

	@Test
	public void testSortLinearPipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineLinear();
		checkOrder(testObj, "0123");
	}
	
	@Test
	public void testSortForkedOncePipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedOnce();
		checkOrder(testObj, "013245");
	}
	
	@Test
	public void testSortForkedOnce2Pipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedOnce2();
		checkOrder(testObj, "01452367");
	}
	
	@Test
	public void testSortForkedMultiplePipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedMultiple();
		checkOrder(testObj, "0167243589");
	}
	
	@Test
	public void testSortPipeline2SinksSimple() throws IllegalStateException{
		PipelineDTO testObj = createTestPipeline2SinksSimple();
		checkOrder(testObj, "012345");
	}
	
	@Test
	public void testSortPipeline3Sinks() throws IllegalStateException{
		PipelineDTO testObj = createTestPipeline3Sinks();
		checkOrder(testObj, "0145896723");
	}
	
	@Test
	public void testSortPipeline2Sinks() throws IllegalStateException{
		PipelineDTO testObj = createTestPipeline2Sinks();
		checkOrder(testObj, "01458967101123");
	}
	
	@Test
	public void testSortPipelineWithCycle() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineWithCycle();
		
		try {
			CycleChecker.checkForCycles(testObj);
			Assert.fail("Exception expected");
		} catch (IllegalStateException e) {
			Assert.assertTrue(e.getMessage().contains("cycles"));
		}
	}
	
	private void checkOrder(PipelineDTO pipeline, String expected){
		PipelineSort.sortPipeline(pipeline);
		
		String actual = "";
		for(PipelineStepDTO step : pipeline.getPipelineSteps()){
			actual += step.getStepNumber();
		}
	
		Assert.assertEquals(expected, actual);
	}
}
