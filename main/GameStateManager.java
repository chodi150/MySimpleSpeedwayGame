package main;

import controller.*;
import model.menu_models.ChoosePlayersMenuModel;
import model.menu_models.WelcomeMenuModel;
import model.menu_models.ResultsModel;
import view.*;
import view.menu_views.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;





public class GameStateManager extends KeyAdapter {


    private WelcomeMenuModel welcome_menu_model;
    private WelcomeMenuView welcome_menu_view;

    private ChoosePlayersMenuModel choose_players_menu_model;
    private ChoosePlayersMenuView choose_players_menu_view;


    private ExclusionMenuView exclusion_menu_view;
    private GameController game_controller;
    private ResultsView results_view;
    private GameView game_view;
    private TipsView tips_view;



    public enum State {MENU, MENU2,  GAME, MENU3, RESULTS, TIPS}
    public static State gameState = State.MENU;

    GameStateManager() {
        welcome_menu_model = new WelcomeMenuModel();
        welcome_menu_view = new WelcomeMenuView(welcome_menu_model);
        choose_players_menu_model = new ChoosePlayersMenuModel();
        choose_players_menu_view = new ChoosePlayersMenuView(choose_players_menu_model);
        exclusion_menu_view = new ExclusionMenuView();
        results_view = new ResultsView();
        tips_view = new TipsView();

    }

     void update() {

        if(gameState == State.MENU)
        {
            if(game_controller!=null)
                game_controller = null;
        }
        if(gameState == State.GAME)
        {
            if(game_controller==null)
            {
                game_controller = new GameController();
                game_view = new GameView(game_controller);
                ResultsModel.position = 1;
            }
            game_controller.tick();
        }

    }

     void render(Graphics g) {
       if (gameState == State.MENU)
           welcome_menu_view.render(g);
        if(gameState == State.MENU2 )
            choose_players_menu_view.render(g);
        if(gameState == State.GAME && game_controller!=null)
            game_view.render(g);
        if(gameState == State.MENU3)
            exclusion_menu_view.render(g);
        if(gameState == State.RESULTS)
        {
            results_view.render(g);
        }
        if(gameState == State.TIPS)
            tips_view.render(g);

        }



    public void keyPressed(KeyEvent e) {

        if(gameState == State.MENU)
            welcome_menu_view.keyPressed(e);
        if(gameState == State.MENU2)
            choose_players_menu_view.keyPressed(e);
        if(gameState == State.GAME && game_controller!=null)
            game_view.keyPressed(e);
        if(gameState == State.MENU3 || gameState==State.RESULTS)
            exclusion_menu_view.keyPressed(e);
        if(gameState == State.TIPS)
            tips_view.keyPressed(e);
    }
    public void keyReleased(KeyEvent e)
    {
        if(gameState == State.GAME && game_controller!=null)
            game_view.keyReleased(e);
        if(gameState == State.MENU)
            welcome_menu_view.keyReleased(e);
        if(gameState == State.MENU2)
            choose_players_menu_view.keyReleased(e);
    }
}