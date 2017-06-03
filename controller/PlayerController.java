package controller;




import model.PlayerModel;


class PlayerController extends RiderController
{

	private PlayerModel player;
	private final double MAX_SPEED = 5.5;


	PlayerController(PlayerModel p)
	{
		super(p);
		player =p;
	}

	 void tick()
	{
		player.resetAngle();
		turnRight();
		turnLeft();
		accelerate();
		riderHitsStraightBands();
		riderGoesInside();
		riderGoesOutside();
		riderMadeItToNextLap();
		playerFinishes();
		playerGetsOutsideBonus();
		updatePlayerCoordinates();
	}

	private void turnRight() {
		if(player.getIsRight())
            player.setAngle(player.getAngle()-2);  //simple formula, speedway is not about turning right
	}

	private void accelerate() {
		if(player.getIsUp())
				playerIsAccelerating();
		else
			playerIsNotAccelerating();
	}

	private void turnLeft() {
		if(player.getIsLeft())
        {
            player.setIsTurning(true);
            playerIsTurning();
        }
        else
            player.setIsTurning(false);
	}

	private void updatePlayerCoordinates()
	{
		player.setSecX((player.getX()-(20*Math.cos(Math.toRadians(player.getAngle())))));
		player.setSecY((player.getY()+(20*Math.sin(Math.toRadians(player.getAngle())))));

		player.setX(player.getX() + player.getSpeed()*Math.cos(Math.toRadians(player.getAngle())));
		player.setY(player.getY()-player.getSpeed()*Math.sin(Math.toRadians(player.getAngle())));
	}

	private void playerIsTurning()
	{
		double angle = player.getAngle();
		angle = calculateAngle(angle);
		player.setAngle(angle);
	}

	private double calculateAngle(double angle) {
		if(player.getIsTurning())
			angle+=(MAX_SPEED /2 + player.getTechnique() - player.getSpeed()/2);
		 else
			angle+=4;
		return angle;
	}

	private void playerGetsOutsideBonus()
	{
		if(playerIsGoingCloseToFenceOnTheCorner())
			player.setAngle(player.getAngle()+player.getOutsideBonus());

	}

	private boolean playerIsGoingCloseToFenceOnTheCorner() {
		return player.getX()>1055 || player.getX()< 125;
	}

	private void playerIsAccelerating()
	{
		if(player.getSpeed()!=0)
			player.setSpeed(player.getSpeed() + player.getAcceleration()/(2*player.getSpeed())); //the faster player goes the slowest acceleration
		else //to avoid dividing by 0
			player.setSpeed(player.getAcceleration());

	}
	private void playerIsNotAccelerating()
	{
		if(player.getSpeed()>0.5)
			player.setSpeed(player.getSpeed() -0.02);
	}

	private void playerFinishes()
	{
		if(player.getLap()==5)
			player.setIsFinished(true);
	}

	


	


	
	


}
