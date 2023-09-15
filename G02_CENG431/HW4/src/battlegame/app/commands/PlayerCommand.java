package battlegame.app.commands;

import battlegame.IGameEngine;
import io.IDisplay;
import io.Input;

import java.util.HashMap;


/* PlayerCommand.java
 * It's a menu command for player specific operations. (Add item to loadout, etc.)
 */
public class PlayerCommand extends AbstractMenuCommand {

    private int playerNo;

    public PlayerCommand(IGameEngine gameEngine, int playerNo, Input inputHandler, IDisplay displayHandler) {
        super(gameEngine, displayHandler, inputHandler);
        setPlayerNo(playerNo);
        initiateMenu();
    }

    public String toString() {
        return "Player" + playerNo;
    }

    protected void initiateCommands() {
        setCommandContainer(new CommandContainer(new HashMap<>()));

        ICommand addItemCommand = new AddItemCommand(getGameEngine(), playerNo, getDisplayHandler(), getInputHandler());
        ICommand addPartCommand = new AddPartCommand(getGameEngine(), getDisplayHandler(), getInputHandler(), playerNo);

        getCommandContainer().register(1, addItemCommand);
        getCommandContainer().register(2, addPartCommand);
    }

    private void setPlayerNo(int playerNo) {
        if (playerNo != 1 && playerNo != 2) {
            throw new IllegalArgumentException("Illegal player number (should be either 1 or 2)");
        }
        this.playerNo = playerNo;
    }
}
