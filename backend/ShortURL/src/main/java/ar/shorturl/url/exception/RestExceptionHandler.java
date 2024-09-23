package ar.shorturl.url.exception;

import ar.shorturl.url.exception.dto.ApiErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

	private final MessageSource messageSource;


	@ExceptionHandler({MethodArgumentNotValidException.class})
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Map<String, String> handleDataIntegrityExceptions(DataIntegrityViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("ERROR", ex.getCause().getCause().toString());
		LOG.error(ex.getMessage(), ex);
		return errors;
	}


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> invalidUsername(Exception ex, Locale locale) {
		String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
		LOG.info(ex.getMessage());
		LOG.debug(ex.getMessage(), ex);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}


	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorMessage> notFound(NotFoundException ex) {
		LOG.info(ex.getMessage());
		LOG.debug(ex.getMessage(), ex);
		return new ResponseEntity<>(new ApiErrorMessage(ex.messageId, ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(OperationNotValidException.class)
	public ResponseEntity<ApiErrorMessage> notValid(OperationNotValidException ex) {
		LOG.info(ex.getMessage());
		LOG.debug(ex.getMessage(), ex);
		return new ResponseEntity<>(new ApiErrorMessage(ex.messageId, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ApiErrorMessage handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		ApiErrorMessage apiErrorMessage = new ApiErrorMessage(
				"illegal-argument",
				ex.getMessage(),
				args -> args.put("exception", ex)
		);
		LOG.error(String.format("Trace: %s", ex.getMessage()), ex);
		return apiErrorMessage;
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({JDBCException.class})
	public ApiErrorMessage handleSQLGrammarException(JDBCException ex) {
		// JDBCException Wraps a {@link SQLException}.  Indicates that an exception occurred during a JDBC call.
		// Retornamos el mensaje de esa excepción que es bastante explicito
		// Estas excepciones no deberían suceder en producción!!!
		ApiErrorMessage apiErrorMessage = new ApiErrorMessage("db-error", ex.getSQLException().getMessage());
		LOG.error(String.format("Trace: %s", ex.getMessage()), ex);
		return apiErrorMessage;
	}

}