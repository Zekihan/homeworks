package note_app;

import io.ConsoleDisplay;
import io.ConsoleInput;
import io.Display;
import io.Input;
import json.NoteSaver;
import json.INoteStorage;
import note.INote;
import note.NoteGroup;

public class NoteAppManager {
    private INote root;
    private final Display display;
    private final Input input;
    private final INoteStorage noteStorage;

    public NoteAppManager() {
        this.root = new NoteGroup(0, "Notes");
        this.display = new ConsoleDisplay();
        this.input = new ConsoleInput();
        this.noteStorage = new NoteSaver();
    }

    public void start(){

        while(true){

            //Display menu items
            display.displayMessage("\nNote App");
            display.displayTitle(1, "Notes");
            display.displayTitle(2, "Export Notes as Json");
            display.displayTitle(3, "Import Notes");
            display.displayTitle(4, "Reset Notes");
            display.displayTitle(5, "Exit");
            display.displayMessage("Please enter your command here: ");

            //Input actions
            switch (input.readString()){

                //Display notes
                case "1":   NoteExplorer explorer = new NoteExplorer(root, display, input);
                            explorer.start();
                            break;

                //Export notes
                case "2":
                            noteStorage.saveNotes(root);
                            break;

                //Import notes
                case "3":
                            if(noteStorage.checkSave()) {
                                root = noteStorage.loadNotes();
                                display.displayMessage("Successfully Imported");
                            }
                            else
                                display.displayMessage("No file to import.");
                            break;

                //Reset notes
                case "4":   root = new NoteGroup(1, "Notes");
                            break;

                //Exit program
                case "5":   input.close();
                            System.exit(0);
                            break;

                //Unrecognized input
                default:    display.displayMessage("Invalid Input");
                            break;
            }
        }
    }
}
