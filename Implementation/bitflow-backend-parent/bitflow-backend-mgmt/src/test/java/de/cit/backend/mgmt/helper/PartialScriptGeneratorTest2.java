package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.helper.service.PartialScriptGeneratorSimple;
import de.cit.backend.mgmt.helper.service.PipelineDistributer3;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PartialScriptGeneratorTest2 extends AbstractPipelineTest{
	
	@Test
	public void testPartialScriptGenerationSequence() throws BitflowException{
		System.out.println("Building script for sequence");
		PipelineDTO test = createTestPipelineLinear();
		DeploymentInformation[] deploy = generateScripts(test);
		printDeployment(deploy);

		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> avg(param1=value1, param2=value2) -> output.csv");
	}
	
	@Test
	public void testPartialScriptGenerationSimpleFork() throws BitflowException{
		System.out.println("Building script for simple fork, one sink");
		PipelineDTO test = createTestPipelineForkedOnce();
		DeploymentInformation[] deploy = generateScripts(test);
		printDeployment(deploy);
		
		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> { %sink%; avg(param1=value1, param2=value2) -> %sink% } ");
		Assert.assertEquals(deploy[1].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> %sink%");
		Assert.assertEquals(deploy[2].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> output.csv");
	}
	
	@Test
	public void testPartialScriptGenerationSimpleForkOnOneAgent() throws BitflowException{
		System.out.println("Building script for simple fork, one sink, on one agent");
		PipelineDTO test = createTestPipelineForkedOnce();
		DeploymentInformation[] deploy = generateScripts(test, 1);
		printDeployment(deploy);
		
		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> { avg(param1=value1, param2=value2) ; avg(param1=value1, param2=value2) } -> avg(param1=value1, param2=value2) -> output.csv");
	}
	
	@Test
	public void testPartialScriptGenerationSimpleFork2() throws BitflowException{
		System.out.println("Building script for simple fork 2, one sink");
		PipelineDTO test = createTestPipelineForkedOnce2();
		DeploymentInformation[] deploy = generateScripts(test);
		printDeployment(deploy);
		
		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> { %sink%; avg(param1=value1, param2=value2) -> avg(param1=value1, param2=value2) -> %sink% } ");
		Assert.assertEquals(deploy[1].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> avg(param1=value1, param2=value2) -> %sink%");
		Assert.assertEquals(deploy[2].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> output.csv");
	}
	
	@Test
	public void testPartialScriptGenerationForkNotJoined() throws BitflowException{
		System.out.println("Building script for simple with 2 sinks");
		PipelineDTO test = createTestPipeline2SinksSimple();
		DeploymentInformation[] deploy = generateScripts(test);
		printDeployment(deploy);
		
		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> { %sink%; avg(param1=value1, param2=value2) -> output.csv } ");
		Assert.assertEquals(deploy[1].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> output.csv");
	}

	@Test
	public void testPartialScriptGenerationInnerForkNotJoined() throws BitflowException{
		System.out.println("Building script for 3 sinks");
		PipelineDTO test = createTestPipeline3Sinks();
		DeploymentInformation[] deploy = generateScripts(test);
		printDeployment(deploy);
		
		Assert.assertEquals(deploy[0].getScript(),
				"127.0.0.1 -> avg(param1=value1, param2=value2) -> { %sink%; avg(param1=value1, param2=value2) -> avg(param1=value1, param2=value2) -> { %sink%; avg(param1=value1, param2=value2) -> output.csv }  } ");
		Assert.assertEquals(deploy[1].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> output.csv");
		Assert.assertEquals(deploy[2].getScript(),
				"%source% -> avg(param1=value1, param2=value2) -> output.csv");
	}
	
	
	
	private void printDeployment(DeploymentInformation[] deploy) {
		for(DeploymentInformation info : deploy){
			System.out.println(info);			
		}
	}

	private DeploymentInformation[] generateScripts(PipelineDTO pipeline, int size) throws BitflowException {
		PipelineDistributer3 builder;
		if(size >= 0){
			builder = new PipelineDistributer3(size);
		}else{
			builder = new PipelineDistributer3();
		}
		PartialScriptGeneratorSimple generator = new PartialScriptGeneratorSimple();

		builder.distributePipeline(pipeline);
		return generator.generateParallelScripts(pipeline);
	}

	private DeploymentInformation[] generateScripts(PipelineDTO pipeline) throws BitflowException{
		return generateScripts(pipeline, -1);
	}
}
