package de.cit.backend.api.impl;

import java.util.Arrays;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.ApiResponseMessage;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.ProjectApiService;
import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.PipelineStep;
import de.cit.backend.api.model.Project;
import de.cit.backend.api.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class ProjectApiServiceImpl extends ProjectApiService {
      @Override
      public Response projectIdGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
    	  User user = new User();
    	  user.setEmail("cit@est.de");
    	  user.setID(1);
    	  user.setName("TestUser");
    	  user.setRegisteredSince(new Date());

    	  Project pro = new Project();
    	  pro.setCreatedAt(new Date());
    	  pro.setCreateUser(user);
    	  pro.setID(1);
    	  pro.setName("TestProject");
    	  
      return Response.ok().entity(pro).build();
  }
      @Override
      public Response projectIdPipelineDelete(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response projectIdPipelineGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
    	  User user = new User();
    	  user.setEmail("cit@est.de");
    	  user.setID(1);
    	  user.setName("TestUser");
    	  user.setRegisteredSince(new Date());

    	  Project pro = new Project();
    	  pro.setCreatedAt(new Date());
    	  pro.setCreateUser(user);
    	  pro.setID(1);
    	  pro.setName("TestProject");
    	  
    	  
    	  PipelineStep step4 = new PipelineStep();
    	  step4.setID(4);
    	  step4.setPipelineId(1);
    	  
    	  PipelineStep step3 = new PipelineStep();
    	  step3.setID(3);
    	  step3.setPipelineId(1);
    	  step3.setSuccessors(Arrays.asList(step4));
    	  
    	  PipelineStep step2 = new PipelineStep();
    	  step2.setID(2);
    	  step2.setPipelineId(1);
    	  step2.setSuccessors(Arrays.asList(step4));
    	  
    	  PipelineStep step1 = new PipelineStep();
    	  step1.setID(1);
    	  step1.setPipelineId(1);
    	  step1.setSuccessors(Arrays.asList(step2, step3));
    	  
    	  
    	  
    	  Pipeline pipe = new Pipeline();
    	  pipe.setID(1);
    	  pipe.setLastChanged(new Date());
    	  pipe.setName("TestPipeline");
    	  pipe.setProject(pro);
    	  pipe.setSript("Bitflow-script");
    	  pipe.setPipelineSteps(Arrays.asList(step1, step2, step3, step4));
    	  
      return Response.ok().entity(pipe).build();
  }
      @Override
      public Response projectIdPipelinePost(Pipeline body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response projectIdUsersGet(Integer id,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response projectProjectIdUsersUserIdDelete(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response projectProjectIdUsersUserIdPost(Integer projectId,Integer userId,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
}
