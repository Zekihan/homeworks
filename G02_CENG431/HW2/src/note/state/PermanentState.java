package note.state;

import exceptions.IllegalStateChangeException;
import note.Note;

public class PermanentState implements INoteState{

    public void cancel(Note note) {
        note.setState(new CancelledState());
    }

    public void complete(Note note) throws IllegalStateChangeException {
        throw new IllegalStateChangeException("Permanent notes cannot be completed.");
    }

    @Override
    public String toString() {
        return "Permanent State";
    }
}
