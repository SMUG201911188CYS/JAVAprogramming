package main.java.com.zeruls.game.states;

import java.awt.Graphics2D;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.Vector2f;

public class PauseState extends GameState {
    private Sprite BackGround;
    public PauseState(GameStateManager gsm) {
        super(gsm);
        BackGround = new Sprite("entity/pauseState.png");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        //System.out.println("paused");
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        // TODO Auto-generated method stub


    }

    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        Sprite.drawImg(g,BackGround.getSprite(),new Vector2f(0,0),1024,768);
    }

}
