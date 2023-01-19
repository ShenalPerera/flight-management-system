package com.fms.fares.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Duplicate Entry!")
public class DuplicateEntryException extends RuntimeException {}
