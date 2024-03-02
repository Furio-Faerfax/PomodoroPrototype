import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
/** This is just a quick and dirty solution of the problem, that resizing in grid and borderlayout isnt possible but in FLowLayout itz is, duh,
*	its just for effectiveness not efficiency!
* 	IDK why, but generating it in classes without dynamic listm seems to working better than in those which does
*/
public class EasyButton extends FrameBasics implements MouseListener{
	JButton button;
		
	boolean hover = true;

	public EasyButton(String title, String imgPath) {
		updateColors();
		
		//this.clrText = clrText;
		JButton tmp = new JButton(title, new ImageIcon(this.getClass().getResource(imgPath)));
		tmp.setForeground(clrText);
		tmp.setPreferredSize(new Dimension(tmp.getIcon().getIconWidth(), tmp.getIcon().getIconHeight()));
				
		tmp.setHorizontalTextPosition(JButton.CENTER);
		tmp.setVerticalTextPosition(JButton.CENTER);
		
		tmp.setBackground(clrNormal);
        tmp.addMouseListener(this);
		tmp.setBorder(BorderFactory.createLineBorder(clrBorderAccent));
           // tmp.setBorder(new LineBorder(new Color(121,121,121)));
           // tmp.setBorder(BorderFactory.createBevelBorder(1));
          //  tmp.setBorder(BorderFactory.createEmptyBorder(5,10,5,50));
	tmp.setBorderPainted(false);
	//tmp.setFocusPainted(true);
	
	tmp.setContentAreaFilled(false);
		
		button = tmp;
	}
	

	
		@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		button.setBorderPainted(false);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		button.setBorderPainted(true);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
	

	
	public JPanel getPanel() {
		
		JPanel test = getNewFlowLayout();
		test.setBackground(clrNormal);
		test.add(button);
		return test;
	}
	public JButton getButton()  {return button;}
	
	public JPanel getNewFlowLayout() {
		JPanel tmp = new JPanel();
		tmp.setLayout( new FlowLayout());
		return tmp;
	};
	
	
	
}

