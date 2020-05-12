package com.ft.template.exceptions;

public class BadRequestException extends RuntimeException{
    BadRequestException(String message) {
        super(message);
    }
}
