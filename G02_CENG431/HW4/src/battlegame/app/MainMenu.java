package battlegame.app;

import battlegame.IGameEngine;
import battlegame.app.commands.*;
import io.IDisplay;
import io.Input;

import java.util.HashMap;

 /* MainMenu.java
  * Main menu class of the game.
  * It's a menu command class since it does not violate the Liskov Substituion Principle, it safely extends the AbstractMenuCommand class. */
public class MainMenu extends AbstractMenuCommand {

    public MainMenu(IGameEngine gameEngine, Input inputHandler, IDisplay displayHandler) {
        super(gameEngine, displayHandler, inputHandler);
        initiateMenu();
    }

    @Override
    protected void initiateCommands() {

        //Available battlegame.app.commands' container
        setCommandContainer(new CommandContainer(new HashMap<Integer, ICommand>()));

        //Initiate battlegame.app.commands
        ICommand resetGameCommand = new ResetCommand(getGameEngine());
        ICommand runSimulationCommand = new RunSimulationCommand(getGameEngine(), getDisplayHandler());
        ICommand playerOperationsCommand = new PlayerOperationsCommand(getGameEngine(), getInputHandler(), getDisplayHandler());
        ICommand exitGameCommand = new ExitGameCommand(getGameEngine(), getDisplayHandler());

        //Add created battlegame.app.commands
        getCommandContainer().register(1, playerOperationsCommand);
        getCommandContainer().register(2, runSimulationCommand);
        getCommandContainer().register(3, resetGameCommand);
        getCommandContainer().register(4, exitGameCommand);
    }


    @Override
    public String toString() {
        return "Main Menu";
    }


}
