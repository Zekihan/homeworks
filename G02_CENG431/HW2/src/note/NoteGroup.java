package note;

import io.ConsoleDisplay;
import io.Display;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class NoteGroup extends AbstractNote{

    private List<INote> notes;

    public NoteGroup(int id, String title) {
        this(id, title, new LinkedList<INote>());
    }

    public NoteGroup(int id, String title, List<INote> notes) {
        super(id, title);
        setNotes(notes);
    }

    public NoteGroup(NoteGroup noteGroup){
        this(noteGroup.getId(), noteGroup.getTitle(), noteGroup.getNotes());
    }

    @Override
    public void displayContent() {
        if(!notes.isEmpty()){
            for(INote note : notes){
                note.displayTitle();
            }
        }else{
            Display display = new ConsoleDisplay();
            display.displayMessage("Empty Group");
        }
    }

    //Adds the given note to the notes list.
    public boolean add(INote note){
        if(note == null){
            throw new IllegalArgumentException("Given note object cannot be null");
        }
        return notes.add(note);
    }

    //Returns the INote object with the given id.
    public INote get(int id) throws NoSuchElementException{
        if(id < 0) {
            throw new IllegalArgumentException("Given id cannot be a negative value.");
        }

        for(INote note : notes){
            if(note.getId() == id){
                return note;
            }
        }
        throw new NoSuchElementException("Note with given id not found");
    }

    //Returns a deep copy of the notes list.
    public List<INote> getNotes() {
        List<INote> copyOfNotes = new LinkedList<INote>();
        copyOfNotes.addAll(notes);
        return copyOfNotes;
    }

    //Sets the notes list attribute as the copy of the given notes list.
    private void setNotes(List<INote> notesList){
        if(notesList == null){
            throw new IllegalArgumentException("Given notes list argument cannot be null.");
        }
        if(notesList.isEmpty()){
            this.notes = new LinkedList<INote>();

        }else{
            this.notes = new LinkedList<INote>();
            notes.addAll(notesList);
        }

    }
}
