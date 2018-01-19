package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.StepTypeEnum;

public class ScriptGeneratorTest {

	public static final String LOCAL_PORT_SOURCE = "127.0.0.1";
	public static final String FILE_SINK = "output.csv";
	public static final String AVG_OPERATION = "avg";
	
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
	public void testPipelineSequenceGeneration(){
		PipelineStepDTO testSource = createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE);
		
		PipelineStepDTO testSink = createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK);
		
		PipelineStepDTO testOp = createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION);
		
		PipelineDTO pipe = new PipelineDTO();
		pipe.getPipelineSteps().add(testSource);
		pipe.getPipelineSteps().add(testOp);
		pipe.getPipelineSteps().add(testSink);
		
		Assert.assertEquals(LOCAL_PORT_SOURCE + " -> " + AVG_OPERATION + "(param1=value1, param2=value2)" + " -> " + FILE_SINK,
				ScriptGenerator.generateScriptForPipeline(pipe));
	}
	
	private PipelineStepDTO createTestPipelineStep(String content, StepTypeEnum type) {
		PipelineStepDTO testStep = new PipelineStepDTO();
		testStep.setType(type);
		testStep.setContent(content);
		testStep.getParams().add(createTestPipelineParam("param1", "value1"));
		testStep.getParams().add(createTestPipelineParam("param2", "value2"));
		return testStep;
	}

	private PipelineParameterDTO createTestPipelineParam(String name, String value) {
		PipelineParameterDTO param = new PipelineParameterDTO();
		param.setParamName(name);
		param.setParamValue(value);
		return param;
	}
}
