package model;

public class EnemyModel extends RiderModel{
	public EnemyModel(double x, double y, double maxSpeed, double acceleration) {
		super(x, y, maxSpeed, acceleration);
	}
	

	public void deleteAcceleration()
	{
		acceleration =0;
	}

}
