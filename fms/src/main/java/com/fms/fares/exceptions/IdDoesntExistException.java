package com.fms.fares.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The ID doesn't exists in the system!")
public class IdDoesntExistException extends RuntimeException {}
