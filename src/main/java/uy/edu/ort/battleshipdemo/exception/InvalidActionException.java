package uy.edu.ort.battleshipdemo.exception;

/**
 * Base of every business rule violation. Presenters catch this type and
 * show its message to the user; the action is ignored.
 */
public class InvalidActionException extends Exception {
    public InvalidActionException(String message) {
        super(message);
    }
}
