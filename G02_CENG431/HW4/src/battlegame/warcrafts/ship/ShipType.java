package battlegame.warcrafts.ship;

import battlegame.warcrafts.WarcraftType;

import java.util.Random;

public enum ShipType implements WarcraftType {
    CRUISER(15, 30),
    DESTROYER(20, 40),
    FRIGATE(10, 45);

    private final int minPoint;
    private final int maxPoint;

    ShipType(int minPoint, int maxPoint) {
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
