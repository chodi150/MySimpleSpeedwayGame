package model.menu_models;

public class WelcomeMenuModel {

	boolean first = true;
	boolean second = false;



	boolean rendering = true;


	public void setFirst(boolean first)
	{
		this.first = first;
	}
	public boolean getIsFirst()
	{
		return first;
	}
	public void setSecond(boolean second)
	{
		this.second = second;
	}
	public boolean getIsSecond()
	{
		return second;
	}
	public boolean getIsRendering() {
		return rendering;
	}

	public void setRendering(boolean rendering) {
		this.rendering = rendering;
	}

	

}
