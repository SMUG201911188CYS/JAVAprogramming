package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.GamePanel;
import main.java.com.zeruls.game.entity.Enemy;
import main.java.com.zeruls.game.entity.Player;
import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.Vector2f;
import main.java.com.zeruls.game.graphics.Font;
import main.java.com.zeruls.game.AttackSystemManager;
import main.java.com.zeruls.game.util.TimeManager;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Time;


public class PlayState extends GameState {

    private Font font;
    private Player player;
    private Sprite BackGround;
    private Enemy Magiction;
    private Enemy Devil;
    private Enemy DeathKnight;
    private Enemy Skeleton;
    private Card[] player_cards;
    private Card[] enemy_cards;
    private Vector2f[] enemy_card_pos;
    private Vector2f[] pos_Selected_Cards;
    public static boolean DrawAttack[][];
    private boolean player_armor;
    private boolean enemy_armor;
    private TimeManager tm;

    public final static int UP = 3;
    public final static int DOWN = 2;
    public final static int RIGHT = 0;
    public final static int LEFT = 1;

    public static Vector2f player_pre_pos;
    public static Vector2f enemy_pre_pos;

    public static final int b_width = 150;
    public static final int b_height = 80;

    private AttackSystemManager ASM;
    public static Vector2f map;

    private int nowindex;
    private boolean Timerstart;

    private boolean isAttack;

    public PlayState(GameStateManager gsm) throws CloneNotSupportedException {
        super(gsm);
        tm = new TimeManager(0);
        Timerstart = false;
        pos_Selected_Cards = new Vector2f[3];
        pos_Selected_Cards[0] = new Vector2f(300,600);
        pos_Selected_Cards[1] = new Vector2f(165,600);
        pos_Selected_Cards[2] = new Vector2f(30,600);
        player_armor = false;
        enemy_armor = false;
        nowindex = 0;
        map = new Vector2f();
        if(AttackSystemManager.enemy_pos == null)
            player = new Player(new Sprite("entity/PlayerFormatted.png"), new Vector2f(250, 350), 64); //map's vector is 0
        else {
            player = new Player(new Sprite("entity/PlayerFormatted.png"), (Vector2f)AttackSystemManager.player_pos,64);
            player.setHP(AttackSystemManager.Player_HP);
            player.setMP(AttackSystemManager.Player_MP);
        }
        makeEnemy();
        BackGround = new Sprite("entity/BackGround.png");
        ASM = new AttackSystemManager();
        setCards();
        System.out.println(GameStateManager.Now_Stage + "스테이지 입니다.");
    }


    private Enemy makeEnemy(String kind,float x,float y) {
        return new Enemy(new Sprite("entity/"+kind+"Formatted.png"), new Vector2f(x,y),64);
    }

    private void makeEnemy() throws CloneNotSupportedException {
        enemy_card_pos = new Vector2f[3];
        enemy_card_pos[0] = new Vector2f(600,600);
        enemy_card_pos[1] = new Vector2f(735,600);
        enemy_card_pos[2] = new Vector2f(870,600);
        Vector2f pos = new Vector2f(700,350);

        switch(GameStateManager.Now_Stage) {
            case SelectState.SKELETON:
                Skeleton = makeEnemy("Skeleton",pos.x,pos.y);
                break;
            case SelectState.DEATHKNIGHT:
                DeathKnight = makeEnemy("DeathKnight",pos.x,pos.y);
                break;
            case SelectState.MAGICTION:
                Magiction = makeEnemy("Magiction",pos.x,pos.y);
                break;
            case SelectState.DEVIL:
                Devil = makeEnemy("Devil",pos.x,pos.y);
                break;
        }
        if(AttackSystemManager.enemy_pos != null) {
            getNowStage().setHP(AttackSystemManager.Enemy_HP);
            getNowStage().setMP((Integer) AttackSystemManager.Enemy_MP);
            getNowStage().setPos((Vector2f) AttackSystemManager.enemy_pos);
        }
    }

    private Enemy getNowStage() {
        switch (GameStateManager.Now_Stage) {
            case SelectState.SKELETON:
                return Skeleton;
            case SelectState.DEATHKNIGHT:
                return DeathKnight;
            case SelectState.MAGICTION:
                return Magiction;
            case SelectState.DEVIL:
                return Devil;
        }
        return null;
    }

