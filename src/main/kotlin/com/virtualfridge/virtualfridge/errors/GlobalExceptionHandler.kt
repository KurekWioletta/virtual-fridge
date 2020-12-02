package com.virtualfridge.virtualfridge.errors

import com.virtualfridge.virtualfridge.models.ApiExceptionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ApiException::class])
    protected fun handleUnknownException(ex: ApiException, request: WebRequest?): ResponseEntity<ApiExceptionResponse> {
        return ResponseEntity.status(422).body(ApiExceptionResponse(422, "Exception message"))
    }

}