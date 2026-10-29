package com.example.battleship.exception;

public class BattleException extends InvalidActionException {

    public BattleException(String message) {
        super(message);
    }

    public static BattleException duplicatePlace() {
        return new BattleException("No se puede librar más de una batalla en el mismo lugar, cambie de nombre");
    }

    public static BattleException notFound(String place) {
        return new BattleException("No existe una batalla en " + place);
    }

    public static BattleException fleetNotDeployed() {
        return new BattleException("Debe posicionar todos sus barcos primero");
    }

    public static BattleException noBoatsLeftToPlace() {
        return new BattleException("No quedan barcos por posicionar");
    }

    public static BattleException cannotPlaceInEnemyWaters() {
        return new BattleException("No puede ubicar barcos en aguas enemigas");
    }

    public static BattleException cannotFireAtOwnWaters() {
        return new BattleException("No puede disparar a sus propias aguas");
    }

    public static BattleException alreadyFinished() {
        return new BattleException("La batalla ya terminó");
    }
}
