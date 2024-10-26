package org.service.message;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;

import javax.validation.ValidationException;

public class ValidationErrorHandler {

    @Produces
    public HttpResponse<String> handleValidationException(ValidationException e) {
        return HttpResponse.badRequest("Validation failed: " + e.getMessage());
    }
}