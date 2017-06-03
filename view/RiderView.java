package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import model.RiderModel;

abstract class RiderView {

	RiderModel rider;
	private Line2D line;

	RiderView( RiderModel p)
	{
		rider = p;

	}
	void render(Graphics g, int c)
	{

		switch(c)
		{
			case 1: 	g.setColor(Color.BLACK); break;
			case 2: 	g.setColor(Color.RED); break;
			case 3: 	g.setColor(Color.BLUE); break;
			case 4: 	g.setColor(Color.GREEN); break;
			case 5: 	g.setColor(Color.YELLOW); break;
		}
        Graphics2D g2d = (Graphics2D) g;
        ((Graphics2D) g).setStroke(new BasicStroke(4f));
        int x = (int)rider.getX();
        int y = (int)rider.getY();   
        int secY =  (int)rider.getSecY();
        int secX = (int)rider.getSecX();
        line = new Line2D.Double(x,y,secX,secY);
		g2d.draw(line);

	}
	
	void showSpeed(Graphics g)
	{
		g.setColor(Color.BLACK);
		double speed = rider.getSpeed()* 22; 
		DecimalFormat df = new DecimalFormat("#.00");
		g.drawString(String.valueOf("Your speed "  + df.format(speed))  + "Km/h", 50, 50);
	}
	void ShowLaps(Graphics g)
	{
		int lap = rider.getLap();
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf("Your laps: "  + lap )+ "/4", 50, 100);
	}


}
