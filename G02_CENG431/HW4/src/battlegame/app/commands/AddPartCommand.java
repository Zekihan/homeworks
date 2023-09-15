package battlegame.app.commands;

import battlegame.IGameEngine;
import battlegame.warcrafts.Addable;
import battlegame.warcrafts.Warcraft;
import battlegame.warcrafts.plane.Plane;
import battlegame.warcrafts.plane.PlaneType;
import battlegame.warcrafts.ship.Ship;
import battlegame.warcrafts.ship.ShipType;
import exceptions.InvalidInputException;
import exceptions.PartAlreadyExistsException;
import exceptions.PartNotCompatibleException;
import io.IDisplay;
import io.Input;

import java.util.List;

public class AddPartCommand extends AbstractCommand {

    private List<Warcraft> warcrafts;
    private IDisplay displayHandler;
    private Input inputHandler;
    private String menuItems;
    private int playerNo;

    public AddPartCommand(IGameEngine gameEngine, IDisplay displayHandler, Input inputHandler, int playerNo) {
        super(gameEngine);
        setWarcrafts(getGameEngine().getPlayerLoadout(playerNo));
        setInputHandler(inputHandler);
        setDisplayHandler(displayHandler);
        setPlayerNo(playerNo);
        initiateMenuText();
    }




    public String toString() {
        return "Add part";
    }

    @Override
    public void execute() {

        //Refresh loadouts everytime add part is to be used
        setWarcrafts(getGameEngine().getPlayerLoadout(playerNo));

        if (warcrafts.size() == 0) {
            displayHandler.displayWarning("No warcraft to select!");
            return;
        }

        /* Get the most up-to-date loadout(as string) of the player. */
        initiateMenuText();

        displayHandler.displayMenu(menuItems, "Choose a warcraft: ");
        int id = inputHandler.readInt();

        /* No items present in the player's loadout. */
        if (id - 1 >= warcrafts.size()) {
            displayHandler.displayErrorMessage("No item with given number");
            return;
        }

        Warcraft selectedWarcraft = warcrafts.get(id - 1);
        int partId;
        Addable partToAdd;

        /* Selected warcraft to add item is of type Ship */
        if (selectedWarcraft.getClass() == Ship.class ||
                selectedWarcraft.getType() instanceof ShipType) {

            /* Addable parts for ship */
            menuItems = "1. Rocket\n" +
                    "2. Torpedo\n" +
                    "3. Cannon";

            displayHandler.displayMenu(menuItems, "Choose a part to add: ");
            partId = inputHandler.readInt();

            switch (partId) {

                /* Addable is of type Rocket */
                case 1:
                    partToAdd = Addable.ROCKET;
                    break;

                /* Addable is of type Torpedo */
                case 2:
                    partToAdd = Addable.TORPEDO;
                    break;

                /* Addable is of type Cannon */
                case 3:
                    partToAdd = Addable.CANNON;
                    break;

                /* Addable type is not recognized */
                default:
                    try {
                        throw new InvalidInputException("Given part number is not in the range!");
                    } catch (InvalidInputException e) {
                        //Display error message and go back.
                        displayHandler.displayErrorMessage("Invalid part number");
                        return;
                    }
            }

        /* Selected warcraft to add item is of type Plane */
        } else if (selectedWarcraft.getClass() == Plane.class ||
                selectedWarcraft.getType() instanceof PlaneType) {

            /* Addable parts for plane */
            menuItems = "1. Rocket\n" +
                    "2. Missile\n" +
                    "3. Machine gun\n" +
                    "4. Bomb";

            displayHandler.displayMenu(menuItems, "Choose a part to add: ");
            partId = inputHandler.readInt();

            switch (partId) {

                /* Addable is of type Rocket */
                case 1:
                    partToAdd = Addable.ROCKET;
                    break;

                /* Addable is of type Missile */
                case 2:
                    partToAdd = Addable.MISSILE;
                    break;

                /* Addable is of type Machine gun */
                case 3:
                    partToAdd = Addable.MACHINE_GUN;
                    break;

                /* Addable is of type Bomb */
                case 4:
                    partToAdd = Addable.BOMB;
                    break;

                /* Addable type is not recognized */
                default:
                    try {
                        throw new InvalidInputException("Given part number is not in the range!");
                    } catch (InvalidInputException e) {
                        //Display error and go back
                        displayHandler.displayErrorMessage("Invalid part number");
                        return;
                    }
            }

        /* Selected warcraft type is not recognized. */
        } else {
            try {
                throw new InvalidInputException("Given warcraft number is not valid!");
            } catch (InvalidInputException e) {
                //Display error message and go back
                displayHandler.displayErrorMessage("Invalid warcraft type number is given!");
                return;
            }
        }


        try {
            getGameEngine().addPart(playerNo, id, partToAdd);
        } catch (PartNotCompatibleException | PartAlreadyExistsException e) {
            /* Error due to some violation of rules */
            displayHandler.displayErrorMessage(e.getMessage());
        }

    }


    private void initiateMenuText() {
        StringBuilder warcrafts = new StringBuilder();
        int menuItemIndex = 1;
        for (Warcraft w : this.warcrafts) {
            warcrafts.append(menuItemIndex + ". " + w.toString() + "\n");
            menuItemIndex++;
        }
        menuItems = warcrafts.toString();
    }

    private void setWarcrafts(List<Warcraft> warcrafts) {
        if (warcrafts == null) {
            throw new IllegalArgumentException("Player's list of battlegame.warcrafts cannot be null");
        }
        this.warcrafts = warcrafts;
    }

    private void setDisplayHandler(IDisplay displayHandler) {
        if (displayHandler == null) {
            throw new IllegalArgumentException("IDisplay object cannot be null");
        }
        this.displayHandler = displayHandler;
    }

    private void setInputHandler(Input inputHandler) {
        if (inputHandler == null) {
            throw new IllegalArgumentException("IDisplay object cannot be null");
        }
        this.inputHandler = inputHandler;
    }

    private void setPlayerNo(int playerNo) {
        if (playerNo <= 0) {
            throw new IllegalArgumentException("Given player number must be greater than 0");
        }
        this.playerNo = playerNo;
    }
}
