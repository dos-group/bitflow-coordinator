package de.cit.backend.api.impl;

import de.cit.backend.api.*;
import io.swagger.model.*;

import io.swagger.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-08T14:35:56.681+01:00")
public class UserApiServiceImpl extends UserApiService {
    @Override
    public Response getUser(Long userId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
