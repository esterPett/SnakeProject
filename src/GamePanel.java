import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import javax.sound.sampled.*;

public class GamePanel  extends JPanel implements ActionListener
{
	// TODO cambiare tutti i public della classe serpente in private
	private JPanel panelSettings;
	private JPanel panelScore;
	private JLabel scoreLabel;
	private JButton settings;
	private JButton pausa;
	private SnakeFrame frame;
	private Serpente serpente;
	private ScorePanel scorePanel;
	private Impostazioni settingsPanel;
	private int lung;
	private int[][] mat;
	//Grandezza  matrice
	private int size = 25; //quanto grandi saranno gli elementi nel pannello del gioco
	private int game =(600*600)/(size*size);//quanti oggetti posso avere nel pannelo
	//(attuali (size = 25, dimensioni = 600*600) = 576 quadrati)
	
									//vengono prese come riferimento le stesse  misure del di altezza elarghezza del frame
	
	// Delay per la velocità del gioco
	private int  delay = 500;
	
	//Mi salvo le coordinate della testa del serpente
	private int testaX;
	private int testaY;
	
	//Contatore mele mangiate
	private int meleMangiate=0; 
	//Posizione mela
	private int melaX;
	private int melaY;
	
	
	// Variabili per la direzione del serpente 
	private boolean left  = false;
	private boolean right = true;
	private boolean up    = false;
	private boolean down  = false;
	
	//Tasto play/pausa 
	private boolean pause;
	//Stato del gioco
	private boolean stato = false;
	private boolean gameOver=false;
	

	private Timer timer;
	private Random random;

	private Clip clip;
	private Clip appleClip;
	private Clip overClip;
	
