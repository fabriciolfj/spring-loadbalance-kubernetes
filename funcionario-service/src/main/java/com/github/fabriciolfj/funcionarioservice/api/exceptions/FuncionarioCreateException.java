package com.github.fabriciolfj.funcionarioservice.api.exceptions;

public class FuncionarioCreateException extends RuntimeException {

    public FuncionarioCreateException(final String msg) {
        super(msg);
    }
}
