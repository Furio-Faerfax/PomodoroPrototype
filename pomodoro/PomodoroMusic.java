import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.nio.file.Files.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
	import java.awt.event.FocusListener;
	import java.awt.event.FocusEvent;
import java.net.URL;
import java.net.URI;
public class PomodoroMusic extends FrameBasics implements ActionListener, Runnable{
	
	public enum MUSICSTATE {
		PLAY,
		PAUSE,
		STOP,
		IDLE,
		LOOPTRACK,
		NONLOOP,
		LOOPPLAYLIST,
		TIMEOVER
	}
	private MUSICSTATE state = MUSICSTATE.IDLE;
	private MUSICSTATE loopState = MUSICSTATE.LOOPPLAYLIST;
	
	
	private JLabel filePathField;
	private JButton playButton;
	private JButton loopButton;
	private JButton nextButton;
	private JPanel panMusic;
	private Pomodoro owner;
	private boolean isPaused = false;
	private boolean isLooping = false;
	private Clip clip;
	
	private File fileTimeOver;
	private Clip clipTimerOver;
	private final String curDir = ".";
	
	private File[] playlist;
	private File currentTrack;
	private File nextTrack;
	private int curTrackID = 0;
	private boolean loopPlaylist = true;
	private boolean autoPlay = true;
	private boolean firstTrackStarted = false;
	private boolean firstTrack = true;
	private boolean musicPlaying = false;
	
	
	ImageIcon playMusicIco = new ImageIcon(this.getClass().getResource("assets/sprites/music_play.png"));
	ImageIcon pauseMusicIco = new ImageIcon(this.getClass().getResource("assets/sprites/music_pause.png"));
	
	ImageIcon replayMusicIco = new ImageIcon(this.getClass().getResource("assets/sprites/music_replay.png"));
	ImageIcon replayDeacMusicIco = new ImageIcon(this.getClass().getResource("assets/sprites/music_replay_deactive.png"));
	ImageIcon replayTrackMusicIco = new ImageIcon(this.getClass().getResource("assets/sprites/music_replay_track.png"));
		


	Color clrMusicTitle = new Color(50,50,50);
	Color clrMusicTitleBox = new Color(30,30,30);
	

