package main.java.com.zeruls.game.Char;

import main.java.com.zeruls.game.graphics.Animation;
import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Charactor {
    protected int HP;
    protected int MP;

    private final int RIGHT = 0;
    private final int LEFT = 1;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean right;
    protected boolean left;

    protected float dx;
    protected float dy;

    protected float maxSpeed = 4f;
    protected float acc = 3f;
    protected float deacc = 0.3f;


    protected int currentAnimation;

    public Charactor(Sprite sprite,Vector2f pos,int size) {
        this.sprite = sprite;
        this.pos = pos;
        this.size = size;
        this.HP = 100;
        this.MP = 100;

        ani = new Animation();
        setAnimation(RIGHT,sprite.getSpriteArray(RIGHT),10);
    }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void setSprite(Sprite sprite) {this.sprite = sprite;}
    public void setSize(int size) {this.size = size;}
    public void setPos(Vector2f pos) {this.pos = pos;}
    public void setMaxSpeed(float maxSpeed) {this.maxSpeed = maxSpeed;}
    public void setAcc(float f) {this.acc = f;}
    public void setDeacc(float f) {this.deacc = f;}
    public void setHP(int HP) {this.HP = HP;}
    public void setMP(int MP) {this.MP = MP;}
    public int getSize() { return size; }
    public Animation getAnimation() {return ani;}
    public Sprite getSprite() {return this.sprite;}
    public Vector2f getPos() {return this.pos;}


    public void animate() {
        if(right) {
            if(currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        }
        else if(left) {
            if(currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        }
        else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }




    public void update() {
        animate();
        ani.update();
    }

    public abstract void render(Graphics2D g) ;
}
