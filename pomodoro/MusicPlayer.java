import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;


public class MusicPlayer extends JFrame implements ActionListener {
	
	
	private JTextField filePathField;
	private JButton playButton;
	private JButton pauseButton;
	private JButton chooseButton;
	private JButton loopButton;
	private boolean isPaused;
	private boolean isLooping = false;
	private JFileChooser fileChooser;
	private Clip clip;
	private final boolean devMode = true;
	
	public MusicPlayer() {
		super("Music");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		
      this.getContentPane().setBackground(Color.BLUE);
		this.filePathField = new JTextField(20);
		this.playButton = new JButton("Play");
		this.pauseButton = new JButton("Pause");
		this.chooseButton = new JButton("Choose File");
		this.loopButton = new JButton("Loop");
		this.isPaused = false;
		this.isLooping = false;
		
		this.playButton.addActionListener(this);
		this.pauseButton.addActionListener(this);
		this.chooseButton.addActionListener(this);
		this.loopButton.addActionListener(this);
		
		this.add(filePathField);
		this.add(chooseButton);
		this.add(pauseButton);
		this.add(playButton);
		this.add(loopButton);
		
		this.fileChooser = new JFileChooser(".");
		this.fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));
		//this.fileChooser.setFileFilter(new FileNameExtensionFilter("OGG Files", "ogg"));
		
        this.setSize(500, 100);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.white);
        this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == playButton) {
			playMusic();
		}
		if(event.getSource() == pauseButton) {
			pauseMusic();
		}
		if(event.getSource() == chooseButton) {
			chooseFile();
		}
		if(event.getSource() == loopButton) {
			toogleLoop();
		}
	}
	
	 private void playMusic() {
		 if(clip != null && clip.isRunning()) {
			clip.stop();
		 }
		 
		 
		 try {
			File file = new File(filePathField.getText());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			if(isLooping) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			
			clip.start();
			
		 }catch(Exception e) {System.out.println(e);}
	 }
	 
	 private void pauseMusic() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
			isPaused = true;
			pauseButton.setText("Resume");
		} else if(clip != null && isPaused) {
			clip.start();
			if(isLooping) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			isPaused = false;
			pauseButton.setText("Pause");
		}
	 }
	 
	 private void chooseFile() {
		 if(devMode)
			fileChooser.setCurrentDirectory(new File("./assets/audio"));
		else
			fileChooser.setCurrentDirectory(new File("."));
		 int result = fileChooser.showOpenDialog(this);
		 if(result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			filePathField.setText(selectedFile.getAbsolutePath());
		 }
	 }
	 private void toogleLoop() {
		isLooping = !isLooping;
		if(isLooping) {
			loopButton.setText("Stop Loop");
			if(clip.isRunning()) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} 
		} else {
				loopButton.setText("Loop");
				if(clip.isRunning()) {
					clip.loop(0);//Clip.loop(3); is running 3times;+
				}
			}
	}
}