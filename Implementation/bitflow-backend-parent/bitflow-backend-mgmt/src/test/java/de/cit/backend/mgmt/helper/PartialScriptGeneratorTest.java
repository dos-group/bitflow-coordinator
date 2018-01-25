package de.cit.backend.mgmt.helper;

import org.junit.Test;

import de.cit.backend.mgmt.helper.model.DeploymentInfo;
import de.cit.backend.mgmt.helper.service.PartialScriptGenerator;
import de.cit.backend.mgmt.helper.service.PipelineDistributer2;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PartialScriptGeneratorTest extends AbstractPipelineTest{

	@Test
	public void testPartialScriptGenerationSimpleFork(){
		PipelineDTO test = createTestPipelineForkedOnce2();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInfo[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInfo info : deploy){
			System.out.println(info);			
		}
	}
	
	@Test
	public void testPartialScriptGenerationFor2Sinks(){
		PipelineDTO test = createTestPipeline2SinksSimple();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInfo[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInfo info : deploy){
			System.out.println(info);			
		}
	}
	
	@Test
	public void testPartialScriptGenerationFor3Sinks(){
		PipelineDTO test = createTestPipeline3Sinks();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInfo[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInfo info : deploy){
			System.out.println(info);			
		}
	}
}
