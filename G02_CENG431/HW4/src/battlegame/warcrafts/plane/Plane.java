package battlegame.warcrafts.plane;

import battlegame.warcrafts.Warcraft;
import battlegame.warcrafts.WarcraftType;

public class Plane implements Warcraft {

    private Engine engine;
    private WarcraftType type;

    public Plane(PlaneType planeType, Engine engine) {
        super();
        setPlaneType(planeType);
        setEngine(engine);
    }

    @Override
    public int getPoint() {
        return type.getPoint() + engine.getPoint();
    }

    public WarcraftType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Plane: " + type.toString() + " [" + type.getMinPoint() + ", " + type.getMaxPoint()
                + "] " + " with " + engine.name() + " [" + engine.getMinPoint() + ", " + engine.getMaxPoint() + "]";
    }

    private void setPlaneType(PlaneType planeType) {
        this.type = planeType;
    }

    private void setEngine(Engine engine) {
        this.engine = engine;
    }

}
