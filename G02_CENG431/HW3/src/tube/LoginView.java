package tube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {

    private JLabel userLabel;
    private JTextField usernameText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JPanel panel;
    private FrameManager frame;

    public LoginView(FrameManager frame) {
        this.frame = frame;
        showLoginView();
    }

    public void showLoginView(){
        panel = new JPanel(new GridLayout(3, 1));
        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds(100, 10, 160, 25);
        panel.add(usernameText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        frame.setNewPanel(panel);
    }

    public void addLoginActionListener(ActionListener actionListener){
        loginButton.addActionListener(actionListener);
    }

    public String getUsername(){
        return usernameText.getText();
    }

    public char[] getPassword(){
        return passwordText.getPassword();
    }

    public FrameManager getFrame() {
        return frame;
    }

    public void securityCheckFailed(){
        passwordText.setBackground(Color.RED);
    }
}
