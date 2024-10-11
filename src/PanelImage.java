import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PanelImage extends JPanel
{
	public PanelImage()
	{
		setPreferredSize(new Dimension(250,250));
		
	}
	
	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		ImageIcon background = new ImageIcon(getClass().getResource("sfondo.jpg"));
		Image background2 = background.getImage();
		
		g.drawImage(background2,0,0,getWidth(),getHeight(), this);
		}
}

