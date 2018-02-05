package de.cit.backend.mgmt.helper;

import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.helper.service.PartialScriptGenerator;
import de.cit.backend.mgmt.helper.service.PipelineDistributer2;
import de.cit.backend.mgmt.helper.service.PipelineDistributer3;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PartialScriptGeneratorTest extends AbstractPipelineTest{

	@Test
	public void testPartialScriptGenerationSimpleFork(){
		System.out.println("Using PipelineDistributer2:");
		PipelineDTO test = createTestPipelineForkedOnce2();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInformation[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInformation info : deploy){
			System.out.println(info);			
		}
	}
	
	@Test
	public void testPartialScriptGenerationSimpleForkNew() throws BitflowException{
		System.out.println("Using PipelineDistributer3:");
		PipelineDistributer3 builder = new PipelineDistributer3();
		PipelineDTO test = createTestPipelineForkedOnce2();
		builder.distributePipeline(test);
		DeploymentInformation[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInformation info : deploy){
			System.out.println(info);			
		}
	}
	
	@Test
	public void testPartialScriptGenerationFor2Sinks(){
		PipelineDTO test = createTestPipeline2SinksSimple();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInformation[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInformation info : deploy){
			System.out.println(info);			
		}
	}
	
	@Test
	public void testPartialScriptGenerationFor3Sinks(){
		PipelineDTO test = createTestPipeline3Sinks();
		PipelineDistributer2.distributePipeline(test);
		DeploymentInformation[] deploy = PartialScriptGenerator.generateParallelScripts(test);
		
		for(DeploymentInformation info : deploy){
			System.out.println(info);			
		}
	}
}
