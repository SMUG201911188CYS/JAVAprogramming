package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.MusicPlayer;
import main.java.com.zeruls.game.util.Vector2f;


import java.util.LinkedList;

import java.awt.*;
import java.util.Queue;

public class SelectState extends GameState{
    public  static Queue<Card> player_cards;
    public  static Queue<Card> enemey_cards;

    public final int PLAYER_CARDS = 10;
    public final int DEATHKNIGHT_CARDS = 4;
    public final int DEVIL_CARDS = 4;
    public final int SKELETON_CARDS = 3;
    public final int MAGICTION_CARDS = 4;
    public final int NORMAL_CARDS = 7;

    public static final int DEVIL = 3;      //3 0 2 1
    public static final int SKELETON = 0;
    public static final int MAGICTION = 2;
    public static final int DEATHKNIGHT = 1;

    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;

    private Sprite BackGround;
    private Card Cards[];
    private boolean isSelect[];
    private Vector2f orginal_card_pos[];
    private Vector2f StartButton_pos[];     //rectangle 기준 왼쪽위 1 오른쪽위 2 오른쪽 밑 3 왼쪽 밑4
    private Vector2f ResetButton_pos[];

    private  Vector2f pos_Selected_Cards[];    //원래 카드들의 위치
    private  int isSelected_Cards[];   //-1일때 안고름
    private Queue<Integer> selected_card_index;
    private int isNormal;
    private int index;
    private MusicPlayer musicPlayer;

    public String getPath(String kind, String path)
    {
        return "entity/" + kind + "Card/" + path + ".png";
    }

    public int getSize()
    {
        return 80;
    }

    public Card makeAttackCard( String kind, String path, float x, float y,int DM,int MP, boolean arrange[][])
    {
        String sprite_path = getPath(kind, path);
        int size = getSize();
        return new Card( new Sprite(sprite_path), new Vector2f(x, y), size, DM,MP,arrange);
    }

    public Card makeHealCard( String kind, String path, float x, float y,int plus, boolean isHeal)
    {
        String sprite_path = getPath(kind, path);
        int size = getSize();
        return new Card( new Sprite(sprite_path), new Vector2f(x, y), size, plus,isHeal);
    }

    public Card makeMoveCard( String kind, String path, float x, float y, int move)
    {
        String sprite_path = getPath(kind, path);
        int size = getSize();
        return new Card( new Sprite(sprite_path), new Vector2f(x, y), size, move);
    }

    public Card makeUtilCard( String kind, String path, float x, float y)
    {
        String sprite_path = getPath(kind, path);
        int size = getSize();
        return new Card( new Sprite(sprite_path), new Vector2f(x, y), size);
    }

    private void SelectEnemyCard(int card,int seed) throws CloneNotSupportedException {
        isNormal = (int)(Math.random()*2);
        System.out.println(isNormal);
        if(isNormal == 0) { //스킬카드
            index = (int)(Math.random()*card) + seed;
            while(Cards[index].isEnemySelected())
                index = (int)(Math.random()*card) + seed;
            enemey_cards.add((Card)Cards[index].clone());
            Cards[index].setEnemySelected(true);
        }
        else {  //보통카드
            index = (int)(Math.random()*6) + 1;
            while(Cards[index].isEnemySelected())
                index = (int)(Math.random()*6) + 1;
            enemey_cards.add((Card)Cards[index].clone());
            Cards[index].setEnemySelected(true);
        }
    }


