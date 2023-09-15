package battlegame.app.commands;

import battlegame.IGameEngine;
import battlegame.warcrafts.WarcraftType;
import battlegame.warcrafts.plane.Engine;
import battlegame.warcrafts.plane.PlaneType;
import battlegame.warcrafts.ship.ShipType;
import exceptions.IllegalPlayerOperationException;
import exceptions.InvalidInputException;
import exceptions.UnknownWarcraftTypeException;
import io.IDisplay;
import io.Input;

public class AddItemCommand extends AbstractCommand {

    private int playerNo;
    private IDisplay displayHandler;
    private Input inputHandler;

    public AddItemCommand(IGameEngine gameEngine, int playerNo, IDisplay displayHandler, Input inputHandler) {
        super(gameEngine);
        setPlayerNo(playerNo);
        setDisplayHandler(displayHandler);
        setDisplayHandler(displayHandler);
        setInputHandler(inputHandler);
    }


    @Override
    public void execute() {

        /* Max five items */
        String menuText = " 1. Plane \n 2. Ship";
        displayHandler.displayMenu(menuText, "Choose a warcraft: ");
        int warcraftNo = inputHandler.readInt();
        WarcraftType warcraftType;
        int typeNo;

        switch (warcraftNo) {

            /* If the selected warcraft is of type Plane */
            case 1:
                menuText = "1. Fighter plane\n2. Bomber Plane\n3. Multirole Plane.";
                displayHandler.displayMenu(menuText, "Choose a plane type: ");
                typeNo = inputHandler.readInt();
                switch (typeNo) {

                    /* Plane of type Fighter */
                    case 1:
                        warcraftType = PlaneType.FIGHTER;
                        break;

                    /* Plane of type Bomber */
                    case 2:
                        warcraftType = PlaneType.BOMBER;
                        break;

                    /* Plane of type Multirole */
                    case 3:
                        warcraftType = PlaneType.MULTIROLE;
                        break;

                    /* Unrecgonized type number */
                    default:
                        try {
                            throw new InvalidInputException("No plane with the given number!");

                        } catch (InvalidInputException e) {
                            //Display error and go back
                            displayHandler.displayErrorMessage("Given number does not match to a plane type.");
                            displayHandler.displayErrorMessage("Warcraft could not be created.");
                            return;
                        }
                }


                menuText = "1. Pulsejet.\n2. Turbojet.";
                displayHandler.displayMenu(menuText, "Choose an engine type: ");
                int engineNo = inputHandler.readInt();
                Engine engine;

                switch (engineNo) {

                    /* Engine of type Pulsejet */
                    case 1:
                        engine = Engine.PULSEJET;
                        break;

                    /* Engine of type Turbojet */
                    case 2:
                        engine = Engine.TURBOJET;
                        break;

                    /* Unrecognized engine number */
                    default:
                        try {
                            throw new InvalidInputException("Invalid input");

                        } catch (InvalidInputException e) {
                            //Display error and go back
                            displayHandler.displayErrorMessage("Given engine type number is invalid.");
                            displayHandler.displayErrorMessage("Warcraft could not be created.");
                            return;
                        }
                }
                try {
                    getGameEngine().addWarcraft(playerNo, warcraftType, engine);

                } catch (UnknownWarcraftTypeException | IllegalPlayerOperationException e) {
                    //Warcraft could not be added due to some rule violation (game rule)
                    displayHandler.displayErrorMessage(e.getMessage());
                    return;
                }
                break;


            /* If the selected warcraft is of type Ship */
            case 2:
                menuText = "1. Cruiser ship\n2. Destroyer ship\n3. Frigate ship.";
                displayHandler.displayMenu(menuText, "Choose a ship type: ");
                typeNo = inputHandler.readInt();
                switch (typeNo) {

                    /* Ship of type Cruiser */
                    case 1:
                        warcraftType = ShipType.CRUISER;
                        break;

                    /* Ship of type Destroyer */
                    case 2:
                        warcraftType = ShipType.DESTROYER;
                        break;

                    /* Plane of type Frigate */
                    case 3:
                        warcraftType = ShipType.FRIGATE;
                        break;

                    /* Unrecognized ship type */
                    default:
                        try {
                            throw new InvalidInputException("Invalid input");

                        } catch (InvalidInputException e) {
                            //Display error and go back
                            displayHandler.displayErrorMessage("Given ship type number does not match to a ship type!");
                            return;
                        }

                }
                try {
                    getGameEngine().addWarcraft(playerNo, warcraftType);
                } catch (UnknownWarcraftTypeException | IllegalPlayerOperationException e) {
                    /* Warcraft could not be added to the loadout due to some rule violation. */
                    displayHandler.displayErrorMessage(e.getMessage());
                }
                break;

            /* Unrecognized warcraft type number */
            default:
                try {
                    throw new InvalidInputException("Invalid warcraft number input.");

                } catch (InvalidInputException e) {
                    //Display error message and go back.
                    displayHandler.displayErrorMessage("Given warcraft number does not match to a real warcraft type!");
                    displayHandler.displayErrorMessage("Warcraft could not be created.");
                }
        }
    }


    public String toString() {
        return "Add item";
    }


    private void setInputHandler(Input inputHandler) {
        if (inputHandler == null) {
            throw new IllegalArgumentException("Input handler object is null");
        }
        this.inputHandler = inputHandler;
    }

    private void setDisplayHandler(IDisplay displayHandler) {
        if (displayHandler == null) {
            throw new IllegalArgumentException("IDisplay object is null");
        }
        this.displayHandler = displayHandler;
    }

    private void setPlayerNo(int playerNo) {
        if (playerNo != 1 && playerNo != 2) {
            throw new IllegalArgumentException("Given player number can either be 1 or 2.");
        }
        this.playerNo = playerNo;
    }

}
