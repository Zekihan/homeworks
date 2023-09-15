package tube;

import java.util.List;

public interface IDataHandler {

    public List<String> getUsernames();

    public User getUser(String username);

    public Video getVideo(int id);

    public void putUser(User user);

    public void putVideo(Video video);
}
