package tube;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class VideoController {
    private final Video video;
    private final VideoView videoView;
    private DataHandler dataHandler;
    private User currentUser;

    public VideoController(Video video, VideoView videoView, User user) {

        this.video = video;
        this.videoView = videoView;
        this.dataHandler = new DataHandler();
        this.currentUser = user;

        video.addObserver(dataHandler);
        currentUser.addObserver(dataHandler);

        videoView.addDislikeActionListener(new DislikeActionListener());
        videoView.addLikeActionListener(new LikeActionListener());
        videoView.addCommentActionListener(new CommentActionListener());
        videoView.addAddToWatchlistActionListener(new AddToWatchlistActionListener());
    }

    private class AddToWatchlistActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] choices = new String[currentUser.getWatchlists().size()];
            if(!currentUser.getWatchlists().isEmpty()){
                for(int i=0; i<currentUser.getWatchlists().size(); i++){
                    choices[i] = currentUser.getWatchlists().get(i).getName();
                }
                String selectedWatchlist = videoView.inputFromAListOfValues("Your watchlists", choices, "Choose a watchlist"); // Array of choices
                List<Watchlist> currentUserWatchlists = currentUser.getWatchlists();
                for(Watchlist watchlist : currentUserWatchlists){
                    if(watchlist.getName().equals(selectedWatchlist)){
                        watchlist.add(video.getId());
                        currentUser.setWatchlist(watchlist);
                    }
                }
            }else{
                //TODO FEEDBACK YOU HAVE NO WATCHLIST TO ADD A VIDEO
            }
        }
    }

    private class LikeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentUser.removeFromDislikes(video.getId())){
                video.setDislikes(video.getDislikes() - 1);
            }
            if(currentUser.addToLikes(video.getId())){
                video.setLikes(video.getLikes() + 1);
            }else{
                currentUser.removeFromLikes(video.getId());
                video.setLikes(video.getLikes() - 1);
            }
        }
    }

    private class DislikeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentUser.removeFromLikes(video.getId())){
                video.setLikes(video.getLikes() - 1);
            }
            if(currentUser.addToDislikes(video.getId())){
                video.setDislikes(video.getDislikes() + 1);
            }else{
                currentUser.removeFromDislikes(video.getId());
                video.setDislikes(video.getDislikes() - 1);
            }
        }
    }

    private class CommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String commentText = videoView.getCommentText();
            video.addComment(new Comment(currentUser.getUsername(), commentText));
            dataHandler.putVideo(video);
        }
    }

}
