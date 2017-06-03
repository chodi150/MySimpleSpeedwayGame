package view.menu_views;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.GameController;
import main.GameStateManager;
import model.menu_models.ChoosePlayersMenuModel;



public class ChoosePlayersMenuView {

	private ChoosePlayersMenuModel menu;
	private BufferedImage background;
	private BufferedImage players[];
	
	public ChoosePlayersMenuView(ChoosePlayersMenuModel menu)
	{
		this.menu = menu;
		try {
		    background = ImageIO.read(getClass().getResource("/menu2.jpg"));  
		    
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		players = new BufferedImage[6];
		for(int i=0; i<6; i++)
		{
			try {
			    players[i] = ImageIO.read(getClass().getResource("/player"+i+".jpg"));
			    
			} catch (IOException e) {
			    throw new RuntimeException(e);
			}
		}
	}
	public void render(Graphics g)
	{
        g.drawImage(background, 0, 0, null);
        g.drawImage(players[menu.getWhichPlayer()], 500, 200, null);
	}

	public void keyPressed(KeyEvent e)
	{

		int key = e.getKeyCode();

		if(key == KeyEvent.VK_RIGHT)
		{
			menu.setIsRight(true);
			menu.setIsLeft(false);

		}

		if(key == KeyEvent.VK_LEFT)
		{
			menu.setIsLeft(true);
			menu.setIsRight(false);
		}
		if(key == KeyEvent.VK_ESCAPE)
		{

			GameStateManager.gameState = GameStateManager.State.MENU;
		}
		if(key == KeyEvent.VK_SPACE)
		{
			GameController.whichPlayer = menu.getWhichPlayer();
			GameStateManager.gameState = GameStateManager.State.GAME;
		}
		if(key == KeyEvent.VK_T)
		{
			GameController.whichPlayer = menu.getWhichPlayer();
			GameStateManager.gameState = GameStateManager.State.TIPS;
		}
		menu.doKeyEvent();
	}
	public void keyReleased(KeyEvent e)
	{

		int key = e.getKeyCode();

		if(key == KeyEvent.VK_RIGHT)
		{
			menu.setIsRight(false);
		}

		if(key == KeyEvent.VK_LEFT)
		{
			menu.setIsLeft(false);

		}

		menu.doKeyEvent();
	}
}
