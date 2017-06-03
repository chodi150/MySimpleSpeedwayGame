package controller;

import generator.GivenQuantGenerator;
import generator.QuantGenerator;
import generator.RandomQuantGenerator;
import model.EnemyModel;
import org.testng.annotations.Test;
import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;



public class EnemyControllerTest {
    private QuantGenerator generator;

    @Test
    public void GivenEnemyThatNotStarted_WhenSimulatingTheGame_ThenTheSpeedDoesNotIncrease()
    {
        EnemyModel enemy1 = new EnemyModel(500,500, 5, 0.03);
        generator = new RandomQuantGenerator();
        EnemyController controller1 = new EnemyController(enemy1, generator);
        for(int i =0; i<20; i++)
            controller1.tick();
        assertEquals(0.0, enemy1.getSpeed());
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenStarted_ThenStartsToAccelerate()
    {
        EnemyModel enemy1 = new EnemyModel(500,500, 5, 0.03);
        generator = new RandomQuantGenerator();
        EnemyController controller1 = new EnemyController(enemy1, generator);
        enemy1.setIsStarted(true);
        controller1.tick();

        assertEquals(true, enemy1.getSpeed()>0);
    }

    @Test
    public void GivenEnemyOnStartLine_WhenStartsAndGameIsBeingSimulated_ThenHeStartsTurningAfterGettingToSpecifiedPoint()
    {
        EnemyModel enemy1 = new EnemyModel(500,520, 5, 0.03);
        generator = new RandomQuantGenerator();
        EnemyController controller1 = new EnemyController(enemy1, generator);
        enemy1.setSpeed(5);
        enemy1.setIsStarted(true);

        while(!enemy1.getIsTurning())
            controller1.tick();

        assertEquals(true, enemy1.getAngle()>0);
    }


    @Test
    public void GivenEnemyOnStartingLine_WhenGameIsSimulated_ThenHeTurnsOnlyByPositiveQuantum()
    {
        EnemyModel enemy = new EnemyModel(500,520, 4.5, 0.15);
        generator = new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        final int numberOfTicksDuringTheRace = 10000;
        for(int i =0; i<numberOfTicksDuringTheRace; i++)
        {
            controller.tick();
            assertEquals(true, controller.getQuantOfAngle()>=0.0);
        }
    }


    @Test
    public void GivenEnemyOnStartingLine_WhenGameIsSimulated_ThenHeTurnsOnlyByPositiveQuantum2()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.0, 0.08);
        generator = new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        final int numberOfTicksDuringTheRace = 10000;
        for(int i =0; i<numberOfTicksDuringTheRace; i++)
        {
            controller.tick();
            assertEquals(true, controller.getQuantOfAngle()>=0.0);
        }
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenGameIsSimulated_ThenHeTurnsOnlyByPositiveQuantum3()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.3, 0.06);
        generator = new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        final int numberOfTicksDuringTheRace = 10000;
        for(int i =0; i<numberOfTicksDuringTheRace; i++)
        {
            controller.tick();
            assertEquals(true, controller.getQuantOfAngle()>=0.0);
        }
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenGameIsSimulated_ThenHeTurnsOnlyByPositiveQuantum4()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.5, 0.07);
        generator = new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        final int numberOfTicksDuringTheRace = 10000;
        for(int i =0; i<numberOfTicksDuringTheRace; i++)
        {
            controller.tick();
            assertEquals(true, controller.getQuantOfAngle()>=0.0);
        }
    }

    private int enemyFinishesRace(EnemyModel enemy)
    {
        int num = 0;
        generator = new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        for(int i =0; i<3000; i++)
        {
            controller.tick();
            if(enemy.getIsExcluded())
            {
                num++;
                break;
            }
        }
        return num;
    }

    private void loadDifferentModelsOfEnemiesToArray(EnemyModel[] tab)
    {
        Random random = new Random();
        for(int i =0; i<10; i++)
            tab[i] = new EnemyModel(500,520, 4.4 + 0.1*i, 0.15 - 0.01*i);
        for(int i = 10; i<20; i++)
            tab[i] = new EnemyModel(500,540, 5.4 - 0.1*i, 0.15 - (0.01*i)/2);
        for(int i = 20; i< 100; i++)
            tab[i] = new EnemyModel(500,510 + 50 * random.nextDouble(),
                    5.4 - random.nextDouble(), 0.18 - 1.1* random.nextDouble() * 0.1);
    }

