package com.fms.fares.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="At least one input is missing!")
public class MissingDataException extends RuntimeException {}
