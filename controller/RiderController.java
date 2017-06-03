package controller;

import model.RiderModel;
import java.awt.geom.Line2D;

class RiderController  {

	private static final int RIGHT_END_OF_TRACK = 1158;
	private RiderModel rider;
	private long timer;

	final private int CENTER_Y = 312;
	final private int CENTER_LEFT_CORNER_X = 403;
	final private int CENTER_RIGHT_CORNER_X = 792;
	final private int DOWN_OF_INSIDE = 500;
	final private int UP_OF_INSIDE = 128;
	final private int LEFT_END_OF_TRACK=20;
	final private int UP_FENCE = 30;
	final private int DOWN_FENCE = 596;


	
	RiderController(RiderModel rider)
	{
		this.rider = rider;
	}

	void riderMadeItToNextLap()
	{
		if(riderPassesStartLine())
		{
			if(atLeastThreeSecondsElapesedFromLastPassingStartLine())
			{
				timer = System.currentTimeMillis();
				rider.nextLap();
			}

		}

	}

	private boolean atLeastThreeSecondsElapesedFromLastPassingStartLine() {
		return System.currentTimeMillis()-timer>3000;
	}

	private boolean riderPassesStartLine() {
		return rider.getX()>530 && rider.getX()<540 && rider.getY()>500 && rider.getY() < 900;
	}

	void riderHitsStraightBands()
	{
		double y = rider.getY();
		double speed = rider.getSpeed();
		double angle = rider.getAngle();
		riderHitsDownBand(y, speed, angle);
		riderHitsUpBand(y, speed, angle);

	}

	private void riderHitsUpBand(double y, double speed, double angle) {

		if(y<UP_FENCE)
		{
			if(riderIsRidingTooFastWhileHittingUpBand(speed, angle))
				rider.setIsExcluded(true);
			if(speed>1)
				rider.setSpeed(speed-1);

			rider.setY(UP_FENCE);
			rider.setAngle(183); //bounce a little bit from the band
		}
	}

	private boolean riderIsRidingTooFastWhileHittingUpBand(double speed, double angle) {
		return speed>3.5 && angle <145;
	}

	private void riderHitsDownBand(double y, double speed, double angle) {

		if(y> DOWN_FENCE)
		{
			if(riderIsRidingTooFastWhileHittingDownBand(speed, angle))
				rider.setIsExcluded(true);
			if(speed>1)
				rider.setSpeed(speed-1);

			rider.setY(DOWN_FENCE);
			rider.setAngle(4); //bounce a little bit from the band
		}
	}

	private boolean riderIsRidingTooFastWhileHittingDownBand(double speed, double angle) {
		return speed>3.5 && angle<320 && angle > 0;
	}

	void riderGoesOutside()
	{
		riderHitsRightFence();
		riderHitsLeftFence();
	}

	private void riderHitsRightFence()
	{
		double x = rider.getX();

		if(x>RIGHT_END_OF_TRACK)
		{
			rider.setX(RIGHT_END_OF_TRACK);
			rider.setIsExcluded(true);
		}
		if(riderIsOnRightCorner(x))
		{
			riderHitsRightFenceLowerHalf();
			riderHitsRightFenceUpperHalf();
		}
		}

	private boolean riderIsOnRightCorner(double x) {
		int beginningOfRightCornerX = 876;
		return x>beginningOfRightCornerX;
	}

	private void riderHitsRightFenceUpperHalf()
	{

		double x = rider.getX();
		double y = rider.getY();
		double exclusionCoordinateY;

		if(y<=CENTER_Y)
		{
            exclusionCoordinateY = 312-Math.sqrt(-Math.pow(x, 2) + 1760*x - 694311); //calculated from equation of circle
			if(y<exclusionCoordinateY)
				rider.setIsExcluded(true);
		}

	}
	private void riderHitsRightFenceLowerHalf()
	{
		double x = rider.getX();
		double y = rider.getY();
		double exclusionCoordinateY;

		if(y>CENTER_Y)
		{
			exclusionCoordinateY = Math.sqrt(-Math.pow(x, 2) + 1760*x - 694311) + 312; //calculated from equation of circle
			if(y>exclusionCoordinateY)
				rider.setIsExcluded(true);
		}
	}

