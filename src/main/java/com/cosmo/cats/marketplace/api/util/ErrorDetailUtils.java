package com.cosmo.cats.marketplace.api.util;

import com.cosmo.cats.marketplace.api.web.error.ParamsViolationDetails;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@UtilityClass
public class ErrorDetailUtils {

    public static ProblemDetail getValidationErrorsProblemDetail(List<ParamsViolationDetails> validationResponse) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Request validation failed");
        problemDetail.setType(URI.create("urn:problem-type:validation-error"));
        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setProperty("invalidParams", validationResponse);
        return problemDetail;
    }



}
