package GameState;

import TileMap.Background;
import TileMap.SpritesExtra;

import java.awt.*;
import java.awt.event.KeyEvent;

import Sonidos.PlayerSonido;

public class MenuState extends GameState {
	
	private Background bg;
	private SpritesExtra logoJuego;
	private SpritesExtra logoComp;
	private PlayerSonido musicaFondo;

	private int currentChoice = 0;
	private String[] options = {
		"1 Jugador",
		"2 Jugadores",
		"Salir"
	};
	
	//private Color titleColor;
	//private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			

			bg = new Background("/Backgrounds/menubg2.gif", 1);
			bg.setVector(-0.1, 0);
			
		
			
			logoJuego = new SpritesExtra("/Logos/LogoJ.gif");
			logoJuego.setPosition(250, 20);
			logoComp = new SpritesExtra("/Logos/logoComp.png");
			logoComp.setPosition(1265, 280);
			
			/*titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic",Font.PLAIN,50);
			*/
			font = new Font("Arial", Font.PLAIN, 20);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		musicaFondo = new PlayerSonido("/Musica/CityRuins.mp3");
		musicaFondo.play();
		
	}
	
	public void init() {

	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// dibujar background
		bg.draw(g);
		
		// draw title
		/*g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Hazard", 250, 80);
		g.drawString("City", 280, 130);*/
		
		//dibujar logos
		logoJuego.draw(g);
		logoComp.draw(g);
		// sibujar menu
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i],280, 180 + i * 25);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVELBASE);
			
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.LEVELBASE);
			LevelBase.dosJug=true;
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			musicaFondo.stop();
			select();		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}










