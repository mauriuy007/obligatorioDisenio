package uy.edu.ort.battleshipdemo.exception;

public class TacticException extends InvalidActionException {

    public TacticException(String message) {
        super(message);
    }

    public static TacticException notFound(String name) {
        return new TacticException("No existe la táctica " + name);
    }
}
