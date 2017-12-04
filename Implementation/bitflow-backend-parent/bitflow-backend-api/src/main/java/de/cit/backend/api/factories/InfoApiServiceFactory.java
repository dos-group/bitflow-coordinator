package de.cit.backend.api.factories;

import de.cit.backend.api.InfoApiService;
import de.cit.backend.api.impl.InfoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class InfoApiServiceFactory {

   private final static InfoApiService service = new InfoApiServiceImpl();

   public static InfoApiService getInfoApi()
   {
      return service;
   }
}
