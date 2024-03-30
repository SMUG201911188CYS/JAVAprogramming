package main.java.com.zeruls.game;
import main.java.com.zeruls.game.states.Card;
import main.java.com.zeruls.game.states.GameStateManager;
import main.java.com.zeruls.game.states.PlayState;
import main.java.com.zeruls.game.util.Vector2f;

public class AttackSystemManager  {

    public static Object enemy_pos;
    public static int Player_HP;
    public static int Player_MP;
    public static int Enemy_HP;
    public static Object player_pos;
    public static int Enemy_MP;
    private final int CHAR = 1;
    private final int EMPTY = 0;
    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    public static int dx = 150;
    public static int dy = 75;

    public void ResetPlayerMap() {
        for(int i=0;i<3;i++) {
            for(int j =0;j<4;j++) {
                GameStateManager.player_map[i][j] = EMPTY;
            }
        }
    }

    public void ResetAnamyMap() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                GameStateManager.enemy_map[i][j] = EMPTY;
            }
        }
    }

    public AttackSystemManager() {

        if(GameStateManager.player_map==null && GameStateManager.enemy_map ==null) {
            GameStateManager.player_map = new int[3][4];
            GameStateManager.enemy_map = new int[3][4];
            ResetAnamyMap();
            ResetAnamyMap();
            GameStateManager.player_map[1][0] = CHAR;
            GameStateManager.enemy_map[1][3] = CHAR;
        }
    }

    //retrun to pos xy
    public boolean Player_swap(Vector2f begin, Vector2f end) {
        return swap_array(begin, end, GameStateManager.player_map);
    }

    public boolean Enemy_swap(Vector2f begin, Vector2f end) {
        return swap_array(begin, end, GameStateManager.enemy_map);
    }

    private boolean swap_array(Vector2f begin, Vector2f end, int[][] arr) {
        try{
            int temp = arr[(int)begin.x][(int)begin.y];  //moving
            arr[(int)begin.x][(int)begin.y] =  arr[(int)end.x][(int)end.y];
            arr[(int)end.x][(int)end.y] = temp;
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public Vector2f getPlayerIndex() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                if(GameStateManager.player_map[i][j] == CHAR) {
                    return new Vector2f(i,j);
                }
            }
        }
        System.out.println("Cannot Find Player");
        return null;
    }

    public Vector2f getEnemyIndex() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                if(GameStateManager.enemy_map[i][j] == CHAR) {
                    return new Vector2f(i,j);
                }
            }
        }
        System.out.println("Cannot Find enemy");
        return null;
    }

    public boolean MovePlayer(int move) {
        switch(move) {
            case UP :
                if(Player_swap(getPlayerIndex(),new Vector2f(getPlayerIndex().x-1,getPlayerIndex().y)))
                    return true;
                else
                    return false;
            case DOWN:
                if(Player_swap(getPlayerIndex(),new Vector2f(getPlayerIndex().x+1,getPlayerIndex().y)))
                    return true;
                else
                    return false;
            case LEFT:
                if(Player_swap(getPlayerIndex(),new Vector2f(getPlayerIndex().x,getPlayerIndex().y-1)))
                    return true;
                else
                    return false;
            case RIGHT:
                if(Player_swap(getPlayerIndex(),new Vector2f(getPlayerIndex().x,getPlayerIndex().y+1)))
                    return true;
                else
                    return false;
            default:
                return false;
        }
    }

    public boolean MoveEnemy(int move) {
        switch(move) {
            case UP :
                if(Enemy_swap(getEnemyIndex(),new Vector2f(getEnemyIndex().x-1,getEnemyIndex().y)))
                    return true;
                else
                    return false;
            case DOWN:
                if(Enemy_swap(getEnemyIndex(),new Vector2f(getEnemyIndex().x+1,getEnemyIndex().y)))
                    return true;
                else
                    return false;
            case LEFT:
                if(Enemy_swap(getEnemyIndex(),new Vector2f(getEnemyIndex().x,getEnemyIndex().y-1)))
                    return true;
                else
                    return false;
            case RIGHT:
                if(Enemy_swap(getEnemyIndex(),new Vector2f(getEnemyIndex().x,getEnemyIndex().y+1)))
                    return true;
                else
                    return false;
            default:
                return false;
        }
    }

    public boolean PlayerAttack(boolean arr[][], boolean isReverse) {
        Vector2f np = getPlayerIndex();
        if(isReverse)
            return CheckAttack(np, GameStateManager.enemy_map,ReverseArr(arr));
        else
            return CheckAttack(np, GameStateManager.enemy_map,arr);
    }

    public boolean EnemyAttack(boolean arr[][] , boolean isReverse) {
        Vector2f np = getEnemyIndex();
        if(isReverse)
            return CheckAttack(np, GameStateManager.player_map,ReverseArr(arr));
        else
            return CheckAttack(np,GameStateManager.player_map,arr);
    }

    public boolean[][] ReverseArr(boolean arr[][]) {

        boolean[][] result = new boolean[3][3];

        result[0][0] = arr[0][1];
        result[1][0] = arr[1][2];
        result[2][0] = arr[2][2];
        result[0][2] = arr[0][0];
        result[1][2] = arr[1][0];
        result[2][2] = arr[2][0];

        return result;
    }

    private boolean CheckAttack(Vector2f np, int[][] map , boolean arr[][]) {
        System.out.println("Player x: " + getPlayerIndex().x + " y: " +getPlayerIndex().y);
        System.out.println("Enemy x: "+getEnemyIndex().x + "y: "+getEnemyIndex().y);
        PlayState.DrawAttack = new boolean[3][4];
        boolean isHeat = false;
        for(int i = (int)np.x-1,i1 = 0; i<=(int)np.x+1;i++,i1++) {
            for(int j = (int)np.y -1,j1 = 0; j<=(int)np.y+1;j++,j1++) {
                System.out.println("SKILL Check x: " + i1 + " y: " +j1);
                System.out.println("Arrange Check x: "+i + "y: "+j);
                if(arr[i1][j1]) {   //공격 범위가 유효할때
                    if(i>=0 && i<3 && j>=0  && j<4) {
                        PlayState.DrawAttack[i][j] = true;
                        if(map[i][j] != EMPTY) {
                            isHeat = true;
                            System.out.println(i + ", " +j + "에서 상대방이 맞음");
                        }
                    }
                }
            }
        }
        return isHeat;
    }


    public class Enemy_MP {
    }
}
