package battlegame;

import battlegame.warcrafts.*;
import battlegame.warcrafts.plane.Plane;
import battlegame.warcrafts.ship.Ship;
import exceptions.IllegalPlayerOperationException;
import exceptions.PartAlreadyExistsException;
import exceptions.PartNotCompatibleException;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private List<Warcraft> warcrafts;

    public Player() {
        this.warcrafts = new ArrayList<>();
    }

    public boolean addWarcraft(Warcraft warcraft) throws IllegalPlayerOperationException {
        if (warcrafts.size() < 5) {
            if (warcraft instanceof Plane) {
                return addPlane(warcraft);
            } else if (warcraft instanceof Ship) {
                return addShip(warcraft);
            } else if (warcraft instanceof WarcraftDecorator) {
                if (((WarcraftDecorator) warcraft).getOriginalType().equals(Plane.class)) {
                    return addPlane(warcraft);
                } else if (((WarcraftDecorator) warcraft).getOriginalType().equals(Ship.class)) {
                    return addShip(warcraft);
                } else System.out.println("Unknown type");
            } else System.out.println("Unknown type");
        } else {
            throw new IllegalPlayerOperationException("Cannot add more than 5 warcraft");
        }
        return false;
    }

    public boolean addPartToWarcraft(int warcraftNo, Addable addable) throws PartNotCompatibleException, PartAlreadyExistsException {
        Warcraft warcraft = warcrafts.get(warcraftNo);

        warcraft = new PartDecorator(warcraft, addable);

        warcrafts.set(warcraftNo, warcraft);
        return true;
    }

    public int simulateAttack() {
        int totalPoint = 0;
        for (Warcraft warcraft : warcrafts) {
            totalPoint += warcraft.getPoint();
        }
        return totalPoint;
    }

    public void resetItems() {
        warcrafts = new ArrayList<>();
    }

    private boolean addShip(Warcraft ship) throws IllegalPlayerOperationException {
        boolean result = false;
        if (getShipCount() < 3) {
            if (getTypeCount(ship.getType()) < 2) {
                result = warcrafts.add(ship);
            } else {
                throw new IllegalPlayerOperationException("Cannot add more than 2 same type ship");
            }
        } else {
            throw new IllegalPlayerOperationException("Cannot add more than 3 ship");
        }
        return result;
    }

    private boolean addPlane(Warcraft plane) throws IllegalPlayerOperationException {
        boolean result = false;
        if (getPlaneCount() < 3) {
            if (getTypeCount(plane.getType()) < 2) {
                result = warcrafts.add(plane);
            } else {
                throw new IllegalPlayerOperationException("Cannot add more than 2 same type plane");
            }
        } else {
            throw new IllegalPlayerOperationException("Cannot add more than 3 plane");
        }
        return result;
    }

    private int getPlaneCount() {
        int count = 0;
        for (Warcraft warcraft : warcrafts) {
            if (warcraft instanceof Plane) {
                count++;
            } else if (warcraft instanceof WarcraftDecorator) {
                if (((WarcraftDecorator) warcraft).getOriginalType().equals(Plane.class)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getShipCount() {
        int count = 0;
        for (Warcraft warcraft : warcrafts) {
            if (warcraft instanceof Ship) {
                count++;
            } else if (warcraft instanceof WarcraftDecorator) {
                if (((WarcraftDecorator) warcraft).getOriginalType().equals(Ship.class)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getTypeCount(WarcraftType type) {
        int count = 0;
        for (Warcraft warcraft : warcrafts) {
            if (warcraft.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    public List<Warcraft> getWarcrafts() {
        return warcrafts;
    }

}
