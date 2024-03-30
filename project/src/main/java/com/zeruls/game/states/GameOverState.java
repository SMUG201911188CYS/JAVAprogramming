package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.MusicPlayer;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.Graphics2D;


public class GameOverState extends GameState {
    private boolean isBadEndig;
    private MusicPlayer musicPlayer;
    private String word;

    public GameOverState(GameStateManager gsm,boolean isBadEnding) {
        super(gsm);
        this.isBadEndig = isBadEnding;
        if(isBadEnding) {
            musicPlayer = new MusicPlayer("audio/BadEnding.mp3",true);
            word = "You lose...";
        }
        else {
            musicPlayer = new MusicPlayer("audio/Ending.mp3",true);
            word = "You save princess!!";
        }
        musicPlayer.start();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        Sprite.drawArray(g,word,new Vector2f(1024/2,768/2),32,24);
    }

}
