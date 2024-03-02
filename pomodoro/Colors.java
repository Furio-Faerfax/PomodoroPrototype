import java.awt.Color;
public class  Colors {
	private static Color clrNormal = new Color(0,0,0);
	private static Color clrStreamer = new Color(0,255,0);
	private static Color clrBorderAccent = new Color(255,255,0);
	private static int greyscale = 80;
	private static Color clrBorderAccentGrey = new Color(greyscale, greyscale, greyscale);
	private static Color clrTransparent = new Color(0.0f,0.0f,0.0f,0.0f);
	private static Color clrText = new Color(255,255,255);
	private static Color clrTextStreamer = new Color(0,0,0);
	private static Color clrTest = new Color(255,0,0);
	
	
	public static Color getColorNormal(){return clrNormal;};
	public static Color getColorStreamer(){return clrStreamer;};
	public static Color getColorBorderAccent(){return clrBorderAccent;};
	public static Color getColorBorderAccentGrey(){return clrBorderAccentGrey;};
	public static Color getColorTransparent(){return clrTransparent;};
	public static Color getColorText(){return clrText;};
	public static Color getColorTextStreamer(){return clrTextStreamer;};
	public static Color getColorTest(){return clrTest;};
	
	public void setColorNormal(Color clr){clrNormal = clr;};
	public void setColorStreamer(Color clr){clrStreamer = clr;};
	public void setColorBorderAccent(Color clr){clrBorderAccent = clr;};
	public void setColorBorderAccentGrey(Color clr){clrBorderAccentGrey = clr;};
	public void setColorTransparent(Color clr){clrTransparent = clr;};
	public void setColorText(Color clr){clrText = clr;};
	public void setColorTextStreamer(Color clr){clrTextStreamer = clr;};
	public void setColorTest(Color clr){clrTest = clr;};
	
	private static Colors instance;
	
	private static boolean referenced = false;
	
	private Colors() {
		instance = this;
	}
	
	public static Colors getInstance() {
		if(instance == null) {
			new Colors();
			referenced = true;
			return instance;
		}
		
		return null; 
	}
	
	public static void updateColors(Color clr1, Color clr2, Color clr3, Color clr4, Color clr5) {
		clr1 = clrNormal;
		clr2 = clrStreamer;
		clr3 = clrBorderAccent;
		clr4 = clrTransparent;
		clr5 = clrText;
	}
}