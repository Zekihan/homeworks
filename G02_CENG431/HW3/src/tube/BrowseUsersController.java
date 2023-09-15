package tube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowseUsersController {

    private final BrowseUsersView browseUsersView;
    private final User currentUser;
    private final DataHandler dataHandler;

    public BrowseUsersController(BrowseUsersView browseUsersView, User user){
        this.dataHandler =  new DataHandler();
        this.browseUsersView = browseUsersView;
        this.currentUser = user;

        dataHandler.observeUsers();
        user.addObserver(dataHandler);
        user.addObserver(browseUsersView);

        //Connect action listeners to the view.
        browseUsersView.addFollowActionListener(new FollowActionListener());
        browseUsersView.addUnfollowActionListener(new UnfollowActionListener());
        browseUsersView.addMainMenuActionListener(new MainMenuActionListener());
    }

    //Event performed whenever the user selects a user and clicks on 'Follow User' button.
    private class FollowActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User targetUser = dataHandler.getUser(browseUsersView.getSelectedUsername());
            currentUser.follow(targetUser.getUsername());
            targetUser.addFollower(currentUser.getUsername());
        }
    }

    //Event performed whenever the user selects a user and clicks on 'Unfollow User' button
    private class UnfollowActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User targetUser = dataHandler.getUser(browseUsersView.getSelectedUsername());
            currentUser.unfollow(targetUser.getUsername());
            targetUser.removeFollower(currentUser.getUsername());
        }
    }

    //Event performed whenver the user clicks on the 'Main Menu' button
    private class MainMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuView menuView = new MenuView(browseUsersView.getFrame());
            MenuController menuController = new MenuController(menuView, currentUser);
        }
    }
}
