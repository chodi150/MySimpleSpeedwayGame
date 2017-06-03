package model.menu_models;

public class ChoosePlayersMenuModel {
	private int whichPlayer=0;
	private boolean isLeft = false;
	private boolean isRight = false;

	
	public ChoosePlayersMenuModel()
	{}
	
	public boolean getisLeft() {
		return isLeft;
	}

	public void setIsLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean getisRight() {
		return isRight;
	}

	public void setIsRight(boolean isRight) {
		this.isRight = isRight;
	}




	public int getWhichPlayer()
	{
		return whichPlayer;
	}
	public void setWhichPlayer(int nr)
	{
		whichPlayer = nr;
	}

	public void doKeyEvent()
	{
		if(this.getisLeft())
		{
			int nrOfPlayer = this.getWhichPlayer();
			nrOfPlayer--;
			if(nrOfPlayer==-1)
				nrOfPlayer = 5;
			this.setWhichPlayer(nrOfPlayer);
		}

		if(this.getisRight())
		{
			int nrOfPlayer = this.getWhichPlayer();
			nrOfPlayer++;
			if(nrOfPlayer==6)
				nrOfPlayer = 0;
			this.setWhichPlayer(nrOfPlayer);
		}

	}


}
