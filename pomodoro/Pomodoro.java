import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.*;
public class Pomodoro extends FrameBasics implements ActionListener, Runnable{
	
	
	Thread musicThread;
	ControlWindow ctrlw;
	private Colors colors;
	
	
    JPanel panMain = new JPanel();
	JPanel panTimer = new JPanel();
	JPanel panMusic = new JPanel();
	JPanel panTasklist = new JPanel();
	JPanel panTimerVisuals = new JPanel();
	JPanel panMenu = new JPanel();
	
	
	public JPanel getPanMain(){return panMain;};
	public JPanel getPanTimer(){return panTimer;};
	public JPanel getPanMusic(){return panMusic;};
	public JPanel getPanTasklist(){return panTasklist;};
	public JPanel getPanArrows(){return panTimerVisuals;};
	
	
	TasklistManager tasks;
	PomodoroMusic musicPlayer;
	OptionsMenu options;
	TimerLogic timerLogic;
	TimerVisuals timerVisuals;
	
	
	
	ArrayList<JButton> menuTimerPreset = new ArrayList<JButton>();
	ArrayList<JButton> menuPausePreset = new ArrayList<JButton>();
	int[] timerPresets = new int[]{15,25,30,45,60};
	int[] pausePresets = new int[]{5,10,15,25,30};
	
	public Pomodoro() {
		this.setTitle("Pomodoro Timer");
        this.setSize(1024, 720);
		ImageIcon img = new ImageIcon(getClass().getResource("assets/sprites/icon.png"));
		this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());
        this.setVisible(true);
		
		try {
			Files.createDirectories(Paths.get("./playlist"));
		} catch(IOException e){ }
		
		
		this.colors = Colors.getInstance();
		updateColors();

		this.add(panMain);
		
        panMain.setLayout(new BorderLayout());
		panMain.add(panMenu, BorderLayout.NORTH);
		panMain.add(panTasklist, BorderLayout.EAST);
		panMain.add(panTimerVisuals,BorderLayout.CENTER);
		panMain.add(panMusic,BorderLayout.WEST);
		panMain.add(panTimer,BorderLayout.SOUTH);
		
		panMain.setBackground(clrNormal);
		panMain.setPreferredSize(panMain.getPreferredSize());
	
		options = new OptionsMenu(panMenu, this, menuTimerPreset, menuPausePreset, timerPresets, pausePresets);
		tasks = new TasklistManager(panTasklist, this);
		musicPlayer = new PomodoroMusic(panMusic, this);
		musicThread = new Thread(musicPlayer);
		musicThread.start();
		
		timerLogic = new TimerLogic(panTimer, this);
		timerVisuals = new TimerVisuals(panTimerVisuals, this, timerLogic);
		timerLogic.setVisuals(timerVisuals);
		
		panMain.setBackground(clrNormal);
		panMenu.setBackground(clrNormal);
		options.panMenuContainer.setBackground(clrNormal);
		panTimer.setBackground(clrNormal);
		panTimerVisuals.setBackground(clrNormal);
		panTasklist.setBackground(clrNormal);
		
	
		SwingUtilities.updateComponentTreeUI(this);
		
		//ctrlw = new ControlWindow(this, timerLogic.getTimer());
	}
	
	//Defenitly not meant to do it this way, but javas layout behaviour  is shitty because of not resizable buttons duh!
	public JPanel getNewFlowLayout() {
		JPanel tmp = new JPanel();
		tmp.setLayout( new FlowLayout());
		return tmp;
	};

		
	public void actionPerformed(ActionEvent e) {
		options.actionPerformed(e);
		tasks.actionPerformed(e);
		musicPlayer.actionPerformed(e);
		timerLogic.actionPerformed(e);
		timerVisuals.actionPerformed(e);
	}
	


   public void run()
    {
        while (true)
        {
            try
            {
				if(timerLogic.getTimer().getTimerFinish()) {
					//System.out.println("BOOOM!");
					timerLogic.getTimer().Stop();
					timerLogic.reset();
					musicPlayer.playTimeOver();
				}
                Thread.sleep(1);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Pomodoro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}