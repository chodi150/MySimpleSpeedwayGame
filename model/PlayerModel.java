package model;

public class PlayerModel extends RiderModel {

	private double outsideBonus = 0;
	private double technique = 0;

	private boolean isFinished = false;
	
	public PlayerModel(double x, double y, double maxSpeed, double acceleration, double bonus, double technique) {
		super(x, y, maxSpeed, acceleration);
		outsideBonus = bonus;
		this.technique = technique;
	}

	public double getOutsideBonus()
	{
		return outsideBonus;
	}

	public double getTechnique() {
		return technique;
	}


	public boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(boolean finished) {
		isFinished = finished;
	}


}
