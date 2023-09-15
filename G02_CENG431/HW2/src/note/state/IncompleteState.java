package note.state;

import note.Note;

public class IncompleteState implements INoteState{

    public void cancel(Note note) {
        note.setState(new CancelledState());
    }


    public void complete(Note note) {
        note.setState(new CompletedState());
    }

    @Override
    public String toString() {
        return "Incomplete State";
    }
}
