package GameState;

import Main.GamePanel;
import TileMap.*;
import XML.MainXML;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Sonidos.PlayerSonido;
import Entidades.*;

import java.util.Random;

import AlmacenTemp.Temp;
import Efectos.Explosion;

public class Arena2players extends GameState {
	
	MainXML Datos= new MainXML();

	public static boolean dosJug;
	
	private SpritesExtra caraPlayer1;
	private SpritesExtra caraPlayer2;


	private SpritesExtra debajoSalud;
	private SpritesExtra barraSalud;
	private SpritesExtra debajoSalud2;
	private SpritesExtra barraSalud2;
	
	private TileMap tileMap;
	private Background bg;
	
	private MC player1;
	private MC player2;
	
	private ArrayList<Explosion> explosiones;
	
	private Font textMuerte;

	
	private PlayerSonido musicaFondo;
	private PlayerSonido sonidoMuerte;


	
	
	
	public Arena2players(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		musicaFondo = new PlayerSonido("/Musica/Box16.mp3");
		sonidoMuerte = new PlayerSonido("/Musica/YOUDIED.mp3");
		/**ANOTACION Markel:
		 * Investigation Start!
		 **/
		musicaFondo.play();
	}
	
	public void init() {
		dosJug=false;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tilesetB&N.gif");
		tileMap.loadMap("/Maps/Arena.map");
		tileMap.setPosition(0, 0);
		//tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/bg1.gif", 0.1);
		
		//primer jugador
		player1 = new MC(tileMap);
		player1.playerNumber=1;
		player1.setPosition(700, 400);
		//segundo jugador
		player2 = new MC(tileMap);
		player2.playerNumber=2;
		player2.setPlayerSkin();
		player2.setPosition(600,400);
		
		//Del temp a clase
		player1.setPelas(0);
		player1.setSalud(60);
		player1.setMunExp(0);		


		caraPlayer1 = new SpritesExtra("/Interfaz/P1Face.gif");
		caraPlayer1.setPosition(0, 0);
		debajoSalud =  new SpritesExtra("/Interfaz/bajosalud.gif");
		debajoSalud.setPosition(59, 1);
		barraSalud =  new SpritesExtra("/Interfaz/salud.gif");
		barraSalud.setPosition(-1, 2);
		barraSalud.cambiarSalud(player1.getSalud());
		
		caraPlayer2 = new SpritesExtra("/Interfaz/P2Face.gif");
		caraPlayer2.setPosition(0, 60);
		debajoSalud2 =  new SpritesExtra("/Interfaz/bajosalud.gif");
		debajoSalud2.setPosition(59, 61);
		barraSalud2 =  new SpritesExtra("/Interfaz/salud.gif");
		barraSalud2.setPosition(-1, 62);
		barraSalud2.cambiarSalud(player2.getSalud());
		
		
		new Font("Arial", Font.PLAIN, 10);
		textMuerte = new Font("Century Gothic",Font.PLAIN,100);
		
		explosiones = new ArrayList<Explosion>();


		
	}
	
	
	public void update() {
		if (player1.getSalud()<=0){

			Datos.GuardarDatos();
			musicaFondo.stop();
			sonidoMuerte.play();
			long millis = System.currentTimeMillis();
            while(millis+8500>=System.currentTimeMillis()) { 	
            }            
			//de clase a temp
			
			gsm.setState(GameStateManager.LEVELBASE);
		}
		if (player2.getSalud()<=0){

			Datos.GuardarDatos();
			musicaFondo.stop();
			sonidoMuerte.play();
			long millis = System.currentTimeMillis();
            while(millis+8500>=System.currentTimeMillis()) { 	
            }            
			//de clase a temp
			
			gsm.setState(GameStateManager.LEVELBASE);
		}
		
		
		//System.out.println(player1.getx()+"-----"+player1.gety());
		// update player	
		player1.update();
		player2.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player1.getx(),
			GamePanel.HEIGHT / 2 - player1.gety()
		);
		
