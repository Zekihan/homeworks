package tube;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@XmlRootElement
public class Watchlist extends Observable {

    @XmlElement
    private String name;

    @XmlElementWrapper(name = "videos")
    @XmlElement(name = "video")
    private List<Integer> videos;

    public Watchlist(List<Integer> videos, String name) {
        setVideos(videos);
        setName(name);
    }

    public Watchlist() {
        this.videos = new ArrayList<>();
        this.name = "No-name";
    }

    public String getName() {
        return this.name;
    }

    //Return a copy of videos
    public List<Integer> getVideos() {
        return new ArrayList<>(videos);
    }

    //Add a video to the watchlist
    public void add(Integer videoId) {
        if(!videos.contains(videoId)){
            videos.add(videoId);
            setChanged();
            notifyObservers(getVideos());
        }
    }

    //Remove video from the watchlist
    public void remove(int id) {
        videos.removeIf(videoId -> videoId == id);
        setChanged();
        notifyObservers(this);
    }

    @Override
    public String toString() {
        return "Watchlist " + "name='" + name;
    }

    private void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name of the watchlist cannot be empty or null");
        }
        this.name = name;
    }

    private void setVideos(List<Integer> videos){
        if(videos == null){
            throw new IllegalArgumentException("List of videos of a watchlist cannot be null");
        }else{
            this.videos = new ArrayList<>(videos);
        }

    }


}
