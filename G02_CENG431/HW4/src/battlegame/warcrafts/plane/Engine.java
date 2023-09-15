package battlegame.warcrafts.plane;

import java.util.Random;

public enum Engine {
    PULSEJET(2, 4),
    TURBOJET(5, 7);

    private final int minPoint;
    private final int maxPoint;

    Engine(int minPoint, int maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public int getMinPoint() {
        return minPoint;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public int getPoint() {
        Random r = new Random();
        return r.nextInt(maxPoint + 1 - minPoint) + minPoint;
    }
}
