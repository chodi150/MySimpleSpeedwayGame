package view;
import java.awt.*;
import java.awt.event.KeyEvent;
import model.PlayerModel;

class PlayerView extends RiderView {


	PlayerView( PlayerModel p)
	{
		super(p);
	}

	 void showWhoIsYourPlayer(Graphics g)
	{
		int x = (int)rider.getX();
		int y = (int)rider.getY();
		g.setColor(Color.green);
		g.drawString("It is your player", x-105,y+4);
	}


	void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_RIGHT)
		{
			rider.setIsRight(true);

		}
		if(key == KeyEvent.VK_LEFT)
		{
			rider.setIsLeft(true);
		}

		if(key == KeyEvent.VK_UP)
		{
			rider.setIsUp(true);
			rider.setIsStarted(true);
		}


	}


	void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_LEFT)
		{
			rider.setIsLeft(false);
		}
		if(key == KeyEvent.VK_UP)
		{
			rider.setIsUp(false);
		}
		if(key == KeyEvent.VK_RIGHT)
		{
			rider.setIsRight(false);
		}


	}

}
