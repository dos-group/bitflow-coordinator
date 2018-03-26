package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ValidationException;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Hendrik on 27.03.2018.
 */
public class UniquePipelineNameValidator extends Validator {

    // lazy loading once to improve performance
    protected static IPipelineService pipelineService;

    public UniquePipelineNameValidator(Object obj, String messge) {
        super(obj, messge);
    }

    @Override
    public void validate() throws ValidationException {
        if(pipelineService == null) {
            Context ctx;
            try {
                ctx = new InitialContext();
                pipelineService = (IPipelineService) ctx.lookup("java:module/PipelineService");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        try {
            pipelineService.loadPipelineByName((String) super.objectToValidate);
        } catch (final BitflowException e) {
            // valid cause exception means pipeline name is not used yet
            return;
        }
        throw new ValidationException(super.message);
    }


}
