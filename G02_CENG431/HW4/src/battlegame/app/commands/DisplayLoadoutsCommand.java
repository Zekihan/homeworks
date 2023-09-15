package battlegame.app.commands;

import battlegame.IGameEngine;
import battlegame.warcrafts.Warcraft;
import io.IDisplay;

import java.util.List;

public class DisplayLoadoutsCommand extends AbstractCommand {

    private final IDisplay displayHandler;

    public DisplayLoadoutsCommand(IGameEngine gameEngine, IDisplay displayHandler) {
        super(gameEngine);
        this.displayHandler = displayHandler;
    }

    @Override
    public void execute() {
        /* Get both of the players' loadouts */
        List<Warcraft> player1Loadouts = getGameEngine().getPlayerLoadout(1);
        List<Warcraft> player2Loadouts = getGameEngine().getPlayerLoadout(2);

        /* Put each item into string */
        StringBuilder player1Loadout = new StringBuilder();
        int itemIndex = 1;

        for (Warcraft w : player1Loadouts) {
            player1Loadout.append(itemIndex + ". " + w.toString() + "\n");
            itemIndex++;
        }


        StringBuilder player2Loadout = new StringBuilder();
        itemIndex = 1;

        for (Warcraft w : player2Loadouts) {
            player2Loadout.append(itemIndex + ". " + w.toString() + "\n");
            itemIndex++;
        }

        displayHandler.displayLoadouts(player1Loadout.toString(), player2Loadout.toString());
    }


    public String toString() {
        return "Display Loadouts";
    }
}
