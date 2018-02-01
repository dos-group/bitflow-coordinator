package de.cit.backend.api;

import de.cit.backend.api.*;
import de.cit.backend.api.model.*;


import de.cit.backend.api.model.DeploymentInfo;
import de.cit.backend.api.model.ErrorMessage;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.PipelineHistory;
import de.cit.backend.api.model.Project;
import de.cit.backend.api.model.User;

import java.util.List;
import de.cit.backend.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-02-01T00:26:34.119+01:00")
public abstract class ProjectApiService {
      public abstract Response projectIdDelete(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelinePost(Pipeline body,Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPipelinesGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdPost(Integer id,Project project,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectIdUsersGet(Integer id,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectPost(Project project,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdPipelinePipelineIdDelete(Integer projectId,Integer pipelineId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdPipelinePipelineIdGet(Integer projectId,Integer pipelineId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdPipelinePipelineIdHistoryGet(Integer projectId,Integer pipelineId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdPipelinePipelineIdPost(Pipeline body,Integer projectId,Integer pipelineId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdPipelinePipelineIdStartPost(Integer projectId,Integer pipelineId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdDelete(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response projectProjectIdUsersUserIdPost(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException;
}
