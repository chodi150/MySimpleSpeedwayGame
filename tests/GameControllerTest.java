package controller;

import main.GameStateManager;

import model.PlayerModel;
import org.testng.annotations.Test;




import static main.GameStateManager.State.GAME;
import static main.GameStateManager.State.MENU3;
import static main.GameStateManager.State.RESULTS;
import static org.junit.Assert.*;


public class GameControllerTest {

    @Test
    public void GivenPlayerAndEnemies_WhenPlayerStartsTooEarly_ThenMenuAppears() {
        GameStateManager.gameState = GAME;
        GameController controller = new GameController();
        PlayerModel model = controller.getPlayerModel();
        model.setIsStarted(true);
        controller.tick(); //in this tick its checked whether player touches tape
        controller.tick(); //this tick takes us to MENU3
        assertEquals(MENU3, GameStateManager.gameState );
    }

    @Test
    public void GivenPlayerThatFinishes4Laps_WhenGameSimulated_ThenResultsAppear()
    {
        GameStateManager.gameState = GAME;
        GameController controller = new GameController();
        PlayerModel model = controller.getPlayerModel();
        model.nextLap(); //start 1 lap
        for(int i =0; i<4; i++)
             model.nextLap();

        controller.tick(); //player "discovers" that he finishes race
        controller.tick(); //this ticks takes us to results
        assertEquals(RESULTS, GameStateManager.gameState );
    }
    @Test
    public void GivenPlayerThatFinishesLaps_WhenGameSimulated_ThenResultsAppearWithNoDelay()
    {
        GameController controller = new GameController();
        PlayerModel model = controller.getPlayerModel();
        GameStateManager.gameState = GAME;
        model.nextLap(); //start 1 lap
        for(int i =0; i<4; i++)
            model.nextLap();
        long timer = System.currentTimeMillis();
        controller.tick(); //player "discovers" that he finishes race
        controller.tick(); //this ticks takes us to results
        long timer1 = System.currentTimeMillis();

        assertEquals(0, timer1-timer);
    }

    @Test
    public void GivenPlayer_WhenGetsExcluded_ThenGameGetsIntoMenuState()
    {
        GameStateManager.gameState = GAME;
        GameController controller = new GameController();
        PlayerModel model = controller.getPlayerModel();
        model.setIsExcluded(true);
        controller.tick(); //in this tick its discovered that player got excluded
        controller.tick(); //this tick takes us to MENU3
        assertEquals(MENU3, GameStateManager.gameState );
    }

}