package note.state;

import exceptions.IllegalStateChangeException;
import note.Note;

public class CompletedState implements INoteState{

    public void cancel(Note note) throws IllegalStateChangeException {
        throw new IllegalStateChangeException("Completed notes cannot be cancelled.");
    }


    public void complete(Note note) throws IllegalStateChangeException {
        throw new IllegalStateChangeException("This note has already been completed.");
    }

    @Override
    public String toString() {
        return "Completed State";
    }
}
