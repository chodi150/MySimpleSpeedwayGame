package controller;

import java.util.Random;

import generator.QuantGenerator;
import model.EnemyModel;
import model.menu_models.ResultsModel;

class EnemyController extends RiderController {
	private EnemyModel enemy;
	private Rectangle rectanglesOnRightCorner[];
	private Rectangle rectanglesOnLeftCorner[];
	private int inWhichRectangleIsEnemy = 0;
	private double angle =0;
	private double dist=0;
	private double quantOfAngle = 0;
	private double lastAngle = 0;
	private QuantGenerator generator;

	private class Rectangle
	{
		private int x;
		private int y;
		private int height;
		private int width;
		private Rectangle(int x,int y,int h,int w)
		{
			this.x = x;
			this.y = y;
			this.height = h;
			this.width = w;
		}
		private boolean isEnemyInsideRectangle(EnemyModel enemy)
		{
			double enemyX = enemy.getX();
			double enemyY = enemy.getY();
			if(width>0)
			{
				return (x<enemyX && (x+width) > enemyX && y<enemyY && (y+height)>enemyY);
			}
			else
			{
				return (x>enemyX && (x+width) < enemyX && y<enemyY && (y+height)>enemyY);

			}

		}
		private double calculateAngleToNextRectangle(EnemyModel enemy)
		{
			double enemyX = enemy.getX();
			double enemyY = enemy.getY();
			double angle;
			double deltaX = x-enemyX;
			double deltaY = enemyY - y;
			angle = Math.atan(Math.abs(deltaY/deltaX));
			angle = Math.toDegrees(angle);
			if(deltaX>0 && deltaY<0 )
			{
			 	angle =angle+10;
			}
			if(deltaX<0)
			{
				angle = (180-angle);
			}
			if(deltaY<0)
			{
				angle = 360-angle;
			}
			return angle;
		}
		
