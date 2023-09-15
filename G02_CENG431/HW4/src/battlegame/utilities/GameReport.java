package battlegame.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameReport {
    private final List<Integer> playerScores;

    public GameReport(List<Integer> playerScores) {
        this.playerScores = playerScores;
    }

    public int getPlayerScore(int playerNo) {
        return playerScores.get(playerNo);
    }

    public int getWinner() {
        int maxVal = Collections.max(playerScores);
        return playerScores.indexOf(maxVal) + 1;
    }

    public int winMargin() {
        Integer maxVal = Collections.max(playerScores);
        int i = playerScores.indexOf(maxVal);
        List<Integer> scores = new ArrayList(playerScores);
        scores.remove(i);
        Integer secMaxVal = Collections.max(scores);

        return maxVal - secMaxVal;
    }
}
