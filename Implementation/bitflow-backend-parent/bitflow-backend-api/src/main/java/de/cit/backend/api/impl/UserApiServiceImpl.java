package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.UserApiService;
import de.cit.backend.api.converter.UserConverter;
import de.cit.backend.api.model.ChangePassword;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;
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
	public Response userIdChangePasswordPost(ChangePassword body, Integer id, SecurityContext securityContext)
			throws NotFoundException {
		try {
			final UserDTO target = userService.loadUser(id);
			if(target == null) {
				return Response.status(404).entity(new BitflowException(ExceptionConstants.USER_NOT_FOUND_ERROR).toFrontendFormat()).build();
			}
			if(!securityContext.isUserInRole(UserRoleEnum.ADMIN.name()) && !securityContext.getUserPrincipal().getName().equals(target.getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
			userService.changePassword(id, securityContext.getUserPrincipal().getName(), body.getOldPassword(), body.getNewPassword());
		} catch (BitflowException e) {
			return Response.status(e.getHttpStatus()).entity(e.toFrontendFormat()).build();
		} catch (Exception e) {
			return Response.status(400).entity(new BitflowException(e).toFrontendFormat()).build();
		}
		return Response.ok().build();
	}
	
	@Override
	public Response userIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException {
		try {
			final UserDTO target = userService.loadUser(id);
			if(target == null) {
				return Response.status(404).entity(new BitflowException(ExceptionConstants.USER_NOT_FOUND_ERROR).toFrontendFormat()).build();
			}
			if(!securityContext.isUserInRole(UserRoleEnum.ADMIN.name()) && !securityContext.getUserPrincipal().getName().equals(target.getName())) {
				throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
			}
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

			UserDTO user = userService.updateUser(id, new UserConverter().convertToBackend(body));
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
			UserDTO user = new UserConverter().convertToBackend(body);
			user.setPassword(body.getPassword());
			user = userService.createUser(user);
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
