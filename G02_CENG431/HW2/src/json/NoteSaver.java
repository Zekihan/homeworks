package json;

import note.INote;

public class NoteSaver implements INoteStorage {

    private IConverter noteJson;
    private FileIO fio;
    private String filePath;

    public NoteSaver() {
        this.filePath = "notes.json";
        this.noteJson = new NoteJson();
        this.fio = new FileIO(filePath);
    }

    public INote loadNotes() {
        String notes = fio.load();
        return noteJson.toNote(notes);
    }

    public boolean saveNotes(INote note) {
        String save = noteJson.convertToFormat(note);
        return fio.save(save);
    }

    public Boolean checkSave(){
        return fio.checkSave();
    }
}
