package controller;

import model.PlayerModel;
import org.testng.annotations.Test;


import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class PlayerControllerTest {
    @Test
    public void GivenPlayer_WhenGoesStraightToDownFence_ThenDies() {
        PlayerModel player = new PlayerModel(500,570,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(270);
        player.setIsUp(true);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertEquals(true, player.getIsExcluded());
    }

    @Test
    public void GivenPlayer_WhenGoesStraightToUpFence_ThenDies() {
        PlayerModel player = new PlayerModel(520,60,5,0.05,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(90);
        player.setIsUp(true);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertTrue(player.getIsExcluded());
    }
    @Test
    public void GivenPlayerWithLittleAngle_WhenHitsStraightDownBand_ThenSurvives() {
        PlayerModel player = new PlayerModel(430,580,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(330);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertEquals(false, player.getIsExcluded());
    }

    @Test
    public void GivenPlayerWithLittleAngle_WhenHitsStraightUpBand_ThenSurvives() {
        PlayerModel player = new PlayerModel(570,40,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(150);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertEquals(false, player.getIsExcluded());
    }

    @Test
    public void GivenPlayerWithLittleAngle_WhenHitsStraightDownBand_ThenBouncesOffTheBand() {
        PlayerModel player = new PlayerModel(430,580,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(330);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertEquals(true, player.getAngle()>0);
    }

    @Test
    public void GivenPlayerWithLittleAngle_WhenHitsStraightUpBand_ThenBouncesOffTheBand() {
        PlayerModel player = new PlayerModel(570,40,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(155);
        for(int i = 0; i< 10; i++)
        {
            c.tick();
        }
        assertEquals(true, player.getAngle()>180);
    }


    @Test
    public void GivenPlayerWithLittleSpeed_WhenHitsStraightDownBand_ThenSurvives() {
        PlayerModel player = new PlayerModel(430,580,5,0.02,0, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(3.4);
        player.setAngle(270);
        for(int i = 0; i< 50; i++)
        {
            c.tick();
        }
        assertEquals(false, player.getIsExcluded());
    }

    @Test
    public void GivenUserHoldingUpArrow_WhenGameSimulated_ThenPlayerMoves() {
        PlayerModel player = new PlayerModel(430,580,5,0.1,0, 0.5);
        PlayerController c = new PlayerController(player);

        for(int i = 0; i< 5; i++)
        {
            player.setIsUp(true);
            c.tick();
        }
        assertEquals(true, (int)Math.ceil(player.getX())>430);
    }


    @Test
    public void GivenUserNotHoldingUpArrow_WhenGameSimulated_ThenPlayerDoesNotMove() {
        PlayerModel player = new PlayerModel(430,580,5,0.1,0, 0.5);
        PlayerController c = new PlayerController(player);

        for(int i = 0; i< 5; i++)
        {
            player.setIsUp(false);
            c.tick();
        }
        assertEquals(true, player.getX()==430);
    }
    @Test
    public void GivenTwoPlayersWithDifferentAccelerations_WhenBothStartRacing_ThenPlayerWithBetterAccelerationIsFaster ()
    {
        double acceleration = 0.2;
        PlayerModel player = new PlayerModel(430,530,5,acceleration,1, 0.5);
        PlayerController c = new PlayerController(player);
        double acceleration1 = 0.3;
        PlayerModel player2 = new PlayerModel(430,530,5,acceleration1,1, 0.5);
        PlayerController c2 = new PlayerController(player2);


        for(int i = 0; i< 10; i++)
       {
           player.setIsUp(true);
           player2.setIsUp(true);
           c2.tick();
           c.tick();
       }

       assertEquals(true, player.getSpeed()<player2.getSpeed());
    }

    @Test
    public void GivenPlayerOnTheCorner_WhenNotTurning_ThenDies ()
    {
        PlayerModel player = new PlayerModel(1003,319,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setIsUp(true);
        for(int i = 0; i< 40; i++)
            c.tick();

        assertEquals(true,player.getIsExcluded());
    }
    @Test
    public void GivenPlayer_WhenUpArrowNotPressed_ThenSlowsDown ()
    {
        PlayerModel player = new PlayerModel(1003,319,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        c.tick();

        assertEquals(true,player.getSpeed()<5);
    }

    @Test
    public void GivenPlayerOnStartLine_WhenGoesInside_ThenIsExcluded ()
    {
        PlayerModel player = new PlayerModel(430,530,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(90);
        int numberOfTicksToGetToTheInside = 40;
        for(int i = 0; i< numberOfTicksToGetToTheInside; i++)
            c.tick();

        assertEquals(true, player.getIsExcluded());
    }

    @Test
    public void GivenTwoPlayers_WhenOneIsSlowerThanAnother_ThenTheSlowerOneCanTurnLeftWithBiggerAngle()
    {
        PlayerModel player = new PlayerModel(430,530,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);

        PlayerModel player2 = new PlayerModel(430,530,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);

        player.setSpeed(5);
        player.setAngle(0);
        player.setIsLeft(true);
        c.tick();
        double angleOfFasterOne = player.getAngle();

        player2.setSpeed(4);
        player2.setAngle(0);
        player2.setIsLeft(true);
        c2.tick();
        double angleOfSlowerOne = player2.getAngle();

        assertEquals(true, angleOfSlowerOne>angleOfFasterOne);

    }

    @Test
    public void GivenTwoPlayersOneBehindAnotherOnDownStraight_WhenColliding_ThenTheOneBehindSlowsDown()
    {
        PlayerModel player = new PlayerModel(432,530,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        PlayerModel player2 = new PlayerModel(430,530,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();

        c.collision(player2);  //The same result for c2.collision(player)

        assertTrue(player.getSpeed()>player2.getSpeed());

    }

    @Test
    public void GivenTwoPlayersOneBehindAnotherOnDownStraight_WhenColliding_ThenTheOneInFrontIsNotAffected()
    {
        PlayerModel player = new PlayerModel(432,530,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        PlayerModel player2 = new PlayerModel(430,530,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();

        c.collision(player2);  //The same result for c2.collision(player)

        assertTrue(player.getSpeed()==5);

    }

    @Test
    public void GivenTwoPlayersOneBehindAnotherOnRightCorner_WhenColliding_ThenTheOneInFrontIsNotAffected()
    {
        PlayerModel player = new PlayerModel(1015,398,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(90);
        PlayerModel player2 = new PlayerModel(1015,400,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player2.setAngle(90);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();

        c.collision(player2);  //The same result for c2.collision(player)

        assertTrue(player.getSpeed()==5);

    }


    @Test
    public void GivenTwoPlayersOneBehindAnotherOnRightCorner_WhenColliding_ThenTheOneBehindSlowsDown()
    {
        PlayerModel player = new PlayerModel(1100,400,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(90);
        PlayerModel player2 = new PlayerModel(1100,398,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();


        c.collision(player2);//The same result for c2.collision(player)

        assertEquals(true,player2.getSpeed()<player.getSpeed());

    }


    @Test
    public void GivenTwoPlayersOneBehindAnotherOnLeftCorner_WhenColliding_ThenTheOneInFrontIsNotAffected()
    {
        PlayerModel player = new PlayerModel(150,400,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(270);
        PlayerModel player2 = new PlayerModel(150,398,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player2.setAngle(270);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();

        c.collision(player2);  //The same result for c2.collision(player)

        assertTrue(player.getSpeed()==5);

    }


    @Test
    public void GivenTwoPlayersOneBehindAnotherOnLeftCorner_WhenColliding_ThenTheOneBehindSlowsDown()
    {
        PlayerModel player = new PlayerModel(150,400,5,0.2,1, 0.5);
        PlayerController c = new PlayerController(player);
        player.setSpeed(5);
        player.setAngle(270);
        PlayerModel player2 = new PlayerModel(150,398,5,0.2,1, 0.5);
        PlayerController c2 = new PlayerController(player2);
        player2.setSpeed(5);
        player2.setAngle(270);
        player.setIsUp(true);
        player2.setIsUp(true);
        c.tick();
        c2.tick();


        c.collision(player2);//The same result for c2.collision(player)

        assertEquals(true,player2.getSpeed()<player.getSpeed());

    }


}