		private double calculateDistanceToNextRectangle()
		{
			double enemyX = enemy.getX();
			double enemyY = enemy.getY();
			double deltaX = x-enemyX;
			double deltaY = enemyY - y;
			return Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2));
		}

	}


	EnemyController(EnemyModel e, QuantGenerator generator)
	{
		super(e);
		this.generator = generator;
		enemy = e;
		rectanglesOnRightCorner = new Rectangle[6];
		rectanglesOnRightCorner[0] = new Rectangle(910,488, 20, 300);
		rectanglesOnRightCorner[1] = new Rectangle(974,415, 20, 300);
		rectanglesOnRightCorner[2] = new Rectangle(1005,319, 20, 300);
		rectanglesOnRightCorner[3] = new Rectangle(966,212, 20, 300);
		rectanglesOnRightCorner[4] = new Rectangle(934,150, 20, 300);
		rectanglesOnRightCorner[5] = new Rectangle(888,118, 20, 300);
		
		rectanglesOnLeftCorner = new Rectangle[6];
		rectanglesOnLeftCorner[0] = new Rectangle(330,134, 20, -300);
		rectanglesOnLeftCorner[1] = new Rectangle(224,185, 20, -300);
		rectanglesOnLeftCorner[2] = new Rectangle(195,285, 20, -300);
		rectanglesOnLeftCorner[3] = new Rectangle(205,370, 20, -300);
		rectanglesOnLeftCorner[4] = new Rectangle(231,422, 20, -300);
		rectanglesOnLeftCorner[5] = new Rectangle(300,470, 20, -300);



	}
	void tick()
	{
		setCoordinatesOfEnemy();
		if(enemy.getIsStarted())
		{
			resetRectangle();
			enemy.resetAngle();
			enemyIsAccelerating();
			getReadyToTurning();
			enemyStartsTurningOnRightCorner();
			turnOnRightCorner();
			enemyStopsTurningOnRightCorner();
			straightenUpTrack();
			enemyStartsTurningOnLeftCorner();
			turnOnLeftCorner();
			enemyStopsTurningOnLeftCorner();
			riderMadeItToNextLap();
			stopWhenFinished4Lap();
			straightenDownTrack();
			checkExclusion();
			stopEnemyWhenGetsExcluded();
		}
	}

	private void checkExclusion() {
		riderHitsStraightBands();
		riderGoesOutside();
		riderGoesInside();
	}

	private void setCoordinatesOfEnemy() {
		enemy.setSecX((enemy.getX()-(20*Math.cos(Math.toRadians(enemy.getAngle())))));
		enemy.setSecY((enemy.getY()+(20*Math.sin(Math.toRadians(enemy.getAngle())))));
		enemy.setX(enemy.getX() + enemy.getSpeed()*Math.cos(Math.toRadians(enemy.getAngle())));
		enemy.setY(enemy.getY()-enemy.getSpeed()*Math.sin(Math.toRadians(enemy.getAngle())));
	}


	private void stopWhenFinished4Lap()
	{
		if(enemy.getLap()==5)
		{
			enemy.setSpeed(2);
			enemy.deleteAcceleration();
			ResultsModel.position++;
			enemy.nextLap();
		}
	}
	private void resetRectangle()
	{
		if(inWhichRectangleIsEnemy==6)
			inWhichRectangleIsEnemy=0;
	}

	private void enemyIsAccelerating()
	{
		if(enemy.getSpeed()!=0)
			enemy.setSpeed(enemy.getSpeed()+0.01 + enemy.getAcceleration()/(2*enemy.getSpeed()));
		else
			enemy.setSpeed(enemy.getAcceleration()); 
	}
	private void enemyStartsTurningOnRightCorner()
	{

		if(enemyIsOnTheBeginningOfRightCorner())
		{
			enemy.setIsTurning(true);
			angle = rectanglesOnRightCorner[inWhichRectangleIsEnemy].calculateAngleToNextRectangle(enemy)-3;
			dist = rectanglesOnRightCorner[inWhichRectangleIsEnemy].calculateDistanceToNextRectangle();
		}
	}

	private boolean enemyIsOnTheBeginningOfRightCorner() {
		return enemy.getX()>773&& enemy.getX()<783 && enemy.getY()>400;
	}

	private void turnOnRightCorner()
	{
		if(enemyIsOnTheRightCorner())
		{
			if(inWhichRectangleIsEnemy<6)
				AIofEnemyOnTheCorner(rectanglesOnRightCorner);
			if(enemy.getIsTurning())
				smoothenTurning();
		}
		
	}

	private boolean enemyIsOnTheRightCorner() {
		return enemy.getX()>720;
	}

	private void enemyStopsTurningOnRightCorner()
	{
		if(enemyIsInTheEndOfRightCorner())
		{
			enemy.setIsTurning(false);
			inWhichRectangleIsEnemy = 0;
			lastAngle = 0;
		}
	}

	private boolean enemyIsInTheEndOfRightCorner() {
		return enemy.getX()>850 && enemy.getX()<856 && enemy.getY()<400;
	}


	private void enemyStartsTurningOnLeftCorner()
	{
		if(enemyIsOnTheBeginningOfTheLeftCorner())
		{
			enemy.setIsTurning(true);
			angle = rectanglesOnLeftCorner[inWhichRectangleIsEnemy].calculateAngleToNextRectangle(enemy)-3;
			dist = rectanglesOnLeftCorner[inWhichRectangleIsEnemy].calculateDistanceToNextRectangle();
		}
	}

	private boolean enemyIsOnTheBeginningOfTheLeftCorner() {
		return enemy.getX()<420 && enemy.getX()>410 && enemy.getY()<400;
	}

	private void turnOnLeftCorner()
	{
		if(enemyIsOnLeftCorner())
		{

			if(inWhichRectangleIsEnemy<6)
				AIofEnemyOnTheCorner(rectanglesOnLeftCorner);

			if(enemy.getIsTurning())
				smoothenTurning();
		}

	}

	private boolean enemyIsOnLeftCorner() {
		return enemy.getX()<500;
	}

	private void AIofEnemyOnTheCorner(Rectangle[] rectanglesOnTheCorner)
	{
		if(rectanglesOnTheCorner[inWhichRectangleIsEnemy].isEnemyInsideRectangle(enemy))
		{
			inWhichRectangleIsEnemy++;
			if(inWhichRectangleIsEnemy==6) return;
			lastAngle = angle;
			angle =rectanglesOnTheCorner[inWhichRectangleIsEnemy].calculateAngleToNextRectangle(enemy)-1;
			dist = rectanglesOnTheCorner[inWhichRectangleIsEnemy].calculateDistanceToNextRectangle();
		}
	}
		
	private void enemyStopsTurningOnLeftCorner()
	{
		if(enemyIsInTheEndOfLeftCorner())
		{
			enemy.setIsTurning(false);
			inWhichRectangleIsEnemy = 0;
			lastAngle = 0;
		}
	}

	private boolean enemyIsInTheEndOfLeftCorner() {
		return enemy.getX()>300 && enemy.getX()<307 && enemy.getY()>400;
	}


	private void smoothenTurning()
	{

		double enemyAngle;
		quantOfAngle = (angle-lastAngle)/dist + 2;
		quantOfAngle -= generator.generate();

		if(enemy.getAngle()<angle)
		{
			if(playerHasToTurnWithBigAngleAndRidesTooFast())
			{
				enemy.setSpeed(enemy.getSpeed()-0.07);
			}
			enemyAngle=enemy.getAngle();
			enemyAngle +=quantOfAngle;
			enemy.setAngle(enemyAngle);
		}
	}

	private boolean playerHasToTurnWithBigAngleAndRidesTooFast() {
		return quantOfAngle>1.5 && enemy.getSpeed()>3.5;
	}

	private void stopEnemyWhenGetsExcluded()
	{
		if(enemy.getIsExcluded())
		{
			enemy.setIsStarted(false);
		}
	}

	private void straightenUpTrack()
	{
		if(isEnemyOnUpStraightAndNotTurning())
		{
			if(enemyEndedRightCornerWithTooBigAngle())
			{
				enemy.setAngle(enemy.getAngle()-1.5);
			}
			else
			{
				enemy.setAngle(enemy.getAngle()+1.5);
			}
		}
	}

	private boolean enemyEndedRightCornerWithTooBigAngle() {
		return enemy.getAngle()>180;
	}

	private boolean isEnemyOnUpStraightAndNotTurning() {
		return !enemy.getIsTurning() && enemy.getY()<400 && enemy.getX()>580;
	}

	private void straightenDownTrack()
	{
		if(isEnemyOnDownStraightAndNotTurning())
		{
			if(enemyEndedLeftCornerWithTooBigAngle())
			{
				enemy.setAngle(enemy.getAngle()-1.5);
			}
			else if(enemyEndedLeftCornerWithTooLittleAngle())
			{
				enemy.setAngle(enemy.getAngle()+1.5);
			}
		}
	}

	private boolean enemyEndedLeftCornerWithTooLittleAngle() {
		return enemy.getAngle()<=360 && enemy.getAngle() > 180;
	}

	private boolean enemyEndedLeftCornerWithTooBigAngle() {
		return enemy.getAngle()>3 && enemy.getAngle()< 100;
	}

	private boolean isEnemyOnDownStraightAndNotTurning() {
		return !enemy.getIsTurning() && enemy.getY()>400 && enemy.getX()<580;
	}


	private void getReadyToTurning()
	{
		double x = enemy.getX();
		double y = enemy.getY();
		if(y>525 && x>580 && !enemy.getIsTurning() && enemy.getAngle() < 8)
		{
			enemy.setAngle(enemy.getAngle()+0.1);
			lastAngle = enemy.getAngle();

		}
		if(y<70 && x<580 && !enemy.getIsTurning() && enemy.getAngle() < 188)
		{
			enemy.setAngle(enemy.getAngle()+0.1);
			lastAngle = enemy.getAngle();
		}

	}

	
/*** ONLY FOR TESTING PURPOSES */////

	 int getRectangle()
{
	return inWhichRectangleIsEnemy;
}
	double getQuantOfAngle() {
		return quantOfAngle;
	}

}
