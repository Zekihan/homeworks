package tube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuController {
    private final MenuView menuView;
    private final User user;

    public MenuController(MenuView menuView, User user) {
        this.menuView = menuView;
        this.user = user;
        menuView.addBrowseUsersActionListener(new BrowseUsersActionListener());
        menuView.addBrowseWatchlistsActionListener(new BrowseWatchlistsActionListener());
        menuView.addLogoutActionListener(new LogoutActionListener());
    }

    private class BrowseUsersActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            IDataHandler dataHandler = new DataHandler();
            BrowseUsersView browseUsersView = new BrowseUsersView(menuView.getFrame(), dataHandler.getUsernames(), user);
            BrowseUsersController browseUsersController = new BrowseUsersController(browseUsersView, user);
        }
    }

    private class BrowseWatchlistsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BrowseWatchlistView browseWatchlistView = new BrowseWatchlistView(menuView.getFrame(), user);
            BrowseWatchlistController browseWatchlistController = new BrowseWatchlistController(user, browseWatchlistView);
        }
    }

    private class LogoutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView(menuView.getFrame());
            LoginController loginController = new LoginController(loginView);
        }
    }
}
