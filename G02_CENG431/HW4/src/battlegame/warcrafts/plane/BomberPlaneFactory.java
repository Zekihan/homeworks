package battlegame.warcrafts.plane;

public class BomberPlaneFactory implements PlaneFactory {
    @Override
    public Plane createPlane(Engine engine) {
        return new Plane(PlaneType.BOMBER, engine);
    }
}
