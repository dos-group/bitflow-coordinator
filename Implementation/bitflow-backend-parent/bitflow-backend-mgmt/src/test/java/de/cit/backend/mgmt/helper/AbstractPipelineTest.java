package de.cit.backend.mgmt.helper;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

public abstract class AbstractPipelineTest {

	public static final String LOCAL_PORT_SOURCE = "127.0.0.1";
	public static final String FILE_SINK = "output.csv";
	public static final String AVG_OPERATION = "avg";
	public static final String AVG_OPERATION_PARAM = AVG_OPERATION + "(param1=value1, param2=value2)";

	protected PipelineDTO createTestPipelineEmpty() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 * 
	 * o--o--o--o
	 * 
	 * @param size
	 * @return
	 */
	protected PipelineDTO createTestPipelineLinear() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 3));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(0)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(1)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(2)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}

	/**
	 *      o 
	 *     / \
	 * o--o   o--o
	 *     \ /
	 *      o
	 * @return
	 */
	protected PipelineDTO createTestPipelineForkedOnce() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 5));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(0)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 3, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(2), succ.get(3)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(4)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}

	/**
	 *      o--o 
	 *     /    \
	 * o--o      o--o
	 *     \    /
	 *      o--o
	 * @return
	 */
	protected PipelineDTO createTestPipelineForkedOnce2() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 7));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 6, succ.get(0)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 5, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(2)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 3, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(4)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(3), succ.get(5)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(6)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 * 
	 *        o
	 *       / \
	 *      o   o
	 *     / \ / \
	 * o--o   o   o--o
	 *     \     /
	 *      o---o
	 * @return
	 */
	protected PipelineDTO createTestPipelineForkedMultiple() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 9));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 8, succ.get(0)));
		//lower branch
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 7, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 6, succ.get(2)));
		//upper branch
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 5, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(4)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 3, succ.get(4)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(5), succ.get(6)));
		//source
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(3), succ.get(7)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(8)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 *      o      o
	 *     / \    / \
	 * o--o   o--o   o--o
	 *     \ /    \ /
	 *      o      o
	 * @return
	 */
	protected PipelineDTO createTestPipelineForkedMultipleSeparat() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 9));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 8, succ.get(0)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 7, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 6, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 5, succ.get(2), succ.get(3)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(4)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 3, succ.get(5)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(5)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(6), succ.get(7)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(8)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 *      o--o 
	 *     /    
	 * o--o      
	 *     \    
	 *      o--o
	 * @return
	 */
	protected PipelineDTO createTestPipeline2SinksSimple() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 5));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(0)));
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 3));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(2)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(1), succ.get(3)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(4)));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 *           o--o
	 *          /
	 *      o--o
	 *     /    \
	 * o--o      o--o
	 *     \    
	 *      o--o
	 * @return
	 */
	protected PipelineDTO createTestPipeline3Sinks() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 9));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 8, succ.get(0)));
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 7));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 6, succ.get(2)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 5, succ.get(1), succ.get(3)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(4)));
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 3));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(6)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(7), succ.get(5)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(8)));
		pipe.setPipelineSteps(succ);
		return pipe;
	}

	/**
	 *           o--o
	 *          /    \
	 *      o--o      o--o
	 *     /    \    /
	 * o--o      o--o
	 *     \    
	 *      o--o
	 * @return
	 */
	protected PipelineDTO createTestPipeline2Sinks() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 11));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 10, succ.get(0)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 9, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 8, succ.get(2)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 7, succ.get(1)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 6, succ.get(4)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 5, succ.get(3), succ.get(5)));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 4, succ.get(6)));
		
		succ.add(createTestPipelineStep(FILE_SINK, StepTypeEnum.SINK, 3));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2, succ.get(8)));
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(7), succ.get(9)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0, succ.get(10)));
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	/**
	 *     /\
	 * o--o--o
	 *
	 * @return
	 */
	protected PipelineDTO createTestPipelineWithCycle() {
		PipelineDTO pipe = new PipelineDTO();
		List<PipelineStepDTO> succ = new ArrayList<>();
		
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 2));
		succ.add(createTestPipelineStep(AVG_OPERATION, StepTypeEnum.OPERATION, 1, succ.get(0)));
		succ.add(createTestPipelineStep(LOCAL_PORT_SOURCE, StepTypeEnum.SOURCE, 0));
		
		succ.get(0).getSuccessors().add(succ.get(1));
		
		pipe.setPipelineSteps(succ);
		return pipe;
	}
	
	protected PipelineStepDTO createTestPipelineStep(String content, StepTypeEnum type, int stepNumber,
			PipelineStepDTO... successors) {
		PipelineStepDTO testStep = new PipelineStepDTO();
		testStep.setType(type);
		testStep.setContent(content);
		if (type == StepTypeEnum.OPERATION) {
			testStep.getParams().add(createTestPipelineParam("param1", "value1"));
			testStep.getParams().add(createTestPipelineParam("param2", "value2"));
		}
		testStep.setStepNumber(stepNumber);
		
		List<PipelineStepDTO> succ = new ArrayList<>();
		for(PipelineStepDTO step : successors){
			succ.add(step);
		}
		testStep.setSuccessors(succ);
		return testStep;
	}

	protected PipelineStepDTO createTestPipelineStep(String content, StepTypeEnum type, int stepNumber) {
		return createTestPipelineStep(content, type, stepNumber, new PipelineStepDTO[0]);
	}

	protected PipelineStepDTO createTestPipelineStep(String content, StepTypeEnum type) {
		return createTestPipelineStep(content, type, 0, new PipelineStepDTO[0]);
	}

	protected PipelineParameterDTO createTestPipelineParam(String name, String value) {
		PipelineParameterDTO param = new PipelineParameterDTO();
		param.setParamName(name);
		param.setParamValue(value);
		return param;
	}
}
