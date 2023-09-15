package battlegame.app.commands;

import battlegame.IGameEngine;
import io.IDisplay;
import io.Input;

import java.util.HashMap;

/* PlayerOperationsCommand.java
 * It's a menu command and since it doesn't violate Liskov Substitution Principle, it safely extends the AbstractMenuCommand.
 * Menu that allows user to select which player to alter. */
public class PlayerOperationsCommand extends AbstractMenuCommand {

    public PlayerOperationsCommand(IGameEngine gameEngine, Input inputHandler, IDisplay displayHandler) {
        super(gameEngine, displayHandler, inputHandler);
        initiateMenu();
    }


    public String toString() {
        return "Player Operations";
    }


    protected void initiateCommands() {
        setCommandContainer(new CommandContainer(new HashMap<>()));

        ICommand player1Command = new PlayerCommand(getGameEngine(), 1, getInputHandler(), getDisplayHandler());
        ICommand player2Command = new PlayerCommand(getGameEngine(), 2, getInputHandler(), getDisplayHandler());
        ICommand displayLoadoutsCommand = new DisplayLoadoutsCommand(getGameEngine(), getDisplayHandler());

        getCommandContainer().register(1, player1Command);
        getCommandContainer().register(2, player2Command);
        getCommandContainer().register(3, displayLoadoutsCommand);
    }

}
