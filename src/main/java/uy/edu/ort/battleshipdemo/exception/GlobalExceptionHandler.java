package uy.edu.ort.battleshipdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Manejador global de excepciones de aplicación para todos los presenters.
 * Responde con status 299, que CommandDispatcher muestra como excepción de
 * aplicación (onException). Los presenters no capturan excepciones: las
 * propagan y se resuelven acá.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int APPLICATION_EXCEPTION = 299;

    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<String> handle(InvalidActionException e) {
        return ResponseEntity.status(APPLICATION_EXCEPTION).body(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handle(BindException e) {
        return ResponseEntity.status(APPLICATION_EXCEPTION)
                .body("Revise los datos ingresados: hay valores que no tienen el formato esperado");
    }
}
