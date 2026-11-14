package uy.edu.ort.battleshipdemo.model;

public class Machine extends Player {

    private Tactic tactic;

    public Machine(Fleet fleet, int width, int height, Tactic tactic) {
        super(fleet, width, height);
        this.tactic = tactic;
    }

    public void fireAt(Player opponent) throws BattleshipException {
        Cell target = tactic.chooseTarget(opponent.getOcean());
        opponent.receiveShot(target.getRow(), target.getColumn());
    }
}
