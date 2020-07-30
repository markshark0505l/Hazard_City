package GameState;

import Main.GamePanel;
import TileMap.*;
import XML.MainXML;

import java.awt.*;
import java.awt.event.KeyEvent;

import AlmacenTemp.Temp;
import Entidades.*;

public class LevelBase extends GameState {
	MainXML Datos= new MainXML();
	//private Temp guardarPaCambio;
	
	public static boolean dosJug;
	
	
	private SpritesExtra caraPlayer1;
	private SpritesExtra caraPlayer2;
	private SpritesExtra caraPlayer3;
	private SpritesExtra debajoSalud;
	private SpritesExtra barraSalud;
	
	private SpritesExtra cara2Player;
	private SpritesExtra debajoSalud2;
	private SpritesExtra barraSalud2;
	
	private TileMap tileMap;
	private Background bg;
	
	private MC player1;
	private MC player2;
	
	private MC bondFire;
	private MC vendedor;
	private MC botiquin;
	private MC munExComp;
	
	
	private String[] textoFlotante = {
			"Arcade",
			"Stage 1",
			"2Player Arena",
		};
	private Font font;

	
	
	
	
	
	public LevelBase(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		dosJug=false;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/MapaBase.map");
		tileMap.setPosition(0, 0);
		//tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/bg1.gif", 0.1);
		
		//primer jugador
		player1 = new MC(tileMap);
		player1.playerNumber=1;
		player1.setPosition(100, 1120);
		
		//Cargar datos guardados a la clase
		/**ANOTACION Markel:
		 * Now, these points of data
		 * Make a beautiful line.
		 * And we're out of beta.
		 * We're releasing on time!
		 * So I'm glad, I got burned
		 * Think of all the things we learned
		 * For the people who are
		 * Still alive.
		 */
		player1.setPelas(Temp.getPelas());
		player1.setSalud(Temp.getSalud());
		player1.setMunExp(Temp.getMunExp());
		if(Temp.getDeDonde()==2){
			player1.setPosition(950, 810);
		}
		Datos.GuardarDatos();
		
		//segundo jugador
		player2 = new MC(tileMap);
		player2.playerNumber=2;
		player2.setPlayerSkin();
		player2.setPosition(80, 810);
		
		
		/**ANOTACION Markel:
		 * Like in firelink shrine!
		 * how nostalgic... 
		 **/
		bondFire = new MC(tileMap);
		bondFire.playerNumber=5;
		bondFire.setPlayerSkin();
		bondFire.setPosition(180, 1145);
		bondFire.setZonaColision(20,20);
		
		vendedor = new MC(tileMap);
		vendedor.playerNumber=6;
		vendedor.setPlayerSkin();
		vendedor.setPosition(1080, 1020);
		vendedor.update();
		
		botiquin = new MC(tileMap);
		botiquin.playerNumber=7;
		botiquin.setPlayerSkin();
		botiquin.setPosition(1050, 1020);
		botiquin.update();

		
		munExComp = new MC(tileMap);
		munExComp.playerNumber=8;
		munExComp.setPlayerSkin();
		munExComp.setPosition(1110, 1020);
		munExComp.update();
		
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
		
		
		cara2Player = new SpritesExtra("/Interfaz/P2Face.gif");
		cara2Player.setPosition(0, 60);
		debajoSalud2 =  new SpritesExtra("/Interfaz/bajosalud.gif");
		debajoSalud2.setPosition(59, 61);
		barraSalud2 =  new SpritesExtra("/Interfaz/salud.gif");
		barraSalud2.setPosition(-1, 62);
		barraSalud2.cambiarSalud(player2.getSalud());
		

		font = new Font("Arial", Font.PLAIN, 10);
		barraSalud.cambiarSalud(player1.getSalud());
		
	}
	
	
	public void update() {
		
		vendedor.update();
		botiquin.update();
		munExComp.update();
		// update player	
		player1.update();
		bondFire.setDisparando();
		bondFire.update();
		if(dosJug==true){player2.update();}
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player1.getx(),
			GamePanel.HEIGHT / 2 - player1.gety()
		);
		
		
		/*if(muereunlanzabombas){
			explosiones.add(new Explosion(enemigo.getX(), enemigo.getY()));
		}*/
		
		
		//background movimiento
		//bg.setPosition(tileMap.getx(), tileMap.gety());
		
		
	}
	
	public void draw(Graphics2D g) {
		//System.out.println(player1.getx()+"-----"+player1.gety());
		//System.out.println(player1.getSalud());
		// dibujar bg
		bg.draw(g);
		
		// dibujar tilemap
		tileMap.draw(g);
		
		// dibujar jugador/es
		
		
		vendedor.draw(g);
		munExComp.draw(g);
		botiquin.draw(g);
		player1.draw(g);
		if(dosJug==true){player2.draw(g);}
		bondFire.draw(g);
		//eventos de mapa
		System.out.println(player1.getx()+"------"+player1.gety());
		
		if(player1.gety()>=770){
			if(player1.gety()<=850){
				//puerta 1
				if (player1.getx()>=930){
					if (player1.getx()<= 970){
						g.setColor(Color.RED);
						g.drawString(textoFlotante[0],315, 150);
					}
				}
				//puerta 2
				if (player1.getx()>=1050){
					if (player1.getx()<= 1090){
						g.setColor(Color.RED);
						g.drawString(textoFlotante[1],315, 150);
					}
				}
				//puerta 3
				if (player1.getx()>=1160){
					if (player1.getx()<= 1200){
						g.setColor(Color.RED);
						g.drawString(textoFlotante[2],315, 150);
					}
				}
			}
		}
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
		if(dosJug){
			debajoSalud2.draw(g);
			barraSalud2.draw(g);
			cara2Player.draw(g);

		}
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(player1.getpelasString(), 61, 22);
		g.setColor(Color.GRAY);
		g.drawString(player1.getMunExp()+"", 61, 35);
	}
	
	public void keyPressed(int k) {			
		if (k == KeyEvent.VK_ENTER){
			if(player1.gety()>=770){
				if(player1.gety()<=850){
					//puerta 1
					if (player1.getx()>=930){
						if (player1.getx()<= 970){
							//de clase a temp
							Temp.setPelas(player1.getpelas());
							Temp.setSalud(player1.getSalud());
							Temp.setMunExp(player1.getMunExp());
							gsm.setState(GameStateManager.LEVELARENA);
							/**ANOTACION Markel:
							 * It's A Trap!
							﻿**/
						}
					}
					//puerta 2
					if (player1.getx()>=1050){
						if (player1.getx()<= 1090){
							gsm.setState(GameStateManager.LEVEL1);
						}
					}
					//Puerta3
					if (player1.getx()>=1160){
						if (player1.getx()<= 1200){
							gsm.setState(GameStateManager.ARENA2PLAYERS);
						}
					}

				}
			}
			//Tienda
			/**ANOTACION Markel:
			 * Buy stuff now or kick yourself later,
			 * Everdays a sale,
			 * every sales a win,
			 * Every day is great when you're me.
			﻿**/
			if(player1.gety()>=1000&&player1.gety()<=1030){
				//Botiquin
				if (player1.getx()>=1030){
					if (player1.getx()<= 1080){
						if(player1.getpelas()>=50){
							player1.setSalud(60);
							barraSalud.setPosition(-1, 2);
							barraSalud.cambiarSalud(60);
							player1.setPelas(player1.getpelas()-50);
						}
					}
				}
				//Balas
				if (player1.getx()>=1095){
					if (player1.getx()<= 1130){
						if(player1.getpelas()>=5){
							player1.setMunExp(player1.getMunExp()+1);
							player1.setPelas(player1.getpelas()-5);
							

						}
					}
				}
				Temp.setPelas(player1.getpelas());
				Temp.setSalud(player1.getSalud());
				Temp.setMunExp(player1.getMunExp());
				Datos.GuardarDatos();
			}
		}
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(true);}
		if (k == KeyEvent.VK_D){player1.setRight(true);}
		if (k == KeyEvent.VK_J){player1.setJumping(true);}
		if (k == KeyEvent.VK_K){player1.setDisparando();}
		//if (k == KeyEvent.VK_X){player1.setGolpe();}
		/*if (k == KeyEvent.VK_L){player.setAtacandoCQC();}*/
		if(dosJug==true){if (k == KeyEvent.VK_Z){player2.setLeft(true);}
		if (k == KeyEvent.VK_X){player2.setRight(true);}
		if (k == KeyEvent.VK_N){player2.setJumping(true);}
		if (k == KeyEvent.VK_M){player2.setDisparando();}}
		//if (k == KeyEvent.VK_K){player1.setDisparando();}

	}
	
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(false);}
		if (k == KeyEvent.VK_D){player1.setRight(false);}
		if (k == KeyEvent.VK_J){player1.setJumping(false);}
		if(dosJug==true){if (k == KeyEvent.VK_Z){player2.setLeft(false);}
		if (k == KeyEvent.VK_X){player2.setRight(false);}
		if (k == KeyEvent.VK_N){player2.setJumping(false);}}
		
	}
	
}

