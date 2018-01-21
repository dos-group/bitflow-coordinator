package de.cit.backend.mgmt.helper;


import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.StepTypeEnum;

public class ScriptGeneratorTest extends AbstractPipelineTest{
	
	@Test
	public void testPipelineStepGeneration(){
		PipelineStepDTO test = createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE);
		Assert.assertEquals(LOCAL_PORT_SOURCE, ScriptGenerator.generateScriptForPipelineStep(test));
		
		PipelineStepDTO testSink = createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK);
		Assert.assertEquals(FILE_SINK, ScriptGenerator.generateScriptForPipelineStep(testSink));
		
		PipelineStepDTO testOp = createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION);
		Assert.assertEquals(AVG_OPERATION + "(param1=value1, param2=value2)", ScriptGenerator.generateScriptForPipelineStep(testOp));
	}

	@Test
	public void testPipelineGenerationEmpty() {
		PipelineDTO testObj = createTestPipelineEmpty();
		
		String expected = "";
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
//		System.out.println(expected);
//		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPipelineGenerationLinear() {
		PipelineDTO testObj = createTestPipelineLinear();
		
		String expected = LOCAL_PORT_SOURCE + " -> " 
				+ AVG_OPERATION + "(param1=value1, param2=value2) -> "
				+ AVG_OPERATION + "(param1=value1, param2=value2) -> "
				+ FILE_SINK;
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
//		System.out.println(expected);
//		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPipelineGenerationForkOnce() {
		PipelineDTO testObj = createTestPipelineForkedOnce();
		
		String expected = LOCAL_PORT_SOURCE + " -> "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " -> "
				+ FILE_SINK;
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
//		System.out.println(expected);
//		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPipelineGenerationForkOnce2() throws IllegalStateException{
		PipelineDTO testObj = createTestPipelineForkedOnce2();
		
		String expected = LOCAL_PORT_SOURCE + " -> "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " -> "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " -> "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " -> "
				+ FILE_SINK;
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
//		System.out.println(expected);
//		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPipelineGenerationForkMultiple() {
		PipelineDTO testObj = createTestPipelineForkedMultiple();
		
		String expected = LOCAL_PORT_SOURCE + " -> "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " -> "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " -> "
				+ FILE_SINK;
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
//		System.out.println(expected);
//		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPipelineGenerationForkMultipleSeparat() {
		PipelineDTO testObj = createTestPipelineForkedMultipleSeparat();
		
		String expected = LOCAL_PORT_SOURCE + " -> "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " -> "
				+ AVG_OPERATION_PARAM + " -> { "
				+ AVG_OPERATION_PARAM + " ; "
				+ AVG_OPERATION_PARAM + " } -> "
				+ AVG_OPERATION_PARAM + " -> "
				+ FILE_SINK;
		String actual = ScriptGenerator.generateScriptForPipeline(testObj);
		Assert.assertEquals(expected, actual);
	}
}
