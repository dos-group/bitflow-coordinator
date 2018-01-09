package de.cit.backend.api.factories;

import de.cit.backend.api.CapabilitiesApiService;
import de.cit.backend.api.impl.CapabilitiesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class CapabilitiesApiServiceFactory {

   private final static CapabilitiesApiService service = new CapabilitiesApiServiceImpl();

   public static CapabilitiesApiService getCapabilitiesApi()
   {
      return service;
   }
}
