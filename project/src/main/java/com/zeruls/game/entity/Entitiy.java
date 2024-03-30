package main.java.com.zeruls.game.entity;


import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.java.com.zeruls.game.AttackSystemManager;
import main.java.com.zeruls.game.graphics.Animation;
import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.states.Card;
import main.java.com.zeruls.game.states.PlayState;
import main.java.com.zeruls.game.util.Vector2f;

public abstract class Entitiy {

    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean isReverse;   //지금 오른쪽 왼쪽을 바라보고 있느냐
    
    protected float dx;
    protected float dy;

    protected float maxSpeed = 4f;
    protected float acc = 3f;
    protected float deacc = 0.3f;


    protected int HP;
    protected int MP;


    public Entitiy(Sprite sprite,Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;
        HP = 100;
        MP = 100;
        ani = new Animation();
        setAnimation(RIGHT,sprite.getSpriteArray(RIGHT),10);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    };

    public void setSize(int i) {size = i;}

    public void setMaxSpeed(float f) {maxSpeed = f;}

    public void setAcc(float f) {acc = f;}

    public void setDeacc(float f) {deacc = f;}

    public void setHP(int HP) {this.HP = HP;}
    public void setisReverse(boolean isLeft) {this.isReverse = isReverse;}
    public void setMP(int MP) {this.MP = MP;}
    public void setPos(Vector2f pos) {this.pos = pos;}
    public int getSize() { return size; }

    public Vector2f getPos() {return pos;}

    public Animation getAnimation() {return ani;}

    public String getHP() {return Integer.toString(HP);}
    public String getMP() {return Integer.toString(MP);}
    public boolean getisReverse() {return this.isReverse;}

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        /*if(up) {
            if(currentAnimation != UP || ani.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        }
        else if(down) {
            if(currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        }*/
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
        move();
        pos.x += dx;
        pos.y += dy;
        checkgo();
    }

    public abstract void render(Graphics2D g) ;

    public abstract void checkgo();

    public void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }
        if(down) {
            dy += acc;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }
        if(right) {
            dx += acc;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
        if(left) {
            dx -= acc;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
    }

    public void setDir(int dir) {
        switch (dir) {
            case PlayState.DOWN:
                down = true;
                break;
            case PlayState.LEFT:
                left = true;
                break;
            case PlayState.RIGHT:
                right = true;
                break;
            case PlayState.UP:
                up = true;
                break;
        }
    }
  
}