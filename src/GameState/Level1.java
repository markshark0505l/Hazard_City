package GameState;

import Main.GamePanel;
import TileMap.*;
import XML.MainXML;

import java.awt.*;
import java.awt.event.KeyEvent;

import AlmacenTemp.Temp;
import Entidades.*;

public class Level1 extends GameState {
	MainXML Datos= new MainXML();
	//private Temp guardarPaCambio;
	
	public static boolean dosJug;
	
	
	private SpritesExtra caraPlayer1;
	private SpritesExtra caraPlayer2;
	private SpritesExtra caraPlayer3;
	private SpritesExtra debajoSalud;
	private SpritesExtra barraSalud;
	

	
	private TileMap tileMap;
	private Background bg;
	
	private MC player1;
	private MC player2;
	
	private MC franc1;

	
	

	
	
	
	
	public Level1(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		dosJug=false;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tilesetB&N.gif");
		tileMap.loadMap("/Maps/Level1.map");
		tileMap.setPosition(0, 0);
		//tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/bg1.gif", 0.1);
		
		//primer jugador
		player1 = new MC(tileMap);
		player1.playerNumber=1;
		player1.setPosition(100, 280);
		
		//Cargar datos guardados a la clase
		player1.setPelas(Temp.getPelas());
		player1.setSalud(Temp.getSalud());
		player1.setMunExp(Temp.getMunExp());
		Datos.GuardarDatos();
		
		franc1 = new MC(tileMap);
		franc1.playerNumber=4;
		franc1.setPlayerSkin();
		franc1.setPosition(2500, 430);
		franc1.setJumping(true);
		/**ANOTACION Markel:
		 * Like in firelink shrine!
		 * how nostalgic... 
		 **/

		
		caraPlayer1 = new SpritesExtra("/Interfaz/P1Face.gif");
		caraPlayer1.setPosition(0, 0);
		caraPlayer2 = new SpritesExtra("/Interfaz/P1Face2.gif");
		caraPlayer2.setPosition(0, 0);
		caraPlayer3 = new SpritesExtra("/Interfaz/P1Face3.gif");
		caraPlayer3.setPosition(0, 0);
		debajoSalud =  new SpritesExtra("/Interfaz/bajosalud.gif");
		debajoSalud.setPosition(59, 1);
		barraSalud =  new SpritesExtra("/Interfaz/salud.gif");
		barraSalud.setPosition(-1, 2);

		
		barraSalud.cambiarSalud(player1.getSalud());
		
		franc1.setLeft(true);
		
		
	}
	
	
	public void update() {
		//puerta 1
		enemigosUpdate();
		if (player1.getx()>=2850){
				Temp.setPelas(player1.getpelas());
				Temp.setSalud(player1.getSalud());
				Temp.setMunExp(player1.getMunExp());
				Datos.GuardarDatos();
				gsm.setState(GameStateManager.LEVELBASE);

			}
		// update player	
		player1.update();

		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player1.getx(),
			GamePanel.HEIGHT / 2 - player1.gety()
		);
		//franc1.update();
		
		
		//background movimiento
		//bg.setPosition(tileMap.getx(), tileMap.gety());
		
		
	}
	
	private void enemigosUpdate() {
		if(franc1.palau==false){
			franc1.setLeft(true);
			franc1.palau=true;
		}else{
			franc1.setLeft(false);
		}
			
		if(player1.getx()>1700){
			if(franc1.objetivoON==false){
			franc1.objetivoX=player1.getx();
			franc1.objetivoY=player1.gety();
			franc1.objetivoON=true;
			franc1.setDisparando();
			System.out.println("DISPARA");
			}else{
				for(int e = 0; e< franc1.getBalas(); e++){
					System.out.println("BALA"+e);
					if(franc1.getBalaY(e)>franc1.objetivoY){
						franc1.setBalaY(e, -8);
						System.out.println("GORA"+e);
					}
					else{
						franc1.setBalaY(e, 8);
						System.out.println("BERA"+e);
					}
					/*if(franc1.getBalaX(e)>franc1.objetivoX){
						franc1.setBalaX(e, -8);
						System.out.println("GORA"+e);
					}
					else{
						franc1.setBalaX(e, 8);
						System.out.println("BERA"+e);
					}*/
				}
			}
			if(franc1.getBalas()==0){
				franc1.objetivoON=false;
			}
		
		}
		
	}

	public void draw(Graphics2D g) {
		//System.out.println(player1.getx()+"-----"+player1.gety());
		//System.out.println(player1.getSalud());
		// dibujar bg
		bg.draw(g);
		
		// dibujar tilemap
		tileMap.draw(g);
		
		// dibujar jugador/es
		player1.draw(g);

		//franc1.draw(g);


		/*g.setColor(Color.RED);
		g.drawString(textoFlotante[0],280, 180);
		g.drawString(textoFlotante[1],280, 180);*/
		debajoSalud.draw(g);
		barraSalud.draw(g);
		if(player1.getSalud()>40){
			caraPlayer1.draw(g);
		}else if (player1.getSalud()>10){
			caraPlayer2.draw(g);
		}else{
			caraPlayer3.draw(g);
		}
		

	}
	
	public void keyPressed(int k) {			
					



				

		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(true);}
		if (k == KeyEvent.VK_D){player1.setRight(true);}
		if (k == KeyEvent.VK_J){player1.setJumping(true);}
		if (k == KeyEvent.VK_K){player1.setDisparando();}
		if (k == KeyEvent.VK_M){franc1.setDisparando();}
		//if (k == KeyEvent.VK_X){player1.setGolpe();}
		/*if (k == KeyEvent.VK_L){player.setAtacandoCQC();}*/
		if(dosJug==true){if (k == KeyEvent.VK_O){player2.setLeft(true);}
		if (k == KeyEvent.VK_P){player2.setRight(true);}
		if (k == KeyEvent.VK_I){player2.setJumping(true);}
		if (k == KeyEvent.VK_U){player2.setDisparando();}}
		//if (k == KeyEvent.VK_K){player1.setDisparando();}

	}
	
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(false);}
		if (k == KeyEvent.VK_D){player1.setRight(false);}
		if (k == KeyEvent.VK_J){player1.setJumping(false);}
		if(dosJug==true){if (k == KeyEvent.VK_O){player2.setLeft(false);}
		if (k == KeyEvent.VK_P){player2.setRight(false);}
		if (k == KeyEvent.VK_I){player2.setJumping(false);}}
		
	}
	
}

