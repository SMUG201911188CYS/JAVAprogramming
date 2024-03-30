package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.MusicPlayer;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IntroState extends GameState {
    private Sprite background[];
    private int introindex;
    private int count;
    private MusicPlayer player;
    public IntroState(GameStateManager gsm) {
        super(gsm);
        Init();

    }

    private void Init() {
        background = new Sprite[5];
        introindex = 0;
        for(int i=0; i<background.length;i++)  {
            background[i] = new Sprite("Background/Intro"+i +".png");
        }
        player = new MusicPlayer("audio/Intro.mp3",true);
        player.start();
        count = 0;
    }

    @Override
    public void update() throws CloneNotSupportedException {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) throws CloneNotSupportedException {
        if(mouse.getButton() == 1) {
            if(introindex < background.length) {
                if(count != 4)
                    count++;
                else {
                    count = 0;
                    introindex++;
                }
            }
            else{
                player.close();
                gsm.pop(GameStateManager.INTRO);
                gsm.add(GameStateManager.SELECT);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(introindex < background.length)
            Sprite.drawImg(g,background[introindex].getSprite(),new Vector2f(0,0),1024,768);
    }
}