		Comprobaciones();

		
		//background movimiento
		//bg.setPosition(tileMap.getx(), tileMap.gety());
		



		
	}
	
	public void draw(Graphics2D g) {
		//System.out.println(player1.getSalud());

		// dibujar bg
		if(bg!=null){
		bg.draw(g);
		}
		// dibujar tilemap
		if(tileMap!=null){
		tileMap.draw(g);
		}
		// dibujar jugador/es
		if(player1!=null){
		player1.draw(g);
		}
		player2.draw(g);
		

		
		/*if(enemigosKamikaze!=null){
		enemigosKamikaze.draw(g);
		}*/
		
		for(int i = 0; i< explosiones.size(); i++){
			explosiones.get(i).draw(g);
		}
		debajoSalud.draw(g);
		barraSalud.draw(g);
		debajoSalud2.draw(g);
		barraSalud2.draw(g);
		caraPlayer1.draw(g);
		caraPlayer2.draw(g);

		
		g.setColor(Color.RED);
		//Muerto
		if (player1.getSalud()<=0){
			g.setFont(textMuerte);
			//System.out.println("AAAAAAAAAAA");
			g.drawString("P1 DIED", 120, 180);
		}
		if (player2.getSalud()<=0){
			g.setFont(textMuerte);
			//System.out.println("AAAAAAAAAAA");
			g.drawString("P2 DIED", 120, 180);
		}
		
	}
	
	public void keyPressed(int k) {	

		if (k == KeyEvent.VK_ENTER){
			if(player1.gety()>=660){
				if(player1.gety()<=698){
					if (player1.getx()>=625){
						if (player1.getx()<= 695){
							musicaFondo.stop();
							//de clase a temp
	
							Temp.setDeDonde(2);
							gsm.setState(GameStateManager.LEVELBASE);
						}
					}
				}
			}
		}
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(true);}
		if (k == KeyEvent.VK_D){player1.setRight(true);}
		if (k == KeyEvent.VK_J){player1.setJumping(true);}
		if (k == KeyEvent.VK_K){player1.setDisparando();}
		
		if (k == KeyEvent.VK_Z){player2.setLeft(true);}
		if (k == KeyEvent.VK_X){player2.setRight(true);}
		if (k == KeyEvent.VK_N){player2.setJumping(true);}
		if (k == KeyEvent.VK_M){player2.setDisparando();}

		
	}
	
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(false);}
		if (k == KeyEvent.VK_D){player1.setRight(false);}
		if (k == KeyEvent.VK_J){player1.setJumping(false);}
		
		if (k == KeyEvent.VK_Z){player2.setLeft(false);}
		if (k == KeyEvent.VK_X){player2.setRight(false);}
		if (k == KeyEvent.VK_N){player2.setJumping(false);}
		if (k == KeyEvent.VK_M){player2.setDisparando();}

		
	}
	
	

	
	private void Comprobaciones(){
		for(int e = 0; e< player2.getBalas(); e++){
			if((player1.gety())+21>player2.getBalaY(e)
			   &&(player1.gety())-21<player2.getBalaY(e)
			   &&(player1.getx())-21<player2.getBalaX(e)
			   &&(player1.getx())+21>player2.getBalaX(e)){
				if(player2.gethitBala(e)==false){
				player2.hitBala(e);
				player1.setGolpe();
				if(player1.getSalud()<=0){}
				else{
				player1.setSalud(player1.getSalud()-5);
				barraSalud.cambiarSalud(-5);
				}
				}
			}
		}
		
		for(int e = 0; e< player1.getBalas(); e++){
			if((player2.gety())+21>player1.getBalaY(e)
			   &&(player2.gety())-21<player1.getBalaY(e)
			   &&(player2.getx())-21<player1.getBalaX(e)
			   &&(player2.getx())+21>player1.getBalaX(e)){
				if(player1.gethitBala(e)==false){
				player1.hitBala(e);
				player2.setGolpe();
				if(player2.getSalud()<=0){}
				else{
					player2.setSalud(player2.getSalud()-5);
				barraSalud2.cambiarSalud(-5);
				}
				}
			}
		}
		
	}
	
	

	public int randomNumeros(){
	    int randomInt;
	    randomInt=0;
	    Random randomGenerator = new Random();
	    randomInt = randomGenerator.nextInt(1250-50+1) + 50;
	    return randomInt;
	}
	
}












