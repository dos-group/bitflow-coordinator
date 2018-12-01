package de.cit.backend.api.factories;

import de.cit.backend.api.UsersApiService;
import de.cit.backend.api.impl.UsersApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class UsersApiServiceFactory {

   private final static UsersApiService service = new UsersApiServiceImpl();

   public static UsersApiService getUsersApi()
   {
      return service;
   }
}
