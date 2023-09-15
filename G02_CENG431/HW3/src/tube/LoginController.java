package tube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController {

    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        loginView.addLoginActionListener(new LoginActionListener());
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentials(loginView.getUsername(), loginView.getPassword());
        }
    }

    private void checkCredentials(String username, char[] password) {

        DataHandler dataHandler = new DataHandler();
        User user = dataHandler.getUser(username);

        if(user != null && user.getPassword().equals(String.valueOf(password))){
            MenuView menuView = new MenuView(loginView.getFrame());
            MenuController menuController = new MenuController(menuView, user);
        }
        else{
            loginView.securityCheckFailed();
        }
    }



}
