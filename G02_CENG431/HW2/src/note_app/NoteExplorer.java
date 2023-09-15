package note_app;

import io.Display;
import io.Input;
import note.INote;
import note.Note;
import note.NoteGroup;
import note.state.INoteState;
import exceptions.IllegalStateChangeException;
import note.state.IncompleteState;
import note.state.PermanentState;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class NoteExplorer {

    private final Stack<NoteGroup> noteGroupStack;
    private NoteGroup currentGroup;
    private final Display display;
    private final Input input;

    public NoteExplorer(INote currentGroup, Display display, Input input) {
        this.noteGroupStack = new Stack<>();
        this.currentGroup = (NoteGroup) currentGroup;
        this.display = display;
        this.input = input;
    }

    public void start(){

        boolean isBrowsing = true;

        while(isBrowsing){
            currentGroup.displayContent();
            display.displayMessage("C:CreateNote G:CreateNoteGroup B:Back Q:Quit");
            display.displayMessage("Please enter your command here: ");
            String inputStr = input.readString().toUpperCase();

            if(isNumeric(inputStr)){
                INote nextNote;
                int i = Integer.parseInt(inputStr);
                try{
                    nextNote = currentGroup.get(i);
                }catch (NoSuchElementException ex){
                    display.displayMessage("Note or Note Group with given id does not exist!\n");
                    continue;
                }

                if (isNote(nextNote)){
                    nextNote.displayContent();
                    display.displayMessage("C:ChangeState B:Back");
                    inputStr = input.readString().toUpperCase();

                    if(inputStr.equals("C")){
                        display.displayMessage("1:Complete 2:Cancel");
                        inputStr = input.readString();
                        if(isNumeric(inputStr)){
                            i = Integer.parseInt(inputStr);
                            changeNoteState((Note) nextNote, i);
                        }else{
                            display.displayMessage("Invalid input");
                        }
                    }else if(!inputStr.equals("B")){
                        display.displayMessage("Invalid input");
                    }
                }else{
                    noteGroupStack.add(currentGroup);
                    currentGroup = (NoteGroup) nextNote;
                }

            }else{
                switch (inputStr){
                    case "C":   createNote(currentGroup);
                        break;
                    case "G":   createNoteGroup(currentGroup);
                        break;
                    case "B":   if(!noteGroupStack.empty()){
                        currentGroup = noteGroupStack.pop();
                    }else{
                        display.displayMessage("You reached root note group cannot go back!\n");
                    }
                        break;
                    case "Q":   isBrowsing = false;
                        break;
                    default:    display.displayMessage("Invalid input");
                        break;
                }
            }
        }
    }

    private void createNote(NoteGroup group) {
        display.displayMessage("Enter Title: ");
        String title = input.readString();
        display.displayMessage("Enter Content: ");
        String content = input.readString();
        display.displayMessage("Enter State(P or I): ");
        String stateStr = input.readString().toUpperCase();
        INoteState state;
        if (stateStr.equals("P")){
            state = new PermanentState();
        }else if(stateStr.equals("I")){
            state = new IncompleteState();
        }else{
            display.displayMessage("Invalid input");
            return;
        }
        int id = generateId(group);
        Note newNote = new Note(id, title, state, content, new Date());
        group.add(newNote);
    }

    private void createNoteGroup(NoteGroup group) {
        display.displayMessage("Enter Title: ");
        String title = input.readString();
        int id = generateId(group);
        NoteGroup newNoteGroup = new NoteGroup(id, title);
        group.add(newNoteGroup);
    }

    //Change note's state
    private void changeNoteState(Note note, int op){
        try {
            if(op == 1){
                note.complete();
            }else if(op == 2){
                note.cancel();
            }else{
                throw new IllegalArgumentException("Illegal State Operation");
            }
            display.displayMessage("Successfully changed the state");

        } catch (IllegalStateChangeException | IllegalArgumentException ex) {

            display.displayMessage("Failed state change: " + ex.getMessage() + "\n");
        }
    }

    //Generates an id that is not already picked by an INote
    private int generateId(NoteGroup noteGroup){
        List<INote> notes = noteGroup.getNotes();
        int max = 0;
        for (INote note: notes) {
            if(note.getId() > max){
                max = note.getId();
            }
        }
        return max + 1;
    }

    //Returns true if the given argument is of type Note.
    private boolean isNote(INote note){
        return Note.class == note.getClass();
    }

    //Returns true if the given String contains numeric values.
    private boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
