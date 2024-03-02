import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;

public class ControlWindow extends JFrame implements ActionListener{
	
	
    JButton btnStart = new JButton("Start");
	JButton btnPause = new JButton("Pause");
	JButton btnReset = new JButton("Reset");
	JButton btnReloadTasks = new JButton("Reload");
	ArrayList<JButton> tasklist = new ArrayList<JButton>();
	JButton btnMusicPlay = new JButton("Play");
	JButton btnMusicPause = new JButton("Pause");
	JButton btnMusicNext = new JButton("Next");
	JButton btnArrowHourUp = new JButton("H Up");
	JButton btnArrowHourDown = new JButton("H Down");
	JButton btnArrowMinuteUp = new JButton("M Up");
	JButton btnArrowMinuteDown = new JButton("M Down");
	JButton btnMenu = new JButton("Menu");
	ArrayList<JButton> menuTimerPreset = new ArrayList<JButton>();
	ArrayList<JButton> menuPausePreset = new ArrayList<JButton>();
	ArrayList<JButton> menuOptions = new ArrayList<JButton>();
	
    JPanel panMain = new JPanel();
	JPanel panTimer = new JPanel();
	JPanel panMusic = new JPanel();
	JPanel panTasklist = new JPanel();
	JPanel panArrows = new JPanel();
	JPanel panMenu = new JPanel();
	JPanel panMenuEntries = new JPanel();
	JPanel panMenuSection1 = new JPanel();
	JPanel panMenuSection2 = new JPanel();
	JPanel panMenuSection3 = new JPanel();
	int[] timerPresets = new int[]{15,25,30,45};
	int[] pausePresets = new int[]{5,10,15,25};
	
	Timer timer;
	Pomodoro owner;
	
	
	
