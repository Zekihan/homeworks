package battlegame.warcrafts.ship;

import battlegame.warcrafts.Warcraft;
import battlegame.warcrafts.WarcraftType;

public class Ship implements Warcraft {
    WarcraftType type;

    public Ship(ShipType type) {
        super();
        setShipType(type);
    }

    @Override
    public int getPoint() {
        return type.getPoint();
    }

    public WarcraftType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Ship: " + type.toString() + " [" + type.getMinPoint() + ", " + type.getMaxPoint() + "]";
    }

    private void setShipType(ShipType shipType) {
        this.type = shipType;
    }


}