	public PomodoroMusic(JPanel panMusic, Pomodoro owner) {
		updateColors();
		this.panMusic = panMusic;
		panMusic.setLayout(new BorderLayout());
		this.owner = owner;
			//super("Music");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ImageIcon img = new ImageIcon("assets/sprites/music_next.png");
		//this.setIconImage(img.getImage());
		this.filePathField = new JLabel();
		
		filePathField.setHorizontalAlignment(JLabel.CENTER);
		filePathField.setVerticalAlignment(JLabel.CENTER);
		filePathField.setForeground(clrText);
		
		//public Image playerSprite = playerSprite = Toolkit.getDefaultToolkit().getImage();

		this.playButton = new EasyButton("", "assets/sprites/music_play.png").getButton();
		this.nextButton = new EasyButton("", "assets/sprites/music_next.png").getButton();
		this.loopButton = new EasyButton("",  "assets/sprites/music_replay.png").getButton();
		this.isPaused = false;
		this.isLooping = false;
		
		this.playButton.addActionListener(owner);
		this.loopButton.addActionListener(owner);
		this.nextButton.addActionListener(owner);
		
		JPanel panMusicContainer = new JPanel();
		panMusicContainer.setLayout(new java.awt.GridLayout(2, 1));
		JPanel box = new JPanel();
		box.setLayout(new FlowLayout());
		box.setBackground(clrTransparent);
		
		
		
		JPanel panMusicComponentContainer = new JPanel();
		
		panMusicComponentContainer.add(playButton);
		panMusicComponentContainer.add(loopButton);
		panMusicComponentContainer.add(nextButton);
		panMusicComponentContainer.setBackground(clrNormal);
		panMusicComponentContainer.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		JPanel titleBox = new JPanel();
		//titleBox.setLayout();
		titleBox.setBackground(clrMusicTitleBox);
		
		JPanel titleInnerBox = new JPanel();
		titleInnerBox.setBackground(clrMusicTitle);
		titleInnerBox.add(filePathField);
		titleBox.add(titleInnerBox);
		
		panMusicContainer.add(titleBox);
		panMusicContainer.add(panMusicComponentContainer);
		panMusicContainer.setBackground(clrMusicTitle);
		
		JPanel innerBox = new JPanel();
		innerBox.setLayout(new FlowLayout());
		innerBox.setBackground(clrNormal);
		innerBox.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		
		JPanel innerInnerBox = new JPanel();
		innerInnerBox.setLayout(new FlowLayout());
		innerInnerBox.setBackground(clrNormal);
		
		
		innerInnerBox.add(panMusicContainer);
		innerInnerBox.setBackground(clrTransparent);
		innerBox.add(innerInnerBox);
		box.add(innerBox);
		this.panMusic.add(box, BorderLayout.SOUTH);
		this.panMusic.setBackground(clrTransparent);
		
		playButton.setPreferredSize(new Dimension(playButton.getIcon().getIconWidth(), playButton.getIcon().getIconHeight()));
		loopButton.setPreferredSize(new Dimension(loopButton.getIcon().getIconWidth(), loopButton.getIcon().getIconHeight()));
		nextButton.setPreferredSize(new Dimension(nextButton.getIcon().getIconWidth(), nextButton.getIcon().getIconHeight()));
        //this.setSize(500, 100);
        //this.setLocationRelativeTo(null);
        //this.setBackground(Color.white);
        //this.setVisible(true);
		
		getAudio();
		if(playlist != null && playlist.length > 2) {
			currentTrack = playlist[curTrackID];
			nextTrack = playlist[curTrackID+1];
			filePathField.setText(getOutputTitle(currentTrack.toString()));
		}
		

	
	}
	
	
	@Override
	public void run()
    {
		while(true) {
			if(autoPlay && state == MUSICSTATE.PLAY && clip != null) {
				if(!clip.isRunning()) {
					if(playlist.length >= 2)
						cyclePlaylist();
				}
			}
			
			//System.out.println("State: "+state+" | music playing: "+musicPlaying);
			if(state == MUSICSTATE.TIMEOVER && !clipTimerOver.isRunning() && musicPlaying) {
				state = MUSICSTATE.PLAY;
				
				playMusic();
			} else if(state == MUSICSTATE.TIMEOVER && !clipTimerOver.isRunning()) {
				state = MUSICSTATE.IDLE;
				
			}
			
			try{
				Thread.sleep(1);
			}catch(InterruptedException e) {}
		}
	}
	
