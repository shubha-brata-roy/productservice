package com.scaler.productservice.controlleradvices;

import com.scaler.productservice.dtos.MessageDto;
import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<MessageDto> handleNotFoundException(
            Exception exception) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(exception.getMessage());
        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.NOT_FOUND);
    }
}