	private void riderHitsLeftFence()
	{
		double x = rider.getX();

		if(x<LEFT_END_OF_TRACK)
		{
			rider.setX(LEFT_END_OF_TRACK);
			rider.setIsExcluded(true);
		}

		if(riderIsOnLeftCorner(x))
		{
			riderHitsLeftFenceUpperHalf();
			riderHitsLeftFenceLowerHalf();
		}


	}

	private boolean riderIsOnLeftCorner(double x) {
		int beginningOfLeftCornerX = 300;
		return x< beginningOfLeftCornerX;
	}

	private void riderHitsLeftFenceUpperHalf()
	{
		double x = rider.getX();
		double y = rider.getY();
		double exclusionCoordinateY;
		if(y<=CENTER_Y)
		{
			exclusionCoordinateY = 312-Math.sqrt(-Math.pow(x, 2) + 600*x - 9911);//calculated from equation of circle
			if(y<exclusionCoordinateY)
			{
				rider.setIsExcluded(true);
			}
		}
	}
	private void riderHitsLeftFenceLowerHalf()
	{
		double x = rider.getX();
		double y = rider.getY();
		double exclusionCoordinateY;

		if(y>CENTER_Y)
		{
			exclusionCoordinateY = Math.sqrt(-Math.pow(x, 2) + 600*x - 9911) +312 ; //calculated from equation of circle
			if(y>exclusionCoordinateY)
			{
				rider.setIsExcluded(true);
			}
		}
	}

	void riderGoesInside()
	{

		riderGoesInsideStraight();

		riderGoesInsideRightCorner();

		riderGoesInsideLeftCorner();
	}

	private void riderGoesInsideStraight() {
		double x = rider.getX();
		double y = rider.getY();
		if(x>CENTER_LEFT_CORNER_X && x<CENTER_RIGHT_CORNER_X && y>UP_OF_INSIDE && y<DOWN_OF_INSIDE)
			rider.setIsExcluded(true);

	}

	private void riderGoesInsideRightCorner() {
		double x = rider.getX();
		double y = rider.getY();
		if(x>CENTER_RIGHT_CORNER_X)
		{
			riderGoesInsideRightCornerDown(x, y);
			riderGoesInsideRightCornerUp(x, y);
		}
	}

	private void riderGoesInsideRightCornerUp(double x, double y) {
		double exclusionCoordinateY;
		if(y<CENTER_Y && y> UP_OF_INSIDE)
        {
            exclusionCoordinateY = 312-Math.sqrt(-Math.pow(x, 2) + 1584*x - 592668);//calculated from equation of circle

            if(y>exclusionCoordinateY)
            {
                rider.setIsExcluded(true);
            }
        }
	}

	private void riderGoesInsideRightCornerDown(double x, double y) {
		double exclusionCoordinateY;
		if(y>CENTER_Y && y < DOWN_OF_INSIDE)
        {
            exclusionCoordinateY = Math.sqrt(-Math.pow(x, 2) + 1584*x - 592668) + 312;//calculated from equation of circle
            if(y<exclusionCoordinateY)
            {
                rider.setIsExcluded(true);
            }
        }
	}

	private void riderGoesInsideLeftCorner() {
		double x = rider.getX();
		double y = rider.getY();
		if(x<CENTER_LEFT_CORNER_X)
		{
			riderGoesInsideLeftCornerDown(x, y);
			riderGoesInsideLeftCornerUp(x, y);
		}
	}

	private void riderGoesInsideLeftCornerUp(double x, double y) {
		double exclusionCoordinateY;
		if(y<CENTER_Y && y> UP_OF_INSIDE)
        {
            exclusionCoordinateY = 312-Math.sqrt(-Math.pow(x, 2) + 824*x - 135148);//calculated from equation of circle
            if(y>exclusionCoordinateY)
            {
                rider.setIsExcluded(true);
            }

        }
	}

	private void riderGoesInsideLeftCornerDown(double x, double y) {
		double exclusionCoordinateY;
		if(y<DOWN_OF_INSIDE && y> CENTER_Y)
        {
            exclusionCoordinateY = 312+ Math.sqrt(-Math.pow(x, 2) + 824*x - 135148);//calculated from equation of circle
            if(y<exclusionCoordinateY)
            {
                rider.setIsExcluded(true);
            }
        }
	}


