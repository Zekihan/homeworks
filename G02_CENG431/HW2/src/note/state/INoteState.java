package note.state;

import exceptions.IllegalStateChangeException;
import note.Note;

public interface INoteState {

    /* Changes the note's state to 'Cancelled'.
    * @param note: Note object to change state of.
    * @throws IllegalStateChange: If the given note object is either in 'Completed' or 'Cancelled' state.
    */
    public void cancel(Note note) throws IllegalStateChangeException;

    /* Changes the note's state to 'Completed'.
     * @param note: Note object to change state of.
     * @throws IllegalStateChange: If the given note object is either in 'Completed', 'Cancelled' or 'Permanent' state.
     */
    public void complete(Note note) throws IllegalStateChangeException;

}
