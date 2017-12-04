package de.cit.backend.api.factories;

import de.cit.backend.api.UserApiService;
import de.cit.backend.api.impl.UserApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class UserApiServiceFactory {

   private final static UserApiService service = new UserApiServiceImpl();

   public static UserApiService getUserApi()
   {
      return service;
   }
}
