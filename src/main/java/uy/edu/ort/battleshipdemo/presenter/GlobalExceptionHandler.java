package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import uy.edu.ort.battleshipdemo.model.BattleshipException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final int errorCodeStatus = 299;

    @ExceptionHandler(BattleshipException.class)
    public ResponseEntity<String> handleException(BattleshipException ex) {
        return ResponseEntity.status(errorCodeStatus).body(ex.getMessage());
    }
}
