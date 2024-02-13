package Pegas.exception;

import Pegas.validator.Error;

import java.util.List;

public class ValidationException extends Throwable {
    private final List<Error> errors;

    public List<Error> getErrors() {
        return errors;
    }

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
