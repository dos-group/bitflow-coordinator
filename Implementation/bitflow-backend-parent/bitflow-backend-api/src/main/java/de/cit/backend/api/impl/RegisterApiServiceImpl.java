package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.RegisterApiService;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class RegisterApiServiceImpl extends RegisterApiService {

	protected IInfoService infoService;

	public RegisterApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			infoService = (IInfoService) ctx.lookup("java:module/InfoService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response registerGet(String ip, Integer port, SecurityContext securityContext) throws NotFoundException {
		// do some magic!
		try {
			infoService.registerAgent(ip, port);
		} catch (Exception e) {
			return BitflowFrontendError.handleException(e);
		}
		return Response.ok().build();
	}
}
