package view;

import controller.GameController;
import main.GameStateManager;
import model.EnemyModel;
import model.PlayerModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class GameView {


    private PlayerView player_view;
    private PlayerModel player_model;
    private EnemyView enemy_view;
    private EnemyView enemy_view2;
    private EnemyView enemy_view3;
    private BufferedImage background;
    private long timer;

    public GameView(GameController controller) {
        EnemyModel enemy_model = controller.getEnemyModel(0);
        EnemyModel enemy_model2 = controller.getEnemyModel(1);
        EnemyModel enemy_model3 = controller.getEnemyModel(2);
        player_model = controller.getPlayerModel();

        enemy_view = new EnemyView(enemy_model);
        enemy_view2 = new EnemyView(enemy_model2);
        enemy_view3 = new EnemyView(enemy_model3);
        player_view = new PlayerView(player_model);

        timer = System.currentTimeMillis();

        try {
            background = ImageIO.read(getClass().getResource("/background.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null);
        countDownToStart(g);
        player_view.render(g, 4);
        enemy_view.render(g, 3);
        enemy_view2.render(g, 2);
        enemy_view3.render(g, 1);


        player_view.showSpeed(g);
        player_view.ShowLaps(g);


        if (!player_model.getIsStarted())
            player_view.showWhoIsYourPlayer(g);

    }

    private void countDownToStart(Graphics g) {
        if (System.currentTimeMillis() - timer < 1000) {
            g.setColor(Color.RED);
            g.fillOval(580, 520, 15, 15);
            g.fillOval(580, 540, 15, 15);
            g.fillOval(580, 560, 15, 15);
        }
        if (System.currentTimeMillis() - timer > 1000 && System.currentTimeMillis() - timer < 1700) {
            g.setColor(Color.RED);
            g.fillOval(580, 520, 15, 15);
            g.fillOval(580, 540, 15, 15);
        }
        if (System.currentTimeMillis() - timer > 1700 && System.currentTimeMillis() - timer < 2500) {
            g.setColor(Color.RED);
            g.fillOval(580, 520, 15, 15);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE)
            GameStateManager.gameState = GameStateManager.State.MENU;

        player_view.keyPressed(e);
    }

    public void keyReleased(KeyEvent e)
    {
        player_view.keyReleased(e);
    }

}
