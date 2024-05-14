package com.gci.certificategenerator.errors.custom;

public class ApplicationException extends RuntimeException {
    public ApplicationException(final String errorMessage, final Throwable e) {
        super(errorMessage, e);
    }
}
