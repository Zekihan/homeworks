package tube;

import java.util.*;

public class DataHandler  implements IDataHandler, Observer {
    Map<String,User> userMap;
    Map<Integer,Video> videoMap;
    IStorage videoStorage, userStorage;
    IFormatter<Map<Integer,Video>> videoFormatter;
    XMLFormatter userFormatter;

    public DataHandler() {
        //load from xml and json
        this.videoStorage = new FileStorage("videos.json");
        this.userStorage = new FileStorage("users.xml");
        this.videoFormatter = new JSONFormatter();
        this.userFormatter = new XMLFormatter();

        //Read videos from the JSON file
        this.videoMap = videoFormatter.toObject(videoStorage.read());

        //Read users from the XML file
        this.userMap = userFormatter.toObject(userStorage.read());
    }

    public void observeUsers(){
        for(User u: userMap.values()){
            u.addObserver(this);
        }
    }

    public List<String> getUsernames() {
        return new ArrayList<>(userMap.keySet());
    }

    public User getUser(String username){
        return userMap.get(username);
    }

    public Video getVideo(int id){
        return videoMap.get(id);
    }

    public void putUser(User user){
        userMap.put(user.getUsername(), user);
        String userAsXML = userFormatter.toFormat(userMap);
        userStorage.save(userAsXML);
    }

    public void putVideo(Video video){
        videoMap.put(video.getId(), video);
        String videoMapJson = videoFormatter.toFormat(videoMap);
        videoStorage.save(videoMapJson);
    }

    /* If any information related to observers change, update the database(XML and JSON files) */
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Video){
            Video video = (Video) o;
            putVideo(video);
        }else{
            User user = (User) o;
            putUser(user);
        }
    }

    public static void main(String[] args) {
        DataHandler d = new DataHandler();
        d.putUser(new User("kaanalgan", "123456", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        d.putUser(new User("efecan", "123456", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        d.putUser(new User("zekihan", "123456", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    }
}
