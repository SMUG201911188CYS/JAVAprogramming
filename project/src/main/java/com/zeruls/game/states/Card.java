package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.*;

public class Card implements Cloneable{
    private Sprite front_sprite;
    private Sprite back_sprite;
    private Vector2f pos;
    private int size;
    private int DM;
    private int MP;
    private boolean isSelected;
    private boolean isEnemySelected;
    private boolean isBacksprite;
    private int move;
    private String s;
    private boolean arrange[][];
    public final static int ATTACK_CARD = 0;
    public final static int MOVE_CARD = 1;
    public final static int HEAL_CARD = 2;    //마나도 포함
    public final static int UTIL_CARD = 3;    //쉴드
    private int attribute;
    private int plus_hp;
    private int plus_mp;
    private int minus_deal;
    private boolean isHeal;

    private void init_card(Sprite front_sprite, Vector2f orgin, int size) {
        this.front_sprite = front_sprite;
        back_sprite = new Sprite("entity/card_Back.png");
        this.pos = orgin;
        this.size = size;
        this.isBacksprite = false;
        isSelected = false;
    }
    //공격 카드
    public Card(Sprite front_sprite, Vector2f orgin,int size,int DM,int MP,boolean arrange[][]) {
        init_card(front_sprite,orgin,size);
        this.DM = DM;
        this.MP = MP;
        this.arrange = arrange;
        attribute = ATTACK_CARD;
        this.isBacksprite = false;
    }

    //이동 카드
    public Card(Sprite front_sprite,Vector2f orgin,int size,int move) {
        init_card(front_sprite,orgin,size);
        this.move = move;
        attribute = MOVE_CARD;
        this.isBacksprite = false;
    }

    //회복 카드
    public Card(Sprite front_sprite,Vector2f orgin,int size,int plus,boolean isHeal) {
        init_card(front_sprite,orgin,size);
        this.isHeal = isHeal;
        if(isHeal) {
            this.plus_hp = plus;
            this.plus_mp = 0;
        }

        else {
            this.plus_mp = plus;
            this.plus_hp = 0;
        }

        attribute = HEAL_CARD;
        this.isBacksprite = false;
    }

    //방어 카드
    public Card(Sprite front_sprite,Vector2f orgin,int size) {
        init_card(front_sprite,orgin,size);
        minus_deal = 15;
        this.isBacksprite = false;
        attribute = UTIL_CARD;
    }

    public int getDM() { return DM; }
    public int getSize() {return size;}
    public Sprite getBack_sprite() { return back_sprite; }
    public Sprite getFront_sprite() { return front_sprite; }
    public int getMP() { return MP; }
    public Vector2f getPos() { return pos; }
    public String getFileName() { return front_sprite.getFileName();}
    public int getMove() {return move;}
    public int getAttribute() {return attribute;}
    public int getPlus_hp() {return plus_hp;}
    public int getPlus_mp() {return plus_mp;}
    public boolean isEnemySelected() {return isEnemySelected;}
    public boolean isSelected() {
        return isSelected;
    }
    public boolean[][] getArrange() {return arrange;}
    public boolean isHeal() {return isHeal;}

    public boolean getisBacksprite() {
        return isBacksprite;
    }

    public void setisBacksprite(boolean isUsed) {
        this.isBacksprite = isUsed;
    }


    public void setFront_sprite(Sprite Front_sprite) {this.front_sprite = front_sprite;}
    public void setBack_sprite(Sprite Back_sprite){this.back_sprite = back_sprite;}
    public void setSize(int size) {this.size = size;}
    public void setDM(int DM) {this.DM = DM;}
    public void setMP(int MP) {this.MP = MP;}


    public void setSelected(boolean selected) {this.isSelected = selected;}
    public void setPos(Vector2f pos) {this.pos = pos;}
    public void setEnemySelected(boolean selected) {this.isEnemySelected = selected;}

    public void render(Graphics2D g) {
        g.setColor(Color.pink);
        if(isBacksprite == false) //카드 선택할때,사용할때 앞
            g.drawImage(front_sprite.getSprite(),(int)pos.x,(int)pos.y,null);
        else    //시작할때 뒤
            g.drawImage(back_sprite.getSprite(),(int)pos.x,(int)pos.y,null);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
