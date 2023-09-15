package battlegame;


import battlegame.utilities.GameReport;
import battlegame.warcrafts.Addable;
import battlegame.warcrafts.Warcraft;
import battlegame.warcrafts.WarcraftType;
import battlegame.warcrafts.plane.Engine;
import exceptions.IllegalPlayerOperationException;
import exceptions.PartAlreadyExistsException;
import exceptions.PartNotCompatibleException;
import exceptions.UnknownWarcraftTypeException;

import java.util.List;

public interface IGameEngine {

    boolean addWarcraft(int playerNo, WarcraftType warcraftType) throws UnknownWarcraftTypeException, IllegalPlayerOperationException;

    boolean addWarcraft(int playerNo, WarcraftType warcraftType, Engine engine) throws UnknownWarcraftTypeException, IllegalPlayerOperationException;

    boolean addPart(int playerNo, int warcraftNo, Addable part) throws PartNotCompatibleException, PartAlreadyExistsException;

    List<Warcraft> getPlayerLoadout(int playerNo);

    GameReport runSimulation();

    void resetItems();

}
