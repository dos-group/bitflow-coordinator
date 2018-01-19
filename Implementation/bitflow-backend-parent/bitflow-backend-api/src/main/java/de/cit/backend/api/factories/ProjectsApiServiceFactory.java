package de.cit.backend.api.factories;

import de.cit.backend.api.ProjectsApiService;
import de.cit.backend.api.impl.ProjectsApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class ProjectsApiServiceFactory {

   private final static ProjectsApiService service = new ProjectsApiServiceImpl();

   public static ProjectsApiService getProjectsApi()
   {
      return service;
   }
}
