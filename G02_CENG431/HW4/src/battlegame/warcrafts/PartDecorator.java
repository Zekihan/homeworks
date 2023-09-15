package battlegame.warcrafts;

import battlegame.warcrafts.plane.Plane;
import battlegame.warcrafts.ship.Ship;
import exceptions.PartAlreadyExistsException;
import exceptions.PartNotCompatibleException;

public class PartDecorator extends WarcraftDecorator {

    private final Addable addable;

    public PartDecorator(Warcraft warcraft, Addable addable) throws PartNotCompatibleException, PartAlreadyExistsException {
        super(warcraft);
        isAddable(addable);
        this.addable = addable;
    }

    @Override
    public int getPoint() {
        return getWarcraft().getPoint() + addable.getPoint();
    }

    @Override
    public String toString() {
        return super.toString() + " (" + addable.toString() + ")";
    }

    @Override
    public boolean isAlreadyAdded(Addable addable) {
        if (getWarcraft() instanceof WarcraftDecorator) {
            return addable.equals(this.addable) || ((WarcraftDecorator) getWarcraft()).isAlreadyAdded(addable);
        }
        return addable.equals(this.addable);
    }

    private void isAddable(Addable addable) throws PartNotCompatibleException, PartAlreadyExistsException {
        if (getOriginalType() == Plane.class) {
            if (!(addable.equals(Addable.ROCKET) || addable.equals(Addable.MISSILE) || addable.equals(Addable.MACHINE_GUN) || addable.equals(Addable.BOMB))) {
                throw new PartNotCompatibleException("Part is not compatible with planes");
            } else if (isAlreadyAdded(addable)) {
                throw new PartAlreadyExistsException("Part you are trying to add already attached to the warcraft");
            }
        } else if (getOriginalType() == Ship.class) {
            if (!(addable.equals(Addable.ROCKET) || addable.equals(Addable.TORPEDO) || addable.equals(Addable.CANNON))) {
                throw new PartNotCompatibleException("Part is not compatible with ships");
            } else if (isAlreadyAdded(addable)) {
                throw new PartAlreadyExistsException("Part you are trying to add already attached to the warcraft");
            }
        }
    }
}
