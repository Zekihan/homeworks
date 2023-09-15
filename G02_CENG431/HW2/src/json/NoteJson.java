package json;

import com.google.gson.*;
import note.INote;
import note.Note;
import note.NoteGroup;
import note.state.INoteState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class NoteJson implements IConverter {


    public String convertToFormat(INote note) {
        return INoteToJson(note);
    }

    public INote toNote(String note) {
        return INoteFromJson(note);
    }

    private String INoteToJson(INote root){
        NoteJson nj = new NoteJson();
        Gson gson = nj.noteToJson();
        if(root.getClass().getCanonicalName().equals(Note.class.getCanonicalName())){
            return gson.toJson(root);
        }
        else {
            return gson.toJson(INoteToJsonHelp(root,gson));
        }
    }

    private INote INoteFromJson(String rootJson){
        NoteJson nj = new NoteJson();
        Gson gson = nj.noteFromJson();
        JsonElement j = gson.fromJson(rootJson, JsonObject.class);
        String className = j.getAsJsonObject().get("class_name").getAsString();
        if(className.equals(Note.class.getCanonicalName())){
            return gson.fromJson(rootJson,Note.class);
        }
        else {
            return noteGroupFromJson().fromJson(rootJson,NoteGroup.class);
        }
    }

    private Gson noteFromJson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(INoteState.class, (JsonDeserializer<INoteState>) (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            try {
                Class<?> c = Class.forName(jsonObject.get("class_name").getAsString());
                Constructor<?> cons = c.getConstructor();
                return (INoteState) cons.newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        });
        builder.setPrettyPrinting();
        return builder.create();
    }

    private Gson noteGroupFromJson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NoteGroup.class, (JsonDeserializer<NoteGroup>) (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray jsonNotes = jsonObject.get("notes").getAsJsonArray();
            List<INote> notes = new LinkedList<>();
            for (JsonElement jsonNote:jsonNotes
            ) {
                String className = jsonNote.getAsJsonObject().get("class_name").getAsString();
                if(className.equals(Note.class.getCanonicalName())){
                    Gson gson = noteFromJson();
                    notes.add(gson.fromJson(jsonNote, Note.class));
                }
                if(className.equals(NoteGroup.class.getCanonicalName())){
                    Gson gson = noteGroupFromJson();
                    notes.add(gson.fromJson(jsonNote, NoteGroup.class));
                }
            }
            int id = jsonObject.get("id").getAsInt();
            String title = jsonObject.get("title").getAsString();
            return new NoteGroup(id,title,notes);
        });
        builder.setPrettyPrinting();
        return builder.create();
    }

    private JsonObject INoteToJsonHelp(INote root, Gson gson){
        List<INote> notes = ((NoteGroup)root).getNotes();
        JsonObject jsonNote = new JsonObject();
        jsonNote.addProperty("id",root.getId());
        jsonNote.addProperty("title",root.getTitle());
        jsonNote.addProperty("class_name",NoteGroup.class.getCanonicalName());
        JsonArray jsonNotes = new JsonArray();
        for (INote note:notes
        ) {
            if(note.getClass().getCanonicalName().equals(Note.class.getCanonicalName())){
                jsonNotes.add(gson.toJsonTree(note));
            }
            if(note.getClass().getCanonicalName().equals(NoteGroup.class.getCanonicalName())){
                jsonNotes.add(INoteToJsonHelp(note,gson));
            }
        }
        jsonNote.add("notes",jsonNotes);
        return jsonNote;
    }

    private Gson noteToJson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Note.class, (JsonSerializer<Note>) (src3, typeOfSrc3, context3) -> {
            GsonBuilder builder2 = new GsonBuilder();
            builder2.registerTypeAdapter(INoteState.class, (JsonSerializer<INoteState>) (src2, typeOfSrc2, context2) -> {
                JsonObject jsonNoteState = new JsonObject();

                jsonNoteState.addProperty("class_name", src2.getClass().getCanonicalName());

                return jsonNoteState;
            });
            Gson gson = builder2.create();
            JsonElement serialize = gson.toJsonTree(src3);
            JsonObject jsonNote = (JsonObject) serialize;
            String className = src3.getClass().getCanonicalName();
            jsonNote.addProperty("class_name", className);

            return jsonNote;
        });
        builder.setPrettyPrinting();
        return builder.create();
    }
}
