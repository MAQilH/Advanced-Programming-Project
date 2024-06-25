package ir.sharif.model.game;

import ir.sharif.model.CommandResult;

public interface LeadersAbility {

    CommandResult execute(int player, MatchTable matchTable);

}