	void collision(RiderModel rider)
	{
		double myX = this.rider.getX();
		double myY= this.rider.getY();
		double mySecX = this.rider.getSecX();
		double mySecY = this.rider.getSecY();
		double otherX = rider.getX();
		double otherY = rider.getY();
		double otherSecX = rider.getSecX();
		double otherSecY = rider.getSecY();
		Line2D line = new Line2D.Double(myX, myY, mySecX, mySecY);
		Line2D line2 = new Line2D.Double(otherX, otherY, otherSecX, otherSecY);
		riderCollisionUpStraight(rider, myX, myY, otherX, line, line2);
		riderCollisionDownStraight(rider, myX, myY, otherX, line, line2);
		riderCollisionRightCorner(rider, myX, myY, otherY, line, line2);
		riderCollisionLeftCorner(rider, myX, myY, otherY, line, line2);
	}

	private void riderCollisionDownStraight(RiderModel rider, double myX, double myY, double otherX, Line2D line, Line2D line2) {
		if(myX>CENTER_LEFT_CORNER_X && myX<CENTER_RIGHT_CORNER_X && myY>DOWN_OF_INSIDE)
		{
			if(ridersCollide(line, line2))
			{
				chooseProperRiderToSlowDownOnDownStraight(rider, myX, otherX); //slow down rider behind
			}
		}
	}

	private void chooseProperRiderToSlowDownOnDownStraight(RiderModel rider, double myX, double otherX) {
		if(myX>otherX && rider.getSpeed()>0.5)
            rider.setSpeed(rider.getSpeed()-0.5);
        else if(myX<otherX && rider.getSpeed()>0.5)
            this.rider.setSpeed(this.rider.getSpeed()-0.5);
	}

	private boolean ridersCollide(Line2D line, Line2D line2) {
		return line.intersectsLine(line2);
	}

	private void riderCollisionUpStraight(RiderModel rider, double myX, double myY, double otherX, Line2D line, Line2D line2) {
		if(myX>CENTER_LEFT_CORNER_X && myX<CENTER_RIGHT_CORNER_X && myY<UP_OF_INSIDE)
		{
			if(ridersCollide(line, line2))
			{
				chooseProperRiderToSlowDownOnUpStraight(rider, myX, otherX);//slow down rider behind
			}
		}
	}

	private void chooseProperRiderToSlowDownOnUpStraight(RiderModel rider, double myX, double otherX) {
		if(myX<otherX && rider.getSpeed()>0.5)
            rider.setSpeed(rider.getSpeed()-0.5);
        else if(myX>otherX && rider.getSpeed()>0.5)
            this.rider.setSpeed(this.rider.getSpeed()-0.5);
	}

	private void riderCollisionLeftCorner(RiderModel rider, double myX, double myY, double otherY, Line2D line, Line2D line2) {
		if(myX<CENTER_LEFT_CORNER_X)
		{
			if(ridersCollide(line, line2))
			{
				chooseProperRiderToSlowDownOnLeftCorner(rider, myY, otherY);//slow down rider behind
			}
		}
	}

	private void chooseProperRiderToSlowDownOnLeftCorner(RiderModel rider, double myY, double otherY) {
		if(myY<otherY && rider.getSpeed()>0.5)
            this.rider.setSpeed(this.rider.getSpeed()-0.5);
        else if(myY>otherY && rider.getSpeed()>0.5)
            rider.setSpeed(rider.getSpeed()-0.5);
	}

	private void riderCollisionRightCorner(RiderModel rider, double myX, double myY, double otherY, Line2D line, Line2D line2) {
		if(myX>CENTER_RIGHT_CORNER_X)
        {
            if(ridersCollide(line, line2))
            {
				chooseProperRiderToSlowDownOnRightCorner(rider, myY, otherY);//slow down rider behind
			}

        }
	}

	private void chooseProperRiderToSlowDownOnRightCorner(RiderModel rider, double myY, double otherY) {
		if(myY>otherY && rider.getSpeed()>0.5)
            this.rider.setSpeed(this.rider.getSpeed()-0.5);
        else if(myY<otherY && rider.getSpeed()>0.5)
            rider.setSpeed(rider.getSpeed()-0.5);
	}


}
