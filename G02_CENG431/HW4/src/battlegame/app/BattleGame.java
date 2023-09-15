package battlegame.app;

import battlegame.GameEngine;
import battlegame.IGameEngine;
import io.ConsoleDisplay;
import io.ConsoleInput;
import io.IDisplay;
import io.Input;

import java.util.Scanner;

public class BattleGame {

    public void start() {

        //Create the game engine
        IGameEngine gameEngine = new GameEngine(2);

        //Initiate input and display handlers
        Input inputHandler = new ConsoleInput(new Scanner(System.in));
        IDisplay displayHandler = new ConsoleDisplay();


        //Initiate Main menu and start the game
        MainMenu mainMenu = new MainMenu(gameEngine, inputHandler, displayHandler);
        while (true) { mainMenu.execute(); }
    }
}
