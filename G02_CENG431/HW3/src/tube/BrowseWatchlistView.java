package tube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class BrowseWatchlistView implements Observer {
    private JPanel panel;
    private JButton mainMenuButton;
    private JButton backButton;
    private JButton createWatchlistButton;
    private JButton openWatchlistButton;
    private JScrollPane scrollPane;
    private JList<String> jWatchlists;
    private FrameManager frame;
    private User user;
    private List<Watchlist> followingsWatchlists;

    public BrowseWatchlistView(FrameManager frame, User user) {
        this.frame = frame;
        this.user = user;
        this.followingsWatchlists = new ArrayList<>();

        panel = new JPanel(new GridLayout(3, 1));
        panel.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 40, 450, 250);
        panel.add(scrollPane);

        jWatchlists = new JList<>();
        scrollPane.setViewportView(jWatchlists);
        showLists();

        openWatchlistButton = new JButton("Open Watchlist");
        openWatchlistButton.setBounds(150, 320, 140, 25);
        panel.add(openWatchlistButton);

        createWatchlistButton = new JButton("Create Watchlist");
        createWatchlistButton.setBounds(10, 320, 140, 25);
        panel.add(createWatchlistButton);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(10, 400, 120, 25);
        panel.add(mainMenuButton);

        frame.setNewPanel(panel);
    }

    private void showLists() {
        List<Watchlist> allWatchlists = new ArrayList();
        allWatchlists.addAll(user.getWatchlists());
        allWatchlists.addAll(followingsWatchlists);
        String[] watchlistsArr = new String[allWatchlists.size()];
        int i = 0;
        for(Watchlist wl: allWatchlists){
            watchlistsArr[i] = "<html><body>" + wl.getName() + "<br>" + " " + "<br>" + "</span></body></html>}";
            i++;
        }
        jWatchlists.setListData(watchlistsArr);
    }

    //Get user input from a popup
    public String getUserInput(String message){
        return JOptionPane.showInputDialog(message);
    }

    public void addOpenWatchlistButton(ActionListener actionListener) {
        openWatchlistButton.addActionListener(actionListener);
    }

    public void addCreateWatchlistButton(ActionListener actionListener) {
        createWatchlistButton.addActionListener(actionListener);
    }

    public void addMainMenuActionListener(ActionListener actionListener) {
        mainMenuButton.addActionListener(actionListener);
    }

    public void addOpenWatchlistMouseListener(MouseListener mouseListener) {
        jWatchlists.addMouseListener(mouseListener);
    }

    public int getSelectedListIndex(){
        return jWatchlists.getSelectedIndex();
    }

    public void addFollowingsWatchlists(List<Watchlist> watchlists){
        followingsWatchlists.addAll(watchlists);
        showLists();
    }

    public FrameManager getFrame() {
        return frame;
    }

    @Override
    public void update(Observable o, Object arg) {
        user = (User) o;
        showLists();
    }
}
