package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.LoginApiService;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.converter.UserConverter;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class LoginApiServiceImpl extends LoginApiService {

	protected IUserService userService;

	public LoginApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			userService = (IUserService) ctx.lookup("java:module/UserService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response loginPost(SecurityContext securityContext) throws NotFoundException {
		UserDTO userDB = userService.loadUser(securityContext.getUserPrincipal().getName());
		if (userDB == null) {
			return Response.status(404).build();
		}
		return Response.ok().entity(new UserConverter().convertToFrontend(userDB)).build();
	}
}