	public ControlWindow(Pomodoro owner, Timer timer) {
		this.owner = owner;
		this.timer = timer;
		
		this.setTitle("Control Window");
        this.setSize(500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.white);
        this.setVisible(true);
        this.getContentPane().setLayout(new BorderLayout());
		
		
		this.add(panMain);
		panMain.add(panMenu);
		panMain.add(panTasklist);
		panMain.add(panArrows);
		panMain.add(panMusic);
		panMain.add(panTimer);
		
        panTimer.add(btnStart);
        panTimer.add(btnPause);
        panTimer.add(btnReset);
        panTasklist.add(btnReloadTasks);
        panMusic.add(btnMusicPlay);
        panMusic.add(btnMusicPause);
        panMusic.add(btnMusicNext);
        panArrows.add(btnArrowHourUp);
        panArrows.add(btnArrowHourDown);
        panArrows.add(btnArrowMinuteUp);
        panArrows.add(btnArrowMinuteDown);
        panMenu.add(btnMenu);
		panMenu.add(panMenuEntries);
		panMenuEntries.add(panMenuSection1);
		panMenuEntries.add(panMenuSection2);
		panMenuEntries.add(panMenuSection3);
        //this.add(menuPausePreset);
        //this.add(menuOptions);
        //this.add(tasklist);
		
		menuTimerPreset.add(new JButton("15"));
		panMenuSection1.add(menuTimerPreset.get(menuTimerPreset.size()-1));
		menuTimerPreset.add(new JButton("25"));
		panMenuSection1.add(menuTimerPreset.get(menuTimerPreset.size()-1));
		menuTimerPreset.add(new JButton("30"));
		panMenuSection1.add(menuTimerPreset.get(menuTimerPreset.size()-1));
		menuTimerPreset.add(new JButton("45"));
		panMenuSection1.add(menuTimerPreset.get(menuTimerPreset.size()-1));
		
		menuPausePreset.add(new JButton("5"));
		panMenuSection2.add(menuPausePreset.get(menuPausePreset.size()-1));
		menuPausePreset.add(new JButton("10"));
		panMenuSection2.add(menuPausePreset.get(menuPausePreset.size()-1));
		menuPausePreset.add(new JButton("15"));
		panMenuSection2.add(menuPausePreset.get(menuPausePreset.size()-1));
		menuPausePreset.add(new JButton("25"));
		panMenuSection2.add(menuPausePreset.get(menuPausePreset.size()-1));
		
		menuOptions.add(new JButton("Arrows"));
		panMenuSection3.add(menuOptions.get(menuOptions.size()-1));
		menuOptions.add(new JButton("Tasks"));
		panMenuSection3.add(menuOptions.get(menuOptions.size()-1));
		menuOptions.add(new JButton("Music"));
		panMenuSection3.add(menuOptions.get(menuOptions.size()-1));
		menuOptions.add(new JButton("Sounds"));
		panMenuSection3.add(menuOptions.get(menuOptions.size()-1));
		
		
		
		
        btnStart.addActionListener(this);
        btnPause.addActionListener(this);
        btnReset.addActionListener(this);
        btnReloadTasks.addActionListener(this);
        btnMusicPlay.addActionListener(this);
        btnMusicPause.addActionListener(this);
        btnMusicNext.addActionListener(this);
        btnArrowHourUp.addActionListener(this);
        btnArrowHourDown.addActionListener(this);
        btnArrowMinuteUp.addActionListener(this);
        btnArrowMinuteDown.addActionListener(this);
        btnMenu.addActionListener(this);
		
		for(int i = 0; i < menuTimerPreset.size(); i++) {
			menuTimerPreset.get(i).addActionListener(this);
		}
		
		for(int i = 0; i < menuPausePreset.size(); i++) {
			menuPausePreset.get(i).addActionListener(this);
		}
		
		for(int i = 0; i < menuOptions.size(); i++) {
			menuOptions.get(i).addActionListener(this);
		}
		
		
		
		
        btnStart.setPreferredSize(new Dimension(100, 30));
        btnPause.setPreferredSize(new Dimension(100, 30));
        btnReset.setPreferredSize(new Dimension(100, 30));
        btnReloadTasks.setPreferredSize(new Dimension(100, 30));
        btnMusicPlay.setPreferredSize(new Dimension(100, 30));
        btnMusicPause.setPreferredSize(new Dimension(100, 30));
        btnMusicNext.setPreferredSize(new Dimension(100, 30));
        btnArrowHourUp.setPreferredSize(new Dimension(100, 30));
        btnArrowHourDown.setPreferredSize(new Dimension(100, 30));
        btnArrowMinuteUp.setPreferredSize(new Dimension(100, 30));
        btnArrowMinuteDown.setPreferredSize(new Dimension(100, 30));
        btnMenu.setPreferredSize(new Dimension(100, 30));
		
		for(int i = 0; i < menuTimerPreset.size(); i++) {
			menuTimerPreset.get(i).setPreferredSize(new Dimension(100, 30));
		}
		
		for(int i = 0; i < menuTimerPreset.size(); i++) {
			menuTimerPreset.get(i).setPreferredSize(new Dimension(100, 30));
		}
		
		for(int i = 0; i < menuTimerPreset.size(); i++) {
			menuTimerPreset.get(i).setPreferredSize(new Dimension(100, 30));
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart){
			
		}
        if (e.getSource() == btnPause){
			
		}
        if (e.getSource() == btnReset){
			
		}
        if (e.getSource() == btnReloadTasks){
			
		}
        if (e.getSource() == btnMusicPlay){
			
		}
        if (e.getSource() == btnArrowHourUp){
			timer.addHour(1);
			//owner.printTimer();
		}
        if (e.getSource() == btnArrowHourDown){
			timer.addHour(-1);
			//owner.printTimer();
			
		}
        if (e.getSource() == btnArrowMinuteUp){
			timer.addMinute(1);
			//owner.printTimer();
			
		}
        if (e.getSource() == btnArrowMinuteDown){
			timer.addMinute(-1);
			//owner.printTimer();
			
		}
		
        if (e.getSource() == btnMenu){
			
		}
		
		for(int i = 0; i < menuTimerPreset.size(); i++) { 
			if (e.getSource() == menuTimerPreset.get(i)){
				timer.reset();
				timer.setHour(timerPresets[i]);
				timer.setMinute(0);
			//owner.printTimer();
			}
		}
		for(int i = 0; i < menuPausePreset.size(); i++) {
		
			if (e.getSource() == menuPausePreset.get(i)){
				timer.reset();
				timer.setHour(pausePresets[i]);
				timer.setMinute(0);
				timer.setPause();
			// owner.printTimer();
			}
		}
		
		for(int i = 0; i < menuOptions.size(); i++) {
			if (e.getSource() == menuOptions.get(i)){
				switch(i){
					case 0:;break;
					case 1:;break;
					case 2:;break;
					case 3:;break;
					default:;break;
				}
			}
		}
		
	}
	

}