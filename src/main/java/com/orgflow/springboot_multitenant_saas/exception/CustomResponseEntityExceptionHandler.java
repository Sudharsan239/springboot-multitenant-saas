package com.orgflow.springboot_multitenant_saas.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{

	@ExceptionHandler(Exception.class)
	public final @Nullable ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
	{
		String message = buildValidationMessage(ex);
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), message, request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	private String buildValidationMessage(MethodArgumentNotValidException ex)
	{
		List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.collect(Collectors.toList());
		List<String> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.toList());

		fieldErrors.addAll(globalErrors);
		if (fieldErrors.isEmpty())
		{
			return "Validation failed";
		}
		return String.join("; ", fieldErrors);
	}
}
