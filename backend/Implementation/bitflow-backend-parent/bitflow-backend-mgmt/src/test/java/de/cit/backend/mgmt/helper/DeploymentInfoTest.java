package de.cit.backend.mgmt.helper;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.helper.model.DeploymentInformation;

public class DeploymentInfoTest {

	@Test
	public void testIpBuilder(){
		String port = "8080";
		String ipPort = "192.10.100.43:" + port;
		Assert.assertEquals(port, DeploymentInformation.extractPort(ipPort));
	}
}
