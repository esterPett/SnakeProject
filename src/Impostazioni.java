import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class Impostazioni extends JPanel
{
	private JButton home;
	private JButton indietro;
	private JCheckBox suono;
	private JCheckBox musica;
	private SnakeFrame frame;
	
	private boolean musicOn;
	private boolean suondOn;
	public Impostazioni(SnakeFrame frame)
	{
		this.frame=frame;
		setPreferredSize(new Dimension (200,200) );
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		home=new JButton ("HOME") {
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon home = new ImageIcon(getClass().getResource("home.png"));
				Image bottone = home.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};
		indietro=new JButton("INDIETRO"){
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon home = new ImageIcon(getClass().getResource("INDIETRO_SCORE.png"));
				Image bottone = home.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};		
		
				
		JPanel CBPanel = new JPanel ();
		CBPanel.setLayout(new BoxLayout(CBPanel, BoxLayout.X_AXIS)); // (orientamento orizzontale)
		
		suono = new JCheckBox("SUONO");
		suono.setPreferredSize(new Dimension (80,50));
		
		musica= new JCheckBox("MUSICA");
		musica.setPreferredSize(new Dimension(80,50));
		
		CBPanel.add(Box.createHorizontalStrut(25));
		CBPanel.add(suono);
		CBPanel.add(Box.createHorizontalStrut(50)); //indica i pixel di distanza tra i due checkbox
		CBPanel.add(musica);
		CBPanel.add(Box.createHorizontalStrut(50));
		CBPanel.setBackground(new Color(0,125,0));
		CBPanel.setSize(new Dimension(200,100));
		this.add(Box.createVerticalStrut(200));//Mette spazio fisso tra i panel verticalmente
		this.add(CBPanel);
		this.add(Box.createHorizontalStrut(100));
		
		
		
		JPanel ButtonPanel = new JPanel ();
		ButtonPanel.setLayout(new GridBagLayout());
		ButtonPanel.setSize(new Dimension(150, 150));
        GridBagConstraints gbc =new GridBagConstraints();
	
		gbc.ipady=10;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.anchor=GridBagConstraints.LAST_LINE_END;
		
		gbc.insets=new Insets (2,0,1,2);
		
		gbc.gridx=0;
		gbc.gridy=9;
		
		gbc.weightx=0.01;
		gbc.weighty=0.5;
		
		ButtonPanel.add(home,gbc);
		
		
		gbc.gridx=0;
		gbc.gridy=10;
		
		
		gbc.weightx=0.01;
		gbc.weighty=0.1;
		
		ButtonPanel.add(indietro,gbc);
		ButtonPanel.setBackground(new Color (0,125,0));
		this.add(ButtonPanel);
		this.add(Box.createVerticalStrut(10));
		
		musicOn= true;
		suondOn=true;
		musica.setSelected(true);
		suono.setSelected(true);
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showMenu();
			}
			
		});
		
		indietro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showGame();
			}
			
		});
		
		
		musica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			if (musica.isSelected())
			{
				musicOn=true;
				
			}
			else
			{
				musicOn=false;
			}
			}
			
		});

		suono.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (suono.isSelected())
				{
					suondOn=true;
					
				}
				else
				{
					suondOn=false;
				}
			}
			
		});
		
	}
	public JButton getIndietro ()
	{
		return indietro;
	}
	public boolean getMusic()
	{
		return musicOn;
		
	}
	public boolean getSound()
	{
		return suondOn;
	}
	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		ImageIcon background = new ImageIcon(getClass().getResource("IMPOSTAZIONI_BG.png"));
		Image background3= background.getImage();
		
		g.drawImage(background3,0,0,getWidth(),getHeight(), this);
		
	
}
	
}
