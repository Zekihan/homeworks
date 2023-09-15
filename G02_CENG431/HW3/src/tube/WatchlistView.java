package tube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

public class WatchlistView implements Observer {

    private Watchlist watchlist;
    private JButton watchVideoButton, mainMenuButton, removeVideoButton;
    private FrameManager frame;
    private JScrollPane scrollPane;
    private JList<String> videos;
    private DefaultListModel<String> videoModels;
    private JPanel panel;
    private JLabel label;

    public WatchlistView(FrameManager frame, Watchlist watchlist) {
        this.frame = frame;
        this.watchlist = watchlist;

        panel = new JPanel(new GridLayout(3, 1));
        panel.setLayout(null);

        JLabel videoTitle = new JLabel(watchlist.getName());
        videoTitle.setBounds(10, 10, 80, 25);
        panel.add(videoTitle);

        videos = new JList<String>();
        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 40, 450, 250);
        panel.add(scrollPane);

        showWatchlist();

        removeVideoButton = new JButton("Delete video");
        removeVideoButton.setBounds(160, 320, 120, 35);
        panel.add(removeVideoButton);

        watchVideoButton = new JButton("Watch");
        watchVideoButton.setBounds(10, 320, 120, 35);
        panel.add(watchVideoButton);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(310, 320, 120, 35);
        panel.add(mainMenuButton);

        frame.setNewPanel(panel);
    }


    public void showWatchlist(){
        String[] videoListArr = new String[watchlist.getVideos().size()];
        int i = 0;
        for(Integer id: watchlist.getVideos()){
            videoListArr[i] = "<html><body>" + id + "<br>" + "</span></body></html>}";
            i++;
        }
        videos = new JList<>(videoListArr);
        scrollPane.setViewportView(videos);
    }

    @Override
    //Whenever the watchlist changes, update the view
    public void update(Observable o, Object arg) {
        this.watchlist = (Watchlist) o;
        showWatchlist();
    }

    public void addDeleteVideoActionListener(ActionListener actionListener) {
        removeVideoButton.addActionListener(actionListener);
    }

    public void addGoToVideoActionListener(ActionListener actionListener) {
        watchVideoButton.addActionListener(actionListener);
    }

    public void addMainMenuActionListener(ActionListener actionListener) {
        mainMenuButton.addActionListener(actionListener);
    }


    public int getSelectedListIndex(){
        return videos.getSelectedIndex();
    }

    public FrameManager getFrame() { return frame; }

}
