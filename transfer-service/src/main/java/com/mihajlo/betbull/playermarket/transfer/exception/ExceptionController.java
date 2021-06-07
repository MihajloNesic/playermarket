package com.mihajlo.betbull.playermarket.transfer.exception;

import com.mihajlo.betbull.playermarket.transfer.model.response.ApiErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorDetails> serverException(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        LOGGER.error("Internal server error: (" + ex.getClass().getSimpleName() + ") " + ex.getMessage() + "\n trace: " + sw);

        ApiErrorDetails errorDetails = new ApiErrorDetails.Builder(ex)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage("Something went wrong, please try again.")
                .withMessageDetails(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorDetails> handle(BaseException ex, HttpServletRequest request) throws IllegalAccessException {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getName();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        LOGGER.error("BaseException: " + errorMessage + "\n trace: " + sw);

        ApiErrorDetails error = new ApiErrorDetails.Builder(ex)
                .withStatus(ex.getErrorCode().getCode())
                .withMessage(errorMessage)
                .withMessageDetails(ex.getCause() != null ? ex.getCause().getMessage() : null)
                .withPath(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, ex.getStatusCode());
    }


}
