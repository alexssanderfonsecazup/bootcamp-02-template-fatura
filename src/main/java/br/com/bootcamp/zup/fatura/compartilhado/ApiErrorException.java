package br.com.bootcamp.zup.fatura.compartilhado;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {

    private final HttpStatus  httpStatus;
    private final String reason;

    public ApiErrorException(HttpStatus status, String reason){
        super(reason);
        this.httpStatus = status;
        this.reason = reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
