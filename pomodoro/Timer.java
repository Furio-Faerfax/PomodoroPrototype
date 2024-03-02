
public class Timer extends Thread{
	
	int hour = 0;
	int minute = 0;
	int second = 0;
	
	int seconds;
	int remainingSeconds;
	
	boolean finish = false;
	int steps = 1000;
	boolean debug = true;
	String msg = "";
	
	TimerLogic owner;
	
	Thread timer;
	
	public Timer(TimerLogic owner) {
		this.owner = owner;
	}
	
	public enum TIMERSTATE {
	  PLAY,
	  PAUSE,
	  STOP,
	  IDLE
	}
	
	public TIMERSTATE getPlayState() {return TIMERSTATE.PLAY;}
	public TIMERSTATE getPauseState() {return TIMERSTATE.PAUSE;}
	public TIMERSTATE getStopState() {return TIMERSTATE.STOP;}
	public TIMERSTATE getIdleState() {return TIMERSTATE.IDLE;}
	
	TIMERSTATE state = TIMERSTATE.IDLE;
	
	public void reset(){
		hour = 0;
		minute = 0;
		second = 0;
		seconds = 0;
		remainingSeconds = 0;
		state = TIMERSTATE.IDLE;
		finish = false;
	}
	public void softReset(){
		remainingSeconds = seconds;
		calculateTime();
		state = TIMERSTATE.IDLE;
		finish = false;
	}
	
	public void calculateTime() {
		
		hour = remainingSeconds/3600;
		minute = (remainingSeconds % 3600)/60;
		second = remainingSeconds % 60;
	}
	
	public void calculateSeconds(){
		seconds = (hour*3600)+(minute*60)+second;
		remainingSeconds = seconds;
	}
	
	public void setPause(){}
	
	public void Play() {
		state = TIMERSTATE.PLAY;
		
		//	System.out.println("Timer Play");
	}
	
	public void Pause() {
		state = TIMERSTATE.PAUSE;
	}
	public void Stop() {
		state = TIMERSTATE.STOP;
		softReset();//For Dev, because hitting reset is tedious. Later in pomodoro version2, this has to be deleted and stop needs more definition of behaviour to staRT breaks and sessiosn
		finish = false;
	}
	public void Resume() {
		state = TIMERSTATE.PLAY;
	}
	
	
	boolean running = true;
	public void  killTimer(){running = false;}
	
	@Override
	public void run() {
		while(running) {
			//System.out.println("Run");
			if(state == TIMERSTATE.PLAY && !getTimerFinish()){
				
				if(remainingSeconds > 0) {
					remainingSeconds--;
					calculateTime();
					if(debug)
						message(msg);
					
					try{
						sleep(1000);
					}catch(InterruptedException e) {}
				} else {
					Stop();
					finish = true;
					try{
						sleep(1);
					}catch(InterruptedException e) {}
				}
			} else {
				
				try{
					sleep(1);
				}catch(InterruptedException e) {}
			}
			if(getTimerFinish() && (state == TIMERSTATE.PLAY || state == TIMERSTATE.PAUSE)) {
				state = TIMERSTATE.IDLE;
			}
			
			
		}
	}
	
	public void message(String msg) {
		owner.timerTick();
		//System.out.println(remainingSeconds+" from Thread: Timer");
		//System.out.println("Time2: "+getHour()+":"+getMinute()+":"+getSecond());

	}
	
	public boolean getTimerFinish() { return finish;}
	public TIMERSTATE getTimerState() { return state;}
	
	
	
	
	
	
	
	
	
	public void setHour(int hour) {
		if(hour < 100 && hour >= 0)
			this.hour = hour;
	}
	
	public void setMinute(int minute) {
		if(minute < 60 && minute >= 0)
			this.minute = minute;
		else
			addHour(minute / 60);
	}
	
	public void addHour(int hour) {
		if(this.hour == 99)
			setMinute(59);
		
		if(this.hour >= 0 && this.hour < 99)
			this.hour += hour;
		else if(this.hour >= 99 && hour < 0)
			this.hour -= 1;
		
		if(this.hour < 0) {
			setHour(0);
			setMinute(0);
		}
	}
	
	public void addMinute(int minute) {
		if(this.minute >= 0  && this.minute < 60)
			this.minute += minute;
		
		if(this.minute > 59) {
			setMinute(0);
			int modifier = minute > 0 ? 1 : minute < 0 ? -1 : 0;
			addHour(modifier);
		}
		if(this.minute < 0) {
			setMinute(59);
			
			int modifier = minute > 0 ? 1 : minute < 0 ? -1 : 0;
			addHour(modifier);
		}
	}
	
	public int[] getTime() {
		return new int[] {hour, minute, second};
	}
	
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getSecond() {
		return second;
	}
	
}