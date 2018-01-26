package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.ApiResponseMessage;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.UserApiService;
import de.cit.backend.api.converter.UserConverter;
import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
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
          try {
        	  userService.deleteUser(id);
          }catch(IllegalArgumentException e)
          {
			  return Response.status(404).entity(new BitflowException(ExceptionConstants.USER_NOT_FOUND_ERROR).toFrontendFormat()).build();
          } catch(ValidationException e) {
			  return Response.status(404).entity(new BitflowException(ExceptionConstants.VALIDATION_ERROR).toFrontendFormat()).build();
          } catch(Exception e) {
			  return Response.status(404).entity(new BitflowException(ExceptionConstants.UNKNOWN_ERROR).toFrontendFormat()).build();
          }
          return Response.ok().build();
          //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "deleted")).build();
  }
      @Override
      public Response userIdGet(Integer id,SecurityContext securityContext)
    		  throws NotFoundException {
    	  UserDTO userDB = userService.loadUser(id);
    	  if(userDB == null){
    		  return Response.status(404).entity(new BitflowException(ExceptionConstants.USER_NOT_FOUND_ERROR).toFrontendFormat()).build();
    	  }
    	  return Response.ok().entity(new UserConverter().convertToFrontend(userDB)).build();
  }
      @Override
      public Response userIdPost(User body, Integer id, SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
 		if(!body.getName().equals(securityContext.getUserPrincipal().getName())) {
			  return Response.status(403).build();
		  }
      	try {
      		 UserDTO user = userService.updateUser(body.getID(), new UserConverter().convertToBackend(body));
             return Response.ok().entity(new UserConverter().convertToFrontend(user)).build();
      	} catch (IllegalArgumentException e) {
     		 return Response.status(404).entity(new BitflowException(ExceptionConstants.USER_NOT_FOUND_ERROR).toFrontendFormat()).build();      		
      	} catch (ValidationException e) {
     		 return Response.status(400).entity(new BitflowException(ExceptionConstants.VALIDATION_ERROR).toFrontendFormat()).build();
      	} catch(Exception e) {
      		 return Response.status(400).entity(new BitflowException(ExceptionConstants.UNKNOWN_ERROR).toFrontendFormat()).build();
      	}
      //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response userPost(User body,SecurityContext securityContext)
      throws NotFoundException {
    	  if(!securityContext.isUserInRole(UserRoleEnum.ADMIN.name())) {
			  return Response.status(403).build();
		  }
    	  try{
    		  userService.createUser(new UserConverter().convertToBackend(body));
    	  }catch (Exception e) {
			return Response.status(400).build();
		}
      return Response.ok().build();
  }
}
