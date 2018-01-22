package de.cit.backend.mgmt.agent;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.squareup.okhttp.MediaType;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.ApiException;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.PipelineApi;
import de.cit.backend.agent.api.model.PipelineResponse;

public class PipelineApiTest {

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
			PipelineResponse resp = pipelineApi.pipelinePost(script, null, null);
			Assert.assertNotNull(resp.getID());
		} catch (ApiException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testMimeType(){
		System.out.println(MediaType.parse("text/plain"));
	}
}
