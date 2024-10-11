import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DifficoltaPanel extends JPanel 
{

		private JButton indietro;
		private JComboBox menuDifficolta;
		private String[] sceltaDifficolta = { "Facile", "Medio","Difficile"};
		
		private SnakeFrame frame;
		private GamePanel game;
		public DifficoltaPanel(SnakeFrame frame, GamePanel game)
		{
			this.frame=frame;
			this.game=game;
			setPreferredSize(new Dimension (200,200) );
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			
			menuDifficolta = new JComboBox(sceltaDifficolta);
			menuDifficolta.setPreferredSize(new Dimension (40,40));
			menuDifficolta.setSelectedIndex(0);
			
			
			
			menuDifficolta.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							if(menuDifficolta.getSelectedIndex() == 0)
							{
								game.setDelay(400);
							}
							if(menuDifficolta.getSelectedIndex() == 1)
							{
								game.setDelay(300);
							}
							if(menuDifficolta.getSelectedIndex() == 2)
							{
								game.setDelay(200);
							}
						}
				
					});
				
			
			
			JPanel menuP = new JPanel ();
			
			menuP.setLayout(new BoxLayout(menuP, BoxLayout.X_AXIS));
			menuP.setPreferredSize(new Dimension(50,50));
			menuP.add(menuDifficolta);
			this.add(Box.createVerticalStrut(300));
			this.add(menuP);
			this.add(Box.createHorizontalStrut(50));
			JPanel indietroP = new JPanel();
			indietroP.setPreferredSize(new Dimension (50,50));
			indietroP.setLayout(new GridBagLayout());
			this.add(Box.createVerticalStrut(50));
			indietroP.setBackground(new Color (0,125,0));
			
			indietro = new JButton("INDIETRO"){
				protected void paintComponent (Graphics g)
				{
					super.paintComponent(g);
					ImageIcon indietro = new ImageIcon(getClass().getResource("INDIETRO_SCORE.png"));
					Image bottone = indietro.getImage();
					g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
				}
					};
			 GridBagConstraints gbc =new GridBagConstraints();
				
			
				
				gbc.ipady=30;
				gbc.fill=GridBagConstraints.HORIZONTAL;
				gbc.anchor=GridBagConstraints.LAST_LINE_END;
				
				gbc.insets=new Insets (2,0,1,2);
			
				gbc.gridx=1;
				gbc.gridy=4;
				
				gbc.weightx=0.01;
				gbc.weighty=0.1;
			indietroP.add(indietro,gbc);
			this.add(indietroP);
			this.add(Box.createHorizontalStrut(500));
			
			
			
			indietro.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					frame.showMenu();
				}
				
			});
		}
		
		@Override
		protected void paintComponent (Graphics g)
		{
			super.paintComponent(g);
			ImageIcon background = new ImageIcon(getClass().getResource("DIFFICOLTA_BG.png"));
			Image background3= background.getImage();
			
			g.drawImage(background3,0,0,getWidth(),getHeight(), this);
			
		
	}	
		
}
