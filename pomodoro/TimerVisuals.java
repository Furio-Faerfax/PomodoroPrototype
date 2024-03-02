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
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;

public class TimerVisuals extends FrameBasics {

	JPanel panTimerVisuals;
	Pomodoro owner;
	TimerLogic timerLogic;
	
	JButton btnArrowHourUp = new EasyButton("", "assets/sprites/counter.png").getButton();
	JButton btnArrowHourDown = new EasyButton("", "assets/sprites/counter_down.png").getButton();
	JButton btnArrowMinuteUp = new EasyButton("", "assets/sprites/counter.png").getButton();
	JButton btnArrowMinuteDown = new EasyButton("", "assets/sprites/counter_down.png").getButton();
	

	JPanel box = new JPanel();
	JPanel upperArrpows = new JPanel();
	JPanel timerVisualPan = new JPanel();
	JPanel bottomArrows = new JPanel();
	JLabel timerText = new JLabel("00 : 00 : 00");
	private boolean arrowsVisible = true;


	public TimerVisuals(JPanel panTimerVisuals, Pomodoro owner, TimerLogic timerLogic){
		updateColors();			
		panTimerVisuals.setLayout(new GridLayout(3,1));
		this.timerLogic = timerLogic;
			
		this.owner = owner;
			
			
		timerText.setFont(new Font("Serif", Font.PLAIN, 48));
		timerText.setForeground(clrText);
		
		upperArrpows.setBackground(clrNormal);
		bottomArrows.setBackground(clrNormal);
		timerVisualPan.setBackground(clrNormal);
		
        upperArrpows.add(btnArrowHourUp);
        bottomArrows.add(btnArrowHourDown);
        upperArrpows.add(btnArrowMinuteUp);
        bottomArrows.add(btnArrowMinuteDown);
		timerVisualPan.add(timerText);
		
		panTimerVisuals.add(upperArrpows, BorderLayout.NORTH);
		panTimerVisuals.add(timerVisualPan, BorderLayout.CENTER);
		panTimerVisuals.add(bottomArrows, BorderLayout.SOUTH);
		
        btnArrowHourUp.addActionListener(owner);
        btnArrowHourDown.addActionListener(owner);
        btnArrowMinuteUp.addActionListener(owner);
        btnArrowMinuteDown.addActionListener(owner);
	}
	public void actionPerformed(ActionEvent e) {
		
        if (e.getSource() == btnArrowHourUp){
			if(timerLogic.getTimer().getTimerState() == timerLogic.getTimer().getIdleState()) {
				timerLogic.getTimer().addHour(1);
				timerLogic.updateVisuals();
			}
		}
        if (e.getSource() == btnArrowHourDown){
			if(timerLogic.getTimer().getTimerState() == timerLogic.getTimer().getIdleState()) {
				timerLogic.getTimer().addHour(-1);
				timerLogic.updateVisuals();
			}
		}
        if (e.getSource() == btnArrowMinuteUp){
			if(timerLogic.getTimer().getTimerState() == timerLogic.getTimer().getIdleState()) {
				timerLogic.getTimer().addMinute(1);
				timerLogic.updateVisuals();
			}
		}
        if (e.getSource() == btnArrowMinuteDown){
			if(timerLogic.getTimer().getTimerState() == timerLogic.getTimer().getIdleState()) {
				timerLogic.getTimer().addMinute(-1);
				timerLogic.updateVisuals();
			}
			
		}
	}
	
	private boolean optionVisible = true;
	public void switchArrowsVisible() {
		optionVisible = !optionVisible;
		upperArrpows.setVisible(optionVisible);
		bottomArrows.setVisible(optionVisible);
	}
	
	public boolean getOptionVisible() {return optionVisible;}
	
	public boolean getArrowsVisible(){return arrowsVisible;}
	public void setArrowsVisible(boolean bool){
		upperArrpows.setVisible(bool);
		bottomArrows.setVisible(bool);
	}
	
}