package wtf.juridicum.buddy.endpoint.exceptionhandler;

import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.validation.ValidationException;

import org.h2.jdbc.JdbcSQLDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wtf.juridicum.buddy.exception.NotFoundException;

/**
 * Register all your Java exceptions here to map them into meaningful HTTP exceptions If you have
 * special cases which are only important for specific endpoints, use ResponseStatusExceptions
 * https://www.baeldung.com/exception-handling-for-rest-with-spring#responsestatusexception
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Use the @ExceptionHandler annotation to write handler for custom exceptions.
     */
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,
                request);
    }

    /**
     * Handle Validation exceptions.
     */
    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleInvalid(RuntimeException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request
        );
    }

    /**
     * Handle database exceptions.
     */
    @ExceptionHandler(value = {JdbcSQLDataException.class})
    protected ResponseEntity<Object> handleDatabase(RuntimeException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        return handleExceptionInternal(ex, "Database could not perform operation", new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * Handle mail exceptions
     */
    @ExceptionHandler(value = {MessagingException.class})
    protected ResponseEntity<Object> handleMail(MessagingException ex, WebRequest request) {
        LOGGER.error(ex.toString());
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    /**
     * Override methods from ResponseEntityExceptionHandler to send a customized HTTP response for a
     * know exception from e.g. Spring.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseEntity<Object> err = new ResponseEntity(String.join(" and ", errors), headers,
                HttpStatus.UNPROCESSABLE_ENTITY);
        return err;
    }
}
