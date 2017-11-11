package de.cit.backend.api;

import de.cit.backend.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-11T17:59:15.762+01:00")
public abstract class UserApiService {
    public abstract Response getUser(Long userId,SecurityContext securityContext) throws NotFoundException;
}
