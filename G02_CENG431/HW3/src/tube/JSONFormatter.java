package tube;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.*;

public class JSONFormatter implements IFormatter<Map<Integer,Video>>{

    private final Gson gson;

    public JSONFormatter() {
        this.gson = new GsonBuilder()
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ")
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        if (field.getDeclaringClass() == Observable.class && field.getName().equals("changed")) {
                            return true;
                        }
                        else return field.getDeclaringClass() == Observable.class && field.getName().equals("obs");
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    public String toFormat(Video video){
        return gson.toJson(video);
    }

    public Video toVideoObject(String video){
        return gson.fromJson(video,Video.class);
    }

    public Map<Integer,Video> toObject(String videoMap){
        Type videoMapType = new TypeToken<Map<Integer,Video>>() {}.getType();
        return gson.fromJson(videoMap,videoMapType);
    }

    public String toFormat(Map<Integer,Video> videoMap){
        return gson.toJson(videoMap);
    }

    public static void main(String[] args) {
        Map<Integer,Video> videoMap = new HashMap<>();
        for (int i = 1; i < 51; i++) {
            Video video = new Video(i,"video "+ i,"video " + i + " content",new Date(),0,0,new ArrayList<>());
            videoMap.put(i,video);
        }

        JSONFormatter jsonFormatter = new JSONFormatter();
        IStorage storage = new FileStorage("videos.json");

        String videoMapJson = jsonFormatter.toFormat(videoMap);
        System.out.println(videoMapJson);
        storage.save(videoMapJson);

        Map<Integer,Video> videoMap2 = jsonFormatter.toObject(storage.read());
        String videoMapJson2 = jsonFormatter.toFormat(videoMap2);
        System.out.println(videoMapJson2);
    }
}
