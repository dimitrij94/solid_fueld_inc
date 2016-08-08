package com.example.handlers;

import com.example.dto.ExceptionMessageDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@ControllerAdvice
public class ValidationHandlerAdvice {

    private MessageSource messageSource;
    private Locale currentLocal = LocaleContextHolder.getLocale();

    @Autowired
    public ValidationHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageDTO notFound(NotFoundException ex) {
        return new ExceptionMessageDTO(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDTO[] processValidationError(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        ExceptionMessageDTO[] dtos = new ExceptionMessageDTO[errors.size()];
        int i = 0;
        for (FieldError error : errors) {
            dtos[i] = processFieldErrors(error);
            i++;
        }
        return dtos;
    }

    private ExceptionMessageDTO processFieldErrors(FieldError error) {
        ExceptionMessageDTO messageDTO = null;
        if (error != null) {
            String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocal);
            messageDTO = new ExceptionMessageDTO(msg);
        }
        return messageDTO;
    }
}
