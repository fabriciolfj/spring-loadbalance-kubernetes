package com.github.fabriciolfj.departamentoservice.api.exceptions;

public class DepartamentoNotFoundException extends RuntimeException {

    public DepartamentoNotFoundException(final String msg) {
        super(msg);
    }
}
