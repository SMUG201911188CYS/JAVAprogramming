package main.java.com.zeruls.game.util;

import com.sun.tools.javac.Main;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

public class MusicPlayer extends Thread {
    private Player player;
    private boolean isLoop;
    private File file;
    private FileInputStream fis;
    private BufferedInputStream bis;

    public MusicPlayer(String name, boolean isLoop) {
        try
        {
            this.isLoop = isLoop;
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(name);

            file = new File(resource.getFile());
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }catch (Exception e) {
            e.getMessage();
        }
    }

    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }

    public void close() {
        isLoop = false;
        player.close();
        this.interrupt();
    }

    @Override
    public void run() {
        try{
            do {
                player.play();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
            }while (isLoop);
            close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}