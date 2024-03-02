import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.Color;
	import java.awt.event.FocusListener;
	import java.awt.event.FocusEvent;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.nio.file.Paths;
import java.io.FileOutputStream;

import java.nio.file.Files.*;
import java.io.File;
import java.io.*;
public class TasklistManager extends FrameBasics{

	ArrayList<String[]> tasks = new ArrayList<String[]>();
	ArrayList<JButton> taskChecker = new ArrayList<JButton>();
	ArrayList<Boolean> taskChecks = new ArrayList<Boolean>();
	ArrayList<JLabel> taskLabel = new ArrayList<JLabel>();
	private final String taskListExampel = "/The displayable Tasks are not really limited, but there is no scrollbar yet\n/- = Title, 1 Done, 0 not done;-\n-;Titel\n0;Not Done\n/;Not Included\n1;Done";
	JLabel title = new JLabel("Title");
	JButton btnReloadTasks;
	JPanel panTasks = new JPanel();
		JPanel taskContainer = new JPanel();
	JPanel panTasklist;
	
	private Pomodoro owner;
	
		JPanel taskBoxContent;
		JPanel taskBox;
	
	private ImageIcon imgCheckerChecked = new ImageIcon("assets/sprites/checked_task.png");
	private ImageIcon imgCheckerUnchecked = new ImageIcon("assets/sprites/unchecked_task.png");

	public TasklistManager(JPanel panTasklist, Pomodoro owner) {
		updateColors();
		
		JPanel box = new JPanel();
		taskContainer.setLayout(new BorderLayout());
		
		this.panTasklist = panTasklist;
		this.owner = owner;
		
		
		File template = new File("tasklist.txt");
		boolean templateCreated = false;

		try {	
			templateCreated = template.createNewFile();
		} catch(IOException e){ }
		
		try{
			if(templateCreated) {
				PrintWriter out = new PrintWriter(template.getAbsolutePath());
				out.println(taskListExampel);
				out.close();
			}
		} catch(FileNotFoundException e){}
		
		
		
		EasyButton easyReload = new EasyButton("Reload", "assets/sprites/btn.png");
		btnReloadTasks = easyReload.getButton();
		JPanel panReload = easyReload.getPanel();
		
		panTasks.setBackground(clrNormal);
		
		title.setForeground(clrText);
		
		taskBoxContent = new JPanel();
		
		taskBox = new JPanel();
		taskBox.add(taskBoxContent);
		taskBox.setBackground(clrNormal);
		taskBoxContent.setLayout(new BorderLayout());
		taskBoxContent.setBackground(clrNormal);
		
        taskContainer.add(panReload, BorderLayout.NORTH);
		taskContainer.add(taskBox, BorderLayout.CENTER);
		
		taskContainer.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
		
		taskBoxContent.add(title, BorderLayout.NORTH);
		taskBoxContent.add(panTasks, BorderLayout.CENTER);
	
		
		box.setBackground(clrNormal);
		box.add(taskContainer);
		
		this.panTasklist.add(box);
		panTasks.setLayout(new java.awt.GridLayout(0, 1));
       // this.panTasklist.setLayout(new FlowLayout());
       // this.panTasklist.setLayout(new FlowLayout());
		
        btnReloadTasks.addActionListener(owner);
        btnReloadTasks.setPreferredSize(new Dimension(btnReloadTasks.getIcon().getIconWidth(), btnReloadTasks.getIcon().getIconHeight()));
		
		Load();
	}

	
	
	
	public void Reload(JPanel panTasks) {
		this.panTasks = panTasks;
		this.panTasks.setLayout(new java.awt.GridLayout(0, 1));
		tasks = new ArrayList<String[]>();
		taskChecker = new ArrayList<JButton>();
		taskChecks = new ArrayList<Boolean>();
		taskLabel = new ArrayList<JLabel>();
		Load();
	}
	public void Load() {
		try {
			List<String> allLines = Files.readAllLines(Paths.get("tasklist.txt"));

			for (String line : allLines) {
				//System.out.println(line);
			}
			
		for(int i = 0; i < allLines.size(); i++) {
			String[] tmp = allLines.get(i).split(";");
			if(!tmp[0].substring(0,1).equals("/"))
				tasks.add(tmp);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int k = 0; k < tasks.size(); k++) {
			if(tasks.get(k)[0].equals("-")) {
				title.setText(tasks.get(k)[1]);
			} else {
				taskChecks.add(tasks.get(k)[0].equals("1") ? true : false);
				//taskChecker.add(new JButton(tasks.get(k)[0]));
				EasyButton easyBtn = new EasyButton("", taskChecks.get(taskChecks.size()-1) ? "assets/sprites/checked_task.png" : "assets/sprites/unchecked_task.png");
				JButton btn = easyBtn.getButton();
				taskChecker.add(btn);
				JLabel tmp = new JLabel(tasks.get(k)[1]);
				tmp.setForeground(clrText);
				taskLabel.add(tmp);
				
				
				JPanel rowInner = new JPanel();
				
				JPanel row = new JPanel();
				row.add(rowInner);
				row.setLayout(new java.awt.GridLayout(1,1));
				
				rowInner.setLayout(new BorderLayout());
				rowInner.setBackground(clrNormal);
				rowInner.add(easyBtn.getPanel(), BorderLayout.WEST);	

				JPanel labelBox = new JPanel();
				labelBox.add(taskLabel.get(taskLabel.size()-1));
				
				labelBox.setBackground(clrNormal);
				rowInner.add(labelBox, BorderLayout.CENTER);
				row.setBorder(BorderFactory.createLineBorder(clrBorderAccentGrey));
				
	
				
				panTasks.add(row);
				
				taskChecker.get(taskChecker.size()-1).addActionListener(owner);
			}
		}
		

		SwingUtilities.updateComponentTreeUI(owner);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == btnReloadTasks){
			taskBoxContent.remove(panTasks);
			panTasks = new JPanel();
			panTasks.setBackground(clrNormal);
			taskBoxContent.add(panTasks);
			Reload(panTasks);
			//owner.invalidate();
			//owner.validate();
			//owner.repaint();
		}
		
		for(int i = 0; i < taskChecker.size(); i++) {
			if (e.getSource() == taskChecker.get(i)){
				switch(i){
					case 0: triggerCheck(i);break;
					case 1: triggerCheck(i);break;
					case 2: triggerCheck(i);break;
					case 3: triggerCheck(i);break;
					case 4: triggerCheck(i);break;
					case 5: triggerCheck(i);break;
					case 6: triggerCheck(i);break;
					case 7: triggerCheck(i);break;
					case 8: triggerCheck(i);break;
					case 9: triggerCheck(i);break;
					case 10: triggerCheck(i);break;
					case 11: triggerCheck(i);break;
					case 12: triggerCheck(i);break;
					case 13: triggerCheck(i);break;
					default:;break;
				}
			}
		}
	}
	private void triggerCheck(int i) {
		taskChecks.set(i, !taskChecks.get(i));
		taskChecker.get(i).setIcon(taskChecks.get(i) ? imgCheckerChecked : imgCheckerUnchecked);
		//taskChecker.get(i).setText(""+taskChecks.get(i));
	}
}