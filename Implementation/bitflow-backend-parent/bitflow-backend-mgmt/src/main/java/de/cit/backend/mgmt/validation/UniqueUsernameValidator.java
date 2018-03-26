package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ValidationException;
import de.cit.backend.mgmt.services.interfaces.IUserService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Hendrik on 27.03.2018.
 */
public class UniqueUsernameValidator extends Validator {

    // lazy loading once to improve performance
    protected static IUserService userService;

    public UniqueUsernameValidator(Object obj, String messge) {
        super(obj, messge);
    }

    @Override
    public void validate() throws ValidationException {
        if(userService == null) {
            Context ctx;
            try {
                ctx = new InitialContext();
                userService = (IUserService) ctx.lookup("java:module/UserService");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        try {
            userService.loadUser((String) super.objectToValidate);
        } catch (final BitflowException e) {
            // valid cause exception means username is not used yet
            return;
        }
        throw new ValidationException(super.message);
    }

}