@Test
    public void GivenRandomEnemyModels_WhenSimulatingTheGame_ThenLessThanTenPercentGetExcluded()
    {
        int numberOfExclusions = 0;
        EnemyModel[] tab = new EnemyModel[100];
        for(int j = 0; j<10; j++)
        {
            loadDifferentModelsOfEnemiesToArray(tab);
            for(int i =0; i<100; i++)
                numberOfExclusions+=enemyFinishesRace(tab[i]);
        }
        String str = "Nr of fails %d";
        assertTrue(String.format(str, numberOfExclusions), numberOfExclusions<100);
    }

    @Test
    public void GivenTwoEnemies_WhenEnemyBehindAnotherEnemyAndCollideWithHim_ThenHisSpeedIsLower()
    {
        EnemyModel player = new EnemyModel(432,530,5,0.2);
        generator = new RandomQuantGenerator();
        EnemyController c = new EnemyController(player, generator);
        player.setSpeed(5);
        EnemyModel player2 = new EnemyModel(430,530,5,0.2);
        EnemyController c2 = new EnemyController(player2, generator);
        player2.setSpeed(5);
        c.tick();
        c2.tick();
        c2.collision(player);

        assertTrue(player.getSpeed() > player2.getSpeed());
    }




    @Test
    public void GivenEnemyOnLeftCornerThatIsNotTurning_WhenGameIsSimulated_ThenDies(){
         EnemyModel enemy = new EnemyModel(104,234, 5.3, 0.06);
        generator = new RandomQuantGenerator();
         EnemyController controller = new EnemyController(enemy, generator);
         enemy.setAngle(180);
         enemy.setIsStarted(true);
         enemy.setSpeed(5);
         for(int i =0; i<100; i++)
            controller.tick();

        assertEquals(true, enemy.getIsExcluded());
    }

    private boolean checkIfEnemyGetsExcludedWithSpecifiedChangeOfQuant(EnemyController con, EnemyModel mod)
    {

        for(int i = 0; i<3000; i++)
            con.tick();
        return !mod.getIsExcluded();
    }

    private boolean checkIfEnemyGetsToEachRectangleWithSpecifiedChangeOfQuant(EnemyController con)
    {
        int checkerIfGoingThroughCornerDoesNotTakeTooLong = 0;
        while(con.getRectangle()<6)
        {
            con.tick();
            checkerIfGoingThroughCornerDoesNotTakeTooLong++;
            if(checkerIfGoingThroughCornerDoesNotTakeTooLong==600)
                break;
        }
        return con.getRectangle()==6;
    }


    @Test
    public void GivenEnemyThatGoesTheBestRaceLine_WhenGameIsSimulated_ThenHeSurvives()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.5, 0.07);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy,generator);
        enemy.setIsStarted(true);

        assertTrue(checkIfEnemyGetsExcludedWithSpecifiedChangeOfQuant(controller, enemy));
    }

    @Test
    public void GivenEnemyThatGoesTheBestRaceLine_WhenGameIsSimulated_ThenHeGetsToEachRectangle()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.5, 0.07);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);
        assertEquals(true, checkIfEnemyGetsToEachRectangleWithSpecifiedChangeOfQuant(controller));

    }

    @Test
    public void GivenEnemyWithConstantHugeDisturbance_WhenGameIsSimulated_ThenHeDies()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.5, 0.07);
        generator = new GivenQuantGenerator(1.1);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);

        assertEquals(false, checkIfEnemyGetsExcludedWithSpecifiedChangeOfQuant(controller, enemy));
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenFinishesRightTurn_ThenStopsTurning()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.5, 0.07);
        generator = new GivenQuantGenerator(1);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);

        while(!enemy.getIsExcluded() && enemy.getX() > 500)
        {
            controller.tick();
        }
        assertEquals(false, enemy.getIsTurning());
    }

    @Test
    public void GivenEnemyOnDownStraight_WhenApproachingDownFence_ThenHeCorrectsHisLineAndManagesToGetToCorner()
    {
        EnemyModel enemy1 = new EnemyModel(500,520, 5, 0.03);
        generator = new RandomQuantGenerator();
        EnemyController controller1 = new EnemyController(enemy1, generator);
        enemy1.setAngle(340);
        enemy1.setIsStarted(true);

        while(!enemy1.getIsTurning())
        {
            controller1.tick();
        }

        assertEquals(true, (enemy1.getAngle()>0 && enemy1.getAngle()< 20));
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenReachesHalfOfRightCorner_ThenHisSpeedIsLowerThenMaximal()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.0, 0.15);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setSpeed(5.0);
        enemy.setIsStarted(true);

        while(controller.getRectangle()!=3)
            controller.tick();

        assertEquals(true, enemy.getSpeed()<4.5);
    }

    @Test
    public void GivenEnemyOnStartingLine_WhenReachesHalfOfLeftCorner_ThenHisSpeedIsLowerThenMaximal()
    {
        EnemyModel enemy = new EnemyModel(500,80, 5.0, 0.15);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setAngle(180);
        enemy.setSpeed(5.0);
        enemy.setIsStarted(true);


        while(controller.getRectangle()!=3)
            controller.tick();

        assertEquals(true, enemy.getSpeed()<4.5);
    }

    @Test
    public void GivenTwoTheSameEnemiesWithDifferentDisturbances_WhenBothOnTheRightCorner_ThenTheOneWithMoreDisturbancesGoesOutside()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.0, 0.2);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);

        EnemyModel enemy2 = new EnemyModel(500,520, 5.0, 0.2);
        generator = new GivenQuantGenerator(0.9);
        EnemyController controller2 = new EnemyController(enemy2, generator);
        enemy2.setIsStarted(true);

        while(controller.getRectangle()!=3)
            controller.tick();
        while(controller2.getRectangle()!=3)
            controller2.tick();


        assertTrue(enemy2.getX()>enemy.getX());
    }

    @Test
    public void GivenTwoTheSameEnemiesWithDifferentDisturbances_WhenBothOnTheRightCorner_ThenTheOneOnTheOutsideIsFaster()
    {
        EnemyModel enemy = new EnemyModel(500,520, 5.0, 0.2);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setIsStarted(true);

        EnemyModel enemy2 = new EnemyModel(500,520, 5.0, 0.2);
        generator = new GivenQuantGenerator(0.9);
        EnemyController controller2 = new EnemyController(enemy2,generator);
        enemy2.setIsStarted(true);

        while(controller.getRectangle()!=3)
            controller.tick();
        while(controller2.getRectangle()!=3)
            controller2.tick();

        assertTrue(enemy2.getSpeed()>enemy.getSpeed());
    }


    @Test
    public void GivenEnemyOnTheCorner_WhenGameIsSimulated_ThenStopsTurning()
    {
        EnemyModel enemy = new EnemyModel(500,80, 5.0, 0.15);
        generator = new GivenQuantGenerator(0);
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setAngle(180);
        enemy.setSpeed(5.0);
        enemy.setIsStarted(true);
        enemy.setIsTurning(true);

        while(enemy.getX()<550)
            controller.tick();
        assertEquals(false, enemy.getIsTurning());
    }

    @Test
    public void GivenEnemyCloseToDownFence_WhenApproachingTheCorner_ThenPreparesToTurning()
    {
        EnemyModel enemy = new EnemyModel(400,590, 5.0, 0.15);
        generator=new RandomQuantGenerator();
        EnemyController controller = new EnemyController(enemy, generator);
        enemy.setAngle(0);
        enemy.setSpeed(5.0);
        enemy.setIsStarted(true);

        while(!enemy.getIsTurning())
            controller.tick();

        assertEquals(true,  enemy.getAngle()>0);
    }

}