package Util;

import exception.ServiceCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtils {

    private final Validator validator;

    @Autowired
    ValidationUtils(Validator validator) {
        this.validator = validator;
    }

    public void validate(Object o) throws ServiceCreateException {
        var violations = this.validator.validate(o);
        this.throwExceptionIfExistsViolations(violations);
    }
    private void throwExceptionIfExistsViolations(Set<ConstraintViolation<Object>> violations) {
        if (!violations.isEmpty()) {
            throw new ServiceCreateException(HttpStatus.BAD_REQUEST.value(), ServiceConstants.SA004,
                    ServiceConstants.SA004M);
        }
    }

}
