package de.cit.backend.api;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.api.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public abstract class ProjectApiService {
      public abstract Response projectIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelineDelete(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelineGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelinePost(Pipeline body,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdUsersGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdDelete(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdPost(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
}
