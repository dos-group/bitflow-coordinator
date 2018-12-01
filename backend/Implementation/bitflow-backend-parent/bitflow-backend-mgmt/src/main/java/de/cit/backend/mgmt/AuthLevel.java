package de.cit.backend.mgmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AuthLevel {

    UserRoleEnum value();

}