	public void getAudio() {	
		File dir = new File(curDir+"/playlist");
		playlist = dir.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".wav");
			}
		});
		System.out.println("~~Playlist~~");
		for(int i = 0; i < playlist.length; i++) {
		  System.out.println(i+1+": "+getOutputTitle(playlist[i].toString()));
		}
		System.out.println("~~~~~~~~\n");
	}
	
	
	
	//strangely the File isnt replayable when loading in constructor
	public void playTimeOver() {
		
		
		if(clipTimerOver != null) {
			if(clipTimerOver.isRunning()) 
			{
				clipTimerOver.stop();
			}
			clipTimerOver.close();
		}
		
		try (InputStream in = getClass().getResourceAsStream("assets/audio/timeIsOver.wav")) {
			InputStream bufferedIn = new BufferedInputStream(in);
			try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
				clipTimerOver = AudioSystem.getClip();
				clipTimerOver.open(audioIn);
			}}catch (Exception e) {
				e.printStackTrace();
			}
		
		

		if(!clipTimerOver.isRunning()) {
			clipTimerOver.start();
			state = MUSICSTATE.TIMEOVER;
			
			if(clip != null && clip.isRunning()) {
				pauseMusic();
			}
		}
	}
	
	private void cyclePlaylist() {
		state = MUSICSTATE.IDLE;
		if(firstTrackStarted) {
			calcNext();
		}
		
		if(loopPlaylist) {
			playMusic();
		} else {
			filePathField.setText(getOutputTitle(currentTrack.toString()));
			//playButton.setText("Play");
			playButton.setIcon(playMusicIco);
			state = MUSICSTATE.STOP;
		}
		
		if(!firstTrackStarted)
			firstTrackStarted = true;
	}
	
		


	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == playButton) {
			musicPlaying = true;
			if(currentTrack != null){
				if(state == MUSICSTATE.STOP){
					//playButton.setText("Pause");
					playButton.setIcon(pauseMusicIco);
					//System.out.println("Impossible");
					playMusic();
					state = MUSICSTATE.PLAY;
				}else {
					if(!firstTrack)
						isPaused = !isPaused;
					if(isPaused) {
						state = MUSICSTATE.PAUSE;
						musicPlaying = false;
						playButton.setIcon(playMusicIco);
						pauseMusic();
					} else{
						//playButton.setText("Pause");
						playButton.setIcon(pauseMusicIco);
						if(firstTrack) {
							firstTrack = false;
							
						//Calling playMusic here instead strangely leads to a bug where the first track is double started
						//System.out.println(getOutputTitle(currentTrack.toString()));
						openTrack();
						
							if(currentTrack != null) {
								filePathField.setText(getOutputTitle(currentTrack.toString()));
								clip.start();
								state = MUSICSTATE.PLAY;
							}
						} else {
							clip.start();
						}
						state = MUSICSTATE.PLAY;
					}
				}
			}
		}
		
		if(event.getSource() == loopButton) {
			if(clip != null)
				toogleLoop();
		}
		
		if(event.getSource() == nextButton) {
			if(clip != null && clip.isRunning()) {
				clip.stop();
			} 
			
			musicPlaying = true;
			if(playlist.length > 2)
				skipToNext();
		}
	}
	
	private void openTrack() {
		try {
			if(currentTrack != null) {
				File file = new File(currentTrack.getAbsolutePath());
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
				
				clip = AudioSystem.getClip();
				clip.open(audioIn);
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	
	private String getOutputTitle(String title) {
		String tmp = new String(title);
		for(int i = tmp.length()-1; i > 0; i--) {
			if(tmp.charAt(i) == '\\' || tmp.charAt(i) == '/') {
				tmp = tmp.substring(i+1);
				break;
			}
		}
		tmp = tmp.substring(0, tmp.length()-4);
		return tmp;
	}
	
	 private void playMusic() {
		//stopMusic();
		//stopMusic();
		//System.out.println(getOutputTitle(currentTrack.toString()));
		openTrack();
		filePathField.setText(getOutputTitle(currentTrack.toString()));
		clip.start();
		state = MUSICSTATE.PLAY;
	 }
	 
	 private void pauseMusic() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
			isPaused = true;
			//playButton.setText("Resume");
		}
	 }
	 
	 private void stopMusic() {
		state = MUSICSTATE.IDLE;
		if(clip != null && clip.isRunning()) {
			clip.stop();
		} 
	}
	
	public void skipToNext() {
		state = MUSICSTATE.IDLE;
		stopMusic();
		calcNext();		
		playMusic();
		playButton.setIcon(pauseMusicIco);
		isPaused = false;
		if(firstTrack)
			firstTrack = false;
		if(!firstTrackStarted)
			firstTrackStarted = true;
	}
	
	public void calcNext() {
		curTrackID = curTrackID+1 == playlist.length ? 0 : ++curTrackID;
		if(curTrackID == 0) {
			currentTrack = playlist[0];
			nextTrack = playlist[1];
		} else {
			currentTrack = nextTrack;
			nextTrack = curTrackID < playlist.length-1 ? playlist[curTrackID+1] : playlist[curTrackID];
		}
	}

	private void toogleLoop() {
		loopState = loopState == MUSICSTATE.NONLOOP ? MUSICSTATE.LOOPPLAYLIST : loopState == MUSICSTATE.LOOPPLAYLIST ? MUSICSTATE.LOOPTRACK : loopState == MUSICSTATE.LOOPTRACK ? MUSICSTATE.NONLOOP : MUSICSTATE.NONLOOP;
		if(loopState == MUSICSTATE.LOOPPLAYLIST) {
			//System.out.println("Loop Playlist");
			//loopButton.setText("Loop Track");
			loopButton.setIcon(replayMusicIco);
			loopPlaylist = true;
		} else if(loopState == MUSICSTATE.LOOPTRACK) {
			
			//System.out.println("Loop Track");
			//7loopButton.setText("Stop Loop");
			if(clip.isRunning()) {
				
				loopButton.setIcon(replayTrackMusicIco);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} 
		} else {
			//System.out.println("Non-Loop");
			loopPlaylist = false;
			
			loopButton.setIcon(replayDeacMusicIco);
			//loopButton.setText("Loop Playlist");
			if(clip.isRunning()) {
				clip.loop(0);
			}
		}
	}
}