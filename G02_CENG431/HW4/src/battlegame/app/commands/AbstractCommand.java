package battlegame.app.commands;

import battlegame.IGameEngine;

/* Top abstract class in the command hierarchy.
 * All command classes should extend this class, and all subclasses should implement toString() and execute() methods coming from the ICommand interface.
 */
public abstract class AbstractCommand implements ICommand {

    private IGameEngine gameEngine;

    protected AbstractCommand(IGameEngine gameEngine) {
        setGameEngine(gameEngine);
    }

    /* All subclasses will use game engine through AbstractCommand. */
    protected IGameEngine getGameEngine() {
        return gameEngine;
    }


    public abstract String toString();

    private void setGameEngine(IGameEngine gameEngine){
        if(gameEngine == null){
            throw new IllegalArgumentException("Given game engine object cannot be null");
        }
        this.gameEngine = gameEngine;
    }

}
