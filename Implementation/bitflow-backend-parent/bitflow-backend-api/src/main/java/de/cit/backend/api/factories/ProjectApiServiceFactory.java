package de.cit.backend.api.factories;

import de.cit.backend.api.ProjectApiService;
import de.cit.backend.api.impl.ProjectApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class ProjectApiServiceFactory {

   private final static ProjectApiService service = new ProjectApiServiceImpl();

   public static ProjectApiService getProjectApi()
   {
      return service;
   }
}
