package note.state;

import exceptions.IllegalStateChangeException;
import note.Note;

public class CancelledState implements INoteState{

    public void cancel(Note note) throws IllegalStateChangeException {
        throw new IllegalStateChangeException("This note has already been cancelled.");
    }

    public void complete(Note note) throws IllegalStateChangeException {
        throw new IllegalStateChangeException("Completed notes cannot be cancelled.");
    }

    @Override
    public String toString() {
        return "Cancelled State";
    }
}
