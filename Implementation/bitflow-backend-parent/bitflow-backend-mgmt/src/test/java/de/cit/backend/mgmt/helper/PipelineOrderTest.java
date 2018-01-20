package de.cit.backend.mgmt.helper;

import javax.jms.IllegalStateException;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PipelineOrderTest extends AbstractPipelineTest{

	@Test
	public void testSortLinearPipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineLinear();
		
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		
		String expected = "";
		String actual = "";
		int i = 0;
		for(PipelineStepDTO step : testObj.getPipelineSteps()){
			actual += step.getStepNumber();
			expected += i;
			i++;
		}

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSortForkedOncePipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedOnce();
		
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		
		String expected = "013245";
		String actual = "";
		for(PipelineStepDTO step : testObj.getPipelineSteps()){
			actual += step.getStepNumber();
		}
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSortForkedOnce2Pipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedOnce2();
		
		String before = "";
		for(int i=0; i< testObj.getPipelineSteps().size(); i++){
			before += testObj.getPipelineSteps().get(i).getStepNumber();
		}
//		System.out.println(before);
		
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		PipelineSort.sortPipeline(testObj);
		
		String expected = "01452367";
		String actual = "";
		for(PipelineStepDTO step : testObj.getPipelineSteps()){
			actual += step.getStepNumber();
		}
	
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSortForkedMultiplePipeline() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedMultiple();
		
		String before = "";
		for(int i=0; i< testObj.getPipelineSteps().size(); i++){
			before += testObj.getPipelineSteps().get(i).getStepNumber();
		}
//		System.out.println(before);
		
		PipelineSort.sortPipeline(testObj);
		
		String expected = "0167243589";
		String actual = "";
		for(PipelineStepDTO step : testObj.getPipelineSteps()){
			actual += step.getStepNumber();
		}
	
		Assert.assertEquals(expected, actual);
	}
}
