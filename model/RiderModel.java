package model;

abstract public class RiderModel {
	
	protected double x;
	protected double y;

	private double secX=0;
	private double secY=0;
	private double angle = 0;
	
	private double speed = 0;
	 double acceleration = 0;
	
	private boolean isTurning = false;
	private boolean isUp = false;
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isExcluded = false;
	private int lap=0;


	private boolean isStarted = false;

	private final double MAX_SPEED;
	
	public RiderModel(double x, double y, double maxSpeed, double acceleration)
	{
		this.x = x;
		this.y = y;
		MAX_SPEED = maxSpeed;
		this.acceleration = acceleration;
	}
	
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX(double x)
	{
		this.x=x;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public void setSpeed(double speed)
	{
		if(speed<=MAX_SPEED)
			this.speed = speed;
	}
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	public double getAngle()
	{
		return angle;
	}
	public boolean getIsTurning()
	{
		return isTurning;
	}
	public void setIsTurning(boolean isTurning)
	{
		this.isTurning = isTurning;
	}
	public double getAcceleration()
	{
		return acceleration;
	}
	
	public double getSpeed()
	{
		return speed;
	}

	public boolean getIsUp()
	{
		return isUp;
	}
	public void setIsUp(boolean isUp)
	{
		this.isUp = isUp;
	}
	
	public boolean getIsLeft()
	{
		return isLeft;
	}
	public void setIsLeft(boolean isLeft)
	{
		this.isLeft = isLeft;
	}
	public boolean getIsRight()
	{
		return isRight;
	}
	public void setIsRight(boolean isRight)
	{
		this.isRight = isRight;
	}
	public double getSecX() {
		return secX;
	}

	public void setSecX(double secX) {
		this.secX = secX;
	}

	public double getSecY() {
		return secY;
	}

	public void setSecY(double secY) {
		this.secY = secY;
	}
	public boolean getIsExcluded() {
		return isExcluded;
	}

	public void setIsExcluded(boolean isExcluded) {
		this.isExcluded = isExcluded;
	}
	public boolean getIsStarted() {
		return isStarted;
	}

	public void setIsStarted(boolean started) {
		isStarted = started;
	}

	public int getLap()
	{
		return lap;
	}
	public void nextLap()
	{
		lap++;
	}

	public void resetAngle()
	{
		if(angle>=360)
			angle%=360;

		if(angle<0)
			angle+=360;
	}

}