	public GamePanel(SnakeFrame frame, ScorePanel scorePanel,Impostazioni settingsPanel)
	{
		this.frame=frame;
		this.scorePanel=scorePanel;
		this.settingsPanel=settingsPanel;
		random = new Random ();
		setPreferredSize(new Dimension(600,600));
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setFocusable(true);
		frame.addKeyListener(new MyKeyAdapter());
		
		panelSettings= new JPanel();
		panelScore= new JPanel();
		settings= new JButton ("IMPOSTAZIONI");
		pausa=new JButton("PAUSA");
		
		panelScore.setBackground(new Color(0,125,0));
		panelScore.setPreferredSize(new Dimension(600,50));
		
		
		panelSettings.setBackground(new Color (0,125,0));
		panelSettings.setPreferredSize(new Dimension(600,50 ));
		panelSettings.setLayout(new BoxLayout(panelSettings, BoxLayout.X_AXIS));
		panelSettings.add(Box.createHorizontalStrut(150));
		panelSettings.add(settings);
		panelSettings.add(Box.createHorizontalStrut(100));
		panelSettings.add(pausa);
		panelSettings.add(Box.createHorizontalStrut(200));
		this.add(panelSettings);
		
		scoreLabel=new JLabel("PUNTEGGIO:   ");
		scoreLabel.setForeground(Color.BLACK);
		panelScore.add(scoreLabel);
		
		
		
		add(panelScore,BorderLayout.PAGE_START);

		add(panelSettings,BorderLayout.PAGE_END);
		
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.showImpostazioni();
			}
			
		});
		
		pausa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning())
				{
					timer.stop();

				}	
				else
				{
					timer.start();
					frame.requestFocusInWindow();
				}
				
			
			}
			
		});
		
		
		mat= new int[18][23];

		soundHandler();
		clip.stop();

	}
	public void setDelay ( int newDelay)
	{
		this.delay=newDelay;
	}
	public boolean getStato ()
	{
		return stato;
	}
	public void inizioGioco()
	{
		scoreLabel.setText("PUNTEGGIO: 0");
		
		//Imposto lo stato del gioco in corso 
		stato=true;
		pause = false; //come inizia il gioco il tasto pausa 
		if(timer!=null)
		{
			timer.stop();
			
		}
		timer= new Timer (delay, this);
		timer.start();
		gameOver= false;
		clip.setMicrosecondPosition(0);
		//genero una posizione casuale per la mela
		nuovaMela();
		serpente = new Serpente();
		//resetto le posizioni
		up=false;
		left=false;
		down=false;
		right=true;
		lung=4;
		for (int i=0;i<18;i++)
		{
			for(int j=0;j<23;j++)
		{
			if(j==melaX && i==melaY)
			{
				mat[i][j]=3; //identifico con 3 la mela 
			}
			else if (serpente.isSnakeThereX(j) && serpente.isSnakeThereY(i))
			{
				mat[i][j]=2; //identifico 2 con il getCorpo() del serpente
				if (j==serpente.getCorpo().get(0).x && i==serpente.getCorpo().get(0).y)
				{
					mat[i][j]=1; //dentifico con 1 la testa del serpente
					testaX=j;
					testaY=i;
				}
					
			}
			else
			{
				mat[i][j]=0;
			}
			
		}
		}
		
		

	}
	public void Tick ()
	{
		spostamento();
		repaint();
		if (gameOver)
		{
			gameOver();
		}
		if (settingsPanel.getMusic() && !gameOver)
		{
			clip.start();
		}
		else
		{
			clip.stop();
		}
		if (!clip.isRunning())
		{
			clip.setMicrosecondPosition(0);
		}
		
		
	}
	//Funzione che mi permette di creare sempre una nuova mela
	public void nuovaMela()
	{
		
		random = new Random();
		do
		{
			melaX=random.nextInt(23);
			melaY=random.nextInt(18);
		
		}
		while(mat[melaY][melaX]==2 || mat[melaY][melaX]==1);
		 mat[melaY][melaX]=3;
		
	
		
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		disegno(g);
		if (gameOver)
		{
			g.setColor(new Color (139,0,139));
			g.setFont(new Font("DialogInput", Font.BOLD,75));
			FontMetrics metrics = getFontMetrics(g. getFont());
			g.drawString("GAME OVER :(", (600-metrics.stringWidth("GAME OVER :("))/2, 600/2);
		}
		
	}
	public void gameOver ()
	{
		timer.stop();
		clip.stop();
		if (settingsPanel.getSound())
		{
			overClip.start();
		}
		 Timer timer1 = new Timer (3000, new ActionListener() {
			 public void actionPerformed (ActionEvent e)
			 {
				 	String  nome = JOptionPane.showInputDialog(frame, "INSERIRE IL NOME DEL GIOCATORE:");
					
					if(nome==null)
					{
						nome = ("Anonymous");
					}
					scorePanel.writeFile(nome, lung-4);
					frame.showMenu();
					
			 }
			
				
		 });
		 timer1.setRepeats(false);
		 timer1.start();
		 
	}
	public void disegno (Graphics g)
	{	//Disegno la griglia
		for(int i=0;i<600/size;i++)
		{
			g.drawLine(5,60+i*size,580,60+i*size);//righe orizzontali
			g.drawLine(5+i*size,60, 5+i*size, 510);//rihhe verticali
			
		}
		//Disegno la matrice
		for(int i=0;i<18;i++)
		{
			for(int j=0;j<23;j++)
		{
			if(mat[i][j]==1)
			{
				g.setColor(new Color ( 0,100,0));
				g.fillRect((j*size)+5,(i*size)+60,size, size);
			}
			else if( mat[i][j]==2) //disegno il serpente
			{
				g.setColor(new Color (34,139,34));
				g.fillRect((j*size)+5,(i*size)+60,size, size);

			}
			else if(mat[i][j]==3) //disegno la mela
			{
				g.setColor(Color.RED);
				g.fillOval((j*size)+5, (i*size)+60, size, size);

			}
		
		}
		
			
		}
	}
	public void spostamento()
	{
		int codaX=serpente.getCorpo().get(lung-1).x;
		int codaY = serpente.getCorpo().get(lung-1).y;
		if(controllaSpostamento())
		{
			
			if (right)
			{
				if(testaX+1<23)
				{
					mat[testaY][testaX+1]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					serpente.getCorpo().get(0).x+=1;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaX=testaX+1;
				}
				else if(testaX+1==23)
				{
					mat[testaY][0]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).x=0;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaX=0;
				}
			}
			else if(left)
			{
				if(testaX-1>=0)
				{
					mat[testaY][testaX-1]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).x-=1;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaX=testaX-1;
				}
				else if(testaX-1==-1)
				{
					mat[testaY][22]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).x=22;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaX=22;
				}
			}
			else if(down)
			{
				if(testaY+1<18)
				{
					mat[testaY+1][testaX]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).y+=1;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaY=testaY+1;
				
				}
				else if(testaY+1==18)
				{
					mat[0][testaX]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).y=0;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaY=0;
				}
				
			}
			else if (up)
			{
				if(testaY-1>=0)
				{
					mat[testaY-1][testaX]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).y-=1;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaY=testaY-1;
				
				}
				else if(testaY-1==-1)
				{
					mat[17][testaX]= 1;
					mat[testaY][testaX]=2;
					mat[serpente.getCorpo().get(lung-1).y][serpente.getCorpo().get(lung-1).x]=0;
					
					serpente.getCorpo().get(0).y=17;
					Point body = new Point(testaX,testaY);
					serpente.getCorpo().add(1,body);
					serpente.getCorpo().remove(lung);
					testaY=17;
					}
				
				
			}
			if((testaX==melaX) && (testaY==melaY))
			{
				mela(codaX,codaY);
			}	
		}
		else
		{
			gameOver=true;
		}
	}
	public boolean controllaSpostamento()
	{
		if (right)
		{
			if(testaX+1<23 &&mat[testaY][testaX+1]==2)
			{
				return false;
			}
			else if(testaX+1==23 && mat[testaY][0]==2)
			{
				return false;
			}
		}
		else if(left)
		{
			if(testaX-1>=0 &&mat[testaY][testaX-1]==2)
			{
				return false;
			}
			else if(testaX-1==-1 && mat[testaY][22]==2)
			{
				return false;
			}
		}
		else if (up)
		{
			if(testaY-1>=0 &&mat[testaY-1][testaX]==2)
			{
				return false;
			}
			else if(testaY-1==-1 && mat[17][testaX]==2)
			{
				return false;
			}
		}
		else if(down)
		{
			if(testaY+1<18 &&mat[testaY+1][testaX]==2)
			{
				
				return false;
			}
			else if(testaY+1==18 && mat[0][testaX]==2)
			{
				
				return false;
			}
		}
		return true;
			
		
	}
	//Funzione che mi permette di controllare quando viene mangiata la mela
	public void mela  (int codaX,int codaY)
	{
		if(settingsPanel.getSound())
		{
			appleClip.setMicrosecondPosition(1200000);
			appleClip.start();
			
		}
		
		Point body = new Point (codaX,codaY);
		lung++;
		serpente.getCorpo().add(body);
		mat[codaY][codaX]=2;
		nuovaMela();
		scoreLabel.setText("PUNTEGGIO: "+(lung-4));
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{
		if(stato)
		{
			
			Tick();
		}
	
	}
	 
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void  keyPressed (KeyEvent e)
		{
			 
			if(e.getKeyCode()==KeyEvent.VK_UP && (!down))
			{
				
				
				up=true;
				down=false;
				left=false;
				right=false;
				
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN &&(!up))
			{
					up=false;
					down=true;
					left=false;
					right=false;
			}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right))
						{
						up=false;
						down=false;
						left=true;
						right=false;
						}
					else if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left))
						{
							up=false;
							down=false;
							left=false;
							right=true;
						}
					}
					}
					
			
	public void soundHandler ()
	{
		File music = new File ("Music\\Snake-sound.wav");
		File soundApple = new File ("Music\\eatApple.wav");
		File soundOver = new File ("Music\\Sad-Game-Over-Sound-Effect.wav");
		
		try {
			AudioInputStream aisMusic = AudioSystem.getAudioInputStream(music);
			clip = AudioSystem.getClip();
			clip.open(aisMusic);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			AudioInputStream aisApple = AudioSystem.getAudioInputStream(soundApple);
			appleClip = AudioSystem.getClip();
			appleClip.open(aisApple);
			AudioInputStream aisOver = AudioSystem.getAudioInputStream(soundOver);
			overClip = AudioSystem.getClip();
			overClip.open(aisOver);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
}


	
	
	
	 

