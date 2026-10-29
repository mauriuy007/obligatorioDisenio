package com.example.battleship.exception;

public class OceanException extends InvalidActionException {

    public OceanException(String message) {
        super(message);
    }

    public static OceanException invalidWidth(int min, int max) {
        return new OceanException("La cantidad de celdas horizontales debe estar entre " + min + " y " + max);
    }

    public static OceanException invalidHeight(int min, int max) {
        return new OceanException("La cantidad de celdas verticales debe estar entre " + min + " y " + max);
    }

    public static OceanException outOfBounds() {
        return new OceanException("El barco queda fuera de los límites del océano");
    }

    public static OceanException overlapping() {
        return new OceanException("El barco se superpone con otro barco ya ubicado");
    }

    public static OceanException touching() {
        return new OceanException("El barco no puede tocar a otro barco, debe haber agua entre ellos");
    }

    public static OceanException alreadyShot() {
        return new OceanException("Ya se disparó a esa celda");
    }
}
