package com.cosmo.cats.marketplace.api.web.error;

import com.cosmo.cats.marketplace.api.service.exception.ProductAlreadyExistsException;
import com.cosmo.cats.marketplace.api.service.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static com.cosmo.cats.marketplace.api.util.ErrorDetailUtils.getValidationErrorsProblemDetail;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product not found");
        return problemDetail;
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    ProblemDetail handleDuplicateProductNameException(ProductAlreadyExistsException ex) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create("this-name-exists"));
        problemDetail.setTitle("Duplicate name");
        return problemDetail;
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationDetails> validationResponse =
                errors.stream().map(err -> ParamsViolationDetails.builder().reason(err.getDefaultMessage()).fieldName(err.getField()).build()).toList();
        log.info("Input params validation failed");
        return ResponseEntity.status(BAD_REQUEST).body(getValidationErrorsProblemDetail(validationResponse));
    }
}