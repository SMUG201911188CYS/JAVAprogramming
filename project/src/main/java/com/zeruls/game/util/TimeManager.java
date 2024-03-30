package main.java.com.zeruls.game.util;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {
    private Timer m_timer;
    private TimerTask m_timertask;
    public int Count;
    private boolean isEnd;


    public TimeManager(int Count) {
        this.Count = Count;
        isEnd = false;
        m_timer = new Timer();
        m_timertask = new TimerTask() {
            @Override
            public void run() {
                running();
            }
        };
    }

    //delay 초에 시작함.
    public void TimerSchdule(int delay) {
        m_timer.schedule(m_timertask,5000);
    }

    public void TimerSchdule(int delay,int period) {
        m_timer.schedule(m_timertask,period);
    }

    public void setCount(int c) {Count = c;}
    public void setEnd(boolean end) {isEnd = end;}

    public int getCount() {return Count;}
    public boolean getEnd() {return isEnd;}

    private void running() {
        if(Count < 1) {
            System.out.println("Timer Start");
            Count++;
        }
        else {
            isEnd = true;
            m_timer.cancel();
            m_timer = new Timer();
        }
    }



}
