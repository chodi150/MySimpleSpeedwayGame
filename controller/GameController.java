package controller;




import generator.QuantGenerator;
import generator.RandomQuantGenerator;
import main.GameStateManager;
import model.EnemyModel;
import model.PlayerModel;



public class GameController{
	private PlayerModel player_model;
	private PlayerController player_controller;
	private EnemyModel  enemy_model;
	private EnemyController enemy_controller;
	private EnemyModel  enemy_model2;
	private EnemyController enemy_controller2;
	private EnemyModel  enemy_model3;
	private EnemyController enemy_controller3;
	private QuantGenerator generator;
	private long timer;
	public static int whichPlayer = 0;
	
	public GameController() {

		switch (whichPlayer) {
			case 0: player_model = new PlayerModel(500, 540, 5.3, 0.12, 0.2, 0.6); break;
			case 1: player_model = new PlayerModel(500, 540, 5.0, 0.15, 0.0, 0.6); break;
			case 2: player_model = new PlayerModel(500, 540, 5.5, 0.09, 0.5, 0.6); break;
			case 3: player_model = new PlayerModel(500, 540, 4.9, 0.13, 0.5, 0.6); break;
			case 4: player_model = new PlayerModel(500, 540, 5.5, 0.11, 0.7, 0.6); break;
			case 5: player_model = new PlayerModel(500, 540, 5.2, 0.10, 0.5, 0.6); break;
		}
		timer = System.currentTimeMillis();
		generator = new RandomQuantGenerator();
		player_controller = new PlayerController(player_model);

		enemy_model = new EnemyModel(500, 550, 4.8, 0.03);
		enemy_controller = new EnemyController(enemy_model, generator);

		enemy_model2 = new EnemyModel(500, 520, 4.5, 0.09);
		enemy_controller2 = new EnemyController(enemy_model2, generator);

		enemy_model3 = new EnemyModel(500, 570, 4.9, 0.08);
		enemy_controller3 = new EnemyController(enemy_model3, generator);

	}

	public void tick()
	{
		if(player_model.getIsExcluded())
		{
			GameStateManager.gameState = GameStateManager.State.MENU3;
			enemy_controller = null;
		}
		else if(player_model.getIsFinished())
		{
			GameStateManager.gameState = GameStateManager.State.RESULTS;
		}
		else
		{
			letEnemiesStart();
			checkIfPlayerTouchesTape();
			enemy_controller.tick();
			player_controller.tick();
			enemy_controller2.tick();
			enemy_controller3.tick();
			player_controller.collision(enemy_model);
			player_controller.collision(enemy_model2);
			player_controller.collision(enemy_model3);
			enemy_controller.collision(enemy_model2);
			enemy_controller.collision(enemy_model3);
			enemy_controller2.collision(enemy_model3);
		}



	}

	private void checkIfPlayerTouchesTape() {

		if(player_model.getIsStarted() && !enemy_model.getIsStarted())
		{
			player_model.setIsExcluded(true);
		}
	}




	private void letEnemiesStart()
	{
		if(System.currentTimeMillis()-timer>2600 && !enemy_model.getIsStarted())
		{
			enemy_model.setIsStarted(true);
			enemy_model2.setIsStarted(true);
			enemy_model3.setIsStarted(true);
		}
	}

	public PlayerModel getPlayerModel()
	{
		return player_model;
	}
	public EnemyModel getEnemyModel(int i)
	{
		switch(i)
		{
			case 0: return enemy_model;
			case 1: return enemy_model2;
			case 2: return enemy_model3;
		}
		return null;
	}



}
