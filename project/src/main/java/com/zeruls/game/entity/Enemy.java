package main.java.com.zeruls.game.entity;

import main.java.com.zeruls.game.AttackSystemManager;
import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.states.PlayState;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entitiy {

    //private AABB sense;
    private int r;

    public Enemy(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);

        super.acc = 1f;
        super.maxSpeed = 3f;
        this.r = 135;
        //ense = new AABB(new Vector2f(orgin.x - size/2,orgin.y - size / 2),r,r);
    }




    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);

        g.setColor(Color.blue);
        //g.drawOval((int)(sense.getPos().getWorldVar().x),(int)(sense.getPos().getWorldVar().y),r,r);
        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
    }

    @Override
    public void checkgo() {
        if(PlayState.enemy_pre_pos != null) {
            if(down) {
                if(pos.y - PlayState.enemy_pre_pos.y >= AttackSystemManager.dy)
                    down = false;
            }
            else if(up) {
                if(PlayState.enemy_pre_pos.y - pos.y  >= AttackSystemManager.dy)
                    up = false;
            }
            else if(right) {
                if(pos.x - PlayState.enemy_pre_pos.x >= AttackSystemManager.dx)
                    right = false;
            }
            else if(left) {
                if(PlayState.enemy_pre_pos.x - pos.x  >= AttackSystemManager.dx)
                    left = false;
            }
        }
    }
}
