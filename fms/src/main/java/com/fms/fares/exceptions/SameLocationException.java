package com.fms.fares.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The Departure and the Arrival should be distinct!")
public class SameLocationException extends RuntimeException {}
