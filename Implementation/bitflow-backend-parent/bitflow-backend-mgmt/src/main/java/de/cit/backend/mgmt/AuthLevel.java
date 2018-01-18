package de.cit.backend.mgmt;

import de.cit.backend.mgmt.persistence.model.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AuthLevel {

    UserRoleEnum value();

}
