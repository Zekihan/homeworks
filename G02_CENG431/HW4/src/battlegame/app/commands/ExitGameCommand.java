package battlegame.app.commands;

import battlegame.IGameEngine;
import io.IDisplay;

public class ExitGameCommand extends AbstractCommand {

    private final IDisplay displayHandler;

    public ExitGameCommand(IGameEngine gameEngine, IDisplay displayHandler) {
        super(gameEngine);
        this.displayHandler = displayHandler;
    }

    @Override
    public void execute() {
        displayHandler.displayWarning("Quitting game....");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "Quit Game";
    }
}
