
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class SnakePanel  extends JPanel
{
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	
	private SnakeFrame frame;
	
	public SnakePanel(SnakeFrame frame)
	{
		setPreferredSize(new Dimension(350,350));
		
		setLayout(new GridBagLayout());
		setBackground(Color.BLACK);
		
		this.frame = frame;

		Border bordoInterno = BorderFactory.createTitledBorder("Menu");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0,5,5,5);
		Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		setBorder(bordoFinale);
		
		button1 = new JButton("Gioca")
				{
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon gioca = new ImageIcon(getClass().getResource("NUOVA PARTITA.png"));
				Image bottone = gioca.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};
				button1.setPreferredSize(new Dimension (30,50));
		
		button2	= new JButton ("Difficoltà")
		{
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon difficolta = new ImageIcon(getClass().getResource("DIFFICOLTA.png"));
				Image bottone = difficolta.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};
		button2.setPreferredSize(new Dimension (30,50));
		button3 = new JButton ("Punteggio")
		{
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon score = new ImageIcon(getClass().getResource("PUNTEGGIO.png"));
				Image bottone = score.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};
		button3.setPreferredSize(new Dimension (30,50));
		button4	= new JButton ("Impostazioni")
		{
			protected void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				ImageIcon settings = new ImageIcon(getClass().getResource("IMPOSTAZIONI.png"));
				Image bottone = settings.getImage();
				g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
			}
				};
		
		button4.setPreferredSize(new Dimension (30,50));

		
		
		
		GridBagConstraints gbc =new GridBagConstraints();
		
		//Grandezza dei bottoni
		
		gbc.ipady=30;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.anchor=GridBagConstraints.CENTER;
		
		gbc.insets=new Insets (4,0,3,4);
		//Button1
		gbc.gridx=1;
		gbc.gridy=0;
		
		gbc.weightx=0.01;
		gbc.weighty=0.1;
		
		add(button1,gbc);
		
		//Button2
		gbc.gridx=1;
		gbc.gridy=1;
		
		
		gbc.weightx=0.01;
		gbc.weighty=0.1;
		
		add(button2,gbc);
		
		//Button3
		gbc.gridx=1;
		gbc.gridy=2;
		
		gbc.weightx=0.01;
		gbc.weighty=0.1;
		
		add(button3,gbc);
		
		//Button4
		gbc.gridx=1;
		gbc.gridy=3;
		
		gbc.weightx=0.01;
		gbc.weighty=0.1;
		
		add(button4,gbc);
		
		
		
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {			

			frame.getGamePanel().inizioGioco();
			frame.showGame();
			
			}
			
		});
		
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showDifficolta();
			}
			
		});
		
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showScore();
			}
			
		});
		
		
		button4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showImpostazioni();
			}
			
		});
		
	}
	
	
		
		
	
	
}
