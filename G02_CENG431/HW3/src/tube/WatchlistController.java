package tube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WatchlistController {

    private final WatchlistView watchlistView;
    private final Watchlist watchlist;
    private User currentUser;

    public WatchlistController(WatchlistView watchlistView, Watchlist watchlist, User user){
        this.watchlistView = watchlistView;
        this.watchlist = watchlist;
        this.currentUser = user;

        watchlist.addObserver(watchlistView);
        watchlistView.addGoToVideoActionListener(new goToVideoActionListener());
        watchlistView.addMainMenuActionListener(new mainMenuActionListener());
        watchlistView.addDeleteVideoActionListener(new deleteVideoActionListener());
    }

    //Event performed whenever the user selects a video and clicks on 'Delete video' button
    private class deleteVideoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentUser.getWatchlists().contains(watchlist)) {
                int videoIndex = watchlistView.getSelectedListIndex();
                int videoId = watchlist.getVideos().get(videoIndex);
                watchlist.remove(videoId);
            }else{
                //TODO FEEDBACK
                //Not your watchlist! cant remove
            }
        }
    }

    //Event performed whenever the user selects a video and clicks on 'Watch' button
    private class goToVideoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = watchlistView.getSelectedListIndex();
            Integer videoId = watchlist.getVideos().get(i);
            //video from videoId?
            DataHandler dataHandler = new DataHandler();
            Video selectedVideo = dataHandler.getVideo(videoId);
            VideoView videoView = new VideoView(watchlistView.getFrame(), selectedVideo);
            VideoController videoController = new VideoController(selectedVideo, videoView, currentUser);
        }
    }

    //Event performed whenever the user clicks on 'Main Menu' button
    private class mainMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuView menuView = new MenuView(watchlistView.getFrame());
            MenuController menuController = new MenuController(menuView, currentUser);
        }
    }

}