    public SelectState(GameStateManager gsm, int ANAMY) throws CloneNotSupportedException {
        super(gsm);
        BackGround = new Sprite("entity/SelectBackground.png");
        Cards = new Card[25];
        isSelect = new boolean[PLAYER_CARDS];
        isSelected_Cards = new int[3];
        pos_Selected_Cards = new Vector2f[3];
        orginal_card_pos = new Vector2f[10];
        StartButton_pos = new Vector2f[4];
        ResetButton_pos = new Vector2f[4];

        player_cards = new LinkedList<Card>();
        enemey_cards = new LinkedList<Card>();
        selected_card_index = new LinkedList<Integer>();

        pos_Selected_Cards[0] = new Vector2f(300,600);
        pos_Selected_Cards[1] = new Vector2f(165,600);
        pos_Selected_Cards[2] = new Vector2f(30,600);
        musicPlayer = new MusicPlayer("audio/SoundEffect/Button.mp3",false);


        boolean arrange[][][] = {
                {{false,false,true},{false,true,true},{false,false,true}},  //내려치기
                {{false,true,true},{false,true,true},{false,true,true}},    //반가르기
                {{false,false,false},{false,true,true},{false,false,false}},    //찌르기

                {{false,false,false},{true,true,true},{false,false,false}},   //영혼가르기
                {{false,true,false},{true,true,true},{false,true,false}},   //영혼십자베기
                {{true,false,false},{true,true,false},{true,false,false}},   //영혼절단
                {{true,true,true},{true,true,true},{true,true,true}},   //영혼폭팔

                {{true,true,true},{true,false,true},{true,true,true}},  //뼈돌출
                {{false,false,false},{true,false,false},{false,false,false}},   //암흑던지기
                {{true,false,true},{false,true,false},{true,false,true}},   //암흑뿌리기
                {{true,false,true},{true,true,true},{true,false,true}}, //암흑폭팔

                {{true,false,false},{false,false,false},{false,true,false}},    //저주죽음
                {{true,false,true},{false,true,false},{true,false,true}},   //피뿌리기
                {{true,true,true},{true,true,true},{true,true,true}},   //피의 비
                {{false,false,false},{true,true,false},{true,true,false}},  //흡혈

                {{true,false,false},{true,true,false},{true,false,false}},  //뼈내리치기
                {{true,false,false},{false,true,false},{true,false,false}},    //뼈던지기
                {{false,false,false},{true,true,false},{false,false,false}}    //뼈찌르기
        };

        Cards[0] = makeHealCard("Normal", "마나회복", 100, 150, 15, false);
        Cards[1] = makeHealCard("Normal", "체력회복", 250, 150, 15, true);
        Cards[2] = makeMoveCard("Normal","아래쪽이동",400,150,DOWN);
        Cards[3] = makeMoveCard("Normal","왼쪽이동",550,150,LEFT);
        Cards[4] = makeMoveCard("Normal","위쪽이동",700,150,UP);
        Cards[5] = makeMoveCard("Normal","오른쪽이동",850,150,RIGHT);
        Cards[6] = makeUtilCard("Normal","방어",100,300);

        Cards[7] = makeAttackCard("Player","내려치기",250,300,30,30,arrange[0]);
        Cards[8] = makeAttackCard("Player","반가르기",400,300,35,30,arrange[1]);
        Cards[9] = makeAttackCard("Player","찌르기",550,300,60,70,arrange[2]);

        //DeathKnight
        Cards[10] = makeAttackCard("DeathKnight","영혼가르기",-9999,-9999,50,50,arrange[3]);
        Cards[11] = makeAttackCard("DeathKnight","영혼십자베기",-9999,-9999,35,40,arrange[4]);
        Cards[12] = makeAttackCard("DeathKnight","영혼절단",-9999,-9999,35,30,arrange[5]);
        Cards[13] = makeAttackCard("DeathKnight","영혼폭발",-9999,-9999,20,15,arrange[6]);

        //Magiction
        Cards[14] = makeAttackCard("Magiction","뼈돌출",-9999,-9999,25,30,arrange[7]);
        Cards[15] = makeAttackCard("Magiction","암흑던지기",-9999,-9999,60,60,arrange[8]);
        Cards[16] = makeAttackCard("Magiction","암흑뿌리기",-9999,-9999,25,25,arrange[9]);
        Cards[17] = makeAttackCard("Magiction","암흑폭발",-9999,-9999,45,50,arrange[10]);

        //Devil
        Cards[18] = makeAttackCard("Devil","저주-죽음",-9999,-9999,70,70,arrange[11]);
        Cards[19] = makeAttackCard("Devil","피뿌리기",-9999,-9999,35,30,arrange[12]);
        Cards[20] = makeAttackCard("Devil","피의 비",-9999,-9999,30,40,arrange[13]);
        Cards[21] = makeAttackCard("Devil","흡혈",-9999,-9999,25,35,arrange[14]);

        //Skelecton
        Cards[22] = makeAttackCard("Skeleton","뼈내리치기",-9999,-9999,20,20,arrange[15]);
        Cards[23] = makeAttackCard("Skeleton","뼈던지기",-9999,-9999,30,35,arrange[16]);
        Cards[24] = makeAttackCard("Skeleton","뼈찌르기",-9999,-9999,40,30,arrange[17]);

        //스타트 버튼
        StartButton_pos[0] = new Vector2f(440,590);
        StartButton_pos[1] = new Vector2f(560,590);
        StartButton_pos[2] = new Vector2f(440,690);
        StartButton_pos[3] = new Vector2f(560,690);

        //
        ResetButton_pos[0] = new Vector2f(440,720);
        ResetButton_pos[1] = new Vector2f(570,720);
        ResetButton_pos[2] = new Vector2f(440,750);
        ResetButton_pos[3] = new Vector2f(570,750);



        for(int i=0;i<10;i++) {
            orginal_card_pos[i] = Cards[i].getPos();
        }

        for(int i=0;i<3;i++) {  //데스나이트 10~13, 메지션 14~17, 데빌 18~21  -> normal카드 1~7 , 스켈레톤 22~24
            switch (ANAMY) {
                case SKELETON:
                    SelectEnemyCard(3,22);
                    break;
                case DEATHKNIGHT:
                    SelectEnemyCard(4,10);
                    break;
                case MAGICTION:
                    SelectEnemyCard(4,14);
                    break;
                case DEVIL:
                    SelectEnemyCard(4,18);
                    break;
            }
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) throws CloneNotSupportedException {
        if(mouse.getButton() == 1) {
            System.out.println(mouse.getX() + ", " + mouse.getY());
            Card_Check(mouse.getX(),mouse.getY());
            Button_Check(mouse.getX(),mouse.getY());
        }
    }

    private void Card_Check(float x,float y) {
        for(int i=0;i<PLAYER_CARDS;i++) {
            Vector2f c_pos = Cards[i].getPos();
            if(x>=c_pos.x && x<=c_pos.x + 100) {
                if(y>=c_pos.y && y<=c_pos.y + 130) {
                    if(Cards[i].isSelected()) { //원래 선택되어있으면
                        System.out.println("카드가 다시 원래대로 돌아갑니다.");
                        System.out.println("이전좌표 : " + Cards[i].getPos().x + "");
                        Cards[i].setPos(orginal_card_pos[i]);   //원래위치로 ㄱㄱ
                        Cards[i].setSelected(false);
                        System.out.println("이후좌표 : " + Cards[i].getPos().x + "");
                        player_cards.poll();
                        selected_card_index.poll();
                        break;
                    }
                    else {
                        if(player_cards.size()<3) {
                            System.out.println("카드가 이동합니다.");
                            System.out.println("이전좌표 : " + Cards[i].getPos().x + "");
                            Cards[i].setSelected(true);
                            Cards[i].setPos(pos_Selected_Cards[player_cards.size()]);   //선택한 곳 좌표로
                            System.out.println("이후좌표 : " + Cards[i].getPos().x + "");
                            player_cards.add(Cards[i]);
                            selected_card_index.add(i);
                            break;
                        }
                    }
                }
            }
        }
    }


    private void Button_Check(float x, float y) throws CloneNotSupportedException {
        if(x>= StartButton_pos[0].x && x<= StartButton_pos[1].x) {
            if(y>= StartButton_pos[0].y && y<= StartButton_pos[3].y) {
                musicPlayer.start();
                musicPlayer = new MusicPlayer("audio/SoundEffect/Button.mp3",false);
                System.out.println("Start Button Selected!");
                if(player_cards.size() == 3) {
                    System.out.println("3개 다채웠군요!");
                    System.out.println(selected_card_index.size());
                    gsm.pop(GameStateManager.SELECT);
                    gsm.add(GameStateManager.PLAY);
                }
                else {
                    System.out.println(3-player_cards.size() + "더 채워주세요 !");

                    System.out.println(selected_card_index.size());
                }
            }
            else if (y>= ResetButton_pos[0].y && y<= ResetButton_pos[3].y) {
                System.out.println("Reset Button Clicked");
                musicPlayer.start();
                musicPlayer = new MusicPlayer("audio/SoundEffect/Button.mp3",false);
                for(int i=0;i<3;i++) {
                    if (player_cards.peek() != null && selected_card_index.peek() != null) {
                        player_cards.peek().setSelected(false);
                        player_cards.peek().setPos(orginal_card_pos[selected_card_index.peek()]);
                        player_cards.poll();
                        selected_card_index.poll();
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {

        Sprite.drawImg(g,BackGround.getSprite(),new Vector2f(0,0),1024,768);
        for(int i=0;i<10;i++) {
            Cards[i].render(g);
        }
    }
}



