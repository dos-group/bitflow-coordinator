package de.cit.backend.api.factories;

import de.cit.backend.api.LoginApiService;
import de.cit.backend.api.impl.LoginApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class LoginApiServiceFactory {

   private final static LoginApiService service = new LoginApiServiceImpl();

   public static LoginApiService getLoginApi()
   {
      return service;
   }
}
