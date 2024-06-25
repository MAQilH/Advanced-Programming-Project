package ir.sharif.model.game;

public interface Ability {
    void execute();
    void execute(int player, MatchTable matchTable);
}
