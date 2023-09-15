package note;

import io.ConsoleDisplay;
import io.Display;

public abstract class AbstractNote implements INote {

    private int id;
    private String title;

    public AbstractNote(int id, String title) {
        setId(id);
        setTitle(title);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void displayTitle() {
        Display display = new ConsoleDisplay();
        display.displayTitle(getId(), getTitle());
    }

    private void setId(int id){
        if(id < 0){
            throw new IllegalArgumentException("Given id cannot be a negative value.");
        }
        this.id = id;
    }

    private void setTitle(String title){
        if(title == null || title == ""){
            throw new IllegalArgumentException("Title cannot be empty or null.");
        }
        this.title = title;
    }
}
