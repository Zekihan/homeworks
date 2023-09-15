package battlegame.warcrafts.ship;

public class DestroyerShipFactory implements ShipFactory {
    @Override
    public Ship createShip() {
        return new Ship(ShipType.DESTROYER);
    }
}
