package battlegame.app.commands;

public interface ICommand {

    /* Fulfill the semantic behind the implementer command class. */
    void execute();

    /* Returns the name of the command class.
    * @return   Name of the command class.*/
    String toString();
}
