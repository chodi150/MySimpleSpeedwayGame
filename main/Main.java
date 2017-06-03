package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Main extends Canvas implements Runnable {
    private static final int WIDTH = 1171;
    private static final int HEIGHT = 629;
    private final String TITLE = "Pro Evolution Speedway 2K17";
    private boolean running = false;
    private Thread thread;
    private GameStateManager gsm;

    void init()
    {
        requestFocus();
        gsm = new GameStateManager();
        addKeyListener(gsm);
    }

    private synchronized void start()
    {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }


    private synchronized void stop()
    {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        performGameLoop(lastTime, ns, delta, timer);
        stop();
    }

    private void performGameLoop(long lastTime, double ns, double delta, long timer) {
        while(running)
        {
            long now = System.nanoTime();

            delta += (now - lastTime)/ns;
            lastTime = now;

            if(delta>=1)
            {
                tick();
                render();
                delta--;
            }
            if(System.currentTimeMillis()-timer>1000)
            {
                timer+=1000;
            }

        }
    }

    private void tick()
    {
        gsm.update();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        gsm.render(g);
        g.dispose();
        bs.show();

    }

    public static void main(String args[])
    {
        Main game = new Main();

        game.setPreferredSize(new Dimension(WIDTH, HEIGHT ));
        game.setMaximumSize(new Dimension(WIDTH , HEIGHT));
        game.setMinimumSize(new Dimension(WIDTH, HEIGHT ));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();
    }




}

