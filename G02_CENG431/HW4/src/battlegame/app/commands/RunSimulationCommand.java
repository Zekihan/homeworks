package battlegame.app.commands;

import battlegame.IGameEngine;
import battlegame.utilities.GameReport;
import io.IDisplay;

public class RunSimulationCommand extends AbstractCommand {

    private final IDisplay displayHandler;

    public RunSimulationCommand(IGameEngine gameEngine, IDisplay displayHandler) {
        super(gameEngine);
        this.displayHandler = displayHandler;
    }


    public String toString() {
        return "Run Simulation";
    }

    @Override
    public void execute() {
        //Run the simulation, get the end game report and display it.
        GameReport simulationResults = getGameEngine().runSimulation();
        displayHandler.displayGameReport(simulationResults.getPlayerScore(0), simulationResults.getPlayerScore(1));
    }
}
