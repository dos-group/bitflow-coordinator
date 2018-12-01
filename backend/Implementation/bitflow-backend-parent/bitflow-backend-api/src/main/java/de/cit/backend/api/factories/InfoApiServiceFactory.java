package de.cit.backend.api.factories;

import de.cit.backend.api.InfoApiService;
import de.cit.backend.api.impl.InfoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class InfoApiServiceFactory {

   private final static InfoApiService service = new InfoApiServiceImpl();

   public static InfoApiService getInfoApi()
   {
      return service;
   }
}
