package com.example.spockgroovy.controller

import com.example.spockgroovy.exception.VisitorNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class VisitorControllerErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleVisitorNotFoundException(VisitorNotFoundException ex, Model model) {
        model.addAttribute('error', ex.message)
        return 'error'
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleGeneralException(Exception ex, Model model) {
        model.addAttribute('error', 'Wystąpił nieznany błąd')
        return 'error'
    }
}
