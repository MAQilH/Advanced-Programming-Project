package ir.sharif.model.game.abilities;

import ir.sharif.model.game.Ability;

public class Transformers implements Ability {
    boolean converted = false;
    @Override
    public void execute() {
        converted = true;
    }

    public boolean isConverted() {
        return converted;
    }
}
