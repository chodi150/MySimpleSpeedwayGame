package view.menu_views;

import main.GameStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ExclusionMenuView {


    private BufferedImage background;


    public ExclusionMenuView()
    {

        try {
            background = ImageIO.read(getClass().getResource("/menu3.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void render(Graphics g)
    {
        g.drawImage(background, 0, 0, null);
    }

    public void keyPressed(KeyEvent e)
    {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_SPACE)
        {
            GameStateManager.gameState = GameStateManager.State.MENU;
        }

    }
}
