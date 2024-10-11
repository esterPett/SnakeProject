import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;


public class ScorePanel extends JPanel 
{
	private JButton indietro;
	private SnakeFrame frame;
	private JLabel punteggi;
	private JPanel punteggiPanel;
	class Punteggio implements Comparable<Punteggio>
	{
		protected int score;
		protected String nome;
		
		public int compareTo(Punteggio x)
		{
			return this.score-x.score;
		}
		
		
	}
	private ArrayList <Punteggio> classifica = new ArrayList <Punteggio> ();
		public ScorePanel(SnakeFrame frame)
		{
			readFile();
			setPreferredSize(new Dimension (200,200));
			setLayout(new GridBagLayout());
			
			
			indietro = new JButton ("INDIETRO") {
				protected void paintComponent (Graphics g)
				{
					super.paintComponent(g);
					ImageIcon indietro = new ImageIcon(getClass().getResource("INDIETRO_SCORE.png"));
					Image bottone = indietro.getImage();
					g.drawImage(bottone, 0, 0, getWidth(), getHeight(), this);
				}
					};
			
				
			
			indietro.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					frame.showMenu();
				}
				
			});
			
			punteggiPanel = new JPanel();
			punteggiPanel.setLayout(new BoxLayout(punteggiPanel,BoxLayout.Y_AXIS));
			punteggiPanel.setSize(new Dimension (600,600));
			punteggiPanel.setBackground(new Color (0,125,0));
			
			JScrollPane scrollPane= new JScrollPane(punteggiPanel);
			scrollPane.setPreferredSize(new Dimension (600,500));
			
			aggiornaPanel();
		
			GridBagConstraints gbc =new GridBagConstraints();
			
			gbc.ipady=300;
			gbc.fill=GridBagConstraints.HORIZONTAL;
			gbc.anchor=GridBagConstraints.CENTER;
			
			gbc.insets=new Insets (4,0,3,4);
			
			gbc.gridx=1;
			gbc.gridy=2;
			
			gbc.weightx=0.01;
			gbc.weighty=0.2;
			add(scrollPane,gbc);
			
			gbc.ipady=30;
			gbc.gridx=1;
			gbc.gridy=4;
			
			gbc.weightx=0.01;
			gbc.weighty=0.1;
			add(indietro,gbc);
		}
		public void aggiornaPanel ()
		{
			punteggiPanel.removeAll();
			for (Punteggio score : classifica)
			{
				punteggi = new JLabel();
				punteggi.setFont( new Font("DialogInput",Font.BOLD,28));
				punteggi.setForeground(Color.BLACK);
				punteggi.setText(score.nome+ ":"+ score.score);
				punteggiPanel.add(punteggi);
				
				
				
			}
		
		}
		public void readFile()
		{
			classifica.clear();
			try {
				BufferedReader reader = new BufferedReader ( new FileReader ("FileScore\\punteggio.txt"));
				
				String riga;
				while ((riga=reader.readLine())!= null)
				{
					Punteggio score = new Punteggio ();
					String[] tmp = riga.split(" ");
					score.nome=tmp[0];
					score.score= Integer.parseInt(tmp[1]);
					classifica.add(score);
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Collections.sort(classifica, Collections.reverseOrder());
			
			
		}
		public void writeFile (String nome, int score)
		
		{
			Punteggio newScore = new Punteggio ();
			newScore.nome=nome;
			newScore.score=score;
			
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter ("FileScore\\punteggio.txt", true));
				writer.write(nome+" "+score+"\n");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			readFile();
			//aggiorna il file e il panel
			aggiornaPanel();
		}
		
		@Override
		protected void paintComponent (Graphics g)
		{
			super.paintComponent(g);
			ImageIcon background = new ImageIcon(getClass().getResource("SCORE_BG.png"));
			Image background3= background.getImage();
			
			g.drawImage(background3,0,0,getWidth(),getHeight(), this);
			
		
	}
}
