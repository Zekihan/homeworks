package battlegame.warcrafts.plane;

import battlegame.warcrafts.WarcraftType;

import java.util.Random;

public enum PlaneType implements WarcraftType {
    FIGHTER(10, 12),
    BOMBER(15, 20),
    MULTIROLE(10, 25);

    private final int minPoint;
    private final int maxPoint;

    PlaneType(int minPoint, int maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public int getPoint() {
        Random r = new Random();
        return r.nextInt(maxPoint + 1 - minPoint) + minPoint;
    }

    @Override
    public int getMaxPoint() {
        return maxPoint;
    }

    @Override
    public int getMinPoint() {
        return minPoint;
    }
}
