package de.cit.backend.api.impl;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.ApiResponseMessage;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.UserApiService;
import de.cit.backend.api.model.User;
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
      public Response userIdDelete(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response userIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
    	  User user = new User();
    	  user.setEmail("cit@est.de");
    	  user.setID(1);
    	  user.setName("TestUser");
    	  user.setRegisteredSince(new Date());
      return Response.ok().entity(user).build();
  }
      @Override
      public Response userIdPost(User body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response userPost(User body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
}
