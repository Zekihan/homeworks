package json;

import note.INote;

public interface INoteStorage {


    /* Returns INote object from the previously saved location.
    * @return INote object */
    public INote loadNotes();

    /* Saves the given INote.
    * @param note:      INote to be saved
    * @return boolean   indicates the success or failure of the save operation. */
    public boolean saveNotes(INote note);


    public Boolean checkSave();

}
