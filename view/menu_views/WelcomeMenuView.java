package view.menu_views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameStateManager;
import model.menu_models.WelcomeMenuModel;

public class WelcomeMenuView {
	private WelcomeMenuModel menu;
	private BufferedImage background;
	public WelcomeMenuView(WelcomeMenuModel menu)
	{
		this.menu = menu;
		loadBackgroundGraphics();

	}

	private void loadBackgroundGraphics() {
		try {
		    background = ImageIO.read(getClass().getResource("/menu.jpg"));

		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
	}

	public void render(Graphics g)
	{

		Font font = new Font("Times New Roman", 1, 20);
        g.drawImage(background, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(font);

        if(menu.getIsFirst())
        	g.drawLine(860, 250, 1115,250);
        else if(menu.getIsSecond())
        	g.drawLine(940, 330, 1030, 330);
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_DOWN)
		{
			menu.setSecond(true);
			menu.setFirst(false);
		}
		if(key == KeyEvent.VK_UP)
		{
			menu.setSecond(false);
			menu.setFirst(true);
		}
		if(key == KeyEvent.VK_ENTER)
		{
			processEnterPressed();
		}
		menu.setRendering(true);
	}

	private void processEnterPressed()
	{
		if(menu.getIsFirst())
		{
			GameStateManager.gameState = GameStateManager.State.MENU2;
		}
		else
			System.exit(0);
	}

	public void keyReleased(KeyEvent e)
	{
		menu.setRendering(false);
	}

}
