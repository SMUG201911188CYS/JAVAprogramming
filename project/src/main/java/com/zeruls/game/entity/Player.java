package main.java.com.zeruls.game.entity;

import java.awt.*;


import main.java.com.zeruls.game.AttackSystemManager;
import main.java.com.zeruls.game.GamePanel;
import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.states.PlayState;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.Vector2f;

public class Player extends Entitiy {

    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        // TODO Auto-generated constructor stub
        super.acc = 2f;   //2f
        super.maxSpeed = 3f;
    }

    public void resetPosition() {
        System.out.println("Reseting Player..");
        pos.x = GamePanel.width / 2 - 32;
        PlayState.map.x = 0;

        pos.y = GamePanel.height / 2 - 32;
        PlayState.map.y = 0;

    }
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        g.setColor(Color.blue);
        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
        //g.drawImage(ani.getImage(),(int)(pos.x),(int)(pos.y),size,size,null);
    }

    @Override
    public void checkgo() {
        if(PlayState.player_pre_pos != null) {
            if(down) {
                if(pos.y - PlayState.player_pre_pos.y >= AttackSystemManager.dy)
                    down = false;
            }
            else if(up) {
                if(PlayState.player_pre_pos.y - pos.y  >= AttackSystemManager.dy)
                    up = false;
            }
            else if(right) {
                if(pos.x - PlayState.player_pre_pos.x >= AttackSystemManager.dx)
                    right = false;
            }
            else if(left) {
                if(PlayState.player_pre_pos.x - pos.x  >= AttackSystemManager.dx)
                    left = false;
            }
        }
    }


    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1) {
            System.out.println("Player: " + pos.x + ", " + pos.y);
        }

    }



    
}