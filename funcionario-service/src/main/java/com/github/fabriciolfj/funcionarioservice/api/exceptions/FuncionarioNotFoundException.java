package com.github.fabriciolfj.funcionarioservice.api.exceptions;

public class FuncionarioNotFoundException extends RuntimeException {

    public FuncionarioNotFoundException(final String msg) {
        super(msg);
    }
}
