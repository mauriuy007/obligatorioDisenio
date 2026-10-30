package uy.edu.ort.battleshipdemo.exception;

public class FleetException extends InvalidActionException {

    public FleetException(String message) {
        super(message);
    }

    public static FleetException invalidQuantity(String boatTypeName, int min, int max) {
        return new FleetException("La cantidad de " + boatTypeName + " debe estar entre " + min + " y " + max);
    }

    public static FleetException empty() {
        return new FleetException("La flota debe tener al menos un barco");
    }

    public static FleetException tooDense(int fleetCells, int oceanCells) {
        return new FleetException("La flota no entra en el océano: ocupa " + fleetCells + " de " + oceanCells
                + " celdas y debe ocupar menos del 25%");
    }

    public static FleetException unknownBoatType(String name) {
        return new FleetException("No existe el tipo de barco " + name);
    }
}
