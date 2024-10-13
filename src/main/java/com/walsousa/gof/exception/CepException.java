package com.walsousa.gof.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CepException extends Exception {

    private String errorCode;
    private String message;
    private String details;

    public CepException(String errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public CepException(Throwable e) {
        super(e);
    }
}
