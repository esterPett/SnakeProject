import java.awt.*;
import java.net.URL;

import javax.swing.*;


public class SnakeFrame  extends JFrame
{
	private Impostazioni settingsPanel;
	private SnakePanel panel;
	private DifficoltaPanel difficoltaPanel;
	private PanelImage panel2;
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private JPanel menuPanel;
	private static JPanel cardPanel;
	public SnakeFrame (String s)
	{
		super (s);
	
		setLayout(new BorderLayout());
		setResizable(false);
		setFocusable(true);
		panel=new SnakePanel(this);
		panel2=new PanelImage();
		scorePanel=new ScorePanel(this);
		settingsPanel = new Impostazioni(this);
		gamePanel=new GamePanel(this,scorePanel, settingsPanel);
		menuPanel=new JPanel(new BorderLayout());
		CardLayout cl = new CardLayout();
		cardPanel= new JPanel(cl);
	
		difficoltaPanel= new DifficoltaPanel(this, gamePanel);
		//applico l'icona alla finestra
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/snake_icon.jpg")));
		
		menuPanel.add(panel, BorderLayout.PAGE_END);
		menuPanel.add(panel2,BorderLayout.PAGE_START);
		
		cardPanel.add(menuPanel, "menu");
		cardPanel.add(settingsPanel, "impostazioni");
		cardPanel.add(difficoltaPanel,"difficolta");
		cardPanel.add(gamePanel,"game");
		cardPanel.add(scorePanel, "score");
		add(cardPanel);
		
		inizializzazione();
	}
	
	
	public void inizializzazione ()
	{
		setSize(new Dimension (600,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);//quando premo x chiudo il programma
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public JPanel getCardPanel()
	{
		return cardPanel;
	}
	
	public void showImpostazioni()
	{
		CardLayout cl = (CardLayout) this.getCardPanel().getLayout();
		cl.show(cardPanel, "impostazioni");
		if(gamePanel.getStato())
		{
			settingsPanel.getIndietro().setEnabled(true);
		}
		else
		{
			settingsPanel.getIndietro().setEnabled(false);
		}
	}
	public void showMenu()
	{
		CardLayout cl= (CardLayout) this.getCardPanel().getLayout();
		cl.show(cardPanel, "menu");
	}
	public void showDifficolta()
	{
		CardLayout cl= (CardLayout) this.getCardPanel().getLayout();
		cl.show(cardPanel, "difficolta");
	}
	
	public void showGame()
	{
		CardLayout cl= (CardLayout) this.getCardPanel().getLayout();
		cl.show(cardPanel, "game");
		
	}
	public void showScore()
	{
		CardLayout cl= (CardLayout) this.getCardPanel().getLayout();
		cl.show(cardPanel, "score");
	}
	public GamePanel getGamePanel()
	{
		return gamePanel;
	}
	public void setGamePanel(GamePanel panelGame)
	{
		this.gamePanel=panelGame;
	}
}


