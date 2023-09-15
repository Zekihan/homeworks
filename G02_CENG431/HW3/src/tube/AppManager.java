package tube;

public class AppManager {
    FrameManager frame;

    public AppManager() {
        this.frame = new FrameManager();
    }

    public void start(){
        LoginView loginView = new LoginView(frame);
        LoginController loginController = new LoginController(loginView);
    }
}
