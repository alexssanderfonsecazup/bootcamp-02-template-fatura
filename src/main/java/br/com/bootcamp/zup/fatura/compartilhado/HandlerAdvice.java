package br.com.bootcamp.zup.fatura.compartilhado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErroFormulario handleErroDeValidacao(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> String.format("Campo %s %s", fieldError.getField(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())))
                .collect(Collectors.toList());

        return new ErroFormulario(errors);
    }


    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErroFormulario> handleApiErroException(ApiErrorException apiErroException) {

        ErroFormulario erro = new ErroFormulario(Arrays.asList(apiErroException.getMessage()));
        return ResponseEntity.status(apiErroException.getHttpStatus()).body(erro);
    }


}
