package com.ef.Parser.exceptions;

/**
 * Custom runtime exception for arguments validation.
 */
public class ArgumentException extends RuntimeException {
    public ArgumentException(final String message) {
        super(message);
    }
}
