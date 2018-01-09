package de.cit.backend.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.model.Pipeline;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public abstract class ProjectApiService {
      public abstract Response projectIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelineDelete(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelineGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelinePost(Pipeline body,Integer id, SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdUsersGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdDelete(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdPost(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
}
