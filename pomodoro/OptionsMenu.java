
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsMenu extends FrameBasics implements ActionListener{
	
	Pomodoro owner;
	JPanel panMenu;
	JPanel panMenuEntries = new JPanel();
	JPanel panMenuSection1 = new JPanel();
	JPanel panMenuSection2 = new JPanel();
	JPanel panMenuSection3 = new JPanel();
	JPanel panMenuEntriesContainer = new JPanel();
	JPanel panMenuContainer = new JPanel();
	
	
	private ImageIcon imgMenuInactive = new ImageIcon("assets/sprites/menu_inactive.png");
	private ImageIcon imgMenuActive = new ImageIcon("assets/sprites/menu_active.png");
	
	JButton btnMenu = new EasyButton("", "assets/sprites/menu_inactive.png").getButton();

	ArrayList<JButton> menuOptions = new ArrayList<JButton>();
	
	ArrayList<JButton> menuTimerPreset;
	ArrayList<JButton> menuPausePreset;
	int[] timerPresets;
	int[] pausePresets;
		
	private boolean menuActive = false;
	private boolean streamerMode = false;
		
	public OptionsMenu(JPanel panMenu, Pomodoro owner, ArrayList<JButton> menuTimerPreset, ArrayList<JButton> menuPausePreset, int[] timerPresets, int[] pausePresets) {
		updateColors();
		this.panMenu = panMenu;
		this.owner = owner;
		this.menuTimerPreset =  menuTimerPreset;
		this.menuPausePreset = menuPausePreset;
		this.timerPresets = timerPresets;
		this.pausePresets = pausePresets;
		
				panMenuContainer.setLayout( new BorderLayout());
		panMenuContainer.add(btnMenu, BorderLayout.WEST);
		panMenuEntriesContainer.add(panMenuEntries);
		panMenuContainer.add(panMenuEntriesContainer, BorderLayout.SOUTH);
		panMenuEntriesContainer.setLayout(new java.awt.GridLayout(1, 1));
	
		
		panMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		panMenu.add(panMenuContainer);
		
		panMenuEntriesContainer.setVisible(menuActive);
		panMenuSection1.setVisible(menuActive);
		panMenuSection2.setVisible(menuActive);
		panMenuSection3.setVisible(menuActive);
		
		panMenuEntries.setLayout(new java.awt.GridLayout(1, 3));
		panMenuEntries.add(panMenuSection1);
		panMenuSection1.setLayout(new java.awt.GridLayout(6, 1));
		panMenuSection1.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		panMenuEntries.add(panMenuSection2);
		panMenuSection2.setLayout(new java.awt.GridLayout(6, 1));
		panMenuSection2.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		panMenuEntries.add(panMenuSection3);
		panMenuSection3.setLayout(new java.awt.GridLayout(6, 1));
		panMenuSection3.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		EasyButton easyBtn;
		String[] sec1Str = new String[]{"15","30","45","45"};
		String[] sec2Str = new String[]{"5","10","15","25"};
		String[] sec3Str = new String[]{"Arrows","Tasks","Music","Streamer Mode"};
		JLabel options1 = new JLabel("Work");
		JLabel options2 = new JLabel("Pause");
		JLabel options3 = new JLabel("Options");
		
		
		options1.setForeground(clrText);
		options2.setForeground(clrText);
		options3.setForeground(clrText);
		
		JPanel tmp = new JPanel();
		tmp.setLayout( new FlowLayout());
		tmp.setBackground(clrNormal);
		tmp.add(options1);
		panMenuSection1.add(tmp);
		panMenuSection1.setBackground(clrNormal);
		
		tmp = new JPanel();
		tmp.setLayout( new FlowLayout());
		tmp.setBackground(clrNormal);
		tmp.add(options2); 
		panMenuSection2.add(tmp);
		panMenuSection2.setBackground(clrNormal);
		
		tmp = new JPanel();
		tmp.setLayout( new FlowLayout());
		tmp.setBackground(clrNormal);
		tmp.add(options3);
		panMenuSection3.add(tmp);
		panMenuSection3.setBackground(clrNormal);
		
		int j = timerPresets.length >= pausePresets.length ? timerPresets.length : sec3Str.length >= pausePresets.length ? sec3Str.length : pausePresets.length;
		
		for(int i = 0; i < j; i ++) {
			if(i < timerPresets.length) {
				easyBtn = new EasyButton(""+timerPresets[i], "assets/sprites/btn.png");
				menuTimerPreset.add(easyBtn.getButton());
				panMenuSection1.add(easyBtn.getPanel());
			}
			
			if(i < pausePresets.length) {
				easyBtn = new EasyButton(""+pausePresets[i], "assets/sprites/btn.png");
				menuPausePreset.add(easyBtn.getButton());
				panMenuSection2.add(easyBtn.getPanel());
			}
			
			if(i < sec3Str.length) {
				easyBtn = new EasyButton(sec3Str[i], "assets/sprites/btn.png");
				menuOptions.add(easyBtn.getButton());
				panMenuSection3.add(easyBtn.getPanel());
			}
		}
		
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
	}
	
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMenu){
			menuActive = !menuActive;
			btnMenu.setIcon(menuActive ? imgMenuActive : imgMenuInactive);
			panMenuEntriesContainer.setVisible(!panMenuEntriesContainer.isVisible());
			panMenuSection1.setVisible(!panMenuSection1.isVisible());
			panMenuSection2.setVisible(!panMenuSection2.isVisible());
			panMenuSection3.setVisible(!panMenuSection3.isVisible());
		}
		
		for(int i = 0; i < menuTimerPreset.size(); i++) { 
			if (e.getSource() == menuTimerPreset.get(i)){
				owner.timerLogic.getTimer().reset();
				owner.timerLogic.getTimer().setHour(0);
				owner.timerLogic.getTimer().setMinute(timerPresets[i]);
				owner.timerLogic.printTimer(owner.timerLogic.getTimer().getTime());
				owner.timerLogic.updateVisuals();
			}
		}
		for(int i = 0; i < menuPausePreset.size(); i++) {
		
			if (e.getSource() == menuPausePreset.get(i)){
				owner.timerLogic.getTimer().reset();
				owner.timerLogic.getTimer().setMinute(pausePresets[i]);
				owner.timerLogic.getTimer().setHour(0);
				owner.timerLogic.getTimer().setPause();
				owner.timerLogic.printTimer(owner.timerLogic.getTimer().getTime());
				owner.timerLogic.updateVisuals();
			}
		}
		
		for(int i = 0; i < menuOptions.size(); i++) {
			if (e.getSource() == menuOptions.get(i)){
				switch(i){
					case 0:owner.timerVisuals.switchArrowsVisible();break; //Arrows
					case 1:owner.getPanTasklist().setVisible(!owner.getPanTasklist().isVisible());break; //Tasks
					case 2:owner.getPanMusic().setVisible(!owner.getPanMusic().isVisible());break; //Music
					case 3:streamerMode = !streamerMode; 
							if(streamerMode){
								owner.getPanMain().setBackground(clrStreamer);
								panMenu.setBackground(clrStreamer);
								owner.getPanTimer().setBackground(clrStreamer);
								owner.getPanMusic().setBackground(clrStreamer);
								owner.getPanTasklist().setBackground(clrStreamer);
								owner.getPanArrows().setBackground(clrStreamer);
								panMenuContainer.setBackground(clrStreamer);
								owner.timerVisuals.timerVisualPan.setBackground(clrStreamer);
								owner.timerVisuals.bottomArrows.setBackground(clrStreamer);
								owner.timerVisuals.upperArrpows.setBackground(clrStreamer);
								owner.timerVisuals.timerText.setForeground(clrTextStreamer);
							} else {
								owner.getPanMain().setBackground(clrNormal);
								panMenu.setBackground(clrNormal);
								owner.getPanTimer().setBackground(clrNormal);
								owner.getPanMusic().setBackground(clrNormal);
								owner.getPanTasklist().setBackground(clrNormal);
								owner.getPanArrows().setBackground(clrNormal);
								panMenuContainer.setBackground(clrNormal);
								owner.timerVisuals.timerVisualPan.setBackground(clrNormal);
								owner.timerVisuals.bottomArrows.setBackground(clrNormal);
								owner.timerVisuals.upperArrpows.setBackground(clrNormal);
								owner.timerVisuals.timerText.setForeground(clrText);
							};break; //Streamer Mode
					default:;break;
				}
			}
		}
		
	}
}