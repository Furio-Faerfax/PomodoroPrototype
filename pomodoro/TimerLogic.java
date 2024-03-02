import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Color;
import javax.swing.BorderFactory;

public class TimerLogic extends FrameBasics {

    public JButton btnStart;
	JButton btnPause;
	JButton btnReset;
	

	Timer timer;
	JPanel panTimer;
	Pomodoro owner;
	TimerVisuals visuals;

	public void updateVisuals() {
		int[] tmp = getTimer().getTime();
		String[] timeFormatted = new String[3];
		timeFormatted[0] = tmp[0] < 10 ? ("0"+tmp[0]) : (""+tmp[0]);
		timeFormatted[1] = tmp[1] < 10 ? ("0"+tmp[1]) : (""+tmp[1]);
		timeFormatted[2] = tmp[2] < 10 ? ("0"+tmp[2]) : (""+tmp[2]);
		if(tmp[0] == 0)
			visuals.timerText.setText(timeFormatted[1]+" : "+timeFormatted[2]);
		else
			visuals.timerText.setText(timeFormatted[0]+" : "+timeFormatted[1]+" : "+timeFormatted[2]);
		
	}
	public void timerTick() {

		int[] tmp = getTimer().getTime();
		updateVisuals();
		printTimer(tmp);
	}
	public void setVisuals(TimerVisuals visuals) {
		
			this.visuals = visuals;
	}
	
	public TimerLogic(JPanel panTimer, Pomodoro owner){
			updateColors();
			timer = new Timer(this);
			timer.start();
			
			this.panTimer = panTimer;
			this.owner = owner;
			
			JPanel box = new JPanel();
			box.setLayout(new FlowLayout());
			box.setBackground(clrNormal);
			box.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
			
			EasyButton easyBtn = new EasyButton("Start", "assets/sprites/btn.png");
			btnStart = easyBtn.getButton();
			box.add(easyBtn.getPanel());
			
			easyBtn = new EasyButton("Pause", "assets/sprites/btn.png");
			btnPause = easyBtn.getButton();
			box.add(easyBtn.getPanel());
			
			easyBtn = new EasyButton("Reset", "assets/sprites/btn.png");
			btnReset = easyBtn.getButton();
			box.add(easyBtn.getPanel());
			
			this.panTimer.add(box);
		
			btnStart.addActionListener(owner);
			btnPause.addActionListener(owner);
			btnReset.addActionListener(owner);
			
			
			btnStart.setPreferredSize(new Dimension(btnStart.getIcon().getIconWidth(), btnStart.getIcon().getIconHeight()));
			btnPause.setPreferredSize(new Dimension(btnPause.getIcon().getIconWidth(), btnPause.getIcon().getIconHeight()));
			btnReset.setPreferredSize(new Dimension(btnReset.getIcon().getIconWidth(), btnReset.getIcon().getIconHeight()));
	
		}
		

	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart && (timer.getHour() > 0 || timer.getMinute() > 0)){
			if(timer.getTimerState() == timer.getIdleState()) {
				//System.out.println("Pomodor Play");
				timer.calculateSeconds();
				timer.Play();
				visuals.setArrowsVisible(false);
			}
			if(timer.getTimerState() == timer.getPauseState()) {
				
				//System.out.println("Pomodor Resume");
				timer.Play();
			}
		}
        if (e.getSource() == btnPause){
			if(timer.getTimerState() == timer.getPlayState()) {
				timer.Pause();
			}
		}
        if (e.getSource() == btnReset){
			reset();
		}
	}
	
	public void reset() {
		timer.reset();
		updateVisuals();
		visuals.timerText.setText("00 : 00 : 00");
		if(visuals.getOptionVisible())
			visuals.setArrowsVisible(true);
	}
	public Timer getTimer() {return timer;}
	
	public void printTimer(int[] tmp) {
		//System.out.println("Time1: "+tmp[0]+":"+tmp[1]+":"+tmp[2]);
		//System.out.println("Time2: "+getTimer().getHour()+":"+getTimer().getMinute()+":"+getTimer().getSecond());
	}
}