import javax.swing.JFrame;
import java.awt.Color;
public class FrameBasics extends JFrame {
	Color clrNormal;
	Color clrStreamer;
	Color clrBorderAccent;
	Color clrBorderAccentGrey;
	Color clrTransparent;
	Color clrText;
	Color clrTextStreamer;
	
	public void updateColors() {
		this.clrNormal = Colors.getColorNormal();
		this.clrStreamer = Colors.getColorStreamer();
		this.clrBorderAccent = Colors.getColorBorderAccent();
		this.clrBorderAccentGrey = Colors.getColorBorderAccentGrey();
		this.clrTransparent = Colors.getColorTransparent();
		this.clrText = Colors.getColorText();
		this.clrTextStreamer = Colors.getColorTextStreamer();
	}
}