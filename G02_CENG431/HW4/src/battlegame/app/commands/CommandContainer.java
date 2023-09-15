package battlegame.app.commands;

import java.util.Map;


/* CommandContainer.java
 * Created and used by menu command classes to delegate the work through.
 * Contains a map with key of the operation id and value of command. (1. Delete comment, etc.) */
public class CommandContainer {
    private final Map<Integer, ICommand> commandMap;

    public CommandContainer(Map<Integer, ICommand> commands) {
        commandMap = commands;
    }

    public Map<Integer, ICommand> getCommands() {
        return commandMap;
    }

    /* Add a new command to the menu. */
    public void register(Integer opId, ICommand command) {
        commandMap.put(opId, command);
    }

    /* Call the command object with the given operationId(key)'s execute() method .*/
    public void execute(Integer operationId) {
        if (!commandMap.containsKey(operationId)) {
            throw new UnsupportedOperationException("Given operation id is not supported.");
        }
        ICommand command = commandMap.get(operationId);
        command.execute();
    }
}
