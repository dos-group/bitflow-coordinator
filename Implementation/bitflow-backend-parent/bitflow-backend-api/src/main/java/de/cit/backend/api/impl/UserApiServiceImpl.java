package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.UserApiService;
import de.cit.backend.api.converter.UserConverter;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class UserApiServiceImpl extends UserApiService {

	protected IUserService userService;

	public UserApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			userService = (IUserService) ctx.lookup("java:module/UserService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response userIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			userService.deleteUser(id);
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}

	@Override
	public Response userIdGet(Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			UserDTO userDB = userService.loadUser(id);
			return Response.ok().entity(new UserConverter().convertToFrontend(userDB)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response userIdPost(User body, Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			checkAuthorization(securityContext, body);

			UserDTO user = userService.updateUser(body.getID(), new UserConverter().convertToBackend(body));
			return Response.ok().entity(new UserConverter().convertToFrontend(user)).build();
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	@Override
	public Response userPost(User body, SecurityContext securityContext) throws NotFoundException {
		try {
			UserDTO user = userService.createUser(new UserConverter().convertToBackend(body));
			return Response.ok().entity(new UserConverter().convertToFrontend(user)).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
	}

	public void checkAuthorization(SecurityContext ctx, User body) throws BitflowException {
		if (!body.getName().equals(ctx.getUserPrincipal().getName())) {
			throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
		}
	}
}
