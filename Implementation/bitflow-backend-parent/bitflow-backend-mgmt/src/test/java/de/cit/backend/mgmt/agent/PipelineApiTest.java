package de.cit.backend.mgmt.agent;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.ApiException;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.PipelineApi;
import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.persistence.model.AgentDTO;

public class PipelineApiTest {

	private static final String test = "listen tcp %s: bind: Normalerweise";

	@Test
	@Ignore("You have to have an agent running on localhost port 8082 for this test to succeed.")
	public void testPipelinePost(){
		ApiClient conf = Configuration.getDefaultApiClient();
		conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
		conf.setBasePath("http://127.0.0.1:8082");
		
		String script = "file://C:/Users/Sven/Workspaces/CIT_workspace/Bitflow-Pipeline/bin/test2.csv -> "
				+ "avg() -> "
				+ "file://C:/Users/Sven/Workspaces/CIT_workspace/Bitflow-Pipeline/bin/output.csv";
		PipelineApi pipelineApi = new PipelineApi(conf);
		try {
			PipelineResponse resp = pipelineApi.pipelinePost(script, null, "-tcp-limit 1");
			Assert.assertNotNull(resp.getID());
		} catch (ApiException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	@Ignore
	public void testPipelinePostFindPort(){
		
		String agentIp = "http://127.0.0.1:8082";
		int startPort = 8085;
		String listenIP = "127.0.0.1";
		String script = "listen://%s -> "
				+ "avg() -> "
				+ "file://C:/Users/Sven/Workspaces/CIT_workspace/Bitflow-Pipeline/bin/output.csv";
		
//		DeploymentInfo deployInfo = new DeploymentInfo(agentIp, script, listenIP, 8085);
//		PipelineResponse response = deployPipelineOn(deployInfo);
//		Assert.assertNotNull(response);
//		Assert.assertNotNull(response.getID());
	}
	
	private PipelineResponse deployPipelineOn(DeploymentInformation deploy){
		for(int i = 0; i < 100; i++){
			ApiClient conf = Configuration.getDefaultApiClient();
			conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
			conf.setBasePath(deploy.getAgentAdress());

			PipelineApi pipelineApi = new PipelineApi(conf);
			System.out.println(i);
			try {
				System.out.println(deploy.getFormattedScript(i));
				PipelineResponse resp = pipelineApi.pipelinePost(deploy.getFormattedScript(i), null, null);
				System.out.println("Deployed on " + deploy.getAdjustedTCPAdress(i));
				return resp;
			} catch (ApiException e) {
				System.out.println(String.format(test, deploy.getAdjustedTCPAdress(i)));
				if(e.getResponseBody() != null && e.getResponseBody().contains(String.format(test, deploy.getAdjustedTCPAdress(i)))){
					continue;					
				}
//				e.printStackTrace();
			}			
		}
		return null;
	}
	
	@Test
	public void testMimeType(){
		System.out.println(MediaType.parse("text/plain"));
	}
	
	@Test
	public void testStringFormat(){
		String test = "String ohne Parameter";
		Assert.assertEquals(test, String.format(test, "void"));
	}
	
	@Test
	public void testHttpParse(){
		HttpUrl url = HttpUrl.parse("127.0.0.1:8082/pipeline?params=-tcp-limit%201");
		Assert.assertTrue(url == null);
		
		HttpUrl url2 = HttpUrl.parse("http://127.0.0.1:8082/pipeline?params=-tcp-limit%201");
		Assert.assertTrue(url2 != null);
	}
	
	@Test
	public void testDeploymentScript(){
		DeploymentInformation deploy = new DeploymentInformation(1);
		String script = "127.0.0.1 -> avg() -> output.csv";
		deploy.appendToScript(script);
		
		Assert.assertEquals(script, deploy.getFormattedScript(6000, "false.csv", "shit.csv"));
	}
	
	@Test
	public void testDeploymentScript2(){
		AgentDTO agent = new AgentDTO();
		agent.setIpAddress("127.0.0.1");
		agent.setPort((short)8082);
		
		DeploymentInformation deploy = new DeploymentInformation(1);
		String script = DeploymentInformation.PLACEHOLDER_SOURCE + " -> avg() -> " + DeploymentInformation.PLACEHOLDER_SINK;
		deploy.appendToScript(script);
		deploy.deployOnAgent(agent, 60000);
		
		Assert.assertEquals("listen://127.0.0.1:60001 -> avg() -> proxyIP", deploy.getFormattedScript(1, "proxyIP"));
	}
	
}
