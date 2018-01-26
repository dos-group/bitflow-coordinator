package de.cit.backend.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.model.ChangePassword;
import de.cit.backend.api.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public abstract class UserApiService {
	public abstract Response userIdChangePasswordPost(ChangePassword body, Integer id, SecurityContext securityContext)
			throws NotFoundException;

	public abstract Response userIdDelete(Integer id, SecurityContext securityContext) throws NotFoundException;

	public abstract Response userIdGet(Integer id, SecurityContext securityContext) throws NotFoundException;

	public abstract Response userIdPost(User body, Integer id, SecurityContext securityContext)
			throws NotFoundException;

	public abstract Response userPost(User body, SecurityContext securityContext) throws NotFoundException;
}
