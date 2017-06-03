package view.menu_views;

import model.menu_models.ResultsModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ResultsView {
    private BufferedImage background;

    public ResultsView()
    {
        try {
            background = ImageIO.read(getClass().getResource("/menu4.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics g)
    {

        g.drawImage(background, 0, 0, null);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(String.valueOf(ResultsModel.position), 600, 350);
    }
}
