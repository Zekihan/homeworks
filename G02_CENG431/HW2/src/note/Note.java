package note;

import io.ConsoleDisplay;
import io.Display;
import note.state.INoteState;
import exceptions.IllegalStateChangeException;

import java.util.Date;

public class Note extends AbstractNote {

    private INoteState state;
    private String content;
    private Date date;

    public Note(int id, String title, INoteState state, String content, Date date) {
        super(id, title);
        setState(state);
        setContent(content);
        setDate(date);
    }

    public Note(Note note){
        this(note.getId(), note.getTitle(), note.getState(), note.getContent(), note.getDate());
    }

    public INoteState getState() {
        return state;
    }

    public void setState(INoteState state) {
        if(state == null){
            throw new IllegalArgumentException("State argument cannot be null");
        }
        this.state = state;
    }

    public void cancel() throws IllegalStateChangeException {
        state.cancel(this);
    }

    public void complete() throws IllegalStateChangeException {
        state.complete(this);
    }

    public void displayContent(){
        Display display = new ConsoleDisplay();
        display.displayNote(getTitle(), getContent(), getDate().toString(), getState().toString());
    }

    private String getContent() {
        return content;
    }

    private Date getDate() {
        return date;
    }

    //Check if the given content for a note is empty or null.
    private void setContent(String content){
        if(content == null || content == ""){
            throw new IllegalArgumentException("Content of a note cannot be empty or null");
        }
        this.content = content;
    }

    //Check if the given date for a note is null.
    private void setDate(Date date){
        if(date == null){
            throw new IllegalArgumentException("Date of a note cannot be null");
        }
        this.date = date;
    }
}