    private void setCards() {
        player_cards = new Card[3];
        enemy_cards = new Card[3];
        try {
            if (SelectState.player_cards.peek() != null) {
                for (int i = 0; i < 3; i++) {
                    player_cards[i] = SelectState.player_cards.peek();
                    player_cards[i].setisBacksprite(true);
                    player_cards[i].setPos(pos_Selected_Cards[i]);
                    System.out.println(SelectState.player_cards.poll().getFront_sprite().getFileName() + "카드를 골랐내요 !");

                    enemy_cards[i] = SelectState.enemey_cards.peek();
                    enemy_cards[i].setisBacksprite(true);
                    enemy_cards[i].setPos(enemy_card_pos[i]);
                    System.out.println(SelectState.enemey_cards.poll().getFront_sprite().getFileName() + "적이 카드를 골랐네요 !");

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "다 안고르고 오셨군요!");
        }
    }

    private void drawString(Graphics2D g) {
        drawHPMP(g);
        drawSTAGE(g);
    }

    private void drawFPS(Graphics2D g) {
        String fps = GamePanel.frames + " FPS";
        Sprite.drawArray(g, fps, new Vector2f(GamePanel.width - fps.length() * 32, 32), 32, 24);
    }

    private void drawHPMP(Graphics2D g) {
        Sprite.drawArray(g, "HP : " + player.getHP(), new Vector2f(80, 30), 32, 24);
        Sprite.drawArray(g, "MP : " + player.getMP(), new Vector2f(80, 60), 32, 24);

        Sprite.drawArray(g, "HP : " + getNowStage().getHP(), new Vector2f(800, 30), 32, 24);
        Sprite.drawArray(g, "MP : " + getNowStage().getMP(), new Vector2f(800, 60), 32, 24);

    }

    private void drawSTAGE(Graphics2D g) {
        Sprite.drawArray(g,"STAGE : " + GameStateManager.Now_Stage, new Vector2f(420,40),32,24);

    }

    private void drawEnemy(Graphics2D g) {
        getNowStage().render(g);
    }

    public void update() throws CloneNotSupportedException {
        player.update();
        getNowStage().update();
    }

    public void open_playerCard() throws CloneNotSupportedException {
        boolean isHit;
        switch (player_cards[nowindex].getAttribute()) {
            case Card.ATTACK_CARD :
               if(Integer.parseInt(player.getMP())>enemy_cards[nowindex].getMP()) {
                   player.setMP((Integer.parseInt(player.getMP()) - player_cards[nowindex].getMP()));
                   isHit =ASM.PlayerAttack(player_cards[nowindex].getArrange(),player.getisReverse());
                   isAttack = true;
                   if(isHit) {
                       System.out.println("공격 성공!");
                       getNowStage().setHP((Integer.parseInt(getNowStage().getHP()) - player_cards[nowindex].getDM()));

                   }
               }
               else {
                   System.out.println("마나가 부족합니다!");
               }
                break;
            case Card.HEAL_CARD:
                player.setHP(Integer.parseInt(player.getHP()) + player_cards[nowindex].getPlus_hp());
                player.setMP(Integer.parseInt(player.getMP()) + player_cards[nowindex].getPlus_mp());
                if(Integer.parseInt(player.getHP()) > 100)
                    player.setHP(100);
                else if(Integer.parseInt(player.getMP()) >100)
                    player.setMP(100);
                break;
            case Card.MOVE_CARD:
                player_pre_pos = (Vector2f)player.getPos().clone();
                switch (player_cards[nowindex].getMove()) {
                    case UP:
                        if(ASM.MovePlayer(UP))
                            player.setDir(UP);
                        break;
                    case DOWN:
                        if(ASM.MovePlayer(DOWN))
                            player.setDir(DOWN);
                        break;
                    case LEFT:
                        if(ASM.MovePlayer(LEFT)) {
                            player.setDir(LEFT);
                            player.setisReverse(true);
                        }

                        break;
                    case RIGHT:
                        if(ASM.MovePlayer(RIGHT)){
                            player.setisReverse(false);
                            player.setDir(RIGHT);
                        }
                        break;
                }
                break;
            case Card.UTIL_CARD:
                player_armor = true;
                break;
        }
    }

    public void enemy_heal(Enemy enemy) {
        enemy.setHP(Integer.parseInt(enemy.getHP()) + enemy_cards[nowindex].getPlus_hp());
        enemy.setMP(Integer.parseInt(enemy.getMP()) + enemy_cards[nowindex].getPlus_mp());
        if(Integer.parseInt(enemy.getHP()) > 100)
            enemy.setHP(100);
        else if(Integer.parseInt(enemy.getMP()) >100)
            enemy.setMP(100);
    }

    public void enemy_move(Enemy enemy) throws CloneNotSupportedException {
        enemy_pre_pos = (Vector2f)enemy.getPos().clone();
        switch (enemy_cards[nowindex].getMove()) {
            case UP:
                if(ASM.MoveEnemy(UP))
                getNowStage().setDir(UP);
                break;
            case DOWN:
                if(ASM.MoveEnemy(DOWN))
                    getNowStage().setDir(DOWN);
                break;
            case LEFT:
                if(ASM.MoveEnemy(LEFT)) {
                    getNowStage().setDir(LEFT);
                    getNowStage().setisReverse(false);
                }
                break;
            case RIGHT:
                if(ASM.MoveEnemy(RIGHT)) {
                    getNowStage().setDir(RIGHT);
                    getNowStage().setisReverse(true);
                }
                break;
        }
    }


    public void open_enemyCard() throws CloneNotSupportedException {
        boolean isHit;
        switch (enemy_cards[nowindex].getAttribute()) {
            case Card.ATTACK_CARD :
                if(Integer.parseInt(getNowStage().getMP())>enemy_cards[nowindex].getMP()) {
                    getNowStage().setMP((Integer.parseInt(getNowStage().getMP())- enemy_cards[nowindex].getMP()));
                    isHit = ASM.EnemyAttack(enemy_cards[nowindex].getArrange(),getNowStage().getisReverse());
                    isAttack = true;
                    if(isHit) {
                        System.out.println("상대방이 공격에 성공했습니다!");
                        player.setHP((Integer.parseInt(player.getHP()) - enemy_cards[nowindex].getDM()));
                    }
                }
                else {
                    System.out.println("마나가 부족합니다!");
                }
                break;
            case Card.HEAL_CARD:
                enemy_heal(getNowStage());
                break;
            case Card.MOVE_CARD:
                enemy_move(getNowStage());
                break;
            case Card.UTIL_CARD:
                enemy_armor = true;
                break;
        }
    }

    public void openCard() throws CloneNotSupportedException {
        open_playerCard();
        open_enemyCard();
        nowindex++;
    }
    public void input(MouseHandler mouse, KeyHandler key) throws CloneNotSupportedException {
        key.escape.tick();
        player.input(mouse, key);
        if (key.escape.clicked) {
            System.out.println("Pressed by PlayState");
            if (gsm.getState(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
            } else {
                gsm.add(GameStateManager.PAUSE);
            }
        }
        if (key.enter.clicked) {
            System.out.println("Pre");
        }
        if (mouse.getButton() == 1) {
            if(Integer.parseInt(player.getHP()) <= 0 || Integer.parseInt(getNowStage().getHP()) <= 0) {
                if(Integer.parseInt(player.getHP()) <= 0) {
                    //player lose
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.GAMEOVER,true);
                }
                if( GameStateManager.Now_Stage++ >= 3) {
                    AttackSystemManager.enemy_pos = null;
                    AttackSystemManager.player_pos = null;
                    ASM = null;
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.GAMEOVER,false);
                }
                else {
                    GameStateManager.player_map = null;
                    GameStateManager.enemy_map = null;
                    AttackSystemManager.enemy_pos = null;
                    AttackSystemManager.player_pos = null;
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.SELECT);
                }
                //Enemy lose
            }
            else {
                System.out.println("x : " + mouse.getX() + " " + "y : " + mouse.getY());
                if(nowindex!=3) {
                    player_cards[nowindex].setisBacksprite(false);
                    enemy_cards[nowindex].setisBacksprite(false);
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Timer Start!");
                            try {
                                openCard();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                            timer.cancel();
                            Timerstart = false;
                        }
                    };
                    if(!Timerstart) {
                        timer.schedule(timerTask,3000);
                        Timerstart = true;
                    }
                }
                else {  //끝
                    AttackSystemManager.player_pos = (Vector2f) player.getPos().clone();
                    AttackSystemManager.enemy_pos = (Vector2f)getNowStage().getPos().clone();
                    AttackSystemManager.Enemy_HP = Integer.parseInt(getNowStage().getHP());
                    AttackSystemManager.Enemy_MP = Integer.parseInt(getNowStage().getMP());
                    AttackSystemManager.Player_HP = Integer.parseInt(player.getHP());
                    AttackSystemManager.Player_MP = Integer.parseInt(player.getMP());
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.SELECT);
                }
            }
        }
    }

    public void render(Graphics2D g) {
        Sprite.drawImg(g, BackGround.getSprite(), new Vector2f(0, 0), 1024, 768);
        drawString(g);
        player.render(g);
        drawEnemy(g);
        for(int i=0;i<3;i++) {
            player_cards[i].render(g);
            enemy_cards[i].render(g);
        }
    }
}